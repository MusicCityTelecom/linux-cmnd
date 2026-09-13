package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceRequestType", propOrder = "value")
public class ServiceRequestType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "TravelSector")
   protected String travelSector;
   @XmlAttribute(name = "InventoryItemRPH")
   protected String inventoryItemRPH;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getTravelSector() {
      return this.travelSector;
   }

   public void setTravelSector(String value) {
      this.travelSector = value;
   }

   public String getInventoryItemRPH() {
      return this.inventoryItemRPH;
   }

   public void setInventoryItemRPH(String value) {
      this.inventoryItemRPH = value;
   }
}
