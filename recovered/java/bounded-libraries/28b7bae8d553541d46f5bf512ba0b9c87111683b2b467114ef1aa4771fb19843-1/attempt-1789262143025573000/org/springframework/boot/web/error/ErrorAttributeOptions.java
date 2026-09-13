/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.error;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public final class ErrorAttributeOptions {
    private final Set<Include> includes;

    private ErrorAttributeOptions(Set<Include> includes) {
        this.includes = includes;
    }

    public boolean isIncluded(Include include) {
        return this.includes.contains((Object)include);
    }

    public Set<Include> getIncludes() {
        return this.includes;
    }

    public ErrorAttributeOptions including(Include ... includes) {
        EnumSet<Include> updated = this.copyIncludes();
        updated.addAll(Arrays.asList(includes));
        return new ErrorAttributeOptions(Collections.unmodifiableSet(updated));
    }

    public ErrorAttributeOptions excluding(Include ... excludes) {
        EnumSet<Include> updated = this.copyIncludes();
        updated.removeAll(Arrays.asList(excludes));
        return new ErrorAttributeOptions(Collections.unmodifiableSet(updated));
    }

    private EnumSet<Include> copyIncludes() {
        return this.includes.isEmpty() ? EnumSet.noneOf(Include.class) : EnumSet.copyOf(this.includes);
    }

    public static ErrorAttributeOptions defaults() {
        return ErrorAttributeOptions.of(new Include[0]);
    }

    public static ErrorAttributeOptions of(Include ... includes) {
        return ErrorAttributeOptions.of(Arrays.asList(includes));
    }

    public static ErrorAttributeOptions of(Collection<Include> includes) {
        return new ErrorAttributeOptions(includes.isEmpty() ? Collections.emptySet() : Collections.unmodifiableSet(EnumSet.copyOf(includes)));
    }

    public static enum Include {
        EXCEPTION,
        STACK_TRACE,
        MESSAGE,
        BINDING_ERRORS;

    }
}

