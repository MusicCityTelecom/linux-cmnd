package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PkgPassengerRPHs", propOrder = "passengerRPH")
public class PkgPassengerRPHs {
   @XmlElement(name = "PassengerRPH", required = true)
   protected List<PkgPassengerRPHs.PassengerRPH> passengerRPH;

   public List<PkgPassengerRPHs.PassengerRPH> getPassengerRPH() {
      if (this.passengerRPH == null) {
         this.passengerRPH = new ArrayList<>();
      }

      return this.passengerRPH;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PassengerRPH {
      @XmlAttribute(name = "RPH", required = true)
      protected String rph;

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }
   }
}
