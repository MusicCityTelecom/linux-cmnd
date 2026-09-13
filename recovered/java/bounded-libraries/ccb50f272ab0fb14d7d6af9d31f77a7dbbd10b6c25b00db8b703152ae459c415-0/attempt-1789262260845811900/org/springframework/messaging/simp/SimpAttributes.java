/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SimpAttributes {
    public static final String SESSION_MUTEX_NAME = SimpAttributes.class.getName() + ".MUTEX";
    public static final String SESSION_COMPLETED_NAME = SimpAttributes.class.getName() + ".COMPLETED";
    public static final String DESTRUCTION_CALLBACK_NAME_PREFIX = SimpAttributes.class.getName() + ".DESTRUCTION_CALLBACK.";
    private static final Log logger = SimpLogging.forLogName(SimpAttributes.class);
    private final String sessionId;
    private final Map<String, Object> attributes;

    public SimpAttributes(String sessionId, Map<String, Object> attributes) {
        Assert.notNull((Object)sessionId, (String)"'sessionId' is required");
        Assert.notNull(attributes, (String)"'attributes' is required");
        this.sessionId = sessionId;
        this.attributes = attributes;
    }

    @Nullable
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
        this.removeDestructionCallback(name);
    }

    public String[] getAttributeNames() {
        return StringUtils.toStringArray(this.attributes.keySet());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerDestructionCallback(String name, Runnable callback) {
        Object object = this.getSessionMutex();
        synchronized (object) {
            if (this.isSessionCompleted()) {
                throw new IllegalStateException("Session id=" + this.getSessionId() + " already completed");
            }
            this.attributes.put(DESTRUCTION_CALLBACK_NAME_PREFIX + name, callback);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeDestructionCallback(String name) {
        Object object = this.getSessionMutex();
        synchronized (object) {
            this.attributes.remove(DESTRUCTION_CALLBACK_NAME_PREFIX + name);
        }
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public Object getSessionMutex() {
        Map<String, Object> mutex = this.attributes.get(SESSION_MUTEX_NAME);
        if (mutex == null) {
            mutex = this.attributes;
        }
        return mutex;
    }

    public boolean isSessionCompleted() {
        return this.attributes.get(SESSION_COMPLETED_NAME) != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sessionCompleted() {
        Object object = this.getSessionMutex();
        synchronized (object) {
            if (!this.isSessionCompleted()) {
                this.executeDestructionCallbacks();
                this.attributes.put(SESSION_COMPLETED_NAME, Boolean.TRUE);
            }
        }
    }

    private void executeDestructionCallbacks() {
        this.attributes.forEach((key, value) -> {
            if (key.startsWith(DESTRUCTION_CALLBACK_NAME_PREFIX)) {
                try {
                    ((Runnable)value).run();
                }
                catch (Throwable ex) {
                    logger.error((Object)"Uncaught error in session attribute destruction callback", ex);
                }
            }
        });
    }

    public static SimpAttributes fromMessage(Message<?> message) {
        Assert.notNull(message, (String)"Message must not be null");
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        Map<String, Object> sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(headers);
        if (sessionId == null) {
            throw new IllegalStateException("No session id in " + message);
        }
        if (sessionAttributes == null) {
            throw new IllegalStateException("No session attributes in " + message);
        }
        return new SimpAttributes(sessionId, sessionAttributes);
    }
}

