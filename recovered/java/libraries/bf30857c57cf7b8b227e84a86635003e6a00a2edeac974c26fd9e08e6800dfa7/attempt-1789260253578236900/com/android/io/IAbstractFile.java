/*
 * Decompiled with CFR 0.152.
 */
package com.android.io;

import com.android.io.IAbstractResource;
import com.android.io.StreamException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IAbstractFile
extends IAbstractResource {
    public InputStream getContents() throws StreamException;

    public void setContents(InputStream var1) throws StreamException;

    public OutputStream getOutputStream() throws StreamException;

    public PreferredWriteMode getPreferredWriteMode();

    public long getModificationStamp();

    public static enum PreferredWriteMode {
        INPUTSTREAM,
        OUTPUTSTREAM;

    }
}

