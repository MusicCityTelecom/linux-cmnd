package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DestinationSystemCodesType", propOrder = "destinationSystemCode")
public class DestinationSystemCodesType {
   @XmlElement(name = "DestinationSystemCode", required = true)
   protected List<DestinationSystemCodesType.DestinationSystemCode> destinationSystemCode;

   public List<DestinationSystemCodesType.DestinationSystemCode> getDestinationSystemCode() {
      if (this.destinationSystemCode == null) {
         this.destinationSystemCode = new ArrayList<>();
      }

      return this.destinationSystemCode;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class DestinationSystemCode {
      @XmlValue
      protected String value;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }
   }
}
