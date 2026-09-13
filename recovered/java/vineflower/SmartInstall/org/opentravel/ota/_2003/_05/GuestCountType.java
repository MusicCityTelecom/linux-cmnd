package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestCountType", propOrder = "guestCount")
public class GuestCountType {
   @XmlElement(name = "GuestCount", required = true)
   protected List<GuestCountType.GuestCount> guestCount;
   @XmlAttribute(name = "IsPerRoom")
   protected Boolean isPerRoom;

   public List<GuestCountType.GuestCount> getGuestCount() {
      if (this.guestCount == null) {
         this.guestCount = new ArrayList<>();
      }

      return this.guestCount;
   }

   public Boolean isIsPerRoom() {
      return this.isPerRoom;
   }

   public void setIsPerRoom(Boolean value) {
      this.isPerRoom = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class GuestCount {
      @XmlAttribute(name = "ResGuestRPH")
      protected String resGuestRPH;
      @XmlAttribute(name = "AgeQualifyingCode")
      protected String ageQualifyingCode;
      @XmlAttribute(name = "Age")
      protected Integer age;
      @XmlAttribute(name = "Count")
      protected Integer count;
      @XmlAttribute(name = "AgeBucket")
      protected String ageBucket;

      public String getResGuestRPH() {
         return this.resGuestRPH;
      }

      public void setResGuestRPH(String value) {
         this.resGuestRPH = value;
      }

      public String getAgeQualifyingCode() {
         return this.ageQualifyingCode;
      }

      public void setAgeQualifyingCode(String value) {
         this.ageQualifyingCode = value;
      }

      public Integer getAge() {
         return this.age;
      }

      public void setAge(Integer value) {
         this.age = value;
      }

      public Integer getCount() {
         return this.count;
      }

      public void setCount(Integer value) {
         this.count = value;
      }

      public String getAgeBucket() {
         return this.ageBucket;
      }

      public void setAgeBucket(String value) {
         this.ageBucket = value;
      }
   }
}
