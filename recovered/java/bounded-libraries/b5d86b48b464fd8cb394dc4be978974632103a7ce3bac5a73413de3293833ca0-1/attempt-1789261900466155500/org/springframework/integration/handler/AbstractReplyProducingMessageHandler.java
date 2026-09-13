/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.core.log.LogMessage
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.handler;

import java.util.LinkedList;
import java.util.List;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.log.LogMessage;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.ReplyRequiredException;
import org.springframework.integration.handler.advice.HandleMessageAdvice;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public abstract class AbstractReplyProducingMessageHandler
extends AbstractMessageProducingHandler
implements BeanClassLoaderAware {
    private final List<Advice> adviceChain = new LinkedList<Advice>();
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private boolean requiresReply = false;
    private volatile RequestHandler advisedRequestHandler;

    public void setRequiresReply(boolean requiresReply) {
        this.requiresReply = requiresReply;
    }

    protected boolean getRequiresReply() {
        return this.requiresReply;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setAdviceChain(List<Advice> adviceChain) {
        Assert.notEmpty(adviceChain, (String)"adviceChain cannot be empty");
        List<Advice> list = this.adviceChain;
        synchronized (list) {
            this.adviceChain.clear();
            this.adviceChain.addAll(adviceChain);
            if (this.isInitialized()) {
                this.initAdvisedRequestHandlerIfAny();
            }
        }
    }

    protected boolean hasAdviceChain() {
        return this.adviceChain.size() > 0;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.outbound_gateway;
    }

    @Override
    protected final void onInit() {
        super.onInit();
        this.initAdvisedRequestHandlerIfAny();
        this.doInit();
    }

    private void initAdvisedRequestHandlerIfAny() {
        if (!this.adviceChain.isEmpty()) {
            ProxyFactory proxyFactory = new ProxyFactory((Object)new AdvisedRequestHandler());
            boolean advised = false;
            for (Advice advice : this.adviceChain) {
                if (advice instanceof HandleMessageAdvice) continue;
                proxyFactory.addAdvice(advice);
                advised = true;
            }
            if (advised) {
                this.advisedRequestHandler = (RequestHandler)proxyFactory.getProxy(this.beanClassLoader);
            }
        }
    }

    protected void doInit() {
    }

    @Override
    protected final void handleMessageInternal(Message<?> message) {
        Object result = this.advisedRequestHandler == null ? this.handleRequestMessage(message) : this.doInvokeAdvisedRequestHandler(message);
        if (result != null) {
            this.sendOutputs(result, message);
        } else {
            if (this.requiresReply && !this.isAsync()) {
                throw new ReplyRequiredException(message, "No reply produced by handler '" + this.getComponentName() + "', and its 'requiresReply' property is set to true.");
            }
            if (!this.isAsync()) {
                this.logger.debug((CharSequence)LogMessage.format((String)"handler '%s' produced no reply for request Message: %s", (Object)this, message));
            }
        }
    }

    @Nullable
    protected Object doInvokeAdvisedRequestHandler(Message<?> message) {
        return this.advisedRequestHandler.handleRequestMessage(message);
    }

    @Nullable
    protected abstract Object handleRequestMessage(Message<?> var1);

    private class AdvisedRequestHandler
    implements RequestHandler {
        AdvisedRequestHandler() {
        }

        @Override
        @Nullable
        public Object handleRequestMessage(Message<?> requestMessage) {
            return AbstractReplyProducingMessageHandler.this.handleRequestMessage(requestMessage);
        }

        public String toString() {
            return AbstractReplyProducingMessageHandler.this.toString();
        }

        @Override
        public AbstractReplyProducingMessageHandler getAdvisedHandler() {
            return AbstractReplyProducingMessageHandler.this;
        }
    }

    public static interface RequestHandler {
        @Nullable
        public Object handleRequestMessage(Message<?> var1);

        public AbstractReplyProducingMessageHandler getAdvisedHandler();
    }
}

