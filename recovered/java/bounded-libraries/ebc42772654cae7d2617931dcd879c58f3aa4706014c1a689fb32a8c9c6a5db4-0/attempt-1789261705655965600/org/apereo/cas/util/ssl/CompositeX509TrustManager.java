/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.ssl;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.net.ssl.X509TrustManager;
import lombok.Generated;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeX509TrustManager
implements X509TrustManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeX509TrustManager.class);
    private final List<X509TrustManager> trustManagers;

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        boolean trusted = this.trustManagers.stream().anyMatch(trustManager -> {
            try {
                trustManager.checkClientTrusted(chain, authType);
                return true;
            }
            catch (CertificateException e) {
                String msg = "Unable to trust the client certificates [%s] for auth type [%s]: [%s]";
                LOGGER.debug(String.format("Unable to trust the client certificates [%s] for auth type [%s]: [%s]", Arrays.stream(chain).map(Certificate::toString).collect(Collectors.toSet()), authType, e.getMessage()), (Throwable)e);
                return false;
            }
        });
        if (!trusted) {
            throw new CertificateException("None of the TrustManagers can trust this client certificate chain");
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        boolean trusted = this.trustManagers.stream().anyMatch(trustManager -> {
            try {
                trustManager.checkServerTrusted(chain, authType);
                return true;
            }
            catch (CertificateException e) {
                String msg = "Unable to trust the server certificates [%s] for auth type [%s]: [%s]";
                LOGGER.debug(String.format("Unable to trust the server certificates [%s] for auth type [%s]: [%s]", Arrays.stream(chain).map(Certificate::toString).collect(Collectors.toSet()), authType, e.getMessage()), (Throwable)e);
                return false;
            }
        });
        if (!trusted) {
            throw new CertificateException("None of the TrustManagers trust this server certificate chain");
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        ArrayList certificates = new ArrayList(this.trustManagers.size());
        this.trustManagers.forEach(trustManager -> certificates.addAll(CollectionUtils.wrapList(trustManager.getAcceptedIssuers())));
        return (X509Certificate[])certificates.toArray(X509Certificate[]::new);
    }

    @Generated
    public CompositeX509TrustManager(List<X509TrustManager> trustManagers) {
        this.trustManagers = trustManagers;
    }
}

