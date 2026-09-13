package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HTNG_ProfileMessageStatusType")
@XmlEnum
public enum HTNGProfileMessageStatusType {
   @XmlEnumValue("New")
   NEW("New"),
   @XmlEnumValue("Viewed")
   VIEWED("Viewed"),
   @XmlEnumValue("Deleted")
   DELETED("Deleted");

   private final String value;

   HTNGProfileMessageStatusType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static HTNGProfileMessageStatusType fromValue(String v) {
      for (HTNGProfileMessageStatusType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
