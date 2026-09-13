/*
 * Decompiled with CFR 0.152.
 */
package com.android.io;

import com.android.io.IAbstractFolder;

public interface IAbstractResource {
    public String getName();

    public String getOsLocation();

    public String getPath();

    public boolean exists();

    public IAbstractFolder getParentFolder();

    public boolean delete();
}

