/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.files;

import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.base.Preconditions;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public final class FilePreconditions {
    public static void checkFileDoesNotExist(Path path) {
        Preconditions.checkArgument(!Files.exists(path, new LinkOption[0]), "File '%s' already exists.", (Object)path);
    }

    public static void checkFileExistsAndReadable(Path path) {
        Preconditions.checkArgument(Files.exists(path, new LinkOption[0]), "File '%s' was not found.", (Object)path);
        Preconditions.checkArgument(Files.isReadable(path), "File '%s' is not readable.", (Object)path);
        Preconditions.checkArgument(!Files.isDirectory(path, new LinkOption[0]), "File '%s' is a directory.", (Object)path);
    }

    public static void checkFileExistsAndExecutable(Path path) {
        Preconditions.checkArgument(Files.exists(path, new LinkOption[0]), "File '%s' was not found.", (Object)path);
        Preconditions.checkArgument(Files.isExecutable(path), "File '%s' is not executable.", (Object)path);
    }

    public static void checkFileHasExtension(String fileDescription, ZipPath path, String extension) {
        FilePreconditions.checkFileHasExtension(fileDescription, path.getFileName().toString(), extension);
    }

    public static void checkFileHasExtension(String fileDescription, Path path, String extension) {
        FilePreconditions.checkFileHasExtension(fileDescription, path.getFileName().toString(), extension);
    }

    private static void checkFileHasExtension(String fileDescription, String filename, String extension) {
        Preconditions.checkArgument(filename.endsWith(extension), "%s '%s' is expected to have '%s' extension.", (Object)fileDescription, (Object)filename, (Object)extension);
    }

    public static void checkDirectoryExists(Path path) {
        Preconditions.checkArgument(Files.exists(path, new LinkOption[0]), "Directory '%s' was not found.", (Object)path);
        Preconditions.checkArgument(Files.isDirectory(path, new LinkOption[0]), "'%s' is not a directory.");
    }

    public static void checkDirectoryExistsAndEmpty(Path path) {
        FilePreconditions.checkDirectoryExists(path);
        Preconditions.checkArgument(path.toFile().list().length == 0, "Directory '%s' is not empty.", (Object)path);
    }

    private FilePreconditions() {
    }
}

