/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.x500.X500Name
 *  org.bouncycastle.asn1.x509.GeneralName
 *  org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
 */
package org.bouncycastle.cert.crmf.jcajce;

import java.math.BigInteger;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.crmf.CertificateRequestMessageBuilder;

public class JcaCertificateRequestMessageBuilder
extends CertificateRequestMessageBuilder {
    public JcaCertificateRequestMessageBuilder(BigInteger bigInteger) {
        super(bigInteger);
    }

    public JcaCertificateRequestMessageBuilder setIssuer(X500Principal x500Principal) {
        if (x500Principal != null) {
            this.setIssuer(X500Name.getInstance((Object)x500Principal.getEncoded()));
        }
        return this;
    }

    public JcaCertificateRequestMessageBuilder setSubject(X500Principal x500Principal) {
        if (x500Principal != null) {
            this.setSubject(X500Name.getInstance((Object)x500Principal.getEncoded()));
        }
        return this;
    }

    public JcaCertificateRequestMessageBuilder setAuthInfoSender(X500Principal x500Principal) {
        if (x500Principal != null) {
            this.setAuthInfoSender(new GeneralName(X500Name.getInstance((Object)x500Principal.getEncoded())));
        }
        return this;
    }

    public JcaCertificateRequestMessageBuilder setPublicKey(PublicKey publicKey) {
        this.setPublicKey(SubjectPublicKeyInfo.getInstance((Object)publicKey.getEncoded()));
        return this;
    }
}

