/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.Collections;
import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class WireTapSpec
extends IntegrationComponentSpec<WireTapSpec, WireTap>
implements ComponentsRegistration {
    private final MessageChannel channel;
    private final String channelName;
    private MessageSelector selector;
    private Long timeout;

    public WireTapSpec(MessageChannel channel) {
        Assert.notNull((Object)channel, (String)"'channel' must not be null");
        this.channel = channel;
        this.channelName = null;
    }

    public WireTapSpec(String channelName) {
        Assert.notNull((Object)channelName, (String)"'channelName' must not be null");
        this.channelName = channelName;
        this.channel = null;
    }

    public WireTapSpec selector(String selectorExpression) {
        return this.selector(new ExpressionEvaluatingSelector(selectorExpression));
    }

    public WireTapSpec selector(Expression selectorExpression) {
        return this.selector(new ExpressionEvaluatingSelector(selectorExpression));
    }

    public WireTapSpec selector(MessageSelector selector) {
        this.selector = selector;
        return this;
    }

    public WireTapSpec timeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    protected WireTap doGet() {
        WireTap wireTap2 = this.channel != null ? new WireTap(this.channel, this.selector) : new WireTap(this.channelName, this.selector);
        if (this.timeout != null) {
            wireTap2.setTimeout(this.timeout);
        }
        return wireTap2;
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        if (this.selector != null) {
            return Collections.singletonMap(this.selector, null);
        }
        return null;
    }
}

