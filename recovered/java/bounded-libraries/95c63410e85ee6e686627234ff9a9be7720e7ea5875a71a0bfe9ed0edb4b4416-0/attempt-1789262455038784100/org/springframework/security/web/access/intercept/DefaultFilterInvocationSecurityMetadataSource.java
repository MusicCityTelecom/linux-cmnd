/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.access.ConfigAttribute
 */
package org.springframework.security.web.access.intercept;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class DefaultFilterInvocationSecurityMetadataSource
implements FilterInvocationSecurityMetadataSource {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    public DefaultFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
        this.requestMap = requestMap;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        HashSet<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) {
        HttpServletRequest request = ((FilterInvocation)object).getRequest();
        int count = 0;
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : this.requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
            if (!this.logger.isTraceEnabled()) continue;
            this.logger.trace((Object)LogMessage.format((String)"Did not match request to %s - %s (%d/%d)", (Object)entry.getKey(), entry.getValue(), (Object)(++count), (Object)this.requestMap.size()));
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

