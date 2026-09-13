package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum PictureFormat implements Convertible {
   NORMAL((byte)0),
   CUSTOM((byte)1),
   REAL((byte)2),
   FULL((byte)3),
   _21_9((byte)4),
   DYNAMIC((byte)5);

   private byte data;

   PictureFormat(final byte data) {
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
