package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TransactionStatusType")
@XmlEnum
public enum TransactionStatusType {
   @XmlEnumValue("Pending")
   PENDING("Pending"),
   @XmlEnumValue("Cancelled")
   CANCELLED("Cancelled"),
   @XmlEnumValue("Modified")
   MODIFIED("Modified"),
   @XmlEnumValue("Committed")
   COMMITTED("Committed"),
   @XmlEnumValue("Ignored")
   IGNORED("Ignored"),
   @XmlEnumValue("On Hold")
   ON_HOLD("On Hold"),
   @XmlEnumValue("Unsuccessful")
   UNSUCCESSFUL("Unsuccessful"),
   @XmlEnumValue("PendingCancellation")
   PENDING_CANCELLATION("PendingCancellation"),
   @XmlEnumValue("PendingPurchase")
   PENDING_PURCHASE("PendingPurchase"),
   @XmlEnumValue("Requested")
   REQUESTED("Requested"),
   @XmlEnumValue("Reserved")
   RESERVED("Reserved"),
   @XmlEnumValue("Unchanged")
   UNCHANGED("Unchanged"),
   @XmlEnumValue("RequestDenied")
   REQUEST_DENIED("RequestDenied"),
   @XmlEnumValue("Ticketed")
   TICKETED("Ticketed");

   private final String value;

   TransactionStatusType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TransactionStatusType fromValue(String v) {
      for (TransactionStatusType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
