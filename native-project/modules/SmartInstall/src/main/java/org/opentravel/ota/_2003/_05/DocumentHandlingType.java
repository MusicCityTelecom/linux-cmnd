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
@XmlType(name = "DocumentHandlingType", propOrder = "vendorOption")
public class DocumentHandlingType {
   @XmlElement(name = "VendorOption")
   protected List<DocumentHandlingType.VendorOption> vendorOption;
   @XmlAttribute(name = "DocumentTypeCode")
   protected String documentTypeCode;
   @XmlAttribute(name = "DeliveryMethodCode")
   protected String deliveryMethodCode;
   @XmlAttribute(name = "DocumentDestination")
   protected String documentDestination;
   @XmlAttribute(name = "SelectedOptionIndicator")
   protected Boolean selectedOptionIndicator;
   @XmlAttribute(name = "DefaultIndicator")
   protected Boolean defaultIndicator;
   @XmlAttribute(name = "AddressRequiredIndicator")
   protected Boolean addressRequiredIndicator;
   @XmlAttribute(name = "AddressRPH")
   protected String addressRPH;
   @XmlAttribute(name = "EmailRPH")
   protected String emailRPH;
   @XmlAttribute(name = "TelephoneRPH")
   protected String telephoneRPH;
   @XmlAttribute(name = "DocumentLanguage")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "language")
   protected String documentLanguage;

   public List<DocumentHandlingType.VendorOption> getVendorOption() {
      if (this.vendorOption == null) {
         this.vendorOption = new ArrayList<>();
      }

      return this.vendorOption;
   }

   public String getDocumentTypeCode() {
      return this.documentTypeCode;
   }

   public void setDocumentTypeCode(String value) {
      this.documentTypeCode = value;
   }

   public String getDeliveryMethodCode() {
      return this.deliveryMethodCode;
   }

   public void setDeliveryMethodCode(String value) {
      this.deliveryMethodCode = value;
   }

   public String getDocumentDestination() {
      return this.documentDestination;
   }

   public void setDocumentDestination(String value) {
      this.documentDestination = value;
   }

   public Boolean isSelectedOptionIndicator() {
      return this.selectedOptionIndicator;
   }

   public void setSelectedOptionIndicator(Boolean value) {
      this.selectedOptionIndicator = value;
   }

   public Boolean isDefaultIndicator() {
      return this.defaultIndicator;
   }

   public void setDefaultIndicator(Boolean value) {
      this.defaultIndicator = value;
   }

   public Boolean isAddressRequiredIndicator() {
      return this.addressRequiredIndicator;
   }

   public void setAddressRequiredIndicator(Boolean value) {
      this.addressRequiredIndicator = value;
   }

   public String getAddressRPH() {
      return this.addressRPH;
   }

   public void setAddressRPH(String value) {
      this.addressRPH = value;
   }

   public String getEmailRPH() {
      return this.emailRPH;
   }

   public void setEmailRPH(String value) {
      this.emailRPH = value;
   }

   public String getTelephoneRPH() {
      return this.telephoneRPH;
   }

   public void setTelephoneRPH(String value) {
      this.telephoneRPH = value;
   }

   public String getDocumentLanguage() {
      return this.documentLanguage;
   }

   public void setDocumentLanguage(String value) {
      this.documentLanguage = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class VendorOption {
      @XmlAttribute(name = "VendorName")
      protected String vendorName;
      @XmlAttribute(name = "Amount")
      protected BigDecimal amount;
      @XmlAttribute(name = "CurrencyCode")
      protected String currencyCode;
      @XmlAttribute(name = "DecimalPlaces")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger decimalPlaces;

      public String getVendorName() {
         return this.vendorName;
      }

      public void setVendorName(String value) {
         this.vendorName = value;
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
