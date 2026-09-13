/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.context.ExpressionCapable;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

public abstract class IntegrationNode {
    private final int nodeId;
    private final String nodeName;
    private final String componentType;
    @Nullable
    private final IntegrationPatternType integrationPatternType;
    @Nullable
    private final IntegrationPatternType.IntegrationPatternCategory integrationPatternCategory;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private final Map<String, Object> unmodifiableProperties = Collections.unmodifiableMap(this.properties);

    protected IntegrationNode(int nodeId, String name, Object nodeObject) {
        Expression expression;
        this.nodeId = nodeId;
        this.nodeName = name;
        String string = this.componentType = nodeObject instanceof NamedComponent ? ((NamedComponent)nodeObject).getComponentType() : nodeObject.getClass().getSimpleName();
        if (nodeObject instanceof ExpressionCapable && (expression = ((ExpressionCapable)nodeObject).getExpression()) != null) {
            this.properties.put("expression", expression.getExpressionString());
        }
        IntegrationPatternType patternType = null;
        if (nodeObject instanceof IntegrationPattern) {
            patternType = ((IntegrationPattern)nodeObject).getIntegrationPatternType();
        } else if (nodeObject instanceof MessageHandler) {
            patternType = IntegrationPatternType.service_activator;
        } else if (nodeObject instanceof MessageChannel) {
            patternType = IntegrationPatternType.message_channel;
        }
        this.integrationPatternType = patternType;
        this.integrationPatternCategory = this.integrationPatternType != null ? this.integrationPatternType.getPatternCategory() : null;
    }

    public int getNodeId() {
        return this.nodeId;
    }

    public String getName() {
        return this.nodeName;
    }

    public final String getComponentType() {
        return this.componentType;
    }

    @Nullable
    public IntegrationPatternType getIntegrationPatternType() {
        return this.integrationPatternType;
    }

    @Nullable
    public IntegrationPatternType.IntegrationPatternCategory getIntegrationPatternCategory() {
        return this.integrationPatternCategory;
    }

    public Map<String, Object> getProperties() {
        return this.unmodifiableProperties;
    }

    public void addProperty(String name, Object value) {
        Assert.hasText((String)name, (String)"'name' must not be null");
        this.properties.put(name, value);
    }

    public void addProperties(@Nullable Map<String, Object> props) {
        if (props != null) {
            this.properties.putAll(props);
        }
    }
}

