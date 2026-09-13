package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"affectedGuests", "sourceRoomInformation", "destinationRoomInformation", "tpaExtensions"})
@XmlRootElement(name = "HTNG_HotelRoomMoveNotifRQ")
public class HTNGHotelRoomMoveNotifRQ extends HTNGRequestBaseType {
   @XmlElement(name = "AffectedGuests", required = true)
   protected HTNGCollectionOfUniqueIDs affectedGuests;
   @XmlElement(name = "SourceRoomInformation", required = true)
   protected HTNGHotelRoomMoveNotifRQ.SourceRoomInformation sourceRoomInformation;
   @XmlElement(name = "DestinationRoomInformation", required = true)
   protected HTNGHotelRoomMoveNotifRQ.DestinationRoomInformation destinationRoomInformation;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;

   public HTNGCollectionOfUniqueIDs getAffectedGuests() {
      return this.affectedGuests;
   }

   public void setAffectedGuests(HTNGCollectionOfUniqueIDs value) {
      this.affectedGuests = value;
   }

   public HTNGHotelRoomMoveNotifRQ.SourceRoomInformation getSourceRoomInformation() {
      return this.sourceRoomInformation;
   }

   public void setSourceRoomInformation(HTNGHotelRoomMoveNotifRQ.SourceRoomInformation value) {
      this.sourceRoomInformation = value;
   }

   public HTNGHotelRoomMoveNotifRQ.DestinationRoomInformation getDestinationRoomInformation() {
      return this.destinationRoomInformation;
   }

   public void setDestinationRoomInformation(HTNGHotelRoomMoveNotifRQ.DestinationRoomInformation value) {
      this.destinationRoomInformation = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"room", "hotelReservations", "tpaExtensions"})
   public static class DestinationRoomInformation {
      @XmlElement(name = "Room", required = true)
      protected HTNGRoomElementType room;
      @XmlElement(name = "HotelReservations", required = true)
      protected HotelReservationsType hotelReservations;
      @XmlElement(name = "TPA_Extensions")
      protected TPAExtensionsType tpaExtensions;

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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"room", "hotelReservations", "tpaExtensions"})
   public static class SourceRoomInformation {
      @XmlElement(name = "Room", required = true)
      protected HTNGRoomElementType room;
      @XmlElement(name = "HotelReservations", required = true)
      protected HotelReservationsType hotelReservations;
      @XmlElement(name = "TPA_Extensions")
      protected TPAExtensionsType tpaExtensions;

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
}
