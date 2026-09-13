/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.io;

import java.io.File;
import java.util.function.Consumer;
import lombok.Generated;
import org.apereo.cas.util.io.PathWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWatcherService
extends PathWatcherService {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWatcherService.class);

    public FileWatcherService(File watchableFile, Consumer<File> onCreate, Consumer<File> onModify, Consumer<File> onDelete) {
        super(watchableFile.getParentFile().toPath(), FileWatcherService.getWatchedFileConsumer(watchableFile, onCreate), FileWatcherService.getWatchedFileConsumer(watchableFile, onModify), FileWatcherService.getWatchedFileConsumer(watchableFile, onDelete));
    }

    public FileWatcherService(File watchableFile, Consumer<File> onModify) {
        super(watchableFile.getParentFile(), FileWatcherService.getWatchedFileConsumer(watchableFile, onModify));
    }

    private static Consumer<File> getWatchedFileConsumer(File watchableFile, Consumer<File> consumer) {
        return file -> {
            if (file.getPath().equals(watchableFile.getPath())) {
                LOGGER.trace("Detected change in file [{}] and calling change consumer to handle event", file);
                consumer.accept((File)file);
            }
        };
    }
}

