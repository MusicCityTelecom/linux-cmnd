/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="setting")
public class Setting {
    @XmlAttribute(name="item", required=true)
    protected String item;
    @XmlAttribute(name="ItemID", required=true)
    protected String ItemID;
    @XmlAttribute(name="lastValue", required=true)
    protected String lastValue;
    @XmlAttribute(name="Value", required=true)
    protected String Value;
    @XmlAttribute(name="cloneIn", required=true)
    protected String cloneIn;
    @XmlAttribute(name="position", required=true)
    protected BigInteger position;
    @XmlAttribute(name="refFile", required=true)
    protected String refFile;
    @XmlAttribute(name="xaddr", required=true)
    protected String xaddr;

    public String getItem() {
        return this.item;
    }

    public String getItem1() {
        return this.ItemID;
    }

    public void setItem(String value) {
        this.item = value;
    }

    public void setItem1(String value) {
        this.ItemID = value;
    }

    public String getLastValue() {
        return this.lastValue;
    }

    public String getLastValue1() {
        return this.Value;
    }

    public void setLastValue(String value) {
        this.lastValue = value;
    }

    public void setLastValue1(String Value2) {
        this.Value = Value2;
    }

    public BigInteger getPosition() {
        return this.position;
    }

    public void setPosition(BigInteger value) {
        this.position = value;
    }

    public String getRefFile() {
        return this.refFile;
    }

    public void setRefFile(String value) {
        this.refFile = value;
    }

    public String getXaddr() {
        return this.xaddr;
    }

    public void setXaddr(String value) {
        this.xaddr = value;
    }

    public String getCloneIn() {
        return this.cloneIn;
    }

    public void setCloneIn(String value) {
        this.cloneIn = value;
    }
}

