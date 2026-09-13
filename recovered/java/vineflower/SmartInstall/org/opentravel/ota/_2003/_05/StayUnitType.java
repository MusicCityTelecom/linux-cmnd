package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "StayUnitType")
@XmlEnum
public enum StayUnitType {
   @XmlEnumValue("Minutes")
   MINUTES("Minutes"),
   @XmlEnumValue("Hours")
   HOURS("Hours"),
   @XmlEnumValue("Days")
   DAYS("Days"),
   @XmlEnumValue("Months")
   MONTHS("Months"),
   MON("MON"),
   TUES("TUES"),
   WED("WED"),
   THU("THU"),
   FRI("FRI"),
   SAT("SAT"),
   SUN("SUN");

   private final String value;

   StayUnitType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static StayUnitType fromValue(String v) {
      for (StayUnitType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
