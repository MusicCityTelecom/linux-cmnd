/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.AutoValue_ModuleSplit;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;

abstract class $AutoValue_ModuleSplit
extends ModuleSplit {
    private final Targeting.ApkTargeting apkTargeting;
    private final Targeting.VariantTargeting variantTargeting;
    private final ModuleSplit.SplitType splitType;
    private final ImmutableList<ModuleEntry> entries;
    private final Optional<Resources.ResourceTable> resourceTable;
    private final AndroidManifest androidManifest;
    private final ImmutableList<ManifestMutator> masterManifestMutators;
    private final BundleModuleName moduleName;
    private final boolean masterSplit;
    private final Optional<Files.NativeLibraries> nativeConfig;
    private final Optional<Files.Assets> assetsConfig;
    private final Optional<Files.ApexImages> apexConfig;

    $AutoValue_ModuleSplit(Targeting.ApkTargeting apkTargeting, Targeting.VariantTargeting variantTargeting, ModuleSplit.SplitType splitType, ImmutableList<ModuleEntry> entries, Optional<Resources.ResourceTable> resourceTable, AndroidManifest androidManifest, ImmutableList<ManifestMutator> masterManifestMutators, BundleModuleName moduleName, boolean masterSplit, Optional<Files.NativeLibraries> nativeConfig, Optional<Files.Assets> assetsConfig, Optional<Files.ApexImages> apexConfig) {
        if (apkTargeting == null) {
            throw new NullPointerException("Null apkTargeting");
        }
        this.apkTargeting = apkTargeting;
        if (variantTargeting == null) {
            throw new NullPointerException("Null variantTargeting");
        }
        this.variantTargeting = variantTargeting;
        if (splitType == null) {
            throw new NullPointerException("Null splitType");
        }
        this.splitType = splitType;
        if (entries == null) {
            throw new NullPointerException("Null entries");
        }
        this.entries = entries;
        if (resourceTable == null) {
            throw new NullPointerException("Null resourceTable");
        }
        this.resourceTable = resourceTable;
        if (androidManifest == null) {
            throw new NullPointerException("Null androidManifest");
        }
        this.androidManifest = androidManifest;
        if (masterManifestMutators == null) {
            throw new NullPointerException("Null masterManifestMutators");
        }
        this.masterManifestMutators = masterManifestMutators;
        if (moduleName == null) {
            throw new NullPointerException("Null moduleName");
        }
        this.moduleName = moduleName;
        this.masterSplit = masterSplit;
        if (nativeConfig == null) {
            throw new NullPointerException("Null nativeConfig");
        }
        this.nativeConfig = nativeConfig;
        if (assetsConfig == null) {
            throw new NullPointerException("Null assetsConfig");
        }
        this.assetsConfig = assetsConfig;
        if (apexConfig == null) {
            throw new NullPointerException("Null apexConfig");
        }
        this.apexConfig = apexConfig;
    }

    @Override
    public Targeting.ApkTargeting getApkTargeting() {
        return this.apkTargeting;
    }

    @Override
    public Targeting.VariantTargeting getVariantTargeting() {
        return this.variantTargeting;
    }

    @Override
    public ModuleSplit.SplitType getSplitType() {
        return this.splitType;
    }

    @Override
    public ImmutableList<ModuleEntry> getEntries() {
        return this.entries;
    }

    @Override
    public Optional<Resources.ResourceTable> getResourceTable() {
        return this.resourceTable;
    }

    @Override
    public AndroidManifest getAndroidManifest() {
        return this.androidManifest;
    }

    @Override
    public ImmutableList<ManifestMutator> getMasterManifestMutators() {
        return this.masterManifestMutators;
    }

    @Override
    public BundleModuleName getModuleName() {
        return this.moduleName;
    }

    @Override
    public boolean isMasterSplit() {
        return this.masterSplit;
    }

    @Override
    public Optional<Files.NativeLibraries> getNativeConfig() {
        return this.nativeConfig;
    }

    @Override
    public Optional<Files.Assets> getAssetsConfig() {
        return this.assetsConfig;
    }

    @Override
    public Optional<Files.ApexImages> getApexConfig() {
        return this.apexConfig;
    }

    public String toString() {
        return "ModuleSplit{apkTargeting=" + this.apkTargeting + ", variantTargeting=" + this.variantTargeting + ", splitType=" + (Object)((Object)this.splitType) + ", entries=" + this.entries + ", resourceTable=" + this.resourceTable + ", androidManifest=" + this.androidManifest + ", masterManifestMutators=" + this.masterManifestMutators + ", moduleName=" + this.moduleName + ", masterSplit=" + this.masterSplit + ", nativeConfig=" + this.nativeConfig + ", assetsConfig=" + this.assetsConfig + ", apexConfig=" + this.apexConfig + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ModuleSplit) {
            ModuleSplit that = (ModuleSplit)o3;
            return this.apkTargeting.equals(that.getApkTargeting()) && this.variantTargeting.equals(that.getVariantTargeting()) && this.splitType.equals((Object)that.getSplitType()) && this.entries.equals(that.getEntries()) && this.resourceTable.equals(that.getResourceTable()) && this.androidManifest.equals(that.getAndroidManifest()) && this.masterManifestMutators.equals(that.getMasterManifestMutators()) && this.moduleName.equals(that.getModuleName()) && this.masterSplit == that.isMasterSplit() && this.nativeConfig.equals(that.getNativeConfig()) && this.assetsConfig.equals(that.getAssetsConfig()) && this.apexConfig.equals(that.getApexConfig());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.apkTargeting.hashCode();
        h$ *= 1000003;
        h$ ^= this.variantTargeting.hashCode();
        h$ *= 1000003;
        h$ ^= this.splitType.hashCode();
        h$ *= 1000003;
        h$ ^= this.entries.hashCode();
        h$ *= 1000003;
        h$ ^= this.resourceTable.hashCode();
        h$ *= 1000003;
        h$ ^= this.androidManifest.hashCode();
        h$ *= 1000003;
        h$ ^= this.masterManifestMutators.hashCode();
        h$ *= 1000003;
        h$ ^= this.moduleName.hashCode();
        h$ *= 1000003;
        h$ ^= this.masterSplit ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.nativeConfig.hashCode();
        h$ *= 1000003;
        h$ ^= this.assetsConfig.hashCode();
        h$ *= 1000003;
        return h$ ^= this.apexConfig.hashCode();
    }

    @Override
    public ModuleSplit.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder
    extends ModuleSplit.Builder {
        private Targeting.ApkTargeting apkTargeting;
        private Targeting.VariantTargeting variantTargeting;
        private ModuleSplit.SplitType splitType;
        private ImmutableList<ModuleEntry> entries;
        private Optional<Resources.ResourceTable> resourceTable = Optional.empty();
        private AndroidManifest androidManifest;
        private ImmutableList.Builder<ManifestMutator> masterManifestMutatorsBuilder$;
        private ImmutableList<ManifestMutator> masterManifestMutators;
        private BundleModuleName moduleName;
        private Boolean masterSplit;
        private Optional<Files.NativeLibraries> nativeConfig = Optional.empty();
        private Optional<Files.Assets> assetsConfig = Optional.empty();
        private Optional<Files.ApexImages> apexConfig = Optional.empty();

        Builder() {
        }

        private Builder(ModuleSplit source) {
            this.apkTargeting = source.getApkTargeting();
            this.variantTargeting = source.getVariantTargeting();
            this.splitType = source.getSplitType();
            this.entries = source.getEntries();
            this.resourceTable = source.getResourceTable();
            this.androidManifest = source.getAndroidManifest();
            this.masterManifestMutators = source.getMasterManifestMutators();
            this.moduleName = source.getModuleName();
            this.masterSplit = source.isMasterSplit();
            this.nativeConfig = source.getNativeConfig();
            this.assetsConfig = source.getAssetsConfig();
            this.apexConfig = source.getApexConfig();
        }

        @Override
        public ModuleSplit.Builder setApkTargeting(Targeting.ApkTargeting apkTargeting) {
            if (apkTargeting == null) {
                throw new NullPointerException("Null apkTargeting");
            }
            this.apkTargeting = apkTargeting;
            return this;
        }

        @Override
        protected Targeting.ApkTargeting getApkTargeting() {
            if (this.apkTargeting == null) {
                throw new IllegalStateException("Property \"apkTargeting\" has not been set");
            }
            return this.apkTargeting;
        }

        @Override
        public ModuleSplit.Builder setVariantTargeting(Targeting.VariantTargeting variantTargeting) {
            if (variantTargeting == null) {
                throw new NullPointerException("Null variantTargeting");
            }
            this.variantTargeting = variantTargeting;
            return this;
        }

        @Override
        protected Targeting.VariantTargeting getVariantTargeting() {
            if (this.variantTargeting == null) {
                throw new IllegalStateException("Property \"variantTargeting\" has not been set");
            }
            return this.variantTargeting;
        }

        @Override
        public ModuleSplit.Builder setSplitType(ModuleSplit.SplitType splitType) {
            if (splitType == null) {
                throw new NullPointerException("Null splitType");
            }
            this.splitType = splitType;
            return this;
        }

        @Override
        public ModuleSplit.Builder setEntries(List<ModuleEntry> entries) {
            this.entries = ImmutableList.copyOf(entries);
            return this;
        }

        @Override
        public ModuleSplit.Builder setResourceTable(Resources.ResourceTable resourceTable) {
            this.resourceTable = Optional.of(resourceTable);
            return this;
        }

        @Override
        public ModuleSplit.Builder setAndroidManifest(AndroidManifest androidManifest) {
            if (androidManifest == null) {
                throw new NullPointerException("Null androidManifest");
            }
            this.androidManifest = androidManifest;
            return this;
        }

        @Override
        ImmutableList.Builder<ManifestMutator> masterManifestMutatorsBuilder() {
            if (this.masterManifestMutatorsBuilder$ == null) {
                if (this.masterManifestMutators == null) {
                    this.masterManifestMutatorsBuilder$ = ImmutableList.builder();
                } else {
                    this.masterManifestMutatorsBuilder$ = ImmutableList.builder();
                    this.masterManifestMutatorsBuilder$.addAll(this.masterManifestMutators);
                    this.masterManifestMutators = null;
                }
            }
            return this.masterManifestMutatorsBuilder$;
        }

        @Override
        public ModuleSplit.Builder setModuleName(BundleModuleName moduleName) {
            if (moduleName == null) {
                throw new NullPointerException("Null moduleName");
            }
            this.moduleName = moduleName;
            return this;
        }

        @Override
        public ModuleSplit.Builder setMasterSplit(boolean masterSplit) {
            this.masterSplit = masterSplit;
            return this;
        }

        @Override
        public ModuleSplit.Builder setNativeConfig(Files.NativeLibraries nativeConfig) {
            this.nativeConfig = Optional.of(nativeConfig);
            return this;
        }

        @Override
        public ModuleSplit.Builder setAssetsConfig(Files.Assets assetsConfig) {
            this.assetsConfig = Optional.of(assetsConfig);
            return this;
        }

        @Override
        public ModuleSplit.Builder setApexConfig(Files.ApexImages apexConfig) {
            this.apexConfig = Optional.of(apexConfig);
            return this;
        }

        @Override
        protected ModuleSplit autoBuild() {
            if (this.masterManifestMutatorsBuilder$ != null) {
                this.masterManifestMutators = this.masterManifestMutatorsBuilder$.build();
            } else if (this.masterManifestMutators == null) {
                this.masterManifestMutators = ImmutableList.of();
            }
            String missing = "";
            if (this.apkTargeting == null) {
                missing = missing + " apkTargeting";
            }
            if (this.variantTargeting == null) {
                missing = missing + " variantTargeting";
            }
            if (this.splitType == null) {
                missing = missing + " splitType";
            }
            if (this.entries == null) {
                missing = missing + " entries";
            }
            if (this.androidManifest == null) {
                missing = missing + " androidManifest";
            }
            if (this.moduleName == null) {
                missing = missing + " moduleName";
            }
            if (this.masterSplit == null) {
                missing = missing + " masterSplit";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ModuleSplit(this.apkTargeting, this.variantTargeting, this.splitType, this.entries, this.resourceTable, this.androidManifest, this.masterManifestMutators, this.moduleName, this.masterSplit, this.nativeConfig, this.assetsConfig, this.apexConfig);
        }
    }
}

