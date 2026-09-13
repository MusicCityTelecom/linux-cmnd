/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.index.qual.NonNegative
 *  org.checkerframework.checker.nullness.qual.Nullable
 *  org.checkerframework.checker.nullness.qual.PolyNull
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

public interface Policy<K, V> {
    public boolean isRecordingStats();

    public @Nullable V getIfPresentQuietly(K var1);

    default public @Nullable CacheEntry<K, V> getEntryIfPresentQuietly(K key) {
        throw new UnsupportedOperationException();
    }

    public Map<K, CompletableFuture<V>> refreshes();

    public Optional<Eviction<K, V>> eviction();

    public Optional<FixedExpiration<K, V>> expireAfterAccess();

    public Optional<FixedExpiration<K, V>> expireAfterWrite();

    public Optional<VarExpiration<K, V>> expireVariably();

    public Optional<FixedRefresh<K, V>> refreshAfterWrite();

    public static interface CacheEntry<K, V>
    extends Map.Entry<K, V> {
        public int weight();

        public long expiresAt();

        default public Duration expiresAfter() {
            return Duration.ofNanos(this.expiresAt() - this.snapshotAt());
        }

        public long refreshableAt();

        default public Duration refreshableAfter() {
            return Duration.ofNanos(this.refreshableAt() - this.snapshotAt());
        }

        public long snapshotAt();
    }

    public static interface FixedRefresh<K, V> {
        public OptionalLong ageOf(K var1, TimeUnit var2);

        default public Optional<Duration> ageOf(K key) {
            OptionalLong duration = this.ageOf(key, TimeUnit.NANOSECONDS);
            return duration.isPresent() ? Optional.of(Duration.ofNanos(duration.getAsLong())) : Optional.empty();
        }

        public @NonNegative long getRefreshesAfter(TimeUnit var1);

        default public Duration getRefreshesAfter() {
            return Duration.ofNanos(this.getRefreshesAfter(TimeUnit.NANOSECONDS));
        }

        public void setRefreshesAfter(@NonNegative long var1, TimeUnit var3);

        default public void setRefreshesAfter(Duration duration) {
            this.setRefreshesAfter(Caffeine.saturatedToNanos(duration), TimeUnit.NANOSECONDS);
        }
    }

    public static interface VarExpiration<K, V> {
        public OptionalLong getExpiresAfter(K var1, TimeUnit var2);

        default public Optional<Duration> getExpiresAfter(K key) {
            OptionalLong duration = this.getExpiresAfter(key, TimeUnit.NANOSECONDS);
            return duration.isPresent() ? Optional.of(Duration.ofNanos(duration.getAsLong())) : Optional.empty();
        }

        public void setExpiresAfter(K var1, @NonNegative long var2, TimeUnit var4);

        default public void setExpiresAfter(K key, Duration duration) {
            this.setExpiresAfter(key, Caffeine.saturatedToNanos(duration), TimeUnit.NANOSECONDS);
        }

        public @Nullable V putIfAbsent(K var1, V var2, @NonNegative long var3, TimeUnit var5);

        default public @Nullable V putIfAbsent(K key, V value, Duration duration) {
            return this.putIfAbsent(key, value, Caffeine.saturatedToNanos(duration), TimeUnit.NANOSECONDS);
        }

        public @Nullable V put(K var1, V var2, @NonNegative long var3, TimeUnit var5);

        default public @Nullable V put(K key, V value, Duration duration) {
            return this.put(key, value, Caffeine.saturatedToNanos(duration), TimeUnit.NANOSECONDS);
        }

        default public @PolyNull V compute(K key, BiFunction<? super K, ? super V, ? extends @PolyNull V> remappingFunction, Duration duration) {
            throw new UnsupportedOperationException();
        }

        public Map<K, V> oldest(@NonNegative int var1);

        default public <T> T oldest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        public Map<K, V> youngest(@NonNegative int var1);

        default public <T> T youngest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction) {
            throw new UnsupportedOperationException();
        }
    }

    public static interface FixedExpiration<K, V> {
        public OptionalLong ageOf(K var1, TimeUnit var2);

        default public Optional<Duration> ageOf(K key) {
            OptionalLong duration = this.ageOf(key, TimeUnit.NANOSECONDS);
            return duration.isPresent() ? Optional.of(Duration.ofNanos(duration.getAsLong())) : Optional.empty();
        }

        public @NonNegative long getExpiresAfter(TimeUnit var1);

        default public Duration getExpiresAfter() {
            return Duration.ofNanos(this.getExpiresAfter(TimeUnit.NANOSECONDS));
        }

        public void setExpiresAfter(@NonNegative long var1, TimeUnit var3);

        default public void setExpiresAfter(Duration duration) {
            this.setExpiresAfter(Caffeine.saturatedToNanos(duration), TimeUnit.NANOSECONDS);
        }

        public Map<K, V> oldest(@NonNegative int var1);

        default public <T> T oldest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        public Map<K, V> youngest(@NonNegative int var1);

        default public <T> T youngest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction) {
            throw new UnsupportedOperationException();
        }
    }

    public static interface Eviction<K, V> {
        public boolean isWeighted();

        public OptionalInt weightOf(K var1);

        public OptionalLong weightedSize();

        public @NonNegative long getMaximum();

        public void setMaximum(@NonNegative long var1);

        public Map<K, V> coldest(@NonNegative int var1);

        default public Map<K, V> coldestWeighted(@NonNegative long weightLimit) {
            throw new UnsupportedOperationException();
        }

        default public <T> T coldest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        public Map<K, V> hottest(@NonNegative int var1);

        default public Map<K, V> hottestWeighted(@NonNegative long weightLimit) {
            throw new UnsupportedOperationException();
        }

        default public <T> T hottest(Function<Stream<CacheEntry<K, V>>, T> mappingFunction) {
            throw new UnsupportedOperationException();
        }
    }
}

