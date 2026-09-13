package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "BerthAccommodationType")
@XmlEnum
public enum BerthAccommodationType {
   @XmlEnumValue("NotSignificant")
   NOT_SIGNIFICANT("NotSignificant"),
   @XmlEnumValue("Berth")
   BERTH("Berth"),
   @XmlEnumValue("Couchette")
   COUCHETTE("Couchette"),
   @XmlEnumValue("Sleeper")
   SLEEPER("Sleeper");

   private final String value;

   BerthAccommodationType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static BerthAccommodationType fromValue(String v) {
      for (BerthAccommodationType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
