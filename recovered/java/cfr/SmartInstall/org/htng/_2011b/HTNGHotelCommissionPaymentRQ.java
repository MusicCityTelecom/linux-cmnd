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
import org.opentravel.ota._2003._05.CommissionType;
import org.opentravel.ota._2003._05.HotelReservationType;
import org.opentravel.ota._2003._05.POSType;
import org.opentravel.ota._2003._05.ProfileType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"pos", "commissionRecipients"})
@XmlRootElement(name="HTNG_HotelCommissionPaymentRQ")
public class HTNGHotelCommissionPaymentRQ {
    @XmlElement(name="POS")
    protected POSType pos;
    @XmlElement(name="CommissionRecipients", required=true)
    protected CommissionRecipients commissionRecipients;
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

    public POSType getPOS() {
        return this.pos;
    }

    public void setPOS(POSType value) {
        this.pos = value;
    }

    public CommissionRecipients getCommissionRecipients() {
        return this.commissionRecipients;
    }

    public void setCommissionRecipients(CommissionRecipients value) {
        this.commissionRecipients = value;
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
    @XmlType(name="", propOrder={"commissionRecipient"})
    public static class CommissionRecipients {
        @XmlElement(name="CommissionRecipient", required=true)
        protected List<CommissionRecipient> commissionRecipient;

        public List<CommissionRecipient> getCommissionRecipient() {
            if (this.commissionRecipient == null) {
                this.commissionRecipient = new ArrayList<CommissionRecipient>();
            }
            return this.commissionRecipient;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"uniqueID", "profile", "commissionableReservations", "totalCommission"})
        public static class CommissionRecipient {
            @XmlElement(name="UniqueID", required=true)
            protected List<UniqueIDType> uniqueID;
            @XmlElement(name="Profile", required=true)
            protected ProfileType profile;
            @XmlElement(name="CommissionableReservations")
            protected CommissionableReservations commissionableReservations;
            @XmlElement(name="TotalCommission", required=true)
            protected CommissionType totalCommission;

            public List<UniqueIDType> getUniqueID() {
                if (this.uniqueID == null) {
                    this.uniqueID = new ArrayList<UniqueIDType>();
                }
                return this.uniqueID;
            }

            public ProfileType getProfile() {
                return this.profile;
            }

            public void setProfile(ProfileType value) {
                this.profile = value;
            }

            public CommissionableReservations getCommissionableReservations() {
                return this.commissionableReservations;
            }

            public void setCommissionableReservations(CommissionableReservations value) {
                this.commissionableReservations = value;
            }

            public CommissionType getTotalCommission() {
                return this.totalCommission;
            }

            public void setTotalCommission(CommissionType value) {
                this.totalCommission = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"commissionableReservation"})
            public static class CommissionableReservations {
                @XmlElement(name="CommissionableReservation", required=true)
                protected CommissionableReservation commissionableReservation;

                public CommissionableReservation getCommissionableReservation() {
                    return this.commissionableReservation;
                }

                public void setCommissionableReservation(CommissionableReservation value) {
                    this.commissionableReservation = value;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="", propOrder={"commissionInfo"})
                public static class CommissionableReservation
                extends HotelReservationType {
                    @XmlElement(name="CommissionInfo", required=true)
                    protected CommissionType commissionInfo;

                    public CommissionType getCommissionInfo() {
                        return this.commissionInfo;
                    }

                    public void setCommissionInfo(CommissionType value) {
                        this.commissionInfo = value;
                    }
                }
            }
        }
    }
}

