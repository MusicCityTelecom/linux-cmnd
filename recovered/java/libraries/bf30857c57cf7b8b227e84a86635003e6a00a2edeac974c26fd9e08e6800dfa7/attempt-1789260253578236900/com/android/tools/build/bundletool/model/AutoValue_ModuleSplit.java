/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.$AutoValue_ModuleSplit;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import java.util.Optional;

final class AutoValue_ModuleSplit
extends $AutoValue_ModuleSplit {
    private volatile Multimap<ZipPath, ModuleEntry> getEntriesByDirectory;

    AutoValue_ModuleSplit(Targeting.ApkTargeting apkTargeting$, Targeting.VariantTargeting variantTargeting$, ModuleSplit.SplitType splitType$, ImmutableList<ModuleEntry> entries$, Optional<Resources.ResourceTable> resourceTable$, AndroidManifest androidManifest$, ImmutableList<ManifestMutator> masterManifestMutators$, BundleModuleName moduleName$, boolean masterSplit$, Optional<Files.NativeLibraries> nativeConfig$, Optional<Files.Assets> assetsConfig$, Optional<Files.ApexImages> apexConfig$) {
        super(apkTargeting$, variantTargeting$, splitType$, entries$, resourceTable$, androidManifest$, masterManifestMutators$, moduleName$, masterSplit$, nativeConfig$, assetsConfig$, apexConfig$);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    Multimap<ZipPath, ModuleEntry> getEntriesByDirectory() {
        if (this.getEntriesByDirectory == null) {
            AutoValue_ModuleSplit autoValue_ModuleSplit = this;
            synchronized (autoValue_ModuleSplit) {
                if (this.getEntriesByDirectory == null) {
                    this.getEntriesByDirectory = super.getEntriesByDirectory();
                    if (this.getEntriesByDirectory == null) {
                        throw new NullPointerException("getEntriesByDirectory() cannot return null");
                    }
                }
            }
        }
        return this.getEntriesByDirectory;
    }
}

