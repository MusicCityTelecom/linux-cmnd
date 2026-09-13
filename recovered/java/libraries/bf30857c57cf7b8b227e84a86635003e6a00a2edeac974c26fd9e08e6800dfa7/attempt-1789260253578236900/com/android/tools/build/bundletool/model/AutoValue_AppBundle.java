/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Config;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleMetadata;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ResourceId;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

final class AutoValue_AppBundle
extends AppBundle {
    private final ImmutableMap<BundleModuleName, BundleModule> modules;
    private final ImmutableSet<ResourceId> masterPinnedResourceIds;
    private final ImmutableSet<String> masterPinnedResourceNames;
    private final Config.BundleConfig bundleConfig;
    private final BundleMetadata bundleMetadata;

    private AutoValue_AppBundle(ImmutableMap<BundleModuleName, BundleModule> modules, ImmutableSet<ResourceId> masterPinnedResourceIds, ImmutableSet<String> masterPinnedResourceNames, Config.BundleConfig bundleConfig, BundleMetadata bundleMetadata) {
        this.modules = modules;
        this.masterPinnedResourceIds = masterPinnedResourceIds;
        this.masterPinnedResourceNames = masterPinnedResourceNames;
        this.bundleConfig = bundleConfig;
        this.bundleMetadata = bundleMetadata;
    }

    @Override
    public ImmutableMap<BundleModuleName, BundleModule> getModules() {
        return this.modules;
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
    public Config.BundleConfig getBundleConfig() {
        return this.bundleConfig;
    }

    @Override
    public BundleMetadata getBundleMetadata() {
        return this.bundleMetadata;
    }

    public String toString() {
        return "AppBundle{modules=" + this.modules + ", masterPinnedResourceIds=" + this.masterPinnedResourceIds + ", masterPinnedResourceNames=" + this.masterPinnedResourceNames + ", bundleConfig=" + this.bundleConfig + ", bundleMetadata=" + this.bundleMetadata + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof AppBundle) {
            AppBundle that = (AppBundle)o3;
            return this.modules.equals(that.getModules()) && this.masterPinnedResourceIds.equals(that.getMasterPinnedResourceIds()) && this.masterPinnedResourceNames.equals(that.getMasterPinnedResourceNames()) && this.bundleConfig.equals(that.getBundleConfig()) && this.bundleMetadata.equals(that.getBundleMetadata());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.modules.hashCode();
        h$ *= 1000003;
        h$ ^= this.masterPinnedResourceIds.hashCode();
        h$ *= 1000003;
        h$ ^= this.masterPinnedResourceNames.hashCode();
        h$ *= 1000003;
        h$ ^= this.bundleConfig.hashCode();
        h$ *= 1000003;
        return h$ ^= this.bundleMetadata.hashCode();
    }

    @Override
    public AppBundle.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder
    extends AppBundle.Builder {
        private ImmutableMap<BundleModuleName, BundleModule> modules;
        private ImmutableSet<ResourceId> masterPinnedResourceIds;
        private ImmutableSet<String> masterPinnedResourceNames;
        private Config.BundleConfig bundleConfig;
        private BundleMetadata bundleMetadata;

        Builder() {
        }

        private Builder(AppBundle source) {
            this.modules = source.getModules();
            this.masterPinnedResourceIds = source.getMasterPinnedResourceIds();
            this.masterPinnedResourceNames = source.getMasterPinnedResourceNames();
            this.bundleConfig = source.getBundleConfig();
            this.bundleMetadata = source.getBundleMetadata();
        }

        @Override
        public AppBundle.Builder setModules(ImmutableMap<BundleModuleName, BundleModule> modules) {
            if (modules == null) {
                throw new NullPointerException("Null modules");
            }
            this.modules = modules;
            return this;
        }

        @Override
        public AppBundle.Builder setMasterPinnedResourceIds(ImmutableSet<ResourceId> masterPinnedResourceIds) {
            if (masterPinnedResourceIds == null) {
                throw new NullPointerException("Null masterPinnedResourceIds");
            }
            this.masterPinnedResourceIds = masterPinnedResourceIds;
            return this;
        }

        @Override
        public AppBundle.Builder setMasterPinnedResourceNames(ImmutableSet<String> masterPinnedResourceNames) {
            if (masterPinnedResourceNames == null) {
                throw new NullPointerException("Null masterPinnedResourceNames");
            }
            this.masterPinnedResourceNames = masterPinnedResourceNames;
            return this;
        }

        @Override
        public AppBundle.Builder setBundleConfig(Config.BundleConfig bundleConfig) {
            if (bundleConfig == null) {
                throw new NullPointerException("Null bundleConfig");
            }
            this.bundleConfig = bundleConfig;
            return this;
        }

        @Override
        public AppBundle.Builder setBundleMetadata(BundleMetadata bundleMetadata) {
            if (bundleMetadata == null) {
                throw new NullPointerException("Null bundleMetadata");
            }
            this.bundleMetadata = bundleMetadata;
            return this;
        }

        @Override
        public AppBundle build() {
            String missing = "";
            if (this.modules == null) {
                missing = missing + " modules";
            }
            if (this.masterPinnedResourceIds == null) {
                missing = missing + " masterPinnedResourceIds";
            }
            if (this.masterPinnedResourceNames == null) {
                missing = missing + " masterPinnedResourceNames";
            }
            if (this.bundleConfig == null) {
                missing = missing + " bundleConfig";
            }
            if (this.bundleMetadata == null) {
                missing = missing + " bundleMetadata";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_AppBundle(this.modules, this.masterPinnedResourceIds, this.masterPinnedResourceNames, this.bundleConfig, this.bundleMetadata);
        }
    }
}

