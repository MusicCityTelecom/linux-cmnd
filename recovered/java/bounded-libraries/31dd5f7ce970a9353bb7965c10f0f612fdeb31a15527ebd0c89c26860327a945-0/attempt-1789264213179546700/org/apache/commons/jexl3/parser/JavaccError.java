/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

public interface JavaccError {
    public int getLine();

    public int getColumn();

    public String getAfter();
}

