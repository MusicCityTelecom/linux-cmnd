/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StateProvType", propOrder={"value"})
public class StateProvType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="StateCode")
    protected String stateCode;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String value) {
        this.stateCode = value;
    }
}

