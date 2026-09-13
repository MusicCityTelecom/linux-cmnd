/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509;

public enum GeneralNameType {
    OtherName,
    RFC822Name,
    DNSName,
    X400Address,
    DirectoryName,
    EdiPartyName,
    UniformResourceIdentifier,
    IPAddress,
    RegisteredID;

    public static final int MIN_TAG_NUMBER = 0;
    public static final int MAX_TAG_NUMBER = 8;

    public static GeneralNameType fromTagNumber(int tagNo) {
        if (tagNo < 0 || tagNo > 8) {
            throw new IllegalArgumentException("Invalid tag number " + tagNo);
        }
        for (GeneralNameType type : GeneralNameType.values()) {
            if (type.ordinal() != tagNo) continue;
            return type;
        }
        throw new IllegalArgumentException("Invalid tag number " + tagNo);
    }
}

