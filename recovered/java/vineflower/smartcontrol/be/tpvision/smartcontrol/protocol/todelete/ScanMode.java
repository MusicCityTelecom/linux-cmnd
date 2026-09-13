package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ScanMode implements Convertible {
   OVERSCAN((byte)0),
   UNDERSCAN((byte)1);

   private byte data;

   ScanMode(final byte data) {
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
