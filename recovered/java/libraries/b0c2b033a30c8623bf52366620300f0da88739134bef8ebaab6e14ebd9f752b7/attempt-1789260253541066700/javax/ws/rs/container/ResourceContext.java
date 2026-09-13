/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.container;

public interface ResourceContext {
    public <T> T getResource(Class<T> var1);

    public <T> T initResource(T var1);
}

