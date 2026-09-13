/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 *  org.cryptacular.util.CertUtil
 *  org.springframework.core.io.InputStreamResource
 *  org.springframework.core.io.InputStreamSource
 */
package org.apereo.cas.util.crypto;

import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Generated;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apereo.cas.util.DateTimeUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.cryptacular.util.CertUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;

public final class CertUtils {
    public static final String X509_CERTIFICATE_TYPE = "X509";

    public static boolean isExpired(X509CRL crl) {
        return CertUtils.isExpired(crl, ZonedDateTime.now(ZoneOffset.UTC));
    }

    public static boolean isExpired(X509CRL crl, ZonedDateTime reference) {
        return reference.isAfter(DateTimeUtils.zonedDateTimeOf(crl.getNextUpdate()));
    }

    public static X509Certificate readCertificate(InputStreamSource resource) {
        X509Certificate x509Certificate;
        block8: {
            InputStream in = resource.getInputStream();
            try {
                x509Certificate = CertUtil.readCertificate((InputStream)in);
                if (in == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    throw new IllegalArgumentException("Error reading certificate " + resource, e);
                }
            }
            in.close();
        }
        return x509Certificate;
    }

    public static X509Certificate readCertificate(InputStream resource) {
        return CertUtils.readCertificate((InputStreamSource)new InputStreamResource(resource));
    }

    public static String toString(X509Certificate cert) {
        return new ToStringBuilder((Object)cert, ToStringStyle.NO_CLASS_NAME_STYLE).append("subjectDn", (Object)cert.getSubjectDN()).append("serialNumber", (Object)cert.getSerialNumber()).build();
    }

    public static CertificateFactory getCertificateFactory() {
        return (CertificateFactory)FunctionUtils.doUnchecked(() -> CertificateFactory.getInstance(X509_CERTIFICATE_TYPE));
    }

    @Generated
    private CertUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

