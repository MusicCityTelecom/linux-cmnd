/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.Duration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TimeInstantType", propOrder={"value"})
public class TimeInstantType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="WindowBefore")
    protected Duration windowBefore;
    @XmlAttribute(name="WindowAfter")
    protected Duration windowAfter;
    @XmlAttribute(name="CrossDateAllowedIndicator")
    protected Boolean crossDateAllowedIndicator;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Duration getWindowBefore() {
        return this.windowBefore;
    }

    public void setWindowBefore(Duration value) {
        this.windowBefore = value;
    }

    public Duration getWindowAfter() {
        return this.windowAfter;
    }

    public void setWindowAfter(Duration value) {
        this.windowAfter = value;
    }

    public Boolean isCrossDateAllowedIndicator() {
        return this.crossDateAllowedIndicator;
    }

    public void setCrossDateAllowedIndicator(Boolean value) {
        this.crossDateAllowedIndicator = value;
    }
}

