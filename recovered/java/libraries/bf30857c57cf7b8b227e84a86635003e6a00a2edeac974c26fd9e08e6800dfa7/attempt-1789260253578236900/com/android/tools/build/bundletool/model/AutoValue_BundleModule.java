/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.bundle.Config;
import com.android.bundle.Files;
import com.android.tools.build.bundletool.model.$AutoValue_BundleModule;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;

final class AutoValue_BundleModule
extends $AutoValue_BundleModule {
    private volatile AndroidManifest getAndroidManifest;
    private volatile boolean hasRenderscript32Bitcode;
    private volatile boolean hasRenderscript32Bitcode$Memoized;

    AutoValue_BundleModule(BundleModuleName name$, Config.BundleConfig bundleConfig$, Resources.XmlNode androidManifestProto$, Optional<Resources.ResourceTable> resourceTable$, Optional<Files.Assets> assetsConfig$, Optional<Files.NativeLibraries> nativeConfig$, Optional<Files.ApexImages> apexConfig$, ImmutableMap<ZipPath, ModuleEntry> entryMap$) {
        super(name$, bundleConfig$, androidManifestProto$, resourceTable$, assetsConfig$, nativeConfig$, apexConfig$, entryMap$);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public AndroidManifest getAndroidManifest() {
        if (this.getAndroidManifest == null) {
            AutoValue_BundleModule autoValue_BundleModule = this;
            synchronized (autoValue_BundleModule) {
                if (this.getAndroidManifest == null) {
                    this.getAndroidManifest = super.getAndroidManifest();
                    if (this.getAndroidManifest == null) {
                        throw new NullPointerException("getAndroidManifest() cannot return null");
                    }
                }
            }
        }
        return this.getAndroidManifest;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean hasRenderscript32Bitcode() {
        if (!this.hasRenderscript32Bitcode$Memoized) {
            AutoValue_BundleModule autoValue_BundleModule = this;
            synchronized (autoValue_BundleModule) {
                if (!this.hasRenderscript32Bitcode$Memoized) {
                    this.hasRenderscript32Bitcode = super.hasRenderscript32Bitcode();
                    this.hasRenderscript32Bitcode$Memoized = true;
                }
            }
        }
        return this.hasRenderscript32Bitcode;
    }
}

