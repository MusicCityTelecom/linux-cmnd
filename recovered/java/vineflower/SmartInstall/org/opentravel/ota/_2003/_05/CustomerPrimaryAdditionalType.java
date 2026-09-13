package org.opentravel.ota._2003._05;

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
@XmlType(name = "CustomerPrimaryAdditionalType", propOrder = {"primary", "additional"})
public class CustomerPrimaryAdditionalType {
   @XmlElement(name = "Primary", required = true)
   protected CustomerPrimaryAdditionalType.Primary primary;
   @XmlElement(name = "Additional")
   protected List<CustomerPrimaryAdditionalType.Additional> additional;

   public CustomerPrimaryAdditionalType.Primary getPrimary() {
      return this.primary;
   }

   public void setPrimary(CustomerPrimaryAdditionalType.Primary value) {
      this.primary = value;
   }

   public List<CustomerPrimaryAdditionalType.Additional> getAdditional() {
      if (this.additional == null) {
         this.additional = new ArrayList<>();
      }

      return this.additional;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Additional extends CustomerType {
      @XmlAttribute(name = "CorpDiscountName")
      protected String corpDiscountName;
      @XmlAttribute(name = "CorpDiscountNmbr")
      protected String corpDiscountNmbr;
      @XmlAttribute(name = "QualificationMethod")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String qualificationMethod;
      @XmlAttribute(name = "Age")
      protected Integer age;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;
      @XmlAttribute(name = "URI")
      @XmlSchemaType(name = "anyURI")
      protected String uri;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public String getCorpDiscountName() {
         return this.corpDiscountName;
      }

      public void setCorpDiscountName(String value) {
         this.corpDiscountName = value;
      }

      public String getCorpDiscountNmbr() {
         return this.corpDiscountNmbr;
      }

      public void setCorpDiscountNmbr(String value) {
         this.corpDiscountNmbr = value;
      }

      public String getQualificationMethod() {
         return this.qualificationMethod;
      }

      public void setQualificationMethod(String value) {
         this.qualificationMethod = value;
      }

      public Integer getAge() {
         return this.age;
      }

      public void setAge(Integer value) {
         this.age = value;
      }

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getCodeContext() {
         return this.codeContext;
      }

      public void setCodeContext(String value) {
         this.codeContext = value;
      }

      public String getURI() {
         return this.uri;
      }

      public void setURI(String value) {
         this.uri = value;
      }

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }

      public String getStart() {
         return this.start;
      }

      public void setStart(String value) {
         this.start = value;
      }

      public String getDuration() {
         return this.duration;
      }

      public void setDuration(String value) {
         this.duration = value;
      }

      public String getEnd() {
         return this.end;
      }

      public void setEnd(String value) {
         this.end = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "customerID")
   public static class Primary extends CustomerType {
      @XmlElement(name = "CustomerID")
      protected UniqueIDType customerID;

      public UniqueIDType getCustomerID() {
         return this.customerID;
      }

      public void setCustomerID(UniqueIDType value) {
         this.customerID = value;
      }
   }
}
