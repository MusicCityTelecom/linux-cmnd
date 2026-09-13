/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.GeneratedApks;
import com.android.tools.build.bundletool.model.InputStreamSuppliers;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.model.ResourceInjector;
import com.android.tools.build.bundletool.model.SplitsProtoXmlBuilder;
import com.android.tools.build.bundletool.model.VariantKey;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class SplitsXmlInjector {
    private static final String XML_TYPE_NAME = "xml";
    private static final String XML_PATH_PATTERN = "res/xml/splits%s.xml";
    private static final String METADATA_KEY = "com.android.vending.splits";

    public GeneratedApks process(GeneratedApks generatedApks) {
        return GeneratedApks.fromModuleSplits(((ImmutableMap)generatedApks.getAllApksGroupedByOrderedVariants().asMap()).entrySet().stream().map(keySplit -> {
            switch (((VariantKey)keySplit.getKey()).getSplitType()) {
                case SYSTEM: 
                case SPLIT: {
                    return this.processSplitApkVariant((Collection)keySplit.getValue());
                }
                case STANDALONE: {
                    return ((Collection)keySplit.getValue()).stream().map(this::processStandaloneVariant).collect(ImmutableList.toImmutableList());
                }
                case INSTANT: {
                    return (Collection)keySplit.getValue();
                }
                case ASSET_SLICE: {
                    throw new IllegalStateException("Unexpected Asset Slice inside variant.");
                }
            }
            throw new IllegalStateException(String.format("Unknown split type %s", new Object[]{((VariantKey)keySplit.getKey()).getSplitType()}));
        }).flatMap(Collection::stream).collect(ImmutableList.toImmutableList()));
    }

    private ModuleSplit processStandaloneVariant(ModuleSplit split) {
        if (!split.getResourceTable().isPresent()) {
            return split;
        }
        SplitsProtoXmlBuilder splitsProtoXmlBuilder = new SplitsProtoXmlBuilder();
        ResourcesUtils.getAllLanguages(split.getResourceTable().get()).stream().filter(language -> !language.isEmpty()).forEach(language -> splitsProtoXmlBuilder.addLanguageMapping(split.getModuleName(), (String)language, ""));
        return this.injectSplitsXml(split, splitsProtoXmlBuilder.build());
    }

    private ImmutableList<ModuleSplit> processSplitApkVariant(Collection<ModuleSplit> splits) {
        SplitsProtoXmlBuilder splitsProtoXmlBuilder = new SplitsProtoXmlBuilder();
        for (ModuleSplit split : splits) {
            String splitId = split.getAndroidManifest().getSplitId().orElse("");
            for (String language : split.getApkTargeting().getLanguageTargeting().getValueList()) {
                splitsProtoXmlBuilder.addLanguageMapping(split.getModuleName(), language, splitId);
            }
        }
        Resources.XmlNode splitsXmlContent = splitsProtoXmlBuilder.build();
        ImmutableList.Builder result = new ImmutableList.Builder();
        for (ModuleSplit split : splits) {
            if (split.isMasterSplit() && split.isBaseModuleSplit()) {
                result.add(this.injectSplitsXml(split, splitsXmlContent));
                continue;
            }
            result.add(split);
        }
        return result.build();
    }

    private ModuleSplit injectSplitsXml(ModuleSplit split, Resources.XmlNode xmlNode) {
        ZipPath resourcePath = SplitsXmlInjector.getUniqueResourcePath(split);
        ResourceInjector resourceInjector = ResourceInjector.fromModuleSplit(split);
        ResourceId resourceId = resourceInjector.addResource(XML_TYPE_NAME, SplitsXmlInjector.createXmlEntry(resourcePath));
        return split.toBuilder().setResourceTable(resourceInjector.build()).setEntries((List<ModuleEntry>)((Object)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(split.getEntries())).add(ModuleEntry.builder().setPath(resourcePath).setContentSupplier(InputStreamSuppliers.fromBytes(xmlNode.toByteArray())).build())).build())).setAndroidManifest(split.getAndroidManifest().toEditor().addMetaDataResourceId(METADATA_KEY, resourceId.getFullResourceId()).save()).build();
    }

    private static Resources.Entry createXmlEntry(ZipPath resourcePath) {
        return Resources.Entry.newBuilder().setName(resourcePath.getFileName().toString().split("\\.", 2)[0]).addConfigValue(Resources.ConfigValue.newBuilder().setValue(Resources.Value.newBuilder().setItem(Resources.Item.newBuilder().setFile(Resources.FileReference.newBuilder().setPath(resourcePath.toString()).setType(Resources.FileReference.Type.PROTO_XML))))).build();
    }

    private static ZipPath getUniqueResourcePath(ModuleSplit split) {
        return Stream.iterate(0, i2 -> i2 + 1).map(number -> ZipPath.create(String.format(XML_PATH_PATTERN, number))).filter(path -> !split.findEntry((ZipPath)path).isPresent()).findFirst().get();
    }
}

