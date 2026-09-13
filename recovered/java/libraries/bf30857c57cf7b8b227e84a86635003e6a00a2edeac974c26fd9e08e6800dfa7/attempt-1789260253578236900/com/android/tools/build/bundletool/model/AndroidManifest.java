/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.AutoValue_AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.ManifestEditor;
import com.android.tools.build.bundletool.model.ManifestMutator;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestFusingException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.model.version.BundleToolVersion;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import com.google.common.collect.Streams;
import com.google.errorprone.annotations.Immutable;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.CheckReturnValue;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class AndroidManifest {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');
    public static final String ANDROID_NAMESPACE_URI = "http://schemas.android.com/apk/res/android";
    public static final String DISTRIBUTION_NAMESPACE_URI = "http://schemas.android.com/apk/distribution";
    public static final String NO_NAMESPACE_URI = "";
    public static final String APPLICATION_ELEMENT_NAME = "application";
    public static final String META_DATA_ELEMENT_NAME = "meta-data";
    public static final String USES_SDK_ELEMENT_NAME = "uses-sdk";
    public static final String ACTIVITY_ELEMENT_NAME = "activity";
    public static final String SERVICE_ELEMENT_NAME = "service";
    public static final String PROVIDER_ELEMENT_NAME = "provider";
    public static final String SUPPORTS_GL_TEXTURE_ELEMENT_NAME = "supports-gl-texture";
    public static final String DEBUGGABLE_ATTRIBUTE_NAME = "debuggable";
    public static final String EXTRACT_NATIVE_LIBS_ATTRIBUTE_NAME = "extractNativeLibs";
    public static final String ICON_ATTRIBUTE_NAME = "icon";
    public static final String MAX_SDK_VERSION_ATTRIBUTE_NAME = "maxSdkVersion";
    public static final String MIN_SDK_VERSION_ATTRIBUTE_NAME = "minSdkVersion";
    public static final String TARGET_SDK_VERSION_ATTRIBUTE_NAME = "targetSdkVersion";
    public static final String NAME_ATTRIBUTE_NAME = "name";
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String CODE_ATTRIBUTE_NAME = "code";
    public static final String EXCLUDE_ATTRIBUTE_NAME = "exclude";
    public static final String THEME_ATTRIBUTE_NAME = "theme";
    public static final String COUNTRY_ELEMENT_NAME = "country";
    public static final String CONDITION_DEVICE_FEATURE_NAME = "device-feature";
    public static final String CONDITION_MIN_SDK_VERSION_NAME = "min-sdk";
    public static final String CONDITION_MAX_SDK_VERSION_NAME = "max-sdk";
    public static final String CONDITION_USER_COUNTRIES_NAME = "user-countries";
    public static final String SPLIT_NAME_ATTRIBUTE_NAME = "splitName";
    public static final String VERSION_NAME_ATTRIBUTE_NAME = "versionName";
    public static final String INSTALL_LOCATION_ATTRIBUTE_NAME = "installLocation";
    public static final String IS_SPLIT_REQUIRED_ATTRIBUTE_NAME = "isSplitRequired";
    public static final String MODULE_TYPE_FEATURE_VALUE = "feature";
    public static final String MODULE_TYPE_ASSET_VALUE = "asset-pack";
    public static final String NATIVE_ACTIVITY_LIB_NAME = "android.app.lib_name";
    public static final int DEBUGGABLE_RESOURCE_ID = 0x101000F;
    public static final int EXTRACT_NATIVE_LIBS_RESOURCE_ID = 16844010;
    public static final int HAS_CODE_RESOURCE_ID = 0x101000C;
    public static final int ICON_RESOURCE_ID = 0x1010002;
    public static final int MAX_SDK_VERSION_RESOURCE_ID = 16843377;
    public static final int MIN_SDK_VERSION_RESOURCE_ID = 16843276;
    public static final int TARGET_SDK_VERSION_RESOURCE_ID = 16843376;
    public static final int NAME_RESOURCE_ID = 0x1010003;
    public static final int VALUE_RESOURCE_ID = 16842788;
    public static final int RESOURCE_RESOURCE_ID = 16842789;
    public static final int VERSION_CODE_RESOURCE_ID = 16843291;
    public static final int VERSION_NAME_RESOURCE_ID = 16843292;
    public static final int IS_FEATURE_SPLIT_RESOURCE_ID = 16844123;
    public static final int TARGET_SANDBOX_VERSION_RESOURCE_ID = 16844108;
    public static final int SPLIT_NAME_RESOURCE_ID = 16844105;
    public static final int INSTALL_LOCATION_RESOURCE_ID = 16843447;
    public static final int IS_SPLIT_REQUIRED_RESOURCE_ID = 16844177;
    public static final int THEME_RESOURCE_ID = 0x1010000;
    public static final int DEVELOPMENT_SDK_VERSION = 10000;
    public static final String META_DATA_KEY_FUSED_MODULE_NAMES = "com.android.dynamic.apk.fused.modules";
    public static final String META_DATA_KEY_SPLITS_REQUIRED = "com.android.vending.splits.required";

    public abstract XmlProtoNode getManifestRoot();

    abstract Version getBundleToolVersion();

    XmlProtoElement getManifestElement() {
        return (XmlProtoElement)this.getManifestRoot().getElement();
    }

    public Optional<ManifestDeliveryElement> getManifestDeliveryElement() {
        return ManifestDeliveryElement.fromManifestElement(this.getManifestElement(), this.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE));
    }

    public Optional<ManifestDeliveryElement> getInstantManifestDeliveryElement() {
        return ManifestDeliveryElement.instantFromManifestElement(this.getManifestElement(), this.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE));
    }

    public static AndroidManifest create(XmlProtoNode manifestRoot, Version bundleToolVersion) {
        return new AutoValue_AndroidManifest(manifestRoot, bundleToolVersion);
    }

    public static AndroidManifest create(Resources.XmlNode manifestRoot, Version bundleToolVersion) {
        return AndroidManifest.create(new XmlProtoNode(manifestRoot), bundleToolVersion);
    }

    @VisibleForTesting
    public static AndroidManifest create(Resources.XmlNode manifestRoot) {
        return AndroidManifest.create(manifestRoot, BundleToolVersion.getCurrentVersion());
    }

    public static AndroidManifest createForConfigSplit(String packageName, Optional<Integer> versionCode, String splitId, String featureSplitId, Optional<Boolean> extractNativeLibs) {
        Preconditions.checkNotNull(splitId);
        Preconditions.checkArgument(!splitId.isEmpty(), "Split Id cannot be empty for config split.");
        Preconditions.checkNotNull(featureSplitId);
        Preconditions.checkNotNull(packageName);
        ManifestEditor editor = new ManifestEditor(AndroidManifest.createMinimalManifestTag(), BundleToolVersion.getCurrentVersion()).setPackage(packageName).setSplitId(splitId).setConfigForSplit(featureSplitId).setHasCode(false);
        versionCode.ifPresent(editor::setVersionCode);
        extractNativeLibs.ifPresent(editor::setExtractNativeLibsValue);
        return editor.save();
    }

    private static XmlProtoNode createMinimalManifestTag() {
        return XmlProtoNode.createElementNode(XmlProtoElementBuilder.create("manifest").addNamespaceDeclaration("android", ANDROID_NAMESPACE_URI).build());
    }

    public boolean getEffectiveApplicationDebuggable() {
        return this.getApplicationDebuggable().orElse(false);
    }

    @CheckReturnValue
    public AndroidManifest applyMutators(ImmutableList<ManifestMutator> manifestMutators) {
        ManifestEditor manifestEditor = this.toEditor();
        for (ManifestMutator manifestMutator : manifestMutators) {
            manifestMutator.accept(manifestEditor);
        }
        return manifestEditor.save();
    }

    public Optional<Boolean> getApplicationDebuggable() {
        return this.getManifestElement().getOptionalChildElement(APPLICATION_ELEMENT_NAME).flatMap(app -> app.getAndroidAttribute(0x101000F)).map(attr -> attr.getValueAsBoolean());
    }

    public ImmutableMap<String, XmlProtoElement> getActivitiesByName() {
        return Streams.stream(this.getManifestElement().getOptionalChildElement(APPLICATION_ELEMENT_NAME)).flatMap(app -> app.getChildrenElements(ACTIVITY_ELEMENT_NAME)).filter(activity -> activity.getAndroidAttribute(0x1010003).isPresent()).collect(ImmutableMap.toImmutableMap(activity -> ((XmlProtoAttribute)activity.getAndroidAttribute(0x1010003).get()).getValueAsString(), Function.identity()));
    }

    public Optional<Integer> getMinSdkVersion() {
        return this.getUsesSdkAttribute(16843276);
    }

    public int getEffectiveMinSdkVersion() {
        return this.getMinSdkVersion().orElse(1);
    }

    public Optional<Integer> getMaxSdkVersion() {
        return this.getUsesSdkAttribute(16843377);
    }

    public Range<Integer> getSdkRange() {
        Optional<Integer> maxSdkVersion = this.getMaxSdkVersion();
        if (maxSdkVersion.isPresent()) {
            return Range.closed(this.getEffectiveMinSdkVersion(), (Comparable)maxSdkVersion.get());
        }
        return Range.atLeast(this.getEffectiveMinSdkVersion());
    }

    public Optional<Integer> getTargetSandboxVersion() {
        return this.getManifestElement().getAndroidAttribute(16844108).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsDecimalInteger());
    }

    private Optional<Integer> getUsesSdkAttribute(int attributeResId) {
        return this.getManifestElement().getOptionalChildElement(USES_SDK_ELEMENT_NAME).flatMap(usesSdk -> usesSdk.getAndroidAttribute(attributeResId)).map(attribute -> AndroidManifest.isSdkCodename(attribute.getValueAsString()) ? 10000 : attribute.getValueAsDecimalInteger());
    }

    public ImmutableList<String> getSupportsGlTextures() {
        return this.getManifestElement().getChildrenElements(SUPPORTS_GL_TEXTURE_ELEMENT_NAME).map(supportsGlTextures -> (XmlProtoAttribute)supportsGlTextures.getAndroidAttribute(0x1010003).orElseThrow(() -> new ValidationException("<supports-gl-texture> element is missing the 'android:name' attribute."))).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString()).collect(ImmutableList.toImmutableList());
    }

    private static boolean isSdkCodename(String sdkVersion) {
        return !sdkVersion.isEmpty() && Range.closed(Character.valueOf('A'), Character.valueOf('Z')).contains(Character.valueOf(sdkVersion.charAt(0))) && (sdkVersion.length() == 1 || '.' == sdkVersion.charAt(1));
    }

    public boolean hasApplicationElement() {
        return this.getManifestElement().getOptionalChildElement(APPLICATION_ELEMENT_NAME).isPresent();
    }

    public Optional<Boolean> getHasCode() {
        return this.getManifestElement().getOptionalChildElement(APPLICATION_ELEMENT_NAME).flatMap(application -> application.getAndroidAttribute(0x101000C)).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsBoolean());
    }

    public boolean getEffectiveHasCode() {
        return this.getHasCode().orElse(true);
    }

    public Optional<Boolean> getIsFeatureSplit() {
        return this.getManifestElement().getAndroidAttribute(16844123).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsBoolean());
    }

    private static BundleModule.ModuleType getModuleTypeFromAttributeValue(String value) {
        switch (value) {
            case "feature": {
                return BundleModule.ModuleType.FEATURE_MODULE;
            }
            case "asset-pack": {
                return BundleModule.ModuleType.ASSET_MODULE;
            }
        }
        throw ValidationException.builder().withMessage("Found invalid type attribute %s for <module> element.", value).build();
    }

    public Optional<BundleModule.ModuleType> getOptionalModuleType() {
        Optional<String> typeAttributeValue = this.getManifestElement().getOptionalChildElement(DISTRIBUTION_NAMESPACE_URI, "module").flatMap(module -> module.getAttribute(DISTRIBUTION_NAMESPACE_URI, "type")).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString());
        return typeAttributeValue.map(AndroidManifest::getModuleTypeFromAttributeValue);
    }

    public BundleModule.ModuleType getModuleType() {
        return this.getOptionalModuleType().orElse(BundleModule.ModuleType.FEATURE_MODULE);
    }

    public Optional<Boolean> getIsModuleIncludedInFusing() {
        return this.getManifestElement().getOptionalChildElement(DISTRIBUTION_NAMESPACE_URI, "module").flatMap(module -> module.getOptionalChildElement(DISTRIBUTION_NAMESPACE_URI, "fusing")).map(fusing -> {
            if (VersionGuardedFeature.NAMESPACE_ON_INCLUDE_ATTRIBUTE_REQUIRED.enabledForVersion(this.getBundleToolVersion())) {
                return (XmlProtoAttribute)fusing.getAttribute(DISTRIBUTION_NAMESPACE_URI, "include").orElseThrow(() -> new ManifestFusingException.FusingMissingIncludeAttribute(this.getSplitId()));
            }
            return (XmlProtoAttribute)fusing.getAttributeIgnoringNamespace("include").orElseThrow(() -> new ManifestFusingException.FusingMissingIncludeAttribute(this.getSplitId()));
        }).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsBoolean());
    }

    public Optional<String> getConfigForSplit() {
        return this.getManifestElement().getAttribute("configForSplit").map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString());
    }

    public String getPackageName() {
        return ((XmlProtoAttribute)this.getManifestElement().getAttribute("package").orElseThrow(() -> new ValidationException("Package name not found in the manifest."))).getValueAsString();
    }

    public Optional<Integer> getVersionCode() {
        return this.getManifestElement().getAndroidAttribute(16843291).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsDecimalInteger());
    }

    public Optional<String> getSplitId() {
        return this.getManifestElement().getAttribute("split").map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString());
    }

    public Optional<Integer> getTitleRefId() {
        return this.getManifestElement().getOptionalChildElement(DISTRIBUTION_NAMESPACE_URI, "module").flatMap(module -> module.getAttribute(DISTRIBUTION_NAMESPACE_URI, "title")).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsRefId());
    }

    public ImmutableList<String> getUsesSplits() {
        return this.getManifestElement().getChildrenElements("uses-split").map(usesSplit -> (XmlProtoAttribute)usesSplit.getAndroidAttribute(0x1010003).orElseThrow(() -> new ValidationException("<uses-split> element is missing the 'android:name' attribute."))).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString()).collect(ImmutableList.toImmutableList());
    }

    public Optional<XmlProtoAttribute> getOnDemandAttribute() {
        return this.getManifestElement().getOptionalChildElement(DISTRIBUTION_NAMESPACE_URI, "module").flatMap(module -> {
            if (VersionGuardedFeature.NAMESPACE_ON_INCLUDE_ATTRIBUTE_REQUIRED.enabledForVersion(this.getBundleToolVersion())) {
                return module.getAttribute(DISTRIBUTION_NAMESPACE_URI, "onDemand");
            }
            return module.getAttributeIgnoringNamespace("onDemand");
        });
    }

    public boolean isDeliveryTypeDeclared() {
        if (this.getManifestDeliveryElement().isPresent()) {
            return this.getManifestDeliveryElement().get().isWellFormed();
        }
        return this.getOnDemandAttribute().isPresent();
    }

    public Optional<Boolean> isInstantModule() {
        if (this.getInstantManifestDeliveryElement().isPresent()) {
            if (!this.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)) {
                throw ValidationException.builder().withMessage("Instant-delivery element is only supported for asset packs.").build();
            }
            return this.getInstantManifestDeliveryElement().map(ManifestDeliveryElement::isWellFormed);
        }
        return this.getInstantAttribute();
    }

    public Optional<Boolean> getInstantAttribute() {
        return this.getManifestElement().getOptionalChildElement(DISTRIBUTION_NAMESPACE_URI, "module").flatMap(module -> module.getAttribute(DISTRIBUTION_NAMESPACE_URI, "instant")).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsBoolean());
    }

    public Optional<Boolean> getExtractNativeLibsValue() {
        return this.getManifestElement().getOptionalChildElement(APPLICATION_ELEMENT_NAME).flatMap(app -> app.getAndroidAttribute(16844010)).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsBoolean());
    }

    public Optional<String> getInstallLocationValue() {
        return this.getManifestElement().getAndroidAttribute(16843447).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString());
    }

    public boolean hasExplicitlyDefinedNativeActivities() {
        return Streams.stream(this.getManifestElement().getOptionalChildElement(APPLICATION_ELEMENT_NAME)).flatMap(app -> app.getChildrenElements(ACTIVITY_ELEMENT_NAME)).flatMap(activity -> activity.getChildrenElements(META_DATA_ELEMENT_NAME)).anyMatch(meta -> meta.getAndroidAttribute(0x1010003).filter(name -> NATIVE_ACTIVITY_LIB_NAME.equals(name.getValueAsString())).isPresent());
    }

    public ImmutableList<String> getFusedModuleNames() {
        return this.getMetadataValue(META_DATA_KEY_FUSED_MODULE_NAMES).map(rawValue -> ImmutableList.copyOf(COMMA_SPLITTER.split((CharSequence)rawValue))).orElse(ImmutableList.of());
    }

    public Optional<String> getMetadataValue(String metadataName) {
        return this.getMetadataAttributeWithName(metadataName).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString());
    }

    public Optional<Integer> getMetadataValueAsInteger(String metadataName) {
        return this.getMetadataAttributeWithName(metadataName).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsDecimalInteger());
    }

    private Optional<XmlProtoAttribute> getMetadataAttributeWithName(String metadataName) {
        return this.getMetadataElement(metadataName).map(metadataElement -> (XmlProtoAttribute)metadataElement.getAndroidAttribute(16842788).orElseThrow(() -> ValidationException.builder().withMessage("Missing expected attribute 'android:value' for <meta-data> element '%s'.", metadataName).build()));
    }

    public Optional<Integer> getMetadataResourceId(String metadataName) {
        return this.getMetadataElement(metadataName).map(metadataElement -> (XmlProtoAttribute)metadataElement.getAndroidAttribute(16842789).orElseThrow(() -> ValidationException.builder().withMessage("Missing expected attribute 'android:resource' for <meta-data> element '%s'.", metadataName).build())).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsRefId());
    }

    private Optional<XmlProtoElement> getMetadataElement(String name) {
        ImmutableList metadataElements = this.getMetadataElements().filter(metadataElement -> metadataElement.getAndroidAttribute(0x1010003).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString()).orElse(NO_NAMESPACE_URI).equals(name)).collect(ImmutableList.toImmutableList());
        switch (metadataElements.size()) {
            case 0: {
                return Optional.empty();
            }
            case 1: {
                return Optional.of(metadataElements.get(0));
            }
        }
        throw ValidationException.builder().withMessage("Found multiple <meta-data> elements for key '%s', expected at most one.", name).build();
    }

    private Stream<XmlProtoElement> getMetadataElements() {
        return this.getManifestElement().getOptionalChildElement(APPLICATION_ELEMENT_NAME).map(applicationElement -> applicationElement.getChildrenElements(META_DATA_ELEMENT_NAME)).orElse(Stream.of(new XmlProtoElement[0]));
    }

    public ManifestEditor toEditor() {
        return new ManifestEditor(this.getManifestRoot(), this.getBundleToolVersion());
    }
}

