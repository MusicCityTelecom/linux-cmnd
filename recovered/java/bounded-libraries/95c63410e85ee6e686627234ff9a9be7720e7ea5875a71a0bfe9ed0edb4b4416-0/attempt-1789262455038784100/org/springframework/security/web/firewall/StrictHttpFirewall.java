/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpMethod
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.firewall;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.FirewalledResponse;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.util.Assert;

public class StrictHttpFirewall
implements HttpFirewall {
    private static final Set<String> ALLOW_ANY_HTTP_METHOD = Collections.emptySet();
    private static final String ENCODED_PERCENT = "%25";
    private static final String PERCENT = "%";
    private static final List<String> FORBIDDEN_ENCODED_PERIOD = Collections.unmodifiableList(Arrays.asList("%2e", "%2E"));
    private static final List<String> FORBIDDEN_SEMICOLON = Collections.unmodifiableList(Arrays.asList(";", "%3b", "%3B"));
    private static final List<String> FORBIDDEN_FORWARDSLASH = Collections.unmodifiableList(Arrays.asList("%2f", "%2F"));
    private static final List<String> FORBIDDEN_DOUBLE_FORWARDSLASH = Collections.unmodifiableList(Arrays.asList("//", "%2f%2f", "%2f%2F", "%2F%2f", "%2F%2F"));
    private static final List<String> FORBIDDEN_BACKSLASH = Collections.unmodifiableList(Arrays.asList("\\", "%5c", "%5C"));
    private static final List<String> FORBIDDEN_NULL = Collections.unmodifiableList(Arrays.asList("\u0000", "%00"));
    private static final List<String> FORBIDDEN_LF = Collections.unmodifiableList(Arrays.asList("\n", "%0a", "%0A"));
    private static final List<String> FORBIDDEN_CR = Collections.unmodifiableList(Arrays.asList("\r", "%0d", "%0D"));
    private static final List<String> FORBIDDEN_LINE_SEPARATOR = Collections.unmodifiableList(Arrays.asList("\u2028"));
    private static final List<String> FORBIDDEN_PARAGRAPH_SEPARATOR = Collections.unmodifiableList(Arrays.asList("\u2029"));
    private Set<String> encodedUrlBlocklist = new HashSet<String>();
    private Set<String> decodedUrlBlocklist = new HashSet<String>();
    private Set<String> allowedHttpMethods = StrictHttpFirewall.createDefaultAllowedHttpMethods();
    private Predicate<String> allowedHostnames = hostname -> true;
    private static final Pattern ASSIGNED_AND_NOT_ISO_CONTROL_PATTERN = Pattern.compile("[\\p{IsAssigned}&&[^\\p{IsControl}]]*");
    private static final Predicate<String> ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE = s -> ASSIGNED_AND_NOT_ISO_CONTROL_PATTERN.matcher((CharSequence)s).matches();
    private Predicate<String> allowedHeaderNames = ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE;
    private Predicate<String> allowedHeaderValues = ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE;
    private Predicate<String> allowedParameterNames = ASSIGNED_AND_NOT_ISO_CONTROL_PREDICATE;
    private Predicate<String> allowedParameterValues = value -> true;

    public StrictHttpFirewall() {
        this.urlBlocklistsAddAll(FORBIDDEN_SEMICOLON);
        this.urlBlocklistsAddAll(FORBIDDEN_FORWARDSLASH);
        this.urlBlocklistsAddAll(FORBIDDEN_DOUBLE_FORWARDSLASH);
        this.urlBlocklistsAddAll(FORBIDDEN_BACKSLASH);
        this.urlBlocklistsAddAll(FORBIDDEN_NULL);
        this.urlBlocklistsAddAll(FORBIDDEN_LF);
        this.urlBlocklistsAddAll(FORBIDDEN_CR);
        this.encodedUrlBlocklist.add(ENCODED_PERCENT);
        this.encodedUrlBlocklist.addAll(FORBIDDEN_ENCODED_PERIOD);
        this.decodedUrlBlocklist.add(PERCENT);
        this.decodedUrlBlocklist.addAll(FORBIDDEN_LINE_SEPARATOR);
        this.decodedUrlBlocklist.addAll(FORBIDDEN_PARAGRAPH_SEPARATOR);
    }

    public void setUnsafeAllowAnyHttpMethod(boolean unsafeAllowAnyHttpMethod) {
        this.allowedHttpMethods = unsafeAllowAnyHttpMethod ? ALLOW_ANY_HTTP_METHOD : StrictHttpFirewall.createDefaultAllowedHttpMethods();
    }

    public void setAllowedHttpMethods(Collection<String> allowedHttpMethods) {
        Assert.notNull(allowedHttpMethods, (String)"allowedHttpMethods cannot be null");
        this.allowedHttpMethods = allowedHttpMethods != ALLOW_ANY_HTTP_METHOD ? new HashSet<String>(allowedHttpMethods) : ALLOW_ANY_HTTP_METHOD;
    }

    public void setAllowSemicolon(boolean allowSemicolon) {
        if (allowSemicolon) {
            this.urlBlocklistsRemoveAll(FORBIDDEN_SEMICOLON);
        } else {
            this.urlBlocklistsAddAll(FORBIDDEN_SEMICOLON);
        }
    }

    public void setAllowUrlEncodedSlash(boolean allowUrlEncodedSlash) {
        if (allowUrlEncodedSlash) {
            this.urlBlocklistsRemoveAll(FORBIDDEN_FORWARDSLASH);
        } else {
            this.urlBlocklistsAddAll(FORBIDDEN_FORWARDSLASH);
        }
    }

    public void setAllowUrlEncodedDoubleSlash(boolean allowUrlEncodedDoubleSlash) {
        if (allowUrlEncodedDoubleSlash) {
            this.urlBlocklistsRemoveAll(FORBIDDEN_DOUBLE_FORWARDSLASH);
        } else {
            this.urlBlocklistsAddAll(FORBIDDEN_DOUBLE_FORWARDSLASH);
        }
    }

    public void setAllowUrlEncodedPeriod(boolean allowUrlEncodedPeriod) {
        if (allowUrlEncodedPeriod) {
            this.encodedUrlBlocklist.removeAll(FORBIDDEN_ENCODED_PERIOD);
        } else {
            this.encodedUrlBlocklist.addAll(FORBIDDEN_ENCODED_PERIOD);
        }
    }

    public void setAllowBackSlash(boolean allowBackSlash) {
        if (allowBackSlash) {
            this.urlBlocklistsRemoveAll(FORBIDDEN_BACKSLASH);
        } else {
            this.urlBlocklistsAddAll(FORBIDDEN_BACKSLASH);
        }
    }

    public void setAllowNull(boolean allowNull) {
        if (allowNull) {
            this.urlBlocklistsRemoveAll(FORBIDDEN_NULL);
        } else {
            this.urlBlocklistsAddAll(FORBIDDEN_NULL);
        }
    }

    public void setAllowUrlEncodedPercent(boolean allowUrlEncodedPercent) {
        if (allowUrlEncodedPercent) {
            this.encodedUrlBlocklist.remove(ENCODED_PERCENT);
            this.decodedUrlBlocklist.remove(PERCENT);
        } else {
            this.encodedUrlBlocklist.add(ENCODED_PERCENT);
            this.decodedUrlBlocklist.add(PERCENT);
        }
    }

    public void setAllowUrlEncodedCarriageReturn(boolean allowUrlEncodedCarriageReturn) {
        if (allowUrlEncodedCarriageReturn) {
            this.urlBlocklistsRemoveAll(FORBIDDEN_CR);
        } else {
            this.urlBlocklistsAddAll(FORBIDDEN_CR);
        }
    }

    public void setAllowUrlEncodedLineFeed(boolean allowUrlEncodedLineFeed) {
        if (allowUrlEncodedLineFeed) {
            this.urlBlocklistsRemoveAll(FORBIDDEN_LF);
        } else {
            this.urlBlocklistsAddAll(FORBIDDEN_LF);
        }
    }

    public void setAllowUrlEncodedParagraphSeparator(boolean allowUrlEncodedParagraphSeparator) {
        if (allowUrlEncodedParagraphSeparator) {
            this.decodedUrlBlocklist.removeAll(FORBIDDEN_PARAGRAPH_SEPARATOR);
        } else {
            this.decodedUrlBlocklist.addAll(FORBIDDEN_PARAGRAPH_SEPARATOR);
        }
    }

    public void setAllowUrlEncodedLineSeparator(boolean allowUrlEncodedLineSeparator) {
        if (allowUrlEncodedLineSeparator) {
            this.decodedUrlBlocklist.removeAll(FORBIDDEN_LINE_SEPARATOR);
        } else {
            this.decodedUrlBlocklist.addAll(FORBIDDEN_LINE_SEPARATOR);
        }
    }

    public void setAllowedHeaderNames(Predicate<String> allowedHeaderNames) {
        Assert.notNull(allowedHeaderNames, (String)"allowedHeaderNames cannot be null");
        this.allowedHeaderNames = allowedHeaderNames;
    }

    public void setAllowedHeaderValues(Predicate<String> allowedHeaderValues) {
        Assert.notNull(allowedHeaderValues, (String)"allowedHeaderValues cannot be null");
        this.allowedHeaderValues = allowedHeaderValues;
    }

    public void setAllowedParameterNames(Predicate<String> allowedParameterNames) {
        Assert.notNull(allowedParameterNames, (String)"allowedParameterNames cannot be null");
        this.allowedParameterNames = allowedParameterNames;
    }

    public void setAllowedParameterValues(Predicate<String> allowedParameterValues) {
        Assert.notNull(allowedParameterValues, (String)"allowedParameterValues cannot be null");
        this.allowedParameterValues = allowedParameterValues;
    }

    public void setAllowedHostnames(Predicate<String> allowedHostnames) {
        Assert.notNull(allowedHostnames, (String)"allowedHostnames cannot be null");
        this.allowedHostnames = allowedHostnames;
    }

    private void urlBlocklistsAddAll(Collection<String> values) {
        this.encodedUrlBlocklist.addAll(values);
        this.decodedUrlBlocklist.addAll(values);
    }

    private void urlBlocklistsRemoveAll(Collection<String> values) {
        this.encodedUrlBlocklist.removeAll(values);
        this.decodedUrlBlocklist.removeAll(values);
    }

    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        this.rejectForbiddenHttpMethod(request);
        this.rejectedBlocklistedUrls(request);
        this.rejectedUntrustedHosts(request);
        if (!StrictHttpFirewall.isNormalized(request)) {
            throw new RequestRejectedException("The request was rejected because the URL was not normalized.");
        }
        this.rejectNonPrintableAsciiCharactersInFieldName(request.getRequestURI(), "requestURI");
        return new StrictFirewalledRequest(request);
    }

    private void rejectNonPrintableAsciiCharactersInFieldName(String toCheck, String propertyName) {
        if (!StrictHttpFirewall.containsOnlyPrintableAsciiCharacters(toCheck)) {
            throw new RequestRejectedException(String.format("The %s was rejected because it can only contain printable ASCII characters.", propertyName));
        }
    }

    private void rejectForbiddenHttpMethod(HttpServletRequest request) {
        if (this.allowedHttpMethods == ALLOW_ANY_HTTP_METHOD) {
            return;
        }
        if (!this.allowedHttpMethods.contains(request.getMethod())) {
            throw new RequestRejectedException("The request was rejected because the HTTP method \"" + request.getMethod() + "\" was not included within the list of allowed HTTP methods " + this.allowedHttpMethods);
        }
    }

    private void rejectedBlocklistedUrls(HttpServletRequest request) {
        for (String forbidden : this.encodedUrlBlocklist) {
            if (!StrictHttpFirewall.encodedUrlContains(request, forbidden)) continue;
            throw new RequestRejectedException("The request was rejected because the URL contained a potentially malicious String \"" + forbidden + "\"");
        }
        for (String forbidden : this.decodedUrlBlocklist) {
            if (!StrictHttpFirewall.decodedUrlContains(request, forbidden)) continue;
            throw new RequestRejectedException("The request was rejected because the URL contained a potentially malicious String \"" + forbidden + "\"");
        }
    }

    private void rejectedUntrustedHosts(HttpServletRequest request) {
        String serverName = request.getServerName();
        if (serverName != null && !this.allowedHostnames.test(serverName)) {
            throw new RequestRejectedException("The request was rejected because the domain " + serverName + " is untrusted.");
        }
    }

    @Override
    public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
        return new FirewalledResponse(response);
    }

    private static Set<String> createDefaultAllowedHttpMethods() {
        HashSet<String> result = new HashSet<String>();
        result.add(HttpMethod.DELETE.name());
        result.add(HttpMethod.GET.name());
        result.add(HttpMethod.HEAD.name());
        result.add(HttpMethod.OPTIONS.name());
        result.add(HttpMethod.PATCH.name());
        result.add(HttpMethod.POST.name());
        result.add(HttpMethod.PUT.name());
        return result;
    }

    private static boolean isNormalized(HttpServletRequest request) {
        if (!StrictHttpFirewall.isNormalized(request.getRequestURI())) {
            return false;
        }
        if (!StrictHttpFirewall.isNormalized(request.getContextPath())) {
            return false;
        }
        if (!StrictHttpFirewall.isNormalized(request.getServletPath())) {
            return false;
        }
        return StrictHttpFirewall.isNormalized(request.getPathInfo());
    }

    private static boolean encodedUrlContains(HttpServletRequest request, String value) {
        if (StrictHttpFirewall.valueContains(request.getContextPath(), value)) {
            return true;
        }
        return StrictHttpFirewall.valueContains(request.getRequestURI(), value);
    }

    private static boolean decodedUrlContains(HttpServletRequest request, String value) {
        if (StrictHttpFirewall.valueContains(request.getServletPath(), value)) {
            return true;
        }
        return StrictHttpFirewall.valueContains(request.getPathInfo(), value);
    }

    private static boolean containsOnlyPrintableAsciiCharacters(String uri) {
        if (uri == null) {
            return true;
        }
        int length = uri.length();
        for (int i = 0; i < length; ++i) {
            char ch = uri.charAt(i);
            if (ch >= ' ' && ch <= '~') continue;
            return false;
        }
        return true;
    }

    private static boolean valueContains(String value, String contains) {
        return value != null && value.contains(contains);
    }

    private static boolean isNormalized(String path) {
        if (path == null) {
            return true;
        }
        int i = path.length();
        while (i > 0) {
            int slashIndex = path.lastIndexOf(47, i - 1);
            int gap = i - slashIndex;
            if (gap == 2 && path.charAt(slashIndex + 1) == '.') {
                return false;
            }
            if (gap == 3 && path.charAt(slashIndex + 1) == '.' && path.charAt(slashIndex + 2) == '.') {
                return false;
            }
            i = slashIndex;
        }
        return true;
    }

    public Set<String> getEncodedUrlBlocklist() {
        return this.encodedUrlBlocklist;
    }

    public Set<String> getDecodedUrlBlocklist() {
        return this.decodedUrlBlocklist;
    }

    @Deprecated
    public Set<String> getEncodedUrlBlacklist() {
        return this.getEncodedUrlBlocklist();
    }

    public Set<String> getDecodedUrlBlacklist() {
        return this.getDecodedUrlBlocklist();
    }

    private class StrictFirewalledRequest
    extends FirewalledRequest {
        StrictFirewalledRequest(HttpServletRequest request) {
            super(request);
        }

        public long getDateHeader(String name) {
            if (name != null) {
                this.validateAllowedHeaderName(name);
            }
            return super.getDateHeader(name);
        }

        public int getIntHeader(String name) {
            if (name != null) {
                this.validateAllowedHeaderName(name);
            }
            return super.getIntHeader(name);
        }

        public String getHeader(String name) {
            String value;
            if (name != null) {
                this.validateAllowedHeaderName(name);
            }
            if ((value = super.getHeader(name)) != null) {
                this.validateAllowedHeaderValue(value);
            }
            return value;
        }

        public Enumeration<String> getHeaders(String name) {
            if (name != null) {
                this.validateAllowedHeaderName(name);
            }
            final Enumeration headers = super.getHeaders(name);
            return new Enumeration<String>(){

                @Override
                public boolean hasMoreElements() {
                    return headers.hasMoreElements();
                }

                @Override
                public String nextElement() {
                    String value = (String)headers.nextElement();
                    StrictFirewalledRequest.this.validateAllowedHeaderValue(value);
                    return value;
                }
            };
        }

        public Enumeration<String> getHeaderNames() {
            final Enumeration names = super.getHeaderNames();
            return new Enumeration<String>(){

                @Override
                public boolean hasMoreElements() {
                    return names.hasMoreElements();
                }

                @Override
                public String nextElement() {
                    String headerNames = (String)names.nextElement();
                    StrictFirewalledRequest.this.validateAllowedHeaderName(headerNames);
                    return headerNames;
                }
            };
        }

        public String getParameter(String name) {
            String value;
            if (name != null) {
                this.validateAllowedParameterName(name);
            }
            if ((value = super.getParameter(name)) != null) {
                this.validateAllowedParameterValue(value);
            }
            return value;
        }

        public Map<String, String[]> getParameterMap() {
            Map parameterMap = super.getParameterMap();
            for (Map.Entry entry : parameterMap.entrySet()) {
                String name = (String)entry.getKey();
                String[] values = (String[])entry.getValue();
                this.validateAllowedParameterName(name);
                for (String value : values) {
                    this.validateAllowedParameterValue(value);
                }
            }
            return parameterMap;
        }

        public Enumeration<String> getParameterNames() {
            final Enumeration paramaterNames = super.getParameterNames();
            return new Enumeration<String>(){

                @Override
                public boolean hasMoreElements() {
                    return paramaterNames.hasMoreElements();
                }

                @Override
                public String nextElement() {
                    String name = (String)paramaterNames.nextElement();
                    StrictFirewalledRequest.this.validateAllowedParameterName(name);
                    return name;
                }
            };
        }

        public String[] getParameterValues(String name) {
            String[] values;
            if (name != null) {
                this.validateAllowedParameterName(name);
            }
            if ((values = super.getParameterValues(name)) != null) {
                for (String value : values) {
                    this.validateAllowedParameterValue(value);
                }
            }
            return values;
        }

        private void validateAllowedHeaderName(String headerNames) {
            if (!StrictHttpFirewall.this.allowedHeaderNames.test(headerNames)) {
                throw new RequestRejectedException("The request was rejected because the header name \"" + headerNames + "\" is not allowed.");
            }
        }

        private void validateAllowedHeaderValue(String value) {
            if (!StrictHttpFirewall.this.allowedHeaderValues.test(value)) {
                throw new RequestRejectedException("The request was rejected because the header value \"" + value + "\" is not allowed.");
            }
        }

        private void validateAllowedParameterName(String name) {
            if (!StrictHttpFirewall.this.allowedParameterNames.test(name)) {
                throw new RequestRejectedException("The request was rejected because the parameter name \"" + name + "\" is not allowed.");
            }
        }

        private void validateAllowedParameterValue(String value) {
            if (!StrictHttpFirewall.this.allowedParameterValues.test(value)) {
                throw new RequestRejectedException("The request was rejected because the parameter value \"" + value + "\" is not allowed.");
            }
        }

        @Override
        public void reset() {
        }
    }
}

