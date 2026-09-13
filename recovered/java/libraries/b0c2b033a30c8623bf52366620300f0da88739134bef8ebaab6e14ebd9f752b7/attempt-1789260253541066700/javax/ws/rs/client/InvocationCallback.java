/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

public interface InvocationCallback<RESPONSE> {
    public void completed(RESPONSE var1);

    public void failed(Throwable var1);
}

