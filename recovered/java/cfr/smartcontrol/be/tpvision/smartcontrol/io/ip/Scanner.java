/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io.ip;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.system.ModelNumberFirmwareVersionBuildDateInfo;
import be.tpvision.smartcontrol.domain.device_settings.system.SicpAndPlatformInfo;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.service.CommandService;
import be.tpvision.smartcontrol.service.DeviceService;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Scanner {
    public static final int DAISY_CHAINED_DELAY_MILLISECONDS = 1500;
    private static final Logger logger = LoggerFactory.getLogger(Scanner.class);
    private final int maximumNumberOfThreads;
    private final int maximumGroupId;
    private final CommandService commandService;
    private final DeviceService deviceService;
    private ExecutorService executorService;

    @Autowired
    public Scanner(@Value(value="${smartcontrol.scanner.maximum-number-of-threads}") int maximumNumberOfThreads, @Value(value="${smartcontrol.scanner.maximum-group-id}") int maximumGroupId, CommandService commandService, DeviceService deviceService) {
        this.maximumNumberOfThreads = maximumNumberOfThreads;
        this.maximumGroupId = maximumGroupId;
        this.commandService = commandService;
        this.deviceService = deviceService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Device> scan(String subnet, int port, int timeout) {
        try {
            this.executorService = Executors.newFixedThreadPool(this.maximumNumberOfThreads);
            List<String> ipAddressesList = this.listIpAddresses(subnet, port, timeout);
            List<Device> list = ipAddressesList.stream().map(ip -> {
                logger.info("Detected possible device on IP: {}. ", ip);
                logger.debug("Scanning ... ");
                return this.detectDevices((String)ip, port);
            }).map(devicesListFuture -> {
                List devicesList = null;
                try {
                    devicesList = (List)devicesListFuture.get();
                }
                catch (InterruptedException | ExecutionException e) {
                    logger.debug("caught exception: {}.", e.getCause());
                }
                return devicesList;
            }).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
            return list;
        }
        finally {
            this.executorService.shutdown();
        }
    }

    private List<String> listIpAddresses(String subnet, int port, int timeout) {
        ArrayList<Future<String>> ipFutureList = new ArrayList<Future<String>>();
        for (int hostId = 1; hostId <= 255; ++hostId) {
            String ip = subnet + "." + hostId;
            Future<String> ipFuture2 = this.isListening(ip, port, timeout);
            ipFutureList.add(ipFuture2);
        }
        return ipFutureList.stream().map(ipFuture -> {
            String ip = null;
            try {
                ip = (String)ipFuture.get();
            }
            catch (InterruptedException | ExecutionException e) {
                String message = e.getMessage();
                logger.error(message, e);
            }
            return ip;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Future<String> isListening(String ip, int port, int timeout) {
        return this.executorService.submit(() -> {
            try (Socket socket = new Socket();){
                InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
                socket.connect(inetSocketAddress, timeout);
                String string = ip;
                return string;
            }
            catch (Exception ex) {
                return null;
            }
        });
    }

    private void shouldSleep(boolean shouldSleep) {
        if (shouldSleep) {
            try {
                Thread.sleep(1500L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    private Device detectDevice(String ip, int port, int controlId, int groupId, boolean isDaisyChained) {
        SicpAndPlatformInfo sicpAndPlatformInfo = SicpAndPlatformInfo.SICP_VERSION;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
        IpDestination ipDestination = new IpDestination(inetSocketAddress, controlId, groupId);
        Device device = new Device();
        device.setAddress(ipDestination);
        StringWrapper sicpVersion = this.commandService.send(device, Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, sicpAndPlatformInfo, StringWrapper.class);
        this.shouldSleep(isDaisyChained);
        if (sicpVersion == null) {
            return null;
        }
        device.setSicpVersion(sicpVersion);
        StringWrapper serialCode = this.commandService.send(device, Command.Type.GET, Command.Setting.SERIAL_CODE, StringWrapper.class);
        this.shouldSleep(isDaisyChained);
        if (serialCode != null) {
            IpDestination existingIpDestination;
            device.setSerialCode(serialCode);
            String serialCodeString = serialCode.getValue();
            Device existingDevice = this.deviceService.getDeviceBySerialCode(serialCodeString);
            if (existingDevice != null && !ipDestination.equals(existingIpDestination = existingDevice.getAddress())) {
                existingDevice.setAddress(ipDestination);
                this.deviceService.updateDevice(existingDevice);
                return null;
            }
        }
        ModelNumberFirmwareVersionBuildDateInfo buildDateInfo = ModelNumberFirmwareVersionBuildDateInfo.MODEL_NUMBER;
        StringWrapper modelNumber = this.commandService.send(device, Command.Type.GET, Command.Setting.MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE, buildDateInfo, StringWrapper.class);
        this.shouldSleep(isDaisyChained);
        if (modelNumber != null) {
            device.setModelNumber(modelNumber);
        }
        return device;
    }

    private List<Device> detectDaisyChainedDevices(String ip, int port, int controlId, int groupId) {
        boolean done = false;
        ArrayList<Device> daisyChainedDevices = new ArrayList<Device>();
        while (!done) {
            Device chainedDevice;
            if ((chainedDevice = this.detectDevice(ip, port, ++controlId, groupId, true)) != null) {
                daisyChainedDevices.add(chainedDevice);
                continue;
            }
            done = true;
        }
        return daisyChainedDevices;
    }

    private Future<List<Device>> detectDevices(String ip, int port) {
        return this.executorService.submit(() -> {
            int controlId = 1;
            boolean done = false;
            ArrayList<Device> devices = new ArrayList<Device>();
            for (int groupId = 0; !done && groupId <= this.maximumGroupId; ++groupId) {
                Device device = this.detectDevice(ip, port, controlId, groupId, false);
                if (device != null) {
                    devices.add(device);
                    List<Device> daisyChainedDevices = this.detectDaisyChainedDevices(ip, port, controlId, groupId);
                    devices.addAll(daisyChainedDevices);
                    done = true;
                    continue;
                }
                logger.debug("ip {}: device not responding on control-id {} and group-id {}.", ip, controlId, groupId);
                try {
                    Thread.sleep(1500L);
                    continue;
                }
                catch (InterruptedException e) {
                    String message = e.getMessage();
                    logger.error(message, e);
                }
            }
            return devices;
        });
    }
}

