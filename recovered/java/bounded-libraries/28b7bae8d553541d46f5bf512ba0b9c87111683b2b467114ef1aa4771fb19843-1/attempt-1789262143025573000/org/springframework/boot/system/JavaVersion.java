/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.system;

import java.io.Console;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.util.ClassUtils;

public enum JavaVersion {
    EIGHT("1.8", Optional.class, "empty"),
    NINE("9", Optional.class, "stream"),
    TEN("10", Optional.class, "orElseThrow"),
    ELEVEN("11", String.class, "strip"),
    TWELVE("12", String.class, "describeConstable"),
    THIRTEEN("13", String.class, "stripIndent"),
    FOURTEEN("14", MethodHandles.Lookup.class, "hasFullPrivilegeAccess"),
    FIFTEEN("15", CharSequence.class, "isEmpty"),
    SIXTEEN("16", Stream.class, "toList"),
    SEVENTEEN("17", Console.class, "charset"),
    EIGHTEEN("18", Duration.class, "isPositive");

    private final String name;
    private final boolean available;

    private JavaVersion(String name, Class<?> clazz, String methodName) {
        this.name = name;
        this.available = ClassUtils.hasMethod(clazz, (String)methodName, (Class[])new Class[0]);
    }

    public String toString() {
        return this.name;
    }

    public static JavaVersion getJavaVersion() {
        List<JavaVersion> candidates = Arrays.asList(JavaVersion.values());
        Collections.reverse(candidates);
        for (JavaVersion candidate : candidates) {
            if (!candidate.available) continue;
            return candidate;
        }
        return EIGHT;
    }

    public boolean isEqualOrNewerThan(JavaVersion version) {
        return this.compareTo(version) >= 0;
    }

    public boolean isOlderThan(JavaVersion version) {
        return this.compareTo(version) < 0;
    }
}

