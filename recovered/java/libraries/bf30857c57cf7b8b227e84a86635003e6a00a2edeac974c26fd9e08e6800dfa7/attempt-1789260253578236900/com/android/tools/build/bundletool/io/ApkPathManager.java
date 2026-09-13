/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.concurrent.GuardedBy;

public class ApkPathManager {
    private static final Joiner NAME_PARTS_JOINER = Joiner.on('-');
    @GuardedBy(value="this")
    private final Set<ZipPath> usedPaths = new HashSet<ZipPath>();

    public ZipPath getApkPath(ModuleSplit moduleSplit) {
        String apkFileName;
        ZipPath directory;
        String moduleName = moduleSplit.getModuleName().getName();
        String targetingSuffix = ApkPathManager.getTargetingSuffix(moduleSplit);
        switch (moduleSplit.getSplitType()) {
            case SPLIT: {
                directory = ZipPath.create("splits");
                apkFileName = ApkPathManager.buildName(moduleName, targetingSuffix);
                break;
            }
            case INSTANT: {
                directory = ZipPath.create("instant");
                apkFileName = ApkPathManager.buildName("instant", moduleName, targetingSuffix);
                break;
            }
            case STANDALONE: {
                directory = ZipPath.create("standalones");
                apkFileName = ApkPathManager.buildName("standalone", targetingSuffix);
                break;
            }
            case SYSTEM: {
                if (moduleSplit.isBaseModuleSplit() && moduleSplit.isMasterSplit()) {
                    directory = ZipPath.create("system");
                    apkFileName = ApkPathManager.buildName("system");
                    break;
                }
                directory = ZipPath.create("splits");
                apkFileName = ApkPathManager.buildName(moduleName, targetingSuffix);
                break;
            }
            case ASSET_SLICE: {
                directory = ZipPath.create("asset-slices");
                apkFileName = ApkPathManager.buildName(moduleName, targetingSuffix);
                break;
            }
            default: {
                throw new IllegalStateException("Unrecognized split type: " + (Object)((Object)moduleSplit.getSplitType()));
            }
        }
        return this.findAndClaimUnusedPath(directory, apkFileName, ApkPathManager.fileExtension(moduleSplit));
    }

    private synchronized ZipPath findAndClaimUnusedPath(ZipPath directory, String proposedName, String fileExtension) {
        ZipPath apkPath = directory.resolve(proposedName + fileExtension);
        int serialNumber = 1;
        while (this.usedPaths.contains(apkPath)) {
            String fileName = String.format("%s_%d", proposedName, ++serialNumber);
            apkPath = directory.resolve(fileName + fileExtension);
        }
        this.usedPaths.add(apkPath);
        return apkPath;
    }

    private static String fileExtension(ModuleSplit moduleSplit) {
        return moduleSplit.isApex() ? ".apex" : ".apk";
    }

    private static String getTargetingSuffix(ModuleSplit moduleSplit) {
        return moduleSplit.isMasterSplit() && !moduleSplit.getSplitType().equals((Object)ModuleSplit.SplitType.STANDALONE) ? "master" : moduleSplit.getSuffix();
    }

    private static String buildName(String ... nameParts) {
        return NAME_PARTS_JOINER.join(Arrays.stream(nameParts).filter(s3 -> !s3.isEmpty()).collect(Collectors.toList()));
    }
}

