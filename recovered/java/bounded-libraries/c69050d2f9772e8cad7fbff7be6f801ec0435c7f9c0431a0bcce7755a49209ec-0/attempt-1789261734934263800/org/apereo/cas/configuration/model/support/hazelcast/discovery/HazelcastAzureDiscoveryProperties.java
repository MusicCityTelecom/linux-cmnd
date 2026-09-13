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
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-discovery-azure")
@JsonFilter(value="HazelcastAzureDiscoveryProperties")
public class HazelcastAzureDiscoveryProperties
implements Serializable {
    public static final String AZURE_DISCOVERY_CLIENT_ID = "client-id";
    public static final String AZURE_DISCOVERY_CLIENT_SECRET = "client-secret";
    public static final String AZURE_DISCOVERY_TENANT_ID = "tenant-id";
    public static final String AZURE_DISCOVERY_SUBSCRIPTION_ID = "subscription-id";
    public static final String AZURE_DISCOVERY_CLUSTER_ID = "cluster-id";
    public static final String AZURE_DISCOVERY_GROUP_NAME = "group-name";
    private static final long serialVersionUID = 3861923784551442190L;
    @RequiredProperty
    private String subscriptionId;
    @RequiredProperty
    private String clientId;
    @RequiredProperty
    private String clientSecret;
    @RequiredProperty
    private String tenantId;
    @RequiredProperty
    private String clusterId;
    @RequiredProperty
    private String groupName;

    @Generated
    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    @Generated
    public String getClientId() {
        return this.clientId;
    }

    @Generated
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Generated
    public String getTenantId() {
        return this.tenantId;
    }

    @Generated
    public String getClusterId() {
        return this.clusterId;
    }

    @Generated
    public String getGroupName() {
        return this.groupName;
    }

    @Generated
    public HazelcastAzureDiscoveryProperties setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    @Generated
    public HazelcastAzureDiscoveryProperties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Generated
    public HazelcastAzureDiscoveryProperties setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    @Generated
    public HazelcastAzureDiscoveryProperties setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Generated
    public HazelcastAzureDiscoveryProperties setClusterId(String clusterId) {
        this.clusterId = clusterId;
        return this;
    }

    @Generated
    public HazelcastAzureDiscoveryProperties setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }
}

