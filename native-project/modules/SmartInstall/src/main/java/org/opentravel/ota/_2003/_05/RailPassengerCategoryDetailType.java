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
@XmlType(name = "RailPassengerCategoryDetailType", propOrder = "passengerDetail")
public class RailPassengerCategoryDetailType extends RailPassengerCategoryType {
   @XmlElement(name = "PassengerDetail")
   protected List<RailPassengerDetailType> passengerDetail;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger quantity;

   public List<RailPassengerDetailType> getPassengerDetail() {
      if (this.passengerDetail == null) {
         this.passengerDetail = new ArrayList<>();
      }

      return this.passengerDetail;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }
}
