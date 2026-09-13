/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.x500.AttributeTypeAndValue
 *  org.bouncycastle.asn1.x500.RDN
 *  org.bouncycastle.asn1.x500.X500Name
 */
package org.cryptacular.x509.dn;

import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.X500Name;
import org.cryptacular.x509.dn.Attributes;
import org.cryptacular.x509.dn.RDN;
import org.cryptacular.x509.dn.RDNSequence;

public class NameReader {
    private final X509Certificate certificate;

    public NameReader(X509Certificate cert) {
        if (cert == null) {
            throw new IllegalArgumentException("Certificate cannot be null.");
        }
        this.certificate = cert;
    }

    public RDNSequence readSubject() {
        return NameReader.readX500Principal(this.certificate.getSubjectX500Principal());
    }

    public RDNSequence readIssuer() {
        return NameReader.readX500Principal(this.certificate.getIssuerX500Principal());
    }

    public static RDNSequence readX500Principal(X500Principal principal) {
        X500Name name = X500Name.getInstance((Object)principal.getEncoded());
        RDNSequence sequence = new RDNSequence();
        for (org.bouncycastle.asn1.x500.RDN rdn : name.getRDNs()) {
            Attributes attributes = new Attributes();
            for (AttributeTypeAndValue tv : rdn.getTypesAndValues()) {
                attributes.add(tv.getType().getId(), tv.getValue().toString());
            }
            sequence.add(new RDN(attributes));
        }
        return sequence;
    }
}

