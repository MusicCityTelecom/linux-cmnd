/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.pbe;

import org.cryptacular.spec.BufferedBlockCipherSpec;
import org.cryptacular.spec.DigestSpec;

public enum PBES1Algorithm {
    PbeWithMD2AndDES_CBC("1.2.840.113549.1.5.1", new BufferedBlockCipherSpec("DES", "CBC", "PKCS5"), new DigestSpec("MD2")),
    PbeWithMD2AndRC2_CBC("1.2.840.113549.1.5.4", new BufferedBlockCipherSpec("RC2", "CBC", "PKCS5"), new DigestSpec("MD2")),
    PbeWithMD5AndDES_CBC("1.2.840.113549.1.5.3", new BufferedBlockCipherSpec("DES", "CBC", "PKCS5"), new DigestSpec("MD5")),
    PbeWithMD5AndRC2_CBC("1.2.840.113549.1.5.6", new BufferedBlockCipherSpec("RC2", "CBC", "PKCS5"), new DigestSpec("MD5")),
    PbeWithSHA1AndDES_CBC("1.2.840.113549.1.5.10", new BufferedBlockCipherSpec("DES", "CBC", "PKCS5"), new DigestSpec("SHA1")),
    PbeWithSHA1AndRC2_CBC("1.2.840.113549.1.5.11", new BufferedBlockCipherSpec("RC2", "CBC", "PKCS5"), new DigestSpec("SHA1"));

    private final String oid;
    private final BufferedBlockCipherSpec cipherSpec;
    private final DigestSpec digestSpec;

    private PBES1Algorithm(String id, BufferedBlockCipherSpec cipherSpec, DigestSpec digestSpec) {
        this.oid = id;
        this.cipherSpec = cipherSpec;
        this.digestSpec = digestSpec;
    }

    public static PBES1Algorithm fromOid(String oid) {
        for (PBES1Algorithm a : PBES1Algorithm.values()) {
            if (!a.getOid().equals(oid)) continue;
            return a;
        }
        throw new IllegalArgumentException("Unknown PBES1Algorithm for OID " + oid);
    }

    public String getOid() {
        return this.oid;
    }

    public BufferedBlockCipherSpec getCipherSpec() {
        return this.cipherSpec;
    }

    public DigestSpec getDigestSpec() {
        return this.digestSpec;
    }
}

