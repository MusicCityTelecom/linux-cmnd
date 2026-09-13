/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.gateway;

import java.util.HashMap;
import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

public class GatewayMethodMetadata {
    private final Map<String, Expression> headerExpressions = new HashMap<String, Expression>();
    private Expression payloadExpression;
    private String requestChannelName;
    private String replyChannelName;
    private String requestTimeout;
    private String replyTimeout;

    @Nullable
    public Expression getPayloadExpression() {
        return this.payloadExpression;
    }

    public void setPayloadExpression(@Nullable Expression payloadExpression) {
        this.payloadExpression = payloadExpression;
    }

    public Map<String, Expression> getHeaderExpressions() {
        return this.headerExpressions;
    }

    public void setHeaderExpressions(@Nullable Map<String, Expression> headerExpressions) {
        this.headerExpressions.clear();
        if (headerExpressions != null) {
            this.headerExpressions.putAll(headerExpressions);
        }
    }

    public String getRequestChannelName() {
        return this.requestChannelName;
    }

    public void setRequestChannelName(String requestChannelName) {
        this.requestChannelName = requestChannelName;
    }

    public String getReplyChannelName() {
        return this.replyChannelName;
    }

    public void setReplyChannelName(String replyChannelName) {
        this.replyChannelName = replyChannelName;
    }

    @Nullable
    public String getRequestTimeout() {
        return this.requestTimeout;
    }

    public void setRequestTimeout(@Nullable String requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    @Nullable
    public String getReplyTimeout() {
        return this.replyTimeout;
    }

    public void setReplyTimeout(@Nullable String replyTimeout) {
        this.replyTimeout = replyTimeout;
    }
}

