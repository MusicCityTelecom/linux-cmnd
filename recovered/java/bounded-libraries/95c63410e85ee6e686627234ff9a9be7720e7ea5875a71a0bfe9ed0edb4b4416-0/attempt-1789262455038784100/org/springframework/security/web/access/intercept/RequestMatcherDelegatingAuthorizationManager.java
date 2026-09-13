/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authorization.AuthorizationDecision
 *  org.springframework.security.authorization.AuthorizationManager
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access.intercept;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.util.Assert;

public final class RequestMatcherDelegatingAuthorizationManager
implements AuthorizationManager<HttpServletRequest> {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;

    private RequestMatcherDelegatingAuthorizationManager(List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings) {
        Assert.notEmpty(mappings, (String)"mappings cannot be empty");
        this.mappings = mappings;
    }

    public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest request) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)LogMessage.format((String)"Authorizing %s", (Object)request));
        }
        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : this.mappings) {
            RequestMatcher matcher = mapping.getRequestMatcher();
            RequestMatcher.MatchResult matchResult = matcher.matcher(request);
            if (!matchResult.isMatch()) continue;
            AuthorizationManager<RequestAuthorizationContext> manager = mapping.getEntry();
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Checking authorization on %s using %s", (Object)request, manager));
            }
            return manager.check(authentication, (Object)new RequestAuthorizationContext(request, matchResult.getVariables()));
        }
        this.logger.trace((Object)"Abstaining since did not find matching RequestMatcher");
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings = new ArrayList<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>>();

        public Builder add(RequestMatcher matcher, AuthorizationManager<RequestAuthorizationContext> manager) {
            Assert.notNull((Object)matcher, (String)"matcher cannot be null");
            Assert.notNull(manager, (String)"manager cannot be null");
            this.mappings.add(new RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>(matcher, manager));
            return this;
        }

        public Builder mappings(Consumer<List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>>> mappingsConsumer) {
            Assert.notNull(mappingsConsumer, (String)"mappingsConsumer cannot be null");
            mappingsConsumer.accept(this.mappings);
            return this;
        }

        public RequestMatcherDelegatingAuthorizationManager build() {
            return new RequestMatcherDelegatingAuthorizationManager(this.mappings);
        }
    }
}

