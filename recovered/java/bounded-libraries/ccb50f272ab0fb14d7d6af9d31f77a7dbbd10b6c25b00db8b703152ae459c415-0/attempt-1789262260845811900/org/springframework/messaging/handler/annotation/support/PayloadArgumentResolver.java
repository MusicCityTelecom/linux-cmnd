/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.validation.Validator
 */
package org.springframework.messaging.handler.annotation.support;

import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.validation.Validator;

@Deprecated
public class PayloadArgumentResolver
extends PayloadMethodArgumentResolver {
    public PayloadArgumentResolver(MessageConverter messageConverter) {
        this(messageConverter, null);
    }

    public PayloadArgumentResolver(MessageConverter messageConverter, @Nullable Validator validator) {
        this(messageConverter, validator, true);
    }

    public PayloadArgumentResolver(MessageConverter messageConverter, @Nullable Validator validator, boolean useDefaultResolution) {
        super(messageConverter, validator, useDefaultResolution);
    }
}

