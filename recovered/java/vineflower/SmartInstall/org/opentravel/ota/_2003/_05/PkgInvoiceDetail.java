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
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PkgInvoiceDetail",
   propOrder = {"costingItems", "grossAmount", "depositAmount", "agentCommission", "netAmount", "taxItems", "balanceDueAmount", "amountReceived"}
)
public class PkgInvoiceDetail {
   @XmlElement(name = "CostingItems")
   protected PkgInvoiceDetail.CostingItems costingItems;
   @XmlElement(name = "GrossAmount")
   protected PkgInvoiceDetail.GrossAmount grossAmount;
   @XmlElement(name = "DepositAmount")
   protected PkgInvoiceDetail.DepositAmount depositAmount;
   @XmlElement(name = "AgentCommission")
   protected List<PkgInvoiceDetail.AgentCommission> agentCommission;
   @XmlElement(name = "NetAmount")
   protected PkgInvoiceDetail.NetAmount netAmount;
   @XmlElement(name = "TaxItems")
   protected TaxesType taxItems;
   @XmlElement(name = "BalanceDueAmount")
   protected PkgInvoiceDetail.BalanceDueAmount balanceDueAmount;
   @XmlElement(name = "AmountReceived")
   protected PkgInvoiceDetail.AmountReceived amountReceived;

   public PkgInvoiceDetail.CostingItems getCostingItems() {
      return this.costingItems;
   }

   public void setCostingItems(PkgInvoiceDetail.CostingItems value) {
      this.costingItems = value;
   }

   public PkgInvoiceDetail.GrossAmount getGrossAmount() {
      return this.grossAmount;
   }

   public void setGrossAmount(PkgInvoiceDetail.GrossAmount value) {
      this.grossAmount = value;
   }

   public PkgInvoiceDetail.DepositAmount getDepositAmount() {
      return this.depositAmount;
   }

   public void setDepositAmount(PkgInvoiceDetail.DepositAmount value) {
      this.depositAmount = value;
   }

   public List<PkgInvoiceDetail.AgentCommission> getAgentCommission() {
      if (this.agentCommission == null) {
         this.agentCommission = new ArrayList<>();
      }

      return this.agentCommission;
   }

   public PkgInvoiceDetail.NetAmount getNetAmount() {
      return this.netAmount;
   }

   public void setNetAmount(PkgInvoiceDetail.NetAmount value) {
      this.netAmount = value;
   }

   public TaxesType getTaxItems() {
      return this.taxItems;
   }

   public void setTaxItems(TaxesType value) {
      this.taxItems = value;
   }

   public PkgInvoiceDetail.BalanceDueAmount getBalanceDueAmount() {
      return this.balanceDueAmount;
   }

   public void setBalanceDueAmount(PkgInvoiceDetail.BalanceDueAmount value) {
      this.balanceDueAmount = value;
   }

   public PkgInvoiceDetail.AmountReceived getAmountReceived() {
      return this.amountReceived;
   }

   public void setAmountReceived(PkgInvoiceDetail.AmountReceived value) {
      this.amountReceived = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class AgentCommission {
      @XmlAttribute(name = "Description")
      protected String description;
      @XmlAttribute(name = "Rate")
      protected BigDecimal rate;
      @XmlAttribute(name = "CommissionType")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String commissionType;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String value) {
         this.description = value;
      }

      public BigDecimal getRate() {
         return this.rate;
      }

      public void setRate(BigDecimal value) {
         this.rate = value;
      }

      public String getCommissionType() {
         return this.commissionType;
      }

      public void setCommissionType(String value) {
         this.commissionType = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class AmountReceived {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class BalanceDueAmount {
      @XmlAttribute(name = "PaymentDueDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar paymentDueDate;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public XMLGregorianCalendar getPaymentDueDate() {
         return this.paymentDueDate;
      }

      public void setPaymentDueDate(XMLGregorianCalendar value) {
         this.paymentDueDate = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "costingItem")
   public static class CostingItems {
      @XmlElement(name = "CostingItem", required = true)
      protected List<CostingItemType> costingItem;

      public List<CostingItemType> getCostingItem() {
         if (this.costingItem == null) {
            this.costingItem = new ArrayList<>();
         }

         return this.costingItem;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class DepositAmount {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class GrossAmount {
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class NetAmount {
      @XmlAttribute(name = "PaymentDueDate")
      @XmlSchemaType(name = "date")
      protected XMLGregorianCalendar paymentDueDate;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public XMLGregorianCalendar getPaymentDueDate() {
         return this.paymentDueDate;
      }

      public void setPaymentDueDate(XMLGregorianCalendar value) {
         this.paymentDueDate = value;
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
