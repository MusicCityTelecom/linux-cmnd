package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcceptedPaymentsType", propOrder = "acceptedPayment")
public class AcceptedPaymentsType {
   @XmlElement(name = "AcceptedPayment", required = true)
   protected List<PaymentFormType> acceptedPayment;

   public List<PaymentFormType> getAcceptedPayment() {
      if (this.acceptedPayment == null) {
         this.acceptedPayment = new ArrayList<>();
      }

      return this.acceptedPayment;
   }
}
