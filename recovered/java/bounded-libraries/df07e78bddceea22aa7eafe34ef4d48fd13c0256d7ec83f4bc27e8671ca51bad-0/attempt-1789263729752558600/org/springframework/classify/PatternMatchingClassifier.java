/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.classify;

import java.util.HashMap;
import java.util.Map;
import org.springframework.classify.Classifier;
import org.springframework.classify.PatternMatcher;

public class PatternMatchingClassifier<T>
implements Classifier<String, T> {
    private PatternMatcher<T> values;

    public PatternMatchingClassifier() {
        this(new HashMap());
    }

    public PatternMatchingClassifier(Map<String, T> values) {
        this.values = new PatternMatcher<T>(values);
    }

    public void setPatternMap(Map<String, T> values) {
        this.values = new PatternMatcher<T>(values);
    }

    @Override
    public T classify(String classifiable) {
        T value = this.values.match(classifiable);
        return value;
    }
}

