package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TimeUnitType")
@XmlEnum
public enum TimeUnitType {
   @XmlEnumValue("Year")
   YEAR("Year"),
   @XmlEnumValue("Month")
   MONTH("Month"),
   @XmlEnumValue("Week")
   WEEK("Week"),
   @XmlEnumValue("Day")
   DAY("Day"),
   @XmlEnumValue("Hour")
   HOUR("Hour"),
   @XmlEnumValue("Second")
   SECOND("Second"),
   @XmlEnumValue("FullDuration")
   FULL_DURATION("FullDuration"),
   @XmlEnumValue("Minute")
   MINUTE("Minute");

   private final String value;

   TimeUnitType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TimeUnitType fromValue(String v) {
      for (TimeUnitType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
