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
@XmlType(name = "HotelRoomListType", propOrder = {"uniqueID", "guests", "masterContact", "masterAccount", "roomStays", "event"})
public class HotelRoomListType {
   @XmlElement(name = "UniqueID")
   protected UniqueIDType uniqueID;
   @XmlElement(name = "Guests")
   protected HotelRoomListType.Guests guests;
   @XmlElement(name = "MasterContact")
   protected HotelRoomListType.MasterContact masterContact;
   @XmlElement(name = "MasterAccount")
   protected HotelRoomListType.MasterAccount masterAccount;
   @XmlElement(name = "RoomStays")
   protected HotelRoomListType.RoomStays roomStays;
   @XmlElement(name = "Event")
   protected HotelRoomListType.Event event;
   @XmlAttribute(name = "GroupBlockCode")
   protected String groupBlockCode;
   @XmlAttribute(name = "CreationDate")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar creationDate;
   @XmlAttribute(name = "ChainCode")
   protected String chainCode;
   @XmlAttribute(name = "BrandCode")
   protected String brandCode;
   @XmlAttribute(name = "HotelCode")
   protected String hotelCode;
   @XmlAttribute(name = "HotelCityCode")
   protected String hotelCityCode;
   @XmlAttribute(name = "HotelName")
   protected String hotelName;
   @XmlAttribute(name = "HotelCodeContext")
   protected String hotelCodeContext;
   @XmlAttribute(name = "ChainName")
   protected String chainName;
   @XmlAttribute(name = "BrandName")
   protected String brandName;
   @XmlAttribute(name = "AreaID")
   protected String areaID;

   public UniqueIDType getUniqueID() {
      return this.uniqueID;
   }

   public void setUniqueID(UniqueIDType value) {
      this.uniqueID = value;
   }

   public HotelRoomListType.Guests getGuests() {
      return this.guests;
   }

   public void setGuests(HotelRoomListType.Guests value) {
      this.guests = value;
   }

   public HotelRoomListType.MasterContact getMasterContact() {
      return this.masterContact;
   }

   public void setMasterContact(HotelRoomListType.MasterContact value) {
      this.masterContact = value;
   }

   public HotelRoomListType.MasterAccount getMasterAccount() {
      return this.masterAccount;
   }

   public void setMasterAccount(HotelRoomListType.MasterAccount value) {
      this.masterAccount = value;
   }

   public HotelRoomListType.RoomStays getRoomStays() {
      return this.roomStays;
   }

   public void setRoomStays(HotelRoomListType.RoomStays value) {
      this.roomStays = value;
   }

   public HotelRoomListType.Event getEvent() {
      return this.event;
   }

   public void setEvent(HotelRoomListType.Event value) {
      this.event = value;
   }

   public String getGroupBlockCode() {
      return this.groupBlockCode;
   }

   public void setGroupBlockCode(String value) {
      this.groupBlockCode = value;
   }

   public XMLGregorianCalendar getCreationDate() {
      return this.creationDate;
   }

   public void setCreationDate(XMLGregorianCalendar value) {
      this.creationDate = value;
   }

   public String getChainCode() {
      return this.chainCode;
   }

   public void setChainCode(String value) {
      this.chainCode = value;
   }

   public String getBrandCode() {
      return this.brandCode;
   }

   public void setBrandCode(String value) {
      this.brandCode = value;
   }

   public String getHotelCode() {
      return this.hotelCode;
   }

   public void setHotelCode(String value) {
      this.hotelCode = value;
   }

   public String getHotelCityCode() {
      return this.hotelCityCode;
   }

   public void setHotelCityCode(String value) {
      this.hotelCityCode = value;
   }

   public String getHotelName() {
      return this.hotelName;
   }

   public void setHotelName(String value) {
      this.hotelName = value;
   }

   public String getHotelCodeContext() {
      return this.hotelCodeContext;
   }

   public void setHotelCodeContext(String value) {
      this.hotelCodeContext = value;
   }

   public String getChainName() {
      return this.chainName;
   }

   public void setChainName(String value) {
      this.chainName = value;
   }

   public String getBrandName() {
      return this.brandName;
   }

   public void setBrandName(String value) {
      this.brandName = value;
   }

   public String getAreaID() {
      return this.areaID;
   }

   public void setAreaID(String value) {
      this.areaID = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "eventContact")
   public static class Event {
      @XmlElement(name = "EventContact", required = true)
      protected ContactPersonType eventContact;
      @XmlAttribute(name = "MeetingName")
      protected String meetingName;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;

      public ContactPersonType getEventContact() {
         return this.eventContact;
      }

      public void setEventContact(ContactPersonType value) {
         this.eventContact = value;
      }

      public String getMeetingName() {
         return this.meetingName;
      }

