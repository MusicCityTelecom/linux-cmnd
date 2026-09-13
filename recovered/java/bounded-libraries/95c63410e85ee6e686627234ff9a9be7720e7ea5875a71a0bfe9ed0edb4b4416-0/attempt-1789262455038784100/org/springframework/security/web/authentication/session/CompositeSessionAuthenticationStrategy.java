/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.session;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.Assert;

public class CompositeSessionAuthenticationStrategy
implements SessionAuthenticationStrategy {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final List<SessionAuthenticationStrategy> delegateStrategies;

    public CompositeSessionAuthenticationStrategy(List<SessionAuthenticationStrategy> delegateStrategies) {
        Assert.notEmpty(delegateStrategies, (String)"delegateStrategies cannot be null or empty");
        for (SessionAuthenticationStrategy strategy : delegateStrategies) {
            Assert.notNull((Object)strategy, () -> "delegateStrategies cannot contain null entires. Got " + delegateStrategies);
        }
        this.delegateStrategies = delegateStrategies;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        int currentPosition = 0;
        int size = this.delegateStrategies.size();
        for (SessionAuthenticationStrategy delegate : this.delegateStrategies) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Preparing session with %s (%d/%d)", (Object)delegate.getClass().getSimpleName(), (Object)(++currentPosition), (Object)size));
            }
            delegate.onAuthentication(authentication, request, response);
        }
    }

    public String toString() {
        return this.getClass().getName() + " [delegateStrategies = " + this.delegateStrategies + "]";
    }
}

