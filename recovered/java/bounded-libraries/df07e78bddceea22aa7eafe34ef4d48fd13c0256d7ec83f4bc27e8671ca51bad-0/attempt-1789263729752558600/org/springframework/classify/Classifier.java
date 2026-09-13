/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.classify;

import java.io.Serializable;

public interface Classifier<C, T>
extends Serializable {
    public T classify(C var1);
}

