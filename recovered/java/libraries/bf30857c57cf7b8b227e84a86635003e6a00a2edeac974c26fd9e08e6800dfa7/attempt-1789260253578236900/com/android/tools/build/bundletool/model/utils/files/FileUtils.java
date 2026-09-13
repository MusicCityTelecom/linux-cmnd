/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.files;

import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.List;

public final class FileUtils {
    public static void createParentDirectories(Path path) {
        FileUtils.createDirectories(path.getParent());
    }

    public static void createDirectories(Path dir) {
        try {
            Files.createDirectories(dir, new FileAttribute[0]);
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error creating directories '%s'.", dir), e2);
        }
    }

    public static ImmutableList<Path> getDistinctParentPaths(Collection<Path> paths) {
        return paths.stream().map(Path::getParent).distinct().collect(ImmutableList.toImmutableList());
    }

    public static String getFileExtension(ZipPath path) {
        if (path.getNameCount() == 0) {
            return "";
        }
        ZipPath name = path.getFileName();
        if (name == null) {
            return "";
        }
        String fileName = name.toString();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    public static List<ZipPath> toPathWalkingOrder(Collection<ZipPath> paths) {
        ImmutableSortedSet.Builder walkOrderedSet = ImmutableSortedSet.naturalOrder();
        for (ZipPath path : paths) {
            for (int i2 = 0; i2 < path.getNameCount(); ++i2) {
                walkOrderedSet.add(path.subpath(0, i2 + 1));
            }
        }
        return walkOrderedSet.build().asList();
    }

    /*
     * Exception decompiling
     */
    public static boolean equalContent(InputStream is1, InputStream is2) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private FileUtils() {
    }
}

