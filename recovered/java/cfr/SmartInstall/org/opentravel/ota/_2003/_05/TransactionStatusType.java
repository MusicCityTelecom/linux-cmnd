/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TransactionStatusType")
@XmlEnum
public enum TransactionStatusType {
    PENDING("Pending"),
    CANCELLED("Cancelled"),
    MODIFIED("Modified"),
    COMMITTED("Committed"),
    IGNORED("Ignored"),
    ON_HOLD("On Hold"),
    UNSUCCESSFUL("Unsuccessful"),
    PENDING_CANCELLATION("PendingCancellation"),
    PENDING_PURCHASE("PendingPurchase"),
    REQUESTED("Requested"),
    RESERVED("Reserved"),
    UNCHANGED("Unchanged"),
    REQUEST_DENIED("RequestDenied"),
    TICKETED("Ticketed");

    private final String value;

    private TransactionStatusType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TransactionStatusType fromValue(String v) {
        for (TransactionStatusType c : TransactionStatusType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

