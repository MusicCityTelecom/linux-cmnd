/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;

public final class WearApkLocator {
    static final String WEAR_APK_1_0_METADATA_KEY = "com.google.android.wearable.beta.app";

    public static Optional<ZipPath> findEmbeddedWearApkPath(ModuleSplit split) {
        if (!split.getResourceTable().isPresent()) {
            return Optional.empty();
        }
        Resources.ResourceTable resourceTable = split.getResourceTable().get();
        AndroidManifest manifest = split.getAndroidManifest();
        Optional<ZipPath> embeddedWearApkPath = manifest.getMetadataResourceId(WEAR_APK_1_0_METADATA_KEY).map(resourceId -> WearApkLocator.findXmlDescriptionResourceEntry(resourceTable, resourceId)).map(entry -> WearApkLocator.getXmlDescriptionPath(entry)).map(xmlDescriptionPath -> WearApkLocator.findXmlDescriptionZipEntry(split, xmlDescriptionPath)).flatMap(xmlDescriptionEntry -> WearApkLocator.extractWearApkName(xmlDescriptionEntry)).map(apkName -> ZipPath.create(String.format("res/raw/%s.apk", apkName)));
        embeddedWearApkPath.ifPresent(path -> {
            if (!split.findEntry((ZipPath)path).isPresent()) {
                throw ValidationException.builder().withMessage("Wear APK expected at location '%s' but was not found.", path).build();
            }
        });
        return embeddedWearApkPath;
    }

    private static Resources.Entry findXmlDescriptionResourceEntry(Resources.ResourceTable resourceTable, int resourceId) {
        return ResourcesUtils.lookupEntryByResourceId(resourceTable, resourceId).orElseThrow(() -> ValidationException.builder().withMessage("Resource 0x%08x is referenced in the manifest in the '%s' metadata, but was not found in the resource table.", resourceId, WEAR_APK_1_0_METADATA_KEY).build());
    }

    private static String getXmlDescriptionPath(Resources.Entry entry) {
        Preconditions.checkState(entry.getConfigValueCount() > 0, "Resource table entry without value: %s", (Object)entry);
        if (entry.getConfigValueCount() > 1) {
            throw new ValidationException("More than one embedded Wear APK is not supported.");
        }
        Resources.ConfigValue configValue = Iterables.getOnlyElement(entry.getConfigValueList());
        String xmlDescriptionPath = configValue.getValue().getItem().getFile().getPath();
        if (xmlDescriptionPath.isEmpty()) {
            throw new ValidationException("No XML description file path found for Wear APK.");
        }
        return xmlDescriptionPath;
    }

    private static ModuleEntry findXmlDescriptionZipEntry(ModuleSplit split, String xmlDescPath) {
        return split.findEntry(xmlDescPath).orElseThrow(() -> ValidationException.builder().withMessage("Wear APK XML description file expected at '%s' but was not found.", xmlDescPath).build());
    }

    private static Optional<String> extractWearApkName(ModuleEntry wearApkDescriptionXmlEntry) {
        XmlProtoNode root;
        try (InputStream content = wearApkDescriptionXmlEntry.getContent();){
            root = new XmlProtoNode(Resources.XmlNode.parseFrom(content));
        }
        catch (InvalidProtocolBufferException e2) {
            throw ValidationException.builder().withCause(e2).withMessage("The wear APK description file '%s' could not be parsed.", wearApkDescriptionXmlEntry.getPath()).build();
        }
        catch (IOException e3) {
            throw new UncheckedIOException(String.format("An unexpected error occurred while reading APK description file '%s'.", wearApkDescriptionXmlEntry.getPath()), e3);
        }
        if (((XmlProtoElement)root.getElement()).getOptionalChildElement("unbundled").isPresent()) {
            return Optional.empty();
        }
        Optional rawPathResId = ((XmlProtoElement)root.getElement()).getOptionalChildElement("rawPathResId");
        if (!rawPathResId.isPresent()) {
            throw ValidationException.builder().withMessage("The wear APK description file '%s' does not contain 'unbundled' or 'rawPathResId'.", wearApkDescriptionXmlEntry.getPath()).build();
        }
        return Optional.of(((XmlProtoNode)((XmlProtoElement)rawPathResId.get()).getChildText().get()).getText());
    }

    private WearApkLocator() {
    }
}

