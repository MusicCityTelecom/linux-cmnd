package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PricingType")
@XmlEnum
public enum PricingType {
   @XmlEnumValue("Per stay")
   PER_STAY("Per stay"),
   @XmlEnumValue("Per person")
   PER_PERSON("Per person"),
   @XmlEnumValue("Per night")
   PER_NIGHT("Per night"),
   @XmlEnumValue("Per person per night")
   PER_PERSON_PER_NIGHT("Per person per night"),
   @XmlEnumValue("Per use")
   PER_USE("Per use");

   private final String value;

   PricingType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static PricingType fromValue(String v) {
      for (PricingType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
