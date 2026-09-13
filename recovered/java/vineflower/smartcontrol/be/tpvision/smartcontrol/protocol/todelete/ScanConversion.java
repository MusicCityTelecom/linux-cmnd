package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ScanConversion implements Convertible {
   PROGRESSIVE((byte)0),
   INTERLACE((byte)1);

   private byte data;

   ScanConversion(final byte data) {
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
