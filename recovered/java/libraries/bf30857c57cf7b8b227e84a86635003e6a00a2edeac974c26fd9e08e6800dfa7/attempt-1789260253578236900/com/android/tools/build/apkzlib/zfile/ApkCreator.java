/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zfile;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

public interface ApkCreator
extends Closeable {
    public void writeZip(File var1, @Nullable Function<String, String> var2, @Nullable Predicate<String> var3) throws IOException;

    public void writeFile(File var1, String var2) throws IOException;

    public void deleteFile(String var1) throws IOException;

    public boolean hasPendingChangesWithWait() throws IOException;
}

