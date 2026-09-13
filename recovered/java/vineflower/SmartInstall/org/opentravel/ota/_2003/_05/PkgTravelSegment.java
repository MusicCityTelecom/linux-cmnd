package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PkgTravelSegment", propOrder = "airSegment")
public class PkgTravelSegment {
   @XmlElement(name = "AirSegment", required = true)
   protected PkgAirSegmentType airSegment;

   public PkgAirSegmentType getAirSegment() {
      return this.airSegment;
   }

   public void setAirSegment(PkgAirSegmentType value) {
      this.airSegment = value;
   }
}
