/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TransactionActionType")
@XmlEnum
public enum TransactionActionType {
    BOOK("Book"),
    QUOTE("Quote"),
    HOLD("Hold"),
    INITIATE("Initiate"),
    IGNORE("Ignore"),
    MODIFY("Modify"),
    COMMIT("Commit"),
    CANCEL("Cancel"),
    COMMIT_OVERRIDE_EDITS("CommitOverrideEdits"),
    VERIFY_PRICE("VerifyPrice"),
    TICKET("Ticket");

    private final String value;

    private TransactionActionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TransactionActionType fromValue(String v) {
        for (TransactionActionType c : TransactionActionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

