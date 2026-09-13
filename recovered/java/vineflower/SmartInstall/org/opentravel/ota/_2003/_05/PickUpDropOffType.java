package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PickUpDropOffType")
@XmlEnum
public enum PickUpDropOffType {
   @XmlEnumValue("Airport")
   AIRPORT("Airport"),
   @XmlEnumValue("Property")
   PROPERTY("Property"),
   @XmlEnumValue("Resort")
   RESORT("Resort");

   private final String value;

   PickUpDropOffType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static PickUpDropOffType fromValue(String v) {
      for (PickUpDropOffType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
