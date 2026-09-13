package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusIdentificationType", propOrder = {"busNumber", "networkCode"})
public class BusIdentificationType {
   @XmlElement(name = "BusNumber", required = true)
   protected String busNumber;
   @XmlElement(name = "NetworkCode", required = true)
   protected NetworkCodeType networkCode;
   @XmlAttribute(name = "BusTypeCode")
   protected String busTypeCode;

   public String getBusNumber() {
      return this.busNumber;
   }

   public void setBusNumber(String value) {
      this.busNumber = value;
   }

   public NetworkCodeType getNetworkCode() {
      return this.networkCode;
   }

   public void setNetworkCode(NetworkCodeType value) {
      this.networkCode = value;
   }

   public String getBusTypeCode() {
      return this.busTypeCode;
   }

   public void setBusTypeCode(String value) {
      this.busTypeCode = value;
   }
}
