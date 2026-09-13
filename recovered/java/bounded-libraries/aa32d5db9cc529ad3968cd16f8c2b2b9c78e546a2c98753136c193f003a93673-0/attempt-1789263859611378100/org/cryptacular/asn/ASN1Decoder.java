/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.asn;

import org.cryptacular.EncodingException;

public interface ASN1Decoder<T> {
    public T decode(byte[] var1, Object ... var2) throws EncodingException;
}

