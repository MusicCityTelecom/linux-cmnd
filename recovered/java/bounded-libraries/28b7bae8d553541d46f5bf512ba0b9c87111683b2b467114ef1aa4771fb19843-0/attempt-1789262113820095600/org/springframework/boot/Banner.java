/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 */
package org.springframework.boot;

import java.io.PrintStream;
import org.springframework.core.env.Environment;

@FunctionalInterface
public interface Banner {
    public void printBanner(Environment var1, Class<?> var2, PrintStream var3);

    public static enum Mode {
        OFF,
        CONSOLE,
        LOG;

    }
}

