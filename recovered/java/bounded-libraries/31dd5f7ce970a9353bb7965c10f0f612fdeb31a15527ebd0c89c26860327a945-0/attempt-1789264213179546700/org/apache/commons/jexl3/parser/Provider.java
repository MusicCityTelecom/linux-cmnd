/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.io.Closeable;
import java.io.IOException;

public interface Provider
extends Closeable {
    public int read(char[] var1, int var2, int var3) throws IOException;
}

