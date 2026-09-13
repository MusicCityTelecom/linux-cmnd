package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TravelPurposeEnum")
@XmlEnum
public enum TravelPurposeEnum {
   @XmlEnumValue("NotSignificant")
   NOT_SIGNIFICANT("NotSignificant"),
   @XmlEnumValue("Business")
   BUSINESS("Business"),
   @XmlEnumValue("Personal")
   PERSONAL("Personal"),
   @XmlEnumValue("Group")
   GROUP("Group"),
   @XmlEnumValue("Conference")
   CONFERENCE("Conference"),
   @XmlEnumValue("Consortiums")
   CONSORTIUMS("Consortiums"),
   @XmlEnumValue("HomeVisiting")
   HOME_VISITING("HomeVisiting"),
   @XmlEnumValue("Other_")
   OTHER("Other_");

   private final String value;

   TravelPurposeEnum(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TravelPurposeEnum fromValue(String v) {
      for (TravelPurposeEnum c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
