package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestType", propOrder = {"guestName", "guestTransportation"})
@XmlSeeAlso(CruiseBookingInfoType.GuestPrices.GuestPrice.class)
public class GuestType {
   @XmlElement(name = "GuestName")
   protected PersonNameType guestName;
   @XmlElement(name = "GuestTransportation")
   protected List<GuestTransportationType> guestTransportation;
   @XmlAttribute(name = "GuestRefNumber")
   protected String guestRefNumber;
   @XmlAttribute(name = "Age")
   protected Integer age;
   @XmlAttribute(name = "Nationality")
   protected String nationality;
   @XmlAttribute(name = "GuestOccupation")
   protected String guestOccupation;
   @XmlAttribute(name = "BirthDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar birthDate;
   @XmlAttribute(name = "Gender")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String gender;
   @XmlAttribute(name = "LoyaltyMembershipID")
   protected String loyaltyMembershipID;
   @XmlAttribute(name = "LoyalLevel")
   protected String loyalLevel;
   @XmlAttribute(name = "LoyalLevelCode")
   protected Integer loyalLevelCode;

   public PersonNameType getGuestName() {
      return this.guestName;
   }

   public void setGuestName(PersonNameType value) {
      this.guestName = value;
   }

   public List<GuestTransportationType> getGuestTransportation() {
      if (this.guestTransportation == null) {
         this.guestTransportation = new ArrayList<>();
      }

      return this.guestTransportation;
   }

   public String getGuestRefNumber() {
      return this.guestRefNumber;
   }

   public void setGuestRefNumber(String value) {
      this.guestRefNumber = value;
   }

   public Integer getAge() {
      return this.age;
   }

   public void setAge(Integer value) {
      this.age = value;
   }

   public String getNationality() {
      return this.nationality;
   }

   public void setNationality(String value) {
      this.nationality = value;
   }

   public String getGuestOccupation() {
      return this.guestOccupation;
   }

   public void setGuestOccupation(String value) {
      this.guestOccupation = value;
   }

   public XMLGregorianCalendar getBirthDate() {
      return this.birthDate;
   }

   public void setBirthDate(XMLGregorianCalendar value) {
      this.birthDate = value;
   }

   public String getGender() {
      return this.gender;
   }

   public void setGender(String value) {
      this.gender = value;
   }

   public String getLoyaltyMembershipID() {
      return this.loyaltyMembershipID;
   }

   public void setLoyaltyMembershipID(String value) {
      this.loyaltyMembershipID = value;
   }

   public String getLoyalLevel() {
      return this.loyalLevel;
   }

   public void setLoyalLevel(String value) {
      this.loyalLevel = value;
   }

   public Integer getLoyalLevelCode() {
      return this.loyalLevelCode;
   }

   public void setLoyalLevelCode(Integer value) {
      this.loyalLevelCode = value;
   }
}
