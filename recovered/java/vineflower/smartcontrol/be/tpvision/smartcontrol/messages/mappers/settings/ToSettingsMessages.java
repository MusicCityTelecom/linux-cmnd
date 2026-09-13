package be.tpvision.smartcontrol.messages.mappers.settings;

import be.tpvision.smartcontrol.messages.Messages;

public class ToSettingsMessages {
   public static final String SETTINGS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Settings");
   public static final String SERVER_IP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Server ip");
   public static final String SERVER_IP_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Server ip");
   public static final String DEFAULT_FTP_SETTINGS_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Default FTP settings");
   public static final String DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Default FTP settings");

   private ToSettingsMessages() {
   }
}
