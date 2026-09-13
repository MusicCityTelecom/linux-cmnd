package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DayOfWeekType")
@XmlEnum
public enum DayOfWeekType {
   @XmlEnumValue("Mon")
   MON("Mon"),
   @XmlEnumValue("Tue")
   TUE("Tue"),
   @XmlEnumValue("Wed")
   WED("Wed"),
   @XmlEnumValue("Thu")
   THU("Thu"),
   @XmlEnumValue("Fri")
   FRI("Fri"),
   @XmlEnumValue("Sat")
   SAT("Sat"),
   @XmlEnumValue("Sun")
   SUN("Sun");

   private final String value;

   DayOfWeekType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static DayOfWeekType fromValue(String v) {
      for (DayOfWeekType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
