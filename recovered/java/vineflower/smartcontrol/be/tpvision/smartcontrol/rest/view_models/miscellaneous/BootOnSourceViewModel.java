package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

public class BootOnSourceViewModel {
   private String videoSourceType;
   private String tag;

   protected BootOnSourceViewModel() {
   }

   public BootOnSourceViewModel(final String videoSourceType, final String tag) {
      this.videoSourceType = videoSourceType;
      this.tag = tag;
   }

   public String getVideoSourceType() {
      return this.videoSourceType;
   }

   public void setVideoSourceType(String videoSourceType) {
      this.videoSourceType = videoSourceType;
   }

   public String getTag() {
      return this.tag;
   }

   public void setTag(String tag) {
      this.tag = tag;
   }
}
