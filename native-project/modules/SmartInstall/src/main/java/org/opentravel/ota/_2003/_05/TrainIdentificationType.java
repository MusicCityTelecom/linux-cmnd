package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrainIdentificationType", propOrder = {"trainNumber", "networkCode"})
public class TrainIdentificationType {
   @XmlElement(name = "TrainNumber", required = true)
   protected String trainNumber;
   @XmlElement(name = "NetworkCode")
   protected NetworkCodeType networkCode;

   public String getTrainNumber() {
      return this.trainNumber;
   }

   public void setTrainNumber(String value) {
      this.trainNumber = value;
   }

   public NetworkCodeType getNetworkCode() {
      return this.networkCode;
   }

   public void setNetworkCode(NetworkCodeType value) {
      this.networkCode = value;
   }
}
