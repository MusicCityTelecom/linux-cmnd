package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelerInformationType", propOrder = {"passengerTypeQuantity", "airTraveler"})
public class TravelerInformationType {
   @XmlElement(name = "PassengerTypeQuantity", required = true)
   protected List<PassengerTypeQuantityType> passengerTypeQuantity;
   @XmlElement(name = "AirTraveler")
   protected AirTravelerType airTraveler;

   public List<PassengerTypeQuantityType> getPassengerTypeQuantity() {
      if (this.passengerTypeQuantity == null) {
         this.passengerTypeQuantity = new ArrayList<>();
      }

      return this.passengerTypeQuantity;
   }

   public AirTravelerType getAirTraveler() {
      return this.airTraveler;
   }

   public void setAirTraveler(AirTravelerType value) {
      this.airTraveler = value;
   }
}
