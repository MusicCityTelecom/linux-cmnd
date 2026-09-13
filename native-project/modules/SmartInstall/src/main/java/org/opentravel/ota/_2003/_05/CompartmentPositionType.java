package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CompartmentPositionType")
@XmlEnum
public enum CompartmentPositionType {
   @XmlEnumValue("CloseToRestaurantCar")
   CLOSE_TO_RESTAURANT_CAR("CloseToRestaurantCar"),
   @XmlEnumValue("CloseToExit")
   CLOSE_TO_EXIT("CloseToExit"),
   @XmlEnumValue("CloseToToilet")
   CLOSE_TO_TOILET("CloseToToilet"),
   @XmlEnumValue("MiddleOfCar")
   MIDDLE_OF_CAR("MiddleOfCar");

   private final String value;

   CompartmentPositionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static CompartmentPositionType fromValue(String v) {
      for (CompartmentPositionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
