package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ActionCodeType")
@XmlEnum
public enum ActionCodeType {
   OK("OK"),
   @XmlEnumValue("Waitlist")
   WAITLIST("Waitlist"),
   @XmlEnumValue("Other")
   OTHER("Other"),
   @XmlEnumValue("Cancel")
   CANCEL("Cancel"),
   @XmlEnumValue("Need")
   NEED("Need");

   private final String value;

   ActionCodeType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static ActionCodeType fromValue(String v) {
      for (ActionCodeType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
