package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mychoice_template")
public class MyChoiceTemplate {
   @Id
   private int id;
   @Column(name = "logo")
   private String logo;
   @Column(name = "above_pin_code")
   private String abovePinCode;
   @Column(name = "below_pin_code")
   private String belowPinCode;
   @Column(name = "number_of_days")
   private String numberOfDays;
   @Column(name = "number_of_hours")
   private String numberOfHours;
   @Column(name = "room_number")
   private String roomNumber;
   @Column(name = "validity_notice")
   private String validityNotice;
   @Column(name = "instructions")
   private String instructions;

   public long getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getLogo() {
      return this.logo;
   }

   public void setLogo(String logo) {
      this.logo = logo;
   }

   public String getAbovePinCode() {
      return this.abovePinCode;
   }

   public void setAbovePinCode(String abovePinCode) {
      this.abovePinCode = abovePinCode;
   }

   public String getBelowPinCode() {
      return this.belowPinCode;
   }

   public void setBelowPinCode(String belowPinCode) {
      this.belowPinCode = belowPinCode;
   }

   public String getNumberOfDays() {
      return this.numberOfDays;
   }

   public void setNumberOfDays(String numberOfDays) {
      this.numberOfDays = numberOfDays;
   }

   public String getNumberOfHours() {
      return this.numberOfHours;
   }

   public void setNumberOfHours(String numberOfHours) {
      this.numberOfHours = numberOfHours;
   }

   public String getRoomNumber() {
      return this.roomNumber;
   }

   public void setRoomNumber(String roomNumber) {
      this.roomNumber = roomNumber;
   }

   public String getValidityNotice() {
      return this.validityNotice;
   }

   public void setValidityNotice(String validityNotice) {
      this.validityNotice = validityNotice;
   }

   public String getInstructions() {
      return this.instructions;
   }

   public void setInstructions(String instructions) {
      this.instructions = instructions;
   }
}
