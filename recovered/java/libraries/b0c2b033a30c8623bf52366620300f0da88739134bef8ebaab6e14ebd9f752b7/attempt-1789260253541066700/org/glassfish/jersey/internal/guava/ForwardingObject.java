/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

abstract class ForwardingObject {
    ForwardingObject() {
    }

    protected abstract Object delegate();

    public String toString() {
        return this.delegate().toString();
    }
}

