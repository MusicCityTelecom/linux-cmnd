package be.tpvision.smartcontrol.messages.repositories.jdbc.content;

import be.tpvision.smartcontrol.messages.Messages;

public class GetContentArgumentsMessages {
   public static final String LOCAL_DATE_TIME_CONVERTER_CAN_NOT_BE_NULL = Messages.getConverterCanNotBeNullMessage("Local date time");
   public static final String ORIENTATION_CONVERTER_CAN_NOT_BE_NULL = Messages.getConverterCanNotBeNullMessage("Orientation");
   public static final String CONTENT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content");

   private GetContentArgumentsMessages() {
   }
}
