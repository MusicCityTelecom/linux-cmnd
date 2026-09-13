/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.size;

import com.android.bundle.SizesOuterClass;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.utils.ZipUtils;
import com.android.tools.build.bundletool.size.ApkComponent;
import com.android.tools.build.bundletool.size.SizeUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import java.io.IOException;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ApkBreakdownGenerator {
    public static SizesOuterClass.Breakdown calculateBreakdown(Path apkPath) throws IOException {
        try (ZipFile apk = new ZipFile(apkPath.toFile());){
            ImmutableMap<String, Long> downloadSizeByEntry = ApkBreakdownGenerator.calculateDownloadSizePerEntry(apk);
            ImmutableMap downloadSizeByComponent = downloadSizeByEntry.entrySet().stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(entry -> ApkComponent.fromEntryName((String)entry.getKey()), Collectors.summingLong(Map.Entry::getValue)), ImmutableMap::copyOf));
            ImmutableMap diskSizeByComponent = apk.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(zipEntry -> ApkComponent.fromEntryName(zipEntry.getName()), Collectors.summingLong(ZipEntry::getCompressedSize)), ImmutableMap::copyOf));
            SizesOuterClass.Sizes actualTotalSize = ApkBreakdownGenerator.calculateActualTotals(apkPath);
            SizesOuterClass.Sizes zipOverheads = SizeUtils.subtractSizes(actualTotalSize, SizeUtils.sizes(diskSizeByComponent.values().stream().mapToLong(Long::longValue).sum(), downloadSizeByComponent.values().stream().mapToLong(Long::longValue).sum()));
            SizesOuterClass.Breakdown breakdown = SizesOuterClass.Breakdown.newBuilder().setDex(ApkBreakdownGenerator.getSizes(ApkComponent.DEX, diskSizeByComponent, downloadSizeByComponent)).setAssets(ApkBreakdownGenerator.getSizes(ApkComponent.ASSETS, diskSizeByComponent, downloadSizeByComponent)).setNativeLibs(ApkBreakdownGenerator.getSizes(ApkComponent.NATIVE_LIBS, diskSizeByComponent, downloadSizeByComponent)).setResources(ApkBreakdownGenerator.getSizes(ApkComponent.RESOURCES, diskSizeByComponent, downloadSizeByComponent)).setOther(SizeUtils.addSizes(ApkBreakdownGenerator.getSizes(ApkComponent.OTHER, diskSizeByComponent, downloadSizeByComponent), zipOverheads)).setTotal(actualTotalSize).build();
            return breakdown;
        }
    }

    /*
     * Exception decompiling
     */
    private static SizesOuterClass.Sizes calculateActualTotals(Path apkPath) throws IOException {
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

    private static SizesOuterClass.Sizes getSizes(ApkComponent component, Map<ApkComponent, Long> diskSizes, Map<ApkComponent, Long> downloadSizes) {
        return SizeUtils.sizes(diskSizes.getOrDefault((Object)component, 0L), downloadSizes.getOrDefault((Object)component, 0L));
    }

    private static ImmutableMap<String, Long> calculateDownloadSizePerEntry(ZipFile zipFile) throws IOException {
        ImmutableList<InputStreamSupplier> streams = zipFile.stream().map(zipStreamEntry -> () -> zipFile.getInputStream((ZipEntry)zipStreamEntry)).collect(ImmutableList.toImmutableList());
        ImmutableList<Long> downloadSizes = ZipUtils.calculateGZipSizeForEntries(streams);
        return Streams.zip(zipFile.stream(), downloadSizes.stream(), AbstractMap.SimpleEntry::new).collect(ImmutableMap.toImmutableMap(entry -> ((ZipEntry)entry.getKey()).getName(), AbstractMap.SimpleEntry::getValue));
    }
}

