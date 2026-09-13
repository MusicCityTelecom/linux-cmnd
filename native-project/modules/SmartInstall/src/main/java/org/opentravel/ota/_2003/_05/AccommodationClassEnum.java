package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AccommodationClassEnum")
@XmlEnum
public enum AccommodationClassEnum {
   @XmlEnumValue("FirstClass")
   FIRST_CLASS("FirstClass"),
   @XmlEnumValue("SecondClass")
   SECOND_CLASS("SecondClass"),
   @XmlEnumValue("Premium")
   PREMIUM("Premium"),
   @XmlEnumValue("Business")
   BUSINESS("Business"),
   @XmlEnumValue("Leisure")
   LEISURE("Leisure"),
   @XmlEnumValue("Coach")
   COACH("Coach"),
   @XmlEnumValue("Deluxe")
   DELUXE("Deluxe"),
   @XmlEnumValue("GranClasse")
   GRAN_CLASSE("GranClasse"),
   @XmlEnumValue("SoftClass")
   SOFT_CLASS("SoftClass"),
   @XmlEnumValue("HardClass")
   HARD_CLASS("HardClass"),
   @XmlEnumValue("SpecialClass")
   SPECIAL_CLASS("SpecialClass"),
   @XmlEnumValue("HighGradeSoftClass")
   HIGH_GRADE_SOFT_CLASS("HighGradeSoftClass"),
   @XmlEnumValue("MixedHardClass")
   MIXED_HARD_CLASS("MixedHardClass"),
   @XmlEnumValue("MixedSoftClass")
   MIXED_SOFT_CLASS("MixedSoftClass"),
   @XmlEnumValue("SoftCompartmentClass")
   SOFT_COMPARTMENT_CLASS("SoftCompartmentClass"),
   @XmlEnumValue("HardCompartmentClass")
   HARD_COMPARTMENT_CLASS("HardCompartmentClass"),
   @XmlEnumValue("Other_")
   OTHER("Other_");

   private final String value;

   AccommodationClassEnum(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static AccommodationClassEnum fromValue(String v) {
      for (AccommodationClassEnum c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
