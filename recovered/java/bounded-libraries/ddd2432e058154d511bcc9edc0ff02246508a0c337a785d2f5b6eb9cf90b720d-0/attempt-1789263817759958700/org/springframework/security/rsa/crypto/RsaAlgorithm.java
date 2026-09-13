/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.rsa.crypto;

public enum RsaAlgorithm {
    DEFAULT("RSA", 117),
    OAEP("RSA/ECB/OAEPPadding", 86);

    private String name;
    private int maxLength;

    private RsaAlgorithm(String name, int maxLength) {
        this.name = name;
        this.maxLength = maxLength;
    }

    public String getJceName() {
        return this.name;
    }

    public int getMaxLength() {
        return this.maxLength;
    }
}

