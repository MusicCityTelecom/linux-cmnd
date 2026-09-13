/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.PaymentCardType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_PaymentCardType", propOrder={"emvData"})
public class HTNGPaymentCardType
extends PaymentCardType {
    @XmlElement(name="EMVData")
    protected String emvData;
    @XmlAttribute(name="CardNumberIsProxy")
    protected Boolean cardNumberIsProxy;
    @XmlAttribute(name="IsEMV")
    protected Boolean isEMV;
    @XmlAttribute(name="IssueNumber")
    protected Integer issueNumber;

    public String getEMVData() {
        return this.emvData;
    }

    public void setEMVData(String value) {
        this.emvData = value;
    }

    public Boolean isCardNumberIsProxy() {
        return this.cardNumberIsProxy;
    }

    public void setCardNumberIsProxy(Boolean value) {
        this.cardNumberIsProxy = value;
    }

    public Boolean isIsEMV() {
        return this.isEMV;
    }

    public void setIsEMV(Boolean value) {
        this.isEMV = value;
    }

    public Integer getIssueNumber() {
        return this.issueNumber;
    }

    public void setIssueNumber(Integer value) {
        this.issueNumber = value;
    }
}

