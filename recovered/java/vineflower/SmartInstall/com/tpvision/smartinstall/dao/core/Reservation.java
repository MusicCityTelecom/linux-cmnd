package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
   @Id
   @Column(name = "reservationId")
   private String reservationId;
   @Column(name = "guests")
   private String guests;
   @Column(name = "rooms")
   private String rooms;
   @Column(name = "status")
   private String status;
   @Column(name = "startTime")
   private String startTime;
   @Column(name = "endTime")
   private String endTime;

   public String getReservationId() {
      return this.reservationId;
   }

   public void setReservationId(String reservationId) {
      this.reservationId = reservationId;
   }

   public String getGuests() {
      return this.guests;
   }

   public void setGuests(String guests) {
      this.guests = guests;
   }

   public String getRooms() {
      return this.rooms;
   }

   public void setRooms(String rooms) {
      this.rooms = rooms;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getStartTime() {
      return this.startTime;
   }

   public void setStartTime(String startTime) {
      this.startTime = startTime;
   }

   public String getEndTime() {
      return this.endTime;
   }

   public void setEndTime(String endTime) {
      this.endTime = endTime;
   }
}
