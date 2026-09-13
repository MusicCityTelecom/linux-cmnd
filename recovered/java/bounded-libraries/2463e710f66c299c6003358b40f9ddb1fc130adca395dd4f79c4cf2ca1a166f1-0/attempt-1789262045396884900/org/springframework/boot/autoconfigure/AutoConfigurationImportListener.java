/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure;

import java.util.EventListener;
import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;

@FunctionalInterface
public interface AutoConfigurationImportListener
extends EventListener {
    public void onAutoConfigurationImportEvent(AutoConfigurationImportEvent var1);
}

