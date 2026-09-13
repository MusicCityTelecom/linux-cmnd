/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.oer.its.CertificateBase;

public class Certificate {
    private final CertificateBase certificateBase;

    public Certificate(CertificateBase certificateBase) {
        this.certificateBase = certificateBase;
    }

    public static Certificate getInstance(Object object) {
        if (object instanceof Certificate) {
            return (Certificate)object;
        }
        return new Builder().setCertificateBase(CertificateBase.getInstance(object)).createCertificate();
    }

    public static Builder builder() {
        return new Builder();
    }

    public CertificateBase getCertificateBase() {
        return this.certificateBase;
    }

    public static class Builder {
        private CertificateBase certificateBase;

        public Builder setCertificateBase(CertificateBase certificateBase) {
            this.certificateBase = certificateBase;
            return this;
        }

        public Certificate createCertificate() {
            return new Certificate(this.certificateBase);
        }
    }
}

