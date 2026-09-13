package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelDateTimeType", propOrder = {"departureDateTime", "arrivalDateTime"})
@XmlSeeAlso({OriginDestinationInformationType.class, RailOriginDestinationInformationType.class})
public class TravelDateTimeType {
   @XmlElement(name = "DepartureDateTime")
   protected TimeInstantType departureDateTime;
   @XmlElement(name = "ArrivalDateTime")
   protected TimeInstantType arrivalDateTime;

   public TimeInstantType getDepartureDateTime() {
      return this.departureDateTime;
   }

   public void setDepartureDateTime(TimeInstantType value) {
      this.departureDateTime = value;
   }

   public TimeInstantType getArrivalDateTime() {
      return this.arrivalDateTime;
   }

   public void setArrivalDateTime(TimeInstantType value) {
      this.arrivalDateTime = value;
   }
}
