/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.radius;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-radius")
@JsonFilter(value="RadiusServerProperties")
public class RadiusServerProperties
implements Serializable {
    private static final long serialVersionUID = -3911282132573730184L;
    private String protocol = "EAP_MSCHAPv2";
    private int retries = 3;
    private String nasIdentifier;
    private long nasPort = -1L;
    private long nasPortId = -1L;
    private long nasRealPort = -1L;
    private int nasPortType = -1;
    private String nasIpAddress;
    private String nasIpv6Address;

    @Generated
    public String getProtocol() {
        return this.protocol;
    }

    @Generated
    public int getRetries() {
        return this.retries;
    }

    @Generated
    public String getNasIdentifier() {
        return this.nasIdentifier;
    }

    @Generated
    public long getNasPort() {
        return this.nasPort;
    }

    @Generated
    public long getNasPortId() {
        return this.nasPortId;
    }

    @Generated
    public long getNasRealPort() {
        return this.nasRealPort;
    }

    @Generated
    public int getNasPortType() {
        return this.nasPortType;
    }

    @Generated
    public String getNasIpAddress() {
        return this.nasIpAddress;
    }

    @Generated
    public String getNasIpv6Address() {
        return this.nasIpv6Address;
    }

    @Generated
    public RadiusServerProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Generated
    public RadiusServerProperties setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    @Generated
    public RadiusServerProperties setNasIdentifier(String nasIdentifier) {
        this.nasIdentifier = nasIdentifier;
        return this;
    }

    @Generated
    public RadiusServerProperties setNasPort(long nasPort) {
        this.nasPort = nasPort;
        return this;
    }

    @Generated
    public RadiusServerProperties setNasPortId(long nasPortId) {
        this.nasPortId = nasPortId;
        return this;
    }

    @Generated
    public RadiusServerProperties setNasRealPort(long nasRealPort) {
        this.nasRealPort = nasRealPort;
        return this;
    }

    @Generated
    public RadiusServerProperties setNasPortType(int nasPortType) {
        this.nasPortType = nasPortType;
        return this;
    }

    @Generated
    public RadiusServerProperties setNasIpAddress(String nasIpAddress) {
        this.nasIpAddress = nasIpAddress;
        return this;
    }

    @Generated
    public RadiusServerProperties setNasIpv6Address(String nasIpv6Address) {
        this.nasIpv6Address = nasIpv6Address;
        return this;
    }
}

