package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SailingSearchQualifierType", propOrder = "port")
public class SailingSearchQualifierType extends SearchQualifierType {
   @XmlElement(name = "Port")
   protected SailingSearchQualifierType.Port port;

   public SailingSearchQualifierType.Port getPort() {
      return this.port;
   }

   public void setPort(SailingSearchQualifierType.Port value) {
      this.port = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Port {
      @XmlAttribute(name = "EmbarkIndicator")
      protected Boolean embarkIndicator;
      @XmlAttribute(name = "DisembarkIndicator")
      protected Boolean disembarkIndicator;
      @XmlAttribute(name = "PortCode")
      protected String portCode;
      @XmlAttribute(name = "PortName")
      protected String portName;
      @XmlAttribute(name = "PortCountryCode")
      protected String portCountryCode;
      @XmlAttribute(name = "DockIndicator")
      protected Boolean dockIndicator;
      @XmlAttribute(name = "ShorexIndicator")
      protected Boolean shorexIndicator;

      public Boolean isEmbarkIndicator() {
         return this.embarkIndicator;
      }

      public void setEmbarkIndicator(Boolean value) {
         this.embarkIndicator = value;
      }

      public Boolean isDisembarkIndicator() {
         return this.disembarkIndicator;
      }

      public void setDisembarkIndicator(Boolean value) {
         this.disembarkIndicator = value;
      }

      public String getPortCode() {
         return this.portCode;
      }

      public void setPortCode(String value) {
         this.portCode = value;
      }

      public String getPortName() {
         return this.portName;
      }

      public void setPortName(String value) {
         this.portName = value;
      }

      public String getPortCountryCode() {
         return this.portCountryCode;
      }

      public void setPortCountryCode(String value) {
         this.portCountryCode = value;
      }

      public Boolean isDockIndicator() {
         return this.dockIndicator;
      }

      public void setDockIndicator(Boolean value) {
         this.dockIndicator = value;
      }

      public Boolean isShorexIndicator() {
         return this.shorexIndicator;
      }

      public void setShorexIndicator(Boolean value) {
         this.shorexIndicator = value;
      }
   }
}
