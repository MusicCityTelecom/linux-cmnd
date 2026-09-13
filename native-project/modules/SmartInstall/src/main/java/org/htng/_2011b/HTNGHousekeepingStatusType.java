package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HTNG_HousekeepingStatusType")
@XmlEnum
public enum HTNGHousekeepingStatusType {
   NEEDS_INSPECTION,
   OCCUPIED_CLEAN,
   OCCUPIED_DIRTY,
   OFF_MARKET,
   OUT_OF_ORDER,
   PICKUP,
   VACANT_CLEAN,
   VACANT_DIRTY;

   public String value() {
      return this.name();
   }

   public static HTNGHousekeepingStatusType fromValue(String v) {
      return valueOf(v);
   }
}
