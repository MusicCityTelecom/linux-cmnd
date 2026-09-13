package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "roomInformationList")
@XmlRootElement(name = "HTNG_HotelRoomStatusSearchRS")
public class HTNGHotelRoomStatusSearchRS extends HTNGResponseBaseType {
   @XmlElement(name = "RoomInformationList", required = true)
   protected HTNGHotelRoomStatusSearchRS.RoomInformationList roomInformationList;

   public HTNGHotelRoomStatusSearchRS.RoomInformationList getRoomInformationList() {
      return this.roomInformationList;
   }

   public void setRoomInformationList(HTNGHotelRoomStatusSearchRS.RoomInformationList value) {
      this.roomInformationList = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "roomInformation")
   public static class RoomInformationList {
      @XmlElement(name = "RoomInformation")
      protected List<HTNGHotelRoomStatusSearchRS.RoomInformationList.RoomInformation> roomInformation;

      public List<HTNGHotelRoomStatusSearchRS.RoomInformationList.RoomInformation> getRoomInformation() {
         if (this.roomInformation == null) {
            this.roomInformation = new ArrayList<>();
         }

         return this.roomInformation;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"room", "hotelReservations", "tpaExtensions"})
      public static class RoomInformation {
         @XmlElement(name = "Room", required = true)
         protected HTNGRoomElementType room;
         @XmlElement(name = "HotelReservations")
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
}
