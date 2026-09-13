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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RailAvailScheduleQueryType", propOrder = {"originDestinationInformation", "railSearchCriteria"})
public class RailAvailScheduleQueryType {
   @XmlElement(name = "OriginDestinationInformation", required = true)
   protected RailOriginDestinationInformationType originDestinationInformation;
   @XmlElement(name = "RailSearchCriteria")
   protected List<TrainQueryType> railSearchCriteria;
   @XmlAttribute(name = "MaxConnections")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger maxConnections;
   @XmlAttribute(name = "TrainTypeCode")
   protected String trainTypeCode;

   public RailOriginDestinationInformationType getOriginDestinationInformation() {
      return this.originDestinationInformation;
   }

   public void setOriginDestinationInformation(RailOriginDestinationInformationType value) {
      this.originDestinationInformation = value;
   }

   public List<TrainQueryType> getRailSearchCriteria() {
      if (this.railSearchCriteria == null) {
         this.railSearchCriteria = new ArrayList<>();
      }

      return this.railSearchCriteria;
   }

   public BigInteger getMaxConnections() {
      return this.maxConnections;
   }

   public void setMaxConnections(BigInteger value) {
      this.maxConnections = value;
   }

   public String getTrainTypeCode() {
      return this.trainTypeCode;
   }

   public void setTrainTypeCode(String value) {
      this.trainTypeCode = value;
   }
}
