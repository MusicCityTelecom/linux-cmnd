/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.handler.advice;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;
import org.springframework.integration.message.AdviceMessage;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.util.StringUtils;

public class ExpressionEvaluatingRequestHandlerAdvice
extends AbstractRequestHandlerAdvice {
    private static final Expression DEFAULT_EXPRESSION = new FunctionExpression<Message>(Message::getPayload);
    private final MessagingTemplate messagingTemplate = new MessagingTemplate();
    private Expression onSuccessExpression;
    private MessageChannel successChannel;
    private String successChannelName;
    private Expression onFailureExpression;
    private MessageChannel failureChannel;
    private String failureChannelName;
    private boolean trapException = false;
    private boolean returnFailureExpressionResult = false;
    private boolean propagateOnSuccessEvaluationFailures;
    private EvaluationContext evaluationContext;

    public void setOnSuccessExpressionString(String onSuccessExpression) {
        this.setOnSuccessExpression(EXPRESSION_PARSER.parseExpression(onSuccessExpression));
    }

    public void setOnSuccessExpression(@Nullable Expression onSuccessExpression) {
        this.onSuccessExpression = onSuccessExpression;
    }

    public void setOnFailureExpressionString(String onFailureExpression) {
        this.setOnFailureExpression(EXPRESSION_PARSER.parseExpression(onFailureExpression));
    }

    public void setOnFailureExpression(@Nullable Expression onFailureExpression) {
        this.onFailureExpression = onFailureExpression;
    }

    public void setSuccessChannel(MessageChannel successChannel) {
        this.successChannel = successChannel;
    }

    public void setSuccessChannelName(String successChannelName) {
        this.successChannelName = successChannelName;
    }

    public void setFailureChannel(MessageChannel failureChannel) {
        this.failureChannel = failureChannel;
    }

    public void setFailureChannelName(String failureChannelName) {
        this.failureChannelName = failureChannelName;
    }

    public void setTrapException(boolean trapException) {
        this.trapException = trapException;
    }

    public void setReturnFailureExpressionResult(boolean returnFailureExpressionResult) {
        this.returnFailureExpressionResult = returnFailureExpressionResult;
    }

    public void setPropagateEvaluationFailures(boolean propagateOnSuccessEvaluationFailures) {
        this.propagateOnSuccessEvaluationFailures = propagateOnSuccessEvaluationFailures;
    }

    @Override
    protected void onInit() {
        super.onInit();
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null) {
            this.messagingTemplate.setBeanFactory(beanFactory);
        }
        if (this.onSuccessExpression == null && (this.successChannel != null || StringUtils.hasText((String)this.successChannelName))) {
            this.onSuccessExpression = DEFAULT_EXPRESSION;
        }
        if (this.onFailureExpression == null && (this.failureChannel != null || StringUtils.hasText((String)this.failureChannelName))) {
            this.onFailureExpression = DEFAULT_EXPRESSION;
        }
    }

    @Override
    protected Object doInvoke(AbstractRequestHandlerAdvice.ExecutionCallback callback, Object target, Message<?> message) {
        try {
            Object result = callback.execute();
            if (this.onSuccessExpression != null) {
                this.evaluateSuccessExpression(message);
            }
            return result;
        }
        catch (RuntimeException e) {
            Exception actualException = this.unwrapExceptionIfNecessary(e);
            if (this.onFailureExpression != null) {
                Object evalResult = this.evaluateFailureExpression(message, actualException);
                if (this.returnFailureExpressionResult) {
                    return evalResult;
                }
            }
            if (!this.trapException) {
                if (e instanceof AbstractRequestHandlerAdvice.ThrowableHolderException) {
                    throw (AbstractRequestHandlerAdvice.ThrowableHolderException)e;
                }
                throw new AbstractRequestHandlerAdvice.ThrowableHolderException(actualException);
            }
            return null;
        }
    }

    private void evaluateSuccessExpression(Message<?> message) {
        Object evalResult;
        try {
            evalResult = this.onSuccessExpression.getValue(this.prepareEvaluationContextToUse(null), message);
        }
        catch (Exception e) {
            evalResult = e;
        }
        DestinationResolver<MessageChannel> channelResolver = this.getChannelResolver();
        if (this.successChannel == null && this.successChannelName != null && channelResolver != null) {
            this.successChannel = (MessageChannel)channelResolver.resolveDestination(this.successChannelName);
        }
        if (evalResult != null && this.successChannel != null) {
            AdviceMessage<Object> resultMessage = new AdviceMessage<Object>(evalResult, message);
            this.messagingTemplate.send(this.successChannel, (Message)resultMessage);
        }
        if (evalResult instanceof Exception && this.propagateOnSuccessEvaluationFailures) {
            throw new AbstractRequestHandlerAdvice.ThrowableHolderException((Exception)evalResult);
        }
    }

    private Object evaluateFailureExpression(Message<?> message, Exception exception) {
        Object evalResult;
        try {
            evalResult = this.onFailureExpression.getValue(this.prepareEvaluationContextToUse(exception), message);
        }
        catch (Exception e) {
            evalResult = e;
            this.logger.error((CharSequence)("Failure expression evaluation failed for " + message + ": " + e.getMessage()));
        }
        DestinationResolver<MessageChannel> channelResolver = this.getChannelResolver();
        if (this.failureChannel == null && this.failureChannelName != null && channelResolver != null) {
            this.failureChannel = (MessageChannel)channelResolver.resolveDestination(this.failureChannelName);
        }
        if (evalResult != null && this.failureChannel != null) {
            MessageHandlingExpressionEvaluatingAdviceException messagingException = new MessageHandlingExpressionEvaluatingAdviceException(message, "Handler Failed", this.unwrapThrowableIfNecessary(exception), evalResult);
            ErrorMessage errorMessage = new ErrorMessage((Throwable)((Object)messagingException));
            this.messagingTemplate.send(this.failureChannel, (Message)errorMessage);
        }
        return evalResult;
    }

    protected StandardEvaluationContext createEvaluationContext() {
        return ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
    }

    private EvaluationContext prepareEvaluationContextToUse(Exception exception) {
        StandardEvaluationContext evaluationContextToUse;
        if (exception != null) {
            evaluationContextToUse = this.createEvaluationContext();
            evaluationContextToUse.setVariable("exception", (Object)exception);
        } else {
            if (this.evaluationContext == null) {
                this.evaluationContext = this.createEvaluationContext();
            }
            evaluationContextToUse = this.evaluationContext;
        }
        return evaluationContextToUse;
    }

    public static class MessageHandlingExpressionEvaluatingAdviceException
    extends MessagingException {
        private static final long serialVersionUID = 1L;
        private final Object evaluationResult;

        public MessageHandlingExpressionEvaluatingAdviceException(Message<?> message, String description, Throwable cause, Object evaluationResult) {
            super(message, description, cause);
            this.evaluationResult = evaluationResult;
        }

        public Object getEvaluationResult() {
            return this.evaluationResult;
        }
    }
}

