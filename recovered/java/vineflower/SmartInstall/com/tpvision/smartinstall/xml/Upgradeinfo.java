package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "upgradeinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Upgradeinfo {
   @XmlElement(name = "productid")
   public String productId;
   @XmlElement(name = "softwareversion")
   public String softWareVersion;

   public String getProductId() {
      return this.productId;
   }

   public void setProductId(String productId) {
      this.productId = productId;
   }

   public String getSoftWareVersion() {
      return this.softWareVersion;
   }

   public void setSoftWareVersion(String softWareVersion) {
      this.softWareVersion = softWareVersion;
   }
}
