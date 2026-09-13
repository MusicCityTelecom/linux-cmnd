/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.splitters.ModuleSplitSplitter;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import java.util.HashSet;
import java.util.List;

public class SanitizerNativeLibrariesSplitter
implements ModuleSplitSplitter {
    @Override
    public ImmutableCollection<ModuleSplit> split(ModuleSplit moduleSplit) {
        if (!moduleSplit.getNativeConfig().isPresent()) {
            return ImmutableList.of(moduleSplit);
        }
        HashSet<ZipPath> hwasanDirs = new HashSet<ZipPath>();
        for (Files.TargetedNativeDirectory dir : moduleSplit.getNativeConfig().get().getDirectoryList()) {
            if (!dir.getTargeting().hasSanitizer() || !dir.getTargeting().getSanitizer().getAlias().equals(Targeting.Sanitizer.SanitizerAlias.HWADDRESS)) continue;
            hwasanDirs.add(ZipPath.create(dir.getPath()));
        }
        List hwasanEntries = moduleSplit.getEntries().stream().filter(entry -> hwasanDirs.contains(entry.getPath().subpath(0, 2))).collect(ImmutableList.toImmutableList());
        if (hwasanEntries.isEmpty()) {
            return ImmutableList.of(moduleSplit);
        }
        List nonHwasanEntries = moduleSplit.getEntries().stream().filter(entry -> !hwasanDirs.contains(entry.getPath().subpath(0, 2))).collect(ImmutableList.toImmutableList());
        ModuleSplit hwasanSplit = moduleSplit.toBuilder().setApkTargeting(moduleSplit.getApkTargeting().toBuilder().setSanitizerTargeting(Targeting.SanitizerTargeting.newBuilder().addValue(Targeting.Sanitizer.newBuilder().setAlias(Targeting.Sanitizer.SanitizerAlias.HWADDRESS))).build()).setMasterSplit(false).setEntries(hwasanEntries).build();
        ModuleSplit nonHwasanSplit = moduleSplit.toBuilder().setEntries(nonHwasanEntries).build();
        return ImmutableList.of(hwasanSplit, nonHwasanSplit);
    }
}

