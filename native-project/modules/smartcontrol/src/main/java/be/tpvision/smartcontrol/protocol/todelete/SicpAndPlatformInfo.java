package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum SicpAndPlatformInfo implements Convertible {
   SICP_VERSION((byte)0),
   PLATFORM_LABEL((byte)1),
   PLATFORM_VERSION((byte)2);

   private byte data;

   SicpAndPlatformInfo(final byte data) {
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
