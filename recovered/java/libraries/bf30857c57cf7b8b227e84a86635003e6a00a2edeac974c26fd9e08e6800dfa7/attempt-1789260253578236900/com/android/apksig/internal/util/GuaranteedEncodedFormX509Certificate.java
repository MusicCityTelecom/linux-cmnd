/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.internal.util.DelegatingX509Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public class GuaranteedEncodedFormX509Certificate
extends DelegatingX509Certificate {
    private static final long serialVersionUID = 1L;
    private final byte[] mEncodedForm;

    public GuaranteedEncodedFormX509Certificate(X509Certificate wrapped, byte[] encodedForm) {
        super(wrapped);
        this.mEncodedForm = encodedForm != null ? (byte[])encodedForm.clone() : null;
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.mEncodedForm != null ? (byte[])this.mEncodedForm.clone() : null;
    }
}

