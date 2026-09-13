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
@XmlType(name = "CabinClassType", propOrder = "airRows")
@XmlSeeAlso(SeatMapDetailsType.CabinClass.class)
public class CabinClassType {
   @XmlElement(name = "AirRows")
   protected CabinClassType.AirRows airRows;
   @XmlAttribute(name = "CabinType", required = true)
   protected CabinType cabinType;
   @XmlAttribute(name = "Name")
   protected String name;

   public CabinClassType.AirRows getAirRows() {
      return this.airRows;
   }

   public void setAirRows(CabinClassType.AirRows value) {
      this.airRows = value;
   }

   public CabinType getCabinType() {
      return this.cabinType;
   }

   public void setCabinType(CabinType value) {
      this.cabinType = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "airRow")
   public static class AirRows {
      @XmlElement(name = "AirRow", required = true)
      protected List<RowDetailsType> airRow;

      public List<RowDetailsType> getAirRow() {
         if (this.airRow == null) {
            this.airRow = new ArrayList<>();
         }

         return this.airRow;
      }
   }
}
