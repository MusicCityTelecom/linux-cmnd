/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.core.ResolvableType
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.Assert
 */
package org.springframework.integration.json;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.ResolvableType;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.mapping.support.JsonHeaders;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapperProvider;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;

public class JsonToObjectTransformer
extends AbstractTransformer
implements BeanClassLoaderAware {
    private final ResolvableType targetType;
    private final JsonObjectMapper<?, ?> jsonObjectMapper;
    private ClassLoader classLoader;
    private Expression valueTypeExpression = new FunctionExpression<Message>(message -> JsonToObjectTransformer.obtainResolvableTypeFromHeadersIfAny(message.getHeaders(), this.classLoader));
    private EvaluationContext evaluationContext;

    public JsonToObjectTransformer() {
        this((Class)null);
    }

    public JsonToObjectTransformer(@Nullable Class<?> targetClass) {
        this(ResolvableType.forClass(targetClass));
    }

    public JsonToObjectTransformer(ResolvableType targetType) {
        this(targetType, null);
    }

    public JsonToObjectTransformer(@Nullable JsonObjectMapper<?, ?> jsonObjectMapper) {
        this((Class)null, jsonObjectMapper);
    }

    public JsonToObjectTransformer(@Nullable Class<?> targetClass, @Nullable JsonObjectMapper<?, ?> jsonObjectMapper) {
        this(ResolvableType.forClass(targetClass), jsonObjectMapper);
    }

    public JsonToObjectTransformer(ResolvableType targetType, @Nullable JsonObjectMapper<?, ?> jsonObjectMapper) {
        Assert.notNull((Object)targetType, (String)"'targetType' must not be null");
        this.targetType = targetType;
        this.jsonObjectMapper = jsonObjectMapper != null ? jsonObjectMapper : JsonObjectMapperProvider.newInstance();
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        if (this.jsonObjectMapper instanceof BeanClassLoaderAware) {
            ((BeanClassLoaderAware)this.jsonObjectMapper).setBeanClassLoader(classLoader);
        }
    }

    public void setValueTypeExpressionString(String valueTypeExpressionString) {
        this.setValueTypeExpression(EXPRESSION_PARSER.parseExpression(valueTypeExpressionString));
    }

    public void setValueTypeExpression(Expression valueTypeExpression) {
        this.valueTypeExpression = valueTypeExpression;
    }

    @Override
    public String getComponentType() {
        return "json-to-object-transformer";
    }

    @Override
    protected void onInit() {
        super.onInit();
        this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
    }

    @Override
    protected Object doTransform(Message<?> message) {
        Object result;
        ResolvableType valueType = this.obtainResolvableType(message);
        boolean removeHeaders = false;
        if (valueType != null) {
            removeHeaders = true;
        } else {
            valueType = this.targetType;
        }
        try {
            result = this.jsonObjectMapper.fromJson(message.getPayload(), valueType);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        if (removeHeaders) {
            return this.getMessageBuilderFactory().withPayload(result).copyHeaders((Map<String, ?>)message.getHeaders()).removeHeaders(JsonHeaders.HEADERS.toArray(new String[0])).build();
        }
        return result;
    }

    @Nullable
    private ResolvableType obtainResolvableType(Message<?> message) {
        try {
            return (ResolvableType)this.valueTypeExpression.getValue(this.evaluationContext, message, ResolvableType.class);
        }
        catch (Exception ex) {
            if (ex.getCause() instanceof ClassNotFoundException) {
                this.logger.debug((Throwable)ex, () -> "Cannot build a ResolvableType from the request message '" + message + "' evaluating expression '" + this.valueTypeExpression.getExpressionString() + "'");
                return null;
            }
            throw ex;
        }
    }

    @Nullable
    private static ResolvableType obtainResolvableTypeFromHeadersIfAny(MessageHeaders headers, ClassLoader classLoader) {
        Object valueType = headers.get((Object)"json_resolvableType");
        Object typeIdHeader = headers.get((Object)"json__TypeId__");
        if (!(valueType instanceof ResolvableType) && typeIdHeader != null) {
            valueType = JsonHeaders.buildResolvableType(classLoader, typeIdHeader, headers.get((Object)"json__ContentTypeId__"), headers.get((Object)"json__KeyTypeId__"));
        }
        return valueType instanceof ResolvableType ? (ResolvableType)valueType : null;
    }
}

