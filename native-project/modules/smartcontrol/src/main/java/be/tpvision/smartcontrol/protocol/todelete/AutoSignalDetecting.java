package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum AutoSignalDetecting implements Convertible {
   OFF((byte)0),
   ALL((byte)1),
   RESERVED((byte)2),
   PC_ONLY((byte)3),
   VIDEO_ONLY((byte)4),
   FAILOVER((byte)5);

   private byte data;

   AutoSignalDetecting(final byte data) {
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
