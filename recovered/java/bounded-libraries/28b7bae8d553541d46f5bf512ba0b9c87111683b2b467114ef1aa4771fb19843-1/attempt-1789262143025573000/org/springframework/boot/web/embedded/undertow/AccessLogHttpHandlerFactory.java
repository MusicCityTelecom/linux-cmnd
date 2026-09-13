/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow
 *  io.undertow.server.HttpHandler
 *  io.undertow.server.handlers.accesslog.AccessLogHandler
 *  io.undertow.server.handlers.accesslog.AccessLogReceiver
 *  io.undertow.server.handlers.accesslog.DefaultAccessLogReceiver
 *  org.springframework.util.Assert
 *  org.xnio.OptionMap
 *  org.xnio.Options
 *  org.xnio.Xnio
 *  org.xnio.XnioWorker
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.accesslog.AccessLogHandler;
import io.undertow.server.handlers.accesslog.AccessLogReceiver;
import io.undertow.server.handlers.accesslog.DefaultAccessLogReceiver;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.util.Assert;
import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Xnio;
import org.xnio.XnioWorker;

class AccessLogHttpHandlerFactory
implements HttpHandlerFactory {
    private final File directory;
    private final String pattern;
    private final String prefix;
    private final String suffix;
    private final boolean rotate;

    AccessLogHttpHandlerFactory(File directory, String pattern, String prefix, String suffix, boolean rotate) {
        this.directory = directory;
        this.pattern = pattern;
        this.prefix = prefix;
        this.suffix = suffix;
        this.rotate = rotate;
    }

    @Override
    public HttpHandler getHandler(HttpHandler next) {
        try {
            this.createAccessLogDirectoryIfNecessary();
            XnioWorker worker = this.createWorker();
            String baseName = this.prefix != null ? this.prefix : "access_log.";
            String formatString = this.pattern != null ? this.pattern : "common";
            return new ClosableAccessLogHandler(next, worker, new DefaultAccessLogReceiver((Executor)worker, this.directory, baseName, this.suffix, this.rotate), formatString);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to create AccessLogHandler", ex);
        }
    }

    private void createAccessLogDirectoryIfNecessary() {
        Assert.state((this.directory != null ? 1 : 0) != 0, (String)"Access log directory is not set");
        if (!this.directory.isDirectory() && !this.directory.mkdirs()) {
            throw new IllegalStateException("Failed to create access log directory '" + this.directory + "'");
        }
    }

    private XnioWorker createWorker() throws IOException {
        Xnio xnio = Xnio.getInstance((ClassLoader)Undertow.class.getClassLoader());
        return xnio.createWorker(OptionMap.builder().set(Options.THREAD_DAEMON, true).getMap());
    }

    private static class ClosableAccessLogHandler
    extends AccessLogHandler
    implements Closeable {
        private final DefaultAccessLogReceiver accessLogReceiver;
        private final XnioWorker worker;

        ClosableAccessLogHandler(HttpHandler next, XnioWorker worker, DefaultAccessLogReceiver accessLogReceiver, String formatString) {
            super(next, (AccessLogReceiver)accessLogReceiver, formatString, Undertow.class.getClassLoader());
            this.worker = worker;
            this.accessLogReceiver = accessLogReceiver;
        }

        @Override
        public void close() throws IOException {
            try {
                this.accessLogReceiver.close();
                this.worker.shutdown();
                this.worker.awaitTermination(30L, TimeUnit.SECONDS);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

