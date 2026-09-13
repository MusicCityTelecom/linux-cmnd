package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public class OtaUpdateGet implements DeviceSetting {
   private OtaImageType imageType;
   private OtaUpdateGet.UpdateStatus status;

   public OtaUpdateGet(OtaImageType imageType, OtaUpdateGet.UpdateStatus status) {
      this.imageType = imageType;
      this.status = status;
   }

   public OtaImageType getImageType() {
      return this.imageType;
   }

   public void setImageType(OtaImageType imageType) {
      this.imageType = imageType;
   }

   public OtaUpdateGet.UpdateStatus getStatus() {
      return this.status;
   }

   public void setStatus(OtaUpdateGet.UpdateStatus status) {
      this.status = status;
   }

   public enum UpdateStatus {
      CANT_UPDATE("Can’t update"),
      ABLE_TO_UPDATE("Able to update");

      private String description;

      UpdateStatus(String description) {
         this.description = description;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String description) {
         this.description = description;
      }
   }
}
