/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.crypto.CipherKeyGenerator
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.params.KeyParameter
 *  org.bouncycastle.crypto.util.AlgorithmIdentifierFactory
 *  org.bouncycastle.crypto.util.CipherFactory
 *  org.bouncycastle.crypto.util.CipherKeyGeneratorFactory
 */
package org.bouncycastle.cert.crmf.bc;

import java.security.SecureRandom;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.crmf.CRMFException;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.util.AlgorithmIdentifierFactory;
import org.bouncycastle.crypto.util.CipherFactory;
import org.bouncycastle.crypto.util.CipherKeyGeneratorFactory;

class CRMFHelper {
    CRMFHelper() {
    }

    CipherKeyGenerator createKeyGenerator(ASN1ObjectIdentifier aSN1ObjectIdentifier, SecureRandom secureRandom) throws CRMFException {
        try {
            return CipherKeyGeneratorFactory.createKeyGenerator((ASN1ObjectIdentifier)aSN1ObjectIdentifier, (SecureRandom)secureRandom);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CRMFException(illegalArgumentException.getMessage(), illegalArgumentException);
        }
    }

    static Object createContentCipher(boolean bl, CipherParameters cipherParameters, AlgorithmIdentifier algorithmIdentifier) throws CRMFException {
        try {
            return CipherFactory.createContentCipher((boolean)bl, (CipherParameters)cipherParameters, (AlgorithmIdentifier)algorithmIdentifier);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CRMFException(illegalArgumentException.getMessage(), illegalArgumentException);
        }
    }

    AlgorithmIdentifier generateEncryptionAlgID(ASN1ObjectIdentifier aSN1ObjectIdentifier, KeyParameter keyParameter, SecureRandom secureRandom) throws CRMFException {
        try {
            return AlgorithmIdentifierFactory.generateEncryptionAlgID((ASN1ObjectIdentifier)aSN1ObjectIdentifier, (int)(keyParameter.getKey().length * 8), (SecureRandom)secureRandom);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CRMFException(illegalArgumentException.getMessage(), illegalArgumentException);
        }
    }
}

