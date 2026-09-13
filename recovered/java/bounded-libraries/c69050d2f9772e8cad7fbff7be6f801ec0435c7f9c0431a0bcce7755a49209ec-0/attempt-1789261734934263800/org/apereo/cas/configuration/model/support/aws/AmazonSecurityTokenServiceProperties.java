/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.aws;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aws")
@JsonFilter(value="AmazonSecurityTokenServiceProperties")
public class AmazonSecurityTokenServiceProperties
extends BaseAmazonWebServicesProperties {
    private static final long serialVersionUID = 5426637051495147084L;
    private String principalAttributeName;
    private String principalAttributeValue;
    private boolean rbacEnabled;

    @Generated
    public String getPrincipalAttributeName() {
        return this.principalAttributeName;
    }

    @Generated
    public String getPrincipalAttributeValue() {
        return this.principalAttributeValue;
    }

    @Generated
    public boolean isRbacEnabled() {
        return this.rbacEnabled;
    }

    @Generated
    public AmazonSecurityTokenServiceProperties setPrincipalAttributeName(String principalAttributeName) {
        this.principalAttributeName = principalAttributeName;
        return this;
    }

    @Generated
    public AmazonSecurityTokenServiceProperties setPrincipalAttributeValue(String principalAttributeValue) {
        this.principalAttributeValue = principalAttributeValue;
        return this;
    }

    @Generated
    public AmazonSecurityTokenServiceProperties setRbacEnabled(boolean rbacEnabled) {
        this.rbacEnabled = rbacEnabled;
        return this;
    }
}

