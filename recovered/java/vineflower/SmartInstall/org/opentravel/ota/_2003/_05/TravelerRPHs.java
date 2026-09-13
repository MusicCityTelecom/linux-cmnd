package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TravelerRPHs", propOrder = "travelerRPH")
public class TravelerRPHs {
   @XmlElement(name = "TravelerRPH", required = true)
   protected List<TravelerRPHs.TravelerRPH> travelerRPH;

   public List<TravelerRPHs.TravelerRPH> getTravelerRPH() {
      if (this.travelerRPH == null) {
         this.travelerRPH = new ArrayList<>();
      }

      return this.travelerRPH;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TravelerRPH {
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
