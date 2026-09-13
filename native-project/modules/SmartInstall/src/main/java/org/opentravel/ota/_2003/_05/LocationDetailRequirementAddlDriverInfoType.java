package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "LocationDetailRequirementAddlDriverInfoType")
@XmlEnum
public enum LocationDetailRequirementAddlDriverInfoType {
   @XmlEnumValue("IncludedAuthorized")
   INCLUDED_AUTHORIZED("IncludedAuthorized"),
   @XmlEnumValue("AdditionalAuthorized")
   ADDITIONAL_AUTHORIZED("AdditionalAuthorized"),
   @XmlEnumValue("Fees")
   FEES("Fees"),
   @XmlEnumValue("Miscellaneous")
   MISCELLANEOUS("Miscellaneous");

   private final String value;

   LocationDetailRequirementAddlDriverInfoType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static LocationDetailRequirementAddlDriverInfoType fromValue(String v) {
      for (LocationDetailRequirementAddlDriverInfoType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
