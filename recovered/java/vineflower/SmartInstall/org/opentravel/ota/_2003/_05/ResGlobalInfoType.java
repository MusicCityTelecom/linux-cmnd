package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResGlobalInfoType", propOrder = {"hotelReservationIDs", "routingHops", "profiles", "bookingRules"})
public class ResGlobalInfoType extends ResCommonDetailType {
   @XmlElement(name = "HotelReservationIDs")
   protected HotelReservationIDsType hotelReservationIDs;
   @XmlElement(name = "RoutingHops")
   protected RoutingHopType routingHops;
   @XmlElement(name = "Profiles")
   protected ProfilesType profiles;
   @XmlElement(name = "BookingRules")
   protected BookingRulesType bookingRules;

   public HotelReservationIDsType getHotelReservationIDs() {
      return this.hotelReservationIDs;
   }

   public void setHotelReservationIDs(HotelReservationIDsType value) {
      this.hotelReservationIDs = value;
   }

   public RoutingHopType getRoutingHops() {
      return this.routingHops;
   }

   public void setRoutingHops(RoutingHopType value) {
      this.routingHops = value;
   }

   public ProfilesType getProfiles() {
      return this.profiles;
   }

   public void setProfiles(ProfilesType value) {
      this.profiles = value;
   }

   public BookingRulesType getBookingRules() {
      return this.bookingRules;
   }

   public void setBookingRules(BookingRulesType value) {
      this.bookingRules = value;
   }
}
