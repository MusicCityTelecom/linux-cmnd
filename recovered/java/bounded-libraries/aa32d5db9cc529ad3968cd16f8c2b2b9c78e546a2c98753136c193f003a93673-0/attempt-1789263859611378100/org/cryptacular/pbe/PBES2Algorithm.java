/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.pbe;

import org.cryptacular.spec.BufferedBlockCipherSpec;

public enum PBES2Algorithm {
    DES("1.3.14.3.2.7", new BufferedBlockCipherSpec("DES", "CBC", "PKCS5"), 64),
    DESede("1.2.840.113549.3.7", new BufferedBlockCipherSpec("DESede", "CBC", "PKCS5"), 192),
    RC2("1.2.840.113549.3.2", new BufferedBlockCipherSpec("RC2", "CBC", "PKCS5"), 64),
    RC5("1.2.840.113549.3.9", new BufferedBlockCipherSpec("RC5", "CBC", "PKCS5"), 128),
    AES128("2.16.840.1.101.3.4.1.2", new BufferedBlockCipherSpec("AES", "CBC", "PKCS5"), 128),
    AES192("2.16.840.1.101.3.4.1.22", new BufferedBlockCipherSpec("AES", "CBC", "PKCS5"), 192),
    AES256("2.16.840.1.101.3.4.1.42", new BufferedBlockCipherSpec("AES", "CBC", "PKCS5"), 256);

    private final String oid;
    private final BufferedBlockCipherSpec cipherSpec;
    private final int keySize;

    private PBES2Algorithm(String id, BufferedBlockCipherSpec cipherSpec, int keySizeBits) {
        this.oid = id;
        this.cipherSpec = cipherSpec;
        this.keySize = keySizeBits;
    }

    public static PBES2Algorithm fromOid(String oid) {
        for (PBES2Algorithm a : PBES2Algorithm.values()) {
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

    public int getKeySize() {
        return this.keySize;
    }
}

