/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.nist.NISTObjectIdentifiers
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.DSA
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.crypto.ExtendedDigest
 *  org.bouncycastle.crypto.params.ECPublicKeyParameters
 *  org.bouncycastle.crypto.signers.DSADigestSigner
 *  org.bouncycastle.crypto.signers.ECDSASigner
 *  org.bouncycastle.oer.OERDefinition$Element
 *  org.bouncycastle.oer.OEREncoder
 *  org.bouncycastle.oer.its.PublicVerificationKey
 *  org.bouncycastle.oer.its.ToBeSignedCertificate
 *  org.bouncycastle.oer.its.VerificationKeyIndicator
 *  org.bouncycastle.oer.its.template.IEEE1609dot2
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.its.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.DSADigestSigner;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.bc.BcITSPublicVerificationKey;
import org.bouncycastle.its.operator.ITSContentVerifierProvider;
import org.bouncycastle.oer.OERDefinition;
import org.bouncycastle.oer.OEREncoder;
import org.bouncycastle.oer.its.PublicVerificationKey;
import org.bouncycastle.oer.its.ToBeSignedCertificate;
import org.bouncycastle.oer.its.VerificationKeyIndicator;
import org.bouncycastle.oer.its.template.IEEE1609dot2;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDefaultDigestProvider;
import org.bouncycastle.util.Arrays;

public class BcITSContentVerifierProvider
implements ITSContentVerifierProvider {
    private final ITSCertificate issuer;
    private final byte[] parentData;
    private final AlgorithmIdentifier digestAlgo;
    private final ECPublicKeyParameters pubParams;
    private final int sigChoice;

    public BcITSContentVerifierProvider(ITSCertificate iTSCertificate) throws IOException {
        PublicVerificationKey publicVerificationKey;
        this.issuer = iTSCertificate;
        this.parentData = iTSCertificate.getEncoded();
        ToBeSignedCertificate toBeSignedCertificate = iTSCertificate.toASN1Structure().getCertificateBase().getToBeSignedCertificate();
        VerificationKeyIndicator verificationKeyIndicator = toBeSignedCertificate.getVerificationKeyIndicator();
        if (verificationKeyIndicator.getObject() instanceof PublicVerificationKey) {
            publicVerificationKey = PublicVerificationKey.getInstance((Object)verificationKeyIndicator.getObject());
            this.sigChoice = publicVerificationKey.getChoice();
            switch (publicVerificationKey.getChoice()) {
                case 0: {
                    this.digestAlgo = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
                    break;
                }
                case 1: {
                    this.digestAlgo = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
                    break;
                }
                case 3: {
                    this.digestAlgo = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384);
                    break;
                }
                default: {
                    throw new IllegalStateException("unknown key type");
                }
            }
        } else {
            throw new IllegalStateException("not public verification key");
        }
        this.pubParams = (ECPublicKeyParameters)new BcITSPublicVerificationKey(publicVerificationKey).getKey();
    }

    public ITSCertificate getAssociatedCertificate() {
        return this.issuer;
    }

    public boolean hasAssociatedCertificate() {
        return this.issuer != null;
    }

    public ContentVerifier get(int n) throws OperatorCreationException {
        Object object;
        byte[] byArray;
        if (this.sigChoice != n) {
            throw new OperatorCreationException("wrong verifier for algorithm: " + n);
        }
        ExtendedDigest extendedDigest = BcDefaultDigestProvider.INSTANCE.get(this.digestAlgo);
        byte[] byArray2 = new byte[extendedDigest.getDigestSize()];
        extendedDigest.update(this.parentData, 0, this.parentData.length);
        extendedDigest.doFinal(byArray2, 0);
        byte[] byArray3 = byArray = this.issuer.getIssuer().isSelf() ? new byte[extendedDigest.getDigestSize()] : null;
        if (byArray != null) {
            object = OEREncoder.toByteArray((ASN1Encodable)this.issuer.toASN1Structure().getCertificateBase().getToBeSignedCertificate(), (OERDefinition.Element)IEEE1609dot2.tbsCertificate);
            extendedDigest.update((byte[])object, 0, ((Object)object).length);
            extendedDigest.doFinal(byArray, 0);
        }
        object = new OutputStream((Digest)extendedDigest){
            final /* synthetic */ Digest val$digest;
            {
                this.val$digest = digest;
            }

            public void write(int n) throws IOException {
                this.val$digest.update((byte)n);
            }

            public void write(byte[] byArray) throws IOException {
                this.val$digest.update(byArray, 0, byArray.length);
            }

            public void write(byte[] byArray, int n, int n2) throws IOException {
                this.val$digest.update(byArray, n, n2);
            }
        };
        return new ContentVerifier((OutputStream)object, (Digest)extendedDigest, byArray, byArray2){
            final DSADigestSigner signer;
            final /* synthetic */ OutputStream val$os;
            final /* synthetic */ Digest val$digest;
            final /* synthetic */ byte[] val$parentTBSDigest;
            final /* synthetic */ byte[] val$parentDigest;
            {
                this.val$os = outputStream;
                this.val$digest = digest;
                this.val$parentTBSDigest = byArray;
                this.val$parentDigest = byArray2;
                this.signer = new DSADigestSigner((DSA)new ECDSASigner(), (Digest)BcDefaultDigestProvider.INSTANCE.get(BcITSContentVerifierProvider.this.digestAlgo));
            }

            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return null;
            }

            public OutputStream getOutputStream() {
                return this.val$os;
            }

            public boolean verify(byte[] byArray) {
                byte[] byArray2 = new byte[this.val$digest.getDigestSize()];
                this.val$digest.doFinal(byArray2, 0);
                this.signer.init(false, (CipherParameters)BcITSContentVerifierProvider.this.pubParams);
                this.signer.update(byArray2, 0, byArray2.length);
                if (this.val$parentTBSDigest != null && Arrays.areEqual((byte[])byArray2, (byte[])this.val$parentTBSDigest)) {
                    byte[] byArray3 = new byte[this.val$digest.getDigestSize()];
                    this.val$digest.doFinal(byArray3, 0);
                    this.signer.update(byArray3, 0, byArray3.length);
                } else {
                    this.signer.update(this.val$parentDigest, 0, this.val$parentDigest.length);
                }
                return this.signer.verifySignature(byArray);
            }
        };
    }
}

