/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.PrettyPrinter
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.core.util.MinimalPrettyPrinter
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.github.javaparser.StaticJavaParser
 *  com.github.javaparser.ast.CompilationUnit
 *  com.github.javaparser.ast.body.BodyDeclaration
 *  com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
 *  com.github.javaparser.ast.body.EnumDeclaration
 *  com.github.javaparser.ast.body.FieldDeclaration
 *  com.github.javaparser.ast.body.TypeDeclaration
 *  com.github.javaparser.ast.body.VariableDeclarator
 *  com.github.javaparser.ast.expr.BooleanLiteralExpr
 *  com.github.javaparser.ast.expr.FieldAccessExpr
 *  com.github.javaparser.ast.expr.LiteralStringValueExpr
 *  lombok.Generated
 *  org.apache.commons.lang3.ClassUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.ReflectionUtils
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
 *  org.springframework.boot.configurationmetadata.Deprecation$Level
 *  org.springframework.boot.configurationmetadata.ValueHint
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.util.ReflectionUtils
 */
package org.apereo.cas.configuration.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataClassSourceLocator;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataHint;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataPropertyCreator;
import org.apereo.cas.configuration.metadata.ConfigurationMetadataUnitParser;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.PropertyOwner;
import org.apereo.cas.configuration.support.RelaxedPropertyNames;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.Deprecation;
import org.springframework.boot.configurationmetadata.ValueHint;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.ReflectionUtils;

