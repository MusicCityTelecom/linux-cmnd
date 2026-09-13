package be.tpvision.smartcontrol.messages.services.miscellaneous;

import be.tpvision.smartcontrol.messages.Messages;

public class SetScanConversionMessages {
   public static final String DEVICE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Device");
   public static final String SCAN_CONVERSION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Scan conversion");

   private SetScanConversionMessages() {
   }
}
