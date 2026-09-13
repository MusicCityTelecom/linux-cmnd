package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateLiteType", propOrder = "rate")
public class RateLiteType {
   @XmlElement(name = "Rate", required = true)
   protected List<AmountLiteType> rate;

   public List<AmountLiteType> getRate() {
      if (this.rate == null) {
         this.rate = new ArrayList<>();
      }

      return this.rate;
   }
}
