/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.system.SicpAndPlatformInfo;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.sicp188.NettyCommandSender;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_factory_provider_impl.GetSicpFactoryMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_factory_provider_impl.GetSicpVersionMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_factory_provider_impl.SetDeviceServiceMessages;
import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommand;
import be.tpvision.smartcontrol.protocol.sicp.SicpFactoryProvider;
import be.tpvision.smartcontrol.protocol.sicp199.SicpFactory;
import be.tpvision.smartcontrol.protocol.sicp200.SicpCommandFactory;
import be.tpvision.smartcontrol.protocol.sicp200.SicpHimalayaFactory;
import be.tpvision.smartcontrol.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SicpFactoryProviderImpl
implements SicpFactoryProvider {
    private DeviceService deviceService;

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        Assert.notNull((Object)deviceService, SetDeviceServiceMessages.DEVICE_SERVICE_CAN_NOT_BE_NULL);
        this.deviceService = deviceService;
    }

    private StringWrapper getSicpVersion(Device device) {
        StringWrapper sicpVersion;
        Device dbDevice;
        Long id = device.getId();
        if (id != null && (dbDevice = this.deviceService.getDevice(id)) != null && (sicpVersion = dbDevice.getSicpVersion()) != null) {
            return sicpVersion;
        }
        SicpCommandFactory sicpCommandFactory = SicpCommandFactory.getInstance();
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, GetSicpVersionMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        SicpAndPlatformInfo sicpAndPlatformInfo = SicpAndPlatformInfo.SICP_VERSION;
        SicpCommand sicpCommand = sicpCommandFactory.getSicpCommand(ipDestination, Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, sicpAndPlatformInfo);
        NettyCommandSender nettyCommandSender = NettyCommandSender.getInstance();
        return nettyCommandSender.send((Request)sicpCommand, ipDestination, StringWrapper.class);
    }

    private StringWrapper getDevicePlatform(Device device) {
        StringWrapper devicePlatform;
        Device dbDevice;
        Long id = device.getId();
        if (id != null && (dbDevice = this.deviceService.getDevice(id)) != null && (devicePlatform = dbDevice.getPlatformLabel()) != null) {
            return devicePlatform;
        }
        SicpCommandFactory sicpCommandFactory = SicpCommandFactory.getInstance();
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, GetSicpVersionMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        SicpAndPlatformInfo sicpAndPlatformInfo = SicpAndPlatformInfo.PLATFORM_LABEL;
        SicpCommand sicpCommand = sicpCommandFactory.getSicpCommand(ipDestination, Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, sicpAndPlatformInfo);
        NettyCommandSender nettyCommandSender = NettyCommandSender.getInstance();
        return nettyCommandSender.send((Request)sicpCommand, ipDestination, StringWrapper.class);
    }

    private StringWrapper getDevicePlatformVersion(Device device) {
        StringWrapper devicePlatformVersion;
        Device dbDevice;
        Long id = device.getId();
        if (id != null && (dbDevice = this.deviceService.getDevice(id)) != null && (devicePlatformVersion = dbDevice.getPlatformVersion()) != null) {
            return devicePlatformVersion;
        }
        SicpCommandFactory sicpCommandFactory = SicpCommandFactory.getInstance();
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, GetSicpVersionMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        SicpAndPlatformInfo sicpAndPlatformInfo = SicpAndPlatformInfo.PLATFORM_VERSION;
        SicpCommand sicpCommand = sicpCommandFactory.getSicpCommand(ipDestination, Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, sicpAndPlatformInfo);
        NettyCommandSender nettyCommandSender = NettyCommandSender.getInstance();
        return nettyCommandSender.send((Request)sicpCommand, ipDestination, StringWrapper.class);
    }

    @Override
    public be.tpvision.smartcontrol.protocol.sicp.SicpFactory getSicpFactory(Device device) {
        StringWrapper sicpVersion = device.getSicpVersion();
        if (sicpVersion == null) {
            sicpVersion = this.getSicpVersion(device);
        }
        if (sicpVersion != null) {
            Long id = device.getId();
            if (id != null) {
                Device dbDevice = this.deviceService.getDevice(id);
                String deviceNotFoundMessage = GetSicpFactoryMessages.getDeviceNotFoundMessage(id);
                Assert.state(dbDevice != null, deviceNotFoundMessage);
                dbDevice.setSicpVersion(sicpVersion);
                this.deviceService.updateDevice(dbDevice);
            }
            String sicpVersionValue = sicpVersion.getValue();
            if (this.checkIfHimalayaDevice(device) && SicpFactoryProviderImpl.isValidHimalayaLinuxSicpVersion(sicpVersionValue)) {
                return SicpHimalayaFactory.getInstance();
            }
            if (sicpVersionValue != null) {
                switch (sicpVersionValue = sicpVersionValue.trim().toLowerCase()) {
                    case "v1.88": 
                    case "1.88": {
                        return be.tpvision.smartcontrol.protocol.sicp188.SicpFactory.getInstance();
                    }
                    case "v1.97": 
                    case "1.97": {
                        return be.tpvision.smartcontrol.protocol.sicp197.SicpFactory.getInstance();
                    }
                    case "v1.99": 
                    case "1.99": {
                        return SicpFactory.getInstance();
                    }
                    case "v2.00": 
                    case "2.00": {
                        return be.tpvision.smartcontrol.protocol.sicp200.SicpFactory.getInstance();
                    }
                    case "v2.02": 
                    case "2.02": {
                        return be.tpvision.smartcontrol.protocol.sicp202.SicpFactory.getInstance();
                    }
                    case "v2.03": 
                    case "2.03": {
                        return be.tpvision.smartcontrol.protocol.sicp203.SicpFactory.getInstance();
                    }
                    case "v2.04": 
                    case "2.04": {
                        return be.tpvision.smartcontrol.protocol.sicp204.SicpFactory.getInstance();
                    }
                    case "v2.05": 
                    case "2.05": {
                        return be.tpvision.smartcontrol.protocol.sicp205.SicpFactory.getInstance();
                    }
                    case "v2.07": 
                    case "2.07": {
                        return be.tpvision.smartcontrol.protocol.sicp207.SicpFactory.getInstance();
                    }
                    case "v2.08": 
                    case "2.08": {
                        return be.tpvision.smartcontrol.protocol.sicp208.SicpFactory.getInstance();
                    }
                }
            }
        }
        return be.tpvision.smartcontrol.protocol.sicp208.SicpFactory.getInstance();
    }

    private static boolean isValidHimalayaLinuxSicpVersion(String sicpVersionValue) {
        if (StringUtils.isBlank(sicpVersionValue)) {
            return true;
        }
        String pureValue = sicpVersionValue.toLowerCase().replace("v", "");
        return !"1.88".equals(pureValue) && pureValue.compareTo("2.0") <= 0;
    }

    private boolean checkIfHimalayaDevice(Device device) {
        StringWrapper devicePlatform = device.getPlatformLabel();
        StringWrapper devicePlatformVersion = device.getPlatformVersion();
        if (devicePlatform == null) {
            devicePlatform = this.getDevicePlatform(device);
        }
        if (devicePlatformVersion == null) {
            devicePlatformVersion = this.getDevicePlatformVersion(device);
        }
        if (devicePlatform != null && devicePlatformVersion != null) {
            Long id = device.getId();
            if (id != null) {
                Device dbDevice = this.deviceService.getDevice(id);
                String deviceNotFoundMessage = GetSicpFactoryMessages.getDeviceNotFoundMessage(id);
                Assert.state(dbDevice != null, deviceNotFoundMessage);
                dbDevice.setPlatformLabel(devicePlatform);
                dbDevice.setPlatformVersion(devicePlatformVersion);
                this.deviceService.updateDevice(dbDevice);
            }
            String devicePlatformValue = devicePlatform.getValue();
            return devicePlatformValue.equalsIgnoreCase("himalaya");
        }
        return false;
    }
}

