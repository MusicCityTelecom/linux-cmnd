package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "billitem")
public class Billitem {
   @Id
   @Column(name = "ID")
   private String ID;
   @Column(name = "BillItemDisplayName")
   private String billItemDisplayName;
   @Column(name = "BillItemAmount")
   private String billItemAmount;
   @Column(name = "displayFlag")
   private String displayFlag = "Yes";
   @Column(name = "BillItemDate")
   private String billItemDate;
   @Column(name = "BillItemTime")
   private String billItemTime;
   @Column(name = "RoomId")
   private String roomId;

   public String getID() {
      return this.ID;
   }

   public void setID(String iD) {
      this.ID = iD;
   }

   public String getBillItemDisplayName() {
      return this.billItemDisplayName;
   }

   public void setBillItemDisplayName(String billItemDisplayName) {
      this.billItemDisplayName = billItemDisplayName;
   }

   public String getBillItemAmount() {
      return this.billItemAmount;
   }

   public void setBillItemAmount(String billItemAmount) {
      this.billItemAmount = billItemAmount;
   }

   public String getDisplayFlag() {
      return this.displayFlag;
   }

   public void setDisplayFlag(String displayFlag) {
      this.displayFlag = displayFlag;
   }

   public String getBillItemDate() {
      return this.billItemDate;
   }

   public void setBillItemDate(String billItemDate) {
      this.billItemDate = billItemDate;
   }

   public String getBillItemTime() {
      return this.billItemTime;
   }

   public void setBillItemTime(String billItemTime) {
      this.billItemTime = billItemTime;
   }

   public String getRoomId() {
      return this.roomId;
   }

   public void setRoomId(String roomId) {
      this.roomId = roomId;
   }
}
