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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TransactionType", propOrder={"amount"})
public class TransactionType {
    @XmlElement(name="Amount", required=true)
    protected Amount amount;
    @XmlAttribute(name="Type", required=true)
    protected String type;
    @XmlAttribute(name="CreditDebitInd")
    protected String creditDebitInd;
    @XmlAttribute(name="PostingDateTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar postingDateTime;
    @XmlAttribute(name="BatchNmbr")
    protected String batchNmbr;
    @XmlAttribute(name="Comment")
    protected String comment;

    public Amount getAmount() {
        return this.amount;
    }

    public void setAmount(Amount value) {
        this.amount = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getCreditDebitInd() {
        return this.creditDebitInd;
    }

    public void setCreditDebitInd(String value) {
        this.creditDebitInd = value;
    }

    public XMLGregorianCalendar getPostingDateTime() {
        return this.postingDateTime;
    }

    public void setPostingDateTime(XMLGregorianCalendar value) {
        this.postingDateTime = value;
    }

    public String getBatchNmbr() {
        return this.batchNmbr;
    }

    public void setBatchNmbr(String value) {
        this.batchNmbr = value;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Amount {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

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
}

