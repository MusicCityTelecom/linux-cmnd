/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 *  org.springframework.util.PatternMatchUtils
 */
package org.springframework.integration.aop;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.expression.Expression;
import org.springframework.integration.aop.PublisherMetadataSource;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;

public class MethodNameMappingPublisherMetadataSource
implements PublisherMetadataSource {
    private final Map<String, Expression> payloadExpressionMap;
    private volatile Map<String, Map<String, Expression>> headerExpressionMap = Collections.emptyMap();
    private volatile Map<String, String> channelMap = Collections.emptyMap();

    public MethodNameMappingPublisherMetadataSource(Map<String, String> payloadExpressionMap) {
        Assert.notEmpty(payloadExpressionMap, (String)"payloadExpressionMap must not be empty");
        this.payloadExpressionMap = payloadExpressionMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> EXPRESSION_PARSER.parseExpression((String)e.getValue())));
    }

    public void setHeaderExpressionMap(Map<String, Map<String, String>> headerExpressionMap) {
        this.headerExpressionMap = headerExpressionMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> ((Map)e.getValue()).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> EXPRESSION_PARSER.parseExpression((String)entry.getValue())))));
    }

    public void setChannelMap(Map<String, String> channelMap) {
        this.channelMap = channelMap;
    }

    @Override
    public Expression getExpressionForPayload(Method method) {
        for (Map.Entry<String, Expression> entry : this.payloadExpressionMap.entrySet()) {
            if (!PatternMatchUtils.simpleMatch((String)entry.getKey(), (String)method.getName())) continue;
            return entry.getValue();
        }
        return null;
    }

    @Override
    public Map<String, Expression> getExpressionsForHeaders(Method method) {
        return this.headerExpressionMap.entrySet().stream().filter(e -> PatternMatchUtils.simpleMatch((String)((String)e.getKey()), (String)method.getName())).map(Map.Entry::getValue).findFirst().orElse(null);
    }

    @Override
    public String getChannelName(Method method) {
        for (Map.Entry<String, String> entry : this.channelMap.entrySet()) {
            if (!PatternMatchUtils.simpleMatch((String)entry.getKey(), (String)method.getName())) continue;
            return entry.getValue();
        }
        return null;
    }
}

