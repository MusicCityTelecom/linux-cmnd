/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.aapt.Resources;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.splitters.SplitterForOneTargetingDimension;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.function.Predicate;

public class LanguageResourcesSplitter
extends SplitterForOneTargetingDimension {
    private final Predicate<ResourceTableEntry> pinResourceToMaster;

    public LanguageResourcesSplitter() {
        this(Predicates.alwaysFalse());
    }

    public LanguageResourcesSplitter(Predicate<ResourceTableEntry> pinResourceToMaster) {
        this.pinResourceToMaster = pinResourceToMaster;
    }

    @Override
    public ImmutableCollection<ModuleSplit> splitInternal(ModuleSplit split) {
        if (!split.getResourceTable().isPresent()) {
            return ImmutableList.of(split);
        }
        Resources.ResourceTable resourceTable = split.getResourceTable().get();
        ImmutableMap<String, Resources.ResourceTable> byLanguage = this.groupByLanguage(resourceTable, LanguageResourcesSplitter.hasNonResourceEntries(split));
        ImmutableList.Builder result = new ImmutableList.Builder();
        for (String language : byLanguage.keySet()) {
            ModuleSplit moduleSplit = split.toBuilder().setEntries(LanguageResourcesSplitter.getEntriesForSplit(split.getEntries(), language, byLanguage.get(language))).setResourceTable(byLanguage.get(language)).setApkTargeting(language.isEmpty() ? split.getApkTargeting() : split.getApkTargeting().toBuilder().setLanguageTargeting(Targeting.LanguageTargeting.newBuilder().addValue(language)).build()).setMasterSplit(split.isMasterSplit() && language.isEmpty()).build();
            result.add(moduleSplit);
        }
        return result.build();
    }

    private static boolean hasNonResourceEntries(ModuleSplit split) {
        return split.getEntries().stream().anyMatch(entry -> !entry.getPath().startsWith(BundleModule.RESOURCES_DIRECTORY));
    }

    private static ImmutableList<ModuleEntry> getEntriesForSplit(ImmutableList<ModuleEntry> inputEntries, String language, Resources.ResourceTable resourceTable) {
        ImmutableList<ModuleEntry> entriesFromResourceTable = ModuleSplit.filterResourceEntries(inputEntries, resourceTable);
        if (language.isEmpty()) {
            return ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(entriesFromResourceTable)).addAll((Iterable)inputEntries.stream().filter(entry -> !entry.getPath().startsWith(BundleModule.RESOURCES_DIRECTORY)).collect(ImmutableList.toImmutableList()))).build();
        }
        return entriesFromResourceTable;
    }

    private ImmutableMap<String, Resources.ResourceTable> groupByLanguage(Resources.ResourceTable table, boolean hasNonResourceEntries) {
        ImmutableSet<String> languages = ResourcesUtils.getAllLanguages(table);
        ImmutableMap.Builder<String, Resources.ResourceTable> resourceTableByLanguage = new ImmutableMap.Builder<String, Resources.ResourceTable>();
        for (String language : languages) {
            Resources.ResourceTable languageResourceTable = this.filterByLanguage(table, language);
            if (languageResourceTable.equals(Resources.ResourceTable.getDefaultInstance())) continue;
            resourceTableByLanguage.put(language, languageResourceTable);
        }
        if (!languages.contains("")) {
            Resources.ResourceTable pinnedResources = ResourcesUtils.filterResourceTable(table, this.pinResourceToMaster.negate(), ResourceTableEntry::getEntry);
            if (hasNonResourceEntries || ResourcesUtils.entries(pinnedResources).count() > 0L) {
                resourceTableByLanguage.put("", pinnedResources);
            }
        }
        return resourceTableByLanguage.build();
    }

    private Resources.ResourceTable filterByLanguage(Resources.ResourceTable input, String language) {
        return ResourcesUtils.filterResourceTable(input, language.isEmpty() ? Predicates.alwaysFalse() : this.pinResourceToMaster, entry -> this.filterEntryForLanguage((ResourceTableEntry)entry, language));
    }

    private Resources.Entry filterEntryForLanguage(ResourceTableEntry initialEntry, String targetLanguage) {
        if (targetLanguage.isEmpty() && this.pinResourceToMaster.test(initialEntry)) {
            return initialEntry.getEntry();
        }
        Iterable<Resources.ConfigValue> filteredConfigValues = Iterables.filter(initialEntry.getEntry().getConfigValueList(), configValue -> ResourcesUtils.convertLocaleToLanguage(configValue.getConfig().getLocale()).equals(targetLanguage));
        return initialEntry.getEntry().toBuilder().clearConfigValue().addAllConfigValue(filteredConfigValues).build();
    }
}

