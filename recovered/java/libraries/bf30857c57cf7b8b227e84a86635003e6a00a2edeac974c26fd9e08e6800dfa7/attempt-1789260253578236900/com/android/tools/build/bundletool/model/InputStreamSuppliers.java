/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.google.common.base.Preconditions;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class InputStreamSuppliers {
    private InputStreamSuppliers() {
    }

    public static InputStreamSupplier fromZipEntry(ZipEntry zipEntry, ZipFile zipFile) {
        Preconditions.checkArgument(!zipEntry.getName().isEmpty(), "Path is empty");
        Preconditions.checkArgument(!zipEntry.isDirectory(), "Expected file, found directory: %s", (Object)zipEntry.getName());
        return BufferedIo.inputStreamSupplier(zipFile, zipEntry);
    }

    public static InputStreamSupplier fromBytes(byte[] contents) {
        byte[] contentsCopy = Arrays.copyOf(contents, contents.length);
        return () -> new ByteArrayInputStream(contentsCopy);
    }

    public static InputStreamSupplier fromFile(Path fileSystemPath) {
        Preconditions.checkArgument(Files.isRegularFile(fileSystemPath, new LinkOption[0]), "Expecting '%s' to be an existing regular file.", (Object)fileSystemPath);
        return BufferedIo.inputStreamSupplier(fileSystemPath);
    }
}

