/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.configurationmetadata.ValueHint;
import org.springframework.boot.configurationmetadata.ValueProvider;

public class Hints {
    private final List<ValueHint> keyHints = new ArrayList<ValueHint>();
    private final List<ValueProvider> keyProviders = new ArrayList<ValueProvider>();
    private final List<ValueHint> valueHints = new ArrayList<ValueHint>();
    private final List<ValueProvider> valueProviders = new ArrayList<ValueProvider>();

    public List<ValueHint> getKeyHints() {
        return this.keyHints;
    }

    public List<ValueProvider> getKeyProviders() {
        return this.keyProviders;
    }

    public List<ValueHint> getValueHints() {
        return this.valueHints;
    }

    public List<ValueProvider> getValueProviders() {
        return this.valueProviders;
    }
}

