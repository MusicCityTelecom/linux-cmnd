package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleTourInfoType", propOrder = "tourOperator")
@XmlSeeAlso(VehicleAvailVendorInfoType.TourInfo.class)
public class VehicleTourInfoType {
   @XmlElement(name = "TourOperator")
   protected CompanyNameType tourOperator;
   @XmlAttribute(name = "TourNumber")
   protected String tourNumber;

   public CompanyNameType getTourOperator() {
      return this.tourOperator;
   }

   public void setTourOperator(CompanyNameType value) {
      this.tourOperator = value;
   }

   public String getTourNumber() {
      return this.tourNumber;
   }

   public void setTourNumber(String value) {
      this.tourNumber = value;
   }
}
