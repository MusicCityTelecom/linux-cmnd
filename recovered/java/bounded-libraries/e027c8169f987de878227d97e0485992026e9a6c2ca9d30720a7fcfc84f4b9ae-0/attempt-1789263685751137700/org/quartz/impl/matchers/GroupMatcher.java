/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.matchers;

import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.StringMatcher;
import org.quartz.utils.Key;

public class GroupMatcher<T extends Key<?>>
extends StringMatcher<T> {
    private static final long serialVersionUID = -3275767650469343849L;

    protected GroupMatcher(String compareTo, StringMatcher.StringOperatorName compareWith) {
        super(compareTo, compareWith);
    }

    public static <T extends Key<T>> GroupMatcher<T> groupEquals(String compareTo) {
        return new GroupMatcher<T>(compareTo, StringMatcher.StringOperatorName.EQUALS);
    }

    public static GroupMatcher<JobKey> jobGroupEquals(String compareTo) {
        return GroupMatcher.groupEquals(compareTo);
    }

    public static GroupMatcher<TriggerKey> triggerGroupEquals(String compareTo) {
        return GroupMatcher.groupEquals(compareTo);
    }

    public static <T extends Key<T>> GroupMatcher<T> groupStartsWith(String compareTo) {
        return new GroupMatcher<T>(compareTo, StringMatcher.StringOperatorName.STARTS_WITH);
    }

    public static GroupMatcher<JobKey> jobGroupStartsWith(String compareTo) {
        return GroupMatcher.groupStartsWith(compareTo);
    }

    public static GroupMatcher<TriggerKey> triggerGroupStartsWith(String compareTo) {
        return GroupMatcher.groupStartsWith(compareTo);
    }

    public static <T extends Key<T>> GroupMatcher<T> groupEndsWith(String compareTo) {
        return new GroupMatcher<T>(compareTo, StringMatcher.StringOperatorName.ENDS_WITH);
    }

    public static GroupMatcher<JobKey> jobGroupEndsWith(String compareTo) {
        return GroupMatcher.groupEndsWith(compareTo);
    }

    public static GroupMatcher<TriggerKey> triggerGroupEndsWith(String compareTo) {
        return GroupMatcher.groupEndsWith(compareTo);
    }

    public static <T extends Key<T>> GroupMatcher<T> groupContains(String compareTo) {
        return new GroupMatcher<T>(compareTo, StringMatcher.StringOperatorName.CONTAINS);
    }

    public static GroupMatcher<JobKey> jobGroupContains(String compareTo) {
        return GroupMatcher.groupContains(compareTo);
    }

    public static GroupMatcher<TriggerKey> triggerGroupContains(String compareTo) {
        return GroupMatcher.groupContains(compareTo);
    }

    public static <T extends Key<T>> GroupMatcher<T> anyGroup() {
        return new GroupMatcher<T>("", StringMatcher.StringOperatorName.ANYTHING);
    }

    public static GroupMatcher<JobKey> anyJobGroup() {
        return GroupMatcher.anyGroup();
    }

    public static GroupMatcher<TriggerKey> anyTriggerGroup() {
        return GroupMatcher.anyGroup();
    }

    @Override
    protected String getValue(T key) {
        return ((Key)key).getGroup();
    }
}

