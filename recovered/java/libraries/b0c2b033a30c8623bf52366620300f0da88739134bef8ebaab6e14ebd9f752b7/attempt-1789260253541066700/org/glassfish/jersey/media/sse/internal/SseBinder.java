/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import javax.inject.Singleton;
import javax.ws.rs.sse.Sse;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.media.sse.internal.JerseySse;

public class SseBinder
extends AbstractBinder {
    @Override
    protected void configure() {
        ((ClassBinding)this.bind(JerseySse.class).to(Sse.class)).in(Singleton.class);
    }
}

