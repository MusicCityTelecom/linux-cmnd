package be.tpvision.smartcontrol.io;

public class FetchInfoDataTaskAlreadyRunningException extends TaskAlreadyRunningException {
   private static final long serialVersionUID = 1303756259250257125L;

   public FetchInfoDataTaskAlreadyRunningException() {
      super("Fetch info data");
   }
}
