/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNodeBuilder;
import com.android.tools.build.bundletool.model.version.Version;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MoreCollectors;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.CheckReturnValue;

public class ManifestEditor {
    private static final ImmutableList<String> SPLIT_NAME_ELEMENT_NAMES = ImmutableList.of("activity", "service", "provider");
    private final XmlProtoNodeBuilder rootNode;
    private final XmlProtoElementBuilder manifestElement;
    private final Version bundleToolVersion;

    public ManifestEditor(XmlProtoNode rootNode, Version bundleToolVersion) {
        this.rootNode = rootNode.toBuilder();
        this.manifestElement = (XmlProtoElementBuilder)this.rootNode.getElement();
        this.bundleToolVersion = bundleToolVersion;
    }

    public XmlProtoElementBuilder getRawProto() {
        return this.manifestElement;
    }

    public ManifestEditor setMinSdkVersion(int minSdkVersion) {
        return this.setUsesSdkAttribute("minSdkVersion", 16843276, minSdkVersion);
    }

    public ManifestEditor setMaxSdkVersion(int maxSdkVersion) {
        return this.setUsesSdkAttribute("maxSdkVersion", 16843377, maxSdkVersion);
    }

    public ManifestEditor setTargetSdkVersion(int targetSdkVersion) {
        return this.setUsesSdkAttribute("targetSdkVersion", 16843376, targetSdkVersion);
    }

    public ManifestEditor setSplitIdForFeatureSplit(String splitId) {
        if (ManifestEditor.isBaseSplit(splitId)) {
            this.manifestElement.removeAttribute("", "split");
            this.manifestElement.removeAttribute("http://schemas.android.com/apk/res/android", "isFeatureSplit");
        } else {
            this.manifestElement.getOrCreateAttribute("split").setValueAsString(splitId);
            this.manifestElement.getOrCreateAndroidAttribute("isFeatureSplit", 16844123).setValueAsBoolean(true);
        }
        this.manifestElement.removeAttribute("", "configForSplit");
        return this;
    }

    public ManifestEditor setHasCode(boolean value) {
        this.manifestElement.getOrCreateChildElement("application").getOrCreateAndroidAttribute("hasCode", 0x101000C).setValueAsBoolean(value);
        return this;
    }

    public ManifestEditor setPackage(String packageName) {
        this.manifestElement.getOrCreateAttribute("package").setValueAsString(packageName);
        return this;
    }

    public ManifestEditor setVersionCode(int versionCode) {
        this.manifestElement.getOrCreateAndroidAttribute("versionCode", 16843291).setValueAsDecimalInteger(versionCode);
        return this;
    }

    public ManifestEditor setVersionName(String versionName) {
        this.manifestElement.getOrCreateAndroidAttribute("versionName", 16843292).setValueAsString(versionName);
        return this;
    }

    public ManifestEditor setConfigForSplit(String featureSplitId) {
        this.manifestElement.getOrCreateAttribute("configForSplit").setValueAsString(featureSplitId);
        return this;
    }

    public ManifestEditor setSplitId(String splitId) {
        this.manifestElement.getOrCreateAttribute("split").setValueAsString(splitId);
        return this;
    }

    public ManifestEditor setTargetSandboxVersion(int version) {
        this.manifestElement.getOrCreateAndroidAttribute("targetSandboxVersion", 16844108).setValueAsDecimalInteger(version);
        return this;
    }

    public ManifestEditor addMetaDataString(String key, String value) {
        return this.addMetaDataValue(key, XmlProtoAttributeBuilder.createAndroidAttribute("value", 16842788).setValueAsString(value));
    }

    public ManifestEditor addMetaDataInteger(String key, int value) {
        return this.addMetaDataValue(key, XmlProtoAttributeBuilder.createAndroidAttribute("value", 16842788).setValueAsDecimalInteger(value));
    }

    public ManifestEditor addMetaDataResourceId(String key, int resourceId) {
        return this.addMetaDataValue(key, XmlProtoAttributeBuilder.createAndroidAttribute("value", 16842789).setValueAsRefId(resourceId));
    }

    private ManifestEditor addMetaDataValue(String key, XmlProtoAttributeBuilder valueAttribute) {
        this.manifestElement.getOrCreateChildElement("application").addChildElement(XmlProtoElementBuilder.create("meta-data").addAttribute(XmlProtoAttributeBuilder.createAndroidAttribute("name", 0x1010003).setValueAsString(key)).addAttribute(valueAttribute));
        return this;
    }

