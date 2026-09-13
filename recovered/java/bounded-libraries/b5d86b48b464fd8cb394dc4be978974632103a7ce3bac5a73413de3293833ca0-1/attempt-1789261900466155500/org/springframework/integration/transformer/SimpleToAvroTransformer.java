/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.avro.io.BinaryEncoder
 *  org.apache.avro.io.Encoder
 *  org.apache.avro.io.EncoderFactory
 *  org.apache.avro.specific.SpecificDatumWriter
 *  org.apache.avro.specific.SpecificRecord
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Map;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class SimpleToAvroTransformer
extends AbstractTransformer {
    private final EncoderFactory encoderFactory = new EncoderFactory();
    private Expression typeIdExpression = new FunctionExpression<Message>(message -> message.getPayload().getClass());
    private EvaluationContext evaluationContext;

    public SimpleToAvroTransformer typeExpression(Expression expression) {
        this.assertExpressionNotNull(expression);
        this.typeIdExpression = expression;
        return this;
    }

    public SimpleToAvroTransformer typeExpression(String expression) {
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
        Assert.state((boolean)(message.getPayload() instanceof SpecificRecord), (String)"Payload must be an implementation of 'SpecificRecord'");
        SpecificRecord specific = (SpecificRecord)message.getPayload();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = this.encoderFactory.directBinaryEncoder((OutputStream)out, null);
        SpecificDatumWriter writer = new SpecificDatumWriter(specific.getSchema());
        try {
            writer.write((Object)specific, (Encoder)encoder);
            encoder.flush();
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return this.getMessageBuilderFactory().withPayload(out.toByteArray()).copyHeaders((Map<String, ?>)message.getHeaders()).setHeader("avro_type", this.typeIdExpression.getValue(this.evaluationContext, message)).build();
    }
}

