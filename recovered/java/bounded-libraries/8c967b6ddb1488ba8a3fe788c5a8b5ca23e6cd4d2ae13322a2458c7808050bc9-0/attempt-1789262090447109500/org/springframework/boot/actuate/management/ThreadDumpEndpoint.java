/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.management;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.management.PlainTextThreadDumpFormatter;

@Endpoint(id="threaddump")
public class ThreadDumpEndpoint {
    private final PlainTextThreadDumpFormatter plainTextFormatter = new PlainTextThreadDumpFormatter();

    @ReadOperation
    public ThreadDumpDescriptor threadDump() {
        return this.getFormattedThreadDump(x$0 -> new ThreadDumpDescriptor((ThreadInfo[])x$0));
    }

    @ReadOperation(produces={"text/plain;charset=UTF-8"})
    public String textThreadDump() {
        return this.getFormattedThreadDump(this.plainTextFormatter::format);
    }

    private <T> T getFormattedThreadDump(Function<ThreadInfo[], T> formatter) {
        return formatter.apply(ManagementFactory.getThreadMXBean().dumpAllThreads(true, true));
    }

    public static final class ThreadDumpDescriptor {
        private final List<ThreadInfo> threads;

        private ThreadDumpDescriptor(ThreadInfo[] threads) {
            this.threads = Arrays.asList(threads);
        }

        public List<ThreadInfo> getThreads() {
            return this.threads;
        }
    }
}

