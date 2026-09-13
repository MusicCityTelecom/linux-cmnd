package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SailingType", propOrder = {"dining", "transportation", "information"})
public class SailingType extends SailingInfoType {
   @XmlElement(name = "Dining")
   protected List<SailingType.Dining> dining;
   @XmlElement(name = "Transportation")
   protected List<GuestTransportationType> transportation;
   @XmlElement(name = "Information")
   protected List<ParagraphType> information;
   @XmlAttribute(name = "MaxCabinOccupancy")
   protected Integer maxCabinOccupancy;
   @XmlAttribute(name = "CategoryLocation")
   protected CategoryLocationType categoryLocation;

   public List<SailingType.Dining> getDining() {
      if (this.dining == null) {
         this.dining = new ArrayList<>();
      }

      return this.dining;
   }

   public List<GuestTransportationType> getTransportation() {
      if (this.transportation == null) {
         this.transportation = new ArrayList<>();
      }

      return this.transportation;
   }

   public List<ParagraphType> getInformation() {
      if (this.information == null) {
         this.information = new ArrayList<>();
      }

      return this.information;
   }

   public Integer getMaxCabinOccupancy() {
      return this.maxCabinOccupancy;
   }

   public void setMaxCabinOccupancy(Integer value) {
      this.maxCabinOccupancy = value;
   }

   public CategoryLocationType getCategoryLocation() {
      return this.categoryLocation;
   }

   public void setCategoryLocation(CategoryLocationType value) {
      this.categoryLocation = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Dining {
      @XmlAttribute(name = "Sitting", required = true)
      protected String sitting;
      @XmlAttribute(name = "Status")
      protected String status;

      public String getSitting() {
         return this.sitting;
      }

      public void setSitting(String value) {
         this.sitting = value;
      }

      public String getStatus() {
         return this.status;
      }

      public void setStatus(String value) {
         this.status = value;
      }
   }
}
