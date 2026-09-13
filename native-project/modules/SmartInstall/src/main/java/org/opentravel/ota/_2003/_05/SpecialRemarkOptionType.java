package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SpecialRemarkOptionType")
@XmlEnum
public enum SpecialRemarkOptionType {
   @XmlEnumValue("Itinerary")
   ITINERARY("Itinerary"),
   @XmlEnumValue("Invoice")
   INVOICE("Invoice"),
   @XmlEnumValue("Endorsement")
   ENDORSEMENT("Endorsement"),
   @XmlEnumValue("Save")
   SAVE("Save"),
   @XmlEnumValue("Confidential")
   CONFIDENTIAL("Confidential"),
   @XmlEnumValue("Free")
   FREE("Free"),
   GRMS("GRMS"),
   @XmlEnumValue("Split")
   SPLIT("Split");

   private final String value;

   SpecialRemarkOptionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static SpecialRemarkOptionType fromValue(String v) {
      for (SpecialRemarkOptionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
