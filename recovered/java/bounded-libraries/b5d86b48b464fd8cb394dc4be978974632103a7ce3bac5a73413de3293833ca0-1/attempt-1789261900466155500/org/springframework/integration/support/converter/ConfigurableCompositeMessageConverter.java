/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.messaging.converter.ByteArrayMessageConverter
 *  org.springframework.messaging.converter.CompositeMessageConverter
 *  org.springframework.messaging.converter.GenericMessageConverter
 *  org.springframework.messaging.converter.MappingJackson2MessageConverter
 *  org.springframework.messaging.converter.MessageConverter
 */
package org.springframework.integration.support.converter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.integration.support.converter.ObjectStringMessageConverter;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JacksonPresent;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.GenericMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

public class ConfigurableCompositeMessageConverter
extends CompositeMessageConverter
implements BeanFactoryAware,
InitializingBean {
    private final boolean registerDefaults;
    private BeanFactory beanFactory;

    public ConfigurableCompositeMessageConverter() {
        super(ConfigurableCompositeMessageConverter.initDefaults());
        this.registerDefaults = true;
    }

    public ConfigurableCompositeMessageConverter(Collection<MessageConverter> converters) {
        this(converters, false);
    }

    public ConfigurableCompositeMessageConverter(Collection<MessageConverter> converters, boolean registerDefaults) {
        super(registerDefaults ? (Collection)Stream.concat(converters.stream(), ConfigurableCompositeMessageConverter.initDefaults().stream()).collect(Collectors.toList()) : converters);
        this.registerDefaults = registerDefaults;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void afterPropertiesSet() {
        if (this.registerDefaults) {
            ConversionService conversionService = IntegrationUtils.getConversionService(this.beanFactory);
            if (conversionService == null) {
                conversionService = DefaultConversionService.getSharedInstance();
            }
            this.getConverters().add(new GenericMessageConverter(conversionService));
        }
    }

    private static Collection<MessageConverter> initDefaults() {
        LinkedList<MessageConverter> converters = new LinkedList<MessageConverter>();
        if (JacksonPresent.isJackson2Present()) {
            MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
            mappingJackson2MessageConverter.setStrictContentTypeMatch(true);
            mappingJackson2MessageConverter.setObjectMapper(new Jackson2JsonObjectMapper().getObjectMapper());
            converters.add((MessageConverter)mappingJackson2MessageConverter);
        }
        converters.add((MessageConverter)new ByteArrayMessageConverter());
        converters.add((MessageConverter)new ObjectStringMessageConverter());
        return converters;
    }
}

