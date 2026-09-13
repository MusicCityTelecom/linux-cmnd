/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.mapping;

import java.util.Map;
import org.springframework.messaging.MessageHeaders;

public interface HeaderMapper<T> {
    public void fromHeaders(MessageHeaders var1, T var2);

    public Map<String, Object> toHeaders(T var1);
}

