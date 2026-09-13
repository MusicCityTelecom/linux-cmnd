package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargesType", propOrder = "charge")
public class ChargesType {
   @XmlElement(name = "Charge", required = true)
   protected List<FeeType> charge;

   public List<FeeType> getCharge() {
      if (this.charge == null) {
         this.charge = new ArrayList<>();
      }

      return this.charge;
   }
}
