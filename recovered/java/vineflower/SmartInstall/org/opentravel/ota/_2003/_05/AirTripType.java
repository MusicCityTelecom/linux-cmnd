package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AirTripType")
@XmlEnum
public enum AirTripType {
   @XmlEnumValue("OneWay")
   ONE_WAY("OneWay"),
   @XmlEnumValue("OneWayOnly")
   ONE_WAY_ONLY("OneWayOnly"),
   @XmlEnumValue("Return")
   RETURN("Return"),
   @XmlEnumValue("Circle")
   CIRCLE("Circle"),
   @XmlEnumValue("OpenJaw")
   OPEN_JAW("OpenJaw"),
   @XmlEnumValue("Other")
   OTHER("Other"),
   @XmlEnumValue("Outbound")
   OUTBOUND("Outbound"),
   @XmlEnumValue("OutboundSeasonRoundtrip")
   OUTBOUND_SEASON_ROUNDTRIP("OutboundSeasonRoundtrip"),
   @XmlEnumValue("Non-directional")
   NON_DIRECTIONAL("Non-directional"),
   @XmlEnumValue("Inbound")
   INBOUND("Inbound"),
   @XmlEnumValue("Roundtrip")
   ROUNDTRIP("Roundtrip");

   private final String value;

   AirTripType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static AirTripType fromValue(String v) {
      for (AirTripType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
