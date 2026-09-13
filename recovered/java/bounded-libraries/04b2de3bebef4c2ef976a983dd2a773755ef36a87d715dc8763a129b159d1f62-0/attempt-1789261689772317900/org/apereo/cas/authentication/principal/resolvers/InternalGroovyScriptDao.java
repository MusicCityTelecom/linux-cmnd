/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.authentication.GroovyPrincipalAttributesProperties
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.services.persondir.support.BaseGroovyScriptDaoImpl
 *  org.apereo.services.persondir.support.IUsernameAttributeProvider
 *  org.apereo.services.persondir.support.SimpleUsernameAttributeProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.principal.resolvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.authentication.GroovyPrincipalAttributesProperties;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.services.persondir.support.BaseGroovyScriptDaoImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.SimpleUsernameAttributeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

public class InternalGroovyScriptDao
extends BaseGroovyScriptDaoImpl {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(InternalGroovyScriptDao.class);
    private final IUsernameAttributeProvider usernameAttributeProvider = new SimpleUsernameAttributeProvider("username");
    private final ApplicationContext applicationContext;
    private final CasConfigurationProperties casProperties;
    private final GroovyPrincipalAttributesProperties groovyPrincipalAttributesProperties;

    public Map<String, List<Object>> getPersonAttributesFromMultivaluedAttributes(Map<String, List<Object>> attributes) {
        String username = this.usernameAttributeProvider.getUsernameFromQuery(attributes);
        HashMap<String, List<Object>> results = new HashMap<String, List<Object>>();
        if (StringUtils.isNotBlank((CharSequence)username)) {
            Object[] args = new Object[]{username, attributes, LOGGER, this.casProperties, this.applicationContext};
            Map finalAttributes = (Map)ScriptingUtils.executeGroovyScript((Resource)this.groovyPrincipalAttributesProperties.getLocation(), (Object[])args, Map.class, (boolean)true);
            LOGGER.debug("Groovy-based attributes found are [{}]", (Object)finalAttributes);
            finalAttributes.forEach((k, v) -> {
                ArrayList values = new ArrayList(CollectionUtils.toCollection((Object)v));
                LOGGER.trace("Adding Groovy-based attribute [{}] with value(s) [{}]", k, values);
                results.put((String)k, values);
            });
        }
        return results;
    }

    @Generated
    public InternalGroovyScriptDao(ApplicationContext applicationContext, CasConfigurationProperties casProperties, GroovyPrincipalAttributesProperties groovyPrincipalAttributesProperties) {
        this.applicationContext = applicationContext;
        this.casProperties = casProperties;
        this.groovyPrincipalAttributesProperties = groovyPrincipalAttributesProperties;
    }
}

