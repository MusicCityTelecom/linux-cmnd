/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.principal.Principal
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServicePrincipalAttributesRepository
extends Serializable {
    public static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServicePrincipalAttributesRepository.class);

    public Map<String, List<Object>> getAttributes(Principal var1, RegisteredService var2);

    public Set<String> getAttributeRepositoryIds();

    default public void update(String id, Map<String, List<Object>> attributes, RegisteredService registeredService) {
        LOGGER.debug("Using [{}], no caching/update takes place for [{}] to add attributes [{}]", new Object[]{id, this.getClass().getSimpleName(), attributes});
    }
}

