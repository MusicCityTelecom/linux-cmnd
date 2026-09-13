/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebSession
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

public class WebSessionServerSecurityContextRepository
implements ServerSecurityContextRepository {
    private static final Log logger = LogFactory.getLog(WebSessionServerSecurityContextRepository.class);
    public static final String DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME = "SPRING_SECURITY_CONTEXT";
    private String springSecurityContextAttrName = "SPRING_SECURITY_CONTEXT";
    private boolean cacheSecurityContext;

    public void setSpringSecurityContextAttrName(String springSecurityContextAttrName) {
        Assert.hasText((String)springSecurityContextAttrName, (String)"springSecurityContextAttrName cannot be null or empty");
        this.springSecurityContextAttrName = springSecurityContextAttrName;
    }

    public void setCacheSecurityContext(boolean cacheSecurityContext) {
        this.cacheSecurityContext = cacheSecurityContext;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return exchange.getSession().doOnNext(session -> {
            if (context == null) {
                session.getAttributes().remove(this.springSecurityContextAttrName);
                logger.debug((Object)LogMessage.format((String)"Removed SecurityContext stored in WebSession: '%s'", (Object)session));
            } else {
                session.getAttributes().put(this.springSecurityContextAttrName, context);
                logger.debug((Object)LogMessage.format((String)"Saved SecurityContext '%s' in WebSession: '%s'", (Object)context, (Object)session));
            }
        }).flatMap(WebSession::changeSessionId);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        Mono result = exchange.getSession().flatMap(session -> {
            SecurityContext context = (SecurityContext)session.getAttribute(this.springSecurityContextAttrName);
            logger.debug((Object)(context != null ? LogMessage.format((String)"Found SecurityContext '%s' in WebSession: '%s'", (Object)context, (Object)session) : LogMessage.format((String)"No SecurityContext found in WebSession: '%s'", (Object)session)));
            return Mono.justOrEmpty((Object)context);
        });
        return this.cacheSecurityContext ? result.cache() : result;
    }
}

