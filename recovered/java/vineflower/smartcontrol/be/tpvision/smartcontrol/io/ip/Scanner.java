package be.tpvision.smartcontrol.io.ip;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.system.ModelNumberFirmwareVersionBuildDateInfo;
import be.tpvision.smartcontrol.domain.device_settings.system.SicpAndPlatformInfo;
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
   public Scanner(
      @Value("${smartcontrol.scanner.maximum-number-of-threads}") final int maximumNumberOfThreads,
      @Value("${smartcontrol.scanner.maximum-group-id}") final int maximumGroupId,
      final CommandService commandService,
      final DeviceService deviceService
   ) {
      this.maximumNumberOfThreads = maximumNumberOfThreads;
      this.maximumGroupId = maximumGroupId;
      this.commandService = commandService;
      this.deviceService = deviceService;
   }

   public List<Device> scan(final String subnet, final int port, final int timeout) {
      try {
         this.executorService = Executors.newFixedThreadPool(this.maximumNumberOfThreads);
         List<String> ipAddressesList = this.listIpAddresses(subnet, port, timeout);
         return ipAddressesList.stream().map(ip -> {
            logger.info("Detected possible device on IP: {}. ", ip);
            logger.debug("Scanning ... ");
            return this.detectDevices(ip, port);
         }).map(devicesListFuture -> {
            List<Device> devicesList = null;

            try {
               devicesList = devicesListFuture.get();
            } catch (InterruptedException | ExecutionException e) {
               logger.debug("caught exception: {}.", e.getCause());
            }

            return devicesList;
         }).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
      } finally {
         this.executorService.shutdown();
      }
   }

   private List<String> listIpAddresses(final String subnet, final int port, final int timeout) {
      List<Future<String>> ipFutureList = new ArrayList<>();

      for (int hostId = 1; hostId <= 255; hostId++) {
         String ip = subnet + "." + hostId;
         Future<String> ipFuture = this.isListening(ip, port, timeout);
         ipFutureList.add(ipFuture);
      }

      return ipFutureList.stream().map(ipFuturex -> {
         String ipx = null;

         try {
            ipx = (String)ipFuturex.get();
         } catch (InterruptedException | ExecutionException e) {
            String message = e.getMessage();
            logger.error(message, e);
         }

         return ipx;
      }).filter(Objects::nonNull).collect(Collectors.toList());
   }

   private Future<String> isListening(final String ip, final int port, final int timeout) {
      return this.executorService.submit(() -> {
         try (Socket socket = new Socket()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            socket.connect(inetSocketAddress, timeout);
            return ip;
         } catch (Exception ex) {
            return null;
         }
      });
   }

   private void shouldSleep(final boolean shouldSleep) {
      if (shouldSleep) {
         try {
            Thread.sleep(1500L);
         } catch (InterruptedException var3) {
         }
      }
   }

   private Device detectDevice(final String ip, final int port, final int controlId, final int groupId, boolean isDaisyChained) {
      SicpAndPlatformInfo sicpAndPlatformInfo = SicpAndPlatformInfo.SICP_VERSION;
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
      IpDestination ipDestination = new IpDestination(inetSocketAddress, controlId, groupId);
      Device device = new Device();
      device.setAddress(ipDestination);
      StringWrapper sicpVersion = this.commandService
         .send(device, Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, sicpAndPlatformInfo, StringWrapper.class);
      this.shouldSleep(isDaisyChained);
      if (sicpVersion == null) {
         return null;
      }

      device.setSicpVersion(sicpVersion);
      StringWrapper serialCode = this.commandService.send(device, Command.Type.GET, Command.Setting.SERIAL_CODE, StringWrapper.class);
      this.shouldSleep(isDaisyChained);
      if (serialCode != null) {
         device.setSerialCode(serialCode);
         String serialCodeString = serialCode.getValue();
         Device existingDevice = this.deviceService.getDeviceBySerialCode(serialCodeString);
         if (existingDevice != null) {
            IpDestination existingIpDestination = existingDevice.getAddress();
            if (!ipDestination.equals(existingIpDestination)) {
               existingDevice.setAddress(ipDestination);
               this.deviceService.updateDevice(existingDevice);
               return null;
            }
         }
      }

      ModelNumberFirmwareVersionBuildDateInfo buildDateInfo = ModelNumberFirmwareVersionBuildDateInfo.MODEL_NUMBER;
      StringWrapper modelNumber = this.commandService
         .send(device, Command.Type.GET, Command.Setting.MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE, buildDateInfo, StringWrapper.class);
      this.shouldSleep(isDaisyChained);
      if (modelNumber != null) {
         device.setModelNumber(modelNumber);
      }

      return device;
   }

   private List<Device> detectDaisyChainedDevices(final String ip, final int port, int controlId, final int groupId) {
      boolean done = false;
      List<Device> daisyChainedDevices = new ArrayList<>();

      while (!done) {
         Device chainedDevice = this.detectDevice(ip, port, ++controlId, groupId, true);
         if (chainedDevice != null) {
            daisyChainedDevices.add(chainedDevice);
         } else {
            done = true;
         }
      }

      return daisyChainedDevices;
   }

   private Future<List<Device>> detectDevices(final String ip, final int port) {
      return this.executorService.submit(() -> {
         int groupId = 0;
         int controlId = 1;
         boolean done = false;
         List<Device> devices = new ArrayList<>();

         while (!done && groupId <= this.maximumGroupId) {
            Device device = this.detectDevice(ip, port, controlId, groupId, false);
            if (device != null) {
               devices.add(device);
               List<Device> daisyChainedDevices = this.detectDaisyChainedDevices(ip, port, controlId, groupId);
               devices.addAll(daisyChainedDevices);
               done = true;
            } else {
               logger.debug("ip {}: device not responding on control-id {} and group-id {}.", ip, controlId, groupId);

               try {
                  Thread.sleep(1500L);
               } catch (InterruptedException e) {
                  String message = e.getMessage();
                  logger.error(message, e);
               }
            }

            groupId++;
         }

         return devices;
      });
   }
}
