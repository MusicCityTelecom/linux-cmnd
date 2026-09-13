/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.io.Destination;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.ConstructorMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.GetCommandMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.GetDecoderMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.GetSicpCommandMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.protocol.sicp.Commands;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommand;
import be.tpvision.smartcontrol.protocol.sicp.SicpCommandFactory;
import be.tpvision.smartcontrol.protocol.sicp.SicpVersion;
import org.springframework.util.Assert;

public abstract class SicpCommandFactoryImpl
implements SicpCommandFactory {
    private final SicpVersion sicpVersion;
    private final Commands commands;

    public SicpCommandFactoryImpl(SicpVersion sicpVersion, Commands commands) {
        Assert.notNull((Object)sicpVersion, ConstructorMessages.SICP_VERSION_CAN_NOT_BE_NULL);
        Assert.notNull((Object)commands, ConstructorMessages.COMMANDS_CAN_NOT_BE_NULL);
        this.sicpVersion = sicpVersion;
        this.commands = commands;
    }

    @Override
    public SicpVersion getSicpVersion() {
        return this.sicpVersion;
    }

    @Override
    public Commands getCommands() {
        return this.commands;
    }

    private Command getCommand(Command.Type type, Command.Setting setting) {
        Assert.notNull((Object)type, GetCommandMessages.TYPE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, GetCommandMessages.SETTING_CAN_NOT_BE_NULL);
        Command command = this.commands.find(type, setting);
        if (command == null) {
            String sicpVersionString = this.sicpVersion.getName();
            Assert.state(sicpVersionString != null, GetCommandMessages.SICP_VERSION_STRING_CAN_NOT_BE_NULL);
            String typeString = String.valueOf((Object)type);
            String settingString = String.valueOf((Object)setting);
            String commandNotFoundMessage = GetCommandMessages.getCommandNotFoundMessage(sicpVersionString, typeString, settingString);
            throw new UnsupportedOperationException(commandNotFoundMessage);
        }
        return command;
    }

    @Override
    public Codec<? extends DeviceSetting> getDecoder(byte settingByte) {
        Command command = this.commands.find(settingByte);
        Assert.state(command != null, GetDecoderMessages.COMMAND_CAN_NOT_BE_NULL);
        return command.getDecoder();
    }

    @Override
    public SicpCommand getSicpCommand(int controlId, int groupId, Command.Type type, Command.Setting setting) {
        Assert.notNull((Object)type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
        Command command = this.getCommand(type, setting);
        Assert.state(command != null, GetSicpCommandMessages.COMMAND_CAN_NOT_BE_NULL);
        byte settingByte = command.getSettingByte();
        return new SicpCommand(controlId, groupId, settingByte);
    }

    @Override
    public SicpCommand getSicpCommand(int controlId, int groupId, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting) {
        Assert.notNull((Object)type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
        Assert.notNull((Object)deviceSetting, GetSicpCommandMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
        Command command = this.getCommand(type, setting);
        Assert.state(command != null, GetSicpCommandMessages.COMMAND_CAN_NOT_BE_NULL);
        byte settingByte = command.getSettingByte();
        Codec<? extends DeviceSetting> codec = command.getEncoder();
        if (codec == null) {
            String sicpVersionString = this.sicpVersion.getName();
            Assert.state(sicpVersionString != null, GetSicpCommandMessages.SICP_VERSION_STRING_CAN_NOT_BE_NULL);
            String typeString = String.valueOf((Object)type);
            String settingString = String.valueOf((Object)setting);
            String encoderNotFoundMessage = GetSicpCommandMessages.getEncoderNotFoundMessage(sicpVersionString, typeString, settingString);
            throw new UnsupportedOperationException(encoderNotFoundMessage);
        }
        Class<? extends DeviceSetting> encoderDeviceSettingClass = codec.getDeviceSettingClass();
        Assert.state(encoderDeviceSettingClass != null, GetSicpCommandMessages.ENCODER_DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL);
        Class<? extends DeviceSetting> deviceSettingClass = deviceSetting.getClass();
        Assert.state(deviceSettingClass != null, GetSicpCommandMessages.DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL);
        if (!deviceSettingClass.isAssignableFrom(encoderDeviceSettingClass)) {
            String encoderDeviceSettingClassString = encoderDeviceSettingClass.toString();
            String deviceSettingClassString = deviceSettingClass.toString();
            String notTheRightDeviceSettingMessage = GetSicpCommandMessages.getWrongDeviceSettingMessage(encoderDeviceSettingClassString, deviceSettingClassString);
            throw new IllegalArgumentException(notTheRightDeviceSettingMessage);
        }
        byte[] protocolSetting = codec.toProtocol(deviceSetting);
        return new SicpCommand(controlId, groupId, settingByte, protocolSetting);
    }

    @Override
    public SicpCommand getSicpCommand(Destination destination, Command.Type type, Command.Setting setting) {
        Assert.notNull((Object)destination, GetSicpCommandMessages.DESTINATION_CAN_NOT_BE_NULL);
        Assert.notNull((Object)type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
        int controlId = destination.getControlId();
        int groupId = destination.getGroupId();
        return this.getSicpCommand(controlId, groupId, type, setting);
    }

    @Override
    public SicpCommand getSicpCommand(Destination destination, Command.Type type, Command.Setting setting, DeviceSetting deviceSetting) {
        Assert.notNull((Object)destination, GetSicpCommandMessages.DESTINATION_CAN_NOT_BE_NULL);
        Assert.notNull((Object)type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, GetSicpCommandMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
        int controlId = destination.getControlId();
        int groupId = destination.getGroupId();
        return this.getSicpCommand(controlId, groupId, type, setting, deviceSetting);
    }
}

