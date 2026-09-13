/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.web.authentication.WebAuthenticationDetails
 *  org.springframework.security.web.util.UrlUtils
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.web.authentication;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

final class DefaultServiceAuthenticationDetails
extends WebAuthenticationDetails
implements ServiceAuthenticationDetails {
    private static final long serialVersionUID = 6192409090610517700L;
    private final String serviceUrl;

    DefaultServiceAuthenticationDetails(String casService, HttpServletRequest request, Pattern artifactPattern) throws MalformedURLException {
        super(request);
        URL casServiceUrl = new URL(casService);
        int port = DefaultServiceAuthenticationDetails.getServicePort(casServiceUrl);
        String query = this.getQueryString(request, artifactPattern);
        this.serviceUrl = UrlUtils.buildFullRequestUrl((String)casServiceUrl.getProtocol(), (String)casServiceUrl.getHost(), (int)port, (String)request.getRequestURI(), (String)query);
    }

    @Override
    public String getServiceUrl() {
        return this.serviceUrl;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof DefaultServiceAuthenticationDetails)) {
            return false;
        }
        ServiceAuthenticationDetails that = (ServiceAuthenticationDetails)obj;
        return this.serviceUrl.equals(that.getServiceUrl());
    }

    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + this.serviceUrl.hashCode();
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        result.append("ServiceUrl: ");
        result.append(this.serviceUrl);
        return result.toString();
    }

    private String getQueryString(HttpServletRequest request, Pattern artifactPattern) {
        String query = request.getQueryString();
        if (query == null) {
            return null;
        }
        String result = artifactPattern.matcher(query).replaceFirst("");
        if (result.length() == 0) {
            return null;
        }
        return result.startsWith("&") ? result.substring(1) : result;
    }

    static Pattern createArtifactPattern(String artifactParameterName) {
        Assert.hasLength((String)artifactParameterName, (String)"artifactParameterName is expected to have a length");
        return Pattern.compile("&?" + Pattern.quote(artifactParameterName) + "=[^&]*");
    }

    private static int getServicePort(URL casServiceUrl) {
        int port = casServiceUrl.getPort();
        if (port == -1) {
            port = casServiceUrl.getDefaultPort();
        }
        return port;
    }
}

