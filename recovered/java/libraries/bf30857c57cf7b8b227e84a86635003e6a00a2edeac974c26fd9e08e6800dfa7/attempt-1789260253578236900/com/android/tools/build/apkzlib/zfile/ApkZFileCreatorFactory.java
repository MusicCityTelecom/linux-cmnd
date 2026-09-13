/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zfile;

import com.android.tools.build.apkzlib.utils.IOExceptionWrapper;
import com.android.tools.build.apkzlib.zfile.ApkCreator;
import com.android.tools.build.apkzlib.zfile.ApkCreatorFactory;
import com.android.tools.build.apkzlib.zfile.ApkZFileCreator;
import com.android.tools.build.apkzlib.zip.ZFileOptions;
import java.io.IOException;

public class ApkZFileCreatorFactory
implements ApkCreatorFactory {
    private final ZFileOptions options;

    public ApkZFileCreatorFactory(ZFileOptions options) {
        this.options = options;
    }

    @Override
    public ApkCreator make(ApkCreatorFactory.CreationData creationData) {
        try {
            return new ApkZFileCreator(creationData, this.options);
        }
        catch (IOException e2) {
            throw new IOExceptionWrapper(e2);
        }
    }
}

