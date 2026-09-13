package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RebateType", propOrder = "paymentInformation")
public class RebateType {
   @XmlElement(name = "PaymentInformation")
   protected List<PaymentCardType> paymentInformation;
   @XmlAttribute(name = "ParticipationInd")
   protected Boolean participationInd;
   @XmlAttribute(name = "ProgramName")
   protected String programName;
   @XmlAttribute(name = "TripPurpose")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String tripPurpose;
   @XmlAttribute(name = "TripPurposeRequiredInd")
   protected Boolean tripPurposeRequiredInd;

   public List<PaymentCardType> getPaymentInformation() {
      if (this.paymentInformation == null) {
         this.paymentInformation = new ArrayList<>();
      }

      return this.paymentInformation;
   }

   public Boolean isParticipationInd() {
      return this.participationInd;
   }

   public void setParticipationInd(Boolean value) {
      this.participationInd = value;
   }

   public String getProgramName() {
      return this.programName;
   }

   public void setProgramName(String value) {
      this.programName = value;
   }

   public String getTripPurpose() {
      return this.tripPurpose;
   }

   public void setTripPurpose(String value) {
      this.tripPurpose = value;
   }

   public Boolean isTripPurposeRequiredInd() {
      return this.tripPurposeRequiredInd;
   }

   public void setTripPurposeRequiredInd(Boolean value) {
      this.tripPurposeRequiredInd = value;
   }
}
