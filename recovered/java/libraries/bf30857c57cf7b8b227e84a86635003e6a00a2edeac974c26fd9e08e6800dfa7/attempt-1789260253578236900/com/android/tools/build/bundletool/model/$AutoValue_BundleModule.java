/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.bundle.Config;
import com.android.bundle.Files;
import com.android.tools.build.bundletool.model.AutoValue_BundleModule;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;

abstract class $AutoValue_BundleModule
extends BundleModule {
    private final BundleModuleName name;
    private final Config.BundleConfig bundleConfig;
    private final Resources.XmlNode androidManifestProto;
    private final Optional<Resources.ResourceTable> resourceTable;
    private final Optional<Files.Assets> assetsConfig;
    private final Optional<Files.NativeLibraries> nativeConfig;
    private final Optional<Files.ApexImages> apexConfig;
    private final ImmutableMap<ZipPath, ModuleEntry> entryMap;

    $AutoValue_BundleModule(BundleModuleName name, Config.BundleConfig bundleConfig, Resources.XmlNode androidManifestProto, Optional<Resources.ResourceTable> resourceTable, Optional<Files.Assets> assetsConfig, Optional<Files.NativeLibraries> nativeConfig, Optional<Files.ApexImages> apexConfig, ImmutableMap<ZipPath, ModuleEntry> entryMap) {
        if (name == null) {
            throw new NullPointerException("Null name");
        }
        this.name = name;
        if (bundleConfig == null) {
            throw new NullPointerException("Null bundleConfig");
        }
        this.bundleConfig = bundleConfig;
        if (androidManifestProto == null) {
            throw new NullPointerException("Null androidManifestProto");
        }
        this.androidManifestProto = androidManifestProto;
        if (resourceTable == null) {
            throw new NullPointerException("Null resourceTable");
        }
        this.resourceTable = resourceTable;
        if (assetsConfig == null) {
            throw new NullPointerException("Null assetsConfig");
        }
        this.assetsConfig = assetsConfig;
        if (nativeConfig == null) {
            throw new NullPointerException("Null nativeConfig");
        }
        this.nativeConfig = nativeConfig;
        if (apexConfig == null) {
            throw new NullPointerException("Null apexConfig");
        }
        this.apexConfig = apexConfig;
        if (entryMap == null) {
            throw new NullPointerException("Null entryMap");
        }
        this.entryMap = entryMap;
    }

    @Override
    public BundleModuleName getName() {
        return this.name;
    }

    @Override
    public Config.BundleConfig getBundleConfig() {
        return this.bundleConfig;
    }

    @Override
    Resources.XmlNode getAndroidManifestProto() {
        return this.androidManifestProto;
    }

    @Override
    public Optional<Resources.ResourceTable> getResourceTable() {
        return this.resourceTable;
    }

    @Override
    public Optional<Files.Assets> getAssetsConfig() {
        return this.assetsConfig;
    }

    @Override
    public Optional<Files.NativeLibraries> getNativeConfig() {
        return this.nativeConfig;
    }

    @Override
    public Optional<Files.ApexImages> getApexConfig() {
        return this.apexConfig;
    }

    @Override
    ImmutableMap<ZipPath, ModuleEntry> getEntryMap() {
        return this.entryMap;
    }

    public String toString() {
        return "BundleModule{name=" + this.name + ", bundleConfig=" + this.bundleConfig + ", androidManifestProto=" + this.androidManifestProto + ", resourceTable=" + this.resourceTable + ", assetsConfig=" + this.assetsConfig + ", nativeConfig=" + this.nativeConfig + ", apexConfig=" + this.apexConfig + ", entryMap=" + this.entryMap + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof BundleModule) {
            BundleModule that = (BundleModule)o3;
            return this.name.equals(that.getName()) && this.bundleConfig.equals(that.getBundleConfig()) && this.androidManifestProto.equals(that.getAndroidManifestProto()) && this.resourceTable.equals(that.getResourceTable()) && this.assetsConfig.equals(that.getAssetsConfig()) && this.nativeConfig.equals(that.getNativeConfig()) && this.apexConfig.equals(that.getApexConfig()) && this.entryMap.equals(that.getEntryMap());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.name.hashCode();
        h$ *= 1000003;
        h$ ^= this.bundleConfig.hashCode();
        h$ *= 1000003;
        h$ ^= this.androidManifestProto.hashCode();
        h$ *= 1000003;
        h$ ^= this.resourceTable.hashCode();
        h$ *= 1000003;
        h$ ^= this.assetsConfig.hashCode();
        h$ *= 1000003;
        h$ ^= this.nativeConfig.hashCode();
        h$ *= 1000003;
        h$ ^= this.apexConfig.hashCode();
        h$ *= 1000003;
        return h$ ^= this.entryMap.hashCode();
    }

    @Override
    public BundleModule.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder
    extends BundleModule.Builder {
        private BundleModuleName name;
        private Config.BundleConfig bundleConfig;
        private Resources.XmlNode androidManifestProto;
        private Optional<Resources.ResourceTable> resourceTable = Optional.empty();
        private Optional<Files.Assets> assetsConfig = Optional.empty();
        private Optional<Files.NativeLibraries> nativeConfig = Optional.empty();
        private Optional<Files.ApexImages> apexConfig = Optional.empty();
        private ImmutableMap.Builder<ZipPath, ModuleEntry> entryMapBuilder$;
        private ImmutableMap<ZipPath, ModuleEntry> entryMap;

        Builder() {
        }

        private Builder(BundleModule source) {
            this.name = source.getName();
            this.bundleConfig = source.getBundleConfig();
            this.androidManifestProto = source.getAndroidManifestProto();
            this.resourceTable = source.getResourceTable();
            this.assetsConfig = source.getAssetsConfig();
            this.nativeConfig = source.getNativeConfig();
            this.apexConfig = source.getApexConfig();
            this.entryMap = source.getEntryMap();
        }

        @Override
        public BundleModule.Builder setName(BundleModuleName name) {
            if (name == null) {
                throw new NullPointerException("Null name");
            }
            this.name = name;
            return this;
        }

        @Override
        public BundleModule.Builder setBundleConfig(Config.BundleConfig bundleConfig) {
            if (bundleConfig == null) {
                throw new NullPointerException("Null bundleConfig");
            }
            this.bundleConfig = bundleConfig;
            return this;
        }

        @Override
        public BundleModule.Builder setAndroidManifestProto(Resources.XmlNode androidManifestProto) {
            if (androidManifestProto == null) {
                throw new NullPointerException("Null androidManifestProto");
            }
            this.androidManifestProto = androidManifestProto;
            return this;
        }

        @Override
        public BundleModule.Builder setResourceTable(Resources.ResourceTable resourceTable) {
            this.resourceTable = Optional.of(resourceTable);
            return this;
        }

        @Override
        public BundleModule.Builder setAssetsConfig(Files.Assets assetsConfig) {
            this.assetsConfig = Optional.of(assetsConfig);
            return this;
        }

        @Override
        public BundleModule.Builder setNativeConfig(Files.NativeLibraries nativeConfig) {
            this.nativeConfig = Optional.of(nativeConfig);
            return this;
        }

        @Override
        public BundleModule.Builder setApexConfig(Files.ApexImages apexConfig) {
            this.apexConfig = Optional.of(apexConfig);
            return this;
        }

        @Override
        BundleModule.Builder setEntryMap(ImmutableMap<ZipPath, ModuleEntry> entryMap) {
            if (entryMap == null) {
                throw new NullPointerException("Null entryMap");
            }
            if (this.entryMapBuilder$ != null) {
                throw new IllegalStateException("Cannot set entryMap after calling entryMapBuilder()");
            }
            this.entryMap = entryMap;
            return this;
        }

        @Override
        ImmutableMap.Builder<ZipPath, ModuleEntry> entryMapBuilder() {
            if (this.entryMapBuilder$ == null) {
                if (this.entryMap == null) {
                    this.entryMapBuilder$ = ImmutableMap.builder();
                } else {
                    this.entryMapBuilder$ = ImmutableMap.builder();
                    this.entryMapBuilder$.putAll(this.entryMap);
                    this.entryMap = null;
                }
            }
            return this.entryMapBuilder$;
        }

        @Override
        public BundleModule build() {
            if (this.entryMapBuilder$ != null) {
                this.entryMap = this.entryMapBuilder$.build();
            } else if (this.entryMap == null) {
                this.entryMap = ImmutableMap.of();
            }
            String missing = "";
            if (this.name == null) {
                missing = missing + " name";
            }
            if (this.bundleConfig == null) {
                missing = missing + " bundleConfig";
            }
            if (this.androidManifestProto == null) {
                missing = missing + " androidManifestProto";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_BundleModule(this.name, this.bundleConfig, this.androidManifestProto, this.resourceTable, this.assetsConfig, this.nativeConfig, this.apexConfig, this.entryMap);
        }
    }
}

