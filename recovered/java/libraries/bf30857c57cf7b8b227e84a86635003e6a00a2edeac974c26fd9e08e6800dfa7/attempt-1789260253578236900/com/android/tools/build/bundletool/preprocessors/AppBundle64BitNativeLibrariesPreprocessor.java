/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.preprocessors;

import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.preprocessors.AppBundlePreprocessor;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.PrintStream;
import java.util.Optional;

public class AppBundle64BitNativeLibrariesPreprocessor
implements AppBundlePreprocessor {
    private final Optional<PrintStream> logPrintStream;

    public AppBundle64BitNativeLibrariesPreprocessor(Optional<PrintStream> logPrintStream) {
        this.logPrintStream = logPrintStream;
    }

    @Override
    public AppBundle preprocess(AppBundle originalBundle) {
        boolean filter64BitLibraries = originalBundle.has32BitRenderscriptCode();
        if (!filter64BitLibraries) {
            return originalBundle;
        }
        this.printWarning("App Bundle contains 32-bit RenderScript bitcode file (.bc) which disables 64-bit support in Android. 64-bit native libraries won't be included in generated APKs.");
        return originalBundle.toBuilder().setRawModules(AppBundle64BitNativeLibrariesPreprocessor.processModules((ImmutableCollection<BundleModule>)originalBundle.getModules().values())).build();
    }

    public static ImmutableCollection<BundleModule> processModules(ImmutableCollection<BundleModule> modules) {
        return modules.stream().map(AppBundle64BitNativeLibrariesPreprocessor::processModule).collect(ImmutableList.toImmutableList());
    }

    private static BundleModule processModule(BundleModule module) {
        Optional<Files.NativeLibraries> nativeConfig = module.getNativeConfig();
        if (!nativeConfig.isPresent()) {
            return module;
        }
        ImmutableSet<Files.TargetedNativeDirectory> dirsToRemove = AppBundle64BitNativeLibrariesPreprocessor.get64BitTargetedNativeDirectories(nativeConfig.get());
        if (dirsToRemove.isEmpty()) {
            return module;
        }
        if (dirsToRemove.size() == nativeConfig.get().getDirectoryCount()) {
            throw CommandExecutionException.builder().withMessage("Usage of 64-bit native libraries is disabled by the presence of a renderscript file, but App Bundle contains only 64-bit native libraries.").build();
        }
        return module.toBuilder().setRawEntries(AppBundle64BitNativeLibrariesPreprocessor.processEntries(module.getEntries(), dirsToRemove)).setNativeConfig(AppBundle64BitNativeLibrariesPreprocessor.processTargeting(nativeConfig.get(), dirsToRemove)).build();
    }

    private static ImmutableCollection<ModuleEntry> processEntries(ImmutableCollection<ModuleEntry> entries, ImmutableCollection<Files.TargetedNativeDirectory> targeted64BitNativeDirectories) {
        return entries.stream().filter(entry -> AppBundle64BitNativeLibrariesPreprocessor.shouldIncludeEntry(entry, targeted64BitNativeDirectories)).collect(ImmutableList.toImmutableList());
    }

    private static boolean shouldIncludeEntry(ModuleEntry entry, ImmutableCollection<Files.TargetedNativeDirectory> targeted64BitNativeDirectories) {
        return targeted64BitNativeDirectories.stream().noneMatch(targetedNativeDirectory -> entry.getPath().startsWith(targetedNativeDirectory.getPath()));
    }

    private static ImmutableSet<Files.TargetedNativeDirectory> get64BitTargetedNativeDirectories(Files.NativeLibraries nativeLibraries) {
        return nativeLibraries.getDirectoryList().stream().filter(AppBundle64BitNativeLibrariesPreprocessor::targets64BitAbi).collect(ImmutableSet.toImmutableSet());
    }

    private static Files.NativeLibraries processTargeting(Files.NativeLibraries nativeConfig, ImmutableSet<Files.TargetedNativeDirectory> dirsToRemove) {
        return nativeConfig.toBuilder().clearDirectory().addAllDirectory(nativeConfig.getDirectoryList().stream().filter(Predicates.not(dirsToRemove::contains)).collect(ImmutableList.toImmutableList())).build();
    }

    private static boolean targets64BitAbi(Files.TargetedNativeDirectory targetedNativeDirectory) {
        return targetedNativeDirectory.getTargeting().hasAbi() && AppBundle64BitNativeLibrariesPreprocessor.is64Bit(targetedNativeDirectory.getTargeting().getAbi());
    }

    private static boolean is64Bit(Targeting.Abi abi) {
        return AbiName.fromProto(abi.getAlias()).getBitSize() == 64;
    }

    private void printWarning(String message) {
        this.logPrintStream.ifPresent(out -> out.println("WARNING: " + message));
    }
}

