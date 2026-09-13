package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "guestinfo")
public class GuestInfo {
   @Id
   @Column(name = "guestId")
   private String guestId;
   @Column(name = "guestName")
   private String guestName;
   @Column(name = "title")
   private String title;
   @Column(name = "guestLanguage")
   private String guestLanguage;
   @Column(name = "roomid")
   private String roomid;
   @Column(name = "tvsettingtype")
   private String tvsettingtype = "Standard";
   @Column(name = "checkin")
   private String checkin = "N";
   @Column(name = "orderid")
   private String orderid;
   @Column(name = "doNotDisturb")
   private String doNotDisturb;
   @Column(name = "viewBill")
   private String viewBill;
   @Column(name = "expressCheckout")
   private String expressCheckout;
   @Column(name = "arrivalDate")
   private String arrivalDate;
   @Column(name = "departureDate")
   private String departureDate;
   @Column(name = "groupName")
   private String groupName;
   @Column(name = "channelPackageId")
   private Integer channelPackageId;
   @Column(name = "appPackageId")
   private Integer appPackageId;
   @Column(name = "balance")
   private String balance;
   @Column(name = "totalBillDateTime")
   private String totalBillDateTime;
   @Column(name = "viewMessage")
   private String viewMessage;
   @Column(name = "scheduleId")
   private Integer scheduleId;
   @Column(name = "contentId")
   private Integer contentId;
   @Column(name = "welcomeId")
   private String welcomeId;
   @Column(name = "settingPackageId")
   private Integer settingPackageId;
   @Column(name = "bannerId")
   private Integer bannerId;
   @Column(name = "checkin_time")
   private String checkinTime;
   @Column(name = "checkout_time")
   private String checkoutTime;
   @Column(name = "roomType")
   private String roomType;

   public String getRoomType() {
      return this.roomType;
   }

   public void setRoomType(String roomType) {
      this.roomType = roomType;
   }

   public String getGuestId() {
      return this.guestId;
   }

   public void setGuestId(String guestId) {
      this.guestId = guestId;
   }

   public String getGuestName() {
      return this.guestName;
   }

   public void setGuestName(String guestName) {
      this.guestName = guestName;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getGuestLanguage() {
      return this.guestLanguage;
   }

   public void setGuestLanguage(String guestLanguage) {
      this.guestLanguage = guestLanguage;
   }

   public String getRoomid() {
      return this.roomid;
   }

   public void setRoomid(String roomid) {
      this.roomid = roomid;
   }

   public String getTvsettingtype() {
      return this.tvsettingtype;
   }

   public void setTvsettingtype(String tvsettingtype) {
      this.tvsettingtype = tvsettingtype;
   }

   public String getCheckin() {
      return this.checkin;
   }

   public void setCheckin(String checkin) {
      this.checkin = checkin;
   }

   public String getOrderid() {
      return this.orderid;
   }

   public void setOrderid(String orderid) {
      this.orderid = orderid;
   }

   public String getDoNotDisturb() {
      return this.doNotDisturb;
   }

   public void setDoNotDisturb(String doNotDisturb) {
      this.doNotDisturb = doNotDisturb;
   }

   public String getViewBill() {
      return this.viewBill;
   }

   public void setViewBill(String viewBill) {
      this.viewBill = viewBill;
   }

   public String getExpressCheckout() {
      return this.expressCheckout;
   }

   public void setExpressCheckout(String expressCheckout) {
      this.expressCheckout = expressCheckout;
   }

   public String getArrivalDate() {
      return this.arrivalDate;
   }

   public void setArrivalDate(String arrivalDate) {
      this.arrivalDate = arrivalDate;
   }

   public String getDepartureDate() {
      return this.departureDate;
   }

   public void setDepartureDate(String departureDate) {
      this.departureDate = departureDate;
   }

   public String getGroupName() {
      return this.groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public Integer getChannelPackageId() {
      return this.channelPackageId;
   }

   public void setChannelPackageId(Integer channelPackageId) {
      this.channelPackageId = channelPackageId;
   }

   public Integer getAppPackageId() {
      return this.appPackageId;
   }

   public void setAppPackageId(Integer appPackageId) {
      this.appPackageId = appPackageId;
   }

   public String getBalance() {
      return this.balance;
   }

   public void setBalance(String balance) {
      this.balance = balance;
   }

   public String getTotalBillDateTime() {
      return this.totalBillDateTime;
   }

   public void setTotalBillDateTime(String totalBillDateTime) {
      this.totalBillDateTime = totalBillDateTime;
   }

   public String getViewMessage() {
      return this.viewMessage;
   }

   public void setViewMessage(String viewMessage) {
      this.viewMessage = viewMessage;
   }

   public Integer getScheduleId() {
      return this.scheduleId;
   }

   public void setScheduleId(Integer scheduleId) {
      this.scheduleId = scheduleId;
   }

   public Integer getContentId() {
      return this.contentId;
   }

   public void setContentId(Integer contentId) {
      this.contentId = contentId;
   }

   public String getWelcomeId() {
      return this.welcomeId;
   }

   public void setWelcomeId(String welcomeId) {
      this.welcomeId = welcomeId;
   }

   public Integer getSettingPackageId() {
      return this.settingPackageId;
   }

   public void setSettingPackageId(Integer settingPackageId) {
      this.settingPackageId = settingPackageId;
   }

   public Integer getBannerId() {
      return this.bannerId;
   }

   public void setBannerId(Integer bannerId) {
      this.bannerId = bannerId;
   }

   public String getCheckoutTime() {
      return this.checkoutTime;
   }

   public void setCheckoutTime(String checkoutTime) {
      this.checkoutTime = checkoutTime;
   }

   public String getCheckinTime() {
      return this.checkinTime;
   }

   public void setCheckinTime(String checkinTime) {
      this.checkinTime = checkinTime;
   }

   public boolean isSupportViewBill() {
      return "True".equalsIgnoreCase(this.getViewBill());
   }
}
