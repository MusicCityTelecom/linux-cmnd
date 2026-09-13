/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.core.Conventions
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.DecodingException
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferUtils
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.validation.BeanPropertyBindingResult
 *  org.springframework.validation.BindingResult
 *  org.springframework.validation.Errors
 *  org.springframework.validation.SmartValidator
 *  org.springframework.validation.Validator
 *  org.springframework.validation.annotation.Validated
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.annotation.reactive;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PayloadMethodArgumentResolver
implements HandlerMethodArgumentResolver {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final List<Decoder<?>> decoders;
    @Nullable
    private final Validator validator;
    private final ReactiveAdapterRegistry adapterRegistry;
    private final boolean useDefaultResolution;

    public PayloadMethodArgumentResolver(List<? extends Decoder<?>> decoders, @Nullable Validator validator, @Nullable ReactiveAdapterRegistry registry, boolean useDefaultResolution) {
        Assert.isTrue((!CollectionUtils.isEmpty(decoders) ? 1 : 0) != 0, (String)"At least one Decoder is required");
        this.decoders = Collections.unmodifiableList(new ArrayList(decoders));
        this.validator = validator;
        this.adapterRegistry = registry != null ? registry : ReactiveAdapterRegistry.getSharedInstance();
        this.useDefaultResolution = useDefaultResolution;
    }

    public List<Decoder<?>> getDecoders() {
        return this.decoders;
    }

    @Nullable
    public Validator getValidator() {
        return this.validator;
    }

    public ReactiveAdapterRegistry getAdapterRegistry() {
        return this.adapterRegistry;
    }

    public boolean isUseDefaultResolution() {
        return this.useDefaultResolution;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Payload.class) || this.useDefaultResolution;
    }

    @Override
    public final Mono<Object> resolveArgument(MethodParameter parameter, Message<?> message) {
        Payload ann = (Payload)parameter.getParameterAnnotation(Payload.class);
        if (ann != null && StringUtils.hasText((String)ann.expression())) {
            throw new IllegalStateException("@Payload SpEL expressions not supported by this resolver");
        }
        MimeType mimeType = this.getMimeType(message);
        mimeType = mimeType != null ? mimeType : MimeTypeUtils.APPLICATION_OCTET_STREAM;
        Flux<DataBuffer> content = this.extractContent(parameter, message);
        return this.decodeContent(parameter, message, ann == null || ann.required(), content, mimeType);
    }

    private Flux<DataBuffer> extractContent(MethodParameter parameter, Message<?> message) {
        Object payload = message.getPayload();
        if (payload instanceof DataBuffer) {
            return Flux.just((Object)((DataBuffer)payload));
        }
        if (payload instanceof Publisher) {
            return Flux.from((Publisher)((Publisher)payload)).map(value -> {
                if (value instanceof DataBuffer) {
                    return (DataBuffer)value;
                }
                String className = value.getClass().getName();
                throw this.getUnexpectedPayloadError(message, parameter, "Publisher<" + className + ">");
            });
        }
        return Flux.error((Throwable)((Object)this.getUnexpectedPayloadError(message, parameter, payload.getClass().getName())));
    }

    private MethodArgumentResolutionException getUnexpectedPayloadError(Message<?> message, MethodParameter parameter, String actualType) {
        return new MethodArgumentResolutionException(message, parameter, "Expected DataBuffer or Publisher<DataBuffer> for the Message payload, actual: " + actualType);
    }

    @Nullable
    protected MimeType getMimeType(Message<?> message) {
        Object headerValue = message.getHeaders().get("contentType");
        if (headerValue == null) {
            return null;
        }
        if (headerValue instanceof String) {
            return MimeTypeUtils.parseMimeType((String)((String)headerValue));
        }
        if (headerValue instanceof MimeType) {
            return (MimeType)headerValue;
        }
        throw new IllegalArgumentException("Unexpected MimeType value: " + headerValue);
    }

    private Mono<Object> decodeContent(MethodParameter parameter, Message<?> message, boolean isContentRequired, Flux<DataBuffer> content, MimeType mimeType) {
        ResolvableType targetType = ResolvableType.forMethodParameter((MethodParameter)parameter);
        Class resolvedType = targetType.resolve();
        ReactiveAdapter adapter = resolvedType != null ? this.getAdapterRegistry().getAdapter(resolvedType) : null;
        ResolvableType elementType = adapter != null ? targetType.getGeneric(new int[0]) : targetType;
        isContentRequired = isContentRequired || adapter != null && !adapter.supportsEmpty();
        Consumer<Object> validator = this.getValidator(message, parameter);
        Map hints = Collections.emptyMap();
        for (Decoder<?> decoder : this.decoders) {
            if (!decoder.canDecode(elementType, mimeType)) continue;
            if (adapter != null && adapter.isMultiValue()) {
                Flux flux = content.filter(this::nonEmptyDataBuffer).map(buffer -> decoder.decode(buffer, elementType, mimeType, hints)).onErrorResume(ex -> Flux.error((Throwable)this.handleReadError(parameter, message, (Throwable)ex)));
                if (isContentRequired) {
                    flux = flux.switchIfEmpty((Publisher)Flux.error(() -> this.handleMissingBody(parameter, message)));
                }
                if (validator != null) {
                    flux = flux.doOnNext(validator);
                }
                return Mono.just((Object)adapter.fromPublisher((Publisher)flux));
            }
            Mono mono = content.next().filter(this::nonEmptyDataBuffer).map(buffer -> decoder.decode(buffer, elementType, mimeType, hints)).onErrorResume(ex -> Mono.error((Throwable)this.handleReadError(parameter, message, (Throwable)ex)));
            if (isContentRequired) {
                mono = mono.switchIfEmpty(Mono.error(() -> this.handleMissingBody(parameter, message)));
            }
            if (validator != null) {
                mono = mono.doOnNext(validator);
            }
            return adapter != null ? Mono.just((Object)adapter.fromPublisher((Publisher)mono)) : Mono.from((Publisher)mono);
        }
        return Mono.error((Throwable)((Object)new MethodArgumentResolutionException(message, parameter, "Cannot decode to [" + targetType + "]" + message)));
    }

    private boolean nonEmptyDataBuffer(DataBuffer buffer) {
        if (buffer.readableByteCount() > 0) {
            return true;
        }
        DataBufferUtils.release((DataBuffer)buffer);
        return false;
    }

    private Throwable handleReadError(MethodParameter parameter, Message<?> message, Throwable ex) {
        return ex instanceof DecodingException ? new MethodArgumentResolutionException(message, parameter, "Failed to read HTTP message", ex) : ex;
    }

    private MethodArgumentResolutionException handleMissingBody(MethodParameter param, Message<?> message) {
        return new MethodArgumentResolutionException(message, param, "Payload content is missing: " + param.getExecutable().toGenericString());
    }

    @Nullable
    private Consumer<Object> getValidator(Message<?> message, MethodParameter parameter) {
        if (this.validator == null) {
            return null;
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
            String name = Conventions.getVariableNameForParameter((MethodParameter)parameter);
            return target -> {
                BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, name);
                if (!ObjectUtils.isEmpty((Object[])validationHints) && this.validator instanceof SmartValidator) {
                    ((SmartValidator)this.validator).validate(target, (Errors)bindingResult, validationHints);
                } else {
                    this.validator.validate(target, (Errors)bindingResult);
                }
                if (bindingResult.hasErrors()) {
                    throw new MethodArgumentNotValidException(message, parameter, (BindingResult)bindingResult);
                }
            };
        }
        return null;
    }
}

