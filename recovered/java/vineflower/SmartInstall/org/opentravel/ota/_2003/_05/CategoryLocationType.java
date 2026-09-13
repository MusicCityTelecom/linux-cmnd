package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CategoryLocationType")
@XmlEnum
public enum CategoryLocationType {
   @XmlEnumValue("Inside")
   INSIDE("Inside"),
   @XmlEnumValue("Outside")
   OUTSIDE("Outside"),
   @XmlEnumValue("Both")
   BOTH("Both");

   private final String value;

   CategoryLocationType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static CategoryLocationType fromValue(String v) {
      for (CategoryLocationType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
