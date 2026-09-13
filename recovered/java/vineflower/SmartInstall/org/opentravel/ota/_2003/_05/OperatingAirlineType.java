package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperatingAirlineType")
public class OperatingAirlineType extends CompanyNameType {
   @XmlAttribute(name = "FlightNumber")
   protected String flightNumber;
   @XmlAttribute(name = "ResBookDesigCode")
   protected String resBookDesigCode;

   public String getFlightNumber() {
      return this.flightNumber;
   }

   public void setFlightNumber(String value) {
      this.flightNumber = value;
   }

   public String getResBookDesigCode() {
      return this.resBookDesigCode;
   }

   public void setResBookDesigCode(String value) {
      this.resBookDesigCode = value;
   }
}
