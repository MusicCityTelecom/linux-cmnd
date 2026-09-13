package be.tpvision.smartcontrol.messages.controllers.version;

import be.tpvision.smartcontrol.messages.Messages;

public class GetVersionMessages {
   public static final String THE_CLASS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("The class");
   public static final String THE_PACKAGE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("The package");
   public static final String VERSION_WILL_BE_SHOWN_HERE_WHEN_PACKAGED = "(version will be shown here when packaged)";

   private GetVersionMessages() {
   }
}
