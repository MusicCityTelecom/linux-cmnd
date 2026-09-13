/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.matchers;

import org.quartz.JobKey;
import org.quartz.Matcher;
import org.quartz.TriggerKey;
import org.quartz.utils.Key;

public class EverythingMatcher<T extends Key<?>>
implements Matcher<T> {
    private static final long serialVersionUID = 202300056681974058L;

    protected EverythingMatcher() {
    }

    public static EverythingMatcher<JobKey> allJobs() {
        return new EverythingMatcher<JobKey>();
    }

    public static EverythingMatcher<TriggerKey> allTriggers() {
        return new EverythingMatcher<TriggerKey>();
    }

    @Override
    public boolean isMatch(T key) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.getClass().equals(this.getClass());
    }

    @Override
    public int hashCode() {
        return this.getClass().getName().hashCode();
    }
}

