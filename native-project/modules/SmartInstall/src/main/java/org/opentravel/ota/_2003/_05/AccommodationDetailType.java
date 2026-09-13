package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccommodationDetailType", propOrder = "facilityChoices")
public class AccommodationDetailType extends AccommodationInfoType {
   @XmlElement(name = "FacilityChoices")
   protected FacilityChoicesType facilityChoices;
   @XmlAttribute(name = "BedQuantity")
   protected Integer bedQuantity;
   @XmlAttribute(name = "TravelOKFlag")
   protected Boolean travelOKFlag;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;

   public FacilityChoicesType getFacilityChoices() {
      return this.facilityChoices;
   }

   public void setFacilityChoices(FacilityChoicesType value) {
      this.facilityChoices = value;
   }

   public Integer getBedQuantity() {
      return this.bedQuantity;
   }

   public void setBedQuantity(Integer value) {
      this.bedQuantity = value;
   }

   public Boolean isTravelOKFlag() {
      return this.travelOKFlag;
   }

   public void setTravelOKFlag(Boolean value) {
      this.travelOKFlag = value;
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
}
