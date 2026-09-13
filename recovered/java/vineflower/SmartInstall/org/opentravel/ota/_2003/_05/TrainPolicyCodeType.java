package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TrainPolicyCodeType")
@XmlEnum
public enum TrainPolicyCodeType {
   @XmlEnumValue("Minimum")
   MINIMUM("Minimum"),
   @XmlEnumValue("Maximum")
   MAXIMUM("Maximum");

   private final String value;

   TrainPolicyCodeType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TrainPolicyCodeType fromValue(String v) {
      for (TrainPolicyCodeType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
