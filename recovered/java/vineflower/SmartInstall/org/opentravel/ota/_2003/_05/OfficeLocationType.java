package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "OfficeLocationType")
@XmlEnum
public enum OfficeLocationType {
   @XmlEnumValue("Main")
   MAIN("Main"),
   @XmlEnumValue("Field")
   FIELD("Field"),
   @XmlEnumValue("Division")
   DIVISION("Division"),
   @XmlEnumValue("Regional")
   REGIONAL("Regional"),
   @XmlEnumValue("Remote")
   REMOTE("Remote");

   private final String value;

   OfficeLocationType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static OfficeLocationType fromValue(String v) {
      for (OfficeLocationType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
