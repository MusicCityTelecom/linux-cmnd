/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.web.authentication;

import java.net.MalformedURLException;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.web.authentication.DefaultServiceAuthenticationDetails;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetails;
import org.springframework.util.Assert;

public class ServiceAuthenticationDetailsSource
implements AuthenticationDetailsSource<HttpServletRequest, ServiceAuthenticationDetails> {
    private final Pattern artifactPattern;
    private ServiceProperties serviceProperties;

    public ServiceAuthenticationDetailsSource(ServiceProperties serviceProperties) {
        this(serviceProperties, "ticket");
    }

    public ServiceAuthenticationDetailsSource(ServiceProperties serviceProperties, String artifactParameterName) {
        Assert.notNull((Object)serviceProperties, (String)"serviceProperties cannot be null");
        this.serviceProperties = serviceProperties;
        this.artifactPattern = DefaultServiceAuthenticationDetails.createArtifactPattern(artifactParameterName);
    }

    public ServiceAuthenticationDetails buildDetails(HttpServletRequest context) {
        try {
            return new DefaultServiceAuthenticationDetails(this.serviceProperties.getService(), context, this.artifactPattern);
        }
        catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

