package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TicketingInfoRS_Type")
@XmlSeeAlso({PricedItineraryType.TicketingInfo.class, PkgReservation.TicketingInfo.class})
public class TicketingInfoRSType extends TicketingInfoType {
   @XmlAttribute(name = "eTicketNumber")
   protected String eTicketNumber;

   public String getETicketNumber() {
      return this.eTicketNumber;
   }

   public void setETicketNumber(String value) {
      this.eTicketNumber = value;
   }
}
