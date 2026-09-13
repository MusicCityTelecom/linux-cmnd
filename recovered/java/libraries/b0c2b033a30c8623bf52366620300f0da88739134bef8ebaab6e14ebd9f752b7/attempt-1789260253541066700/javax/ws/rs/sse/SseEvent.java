/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.sse;

public interface SseEvent {
    public static final long RECONNECT_NOT_SET = -1L;

    public String getId();

    public String getName();

    public String getComment();

    public long getReconnectDelay();

    public boolean isReconnectDelaySet();
}

