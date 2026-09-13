/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.PathMatcher
 *  org.springframework.util.RouteMatcher
 *  org.springframework.util.RouteMatcher$Route
 *  org.springframework.util.SimpleRouteMatcher
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.AbstractMessageCondition;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.RouteMatcher;
import org.springframework.util.SimpleRouteMatcher;
import org.springframework.util.StringUtils;

public class DestinationPatternsMessageCondition
extends AbstractMessageCondition<DestinationPatternsMessageCondition> {
    public static final String LOOKUP_DESTINATION_HEADER = "lookupDestination";
    private final Set<String> patterns;
    private final RouteMatcher routeMatcher;

    public DestinationPatternsMessageCondition(String ... patterns) {
        this(patterns, (PathMatcher)null);
    }

    public DestinationPatternsMessageCondition(String[] patterns, @Nullable PathMatcher matcher) {
        this(patterns, (RouteMatcher)new SimpleRouteMatcher((PathMatcher)(matcher != null ? matcher : new AntPathMatcher())));
    }

    public DestinationPatternsMessageCondition(String[] patterns, RouteMatcher routeMatcher) {
        this(Collections.unmodifiableSet(DestinationPatternsMessageCondition.prependLeadingSlash(patterns, routeMatcher)), routeMatcher);
    }

    private static Set<String> prependLeadingSlash(String[] patterns, RouteMatcher routeMatcher) {
        boolean slashSeparator = routeMatcher.combine("a", "a").equals("a/a");
        LinkedHashSet<String> result = new LinkedHashSet<String>(patterns.length);
        for (String pattern : patterns) {
            if (slashSeparator && StringUtils.hasLength((String)pattern) && !pattern.startsWith("/")) {
                pattern = "/" + pattern;
            }
            result.add(pattern);
        }
        return result;
    }

    private DestinationPatternsMessageCondition(Set<String> patterns, RouteMatcher routeMatcher) {
        this.patterns = patterns;
        this.routeMatcher = routeMatcher;
    }

    public Set<String> getPatterns() {
        return this.patterns;
    }

    @Override
    protected Collection<String> getContent() {
        return this.patterns;
    }

    @Override
    protected String getToStringInfix() {
        return " || ";
    }

    @Override
    public DestinationPatternsMessageCondition combine(DestinationPatternsMessageCondition other) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        if (!this.patterns.isEmpty() && !other.patterns.isEmpty()) {
            for (String pattern1 : this.patterns) {
                for (String pattern2 : other.patterns) {
                    result.add(this.routeMatcher.combine(pattern1, pattern2));
                }
            }
        } else if (!this.patterns.isEmpty()) {
            result.addAll(this.patterns);
        } else if (!other.patterns.isEmpty()) {
            result.addAll(other.patterns);
        } else {
            result.add("");
        }
        return new DestinationPatternsMessageCondition(result, this.routeMatcher);
    }

    @Override
    @Nullable
    public DestinationPatternsMessageCondition getMatchingCondition(Message<?> message) {
        Object destination = message.getHeaders().get(LOOKUP_DESTINATION_HEADER);
        if (destination == null) {
            return null;
        }
        if (this.patterns.isEmpty()) {
            return this;
        }
        ArrayList<String> matches = null;
        for (String pattern : this.patterns) {
            if (!pattern.equals(destination) && !this.matchPattern(pattern, destination)) continue;
            if (matches == null) {
                matches = new ArrayList<String>();
            }
            matches.add(pattern);
        }
        if (CollectionUtils.isEmpty(matches)) {
            return null;
        }
        matches.sort(this.getPatternComparator(destination));
        return new DestinationPatternsMessageCondition(new LinkedHashSet<String>(matches), this.routeMatcher);
    }

    private boolean matchPattern(String pattern, Object destination) {
        return destination instanceof RouteMatcher.Route ? this.routeMatcher.match(pattern, (RouteMatcher.Route)destination) : ((SimpleRouteMatcher)this.routeMatcher).getPathMatcher().match(pattern, (String)destination);
    }

    private Comparator<String> getPatternComparator(Object destination) {
        return destination instanceof RouteMatcher.Route ? this.routeMatcher.getPatternComparator((RouteMatcher.Route)destination) : ((SimpleRouteMatcher)this.routeMatcher).getPathMatcher().getPatternComparator((String)destination);
    }

    @Override
    public int compareTo(DestinationPatternsMessageCondition other, Message<?> message) {
        Object destination = message.getHeaders().get(LOOKUP_DESTINATION_HEADER);
        if (destination == null) {
            return 0;
        }
        Comparator<String> patternComparator = this.getPatternComparator(destination);
        Iterator<String> iterator = this.patterns.iterator();
        Iterator<String> iteratorOther = other.patterns.iterator();
        while (iterator.hasNext() && iteratorOther.hasNext()) {
            int result = patternComparator.compare(iterator.next(), iteratorOther.next());
            if (result == 0) continue;
            return result;
        }
        if (iterator.hasNext()) {
            return -1;
        }
        if (iteratorOther.hasNext()) {
            return 1;
        }
        return 0;
    }
}

