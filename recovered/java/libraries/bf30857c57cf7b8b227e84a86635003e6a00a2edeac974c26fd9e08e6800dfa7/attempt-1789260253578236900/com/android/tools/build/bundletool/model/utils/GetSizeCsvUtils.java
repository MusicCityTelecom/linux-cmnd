/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.tools.build.bundletool.model.ConfigurationSizes;
import com.android.tools.build.bundletool.model.GetSizeRequest;
import com.android.tools.build.bundletool.model.SizeConfiguration;
import com.android.tools.build.bundletool.model.utils.CsvFormatter;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class GetSizeCsvUtils {
    private static final Ordering<GetSizeRequest.Dimension> DIMENSIONS_COMPARATOR = Ordering.explicit(GetSizeRequest.Dimension.SDK, GetSizeRequest.Dimension.ABI, GetSizeRequest.Dimension.SCREEN_DENSITY, GetSizeRequest.Dimension.LANGUAGE);

    public static String getSizeTotalOutputInCsv(ConfigurationSizes configurationSizes, ImmutableSet<GetSizeRequest.Dimension> dimensions) {
        Preconditions.checkState(((ImmutableSet)configurationSizes.getMinSizeConfigurationMap().keySet()).equals(configurationSizes.getMaxSizeConfigurationMap().keySet()), "Min and Max maps should contains same keys.");
        CsvFormatter.Builder csvFormatter = CsvFormatter.builder();
        csvFormatter.setHeader(GetSizeCsvUtils.getSizeTotalCsvHeader(dimensions));
        for (SizeConfiguration sizeConfiguration : configurationSizes.getMinSizeConfigurationMap().keySet()) {
            csvFormatter.addRow(GetSizeCsvUtils.getSizeTotalCsvRow(dimensions, sizeConfiguration, configurationSizes.getMinSizeConfigurationMap().get(sizeConfiguration), configurationSizes.getMaxSizeConfigurationMap().get(sizeConfiguration)));
        }
        return csvFormatter.build().format();
    }

    private static ImmutableList<String> getSizeTotalCsvHeader(ImmutableSet<GetSizeRequest.Dimension> dimensions) {
        return Stream.concat(dimensions.stream().sorted(DIMENSIONS_COMPARATOR).map(Enum::name), Stream.of("MIN", "MAX")).collect(ImmutableList.toImmutableList());
    }

    private static ImmutableList<String> getSizeTotalCsvRow(ImmutableSet<GetSizeRequest.Dimension> dimensions, SizeConfiguration sizeConfiguration, long minSize, long maxSize) {
        ImmutableMap<GetSizeRequest.Dimension, Supplier<Optional>> dimensionToTextMap = ImmutableMap.of(GetSizeRequest.Dimension.ABI, sizeConfiguration::getAbi, GetSizeRequest.Dimension.SDK, sizeConfiguration::getSdkVersion, GetSizeRequest.Dimension.LANGUAGE, sizeConfiguration::getLocale, GetSizeRequest.Dimension.SCREEN_DENSITY, sizeConfiguration::getScreenDensity);
        return Stream.concat(dimensions.stream().sorted(DIMENSIONS_COMPARATOR).map(dimension -> ((Optional)((Supplier)dimensionToTextMap.get(dimension)).get()).orElse("")), Stream.of(String.valueOf(minSize), String.valueOf(maxSize))).collect(ImmutableList.toImmutableList());
    }

    private GetSizeCsvUtils() {
    }
}

