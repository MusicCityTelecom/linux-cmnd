/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.javaparser.ast.Modifier
 *  com.github.javaparser.ast.Node
 *  com.github.javaparser.ast.body.FieldDeclaration
 *  com.github.javaparser.ast.body.VariableDeclarator
 *  com.github.javaparser.ast.type.ClassOrInterfaceType
 *  com.github.javaparser.ast.visitor.VoidVisitorAdapter
 *  lombok.Generated
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.metadata;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataClassSourceLocator;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataPropertyCreator;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataUnitParser;
import org.apereo.cas.util.model.TriStateBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.core.io.Resource;

public class ConfigurationMetadataFieldVisitor
extends VoidVisitorAdapter<ConfigurationMetadataProperty> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationMetadataFieldVisitor.class);
    private static final Pattern EXCLUDED_TYPES = Pattern.compile(String.class.getSimpleName() + "|" + Integer.class.getSimpleName() + "|" + Double.class.getSimpleName() + "|" + Long.class.getSimpleName() + "|" + Float.class.getSimpleName() + "|" + Boolean.class.getSimpleName() + "|" + TriStateBoolean.class.getSimpleName() + "|" + Resource.class.getSimpleName() + "|" + Map.class.getSimpleName() + "<.+>|" + List.class.getSimpleName() + "<(String|Long|Double|Integer|Boolean)>|" + Set.class.getSimpleName() + "<(String|Long|Double|Integer|Boolean)>");
    private final Set<ConfigurationMetadataProperty> properties;
    private final Set<ConfigurationMetadataProperty> groups;
    private final boolean indexNameWithBrackets;
    private final String parentClass;
    private final String sourcePath;
    private ConfigurationMetadataProperty result;

    private static boolean shouldTypeBeExcluded(ClassOrInterfaceType type) {
        return EXCLUDED_TYPES.matcher(type.toString()).matches();
    }

    public void visit(FieldDeclaration field, ConfigurationMetadataProperty property) {
        if (field.getVariables().isEmpty()) {
            throw new IllegalArgumentException("Field " + field + " has no variable definitions");
        }
        VariableDeclarator variable = field.getVariable(0);
        if (field.getModifiers().contains((Node)Modifier.staticModifier())) {
            LOGGER.debug("Field [{}] is static and will be ignored for metadata generation", (Object)variable.getNameAsString());
            return;
        }
        if (field.getJavadoc().isEmpty()) {
            LOGGER.error("Field [{}] has no Javadoc defined", (Object)field);
        }
        ConfigurationMetadataPropertyCreator creator = new ConfigurationMetadataPropertyCreator(this.indexNameWithBrackets, this.properties, this.groups, this.parentClass);
        this.result = creator.createConfigurationProperty(field, property.getName());
        LOGGER.debug("Created [{}]", (Object)this.result.getName());
        this.processNestedClassOrInterfaceTypeIfNeeded(field, this.result);
    }

    protected void processNestedClassOrInterfaceTypeIfNeeded(FieldDeclaration n, ConfigurationMetadataProperty prop) {
        if (n.getElementType() instanceof ClassOrInterfaceType) {
            ClassOrInterfaceType type = (ClassOrInterfaceType)n.getElementType();
            if (!ConfigurationMetadataFieldVisitor.shouldTypeBeExcluded(type)) {
                ConfigurationMetadataClassSourceLocator instance = ConfigurationMetadataClassSourceLocator.getInstance();
                Class clz = instance.locatePropertiesClassForType(type);
                if (clz != null && !clz.isMemberClass()) {
                    String typePath = ConfigurationMetadataClassSourceLocator.buildTypeSourcePath(this.sourcePath, clz.getName());
                    LOGGER.debug("Processing type path [{}]", (Object)typePath);
                    ConfigurationMetadataUnitParser parser = new ConfigurationMetadataUnitParser(this.sourcePath);
                    parser.parseCompilationUnit(this.properties, this.groups, prop, typePath, clz.getName(), false);
                }
            } else {
                LOGGER.debug("Type [{}] is excluded from processing", (Object)type);
            }
        }
    }

    @Generated
    public ConfigurationMetadataFieldVisitor(Set<ConfigurationMetadataProperty> properties, Set<ConfigurationMetadataProperty> groups, boolean indexNameWithBrackets, String parentClass, String sourcePath) {
        this.properties = properties;
        this.groups = groups;
        this.indexNameWithBrackets = indexNameWithBrackets;
        this.parentClass = parentClass;
        this.sourcePath = sourcePath;
    }

    @Generated
    public ConfigurationMetadataProperty getResult() {
        return this.result;
    }
}

