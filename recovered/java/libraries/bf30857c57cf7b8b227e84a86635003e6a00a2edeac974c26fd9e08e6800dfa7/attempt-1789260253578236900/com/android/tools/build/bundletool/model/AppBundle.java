/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Config;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AutoValue_AppBundle;
import com.android.tools.build.bundletool.model.BundleMetadata;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ClassesDexNameSanitizer;
import com.android.tools.build.bundletool.model.InputStreamSuppliers;
import com.android.tools.build.bundletool.model.ModuleAbiSanitizer;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ZipUtils;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.CheckReturnValue;

@Immutable
@AutoValue
public abstract class AppBundle {
    public static final ZipPath METADATA_DIRECTORY = ZipPath.create("BUNDLE-METADATA");
    public static final String BUNDLE_CONFIG_FILE_NAME = "BundleConfig.pb";

    public static AppBundle buildFromZip(ZipFile bundleFile) {
        Config.BundleConfig bundleConfig = AppBundle.readBundleConfig(bundleFile);
        return AppBundle.buildFromModules(AppBundle.sanitize(AppBundle.extractModules(bundleFile, bundleConfig), bundleConfig), bundleConfig, AppBundle.readBundleMetadata(bundleFile));
    }

    public static AppBundle buildFromModules(ImmutableList<BundleModule> modules, Config.BundleConfig bundleConfig, BundleMetadata bundleMetadata) {
        ImmutableSet<ResourceId> pinnedResourceIds = bundleConfig.getMasterResources().getResourceIdsList().stream().map(ResourceId::create).collect(ImmutableSet.toImmutableSet());
        ImmutableSet<String> pinnedResourceNames = ImmutableSet.copyOf(bundleConfig.getMasterResources().getResourceNamesList());
        return AppBundle.builder().setModules(Maps.uniqueIndex(modules, BundleModule::getName)).setMasterPinnedResourceIds(pinnedResourceIds).setMasterPinnedResourceNames(pinnedResourceNames).setBundleConfig(bundleConfig).setBundleMetadata(bundleMetadata).build();
    }

    public abstract ImmutableMap<BundleModuleName, BundleModule> getModules();

    public abstract ImmutableSet<ResourceId> getMasterPinnedResourceIds();

    public abstract ImmutableSet<String> getMasterPinnedResourceNames();

    public abstract Config.BundleConfig getBundleConfig();

    public abstract BundleMetadata getBundleMetadata();

    public ImmutableMap<BundleModuleName, BundleModule> getFeatureModules() {
        return this.getModules().values().stream().filter(module -> module.getModuleType().equals((Object)BundleModule.ModuleType.FEATURE_MODULE)).collect(ImmutableMap.toImmutableMap(BundleModule::getName, Function.identity()));
    }

