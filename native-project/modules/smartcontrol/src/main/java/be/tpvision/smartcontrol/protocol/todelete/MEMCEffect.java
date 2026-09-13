package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum MEMCEffect implements Convertible {
   OFF((byte)0),
   LOW((byte)1),
   MEDIUM((byte)2),
   HIGH((byte)3);

   private byte data;

   MEMCEffect(final byte data) {
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
