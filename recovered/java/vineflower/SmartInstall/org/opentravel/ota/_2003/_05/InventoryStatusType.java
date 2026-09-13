package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "InventoryStatusType")
@XmlEnum
public enum InventoryStatusType {
   @XmlEnumValue("Available")
   AVAILABLE("Available"),
   @XmlEnumValue("Unavailable")
   UNAVAILABLE("Unavailable"),
   @XmlEnumValue("OnRequest")
   ON_REQUEST("OnRequest"),
   @XmlEnumValue("Confirmed")
   CONFIRMED("Confirmed"),
   @XmlEnumValue("All")
   ALL("All"),
   @XmlEnumValue("Waitlist")
   WAITLIST("Waitlist"),
   @XmlEnumValue("SupplierBooked")
   SUPPLIER_BOOKED("SupplierBooked");

   private final String value;

   InventoryStatusType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static InventoryStatusType fromValue(String v) {
      for (InventoryStatusType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
