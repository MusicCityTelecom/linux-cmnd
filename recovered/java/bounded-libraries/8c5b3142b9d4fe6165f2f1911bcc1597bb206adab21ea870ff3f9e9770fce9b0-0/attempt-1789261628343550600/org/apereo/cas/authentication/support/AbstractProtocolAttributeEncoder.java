/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.ProtocolAttributeEncoder
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceCipherExecutor
 *  org.apereo.cas.services.ServicesManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceCipherExecutor;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProtocolAttributeEncoder
implements ProtocolAttributeEncoder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProtocolAttributeEncoder.class);
    protected final ServicesManager servicesManager;
    private final RegisteredServiceCipherExecutor cipherExecutor;

    public Map<String, Object> encodeAttributes(Map<String, Object> attributes, RegisteredService registeredService, WebApplicationService webApplicationService) {
        LOGGER.trace("Starting to encode attributes for release to service [{}]", (Object)registeredService);
        HashMap<String, Object> newEncodedAttributes = new HashMap<String, Object>(attributes);
        if (registeredService != null && registeredService.getAccessStrategy().isServiceAccessAllowed()) {
            Map<String, String> cachedAttributesToEncode = this.initialize(newEncodedAttributes);
            this.encodeAttributesInternal(newEncodedAttributes, cachedAttributesToEncode, this.cipherExecutor, registeredService, webApplicationService);
            LOGGER.debug("[{}] encoded attributes are available for release to [{}]: [{}]", new Object[]{newEncodedAttributes.size(), registeredService.getName(), newEncodedAttributes.keySet()});
        } else {
            LOGGER.debug("Service is not found/enabled in the service registry so no encoding has taken place.");
        }
        return newEncodedAttributes;
    }

    protected abstract void encodeAttributesInternal(Map<String, Object> var1, Map<String, String> var2, RegisteredServiceCipherExecutor var3, RegisteredService var4, WebApplicationService var5);

    protected Map<String, String> initialize(Map<String, Object> attributes) {
        HashMap<String, String> cachedAttributesToEncode = new HashMap<String, String>();
        AbstractProtocolAttributeEncoder.removeAttributeAndCacheForEncoding(attributes, cachedAttributesToEncode, "credential");
        AbstractProtocolAttributeEncoder.removeAttributeAndCacheForEncoding(attributes, cachedAttributesToEncode, "proxyGrantingTicket");
        AbstractProtocolAttributeEncoder.removeAttributeAndCacheForEncoding(attributes, cachedAttributesToEncode, "pgtIou");
        return cachedAttributesToEncode;
    }

    private static void removeAttributeAndCacheForEncoding(Map<String, Object> attributes, Map<String, String> cachedAttributesToEncode, String attributeName) {
        String messageFormat = "Removed [{}] as an authentication attribute and cached it locally.";
        Collection collection = (Collection)attributes.remove(attributeName);
        if (collection != null && collection.size() == 1) {
            cachedAttributesToEncode.put(attributeName, collection.iterator().next().toString());
            LOGGER.debug("Removed [{}] as an authentication attribute and cached it locally.", (Object)attributeName);
        }
    }

    @Generated
    protected AbstractProtocolAttributeEncoder(ServicesManager servicesManager, RegisteredServiceCipherExecutor cipherExecutor) {
        this.servicesManager = servicesManager;
        this.cipherExecutor = cipherExecutor;
    }
}

