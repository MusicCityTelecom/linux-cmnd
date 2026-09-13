package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HTNG_EventFilterType")
@XmlEnum
public enum HTNGEventFilterType {
   @XmlEnumValue("Applied")
   APPLIED("Applied"),
   @XmlEnumValue("Available")
   AVAILABLE("Available"),
   @XmlEnumValue("Requested")
   REQUESTED("Requested");

   private final String value;

   HTNGEventFilterType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static HTNGEventFilterType fromValue(String v) {
      for (HTNGEventFilterType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
