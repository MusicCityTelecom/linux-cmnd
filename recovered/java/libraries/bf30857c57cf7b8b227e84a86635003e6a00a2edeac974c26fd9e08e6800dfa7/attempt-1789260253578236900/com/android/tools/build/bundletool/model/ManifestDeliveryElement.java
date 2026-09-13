/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.AutoValue_ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.DeviceFeatureCondition;
import com.android.tools.build.bundletool.model.ModuleConditions;
import com.android.tools.build.bundletool.model.UserCountriesCondition;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ManifestDeliveryElement {
    private static final String VERSION_ATTRIBUTE_NAME = "version";
    private static final ImmutableList<String> KNOWN_DELIVERY_MODES = ImmutableList.of("install-time", "on-demand", "fast-follow");
    private static final ImmutableList<String> CONDITIONS_ALLOWED_ONLY_ONCE = ImmutableList.of("min-sdk", "max-sdk", "user-countries");

    abstract XmlProtoElement getDeliveryElement();

    abstract boolean isFastFollowAllowed();

    public boolean isWellFormed() {
        return this.hasOnDemandElement() || this.hasInstallTimeElement() || this.isFastFollowAllowed() && this.hasFastFollowElement();
    }

    public boolean hasModuleConditions() {
        return !this.getModuleConditions().isEmpty();
    }

    public boolean hasOnDemandElement() {
        return this.getDeliveryElement().getOptionalChildElement("http://schemas.android.com/apk/distribution", "on-demand").isPresent();
    }

    public boolean hasFastFollowElement() {
        return this.getDeliveryElement().getOptionalChildElement("http://schemas.android.com/apk/distribution", "fast-follow").isPresent();
    }

    public boolean hasInstallTimeElement() {
        return this.getDeliveryElement().getOptionalChildElement("http://schemas.android.com/apk/distribution", "install-time").isPresent();
    }

    public ModuleConditions getModuleConditions() {
        ImmutableList<XmlProtoElement> conditionElements = this.getModuleConditionElements();
        Map<String, Long> conditionCounts = conditionElements.stream().collect(Collectors.groupingBy(rec$ -> ((XmlProtoElement)rec$).getName(), Collectors.counting()));
        for (String string : CONDITIONS_ALLOWED_ONLY_ONCE) {
            if (conditionCounts.getOrDefault(string, 0L) <= 1L) continue;
            throw ValidationException.builder().withMessage("Multiple '<dist:%s>' conditions are not supported.", string).build();
        }
        ModuleConditions.Builder moduleConditions = ModuleConditions.builder();
        block13: for (XmlProtoElement conditionElement : conditionElements) {
            if (!conditionElement.getNamespaceUri().equals("http://schemas.android.com/apk/distribution")) {
                throw ValidationException.builder().withMessage("Invalid namespace found in the module condition element. Expected '%s'; found '%s'.", "http://schemas.android.com/apk/distribution", conditionElement.getNamespaceUri()).build();
            }
            switch (conditionElement.getName()) {
                case "device-feature": {
                    moduleConditions.addDeviceFeatureCondition(this.parseDeviceFeatureCondition(conditionElement));
                    continue block13;
                }
                case "min-sdk": {
                    moduleConditions.setMinSdkVersion(ManifestDeliveryElement.parseMinSdkVersionCondition(conditionElement));
                    continue block13;
                }
                case "max-sdk": {
                    moduleConditions.setMaxSdkVersion(ManifestDeliveryElement.parseMaxSdkVersionCondition(conditionElement));
                    continue block13;
                }
                case "user-countries": {
                    moduleConditions.setUserCountriesCondition(this.parseUserCountriesCondition(conditionElement));
                    continue block13;
                }
            }
            throw new ValidationException(String.format("Unrecognized module condition: '%s'", conditionElement.getName()));
        }
        ModuleConditions moduleConditions2 = moduleConditions.build();
        if (moduleConditions2.getMinSdkVersion().isPresent() && moduleConditions2.getMaxSdkVersion().isPresent() && moduleConditions2.getMinSdkVersion().get() > moduleConditions2.getMaxSdkVersion().get()) {
            throw ValidationException.builder().withMessage("Illegal SDK-based conditional module targeting (min SDK must be less than or equal to max SD). Provided min and max values, respectively, are %s and %s", moduleConditions2.getMinSdkVersion(), moduleConditions2.getMaxSdkVersion()).build();
        }
        return moduleConditions2;
    }

    private UserCountriesCondition parseUserCountriesCondition(XmlProtoElement conditionElement) {
        ImmutableList.Builder countryCodes = ImmutableList.builder();
        for (XmlProtoElement countryElement : conditionElement.getChildrenElements().collect(ImmutableList.toImmutableList())) {
            if (!countryElement.getName().equals("country")) {
                throw ValidationException.builder().withMessage("Expected only <dist:country> elements inside <dist:user-countries>, but found %s", ManifestDeliveryElement.printElement(conditionElement)).build();
            }
            countryCodes.add(countryElement.getAttribute("http://schemas.android.com/apk/distribution", "code").map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsString()).map(String::toUpperCase).orElseThrow(() -> ValidationException.builder().withMessage("<dist:country> element is expected to have 'dist:code' attribute but found none.").build()));
        }
        boolean exclude = conditionElement.getAttribute("http://schemas.android.com/apk/distribution", "exclude").map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsBoolean()).orElse(false);
        return UserCountriesCondition.create((ImmutableList<String>)countryCodes.build(), exclude);
    }

    private static void validateDeliveryElement(XmlProtoElement deliveryElement, boolean isFastFollowAllowed) {
        ManifestDeliveryElement.validateDeliveryElementChildren(deliveryElement, isFastFollowAllowed);
        ManifestDeliveryElement.validateInstallTimeElement(deliveryElement.getOptionalChildElement("http://schemas.android.com/apk/distribution", "install-time"));
        ManifestDeliveryElement.validateOnDemandElement(deliveryElement.getOptionalChildElement("http://schemas.android.com/apk/distribution", "on-demand"));
        if (isFastFollowAllowed) {
            ManifestDeliveryElement.validateFastFollowElement(deliveryElement.getOptionalChildElement("http://schemas.android.com/apk/distribution", "fast-follow"));
        }
    }

    private static void validateDeliveryElementChildren(XmlProtoElement deliveryElement, boolean isFastFollowAllowed) {
        Optional offendingElement;
        LinkedHashSet<String> allowedDeliveryModes = new LinkedHashSet<String>(KNOWN_DELIVERY_MODES);
        if (!isFastFollowAllowed) {
            allowedDeliveryModes.remove("fast-follow");
        }
        if ((offendingElement = deliveryElement.getChildrenElements(child -> !child.getNamespaceUri().equals("http://schemas.android.com/apk/distribution") || !allowedDeliveryModes.contains(child.getName())).findAny()).isPresent()) {
            throw ValidationException.builder().withMessage("Expected <dist:delivery> element to contain only %s elements but found: %s", allowedDeliveryModes.stream().map(name -> String.format("<dist:%s>", name)).collect(Collectors.joining(", ")), ManifestDeliveryElement.printElement((XmlProtoElement)offendingElement.get())).build();
        }
    }

    private static void validateInstallTimeElement(Optional<XmlProtoElement> installTimeElement) {
        Optional offendingElement = installTimeElement.flatMap(installTime -> installTime.getChildrenElements(child -> !child.getNamespaceUri().equals("http://schemas.android.com/apk/distribution") || !child.getName().equals("conditions")).findAny());
        if (offendingElement.isPresent()) {
            throw ValidationException.builder().withMessage("Expected <dist:install-time> element to contain only <dist:conditions> element but found: %s.", ManifestDeliveryElement.printElement((XmlProtoElement)offendingElement.get())).build();
        }
    }

    private static void validateOnDemandElement(Optional<XmlProtoElement> onDemandElement) {
        Optional offendingChild = onDemandElement.flatMap(element -> element.getChildrenElements().findAny());
        if (offendingChild.isPresent()) {
            throw ValidationException.builder().withMessage("Expected <dist:on-demand> element to have no child elements but found: %s.", ManifestDeliveryElement.printElement((XmlProtoElement)offendingChild.get())).build();
        }
    }

    private static void validateFastFollowElement(Optional<XmlProtoElement> fastFollowElement) {
        Optional offendingChild = fastFollowElement.flatMap(element -> element.getChildrenElements().findAny());
        if (offendingChild.isPresent()) {
            throw ValidationException.builder().withMessage("Expected <dist:fast-follow> element to have no child elements but found: %s.", ManifestDeliveryElement.printElement((XmlProtoElement)offendingChild.get())).build();
        }
    }

    private ImmutableList<XmlProtoElement> getModuleConditionElements() {
        Optional installTimeElement = this.getDeliveryElement().getOptionalChildElement("http://schemas.android.com/apk/distribution", "install-time");
        return installTimeElement.flatMap(installTime -> installTime.getOptionalChildElement("http://schemas.android.com/apk/distribution", "conditions")).map(conditions -> conditions.getChildrenElements().collect(ImmutableList.toImmutableList())).orElse(ImmutableList.of());
    }

    private DeviceFeatureCondition parseDeviceFeatureCondition(XmlProtoElement conditionElement) {
        return DeviceFeatureCondition.create(((XmlProtoAttribute)conditionElement.getAttribute("http://schemas.android.com/apk/distribution", "name").orElseThrow(() -> new ValidationException("Missing required 'dist:name' attribute in the 'device-feature' condition element."))).getValueAsString(), conditionElement.getAttribute("http://schemas.android.com/apk/distribution", VERSION_ATTRIBUTE_NAME).map(rec$ -> ((XmlProtoAttribute)rec$).getValueAsInteger()));
    }

    private static int parseMinSdkVersionCondition(XmlProtoElement conditionElement) {
        return ((XmlProtoAttribute)conditionElement.getAttribute("http://schemas.android.com/apk/distribution", "value").orElseThrow(() -> new ValidationException("Missing required 'dist:value' attribute in the 'min-sdk' condition element."))).getValueAsDecimalInteger();
    }

    private static int parseMaxSdkVersionCondition(XmlProtoElement conditionElement) {
        return ((XmlProtoAttribute)conditionElement.getAttribute("http://schemas.android.com/apk/distribution", "value").orElseThrow(() -> new ValidationException("Missing required 'dist:value' attribute in the 'max-sdk' condition element."))).getValueAsDecimalInteger();
    }

    private static String printElement(XmlProtoElement element) {
        if (element.getNamespaceUri().isEmpty()) {
            return String.format("'%s' with namespace not provided", element.getName());
        }
        return String.format("'%s' with namespace URI: '%s'", element.getName(), element.getNamespaceUri());
    }

    public static Optional<ManifestDeliveryElement> fromManifestElement(XmlProtoElement manifestElement, boolean isFastFollowAllowed) {
        return ManifestDeliveryElement.fromManifestElement(manifestElement, "delivery", isFastFollowAllowed);
    }

    private static Optional<ManifestDeliveryElement> fromManifestElement(XmlProtoElement manifestElement, String deliveryTag, boolean isFastFollowAllowed) {
        return manifestElement.getOptionalChildElement("http://schemas.android.com/apk/distribution", "module").flatMap(elem -> elem.getOptionalChildElement("http://schemas.android.com/apk/distribution", deliveryTag)).map(elem -> {
            ManifestDeliveryElement.validateDeliveryElement(elem, isFastFollowAllowed);
            return new AutoValue_ManifestDeliveryElement((XmlProtoElement)elem, isFastFollowAllowed);
        });
    }

    public static Optional<ManifestDeliveryElement> instantFromManifestElement(XmlProtoElement manifestElement, boolean isFastFollowAllowed) {
        return ManifestDeliveryElement.fromManifestElement(manifestElement, "instant-delivery", isFastFollowAllowed);
    }

    @VisibleForTesting
    static Optional<ManifestDeliveryElement> fromManifestRootNode(Resources.XmlNode xmlNode, boolean isFastFollowAllowed) {
        return ManifestDeliveryElement.fromManifestElement((XmlProtoElement)new XmlProtoNode(xmlNode).getElement(), isFastFollowAllowed);
    }

    @VisibleForTesting
    static Optional<ManifestDeliveryElement> instantFromManifestRootNode(Resources.XmlNode xmlNode, boolean isFastFollowAllowed) {
        return ManifestDeliveryElement.instantFromManifestElement((XmlProtoElement)new XmlProtoNode(xmlNode).getElement(), isFastFollowAllowed);
    }
}

