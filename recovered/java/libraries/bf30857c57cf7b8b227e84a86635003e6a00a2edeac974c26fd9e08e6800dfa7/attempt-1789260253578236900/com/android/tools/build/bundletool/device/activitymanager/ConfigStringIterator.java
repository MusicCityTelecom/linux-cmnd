/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device.activitymanager;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConfigStringIterator
implements Iterator<String> {
    private static final Splitter SPLITTER = Splitter.on('-');
    private final ImmutableList<String> resourceQualifiers;
    private int iteratorIndex = -1;
    private int savedIndex = -1;

    public ConfigStringIterator(String resourceString) {
        this.resourceQualifiers = ImmutableList.copyOf(SPLITTER.split(resourceString));
    }

    public String getValue() {
        Preconditions.checkState(this.iteratorIndex >= 0 && this.iteratorIndex < this.resourceQualifiers.size());
        return (String)this.resourceQualifiers.get(this.iteratorIndex);
    }

    @Override
    public boolean hasNext() {
        return this.iteratorIndex < this.resourceQualifiers.size() - 1;
    }

    @Override
    public String next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more qualifiers in the string.");
        }
        ++this.iteratorIndex;
        return this.getValue();
    }

    public void savePosition() {
        this.savedIndex = this.iteratorIndex;
    }

    public void restorePosition() {
        this.iteratorIndex = this.savedIndex;
    }
}

