package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TicketType")
@XmlEnum
public enum TicketType {
   @XmlEnumValue("eTicket")
   E_TICKET("eTicket"),
   @XmlEnumValue("Paper")
   PAPER("Paper"),
   MCO("MCO");

   private final String value;

   TicketType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TicketType fromValue(String v) {
      for (TicketType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
