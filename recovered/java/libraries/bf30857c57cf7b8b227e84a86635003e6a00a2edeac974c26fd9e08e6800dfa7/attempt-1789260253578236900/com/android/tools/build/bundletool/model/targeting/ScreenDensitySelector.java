/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.aapt.Resources;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Booleans;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import javax.annotation.CheckReturnValue;

public final class ScreenDensitySelector {
    public ImmutableList<Resources.ConfigValue> selectAllMatchingConfigValues(ImmutableList<Resources.ConfigValue> values2, Targeting.ScreenDensity.DensityAlias forDensityAlias, Set<Targeting.ScreenDensity.DensityAlias> alternatives, Version bundleVersion) {
        Integer targetDpi = ResourcesUtils.DENSITY_ALIAS_TO_DPI_MAP.get(forDensityAlias);
        ImmutableSet<Integer> alternativeDpis = alternatives.stream().map(ResourcesUtils.DENSITY_ALIAS_TO_DPI_MAP::get).collect(ImmutableSet.toImmutableSet());
        return this.getReachableConfigValues(this.getDpiRange(targetDpi, alternativeDpis), values2, bundleVersion);
    }

    @CheckReturnValue
    private ImmutableList<Resources.ConfigValue> getReachableConfigValues(Range<Integer> deviceDpiRange, ImmutableList<Resources.ConfigValue> values2, Version bundleVersion) {
        Optional<Object> lowestResourceDpi = Optional.empty();
        Optional<Object> highestResourceDpi = Optional.empty();
        if (deviceDpiRange.hasLowerBound()) {
            lowestResourceDpi = Optional.of(this.selectBestConfigValue(values2, deviceDpiRange.lowerEndpoint(), bundleVersion).getConfig().getDensity());
        }
        if (deviceDpiRange.hasUpperBound()) {
            highestResourceDpi = Optional.of(this.selectBestConfigValue(values2, deviceDpiRange.upperEndpoint(), bundleVersion).getConfig().getDensity());
        }
        Range<Object> effectiveDpiRange = deviceDpiRange.equals(Range.all()) ? Range.all() : (!lowestResourceDpi.isPresent() ? Range.atMost((Comparable)highestResourceDpi.get()) : (!highestResourceDpi.isPresent() ? Range.atLeast((Comparable)lowestResourceDpi.get()) : Range.closed((Comparable)lowestResourceDpi.get(), (Comparable)highestResourceDpi.get())));
        return values2.stream().filter(configValue -> effectiveDpiRange.contains(configValue.getConfig().getDensity())).collect(ImmutableList.toImmutableList());
    }

    private Range<Integer> getDpiRange(int targetDpi, ImmutableSet<Integer> alternatives) {
        if (alternatives.isEmpty()) {
            return Range.all();
        }
        Optional<Integer> lowMidPoint = ScreenDensitySelector.getNearestLowerDpi(targetDpi, alternatives).map(lowerDpi -> ScreenDensitySelector.getMidPoint(lowerDpi, targetDpi)).map(val -> (int)Math.ceil(val));
        Optional<Integer> highMidPoint = ScreenDensitySelector.getNearestHigherDpi(targetDpi, alternatives).map(higherDpi -> ScreenDensitySelector.getMidPoint(targetDpi, higherDpi)).map(val -> (int)Math.floor(val));
        if (!lowMidPoint.isPresent()) {
            return Range.atMost((Comparable)highMidPoint.get());
        }
        if (!highMidPoint.isPresent()) {
            return Range.atLeast((Comparable)lowMidPoint.get());
        }
        return Range.closed((Comparable)lowMidPoint.get(), (Comparable)highMidPoint.get());
    }

    private static double getMidPoint(int lowerDpi, int higherDpi) {
        Preconditions.checkArgument(lowerDpi < higherDpi);
        return ((double)(-higherDpi) + Math.sqrt(higherDpi * higherDpi + 8 * lowerDpi * higherDpi)) / 2.0;
    }

