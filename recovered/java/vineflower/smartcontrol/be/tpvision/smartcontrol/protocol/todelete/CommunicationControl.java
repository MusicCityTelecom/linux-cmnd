package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum CommunicationControl implements Convertible {
   ACKNOWLEDGED((byte)6),
   NOT_ACKNOWLEDGED((byte)21),
   NOT_AVAILABLE((byte)24);

   private byte data;

   CommunicationControl(final byte data) {
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
