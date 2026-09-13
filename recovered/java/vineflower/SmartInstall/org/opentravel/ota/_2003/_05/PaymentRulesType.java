package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentRulesType", propOrder = "paymentRule")
@XmlSeeAlso(RateRulesType.PaymentRules.class)
public class PaymentRulesType {
   @XmlElement(name = "PaymentRule", required = true)
   protected List<MonetaryRuleType> paymentRule;

   public List<MonetaryRuleType> getPaymentRule() {
      if (this.paymentRule == null) {
         this.paymentRule = new ArrayList<>();
      }

      return this.paymentRule;
   }
}
