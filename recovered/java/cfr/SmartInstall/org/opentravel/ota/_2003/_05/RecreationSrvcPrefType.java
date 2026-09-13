/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.opentravel.ota._2003._05.PreferLevelType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecreationSrvcPrefType", propOrder={"value"})
public class RecreationSrvcPrefType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="RecreationSrvcType")
    protected String recreationSrvcType;
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRecreationSrvcType() {
        return this.recreationSrvcType;
    }

    public void setRecreationSrvcType(String value) {
        this.recreationSrvcType = value;
    }

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }
}

