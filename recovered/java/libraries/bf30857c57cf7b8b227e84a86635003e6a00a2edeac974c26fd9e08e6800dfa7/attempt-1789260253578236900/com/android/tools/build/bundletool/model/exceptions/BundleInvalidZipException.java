/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import java.util.zip.ZipException;

public class BundleInvalidZipException
extends ValidationException {
    public BundleInvalidZipException(ZipException cause) {
        super("Bundle is not a valid zip file", cause);
    }
}

