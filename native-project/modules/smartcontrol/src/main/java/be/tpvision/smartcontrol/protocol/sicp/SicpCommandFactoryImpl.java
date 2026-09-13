package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.io.Destination;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.ConstructorMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.GetCommandMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.GetDecoderMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl.GetSicpCommandMessages;
import org.springframework.util.Assert;

public abstract class SicpCommandFactoryImpl implements SicpCommandFactory {
   private final SicpVersion sicpVersion;
   private final Commands commands;

   public SicpCommandFactoryImpl(final SicpVersion sicpVersion, final Commands commands) {
      Assert.notNull(sicpVersion, ConstructorMessages.SICP_VERSION_CAN_NOT_BE_NULL);
      Assert.notNull(commands, ConstructorMessages.COMMANDS_CAN_NOT_BE_NULL);
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

   private Command getCommand(final Command.Type type, final Command.Setting setting) {
      Assert.notNull(type, GetCommandMessages.TYPE_CAN_NOT_BE_NULL);
      Assert.notNull(setting, GetCommandMessages.SETTING_CAN_NOT_BE_NULL);
      Command command = this.commands.find(type, setting);
      if (command == null) {
         String sicpVersionString = this.sicpVersion.getName();
         Assert.state(sicpVersionString != null, GetCommandMessages.SICP_VERSION_STRING_CAN_NOT_BE_NULL);
         String typeString = String.valueOf(type);
         String settingString = String.valueOf(setting);
         String commandNotFoundMessage = GetCommandMessages.getCommandNotFoundMessage(sicpVersionString, typeString, settingString);
         throw new UnsupportedOperationException(commandNotFoundMessage);
      } else {
         return command;
      }
   }

   @Override
   public Codec<? extends DeviceSetting> getDecoder(final byte settingByte) {
      Command command = this.commands.find(settingByte);
      Assert.state(command != null, GetDecoderMessages.COMMAND_CAN_NOT_BE_NULL);
      return command.getDecoder();
   }

   @Override
   public SicpCommand getSicpCommand(final int controlId, final int groupId, final Command.Type type, final Command.Setting setting) {
      Assert.notNull(type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
      Assert.notNull(setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
      Command command = this.getCommand(type, setting);
      Assert.state(command != null, GetSicpCommandMessages.COMMAND_CAN_NOT_BE_NULL);
      byte settingByte = command.getSettingByte();
      return new SicpCommand(controlId, groupId, settingByte);
   }

   @Override
   public SicpCommand getSicpCommand(
      final int controlId, final int groupId, final Command.Type type, final Command.Setting setting, final DeviceSetting deviceSetting
   ) {
      Assert.notNull(type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
      Assert.notNull(setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
      Assert.notNull(deviceSetting, GetSicpCommandMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
      Command command = this.getCommand(type, setting);
      Assert.state(command != null, GetSicpCommandMessages.COMMAND_CAN_NOT_BE_NULL);
      byte settingByte = command.getSettingByte();
      Codec codec = command.getEncoder();
      if (codec == null) {
         String sicpVersionString = this.sicpVersion.getName();
         Assert.state(sicpVersionString != null, GetSicpCommandMessages.SICP_VERSION_STRING_CAN_NOT_BE_NULL);
         String typeString = String.valueOf(type);
         String settingString = String.valueOf(setting);
         String encoderNotFoundMessage = GetSicpCommandMessages.getEncoderNotFoundMessage(sicpVersionString, typeString, settingString);
         throw new UnsupportedOperationException(encoderNotFoundMessage);
      } else {
         Class<?> encoderDeviceSettingClass = codec.getDeviceSettingClass();
         Assert.state(encoderDeviceSettingClass != null, GetSicpCommandMessages.ENCODER_DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL);
         Class<? extends DeviceSetting> deviceSettingClass = (Class<? extends DeviceSetting>)deviceSetting.getClass();
         Assert.state(deviceSettingClass != null, GetSicpCommandMessages.DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL);
         if (!deviceSettingClass.isAssignableFrom(encoderDeviceSettingClass)) {
            String encoderDeviceSettingClassString = encoderDeviceSettingClass.toString();
            String deviceSettingClassString = deviceSettingClass.toString();
            String notTheRightDeviceSettingMessage = GetSicpCommandMessages.getWrongDeviceSettingMessage(
               encoderDeviceSettingClassString, deviceSettingClassString
            );
            throw new IllegalArgumentException(notTheRightDeviceSettingMessage);
         } else {
            byte[] protocolSetting = codec.toProtocol(deviceSetting);
            return new SicpCommand(controlId, groupId, settingByte, protocolSetting);
         }
      }
   }

   @Override
   public SicpCommand getSicpCommand(final Destination destination, final Command.Type type, final Command.Setting setting) {
      Assert.notNull(destination, GetSicpCommandMessages.DESTINATION_CAN_NOT_BE_NULL);
      Assert.notNull(type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
      Assert.notNull(setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
      int controlId = destination.getControlId();
      int groupId = destination.getGroupId();
      return this.getSicpCommand(controlId, groupId, type, setting);
   }

   @Override
   public SicpCommand getSicpCommand(final Destination destination, final Command.Type type, final Command.Setting setting, final DeviceSetting deviceSetting) {
      Assert.notNull(destination, GetSicpCommandMessages.DESTINATION_CAN_NOT_BE_NULL);
      Assert.notNull(type, GetSicpCommandMessages.TYPE_CAN_NOT_BE_NULL);
      Assert.notNull(setting, GetSicpCommandMessages.SETTING_CAN_NOT_BE_NULL);
      Assert.notNull(setting, GetSicpCommandMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
      int controlId = destination.getControlId();
      int groupId = destination.getGroupId();
      return this.getSicpCommand(controlId, groupId, type, setting, deviceSetting);
   }
}
