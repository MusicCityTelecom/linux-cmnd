/*
 * Decompiled with CFR 0.152.
 */
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
import javax.xml.datatype.XMLGregorianCalendar;
import org.htng._2011b.HTNGHotelCommissionPaymentRQ;
import org.opentravel.ota._2003._05.HotelResModifyType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.OTANotifReportRQ;
import org.opentravel.ota._2003._05.POSType;
import org.opentravel.ota._2003._05.ResGlobalInfoType;
import org.opentravel.ota._2003._05.ResGuestsType;
import org.opentravel.ota._2003._05.RoomStaysType;
import org.opentravel.ota._2003._05.ServicesType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.opentravel.ota._2003._05.WrittenConfInstType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelReservationType", propOrder={"pos", "uniqueID", "roomStays", "services", "billingInstructionCode", "resGuests", "resGlobalInfo", "writtenConfInst", "queue", "tpaExtensions"})
@XmlSeeAlso(value={HotelReservationsType.HotelReservation.class, HTNGHotelCommissionPaymentRQ.CommissionRecipients.CommissionRecipient.CommissionableReservations.CommissionableReservation.class, OTANotifReportRQ.NotifDetails.HotelNotifReport.HotelReservations.HotelReservation.class, HotelResModifyType.HotelResModify.class})
public class HotelReservationType {
    @XmlElement(name="POS")
    protected POSType pos;
    @XmlElement(name="UniqueID")
    protected List<UniqueIDType> uniqueID;
    @XmlElement(name="RoomStays")
    protected RoomStaysType roomStays;
    @XmlElement(name="Services")
    protected ServicesType services;
    @XmlElement(name="BillingInstructionCode")
    protected List<BillingInstructionCode> billingInstructionCode;
    @XmlElement(name="ResGuests")
    protected ResGuestsType resGuests;
    @XmlElement(name="ResGlobalInfo")
    protected ResGlobalInfoType resGlobalInfo;
    @XmlElement(name="WrittenConfInst")
    protected WrittenConfInstType writtenConfInst;
    @XmlElement(name="Queue")
    protected Queue queue;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="RoomStayReservation")
    protected Boolean roomStayReservation;
    @XmlAttribute(name="ResStatus")
    protected String resStatus;
    @XmlAttribute(name="ForcedSellIndicator")
    protected Boolean forcedSellIndicator;
    @XmlAttribute(name="ServiceOverrideIndicator")
    protected Boolean serviceOverrideIndicator;
    @XmlAttribute(name="RateOverrideIndicator")
    protected Boolean rateOverrideIndicator;
    @XmlAttribute(name="WalkInIndicator")
    protected Boolean walkInIndicator;
    @XmlAttribute(name="RoomNumberLockedIndicator")
    protected Boolean roomNumberLockedIndicator;
    @XmlAttribute(name="OriginalDeliveryMethodCode")
    protected String originalDeliveryMethodCode;
    @XmlAttribute(name="PassiveIndicator")
    protected Boolean passiveIndicator;
    @XmlAttribute(name="CreateDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar createDateTime;
    @XmlAttribute(name="CreatorID")
    protected String creatorID;
    @XmlAttribute(name="LastModifyDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar lastModifyDateTime;
    @XmlAttribute(name="LastModifierID")
    protected String lastModifierID;
    @XmlAttribute(name="PurgeDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar purgeDate;

    public POSType getPOS() {
        return this.pos;
    }

    public void setPOS(POSType value) {
        this.pos = value;
    }

    public List<UniqueIDType> getUniqueID() {
        if (this.uniqueID == null) {
            this.uniqueID = new ArrayList<UniqueIDType>();
        }
        return this.uniqueID;
    }

    public RoomStaysType getRoomStays() {
        return this.roomStays;
    }

    public void setRoomStays(RoomStaysType value) {
        this.roomStays = value;
    }

    public ServicesType getServices() {
        return this.services;
    }

    public void setServices(ServicesType value) {
        this.services = value;
    }

    public List<BillingInstructionCode> getBillingInstructionCode() {
        if (this.billingInstructionCode == null) {
            this.billingInstructionCode = new ArrayList<BillingInstructionCode>();
        }
        return this.billingInstructionCode;
    }

    public ResGuestsType getResGuests() {
        return this.resGuests;
    }

    public void setResGuests(ResGuestsType value) {
        this.resGuests = value;
    }

    public ResGlobalInfoType getResGlobalInfo() {
        return this.resGlobalInfo;
    }

    public void setResGlobalInfo(ResGlobalInfoType value) {
        this.resGlobalInfo = value;
    }

    public WrittenConfInstType getWrittenConfInst() {
        return this.writtenConfInst;
    }

    public void setWrittenConfInst(WrittenConfInstType value) {
        this.writtenConfInst = value;
    }

    public Queue getQueue() {
        return this.queue;
    }

    public void setQueue(Queue value) {
        this.queue = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public Boolean isRoomStayReservation() {
        return this.roomStayReservation;
    }

    public void setRoomStayReservation(Boolean value) {
        this.roomStayReservation = value;
    }

    public String getResStatus() {
        return this.resStatus;
    }

    public void setResStatus(String value) {
        this.resStatus = value;
    }

    public Boolean isForcedSellIndicator() {
        return this.forcedSellIndicator;
    }

    public void setForcedSellIndicator(Boolean value) {
        this.forcedSellIndicator = value;
    }

    public Boolean isServiceOverrideIndicator() {
        return this.serviceOverrideIndicator;
    }

    public void setServiceOverrideIndicator(Boolean value) {
        this.serviceOverrideIndicator = value;
    }

    public Boolean isRateOverrideIndicator() {
        return this.rateOverrideIndicator;
    }

    public void setRateOverrideIndicator(Boolean value) {
        this.rateOverrideIndicator = value;
    }

    public Boolean isWalkInIndicator() {
        return this.walkInIndicator;
    }

    public void setWalkInIndicator(Boolean value) {
        this.walkInIndicator = value;
    }

    public Boolean isRoomNumberLockedIndicator() {
        return this.roomNumberLockedIndicator;
    }

    public void setRoomNumberLockedIndicator(Boolean value) {
        this.roomNumberLockedIndicator = value;
    }

    public String getOriginalDeliveryMethodCode() {
        return this.originalDeliveryMethodCode;
    }

    public void setOriginalDeliveryMethodCode(String value) {
        this.originalDeliveryMethodCode = value;
    }

    public Boolean isPassiveIndicator() {
        return this.passiveIndicator;
    }

    public void setPassiveIndicator(Boolean value) {
        this.passiveIndicator = value;
    }

    public XMLGregorianCalendar getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(XMLGregorianCalendar value) {
        this.createDateTime = value;
    }

    public String getCreatorID() {
        return this.creatorID;
    }

    public void setCreatorID(String value) {
        this.creatorID = value;
    }

    public XMLGregorianCalendar getLastModifyDateTime() {
        return this.lastModifyDateTime;
    }

    public void setLastModifyDateTime(XMLGregorianCalendar value) {
        this.lastModifyDateTime = value;
    }

    public String getLastModifierID() {
        return this.lastModifierID;
    }

    public void setLastModifierID(String value) {
        this.lastModifierID = value;
    }

    public XMLGregorianCalendar getPurgeDate() {
        return this.purgeDate;
    }

    public void setPurgeDate(XMLGregorianCalendar value) {
        this.purgeDate = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Queue {
        @XmlAttribute(name="PseudoCityCode")
        protected String pseudoCityCode;
        @XmlAttribute(name="QueueNumber")
        protected String queueNumber;
        @XmlAttribute(name="QueueCategory")
        protected String queueCategory;
        @XmlAttribute(name="SystemCode")
        protected String systemCode;
        @XmlAttribute(name="QueueID")
        protected String queueID;

        public String getPseudoCityCode() {
            return this.pseudoCityCode;
        }

        public void setPseudoCityCode(String value) {
            this.pseudoCityCode = value;
        }

        public String getQueueNumber() {
            return this.queueNumber;
        }

        public void setQueueNumber(String value) {
            this.queueNumber = value;
        }

        public String getQueueCategory() {
            return this.queueCategory;
        }

        public void setQueueCategory(String value) {
            this.queueCategory = value;
        }

        public String getSystemCode() {
            return this.systemCode;
        }

        public void setSystemCode(String value) {
            this.systemCode = value;
        }

        public String getQueueID() {
            return this.queueID;
        }

        public void setQueueID(String value) {
            this.queueID = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"resGuestRPH"})
    public static class BillingInstructionCode {
        @XmlElement(name="ResGuestRPH")
        protected List<ResGuestRPH> resGuestRPH;
        @XmlAttribute(name="BillingCode", required=true)
        protected String billingCode;
        @XmlAttribute(name="BillingType")
        protected String billingType;
        @XmlAttribute(name="AuthorizationCode")
        protected String authorizationCode;
        @XmlAttribute(name="Description")
        protected String description;
        @XmlAttribute(name="AccountNumber")
        protected String accountNumber;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public List<ResGuestRPH> getResGuestRPH() {
            if (this.resGuestRPH == null) {
                this.resGuestRPH = new ArrayList<ResGuestRPH>();
            }
            return this.resGuestRPH;
        }

        public String getBillingCode() {
            return this.billingCode;
        }

        public void setBillingCode(String value) {
            this.billingCode = value;
        }

        public String getBillingType() {
            return this.billingType;
        }

        public void setBillingType(String value) {
            this.billingType = value;
        }

        public String getAuthorizationCode() {
            return this.authorizationCode;
        }

        public void setAuthorizationCode(String value) {
            this.authorizationCode = value;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String value) {
            this.description = value;
        }

        public String getAccountNumber() {
            return this.accountNumber;
        }

        public void setAccountNumber(String value) {
            this.accountNumber = value;
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

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class ResGuestRPH {
            @XmlAttribute(name="RPH", required=true)
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

