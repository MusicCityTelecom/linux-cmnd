package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum PowerSavingMode implements Convertible {
   RGB_OFF_VIDEO_OFF((byte)0),
   RGB_OFF_VIDEO_ON((byte)1),
   RGB_ON_VIDEO_OFF((byte)2),
   RGB_ON_VIDEO_ON((byte)3);

   private byte data;

   PowerSavingMode(final byte data) {
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