    public ImmutableMap<BundleModuleName, BundleModule> getAssetModules() {
        return this.getModules().values().stream().filter(module -> module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)).collect(ImmutableMap.toImmutableMap(BundleModule::getName, Function.identity()));
    }

    public BundleModule getBaseModule() {
        return this.getModule(BundleModuleName.BASE_MODULE_NAME);
    }

    public BundleModule getModule(BundleModuleName moduleName) {
        BundleModule module = this.getModules().get(moduleName);
        if (module == null) {
            throw CommandExecutionException.builder().withMessage("Module '%s' not found.", moduleName).build();
        }
        return module;
    }

    public Version getVersion() {
        return Version.of(this.getBundleConfig().getBundletool().getVersion());
    }

    public boolean has32BitRenderscriptCode() {
        return this.getFeatureModules().values().stream().anyMatch(BundleModule::hasRenderscript32Bitcode);
    }

    public ImmutableSet<Targeting.Abi> getTargetedAbis() {
        return this.getFeatureModules().values().stream().map(BundleModule::getNativeConfig).flatMap(nativeConfig -> {
            if (nativeConfig.isPresent()) {
                return ((Files.NativeLibraries)nativeConfig.get()).getDirectoryList().stream().map(Files.TargetedNativeDirectory::getTargeting).map(Targeting.NativeDirectoryTargeting::getAbi);
            }
            return Stream.empty();
        }).distinct().collect(ImmutableSet.toImmutableSet());
    }

    public static Optional<BundleModuleName> extractModuleName(ZipEntry entry) {
        ZipPath path = ZipPath.create(entry.getName());
        if (path.startsWith(METADATA_DIRECTORY)) {
            return Optional.empty();
        }
        if (path.startsWith("META-INF")) {
            return Optional.empty();
        }
        if (path.getNameCount() <= 1) {
            return Optional.empty();
        }
        if (path.toString().endsWith(".class")) {
            return Optional.empty();
        }
        return Optional.of(BundleModuleName.create(path.getName(0).toString()));
    }

    public boolean isApex() {
        return this.getBaseModule().getApexConfig().isPresent();
    }

    public abstract Builder toBuilder();

    static Builder builder() {
        return new AutoValue_AppBundle.Builder();
    }

    private static ImmutableList<BundleModule> extractModules(ZipFile bundleFile, Config.BundleConfig bundleConfig) {
        HashMap<BundleModuleName, BundleModule.Builder> moduleBuilders = new HashMap<BundleModuleName, BundleModule.Builder>();
        Enumeration<? extends ZipEntry> entries = bundleFile.entries();
        while (entries.hasMoreElements()) {
            Optional<BundleModuleName> moduleName;
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory() || !(moduleName = AppBundle.extractModuleName(entry)).isPresent()) continue;
            BundleModule.Builder moduleBuilder = moduleBuilders.computeIfAbsent(moduleName.get(), name -> BundleModule.builder().setName((BundleModuleName)name).setBundleConfig(bundleConfig));
            try {
                moduleBuilder.addEntry(ModuleEntry.builder().setPath(ZipUtils.convertBundleToModulePath(ZipPath.create(entry.getName()))).setContentSupplier(InputStreamSuppliers.fromZipEntry(entry, bundleFile)).build());
            }
            catch (IOException e2) {
                throw ValidationException.builder().withCause(e2).withMessage("Error processing zip entry '%s' of module '%s'.", entry.getName(), moduleName.get()).build();
            }
        }
        return moduleBuilders.values().stream().map(module -> module.build()).collect(ImmutableList.toImmutableList());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Config.BundleConfig readBundleConfig(ZipFile bundleFile) {
        ZipEntry bundleConfigEntry = bundleFile.getEntry(BUNDLE_CONFIG_FILE_NAME);
        if (bundleConfigEntry == null) {
            throw ValidationException.builder().withMessage("File '%s' was not found.", BUNDLE_CONFIG_FILE_NAME).build();
        }
        try (InputStream is = BufferedIo.inputStream(bundleFile, bundleConfigEntry);){
            Config.BundleConfig bundleConfig = Config.BundleConfig.parseFrom(is);
            return bundleConfig;
        }
        catch (InvalidProtocolBufferException e2) {
            throw ValidationException.builder().withCause(e2).withMessage("Bundle config '%s' could not be parsed.", BUNDLE_CONFIG_FILE_NAME).build();
        }
        catch (IOException e3) {
            throw CommandExecutionException.builder().withCause(e3).withMessage("Error reading file '%s'.", BUNDLE_CONFIG_FILE_NAME).build();
        }
    }

    private static BundleMetadata readBundleMetadata(ZipFile bundleFile) {
        BundleMetadata.Builder metadata = BundleMetadata.builder();
        ZipUtils.allFileEntries(bundleFile).filter(entry -> ZipPath.create(entry.getName()).startsWith(METADATA_DIRECTORY)).forEach(zipEntry -> {
            ZipPath bundlePath = ZipPath.create(zipEntry.getName());
            ZipPath metadataPath = bundlePath.subpath(1, bundlePath.getNameCount());
            metadata.addFile(metadataPath, BufferedIo.inputStreamSupplier(bundleFile, zipEntry));
        });
        return metadata.build();
    }

    @CheckReturnValue
    private static ImmutableList<BundleModule> sanitize(ImmutableList<BundleModule> modules, Config.BundleConfig bundleConfig) {
        Version bundleVersion = BundleToolVersion.getVersionFromBundleConfig(bundleConfig);
        if (!VersionGuardedFeature.ABI_SANITIZER_DISABLED.enabledForVersion(bundleVersion)) {
            modules = modules.stream().map(new ModuleAbiSanitizer()::sanitize).collect(ImmutableList.toImmutableList());
        }
        modules = modules.stream().map(new ClassesDexNameSanitizer()::sanitize).collect(ImmutableList.toImmutableList());
        return modules;
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setModules(ImmutableMap<BundleModuleName, BundleModule> var1);

        public Builder setRawModules(Collection<BundleModule> bundleModules) {
            this.setModules(bundleModules.stream().collect(ImmutableMap.toImmutableMap(BundleModule::getName, Function.identity())));
            return this;
        }

        public abstract Builder setMasterPinnedResourceIds(ImmutableSet<ResourceId> var1);

        public abstract Builder setMasterPinnedResourceNames(ImmutableSet<String> var1);

        public abstract Builder setBundleConfig(Config.BundleConfig var1);

        public abstract Builder setBundleMetadata(BundleMetadata var1);

        public abstract AppBundle build();
    }
}

