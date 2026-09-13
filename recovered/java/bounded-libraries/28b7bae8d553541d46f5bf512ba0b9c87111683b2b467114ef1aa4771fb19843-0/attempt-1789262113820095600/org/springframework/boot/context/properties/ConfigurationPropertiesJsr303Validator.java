/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.MessageSource
 *  org.springframework.util.ClassUtils
 *  org.springframework.validation.Errors
 *  org.springframework.validation.Validator
 *  org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
 */
package org.springframework.boot.context.properties;

import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

final class ConfigurationPropertiesJsr303Validator
implements Validator {
    private static final String[] VALIDATOR_CLASSES = new String[]{"javax.validation.Validator", "javax.validation.ValidatorFactory", "javax.validation.bootstrap.GenericBootstrap"};
    private final Delegate delegate;

    ConfigurationPropertiesJsr303Validator(ApplicationContext applicationContext) {
        this.delegate = new Delegate(applicationContext);
    }

    public boolean supports(Class<?> type) {
        return this.delegate.supports(type);
    }

    public void validate(Object target, Errors errors) {
        this.delegate.validate(target, errors);
    }

    static boolean isJsr303Present(ApplicationContext applicationContext) {
        ClassLoader classLoader = applicationContext.getClassLoader();
        for (String validatorClass : VALIDATOR_CLASSES) {
            if (ClassUtils.isPresent((String)validatorClass, (ClassLoader)classLoader)) continue;
            return false;
        }
        return true;
    }

    private static class Delegate
    extends LocalValidatorFactoryBean {
        Delegate(ApplicationContext applicationContext) {
            this.setApplicationContext(applicationContext);
            this.setMessageInterpolator(new MessageInterpolatorFactory((MessageSource)applicationContext).getObject());
            this.afterPropertiesSet();
        }
    }
}

