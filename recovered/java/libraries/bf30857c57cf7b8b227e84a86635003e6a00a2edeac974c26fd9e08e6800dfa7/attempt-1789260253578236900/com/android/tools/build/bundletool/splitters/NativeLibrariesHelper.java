/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Files;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.Optional;

public class NativeLibrariesHelper {
    private static final String MAIN_NATIVE_LIBRARY = "libmain.so";

    public static boolean mayHaveNativeActivities(ModuleSplit moduleSplit) {
        return NativeLibrariesHelper.mayHaveNativeActivities(moduleSplit.getAndroidManifest(), moduleSplit.getNativeConfig(), moduleSplit.getEntries());
    }

    public static boolean mayHaveNativeActivities(BundleModule module) {
        return NativeLibrariesHelper.mayHaveNativeActivities(module.getAndroidManifest(), module.getNativeConfig(), module.getEntries());
    }

    private static boolean mayHaveNativeActivities(AndroidManifest manifest, Optional<Files.NativeLibraries> nativeConfig, ImmutableCollection<ModuleEntry> entries) {
        return manifest.hasExplicitlyDefinedNativeActivities() || NativeLibrariesHelper.hasMainLibrary(nativeConfig, entries);
    }

    private static boolean hasMainLibrary(Optional<Files.NativeLibraries> nativeConfig, ImmutableCollection<ModuleEntry> entries) {
        List nativeLibPaths = Streams.stream(nativeConfig).flatMap(config -> config.getDirectoryList().stream()).map(directory -> ZipPath.create(directory.getPath())).collect(ImmutableList.toImmutableList());
        return entries.stream().anyMatch(entry -> {
            if (!entry.getPath().endsWith(MAIN_NATIVE_LIBRARY)) return false;
            if (!nativeLibPaths.stream().anyMatch(entry.getPath()::startsWith)) return false;
            return true;
        });
    }

    private NativeLibrariesHelper() {
    }
}

