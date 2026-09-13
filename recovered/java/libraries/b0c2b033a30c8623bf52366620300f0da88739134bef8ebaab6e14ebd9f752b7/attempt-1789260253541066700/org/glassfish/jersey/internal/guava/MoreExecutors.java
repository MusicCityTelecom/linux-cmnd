/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.concurrent.Executor;

public final class MoreExecutors {
    private MoreExecutors() {
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    private static enum DirectExecutor implements Executor
    {
        INSTANCE;


        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}

