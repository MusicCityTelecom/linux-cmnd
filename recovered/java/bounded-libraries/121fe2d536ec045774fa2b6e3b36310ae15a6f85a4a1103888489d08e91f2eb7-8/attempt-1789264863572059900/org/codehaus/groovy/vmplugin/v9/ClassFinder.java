/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v9;

import groovy.lang.Tuple2;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.codehaus.groovy.vmplugin.v9.ClassFindFailedException;

public class ClassFinder {
    public static Map<String, Set<String>> find(URI classpathEntryURI, String packageName) {
        return ClassFinder.find(classpathEntryURI, packageName, false);
    }

    public static Map<String, Set<String>> find(URI classpathEntryURI, String packageName, boolean recursive) {
        String prefix;
        URI uri;
        String scheme = classpathEntryURI.getScheme();
        if ("jrt".equals(scheme)) {
            uri = URI.create("jrt:/");
            prefix = classpathEntryURI.getPath().substring(1) + "/";
        } else if ("file".equals(scheme)) {
            Path path = Paths.get(classpathEntryURI);
            if (!Files.exists(path, new LinkOption[0])) {
                throw new IllegalArgumentException(path + " does not exist");
            }
            if (Files.isDirectory(path, new LinkOption[0])) {
                uri = URI.create("file:/");
                prefix = path.toString();
            } else {
                uri = URI.create("jar:" + classpathEntryURI.toString());
                prefix = "";
            }
        } else {
            throw new UnsupportedOperationException(classpathEntryURI + " is not supported");
        }
        return ClassFinder.find(uri, prefix, packageName, recursive);
    }

    private static Map<String, Set<String>> find(URI uri, String prefix, final String packageName, final boolean recursive) {
        final boolean wfs = "file".equals(uri.getScheme());
        if (wfs) {
            prefix = prefix.replace("/", File.separator);
        }
        final String sepPatten = Pattern.quote(wfs ? File.separator : "/");
        final int prefixElemCnt = prefix.trim().isEmpty() ? 0 : prefix.split(sepPatten).length;
        final LinkedHashMap<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
        Tuple2<FileSystem, Boolean> fsMaybeNew = null;
        try {
            fsMaybeNew = ClassFinder.maybeNewFileSystem(uri);
            Files.walkFileTree(fsMaybeNew.getV1().getPath(prefix + "/" + packageName, new String[0]), (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    String[] pathElems = path.toString().split(sepPatten);
                    int nameCount = pathElems.length;
                    if (nameCount <= prefixElemCnt) {
                        return FileVisitResult.CONTINUE;
                    }
                    String filename = pathElems[nameCount - 1];
                    if (!filename.endsWith(".class") || "module-info.class".equals(filename) || "package-info.class".equals(filename)) {
                        return FileVisitResult.CONTINUE;
                    }
                    String packagePathStr = String.join((CharSequence)"/", Arrays.copyOfRange(pathElems, prefixElemCnt + (!wfs && pathElems[0].isEmpty() ? 1 : 0), nameCount - 1));
                    if (recursive || packageName.equals(packagePathStr)) {
                        Set packageNameSet = result.computeIfAbsent(filename.substring(0, filename.lastIndexOf(46)), f -> new HashSet(2));
                        packageNameSet.add(packagePathStr);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
        }
        catch (Exception e) {
            throw new ClassFindFailedException(String.format("Failed to find classes via uri: %s, prefix: %s, packageName: %s, recursive: %s", uri, prefix, packageName, recursive), e);
        }
        finally {
            if (fsMaybeNew != null && fsMaybeNew.getV2().booleanValue()) {
                ClassFinder.closeQuietly(fsMaybeNew.getV1());
            }
        }
        return result;
    }

    private static void closeQuietly(FileSystem fs) {
        try {
            fs.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private static Tuple2<FileSystem, Boolean> maybeNewFileSystem(URI uri) throws IOException {
        try {
            return new Tuple2<FileSystem, Boolean>(FileSystems.newFileSystem(uri, Collections.emptyMap()), true);
        }
        catch (FileSystemAlreadyExistsException e) {
            return new Tuple2<FileSystem, Boolean>(FileSystems.getFileSystem(uri), false);
        }
    }

    private ClassFinder() {
    }
}

