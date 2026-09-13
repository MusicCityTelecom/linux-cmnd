package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum SwitchOnDelay implements Convertible {
   OFF((byte)0),
   _2_SECONDS((byte)2),
   _4_SECONDS((byte)4),
   _6_SECONDS((byte)6),
   _8_SECONDS((byte)8),
   _10_SECONDS((byte)10),
   _20_SECONDS((byte)20),
   _30_SECONDS((byte)30),
   _40_SECONDS((byte)40),
   _50_SECONDS((byte)50),
   AUTO((byte)60);

   private byte data;

   SwitchOnDelay(final byte data) {
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
