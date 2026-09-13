/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.web.support;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apereo.cas.web.support.ThrottledSubmission;

public interface ThrottledSubmissionsStore<T extends ThrottledSubmission> {
    public static final String BEAN_NAME = "throttleSubmissionMap";

    public void removeIf(Predicate<T> var1);

    public void remove(String var1);

    public void put(T var1);

    public boolean contains(String var1);

    public T get(String var1);

    public Stream<T> entries();

    public boolean exceedsThreshold(String var1, double var2);

    public void release(double var1);
}

