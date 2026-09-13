/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Message
 *  org.springframework.messaging.support.HeaderMapper
 */
package org.springframework.jms.support;

import javax.jms.Message;
import org.springframework.messaging.support.HeaderMapper;

public interface JmsHeaderMapper
extends HeaderMapper<Message> {
    public static final String CONTENT_TYPE_PROPERTY = "content_type";
}

