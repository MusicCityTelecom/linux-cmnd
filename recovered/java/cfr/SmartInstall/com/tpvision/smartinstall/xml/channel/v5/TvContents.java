/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v5;

import com.tpvision.smartinstall.xml.channel.v5.ApplicationMap;
import com.tpvision.smartinstall.xml.channel.v5.ChannelMap;
import com.tpvision.smartinstall.xml.channel.v5.SchemaVersion;
import com.tpvision.smartinstall.xml.channel.v5.ThemeTV;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"schemaVersion", "channelMap", "applicationMap", "themeTV"})
@XmlRootElement(name="TvContents")
public class TvContents {
    @XmlElement(name="SchemaVersion", required=true)
    protected SchemaVersion schemaVersion;
    @XmlElement(name="ChannelMap", required=true)
    protected ChannelMap channelMap;
    @XmlElement(name="ApplicationMap", required=true)
    protected ApplicationMap applicationMap;
    @XmlElement(name="ThemeTV", required=true)
    protected ThemeTV themeTV;

    public SchemaVersion getSchemaVersion() {
        return this.schemaVersion;
    }

    public void setSchemaVersion(SchemaVersion value) {
        this.schemaVersion = value;
    }

    public ChannelMap getChannelMap() {
        return this.channelMap;
    }

    public void setChannelMap(ChannelMap value) {
        this.channelMap = value;
    }

    public ApplicationMap getApplicationMap() {
        return this.applicationMap;
    }

    public void setApplicationMap(ApplicationMap value) {
        this.applicationMap = value;
    }

    public ThemeTV getThemeTV() {
        return this.themeTV;
    }

    public void setThemeTV(ThemeTV value) {
        this.themeTV = value;
    }
}

