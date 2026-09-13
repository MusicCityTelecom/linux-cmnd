package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SeatDirectionType")
@XmlEnum
public enum SeatDirectionType {
   @XmlEnumValue("Facing")
   FACING("Facing"),
   @XmlEnumValue("Back")
   BACK("Back"),
   @XmlEnumValue("Airline")
   AIRLINE("Airline"),
   @XmlEnumValue("Lateral")
   LATERAL("Lateral"),
   @XmlEnumValue("Unknown")
   UNKNOWN("Unknown");

   private final String value;

   SeatDirectionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static SeatDirectionType fromValue(String v) {
      for (SeatDirectionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
