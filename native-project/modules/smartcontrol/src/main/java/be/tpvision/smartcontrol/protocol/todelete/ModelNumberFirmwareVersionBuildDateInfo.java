package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ModelNumberFirmwareVersionBuildDateInfo implements Convertible {
   MODEL_NUMBER((byte)0),
   FIRMWARE_VERSION((byte)1),
   BUILD_DATE((byte)2);

   private byte data;

   ModelNumberFirmwareVersionBuildDateInfo(final byte data) {
      this.data = data;
   }

   public byte getData() {
      return this.data;
   }

   @Override
   public byte convert() {
      return this.data;
   }
}
