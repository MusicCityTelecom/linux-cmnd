package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "OffLocationServiceID_Type")
@XmlEnum
public enum OffLocationServiceIDType {
   @XmlEnumValue("CustPickUp")
   CUST_PICK_UP("CustPickUp"),
   @XmlEnumValue("VehDelivery")
   VEH_DELIVERY("VehDelivery"),
   @XmlEnumValue("CustDropOff")
   CUST_DROP_OFF("CustDropOff"),
   @XmlEnumValue("VehCollection")
   VEH_COLLECTION("VehCollection"),
   @XmlEnumValue("Exchange")
   EXCHANGE("Exchange"),
   @XmlEnumValue("RepairLocation")
   REPAIR_LOCATION("RepairLocation");

   private final String value;

   OffLocationServiceIDType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static OffLocationServiceIDType fromValue(String v) {
      for (OffLocationServiceIDType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
