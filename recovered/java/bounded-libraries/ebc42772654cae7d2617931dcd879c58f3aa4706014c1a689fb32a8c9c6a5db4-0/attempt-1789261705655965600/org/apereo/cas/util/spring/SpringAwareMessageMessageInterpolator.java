/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.MessageInterpolator
 *  javax.validation.MessageInterpolator$Context
 *  javax.validation.Validation
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.NoSuchMessageException
 *  org.springframework.context.i18n.LocaleContextHolder
 */
package org.apereo.cas.util.spring;

import java.util.Locale;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

public class SpringAwareMessageMessageInterpolator
implements MessageInterpolator,
MessageSourceAware {
    private final MessageInterpolator defaultMessageInterpolator = Validation.byDefaultProvider().configure().getDefaultMessageInterpolator();
    private MessageSource messageSource;

    public String interpolate(String s, MessageInterpolator.Context context) {
        return this.interpolate(s, context, LocaleContextHolder.getLocale());
    }

    public String interpolate(String s, MessageInterpolator.Context context, Locale locale) {
        try {
            return this.messageSource.getMessage(s, context.getConstraintDescriptor().getAttributes().values().toArray(ArrayUtils.EMPTY_OBJECT_ARRAY), locale);
        }
        catch (NoSuchMessageException e) {
            return this.defaultMessageInterpolator.interpolate(s, context, locale);
        }
    }

    @Generated
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}

