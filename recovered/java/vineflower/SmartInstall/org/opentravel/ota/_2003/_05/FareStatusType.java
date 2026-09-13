package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FareStatusType")
@XmlEnum
public enum FareStatusType {
   @XmlEnumValue("constructed")
   CONSTRUCTED("constructed"),
   @XmlEnumValue("published")
   PUBLISHED("published"),
   @XmlEnumValue("created")
   CREATED("created"),
   @XmlEnumValue("fareByRule")
   FARE_BY_RULE("fareByRule"),
   @XmlEnumValue("fareByRulePrivate")
   FARE_BY_RULE_PRIVATE("fareByRulePrivate");

   private final String value;

   FareStatusType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static FareStatusType fromValue(String v) {
      for (FareStatusType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
