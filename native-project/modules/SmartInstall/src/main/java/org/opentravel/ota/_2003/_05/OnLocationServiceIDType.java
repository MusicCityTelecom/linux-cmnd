package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "OnLocationServiceID_Type")
@XmlEnum
public enum OnLocationServiceIDType {
   @XmlEnumValue("ComputerDrivingDirections")
   COMPUTER_DRIVING_DIRECTIONS("ComputerDrivingDirections"),
   @XmlEnumValue("ExpressReturnService")
   EXPRESS_RETURN_SERVICE("ExpressReturnService"),
   @XmlEnumValue("SpecialNeeds")
   SPECIAL_NEEDS("SpecialNeeds"),
   @XmlEnumValue("FrequentRenter")
   FREQUENT_RENTER("FrequentRenter"),
   @XmlEnumValue("Miscellaneous")
   MISCELLANEOUS("Miscellaneous");

   private final String value;

   OnLocationServiceIDType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static OnLocationServiceIDType fromValue(String v) {
      for (OnLocationServiceIDType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
