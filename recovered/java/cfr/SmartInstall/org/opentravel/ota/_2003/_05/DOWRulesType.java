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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DOW_RulesType")
public class DOWRulesType {
    @XmlAttribute(name="DOW_TypeCode")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String dowTypeCode;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;
    @XmlAttribute(name="Mon")
    protected Boolean mon;
    @XmlAttribute(name="Tue")
    protected Boolean tue;
    @XmlAttribute(name="Weds")
    protected Boolean weds;
    @XmlAttribute(name="Thur")
    protected Boolean thur;
    @XmlAttribute(name="Fri")
    protected Boolean fri;
    @XmlAttribute(name="Sat")
    protected Boolean sat;
    @XmlAttribute(name="Sun")
    protected Boolean sun;

    public String getDOWTypeCode() {
        return this.dowTypeCode;
    }

    public void setDOWTypeCode(String value) {
        this.dowTypeCode = value;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String value) {
        this.start = value;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String value) {
        this.end = value;
    }

    public Boolean isMon() {
        return this.mon;
    }

    public void setMon(Boolean value) {
        this.mon = value;
    }

    public Boolean isTue() {
        return this.tue;
    }

    public void setTue(Boolean value) {
        this.tue = value;
    }

    public Boolean isWeds() {
        return this.weds;
    }

    public void setWeds(Boolean value) {
        this.weds = value;
    }

    public Boolean isThur() {
        return this.thur;
    }

    public void setThur(Boolean value) {
        this.thur = value;
    }

    public Boolean isFri() {
        return this.fri;
    }

    public void setFri(Boolean value) {
        this.fri = value;
    }

    public Boolean isSat() {
        return this.sat;
    }

    public void setSat(Boolean value) {
        this.sat = value;
    }

    public Boolean isSun() {
        return this.sun;
    }

    public void setSun(Boolean value) {
        this.sun = value;
    }
}

