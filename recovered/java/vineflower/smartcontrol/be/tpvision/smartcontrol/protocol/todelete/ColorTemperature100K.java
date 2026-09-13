package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum ColorTemperature100K implements Convertible {
   _2000K((byte)20),
   _2100K((byte)21),
   _2200K((byte)22),
   _2300K((byte)23),
   _2400K((byte)24),
   _2500K((byte)25),
   _2600K((byte)26),
   _2700K((byte)27),
   _2800K((byte)28),
   _2900K((byte)29),
   _3000K((byte)30),
   _3100K((byte)31),
   _3200K((byte)32),
   _3300K((byte)33),
   _3400K((byte)34),
   _3500K((byte)35),
   _3600K((byte)36),
   _3700K((byte)37),
   _3800K((byte)38),
   _3900K((byte)39),
   _4000K((byte)40),
   _4100K((byte)41),
   _4200K((byte)42),
   _4300K((byte)43),
   _4400K((byte)44),
   _4500K((byte)45),
   _4600K((byte)46),
   _4700K((byte)47),
   _4800K((byte)48),
   _4900K((byte)49),
   _5000K((byte)50),
   _5100K((byte)51),
   _5200K((byte)52),
   _5300K((byte)53),
   _5400K((byte)54),
   _5500K((byte)55),
   _5600K((byte)56),
   _5700K((byte)57),
   _5800K((byte)58),
   _5900K((byte)59),
   _6000K((byte)60),
   _6100K((byte)61),
   _6200K((byte)62),
   _6300K((byte)63),
   _6400K((byte)64),
   _6500K((byte)65),
   _6600K((byte)66),
   _6700K((byte)67),
   _6800K((byte)68),
   _6900K((byte)69),
   _7000K((byte)70),
   _7100K((byte)71),
   _7200K((byte)72),
   _7300K((byte)73),
   _7400K((byte)74),
   _7500K((byte)75),
   _7600K((byte)76),
   _7700K((byte)77),
   _7800K((byte)78),
   _7900K((byte)79),
   _8000K((byte)80),
   _8100K((byte)81),
   _8200K((byte)82),
   _8300K((byte)83),
   _8400K((byte)84),
   _8500K((byte)85),
   _8600K((byte)86),
   _8700K((byte)87),
   _8800K((byte)88),
   _8900K((byte)89),
   _9000K((byte)90),
   _9100K((byte)91),
   _9200K((byte)92),
   _9300K((byte)93),
   _9400K((byte)94),
   _9500K((byte)95),
   _9600K((byte)96),
   _9700K((byte)97),
   _9800K((byte)98),
   _9900K((byte)99),
   _10000K((byte)100);

   private byte data;

   ColorTemperature100K(final byte data) {
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
