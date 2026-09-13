/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.origin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.boot.origin.OriginProvider;

public interface Origin {
    default public Origin getParent() {
        return null;
    }

    public static Origin from(Object source) {
        if (source instanceof Origin) {
            return (Origin)source;
        }
        Origin origin = null;
        if (source instanceof OriginProvider) {
            origin = ((OriginProvider)source).getOrigin();
        }
        if (origin == null && source instanceof Throwable) {
            return Origin.from(((Throwable)source).getCause());
        }
        return origin;
    }

    public static List<Origin> parentsFrom(Object source) {
        Origin origin = Origin.from(source);
        if (origin == null) {
            return Collections.emptyList();
        }
        LinkedHashSet<Origin> parents = new LinkedHashSet<Origin>();
        for (origin = origin.getParent(); origin != null && !parents.contains(origin); origin = origin.getParent()) {
            parents.add(origin);
        }
        return Collections.unmodifiableList(new ArrayList(parents));
    }
}

