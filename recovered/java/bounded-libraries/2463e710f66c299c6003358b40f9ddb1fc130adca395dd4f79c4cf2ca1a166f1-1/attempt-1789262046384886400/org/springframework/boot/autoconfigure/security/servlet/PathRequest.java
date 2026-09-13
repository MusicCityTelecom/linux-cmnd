/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.boot.security.servlet.ApplicationContextRequestMatcher
 *  org.springframework.boot.web.context.WebServerApplicationContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.security.web.util.matcher.AntPathRequestMatcher
 *  org.springframework.security.web.util.matcher.RequestMatcher
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.autoconfigure.security.servlet;

import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest;
import org.springframework.boot.security.servlet.ApplicationContextRequestMatcher;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.WebApplicationContext;

public final class PathRequest {
    private PathRequest() {
    }

    public static StaticResourceRequest toStaticResources() {
        return StaticResourceRequest.INSTANCE;
    }

    public static H2ConsoleRequestMatcher toH2Console() {
        return new H2ConsoleRequestMatcher();
    }

    public static final class H2ConsoleRequestMatcher
    extends ApplicationContextRequestMatcher<H2ConsoleProperties> {
        private volatile RequestMatcher delegate;

        private H2ConsoleRequestMatcher() {
            super(H2ConsoleProperties.class);
        }

        protected boolean ignoreApplicationContext(WebApplicationContext applicationContext) {
            return WebServerApplicationContext.hasServerNamespace((ApplicationContext)applicationContext, (String)"management");
        }

        protected void initialized(Supplier<H2ConsoleProperties> h2ConsoleProperties) {
            this.delegate = new AntPathRequestMatcher(h2ConsoleProperties.get().getPath() + "/**");
        }

        protected boolean matches(HttpServletRequest request, Supplier<H2ConsoleProperties> context) {
            return this.delegate.matches(request);
        }
    }
}

