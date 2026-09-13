package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "LocationDetailRequirementInfoType")
@XmlEnum
public enum LocationDetailRequirementInfoType {
   @XmlEnumValue("OneWayRental")
   ONE_WAY_RENTAL("OneWayRental"),
   @XmlEnumValue("Geographic")
   GEOGRAPHIC("Geographic"),
   @XmlEnumValue("DropOff")
   DROP_OFF("DropOff"),
   @XmlEnumValue("License")
   LICENSE("License"),
   @XmlEnumValue("Insurance")
   INSURANCE("Insurance"),
   @XmlEnumValue("Eligibility")
   ELIGIBILITY("Eligibility"),
   @XmlEnumValue("Miscellaneous")
   MISCELLANEOUS("Miscellaneous");

   private final String value;

   LocationDetailRequirementInfoType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static LocationDetailRequirementInfoType fromValue(String v) {
      for (LocationDetailRequirementInfoType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
