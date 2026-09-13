package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ResGuestType",
   propOrder = {
         "profiles",
         "specialRequests",
         "comments",
         "serviceRPHs",
         "profileRPHs",
         "arrivalTransport",
         "departureTransport",
         "guestCounts",
         "inHouseTimeSpan",
         "tpaExtensions"
   }
)
public class ResGuestType {
   @XmlElement(name = "Profiles")
   protected ProfilesType profiles;
   @XmlElement(name = "SpecialRequests")
   protected SpecialRequestType specialRequests;
   @XmlElement(name = "Comments")
   protected CommentType comments;
   @XmlElement(name = "ServiceRPHs")
   protected ServiceRPHsType serviceRPHs;
   @XmlElement(name = "ProfileRPHs")
   protected ResGuestType.ProfileRPHs profileRPHs;
   @XmlElement(name = "ArrivalTransport")
   protected TransportInfoType arrivalTransport;
   @XmlElement(name = "DepartureTransport")
   protected TransportInfoType departureTransport;
   @XmlElement(name = "GuestCounts")
   protected GuestCountType guestCounts;
   @XmlElement(name = "InHouseTimeSpan")
   protected DateTimeSpanType inHouseTimeSpan;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "ResGuestRPH")
   protected String resGuestRPH;
   @XmlAttribute(name = "AgeQualifyingCode")
   protected String ageQualifyingCode;
   @XmlAttribute(name = "ArrivalTime")
   @XmlSchemaType(name = "time")
   protected XMLGregorianCalendar arrivalTime;
   @XmlAttribute(name = "DepartureTime")
   @XmlSchemaType(name = "time")
   protected XMLGregorianCalendar departureTime;
   @XmlAttribute(name = "GroupEventCode")
   protected String groupEventCode;
   @XmlAttribute(name = "VIP")
   protected Boolean vip;
   @XmlAttribute(name = "PrimaryIndicator")
   protected Boolean primaryIndicator;
   @XmlAttribute(name = "Age")
   protected Integer age;
   @XmlAttribute(name = "Action")
   protected ActionType action;
   @XmlAttribute(name = "LocationCode")
   protected String locationCode;
   @XmlAttribute(name = "CodeContext")
   protected String codeContext;

   public ProfilesType getProfiles() {
      return this.profiles;
   }

   public void setProfiles(ProfilesType value) {
      this.profiles = value;
   }

   public SpecialRequestType getSpecialRequests() {
      return this.specialRequests;
   }

   public void setSpecialRequests(SpecialRequestType value) {
      this.specialRequests = value;
   }

   public CommentType getComments() {
      return this.comments;
   }

   public void setComments(CommentType value) {
      this.comments = value;
   }

   public ServiceRPHsType getServiceRPHs() {
      return this.serviceRPHs;
   }

   public void setServiceRPHs(ServiceRPHsType value) {
      this.serviceRPHs = value;
   }

   public ResGuestType.ProfileRPHs getProfileRPHs() {
      return this.profileRPHs;
   }

   public void setProfileRPHs(ResGuestType.ProfileRPHs value) {
      this.profileRPHs = value;
   }

   public TransportInfoType getArrivalTransport() {
      return this.arrivalTransport;
   }

   public void setArrivalTransport(TransportInfoType value) {
      this.arrivalTransport = value;
   }

   public TransportInfoType getDepartureTransport() {
      return this.departureTransport;
   }

   public void setDepartureTransport(TransportInfoType value) {
      this.departureTransport = value;
   }

   public GuestCountType getGuestCounts() {
      return this.guestCounts;
   }

   public void setGuestCounts(GuestCountType value) {
      this.guestCounts = value;
   }

   public DateTimeSpanType getInHouseTimeSpan() {
      return this.inHouseTimeSpan;
   }

   public void setInHouseTimeSpan(DateTimeSpanType value) {
      this.inHouseTimeSpan = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public String getResGuestRPH() {
      return this.resGuestRPH;
   }

   public void setResGuestRPH(String value) {
      this.resGuestRPH = value;
   }

   public String getAgeQualifyingCode() {
      return this.ageQualifyingCode;
   }

   public void setAgeQualifyingCode(String value) {
      this.ageQualifyingCode = value;
   }

   public XMLGregorianCalendar getArrivalTime() {
      return this.arrivalTime;
   }

   public void setArrivalTime(XMLGregorianCalendar value) {
      this.arrivalTime = value;
   }

   public XMLGregorianCalendar getDepartureTime() {
      return this.departureTime;
   }

   public void setDepartureTime(XMLGregorianCalendar value) {
      this.departureTime = value;
   }

   public String getGroupEventCode() {
      return this.groupEventCode;
   }

   public void setGroupEventCode(String value) {
      this.groupEventCode = value;
   }

   public Boolean isVIP() {
      return this.vip;
   }

   public void setVIP(Boolean value) {
      this.vip = value;
   }

   public Boolean isPrimaryIndicator() {
      return this.primaryIndicator;
   }

   public void setPrimaryIndicator(Boolean value) {
      this.primaryIndicator = value;
   }

   public Integer getAge() {
      return this.age;
   }

   public void setAge(Integer value) {
      this.age = value;
   }

   public ActionType getAction() {
      return this.action;
   }

   public void setAction(ActionType value) {
      this.action = value;
   }

   public String getLocationCode() {
      return this.locationCode;
   }

   public void setLocationCode(String value) {
      this.locationCode = value;
   }

   public String getCodeContext() {
      return this.codeContext;
   }

   public void setCodeContext(String value) {
      this.codeContext = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "profileRPH")
   public static class ProfileRPHs {
      @XmlElement(name = "ProfileRPH", required = true)
      protected List<ResGuestType.ProfileRPHs.ProfileRPH> profileRPH;

      public List<ResGuestType.ProfileRPHs.ProfileRPH> getProfileRPH() {
         if (this.profileRPH == null) {
            this.profileRPH = new ArrayList<>();
         }

         return this.profileRPH;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class ProfileRPH {
         @XmlAttribute(name = "RPH")
         protected String rph;

         public String getRPH() {
            return this.rph;
         }

         public void setRPH(String value) {
            this.rph = value;
         }
      }
   }
}
