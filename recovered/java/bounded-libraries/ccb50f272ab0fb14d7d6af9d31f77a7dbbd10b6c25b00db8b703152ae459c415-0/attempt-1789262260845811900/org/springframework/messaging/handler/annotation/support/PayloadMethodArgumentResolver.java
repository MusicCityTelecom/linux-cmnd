/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.validation.BeanPropertyBindingResult
 *  org.springframework.validation.BindingResult
 *  org.springframework.validation.Errors
 *  org.springframework.validation.ObjectError
 *  org.springframework.validation.SmartValidator
 *  org.springframework.validation.Validator
 *  org.springframework.validation.annotation.Validated
 */
package org.springframework.messaging.handler.annotation.support;

import java.lang.annotation.Annotation;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SmartMessageConverter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

public class PayloadMethodArgumentResolver
implements HandlerMethodArgumentResolver {
    private final MessageConverter converter;
    @Nullable
    private final Validator validator;
    private final boolean useDefaultResolution;

    public PayloadMethodArgumentResolver(MessageConverter messageConverter) {
        this(messageConverter, null);
    }

    public PayloadMethodArgumentResolver(MessageConverter messageConverter, @Nullable Validator validator) {
        this(messageConverter, validator, true);
    }

    public PayloadMethodArgumentResolver(MessageConverter messageConverter, @Nullable Validator validator, boolean useDefaultResolution) {
        Assert.notNull((Object)messageConverter, (String)"MessageConverter must not be null");
        this.converter = messageConverter;
        this.validator = validator;
        this.useDefaultResolution = useDefaultResolution;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Payload.class) || this.useDefaultResolution;
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        Class<?> payloadClass;
        Payload ann = (Payload)parameter.getParameterAnnotation(Payload.class);
        if (ann != null && StringUtils.hasText((String)ann.expression())) {
            throw new IllegalStateException("@Payload SpEL expressions not supported by this resolver");
        }
        Object payload = message.getPayload();
        if (this.isEmptyPayload(payload)) {
            if (ann == null || ann.required()) {
                String paramName = this.getParameterName(parameter);
                BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(payload, paramName);
                bindingResult.addError(new ObjectError(paramName, "Payload value must not be empty"));
                throw new MethodArgumentNotValidException(message, parameter, (BindingResult)bindingResult);
            }
            return null;
        }
        Class<?> targetClass = this.resolveTargetClass(parameter, message);
        if (ClassUtils.isAssignable(targetClass, payloadClass = payload.getClass())) {
            this.validate(message, parameter, payload);
            return payload;
        }
        if (this.converter instanceof SmartMessageConverter) {
            SmartMessageConverter smartConverter = (SmartMessageConverter)this.converter;
            payload = smartConverter.fromMessage(message, targetClass, parameter);
        } else {
            payload = this.converter.fromMessage(message, targetClass);
        }
        if (payload == null) {
            throw new MessageConversionException(message, "Cannot convert from [" + payloadClass.getName() + "] to [" + targetClass.getName() + "] for " + message);
        }
        this.validate(message, parameter, payload);
        return payload;
    }

    private String getParameterName(MethodParameter param) {
        String paramName = param.getParameterName();
        return paramName != null ? paramName : "Arg " + param.getParameterIndex();
    }

    protected boolean isEmptyPayload(@Nullable Object payload) {
        if (payload == null) {
            return true;
        }
        if (payload instanceof byte[]) {
            return ((byte[])payload).length == 0;
        }
        if (payload instanceof String) {
            return !StringUtils.hasText((String)((String)payload));
        }
        return false;
    }

    protected Class<?> resolveTargetClass(MethodParameter parameter, Message<?> message) {
        return parameter.getParameterType();
    }

    protected void validate(Message<?> message, MethodParameter parameter, Object target) {
        if (this.validator == null) {
            return;
        }
        for (Annotation ann : parameter.getParameterAnnotations()) {
            Object[] objectArray;
            Object hints;
            Validated validatedAnn = (Validated)AnnotationUtils.getAnnotation((Annotation)ann, Validated.class);
            if (validatedAnn == null && !ann.annotationType().getSimpleName().startsWith("Valid")) continue;
            Object object = hints = validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue((Annotation)ann);
            if (hints instanceof Object[]) {
                objectArray = hints;
            } else {
                Object[] objectArray2 = new Object[1];
                objectArray = objectArray2;
                objectArray2[0] = hints;
            }
            Object[] validationHints = objectArray;
            BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, this.getParameterName(parameter));
            if (!ObjectUtils.isEmpty((Object[])validationHints) && this.validator instanceof SmartValidator) {
                ((SmartValidator)this.validator).validate(target, (Errors)bindingResult, validationHints);
            } else {
                this.validator.validate(target, (Errors)bindingResult);
            }
            if (!bindingResult.hasErrors()) break;
            throw new MethodArgumentNotValidException(message, parameter, (BindingResult)bindingResult);
        }
    }
}

