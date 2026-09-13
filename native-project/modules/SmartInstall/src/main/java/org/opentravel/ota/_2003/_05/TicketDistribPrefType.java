package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TicketDistribPrefType", propOrder = "value")
@XmlSeeAlso(AirSearchPrefsType.TicketDistribPref.class)
public class TicketDistribPrefType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "DistribType")
   protected String distribType;
   @XmlAttribute(name = "TicketTime")
   protected Duration ticketTime;
   @XmlAttribute(name = "Remark")
   protected String remark;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getDistribType() {
      return this.distribType;
   }

   public void setDistribType(String value) {
      this.distribType = value;
   }

   public Duration getTicketTime() {
      return this.ticketTime;
   }

   public void setTicketTime(Duration value) {
      this.ticketTime = value;
   }

   public String getRemark() {
      return this.remark;
   }

   public void setRemark(String value) {
      this.remark = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
   }
}
