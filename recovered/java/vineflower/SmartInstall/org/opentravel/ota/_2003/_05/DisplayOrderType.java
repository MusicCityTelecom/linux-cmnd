package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DisplayOrderType")
@XmlEnum
public enum DisplayOrderType {
   @XmlEnumValue("ByDepartureTime")
   BY_DEPARTURE_TIME("ByDepartureTime"),
   @XmlEnumValue("ByArrivalTime")
   BY_ARRIVAL_TIME("ByArrivalTime"),
   @XmlEnumValue("ByJourneyTime")
   BY_JOURNEY_TIME("ByJourneyTime"),
   @XmlEnumValue("ByPriceHighToLow")
   BY_PRICE_HIGH_TO_LOW("ByPriceHighToLow"),
   @XmlEnumValue("ByPriceLowToHigh")
   BY_PRICE_LOW_TO_HIGH("ByPriceLowToHigh");

   private final String value;

   DisplayOrderType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static DisplayOrderType fromValue(String v) {
      for (DisplayOrderType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
