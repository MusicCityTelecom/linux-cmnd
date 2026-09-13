package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SeatAccommodationType")
@XmlEnum
public enum SeatAccommodationType {
   @XmlEnumValue("NotSignificant")
   NOT_SIGNIFICANT("NotSignificant"),
   @XmlEnumValue("Seat")
   SEAT("Seat"),
   @XmlEnumValue("Sleeperette")
   SLEEPERETTE("Sleeperette"),
   @XmlEnumValue("NoSeat")
   NO_SEAT("NoSeat");

   private final String value;

   SeatAccommodationType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static SeatAccommodationType fromValue(String v) {
      for (SeatAccommodationType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
