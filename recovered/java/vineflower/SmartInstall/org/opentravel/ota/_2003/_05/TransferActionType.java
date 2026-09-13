package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TransferActionType")
@XmlEnum
public enum TransferActionType {
   @XmlEnumValue("Automatic")
   AUTOMATIC("Automatic"),
   @XmlEnumValue("Mandatory")
   MANDATORY("Mandatory"),
   @XmlEnumValue("Selectable")
   SELECTABLE("Selectable");

   private final String value;

   TransferActionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TransferActionType fromValue(String v) {
      for (TransferActionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
