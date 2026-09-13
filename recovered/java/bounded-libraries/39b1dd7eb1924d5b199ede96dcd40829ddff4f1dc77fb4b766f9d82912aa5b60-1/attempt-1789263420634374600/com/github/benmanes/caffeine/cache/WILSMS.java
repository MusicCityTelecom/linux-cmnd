/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque;
import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.FrequencySketch;
import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.WILS;

class WILSMS<K, V>
extends WILS<K, V> {
    long maximum;
    long weightedSize;
    long windowMaximum;
    long windowWeightedSize;
    long mainProtectedMaximum;
    long mainProtectedWeightedSize;
    double stepSize;
    long adjustment;
    int hitsInSample;
    int missesInSample;
    double previousSampleHitRate;
    final FrequencySketch<K> sketch = new FrequencySketch();
    final AccessOrderDeque<Node<K, V>> accessOrderWindowDeque;
    final AccessOrderDeque<Node<K, V>> accessOrderProbationDeque;
    final AccessOrderDeque<Node<K, V>> accessOrderProtectedDeque;

    WILSMS(Caffeine<K, V> caffeine, AsyncCacheLoader<? super K, V> asyncCacheLoader, boolean bl) {
        super(caffeine, asyncCacheLoader, bl);
        if (caffeine.hasInitialCapacity()) {
            long l = Math.min(caffeine.getMaximum(), (long)caffeine.getInitialCapacity());
            this.sketch.ensureCapacity(l);
        }
        this.accessOrderWindowDeque = caffeine.evicts() || caffeine.expiresAfterAccess() ? new AccessOrderDeque() : null;
        this.accessOrderProbationDeque = new AccessOrderDeque();
        this.accessOrderProtectedDeque = new AccessOrderDeque();
    }

    @Override
    protected final boolean evicts() {
        return true;
    }

    @Override
    protected final long maximum() {
        return this.maximum;
    }

    @Override
    protected final void setMaximum(long l) {
        this.maximum = l;
    }

    @Override
    protected final long weightedSize() {
        return this.weightedSize;
    }

    @Override
    protected final void setWeightedSize(long l) {
        this.weightedSize = l;
    }

    @Override
    protected final long windowMaximum() {
        return this.windowMaximum;
    }

    @Override
    protected final void setWindowMaximum(long l) {
        this.windowMaximum = l;
    }

    @Override
    protected final long windowWeightedSize() {
        return this.windowWeightedSize;
    }

    @Override
    protected final void setWindowWeightedSize(long l) {
        this.windowWeightedSize = l;
    }

    @Override
    protected final long mainProtectedMaximum() {
        return this.mainProtectedMaximum;
    }

    @Override
    protected final void setMainProtectedMaximum(long l) {
        this.mainProtectedMaximum = l;
    }

    @Override
    protected final long mainProtectedWeightedSize() {
        return this.mainProtectedWeightedSize;
    }

    @Override
    protected final void setMainProtectedWeightedSize(long l) {
        this.mainProtectedWeightedSize = l;
    }

    @Override
    protected final double stepSize() {
        return this.stepSize;
    }

    @Override
    protected final void setStepSize(double d) {
        this.stepSize = d;
    }

    @Override
    protected final long adjustment() {
        return this.adjustment;
    }

    @Override
    protected final void setAdjustment(long l) {
        this.adjustment = l;
    }

    @Override
    protected final int hitsInSample() {
        return this.hitsInSample;
    }

    @Override
    protected final void setHitsInSample(int n) {
        this.hitsInSample = n;
    }

    @Override
    protected final int missesInSample() {
        return this.missesInSample;
    }

    @Override
    protected final void setMissesInSample(int n) {
        this.missesInSample = n;
    }

    @Override
    protected final double previousSampleHitRate() {
        return this.previousSampleHitRate;
    }

    @Override
    protected final void setPreviousSampleHitRate(double d) {
        this.previousSampleHitRate = d;
    }

    @Override
    protected final FrequencySketch<K> frequencySketch() {
        return this.sketch;
    }

    @Override
    protected final AccessOrderDeque<Node<K, V>> accessOrderWindowDeque() {
        return this.accessOrderWindowDeque;
    }

    @Override
    protected final AccessOrderDeque<Node<K, V>> accessOrderProbationDeque() {
        return this.accessOrderProbationDeque;
    }

    @Override
    protected final AccessOrderDeque<Node<K, V>> accessOrderProtectedDeque() {
        return this.accessOrderProtectedDeque;
    }
}

