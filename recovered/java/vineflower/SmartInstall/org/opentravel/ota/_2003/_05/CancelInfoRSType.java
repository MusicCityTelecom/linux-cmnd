package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelInfoRSType", propOrder = {"cancelRules", "uniqueID"})
public class CancelInfoRSType {
   @XmlElement(name = "CancelRules")
   protected CancelInfoRSType.CancelRules cancelRules;
   @XmlElement(name = "UniqueID")
   protected UniqueIDType uniqueID;

   public CancelInfoRSType.CancelRules getCancelRules() {
      return this.cancelRules;
   }

   public void setCancelRules(CancelInfoRSType.CancelRules value) {
      this.cancelRules = value;
   }

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "cancelRule")
   public static class CancelRules {
      @XmlElement(name = "CancelRule", required = true)
      protected List<CancelRuleType> cancelRule;

      public List<CancelRuleType> getCancelRule() {
         if (this.cancelRule == null) {
            this.cancelRule = new ArrayList<>();
         }

         return this.cancelRule;
      }
   }
}
