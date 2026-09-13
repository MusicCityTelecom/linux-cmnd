package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AmountDeterminationType")
@XmlEnum
public enum AmountDeterminationType {
   @XmlEnumValue("Inclusive")
   INCLUSIVE("Inclusive"),
   @XmlEnumValue("Exclusive")
   EXCLUSIVE("Exclusive"),
   @XmlEnumValue("Cumulative")
   CUMULATIVE("Cumulative");

   private final String value;

   AmountDeterminationType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static AmountDeterminationType fromValue(String v) {
      for (AmountDeterminationType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
