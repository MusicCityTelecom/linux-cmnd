/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1UTF8String
 *  org.bouncycastle.asn1.crmf.AttributeTypeAndValue
 *  org.bouncycastle.asn1.crmf.CRMFObjectIdentifiers
 *  org.bouncycastle.asn1.crmf.CertReqMsg
 *  org.bouncycastle.asn1.crmf.CertTemplate
 *  org.bouncycastle.asn1.crmf.Controls
 *  org.bouncycastle.asn1.crmf.PKIArchiveOptions
 *  org.bouncycastle.asn1.crmf.PKMACValue
 *  org.bouncycastle.asn1.crmf.POPOSigningKey
 *  org.bouncycastle.asn1.crmf.ProofOfPossession
 *  org.bouncycastle.util.Encodable
 */
package org.bouncycastle.cert.crmf;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1UTF8String;
import org.bouncycastle.asn1.crmf.AttributeTypeAndValue;
import org.bouncycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.bouncycastle.asn1.crmf.CertReqMsg;
import org.bouncycastle.asn1.crmf.CertTemplate;
import org.bouncycastle.asn1.crmf.Controls;
import org.bouncycastle.asn1.crmf.PKIArchiveOptions;
import org.bouncycastle.asn1.crmf.PKMACValue;
import org.bouncycastle.asn1.crmf.POPOSigningKey;
import org.bouncycastle.asn1.crmf.ProofOfPossession;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.crmf.AuthenticatorControl;
import org.bouncycastle.cert.crmf.CRMFException;
import org.bouncycastle.cert.crmf.CRMFUtil;
import org.bouncycastle.cert.crmf.Control;
import org.bouncycastle.cert.crmf.PKIArchiveControl;
import org.bouncycastle.cert.crmf.PKMACBuilder;
import org.bouncycastle.cert.crmf.PKMACValueVerifier;
import org.bouncycastle.cert.crmf.RegTokenControl;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Encodable;

