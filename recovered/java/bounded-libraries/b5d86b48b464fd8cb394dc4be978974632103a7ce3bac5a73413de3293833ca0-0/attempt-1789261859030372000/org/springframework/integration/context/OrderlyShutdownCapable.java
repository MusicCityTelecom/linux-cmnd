/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.context;

public interface OrderlyShutdownCapable {
    public int beforeShutdown();

    public int afterShutdown();
}

