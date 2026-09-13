package be.tpvision.smartcontrol.service.exceptions;

public class SerialCodeAlreadyExistsException extends RuntimeException {
   private static final long serialVersionUID = 9002971596936456787L;

   public SerialCodeAlreadyExistsException() {
      super("Serial code already exists.");
   }

   public SerialCodeAlreadyExistsException(final String message) {
      super(message);
   }
}
