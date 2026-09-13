/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1InputStream
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.x9.X9ECParameters
 *  org.bouncycastle.crypto.params.AsymmetricKeyParameter
 *  org.bouncycastle.crypto.params.DSAParameters
 *  org.bouncycastle.crypto.params.DSAPrivateKeyParameters
 *  org.bouncycastle.crypto.params.ECDomainParameters
 *  org.bouncycastle.crypto.params.ECPrivateKeyParameters
 *  org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters
 *  org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil
 */
package org.cryptacular.asn;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.cryptacular.EncodingException;
import org.cryptacular.asn.AbstractPrivateKeyDecoder;
import org.cryptacular.pbe.OpenSSLAlgorithm;
import org.cryptacular.pbe.OpenSSLEncryptionScheme;
import org.cryptacular.util.ByteUtil;
import org.cryptacular.util.CodecUtil;
import org.cryptacular.util.PemUtil;

public class OpenSSLPrivateKeyDecoder
extends AbstractPrivateKeyDecoder<AsymmetricKeyParameter> {
    @Override
    protected byte[] decryptKey(byte[] encrypted, char[] password) {
        String pem = new String(encrypted, ByteUtil.ASCII_CHARSET);
        int start = pem.indexOf("DEK-Info:");
        int eol = pem.indexOf(10, start);
        String[] dekInfo = pem.substring(start + 10, eol).split(",");
        String alg = dekInfo[0];
        byte[] iv = CodecUtil.hex(dekInfo[1]);
        byte[] bytes = PemUtil.decode(encrypted);
        return new OpenSSLEncryptionScheme(OpenSSLAlgorithm.fromAlgorithmId(alg), iv, password).decrypt(bytes);
    }

    @Override
    protected AsymmetricKeyParameter decodeASN1(byte[] encoded) {
        ECPrivateKeyParameters key;
        ASN1Primitive o;
        ASN1InputStream stream = new ASN1InputStream(encoded);
        try {
            o = stream.readObject();
        }
        catch (Exception e) {
            throw new EncodingException("Invalid encoded key", e);
        }
        if (o instanceof ASN1ObjectIdentifier) {
            try {
                key = this.parseECPrivateKey(ASN1Sequence.getInstance((Object)stream.readObject()));
            }
            catch (Exception e) {
                throw new EncodingException("Invalid encoded key", e);
            }
        } else {
            ASN1Sequence sequence = ASN1Sequence.getInstance((Object)o);
            if (sequence.size() == 9) {
                key = new RSAPrivateCrtKeyParameters(ASN1Integer.getInstance((Object)sequence.getObjectAt(1)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(2)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(3)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(4)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(5)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(6)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(7)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(8)).getValue());
            } else if (sequence.size() == 6) {
                key = new DSAPrivateKeyParameters(ASN1Integer.getInstance((Object)sequence.getObjectAt(5)).getValue(), new DSAParameters(ASN1Integer.getInstance((Object)sequence.getObjectAt(1)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(2)).getValue(), ASN1Integer.getInstance((Object)sequence.getObjectAt(3)).getValue()));
            } else if (sequence.size() == 4) {
                key = this.parseECPrivateKey(sequence);
            } else {
                throw new EncodingException("Invalid OpenSSL traditional private key format.");
            }
        }
        return key;
    }

    private ECPrivateKeyParameters parseECPrivateKey(ASN1Sequence seq) {
        ASN1TaggedObject asn1Params = ASN1TaggedObject.getInstance((Object)seq.getObjectAt(2));
        X9ECParameters params = asn1Params.getObject() instanceof ASN1ObjectIdentifier ? ECUtil.getNamedCurveByOid((ASN1ObjectIdentifier)ASN1ObjectIdentifier.getInstance((Object)asn1Params.getObject())) : X9ECParameters.getInstance((Object)asn1Params.getObject());
        return new ECPrivateKeyParameters(new BigInteger(1, ASN1OctetString.getInstance((Object)seq.getObjectAt(1)).getOctets()), new ECDomainParameters(params.getCurve(), params.getG(), params.getN(), params.getH(), params.getSeed()));
    }
}

