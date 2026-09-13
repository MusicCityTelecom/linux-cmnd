package be.tpvision.smartcontrol.service.exceptions;

public class FailedToAddGroupException extends RuntimeException {
   private static final long serialVersionUID = 6355830657316698924L;
   private AddGroupResult addGroupResult;

   public FailedToAddGroupException() {
      super("Failed to add group.");
   }

   public FailedToAddGroupException(final String message) {
      super(message);
   }

   public FailedToAddGroupException(final AddGroupResult addGroupResult) {
      this();
      this.addGroupResult = addGroupResult;
   }

   public AddGroupResult getAddGroupResult() {
      return this.addGroupResult;
   }
}
