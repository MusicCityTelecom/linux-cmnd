/*
 * Decompiled with CFR 0.152.
 */
package com.android.support;

import com.android.support.AndroidxMigrationParserKt;
import com.android.support.AndroidxName;
import com.android.support.MigrationParserVisitor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidxNameUtils {
    private static final Logger LOG = Logger.getLogger(AndroidxName.class.getName());
    static final String ANDROID_SUPPORT_PKG = "android.support.";
    static final String ANDROID_ARCH_PKG = "android.arch.";
    static final String ANDROID_DATABINDING_PKG = "android.databinding.";
    static final ImmutableMap<String, String> ANDROIDX_PKG_MAPPING;
    static final ImmutableMap<String, String> ANDROIDX_FULL_CLASS_MAPPING;
    static final ImmutableMap<String, String> ANDROIDX_COORDINATES_MAPPING;
    static final ImmutableMap<String, String> ANDROIDX_VERSIONED_COORDINATES_MAPPING;
    static final ImmutableList<String> ANDROIDX_OLD_PKGS;

    static String getPackageMapping(String oldPkgName, boolean strictChecking) {
        int n2 = ANDROIDX_OLD_PKGS.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            String prefix = (String)ANDROIDX_OLD_PKGS.get(i2);
            if (!oldPkgName.startsWith(prefix)) continue;
            return ANDROIDX_PKG_MAPPING.get(prefix) + oldPkgName.substring(prefix.length());
        }
        if (strictChecking && LOG.isLoggable(Level.FINE)) {
            LOG.fine("support library package not found: " + oldPkgName);
        }
        return oldPkgName;
    }

    public static Collection<String> getAllAndroidxCoordinates() {
        return ANDROIDX_COORDINATES_MAPPING.values();
    }

    public static String getCoordinateMapping(String coordinate) {
        return ANDROIDX_COORDINATES_MAPPING.getOrDefault(coordinate, coordinate);
    }

    public static String getVersionedCoordinateMapping(String coordinate) {
        String[] components = coordinate.split(":");
        if (components.length < 2) {
            return coordinate;
        }
        String canonicalCoordinate = components[0] + ":" + components[1];
        return ANDROIDX_VERSIONED_COORDINATES_MAPPING.getOrDefault(canonicalCoordinate, coordinate);
    }

    public static String getNewName(String oldName) {
        int innerClassSymbol = oldName.indexOf(36);
        if (innerClassSymbol != -1) {
            String outerClassName = oldName.substring(0, innerClassSymbol);
            String innerClassName = oldName.substring(innerClassSymbol);
            return AndroidxNameUtils.getNewName(outerClassName) + innerClassName;
        }
        String newName = ANDROIDX_FULL_CLASS_MAPPING.get(oldName);
        if (newName != null) {
            return newName;
        }
        int lastDot = oldName.lastIndexOf(46);
        return AndroidxNameUtils.getPackageMapping(oldName.substring(0, lastDot + 1), false) + oldName.substring(lastDot + 1);
    }

    static {
        final ImmutableMap.Builder classTransformMap = ImmutableMap.builder();
        final ImmutableMap.Builder packageTransformMap = ImmutableMap.builder();
        final ImmutableMap.Builder coordinatesTransformMap = ImmutableMap.builder();
        final ImmutableMap.Builder versionedCoordinatesTransformMap = ImmutableMap.builder();
        try {
            AndroidxMigrationParserKt.parseMigrationFile(new MigrationParserVisitor(){

                @Override
                public void visitGradleCoordinateUpgrade(String groupName, String artifactName, String newBaseVersion) {
                }

                @Override
                public void visitGradleCoordinate(String oldGroupName, String oldArtifactName, String newGroupName, String newArtifactName, String newBaseVersion) {
                    coordinatesTransformMap.put(oldGroupName + ":" + oldArtifactName, newGroupName + ":" + newArtifactName);
                    versionedCoordinatesTransformMap.put(oldGroupName + ":" + oldArtifactName, newGroupName + ":" + newArtifactName + ":" + newBaseVersion);
                }

                @Override
                public void visitClass(String old, String newName) {
                    classTransformMap.put(old, newName);
                }

                @Override
                public void visitPackage(String old, String newName) {
                    packageTransformMap.put(old, newName);
                }
            });
        }
        catch (Throwable e2) {
            LOG.severe("Error loading androidx migration mapping: " + e2.getLocalizedMessage());
        }
        ANDROIDX_FULL_CLASS_MAPPING = classTransformMap.build();
        ANDROIDX_PKG_MAPPING = packageTransformMap.build();
        ANDROIDX_OLD_PKGS = Ordering.from((left, right) -> Ints.compare(right.length(), left.length())).immutableSortedCopy(ANDROIDX_PKG_MAPPING.keySet());
        ANDROIDX_COORDINATES_MAPPING = coordinatesTransformMap.build();
        ANDROIDX_VERSIONED_COORDINATES_MAPPING = versionedCoordinatesTransformMap.build();
    }
}

