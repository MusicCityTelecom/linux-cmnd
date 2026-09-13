/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.mapping;

import java.util.Map;
import org.springframework.messaging.MessageHeaders;

public interface RequestReplyHeaderMapper<T> {
    public void fromHeadersToRequest(MessageHeaders var1, T var2);

    public void fromHeadersToReply(MessageHeaders var1, T var2);

    public Map<String, Object> toHeadersFromRequest(T var1);

    public Map<String, Object> toHeadersFromReply(T var1);
}

