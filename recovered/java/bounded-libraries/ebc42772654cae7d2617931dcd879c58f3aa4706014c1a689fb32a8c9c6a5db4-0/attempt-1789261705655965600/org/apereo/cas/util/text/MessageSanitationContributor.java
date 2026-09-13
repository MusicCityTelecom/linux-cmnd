/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.text;

import java.util.List;

@FunctionalInterface
public interface MessageSanitationContributor {
    public List<String> getTicketIdentifierPrefixes();
}

