/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.MessageInterpolator
 *  javax.validation.Validation
 *  javax.validation.ValidationException
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.ObjectFactory
 *  org.springframework.context.MessageSource
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.validation;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.validation.MessageSourceMessageInterpolator;
import org.springframework.context.MessageSource;
import org.springframework.util.ClassUtils;

public class MessageInterpolatorFactory
implements ObjectFactory<MessageInterpolator> {
    private static final Set<String> FALLBACKS;
    private final MessageSource messageSource;

    public MessageInterpolatorFactory() {
        this(null);
    }

    public MessageInterpolatorFactory(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageInterpolator getObject() throws BeansException {
        MessageInterpolator messageInterpolator = this.getMessageInterpolator();
        if (this.messageSource != null) {
            return new MessageSourceMessageInterpolator(this.messageSource, messageInterpolator);
        }
        return messageInterpolator;
    }

    private MessageInterpolator getMessageInterpolator() {
        try {
            return Validation.byDefaultProvider().configure().getDefaultMessageInterpolator();
        }
        catch (ValidationException ex) {
            MessageInterpolator fallback = this.getFallback();
            if (fallback != null) {
                return fallback;
            }
            throw ex;
        }
    }

    private MessageInterpolator getFallback() {
        for (String fallback : FALLBACKS) {
            try {
                return this.getFallback(fallback);
            }
            catch (Exception exception) {
            }
        }
        return null;
    }

    private MessageInterpolator getFallback(String fallback) {
        Class interpolatorClass = ClassUtils.resolveClassName((String)fallback, null);
        Object interpolator = BeanUtils.instantiateClass((Class)interpolatorClass);
        return (MessageInterpolator)interpolator;
    }

    static {
        LinkedHashSet<String> fallbacks = new LinkedHashSet<String>();
        fallbacks.add("org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator");
        FALLBACKS = Collections.unmodifiableSet(fallbacks);
    }
}

