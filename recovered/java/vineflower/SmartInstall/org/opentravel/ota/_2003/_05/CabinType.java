package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CabinType")
@XmlEnum
public enum CabinType {
   @XmlEnumValue("Cockpit")
   COCKPIT("Cockpit"),
   @XmlEnumValue("Suite")
   SUITE("Suite"),
   @XmlEnumValue("First")
   FIRST("First"),
   @XmlEnumValue("PremiumBusiness")
   PREMIUM_BUSINESS("PremiumBusiness"),
   @XmlEnumValue("Business")
   BUSINESS("Business"),
   @XmlEnumValue("PremiumEconomy")
   PREMIUM_ECONOMY("PremiumEconomy"),
   @XmlEnumValue("Economy")
   ECONOMY("Economy");

   private final String value;

   CabinType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static CabinType fromValue(String v) {
      for (CabinType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
