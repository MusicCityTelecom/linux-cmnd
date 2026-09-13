/*
 * Decompiled with CFR 0.152.
 */
package com.android.io;

import com.android.io.IAbstractFile;
import com.android.io.IAbstractResource;

public interface IAbstractFolder
extends IAbstractResource {
    public boolean hasFile(String var1);

    public IAbstractFile getFile(String var1);

    public IAbstractFolder getFolder(String var1);

    public IAbstractResource[] listMembers();

    public String[] list(FilenameFilter var1);

    public static interface FilenameFilter {
        public boolean accept(IAbstractFolder var1, String var2);
    }
}

