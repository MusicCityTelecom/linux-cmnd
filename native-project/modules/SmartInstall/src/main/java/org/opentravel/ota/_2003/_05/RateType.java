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
@XmlType(name = "RateType", propOrder = "rate")
public class RateType {
   @XmlElement(name = "Rate", required = true)
   protected List<RateType.Rate> rate;

   public List<RateType.Rate> getRate() {
      if (this.rate == null) {
         this.rate = new ArrayList<>();
      }

      return this.rate;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "tpaExtensions")
   public static class Rate extends AmountType {
      @XmlElement(name = "TPA_Extensions")
      protected TPAExtensionsType tpaExtensions;
      @XmlAttribute(name = "Duration")
      protected Duration duration;
      @XmlAttribute(name = "RateMode")
      protected String rateMode;
      @XmlAttribute(name = "CachedIndicator")
      protected Boolean cachedIndicator;
      @XmlAttribute(name = "RateSource")
      protected String rateSource;
      @XmlAttribute(name = "RateTypeCode")
      protected String rateTypeCode;
      @XmlAttribute(name = "RoomPricingType")
      protected PricingType roomPricingType;

      public TPAExtensionsType getTPAExtensions() {
         return this.tpaExtensions;
      }

      public void setTPAExtensions(TPAExtensionsType value) {
         this.tpaExtensions = value;
      }

      public Duration getDuration() {
         return this.duration;
      }

      public void setDuration(Duration value) {
         this.duration = value;
      }

      public String getRateMode() {
         return this.rateMode;
      }

      public void setRateMode(String value) {
         this.rateMode = value;
      }

      public Boolean isCachedIndicator() {
         return this.cachedIndicator;
      }

      public void setCachedIndicator(Boolean value) {
         this.cachedIndicator = value;
      }

      public String getRateSource() {
         return this.rateSource;
      }

      public void setRateSource(String value) {
         this.rateSource = value;
      }

      public String getRateTypeCode() {
         return this.rateTypeCode;
      }

      public void setRateTypeCode(String value) {
         this.rateTypeCode = value;
      }

      public PricingType getRoomPricingType() {
         return this.roomPricingType;
      }

      public void setRoomPricingType(PricingType value) {
         this.roomPricingType = value;
      }
   }
}
