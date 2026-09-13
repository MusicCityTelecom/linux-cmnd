/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.expression.Expression
 *  org.springframework.expression.common.LiteralExpression
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.gateway;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.JavaUtils;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.gateway.GatewayMethodMetadata;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.gateway.MethodArgsMessageMapper;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class AnnotationGatewayProxyFactoryBean
extends GatewayProxyFactoryBean {
    private final AnnotationAttributes gatewayAttributes;

    public AnnotationGatewayProxyFactoryBean(Class<?> serviceInterface) {
        super(serviceInterface);
        AnnotationAttributes annotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(serviceInterface, (String)MessagingGateway.class.getName(), (boolean)false, (boolean)true);
        if (annotationAttributes == null) {
            annotationAttributes = AnnotationUtils.getAnnotationAttributes((Annotation)AnnotationUtils.synthesizeAnnotation(MessagingGateway.class), (boolean)false, (boolean)true);
        }
        this.gatewayAttributes = annotationAttributes;
        String id = annotationAttributes.getString("name");
        if (StringUtils.hasText((String)id)) {
            this.setBeanName(id);
        }
    }

    @Override
    protected void onInit() {
        this.populateGatewayMethodMetadataIfAny();
        String defaultRequestTimeout = this.resolveAttribute("defaultRequestTimeout");
        String defaultReplyTimeout = this.resolveAttribute("defaultReplyTimeout");
        JavaUtils.INSTANCE.acceptIfCondition(this.getDefaultRequestChannel() == null && this.getDefaultRequestChannelName() == null, this.resolveAttribute("defaultRequestChannel"), this::setDefaultRequestChannelName).acceptIfCondition(this.getDefaultReplyChannel() == null && this.getDefaultReplyChannelName() == null, this.resolveAttribute("defaultReplyChannel"), this::setDefaultReplyChannelName).acceptIfCondition(this.getErrorChannel() == null && this.getErrorChannelName() == null, this.resolveAttribute("errorChannel"), this::setErrorChannelName).acceptIfCondition(this.getDefaultRequestTimeout() == null && StringUtils.hasText((String)defaultRequestTimeout), defaultRequestTimeout, value -> this.setDefaultRequestTimeout(Long.parseLong(value))).acceptIfCondition(this.getDefaultReplyTimeout() == null && StringUtils.hasText((String)defaultReplyTimeout), defaultReplyTimeout, value -> this.setDefaultReplyTimeout(Long.parseLong(value)));
        this.populateAsyncExecutorIfAny();
        boolean proxyDefaultMethods = this.gatewayAttributes.getBoolean("proxyDefaultMethods");
        if (proxyDefaultMethods) {
            this.setProxyDefaultMethods(true);
        }
        super.onInit();
    }

    private void populateGatewayMethodMetadataIfAny() {
        if (this.getGlobalMethodMetadata() != null) {
            return;
        }
        ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory)this.getBeanFactory();
        String defaultPayloadExpression = this.resolveAttribute("defaultPayloadExpression");
        Object[] defaultHeaders = (Map[])this.gatewayAttributes.get((Object)"defaultHeaders");
        String mapper = this.resolveAttribute("mapper");
        boolean hasMapper = StringUtils.hasText((String)mapper);
        boolean hasDefaultPayloadExpression = StringUtils.hasText((String)defaultPayloadExpression);
        Assert.state((!hasMapper || !hasDefaultPayloadExpression ? 1 : 0) != 0, (String)"'defaultPayloadExpression' is not allowed when a 'mapper' is provided");
        boolean hasDefaultHeaders = !ObjectUtils.isEmpty((Object[])defaultHeaders);
        Assert.state((!hasMapper || !hasDefaultHeaders ? 1 : 0) != 0, (String)"'defaultHeaders' are not allowed when a 'mapper' is provided");
        JavaUtils.INSTANCE.acceptIfCondition(hasMapper && this.getMapper() == null, mapper, value -> this.setMapper((MethodArgsMessageMapper)beanFactory.getBean(value, MethodArgsMessageMapper.class)));
        if (hasDefaultHeaders || hasDefaultPayloadExpression) {
            GatewayMethodMetadata gatewayMethodMetadata = new GatewayMethodMetadata();
            if (hasDefaultPayloadExpression) {
                gatewayMethodMetadata.setPayloadExpression(EXPRESSION_PARSER.parseExpression(defaultPayloadExpression));
            }
            Map<String, Expression> headerExpressions = Arrays.stream(defaultHeaders).collect(Collectors.toMap(header -> beanFactory.resolveEmbeddedValue((String)header.get("name")), header -> {
                String headerExpression;
                String headerValue = beanFactory.resolveEmbeddedValue((String)header.get("value"));
                boolean hasValue = StringUtils.hasText((String)headerValue);
                Assert.state((hasValue != StringUtils.hasText((String)(headerExpression = beanFactory.resolveEmbeddedValue((String)header.get("expression")))) ? 1 : 0) != 0, (String)"exactly one of 'value' or 'expression' is required on a gateway's header.");
                return hasValue ? new LiteralExpression(headerValue) : EXPRESSION_PARSER.parseExpression(headerExpression);
            }));
            gatewayMethodMetadata.setHeaderExpressions(headerExpressions);
            this.setGlobalMethodMetadata(gatewayMethodMetadata);
        }
    }

    private void populateAsyncExecutorIfAny() {
        BeanFactory beanFactory = this.getBeanFactory();
        if (!this.isAsyncExecutorExplicitlySet()) {
            String asyncExecutor = this.resolveAttribute("asyncExecutor");
            if (asyncExecutor == null || "__NULL__".equals(asyncExecutor)) {
                this.setAsyncExecutor(null);
            } else if (StringUtils.hasText((String)asyncExecutor)) {
                this.setAsyncExecutor((Executor)beanFactory.getBean(asyncExecutor, Executor.class));
            }
        }
    }

    @Nullable
    private String resolveAttribute(String attributeName) {
        ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory)this.getBeanFactory();
        return beanFactory.resolveEmbeddedValue(this.gatewayAttributes.getString(attributeName));
    }
}

