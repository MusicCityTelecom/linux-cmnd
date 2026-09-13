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
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-radius")
@JsonFilter(value="RadiusClientProperties")
public class RadiusClientProperties
implements Serializable {
    private static final long serialVersionUID = -7961769318651312854L;
    @RequiredProperty
    private String inetAddress = "localhost";
    @RequiredProperty
    private String sharedSecret = "N0Sh@ar3d$ecReT";
    private int socketTimeout;
    private int authenticationPort = 1812;
    private int accountingPort = 1813;
    private RadiusClientTransportTypes transportType = RadiusClientTransportTypes.UDP;

    @Generated
    public String getInetAddress() {
        return this.inetAddress;
    }

    @Generated
    public String getSharedSecret() {
        return this.sharedSecret;
    }

    @Generated
    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    @Generated
    public int getAuthenticationPort() {
        return this.authenticationPort;
    }

    @Generated
    public int getAccountingPort() {
        return this.accountingPort;
    }

    @Generated
    public RadiusClientTransportTypes getTransportType() {
        return this.transportType;
    }

    @Generated
    public RadiusClientProperties setInetAddress(String inetAddress) {
        this.inetAddress = inetAddress;
        return this;
    }

    @Generated
    public RadiusClientProperties setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
        return this;
    }

    @Generated
    public RadiusClientProperties setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    @Generated
    public RadiusClientProperties setAuthenticationPort(int authenticationPort) {
        this.authenticationPort = authenticationPort;
        return this;
    }

    @Generated
    public RadiusClientProperties setAccountingPort(int accountingPort) {
        this.accountingPort = accountingPort;
        return this;
    }

    @Generated
    public RadiusClientProperties setTransportType(RadiusClientTransportTypes transportType) {
        this.transportType = transportType;
        return this;
    }

    public static enum RadiusClientTransportTypes {
        UDP,
        RADSEC;

    }
}

