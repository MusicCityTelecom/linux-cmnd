/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.exceptions.BundleFileTypesException;
import com.android.tools.build.bundletool.validation.SubValidator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BundleZipValidator
extends SubValidator {
    @Override
    public void validateBundleZipEntry(ZipFile bundleFile, ZipEntry zipEntry) {
        if (zipEntry.isDirectory()) {
            throw new BundleFileTypesException.DirectoryInBundleException(zipEntry);
        }
    }
}

