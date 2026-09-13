/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.services.RegisteredServicePublicKey
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.crypto.PublicKeyFactoryBean
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.security.PublicKey;
import javax.crypto.Cipher;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.RegisteredServicePublicKey;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.crypto.PublicKeyFactoryBean;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RegisteredServicePublicKeyImpl
implements RegisteredServicePublicKey {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServicePublicKeyImpl.class);
    private static final long serialVersionUID = -8497658523695695863L;
    @ExpressionLanguageCapable
    private String location;
    private String algorithm = "RSA";

    public PublicKey createInstance() {
        return (PublicKey)Unchecked.supplier(() -> {
            if (StringUtils.isNotBlank((CharSequence)this.location)) {
                LOGGER.trace("Attempting to read public key from [{}]", (Object)this.location);
                PublicKeyFactoryBean factory = this.initializePublicKeyFactoryBean();
                return (PublicKey)factory.getObject();
            }
            LOGGER.warn("No public key location is defined");
            return null;
        }).get();
    }

    public Cipher toCipher() {
        return (Cipher)Unchecked.supplier(() -> {
            if (StringUtils.isNotBlank((CharSequence)this.location)) {
                LOGGER.trace("Attempting to initialize the cipher for public key [{}]", (Object)this.location);
                return this.initializePublicKeyFactoryBean().toCipher();
            }
            LOGGER.warn("NO public key location is defined");
            return null;
        }).get();
    }

    @JsonIgnore
    private PublicKeyFactoryBean initializePublicKeyFactoryBean() throws Exception {
        String resolved = SpringExpressionLanguageValueResolver.getInstance().resolve(this.location);
        AbstractResource resource = ResourceUtils.getResourceFrom((String)resolved);
        PublicKeyFactoryBean factory = new PublicKeyFactoryBean((Resource)resource, this.algorithm);
        factory.setSingleton(false);
        return factory;
    }

    @Generated
    public String toString() {
        return "RegisteredServicePublicKeyImpl(location=" + this.location + ", algorithm=" + this.algorithm + ")";
    }

    @Generated
    public String getLocation() {
        return this.location;
    }

    @Generated
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Generated
    public RegisteredServicePublicKeyImpl setLocation(String location) {
        this.location = location;
        return this;
    }

    @Generated
    public RegisteredServicePublicKeyImpl setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    @Generated
    public RegisteredServicePublicKeyImpl() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegisteredServicePublicKeyImpl)) {
            return false;
        }
        RegisteredServicePublicKeyImpl other = (RegisteredServicePublicKeyImpl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$location = this.location;
        String other$location = other.location;
        if (this$location == null ? other$location != null : !this$location.equals(other$location)) {
            return false;
        }
        String this$algorithm = this.algorithm;
        String other$algorithm = other.algorithm;
        return !(this$algorithm == null ? other$algorithm != null : !this$algorithm.equals(other$algorithm));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RegisteredServicePublicKeyImpl;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $location = this.location;
        result = result * 59 + ($location == null ? 43 : $location.hashCode());
        String $algorithm = this.algorithm;
        result = result * 59 + ($algorithm == null ? 43 : $algorithm.hashCode());
        return result;
    }

    @Generated
    public RegisteredServicePublicKeyImpl(String location, String algorithm) {
        this.location = location;
        this.algorithm = algorithm;
    }
}

