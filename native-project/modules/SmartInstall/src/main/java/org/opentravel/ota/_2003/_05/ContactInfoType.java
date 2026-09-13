package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfoType", propOrder = {"names", "addresses", "phones", "emails", "urLs", "companyName"})
@XmlSeeAlso(
   {AreaInfoType.Attractions.Attraction.Contact.class, ContactInfoRootType.class, HotelInfoType.OwnershipManagementInfos.OwnershipManagementInfo.class}
)
public class ContactInfoType {
   @XmlElement(name = "Names")
   protected ContactsType names;
   @XmlElement(name = "Addresses")
   protected AddressesType addresses;
   @XmlElement(name = "Phones")
   protected PhonesType phones;
   @XmlElement(name = "Emails")
   protected EmailsType emails;
   @XmlElement(name = "URLs")
   protected URLsType urLs;
   @XmlElement(name = "CompanyName")
   protected ContactInfoType.CompanyName companyName;
   @XmlAttribute(name = "Location")
   protected String location;

   public ContactsType getNames() {
      return this.names;
   }

   public void setNames(ContactsType value) {
      this.names = value;
   }

   public AddressesType getAddresses() {
      return this.addresses;
   }

   public void setAddresses(AddressesType value) {
      this.addresses = value;
   }

   public PhonesType getPhones() {
      return this.phones;
   }

   public void setPhones(PhonesType value) {
      this.phones = value;
   }

   public EmailsType getEmails() {
      return this.emails;
   }

   public void setEmails(EmailsType value) {
      this.emails = value;
   }

   public URLsType getURLs() {
      return this.urLs;
   }

   public void setURLs(URLsType value) {
      this.urLs = value;
   }

   public ContactInfoType.CompanyName getCompanyName() {
      return this.companyName;
   }

   public void setCompanyName(ContactInfoType.CompanyName value) {
      this.companyName = value;
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String value) {
      this.location = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class CompanyName extends CompanyNameType {
      @XmlAttribute(name = "ID")
      protected String id;

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }
   }
}
