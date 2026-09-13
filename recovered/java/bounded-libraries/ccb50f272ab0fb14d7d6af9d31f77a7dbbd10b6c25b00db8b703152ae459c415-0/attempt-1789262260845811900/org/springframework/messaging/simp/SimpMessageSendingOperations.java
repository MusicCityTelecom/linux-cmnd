/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp;

import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.core.MessageSendingOperations;

public interface SimpMessageSendingOperations
extends MessageSendingOperations<String> {
    public void convertAndSendToUser(String var1, String var2, Object var3) throws MessagingException;

    public void convertAndSendToUser(String var1, String var2, Object var3, Map<String, Object> var4) throws MessagingException;

    public void convertAndSendToUser(String var1, String var2, Object var3, MessagePostProcessor var4) throws MessagingException;

    public void convertAndSendToUser(String var1, String var2, Object var3, @Nullable Map<String, Object> var4, @Nullable MessagePostProcessor var5) throws MessagingException;
}

