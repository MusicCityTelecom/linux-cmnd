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

@RequiresModule(name="cas-server-support-hazelcast-discovery-kubernetes")
@JsonFilter(value="HazelcastKubernetesDiscoveryProperties")
public class HazelcastKubernetesDiscoveryProperties
implements Serializable {
    private static final long serialVersionUID = 8590530159392472509L;
    private String serviceDns;
    private int serviceDnsTimeout = -1;
    private String serviceName;
    private String serviceLabelName;
    private String serviceLabelValue;
    private String namespace;
    private boolean resolveNotReadyAddresses;
    private String kubernetesMaster;
    private String apiToken;
    private String podLabelName;
    private String podLabelValue;
    private boolean useNodeNameAsExternalAddress;
    private int apiRetries = 3;
    private String caCertificate;
    private int servicePort;

    @Generated
    public String getServiceDns() {
        return this.serviceDns;
    }

    @Generated
    public int getServiceDnsTimeout() {
        return this.serviceDnsTimeout;
    }

    @Generated
    public String getServiceName() {
        return this.serviceName;
    }

    @Generated
    public String getServiceLabelName() {
        return this.serviceLabelName;
    }

    @Generated
    public String getServiceLabelValue() {
        return this.serviceLabelValue;
    }

    @Generated
    public String getNamespace() {
        return this.namespace;
    }

    @Generated
    public boolean isResolveNotReadyAddresses() {
        return this.resolveNotReadyAddresses;
    }

    @Generated
    public String getKubernetesMaster() {
        return this.kubernetesMaster;
    }

    @Generated
    public String getApiToken() {
        return this.apiToken;
    }

    @Generated
    public String getPodLabelName() {
        return this.podLabelName;
    }

    @Generated
    public String getPodLabelValue() {
        return this.podLabelValue;
    }

    @Generated
    public boolean isUseNodeNameAsExternalAddress() {
        return this.useNodeNameAsExternalAddress;
    }

    @Generated
    public int getApiRetries() {
        return this.apiRetries;
    }

    @Generated
    public String getCaCertificate() {
        return this.caCertificate;
    }

    @Generated
    public int getServicePort() {
        return this.servicePort;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setServiceDns(String serviceDns) {
        this.serviceDns = serviceDns;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setServiceDnsTimeout(int serviceDnsTimeout) {
        this.serviceDnsTimeout = serviceDnsTimeout;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setServiceLabelName(String serviceLabelName) {
        this.serviceLabelName = serviceLabelName;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setServiceLabelValue(String serviceLabelValue) {
        this.serviceLabelValue = serviceLabelValue;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setResolveNotReadyAddresses(boolean resolveNotReadyAddresses) {
        this.resolveNotReadyAddresses = resolveNotReadyAddresses;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setKubernetesMaster(String kubernetesMaster) {
        this.kubernetesMaster = kubernetesMaster;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setApiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setPodLabelName(String podLabelName) {
        this.podLabelName = podLabelName;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setPodLabelValue(String podLabelValue) {
        this.podLabelValue = podLabelValue;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setUseNodeNameAsExternalAddress(boolean useNodeNameAsExternalAddress) {
        this.useNodeNameAsExternalAddress = useNodeNameAsExternalAddress;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setApiRetries(int apiRetries) {
        this.apiRetries = apiRetries;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setCaCertificate(String caCertificate) {
        this.caCertificate = caCertificate;
        return this;
    }

    @Generated
    public HazelcastKubernetesDiscoveryProperties setServicePort(int servicePort) {
        this.servicePort = servicePort;
        return this;
    }
}

