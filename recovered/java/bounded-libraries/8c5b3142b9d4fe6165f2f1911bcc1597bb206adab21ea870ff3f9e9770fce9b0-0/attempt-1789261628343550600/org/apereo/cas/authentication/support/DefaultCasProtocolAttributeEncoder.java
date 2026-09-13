/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicates
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.authentication.ProtocolAttributeEncoder
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceCipherExecutor
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.EncodingUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.support;

import com.google.common.base.Predicates;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.authentication.support.AbstractProtocolAttributeEncoder;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceCipherExecutor;
import org.apereo.cas.services.RegisteredServicePublicKeyCipherExecutor;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCasProtocolAttributeEncoder
extends AbstractProtocolAttributeEncoder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCasProtocolAttributeEncoder.class);
    private final CipherExecutor<String, String> cacheCredentialCipherExecutor;

    public DefaultCasProtocolAttributeEncoder(ServicesManager servicesManager, CipherExecutor<String, String> cacheCredentialCipherExecutor) {
        this(servicesManager, new RegisteredServicePublicKeyCipherExecutor(), cacheCredentialCipherExecutor);
    }

    public DefaultCasProtocolAttributeEncoder(ServicesManager servicesManager, RegisteredServiceCipherExecutor cipherExecutor, CipherExecutor<String, String> cacheCredentialCipherExecutor) {
        super(servicesManager, cipherExecutor);
        this.cacheCredentialCipherExecutor = cacheCredentialCipherExecutor;
    }

    private static void sanitizeAndTransformAttributeNames(Map<String, Object> attributes, WebApplicationService webApplicationService) {
        if (webApplicationService != null && webApplicationService.getFormat() != null && !webApplicationService.getFormat().isEncodingNecessary()) {
            LOGGER.trace("Skipping attribute name sanitization for [{}]", (Object)webApplicationService);
            return;
        }
        LOGGER.trace("Sanitizing attribute names in preparation of the final validation response");
        Set<Pair> attrs = attributes.keySet().stream().filter(DefaultCasProtocolAttributeEncoder::getSanitizingAttributeNamePredicate).map(s -> {
            Object values = attributes.get(s);
            LOGGER.trace("Encoding attribute [{}] with value(s) [{}]", s, values);
            return Pair.of((Object)ProtocolAttributeEncoder.encodeAttribute((String)s), values);
        }).collect(Collectors.toSet());
        if (!attrs.isEmpty()) {
            LOGGER.info("Found [{}] attribute(s) that need to be sanitized/encoded.", (Object)attrs.size());
            attributes.keySet().removeIf(DefaultCasProtocolAttributeEncoder::getSanitizingAttributeNamePredicate);
            attrs.forEach(p -> {
                String key = (String)p.getKey();
                LOGGER.trace("Sanitized attribute name to be [{}]", (Object)key);
                attributes.put(key, DefaultCasProtocolAttributeEncoder.transformAttributeValueIfNecessary(p.getValue()));
            });
        }
    }

    private static boolean getSanitizingAttributeNamePredicate(String s) {
        return s.contains(":") || s.contains("@");
    }

    private static void sanitizeAndTransformAttributeValues(Map<String, Object> attributes) {
        LOGGER.trace("Sanitizing attribute values in preparation of the final validation response");
        attributes.forEach((key, value) -> {
            Set values = CollectionUtils.toCollection((Object)value);
            values.stream().filter(v -> DefaultCasProtocolAttributeEncoder.getBinaryAttributeValuePredicate().test(v)).forEach(v -> attributes.put((String)key, DefaultCasProtocolAttributeEncoder.transformAttributeValueIfNecessary(v)));
        });
    }

    private static Object transformAttributeValueIfNecessary(Object attributeValue) {
        if (DefaultCasProtocolAttributeEncoder.getBinaryAttributeValuePredicate().test(attributeValue)) {
            return EncodingUtils.encodeBase64((byte[])((byte[])attributeValue));
        }
        return attributeValue;
    }

    private static Predicate<Object> getBinaryAttributeValuePredicate() {
        return Predicates.instanceOf(byte[].class);
    }

    protected void encodeAndEncryptCredentialPassword(Map<String, Object> attributes, Map<String, String> cachedAttributesToEncode, RegisteredServiceCipherExecutor cipher, RegisteredService registeredService) {
        if (cachedAttributesToEncode.containsKey("credential")) {
            String value = cachedAttributesToEncode.get("credential");
            String decodedValue = (String)this.cacheCredentialCipherExecutor.decode((Object)value, ArrayUtils.EMPTY_OBJECT_ARRAY);
            cachedAttributesToEncode.remove("credential");
            if (StringUtils.isNotBlank((CharSequence)decodedValue)) {
                cachedAttributesToEncode.put("credential", decodedValue);
            }
        }
        this.encryptAndEncodeAndPutIntoAttributesMap(attributes, cachedAttributesToEncode, "credential", cipher, registeredService);
    }

    protected void encodeAndEncryptProxyGrantingTicket(Map<String, Object> attributes, Map<String, String> cachedAttributesToEncode, RegisteredServiceCipherExecutor cipher, RegisteredService registeredService) {
        this.encryptAndEncodeAndPutIntoAttributesMap(attributes, cachedAttributesToEncode, "proxyGrantingTicket", cipher, registeredService);
        this.encryptAndEncodeAndPutIntoAttributesMap(attributes, cachedAttributesToEncode, "pgtIou", cipher, registeredService);
    }

    protected void encryptAndEncodeAndPutIntoAttributesMap(Map<String, Object> attributes, Map<String, String> cachedAttributesToEncode, String cachedAttributeName, RegisteredServiceCipherExecutor cipher, RegisteredService registeredService) {
        String cachedAttribute = cachedAttributesToEncode.remove(cachedAttributeName);
        if (StringUtils.isNotBlank((CharSequence)cachedAttribute)) {
            LOGGER.trace("Retrieved [{}] as a cached model attribute...", (Object)cachedAttributeName);
            String encodedValue = cipher.encode(cachedAttribute, Optional.of(registeredService));
            if (StringUtils.isNotBlank((CharSequence)encodedValue)) {
                attributes.put(cachedAttributeName, encodedValue);
                LOGGER.trace("Encrypted and encoded [{}] as an attribute to [{}].", (Object)cachedAttributeName, (Object)encodedValue);
            } else {
                LOGGER.warn("Attribute [{}] cannot be encoded and is removed from the collection of attributes", (Object)cachedAttributeName);
            }
        } else {
            LOGGER.trace("[{}] is not available as a cached model attribute to encrypt...", (Object)cachedAttributeName);
        }
    }

    @Override
    protected void encodeAttributesInternal(Map<String, Object> attributes, Map<String, String> cachedAttributesToEncode, RegisteredServiceCipherExecutor cipher, RegisteredService registeredService, WebApplicationService webApplicationService) {
        this.encodeAndEncryptCredentialPassword(attributes, cachedAttributesToEncode, cipher, registeredService);
        this.encodeAndEncryptProxyGrantingTicket(attributes, cachedAttributesToEncode, cipher, registeredService);
        DefaultCasProtocolAttributeEncoder.sanitizeAndTransformAttributeNames(attributes, webApplicationService);
        DefaultCasProtocolAttributeEncoder.sanitizeAndTransformAttributeValues(attributes);
    }
}

