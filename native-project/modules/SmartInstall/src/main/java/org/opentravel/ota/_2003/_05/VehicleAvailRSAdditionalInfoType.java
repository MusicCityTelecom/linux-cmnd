package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleAvailRSAdditionalInfoType", propOrder = "customer")
public class VehicleAvailRSAdditionalInfoType {
   @XmlElement(name = "Customer")
   protected CustomerPrimaryAdditionalType customer;

   public CustomerPrimaryAdditionalType getCustomer() {
      return this.customer;
   }

   public void setCustomer(CustomerPrimaryAdditionalType value) {
      this.customer = value;
   }
}
