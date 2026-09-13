/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.classify;

import org.springframework.classify.Classifier;
import org.springframework.classify.util.MethodInvoker;
import org.springframework.classify.util.MethodInvokerUtils;
import org.springframework.util.Assert;

public class ClassifierAdapter<C, T>
implements Classifier<C, T> {
    private MethodInvoker invoker;
    private Classifier<C, T> classifier;

    public ClassifierAdapter() {
    }

    public ClassifierAdapter(Object delegate) {
        this.setDelegate(delegate);
    }

    public ClassifierAdapter(Classifier<C, T> delegate) {
        this.classifier = delegate;
    }

    public void setDelegate(Classifier<C, T> delegate) {
        this.classifier = delegate;
        this.invoker = null;
    }

    public final void setDelegate(Object delegate) {
        this.classifier = null;
        this.invoker = MethodInvokerUtils.getMethodInvokerByAnnotation(org.springframework.classify.annotation.Classifier.class, delegate);
        if (this.invoker == null) {
            this.invoker = MethodInvokerUtils.getMethodInvokerForSingleArgument(delegate);
        }
        Assert.state((this.invoker != null ? 1 : 0) != 0, (String)("No single argument public method with or without @Classifier was found in delegate of type " + delegate.getClass()));
    }

    @Override
    public T classify(C classifiable) {
        if (this.classifier != null) {
            return this.classifier.classify(classifiable);
        }
        return (T)this.invoker.invokeMethod(classifiable);
    }
}

