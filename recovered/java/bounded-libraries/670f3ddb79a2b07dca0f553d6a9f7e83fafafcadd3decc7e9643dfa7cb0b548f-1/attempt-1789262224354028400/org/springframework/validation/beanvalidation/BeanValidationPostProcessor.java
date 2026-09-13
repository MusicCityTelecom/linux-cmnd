/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validation
 *  javax.validation.Validator
 *  javax.validation.ValidatorFactory
 *  org.springframework.aop.framework.AopProxyUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.validation.beanvalidation;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class BeanValidationPostProcessor
implements BeanPostProcessor,
InitializingBean {
    @Nullable
    private Validator validator;
    private boolean afterInitialization = false;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validator = validatorFactory.getValidator();
    }

    public void setAfterInitialization(boolean afterInitialization) {
        this.afterInitialization = afterInitialization;
    }

    public void afterPropertiesSet() {
        if (this.validator == null) {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!this.afterInitialization) {
            this.doValidate(bean);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.afterInitialization) {
            this.doValidate(bean);
        }
        return bean;
    }

    protected void doValidate(Object bean) {
        Set result;
        Assert.state((this.validator != null ? 1 : 0) != 0, (String)"No Validator set");
        Object objectToValidate = AopProxyUtils.getSingletonTarget((Object)bean);
        if (objectToValidate == null) {
            objectToValidate = bean;
        }
        if (!(result = this.validator.validate(objectToValidate, new Class[0])).isEmpty()) {
            StringBuilder sb = new StringBuilder("Bean state is invalid: ");
            Iterator it = result.iterator();
            while (it.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation)it.next();
                sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
                if (!it.hasNext()) continue;
                sb.append("; ");
            }
            throw new BeanInitializationException(sb.toString());
        }
    }
}

