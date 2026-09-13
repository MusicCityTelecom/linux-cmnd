/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.converter.ConverterRegistry
 *  org.springframework.core.convert.support.ConversionServiceFactory
 *  org.springframework.core.convert.support.GenericConversionService
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.ConversionServiceFactory;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.integration.json.JsonNodeWrapperToJsonNodeConverter;
import org.springframework.integration.support.json.JacksonPresent;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.util.Assert;

class ConverterRegistrar
implements InitializingBean,
ApplicationContextAware {
    private final Set<Object> converters;
    private ApplicationContext applicationContext;

    ConverterRegistrar() {
        this(new HashSet<Object>());
    }

    ConverterRegistrar(Set<Object> converters) {
        this.converters = converters;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() {
        ConversionService conversionService = IntegrationUtils.getConversionService((BeanFactory)this.applicationContext);
        if (conversionService instanceof GenericConversionService) {
            this.registerConverters((GenericConversionService)conversionService);
        } else {
            Assert.notNull((Object)conversionService, () -> "Failed to locate 'integrationConversionService'");
        }
    }

    private void registerConverters(GenericConversionService conversionService) {
        this.converters.addAll(this.applicationContext.getBeansWithAnnotation(IntegrationConverter.class).values());
        if (JacksonPresent.isJackson2Present()) {
            this.converters.add(new JsonNodeWrapperToJsonNodeConverter());
        }
        ConversionServiceFactory.registerConverters(this.converters, (ConverterRegistry)conversionService);
    }
}

