/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.classify;

import java.util.Map;
import org.springframework.classify.Classifier;
import org.springframework.classify.ClassifierAdapter;
import org.springframework.classify.PatternMatchingClassifier;

public class BackToBackPatternClassifier<C, T>
implements Classifier<C, T> {
    private Classifier<C, String> router;
    private Classifier<String, T> matcher;

    public BackToBackPatternClassifier() {
    }

    public BackToBackPatternClassifier(Classifier<C, String> router, Classifier<String, T> matcher) {
        this.router = router;
        this.matcher = matcher;
    }

    public void setMatcherMap(Map<String, T> map) {
        this.matcher = new PatternMatchingClassifier<T>(map);
    }

    public void setRouterDelegate(Object delegate) {
        this.router = new ClassifierAdapter<C, String>(delegate);
    }

    @Override
    public T classify(C classifiable) {
        return this.matcher.classify(this.router.classify(classifiable));
    }
}

