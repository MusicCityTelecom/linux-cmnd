package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "inventories")
@XmlRootElement(name = "OTA_HotelInvCountNotifRS")
public class OTAHotelInvCountNotifRS extends MessageAcknowledgementType {
   @XmlElement(name = "Inventories")
   protected InvCountType inventories;

   public InvCountType getInventories() {
      return this.inventories;
   }

   public void setInventories(InvCountType value) {
      this.inventories = value;
   }
}
