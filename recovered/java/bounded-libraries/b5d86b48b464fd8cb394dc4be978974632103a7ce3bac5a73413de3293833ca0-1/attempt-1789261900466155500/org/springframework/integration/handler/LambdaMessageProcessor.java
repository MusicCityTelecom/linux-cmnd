/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.core.MethodIntrospector
 *  org.springframework.core.log.LogMessage
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.log.LogMessage;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class LambdaMessageProcessor
implements MessageProcessor<Object>,
BeanFactoryAware {
    private static final Log LOGGER = LogFactory.getLog(LambdaMessageProcessor.class);
    private final Object target;
    private final Method method;
    @Nullable
    private final Class<?> expectedType;
    private final Class<?>[] parameterTypes;
    private MessageConverter messageConverter;

    public LambdaMessageProcessor(Object target, @Nullable Class<?> expectedType) {
        Assert.notNull((Object)target, (String)"'target' must not be null");
        this.target = target;
        Set methods = MethodIntrospector.selectMethods(target.getClass(), methodCandidate -> methodCandidate.getDeclaringClass() != Object.class && !methodCandidate.getDeclaringClass().getName().equals("kotlin.jvm.internal.Lambda") && !methodCandidate.isDefault() && !Modifier.isStatic(methodCandidate.getModifiers()));
        Assert.state((methods.size() == 1 ? 1 : 0) != 0, (String)"LambdaMessageProcessor is applicable for inline or lambda classes with single method - functional interface implementations.");
        this.method = (Method)methods.iterator().next();
        ReflectionUtils.makeAccessible((Method)this.method);
        this.parameterTypes = this.method.getParameterTypes();
        this.expectedType = expectedType;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.messageConverter = (MessageConverter)beanFactory.getBean("integrationArgumentResolverMessageConverter", MessageConverter.class);
    }

    @Override
    public Object processMessage(Message<?> message) {
        Object[] args = this.buildArgs(message);
        try {
            Object result = this.method.invoke(this.target, args);
            if (result != null && org.springframework.integration.util.ClassUtils.isKotlinUnit(result.getClass())) {
                result = null;
            }
            return result;
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (e.getTargetException() instanceof ClassCastException) {
                LOGGER.error((Object)("Could not invoke the method '" + this.method + "' due to a class cast exception, if using a lambda in the DSL, consider using an overloaded EIP method that takes a Class<?> argument to explicitly  specify the type. An example of when this often occurs is if the lambda is configured to receive a Message<?> argument."), cause);
            }
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw new IllegalStateException("Could not invoke the method '" + this.method + "'", cause);
        }
        catch (Exception e) {
            throw new IllegalStateException("error occurred during processing message in 'LambdaMessageProcessor' for method [" + this.method + "]", e);
        }
    }

    private Object[] buildArgs(Message<?> message) {
        Object[] args = new Object[this.parameterTypes.length];
        for (int i = 0; i < this.parameterTypes.length; ++i) {
            Class<?> parameterType = this.parameterTypes[i];
            if (Message.class.isAssignableFrom(parameterType)) {
                args[i] = message;
                continue;
            }
            if (Map.class.isAssignableFrom(parameterType)) {
                if (message.getPayload() instanceof Map && this.parameterTypes.length == 1) {
                    args[i] = message.getPayload();
                    continue;
                }
                args[i] = message.getHeaders();
                continue;
            }
            if (this.expectedType != null && !ClassUtils.isAssignable(this.expectedType, message.getPayload().getClass())) {
                if (Message.class.isAssignableFrom(this.expectedType)) {
                    args[i] = message;
                    continue;
                }
                Object payload = this.messageConverter.fromMessage(message, this.expectedType);
                if (payload == null && LOGGER.isWarnEnabled()) {
                    LOGGER.warn((Object)LogMessage.format((String)"The '%s' returned 'null' for the payload conversion from the '%s' and expected type '%s'.", (Object)this.messageConverter, message, this.expectedType));
                }
                args[i] = payload;
                continue;
            }
            args[i] = message.getPayload();
        }
        return args;
    }
}

