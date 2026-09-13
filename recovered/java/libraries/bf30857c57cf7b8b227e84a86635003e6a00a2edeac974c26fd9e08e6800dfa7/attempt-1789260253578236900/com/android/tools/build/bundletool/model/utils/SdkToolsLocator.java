/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.OsPlatform;
import com.android.tools.build.bundletool.model.utils.SystemEnvironmentProvider;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MoreCollectors;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public final class SdkToolsLocator {
    public static final String ANDROID_HOME_VARIABLE = "ANDROID_HOME";
    public static final String SYSTEM_PATH_VARIABLE = "PATH";
    private static final String ADB_PATH_GLOB = "glob:**/{adb,adb.exe}";
    private static final String ADB_SDK_GLOB = "glob:**/platform-tools/{adb,adb.exe}";
    private static final BiPredicate<Path, BasicFileAttributes> AAPT2_MATCHER = (file, attrs) -> file.getFileName().toString().matches("aapt2(\\.exe)?");
    private final FileSystem fileSystem;

    public SdkToolsLocator() {
        this(FileSystems.getDefault());
    }

    SdkToolsLocator(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Optional<Path> extractAapt2(Path tempDir) {
        Path aapt2;
        block35: {
            String osDir = SdkToolsLocator.getOsSpecificJarDirectory();
            URL osDirUrl = SdkToolsLocator.class.getResource(osDir);
            if (osDirUrl == null) {
                return Optional.empty();
            }
            try {
                Path outputDir = tempDir.resolve("output");
                if ("jar".equals(osDirUrl.getProtocol())) {
                    this.extractFilesFromJar(outputDir, osDirUrl, osDir);
                    try (Stream<Path> aapt2Binaries = Files.find(outputDir, 3, AAPT2_MATCHER, new FileVisitOption[0]);){
                        aapt2 = (Path)aapt2Binaries.collect(MoreCollectors.onlyElement());
                        break block35;
                    }
                }
                try (Stream<Path> aapt2Binaries = Files.find(Paths.get(osDirUrl.toURI()), 3, AAPT2_MATCHER, new FileVisitOption[0]);){
                    Optional<Path> aapt2Path = aapt2Binaries.findFirst();
                    if (!aapt2Path.isPresent()) {
                        Optional<Path> optional = Optional.empty();
                        return optional;
                    }
                    aapt2 = aapt2Path.get();
                }
            }
            catch (NoSuchElementException e2) {
                throw new CommandExecutionException("Unable to locate aapt2 inside jar.", e2);
            }
            catch (IOException | URISyntaxException e3) {
                throw new CommandExecutionException("Unable to extract aapt2 from jar.", e3);
            }
        }
        Preconditions.checkState(Files.exists(aapt2, new LinkOption[0]));
        try {
            aapt2.toFile().setExecutable(true);
            return Optional.of(aapt2);
        }
        catch (SecurityException e4) {
            throw new CommandExecutionException("Unable to make aapt2 executable. This may be a permission issue. If it persists, consider passing the path to aapt2 using the flag --aapt2.", e4);
        }
    }

    private void extractFilesFromJar(Path outputDir, URL directoryUrl, String startDir) throws IOException, URISyntaxException {
        try (FileSystem fs = FileSystems.newFileSystem(directoryUrl.toURI(), ImmutableMap.of());
             Stream<Path> paths = Files.walk(fs.getPath(startDir, new String[0]), new FileVisitOption[0]);){
            for (Path path : paths.collect(ImmutableList.toImmutableList())) {
                String pathStr = path.toString();
                InputStream is = SdkToolsLocator.sanitize(this.getClass().getResourceAsStream(pathStr));
                Throwable throwable = null;
                try {
                    if (is.available() == 0) continue;
                    Path target = outputDir.resolve(pathStr.replaceFirst("^/", ""));
                    Files.createDirectories(target.getParent(), new FileAttribute[0]);
                    Files.copy(is, target, new CopyOption[0]);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (is == null) continue;
                    if (throwable != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    is.close();
                }
            }
        }
    }

    private static String getOsSpecificJarDirectory() {
        switch (OsPlatform.getCurrentPlatform()) {
            case WINDOWS: {
                return "/windows";
            }
            case MACOS: {
                return "/macos";
            }
            case LINUX: {
                return "/linux";
            }
            case OTHER: {
                return "/linux";
            }
        }
        throw new IllegalStateException();
    }

    private static InputStream sanitize(InputStream is) throws IOException {
        try {
            is.available();
            return is;
        }
        catch (NullPointerException e2) {
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    public Optional<Path> locateAdb(SystemEnvironmentProvider systemEnvironmentProvider) {
        Optional<Path> adbInSdkDir = this.locateAdbInSdkDir(systemEnvironmentProvider);
        if (adbInSdkDir.isPresent()) {
            return adbInSdkDir;
        }
        Optional<Path> adbOnPath = this.locateBinaryOnSystemPath(ADB_PATH_GLOB, systemEnvironmentProvider);
        if (adbOnPath.isPresent()) {
            return adbOnPath;
        }
        return Optional.empty();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Optional<Path> locateAdbInSdkDir(SystemEnvironmentProvider systemEnvironmentProvider) {
        Optional<String> sdkDir = systemEnvironmentProvider.getVariable(ANDROID_HOME_VARIABLE);
        if (!sdkDir.isPresent()) {
            return Optional.empty();
        }
        Path platformToolsDir = this.fileSystem.getPath(sdkDir.get(), "platform-tools");
        if (!Files.isDirectory(platformToolsDir, new LinkOption[0])) {
            return Optional.empty();
        }
        PathMatcher adbPathMatcher = this.fileSystem.getPathMatcher(ADB_SDK_GLOB);
        try (Stream<Path> pathStream = Files.find(platformToolsDir, 1, (path, attributes) -> adbPathMatcher.matches((Path)path) && Files.isExecutable(path), new FileVisitOption[0]);){
            Optional<Path> optional = pathStream.findFirst();
            return optional;
        }
        catch (IOException e2) {
            throw CommandExecutionException.builder().withCause(e2).withMessage("Error while trying to locate adb in SDK dir '%s'.", sdkDir).build();
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private Optional<Path> locateBinaryOnSystemPath(String binaryGlob, SystemEnvironmentProvider systemEnvironmentProvider) {
        Optional<String> rawPath = systemEnvironmentProvider.getVariable(SYSTEM_PATH_VARIABLE);
        if (!rawPath.isPresent()) {
            return Optional.empty();
        }
        String pathSeparator = systemEnvironmentProvider.getProperty("path.separator").get();
        PathMatcher binPathMatcher = this.fileSystem.getPathMatcher(binaryGlob);
        for (String pathDir : Splitter.on(pathSeparator).splitToList(rawPath.get())) {
            try {
                Stream<Path> pathStream222 = Files.find(this.fileSystem.getPath(pathDir, new String[0]), 1, (path, attributes) -> binPathMatcher.matches((Path)path) && Files.isExecutable(path), new FileVisitOption[0]);
                Throwable throwable = null;
                try {
                    Optional<Path> binaryInDir = pathStream222.findFirst();
                    if (!binaryInDir.isPresent()) continue;
                    Optional<Path> optional = binaryInDir;
                    return optional;
                }
                catch (Throwable throwable4) {
                    throwable = throwable4;
                    throw throwable4;
                }
                finally {
                    if (pathStream222 == null) continue;
                    if (throwable != null) {
                        try {
                            pathStream222.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        continue;
                    }
                    pathStream222.close();
                }
            }
            catch (NoSuchFileException | NotDirectoryException pathStream222) {
            }
            catch (IOException e2) {
                throw CommandExecutionException.builder().withCause(e2).withMessage("Error while trying to locate adb on system PATH in directory '%s'.", pathDir).build();
            }
        }
        return Optional.empty();
    }
}

