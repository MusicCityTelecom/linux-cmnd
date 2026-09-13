/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.bundle.Commands;
import com.android.bundle.Config;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.$AutoValue_BundleModule;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.ModuleConditions;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class BundleModule {
    public static final String MANIFEST_FILENAME = "AndroidManifest.xml";
    public static final ZipPath ASSETS_DIRECTORY = ZipPath.create("assets");
    public static final ZipPath DEX_DIRECTORY = ZipPath.create("dex");
    public static final ZipPath LIB_DIRECTORY = ZipPath.create("lib");
    public static final ZipPath MANIFEST_DIRECTORY = ZipPath.create("manifest");
    public static final ZipPath RESOURCES_DIRECTORY = ZipPath.create("res");
    public static final ZipPath ROOT_DIRECTORY = ZipPath.create("root");
    public static final ZipPath APEX_DIRECTORY = ZipPath.create("apex");
    public static final String APEX_IMAGE_SUFFIX = "img";
    public static final String BUILD_INFO_SUFFIX = "build_info.pb";
    public static final ZipPath APEX_MANIFEST_PATH = ZipPath.create("root/apex_manifest.pb");
    public static final ZipPath APEX_MANIFEST_JSON_PATH = ZipPath.create("root/apex_manifest.json");
    public static final ZipPath APEX_PUBKEY_PATH = ZipPath.create("root/apex_pubkey");
    public static final ZipPath APEX_NOTICE_PATH = ZipPath.create("assets/NOTICE.html.gz");
    public static final Splitter ABI_SPLITTER = Splitter.on(".").omitEmptyStrings();

    public abstract BundleModuleName getName();

    public abstract Config.BundleConfig getBundleConfig();

    abstract Resources.XmlNode getAndroidManifestProto();

    public AndroidManifest getAndroidManifest() {
        return AndroidManifest.create(this.getAndroidManifestProto(), BundleToolVersion.getVersionFromBundleConfig(this.getBundleConfig()));
    }

    public abstract Optional<Resources.ResourceTable> getResourceTable();

    public abstract Optional<Files.Assets> getAssetsConfig();

    public abstract Optional<Files.NativeLibraries> getNativeConfig();

    public abstract Optional<Files.ApexImages> getApexConfig();

    abstract ImmutableMap<ZipPath, ModuleEntry> getEntryMap();

    public ImmutableCollection<ModuleEntry> getEntries() {
        return this.getEntryMap().values();
    }

    public boolean isBaseModule() {
        return BundleModuleName.BASE_MODULE_NAME.equals(this.getName());
    }

    public ModuleDeliveryType getDeliveryType() {
        if (this.getAndroidManifest().getManifestDeliveryElement().isPresent()) {
            ManifestDeliveryElement manifestDeliveryElement = this.getAndroidManifest().getManifestDeliveryElement().get();
            if (manifestDeliveryElement.hasInstallTimeElement()) {
                return manifestDeliveryElement.hasModuleConditions() ? ModuleDeliveryType.CONDITIONAL_INITIAL_INSTALL : ModuleDeliveryType.ALWAYS_INITIAL_INSTALL;
            }
            return ModuleDeliveryType.NO_INITIAL_INSTALL;
        }
        if (this.getAndroidManifest().getOnDemandAttribute().map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsBoolean()).orElse(false).booleanValue()) {
            return ModuleDeliveryType.NO_INITIAL_INSTALL;
        }
        return ModuleDeliveryType.ALWAYS_INITIAL_INSTALL;
    }

    public Optional<ModuleDeliveryType> getInstantDeliveryType() {
        if (!this.isInstantModule()) {
            return Optional.empty();
        }
        if (this.getAndroidManifest().getInstantManifestDeliveryElement().isPresent()) {
            ManifestDeliveryElement instantManifestDeliveryElement = this.getAndroidManifest().getInstantManifestDeliveryElement().get();
            if (instantManifestDeliveryElement.hasInstallTimeElement()) {
                return instantManifestDeliveryElement.hasModuleConditions() ? Optional.of(ModuleDeliveryType.CONDITIONAL_INITIAL_INSTALL) : Optional.of(ModuleDeliveryType.ALWAYS_INITIAL_INSTALL);
            }
            return Optional.of(ModuleDeliveryType.NO_INITIAL_INSTALL);
        }
        return Optional.of(ModuleDeliveryType.NO_INITIAL_INSTALL);
    }

    public ModuleType getModuleType() {
        return this.getAndroidManifest().getModuleType();
    }

    public boolean isIncludedInFusing() {
        return this.isBaseModule() || this.getAndroidManifest().getIsModuleIncludedInFusing().orElseThrow(() -> new ValidationException("Unable to determine if module should be fused: missing <dist:fusing> tag inside <dist:module> in AndroidManifest.xml")) != false;
    }

    public boolean isInstantModule() {
        Optional<Boolean> isInstantModule = this.getAndroidManifest().isInstantModule();
        return isInstantModule.orElse(false);
    }

    public boolean hasRenderscript32Bitcode() {
        return this.findEntries(zipPath -> zipPath.toString().endsWith(".bc")).findFirst().isPresent();
    }

    public ImmutableList<String> getDependencies() {
        return this.getAndroidManifest().getUsesSplits();
    }

    private Targeting.ModuleTargeting getModuleTargeting() {
        return this.getAndroidManifest().getManifestDeliveryElement().map(ManifestDeliveryElement::getModuleConditions).map(ModuleConditions::toTargeting).map(this::applyMinSdkVersion).orElse(Targeting.ModuleTargeting.getDefaultInstance());
    }

    private Targeting.ModuleTargeting applyMinSdkVersion(Targeting.ModuleTargeting moduleTargeting) {
        Optional<Integer> minSdkVersion = this.getAndroidManifest().getMinSdkVersion();
        if (moduleTargeting.equals(Targeting.ModuleTargeting.getDefaultInstance()) || moduleTargeting.hasSdkVersionTargeting() || !minSdkVersion.isPresent()) {
            return moduleTargeting;
        }
        return moduleTargeting.toBuilder().setSdkVersionTargeting(TargetingProtoUtils.sdkVersionTargeting(TargetingProtoUtils.sdkVersionFrom(minSdkVersion.get()))).build();
    }

    public Stream<ModuleEntry> findEntries(Predicate<ZipPath> pathPredicate) {
        return this.getEntries().stream().filter(entry -> pathPredicate.test(entry.getPath()));
    }

    public Stream<ModuleEntry> findEntriesUnderPath(ZipPath path) {
        return this.findEntries(p3 -> p3.startsWith(path));
    }

    public Optional<ModuleEntry> getEntry(ZipPath path) {
        return Optional.ofNullable(this.getEntryMap().get(path));
    }

    public Commands.ModuleMetadata getModuleMetadata() {
        return Commands.ModuleMetadata.newBuilder().setName(this.getName().getName()).setIsInstant(this.isInstantModule()).addAllDependencies(this.getDependencies()).setTargeting(this.getModuleTargeting()).setDeliveryType(BundleModule.moduleDeliveryTypeToDeliveryType(this.getDeliveryType())).build();
    }

    private static Commands.DeliveryType moduleDeliveryTypeToDeliveryType(ModuleDeliveryType moduleDeliveryType) {
        switch (moduleDeliveryType) {
            case ALWAYS_INITIAL_INSTALL: 
            case CONDITIONAL_INITIAL_INSTALL: {
                return Commands.DeliveryType.INSTALL_TIME;
            }
            case NO_INITIAL_INSTALL: {
                return Commands.DeliveryType.ON_DEMAND;
            }
        }
        throw new IllegalArgumentException("Unknown module delivery type: " + (Object)((Object)moduleDeliveryType));
    }

    public static Builder builder() {
        return new $AutoValue_BundleModule.Builder();
    }

    public abstract Builder toBuilder();

    public static enum SpecialModuleEntry {
        ANDROID_MANIFEST("manifest/AndroidManifest.xml"){

            @Override
            void addToModule(Builder module, InputStream inputStream) throws IOException {
                module.setAndroidManifestProto(Resources.XmlNode.parseFrom(inputStream));
            }
        }
        ,
        RESOURCE_TABLE("resources.pb"){

            @Override
            void addToModule(Builder module, InputStream inputStream) throws IOException {
                module.setResourceTable(Resources.ResourceTable.parseFrom(inputStream));
            }
        }
        ,
        ASSETS_TABLE("assets.pb"){

            @Override
            void addToModule(Builder module, InputStream inputStream) throws IOException {
                module.setAssetsConfig(Files.Assets.parseFrom(inputStream));
            }
        }
        ,
        NATIVE_LIBS_TABLE("native.pb"){

            @Override
            void addToModule(Builder module, InputStream inputStream) throws IOException {
                module.setNativeConfig(Files.NativeLibraries.parseFrom(inputStream));
            }
        }
        ,
        APEX_TABLE("apex.pb"){

            @Override
            void addToModule(Builder module, InputStream inputStream) throws IOException {
                module.setApexConfig(Files.ApexImages.parseFrom(inputStream));
            }
        };

        private static final ImmutableMap<ZipPath, SpecialModuleEntry> SPECIAL_ENTRY_BY_PATH;
        private final ZipPath entryPath;

        abstract void addToModule(Builder var1, InputStream var2) throws IOException;

        public static Optional<SpecialModuleEntry> getSpecialEntry(ZipPath entryPath) {
            return Optional.ofNullable(SPECIAL_ENTRY_BY_PATH.get(entryPath));
        }

        private SpecialModuleEntry(String entryPath) {
            this.entryPath = ZipPath.create(entryPath);
        }

        public ZipPath getPath() {
            return this.entryPath;
        }

        static {
            SPECIAL_ENTRY_BY_PATH = Arrays.stream(SpecialModuleEntry.values()).collect(ImmutableMap.toImmutableMap(SpecialModuleEntry::getPath, Function.identity()));
        }
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setName(BundleModuleName var1);

        public abstract Builder setBundleConfig(Config.BundleConfig var1);

        public abstract Builder setResourceTable(Resources.ResourceTable var1);

        public Builder setAndroidManifest(AndroidManifest androidManifest) {
            return this.setAndroidManifestProto(androidManifest.getManifestRoot().getProto());
        }

        public abstract Builder setAndroidManifestProto(Resources.XmlNode var1);

        public abstract Builder setAssetsConfig(Files.Assets var1);

        public abstract Builder setNativeConfig(Files.NativeLibraries var1);

        public abstract Builder setApexConfig(Files.ApexImages var1);

        abstract ImmutableMap.Builder<ZipPath, ModuleEntry> entryMapBuilder();

        abstract Builder setEntryMap(ImmutableMap<ZipPath, ModuleEntry> var1);

        public Builder setRawEntries(Collection<ModuleEntry> entries) {
            entries.forEach(entry -> Preconditions.checkArgument(!SpecialModuleEntry.getSpecialEntry(entry.getPath()).isPresent(), "Cannot add special entry '%s' using method setRawEntries.", (Object)entry.getPath()));
            this.setEntryMap(entries.stream().collect(ImmutableMap.toImmutableMap(ModuleEntry::getPath, Function.identity())));
            return this;
        }

        public Builder addEntries(Collection<ModuleEntry> entries) throws IOException {
            for (ModuleEntry entry : entries) {
                this.addEntry(entry);
            }
            return this;
        }

        public Builder addEntry(ModuleEntry moduleEntry) throws IOException {
            Optional<SpecialModuleEntry> specialEntry = SpecialModuleEntry.getSpecialEntry(moduleEntry.getPath());
            if (specialEntry.isPresent()) {
                try (InputStream inputStream = moduleEntry.getContent();){
                    specialEntry.get().addToModule(this, inputStream);
                }
            } else {
                this.entryMapBuilder().put(moduleEntry.getPath(), moduleEntry);
            }
            return this;
        }

        public abstract BundleModule build();
    }

    public static enum ModuleType {
        FEATURE_MODULE,
        ASSET_MODULE;

    }

    public static enum ModuleDeliveryType {
        ALWAYS_INITIAL_INSTALL,
        CONDITIONAL_INITIAL_INSTALL,
        NO_INITIAL_INSTALL;

    }
}

