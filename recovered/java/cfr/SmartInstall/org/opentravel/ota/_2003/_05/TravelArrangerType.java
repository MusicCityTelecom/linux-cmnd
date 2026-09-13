/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.CompanyNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TravelArrangerType")
public class TravelArrangerType
extends CompanyNameType {
    @XmlAttribute(name="TravelArrangerType")
    protected String travelArrangerType;
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

    public String getTravelArrangerType() {
        return this.travelArrangerType;
    }

    public void setTravelArrangerType(String value) {
        this.travelArrangerType = value;
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

