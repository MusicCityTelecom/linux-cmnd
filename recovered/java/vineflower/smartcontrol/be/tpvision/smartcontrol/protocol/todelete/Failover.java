package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum Failover implements Convertible {
   HDMI_1((byte)0),
   COMPONENT((byte)1),
   COMPOSITE((byte)2),
   DISPLAY_PORT((byte)3),
   DVI_D((byte)4),
   VGA((byte)5),
   OPS((byte)6),
   USB((byte)7),
   BROWSER((byte)8),
   SMART_CMS((byte)9),
   INTERNAL_STORAGE((byte)10),
   DIGITAL_MEDIA_SERVER((byte)11),
   HDMI_2((byte)12),
   HDMI_3((byte)13);

   private byte data;

   Failover(final byte data) {
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
