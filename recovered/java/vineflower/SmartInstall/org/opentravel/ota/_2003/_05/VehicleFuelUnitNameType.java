package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "VehicleFuelUnitNameType")
@XmlEnum
public enum VehicleFuelUnitNameType {
   @XmlEnumValue("Gallon")
   GALLON("Gallon"),
   @XmlEnumValue("Liter")
   LITER("Liter");

   private final String value;

   VehicleFuelUnitNameType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static VehicleFuelUnitNameType fromValue(String v) {
      for (VehicleFuelUnitNameType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
