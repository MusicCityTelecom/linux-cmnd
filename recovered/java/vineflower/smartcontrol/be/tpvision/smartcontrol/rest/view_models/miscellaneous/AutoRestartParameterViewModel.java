package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

public class AutoRestartParameterViewModel {
   private String status;
   private String restartTime;

   protected AutoRestartParameterViewModel() {
   }

   public AutoRestartParameterViewModel(String status, String restartTime) {
      this.status = status;
      this.restartTime = restartTime;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getRestartTime() {
      return this.restartTime;
   }

   public void setRestartTime(String restartTime) {
      this.restartTime = restartTime;
   }
}
