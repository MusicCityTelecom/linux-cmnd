/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_MagneticStripeType", propOrder={"value"})
public class HTNGMagneticStripeType {
    @XmlValue
    protected byte[] value;
    @XmlAttribute(name="Track1")
    protected byte[] track1;
    @XmlAttribute(name="Track2")
    protected byte[] track2;
    @XmlAttribute(name="Track3")
    protected byte[] track3;

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public byte[] getTrack1() {
        return this.track1;
    }

    public void setTrack1(byte[] value) {
        this.track1 = value;
    }

    public byte[] getTrack2() {
        return this.track2;
    }

    public void setTrack2(byte[] value) {
        this.track2 = value;
    }

    public byte[] getTrack3() {
        return this.track3;
    }

    public void setTrack3(byte[] value) {
        this.track3 = value;
    }
}

