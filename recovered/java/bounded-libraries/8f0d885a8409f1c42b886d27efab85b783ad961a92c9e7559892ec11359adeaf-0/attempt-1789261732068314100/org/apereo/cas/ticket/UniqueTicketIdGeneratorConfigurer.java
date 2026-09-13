/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package org.apereo.cas.ticket;

import java.util.Collection;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;

@FunctionalInterface
public interface UniqueTicketIdGeneratorConfigurer {
    public Collection<Pair<String, UniqueTicketIdGenerator>> buildUniqueTicketIdGenerators();
}

