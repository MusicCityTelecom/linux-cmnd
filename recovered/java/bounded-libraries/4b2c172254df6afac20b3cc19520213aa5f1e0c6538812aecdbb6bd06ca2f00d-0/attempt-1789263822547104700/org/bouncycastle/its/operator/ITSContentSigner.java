/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.its.operator;

import java.io.OutputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.its.ITSCertificate;

public interface ITSContentSigner {
    public OutputStream getOutputStream();

    public byte[] getSignature();

    public ITSCertificate getAssociatedCertificate();

    public byte[] getAssociatedCertificateDigest();

    public AlgorithmIdentifier getDigestAlgorithm();

    public boolean isForSelfSigning();
}

