/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.format.Formatter
 *  org.springframework.format.FormatterRegistry
 */
package org.springframework.boot.context.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;

class ConversionServiceDeducer {
    private final ApplicationContext applicationContext;

    ConversionServiceDeducer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    List<ConversionService> getConversionServices() {
        if (this.hasUserDefinedConfigurationServiceBean()) {
            return Collections.singletonList(this.applicationContext.getBean("conversionService", ConversionService.class));
        }
        if (this.applicationContext instanceof ConfigurableApplicationContext) {
            return this.getConversionServices((ConfigurableApplicationContext)this.applicationContext);
        }
        return null;
    }

    private List<ConversionService> getConversionServices(ConfigurableApplicationContext applicationContext) {
        ConverterBeans converterBeans;
        ArrayList<ConversionService> conversionServices = new ArrayList<ConversionService>();
        if (applicationContext.getBeanFactory().getConversionService() != null) {
            conversionServices.add(applicationContext.getBeanFactory().getConversionService());
        }
        if (!(converterBeans = new ConverterBeans(applicationContext)).isEmpty()) {
            ApplicationConversionService beansConverterService = new ApplicationConversionService();
            converterBeans.addTo((FormatterRegistry)beansConverterService);
            conversionServices.add((ConversionService)beansConverterService);
        }
        return conversionServices;
    }

    private boolean hasUserDefinedConfigurationServiceBean() {
        String beanName = "conversionService";
        return this.applicationContext.containsBean(beanName) && this.applicationContext.getAutowireCapableBeanFactory().isTypeMatch(beanName, ConversionService.class);
    }

    private static class ConverterBeans {
        private final List<Converter> converters;
        private final List<GenericConverter> genericConverters;
        private final List<Formatter> formatters;

        ConverterBeans(ConfigurableApplicationContext applicationContext) {
            ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
            this.converters = this.beans(Converter.class, "org.springframework.boot.context.properties.ConfigurationPropertiesBinding", (ListableBeanFactory)beanFactory);
            this.genericConverters = this.beans(GenericConverter.class, "org.springframework.boot.context.properties.ConfigurationPropertiesBinding", (ListableBeanFactory)beanFactory);
            this.formatters = this.beans(Formatter.class, "org.springframework.boot.context.properties.ConfigurationPropertiesBinding", (ListableBeanFactory)beanFactory);
        }

        private <T> List<T> beans(Class<T> type, String qualifier, ListableBeanFactory beanFactory) {
            return new ArrayList(BeanFactoryAnnotationUtils.qualifiedBeansOfType((ListableBeanFactory)beanFactory, type, (String)qualifier).values());
        }

        boolean isEmpty() {
            return this.converters.isEmpty() && this.genericConverters.isEmpty() && this.formatters.isEmpty();
        }

        void addTo(FormatterRegistry registry) {
            for (Converter converter : this.converters) {
                registry.addConverter(converter);
            }
            for (GenericConverter genericConverter : this.genericConverters) {
                registry.addConverter(genericConverter);
            }
            for (Formatter formatter : this.formatters) {
                registry.addFormatter(formatter);
            }
        }
    }
}

