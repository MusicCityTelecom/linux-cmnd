package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OwnInsuranceChoiceType", propOrder = "customerCounts")
public class OwnInsuranceChoiceType {
   @XmlElement(name = "CustomerCounts")
   protected CustomerCountsType customerCounts;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "InsuranceCompany", required = true)
   protected String insuranceCompany;
   @XmlAttribute(name = "PolicyNmbr")
   protected String policyNmbr;

   public CustomerCountsType getCustomerCounts() {
      return this.customerCounts;
   }

   public void setCustomerCounts(CustomerCountsType value) {
      this.customerCounts = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getInsuranceCompany() {
      return this.insuranceCompany;
   }

   public void setInsuranceCompany(String value) {
      this.insuranceCompany = value;
   }

   public String getPolicyNmbr() {
      return this.policyNmbr;
   }

   public void setPolicyNmbr(String value) {
      this.policyNmbr = value;
   }
}
