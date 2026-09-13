/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

public interface RequestContext {
    public RequestContext getReference();

    public void release();
}

