package com.tpvision.smartinstall.xml.channel.v5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Broadcast")
public class Broadcast {
   @XmlAttribute(name = "ONID", required = true)
   protected String onid;
   @XmlAttribute(name = "TSID", required = true)
   protected String tsid;
   @XmlAttribute(name = "bandwidth", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "NMTOKEN")
   protected String bandwidth;
   @XmlAttribute(name = "frequency", required = true)
   protected String frequency;
   @XmlAttribute(name = "medium", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "NCName")
   protected String medium;
   @XmlAttribute(name = "modulation", required = true)
   protected String modulation;
   @XmlAttribute(name = "serviceID", required = true)
   protected String serviceID;
   @XmlAttribute(name = "servicetype", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "NCName")
   protected String servicetype;
   @XmlAttribute(name = "symbolrate", required = true)
   protected String symbolrate;
   @XmlAttribute(name = "system", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "NCName")
   protected String system;
   @XmlAttribute(name = "ProgramNumber", required = true)
   protected String programnumber;
   @XmlAttribute(name = "PhysicalChannel", required = true)
   protected String physicalchannel;
   @XmlAttribute(name = "Orbitalposition", required = true)
   protected String orbitalposition;
   @XmlAttribute(name = "Polarization", required = true)
   protected String polarization;

   public String getProgramNumber() {
      return this.programnumber;
   }

   public void setProgramNumber(String programNumber) {
      this.programnumber = programNumber;
   }

   public String getPhysicalChannel() {
      return this.physicalchannel;
   }

   public void setPhysicalChannel(String physicalChannel) {
      this.physicalchannel = physicalChannel;
   }

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

   public String getOrbitalposition() {
      return this.orbitalposition;
   }

   public void setOrbitalposition(String orbitalposition) {
      this.orbitalposition = orbitalposition;
   }

   public String getPolarization() {
      return this.polarization;
   }

   public void setPolarization(String polarization) {
      this.polarization = polarization;
   }
}
