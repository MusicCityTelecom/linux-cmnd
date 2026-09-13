package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ActionType")
@XmlEnum
public enum ActionType {
   @XmlEnumValue("Add-Update")
   ADD_UPDATE("Add-Update"),
   @XmlEnumValue("Cancel")
   CANCEL("Cancel"),
   @XmlEnumValue("Delete")
   DELETE("Delete"),
   @XmlEnumValue("Add")
   ADD("Add"),
   @XmlEnumValue("Replace")
   REPLACE("Replace");

   private final String value;

   ActionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static ActionType fromValue(String v) {
      for (ActionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
