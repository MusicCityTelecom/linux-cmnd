/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.boot.security.servlet.ApplicationContextRequestMatcher
 *  org.springframework.boot.web.context.WebServerApplicationContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.security.web.util.matcher.AntPathRequestMatcher
 *  org.springframework.security.web.util.matcher.OrRequestMatcher
 *  org.springframework.security.web.util.matcher.RequestMatcher
 *  org.springframework.util.Assert
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.autoconfigure.security.servlet;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.security.servlet.ApplicationContextRequestMatcher;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

public final class StaticResourceRequest {
    static final StaticResourceRequest INSTANCE = new StaticResourceRequest();

    private StaticResourceRequest() {
    }

    public StaticResourceRequestMatcher atCommonLocations() {
        return this.at(EnumSet.allOf(StaticResourceLocation.class));
    }

    public StaticResourceRequestMatcher at(StaticResourceLocation first, StaticResourceLocation ... rest) {
        return this.at(EnumSet.of(first, rest));
    }

    public StaticResourceRequestMatcher at(Set<StaticResourceLocation> locations) {
        Assert.notNull(locations, (String)"Locations must not be null");
        return new StaticResourceRequestMatcher(new LinkedHashSet<StaticResourceLocation>(locations));
    }

    public static final class StaticResourceRequestMatcher
    extends ApplicationContextRequestMatcher<DispatcherServletPath> {
        private final Set<StaticResourceLocation> locations;
        private volatile RequestMatcher delegate;

        private StaticResourceRequestMatcher(Set<StaticResourceLocation> locations) {
            super(DispatcherServletPath.class);
            this.locations = locations;
        }

        public StaticResourceRequestMatcher excluding(StaticResourceLocation first, StaticResourceLocation ... rest) {
            return this.excluding(EnumSet.of(first, rest));
        }

        public StaticResourceRequestMatcher excluding(Set<StaticResourceLocation> locations) {
            Assert.notNull(locations, (String)"Locations must not be null");
            LinkedHashSet<StaticResourceLocation> subset = new LinkedHashSet<StaticResourceLocation>(this.locations);
            subset.removeAll(locations);
            return new StaticResourceRequestMatcher(subset);
        }

        protected void initialized(Supplier<DispatcherServletPath> dispatcherServletPath) {
            this.delegate = new OrRequestMatcher(this.getDelegateMatchers(dispatcherServletPath.get()));
        }

        private List<RequestMatcher> getDelegateMatchers(DispatcherServletPath dispatcherServletPath) {
            return this.getPatterns(dispatcherServletPath).map(AntPathRequestMatcher::new).collect(Collectors.toList());
        }

        private Stream<String> getPatterns(DispatcherServletPath dispatcherServletPath) {
            return this.locations.stream().flatMap(StaticResourceLocation::getPatterns).map(dispatcherServletPath::getRelativePath);
        }

        protected boolean ignoreApplicationContext(WebApplicationContext applicationContext) {
            return WebServerApplicationContext.hasServerNamespace((ApplicationContext)applicationContext, (String)"management");
        }

        protected boolean matches(HttpServletRequest request, Supplier<DispatcherServletPath> context) {
            return this.delegate.matches(request);
        }
    }
}

