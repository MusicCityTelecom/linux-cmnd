/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.jooq.lambda.Unchecked
 *  org.jooq.lambda.fi.util.function.CheckedConsumer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 */
package org.apereo.cas.util.io;

import com.sun.nio.file.SensitivityWatchEventModifier;
import java.io.Closeable;
import java.io.File;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.io.WatcherService;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class PathWatcherService
implements WatcherService,
Runnable,
Closeable,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PathWatcherService.class);
    private static final WatchEvent.Kind[] KINDS = new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY};
    private final WatchService watcher;
    private final Consumer<File> onCreate;
    private final Consumer<File> onModify;
    private final Consumer<File> onDelete;
    private Thread thread;

    public PathWatcherService(File watchablePath, Consumer<File> onModify) {
        this(watchablePath.toPath(), file -> {}, onModify, file -> {});
    }

    public PathWatcherService(Path watchablePath, Consumer<File> onCreate, Consumer<File> onModify, Consumer<File> onDelete) {
        LOGGER.info("Watching directory path at [{}]", (Object)watchablePath);
        this.onCreate = onCreate;
        this.onModify = onModify;
        this.onDelete = onDelete;
        this.watcher = (WatchService)FunctionUtils.doUnchecked(() -> watchablePath.getFileSystem().newWatchService());
        LOGGER.trace("Created watcher for events of type [{}]", (Object)Arrays.stream(KINDS).map(WatchEvent.Kind::name).collect(Collectors.joining(",")));
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)unused -> watchablePath.register(this.watcher, KINDS, SensitivityWatchEventModifier.HIGH)), new Object[0]);
    }

    @Override
    public void run() {
        try {
            WatchKey key = null;
            while ((key = this.watcher.take()) != null) {
                this.handleEvent(key);
                boolean valid = key.reset();
                if (valid) continue;
                LOGGER.info("Directory key is no longer valid. Quitting watcher service");
            }
        }
        catch (InterruptedException | ClosedWatchServiceException e) {
            LOGGER.trace(e.getMessage(), (Throwable)e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() {
        LOGGER.trace("Closing service registry watcher thread");
        IOUtils.closeQuietly((Closeable)this.watcher);
        if (this.thread != null) {
            this.thread.interrupt();
        }
        LOGGER.trace("Closed service registry watcher thread");
    }

    @Override
    public void start(String name) {
        LOGGER.trace("Starting watcher thread");
        this.thread = new Thread(this);
        this.thread.setName(name);
        this.thread.start();
    }

    public void destroy() {
        this.close();
    }

    private void handleEvent(WatchKey key) {
        key.pollEvents().forEach(Unchecked.consumer(event -> {
            String eventName = event.kind().name();
            WatchEvent ev = event;
            Path filename = (Path)ev.context();
            Path parent = (Path)key.watchable();
            Path fullPath = parent.resolve(filename);
            File file = fullPath.toFile();
            LOGGER.trace("Detected event [{}] on file [{}]", (Object)eventName, (Object)file);
            if (eventName.equals(StandardWatchEventKinds.ENTRY_CREATE.name()) && file.exists()) {
                this.onCreate.accept(file);
            } else if (eventName.equals(StandardWatchEventKinds.ENTRY_DELETE.name())) {
                this.onDelete.accept(file);
            } else if (eventName.equals(StandardWatchEventKinds.ENTRY_MODIFY.name()) && file.exists()) {
                this.onModify.accept(file);
            }
        }));
    }
}

