package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeatDetailType", propOrder = "value")
public class SeatDetailType {
   @XmlValue
   protected SeatAccommodationType value;
   @XmlAttribute(name = "Number")
   protected String number;
   @XmlAttribute(name = "Position")
   protected SeatPositionType position;
   @XmlAttribute(name = "Direction")
   protected SeatDirectionType direction;

   public SeatAccommodationType getValue() {
      return this.value;
   }

   public void setValue(SeatAccommodationType value) {
      this.value = value;
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String value) {
      this.number = value;
   }

   public SeatPositionType getPosition() {
      return this.position;
   }

   public void setPosition(SeatPositionType value) {
      this.position = value;
   }

   public SeatDirectionType getDirection() {
      return this.direction;
   }

   public void setDirection(SeatDirectionType value) {
      this.direction = value;
   }
}
