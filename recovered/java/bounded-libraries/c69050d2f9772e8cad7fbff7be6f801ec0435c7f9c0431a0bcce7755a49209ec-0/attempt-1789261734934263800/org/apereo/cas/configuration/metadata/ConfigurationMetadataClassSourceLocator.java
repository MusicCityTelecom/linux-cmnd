/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.javaparser.ast.type.ClassOrInterfaceType
 *  org.apereo.cas.util.ReflectionUtils
 */
package org.apereo.cas.configuration.metadata;

import com.github.javaparser.ast.type.ClassOrInterfaceType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apereo.cas.util.ReflectionUtils;

public class ConfigurationMetadataClassSourceLocator {
    private static final Pattern GENERIC_TYPED_CLASS = Pattern.compile("\\w+<(\\w+)>");
    private static ConfigurationMetadataClassSourceLocator INSTANCE;
    private final Map<String, Class> cachedPropertiesClasses = new HashMap<String, Class>(0);

    public static ConfigurationMetadataClassSourceLocator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigurationMetadataClassSourceLocator();
        }
        return INSTANCE;
    }

    public static String buildTypeSourcePath(String sourcePath, String type) {
        String newName = type.replace(".", File.separator);
        return sourcePath + "/src/main/java/" + newName + ".java";
    }

    public Class locatePropertiesClassForType(ClassOrInterfaceType type) {
        String typeName = type.getNameAsString();
        if (this.cachedPropertiesClasses.containsKey(typeName)) {
            return this.cachedPropertiesClasses.get(typeName);
        }
        Matcher matcher = GENERIC_TYPED_CLASS.matcher(type.toString());
        if (matcher.matches()) {
            typeName = matcher.group(1);
        }
        IllegalArgumentException error = new IllegalArgumentException("Cant locate class for " + typeName);
        Class clz = (Class)ReflectionUtils.findClassBySimpleNameInPackage((String)typeName, (String)"org.apereo.cas").orElseThrow(() -> error);
        this.cachedPropertiesClasses.put(typeName, clz);
        return clz;
    }
}

