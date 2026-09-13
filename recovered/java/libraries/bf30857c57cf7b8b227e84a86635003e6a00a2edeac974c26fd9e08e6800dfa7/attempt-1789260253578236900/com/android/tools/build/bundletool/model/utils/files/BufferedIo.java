/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.files;

import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.google.errorprone.annotations.MustBeClosed;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.WillCloseWhenClosed;
import javax.annotation.WillNotClose;

public final class BufferedIo {
    @MustBeClosed
    public static BufferedReader reader(@WillCloseWhenClosed InputStream is) {
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    @MustBeClosed
    public static BufferedReader reader(Path file) throws IOException {
        return Files.newBufferedReader(file, StandardCharsets.UTF_8);
    }

    @MustBeClosed
    public static InputStream inputStream(Path file) throws IOException {
        return BufferedIo.makeBuffered(Files.newInputStream(file, new OpenOption[0]));
    }

    @MustBeClosed
    public static InputStream inputStream(@WillNotClose ZipFile zipFile, ZipEntry zipEntry) throws IOException {
        return BufferedIo.makeBuffered(zipFile.getInputStream(zipEntry));
    }

    public static InputStreamSupplier inputStreamSupplier(Path file) {
        return () -> BufferedIo.inputStream(file);
    }

    public static InputStreamSupplier inputStreamSupplier(@WillNotClose ZipFile zipFile, ZipEntry zipEntry) {
        return () -> BufferedIo.inputStream(zipFile, zipEntry);
    }

    @MustBeClosed
    public static OutputStream outputStream(Path file) throws IOException {
        return BufferedIo.makeBuffered(Files.newOutputStream(file, new OpenOption[0]));
    }

    @MustBeClosed
    static InputStream makeBuffered(@WillCloseWhenClosed InputStream is) {
        return is instanceof BufferedInputStream || is instanceof ByteArrayInputStream ? is : new BufferedInputStream(is);
    }

    @MustBeClosed
    static OutputStream makeBuffered(@WillCloseWhenClosed OutputStream os) {
        return os instanceof BufferedOutputStream || os instanceof ByteArrayOutputStream ? os : new BufferedOutputStream(os);
    }

    private BufferedIo() {
    }
}

