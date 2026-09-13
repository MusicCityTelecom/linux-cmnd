/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Files;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;

class ModuleAbiSanitizer {
    private static final Logger logger = Logger.getLogger(ModuleAbiSanitizer.class.getName());

    ModuleAbiSanitizer() {
    }

    @CheckReturnValue
    public BundleModule sanitize(BundleModule module) {
        ImmutableMultimap<ZipPath, ModuleEntry> libFilesByAbiDir = ModuleAbiSanitizer.indexLibFilesByAbiDir(module);
        ImmutableMultimap<ZipPath, ModuleEntry> libFilesByAbiDirToKeep = ModuleAbiSanitizer.discardAbiDirsWithTooFewFiles(libFilesByAbiDir);
        if (libFilesByAbiDirToKeep.size() == libFilesByAbiDir.size()) {
            return module;
        }
        logger.warning(String.format("Native directories of module '%s' don't contain the same number of files. The following files were therefore discarded: %s", module.getName(), Sets.difference(ImmutableSet.copyOf(libFilesByAbiDir.values()), ImmutableSet.copyOf(libFilesByAbiDirToKeep.values())).stream().map(ModuleEntry::getPath).map(ZipPath::toString).sorted().collect(ImmutableList.toImmutableList())));
        return ModuleAbiSanitizer.sanitizedModule(module, libFilesByAbiDirToKeep);
    }

    private static ImmutableMultimap<ZipPath, ModuleEntry> indexLibFilesByAbiDir(BundleModule module) {
        return module.getEntries().stream().filter(entry -> entry.getPath().startsWith(BundleModule.LIB_DIRECTORY)).collect(ImmutableSetMultimap.toImmutableSetMultimap(entry -> entry.getPath().subpath(0, 2), Function.identity()));
    }

    private static ImmutableMultimap<ZipPath, ModuleEntry> discardAbiDirsWithTooFewFiles(ImmutableMultimap<ZipPath, ModuleEntry> libFilesByAbiDir) {
        int maxAbiFiles = ((ImmutableMap)libFilesByAbiDir.asMap()).values().stream().mapToInt(Collection::size).max().orElse(0);
        return ImmutableSetMultimap.copyOf(Multimaps.filterKeys(libFilesByAbiDir, abiDir -> ((AbstractCollection)libFilesByAbiDir.get(abiDir)).size() == maxAbiFiles));
    }

    private static BundleModule sanitizedModule(BundleModule module, ImmutableMultimap<ZipPath, ModuleEntry> libFilesByAbiDirToKeep) {
        BundleModule.Builder newModule = module.toBuilder();
        if (module.getNativeConfig().isPresent()) {
            Files.NativeLibraries newNativeConfig = ModuleAbiSanitizer.filterNativeTargeting(module.getNativeConfig().get(), libFilesByAbiDirToKeep);
            newModule.setNativeConfig(newNativeConfig);
        }
        newModule.setEntryMap(module.getEntries().stream().filter(entry -> !entry.getPath().startsWith(BundleModule.LIB_DIRECTORY) || libFilesByAbiDirToKeep.containsValue(entry)).collect(ImmutableMap.toImmutableMap(ModuleEntry::getPath, Function.identity())));
        return newModule.build();
    }

    private static Files.NativeLibraries filterNativeTargeting(Files.NativeLibraries nativeLibraries, ImmutableMultimap<ZipPath, ModuleEntry> preservedEntriesByAbiDir) {
        ImmutableSet preservedAbiDirs = preservedEntriesByAbiDir.keySet().stream().map(ZipPath::toString).collect(ImmutableSet.toImmutableSet());
        return nativeLibraries.toBuilder().clearDirectory().addAllDirectory(nativeLibraries.getDirectoryList().stream().filter(targetedDirectory -> preservedAbiDirs.contains(targetedDirectory.getPath())).collect(ImmutableList.toImmutableList())).build();
    }
}

