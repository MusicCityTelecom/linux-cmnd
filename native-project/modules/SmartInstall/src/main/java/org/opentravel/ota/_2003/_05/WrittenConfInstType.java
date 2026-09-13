package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WrittenConfInstType", propOrder = {"supplementalData", "email"})
public class WrittenConfInstType {
   @XmlElement(name = "SupplementalData")
   protected ParagraphType supplementalData;
   @XmlElement(name = "Email")
   protected EmailType email;
   @XmlAttribute(name = "LanguageID")
   protected String languageID;
   @XmlAttribute(name = "AddresseeName")
   protected String addresseeName;
   @XmlAttribute(name = "Address")
   protected String address;
   @XmlAttribute(name = "Telephone")
   protected String telephone;
   @XmlAttribute(name = "ConfirmInd")
   protected Boolean confirmInd;

   public ParagraphType getSupplementalData() {
      return this.supplementalData;
   }

   public void setSupplementalData(ParagraphType value) {
      this.supplementalData = value;
   }

   public EmailType getEmail() {
      return this.email;
   }

   public void setEmail(EmailType value) {
      this.email = value;
   }

   public String getLanguageID() {
      return this.languageID;
   }

   public void setLanguageID(String value) {
      this.languageID = value;
   }

   public String getAddresseeName() {
      return this.addresseeName;
   }

   public void setAddresseeName(String value) {
      this.addresseeName = value;
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String value) {
      this.address = value;
   }

   public String getTelephone() {
      return this.telephone;
   }

   public void setTelephone(String value) {
      this.telephone = value;
   }

   public Boolean isConfirmInd() {
      return this.confirmInd;
   }

   public void setConfirmInd(Boolean value) {
      this.confirmInd = value;
   }
}
