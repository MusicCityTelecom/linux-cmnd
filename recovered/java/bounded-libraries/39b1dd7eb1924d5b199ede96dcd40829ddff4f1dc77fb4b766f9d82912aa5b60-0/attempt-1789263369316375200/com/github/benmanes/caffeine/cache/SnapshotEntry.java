/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Policy;
import java.util.Map;
import java.util.Objects;

class SnapshotEntry<K, V>
implements Policy.CacheEntry<K, V> {
    private final long snapshot;
    private final V value;
    private final K key;

    SnapshotEntry(K key, V value, long snapshot) {
        this.snapshot = snapshot;
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int weight() {
        return 1;
    }

    @Override
    public long expiresAt() {
        return this.snapshot + Long.MAX_VALUE;
    }

    @Override
    public long refreshableAt() {
        return this.snapshot + Long.MAX_VALUE;
    }

    @Override
    public long snapshotAt() {
        return this.snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry)o;
        return this.key.equals(entry.getKey()) && this.value.equals(entry.getValue());
    }

    @Override
    public int hashCode() {
        return this.key.hashCode() ^ this.value.hashCode();
    }

    public String toString() {
        return this.key + "=" + this.value;
    }

    public static <K, V> SnapshotEntry<K, V> forEntry(K key, V value) {
        return new SnapshotEntry<K, V>(key, value, 0L);
    }

    public static <K, V> SnapshotEntry<K, V> forEntry(K key, V value, long snapshot, int weight, long expiresAt, long refreshableAt) {
        long unsetTicks = snapshot + Long.MAX_VALUE;
        int features = 0 | (refreshableAt == unsetTicks ? 0 : 4) | (expiresAt == unsetTicks ? 0 : 2) | (weight < 0 || weight == 1 ? 0 : 1);
        switch (features) {
            case 0: {
                return new SnapshotEntry<K, V>(key, value, snapshot);
            }
            case 1: {
                return new WeightedEntry<K, V>(key, value, snapshot, weight);
            }
            case 2: {
                return new ExpirableEntry<K, V>(key, value, snapshot, expiresAt);
            }
            case 3: {
                return new ExpirableWeightedEntry<K, V>(key, value, snapshot, weight, expiresAt);
            }
            case 6: {
                return new RefreshableExpirableEntry<K, V>(key, value, snapshot, expiresAt, refreshableAt);
            }
        }
        return new CompleteEntry<K, V>(key, value, snapshot, weight, expiresAt, refreshableAt);
    }

    static final class CompleteEntry<K, V>
    extends ExpirableWeightedEntry<K, V> {
        final long refreshableAt;

        CompleteEntry(K key, V value, long snapshot, int weight, long expiresAt, long refreshableAt) {
            super(key, value, snapshot, weight, expiresAt);
            this.refreshableAt = refreshableAt;
        }

        @Override
        public long refreshableAt() {
            return this.refreshableAt;
        }
    }

    static class RefreshableExpirableEntry<K, V>
    extends ExpirableEntry<K, V> {
        final long refreshableAt;

        RefreshableExpirableEntry(K key, V value, long snapshot, long expiresAt, long refreshableAt) {
            super(key, value, snapshot, expiresAt);
            this.refreshableAt = refreshableAt;
        }

        @Override
        public long refreshableAt() {
            return this.refreshableAt;
        }
    }

    static class ExpirableWeightedEntry<K, V>
    extends WeightedEntry<K, V> {
        final long expiresAt;

        ExpirableWeightedEntry(K key, V value, long snapshot, int weight, long expiresAt) {
            super(key, value, snapshot, weight);
            this.expiresAt = expiresAt;
        }

        @Override
        public long expiresAt() {
            return this.expiresAt;
        }
    }

    static class ExpirableEntry<K, V>
    extends SnapshotEntry<K, V> {
        final long expiresAt;

        ExpirableEntry(K key, V value, long snapshot, long expiresAt) {
            super(key, value, snapshot);
            this.expiresAt = expiresAt;
        }

        @Override
        public long expiresAt() {
            return this.expiresAt;
        }
    }

    static class WeightedEntry<K, V>
    extends SnapshotEntry<K, V> {
        final int weight;

        WeightedEntry(K key, V value, long snapshot, int weight) {
            super(key, value, snapshot);
            this.weight = weight;
        }

        @Override
        public int weight() {
            return this.weight;
        }
    }
}

