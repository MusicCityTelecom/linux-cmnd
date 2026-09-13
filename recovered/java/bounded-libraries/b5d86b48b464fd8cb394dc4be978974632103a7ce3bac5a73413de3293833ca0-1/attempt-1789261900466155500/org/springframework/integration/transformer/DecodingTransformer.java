/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.codec.Codec;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class DecodingTransformer<T>
extends AbstractTransformer {
    private final Codec codec;
    private final Class<T> type;
    private final Expression typeExpression;
    private volatile StandardEvaluationContext evaluationContext;

    public DecodingTransformer(Codec codec, Class<T> type) {
        Assert.notNull((Object)codec, (String)"'codec' cannot be null");
        Assert.notNull(type, (String)"'type' cannot be null");
        this.codec = codec;
        this.type = type;
        this.typeExpression = null;
    }

    public DecodingTransformer(Codec codec, Expression typeExpression) {
        Assert.notNull((Object)codec, (String)"'codec' cannot be null");
        Assert.notNull((Object)typeExpression, (String)"'typeExpression' cannot be null");
        this.codec = codec;
        this.type = null;
        this.typeExpression = typeExpression;
    }

    public void setEvaluationContext(StandardEvaluationContext evaluationContext) {
        this.evaluationContext = evaluationContext;
    }

    @Override
    protected void onInit() {
        if (this.evaluationContext == null) {
            this.evaluationContext = IntegrationContextUtils.getEvaluationContext(this.getBeanFactory());
        }
    }

    protected T doTransform(Message<?> message) {
        Assert.isTrue((boolean)(message.getPayload() instanceof byte[]), (String)"Message payload must be byte[]");
        byte[] bytes = (byte[])message.getPayload();
        try {
            return this.codec.decode(bytes, this.type != null ? this.type : this.type(message));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Class<T> type(Message<?> message) {
        Assert.state((this.evaluationContext != null ? 1 : 0) != 0, (String)"EvaluationContext required");
        return (Class)this.typeExpression.getValue((EvaluationContext)this.evaluationContext, message, Class.class);
    }
}

