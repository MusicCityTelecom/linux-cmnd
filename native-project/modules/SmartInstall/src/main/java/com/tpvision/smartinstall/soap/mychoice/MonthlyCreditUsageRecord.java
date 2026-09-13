package com.tpvision.smartinstall.soap.mychoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthlyCreditUsageRecord", propOrder = {"year", "month", "credits"})
public class MonthlyCreditUsageRecord {
   protected int year;
   protected int month;
   protected int credits;

   public int getYear() {
      return this.year;
   }

   public void setYear(int value) {
      this.year = value;
   }

   public int getMonth() {
      return this.month;
   }

   public void setMonth(int value) {
      this.month = value;
   }

   public int getCredits() {
      return this.credits;
   }

   public void setCredits(int value) {
      this.credits = value;
   }
}
