package be.tpvision.smartcontrol.messages.controllers.device;

import be.tpvision.smartcontrol.messages.Messages;

public class SetDefaultFtpSettingsMessages {
   public static final String SETTINGS_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Settings");
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Settings");
   public static final String DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Default FTP settings");

   private SetDefaultFtpSettingsMessages() {
   }
}
