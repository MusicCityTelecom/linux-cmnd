package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public enum OtaImageType implements DeviceSetting {
   SCALER_FIRMWARE(1, "Scaler Firmware"),
   HDMI_SWITCH_FIRMWARE(2, "HDMI Switch Firmware"),
   LAN_FIRMWARE(3, "LAN Firmware"),
   ANDROID_IMAGE(4, "Android image");

   private int index;
   private String description;

   OtaImageType(int index, String description) {
      this.index = index;
      this.description = description;
   }

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public static OtaImageType ofIndex(int index) {
      for (OtaImageType type : values()) {
         if (type.getIndex() == index) {
            return type;
         }
      }

      return null;
   }
}
