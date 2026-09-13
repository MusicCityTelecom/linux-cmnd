/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.CertificateBase;
import org.bouncycastle.oer.its.CertificateType;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.oer.its.ToBeSignedCertificate;

public class ExplicitCertificate
extends CertificateBase {
    public ExplicitCertificate(ASN1Integer aSN1Integer, IssuerIdentifier issuerIdentifier, ToBeSignedCertificate toBeSignedCertificate, Signature signature) {
        super(aSN1Integer, CertificateType.Explicit, issuerIdentifier, toBeSignedCertificate, signature);
    }
}

