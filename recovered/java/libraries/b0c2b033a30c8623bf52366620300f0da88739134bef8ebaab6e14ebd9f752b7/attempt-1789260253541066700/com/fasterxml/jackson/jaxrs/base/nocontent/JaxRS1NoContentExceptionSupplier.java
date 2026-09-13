/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.base.nocontent;

import com.fasterxml.jackson.jaxrs.base.NoContentExceptionSupplier;
import java.io.IOException;

public class JaxRS1NoContentExceptionSupplier
implements NoContentExceptionSupplier {
    @Override
    public IOException createNoContentException() {
        return new IOException("No content (empty input stream)");
    }
}

