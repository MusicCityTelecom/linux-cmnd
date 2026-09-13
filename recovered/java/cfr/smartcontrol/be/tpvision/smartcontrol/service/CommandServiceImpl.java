/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.io.CommandNotAcknowledgedException;
import be.tpvision.smartcontrol.io.CommandNotAvailableException;
import be.tpvision.smartcontrol.io.CommandSender;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.io.ip.sicp.DestinationUnreachableException;
import be.tpvision.smartcontrol.messages.services.command.GetRequestMessages;
import be.tpvision.smartcontrol.messages.services.command.SendMessages;
import be.tpvision.smartcontrol.messages.services.command.UpdateDeviceMessages;
import be.tpvision.smartcontrol.protocol.Request;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory;
import be.tpvision.smartcontrol.protocol.sicp.SicpFactory;
import be.tpvision.smartcontrol.protocol.sicp.SicpFactoryProvider;
import be.tpvision.smartcontrol.service.CommandService;
import be.tpvision.smartcontrol.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CommandServiceImpl
implements CommandService {
    private final SicpFactoryProvider sicpFactoryProvider;
    private final DeviceService deviceService;

    @Autowired
    public CommandServiceImpl(SicpFactoryProvider sicpFactoryProvider, DeviceService deviceService) {
        this.sicpFactoryProvider = sicpFactoryProvider;
        this.deviceService = deviceService;
    }

    @Override
    public void send(Device device, Command.Type type, Command.Setting setting) {
        DeviceSetting deviceSetting = null;
        this.send(device, type, setting, deviceSetting);
    }

    private Request getRequest(SicpFactory sicpFactory, IpDestination ipDestination, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting) {
        Assert.notNull((Object)sicpFactory, GetRequestMessages.SICP_FACTORY_CAN_NOT_BE_NULL);
        Assert.notNull((Object)ipDestination, GetRequestMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        Assert.notNull((Object)type, GetRequestMessages.TYPE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, GetRequestMessages.SETTING_CAN_NOT_BE_NULL);
        SicpCommandFactory sicpCommandFactory = sicpFactory.getSicpCommandFactory();
        Assert.state(sicpCommandFactory != null, GetRequestMessages.SICP_COMMAND_FACTORY_CAN_NOT_BE_NULL);
        if (deviceSetting != null) {
            return sicpCommandFactory.getSicpCommand(ipDestination, type, setting, deviceSetting);
        }
        return sicpCommandFactory.getSicpCommand(ipDestination, type, setting);
    }

    private void updateDevice(Device device, Command.Setting setting) {
        Assert.notNull((Object)device, UpdateDeviceMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, UpdateDeviceMessages.SETTING_CAN_NOT_BE_NULL);
        switch (setting) {
            case POWER_STATE: {
                device.setPowerState(null);
                this.deviceService.updateDevice(device);
                break;
            }
            case INPUT_SOURCE: {
                device.setInputSource(null);
                this.deviceService.updateDevice(device);
                break;
            }
        }
    }

    @Override
    public void send(Device device, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting) {
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, SendMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        SicpFactory sicpFactory = this.sicpFactoryProvider.getSicpFactory(device);
        Assert.state(sicpFactory != null, SendMessages.SICP_FACTORY_CAN_NOT_BE_NULL);
        Request request = this.getRequest(sicpFactory, ipDestination, type, setting, deviceSetting);
        CommandSender<IpDestination> commandSender = sicpFactory.getCommandSender();
        try {
            commandSender.send(request, ipDestination);
        }
        catch (CommandNotAcknowledgedException | CommandNotAvailableException | DestinationUnreachableException e) {
            this.updateDevice(device, setting);
            throw e;
        }
    }

    @Override
    public <T> T send(Device device, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting, Class<T> responseClass) {
        Assert.notNull(responseClass, SendMessages.RESPONSE_CLASS_CAN_NOT_BE_NULL);
        IpDestination ipDestination = device.getAddress();
        Assert.state(ipDestination != null, SendMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
        SicpFactory sicpFactory = this.sicpFactoryProvider.getSicpFactory(device);
        Assert.state(sicpFactory != null, SendMessages.SICP_FACTORY_CAN_NOT_BE_NULL);
        Request request = this.getRequest(sicpFactory, ipDestination, type, setting, deviceSetting);
        CommandSender<IpDestination> commandSender = sicpFactory.getCommandSender();
        try {
            if (DeviceSetting.class.isAssignableFrom(responseClass)) {
                DeviceSetting deviceSettingResponse = commandSender.send(request, ipDestination, DeviceSetting.class);
                return responseClass.cast(deviceSettingResponse);
            }
            return commandSender.send(request, ipDestination, responseClass);
        }
        catch (CommandNotAcknowledgedException | CommandNotAvailableException | DestinationUnreachableException e) {
            this.updateDevice(device, setting);
            throw e;
        }
    }
}

