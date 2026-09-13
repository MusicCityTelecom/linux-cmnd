package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "VehicleTransmissionType")
@XmlEnum
public enum VehicleTransmissionType {
   @XmlEnumValue("Automatic")
   AUTOMATIC("Automatic"),
   @XmlEnumValue("Manual")
   MANUAL("Manual");

   private final String value;

   VehicleTransmissionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static VehicleTransmissionType fromValue(String v) {
      for (VehicleTransmissionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
