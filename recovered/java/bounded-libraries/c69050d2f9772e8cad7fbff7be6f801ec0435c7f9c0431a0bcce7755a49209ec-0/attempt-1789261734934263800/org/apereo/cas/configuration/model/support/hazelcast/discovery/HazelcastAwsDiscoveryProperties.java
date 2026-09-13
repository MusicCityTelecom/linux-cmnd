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

@RequiresModule(name="cas-server-support-hazelcast-discovery-aws")
@JsonFilter(value="HazelcastAwsDiscoveryProperties")
public class HazelcastAwsDiscoveryProperties
implements Serializable {
    public static final String AWS_DISCOVERY_ACCESS_KEY = "access-key";
    public static final String AWS_DISCOVERY_SECRET_KEY = "secret-key";
    public static final String AWS_DISCOVERY_IAM_ROLE = "iam-role";
    public static final String AWS_DISCOVERY_REGION = "region";
    public static final String AWS_DISCOVERY_HOST_HEADER = "host-header";
    public static final String AWS_DISCOVERY_SECURITY_GROUP_NAME = "security-group-name";
    public static final String AWS_DISCOVERY_TAG_KEY = "tag-key";
    public static final String AWS_DISCOVERY_TAG_VALUE = "tag-value";
    public static final String AWS_DISCOVERY_PORT = "hz-port";
    private static final long serialVersionUID = -8281247687171101766L;
    @RequiredProperty
    private String accessKey;
    @RequiredProperty
    private String secretKey;
    private String iamRole;
    private String region = "us-east-1";
    private String hostHeader;
    private String securityGroupName;
    private String tagKey;
    private String tagValue;
    private int port = -1;
    private int connectionTimeoutSeconds = 5;

    @Generated
    public String getAccessKey() {
        return this.accessKey;
    }

    @Generated
    public String getSecretKey() {
        return this.secretKey;
    }

    @Generated
    public String getIamRole() {
        return this.iamRole;
    }

    @Generated
    public String getRegion() {
        return this.region;
    }

    @Generated
    public String getHostHeader() {
        return this.hostHeader;
    }

    @Generated
    public String getSecurityGroupName() {
        return this.securityGroupName;
    }

    @Generated
    public String getTagKey() {
        return this.tagKey;
    }

    @Generated
    public String getTagValue() {
        return this.tagValue;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public int getConnectionTimeoutSeconds() {
        return this.connectionTimeoutSeconds;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setIamRole(String iamRole) {
        this.iamRole = iamRole;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setRegion(String region) {
        this.region = region;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setHostHeader(String hostHeader) {
        this.hostHeader = hostHeader;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setSecurityGroupName(String securityGroupName) {
        this.securityGroupName = securityGroupName;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setTagKey(String tagKey) {
        this.tagKey = tagKey;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setTagValue(String tagValue) {
        this.tagValue = tagValue;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public HazelcastAwsDiscoveryProperties setConnectionTimeoutSeconds(int connectionTimeoutSeconds) {
        this.connectionTimeoutSeconds = connectionTimeoutSeconds;
        return this;
    }
}

