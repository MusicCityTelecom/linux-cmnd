package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleAvailAdditionalInfoType", propOrder = {"pricedCoverages", "paymentRules", "tpaExtensions"})
public class VehicleAvailAdditionalInfoType {
   @XmlElement(name = "PricedCoverages")
   protected VehicleAvailAdditionalInfoType.PricedCoverages pricedCoverages;
   @XmlElement(name = "PaymentRules")
   protected PaymentRulesType paymentRules;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "ChargeablePeriod")
   protected Duration chargeablePeriod;

   public VehicleAvailAdditionalInfoType.PricedCoverages getPricedCoverages() {
      return this.pricedCoverages;
   }

   public void setPricedCoverages(VehicleAvailAdditionalInfoType.PricedCoverages value) {
      this.pricedCoverages = value;
   }

   public PaymentRulesType getPaymentRules() {
      return this.paymentRules;
   }

   public void setPaymentRules(PaymentRulesType value) {
      this.paymentRules = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public Duration getChargeablePeriod() {
      return this.chargeablePeriod;
   }

   public void setChargeablePeriod(Duration value) {
      this.chargeablePeriod = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "pricedCoverage")
   public static class PricedCoverages {
      @XmlElement(name = "PricedCoverage", required = true)
      protected List<CoveragePricedType> pricedCoverage;

      public List<CoveragePricedType> getPricedCoverage() {
         if (this.pricedCoverage == null) {
            this.pricedCoverage = new ArrayList<>();
         }

         return this.pricedCoverage;
      }
   }
}
