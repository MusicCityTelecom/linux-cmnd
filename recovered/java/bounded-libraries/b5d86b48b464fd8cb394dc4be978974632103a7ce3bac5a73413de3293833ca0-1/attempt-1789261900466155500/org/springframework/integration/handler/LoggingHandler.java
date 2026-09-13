/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.log.LogAccessor
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.core.log.LogAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.dispatcher.AggregateMessageDeliveryException;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class LoggingHandler
extends AbstractMessageHandler {
    private Level level;
    private Expression expression = new FunctionExpression<Message>(Message::getPayload);
    private boolean expressionSet;
    private EvaluationContext evaluationContext = ExpressionUtils.createStandardEvaluationContext();
    private boolean shouldLogFullMessageSet;
    private LogAccessor messageLogger = this.logger;

    public LoggingHandler(String level) {
        this(LoggingHandler.convertLevel(level));
    }

    private static Level convertLevel(String level) {
        Assert.hasText((String)level, (String)"'level' cannot be empty");
        try {
            return Level.valueOf(level.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid log level '" + level + "'. The (case-insensitive) supported values are: " + StringUtils.arrayToCommaDelimitedString((Object[])Level.values()));
        }
    }

    public LoggingHandler(Level level) {
        this.doSetLevel(level);
    }

    public void setLogExpressionString(String expressionString) {
        Assert.hasText((String)expressionString, (String)"'expressionString' must not be empty");
        this.setLogExpression(EXPRESSION_PARSER.parseExpression(expressionString));
    }

    public void setLogExpression(Expression expression) {
        Assert.isTrue((!this.shouldLogFullMessageSet ? 1 : 0) != 0, (String)"Cannot set both 'expression' AND 'shouldLogFullMessage' properties");
        this.expressionSet = true;
        this.expression = expression;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.doSetLevel(level);
    }

    private void doSetLevel(Level level) {
        Assert.notNull((Object)((Object)level), (String)"'level' cannot be null");
        this.level = level;
    }

    public void setLoggerName(String loggerName) {
        Assert.hasText((String)loggerName, (String)"loggerName must not be empty");
        this.messageLogger = new LogAccessor(loggerName);
    }

    public void setShouldLogFullMessage(boolean shouldLogFullMessage) {
        Assert.isTrue((!this.expressionSet ? 1 : 0) != 0, (String)"Cannot set both 'expression' AND 'shouldLogFullMessage' properties");
        this.shouldLogFullMessageSet = true;
        this.expression = shouldLogFullMessage ? new FunctionExpression(Function.identity()) : new FunctionExpression<Message>(Message::getPayload);
    }

    @Override
    public String getComponentType() {
        return "logging-channel-adapter";
    }

    @Override
    protected void onInit() {
        super.onInit();
        this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        Supplier<CharSequence> logMessage = () -> this.createLogMessage(message);
        switch (this.level) {
            case FATAL: {
                this.messageLogger.fatal(logMessage);
                break;
            }
            case ERROR: {
                this.messageLogger.error(logMessage);
                break;
            }
            case WARN: {
                this.messageLogger.warn(logMessage);
                break;
            }
            case INFO: {
                this.messageLogger.info(logMessage);
                break;
            }
            case DEBUG: {
                this.messageLogger.debug(logMessage);
                break;
            }
            case TRACE: {
                this.messageLogger.trace(logMessage);
                break;
            }
            default: {
                throw new IllegalStateException("Level '" + (Object)((Object)this.level) + "' is not supported");
            }
        }
    }

    @Nullable
    private String createLogMessage(Message<?> message) {
        Object logMessage = this.expression.getValue(this.evaluationContext, message);
        return logMessage instanceof Throwable ? this.createLogMessage((Throwable)logMessage) : Objects.toString(logMessage);
    }

    private String createLogMessage(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        if (throwable instanceof AggregateMessageDeliveryException) {
            stringWriter.append(throwable.getMessage());
            for (Exception exception : ((AggregateMessageDeliveryException)((Object)throwable)).getAggregatedExceptions()) {
                this.printStackTrace(exception, stringWriter);
            }
        } else {
            this.printStackTrace(throwable, stringWriter);
        }
        return stringWriter.toString();
    }

    private void printStackTrace(Throwable throwable, Writer writer) {
        throwable.printStackTrace(new PrintWriter(writer, true));
    }

    public static enum Level {
        FATAL,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE;

    }
}

