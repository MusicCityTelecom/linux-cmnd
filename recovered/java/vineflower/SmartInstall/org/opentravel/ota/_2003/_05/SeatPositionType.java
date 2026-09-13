package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SeatPositionType")
@XmlEnum
public enum SeatPositionType {
   @XmlEnumValue("None")
   NONE("None"),
   @XmlEnumValue("Together")
   TOGETHER("Together"),
   @XmlEnumValue("Aisle")
   AISLE("Aisle"),
   @XmlEnumValue("Center")
   CENTER("Center"),
   @XmlEnumValue("Window")
   WINDOW("Window"),
   @XmlEnumValue("Specific")
   SPECIFIC("Specific"),
   @XmlEnumValue("Exit")
   EXIT("Exit"),
   @XmlEnumValue("Table")
   TABLE("Table"),
   @XmlEnumValue("AdjacentAisle")
   ADJACENT_AISLE("AdjacentAisle"),
   @XmlEnumValue("Individual")
   INDIVIDUAL("Individual"),
   @XmlEnumValue("Middle")
   MIDDLE("Middle");

   private final String value;

   SeatPositionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static SeatPositionType fromValue(String v) {
      for (SeatPositionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
