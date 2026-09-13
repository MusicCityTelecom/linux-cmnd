package be.tpvision.smartcontrol.service.exceptions;

public class DeviceAlreadyExistsException extends RuntimeException {
   private static final long serialVersionUID = 5456220751530429075L;

   public DeviceAlreadyExistsException() {
      super("Device already exists.");
   }

   public DeviceAlreadyExistsException(final String message) {
      super(message);
   }
}
