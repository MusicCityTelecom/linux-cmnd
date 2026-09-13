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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PTCFareBreakdownType",
   propOrder = {"passengerTypeQuantity", "fareBasisCodes", "passengerFare", "travelerRefNumber", "ticketDesignators", "endorsements", "fareInfo", "pricingUnit"}
)
public class PTCFareBreakdownType {
   @XmlElement(name = "PassengerTypeQuantity", required = true)
   protected PassengerTypeQuantityType passengerTypeQuantity;
   @XmlElement(name = "FareBasisCodes")
   protected PTCFareBreakdownType.FareBasisCodes fareBasisCodes;
   @XmlElement(name = "PassengerFare")
   protected List<PTCFareBreakdownType.PassengerFare> passengerFare;
   @XmlElement(name = "TravelerRefNumber")
   protected List<PTCFareBreakdownType.TravelerRefNumber> travelerRefNumber;
   @XmlElement(name = "TicketDesignators")
   protected PTCFareBreakdownType.TicketDesignators ticketDesignators;
   @XmlElement(name = "Endorsements")
   protected PTCFareBreakdownType.Endorsements endorsements;
   @XmlElement(name = "FareInfo")
   protected List<PTCFareBreakdownType.FareInfo> fareInfo;
   @XmlElement(name = "PricingUnit")
   protected List<PTCFareBreakdownType.PricingUnit> pricingUnit;
   @XmlAttribute(name = "PricingSource")
   protected PricingSourceType pricingSource;
   @XmlAttribute(name = "FlightRefNumberRPHList")
   protected List<String> flightRefNumberRPHList;

   public PassengerTypeQuantityType getPassengerTypeQuantity() {
      return this.passengerTypeQuantity;
   }

   public void setPassengerTypeQuantity(PassengerTypeQuantityType value) {
      this.passengerTypeQuantity = value;
   }

   public PTCFareBreakdownType.FareBasisCodes getFareBasisCodes() {
      return this.fareBasisCodes;
   }

   public void setFareBasisCodes(PTCFareBreakdownType.FareBasisCodes value) {
      this.fareBasisCodes = value;
   }

   public List<PTCFareBreakdownType.PassengerFare> getPassengerFare() {
      if (this.passengerFare == null) {
         this.passengerFare = new ArrayList<>();
      }

      return this.passengerFare;
   }

   public List<PTCFareBreakdownType.TravelerRefNumber> getTravelerRefNumber() {
      if (this.travelerRefNumber == null) {
         this.travelerRefNumber = new ArrayList<>();
      }

      return this.travelerRefNumber;
   }

   public PTCFareBreakdownType.TicketDesignators getTicketDesignators() {
      return this.ticketDesignators;
   }

   public void setTicketDesignators(PTCFareBreakdownType.TicketDesignators value) {
      this.ticketDesignators = value;
   }

   public PTCFareBreakdownType.Endorsements getEndorsements() {
      return this.endorsements;
   }

   public void setEndorsements(PTCFareBreakdownType.Endorsements value) {
      this.endorsements = value;
   }

   public List<PTCFareBreakdownType.FareInfo> getFareInfo() {
      if (this.fareInfo == null) {
         this.fareInfo = new ArrayList<>();
      }

      return this.fareInfo;
   }

   public List<PTCFareBreakdownType.PricingUnit> getPricingUnit() {
      if (this.pricingUnit == null) {
         this.pricingUnit = new ArrayList<>();
      }

      return this.pricingUnit;
   }

   public PricingSourceType getPricingSource() {
      return this.pricingSource;
   }

   public void setPricingSource(PricingSourceType value) {
      this.pricingSource = value;
   }

   public List<String> getFlightRefNumberRPHList() {
      if (this.flightRefNumberRPHList == null) {
         this.flightRefNumberRPHList = new ArrayList<>();
      }

      return this.flightRefNumberRPHList;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "endorsement")
   public static class Endorsements {
      @XmlElement(name = "Endorsement", required = true)
      protected List<PTCFareBreakdownType.Endorsements.Endorsement> endorsement;
      @XmlAttribute(name = "NonRefundableIndicator")
      protected Boolean nonRefundableIndicator;
      @XmlAttribute(name = "NonEndorsableIndicator")
      protected Boolean nonEndorsableIndicator;

      public List<PTCFareBreakdownType.Endorsements.Endorsement> getEndorsement() {
         if (this.endorsement == null) {
            this.endorsement = new ArrayList<>();
         }

         return this.endorsement;
      }

      public Boolean isNonRefundableIndicator() {
         return this.nonRefundableIndicator;
      }

      public void setNonRefundableIndicator(Boolean value) {
         this.nonRefundableIndicator = value;
      }

      public Boolean isNonEndorsableIndicator() {
         return this.nonEndorsableIndicator;
      }

