package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.MixedEnumDeviceSetting;

public enum HdmiOneWire implements MixedEnumDeviceSetting {
   OFF(0, "off"),
   ON_WITH_POWER_OFF_DISABLED(1, "on/on with “HDMI one wire power off” disabled"),
   RESERVED(16, "reserved"),
   ON_WITH_POWER_OFF_ENABLED(17, "on with “HDMI one wire power off” enabled");

   private int byteTag;
   private String info;

   public int getByteTag() {
      return this.byteTag;
   }

   public void setByteTag(int byteTag) {
      this.byteTag = byteTag;
   }

   public String getInfo() {
      return this.info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   HdmiOneWire(int byteTag, String info) {
      this.byteTag = byteTag;
      this.info = info;
   }

   @Override
   public String getOptionText() {
      return this.info;
   }

   @Override
   public String getOptionValue() {
      return this.name();
   }
}
