/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.authentication.preauth.x509;

import java.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor;

public class X509AuthenticationFilter
extends AbstractPreAuthenticatedProcessingFilter {
    private X509PrincipalExtractor principalExtractor = new SubjectDnX509PrincipalExtractor();

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        X509Certificate cert = this.extractClientCertificate(request);
        return cert != null ? this.principalExtractor.extractPrincipal(cert) : null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return this.extractClientCertificate(request);
    }

    private X509Certificate extractClientCertificate(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
        if (certs != null && certs.length > 0) {
            this.logger.debug((Object)LogMessage.format((String)"X.509 client authentication certificate:%s", (Object)certs[0]));
            return certs[0];
        }
        this.logger.debug((Object)"No client certificate found in request.");
        return null;
    }

    public void setPrincipalExtractor(X509PrincipalExtractor principalExtractor) {
        this.principalExtractor = principalExtractor;
    }
}

