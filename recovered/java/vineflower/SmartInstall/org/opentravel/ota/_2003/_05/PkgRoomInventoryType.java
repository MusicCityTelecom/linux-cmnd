package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PkgRoomInventoryType", propOrder = "supplementCharges")
public class PkgRoomInventoryType extends RoomStayCandidateType {
   @XmlElement(name = "SupplementCharges")
   protected List<ChargesType> supplementCharges;
   @XmlAttribute(name = "Description")
   protected String description;
   @XmlAttribute(name = "MaxAdults")
   protected Integer maxAdults;
   @XmlAttribute(name = "CotQuantity")
   protected Integer cotQuantity;
   @XmlAttribute(name = "FreeChildFlag")
   protected Boolean freeChildFlag;
   @XmlAttribute(name = "MinOccupancy")
   protected Integer minOccupancy;
   @XmlAttribute(name = "MaxOccupancy")
   protected Integer maxOccupancy;

   public List<ChargesType> getSupplementCharges() {
      if (this.supplementCharges == null) {
         this.supplementCharges = new ArrayList<>();
      }

      return this.supplementCharges;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      this.description = value;
   }

   public Integer getMaxAdults() {
      return this.maxAdults;
   }

   public void setMaxAdults(Integer value) {
      this.maxAdults = value;
   }

   public Integer getCotQuantity() {
      return this.cotQuantity;
   }

   public void setCotQuantity(Integer value) {
      this.cotQuantity = value;
   }

   public Boolean isFreeChildFlag() {
      return this.freeChildFlag;
   }

   public void setFreeChildFlag(Boolean value) {
      this.freeChildFlag = value;
   }

   public Integer getMinOccupancy() {
      return this.minOccupancy;
   }

   public void setMinOccupancy(Integer value) {
      this.minOccupancy = value;
   }

   public Integer getMaxOccupancy() {
      return this.maxOccupancy;
   }

   public void setMaxOccupancy(Integer value) {
      this.maxOccupancy = value;
   }
}
