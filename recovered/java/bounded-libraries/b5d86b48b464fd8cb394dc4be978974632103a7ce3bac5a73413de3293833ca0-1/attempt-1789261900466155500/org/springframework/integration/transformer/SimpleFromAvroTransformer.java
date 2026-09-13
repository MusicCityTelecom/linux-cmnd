/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.avro.io.Decoder
 *  org.apache.avro.io.DecoderFactory
 *  org.apache.avro.specific.SpecificDatumReader
 *  org.apache.avro.specific.SpecificRecord
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.transformer;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class SimpleFromAvroTransformer
extends AbstractTransformer
implements BeanClassLoaderAware {
    private final Class<? extends SpecificRecord> defaultType;
    private final DecoderFactory decoderFactory = new DecoderFactory();
    private Expression typeIdExpression = new FunctionExpression<Message>(message -> message.getHeaders().get((Object)"avro_type"));
    private EvaluationContext evaluationContext;
    private ClassLoader beanClassLoader;

    public SimpleFromAvroTransformer(Class<? extends SpecificRecord> defaultType) {
        Assert.notNull(defaultType, (String)"'defaultType' must not be null");
        this.defaultType = defaultType;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public SimpleFromAvroTransformer typeExpression(Expression expression) {
        this.assertExpressionNotNull(expression);
        this.typeIdExpression = expression;
        return this;
    }

    public SimpleFromAvroTransformer typeExpression(String expression) {
        this.assertExpressionNotNull(expression);
        this.typeIdExpression = EXPRESSION_PARSER.parseExpression(expression);
        return this;
    }

    public void setTypeExpression(Expression expression) {
        this.assertExpressionNotNull(expression);
        this.typeIdExpression = expression;
    }

    public void setTypeExpressionString(String expression) {
        this.assertExpressionNotNull(expression);
        this.typeIdExpression = EXPRESSION_PARSER.parseExpression(expression);
    }

    private void assertExpressionNotNull(Object expression) {
        Assert.notNull((Object)expression, (String)"'expression' must not be null");
    }

    @Override
    protected void onInit() {
        this.evaluationContext = IntegrationContextUtils.getEvaluationContext(this.getBeanFactory());
    }

    @Override
    protected Object doTransform(Message<?> message) {
        Assert.state((boolean)(message.getPayload() instanceof byte[]), (String)"Payload must be a byte[]");
        Class type = null;
        Object value = this.typeIdExpression.getValue(this.evaluationContext, message);
        if (value instanceof Class) {
            type = (Class)value;
        } else if (value instanceof String) {
            try {
                type = ClassUtils.forName((String)((String)value), (ClassLoader)this.beanClassLoader);
            }
            catch (ClassNotFoundException | LinkageError e) {
                throw new IllegalStateException(e);
            }
        }
        if (type == null) {
            type = this.defaultType;
        }
        SpecificDatumReader reader = new SpecificDatumReader(type);
        try {
            return reader.read(null, (Decoder)this.decoderFactory.binaryDecoder((byte[])message.getPayload(), null));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

