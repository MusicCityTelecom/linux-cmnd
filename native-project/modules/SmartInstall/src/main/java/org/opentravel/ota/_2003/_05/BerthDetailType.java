package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BerthDetailType", propOrder = "value")
public class BerthDetailType {
   @XmlValue
   protected BerthAccommodationType value;
   @XmlAttribute(name = "Number")
   protected String number;
   @XmlAttribute(name = "Position")
   protected BerthPositionType position;

   public BerthAccommodationType getValue() {
      return this.value;
   }

   public void setValue(BerthAccommodationType value) {
      this.value = value;
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String value) {
      this.number = value;
   }

   public BerthPositionType getPosition() {
      return this.position;
   }

   public void setPosition(BerthPositionType value) {
      this.position = value;
   }
}
