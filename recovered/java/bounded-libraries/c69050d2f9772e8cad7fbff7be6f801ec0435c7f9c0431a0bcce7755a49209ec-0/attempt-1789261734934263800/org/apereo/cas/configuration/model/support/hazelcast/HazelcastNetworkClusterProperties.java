/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastNetworkSslProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastClusterProperties")
public class HazelcastNetworkClusterProperties
implements Serializable {
    private static final long serialVersionUID = -8474968308106013185L;
    private boolean tcpipEnabled = true;
    @RequiredProperty
    private List<String> members = Stream.of("localhost").collect(Collectors.toList());
    private boolean portAutoIncrement = true;
    @RequiredProperty
    private int port = 5701;
    private List<String> outboundPorts = new ArrayList<String>();
    private String localAddress;
    private String publicAddress;
    private String networkInterfaces;
    private boolean ipv4Enabled = true;
    @NestedConfigurationProperty
    private HazelcastNetworkSslProperties ssl = new HazelcastNetworkSslProperties();

    @Generated
    public boolean isTcpipEnabled() {
        return this.tcpipEnabled;
    }

    @Generated
    public List<String> getMembers() {
        return this.members;
    }

    @Generated
    public boolean isPortAutoIncrement() {
        return this.portAutoIncrement;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public List<String> getOutboundPorts() {
        return this.outboundPorts;
    }

    @Generated
    public String getLocalAddress() {
        return this.localAddress;
    }

    @Generated
    public String getPublicAddress() {
        return this.publicAddress;
    }

    @Generated
    public String getNetworkInterfaces() {
        return this.networkInterfaces;
    }

    @Generated
    public boolean isIpv4Enabled() {
        return this.ipv4Enabled;
    }

    @Generated
    public HazelcastNetworkSslProperties getSsl() {
        return this.ssl;
    }

    @Generated
    public HazelcastNetworkClusterProperties setTcpipEnabled(boolean tcpipEnabled) {
        this.tcpipEnabled = tcpipEnabled;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setMembers(List<String> members) {
        this.members = members;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setPortAutoIncrement(boolean portAutoIncrement) {
        this.portAutoIncrement = portAutoIncrement;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setOutboundPorts(List<String> outboundPorts) {
        this.outboundPorts = outboundPorts;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setNetworkInterfaces(String networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setIpv4Enabled(boolean ipv4Enabled) {
        this.ipv4Enabled = ipv4Enabled;
        return this;
    }

    @Generated
    public HazelcastNetworkClusterProperties setSsl(HazelcastNetworkSslProperties ssl) {
        this.ssl = ssl;
        return this;
    }
}

