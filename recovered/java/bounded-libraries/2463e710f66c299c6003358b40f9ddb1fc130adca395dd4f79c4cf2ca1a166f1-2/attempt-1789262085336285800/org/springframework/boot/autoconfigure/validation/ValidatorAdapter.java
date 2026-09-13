/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.ValidationException
 *  javax.validation.Validator
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.boot.validation.MessageInterpolatorFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.MessageSource
 *  org.springframework.validation.Errors
 *  org.springframework.validation.SmartValidator
 *  org.springframework.validation.Validator
 *  org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean
 *  org.springframework.validation.beanvalidation.SpringValidatorAdapter
 */
package org.springframework.boot.autoconfigure.validation;

import javax.validation.ValidationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

public class ValidatorAdapter
implements SmartValidator,
ApplicationContextAware,
InitializingBean,
DisposableBean {
    private final SmartValidator target;
    private final boolean existingBean;

    ValidatorAdapter(SmartValidator target, boolean existingBean) {
        this.target = target;
        this.existingBean = existingBean;
    }

    public final Validator getTarget() {
        return this.target;
    }

    public boolean supports(Class<?> clazz) {
        return this.target.supports(clazz);
    }

    public void validate(Object target, Errors errors) {
        this.target.validate(target, errors);
    }

    public void validate(Object target, Errors errors, Object ... validationHints) {
        this.target.validate(target, errors, validationHints);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (!this.existingBean && this.target instanceof ApplicationContextAware) {
            ((ApplicationContextAware)this.target).setApplicationContext(applicationContext);
        }
    }

    public void afterPropertiesSet() throws Exception {
        if (!this.existingBean && this.target instanceof InitializingBean) {
            ((InitializingBean)this.target).afterPropertiesSet();
        }
    }

    public void destroy() throws Exception {
        if (!this.existingBean && this.target instanceof DisposableBean) {
            ((DisposableBean)this.target).destroy();
        }
    }

    public static Validator get(ApplicationContext applicationContext, Validator validator) {
        if (validator != null) {
            return ValidatorAdapter.wrap(validator, false);
        }
        return ValidatorAdapter.getExistingOrCreate(applicationContext);
    }

    private static Validator getExistingOrCreate(ApplicationContext applicationContext) {
        Validator existing = ValidatorAdapter.getExisting(applicationContext);
        if (existing != null) {
            return ValidatorAdapter.wrap(existing, true);
        }
        return ValidatorAdapter.create((MessageSource)applicationContext);
    }

    private static Validator getExisting(ApplicationContext applicationContext) {
        try {
            javax.validation.Validator validator = (javax.validation.Validator)applicationContext.getBean(javax.validation.Validator.class);
            if (validator instanceof Validator) {
                return (Validator)validator;
            }
            return new SpringValidatorAdapter(validator);
        }
        catch (NoSuchBeanDefinitionException ex) {
            return null;
        }
    }

    private static Validator create(MessageSource messageSource) {
        OptionalValidatorFactoryBean validator = new OptionalValidatorFactoryBean();
        try {
            MessageInterpolatorFactory factory = new MessageInterpolatorFactory(messageSource);
            validator.setMessageInterpolator(factory.getObject());
        }
        catch (ValidationException validationException) {
            // empty catch block
        }
        return ValidatorAdapter.wrap((Validator)validator, false);
    }

    private static Validator wrap(Validator validator, boolean existingBean) {
        if (validator instanceof javax.validation.Validator) {
            if (validator instanceof SpringValidatorAdapter) {
                return new ValidatorAdapter((SmartValidator)((SpringValidatorAdapter)validator), existingBean);
            }
            return new ValidatorAdapter((SmartValidator)new SpringValidatorAdapter((javax.validation.Validator)validator), existingBean);
        }
        return validator;
    }
}

