/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import org.glassfish.jersey.client.ClientConfig;

public interface Initializable<T extends Initializable<T>> {
    public T preInitialize();

    public ClientConfig getConfiguration();
}

