package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "LocationDetailRequirementAgeInfoType")
@XmlEnum
public enum LocationDetailRequirementAgeInfoType {
   @XmlEnumValue("MinimumAge")
   MINIMUM_AGE("MinimumAge"),
   @XmlEnumValue("MinimumAgeExceptions")
   MINIMUM_AGE_EXCEPTIONS("MinimumAgeExceptions"),
   @XmlEnumValue("Miscellaneous")
   MISCELLANEOUS("Miscellaneous");

   private final String value;

   LocationDetailRequirementAgeInfoType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static LocationDetailRequirementAgeInfoType fromValue(String v) {
      for (LocationDetailRequirementAgeInfoType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
