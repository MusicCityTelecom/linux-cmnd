/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.AirTravelerType;
import org.opentravel.ota._2003._05.CompanyInfoType;
import org.opentravel.ota._2003._05.CustomerType;
import org.opentravel.ota._2003._05.EmailsType;
import org.opentravel.ota._2003._05.RailPassengerDetailType;
import org.opentravel.ota._2003._05.RailPersonInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EmailType", propOrder={"value"})
@XmlSeeAlso(value={AirTravelerType.Email.class, CustomerType.Email.class, CompanyInfoType.Email.class, EmailsType.Email.class, RailPassengerDetailType.Email.class, RailPersonInfoType.Email.class})
public class EmailType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="EmailType")
    protected String emailType;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="Remark")
    protected String remark;
    @XmlAttribute(name="DefaultInd")
    protected Boolean defaultInd;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEmailType() {
        return this.emailType;
    }

    public void setEmailType(String value) {
        this.emailType = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    public Boolean isDefaultInd() {
        return this.defaultInd;
    }

    public void setDefaultInd(Boolean value) {
        this.defaultInd = value;
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

