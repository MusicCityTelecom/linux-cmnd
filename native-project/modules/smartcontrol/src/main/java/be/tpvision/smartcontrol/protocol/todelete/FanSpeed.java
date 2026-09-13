package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum FanSpeed implements Convertible {
   OFF((byte)0),
   AUTO((byte)1),
   LOW((byte)2),
   MIDDLE((byte)3),
   HIGH((byte)4);

   private byte data;

   FanSpeed(final byte data) {
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
