/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1.ber;

import com.android.apksig.internal.asn1.ber.BerDataValue;
import com.android.apksig.internal.asn1.ber.BerDataValueFormatException;

public interface BerDataValueReader {
    public BerDataValue readDataValue() throws BerDataValueFormatException;
}

