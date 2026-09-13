package com.tpvision.smartinstall.api;

class PmsCheckinVO {
   String guestName;
   String checkinTime;
   String checkoutTime;
   String guestLanguage;
   String roomType;
   String roomNo;

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

   public String getGuestLanguage() {
      return this.guestLanguage;
   }

   public void setGuestLanguage(String guestLanguage) {
      this.guestLanguage = guestLanguage;
   }

   public String getRoomType() {
      return this.roomType;
   }

   public void setRoomType(String roomType) {
      this.roomType = roomType;
   }

   public String getRoomNo() {
      return this.roomNo;
   }

   public void setRoomNo(String roomNo) {
      this.roomNo = roomNo;
   }
}