    private static Optional<Integer> getNearestHigherDpi(int targetDpi, ImmutableSet<Integer> alternatives) {
        return alternatives.stream().filter(alt -> alt > targetDpi).min(Comparator.naturalOrder());
    }

    private static Optional<Integer> getNearestLowerDpi(int targetDpi, ImmutableSet<Integer> alternatives) {
        return alternatives.stream().filter(alt -> alt < targetDpi).max(Comparator.naturalOrder());
    }

    public Resources.ConfigValue selectBestConfigValue(Iterable<Resources.ConfigValue> values2, Targeting.ScreenDensity.DensityAlias desiredDensityAlias, Version bundleVersion) {
        Preconditions.checkArgument(ResourcesUtils.DENSITY_ALIAS_TO_DPI_MAP.containsKey(desiredDensityAlias));
        return this.selectBestConfigValue(values2, ResourcesUtils.DENSITY_ALIAS_TO_DPI_MAP.get(desiredDensityAlias), bundleVersion);
    }

    public Resources.ConfigValue selectBestConfigValue(Iterable<Resources.ConfigValue> values2, int desiredDpi, Version bundleVersion) {
        return Ordering.from(this.comparatorForConfigValues(desiredDpi, bundleVersion)).max(values2);
    }

    public int selectBestDensity(Iterable<Integer> densities, int desiredDpi) {
        return Ordering.from(new ScreenDensityComparator(desiredDpi)).max(densities);
    }

    private Comparator<Resources.ConfigValue> comparatorForConfigValues(int desiredDpi, Version bundleVersion) {
        Comparator<Resources.ConfigValue> compositeComparator = Comparator.comparing(ScreenDensitySelector::getDpiValue, new ScreenDensityComparator(desiredDpi));
        if (VersionGuardedFeature.PREFER_EXPLICIT_DPI_OVER_DEFAULT_CONFIG.enabledForVersion(bundleVersion)) {
            compositeComparator = compositeComparator.thenComparing(ScreenDensitySelector::isExplicitDpi, Booleans.falseFirst());
        }
        return compositeComparator;
    }

    private static int getDpiValue(Resources.ConfigValue configValue) {
        if (configValue.getConfig().getDensity() == 0) {
            return 160;
        }
        return configValue.getConfig().getDensity();
    }

    private static boolean isExplicitDpi(Resources.ConfigValue configValue) {
        int configDpi = configValue.getConfig().getDensity();
        return configDpi != 65534 && configDpi != 0 && configDpi != 65535;
    }

    private static class ScreenDensityComparator
    implements Comparator<Integer> {
        private final int desiredDpi;

        public ScreenDensityComparator(int desiredDpi) {
            Preconditions.checkArgument(desiredDpi != 65535);
            this.desiredDpi = desiredDpi == 0 || desiredDpi == 65534 ? 160 : desiredDpi;
        }

        @Override
        public int compare(Integer dpiA, Integer dpiB) {
            Preconditions.checkNotNull(dpiA);
            Preconditions.checkNotNull(dpiB);
            if (dpiA.equals(dpiB)) {
                return 0;
            }
            if (dpiA.equals(65534)) {
                return 1;
            }
            if (dpiB.equals(65534)) {
                return -1;
            }
            if (dpiA > dpiB) {
                return -1 * this.compareOrdered(dpiB, dpiA);
            }
            return this.compareOrdered(dpiA, dpiB);
        }

        private int compareOrdered(int lowerDpi, int higherDpi) {
            if (this.desiredDpi >= higherDpi) {
                return -1;
            }
            if (this.desiredDpi <= lowerDpi) {
                return 1;
            }
            if ((2 * lowerDpi - this.desiredDpi) * higherDpi > this.desiredDpi * this.desiredDpi) {
                return 1;
            }
            return -1;
        }
    }
}

