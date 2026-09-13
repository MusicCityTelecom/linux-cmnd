/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.LocalVariableTableParameterNameDiscoverer
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.handler.annotation.Header
 *  org.springframework.messaging.handler.annotation.Payload
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.expression.Expression;
import org.springframework.integration.annotation.Publisher;
import org.springframework.integration.aop.PublisherMetadataSource;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class MethodAnnotationPublisherMetadataSource
implements PublisherMetadataSource {
    private final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final Map<Method, String> channels = new ConcurrentHashMap<Method, String>();
    private final Map<Method, Expression> payloadExpressions = new ConcurrentHashMap<Method, Expression>();
    private final Map<Method, Map<String, Expression>> headersExpressions = new ConcurrentHashMap<Method, Map<String, Expression>>();
    private final Set<Class<? extends Annotation>> annotationTypes;
    private volatile String channelAttributeName = "channel";

    public MethodAnnotationPublisherMetadataSource() {
        this(Collections.singleton(Publisher.class));
    }

    public MethodAnnotationPublisherMetadataSource(Set<Class<? extends Annotation>> annotationTypes) {
        Assert.notEmpty(annotationTypes, (String)"annotationTypes must not be empty");
        this.annotationTypes = annotationTypes;
    }

    public void setChannelAttributeName(String channelAttributeName) {
        Assert.hasText((String)channelAttributeName, (String)"channelAttributeName must not be empty");
        this.channelAttributeName = channelAttributeName;
    }

    @Override
    public String getChannelName(Method method) {
        return this.channels.computeIfAbsent(method, method1 -> {
            String channelName = this.getAnnotationValue(method, this.channelAttributeName);
            if (channelName == null) {
                channelName = this.getAnnotationValue(method.getDeclaringClass(), this.channelAttributeName);
            }
            return StringUtils.hasText((String)channelName) ? channelName : null;
        });
    }

    @Override
    public Expression getExpressionForPayload(Method method) {
        return this.payloadExpressions.computeIfAbsent(method, method1 -> {
            Expression payloadExpression = null;
            MergedAnnotation payloadMergedAnnotation = MergedAnnotations.from((AnnotatedElement)method, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(Payload.class);
            if (payloadMergedAnnotation.isPresent()) {
                String payloadExpressionString = payloadMergedAnnotation.getString("expression");
                payloadExpression = !StringUtils.hasText((String)payloadExpressionString) ? RETURN_VALUE_EXPRESSION : EXPRESSION_PARSER.parseExpression(payloadExpressionString);
            }
            Annotation[][] annotationArray = method.getParameterAnnotations();
            for (int i = 0; i < annotationArray.length; ++i) {
                Annotation[] parameterAnnotations = annotationArray[i];
                payloadMergedAnnotation = MergedAnnotations.from((Annotation[])parameterAnnotations).get(Payload.class);
                if (!payloadMergedAnnotation.isPresent()) continue;
                Assert.state((payloadExpression == null ? 1 : 0) != 0, (String)"@Payload can be used at most once on a @Publisher method, either at method-level or on a single parameter");
                Assert.state((boolean)"".equals(payloadMergedAnnotation.getString("expression")), (String)"@Payload on a parameter for a @Publisher method may not contain an 'expression'");
                payloadExpression = EXPRESSION_PARSER.parseExpression("#args[" + i + "]");
            }
            if (payloadExpression == null || RETURN_VALUE_EXPRESSION.getExpressionString().equals(payloadExpression.getExpressionString())) {
                Assert.isTrue((!Void.TYPE.equals(method.getReturnType()) ? 1 : 0) != 0, (String)"When defining @Publisher on a void-returning method, an explicit payload expression that does not rely upon a #return value is required.");
            }
            return payloadExpression;
        });
    }

    @Override
    public Map<String, Expression> getExpressionsForHeaders(Method method) {
        return this.headersExpressions.computeIfAbsent(method, method1 -> {
            HashMap<String, Expression> headerExpressions = new HashMap<String, Expression>();
            String[] parameterNames = this.parameterNameDiscoverer.getParameterNames(method);
            Annotation[][] annotationArray = method.getParameterAnnotations();
            for (int i = 0; i < annotationArray.length; ++i) {
                Annotation[] parameterAnnotations = annotationArray[i];
                MergedAnnotation headerMergedAnnotation = MergedAnnotations.from((Annotation[])parameterAnnotations).get(Header.class);
                if (!headerMergedAnnotation.isPresent()) continue;
                String name = headerMergedAnnotation.getString("name");
                if (!StringUtils.hasText((String)name)) {
                    name = parameterNames != null ? parameterNames[i] : method.getName() + ".arg#" + i;
                }
                headerExpressions.put(name, EXPRESSION_PARSER.parseExpression("#args[" + i + ']'));
            }
            return headerExpressions;
        });
    }

    @Nullable
    private String getAnnotationValue(AnnotatedElement element, String attributeName) {
        MergedAnnotations mergedAnnotations = MergedAnnotations.from((AnnotatedElement)element, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
        String value = null;
        for (Class<? extends Annotation> annotationType : this.annotationTypes) {
            MergedAnnotation mergedAnnotation = mergedAnnotations.get(annotationType);
            if (!mergedAnnotation.isPresent()) continue;
            if (value != null) {
                throw new IllegalStateException("The [" + element + "] contains more than one publisher annotation");
            }
            value = mergedAnnotation.getString(attributeName);
        }
        return value;
    }
}

