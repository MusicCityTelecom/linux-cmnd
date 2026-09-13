package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PricingSourceType")
@XmlEnum
public enum PricingSourceType {
   @XmlEnumValue("Published")
   PUBLISHED("Published"),
   @XmlEnumValue("Private")
   PRIVATE("Private"),
   @XmlEnumValue("Both")
   BOTH("Both");

   private final String value;

   PricingSourceType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static PricingSourceType fromValue(String v) {
      for (PricingSourceType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
