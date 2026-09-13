package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelerInfoType", propOrder = {"airTraveler", "specialReqDetails"})
public class TravelerInfoType {
   @XmlElement(name = "AirTraveler")
   protected List<TravelerInfoType.AirTraveler> airTraveler;
   @XmlElement(name = "SpecialReqDetails")
   protected List<SpecialReqDetailsType> specialReqDetails;

   public List<TravelerInfoType.AirTraveler> getAirTraveler() {
      if (this.airTraveler == null) {
         this.airTraveler = new ArrayList<>();
      }

      return this.airTraveler;
   }

   public List<SpecialReqDetailsType> getSpecialReqDetails() {
      if (this.specialReqDetails == null) {
         this.specialReqDetails = new ArrayList<>();
      }

      return this.specialReqDetails;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "comment")
   public static class AirTraveler extends AirTravelerType {
      @XmlElement(name = "Comment")
      protected List<TravelerInfoType.AirTraveler.Comment> comment;

      public List<TravelerInfoType.AirTraveler.Comment> getComment() {
         if (this.comment == null) {
            this.comment = new ArrayList<>();
         }

         return this.comment;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Comment extends FormattedTextTextType {
         @XmlAttribute(name = "Name")
         protected String name;

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }
      }
   }
}
