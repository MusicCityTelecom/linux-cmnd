package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AirTripDirectionType")
@XmlEnum
public enum AirTripDirectionType {
   @XmlEnumValue("Outbound")
   OUTBOUND("Outbound"),
   @XmlEnumValue("Return")
   RETURN("Return"),
   @XmlEnumValue("All")
   ALL("All");

   private final String value;

   AirTripDirectionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static AirTripDirectionType fromValue(String v) {
      for (AirTripDirectionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
