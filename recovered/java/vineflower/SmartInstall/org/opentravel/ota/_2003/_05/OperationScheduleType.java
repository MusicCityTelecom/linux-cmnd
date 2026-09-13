package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationScheduleType", propOrder = "operationTimes")
@XmlSeeAlso({OperationSchedulePlusChargeType.class, PeriodPriceType.class})
public class OperationScheduleType {
   @XmlElement(name = "OperationTimes")
   protected OperationScheduleType.OperationTimes operationTimes;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;

   public OperationScheduleType.OperationTimes getOperationTimes() {
      return this.operationTimes;
   }

   public void setOperationTimes(OperationScheduleType.OperationTimes value) {
      this.operationTimes = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "operationTime")
   public static class OperationTimes {
      @XmlElement(name = "OperationTime", required = true)
      protected List<OperationScheduleType.OperationTimes.OperationTime> operationTime;

      public List<OperationScheduleType.OperationTimes.OperationTime> getOperationTime() {
         if (this.operationTime == null) {
            this.operationTime = new ArrayList<>();
         }

         return this.operationTime;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class OperationTime {
         @XmlAttribute(name = "AdditionalOperationInfoCode")
         protected String additionalOperationInfoCode;
         @XmlAttribute(name = "Frequency")
         protected String frequency;
         @XmlAttribute(name = "Text")
         protected String text;
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

         public String getAdditionalOperationInfoCode() {
            return this.additionalOperationInfoCode;
         }

         public void setAdditionalOperationInfoCode(String value) {
            this.additionalOperationInfoCode = value;
         }

         public String getFrequency() {
            return this.frequency;
         }

         public void setFrequency(String value) {
            this.frequency = value;
         }

         public String getText() {
            return this.text;
         }

         public void setText(String value) {
            this.text = value;
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
      }
   }
}
