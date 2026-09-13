/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.javaparser.StaticJavaParser
 *  com.github.javaparser.ast.CompilationUnit
 *  com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
 *  com.github.javaparser.ast.body.TypeDeclaration
 *  com.github.javaparser.ast.type.ClassOrInterfaceType
 *  lombok.Generated
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
 */
package org.apereo.cas.configuration.metadata;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataClassSourceLocator;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataFieldVisitor;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;

public class ConfigurationMetadataUnitParser {
    private final String sourcePath;

    public void parseCompilationUnit(Set<ConfigurationMetadataProperty> collectedProps, Set<ConfigurationMetadataProperty> collectedGroups, ConfigurationMetadataProperty property, String typePath, String typeName, boolean indexNameWithBrackets) {
        try (InputStream is = Files.newInputStream(Paths.get(typePath, new String[0]), new OpenOption[0]);){
            TypeDeclaration type;
            CompilationUnit cu = StaticJavaParser.parse((InputStream)is);
            new ConfigurationMetadataFieldVisitor(collectedProps, collectedGroups, indexNameWithBrackets, typeName, this.sourcePath).visit(cu, property);
            if (!cu.getTypes().isEmpty() && (type = cu.getType(0)).isClassOrInterfaceDeclaration()) {
                ClassOrInterfaceDeclaration decl = (ClassOrInterfaceDeclaration)ClassOrInterfaceDeclaration.class.cast(type);
                for (int i = 0; i < decl.getExtendedTypes().size(); ++i) {
                    ClassOrInterfaceType parentType = (ClassOrInterfaceType)decl.getExtendedTypes().get(i);
                    ConfigurationMetadataClassSourceLocator instance = ConfigurationMetadataClassSourceLocator.getInstance();
                    Class parentClazz = instance.locatePropertiesClassForType(parentType);
                    String parentTypePath = ConfigurationMetadataClassSourceLocator.buildTypeSourcePath(this.sourcePath, parentClazz.getName());
                    this.parseCompilationUnit(collectedProps, collectedGroups, property, parentTypePath, parentClazz.getName(), indexNameWithBrackets);
                }
            }
        }
    }

    public static CompilationUnit getCompilationUnit(String typePath) {
        CompilationUnit compilationUnit;
        block8: {
            InputStream is = Files.newInputStream(Paths.get(typePath, new String[0]), new OpenOption[0]);
            try {
                compilationUnit = StaticJavaParser.parse((InputStream)is);
                if (is == null) break block8;
            }
            catch (Throwable throwable) {
                if (is != null) {
                    try {
                        is.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            is.close();
        }
        return compilationUnit;
    }

    @Generated
    public ConfigurationMetadataUnitParser(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}

