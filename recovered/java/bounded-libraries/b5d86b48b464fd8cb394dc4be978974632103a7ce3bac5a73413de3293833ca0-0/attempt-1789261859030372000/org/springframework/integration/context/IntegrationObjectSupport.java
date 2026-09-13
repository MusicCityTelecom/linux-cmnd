/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.AopProxyUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.core.log.LogAccessor
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.AlternativeJdkIdGenerator
 *  org.springframework.util.Assert
 *  org.springframework.util.IdGenerator
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.context;

import java.util.Properties;
import java.util.UUID;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.log.LogAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.context.ExpressionCapable;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.context.IntegrationProperties;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.util.StringUtils;

public abstract class IntegrationObjectSupport
implements BeanNameAware,
NamedComponent,
ApplicationContextAware,
BeanFactoryAware,
InitializingBean,
ExpressionCapable {
    protected static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private static final IdGenerator ID_GENERATOR = new AlternativeJdkIdGenerator();
    protected final LogAccessor logger = new LogAccessor(this.getClass());
    private final ConversionService defaultConversionService = DefaultConversionService.getSharedInstance();
    private DestinationResolver<MessageChannel> channelResolver;
    private String beanName;
    private String componentName;
    private BeanFactory beanFactory;
    private TaskScheduler taskScheduler;
    private Properties integrationProperties = IntegrationProperties.defaults();
    private ConversionService conversionService;
    private ApplicationContext applicationContext;
    private MessageBuilderFactory messageBuilderFactory;
    private Expression expression;
    private boolean initialized;

    public final void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public String getComponentName() {
        return StringUtils.hasText((String)this.componentName) ? this.componentName : this.beanName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    @Override
    public String getComponentType() {
        return null;
    }

    public String getBeanDescription() {
        String description = null;
        Object source = null;
        if (this.beanFactory instanceof ConfigurableListableBeanFactory && ((ConfigurableListableBeanFactory)this.beanFactory).containsBeanDefinition(this.beanName)) {
            BeanDefinition beanDefinition = ((ConfigurableListableBeanFactory)this.beanFactory).getBeanDefinition(this.beanName);
            description = beanDefinition.getResourceDescription();
            source = beanDefinition.getSource();
        }
        StringBuilder sb = new StringBuilder("bean '").append(this.beanName).append("'");
        if (!this.beanName.equals(this.getComponentName())) {
            sb.append(" for component '").append(this.getComponentName()).append("'");
        }
        if (description != null) {
            sb.append("; defined in: '").append(description).append("'");
        }
        if (source != null) {
            sb.append("; from source: '").append(source).append("'");
        }
        return sb.toString();
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"'beanFactory' must not be null");
        this.beanFactory = beanFactory;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Assert.notNull((Object)applicationContext, (String)"'applicationContext' must not be null");
        this.applicationContext = applicationContext;
    }

    public void setChannelResolver(DestinationResolver<MessageChannel> channelResolver) {
        Assert.notNull(channelResolver, (String)"'channelResolver' must not be null");
        this.channelResolver = channelResolver;
    }

    @Override
    public Expression getExpression() {
        return this.expression;
    }

    public final void setPrimaryExpression(Expression expression) {
        this.expression = expression;
    }

    public final void afterPropertiesSet() {
        this.integrationProperties = IntegrationContextUtils.getIntegrationProperties(this.beanFactory);
        if (this.messageBuilderFactory == null) {
            this.messageBuilderFactory = this.beanFactory != null ? IntegrationUtils.getMessageBuilderFactory(this.beanFactory) : new DefaultMessageBuilderFactory();
        }
        this.onInit();
        this.initialized = true;
    }

    protected void onInit() {
    }

    protected boolean isInitialized() {
        return this.initialized;
    }

    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        Assert.notNull((Object)taskScheduler, (String)"taskScheduler must not be null");
        this.taskScheduler = taskScheduler;
    }

    protected TaskScheduler getTaskScheduler() {
        if (this.taskScheduler == null && this.beanFactory != null) {
            this.taskScheduler = IntegrationContextUtils.getTaskScheduler(this.beanFactory);
        }
        return this.taskScheduler;
    }

    protected DestinationResolver<MessageChannel> getChannelResolver() {
        if (this.channelResolver == null) {
            this.channelResolver = ChannelResolverUtils.getChannelResolver(this.beanFactory);
        }
        return this.channelResolver;
    }

    public ConversionService getConversionService() {
        if (this.conversionService == null && this.beanFactory != null) {
            this.conversionService = IntegrationUtils.getConversionService(this.beanFactory);
            if (this.conversionService == null) {
                this.logger.debug(() -> "Unable to attempt conversion of Message payload types. Component '" + this.getComponentName() + "' has no explicit ConversionService reference, and there is no 'integrationConversionService' bean within the context.");
            }
        }
        return this.conversionService;
    }

    protected void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public String getApplicationContextId() {
        return this.applicationContext == null ? null : this.applicationContext.getId();
    }

    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Deprecated
    protected Properties getIntegrationProperties() {
        return this.integrationProperties;
    }

    protected MessageBuilderFactory getMessageBuilderFactory() {
        if (this.messageBuilderFactory == null) {
            this.messageBuilderFactory = new DefaultMessageBuilderFactory();
        }
        return this.messageBuilderFactory;
    }

    public void setMessageBuilderFactory(MessageBuilderFactory messageBuilderFactory) {
        this.messageBuilderFactory = messageBuilderFactory;
    }

    protected <T> T getIntegrationProperty(String key, Class<T> tClass) {
        return (T)this.defaultConversionService.convert((Object)this.integrationProperties.getProperty(key), tClass);
    }

    public String toString() {
        return this.beanName != null ? this.getBeanDescription() : super.toString();
    }

    @Nullable
    public static <T> T extractTypeIfPossible(@Nullable Object targetObject, Class<T> expectedType) {
        if (targetObject == null) {
            return null;
        }
        if (expectedType.isAssignableFrom(targetObject.getClass())) {
            return (T)targetObject;
        }
        return IntegrationObjectSupport.extractTypeIfPossible(AopProxyUtils.getSingletonTarget((Object)targetObject), expectedType);
    }

    public static UUID generateId() {
        return ID_GENERATOR.generateId();
    }
}

