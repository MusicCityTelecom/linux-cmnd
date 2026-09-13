package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ColorTemperature implements Convertible {
   USER_1((byte)0),
   NATURE((byte)1),
   _11000K((byte)2),
   _10000K((byte)3),
   _9300K((byte)4),
   _7500K((byte)5),
   _6500K((byte)6),
   _5770K((byte)7),
   _5500K((byte)8),
   _5000K((byte)9),
   _4000K((byte)10),
   _3400K((byte)11),
   _3350K((byte)12),
   _3000K((byte)13),
   _2800K((byte)14),
   _2600K((byte)15),
   _1850K((byte)16),
   USER_2((byte)18);

   private byte data;

   ColorTemperature(final byte data) {
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
