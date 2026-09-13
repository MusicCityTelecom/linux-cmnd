package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "VehiclePeriodUnitNameType")
@XmlEnum
public enum VehiclePeriodUnitNameType {
   @XmlEnumValue("RentalPeriod")
   RENTAL_PERIOD("RentalPeriod"),
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
   @XmlEnumValue("Weekend")
   WEEKEND("Weekend"),
   @XmlEnumValue("ExtraMonth")
   EXTRA_MONTH("ExtraMonth"),
   @XmlEnumValue("Bundle")
   BUNDLE("Bundle"),
   @XmlEnumValue("Package")
   PACKAGE("Package"),
   @XmlEnumValue("ExtraDay")
   EXTRA_DAY("ExtraDay"),
   @XmlEnumValue("ExtraHour")
   EXTRA_HOUR("ExtraHour"),
   @XmlEnumValue("ExtraWeek")
   EXTRA_WEEK("ExtraWeek");

   private final String value;

   VehiclePeriodUnitNameType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static VehiclePeriodUnitNameType fromValue(String v) {
      for (VehiclePeriodUnitNameType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
