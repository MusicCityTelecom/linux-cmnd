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
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.InvBlockType;
import org.opentravel.ota._2003._05.POSType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"pos", "hotelInvBlockRequests"})
@XmlRootElement(name="OTA_HotelInvBlockRQ")
public class OTAHotelInvBlockRQ {
    @XmlElement(name="POS")
    protected POSType pos;
    @XmlElement(name="HotelInvBlockRequests", required=true)
    protected HotelInvBlockRequests hotelInvBlockRequests;
    @XmlAttribute(name="SummaryOnly")
    protected Boolean summaryOnly;
    @XmlAttribute(name="SortOrder")
    protected String sortOrder;
    @XmlAttribute(name="RequestedCurrency")
    protected String requestedCurrency;
    @XmlAttribute(name="SearchCacheLevel")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String searchCacheLevel;
    @XmlAttribute(name="MaximumWaitTime")
    protected BigDecimal maximumWaitTime;
    @XmlAttribute(name="MoreDataEchoToken")
    protected String moreDataEchoToken;
    @XmlAttribute(name="InfoSource")
    protected String infoSource;
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
    @XmlAttribute(name="MaxResponses")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger maxResponses;

    public POSType getPOS() {
        return this.pos;
    }

    public void setPOS(POSType value) {
        this.pos = value;
    }

    public HotelInvBlockRequests getHotelInvBlockRequests() {
        return this.hotelInvBlockRequests;
    }

    public void setHotelInvBlockRequests(HotelInvBlockRequests value) {
        this.hotelInvBlockRequests = value;
    }

    public Boolean isSummaryOnly() {
        return this.summaryOnly;
    }

    public void setSummaryOnly(Boolean value) {
        this.summaryOnly = value;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String value) {
        this.sortOrder = value;
    }

    public String getRequestedCurrency() {
        return this.requestedCurrency;
    }

    public void setRequestedCurrency(String value) {
        this.requestedCurrency = value;
    }

    public String getSearchCacheLevel() {
        return this.searchCacheLevel;
    }

    public void setSearchCacheLevel(String value) {
        this.searchCacheLevel = value;
    }

    public BigDecimal getMaximumWaitTime() {
        return this.maximumWaitTime;
    }

    public void setMaximumWaitTime(BigDecimal value) {
        this.maximumWaitTime = value;
    }

    public String getMoreDataEchoToken() {
        return this.moreDataEchoToken;
    }

    public void setMoreDataEchoToken(String value) {
        this.moreDataEchoToken = value;
    }

    public String getInfoSource() {
        return this.infoSource;
    }

    public void setInfoSource(String value) {
        this.infoSource = value;
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

    public BigInteger getMaxResponses() {
        return this.maxResponses;
    }

    public void setMaxResponses(BigInteger value) {
        this.maxResponses = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"hotelInvBlockRequest"})
    public static class HotelInvBlockRequests {
        @XmlElement(name="HotelInvBlockRequest", required=true)
        protected List<HotelInvBlockRequest> hotelInvBlockRequest;

        public List<HotelInvBlockRequest> getHotelInvBlockRequest() {
            if (this.hotelInvBlockRequest == null) {
                this.hotelInvBlockRequest = new ArrayList<HotelInvBlockRequest>();
            }
            return this.hotelInvBlockRequest;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"dateRange", "invBlock", "hotelRef", "tpaExtensions"})
        public static class HotelInvBlockRequest {
            @XmlElement(name="DateRange")
            protected DateRange dateRange;
            @XmlElement(name="InvBlock")
            protected List<InvBlockType> invBlock;
            @XmlElement(name="HotelRef")
            protected HotelRef hotelRef;
            @XmlElement(name="TPA_Extensions")
            protected TPAExtensionsType tpaExtensions;
            @XmlAttribute(name="SendBookingLimit")
            protected Boolean sendBookingLimit;
            @XmlAttribute(name="IncludeAllBlocksAffectingDateRange")
            protected Boolean includeAllBlocksAffectingDateRange;
            @XmlAttribute(name="BookingLimitMessageType")
            protected String bookingLimitMessageType;

            public DateRange getDateRange() {
                return this.dateRange;
            }

            public void setDateRange(DateRange value) {
                this.dateRange = value;
            }

            public List<InvBlockType> getInvBlock() {
                if (this.invBlock == null) {
                    this.invBlock = new ArrayList<InvBlockType>();
                }
                return this.invBlock;
            }

            public HotelRef getHotelRef() {
                return this.hotelRef;
            }

            public void setHotelRef(HotelRef value) {
                this.hotelRef = value;
            }

            public TPAExtensionsType getTPAExtensions() {
                return this.tpaExtensions;
            }

            public void setTPAExtensions(TPAExtensionsType value) {
                this.tpaExtensions = value;
            }

            public Boolean isSendBookingLimit() {
                return this.sendBookingLimit;
            }

            public void setSendBookingLimit(Boolean value) {
                this.sendBookingLimit = value;
            }

            public Boolean isIncludeAllBlocksAffectingDateRange() {
                return this.includeAllBlocksAffectingDateRange;
            }

            public void setIncludeAllBlocksAffectingDateRange(Boolean value) {
                this.includeAllBlocksAffectingDateRange = value;
            }

            public String getBookingLimitMessageType() {
                return this.bookingLimitMessageType;
            }

            public void setBookingLimitMessageType(String value) {
                this.bookingLimitMessageType = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class HotelRef {
                @XmlAttribute(name="SegmentCategoryCode")
                protected String segmentCategoryCode;
                @XmlAttribute(name="PropertyClassCode")
                protected String propertyClassCode;
                @XmlAttribute(name="ArchitecturalStyleCode")
                protected String architecturalStyleCode;
                @XmlAttribute(name="SupplierIntegrationLevel")
                @XmlSchemaType(name="nonNegativeInteger")
                protected BigInteger supplierIntegrationLevel;
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

                public String getSegmentCategoryCode() {
                    return this.segmentCategoryCode;
                }

                public void setSegmentCategoryCode(String value) {
                    this.segmentCategoryCode = value;
                }

                public String getPropertyClassCode() {
                    return this.propertyClassCode;
                }

                public void setPropertyClassCode(String value) {
                    this.propertyClassCode = value;
                }

                public String getArchitecturalStyleCode() {
                    return this.architecturalStyleCode;
                }

                public void setArchitecturalStyleCode(String value) {
                    this.architecturalStyleCode = value;
                }

                public BigInteger getSupplierIntegrationLevel() {
                    return this.supplierIntegrationLevel;
                }

                public void setSupplierIntegrationLevel(BigInteger value) {
                    this.supplierIntegrationLevel = value;
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
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class DateRange {
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
}

