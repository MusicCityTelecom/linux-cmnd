package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PreferLevelType")
@XmlEnum
public enum PreferLevelType {
   @XmlEnumValue("Only")
   ONLY("Only"),
   @XmlEnumValue("Unacceptable")
   UNACCEPTABLE("Unacceptable"),
   @XmlEnumValue("Preferred")
   PREFERRED("Preferred"),
   @XmlEnumValue("Required")
   REQUIRED("Required"),
   @XmlEnumValue("NoPreference")
   NO_PREFERENCE("NoPreference");

   private final String value;

   PreferLevelType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static PreferLevelType fromValue(String v) {
      for (PreferLevelType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
