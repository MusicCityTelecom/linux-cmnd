/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.base.nocontent;

import com.fasterxml.jackson.jaxrs.base.NoContentExceptionSupplier;
import java.io.IOException;
import javax.ws.rs.core.NoContentException;

public class JaxRS2NoContentExceptionSupplier
implements NoContentExceptionSupplier {
    @Override
    public IOException createNoContentException() {
        return new NoContentException("No content (empty input stream)");
    }
}

