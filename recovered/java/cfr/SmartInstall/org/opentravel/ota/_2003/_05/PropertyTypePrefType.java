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
@XmlType(name="PropertyTypePrefType", propOrder={"value"})
public class PropertyTypePrefType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="PropertyType")
    protected String propertyType;
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String value) {
        this.propertyType = value;
    }

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }
}

