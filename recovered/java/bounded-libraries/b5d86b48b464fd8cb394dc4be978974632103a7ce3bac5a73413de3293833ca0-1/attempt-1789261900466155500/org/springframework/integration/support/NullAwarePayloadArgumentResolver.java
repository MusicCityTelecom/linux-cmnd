/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver
 *  org.springframework.validation.Validator
 */
package org.springframework.integration.support;

import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.validation.Validator;

public class NullAwarePayloadArgumentResolver
extends PayloadMethodArgumentResolver {
    public NullAwarePayloadArgumentResolver(MessageConverter messageConverter) {
        super(messageConverter, null, false);
    }

    public NullAwarePayloadArgumentResolver(MessageConverter messageConverter, Validator validator) {
        super(messageConverter, validator, false);
    }

    protected boolean isEmptyPayload(Object payload) {
        return super.isEmptyPayload(payload) || "KafkaNull".equals(payload.getClass().getSimpleName());
    }
}

