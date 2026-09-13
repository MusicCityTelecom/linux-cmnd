/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.tools.build.bundletool.io.ZipBuilder;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.protobuf.MessageLite;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class AppBundleSerializer {
    private final boolean allEntriesUncompressed;

    public AppBundleSerializer(boolean allEntriesUncompressed) {
        this.allEntriesUncompressed = allEntriesUncompressed;
    }

    public AppBundleSerializer() {
        this(false);
    }

    public void writeToDisk(AppBundle bundle, Path pathOnDisk) throws IOException {
        ZipBuilder.EntryOption[] entryOptionArray;
        ZipBuilder zipBuilder = new ZipBuilder();
        if (this.allEntriesUncompressed) {
            ZipBuilder.EntryOption[] entryOptionArray2 = new ZipBuilder.EntryOption[1];
            entryOptionArray = entryOptionArray2;
            entryOptionArray2[0] = ZipBuilder.EntryOption.UNCOMPRESSED;
        } else {
            entryOptionArray = new ZipBuilder.EntryOption[]{};
        }
        ZipBuilder.EntryOption[] compression = entryOptionArray;
        zipBuilder.addFileWithProtoContent(ZipPath.create("BundleConfig.pb"), bundle.getBundleConfig(), compression);
        if (bundle.getFeatureModules().isEmpty() || !bundle.isApex()) {
            for (Map.Entry metadataEntry : bundle.getBundleMetadata().getFileDataMap().entrySet()) {
                zipBuilder.addFile(AppBundle.METADATA_DIRECTORY.resolve((ZipPath)metadataEntry.getKey()), (InputStreamSupplier)metadataEntry.getValue(), compression);
            }
        }
        for (BundleModule module : bundle.getModules().values()) {
            ZipPath moduleDir = ZipPath.create(module.getName().toString());
            for (ModuleEntry entry : module.getEntries()) {
                ZipPath entryPath = moduleDir.resolve(entry.getPath());
                zipBuilder.addFile(entryPath, entry.getContentSupplier(), compression);
            }
            zipBuilder.addFileWithProtoContent(moduleDir.resolve(BundleModule.SpecialModuleEntry.ANDROID_MANIFEST.getPath()), module.getAndroidManifest().getManifestRoot().getProto(), compression);
            module.getAssetsConfig().ifPresent(assetsConfig -> zipBuilder.addFileWithProtoContent(moduleDir.resolve(BundleModule.SpecialModuleEntry.ASSETS_TABLE.getPath()), (MessageLite)assetsConfig, compression));
            module.getNativeConfig().ifPresent(nativeConfig -> zipBuilder.addFileWithProtoContent(moduleDir.resolve(BundleModule.SpecialModuleEntry.NATIVE_LIBS_TABLE.getPath()), (MessageLite)nativeConfig, compression));
            module.getResourceTable().ifPresent(resourceTable -> zipBuilder.addFileWithProtoContent(moduleDir.resolve(BundleModule.SpecialModuleEntry.RESOURCE_TABLE.getPath()), (MessageLite)resourceTable, compression));
            module.getApexConfig().ifPresent(apexConfig -> zipBuilder.addFileWithProtoContent(moduleDir.resolve(BundleModule.SpecialModuleEntry.APEX_TABLE.getPath()), (MessageLite)apexConfig, compression));
        }
        zipBuilder.writeTo(pathOnDisk);
    }
}

