/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v5;

import com.tpvision.smartinstall.xml.channel.v5.App;
import com.tpvision.smartinstall.xml.channel.v5.Broadcast;
import com.tpvision.smartinstall.xml.channel.v5.Media;
import com.tpvision.smartinstall.xml.channel.v5.Multicast;
import com.tpvision.smartinstall.xml.channel.v5.Setup;
import com.tpvision.smartinstall.xml.channel.v5.Source;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"broadcast", "media", "multicast", "source", "app", "setup"})
@XmlRootElement(name="Channel")
public class Channel {
    @XmlElement(name="Broadcast")
    protected Broadcast broadcast;
    @XmlElement(name="Media")
    protected Media media;
    @XmlElement(name="Multicast")
    protected Multicast multicast;
    @XmlElement(name="Source")
    protected Source source;
    @XmlElement(name="App")
    protected App app;
    @XmlElement(name="Setup", required=true)
    protected Setup setup;

    public Broadcast getBroadcast() {
        return this.broadcast;
    }

    public void setBroadcast(Broadcast value) {
        this.broadcast = value;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media value) {
        this.media = value;
    }

    public Multicast getMulticast() {
        return this.multicast;
    }

    public void setMulticast(Multicast value) {
        this.multicast = value;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source value) {
        this.source = value;
    }

    public Setup getSetup() {
        return this.setup;
    }

    public void setSetup(Setup value) {
        this.setup = value;
    }

    public App getApp() {
        return this.app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}

