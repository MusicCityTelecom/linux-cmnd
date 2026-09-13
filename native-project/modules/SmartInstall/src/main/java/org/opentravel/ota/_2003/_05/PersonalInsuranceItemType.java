package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonalInsuranceItemType", propOrder = "customerCounts")
public class PersonalInsuranceItemType {
   @XmlElement(name = "CustomerCounts", required = true)
   protected CustomerCountsType customerCounts;
   @XmlAttribute(name = "Code", required = true)
   protected PkgPersonalInsuranceCode code;

   public CustomerCountsType getCustomerCounts() {
      return this.customerCounts;
   }

   public void setCustomerCounts(CustomerCountsType value) {
      this.customerCounts = value;
   }

   public PkgPersonalInsuranceCode getCode() {
      return this.code;
   }

   public void setCode(PkgPersonalInsuranceCode value) {
      this.code = value;
   }
}
