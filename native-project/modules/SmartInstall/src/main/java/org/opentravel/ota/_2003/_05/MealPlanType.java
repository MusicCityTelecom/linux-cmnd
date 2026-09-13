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
@XmlType(name = "MealPlanType", propOrder = {"customerCounts", "passengerRPHs", "prices"})
public class MealPlanType {
   @XmlElement(name = "CustomerCounts")
   protected CustomerCountsType customerCounts;
   @XmlElement(name = "PassengerRPHs")
   protected MealPlanType.PassengerRPHs passengerRPHs;
   @XmlElement(name = "Prices")
   protected MealPlanType.Prices prices;
   @XmlAttribute(name = "ListOfRoomRPH")
   protected List<String> listOfRoomRPH;
   @XmlAttribute(name = "Plan")
   protected String plan;
   @XmlAttribute(name = "Code", required = true)
   protected String code;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger quantity;

   public CustomerCountsType getCustomerCounts() {
      return this.customerCounts;
   }

   public void setCustomerCounts(CustomerCountsType value) {
      this.customerCounts = value;
   }

   public MealPlanType.PassengerRPHs getPassengerRPHs() {
      return this.passengerRPHs;
   }

   public void setPassengerRPHs(MealPlanType.PassengerRPHs value) {
      this.passengerRPHs = value;
   }

   public MealPlanType.Prices getPrices() {
      return this.prices;
   }

   public void setPrices(MealPlanType.Prices value) {
      this.prices = value;
   }

   public List<String> getListOfRoomRPH() {
      if (this.listOfRoomRPH == null) {
         this.listOfRoomRPH = new ArrayList<>();
      }

      return this.listOfRoomRPH;
   }

   public String getPlan() {
      return this.plan;
   }

   public void setPlan(String value) {
      this.plan = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class PassengerRPHs {
      @XmlAttribute(name = "ListOfPassengerRPH")
      protected List<String> listOfPassengerRPH;

      public List<String> getListOfPassengerRPH() {
         if (this.listOfPassengerRPH == null) {
            this.listOfPassengerRPH = new ArrayList<>();
         }

         return this.listOfPassengerRPH;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "price")
   public static class Prices {
      @XmlElement(name = "Price", required = true)
      protected List<MealPlanType.Prices.Price> price;

      public List<MealPlanType.Prices.Price> getPrice() {
         if (this.price == null) {
            this.price = new ArrayList<>();
         }

         return this.price;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Price {
         @XmlAttribute(name = "PriceQualifier")
         protected Integer priceQualifier;
         @XmlAttribute(name = "PriceBasis")
         protected PricingType priceBasis;
         @XmlAttribute(name = "AgeQualifyingCode")
         protected String ageQualifyingCode;
         @XmlAttribute(name = "Age")
         protected Integer age;
         @XmlAttribute(name = "Count")
         protected Integer count;
         @XmlAttribute(name = "AgeBucket")
         protected String ageBucket;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public Integer getPriceQualifier() {
            return this.priceQualifier;
         }

         public void setPriceQualifier(Integer value) {
            this.priceQualifier = value;
         }

         public PricingType getPriceBasis() {
            return this.priceBasis;
         }

         public void setPriceBasis(PricingType value) {
            this.priceBasis = value;
         }

         public String getAgeQualifyingCode() {
            return this.ageQualifyingCode;
         }

         public void setAgeQualifyingCode(String value) {
            this.ageQualifyingCode = value;
         }

         public Integer getAge() {
            return this.age;
         }

         public void setAge(Integer value) {
            this.age = value;
         }

         public Integer getCount() {
            return this.count;
         }

         public void setCount(Integer value) {
            this.count = value;
         }

         public String getAgeBucket() {
            return this.ageBucket;
         }

         public void setAgeBucket(String value) {
            this.ageBucket = value;
         }

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
