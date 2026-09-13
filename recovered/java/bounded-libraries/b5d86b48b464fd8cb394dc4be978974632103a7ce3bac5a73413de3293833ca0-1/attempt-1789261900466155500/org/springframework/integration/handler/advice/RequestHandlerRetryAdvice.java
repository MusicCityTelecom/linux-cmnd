/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.retry.RecoveryCallback
 *  org.springframework.retry.RetryCallback
 *  org.springframework.retry.RetryContext
 *  org.springframework.retry.RetryListener
 *  org.springframework.retry.RetryState
 *  org.springframework.retry.support.RetryTemplate
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler.advice;

import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;
import org.springframework.integration.handler.advice.RetryStateGenerator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryState;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;

public class RequestHandlerRetryAdvice
extends AbstractRequestHandlerAdvice
implements RetryListener {
    private static final ThreadLocal<Message<?>> MESSAGE_HOLDER = new ThreadLocal();
    private RetryTemplate retryTemplate = new RetryTemplate();
    private RecoveryCallback<Object> recoveryCallback;
    private volatile RetryStateGenerator retryStateGenerator = message -> null;

    public void setRetryTemplate(RetryTemplate retryTemplate) {
        Assert.notNull((Object)retryTemplate, (String)"'retryTemplate' cannot be null");
        this.retryTemplate = retryTemplate;
    }

    public void setRecoveryCallback(RecoveryCallback<Object> recoveryCallback) {
        this.recoveryCallback = recoveryCallback;
    }

    public void setRetryStateGenerator(RetryStateGenerator retryStateGenerator) {
        Assert.notNull((Object)retryStateGenerator, (String)"'retryStateGenerator' cannot be null");
        this.retryStateGenerator = retryStateGenerator;
    }

    @Override
    protected void onInit() {
        super.onInit();
        this.retryTemplate.registerListener((RetryListener)this);
    }

    @Override
    protected Object doInvoke(AbstractRequestHandlerAdvice.ExecutionCallback callback, Object target, Message<?> message) {
        RetryState retryState = this.retryStateGenerator.determineRetryState(message);
        MESSAGE_HOLDER.set(message);
        try {
            Object object = this.retryTemplate.execute(context -> callback.cloneAndExecute(), this.recoveryCallback, retryState);
            return object;
        }
        catch (MessagingException e) {
            if (e.getFailedMessage() == null) {
                throw new MessagingException(message, "Failed to invoke handler", (Throwable)e);
            }
            throw e;
        }
        catch (AbstractRequestHandlerAdvice.ThrowableHolderException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AbstractRequestHandlerAdvice.ThrowableHolderException(e);
        }
        finally {
            MESSAGE_HOLDER.remove();
        }
    }

    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        context.setAttribute("message", MESSAGE_HOLDER.get());
        return true;
    }

    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
    }

    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
    }
}

