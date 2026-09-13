package be.tpvision.smartcontrol.messages.repositories.jdbc.content;

import be.tpvision.smartcontrol.messages.Messages;

public class DeleteContentMessages {
   public static final String CONTENT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content");
   public static final String ID_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Id");
   public static final String ID_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Id");

   private DeleteContentMessages() {
   }
}
