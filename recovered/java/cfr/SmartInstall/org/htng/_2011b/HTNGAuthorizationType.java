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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.htng._2011b.HTNGPaymentCardType;
import org.opentravel.ota._2003._05.TimeUnitType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_AuthorizationType", propOrder={"creditCardAuthorization", "bookingReferenceID"})
public class HTNGAuthorizationType {
    @XmlElement(name="CreditCardAuthorization")
    protected CreditCardAuthorization creditCardAuthorization;
    @XmlElement(name="BookingReferenceID")
    protected BookingReferenceID bookingReferenceID;
    @XmlAttribute(name="PrincipalCompanyCode")
    protected String principalCompanyCode;
    @XmlAttribute(name="RefNumber")
    protected String refNumber;

    public CreditCardAuthorization getCreditCardAuthorization() {
        return this.creditCardAuthorization;
    }

    public void setCreditCardAuthorization(CreditCardAuthorization value) {
        this.creditCardAuthorization = value;
    }

    public BookingReferenceID getBookingReferenceID() {
        return this.bookingReferenceID;
    }

    public void setBookingReferenceID(BookingReferenceID value) {
        this.bookingReferenceID = value;
    }

    public String getPrincipalCompanyCode() {
        return this.principalCompanyCode;
    }

    public void setPrincipalCompanyCode(String value) {
        this.principalCompanyCode = value;
    }

    public String getRefNumber() {
        return this.refNumber;
    }

    public void setRefNumber(String value) {
        this.refNumber = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"creditCard", "id"})
    public static class CreditCardAuthorization {
        @XmlElement(name="CreditCard", required=true)
        protected HTNGPaymentCardType creditCard;
        @XmlElement(name="ID")
        protected List<UniqueIDType> id;
        @XmlAttribute(name="SourceType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String sourceType;
        @XmlAttribute(name="ExtendedPaymentInd")
        protected Boolean extendedPaymentInd;
        @XmlAttribute(name="ExtendedPaymentQuantity")
        protected Integer extendedPaymentQuantity;
        @XmlAttribute(name="ExtendedPaymentFrequency")
        protected TimeUnitType extendedPaymentFrequency;
        @XmlAttribute(name="AuthorizationCode")
        protected String authorizationCode;
        @XmlAttribute(name="ReversalIndicator")
        protected Boolean reversalIndicator;
        @XmlAttribute(name="CardPresentInd")
        protected Boolean cardPresentInd;
        @XmlAttribute(name="E_CommerceCode")
        protected String eCommerceCode;
        @XmlAttribute(name="AuthTransactionID")
        protected String authTransactionID;
        @XmlAttribute(name="AuthVerificationValue")
        protected String authVerificationValue;
        @XmlAttribute(name="TransactionType", required=true)
        protected String transactionType;
        @XmlAttribute(name="SaleCode", required=true)
        protected String saleCode;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public HTNGPaymentCardType getCreditCard() {
            return this.creditCard;
        }

        public void setCreditCard(HTNGPaymentCardType value) {
            this.creditCard = value;
        }

        public List<UniqueIDType> getID() {
            if (this.id == null) {
                this.id = new ArrayList<UniqueIDType>();
            }
            return this.id;
        }

        public String getSourceType() {
            return this.sourceType;
        }

        public void setSourceType(String value) {
            this.sourceType = value;
        }

        public Boolean isExtendedPaymentInd() {
            return this.extendedPaymentInd;
        }

        public void setExtendedPaymentInd(Boolean value) {
            this.extendedPaymentInd = value;
        }

        public Integer getExtendedPaymentQuantity() {
            return this.extendedPaymentQuantity;
        }

        public void setExtendedPaymentQuantity(Integer value) {
            this.extendedPaymentQuantity = value;
        }

        public TimeUnitType getExtendedPaymentFrequency() {
            return this.extendedPaymentFrequency;
        }

        public void setExtendedPaymentFrequency(TimeUnitType value) {
            this.extendedPaymentFrequency = value;
        }

        public String getAuthorizationCode() {
            return this.authorizationCode;
        }

        public void setAuthorizationCode(String value) {
            this.authorizationCode = value;
        }

        public Boolean isReversalIndicator() {
            return this.reversalIndicator;
        }

        public void setReversalIndicator(Boolean value) {
            this.reversalIndicator = value;
        }

        public Boolean isCardPresentInd() {
            return this.cardPresentInd;
        }

        public void setCardPresentInd(Boolean value) {
            this.cardPresentInd = value;
        }

        public String getECommerceCode() {
            return this.eCommerceCode;
        }

        public void setECommerceCode(String value) {
            this.eCommerceCode = value;
        }

        public String getAuthTransactionID() {
            return this.authTransactionID;
        }

        public void setAuthTransactionID(String value) {
            this.authTransactionID = value;
        }

        public String getAuthVerificationValue() {
            return this.authVerificationValue;
        }

        public void setAuthVerificationValue(String value) {
            this.authVerificationValue = value;
        }

        public String getTransactionType() {
            return this.transactionType;
        }

        public void setTransactionType(String value) {
            this.transactionType = value;
        }

        public String getSaleCode() {
            return this.saleCode;
        }

        public void setSaleCode(String value) {
            this.saleCode = value;
        }

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        public String getCurrencyCode() {
            return this.currencyCode;
        }

        public void setCurrencyCode(String value) {
            this.currencyCode = value;
        }

        public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
        }

        public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BookingReferenceID
    extends UniqueIDType {
        @XmlAttribute(name="IgnoreReservationInd")
        protected Boolean ignoreReservationInd;

        public Boolean isIgnoreReservationInd() {
            return this.ignoreReservationInd;
        }

        public void setIgnoreReservationInd(Boolean value) {
            this.ignoreReservationInd = value;
        }
    }
}

