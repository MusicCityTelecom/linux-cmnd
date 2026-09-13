/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.ProxyMethodInvocation
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 */
package org.springframework.integration.handler.advice;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public abstract class AbstractRequestHandlerAdvice
extends IntegrationObjectSupport
implements MethodInterceptor {
    public final Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        boolean isMessageMethod = (method.getName().equals("handleRequestMessage") || method.getName().equals("handleMessage")) && arguments.length == 1 && arguments[0] instanceof Message;
        Object invocationThis = invocation.getThis();
        if (!isMessageMethod) {
            boolean isMessageHandler;
            boolean bl = isMessageHandler = invocationThis != null && MessageHandler.class.isAssignableFrom(invocationThis.getClass());
            if (!isMessageHandler && this.logger.isWarnEnabled()) {
                String clazzName = invocationThis == null ? method.getDeclaringClass().getName() : invocationThis.getClass().getName();
                this.logger.warn((CharSequence)("This advice " + this.getClass().getName() + " can only be used for MessageHandlers; an attempt to advise method '" + method.getName() + "' in '" + clazzName + "' is ignored"));
            }
            return invocation.proceed();
        }
        Message message = (Message)arguments[0];
        try {
            return this.doInvoke(new CallbackImpl(invocation), invocationThis, message);
        }
        catch (Exception e) {
            throw this.unwrapThrowableIfNecessary(e);
        }
    }

    protected abstract Object doInvoke(ExecutionCallback var1, Object var2, Message<?> var3);

    protected Exception unwrapExceptionIfNecessary(Exception e) {
        Exception actualException = e;
        if (e instanceof ThrowableHolderException && e.getCause() instanceof Exception) {
            actualException = (Exception)e.getCause();
        }
        return actualException;
    }

    protected Throwable unwrapThrowableIfNecessary(Exception e) {
        Throwable actualThrowable = e;
        if (e instanceof ThrowableHolderException) {
            actualThrowable = e.getCause();
        }
        return actualThrowable;
    }

    private static final class CallbackImpl
    implements ExecutionCallback {
        private final MethodInvocation invocation;

        CallbackImpl(MethodInvocation invocation) {
            this.invocation = invocation;
        }

        @Override
        public Object execute() {
            try {
                return this.invocation.proceed();
            }
            catch (Throwable e) {
                throw new ThrowableHolderException(e);
            }
        }

        @Override
        public Object cloneAndExecute() {
            try {
                if (this.invocation instanceof ProxyMethodInvocation) {
                    return ((ProxyMethodInvocation)this.invocation).invocableClone().proceed();
                }
                throw new IllegalStateException("MethodInvocation of the wrong type detected - this should not happen with Spring AOP, so please raise an issue if you see this exception");
            }
            catch (Exception e) {
                throw new MessagingException((Message)this.invocation.getArguments()[0], "Failed to handle", (Throwable)e);
            }
            catch (Throwable e) {
                throw new ThrowableHolderException(e);
            }
        }
    }

    protected static interface ExecutionCallback {
        public Object execute();

        public Object cloneAndExecute();
    }

    protected static final class ThrowableHolderException
    extends RuntimeException {
        ThrowableHolderException(Throwable cause) {
            super(cause);
        }
    }
}

