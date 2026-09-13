/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.util.CasVersion
 *  org.apereo.cas.util.LoggingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.ConfigurationPropertiesBean
 *  org.springframework.boot.context.properties.bind.BindException
 *  org.springframework.boot.context.properties.bind.BindHandler
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.boot.context.properties.bind.PlaceholdersResolver
 *  org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver
 *  org.springframework.boot.context.properties.bind.UnboundConfigurationPropertiesException
 *  org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler
 *  org.springframework.boot.context.properties.bind.handler.NoUnboundElementsBindHandler
 *  org.springframework.boot.context.properties.source.ConfigurationPropertySources
 *  org.springframework.boot.context.properties.source.UnboundElementsSourceFilter
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.convert.ConversionService
 */
package org.apereo.cas.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.util.CasVersion;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PlaceholdersResolver;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.bind.UnboundConfigurationPropertiesException;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.boot.context.properties.bind.handler.NoUnboundElementsBindHandler;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.UnboundElementsSourceFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;

public class CasConfigurationPropertiesValidator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasConfigurationPropertiesValidator.class);
    private final ConfigurableApplicationContext applicationContext;

    public List<String> validate() {
        ArrayList<String> validationResults = new ArrayList<String>(0);
        this.validateCasConfiguration(validationResults);
        if (validationResults.isEmpty()) {
            LOGGER.info("Validated CAS property sources and configuration successfully.");
        } else {
            Object message = String.join((CharSequence)"\n", validationResults);
            message = (String)message + "\n\nListed settings above are no longer recognized by CAS " + CasVersion.getVersion() + ". They may have been renamed, removed, or relocated to a new namespace in the CAS configuration schema. CAS will ignore such settings to proceed with its normal initialization sequence. Please consult the CAS documentation to review and adjust each setting to find an alternative or remove the definition from the property source. Failure to do so puts the server stability in danger and complicates future upgrades.\n";
            LOGGER.error((String)message);
        }
        return validationResults;
    }

    private void validateCasConfiguration(List<String> validationResults) {
        try {
            this.validateConfiguration(CasConfigurationProperties.class, validationResults);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
        }
    }

    private void validateConfiguration(Class clazz, List<String> validationResults) {
        Map beans = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)this.applicationContext.getBeanFactory(), (Class)clazz);
        beans.values().forEach(bean -> {
            ConfigurationPropertiesBean configBean = ConfigurationPropertiesBean.get((ApplicationContext)this.applicationContext, (Object)bean, (String)UUID.randomUUID().toString());
            Bindable target = configBean.asBindTarget();
            ConfigurationProperties annotation = configBean.getAnnotation();
            NoUnboundElementsBindHandler handler = new NoUnboundElementsBindHandler((BindHandler)new IgnoreTopLevelConverterNotFoundBindHandler(), (Function)new UnboundElementsSourceFilter());
            Binder configBinder = new Binder(ConfigurationPropertySources.from((Iterable)this.applicationContext.getEnvironment().getPropertySources()), (PlaceholdersResolver)new PropertySourcesPlaceholdersResolver((Iterable)this.applicationContext.getEnvironment().getPropertySources()), (ConversionService)this.applicationContext.getEnvironment().getConversionService(), null, null, null);
            try {
                configBinder.bind(annotation.prefix(), target, (BindHandler)handler);
            }
            catch (BindException e) {
                UnboundConfigurationPropertiesException cause;
                Object message = "\n".concat(e.getMessage()).concat("\n");
                if (e.getCause() != null && (cause = (UnboundConfigurationPropertiesException)e.getCause()) != null) {
                    message = (String)message + cause.getUnboundProperties().stream().map(property -> String.format("%n\t%s = %s (Origin: %s)", property.getName(), property.getValue(), property.getOrigin())).collect(Collectors.joining("\n"));
                }
                validationResults.add((String)message);
            }
        });
    }

    @Generated
    public CasConfigurationPropertiesValidator(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}

