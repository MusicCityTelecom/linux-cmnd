package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "BerthPositionType")
@XmlEnum
public enum BerthPositionType {
   @XmlEnumValue("Upper")
   UPPER("Upper"),
   @XmlEnumValue("Middle")
   MIDDLE("Middle"),
   @XmlEnumValue("Lower")
   LOWER("Lower");

   private final String value;

   BerthPositionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static BerthPositionType fromValue(String v) {
      for (BerthPositionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
