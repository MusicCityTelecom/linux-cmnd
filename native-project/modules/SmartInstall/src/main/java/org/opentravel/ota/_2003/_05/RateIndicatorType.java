package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RateIndicatorType")
@XmlEnum
public enum RateIndicatorType {
   @XmlEnumValue("ChangeDuringStay")
   CHANGE_DURING_STAY("ChangeDuringStay"),
   @XmlEnumValue("MultipleNights")
   MULTIPLE_NIGHTS("MultipleNights"),
   @XmlEnumValue("Exclusive")
   EXCLUSIVE("Exclusive"),
   @XmlEnumValue("OnRequest")
   ON_REQUEST("OnRequest"),
   @XmlEnumValue("LimitedAvailability")
   LIMITED_AVAILABILITY("LimitedAvailability"),
   @XmlEnumValue("AvailableForSale")
   AVAILABLE_FOR_SALE("AvailableForSale"),
   @XmlEnumValue("ClosedOut")
   CLOSED_OUT("ClosedOut"),
   @XmlEnumValue("OtherAvailable")
   OTHER_AVAILABLE("OtherAvailable"),
   @XmlEnumValue("UnableToProcess")
   UNABLE_TO_PROCESS("UnableToProcess"),
   @XmlEnumValue("NoAvailability")
   NO_AVAILABILITY("NoAvailability"),
   @XmlEnumValue("RoomTypeClosed")
   ROOM_TYPE_CLOSED("RoomTypeClosed"),
   @XmlEnumValue("RatePlanClosed")
   RATE_PLAN_CLOSED("RatePlanClosed"),
   @XmlEnumValue("LOS_Restricted")
   LOS_RESTRICTED("LOS_Restricted"),
   @XmlEnumValue("Restricted")
   RESTRICTED("Restricted"),
   @XmlEnumValue("DoesNotExist")
   DOES_NOT_EXIST("DoesNotExist");

   private final String value;

   RateIndicatorType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static RateIndicatorType fromValue(String v) {
      for (RateIndicatorType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
