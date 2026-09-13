/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.ProtocolAttributeEncoder
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.support;

import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOpProtocolAttributeEncoder
implements ProtocolAttributeEncoder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(NoOpProtocolAttributeEncoder.class);

    public Map<String, Object> encodeAttributes(Map<String, Object> attributes, RegisteredService registeredService, WebApplicationService webApplicationService) {
        LOGGER.warn("Attributes are not encoded via [{}]. Total of [{}] attributes will be returned for service [{}]", new Object[]{this.getClass().getSimpleName(), attributes.size(), registeredService});
        return new HashMap<String, Object>(attributes);
    }
}

