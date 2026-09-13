/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.util.Arrays;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class HeaderFilter
extends IntegrationObjectSupport
implements Transformer,
IntegrationPattern {
    private final String[] headersToRemove;
    private volatile boolean patternMatch = true;

    public HeaderFilter(String ... headersToRemove) {
        Assert.notEmpty((Object[])headersToRemove, (String)"At least one header name to remove is required.");
        this.headersToRemove = Arrays.copyOf(headersToRemove, headersToRemove.length);
    }

    public void setPatternMatch(boolean patternMatch) {
        this.patternMatch = patternMatch;
    }

    @Override
    public String getComponentType() {
        return "header-filter";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.header_filter;
    }

    @Override
    protected void onInit() {
        super.onInit();
        if (this.getMessageBuilderFactory() instanceof DefaultMessageBuilderFactory) {
            for (String header : this.headersToRemove) {
                if (header.contains("*") || !"id".equals(header) && !"timestamp".equals(header)) continue;
                throw new BeanInitializationException("HeaderFilter cannot remove 'id' and 'timestamp' read-only headers.\nWrong 'headersToRemove' [" + Arrays.toString(this.headersToRemove) + "] configuration for " + this.getComponentName());
            }
        }
    }

    @Override
    public Message<?> transform(Message<?> message) {
        AbstractIntegrationMessageBuilder<?> builder = this.getMessageBuilderFactory().fromMessage(message);
        if (this.patternMatch) {
            builder.removeHeaders(this.headersToRemove);
        } else {
            for (String headerToRemove : this.headersToRemove) {
                builder.removeHeader(headerToRemove);
            }
        }
        return builder.build();
    }
}

