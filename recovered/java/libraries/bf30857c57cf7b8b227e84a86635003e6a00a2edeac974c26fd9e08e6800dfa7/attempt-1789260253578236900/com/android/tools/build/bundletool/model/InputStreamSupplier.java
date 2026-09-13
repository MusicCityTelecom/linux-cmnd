/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.MustBeClosed;
import java.io.IOException;
import java.io.InputStream;

@Immutable
public interface InputStreamSupplier {
    @MustBeClosed
    public InputStream get() throws IOException;
}

