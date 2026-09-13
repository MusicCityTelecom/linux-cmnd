/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.utils;

import com.android.ddmlib.Log;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

public class FilePermissionUtil {
    private static int numericalPermission(PosixFilePermission p3) {
        switch (p3) {
            case OWNER_READ: {
                return 256;
            }
            case OWNER_WRITE: {
                return 128;
            }
            case OWNER_EXECUTE: {
                return 64;
            }
            case GROUP_READ: {
                return 32;
            }
            case GROUP_WRITE: {
                return 16;
            }
            case GROUP_EXECUTE: {
                return 8;
            }
            case OTHERS_READ: {
                return 4;
            }
            case OTHERS_WRITE: {
                return 2;
            }
            case OTHERS_EXECUTE: {
                return 1;
            }
        }
        return 0;
    }

    public static int getFilePosixPermission(File file) {
        Set<Object> perms;
        block7: {
            perms = new HashSet();
            try {
                perms = Files.getPosixFilePermissions(file.toPath(), new LinkOption[0]);
            }
            catch (IOException e2) {
                Log.e("ddms", "Error when reading file permission: " + e2.getMessage());
                return 420;
            }
            catch (UnsupportedOperationException e3) {
                if (file.canRead()) {
                    perms.add((Object)PosixFilePermission.OWNER_READ);
                }
                if (file.canWrite()) {
                    perms.add((Object)PosixFilePermission.OWNER_WRITE);
                }
                if (file.canExecute()) {
                    perms.add((Object)PosixFilePermission.OWNER_EXECUTE);
                }
                if (!perms.isEmpty()) break block7;
                return 420;
            }
        }
        Log.d("ddms", String.format("Reading file permision of %s as: %s", file.getAbsoluteFile(), PosixFilePermissions.toString(perms)));
        int result = 0;
        for (PosixFilePermission posixFilePermission : perms) {
            result += FilePermissionUtil.numericalPermission(posixFilePermission);
        }
        return result;
    }
}

