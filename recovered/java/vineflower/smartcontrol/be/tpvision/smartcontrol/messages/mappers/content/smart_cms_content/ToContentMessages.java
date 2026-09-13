package be.tpvision.smartcontrol.messages.mappers.content.smart_cms_content;

import be.tpvision.smartcontrol.messages.Messages;

public class ToContentMessages {
   public static final String SMART_CMS_CONTENT_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Smart CMS content");

   private ToContentMessages() {
   }

   public static String getOrientationIsNotSupportedMessage(final int orientation) {
      return String.format("Orientation %d is not supported.", orientation);
   }
}
