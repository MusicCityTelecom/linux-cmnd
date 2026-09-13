package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "future_check_in")
public class FutureCheckIn {
   @Id
   @Column(name = "id")
   private int id;
   @Column(name = "room_id")
   private String roomid;
   @Column(name = "guest_name")
   private String guestName;
   @Column(name = "checkin_time")
   private String checkinTime;
   @Column(name = "checkout_time")
   private String checkoutTime;
   @Column(name = "room_type")
   private String roomType;
   @Column(name = "guest_language")
   private String guestLanguage;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getRoomid() {
      return this.roomid;
   }

   public void setRoomid(String roomid) {
      this.roomid = roomid;
   }

   public String getGuestName() {
      return this.guestName;
   }

   public void setGuestName(String guestName) {
      this.guestName = guestName;
   }

   public String getCheckinTime() {
      return this.checkinTime;
   }

   public void setCheckinTime(String checkinTime) {
      this.checkinTime = checkinTime;
   }

   public String getCheckoutTime() {
      return this.checkoutTime;
   }

   public void setCheckoutTime(String checkoutTime) {
      this.checkoutTime = checkoutTime;
   }

   public String getRoomType() {
      return this.roomType;
   }

   public void setRoomType(String roomType) {
      this.roomType = roomType;
   }

   public String getGuestLanguage() {
      return this.guestLanguage;
   }

   public void setGuestLanguage(String guestLanguage) {
      this.guestLanguage = guestLanguage;
   }
}
