package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;

public enum OpsSdmSettings implements DeviceSetting {
   ALWAYS_OFF("always off"),
   ALWAYS_ON("always on"),
   AUTO("Auto");

   private String description;

   OpsSdmSettings(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }
}
