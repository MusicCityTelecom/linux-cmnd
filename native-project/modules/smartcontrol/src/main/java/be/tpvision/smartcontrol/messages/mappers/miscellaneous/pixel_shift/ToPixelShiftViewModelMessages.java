package be.tpvision.smartcontrol.messages.mappers.miscellaneous.pixel_shift;

import be.tpvision.smartcontrol.messages.Messages;

public class ToPixelShiftViewModelMessages {
   public static final String PIXEL_SHIFT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Pixel Shift");
   public static final String DOMAIN_STATE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("domain State");

   private ToPixelShiftViewModelMessages() {
   }
}
