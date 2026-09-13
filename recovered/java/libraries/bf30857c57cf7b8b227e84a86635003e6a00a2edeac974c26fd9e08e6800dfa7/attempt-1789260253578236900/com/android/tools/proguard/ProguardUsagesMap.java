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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProguardUsagesMap {
    private final Set<String> classes;
    private final Multimap<String, String> methodsByClass;
    private final Multimap<String, String> fieldsByClass;
    private static ImmutableSet<String> modifiers = ImmutableSet.of("abstract", "final", "native", "private", "protected", "public", new String[]{"strictfp", "static", "synchronized", "transient", "volatile"});

    private ProguardUsagesMap(Set<String> classes, Multimap<String, String> methodsByClass, Multimap<String, String> fieldsByClass) {
        this.classes = ImmutableSet.copyOf(classes);
        this.methodsByClass = ImmutableMultimap.copyOf(methodsByClass);
        this.fieldsByClass = ImmutableMultimap.copyOf(fieldsByClass);
    }

    public Collection<String> getClasses() {
        return this.classes;
    }

    public Multimap<String, String> getMethodsByClass() {
        return this.methodsByClass;
    }

    public Multimap<String, String> getFieldsByClass() {
        return this.fieldsByClass;
    }

    public boolean hasClass(String fqcn) {
        return this.classes.contains(fqcn);
    }

    public boolean hasMethod(String fqcn, String methodSig) {
        return this.methodsByClass.containsEntry(fqcn, methodSig);
    }

    public boolean hasField(String fqcn, String fieldName) {
        return this.fieldsByClass.containsEntry(fqcn, fieldName);
    }

    public static ProguardUsagesMap parse(Path usageFile) throws IOException {
        return ProguardUsagesMap.parse(Files.newBufferedReader(usageFile, Charsets.UTF_8));
    }

    public static ProguardUsagesMap parse(Reader reader) throws IOException {
        HashSet<String> classes = new HashSet<String>();
        ArrayListMultimap<String, String> methods = ArrayListMultimap.create();
        ArrayListMultimap<String, String> fields = ArrayListMultimap.create();
        try (BufferedReader br = new BufferedReader(reader);){
            String line;
            String currentClass = null;
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                if (line.isEmpty() || trimmedLine.charAt(0) == '#') continue;
                if (!Character.isWhitespace(line.charAt(0))) {
                    if (line.endsWith(":")) {
                        currentClass = line.substring(0, line.length() - 1);
                        continue;
                    }
                    classes.add(line);
                    currentClass = null;
                    continue;
                }
                line = trimmedLine;
                if (currentClass == null) {
                    String msg = "Unexpected format for proguard usages map. Encountered method or field with unknown class at line: " + line;
                    throw new IOException(msg);
                }
                if ((line = line.substring(line.lastIndexOf(58) + 1)).contains("(")) {
                    methods.put(currentClass, ProguardUsagesMap.getMethodSpec(line));
                    continue;
                }
                fields.put(currentClass, ProguardUsagesMap.getFieldName(line));
            }
        }
        return new ProguardUsagesMap(classes, methods, fields);
    }

    private static String getFieldName(String line) throws IOException {
        int i2 = line.lastIndexOf(32);
        int j2 = line.lastIndexOf(32, i2 - 1);
        int n2 = i2 = j2 >= 0 ? j2 : 0;
        if (i2 < 0 || i2 == line.length() - 1) {
            String message = "Unexpected field specification in proguard usages map: " + line;
            throw new IOException(message);
        }
        return line.substring(i2 + 1);
    }

    private static String getMethodSpec(String line) {
        return Arrays.stream(line.split(" ")).filter(s3 -> !modifiers.contains(s3)).collect(Collectors.joining(" ")).trim();
    }
}

