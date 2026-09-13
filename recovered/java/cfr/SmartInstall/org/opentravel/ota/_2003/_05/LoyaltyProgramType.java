/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="LoyaltyProgramType", propOrder={"value"})
public class LoyaltyProgramType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="ProgramCode")
    protected String programCode;
    @XmlAttribute(name="LoyaltyLevel")
    protected String loyaltyLevel;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="PrimaryLoyaltyIndicator")
    protected Boolean primaryLoyaltyIndicator;
    @XmlAttribute(name="SingleVendorInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String singleVendorInd;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProgramCode() {
        return this.programCode;
    }

    public void setProgramCode(String value) {
        this.programCode = value;
    }

    public String getLoyaltyLevel() {
        return this.loyaltyLevel;
    }

    public void setLoyaltyLevel(String value) {
        this.loyaltyLevel = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public Boolean isPrimaryLoyaltyIndicator() {
        return this.primaryLoyaltyIndicator;
    }

    public void setPrimaryLoyaltyIndicator(Boolean value) {
        this.primaryLoyaltyIndicator = value;
    }

    public String getSingleVendorInd() {
        return this.singleVendorInd;
    }

    public void setSingleVendorInd(String value) {
        this.singleVendorInd = value;
    }
}

