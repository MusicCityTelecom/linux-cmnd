package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "LocationDetailShuttleInfoType")
@XmlEnum
public enum LocationDetailShuttleInfoType {
   @XmlEnumValue("Transportation")
   TRANSPORTATION("Transportation"),
   @XmlEnumValue("Frequency")
   FREQUENCY("Frequency"),
   @XmlEnumValue("PickupInfo")
   PICKUP_INFO("PickupInfo"),
   @XmlEnumValue("Distance")
   DISTANCE("Distance"),
   @XmlEnumValue("ElapsedTime")
   ELAPSED_TIME("ElapsedTime"),
   @XmlEnumValue("Fee")
   FEE("Fee"),
   @XmlEnumValue("Miscellaneous")
   MISCELLANEOUS("Miscellaneous"),
   @XmlEnumValue("Hours")
   HOURS("Hours");

   private final String value;

   LocationDetailShuttleInfoType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static LocationDetailShuttleInfoType fromValue(String v) {
      for (LocationDetailShuttleInfoType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
