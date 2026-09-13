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

@RequiresModule(name="cas-server-support-hazelcast-discovery-jclouds")
@JsonFilter(value="HazelcastJCloudsDiscoveryProperties")
public class HazelcastJCloudsDiscoveryProperties
implements Serializable {
    public static final String JCLOUDS_DISCOVERY_PROVIDER = "provider";
    public static final String JCLOUDS_DISCOVERY_IDENTITY = "identity";
    public static final String JCLOUDS_DISCOVERY_CREDENTIAL = "credential";
    public static final String JCLOUDS_DISCOVERY_ENDPOINT = "endpoint";
    public static final String JCLOUDS_DISCOVERY_ZONES = "zones";
    public static final String JCLOUDS_DISCOVERY_REGIONS = "regions";
    public static final String JCLOUDS_DISCOVERY_TAG_KEYS = "tag-keys";
    public static final String JCLOUDS_DISCOVERY_TAG_VALUES = "tag-values";
    public static final String JCLOUDS_DISCOVERY_GROUP = "group";
    public static final String JCLOUDS_DISCOVERY_HZ_PORT = "hz-port";
    public static final String JCLOUDS_DISCOVERY_ROLE_NAME = "role-name";
    public static final String JCLOUDS_DISCOVERY_CREDENTIAL_PATH = "credentialPath";
    private static final long serialVersionUID = -8281247687171101766L;
    @RequiredProperty
    private String provider;
    @RequiredProperty
    private String identity;
    @RequiredProperty
    private String credential;
    private String endpoint;
    private String zones;
    private String regions;
    private String tagKeys;
    private String tagValues;
    private String group;
    private int port = -1;
    private String roleName;
    private String credentialPath;

    @Generated
    public String getProvider() {
        return this.provider;
    }

    @Generated
    public String getIdentity() {
        return this.identity;
    }

    @Generated
    public String getCredential() {
        return this.credential;
    }

    @Generated
    public String getEndpoint() {
        return this.endpoint;
    }

    @Generated
    public String getZones() {
        return this.zones;
    }

    @Generated
    public String getRegions() {
        return this.regions;
    }

    @Generated
    public String getTagKeys() {
        return this.tagKeys;
    }

    @Generated
    public String getTagValues() {
        return this.tagValues;
    }

    @Generated
    public String getGroup() {
        return this.group;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public String getRoleName() {
        return this.roleName;
    }

    @Generated
    public String getCredentialPath() {
        return this.credentialPath;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setIdentity(String identity) {
        this.identity = identity;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setCredential(String credential) {
        this.credential = credential;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setZones(String zones) {
        this.zones = zones;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setRegions(String regions) {
        this.regions = regions;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setTagKeys(String tagKeys) {
        this.tagKeys = tagKeys;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setTagValues(String tagValues) {
        this.tagValues = tagValues;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setGroup(String group) {
        this.group = group;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    @Generated
    public HazelcastJCloudsDiscoveryProperties setCredentialPath(String credentialPath) {
        this.credentialPath = credentialPath;
        return this;
    }
}