      public void setMeetingName(String value) {
         this.meetingName = value;
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
   @XmlType(name = "", propOrder = "guest")
   public static class Guests {
      @XmlElement(name = "Guest", required = true)
      protected List<HotelRoomListType.Guests.Guest> guest;

      public List<HotelRoomListType.Guests.Guest> getGuest() {
         if (this.guest == null) {
            this.guest = new ArrayList<>();
         }

         return this.guest;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"uniqueID", "loyalty", "guaranteePayment", "additionalDetails"})
      public static class Guest extends ContactPersonType {
         @XmlElement(name = "UniqueID")
         protected UniqueIDType uniqueID;
         @XmlElement(name = "Loyalty")
         protected List<HotelRoomListType.Guests.Guest.Loyalty> loyalty;
         @XmlElement(name = "GuaranteePayment")
         protected List<HotelRoomListType.Guests.Guest.GuaranteePayment> guaranteePayment;
         @XmlElement(name = "AdditionalDetails")
         protected AdditionalDetailsType additionalDetails;
         @XmlAttribute(name = "GuestAction")
         protected ActionType guestAction;
         @XmlAttribute(name = "PrintConfoInd")
         protected Boolean printConfoInd;

         public UniqueIDType getUniqueID() {
            return this.uniqueID;
         }

         public void setUniqueID(UniqueIDType value) {
            this.uniqueID = value;
         }

         public List<HotelRoomListType.Guests.Guest.Loyalty> getLoyalty() {
            if (this.loyalty == null) {
               this.loyalty = new ArrayList<>();
            }

            return this.loyalty;
         }

         public List<HotelRoomListType.Guests.Guest.GuaranteePayment> getGuaranteePayment() {
            if (this.guaranteePayment == null) {
               this.guaranteePayment = new ArrayList<>();
            }

            return this.guaranteePayment;
         }

         public AdditionalDetailsType getAdditionalDetails() {
            return this.additionalDetails;
         }

         public void setAdditionalDetails(AdditionalDetailsType value) {
            this.additionalDetails = value;
         }

         public ActionType getGuestAction() {
            return this.guestAction;
         }

         public void setGuestAction(ActionType value) {
            this.guestAction = value;
         }

         public Boolean isPrintConfoInd() {
            return this.printConfoInd;
         }

         public void setPrintConfoInd(Boolean value) {
            this.printConfoInd = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class GuaranteePayment extends HotelPaymentFormType {
            @XmlAttribute(name = "DetailType")
            protected String detailType;
            @XmlAttribute(name = "GuaranteeType")
            protected String guaranteeType;

            public String getDetailType() {
               return this.detailType;
            }

            public void setDetailType(String value) {
               this.detailType = value;
            }

            public String getGuaranteeType() {
               return this.guaranteeType;
            }

            public void setGuaranteeType(String value) {
               this.guaranteeType = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class Loyalty {
            @XmlAttribute(name = "ReservationActionType")
            protected String reservationActionType;
            @XmlAttribute(name = "SelectedLoyaltyRPH")
            protected String selectedLoyaltyRPH;
            @XmlAttribute(name = "ProgramCode")
            protected String programCode;
            @XmlAttribute(name = "BonusCode")
            protected String bonusCode;
            @XmlAttribute(name = "AccountID")
            protected String accountID;
            @XmlAttribute(name = "PointsEarned")
            protected String pointsEarned;

            public String getReservationActionType() {
               return this.reservationActionType;
            }

            public void setReservationActionType(String value) {
               this.reservationActionType = value;
            }

            public String getSelectedLoyaltyRPH() {
               return this.selectedLoyaltyRPH;
            }

            public void setSelectedLoyaltyRPH(String value) {
               this.selectedLoyaltyRPH = value;
            }

            public String getProgramCode() {
               return this.programCode;
            }

            public void setProgramCode(String value) {
               this.programCode = value;
            }

            public String getBonusCode() {
               return this.bonusCode;
            }

            public void setBonusCode(String value) {
               this.bonusCode = value;
            }

            public String getAccountID() {
               return this.accountID;
            }

            public void setAccountID(String value) {
               this.accountID = value;
            }

            public String getPointsEarned() {
               return this.pointsEarned;
            }

            public void setPointsEarned(String value) {
               this.pointsEarned = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MasterAccount extends DirectBillType {
      @XmlAttribute(name = "BillingType")
      protected String billingType;
      @XmlAttribute(name = "SignFoodAndBev")
      protected Boolean signFoodAndBev;

      public String getBillingType() {
         return this.billingType;
      }

      public void setBillingType(String value) {
         this.billingType = value;
      }

      public Boolean isSignFoodAndBev() {
         return this.signFoodAndBev;
      }

      public void setSignFoodAndBev(Boolean value) {
         this.signFoodAndBev = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"uniqueIDs", "loyalty"})
   public static class MasterContact extends ContactPersonType {
      @XmlElement(name = "UniqueIDs")
      protected HotelRoomListType.MasterContact.UniqueIDs uniqueIDs;
      @XmlElement(name = "Loyalty")
      protected List<HotelRoomListType.MasterContact.Loyalty> loyalty;

      public HotelRoomListType.MasterContact.UniqueIDs getUniqueIDs() {
         return this.uniqueIDs;
      }

      public void setUniqueIDs(HotelRoomListType.MasterContact.UniqueIDs value) {
         this.uniqueIDs = value;
      }

      public List<HotelRoomListType.MasterContact.Loyalty> getLoyalty() {
         if (this.loyalty == null) {
            this.loyalty = new ArrayList<>();
         }

         return this.loyalty;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class Loyalty {
         @XmlAttribute(name = "ReservationActionType")
         protected String reservationActionType;
         @XmlAttribute(name = "SelectedLoyaltyRPH")
         protected String selectedLoyaltyRPH;
         @XmlAttribute(name = "ProgramCode")
         protected String programCode;
         @XmlAttribute(name = "BonusCode")
         protected String bonusCode;
         @XmlAttribute(name = "AccountID")
         protected String accountID;
         @XmlAttribute(name = "PointsEarned")
         protected String pointsEarned;

         public String getReservationActionType() {
            return this.reservationActionType;
         }

         public void setReservationActionType(String value) {
            this.reservationActionType = value;
         }

         public String getSelectedLoyaltyRPH() {
            return this.selectedLoyaltyRPH;
         }

         public void setSelectedLoyaltyRPH(String value) {
            this.selectedLoyaltyRPH = value;
         }

         public String getProgramCode() {
            return this.programCode;
         }

         public void setProgramCode(String value) {
            this.programCode = value;
         }

         public String getBonusCode() {
            return this.bonusCode;
         }

         public void setBonusCode(String value) {
            this.bonusCode = value;
         }

         public String getAccountID() {
            return this.accountID;
         }

         public void setAccountID(String value) {
            this.accountID = value;
         }

         public String getPointsEarned() {
            return this.pointsEarned;
         }

         public void setPointsEarned(String value) {
            this.pointsEarned = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "uniqueID")
      public static class UniqueIDs {
         @XmlElement(name = "UniqueID")
         protected List<UniqueIDType> uniqueID;

         public List<UniqueIDType> getUniqueID() {
            if (this.uniqueID == null) {
               this.uniqueID = new ArrayList<>();
            }

            return this.uniqueID;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "roomStay")
   public static class RoomStays {
      @XmlElement(name = "RoomStay", required = true)
      protected List<HotelRoomListType.RoomStays.RoomStay> roomStay;

      public List<HotelRoomListType.RoomStays.RoomStay> getRoomStay() {
         if (this.roomStay == null) {
            this.roomStay = new ArrayList<>();
         }

         return this.roomStay;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"hotelReservationIDs", "roomShares", "uniqueID", "success", "warnings", "errors"})
      public static class RoomStay extends RoomStayType {
         @XmlElement(name = "HotelReservationIDs")
         protected HotelReservationIDsType hotelReservationIDs;
         @XmlElement(name = "RoomShares")
         protected RoomSharesType roomShares;
         @XmlElement(name = "UniqueID")
         protected UniqueIDType uniqueID;
         @XmlElement(name = "Success")
         protected SuccessType success;
         @XmlElement(name = "Warnings")
         protected WarningsType warnings;
         @XmlElement(name = "Errors")
         protected ErrorsType errors;
         @XmlAttribute(name = "RoomStay")
         protected ActionType roomStay;

         public HotelReservationIDsType getHotelReservationIDs() {
            return this.hotelReservationIDs;
         }

         public void setHotelReservationIDs(HotelReservationIDsType value) {
            this.hotelReservationIDs = value;
         }

         public RoomSharesType getRoomShares() {
            return this.roomShares;
         }

         public void setRoomShares(RoomSharesType value) {
            this.roomShares = value;
         }

         public UniqueIDType getUniqueID() {
            return this.uniqueID;
         }

         public void setUniqueID(UniqueIDType value) {
            this.uniqueID = value;
         }

         public SuccessType getSuccess() {
            return this.success;
         }

         public void setSuccess(SuccessType value) {
            this.success = value;
         }

         public WarningsType getWarnings() {
            return this.warnings;
         }

         public void setWarnings(WarningsType value) {
            this.warnings = value;
         }

         public ErrorsType getErrors() {
            return this.errors;
         }

         public void setErrors(ErrorsType value) {
            this.errors = value;
         }

         public ActionType getRoomStay() {
            return this.roomStay;
         }

         public void setRoomStay(ActionType value) {
            this.roomStay = value;
         }
      }
   }
}
