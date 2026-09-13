/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageListener
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.MethodInvoker
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.jms.listener.adapter;

import java.lang.reflect.InvocationTargetException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.listener.SubscriptionNameProvider;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.jms.listener.adapter.ListenerExecutionFailedException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ObjectUtils;

public class MessageListenerAdapter
extends AbstractAdaptableMessageListener
implements SubscriptionNameProvider {
    public static final String ORIGINAL_DEFAULT_LISTENER_METHOD = "handleMessage";
    private Object delegate;
    private String defaultListenerMethod = "handleMessage";

    public MessageListenerAdapter() {
        this.delegate = this;
    }

    public MessageListenerAdapter(Object delegate) {
        Assert.notNull((Object)delegate, (String)"Delegate must not be null");
        this.delegate = delegate;
    }

    public void setDelegate(Object delegate) {
        Assert.notNull((Object)delegate, (String)"Delegate must not be null");
        this.delegate = delegate;
    }

    protected Object getDelegate() {
        return this.delegate;
    }

    public void setDefaultListenerMethod(String defaultListenerMethod) {
        this.defaultListenerMethod = defaultListenerMethod;
    }

    protected String getDefaultListenerMethod() {
        return this.defaultListenerMethod;
    }

    @Override
    public void onMessage(Message message, @Nullable Session session) throws JMSException {
        Object[] listenerArguments;
        Object convertedMessage;
        String methodName;
        Object result;
        Object delegate = this.getDelegate();
        if (delegate != this) {
            if (delegate instanceof SessionAwareMessageListener) {
                Assert.state((session != null ? 1 : 0) != 0, (String)"Session is required for SessionAwareMessageListener");
                ((SessionAwareMessageListener)delegate).onMessage(message, session);
                return;
            }
            if (delegate instanceof MessageListener) {
                ((MessageListener)delegate).onMessage(message);
                return;
            }
        }
        if ((result = this.invokeListenerMethod(methodName = this.getListenerMethodName(message, convertedMessage = this.extractMessage(message)), listenerArguments = this.buildListenerArguments(convertedMessage))) != null) {
            this.handleResult(result, message, session);
        } else {
            this.logger.trace((Object)"No result object given - no result to handle");
        }
    }

    @Override
    public String getSubscriptionName() {
        Object delegate = this.getDelegate();
        if (delegate != this && delegate instanceof SubscriptionNameProvider) {
            return ((SubscriptionNameProvider)delegate).getSubscriptionName();
        }
        return delegate.getClass().getName();
    }

    protected String getListenerMethodName(Message originalMessage, Object extractedMessage) throws JMSException {
        return this.getDefaultListenerMethod();
    }

    protected Object[] buildListenerArguments(Object extractedMessage) {
        return new Object[]{extractedMessage};
    }

    @Nullable
    protected Object invokeListenerMethod(String methodName, Object[] arguments) throws JMSException {
        try {
            MethodInvoker methodInvoker = new MethodInvoker();
            methodInvoker.setTargetObject(this.getDelegate());
            methodInvoker.setTargetMethod(methodName);
            methodInvoker.setArguments(arguments);
            methodInvoker.prepare();
            return methodInvoker.invoke();
        }
        catch (InvocationTargetException ex) {
            Throwable targetEx = ex.getTargetException();
            if (targetEx instanceof JMSException) {
                throw (JMSException)targetEx;
            }
            throw new ListenerExecutionFailedException("Listener method '" + methodName + "' threw exception", targetEx);
        }
        catch (Throwable ex) {
            throw new ListenerExecutionFailedException("Failed to invoke target method '" + methodName + "' with arguments " + ObjectUtils.nullSafeToString((Object[])arguments), ex);
        }
    }
}

