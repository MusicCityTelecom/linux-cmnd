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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import org.opentravel.ota._2003._05.ContactPersonType;
import org.opentravel.ota._2003._05.DestinationSystemCodesType;
import org.opentravel.ota._2003._05.InvBlockRoomType;
import org.opentravel.ota._2003._05.OTAHotelInvBlockNotifRQ;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.TransactionActionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="InvBlockType", propOrder={"hotelRef", "invBlockDates", "roomTypes", "methodInfo", "blockDescriptions", "contacts", "destinationSystemCodes"})
@XmlSeeAlso(value={OTAHotelInvBlockNotifRQ.InvBlocks.InvBlock.class})
public class InvBlockType {
    @XmlElement(name="HotelRef")
    protected HotelRef hotelRef;
    @XmlElement(name="InvBlockDates")
    protected InvBlockDates invBlockDates;
    @XmlElement(name="RoomTypes")
    protected RoomTypes roomTypes;
    @XmlElement(name="MethodInfo")
    protected MethodInfo methodInfo;
    @XmlElement(name="BlockDescriptions")
    protected BlockDescriptions blockDescriptions;
    @XmlElement(name="Contacts")
    protected Contacts contacts;
    @XmlElement(name="DestinationSystemCodes")
    protected DestinationSystemCodesType destinationSystemCodes;
    @XmlAttribute(name="BookingStatus")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String bookingStatus;
    @XmlAttribute(name="InvBlockTypeCode")
    protected String invBlockTypeCode;
    @XmlAttribute(name="InvBlockCode")
    protected String invBlockCode;
    @XmlAttribute(name="InvBlockGroupingCode")
    protected String invBlockGroupingCode;
    @XmlAttribute(name="InvBlockName")
    protected String invBlockName;
    @XmlAttribute(name="InvBlockLongName")
    protected String invBlockLongName;
    @XmlAttribute(name="InvBlockStatusCode")
    protected String invBlockStatusCode;
    @XmlAttribute(name="PMS_InvBlockID")
    protected String pmsInvBlockID;
    @XmlAttribute(name="OpportunityID")
    protected String opportunityID;
    @XmlAttribute(name="InvBlockCompanyID")
    protected String invBlockCompanyID;
    @XmlAttribute(name="RestrictedBookingCodeList")
    protected List<String> restrictedBookingCodeList;
    @XmlAttribute(name="RestrictedViewingCodeList")
    protected List<String> restrictedViewingCodeList;
    @XmlAttribute(name="TransactionAction")
    protected TransactionActionType transactionAction;
    @XmlAttribute(name="TransactionDetail")
    protected String transactionDetail;
    @XmlAttribute(name="QuoteID")
    protected String quoteID;

    public HotelRef getHotelRef() {
        return this.hotelRef;
    }

    public void setHotelRef(HotelRef value) {
        this.hotelRef = value;
    }

    public InvBlockDates getInvBlockDates() {
        return this.invBlockDates;
    }

    public void setInvBlockDates(InvBlockDates value) {
        this.invBlockDates = value;
    }

    public RoomTypes getRoomTypes() {
        return this.roomTypes;
    }

    public void setRoomTypes(RoomTypes value) {
        this.roomTypes = value;
    }

    public MethodInfo getMethodInfo() {
        return this.methodInfo;
    }

    public void setMethodInfo(MethodInfo value) {
        this.methodInfo = value;
    }

    public BlockDescriptions getBlockDescriptions() {
        return this.blockDescriptions;
    }

    public void setBlockDescriptions(BlockDescriptions value) {
        this.blockDescriptions = value;
    }

    public Contacts getContacts() {
        return this.contacts;
    }

    public void setContacts(Contacts value) {
        this.contacts = value;
    }

    public DestinationSystemCodesType getDestinationSystemCodes() {
        return this.destinationSystemCodes;
    }

    public void setDestinationSystemCodes(DestinationSystemCodesType value) {
        this.destinationSystemCodes = value;
    }

    public String getBookingStatus() {
        return this.bookingStatus;
    }

    public void setBookingStatus(String value) {
        this.bookingStatus = value;
    }

    public String getInvBlockTypeCode() {
        return this.invBlockTypeCode;
    }

    public void setInvBlockTypeCode(String value) {
        this.invBlockTypeCode = value;
    }

    public String getInvBlockCode() {
        return this.invBlockCode;
    }

    public void setInvBlockCode(String value) {
        this.invBlockCode = value;
    }

    public String getInvBlockGroupingCode() {
        return this.invBlockGroupingCode;
    }

    public void setInvBlockGroupingCode(String value) {
        this.invBlockGroupingCode = value;
    }

    public String getInvBlockName() {
        return this.invBlockName;
    }

    public void setInvBlockName(String value) {
        this.invBlockName = value;
    }

    public String getInvBlockLongName() {
        return this.invBlockLongName;
    }

    public void setInvBlockLongName(String value) {
        this.invBlockLongName = value;
    }

    public String getInvBlockStatusCode() {
        return this.invBlockStatusCode;
    }

