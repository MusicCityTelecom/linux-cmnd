package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelPenaltyType", propOrder = {"deadline", "amountPercent", "penaltyDescription"})
public class CancelPenaltyType {
   @XmlElement(name = "Deadline")
   protected CancelPenaltyType.Deadline deadline;
   @XmlElement(name = "AmountPercent")
   protected AmountPercentType amountPercent;
   @XmlElement(name = "PenaltyDescription")
   protected List<ParagraphType> penaltyDescription;
   @XmlAttribute(name = "ConfirmClassCode")
   protected String confirmClassCode;
   @XmlAttribute(name = "PolicyCode")
   protected String policyCode;
   @XmlAttribute(name = "NonRefundable")
   protected Boolean nonRefundable;
   @XmlAttribute(name = "RoomTypeCode")
   protected String roomTypeCode;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;
   @XmlAttribute(name = "Mon")
   protected Boolean mon;
   @XmlAttribute(name = "Tue")
   protected Boolean tue;
   @XmlAttribute(name = "Weds")
   protected Boolean weds;
   @XmlAttribute(name = "Thur")
   protected Boolean thur;
   @XmlAttribute(name = "Fri")
   protected Boolean fri;
   @XmlAttribute(name = "Sat")
   protected Boolean sat;
   @XmlAttribute(name = "Sun")
   protected Boolean sun;

   public CancelPenaltyType.Deadline getDeadline() {
      return this.deadline;
   }

   public void setDeadline(CancelPenaltyType.Deadline value) {
      this.deadline = value;
   }

   public AmountPercentType getAmountPercent() {
      return this.amountPercent;
   }

   public void setAmountPercent(AmountPercentType value) {
      this.amountPercent = value;
   }

   public List<ParagraphType> getPenaltyDescription() {
      if (this.penaltyDescription == null) {
         this.penaltyDescription = new ArrayList<>();
      }

      return this.penaltyDescription;
   }

   public String getConfirmClassCode() {
      return this.confirmClassCode;
   }

   public void setConfirmClassCode(String value) {
      this.confirmClassCode = value;
   }

   public String getPolicyCode() {
      return this.policyCode;
   }

   public void setPolicyCode(String value) {
      this.policyCode = value;
   }

   public Boolean isNonRefundable() {
      return this.nonRefundable;
   }

   public void setNonRefundable(Boolean value) {
      this.nonRefundable = value;
   }

   public String getRoomTypeCode() {
      return this.roomTypeCode;
   }

   public void setRoomTypeCode(String value) {
      this.roomTypeCode = value;
   }

   public String getStart() {
      return this.start;
   }

   public void setStart(String value) {
      this.start = value;
   }

   public String getDuration() {
      return this.duration;
   }

   public void setDuration(String value) {
      this.duration = value;
   }

   public String getEnd() {
      return this.end;
   }

   public void setEnd(String value) {
      this.end = value;
   }

   public Boolean isMon() {
      return this.mon;
   }

   public void setMon(Boolean value) {
      this.mon = value;
   }

   public Boolean isTue() {
      return this.tue;
   }

   public void setTue(Boolean value) {
      this.tue = value;
   }

   public Boolean isWeds() {
      return this.weds;
   }

   public void setWeds(Boolean value) {
      this.weds = value;
   }

   public Boolean isThur() {
      return this.thur;
   }

   public void setThur(Boolean value) {
      this.thur = value;
   }

   public Boolean isFri() {
      return this.fri;
   }

   public void setFri(Boolean value) {
      this.fri = value;
   }

   public Boolean isSat() {
      return this.sat;
   }

   public void setSat(Boolean value) {
      this.sat = value;
   }

   public Boolean isSun() {
      return this.sun;
   }

   public void setSun(Boolean value) {
      this.sun = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Deadline {
      @XmlAttribute(name = "AbsoluteDeadline")
      protected String absoluteDeadline;
      @XmlAttribute(name = "OffsetTimeUnit")
      protected TimeUnitType offsetTimeUnit;
      @XmlAttribute(name = "OffsetUnitMultiplier")
      protected Integer offsetUnitMultiplier;
      @XmlAttribute(name = "OffsetDropTime")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String offsetDropTime;

      public String getAbsoluteDeadline() {
         return this.absoluteDeadline;
      }

      public void setAbsoluteDeadline(String value) {
         this.absoluteDeadline = value;
      }

      public TimeUnitType getOffsetTimeUnit() {
         return this.offsetTimeUnit;
      }

      public void setOffsetTimeUnit(TimeUnitType value) {
         this.offsetTimeUnit = value;
      }

      public Integer getOffsetUnitMultiplier() {
         return this.offsetUnitMultiplier;
      }

      public void setOffsetUnitMultiplier(Integer value) {
         this.offsetUnitMultiplier = value;
      }

      public String getOffsetDropTime() {
         return this.offsetDropTime;
      }

      public void setOffsetDropTime(String value) {
         this.offsetDropTime = value;
      }
   }
}
