package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FareApplicationType")
@XmlEnum
public enum FareApplicationType {
   @XmlEnumValue("OneWay")
   ONE_WAY("OneWay"),
   @XmlEnumValue("Return")
   RETURN("Return"),
   @XmlEnumValue("HalfReturn")
   HALF_RETURN("HalfReturn"),
   @XmlEnumValue("Roundtrip")
   ROUNDTRIP("Roundtrip"),
   @XmlEnumValue("OneWayOnly")
   ONE_WAY_ONLY("OneWayOnly");

   private final String value;

   FareApplicationType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static FareApplicationType fromValue(String v) {
      for (FareApplicationType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
