package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public class OtaUpdateSet implements DeviceSetting {
   private OtaImageType imageType;
   private OtaUpdateSet.Status status;

   public OtaUpdateSet(OtaImageType imageType, OtaUpdateSet.Status status) {
      this.imageType = imageType;
      this.status = status;
   }

   public OtaImageType getImageType() {
      return this.imageType;
   }

   public void setImageType(OtaImageType imageType) {
      this.imageType = imageType;
   }

   public OtaUpdateSet.Status getStatus() {
      return this.status;
   }

   public void setStatus(OtaUpdateSet.Status status) {
      this.status = status;
   }

   public enum Status {
      ON(1),
      OFF(2);

      private int index;

      Status(int index) {
         this.index = index;
      }

      public int getIndex() {
         return this.index;
      }

      public void setIndex(int index) {
         this.index = index;
      }
   }
}
