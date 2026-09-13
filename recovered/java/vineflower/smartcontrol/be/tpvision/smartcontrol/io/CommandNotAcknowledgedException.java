package be.tpvision.smartcontrol.io;

public class CommandNotAcknowledgedException extends RuntimeException {
   private static final long serialVersionUID = -1276770723650878759L;

   public CommandNotAcknowledgedException(final String message) {
      super(message);
   }
}
