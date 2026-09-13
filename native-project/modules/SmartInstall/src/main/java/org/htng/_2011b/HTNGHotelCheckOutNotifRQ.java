package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"affectedGuests", "room", "hotelReservations", "tpaExtensions"})
@XmlRootElement(name = "HTNG_HotelCheckOutNotifRQ")
public class HTNGHotelCheckOutNotifRQ extends HTNGRequestBaseType {
   @XmlElement(name = "AffectedGuests", required = true)
   protected HTNGCollectionOfUniqueIDs affectedGuests;
   @XmlElement(name = "Room", required = true)
   protected HTNGRoomElementType room;
   @XmlElement(name = "HotelReservations", required = true)
   protected HotelReservationsType hotelReservations;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;

   public HTNGCollectionOfUniqueIDs getAffectedGuests() {
      return this.affectedGuests;
   }

   public void setAffectedGuests(HTNGCollectionOfUniqueIDs value) {
      this.affectedGuests = value;
   }

   public HTNGRoomElementType getRoom() {
      return this.room;
   }

   public void setRoom(HTNGRoomElementType value) {
      this.room = value;
   }

   public HotelReservationsType getHotelReservations() {
      return this.hotelReservations;
   }

   public void setHotelReservations(HotelReservationsType value) {
      this.hotelReservations = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }
}