public class ConfigurationMetadataGenerator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationMetadataGenerator.class);
    private static final ObjectMapper MAPPER = new ObjectMapper().setDefaultPrettyPrinter((PrettyPrinter)new MinimalPrettyPrinter()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setSerializationInclusion(JsonInclude.Include.NON_NULL).enable(new MapperFeature[]{MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS}).findAndRegisterModules();
    private static final Pattern PATTERN_GENERICS = Pattern.compile(".+\\<(.+)\\>");
    private static final Pattern NESTED_TYPE_PATTERN1 = Pattern.compile("java\\.util\\.\\w+<(org\\.apereo\\.cas\\..+)>");
    private static final Pattern NESTED_TYPE_PATTERN2 = Pattern.compile("java\\.util\\.(List|Set)<(.+Properties)>");
    private static final Pattern MAP_TYPE_STRING_KEY_OBJECT_PATTERN = Pattern.compile("java\\.util\\.Map<java\\.lang\\.String,\\s*(org\\.apereo\\.cas\\..+)>");
    private static final Pattern NESTED_CLASS_PATTERN = Pattern.compile("(.+)\\$(\\w+)");
    private final String buildDir;
    private final String sourcePath;

    public static void main(String[] args) throws Exception {
        String buildDir = args[0];
        String projectDir = args[1];
        ConfigurationMetadataGenerator generator = new ConfigurationMetadataGenerator(buildDir, projectDir);
        generator.adjustConfigurationMetadata();
    }

    protected static Set<ConfigurationMetadataHint> processHints(Collection<ConfigurationMetadataProperty> props, Collection<ConfigurationMetadataProperty> groups) {
        LinkedHashSet<ConfigurationMetadataHint> hints = new LinkedHashSet<ConfigurationMetadataHint>(0);
        List allValidProps = props.stream().filter(p -> p.getDeprecation() == null || !Deprecation.Level.ERROR.equals((Object)p.getDeprecation().getLevel())).collect(Collectors.toList());
        for (ConfigurationMetadataProperty entry : allValidProps) {
            String propName = StringUtils.substringAfterLast((String)entry.getName(), (String)".");
            String groupName = StringUtils.substringBeforeLast((String)entry.getName(), (String)".");
            groups.stream().filter(g -> g.getName().equalsIgnoreCase(groupName)).findFirst().ifPresent(grp -> {
                try {
                    Matcher matcher = PATTERN_GENERICS.matcher(grp.getType());
                    String className = matcher.find() ? matcher.group(1) : grp.getType();
                    Class clazz = ClassUtils.getClass((String)className);
                    ConfigurationMetadataHint hint = new ConfigurationMetadataHint();
                    hint.setName(entry.getName());
                    RequiresModule annotation = Arrays.stream(clazz.getAnnotations()).filter(a -> a.annotationType().equals(RequiresModule.class)).findFirst().map(RequiresModule.class::cast).orElseThrow(() -> new RuntimeException(clazz.getCanonicalName() + " is missing @RequiresModule"));
                    ValueHint valueHint = new ValueHint();
                    valueHint.setValue((Object)ConfigurationMetadataGenerator.toJson(Map.of("module", annotation.name(), "automated", annotation.automated())));
                    valueHint.setDescription(RequiresModule.class.getName());
                    hint.getValues().add(valueHint);
                    ValueHint grpHint = new ValueHint();
                    grpHint.setValue((Object)ConfigurationMetadataGenerator.toJson(Map.of("owner", clazz.getCanonicalName())));
                    grpHint.setDescription(PropertyOwner.class.getName());
                    hint.getValues().add(grpHint);
                    RelaxedPropertyNames names = RelaxedPropertyNames.forCamelCase(propName);
                    names.getValues().forEach(Unchecked.consumer(name -> {
                        ValueHint propertyHint;
                        Field f = ReflectionUtils.findField((Class)clazz, (String)name);
                        if (f != null && f.isAnnotationPresent(RequiredProperty.class)) {
                            propertyHint = new ValueHint();
                            propertyHint.setValue((Object)ConfigurationMetadataGenerator.toJson(Map.of("owner", clazz.getName())));
                            propertyHint.setDescription(RequiredProperty.class.getName());
                            hint.getValues().add(propertyHint);
                        }
                        if (f != null && f.isAnnotationPresent(DurationCapable.class)) {
                            propertyHint = new ValueHint();
                            propertyHint.setDescription(DurationCapable.class.getName());
                            propertyHint.setValue((Object)ConfigurationMetadataGenerator.toJson(List.of(DurationCapable.class.getName())));
                            hint.getValues().add(propertyHint);
                        }
                        if (f != null && f.isAnnotationPresent(ExpressionLanguageCapable.class)) {
                            propertyHint = new ValueHint();
                            propertyHint.setDescription(ExpressionLanguageCapable.class.getName());
                            propertyHint.setValue((Object)ConfigurationMetadataGenerator.toJson(List.of(ExpressionLanguageCapable.class.getName())));
                            hint.getValues().add(propertyHint);
                        }
                    }));
                    if (!hint.getValues().isEmpty()) {
                        hints.add(hint);
                    }
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), (Throwable)e);
                }
            });
        }
        return hints;
    }

    private static void processDeprecatedProperties(Set<ConfigurationMetadataProperty> properties) {
        properties.stream().filter(p -> p.getDeprecation() != null).forEach(property -> property.getDeprecation().setLevel(Deprecation.Level.ERROR));
    }

    private static String toJson(Object value) throws Exception {
        return MAPPER.writeValueAsString(value);
    }

    private static void removeNestedConfigurationPropertyGroups(Set<ConfigurationMetadataProperty> properties, Set<ConfigurationMetadataProperty> groups) {
        Iterator<ConfigurationMetadataProperty> it = properties.iterator();
        while (it.hasNext()) {
            ConfigurationMetadataProperty entry = it.next();
            try {
                String propName = StringUtils.substringAfterLast((String)entry.getName(), (String)".");
                String groupName = StringUtils.substringBeforeLast((String)entry.getName(), (String)".");
                Optional<ConfigurationMetadataProperty> res = groups.stream().filter(g -> g.getName().equalsIgnoreCase(groupName)).findFirst();
                if (!res.isPresent()) continue;
                ConfigurationMetadataProperty grp = res.get();
                String className = grp.getType();
                Class clazz = ClassUtils.getClass((String)className);
                RelaxedPropertyNames names = RelaxedPropertyNames.forCamelCase(propName);
                names.getValues().forEach(Unchecked.consumer(name -> {
                    Field f = ReflectionUtils.findField((Class)clazz, (String)name);
                    if (f != null && f.isAnnotationPresent(NestedConfigurationProperty.class)) {
                        it.remove();
                    }
                }));
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void adjustConfigurationMetadata() throws Exception {
        File jsonFile = new File(this.buildDir, "classes/java/main/META-INF/spring-configuration-metadata.json");
        if (!jsonFile.exists()) {
            throw new RuntimeException("Could not locate file " + jsonFile.getCanonicalPath());
        }
        TypeReference<Map<String, Set<ConfigurationMetadataProperty>>> values = new TypeReference<Map<String, Set<ConfigurationMetadataProperty>>>(){};
        Map jsonMap = (Map)MAPPER.readValue(jsonFile, (TypeReference)values);
        Set properties = (Set)jsonMap.get("properties");
        Set groups = (Set)jsonMap.get("groups");
        this.processMappableProperties(properties, groups);
        this.processNestedTypes(properties, groups);
        Set<ConfigurationMetadataHint> hints = ConfigurationMetadataGenerator.processHints(properties, groups);
        this.processNestedEnumProperties(properties, groups);
        ConfigurationMetadataGenerator.processDeprecatedProperties(properties);
        this.processTopLevelEnumTypes(properties);
        ConfigurationMetadataGenerator.removeNestedConfigurationPropertyGroups(properties, groups);
        jsonMap.put("properties", properties);
        jsonMap.put("groups", groups);
        jsonMap.put("hints", hints);
        LOGGER.info("Final results is written to [{}]", (Object)jsonFile.getAbsolutePath());
        MAPPER.writeValue(jsonFile, (Object)jsonMap);
        File copy = new File(this.buildDir, jsonFile.getName());
        LOGGER.info("A copy of the results is written to [{}]", (Object)copy.getAbsolutePath());
        MAPPER.writeValue(copy, (Object)jsonMap);
    }

    private void processTopLevelEnumTypes(Set<ConfigurationMetadataProperty> properties) throws Exception {
        for (ConfigurationMetadataProperty property : properties) {
            String typePath = ConfigurationMetadataClassSourceLocator.buildTypeSourcePath(this.sourcePath, property.getType());
            File typeFile = new File(typePath);
            if (!typeFile.exists()) continue;
            CompilationUnit cu = StaticJavaParser.parse((File)new File(typePath));
            for (TypeDeclaration type : cu.getTypes()) {
                if (!type.isEnumDeclaration()) continue;
                EnumDeclaration enumMem = type.asEnumDeclaration();
                StringBuilder builder = ConfigurationMetadataPropertyCreator.collectJavadocsEnumFields(property, enumMem);
                property.setDescription(builder.toString());
            }
        }
    }

    private void processNestedTypes(Set<ConfigurationMetadataProperty> properties, Set<ConfigurationMetadataProperty> groups) {
        HashSet collectedProps = new HashSet(0);
        HashSet collectedGroups = new HashSet(0);
        LOGGER.trace("Processing nested configuration types...");
        properties.forEach(Unchecked.consumer(p -> {
            Matcher matcher;
            boolean indexBrackets = false;
            String typeName = "";
            if (NESTED_TYPE_PATTERN1.matcher(p.getType()).matches()) {
                matcher = NESTED_TYPE_PATTERN1.matcher(p.getType());
                indexBrackets = matcher.matches();
                typeName = matcher.group(1);
            } else if (NESTED_TYPE_PATTERN2.matcher(p.getType()).matches()) {
                matcher = NESTED_TYPE_PATTERN2.matcher(p.getType());
                indexBrackets = matcher.matches();
                typeName = matcher.group(2);
                Optional result = org.apereo.cas.util.ReflectionUtils.findClassBySimpleNameInPackage((String)typeName, (String)"org.apereo.cas");
                if (result.isPresent()) {
                    typeName = ((Class)result.get()).getName();
                }
            }
            if (!typeName.isEmpty()) {
                String typePath = ConfigurationMetadataClassSourceLocator.buildTypeSourcePath(this.sourcePath, typeName);
                LOGGER.debug("Matched Type [{}], Property [{}], Type: [{}], Path [{}]", new Object[]{typeName, p.getName(), p.getType(), typePath});
                ConfigurationMetadataUnitParser parser = new ConfigurationMetadataUnitParser(this.sourcePath);
                parser.parseCompilationUnit(collectedProps, collectedGroups, (ConfigurationMetadataProperty)p, typePath, typeName, indexBrackets);
            }
        }));
        properties.addAll(collectedProps);
        groups.addAll(collectedGroups);
    }

    protected void processMappableProperties(Set<ConfigurationMetadataProperty> properties, Set<ConfigurationMetadataProperty> groups) {
        HashSet collectedProps = new HashSet(0);
        HashSet collectedGroups = new HashSet(0);
        properties.forEach(property -> {
            Matcher matcher = MAP_TYPE_STRING_KEY_OBJECT_PATTERN.matcher(property.getType());
            if (matcher.matches()) {
                String valueType = matcher.group(1);
                String typePath = ConfigurationMetadataClassSourceLocator.buildTypeSourcePath(this.sourcePath, valueType);
                File typeFile = new File(typePath);
                if (typeFile.exists()) {
                    ConfigurationMetadataUnitParser parser = new ConfigurationMetadataUnitParser(this.sourcePath);
                    property.setName(property.getName().concat(".[key]"));
                    property.setId(property.getName());
                    parser.parseCompilationUnit(collectedProps, collectedGroups, (ConfigurationMetadataProperty)property, typePath, valueType, false);
                } else {
                    throw new RuntimeException(typePath + " does not exist");
                }
            }
        });
        properties.addAll(collectedProps);
        groups.addAll(collectedGroups);
    }

    protected void processNestedEnumProperties(Set<ConfigurationMetadataProperty> properties, Set<ConfigurationMetadataProperty> groups) {
        Set propertiesToProcess = properties.stream().filter(e -> {
            Matcher matcher = NESTED_CLASS_PATTERN.matcher(e.getType());
            return matcher.matches();
        }).collect(Collectors.toSet());
        for (ConfigurationMetadataProperty prop : propertiesToProcess) {
            Matcher matcher = NESTED_CLASS_PATTERN.matcher(prop.getType());
            if (!matcher.matches()) {
                throw new RuntimeException("Unable to find a match for " + prop.getType());
            }
            String parent = matcher.group(1);
            String innerType = matcher.group(2);
            String typePath = ConfigurationMetadataClassSourceLocator.buildTypeSourcePath(this.sourcePath, parent);
            try {
                TypeDeclaration primaryType = null;
                if (typePath.contains("$")) {
                    String innerClass = StringUtils.substringBetween((String)typePath, (String)"$", (String)".");
                    typePath = StringUtils.remove((String)typePath, (String)("$" + innerClass));
                    CompilationUnit cu = StaticJavaParser.parse((File)new File(typePath));
                    block3: for (TypeDeclaration type : cu.getTypes()) {
                        for (BodyDeclaration member2 : type.getMembers()) {
                            String name;
                            if (!member2.isClassOrInterfaceDeclaration() || !(name = member2.asClassOrInterfaceDeclaration().getNameAsString()).equals(innerClass)) continue;
                            primaryType = member2.asClassOrInterfaceDeclaration();
                            continue block3;
                        }
                    }
                } else {
                    CompilationUnit cu = StaticJavaParser.parse((File)new File(typePath));
                    primaryType = (TypeDeclaration)cu.getPrimaryType().get();
                }
                Objects.requireNonNull(primaryType).getMembers().stream().peek(member -> {
                    FieldDeclaration fieldDecl;
                    VariableDeclarator variable;
                    if (member.isFieldDeclaration() && (variable = (fieldDecl = member.asFieldDeclaration()).getVariable(0)).getInitializer().isPresent()) {
                        int beginIndex = prop.getName().lastIndexOf(46);
                        String propShortName = beginIndex != -1 ? prop.getName().substring(beginIndex + 1) : prop.getName();
                        Set<String> names = RelaxedPropertyNames.forCamelCase(variable.getNameAsString()).getValues();
                        if (names.contains(propShortName)) {
                            variable.getInitializer().ifPresent(exp -> {
                                Object value = null;
                                if (exp instanceof LiteralStringValueExpr) {
                                    value = ((LiteralStringValueExpr)exp).getValue();
                                } else if (exp instanceof BooleanLiteralExpr) {
                                    value = ((BooleanLiteralExpr)exp).getValue();
                                } else if (exp instanceof FieldAccessExpr) {
                                    value = ((FieldAccessExpr)exp).getNameAsString();
                                }
                                prop.setDefaultValue(value);
                            });
                        }
                    }
                }).filter(member -> {
                    if (member.isEnumDeclaration()) {
                        EnumDeclaration enumMem = member.asEnumDeclaration();
                        return enumMem.getNameAsString().equals(innerType);
                    }
                    if (member.isClassOrInterfaceDeclaration()) {
                        ClassOrInterfaceDeclaration typeName = member.asClassOrInterfaceDeclaration();
                        return typeName.getNameAsString().equals(innerType);
                    }
                    return false;
                }).forEach(member -> {
                    if (member.isEnumDeclaration()) {
                        EnumDeclaration enumMem = member.asEnumDeclaration();
                        StringBuilder builder = ConfigurationMetadataPropertyCreator.collectJavadocsEnumFields(prop, enumMem);
                        prop.setDescription(builder.toString());
                    }
                    if (member.isClassOrInterfaceDeclaration()) {
                        ClassOrInterfaceDeclaration typeName = member.asClassOrInterfaceDeclaration();
                        typeName.getFields().stream().filter(field -> !field.isStatic()).forEach(field -> {
                            HashSet<ConfigurationMetadataProperty> resultProps = new HashSet<ConfigurationMetadataProperty>();
                            HashSet<ConfigurationMetadataProperty> resultGroups = new HashSet<ConfigurationMetadataProperty>();
                            ConfigurationMetadataPropertyCreator creator = new ConfigurationMetadataPropertyCreator(false, resultProps, resultGroups, parent);
                            creator.createConfigurationProperty((FieldDeclaration)field, prop.getName());
                            groups.addAll(resultGroups);
                            properties.addAll(resultProps);
                        });
                    }
                });
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Generated
    public ConfigurationMetadataGenerator(String buildDir, String sourcePath) {
        this.buildDir = buildDir;
        this.sourcePath = sourcePath;
    }
}

