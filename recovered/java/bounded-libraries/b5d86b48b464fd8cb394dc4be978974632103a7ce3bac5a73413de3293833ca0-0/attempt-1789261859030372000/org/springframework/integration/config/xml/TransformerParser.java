/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.config.xml;

import org.springframework.integration.config.TransformerFactoryBean;
import org.springframework.integration.config.xml.AbstractDelegatingConsumerEndpointParser;

public class TransformerParser
extends AbstractDelegatingConsumerEndpointParser {
    @Override
    String getFactoryBeanClassName() {
        return TransformerFactoryBean.class.getName();
    }

    @Override
    boolean hasDefaultOption() {
        return false;
    }
}

