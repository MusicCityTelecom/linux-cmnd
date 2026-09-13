/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.authentication.preauth.x509;

import java.security.cert.X509Certificate;

public interface X509PrincipalExtractor {
    public Object extractPrincipal(X509Certificate var1);
}

