package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportationsType", propOrder = "transportations")
@XmlSeeAlso(RelativePositionType.class)
public class TransportationsType {
   @XmlElement(name = "Transportations")
   protected TransportationType transportations;

   public TransportationType getTransportations() {
      return this.transportations;
   }

   public void setTransportations(TransportationType value) {
      this.transportations = value;
   }
}
