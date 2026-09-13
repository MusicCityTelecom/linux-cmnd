/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.io;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {
    public InputStream getInputStream() throws IOException;
}

