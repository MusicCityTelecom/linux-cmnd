/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.cms;

import org.bouncycastle.cms.KEKRecipientInformation;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.util.Arrays;

public class KEKRecipientId
extends RecipientId {
    private byte[] keyIdentifier;

    public KEKRecipientId(byte[] byArray) {
        super(1);
        this.keyIdentifier = byArray;
    }

    public int hashCode() {
        return Arrays.hashCode((byte[])this.keyIdentifier);
    }

    public boolean equals(Object object) {
        if (!(object instanceof KEKRecipientId)) {
            return false;
        }
        KEKRecipientId kEKRecipientId = (KEKRecipientId)object;
        return Arrays.areEqual((byte[])this.keyIdentifier, (byte[])kEKRecipientId.keyIdentifier);
    }

    public byte[] getKeyIdentifier() {
        return Arrays.clone((byte[])this.keyIdentifier);
    }

    public Object clone() {
        return new KEKRecipientId(this.keyIdentifier);
    }

    public boolean match(Object object) {
        if (object instanceof byte[]) {
            return Arrays.areEqual((byte[])this.keyIdentifier, (byte[])((byte[])object));
        }
        if (object instanceof KEKRecipientInformation) {
            return ((KEKRecipientInformation)object).getRID().equals(this);
        }
        return false;
    }
}

