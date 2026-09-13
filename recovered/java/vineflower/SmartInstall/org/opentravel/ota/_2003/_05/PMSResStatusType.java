package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PMS_ResStatusType")
@XmlEnum
public enum PMSResStatusType {
   @XmlEnumValue("Reserved")
   RESERVED("Reserved"),
   @XmlEnumValue("Requested")
   REQUESTED("Requested"),
   @XmlEnumValue("Request denied")
   REQUEST_DENIED("Request denied"),
   @XmlEnumValue("No-show")
   NO_SHOW("No-show"),
   @XmlEnumValue("Cancelled")
   CANCELLED("Cancelled"),
   @XmlEnumValue("In-house")
   IN_HOUSE("In-house"),
   @XmlEnumValue("Checked out")
   CHECKED_OUT("Checked out"),
   @XmlEnumValue("Waitlisted")
   WAITLISTED("Waitlisted");

   private final String value;

   PMSResStatusType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static PMSResStatusType fromValue(String v) {
      for (PMSResStatusType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
