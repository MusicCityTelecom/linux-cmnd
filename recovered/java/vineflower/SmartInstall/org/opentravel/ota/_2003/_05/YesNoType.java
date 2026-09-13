package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "YesNoType")
@XmlEnum
public enum YesNoType {
   @XmlEnumValue("Yes")
   YES("Yes"),
   @XmlEnumValue("No")
   NO("No");

   private final String value;

   YesNoType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static YesNoType fromValue(String v) {
      for (YesNoType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
