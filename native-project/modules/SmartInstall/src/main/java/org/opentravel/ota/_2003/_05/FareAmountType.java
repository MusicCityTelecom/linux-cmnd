package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FareAmountType")
@XmlEnum
public enum FareAmountType {
   NOADC("NOADC"),
   @XmlEnumValue("Bulk")
   BULK("Bulk"),
   IT("IT");

   private final String value;

   FareAmountType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static FareAmountType fromValue(String v) {
      for (FareAmountType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
