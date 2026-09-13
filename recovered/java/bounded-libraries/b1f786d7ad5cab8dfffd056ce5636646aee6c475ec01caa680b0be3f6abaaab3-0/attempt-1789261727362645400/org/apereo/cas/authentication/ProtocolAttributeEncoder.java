/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.apache.commons.codec.binary.Hex
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import com.google.common.collect.Maps;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ProtocolAttributeEncoder {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProtocolAttributeEncoder.class);
    public static final String ENCODED_ATTRIBUTE_PREFIX = "_";

    default public Map<String, Object> encodeAttributes(Map<String, Object> attributes, RegisteredService registeredService, WebApplicationService webApplicationService) {
        HashMap finalAttributes = Maps.newHashMapWithExpectedSize((int)attributes.size());
        attributes.forEach((k, v) -> {
            String attributeName = ProtocolAttributeEncoder.decodeAttribute(k);
            LOGGER.debug("Decoded attribute [{}] to [{}] with value(s) [{}]", new Object[]{k, attributeName, v});
            finalAttributes.put(attributeName, v);
        });
        return finalAttributes;
    }

    public static boolean isAttributeNameEncoded(String name) {
        return name.startsWith(ENCODED_ATTRIBUTE_PREFIX);
    }

    public static String encodeAttribute(String s) {
        return ENCODED_ATTRIBUTE_PREFIX + new String(Hex.encodeHex((byte[])s.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decodeAttribute(String s) {
        try {
            if (ProtocolAttributeEncoder.isAttributeNameEncoded(s)) {
                return new String(Hex.decodeHex((String)s.substring(1)), StandardCharsets.UTF_8);
            }
        }
        catch (Exception e) {
            LOGGER.trace("Unable to decode attribute [{}]: [{}]", (Object)s, (Object)e.getMessage());
        }
        return s;
    }

    public static Map<String, Object> decodeAttributes(Map<String, Object> attributes, RegisteredService registeredService, WebApplicationService webApplicationService) {
        HashMap finalAttributes = Maps.newHashMapWithExpectedSize((int)attributes.size());
        attributes.forEach((k, v) -> {
            String attributeName = ProtocolAttributeEncoder.decodeAttribute(k);
            LOGGER.debug("Decoded SAML attribute [{}] to [{}] with value(s) [{}]", new Object[]{k, attributeName, v});
            finalAttributes.put(attributeName, v);
        });
        return finalAttributes;
    }
}

