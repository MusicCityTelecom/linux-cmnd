/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

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
import org.htng._2011b.HTNGEventFiltersType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.WarningsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"success", "warnings", "subscriptions", "errors"})
@XmlRootElement(name="HTNG_SubscriptionStatusRS")
public class HTNGSubscriptionStatusRS {
    @XmlElement(name="Success")
    protected SuccessType success;
    @XmlElement(name="Warnings")
    protected WarningsType warnings;
    @XmlElement(name="Subscriptions")
    protected Subscriptions subscriptions;
    @XmlElement(name="Errors")
    protected ErrorsType errors;
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

    public Subscriptions getSubscriptions() {
        return this.subscriptions;
    }

    public void setSubscriptions(Subscriptions value) {
        this.subscriptions = value;
    }

    public ErrorsType getErrors() {
        return this.errors;
    }

    public void setErrors(ErrorsType value) {
        this.errors = value;
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
    @XmlType(name="", propOrder={"subscription"})
    public static class Subscriptions {
        @XmlElement(name="Subscription", required=true)
        protected List<Subscription> subscription;

        public List<Subscription> getSubscription() {
            if (this.subscription == null) {
                this.subscription = new ArrayList<Subscription>();
            }
            return this.subscription;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"producerEndpoint", "consumerEndpoint", "targetNamespace", "description", "appliedFilters"})
        public static class Subscription {
            @XmlElement(name="ProducerEndpoint", required=true)
            @XmlSchemaType(name="anyURI")
            protected String producerEndpoint;
            @XmlElement(name="ConsumerEndpoint", required=true)
            @XmlSchemaType(name="anyURI")
            protected String consumerEndpoint;
            @XmlElement(name="TargetNamespace", required=true)
            @XmlSchemaType(name="anyURI")
            protected String targetNamespace;
            @XmlElement(name="Description", required=true)
            protected FormattedTextTextType description;
            @XmlElement(name="AppliedFilters")
            protected HTNGEventFiltersType appliedFilters;
            @XmlAttribute(name="ConsumerSubscriptionID")
            protected String consumerSubscriptionID;
            @XmlAttribute(name="ProducerEventID")
            protected String producerEventID;
            @XmlAttribute(name="LastUpdatedDateTime")
            @XmlSchemaType(name="dateTime")
            protected XMLGregorianCalendar lastUpdatedDateTime;
            @XmlAttribute(name="TerminationDateTime")
            @XmlSchemaType(name="dateTime")
            protected XMLGregorianCalendar terminationDateTime;
            @XmlAttribute(name="MessageEventType")
            protected String messageEventType;
            @XmlAttribute(name="ProducerReason")
            protected String producerReason;
            @XmlAttribute(name="VendorVersionID")
            protected Float vendorVersionID;
            @XmlAttribute(name="VendorProfileID")
            protected String vendorProfileID;

            public String getProducerEndpoint() {
                return this.producerEndpoint;
            }

            public void setProducerEndpoint(String value) {
                this.producerEndpoint = value;
            }

            public String getConsumerEndpoint() {
                return this.consumerEndpoint;
            }

            public void setConsumerEndpoint(String value) {
                this.consumerEndpoint = value;
            }

            public String getTargetNamespace() {
                return this.targetNamespace;
            }

            public void setTargetNamespace(String value) {
                this.targetNamespace = value;
            }

            public FormattedTextTextType getDescription() {
                return this.description;
            }

            public void setDescription(FormattedTextTextType value) {
                this.description = value;
            }

            public HTNGEventFiltersType getAppliedFilters() {
                return this.appliedFilters;
            }

            public void setAppliedFilters(HTNGEventFiltersType value) {
                this.appliedFilters = value;
            }

            public String getConsumerSubscriptionID() {
                return this.consumerSubscriptionID;
            }

            public void setConsumerSubscriptionID(String value) {
                this.consumerSubscriptionID = value;
            }

            public String getProducerEventID() {
                return this.producerEventID;
            }

            public void setProducerEventID(String value) {
                this.producerEventID = value;
            }

            public XMLGregorianCalendar getLastUpdatedDateTime() {
                return this.lastUpdatedDateTime;
            }

            public void setLastUpdatedDateTime(XMLGregorianCalendar value) {
                this.lastUpdatedDateTime = value;
            }

            public XMLGregorianCalendar getTerminationDateTime() {
                return this.terminationDateTime;
            }

            public void setTerminationDateTime(XMLGregorianCalendar value) {
                this.terminationDateTime = value;
            }

            public String getMessageEventType() {
                return this.messageEventType;
            }

            public void setMessageEventType(String value) {
                this.messageEventType = value;
            }

            public String getProducerReason() {
                return this.producerReason;
            }

            public void setProducerReason(String value) {
                this.producerReason = value;
            }

            public Float getVendorVersionID() {
                return this.vendorVersionID;
            }

            public void setVendorVersionID(Float value) {
                this.vendorVersionID = value;
            }

            public String getVendorProfileID() {
                return this.vendorProfileID;
            }

            public void setVendorProfileID(String value) {
                this.vendorProfileID = value;
            }
        }
    }
}

