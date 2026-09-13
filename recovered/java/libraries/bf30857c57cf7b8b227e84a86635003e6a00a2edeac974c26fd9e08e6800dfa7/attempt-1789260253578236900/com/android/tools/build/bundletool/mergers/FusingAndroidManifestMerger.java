/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.tools.build.bundletool.mergers.AndroidManifestMerger;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FusingAndroidManifestMerger
implements AndroidManifestMerger {
    @Override
    public AndroidManifest merge(SetMultimap<BundleModuleName, AndroidManifest> manifests) {
        if (!manifests.containsKey(BundleModuleName.BASE_MODULE_NAME)) {
            throw CommandExecutionException.builder().withMessage("Expected to have base module.").build();
        }
        return FusingAndroidManifestMerger.merge(FusingAndroidManifestMerger.ensureOneManifestPerModule(manifests));
    }

    private static AndroidManifest merge(Map<BundleModuleName, AndroidManifest> manifests) {
        AndroidManifest baseManifest = manifests.get(BundleModuleName.BASE_MODULE_NAME);
        List featureManifests = manifests.entrySet().stream().filter(entry -> !BundleModuleName.BASE_MODULE_NAME.equals(entry.getKey())).map(Map.Entry::getValue).collect(ImmutableList.toImmutableList());
        return FusingAndroidManifestMerger.mergeFeatureActivitiesToBase(baseManifest, FusingAndroidManifestMerger.extractActivities(featureManifests));
    }

    private static AndroidManifest mergeFeatureActivitiesToBase(AndroidManifest base, Map<String, XmlProtoElement> featureActivities) {
        return base.toEditor().addOrReplaceActivities(featureActivities).save();
    }

    private static ImmutableMap<BundleModuleName, AndroidManifest> ensureOneManifestPerModule(SetMultimap<BundleModuleName, AndroidManifest> manifests) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (BundleModuleName moduleName : manifests.keys()) {
            Collection moduleManifests = manifests.get((Object)moduleName);
            if (moduleManifests.size() != 1) {
                throw CommandExecutionException.builder().withMessage("Expected exactly one %s module manifest, but found %d.", moduleName.getName(), moduleManifests.size()).build();
            }
            builder.put(moduleName, Iterables.getOnlyElement(moduleManifests));
        }
        return builder.build();
    }

    private static ImmutableMap<String, XmlProtoElement> extractActivities(List<AndroidManifest> manifests) {
        return manifests.stream().flatMap(manifest -> manifest.getActivitiesByName().entrySet().stream()).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

