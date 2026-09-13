/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.PrettyPrinter
 *  com.fasterxml.jackson.core.util.DefaultPrettyPrinter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.services.util;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.serialization.AbstractJacksonBackedStringSerializer;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class BaseRegisteredServiceSerializer
extends AbstractJacksonBackedStringSerializer<RegisteredService> {
    private static final long serialVersionUID = -86170670153712101L;
    protected final ConfigurableApplicationContext applicationContext;

    protected BaseRegisteredServiceSerializer(ConfigurableApplicationContext applicationContext) {
        super((PrettyPrinter)new DefaultPrettyPrinter());
        this.applicationContext = applicationContext;
    }

    protected void configureObjectMapper(ObjectMapper mapper) {
        super.configureObjectMapper(mapper);
        JacksonObjectMapperFactory.configure((ConfigurableApplicationContext)this.applicationContext, (ObjectMapper)mapper);
    }
}

