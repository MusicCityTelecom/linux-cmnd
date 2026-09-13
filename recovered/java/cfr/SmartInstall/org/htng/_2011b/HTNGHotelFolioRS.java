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
import org.htng._2011b.HTNGRevenueDetailsType;
import org.opentravel.ota._2003._05.BasicPropertyInfoType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.PkgInvoiceDetail;
import org.opentravel.ota._2003._05.ProfileType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.opentravel.ota._2003._05.WarningsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"success", "warnings", "uniqueID", "folios", "errors"})
@XmlRootElement(name="HTNG_HotelFolioRS")
public class HTNGHotelFolioRS {
    @XmlElement(name="Success")
    protected SuccessType success;
    @XmlElement(name="Warnings")
    protected WarningsType warnings;
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;
    @XmlElement(name="Folios")
    protected Folios folios;
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

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public Folios getFolios() {
        return this.folios;
    }

    public void setFolios(Folios value) {
        this.folios = value;
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
    @XmlType(name="", propOrder={"folio"})
    public static class Folios {
        @XmlElement(name="Folio", required=true)
        protected List<Folio> folio;

        public List<Folio> getFolio() {
            if (this.folio == null) {
                this.folio = new ArrayList<Folio>();
            }
            return this.folio;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"basicPropertyInfo", "customerProfile", "payerProfile", "revenueSummary", "revenueDetails"})
        public static class Folio {
            @XmlElement(name="BasicPropertyInfo", required=true)
            protected BasicPropertyInfoType basicPropertyInfo;
            @XmlElement(name="CustomerProfile", required=true)
            protected ProfileType customerProfile;
            @XmlElement(name="PayerProfile", required=true)
            protected ProfileType payerProfile;
            @XmlElement(name="RevenueSummary")
            protected PkgInvoiceDetail revenueSummary;
            @XmlElement(name="RevenueDetails")
            protected HTNGRevenueDetailsType revenueDetails;
            @XmlAttribute(name="FolioID")
            protected String folioID;
            @XmlAttribute(name="FolioGroupingID")
            protected String folioGroupingID;
            @XmlAttribute(name="FolioType")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            protected String folioType;
            @XmlAttribute(name="InvoiceID")
            protected String invoiceID;
            @XmlAttribute(name="GuestViewable")
            protected Boolean guestViewable;
            @XmlAttribute(name="GuestPayable")
            protected Boolean guestPayable;

            public BasicPropertyInfoType getBasicPropertyInfo() {
                return this.basicPropertyInfo;
            }

            public void setBasicPropertyInfo(BasicPropertyInfoType value) {
                this.basicPropertyInfo = value;
            }

            public ProfileType getCustomerProfile() {
                return this.customerProfile;
            }

            public void setCustomerProfile(ProfileType value) {
                this.customerProfile = value;
            }

            public ProfileType getPayerProfile() {
                return this.payerProfile;
            }

            public void setPayerProfile(ProfileType value) {
                this.payerProfile = value;
            }

            public PkgInvoiceDetail getRevenueSummary() {
                return this.revenueSummary;
            }

            public void setRevenueSummary(PkgInvoiceDetail value) {
                this.revenueSummary = value;
            }

            public HTNGRevenueDetailsType getRevenueDetails() {
                return this.revenueDetails;
            }

            public void setRevenueDetails(HTNGRevenueDetailsType value) {
                this.revenueDetails = value;
            }

            public String getFolioID() {
                return this.folioID;
            }

            public void setFolioID(String value) {
                this.folioID = value;
            }

            public String getFolioGroupingID() {
                return this.folioGroupingID;
            }

            public void setFolioGroupingID(String value) {
                this.folioGroupingID = value;
            }

            public String getFolioType() {
                return this.folioType;
            }

            public void setFolioType(String value) {
                this.folioType = value;
            }

            public String getInvoiceID() {
                return this.invoiceID;
            }

            public void setInvoiceID(String value) {
                this.invoiceID = value;
            }

            public Boolean isGuestViewable() {
                return this.guestViewable;
            }

            public void setGuestViewable(Boolean value) {
                this.guestViewable = value;
            }

            public Boolean isGuestPayable() {
                return this.guestPayable;
            }

            public void setGuestPayable(Boolean value) {
                this.guestPayable = value;
            }
        }
    }
}

