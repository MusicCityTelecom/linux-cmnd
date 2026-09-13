package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeaturesType", propOrder = "feature")
public class FeaturesType {
   @XmlElement(name = "Feature", required = true)
   protected List<FeaturesType.Feature> feature;

   public List<FeaturesType.Feature> getFeature() {
      if (this.feature == null) {
         this.feature = new ArrayList<>();
      }

      return this.feature;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"charge", "multimediaDescriptions", "descriptiveText"})
   public static class Feature {
      @XmlElement(name = "Charge")
      protected FeaturesType.Feature.Charge charge;
      @XmlElement(name = "MultimediaDescriptions")
      protected MultimediaDescriptionsType multimediaDescriptions;
      @XmlElement(name = "DescriptiveText")
      protected String descriptiveText;
      @XmlAttribute(name = "AccessibleCode")
      protected String accessibleCode;
      @XmlAttribute(name = "SecurityCode")
      protected String securityCode;
      @XmlAttribute(name = "ExistsCode")
      protected String existsCode;
      @XmlAttribute(name = "ProximityCode")
      protected String proximityCode;
      @XmlAttribute(name = "ID")
      protected String id;
      @XmlAttribute(name = "CodeDetail")
      protected String codeDetail;
      @XmlAttribute(name = "Removal")
      protected Boolean removal;

      public FeaturesType.Feature.Charge getCharge() {
         return this.charge;
      }

      public void setCharge(FeaturesType.Feature.Charge value) {
         this.charge = value;
      }

      public MultimediaDescriptionsType getMultimediaDescriptions() {
         return this.multimediaDescriptions;
      }

      public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
         this.multimediaDescriptions = value;
      }

      public String getDescriptiveText() {
         return this.descriptiveText;
      }

      public void setDescriptiveText(String value) {
         this.descriptiveText = value;
      }

      public String getAccessibleCode() {
         return this.accessibleCode;
      }

      public void setAccessibleCode(String value) {
         this.accessibleCode = value;
      }

      public String getSecurityCode() {
         return this.securityCode;
      }

      public void setSecurityCode(String value) {
         this.securityCode = value;
      }

      public String getExistsCode() {
         return this.existsCode;
      }

      public void setExistsCode(String value) {
         this.existsCode = value;
      }

      public String getProximityCode() {
         return this.proximityCode;
      }

      public void setProximityCode(String value) {
         this.proximityCode = value;
      }

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }

      public String getCodeDetail() {
         return this.codeDetail;
      }

      public void setCodeDetail(String value) {
         this.codeDetail = value;
      }

      public Boolean isRemoval() {
         return this.removal;
      }

      public void setRemoval(Boolean value) {
         this.removal = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Charge {
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public BigDecimal getAmount() {
            return this.amount;
         }

         public void setAmount(BigDecimal value) {
            this.amount = value;
         }

         public String getCurrencyCode() {
            return this.currencyCode;
         }

         public void setCurrencyCode(String value) {
            this.currencyCode = value;
         }

         public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
         }

         public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
         }
      }
   }
}
