/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast.discovery;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-discovery-swarm")
@JsonFilter(value="HazelcastDockerSwarmDiscoveryProperties")
public class HazelcastDockerSwarmDiscoveryProperties
implements Serializable {
    private static final long serialVersionUID = -1409066358752067150L;
    private DnsRProvider dnsProvider = new DnsRProvider();
    private MemberAddressProvider memberProvider = new MemberAddressProvider();

    @Generated
    public DnsRProvider getDnsProvider() {
        return this.dnsProvider;
    }

    @Generated
    public MemberAddressProvider getMemberProvider() {
        return this.memberProvider;
    }

    @Generated
    public HazelcastDockerSwarmDiscoveryProperties setDnsProvider(DnsRProvider dnsProvider) {
        this.dnsProvider = dnsProvider;
        return this;
    }

    @Generated
    public HazelcastDockerSwarmDiscoveryProperties setMemberProvider(MemberAddressProvider memberProvider) {
        this.memberProvider = memberProvider;
        return this;
    }

    @RequiresModule(name="cas-server-support-hazelcast-discovery-swarm")
    public static class MemberAddressProvider
    implements Serializable {
        private static final long serialVersionUID = -2963901001243353939L;
        private boolean enabled;
        private String dockerNetworkNames;
        private String dockerServiceNames;
        private String dockerServiceLabels;
        private String swarmMgrUri;
        private boolean skipVerifySsl;
        private int hazelcastPeerPort = 5701;

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public String getDockerNetworkNames() {
            return this.dockerNetworkNames;
        }

        @Generated
        public String getDockerServiceNames() {
            return this.dockerServiceNames;
        }

        @Generated
        public String getDockerServiceLabels() {
            return this.dockerServiceLabels;
        }

        @Generated
        public String getSwarmMgrUri() {
            return this.swarmMgrUri;
        }

        @Generated
        public boolean isSkipVerifySsl() {
            return this.skipVerifySsl;
        }

        @Generated
        public int getHazelcastPeerPort() {
            return this.hazelcastPeerPort;
        }

        @Generated
        public MemberAddressProvider setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        @Generated
        public MemberAddressProvider setDockerNetworkNames(String dockerNetworkNames) {
            this.dockerNetworkNames = dockerNetworkNames;
            return this;
        }

        @Generated
        public MemberAddressProvider setDockerServiceNames(String dockerServiceNames) {
            this.dockerServiceNames = dockerServiceNames;
            return this;
        }

        @Generated
        public MemberAddressProvider setDockerServiceLabels(String dockerServiceLabels) {
            this.dockerServiceLabels = dockerServiceLabels;
            return this;
        }

        @Generated
        public MemberAddressProvider setSwarmMgrUri(String swarmMgrUri) {
            this.swarmMgrUri = swarmMgrUri;
            return this;
        }

        @Generated
        public MemberAddressProvider setSkipVerifySsl(boolean skipVerifySsl) {
            this.skipVerifySsl = skipVerifySsl;
            return this;
        }

        @Generated
        public MemberAddressProvider setHazelcastPeerPort(int hazelcastPeerPort) {
            this.hazelcastPeerPort = hazelcastPeerPort;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-hazelcast-discovery-swarm")
    public static class DnsRProvider
    implements Serializable {
        private static final long serialVersionUID = -1863901001243353934L;
        private boolean enabled;
        private String serviceName;
        private int servicePort = 5701;
        private String peerServices;

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public String getServiceName() {
            return this.serviceName;
        }

        @Generated
        public int getServicePort() {
            return this.servicePort;
        }

        @Generated
        public String getPeerServices() {
            return this.peerServices;
        }

        @Generated
        public DnsRProvider setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        @Generated
        public DnsRProvider setServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        @Generated
        public DnsRProvider setServicePort(int servicePort) {
            this.servicePort = servicePort;
            return this;
        }

        @Generated
        public DnsRProvider setPeerServices(String peerServices) {
            this.peerServices = peerServices;
            return this;
        }
    }
}

