package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DistanceUnitNameType")
@XmlEnum
public enum DistanceUnitNameType {
   @XmlEnumValue("Mile")
   MILE("Mile"),
   @XmlEnumValue("Km")
   KM("Km"),
   @XmlEnumValue("Block")
   BLOCK("Block");

   private final String value;

   DistanceUnitNameType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static DistanceUnitNameType fromValue(String v) {
      for (DistanceUnitNameType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
