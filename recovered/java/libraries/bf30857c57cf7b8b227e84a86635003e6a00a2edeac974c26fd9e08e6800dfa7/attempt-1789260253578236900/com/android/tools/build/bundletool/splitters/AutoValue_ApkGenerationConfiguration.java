/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Config;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.splitters.ApkGenerationConfiguration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

final class AutoValue_ApkGenerationConfiguration
extends ApkGenerationConfiguration {
    private final ImmutableSet<OptimizationDimension> optimizationDimensions;
    private final boolean forInstantAppVariants;
    private final boolean enableNativeLibraryCompressionSplitter;
    private final boolean enableDexCompressionSplitter;
    private final boolean installableOnExternalStorage;
    private final ImmutableSet<Targeting.Abi> abisForPlaceholderLibs;
    private final ImmutableSet<ResourceId> masterPinnedResourceIds;
    private final ImmutableSet<String> masterPinnedResourceNames;
    private final ImmutableSet<ResourceId> baseManifestReachableResources;
    private final ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings;

    private AutoValue_ApkGenerationConfiguration(ImmutableSet<OptimizationDimension> optimizationDimensions, boolean forInstantAppVariants, boolean enableNativeLibraryCompressionSplitter, boolean enableDexCompressionSplitter, boolean installableOnExternalStorage, ImmutableSet<Targeting.Abi> abisForPlaceholderLibs, ImmutableSet<ResourceId> masterPinnedResourceIds, ImmutableSet<String> masterPinnedResourceNames, ImmutableSet<ResourceId> baseManifestReachableResources, ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings) {
        this.optimizationDimensions = optimizationDimensions;
        this.forInstantAppVariants = forInstantAppVariants;
        this.enableNativeLibraryCompressionSplitter = enableNativeLibraryCompressionSplitter;
        this.enableDexCompressionSplitter = enableDexCompressionSplitter;
        this.installableOnExternalStorage = installableOnExternalStorage;
        this.abisForPlaceholderLibs = abisForPlaceholderLibs;
        this.masterPinnedResourceIds = masterPinnedResourceIds;
        this.masterPinnedResourceNames = masterPinnedResourceNames;
        this.baseManifestReachableResources = baseManifestReachableResources;
        this.suffixStrippings = suffixStrippings;
    }

    @Override
    public ImmutableSet<OptimizationDimension> getOptimizationDimensions() {
        return this.optimizationDimensions;
    }

    @Override
    public boolean isForInstantAppVariants() {
        return this.forInstantAppVariants;
    }

    @Override
    public boolean getEnableNativeLibraryCompressionSplitter() {
        return this.enableNativeLibraryCompressionSplitter;
    }

    @Override
    public boolean getEnableDexCompressionSplitter() {
        return this.enableDexCompressionSplitter;
    }

    @Override
    public boolean isInstallableOnExternalStorage() {
        return this.installableOnExternalStorage;
    }

    @Override
    public ImmutableSet<Targeting.Abi> getAbisForPlaceholderLibs() {
        return this.abisForPlaceholderLibs;
    }

    @Override
    public ImmutableSet<ResourceId> getMasterPinnedResourceIds() {
        return this.masterPinnedResourceIds;
    }

    @Override
    public ImmutableSet<String> getMasterPinnedResourceNames() {
        return this.masterPinnedResourceNames;
    }

    @Override
    public ImmutableSet<ResourceId> getBaseManifestReachableResources() {
        return this.baseManifestReachableResources;
    }

    @Override
    public ImmutableMap<OptimizationDimension, Config.SuffixStripping> getSuffixStrippings() {
        return this.suffixStrippings;
    }

    public String toString() {
        return "ApkGenerationConfiguration{optimizationDimensions=" + this.optimizationDimensions + ", forInstantAppVariants=" + this.forInstantAppVariants + ", enableNativeLibraryCompressionSplitter=" + this.enableNativeLibraryCompressionSplitter + ", enableDexCompressionSplitter=" + this.enableDexCompressionSplitter + ", installableOnExternalStorage=" + this.installableOnExternalStorage + ", abisForPlaceholderLibs=" + this.abisForPlaceholderLibs + ", masterPinnedResourceIds=" + this.masterPinnedResourceIds + ", masterPinnedResourceNames=" + this.masterPinnedResourceNames + ", baseManifestReachableResources=" + this.baseManifestReachableResources + ", suffixStrippings=" + this.suffixStrippings + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ApkGenerationConfiguration) {
            ApkGenerationConfiguration that = (ApkGenerationConfiguration)o3;
            return this.optimizationDimensions.equals(that.getOptimizationDimensions()) && this.forInstantAppVariants == that.isForInstantAppVariants() && this.enableNativeLibraryCompressionSplitter == that.getEnableNativeLibraryCompressionSplitter() && this.enableDexCompressionSplitter == that.getEnableDexCompressionSplitter() && this.installableOnExternalStorage == that.isInstallableOnExternalStorage() && this.abisForPlaceholderLibs.equals(that.getAbisForPlaceholderLibs()) && this.masterPinnedResourceIds.equals(that.getMasterPinnedResourceIds()) && this.masterPinnedResourceNames.equals(that.getMasterPinnedResourceNames()) && this.baseManifestReachableResources.equals(that.getBaseManifestReachableResources()) && this.suffixStrippings.equals(that.getSuffixStrippings());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.optimizationDimensions.hashCode();
        h$ *= 1000003;
        h$ ^= this.forInstantAppVariants ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.enableNativeLibraryCompressionSplitter ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.enableDexCompressionSplitter ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.installableOnExternalStorage ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.abisForPlaceholderLibs.hashCode();
        h$ *= 1000003;
        h$ ^= this.masterPinnedResourceIds.hashCode();
        h$ *= 1000003;
        h$ ^= this.masterPinnedResourceNames.hashCode();
        h$ *= 1000003;
        h$ ^= this.baseManifestReachableResources.hashCode();
        h$ *= 1000003;
        return h$ ^= this.suffixStrippings.hashCode();
    }

    @Override
    public ApkGenerationConfiguration.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder
    extends ApkGenerationConfiguration.Builder {
        private ImmutableSet<OptimizationDimension> optimizationDimensions;
        private Boolean forInstantAppVariants;
        private Boolean enableNativeLibraryCompressionSplitter;
        private Boolean enableDexCompressionSplitter;
        private Boolean installableOnExternalStorage;
        private ImmutableSet<Targeting.Abi> abisForPlaceholderLibs;
        private ImmutableSet<ResourceId> masterPinnedResourceIds;
        private ImmutableSet<String> masterPinnedResourceNames;
        private ImmutableSet<ResourceId> baseManifestReachableResources;
        private ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings;

        Builder() {
        }

        private Builder(ApkGenerationConfiguration source) {
            this.optimizationDimensions = source.getOptimizationDimensions();
            this.forInstantAppVariants = source.isForInstantAppVariants();
            this.enableNativeLibraryCompressionSplitter = source.getEnableNativeLibraryCompressionSplitter();
            this.enableDexCompressionSplitter = source.getEnableDexCompressionSplitter();
            this.installableOnExternalStorage = source.isInstallableOnExternalStorage();
            this.abisForPlaceholderLibs = source.getAbisForPlaceholderLibs();
            this.masterPinnedResourceIds = source.getMasterPinnedResourceIds();
            this.masterPinnedResourceNames = source.getMasterPinnedResourceNames();
            this.baseManifestReachableResources = source.getBaseManifestReachableResources();
            this.suffixStrippings = source.getSuffixStrippings();
        }

        @Override
        public ApkGenerationConfiguration.Builder setOptimizationDimensions(ImmutableSet<OptimizationDimension> optimizationDimensions) {
            if (optimizationDimensions == null) {
                throw new NullPointerException("Null optimizationDimensions");
            }
            this.optimizationDimensions = optimizationDimensions;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setForInstantAppVariants(boolean forInstantAppVariants) {
            this.forInstantAppVariants = forInstantAppVariants;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setEnableNativeLibraryCompressionSplitter(boolean enableNativeLibraryCompressionSplitter) {
            this.enableNativeLibraryCompressionSplitter = enableNativeLibraryCompressionSplitter;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setEnableDexCompressionSplitter(boolean enableDexCompressionSplitter) {
            this.enableDexCompressionSplitter = enableDexCompressionSplitter;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setInstallableOnExternalStorage(boolean installableOnExternalStorage) {
            this.installableOnExternalStorage = installableOnExternalStorage;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setAbisForPlaceholderLibs(ImmutableSet<Targeting.Abi> abisForPlaceholderLibs) {
            if (abisForPlaceholderLibs == null) {
                throw new NullPointerException("Null abisForPlaceholderLibs");
            }
            this.abisForPlaceholderLibs = abisForPlaceholderLibs;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setMasterPinnedResourceIds(ImmutableSet<ResourceId> masterPinnedResourceIds) {
            if (masterPinnedResourceIds == null) {
                throw new NullPointerException("Null masterPinnedResourceIds");
            }
            this.masterPinnedResourceIds = masterPinnedResourceIds;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setMasterPinnedResourceNames(ImmutableSet<String> masterPinnedResourceNames) {
            if (masterPinnedResourceNames == null) {
                throw new NullPointerException("Null masterPinnedResourceNames");
            }
            this.masterPinnedResourceNames = masterPinnedResourceNames;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setBaseManifestReachableResources(ImmutableSet<ResourceId> baseManifestReachableResources) {
            if (baseManifestReachableResources == null) {
                throw new NullPointerException("Null baseManifestReachableResources");
            }
            this.baseManifestReachableResources = baseManifestReachableResources;
            return this;
        }

        @Override
        public ApkGenerationConfiguration.Builder setSuffixStrippings(ImmutableMap<OptimizationDimension, Config.SuffixStripping> suffixStrippings) {
            if (suffixStrippings == null) {
                throw new NullPointerException("Null suffixStrippings");
            }
            this.suffixStrippings = suffixStrippings;
            return this;
        }

        @Override
        public ApkGenerationConfiguration build() {
            String missing = "";
            if (this.optimizationDimensions == null) {
                missing = missing + " optimizationDimensions";
            }
            if (this.forInstantAppVariants == null) {
                missing = missing + " forInstantAppVariants";
            }
            if (this.enableNativeLibraryCompressionSplitter == null) {
                missing = missing + " enableNativeLibraryCompressionSplitter";
            }
            if (this.enableDexCompressionSplitter == null) {
                missing = missing + " enableDexCompressionSplitter";
            }
            if (this.installableOnExternalStorage == null) {
                missing = missing + " installableOnExternalStorage";
            }
            if (this.abisForPlaceholderLibs == null) {
                missing = missing + " abisForPlaceholderLibs";
            }
            if (this.masterPinnedResourceIds == null) {
                missing = missing + " masterPinnedResourceIds";
            }
            if (this.masterPinnedResourceNames == null) {
                missing = missing + " masterPinnedResourceNames";
            }
            if (this.baseManifestReachableResources == null) {
                missing = missing + " baseManifestReachableResources";
            }
            if (this.suffixStrippings == null) {
                missing = missing + " suffixStrippings";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ApkGenerationConfiguration(this.optimizationDimensions, this.forInstantAppVariants, this.enableNativeLibraryCompressionSplitter, this.enableDexCompressionSplitter, this.installableOnExternalStorage, this.abisForPlaceholderLibs, this.masterPinnedResourceIds, this.masterPinnedResourceNames, this.baseManifestReachableResources, this.suffixStrippings);
        }
    }
}

