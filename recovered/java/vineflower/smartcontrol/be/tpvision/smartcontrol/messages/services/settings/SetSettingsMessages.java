package be.tpvision.smartcontrol.messages.services.settings;

import be.tpvision.smartcontrol.messages.Messages;

public class SetSettingsMessages {
   public static final String SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Settings");
   public static final String DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Default FTP settings");
   public static final String PASSWORD_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Password");
   public static final String ENCODED_PASSWORD_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Encoded password");
   public static final String SETTINGS_SERVICE_CAN_NOT_BE_NULL = Messages.getServiceCanNotBeNullMessage("Settings");

   private SetSettingsMessages() {
   }
}
