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
@XmlType(name="TimeDurationType", propOrder={"value"})
public class TimeDurationType {
    @XmlValue
    protected Duration value;
    @XmlAttribute(name="Minimum")
    protected Duration minimum;
    @XmlAttribute(name="Maximum")
    protected Duration maximum;

    public Duration getValue() {
        return this.value;
    }

    public void setValue(Duration value) {
        this.value = value;
    }

    public Duration getMinimum() {
        return this.minimum;
    }

    public void setMinimum(Duration value) {
        this.minimum = value;
    }

    public Duration getMaximum() {
        return this.maximum;
    }

    public void setMaximum(Duration value) {
        this.maximum = value;
    }
}

