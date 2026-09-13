package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionType", propOrder = "connectionLocation")
public class ConnectionType {
   @XmlElement(name = "ConnectionLocation", required = true)
   protected List<ConnectionType.ConnectionLocation> connectionLocation;

   public List<ConnectionType.ConnectionLocation> getConnectionLocation() {
      if (this.connectionLocation == null) {
         this.connectionLocation = new ArrayList<>();
      }

      return this.connectionLocation;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ConnectionLocation extends LocationType {
      @XmlAttribute(name = "Inclusive")
      protected Boolean inclusive;
      @XmlAttribute(name = "MinChangeTime")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger minChangeTime;
      @XmlAttribute(name = "ConnectionInfo")
      protected String connectionInfo;
      @XmlAttribute(name = "MultiAirportCityInd")
      protected Boolean multiAirportCityInd;
      @XmlAttribute(name = "ConnectType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String connectType;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

      public Boolean isInclusive() {
         return this.inclusive;
      }

      public void setInclusive(Boolean value) {
         this.inclusive = value;
      }

      public BigInteger getMinChangeTime() {
         return this.minChangeTime;
      }

      public void setMinChangeTime(BigInteger value) {
         this.minChangeTime = value;
      }

      public String getConnectionInfo() {
         return this.connectionInfo;
      }

      public void setConnectionInfo(String value) {
         this.connectionInfo = value;
      }

      public Boolean isMultiAirportCityInd() {
         return this.multiAirportCityInd;
      }

      public void setMultiAirportCityInd(Boolean value) {
         this.multiAirportCityInd = value;
      }

      public String getConnectType() {
         return this.connectType;
      }

      public void setConnectType(String value) {
         this.connectType = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }
}
