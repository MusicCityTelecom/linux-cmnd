/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.Validator
 *  javax.validation.executable.ExecutableValidator
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.validation.MessageInterpolatorFactory
 *  org.springframework.boot.validation.beanvalidation.FilteredMethodValidationPostProcessor
 *  org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.MessageSource
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.context.annotation.Lazy
 *  org.springframework.context.annotation.Role
 *  org.springframework.core.env.Environment
 *  org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
 *  org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 */
package org.springframework.boot.autoconfigure.validation;

import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.validation.PrimaryDefaultValidatorPostProcessor;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.boot.validation.beanvalidation.FilteredMethodValidationPostProcessor;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@AutoConfiguration
@ConditionalOnClass(value={ExecutableValidator.class})
@ConditionalOnResource(resources={"classpath:META-INF/services/javax.validation.spi.ValidationProvider"})
@Import(value={PrimaryDefaultValidatorPostProcessor.class})
public class ValidationAutoConfiguration {
    @Bean
    @Role(value=2)
    @ConditionalOnMissingBean(value={Validator.class})
    public static LocalValidatorFactoryBean defaultValidator(ApplicationContext applicationContext) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory((MessageSource)applicationContext);
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        return factoryBean;
    }

    @Bean
    @ConditionalOnMissingBean(search=SearchStrategy.CURRENT)
    public static MethodValidationPostProcessor methodValidationPostProcessor(Environment environment, @Lazy Validator validator, ObjectProvider<MethodValidationExcludeFilter> excludeFilters) {
        FilteredMethodValidationPostProcessor processor = new FilteredMethodValidationPostProcessor(excludeFilters.orderedStream());
        boolean proxyTargetClass = (Boolean)environment.getProperty("spring.aop.proxy-target-class", Boolean.class, (Object)true);
        processor.setProxyTargetClass(proxyTargetClass);
        processor.setValidator(validator);
        return processor;
    }
}

