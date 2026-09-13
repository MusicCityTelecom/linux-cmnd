/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.aapt.Resources;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MoreCollectors;
import java.util.Collection;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class ResourcesUtils {
    public static final ImmutableBiMap<String, Targeting.ScreenDensity.DensityAlias> SCREEN_DENSITY_TO_PROTO_VALUE_MAP = ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)ImmutableBiMap.builder().put("nodpi", Targeting.ScreenDensity.DensityAlias.NODPI)).put("default", Targeting.ScreenDensity.DensityAlias.DENSITY_UNSPECIFIED)).put("ldpi", Targeting.ScreenDensity.DensityAlias.LDPI)).put("mdpi", Targeting.ScreenDensity.DensityAlias.MDPI)).put("tvdpi", Targeting.ScreenDensity.DensityAlias.TVDPI)).put("hdpi", Targeting.ScreenDensity.DensityAlias.HDPI)).put("xhdpi", Targeting.ScreenDensity.DensityAlias.XHDPI)).put("xxhdpi", Targeting.ScreenDensity.DensityAlias.XXHDPI)).put("xxxhdpi", Targeting.ScreenDensity.DensityAlias.XXXHDPI)).build();
    public static final int DEFAULT_DENSITY_VALUE = 0;
    public static final int LDPI_VALUE = 120;
    public static final int MDPI_VALUE = 160;
    public static final int TVDPI_VALUE = 213;
    public static final int HDPI_VALUE = 240;
    public static final int XHDPI_VALUE = 320;
    public static final int XXHDPI_VALUE = 480;
    public static final int XXXHDPI_VALUE = 640;
    public static final int ANY_DENSITY_VALUE = 65534;
    public static final int NONE_DENSITY_VALUE = 65535;
    public static final String MIPMAP_TYPE = "mipmap";
    public static final ImmutableMap<Targeting.ScreenDensity.DensityAlias, Integer> DENSITY_ALIAS_TO_DPI_MAP = ImmutableMap.builder().put(Targeting.ScreenDensity.DensityAlias.NODPI, 65535).put(Targeting.ScreenDensity.DensityAlias.DENSITY_UNSPECIFIED, 0).put(Targeting.ScreenDensity.DensityAlias.LDPI, 120).put(Targeting.ScreenDensity.DensityAlias.MDPI, 160).put(Targeting.ScreenDensity.DensityAlias.TVDPI, 213).put(Targeting.ScreenDensity.DensityAlias.HDPI, 240).put(Targeting.ScreenDensity.DensityAlias.XHDPI, 320).put(Targeting.ScreenDensity.DensityAlias.XXHDPI, 480).put(Targeting.ScreenDensity.DensityAlias.XXXHDPI, 640).build();

    public static Resources.ResourceTable filterResourceTable(Resources.ResourceTable originalTable, Predicate<ResourceTableEntry> removeEntryPredicate, Function<ResourceTableEntry, Resources.Entry> configValuesFilterFn) {
        Resources.ResourceTable.Builder filteredTable = originalTable.toBuilder();
        for (int pkgIdx = filteredTable.getPackageCount() - 1; pkgIdx >= 0; --pkgIdx) {
            Resources.Package.Builder pkg = filteredTable.getPackageBuilder(pkgIdx);
            for (int typeIdx = pkg.getTypeCount() - 1; typeIdx >= 0; --typeIdx) {
                Resources.Type.Builder type = pkg.getTypeBuilder(typeIdx);
                for (int entryIdx = type.getEntryCount() - 1; entryIdx >= 0; --entryIdx) {
                    ResourceTableEntry entry = ResourceTableEntry.create(filteredTable.getPackage(pkgIdx), pkg.getType(typeIdx), type.getEntry(entryIdx));
                    if (removeEntryPredicate.test(entry)) {
                        type.removeEntry(entryIdx);
                        continue;
                    }
                    Resources.Entry filteredEntry = configValuesFilterFn.apply(entry);
                    if (filteredEntry.getConfigValueCount() > 0) {
                        type.setEntry(entryIdx, filteredEntry);
                        continue;
                    }
                    type.removeEntry(entryIdx);
                }
                if (type.getEntryCount() != 0) continue;
                pkg.removeType(typeIdx);
            }
            if (pkg.getTypeCount() != 0) continue;
            filteredTable.removePackage(pkgIdx);
        }
        return filteredTable.build();
    }

    public static Stream<ResourceTableEntry> entries(Resources.ResourceTable resourceTable) {
        Stream.Builder<ResourceTableEntry> stream = Stream.builder();
        for (Resources.Package pkg : resourceTable.getPackageList()) {
            for (Resources.Type type : pkg.getTypeList()) {
                for (Resources.Entry entry : type.getEntryList()) {
                    stream.add(ResourceTableEntry.create(pkg, type, entry));
                }
            }
        }
        return stream.build();
    }

    public static Stream<Resources.ConfigValue> configValues(Resources.ResourceTable resourceTable) {
        return ResourcesUtils.entries(resourceTable).map(ResourceTableEntry::getEntry).map(Resources.Entry::getConfigValueList).flatMap(Collection::stream);
    }

    public static ImmutableSet<ZipPath> getAllFileReferences(Resources.ResourceTable resourceTable) {
        return ResourcesUtils.configValues(resourceTable).filter(configValue -> configValue.getValue().getItem().hasFile()).map(configValue -> ZipPath.create(configValue.getValue().getItem().getFile().getPath())).collect(ImmutableSet.toImmutableSet());
    }

    public static Integer convertToDpi(Targeting.ScreenDensity screenDensity) {
        switch (screenDensity.getDensityOneofCase()) {
            case DENSITY_ALIAS: {
                return DENSITY_ALIAS_TO_DPI_MAP.get(screenDensity.getDensityAlias());
            }
            case DENSITY_DPI: {
                return screenDensity.getDensityDpi();
            }
            case DENSITYONEOF_NOT_SET: {
                throw new IllegalArgumentException("ScreenDensity proto is not set properly.");
            }
        }
        throw new IllegalArgumentException("ScreenDensity value is not recognized.");
    }

    public static String convertLocaleToLanguage(String locale) {
        return Locale.forLanguageTag(locale).getLanguage();
    }

    public static Optional<Resources.Entry> lookupEntryByResourceId(Resources.ResourceTable resourceTable, int resourceId) {
        return ResourcesUtils.entries(resourceTable).filter(entry -> entry.getResourceId().getFullResourceId() == resourceId).map(ResourceTableEntry::getEntry).collect(MoreCollectors.toOptional());
    }

    public static ImmutableSet<String> getAllLanguages(Resources.ResourceTable table) {
        return ResourcesUtils.configValues(table).map(configValue -> configValue.getConfig().getLocale()).map(ResourcesUtils::convertLocaleToLanguage).collect(ImmutableSet.toImmutableSet());
    }

    public static Targeting.ScreenDensity.DensityAlias getLowestDensity(ImmutableCollection<Targeting.ScreenDensity.DensityAlias> densities) {
        return densities.stream().min(Comparator.comparing(DENSITY_ALIAS_TO_DPI_MAP::get)).get();
    }

    private ResourcesUtils() {
    }
}

