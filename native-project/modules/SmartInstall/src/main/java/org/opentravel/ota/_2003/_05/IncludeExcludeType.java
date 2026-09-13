package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "IncludeExcludeType")
@XmlEnum
public enum IncludeExcludeType {
   @XmlEnumValue("Include")
   INCLUDE("Include"),
   @XmlEnumValue("Exclude")
   EXCLUDE("Exclude"),
   @XmlEnumValue("Required")
   REQUIRED("Required"),
   @XmlEnumValue("Allowed")
   ALLOWED("Allowed");

   private final String value;

   IncludeExcludeType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static IncludeExcludeType fromValue(String v) {
      for (IncludeExcludeType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
