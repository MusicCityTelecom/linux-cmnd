/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanExpressionContext
 *  org.springframework.beans.factory.config.BeanExpressionResolver
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 */
package org.springframework.messaging.handler.annotation.reactive;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.reactive.SyncHandlerMethodArgumentResolver;
import org.springframework.util.ClassUtils;

public abstract class AbstractNamedValueMethodArgumentResolver
implements SyncHandlerMethodArgumentResolver {
    private final ConversionService conversionService;
    @Nullable
    private final ConfigurableBeanFactory configurableBeanFactory;
    @Nullable
    private final BeanExpressionContext expressionContext;
    private final Map<MethodParameter, NamedValueInfo> namedValueInfoCache = new ConcurrentHashMap<MethodParameter, NamedValueInfo>(256);

    protected AbstractNamedValueMethodArgumentResolver(ConversionService conversionService, @Nullable ConfigurableBeanFactory beanFactory) {
        this.conversionService = conversionService;
        this.configurableBeanFactory = beanFactory;
        this.expressionContext = beanFactory != null ? new BeanExpressionContext(beanFactory, null) : null;
    }

    @Override
    public Object resolveArgumentValue(MethodParameter parameter, Message<?> message) {
        NamedValueInfo namedValueInfo = this.getNamedValueInfo(parameter);
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Object resolvedName = this.resolveEmbeddedValuesAndExpressions(namedValueInfo.name);
        if (resolvedName == null) {
            throw new IllegalArgumentException("Specified name must not resolve to null: [" + namedValueInfo.name + "]");
        }
        Object arg = this.resolveArgumentInternal(nestedParameter, message, resolvedName.toString());
        if (arg == null) {
            if (namedValueInfo.defaultValue != null) {
                arg = this.resolveEmbeddedValuesAndExpressions(namedValueInfo.defaultValue);
            } else if (namedValueInfo.required && !nestedParameter.isOptional()) {
                this.handleMissingValue(namedValueInfo.name, nestedParameter, message);
            }
            arg = this.handleNullValue(namedValueInfo.name, arg, nestedParameter.getNestedParameterType());
        } else if ("".equals(arg) && namedValueInfo.defaultValue != null) {
            arg = this.resolveEmbeddedValuesAndExpressions(namedValueInfo.defaultValue);
        }
        if (!(parameter == nestedParameter && ClassUtils.isAssignableValue((Class)parameter.getParameterType(), (Object)arg) || (arg = this.conversionService.convert(arg, TypeDescriptor.forObject((Object)arg), new TypeDescriptor(parameter))) != null || namedValueInfo.defaultValue != null || !namedValueInfo.required || nestedParameter.isOptional())) {
            this.handleMissingValue(namedValueInfo.name, nestedParameter, message);
        }
        return arg;
    }

    private NamedValueInfo getNamedValueInfo(MethodParameter parameter) {
        NamedValueInfo namedValueInfo = this.namedValueInfoCache.get(parameter);
        if (namedValueInfo == null) {
            namedValueInfo = this.createNamedValueInfo(parameter);
            namedValueInfo = this.updateNamedValueInfo(parameter, namedValueInfo);
            this.namedValueInfoCache.put(parameter, namedValueInfo);
        }
        return namedValueInfo;
    }

    protected abstract NamedValueInfo createNamedValueInfo(MethodParameter var1);

    private NamedValueInfo updateNamedValueInfo(MethodParameter parameter, NamedValueInfo info) {
        String name = info.name;
        if (info.name.isEmpty() && (name = parameter.getParameterName()) == null) {
            throw new IllegalArgumentException("Name for argument of type [" + parameter.getNestedParameterType().getName() + "] not specified, and parameter name information not found in class file either.");
        }
        return new NamedValueInfo(name, info.required, "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n".equals(info.defaultValue) ? null : info.defaultValue);
    }

    @Nullable
    private Object resolveEmbeddedValuesAndExpressions(String value) {
        if (this.configurableBeanFactory == null || this.expressionContext == null) {
            return value;
        }
        String placeholdersResolved = this.configurableBeanFactory.resolveEmbeddedValue(value);
        BeanExpressionResolver exprResolver = this.configurableBeanFactory.getBeanExpressionResolver();
        if (exprResolver == null) {
            return value;
        }
        return exprResolver.evaluate(placeholdersResolved, this.expressionContext);
    }

    @Nullable
    protected abstract Object resolveArgumentInternal(MethodParameter var1, Message<?> var2, String var3);

    protected abstract void handleMissingValue(String var1, MethodParameter var2, Message<?> var3);

    @Nullable
    private Object handleNullValue(String name, @Nullable Object value, Class<?> paramType) {
        if (value == null) {
            if (Boolean.TYPE.equals(paramType)) {
                return Boolean.FALSE;
            }
            if (paramType.isPrimitive()) {
                throw new IllegalStateException("Optional " + paramType + " parameter '" + name + "' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
            }
        }
        return value;
    }

    protected static class NamedValueInfo {
        private final String name;
        private final boolean required;
        @Nullable
        private final String defaultValue;

        protected NamedValueInfo(String name, boolean required, @Nullable String defaultValue) {
            this.name = name;
            this.required = required;
            this.defaultValue = defaultValue;
        }
    }
}

