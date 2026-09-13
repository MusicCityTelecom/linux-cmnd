package be.tpvision.smartcontrol.io;

public class FetchOverviewDataTaskAlreadyRunningException extends TaskAlreadyRunningException {
   private static final long serialVersionUID = 312746151619184781L;

   public FetchOverviewDataTaskAlreadyRunningException() {
      super("Fetch overview data");
   }
}