public class CertificateRequestMessage
implements Encodable {
    public static final int popRaVerified = 0;
    public static final int popSigningKey = 1;
    public static final int popKeyEncipherment = 2;
    public static final int popKeyAgreement = 3;
    private final CertReqMsg certReqMsg;
    private final Controls controls;

    private static CertReqMsg parseBytes(byte[] byArray) throws IOException {
        try {
            return CertReqMsg.getInstance((Object)ASN1Primitive.fromByteArray((byte[])byArray));
        }
        catch (ClassCastException classCastException) {
            throw new CertIOException("malformed data: " + classCastException.getMessage(), classCastException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CertIOException("malformed data: " + illegalArgumentException.getMessage(), illegalArgumentException);
        }
    }

    public CertificateRequestMessage(byte[] byArray) throws IOException {
        this(CertificateRequestMessage.parseBytes(byArray));
    }

    public CertificateRequestMessage(CertReqMsg certReqMsg) {
        this.certReqMsg = certReqMsg;
        this.controls = certReqMsg.getCertReq().getControls();
    }

    public CertReqMsg toASN1Structure() {
        return this.certReqMsg;
    }

    public CertTemplate getCertTemplate() {
        return this.certReqMsg.getCertReq().getCertTemplate();
    }

    public boolean hasControls() {
        return this.controls != null;
    }

    public boolean hasControl(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.findControl(aSN1ObjectIdentifier) != null;
    }

    public Control getControl(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        AttributeTypeAndValue attributeTypeAndValue = this.findControl(aSN1ObjectIdentifier);
        if (attributeTypeAndValue != null) {
            if (attributeTypeAndValue.getType().equals((ASN1Primitive)CRMFObjectIdentifiers.id_regCtrl_pkiArchiveOptions)) {
                return new PKIArchiveControl(PKIArchiveOptions.getInstance((Object)attributeTypeAndValue.getValue()));
            }
            if (attributeTypeAndValue.getType().equals((ASN1Primitive)CRMFObjectIdentifiers.id_regCtrl_regToken)) {
                return new RegTokenControl(ASN1UTF8String.getInstance((Object)attributeTypeAndValue.getValue()));
            }
            if (attributeTypeAndValue.getType().equals((ASN1Primitive)CRMFObjectIdentifiers.id_regCtrl_authenticator)) {
                return new AuthenticatorControl(ASN1UTF8String.getInstance((Object)attributeTypeAndValue.getValue()));
            }
        }
        return null;
    }

    private AttributeTypeAndValue findControl(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (this.controls == null) {
            return null;
        }
        AttributeTypeAndValue[] attributeTypeAndValueArray = this.controls.toAttributeTypeAndValueArray();
        AttributeTypeAndValue attributeTypeAndValue = null;
        for (int i = 0; i != attributeTypeAndValueArray.length; ++i) {
            if (!attributeTypeAndValueArray[i].getType().equals((ASN1Primitive)aSN1ObjectIdentifier)) continue;
            attributeTypeAndValue = attributeTypeAndValueArray[i];
            break;
        }
        return attributeTypeAndValue;
    }

    public boolean hasProofOfPossession() {
        return this.certReqMsg.getPopo() != null;
    }

    public int getProofOfPossessionType() {
        return this.certReqMsg.getPopo().getType();
    }

    public boolean hasSigningKeyProofOfPossessionWithPKMAC() {
        ProofOfPossession proofOfPossession = this.certReqMsg.getPopo();
        if (proofOfPossession.getType() == 1) {
            POPOSigningKey pOPOSigningKey = POPOSigningKey.getInstance((Object)proofOfPossession.getObject());
            return pOPOSigningKey.getPoposkInput().getPublicKeyMAC() != null;
        }
        return false;
    }

    public boolean isValidSigningKeyPOP(ContentVerifierProvider contentVerifierProvider) throws CRMFException, IllegalStateException {
        ProofOfPossession proofOfPossession = this.certReqMsg.getPopo();
        if (proofOfPossession.getType() == 1) {
            POPOSigningKey pOPOSigningKey = POPOSigningKey.getInstance((Object)proofOfPossession.getObject());
            if (pOPOSigningKey.getPoposkInput() != null && pOPOSigningKey.getPoposkInput().getPublicKeyMAC() != null) {
                throw new IllegalStateException("verification requires password check");
            }
            return this.verifySignature(contentVerifierProvider, pOPOSigningKey);
        }
        throw new IllegalStateException("not Signing Key type of proof of possession");
    }

    public boolean isValidSigningKeyPOP(ContentVerifierProvider contentVerifierProvider, PKMACBuilder pKMACBuilder, char[] cArray) throws CRMFException, IllegalStateException {
        ProofOfPossession proofOfPossession = this.certReqMsg.getPopo();
        if (proofOfPossession.getType() == 1) {
            POPOSigningKey pOPOSigningKey = POPOSigningKey.getInstance((Object)proofOfPossession.getObject());
            if (pOPOSigningKey.getPoposkInput() == null || pOPOSigningKey.getPoposkInput().getSender() != null) {
                throw new IllegalStateException("no PKMAC present in proof of possession");
            }
            PKMACValueVerifier pKMACValueVerifier = new PKMACValueVerifier(pKMACBuilder);
            PKMACValue pKMACValue = pOPOSigningKey.getPoposkInput().getPublicKeyMAC();
            if (pKMACValueVerifier.isValid(pKMACValue, cArray, this.getCertTemplate().getPublicKey())) {
                return this.verifySignature(contentVerifierProvider, pOPOSigningKey);
            }
            return false;
        }
        throw new IllegalStateException("not Signing Key type of proof of possession");
    }

    private boolean verifySignature(ContentVerifierProvider contentVerifierProvider, POPOSigningKey pOPOSigningKey) throws CRMFException {
        ContentVerifier contentVerifier;
        try {
            contentVerifier = contentVerifierProvider.get(pOPOSigningKey.getAlgorithmIdentifier());
        }
        catch (OperatorCreationException operatorCreationException) {
            throw new CRMFException("unable to create verifier: " + operatorCreationException.getMessage(), operatorCreationException);
        }
        if (pOPOSigningKey.getPoposkInput() != null) {
            CRMFUtil.derEncodeToStream((ASN1Object)pOPOSigningKey.getPoposkInput(), contentVerifier.getOutputStream());
        } else {
            CRMFUtil.derEncodeToStream((ASN1Object)this.certReqMsg.getCertReq(), contentVerifier.getOutputStream());
        }
        return contentVerifier.verify(pOPOSigningKey.getSignature().getOctets());
    }

    public byte[] getEncoded() throws IOException {
        return this.certReqMsg.getEncoded();
    }
}

