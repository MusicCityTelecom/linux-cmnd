/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.nist.NISTObjectIdentifiers
 *  org.bouncycastle.asn1.sec.SECObjectIdentifiers
 *  org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.DSA
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.crypto.io.DigestOutputStream
 *  org.bouncycastle.crypto.params.ECNamedDomainParameters
 *  org.bouncycastle.crypto.params.ECPrivateKeyParameters
 *  org.bouncycastle.crypto.signers.DSADigestSigner
 *  org.bouncycastle.crypto.signers.ECDSASigner
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.its.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.io.DigestOutputStream;
import org.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.DSADigestSigner;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.operator.ITSContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDefaultDigestProvider;
import org.bouncycastle.util.Arrays;

public class BcITSContentSigner
implements ITSContentSigner {
    private final ECPrivateKeyParameters privKey;
    private final ITSCertificate signerCert;
    private final AlgorithmIdentifier digestAlgo;
    private final Digest digest;
    private final byte[] parentData;
    private final ASN1ObjectIdentifier curveID;
    private final byte[] parentDigest;

    public BcITSContentSigner(ECPrivateKeyParameters eCPrivateKeyParameters) {
        this(eCPrivateKeyParameters, null);
    }

    public BcITSContentSigner(ECPrivateKeyParameters eCPrivateKeyParameters, ITSCertificate iTSCertificate) {
        this.privKey = eCPrivateKeyParameters;
        this.curveID = ((ECNamedDomainParameters)eCPrivateKeyParameters.getParameters()).getName();
        this.signerCert = iTSCertificate;
        if (this.curveID.equals((ASN1Primitive)SECObjectIdentifiers.secp256r1)) {
            this.digestAlgo = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
        } else if (this.curveID.equals((ASN1Primitive)TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            this.digestAlgo = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
        } else if (this.curveID.equals((ASN1Primitive)TeleTrusTObjectIdentifiers.brainpoolP384r1)) {
            this.digestAlgo = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384);
        } else {
            throw new IllegalArgumentException("unknown key type");
        }
        try {
            this.digest = BcDefaultDigestProvider.INSTANCE.get(this.digestAlgo);
        }
        catch (OperatorCreationException operatorCreationException) {
            throw new IllegalStateException("cannot recognise digest type: " + this.digestAlgo.getAlgorithm());
        }
        if (iTSCertificate != null) {
            try {
                this.parentData = iTSCertificate.getEncoded();
                this.parentDigest = new byte[this.digest.getDigestSize()];
                this.digest.update(this.parentData, 0, this.parentData.length);
                this.digest.doFinal(this.parentDigest, 0);
            }
            catch (IOException iOException) {
                throw new IllegalStateException("signer certificate encoding failed: " + iOException.getMessage());
            }
        } else {
            this.parentData = null;
            this.parentDigest = new byte[this.digest.getDigestSize()];
            this.digest.doFinal(this.parentDigest, 0);
        }
    }

    public ITSCertificate getAssociatedCertificate() {
        return this.signerCert;
    }

    public byte[] getAssociatedCertificateDigest() {
        return Arrays.clone((byte[])this.parentDigest);
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgo;
    }

    public OutputStream getOutputStream() {
        return new DigestOutputStream(this.digest);
    }

    public boolean isForSelfSigning() {
        return this.parentData == null;
    }

    public byte[] getSignature() {
        byte[] byArray = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(byArray, 0);
        DSADigestSigner dSADigestSigner = new DSADigestSigner((DSA)new ECDSASigner(), this.digest);
        dSADigestSigner.init(true, (CipherParameters)this.privKey);
        dSADigestSigner.update(byArray, 0, byArray.length);
        dSADigestSigner.update(this.parentDigest, 0, this.parentDigest.length);
        return dSADigestSigner.generateSignature();
    }
}

