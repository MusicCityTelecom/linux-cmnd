/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1InputStream
 *  org.bouncycastle.crypto.params.AsymmetricKeyParameter
 *  org.bouncycastle.crypto.util.PublicKeyFactory
 */
package org.cryptacular.asn;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.cryptacular.EncodingException;
import org.cryptacular.asn.ASN1Decoder;
import org.cryptacular.util.PemUtil;

public class PublicKeyDecoder
implements ASN1Decoder<AsymmetricKeyParameter> {
    @Override
    public AsymmetricKeyParameter decode(byte[] encoded, Object ... args) {
        try {
            if (PemUtil.isPem(encoded)) {
                return PublicKeyFactory.createKey((byte[])PemUtil.decode(encoded));
            }
            return PublicKeyFactory.createKey((byte[])new ASN1InputStream(encoded).readObject().getEncoded());
        }
        catch (IOException e) {
            throw new EncodingException("ASN.1 decoding error", e);
        }
    }
}

