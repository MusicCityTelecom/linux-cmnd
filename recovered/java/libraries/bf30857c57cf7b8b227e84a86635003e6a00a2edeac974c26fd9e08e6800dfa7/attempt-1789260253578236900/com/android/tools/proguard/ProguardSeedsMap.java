/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.proguard;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ProguardSeedsMap {
    private final Set<String> classes;
    private final Multimap<String, String> methodSpecsByClass;
    private final Multimap<String, String> fieldNamesByClass;

    private ProguardSeedsMap(Set<String> classes, Multimap<String, String> methodSpecsByClass, Multimap<String, String> fieldNamesByClass) {
        this.classes = ImmutableSet.copyOf(classes);
        this.methodSpecsByClass = ImmutableMultimap.copyOf(methodSpecsByClass);
        this.fieldNamesByClass = ImmutableMultimap.copyOf(fieldNamesByClass);
    }

    public boolean hasClass(String fqcn) {
        return this.classes.contains(fqcn);
    }

    public boolean hasMethod(String fqcn, String methodNameAndParams) {
        return this.methodSpecsByClass.containsEntry(fqcn, methodNameAndParams);
    }

    public boolean hasField(String fqcn, String fieldName) {
        return this.fieldNamesByClass.containsEntry(fqcn, fieldName);
    }

    public static ProguardSeedsMap parse(Path seedsMap) throws IOException {
        return ProguardSeedsMap.parse(Files.newBufferedReader(seedsMap, Charsets.UTF_8));
    }

    public static ProguardSeedsMap parse(Reader reader) throws IOException {
        HashSet<String> classes = new HashSet<String>();
        ArrayListMultimap<String, String> methodsByClass = ArrayListMultimap.create();
        ArrayListMultimap<String, String> fieldsByClass = ArrayListMultimap.create();
        try (BufferedReader br = new BufferedReader(reader);){
            String line;
            while ((line = br.readLine()) != null) {
                int index = line.indexOf(58);
                if (index < 0) {
                    classes.add(line.trim());
                    continue;
                }
                String fqcn = line.substring(0, index).trim();
                String rest = line.substring(index + 1).trim();
                if (rest.contains("(")) {
                    if (rest.indexOf(32) != -1) {
                        rest = rest.substring(rest.indexOf(32) + 1);
                    }
                    methodsByClass.put(fqcn, rest);
                    continue;
                }
                String fieldName = rest.substring(rest.indexOf(32) + 1);
                fieldsByClass.put(fqcn, fieldName);
            }
        }
        return new ProguardSeedsMap(classes, methodsByClass, fieldsByClass);
    }
}

