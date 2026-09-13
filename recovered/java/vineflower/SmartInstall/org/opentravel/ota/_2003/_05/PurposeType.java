package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PurposeType")
@XmlEnum
public enum PurposeType {
   @XmlEnumValue("Sell")
   SELL("Sell"),
   @XmlEnumValue("Net")
   NET("Net"),
   @XmlEnumValue("Base")
   BASE("Base"),
   @XmlEnumValue("Refund")
   REFUND("Refund"),
   @XmlEnumValue("Additional")
   ADDITIONAL("Additional");

   private final String value;

   PurposeType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static PurposeType fromValue(String v) {
      for (PurposeType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
