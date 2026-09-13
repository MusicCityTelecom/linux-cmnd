package be.tpvision.smartcontrol.messages.mappers.device.device;

import be.tpvision.smartcontrol.messages.Messages;

public class ToDeviceMessages {
   public static final String DETECT_DEVICES_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Detect devices");
   public static final String ADD_OR_EDIT_DEVICE_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Add or edit device");
   public static final String GROUP_DEVICE_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Group device");
   public static final String IMPORT_EXPORT_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Import / export");

   private ToDeviceMessages() {
   }
}
