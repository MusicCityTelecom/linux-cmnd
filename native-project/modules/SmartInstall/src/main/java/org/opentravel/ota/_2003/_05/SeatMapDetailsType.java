package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeatMapDetailsType", propOrder = "cabinClass")
public class SeatMapDetailsType {
   @XmlElement(name = "CabinClass", required = true)
   protected List<SeatMapDetailsType.CabinClass> cabinClass;
   @XmlAttribute(name = "TravelerRefNumberRPHs")
   protected List<String> travelerRefNumberRPHs;

   public List<SeatMapDetailsType.CabinClass> getCabinClass() {
      if (this.cabinClass == null) {
         this.cabinClass = new ArrayList<>();
      }

      return this.cabinClass;
   }

   public List<String> getTravelerRefNumberRPHs() {
      if (this.travelerRefNumberRPHs == null) {
         this.travelerRefNumberRPHs = new ArrayList<>();
      }

      return this.travelerRefNumberRPHs;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CabinClass extends CabinClassType {
      @XmlAttribute(name = "StartingRow")
      protected Integer startingRow;
      @XmlAttribute(name = "EndingRow")
      protected Integer endingRow;

      public Integer getStartingRow() {
         return this.startingRow;
      }

      public void setStartingRow(Integer value) {
         this.startingRow = value;
      }

      public Integer getEndingRow() {
         return this.endingRow;
      }

      public void setEndingRow(Integer value) {
         this.endingRow = value;
      }
   }
}
