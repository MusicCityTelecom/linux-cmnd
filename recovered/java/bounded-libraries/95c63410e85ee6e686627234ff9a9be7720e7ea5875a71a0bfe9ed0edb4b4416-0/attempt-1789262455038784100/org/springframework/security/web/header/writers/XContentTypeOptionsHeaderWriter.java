/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.header.writers;

import org.springframework.security.web.header.writers.StaticHeadersWriter;

public final class XContentTypeOptionsHeaderWriter
extends StaticHeadersWriter {
    public XContentTypeOptionsHeaderWriter() {
        super("X-Content-Type-Options", "nosniff");
    }
}

