/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.opentravel.ota._2003._05.AvailRequestSegmentsType;
import org.opentravel.ota._2003._05.HotelReservationIDsType;
import org.opentravel.ota._2003._05.POSType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"pos", "availRequestSegments", "hotelReservationIDs"})
@XmlRootElement(name="OTA_HotelAvailRQ")
public class OTAHotelAvailRQ {
    @XmlElement(name="POS")
    protected POSType pos;
    @XmlElement(name="AvailRequestSegments", required=true)
    protected AvailRequestSegments availRequestSegments;
    @XmlElement(name="HotelReservationIDs")
    protected HotelReservationIDsType hotelReservationIDs;
    @XmlAttribute(name="SummaryOnly")
    protected Boolean summaryOnly;
    @XmlAttribute(name="SortOrder")
    protected String sortOrder;
    @XmlAttribute(name="AvailRatesOnly")
    protected Boolean availRatesOnly;
    @XmlAttribute(name="OnRequestInd")
    protected Boolean onRequestInd;
    @XmlAttribute(name="BestOnly")
    protected Boolean bestOnly;
    @XmlAttribute(name="RateRangeOnly")
    protected Boolean rateRangeOnly;
    @XmlAttribute(name="ExactMatchOnly")
    protected Boolean exactMatchOnly;
    @XmlAttribute(name="AllowPartialAvail")
    protected Boolean allowPartialAvail;
    @XmlAttribute(name="RequestedCurrency")
    protected String requestedCurrency;
    @XmlAttribute(name="RequestedCurrencyIndicator")
    protected Boolean requestedCurrencyIndicator;
    @XmlAttribute(name="IsModify")
    protected Boolean isModify;
    @XmlAttribute(name="SearchCacheLevel")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String searchCacheLevel;
    @XmlAttribute(name="HotelStayOnly")
    protected Boolean hotelStayOnly;
    @XmlAttribute(name="RateDetailsInd")
    protected Boolean rateDetailsInd;
    @XmlAttribute(name="DuplicateInd")
    protected Boolean duplicateInd;
    @XmlAttribute(name="PricingMethod")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String pricingMethod;
    @XmlAttribute(name="MapRequired")
    protected Boolean mapRequired;
    @XmlAttribute(name="MapHeight")
    protected Integer mapHeight;
    @XmlAttribute(name="MapWidth")
    protected Integer mapWidth;
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

    public AvailRequestSegments getAvailRequestSegments() {
        return this.availRequestSegments;
    }

    public void setAvailRequestSegments(AvailRequestSegments value) {
        this.availRequestSegments = value;
    }

    public HotelReservationIDsType getHotelReservationIDs() {
        return this.hotelReservationIDs;
    }

    public void setHotelReservationIDs(HotelReservationIDsType value) {
        this.hotelReservationIDs = value;
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

    public Boolean isAvailRatesOnly() {
        return this.availRatesOnly;
    }

    public void setAvailRatesOnly(Boolean value) {
        this.availRatesOnly = value;
    }

    public Boolean isOnRequestInd() {
        return this.onRequestInd;
    }

    public void setOnRequestInd(Boolean value) {
        this.onRequestInd = value;
    }

    public Boolean isBestOnly() {
        return this.bestOnly;
    }

    public void setBestOnly(Boolean value) {
        this.bestOnly = value;
    }

    public Boolean isRateRangeOnly() {
        return this.rateRangeOnly;
    }

    public void setRateRangeOnly(Boolean value) {
        this.rateRangeOnly = value;
    }

    public Boolean isExactMatchOnly() {
        return this.exactMatchOnly;
    }

    public void setExactMatchOnly(Boolean value) {
        this.exactMatchOnly = value;
    }

    public Boolean isAllowPartialAvail() {
        return this.allowPartialAvail;
    }

    public void setAllowPartialAvail(Boolean value) {
        this.allowPartialAvail = value;
    }

    public String getRequestedCurrency() {
        return this.requestedCurrency;
    }

    public void setRequestedCurrency(String value) {
        this.requestedCurrency = value;
    }

    public Boolean isRequestedCurrencyIndicator() {
        return this.requestedCurrencyIndicator;
    }

    public void setRequestedCurrencyIndicator(Boolean value) {
        this.requestedCurrencyIndicator = value;
    }

    public Boolean isIsModify() {
        return this.isModify;
    }

    public void setIsModify(Boolean value) {
        this.isModify = value;
    }

    public String getSearchCacheLevel() {
        return this.searchCacheLevel;
    }

    public void setSearchCacheLevel(String value) {
        this.searchCacheLevel = value;
    }

    public Boolean isHotelStayOnly() {
        return this.hotelStayOnly;
    }

    public void setHotelStayOnly(Boolean value) {
        this.hotelStayOnly = value;
    }

    public Boolean isRateDetailsInd() {
        return this.rateDetailsInd;
    }

    public void setRateDetailsInd(Boolean value) {
        this.rateDetailsInd = value;
    }

    public Boolean isDuplicateInd() {
        return this.duplicateInd;
    }

    public void setDuplicateInd(Boolean value) {
        this.duplicateInd = value;
    }

    public String getPricingMethod() {
        return this.pricingMethod;
    }

    public void setPricingMethod(String value) {
        this.pricingMethod = value;
    }

    public Boolean isMapRequired() {
        return this.mapRequired;
    }

    public void setMapRequired(Boolean value) {
        this.mapRequired = value;
    }

    public Integer getMapHeight() {
        return this.mapHeight;
    }

    public void setMapHeight(Integer value) {
        this.mapHeight = value;
    }

    public Integer getMapWidth() {
        return this.mapWidth;
    }

    public void setMapWidth(Integer value) {
        this.mapWidth = value;
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
    @XmlType(name="")
    public static class AvailRequestSegments
    extends AvailRequestSegmentsType {
        @XmlAttribute(name="MaximumWaitTime")
        protected BigDecimal maximumWaitTime;

        public BigDecimal getMaximumWaitTime() {
            return this.maximumWaitTime;
        }

        public void setMaximumWaitTime(BigDecimal value) {
            this.maximumWaitTime = value;
        }
    }
}

