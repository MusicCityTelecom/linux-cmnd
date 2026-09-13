/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transaction;

import java.util.Map;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.transaction.IntegrationResourceHolder;
import org.springframework.integration.transaction.TransactionSynchronizationProcessor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class ExpressionEvaluatingTransactionSynchronizationProcessor
extends IntegrationObjectSupport
implements TransactionSynchronizationProcessor {
    private volatile EvaluationContext evaluationContext;
    private volatile Expression beforeCommitExpression;
    private volatile Expression afterCommitExpression;
    private volatile Expression afterRollbackExpression;
    private volatile MessageChannel beforeCommitChannel;
    private volatile MessageChannel afterCommitChannel;
    private volatile MessageChannel afterRollbackChannel;

    public void setIntegrationEvaluationContext(EvaluationContext evaluationContext) {
        this.evaluationContext = evaluationContext;
    }

    public void setBeforeCommitChannel(MessageChannel beforeCommitChannel) {
        Assert.notNull((Object)beforeCommitChannel, (String)"'beforeCommitChannel' must not be null");
        this.beforeCommitChannel = beforeCommitChannel;
    }

    public void setAfterCommitChannel(MessageChannel afterCommitChannel) {
        Assert.notNull((Object)afterCommitChannel, (String)"'afterCommitChannel' must not be null");
        this.afterCommitChannel = afterCommitChannel;
    }

    public void setAfterRollbackChannel(MessageChannel afterRollbackChannel) {
        Assert.notNull((Object)afterRollbackChannel, (String)"'afterRollbackChannel' must not be null");
        this.afterRollbackChannel = afterRollbackChannel;
    }

    public void setBeforeCommitExpression(Expression beforeCommitExpression) {
        Assert.notNull((Object)beforeCommitExpression, (String)"'beforeCommitExpression' must not be null");
        this.beforeCommitExpression = beforeCommitExpression;
    }

    public void setAfterCommitExpression(Expression afterCommitExpression) {
        Assert.notNull((Object)afterCommitExpression, (String)"'afterCommitExpression' must not be null");
        this.afterCommitExpression = afterCommitExpression;
    }

    public void setAfterRollbackExpression(Expression afterRollbackExpression) {
        Assert.notNull((Object)afterRollbackExpression, (String)"'afterRollbackExpression' must not be null");
        this.afterRollbackExpression = afterRollbackExpression;
    }

    @Override
    protected void onInit() {
        super.onInit();
        if (this.evaluationContext == null) {
            this.evaluationContext = this.createEvaluationContext();
        }
    }

    @Override
    public void processBeforeCommit(IntegrationResourceHolder holder) {
        this.doProcess(holder, this.beforeCommitExpression, this.beforeCommitChannel, "beforeCommit");
    }

    @Override
    public void processAfterCommit(IntegrationResourceHolder holder) {
        this.doProcess(holder, this.afterCommitExpression, this.afterCommitChannel, "afterCommit");
    }

    @Override
    public void processAfterRollback(IntegrationResourceHolder holder) {
        this.doProcess(holder, this.afterRollbackExpression, this.afterRollbackChannel, "afterRollback");
    }

    private void doProcess(IntegrationResourceHolder holder, Expression expression, @Nullable MessageChannel messageChannel, String expressionType) {
        Message<?> message = holder.getMessage();
        if (message != null) {
            if (expression != null) {
                this.logger.debug(() -> "Evaluating " + expressionType + " expression: '" + expression.getExpressionString() + "' on " + message);
                EvaluationContext evaluationContextToUse = this.prepareEvaluationContextToUse(holder);
                Object value = expression.getValue(evaluationContextToUse, message);
                if (value != null && messageChannel != null) {
                    this.sendMessageForExpressionResult(value, (Map<String, ?>)message.getHeaders(), messageChannel, expressionType);
                } else {
                    this.logger.trace((CharSequence)"Expression evaluation returned null");
                }
            } else if (messageChannel != null) {
                this.logger.debug(() -> "Sending received message to " + messageChannel + " as part of '" + expressionType + "' transaction synchronization");
                try {
                    this.sendMessage(messageChannel, message);
                }
                catch (Exception ex) {
                    this.logger.error((Throwable)ex, () -> "Failed to send " + message);
                }
            }
        }
    }

    private void sendMessageForExpressionResult(Object value, Map<String, ?> headers, MessageChannel messageChannel, String expressionType) {
        this.logger.debug(() -> "Sending expression result message to " + messageChannel + " as part of '" + expressionType + "' transaction synchronization");
        Message<Object> spelResultMessage = value instanceof Message ? (Message<Object>)value : this.getMessageBuilderFactory().withPayload(value).copyHeaders(headers).build();
        try {
            this.sendMessage(messageChannel, spelResultMessage);
        }
        catch (Exception ex) {
            this.logger.error((Throwable)ex, () -> "Failed to send " + expressionType + " evaluation result " + spelResultMessage);
        }
    }

    private void sendMessage(MessageChannel channel, Message<?> message) {
        channel.send(message, 0L);
    }

    private EvaluationContext prepareEvaluationContextToUse(Object resource) {
        if (resource != null) {
            StandardEvaluationContext evaluationContextWithVariables = this.createEvaluationContext();
            if (resource instanceof IntegrationResourceHolder) {
                IntegrationResourceHolder holder = (IntegrationResourceHolder)resource;
                for (Map.Entry<String, Object> entry : holder.getAttributes().entrySet()) {
                    String key = entry.getKey();
                    evaluationContextWithVariables.setVariable(key, entry.getValue());
                }
            }
            return evaluationContextWithVariables;
        }
        return this.evaluationContext;
    }

    protected StandardEvaluationContext createEvaluationContext() {
        return ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
    }
}

