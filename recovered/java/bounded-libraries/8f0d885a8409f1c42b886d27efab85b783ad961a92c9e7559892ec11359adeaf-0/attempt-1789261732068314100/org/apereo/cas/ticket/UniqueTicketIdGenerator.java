/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket;

@FunctionalInterface
public interface UniqueTicketIdGenerator {
    public static final char SEPARATOR = '-';
    public static final int TICKET_SIZE = 24;

    public String getNewTicketId(String var1);
}

