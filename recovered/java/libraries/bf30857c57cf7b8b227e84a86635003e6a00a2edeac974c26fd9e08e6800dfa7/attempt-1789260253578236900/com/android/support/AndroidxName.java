/*
 * Decompiled with CFR 0.152.
 */
package com.android.support;

import com.android.support.AndroidxNameUtils;

public class AndroidxName {
    private final String myOldName;
    private final String myNewName;

    private AndroidxName(String oldName, String newName) {
        this.myOldName = oldName;
        this.myNewName = newName;
    }

    public static AndroidxName of(String oldPackage, String simpleClassName) {
        assert (oldPackage.endsWith("."));
        assert (oldPackage.startsWith("android.support.") || oldPackage.startsWith("android.arch.") || oldPackage.startsWith("android.databinding."));
        String fullOldName = oldPackage + simpleClassName;
        return new AndroidxName(fullOldName, AndroidxNameUtils.getNewName(fullOldName));
    }

    public static AndroidxName of(AndroidxName pkg, String simpleClassName) {
        assert (!simpleClassName.contains("."));
        return new AndroidxName(pkg.oldName() + (pkg.oldName().endsWith(".") ? "" : ".") + simpleClassName, pkg.newName() + (pkg.newName().endsWith(".") ? "" : ".") + simpleClassName);
    }

    public static AndroidxName of(String oldPackageName) {
        assert (oldPackageName.endsWith("."));
        assert (oldPackageName.startsWith("android.support.") || oldPackageName.startsWith("android.arch.") || oldPackageName.startsWith("android.databinding."));
        return new AndroidxName(oldPackageName, AndroidxNameUtils.getPackageMapping(oldPackageName, true));
    }

    public String oldName() {
        return this.myOldName;
    }

    public String newName() {
        return this.myNewName;
    }

    public String defaultName() {
        return this.myOldName;
    }

    public boolean isPrefix(String name) {
        return this.isPrefix(name, false);
    }

    public boolean isPrefix(String name, boolean strict) {
        if (name == null) {
            return false;
        }
        if (name.startsWith(this.oldName())) {
            return !strict || this.oldName().length() < name.length();
        }
        if (name.startsWith(this.newName())) {
            return !strict || this.newName().length() < name.length();
        }
        return false;
    }

    public String removeFrom(String qualifiedName) {
        if (qualifiedName.startsWith(this.oldName())) {
            return qualifiedName.substring(this.oldName().length());
        }
        if (qualifiedName.startsWith(this.newName())) {
            return qualifiedName.substring(this.newName().length());
        }
        return qualifiedName;
    }

    public boolean isEquals(String strName) {
        if (strName == null) {
            return false;
        }
        return this.myOldName.equals(strName) || this.myNewName.equals(strName);
    }

    public boolean isEqualsIgnoreCase(String strName) {
        if (strName == null) {
            return false;
        }
        return this.myOldName.equalsIgnoreCase(strName) || this.myNewName.equalsIgnoreCase(strName);
    }

    public String toString() {
        assert (false) : "toString can not be used on AndroidxName";
        return super.toString();
    }
}

