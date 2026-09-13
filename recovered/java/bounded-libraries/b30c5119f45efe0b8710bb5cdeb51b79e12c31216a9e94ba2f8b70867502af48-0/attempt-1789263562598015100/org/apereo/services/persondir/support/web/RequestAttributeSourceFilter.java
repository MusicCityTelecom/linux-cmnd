/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.apereo.services.persondir.support.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apereo.services.persondir.support.IAdditionalDescriptors;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class RequestAttributeSourceFilter
extends GenericFilterBean {
    private Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    private String usernameAttribute;
    private Map<String, Set<String>> cookieAttributeMapping = Collections.emptyMap();
    private Map<String, Set<String>> headerAttributeMapping = Collections.emptyMap();
    private Map<String, Set<String>> parameterAttributeMapping = Collections.emptyMap();
    private Map<String, Set<String>> requestAttributeMapping = Collections.emptyMap();
    private Set<String> headersToIgnoreSemicolons = new HashSet<String>(Arrays.asList("User-Agent"));
    private IAdditionalDescriptors additionalDescriptors;
    private String remoteUserAttribute;
    private String remoteAddrAttribute;
    private String remoteHostAttribute;
    private String serverNameAttribute;
    private String serverPortAttribute;
    private boolean clearExistingAttributes = false;
    private String referringParameterName;
    private String urlCharacterEncoding = StandardCharsets.UTF_8.name();
    private ProcessingPosition processingPosition = ProcessingPosition.POST;

    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    public void setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
    }

    public String getRemoteUserAttribute() {
        return this.remoteUserAttribute;
    }

    public void setRemoteUserAttribute(String remoteUserAttribute) {
        this.remoteUserAttribute = remoteUserAttribute;
    }

    public String getRemoteAddrAttribute() {
        return this.remoteAddrAttribute;
    }

    public void setRemoteAddrAttribute(String remoteAddrAttribute) {
        this.remoteAddrAttribute = remoteAddrAttribute;
    }

    public String getRemoteHostAttribute() {
        return this.remoteHostAttribute;
    }

    public void setRemoteHostAttribute(String remoteHostAttribute) {
        this.remoteHostAttribute = remoteHostAttribute;
    }

    public String getServerNameAttribute() {
        return this.serverNameAttribute;
    }

    public void setServerNameAttribute(String serverNameAttribute) {
        this.serverNameAttribute = serverNameAttribute;
    }

    public String getServerPortAttribute() {
        return this.serverPortAttribute;
    }

    public void setServerPortAttribute(String serverPortAttribute) {
        this.serverPortAttribute = serverPortAttribute;
    }

    public IAdditionalDescriptors getAdditionalDescriptors() {
        return this.additionalDescriptors;
    }

    public void setAdditionalDescriptors(IAdditionalDescriptors additionalDescriptors) {
        this.additionalDescriptors = additionalDescriptors;
    }

    public boolean isClearExistingAttributes() {
        return this.clearExistingAttributes;
    }

    public void setClearExistingAttributes(boolean clearExistingAttributes) {
        this.clearExistingAttributes = clearExistingAttributes;
    }

    public ProcessingPosition getProcessingPosition() {
        return this.processingPosition;
    }

    public void setProcessingPosition(ProcessingPosition processingPosition) {
        this.processingPosition = processingPosition;
    }

    public void setCookieAttributeMapping(Map<String, ?> cookieAttributeMapping) {
        this.cookieAttributeMapping = this.makeMapValueSetOfStrings(cookieAttributeMapping);
    }

    public Map<String, Set<String>> getCookieAttributeMapping() {
        return this.cookieAttributeMapping;
    }

    public Map<String, Set<String>> getHeaderAttributeMapping() {
        return this.headerAttributeMapping;
    }

    public void setHeaderAttributeMapping(Map<String, ?> headerAttributeMapping) {
        this.headerAttributeMapping = this.makeMapValueSetOfStrings(headerAttributeMapping);
    }

    public Map<String, Set<String>> getRequestAttributeMapping() {
        return this.requestAttributeMapping;
    }

    public void setRequestAttributeMapping(Map<String, ?> requestAttributeMapping) {
        this.requestAttributeMapping = this.makeMapValueSetOfStrings(requestAttributeMapping);
    }

    public Map<String, Set<String>> getParameterAttributeMapping() {
        return this.parameterAttributeMapping;
    }

    public void setParameterAttributeMapping(Map<String, ?> parameterAttributeMapping) {
        this.parameterAttributeMapping = this.makeMapValueSetOfStrings(parameterAttributeMapping);
    }

    private Map<String, Set<String>> makeMapValueSetOfStrings(Map<String, ?> attributeMapping) {
        Map<String, Set<String>> parsedParameterAttributeMapping = MultivaluedPersonAttributeUtils.parseAttributeToAttributeMapping(attributeMapping);
        if (parsedParameterAttributeMapping.containsKey("")) {
            throw new IllegalArgumentException("The map from attribute names to attributes must not have any empty keys.");
        }
        return parsedParameterAttributeMapping;
    }

    public String getReferringParameterName() {
        return this.referringParameterName;
    }

    public void setReferringParameterName(String referringParameterName) {
        this.referringParameterName = referringParameterName;
    }

    public String getUrlCharacterEncoding() {
        return this.urlCharacterEncoding;
    }

    public void setUrlCharacterEncoding(String urlCharacterEncoding) {
        this.urlCharacterEncoding = urlCharacterEncoding;
    }

    public Set<String> getHeadersToIgnoreSemicolons() {
        return this.headersToIgnoreSemicolons;
    }

    public void setHeadersToIgnoreSemicolons(Set<String> headersToIgnoreSemicolons) {
        this.headersToIgnoreSemicolons = headersToIgnoreSemicolons;
    }

    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        if (ProcessingPosition.PRE == this.processingPosition || ProcessingPosition.BOTH == this.processingPosition) {
            this.doProcessing(servletRequest);
        }
        chain.doFilter(servletRequest, servletResponse);
        if (ProcessingPosition.POST == this.processingPosition || ProcessingPosition.BOTH == this.processingPosition) {
            this.doProcessing(servletRequest);
        }
    }

    private void doProcessing(ServletRequest servletRequest) {
        if (servletRequest instanceof HttpServletRequest) {
            String username;
            HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
            LinkedHashMap<String, List<Object>> attributes = new LinkedHashMap<String, List<Object>>();
            this.addRequestProperties(httpServletRequest, attributes);
            this.addRequestCookies(httpServletRequest, attributes);
            this.addRequestHeaders(httpServletRequest, attributes);
            this.addRequestParameters(httpServletRequest, attributes);
            this.addRequestAttributes(httpServletRequest, attributes);
            List usernameAttributes = (List)attributes.get(this.usernameAttribute);
            if (usernameAttributes == null || usernameAttributes.isEmpty() || usernameAttributes.get(0) == null) {
                this.logger.info("No username found as attribute '{}' among {}", (Object)this.usernameAttribute, attributes);
                username = null;
            } else {
                username = usernameAttributes.get(0).toString();
            }
            this.logger.debug("Adding attributes for user {}, attributes {}", (Object)username, attributes);
            this.additionalDescriptors.setName(username);
            if (this.clearExistingAttributes) {
                this.additionalDescriptors.setAttributes(attributes);
            } else {
                this.additionalDescriptors.addAttributes(attributes);
            }
        }
    }

    protected void addRequestProperties(HttpServletRequest httpServletRequest, Map<String, List<Object>> attributes) {
        if (this.remoteUserAttribute != null) {
            String remoteUser = httpServletRequest.getRemoteUser();
            attributes.put(this.remoteUserAttribute, this.list(remoteUser));
        }
        if (this.remoteAddrAttribute != null) {
            String remoteAddr = httpServletRequest.getRemoteAddr();
            attributes.put(this.remoteAddrAttribute, this.list(remoteAddr));
        }
        if (this.remoteHostAttribute != null) {
            String remoteHost = httpServletRequest.getRemoteHost();
            attributes.put(this.remoteHostAttribute, this.list(remoteHost));
        }
        if (this.serverNameAttribute != null) {
            String serverName = httpServletRequest.getServerName();
            attributes.put(this.serverNameAttribute, this.list(serverName));
        }
        if (this.serverPortAttribute != null) {
            int serverPort = httpServletRequest.getServerPort();
            attributes.put(this.serverPortAttribute, this.list(serverPort));
        }
    }

    protected void addRequestCookies(HttpServletRequest httpServletRequest, Map<String, List<Object>> attributes) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if (!this.cookieAttributeMapping.containsKey(cookieName)) continue;
            for (String attributeName : this.cookieAttributeMapping.get(cookieName)) {
                attributes.put(attributeName, this.list(cookie.getValue()));
            }
        }
    }

    protected void addRequestHeaders(HttpServletRequest httpServletRequest, Map<String, List<Object>> attributes) {
        for (Map.Entry<String, Set<String>> headerAttributeEntry : this.headerAttributeMapping.entrySet()) {
            String headerName = headerAttributeEntry.getKey();
            String value = httpServletRequest.getHeader(headerName);
            if (value == null) continue;
            for (String attributeName : headerAttributeEntry.getValue()) {
                attributes.put(attributeName, this.headersToIgnoreSemicolons.contains(headerName) ? this.list(value) : RequestAttributeSourceFilter.splitOnSemiColonHandlingBackslashEscaping(value));
            }
        }
    }

    protected void addRequestAttributes(HttpServletRequest httpServletRequest, Map<String, List<Object>> attributes) {
        for (Map.Entry<String, Set<String>> attributeMapping : this.requestAttributeMapping.entrySet()) {
            String attributeName = attributeMapping.getKey();
            Object value = httpServletRequest.getAttribute(attributeName);
            if (value != null) {
                if (value instanceof String) {
                    for (String attrName : attributeMapping.getValue()) {
                        attributes.put(attrName, RequestAttributeSourceFilter.splitOnSemiColonHandlingBackslashEscaping((String)value));
                    }
                    continue;
                }
                this.logger.warn("Specified request attribute {} is not a String, is a {} so it is ignored", (Object)attributeName, value.getClass());
                continue;
            }
            this.logger.debug("Did not find request attribute {} in the request", (Object)attributeName);
        }
    }

    protected void addRequestParameters(HttpServletRequest httpServletRequest, Map<String, List<Object>> attributes) {
        if (this.referringParameterName != null && StringUtils.isNotBlank((CharSequence)httpServletRequest.getParameter(this.referringParameterName))) {
            String referringValue = httpServletRequest.getParameter(this.referringParameterName);
            Map<String, String> referringParameters = this.parseRequestParameterString(referringValue);
            for (Map.Entry<String, Set<String>> parameterMapping : this.parameterAttributeMapping.entrySet()) {
                String parameterName = parameterMapping.getKey();
                String value = referringParameters.get(parameterName);
                if (value == null) continue;
                for (String attributeName : parameterMapping.getValue()) {
                    attributes.put(attributeName, this.list(value));
                }
            }
        }
        for (Map.Entry<String, Set<String>> parameterMapping : this.parameterAttributeMapping.entrySet()) {
            String parameterName = parameterMapping.getKey();
            String value = httpServletRequest.getParameter(parameterName);
            if (value == null) continue;
            for (String attributeName : parameterMapping.getValue()) {
                attributes.put(attributeName, this.list(value));
            }
        }
    }

    private Map<String, String> parseRequestParameterString(String requestParameterString) {
        String[] parameterStrings;
        HashMap<String, String> parameters = new HashMap<String, String>();
        if (requestParameterString.indexOf("?") > 0) {
            requestParameterString = requestParameterString.substring(requestParameterString.indexOf("?") + 1);
        }
        for (String parameterString : parameterStrings = requestParameterString.trim().split("&")) {
            String[] parts = parameterString.split("=");
            if (parts.length > 1) {
                parameters.put(parts[0], parts[1]);
                continue;
            }
            this.logger.info("Ignoring encoded parameter {} in referring url parameter because it has no value", (Object)parts[0]);
        }
        return parameters;
    }

    private static List<Object> splitOnSemiColonHandlingBackslashEscaping(String in) {
        String[] splitStringArr;
        LinkedList<Object> result = new LinkedList<Object>();
        int i = 1;
        Object prefix = "";
        for (String s : splitStringArr = in.split(";")) {
            String s2 = s.replaceFirst("\\\\$", ";");
            if (s.equals(s2) || i == splitStringArr.length) {
                result.add((String)prefix + s2);
                prefix = "";
            } else {
                prefix = (String)prefix + s2;
            }
            ++i;
        }
        return result;
    }

    private List<Object> list(Object value) {
        return Collections.singletonList(value);
    }

    public static enum ProcessingPosition {
        PRE,
        POST,
        BOTH;

    }
}

