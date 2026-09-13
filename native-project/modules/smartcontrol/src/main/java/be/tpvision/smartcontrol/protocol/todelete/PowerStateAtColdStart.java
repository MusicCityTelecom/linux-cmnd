package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum PowerStateAtColdStart implements Convertible {
   OFF((byte)0),
   ON((byte)1),
   LAST_STATUS((byte)2);

   private byte data;

   PowerStateAtColdStart(final byte data) {
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
