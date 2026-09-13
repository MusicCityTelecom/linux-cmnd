package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.RevenueDetailType;
import org.opentravel.ota._2003._05.TotalType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_RevenueDetailType", propOrder = {"unitPrice", "extendedPrice"})
public class HTNGRevenueDetailType extends RevenueDetailType {
   @XmlElement(name = "UnitPrice")
   protected TotalType unitPrice;
   @XmlElement(name = "ExtendedPrice")
   protected HTNGExtendedPrice extendedPrice;

   public TotalType getUnitPrice() {
      return this.unitPrice;
   }

   public void setUnitPrice(TotalType value) {
      this.unitPrice = value;
   }

   public HTNGExtendedPrice getExtendedPrice() {
      return this.extendedPrice;
   }

   public void setExtendedPrice(HTNGExtendedPrice value) {
      this.extendedPrice = value;
   }
}