    public ManifestEditor setExtractNativeLibsValue(boolean value) {
        this.manifestElement.getOrCreateChildElement("application").getOrCreateAndroidAttribute("extractNativeLibs", 16844010).setValueAsBoolean(value);
        return this;
    }

    public ManifestEditor setFusedModuleNames(ImmutableList<String> moduleNames) {
        String moduleNamesString = moduleNames.stream().sorted().distinct().collect(Collectors.joining(","));
        this.setMetadataValue("com.android.dynamic.apk.fused.modules", XmlProtoAttributeBuilder.createAndroidAttribute("value", 16842788).setValueAsString(moduleNamesString));
        return this;
    }

    public ManifestEditor setSplitsRequired(boolean value) {
        this.setMetadataValue("com.android.vending.splits.required", XmlProtoAttributeBuilder.createAndroidAttribute("value", 16842788).setValueAsBoolean(value));
        this.manifestElement.getOrCreateChildElement("application").getOrCreateAndroidAttribute("isSplitRequired", 16844177).setValueAsBoolean(value);
        return this;
    }

    public ManifestEditor addApplicationElementIfMissing() {
        this.manifestElement.getOrCreateChildElement("application");
        return this;
    }

    public ManifestEditor removeSplitName() {
        this.manifestElement.getOrCreateChildElement("application").getChildrenElements(el -> SPLIT_NAME_ELEMENT_NAMES.contains(el.getName())).forEach(element -> element.removeAndroidAttribute(16844105));
        return this;
    }

    public ManifestEditor removeUnknownSplitComponents(ImmutableSet<String> allModuleNames) {
        Optional applicationElement = this.manifestElement.getOptionalChildElement("application");
        if (!applicationElement.isPresent()) {
            return this;
        }
        ((XmlProtoElementBuilder)applicationElement.get()).removeChildrenElementsIf(el -> el.isElement() && ((XmlProtoElementBuilder)el.getElement()).getAndroidAttribute(16844105).filter(attr -> !allModuleNames.contains(attr.getValueAsString())).isPresent());
        return this;
    }

    public ManifestEditor addOrReplaceActivities(Map<String, XmlProtoElement> activitiesByClassName) {
        if (activitiesByClassName.isEmpty()) {
            return this;
        }
        XmlProtoElementBuilder app = this.manifestElement.getOrCreateChildElement("application");
        app.removeChildrenElementsIf(el -> el.isElement() && "activity".equals(((XmlProtoElementBuilder)el.getElement()).getName()) && ((XmlProtoElementBuilder)el.getElement()).getAndroidAttribute(0x1010003).filter(name -> activitiesByClassName.containsKey(name.getValueAsString())).isPresent());
        activitiesByClassName.values().forEach(activity -> app.addChildElement(activity.toBuilder()));
        return this;
    }

    @CheckReturnValue
    public AndroidManifest save() {
        return AndroidManifest.create(this.rootNode.build(), this.bundleToolVersion);
    }

    private ManifestEditor setMetadataValue(String name, XmlProtoAttributeBuilder valueAttr) {
        XmlProtoElementBuilder applicationEl = this.manifestElement.getOrCreateChildElement("application");
        Optional existingMetadataEl = applicationEl.getChildrenElements("meta-data").filter(metadataEl -> metadataEl.getAndroidAttribute(0x1010003).map(nameAttr -> name.equals(nameAttr.getValueAsString())).orElse(false)).collect(MoreCollectors.toOptional());
        if (existingMetadataEl.isPresent()) {
            ((XmlProtoElementBuilder)existingMetadataEl.get()).removeAndroidAttribute(16842788).addAttribute(valueAttr);
        } else {
            applicationEl.addChildElement(XmlProtoElementBuilder.create("meta-data").addAttribute(XmlProtoAttributeBuilder.createAndroidAttribute("name", 0x1010003).setValueAsString(name)).addAttribute(valueAttr));
        }
        return this;
    }

    private ManifestEditor setUsesSdkAttribute(String attributeName, int attributeResId, int value) {
        this.manifestElement.getOrCreateChildElement("uses-sdk").getOrCreateAndroidAttribute(attributeName, attributeResId).setValueAsDecimalInteger(value);
        return this;
    }

    private static boolean isBaseSplit(String splitId) {
        return splitId.isEmpty();
    }
}

