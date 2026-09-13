/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.x500.X500Name
 */
package org.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.security.cert.X509CertSelector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cms.KeyTransRecipientId;
import org.bouncycastle.cms.SignerId;

public class JcaSelectorConverter {
    public SignerId getSignerId(X509CertSelector x509CertSelector) {
        try {
            if (x509CertSelector.getSubjectKeyIdentifier() != null) {
                return new SignerId(X500Name.getInstance((Object)x509CertSelector.getIssuerAsBytes()), x509CertSelector.getSerialNumber(), ASN1OctetString.getInstance((Object)x509CertSelector.getSubjectKeyIdentifier()).getOctets());
            }
            return new SignerId(X500Name.getInstance((Object)x509CertSelector.getIssuerAsBytes()), x509CertSelector.getSerialNumber());
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("unable to convert issuer: " + iOException.getMessage());
        }
    }

    public KeyTransRecipientId getKeyTransRecipientId(X509CertSelector x509CertSelector) {
        try {
            if (x509CertSelector.getSubjectKeyIdentifier() != null) {
                return new KeyTransRecipientId(X500Name.getInstance((Object)x509CertSelector.getIssuerAsBytes()), x509CertSelector.getSerialNumber(), ASN1OctetString.getInstance((Object)x509CertSelector.getSubjectKeyIdentifier()).getOctets());
            }
            return new KeyTransRecipientId(X500Name.getInstance((Object)x509CertSelector.getIssuerAsBytes()), x509CertSelector.getSerialNumber());
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("unable to convert issuer: " + iOException.getMessage());
        }
    }
}

