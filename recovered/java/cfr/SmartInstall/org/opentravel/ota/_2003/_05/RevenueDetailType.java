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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.htng._2011b.HTNGRevenueDetailType;
import org.opentravel.ota._2003._05.FolioIDsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RevenueDetailType", propOrder={"folioIDs"})
@XmlSeeAlso(value={HTNGRevenueDetailType.class})
public class RevenueDetailType {
    @XmlElement(name="FolioIDs", required=true)
    protected FolioIDsType folioIDs;
    @XmlAttribute(name="ReferenceID")
    protected String referenceID;
    @XmlAttribute(name="TransactionDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar transactionDate;
    @XmlAttribute(name="RatePlanCode")
    protected String ratePlanCode;
    @XmlAttribute(name="Description")
    protected String description;
    @XmlAttribute(name="PMSRevenueCode")
    protected String pmsRevenueCode;
    @XmlAttribute(name="Amount")
    protected BigDecimal amount;
    @XmlAttribute(name="CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name="DecimalPlaces")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger decimalPlaces;

    public FolioIDsType getFolioIDs() {
        return this.folioIDs;
    }

    public void setFolioIDs(FolioIDsType value) {
        this.folioIDs = value;
    }

    public String getReferenceID() {
        return this.referenceID;
    }

    public void setReferenceID(String value) {
        this.referenceID = value;
    }

    public XMLGregorianCalendar getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    public String getRatePlanCode() {
        return this.ratePlanCode;
    }

    public void setRatePlanCode(String value) {
        this.ratePlanCode = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getPMSRevenueCode() {
        return this.pmsRevenueCode;
    }

    public void setPMSRevenueCode(String value) {
        this.pmsRevenueCode = value;
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

