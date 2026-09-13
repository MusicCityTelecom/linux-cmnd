package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "EquipmentRestrictionType")
@XmlEnum
public enum EquipmentRestrictionType {
   @XmlEnumValue("OneWayOnly")
   ONE_WAY_ONLY("OneWayOnly"),
   @XmlEnumValue("RoundTripOnly")
   ROUND_TRIP_ONLY("RoundTripOnly"),
   @XmlEnumValue("AnyReservation")
   ANY_RESERVATION("AnyReservation");

   private final String value;

   EquipmentRestrictionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static EquipmentRestrictionType fromValue(String v) {
      for (EquipmentRestrictionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
