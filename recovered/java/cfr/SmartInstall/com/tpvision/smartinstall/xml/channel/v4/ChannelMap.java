/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v4;

import com.tpvision.smartinstall.xml.channel.v4.Channel;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"channel"})
@XmlRootElement(name="ChannelMap")
public class ChannelMap {
    @XmlElement(name="Channel", required=true)
    protected List<Channel> channel;

    public List<Channel> getChannel() {
        if (null == this.channel) {
            this.channel = new ArrayList<Channel>();
        }
        return this.channel;
    }
}

