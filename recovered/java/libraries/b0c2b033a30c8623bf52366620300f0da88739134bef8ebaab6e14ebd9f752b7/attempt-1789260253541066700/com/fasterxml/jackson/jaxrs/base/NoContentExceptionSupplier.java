/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.base;

import java.io.IOException;

public interface NoContentExceptionSupplier {
    public static final String NO_CONTENT_MESSAGE = "No content (empty input stream)";

    public IOException createNoContentException();
}

