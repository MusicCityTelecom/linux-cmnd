package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FlightTypeType")
@XmlEnum
public enum FlightTypeType {
   @XmlEnumValue("Nonstop")
   NONSTOP("Nonstop"),
   @XmlEnumValue("Direct")
   DIRECT("Direct"),
   @XmlEnumValue("Connection")
   CONNECTION("Connection"),
   @XmlEnumValue("SingleConnection")
   SINGLE_CONNECTION("SingleConnection"),
   @XmlEnumValue("DoubleConnection")
   DOUBLE_CONNECTION("DoubleConnection"),
   @XmlEnumValue("OneStopOnly")
   ONE_STOP_ONLY("OneStopOnly");

   private final String value;

   FlightTypeType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static FlightTypeType fromValue(String v) {
      for (FlightTypeType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
