/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.apereo.cas.services.RegisteredServicePublicKey
 *  org.apereo.cas.util.EncodingUtils
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import lombok.Generated;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.services.RegisteredServicePublicKey;
import org.apereo.cas.util.EncodingUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class ReturnEncryptedAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnEncryptedAttributeReleasePolicy.class);
    private static final long serialVersionUID = -5771481877391140569L;
    private List<String> allowedAttributes = new ArrayList<String>(0);

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attrs) {
        return this.authorizeReleaseOfAllowedAttributes(context, attrs);
    }

    protected Map<String, List<Object>> authorizeReleaseOfAllowedAttributes(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attrs) {
        RegisteredServicePublicKey publicKey = context.getRegisteredService().getPublicKey();
        if (publicKey == null) {
            LOGGER.error("No public key is defined for service [{}]. No attributes will be released", (Object)context.getRegisteredService());
            return new HashMap<String, List<Object>>(0);
        }
        LOGGER.debug("Using service [{}] public key [{}] to initialize the cipher", (Object)context.getRegisteredService().getServiceId(), (Object)publicKey);
        Cipher cipher = publicKey.toCipher();
        if (cipher == null) {
            LOGGER.error("Unable to initialize cipher given the public key algorithm [{}]", (Object)publicKey.getAlgorithm());
            return new HashMap<String, List<Object>>(0);
        }
        TreeMap<String, List<Object>> resolvedAttributes = new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
        resolvedAttributes.putAll(attrs);
        HashMap<String, List<Object>> attributesToRelease = new HashMap<String, List<Object>>();
        this.getAllowedAttributes().stream().filter(resolvedAttributes::containsKey).forEach(attr -> {
            LOGGER.debug("Found attribute [{}] in the list of allowed attributes. Encoding...", attr);
            List encodedValues = ((List)resolvedAttributes.get(attr)).stream().map(Unchecked.function(value -> {
                LOGGER.trace("Encrypting attribute [{}] with value [{}]", attr, value);
                String result = EncodingUtils.encodeBase64((byte[])cipher.doFinal(value.toString().getBytes(StandardCharsets.UTF_8)));
                LOGGER.trace("Encrypted attribute [{}] with value [{}]", attr, (Object)result);
                return result;
            })).collect(Collectors.toList());
            attributesToRelease.put((String)attr, encodedValues);
        });
        return attributesToRelease;
    }

    @Override
    public List<String> determineRequestedAttributeDefinitions(RegisteredServiceAttributeReleasePolicyContext context) {
        return this.getAllowedAttributes();
    }

    @Override
    @Generated
    public String toString() {
        return "ReturnEncryptedAttributeReleasePolicy(super=" + super.toString() + ", allowedAttributes=" + this.allowedAttributes + ")";
    }

    @Generated
    public List<String> getAllowedAttributes() {
        return this.allowedAttributes;
    }

    @Generated
    public ReturnEncryptedAttributeReleasePolicy setAllowedAttributes(List<String> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
        return this;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReturnEncryptedAttributeReleasePolicy)) {
            return false;
        }
        ReturnEncryptedAttributeReleasePolicy other = (ReturnEncryptedAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        List<String> this$allowedAttributes = this.allowedAttributes;
        List<String> other$allowedAttributes = other.allowedAttributes;
        return !(this$allowedAttributes == null ? other$allowedAttributes != null : !((Object)this$allowedAttributes).equals(other$allowedAttributes));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ReturnEncryptedAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        List<String> $allowedAttributes = this.allowedAttributes;
        result = result * 59 + ($allowedAttributes == null ? 43 : ((Object)$allowedAttributes).hashCode());
        return result;
    }

    @Generated
    public ReturnEncryptedAttributeReleasePolicy() {
    }

    @Generated
    public ReturnEncryptedAttributeReleasePolicy(List<String> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
    }

    static {
        Security.addProvider((Provider)new BouncyCastleProvider());
    }
}

