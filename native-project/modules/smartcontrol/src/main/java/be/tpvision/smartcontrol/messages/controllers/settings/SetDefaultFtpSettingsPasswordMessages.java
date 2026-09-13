package be.tpvision.smartcontrol.messages.controllers.settings;

import be.tpvision.smartcontrol.messages.Messages;

public class SetDefaultFtpSettingsPasswordMessages {
   public static final String SETTINGS_SERVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Settings service");
   public static final String DEFAULT_FTP_SETTINGS_PASSWORD_VIEW_MODEL = Messages.getViewModelCanNotBeNullMessage("Default FTP settings password");
   public static final String PASSWORD_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Password");
   public static final String SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Settings");
   public static final String DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Default FTP settings");

   private SetDefaultFtpSettingsPasswordMessages() {
   }
}