      public void setNonEndorsableIndicator(Boolean value) {
         this.nonEndorsableIndicator = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Endorsement extends FreeTextType {
         @XmlAttribute(name = "Operation")
         protected ActionType operation;

         public ActionType getOperation() {
            return this.operation;
         }

         public void setOperation(ActionType value) {
            this.operation = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "fareBasisCode")
   public static class FareBasisCodes {
      @XmlElement(name = "FareBasisCode", required = true)
      protected List<FareBasisCodeType> fareBasisCode;

      public List<FareBasisCodeType> getFareBasisCode() {
         if (this.fareBasisCode == null) {
            this.fareBasisCode = new ArrayList<>();
         }

         return this.fareBasisCode;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "passengerFare")
   public static class FareInfo extends FareInfoType {
      @XmlElement(name = "PassengerFare", required = true)
      protected FareType passengerFare;

      public FareType getPassengerFare() {
         return this.passengerFare;
      }

      public void setPassengerFare(FareType value) {
         this.passengerFare = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "ticketFeeDetail")
   public static class PassengerFare extends FareType {
      @XmlElement(name = "TicketFeeDetail")
      protected PTCFareBreakdownType.PassengerFare.TicketFeeDetail ticketFeeDetail;
      @XmlAttribute(name = "Usage")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String usage;

      public PTCFareBreakdownType.PassengerFare.TicketFeeDetail getTicketFeeDetail() {
         return this.ticketFeeDetail;
      }

      public void setTicketFeeDetail(PTCFareBreakdownType.PassengerFare.TicketFeeDetail value) {
         this.ticketFeeDetail = value;
      }

      public String getUsage() {
         return this.usage;
      }

      public void setUsage(String value) {
         this.usage = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"fee", "total"})
      public static class TicketFeeDetail {
         @XmlElement(name = "Fee", required = true)
         protected List<PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee> fee;
         @XmlElement(name = "Total")
         protected List<PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Total> total;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public List<PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee> getFee() {
            if (this.fee == null) {
               this.fee = new ArrayList<>();
            }

            return this.fee;
         }

         public List<PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Total> getTotal() {
            if (this.total == null) {
               this.total = new ArrayList<>();
            }

            return this.total;
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = {"baseFee", "taxes", "total"})
         public static class Fee {
            @XmlElement(name = "BaseFee")
            protected PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.BaseFee baseFee;
            @XmlElement(name = "Taxes")
            protected PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.Taxes taxes;
            @XmlElement(name = "Total")
            protected PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.Total total;
            @XmlAttribute(name = "FeeCode", required = true)
            protected String feeCode;
            @XmlAttribute(name = "Description")
            protected String description;

            public PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.BaseFee getBaseFee() {
               return this.baseFee;
            }

            public void setBaseFee(PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.BaseFee value) {
               this.baseFee = value;
            }

            public PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.Taxes getTaxes() {
               return this.taxes;
            }

            public void setTaxes(PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.Taxes value) {
               this.taxes = value;
            }

            public PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.Total getTotal() {
               return this.total;
            }

            public void setTotal(PTCFareBreakdownType.PassengerFare.TicketFeeDetail.Fee.Total value) {
               this.total = value;
            }

            public String getFeeCode() {
               return this.feeCode;
            }

            public void setFeeCode(String value) {
               this.feeCode = value;
            }

            public String getDescription() {
               return this.description;
            }

            public void setDescription(String value) {
               this.description = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class BaseFee {
               @XmlAttribute(name = "Amount", required = true)
               protected BigDecimal amount;

               public BigDecimal getAmount() {
                  return this.amount;
               }

               public void setAmount(BigDecimal value) {
                  this.amount = value;
               }
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = "tax")
            public static class Taxes {
               @XmlElement(name = "Tax", required = true)
               protected List<AirTaxType> tax;
               @XmlAttribute(name = "Amount")
               protected BigDecimal amount;

               public List<AirTaxType> getTax() {
                  if (this.tax == null) {
                     this.tax = new ArrayList<>();
                  }

                  return this.tax;
               }

               public BigDecimal getAmount() {
                  return this.amount;
               }

               public void setAmount(BigDecimal value) {
                  this.amount = value;
               }
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Total {
               @XmlAttribute(name = "Amount", required = true)
               protected BigDecimal amount;

               public BigDecimal getAmount() {
                  return this.amount;
               }

               public void setAmount(BigDecimal value) {
                  this.amount = value;
               }
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Total {
            @XmlAttribute(name = "Type", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String type;
            @XmlAttribute(name = "Amount", required = true)
            protected BigDecimal amount;

            public String getType() {
               return this.type;
            }

            public void setType(String value) {
               this.type = value;
            }

            public BigDecimal getAmount() {
               return this.amount;
            }

            public void setAmount(BigDecimal value) {
               this.amount = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "fareComponent")
   public static class PricingUnit {
      @XmlElement(name = "FareComponent", required = true)
      protected List<PTCFareBreakdownType.PricingUnit.FareComponent> fareComponent;
      @XmlAttribute(name = "UnitNumber", required = true)
      protected int unitNumber;

      public List<PTCFareBreakdownType.PricingUnit.FareComponent> getFareComponent() {
         if (this.fareComponent == null) {
            this.fareComponent = new ArrayList<>();
         }

         return this.fareComponent;
      }

      public int getUnitNumber() {
         return this.unitNumber;
      }

      public void setUnitNumber(int value) {
         this.unitNumber = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "flightLeg")
      public static class FareComponent {
         @XmlElement(name = "FlightLeg", required = true)
         protected List<PTCFareBreakdownType.PricingUnit.FareComponent.FlightLeg> flightLeg;
         @XmlAttribute(name = "Number", required = true)
         protected int number;
         @XmlAttribute(name = "Amount")
         protected BigDecimal amount;
         @XmlAttribute(name = "CurrencyCode")
         protected String currencyCode;
         @XmlAttribute(name = "DecimalPlaces")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger decimalPlaces;

         public List<PTCFareBreakdownType.PricingUnit.FareComponent.FlightLeg> getFlightLeg() {
            if (this.flightLeg == null) {
               this.flightLeg = new ArrayList<>();
            }

            return this.flightLeg;
         }

         public int getNumber() {
            return this.number;
         }

         public void setNumber(int value) {
            this.number = value;
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

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class FlightLeg extends BookFlightSegmentType {
            @XmlAttribute(name = "SurchargeInd")
            protected Boolean surchargeInd;
            @XmlAttribute(name = "FareBasisCode")
            protected String fareBasisCode;
            @XmlAttribute(name = "UnitOfMeasureQuantity")
            protected BigDecimal unitOfMeasureQuantity;
            @XmlAttribute(name = "UnitOfMeasure")
            protected String unitOfMeasure;
            @XmlAttribute(name = "UnitOfMeasureCode")
            protected String unitOfMeasureCode;

            public Boolean isSurchargeInd() {
               return this.surchargeInd;
            }

            public void setSurchargeInd(Boolean value) {
               this.surchargeInd = value;
            }

            public String getFareBasisCode() {
               return this.fareBasisCode;
            }

            public void setFareBasisCode(String value) {
               this.fareBasisCode = value;
            }

            public BigDecimal getUnitOfMeasureQuantity() {
               return this.unitOfMeasureQuantity;
            }

            public void setUnitOfMeasureQuantity(BigDecimal value) {
               this.unitOfMeasureQuantity = value;
            }

            public String getUnitOfMeasure() {
               return this.unitOfMeasure;
            }

            public void setUnitOfMeasure(String value) {
               this.unitOfMeasure = value;
            }

            public String getUnitOfMeasureCode() {
               return this.unitOfMeasureCode;
            }

            public void setUnitOfMeasureCode(String value) {
               this.unitOfMeasureCode = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "ticketDesignator")
   public static class TicketDesignators {
      @XmlElement(name = "TicketDesignator", required = true)
      protected List<PTCFareBreakdownType.TicketDesignators.TicketDesignator> ticketDesignator;

      public List<PTCFareBreakdownType.TicketDesignators.TicketDesignator> getTicketDesignator() {
         if (this.ticketDesignator == null) {
            this.ticketDesignator = new ArrayList<>();
         }

         return this.ticketDesignator;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class TicketDesignator {
         @XmlAttribute(name = "FlightRefRPH")
         protected String flightRefRPH;
         @XmlAttribute(name = "TicketDesignatorCode")
         protected String ticketDesignatorCode;
         @XmlAttribute(name = "TicketDesignatorExtension")
         protected String ticketDesignatorExtension;

         public String getFlightRefRPH() {
            return this.flightRefRPH;
         }

         public void setFlightRefRPH(String value) {
            this.flightRefRPH = value;
         }

         public String getTicketDesignatorCode() {
            return this.ticketDesignatorCode;
         }

         public void setTicketDesignatorCode(String value) {
            this.ticketDesignatorCode = value;
         }

         public String getTicketDesignatorExtension() {
            return this.ticketDesignatorExtension;
         }

         public void setTicketDesignatorExtension(String value) {
            this.ticketDesignatorExtension = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TravelerRefNumber {
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "SurnameRefNumber")
      protected String surnameRefNumber;

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }

      public String getSurnameRefNumber() {
         return this.surnameRefNumber;
      }

      public void setSurnameRefNumber(String value) {
         this.surnameRefNumber = value;
      }
   }
}
