package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "LocationDetailVehicleInfoType")
@XmlEnum
public enum LocationDetailVehicleInfoType {
   @XmlEnumValue("GeneralInformation")
   GENERAL_INFORMATION("GeneralInformation"),
   @XmlEnumValue("Disclaimer")
   DISCLAIMER("Disclaimer"),
   @XmlEnumValue("AdvancedBooking")
   ADVANCED_BOOKING("AdvancedBooking"),
   @XmlEnumValue("NonSmokingVehicles")
   NON_SMOKING_VEHICLES("NonSmokingVehicles"),
   @XmlEnumValue("SpecialityVehicles")
   SPECIALITY_VEHICLES("SpecialityVehicles");

   private final String value;

   LocationDetailVehicleInfoType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static LocationDetailVehicleInfoType fromValue(String v) {
      for (LocationDetailVehicleInfoType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
