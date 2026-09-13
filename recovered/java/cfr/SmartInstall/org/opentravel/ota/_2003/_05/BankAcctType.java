/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BankAcctType", propOrder={"bankAcctName"})
public class BankAcctType {
    @XmlElement(name="BankAcctName")
    protected String bankAcctName;
    @XmlAttribute(name="BankID")
    protected String bankID;
    @XmlAttribute(name="AcctType")
    protected String acctType;
    @XmlAttribute(name="BankAcctNumber")
    protected String bankAcctNumber;
    @XmlAttribute(name="ChecksAcceptedInd")
    protected Boolean checksAcceptedInd;
    @XmlAttribute(name="CheckNumber")
    protected String checkNumber;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public String getBankAcctName() {
        return this.bankAcctName;
    }

    public void setBankAcctName(String value) {
        this.bankAcctName = value;
    }

    public String getBankID() {
        return this.bankID;
    }

    public void setBankID(String value) {
        this.bankID = value;
    }

    public String getAcctType() {
        return this.acctType;
    }

    public void setAcctType(String value) {
        this.acctType = value;
    }

    public String getBankAcctNumber() {
        return this.bankAcctNumber;
    }

    public void setBankAcctNumber(String value) {
        this.bankAcctNumber = value;
    }

    public Boolean isChecksAcceptedInd() {
        return this.checksAcceptedInd;
    }

    public void setChecksAcceptedInd(Boolean value) {
        this.checksAcceptedInd = value;
    }

    public String getCheckNumber() {
        return this.checkNumber;
    }

    public void setCheckNumber(String value) {
        this.checkNumber = value;
    }

    public String getShareSynchInd() {
        return this.shareSynchInd;
    }

    public void setShareSynchInd(String value) {
        this.shareSynchInd = value;
    }

    public String getShareMarketInd() {
        return this.shareMarketInd;
    }

    public void setShareMarketInd(String value) {
        this.shareMarketInd = value;
    }
}

