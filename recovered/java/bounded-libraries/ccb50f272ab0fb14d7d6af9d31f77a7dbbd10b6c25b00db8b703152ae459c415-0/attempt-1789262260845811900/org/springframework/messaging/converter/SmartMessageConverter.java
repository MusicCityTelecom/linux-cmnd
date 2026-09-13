/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.converter;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;

public interface SmartMessageConverter
extends MessageConverter {
    @Nullable
    public Object fromMessage(Message<?> var1, Class<?> var2, @Nullable Object var3);

    @Nullable
    public Message<?> toMessage(Object var1, @Nullable MessageHeaders var2, @Nullable Object var3);
}

