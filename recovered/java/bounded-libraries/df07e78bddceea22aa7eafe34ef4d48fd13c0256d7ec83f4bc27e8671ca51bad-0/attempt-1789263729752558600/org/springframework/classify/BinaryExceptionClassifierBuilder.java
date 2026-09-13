/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.classify;

import java.util.ArrayList;
import java.util.List;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.util.Assert;

public class BinaryExceptionClassifierBuilder {
    private Boolean isWhiteList = null;
    private boolean traverseCauses = false;
    private List<Class<? extends Throwable>> exceptionClasses = new ArrayList<Class<? extends Throwable>>();

    public BinaryExceptionClassifierBuilder retryOn(Class<? extends Throwable> throwable) {
        Assert.isTrue((this.isWhiteList == null || this.isWhiteList != false ? 1 : 0) != 0, (String)"Please use only retryOn() or only notRetryOn()");
        Assert.notNull(throwable, (String)"Exception class can not be null");
        this.isWhiteList = true;
        this.exceptionClasses.add(throwable);
        return this;
    }

    public BinaryExceptionClassifierBuilder notRetryOn(Class<? extends Throwable> throwable) {
        Assert.isTrue((this.isWhiteList == null || this.isWhiteList == false ? 1 : 0) != 0, (String)"Please use only retryOn() or only notRetryOn()");
        Assert.notNull(throwable, (String)"Exception class can not be null");
        this.isWhiteList = false;
        this.exceptionClasses.add(throwable);
        return this;
    }

    public BinaryExceptionClassifierBuilder traversingCauses() {
        this.traverseCauses = true;
        return this;
    }

    public BinaryExceptionClassifier build() {
        Assert.isTrue((!this.exceptionClasses.isEmpty() ? 1 : 0) != 0, (String)"Attempt to build classifier with empty rules. To build always true, or always false instance, please use explicit rule for Throwable");
        BinaryExceptionClassifier classifier = new BinaryExceptionClassifier(this.exceptionClasses, (boolean)this.isWhiteList);
        classifier.setTraverseCauses(this.traverseCauses);
        return classifier;
    }
}

