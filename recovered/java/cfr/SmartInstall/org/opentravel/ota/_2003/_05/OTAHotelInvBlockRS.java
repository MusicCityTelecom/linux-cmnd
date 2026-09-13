/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ContactPersonType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.InvBlockType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TransactionActionType;
import org.opentravel.ota._2003._05.WarningsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"success", "warnings", "invBlocks", "tpaExtensions", "errors"})
@XmlRootElement(name="OTA_HotelInvBlockRS")
public class OTAHotelInvBlockRS {
    @XmlElement(name="Success")
    protected SuccessType success;
    @XmlElement(name="Warnings")
    protected WarningsType warnings;
    @XmlElement(name="InvBlocks")
    protected InvBlocks invBlocks;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlElement(name="Errors")
    protected ErrorsType errors;
    @XmlAttribute(name="MessageContentCode")
    protected String messageContentCode;
    @XmlAttribute(name="EchoToken")
    protected String echoToken;
    @XmlAttribute(name="TimeStamp")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlAttribute(name="Target")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name="TargetName")
    protected String targetName;
    @XmlAttribute(name="Version", required=true)
    protected BigDecimal version;
    @XmlAttribute(name="TransactionIdentifier")
    protected String transactionIdentifier;
    @XmlAttribute(name="SequenceNmbr")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger sequenceNmbr;
    @XmlAttribute(name="TransactionStatusCode")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String transactionStatusCode;
    @XmlAttribute(name="RetransmissionIndicator")
    protected Boolean retransmissionIndicator;
    @XmlAttribute(name="CorrelationID")
    protected String correlationID;
    @XmlAttribute(name="AltLangID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String altLangID;
    @XmlAttribute(name="PrimaryLangID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String primaryLangID;

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

    public InvBlocks getInvBlocks() {
        return this.invBlocks;
    }

    public void setInvBlocks(InvBlocks value) {
        this.invBlocks = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public ErrorsType getErrors() {
        return this.errors;
    }

    public void setErrors(ErrorsType value) {
        this.errors = value;
    }

    public String getMessageContentCode() {
        return this.messageContentCode;
    }

    public void setMessageContentCode(String value) {
        this.messageContentCode = value;
    }

    public String getEchoToken() {
        return this.echoToken;
    }

    public void setEchoToken(String value) {
        this.echoToken = value;
    }

    public XMLGregorianCalendar getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String value) {
        this.target = value;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public void setTargetName(String value) {
        this.targetName = value;
    }

    public BigDecimal getVersion() {
        return this.version;
    }

    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    public String getTransactionIdentifier() {
        return this.transactionIdentifier;
    }

    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }

    public BigInteger getSequenceNmbr() {
        return this.sequenceNmbr;
    }

    public void setSequenceNmbr(BigInteger value) {
        this.sequenceNmbr = value;
    }

    public String getTransactionStatusCode() {
        return this.transactionStatusCode;
    }

    public void setTransactionStatusCode(String value) {
        this.transactionStatusCode = value;
    }

    public Boolean isRetransmissionIndicator() {
        return this.retransmissionIndicator;
    }

    public void setRetransmissionIndicator(Boolean value) {
        this.retransmissionIndicator = value;
    }

    public String getCorrelationID() {
        return this.correlationID;
    }

    public void setCorrelationID(String value) {
        this.correlationID = value;
    }

    public String getAltLangID() {
        return this.altLangID;
    }

    public void setAltLangID(String value) {
        this.altLangID = value;
    }

    public String getPrimaryLangID() {
        return this.primaryLangID;
    }

    public void setPrimaryLangID(String value) {
        this.primaryLangID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"hotelRef", "invBlockDates", "invBlock", "contacts"})
    public static class InvBlocks {
        @XmlElement(name="HotelRef")
        protected HotelRef hotelRef;
        @XmlElement(name="InvBlockDates")
        protected InvBlockDates invBlockDates;
        @XmlElement(name="InvBlock")
        protected List<InvBlockType> invBlock;
        @XmlElement(name="Contacts")
        protected Contacts contacts;
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

        public List<InvBlockType> getInvBlock() {
            if (this.invBlock == null) {
                this.invBlock = new ArrayList<InvBlockType>();
            }
            return this.invBlock;
        }

        public Contacts getContacts() {
            return this.contacts;
        }

        public void setContacts(Contacts value) {
            this.contacts = value;
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
    }
}

