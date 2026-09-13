package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CoverageTextType")
@XmlEnum
public enum CoverageTextType {
   @XmlEnumValue("Supplement")
   SUPPLEMENT("Supplement"),
   @XmlEnumValue("Description")
   DESCRIPTION("Description"),
   @XmlEnumValue("Limits")
   LIMITS("Limits");

   private final String value;

   CoverageTextType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static CoverageTextType fromValue(String v) {
      for (CoverageTextType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
