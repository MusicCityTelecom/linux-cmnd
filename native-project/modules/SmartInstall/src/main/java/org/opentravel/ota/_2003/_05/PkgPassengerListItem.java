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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PkgPassengerListItem", propOrder = {"name", "specialNeed", "passportInformation"})
public class PkgPassengerListItem {
   @XmlElement(name = "Name")
   protected PersonNameType name;
   @XmlElement(name = "SpecialNeed")
   protected List<PkgPassengerListItem.SpecialNeed> specialNeed;
   @XmlElement(name = "PassportInformation")
   protected DocumentType passportInformation;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "InsuranceRPH")
   protected String insuranceRPH;
   @XmlAttribute(name = "Nationality")
   protected String nationality;
   @XmlAttribute(name = "LeadCustomerInd")
   protected Boolean leadCustomerInd;
   @XmlAttribute(name = "Gender")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String gender;
   @XmlAttribute(name = "BirthDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar birthDate;
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

   public PersonNameType getName() {
      return this.name;
   }

   public void setName(PersonNameType value) {
      this.name = value;
   }

   public List<PkgPassengerListItem.SpecialNeed> getSpecialNeed() {
      if (this.specialNeed == null) {
         this.specialNeed = new ArrayList<>();
      }

      return this.specialNeed;
   }

   public DocumentType getPassportInformation() {
      return this.passportInformation;
   }

   public void setPassportInformation(DocumentType value) {
      this.passportInformation = value;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getInsuranceRPH() {
      return this.insuranceRPH;
   }

   public void setInsuranceRPH(String value) {
      this.insuranceRPH = value;
   }

   public String getNationality() {
      return this.nationality;
   }

   public void setNationality(String value) {
      this.nationality = value;
   }

   public Boolean isLeadCustomerInd() {
      return this.leadCustomerInd;
   }

   public void setLeadCustomerInd(Boolean value) {
      this.leadCustomerInd = value;
   }

   public String getGender() {
      return this.gender;
   }

   public void setGender(String value) {
      this.gender = value;
   }

   public XMLGregorianCalendar getBirthDate() {
      return this.birthDate;
   }

   public void setBirthDate(XMLGregorianCalendar value) {
      this.birthDate = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class SpecialNeed {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "Code")
      protected String code;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }
   }
}
