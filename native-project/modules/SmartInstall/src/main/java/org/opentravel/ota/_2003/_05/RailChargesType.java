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
@XmlType(name = "RailChargesType", propOrder = {"total", "charges"})
public class RailChargesType {
   @XmlElement(name = "Total")
   protected TotalType total;
   @XmlElement(name = "Charges", required = true)
   protected RailChargesType.Charges charges;
   @XmlAttribute(name = "CurrencyCode")
   protected String currencyCode;
   @XmlAttribute(name = "DecimalPlaces")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger decimalPlaces;

   public TotalType getTotal() {
      return this.total;
   }

   public void setTotal(TotalType value) {
      this.total = value;
   }

   public RailChargesType.Charges getCharges() {
      return this.charges;
   }

   public void setCharges(RailChargesType.Charges value) {
      this.charges = value;
   }

   public String getCurrencyCode() {
      return this.currencyCode;
   }

   public void setCurrencyCode(String value) {
      this.currencyCode = value;
   }

   public BigInteger getDecimalPlaces() {
      return this.decimalPlaces;
   }

   public void setDecimalPlaces(BigInteger value) {
      this.decimalPlaces = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "charge")
   public static class Charges {
      @XmlElement(name = "Charge", required = true)
      protected List<FeeType> charge;

      public List<FeeType> getCharge() {
         if (this.charge == null) {
            this.charge = new ArrayList<>();
         }

         return this.charge;
      }
   }
}
