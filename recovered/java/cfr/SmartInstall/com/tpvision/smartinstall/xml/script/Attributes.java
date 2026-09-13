/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.script;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Attributes")
@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(propOrder={"channelNumber"})
public class Attributes {
    @XmlElement(name="ChannelNumber", required=false)
    protected String channelNumber;

    public String getChannelNumber() {
        return this.channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }
}

