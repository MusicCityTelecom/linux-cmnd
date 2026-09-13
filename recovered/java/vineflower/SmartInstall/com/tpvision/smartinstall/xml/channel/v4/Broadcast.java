package com.tpvision.smartinstall.xml.channel.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Broadcast")
public class Broadcast {
   @XmlAttribute(name = "medium", required = true)
   protected String medium;
   @XmlAttribute(name = "frequency", required = true)
   protected String frequency;
   @XmlAttribute(name = "system", required = true)
   protected String system;
   @XmlAttribute(name = "serviceID", required = true)
   protected String serviceID;
   @XmlAttribute(name = "ONID", required = true)
   protected String onid;
   @XmlAttribute(name = "TSID", required = true)
   protected String tsid;
   @XmlAttribute(name = "modulation", required = true)
   protected String modulation;
   @XmlAttribute(name = "symbolrate", required = true)
   protected String symbolrate;
   @XmlAttribute(name = "bandwidth", required = true)
   protected String bandwidth;
   @XmlAttribute(name = "servicetype", required = true)
   protected String servicetype;

   public String getONID() {
      return this.onid;
   }

   public void setONID(String value) {
      this.onid = value;
   }

   public String getTSID() {
      return this.tsid;
   }

   public void setTSID(String value) {
      this.tsid = value;
   }

   public String getBandwidth() {
      return this.bandwidth;
   }

   public void setBandwidth(String value) {
      this.bandwidth = value;
   }

   public String getFrequency() {
      return this.frequency;
   }

   public void setFrequency(String value) {
      this.frequency = value;
   }

   public String getMedium() {
      return this.medium;
   }

   public void setMedium(String value) {
      this.medium = value;
   }

   public String getModulation() {
      return this.modulation;
   }

   public void setModulation(String value) {
      this.modulation = value;
   }

   public String getServiceID() {
      return this.serviceID;
   }

   public void setServiceID(String value) {
      this.serviceID = value;
   }

   public String getServicetype() {
      return this.servicetype;
   }

   public void setServicetype(String value) {
      this.servicetype = value;
   }

   public String getSymbolrate() {
      return this.symbolrate;
   }

   public void setSymbolrate(String value) {
      this.symbolrate = value;
   }

   public String getSystem() {
      return this.system;
   }

   public void setSystem(String value) {
      this.system = value;
   }
}