    public void setInvBlockStatusCode(String value) {
        this.invBlockStatusCode = value;
    }

    public String getPMSInvBlockID() {
        return this.pmsInvBlockID;
    }

    public void setPMSInvBlockID(String value) {
        this.pmsInvBlockID = value;
    }

    public String getOpportunityID() {
        return this.opportunityID;
    }

    public void setOpportunityID(String value) {
        this.opportunityID = value;
    }

    public String getInvBlockCompanyID() {
        return this.invBlockCompanyID;
    }

    public void setInvBlockCompanyID(String value) {
        this.invBlockCompanyID = value;
    }

    public List<String> getRestrictedBookingCodeList() {
        if (this.restrictedBookingCodeList == null) {
            this.restrictedBookingCodeList = new ArrayList<String>();
        }
        return this.restrictedBookingCodeList;
    }

    public List<String> getRestrictedViewingCodeList() {
        if (this.restrictedViewingCodeList == null) {
            this.restrictedViewingCodeList = new ArrayList<String>();
        }
        return this.restrictedViewingCodeList;
    }

    public TransactionActionType getTransactionAction() {
        return this.transactionAction;
    }

    public void setTransactionAction(TransactionActionType value) {
        this.transactionAction = value;
    }

    public String getTransactionDetail() {
        return this.transactionDetail;
    }

    public void setTransactionDetail(String value) {
        this.transactionDetail = value;
    }

    public String getQuoteID() {
        return this.quoteID;
    }

    public void setQuoteID(String value) {
        this.quoteID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"roomType"})
    public static class RoomTypes {
        @XmlElement(name="RoomType", required=true)
        protected List<InvBlockRoomType> roomType;

        public List<InvBlockRoomType> getRoomType() {
            if (this.roomType == null) {
                this.roomType = new ArrayList<InvBlockRoomType>();
            }
            return this.roomType;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class MethodInfo {
        @XmlAttribute(name="ReservationMethodCode")
        protected String reservationMethodCode;
        @XmlAttribute(name="BillingType")
        protected String billingType;
        @XmlAttribute(name="SignFoodAndBev")
        protected Boolean signFoodAndBev;

        public String getReservationMethodCode() {
            return this.reservationMethodCode;
        }

        public void setReservationMethodCode(String value) {
            this.reservationMethodCode = value;
        }

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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class InvBlockDates {
        @XmlAttribute(name="EndDateExtensionIndicator")
        protected Boolean endDateExtensionIndicator;
        @XmlAttribute(name="AbsoluteCutoff")
        protected String absoluteCutoff;
        @XmlAttribute(name="OffsetDuration")
        protected Duration offsetDuration;
        @XmlAttribute(name="OffsetCalculationMode")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String offsetCalculationMode;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public Boolean isEndDateExtensionIndicator() {
            return this.endDateExtensionIndicator;
        }

        public void setEndDateExtensionIndicator(Boolean value) {
            this.endDateExtensionIndicator = value;
        }

        public String getAbsoluteCutoff() {
            return this.absoluteCutoff;
        }

        public void setAbsoluteCutoff(String value) {
            this.absoluteCutoff = value;
        }

        public Duration getOffsetDuration() {
            return this.offsetDuration;
        }

        public void setOffsetDuration(Duration value) {
            this.offsetDuration = value;
        }

        public String getOffsetCalculationMode() {
            return this.offsetCalculationMode;
        }

        public void setOffsetCalculationMode(String value) {
            this.offsetCalculationMode = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class HotelRef {
        @XmlAttribute(name="ChainCode")
        protected String chainCode;
        @XmlAttribute(name="BrandCode")
        protected String brandCode;
        @XmlAttribute(name="HotelCode")
        protected String hotelCode;
        @XmlAttribute(name="HotelCityCode")
        protected String hotelCityCode;
        @XmlAttribute(name="HotelName")
        protected String hotelName;
        @XmlAttribute(name="HotelCodeContext")
        protected String hotelCodeContext;
        @XmlAttribute(name="ChainName")
        protected String chainName;
        @XmlAttribute(name="BrandName")
        protected String brandName;
        @XmlAttribute(name="AreaID")
        protected String areaID;

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
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"contact"})
    public static class Contacts {
        @XmlElement(name="Contact", required=true)
        protected List<ContactPersonType> contact;

        public List<ContactPersonType> getContact() {
            if (this.contact == null) {
                this.contact = new ArrayList<ContactPersonType>();
            }
            return this.contact;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"blockDescription"})
    public static class BlockDescriptions {
        @XmlElement(name="BlockDescription", required=true)
        protected List<BlockDescription> blockDescription;

        public List<BlockDescription> getBlockDescription() {
            if (this.blockDescription == null) {
                this.blockDescription = new ArrayList<BlockDescription>();
            }
            return this.blockDescription;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class BlockDescription
        extends ParagraphType {
            @XmlAttribute(name="Start")
            protected String start;
            @XmlAttribute(name="Duration")
            protected String duration;
            @XmlAttribute(name="End")
            protected String end;

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
    }
}

