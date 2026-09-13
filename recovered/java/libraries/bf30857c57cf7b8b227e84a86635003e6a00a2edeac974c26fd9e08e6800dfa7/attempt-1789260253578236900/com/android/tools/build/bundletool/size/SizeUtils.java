/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.size;

import com.android.bundle.SizesOuterClass;

public class SizeUtils {
    public static SizesOuterClass.Sizes addSizes(SizesOuterClass.Sizes sizeA, SizesOuterClass.Sizes sizeB) {
        return SizesOuterClass.Sizes.newBuilder().setDiskSize(sizeA.getDiskSize() + sizeB.getDiskSize()).setDownloadSize(sizeA.getDownloadSize() + sizeB.getDownloadSize()).build();
    }

    public static SizesOuterClass.Sizes subtractSizes(SizesOuterClass.Sizes sizeA, SizesOuterClass.Sizes sizeB) {
        return SizesOuterClass.Sizes.newBuilder().setDiskSize(sizeA.getDiskSize() - sizeB.getDiskSize()).setDownloadSize(sizeA.getDownloadSize() - sizeB.getDownloadSize()).build();
    }

    public static SizesOuterClass.Sizes sizes(long diskSize, long downloadSize) {
        return SizesOuterClass.Sizes.newBuilder().setDiskSize(diskSize).setDownloadSize(downloadSize).build();
    }

    private SizeUtils() {
    }
}

