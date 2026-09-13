/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.transformer.support;

import org.springframework.integration.handler.MessageProcessor;

public interface HeaderValueMessageProcessor<T>
extends MessageProcessor<T> {
    public Boolean isOverwrite();
}

