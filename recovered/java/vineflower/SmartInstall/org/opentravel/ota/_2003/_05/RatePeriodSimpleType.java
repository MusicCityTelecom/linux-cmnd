package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RatePeriodSimpleType")
@XmlEnum
public enum RatePeriodSimpleType {
   @XmlEnumValue("Hourly")
   HOURLY("Hourly"),
   @XmlEnumValue("Daily")
   DAILY("Daily"),
   @XmlEnumValue("Weekly")
   WEEKLY("Weekly"),
   @XmlEnumValue("Monthly")
   MONTHLY("Monthly"),
   @XmlEnumValue("WeekendDay")
   WEEKEND_DAY("WeekendDay"),
   @XmlEnumValue("Other")
   OTHER("Other"),
   @XmlEnumValue("Package")
   PACKAGE("Package"),
   @XmlEnumValue("Bundle")
   BUNDLE("Bundle"),
   @XmlEnumValue("Total")
   TOTAL("Total");

   private final String value;

   RatePeriodSimpleType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static RatePeriodSimpleType fromValue(String v) {
      for (RatePeriodSimpleType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
