/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.javaparser.ast.Node
 *  com.github.javaparser.ast.body.EnumDeclaration
 *  com.github.javaparser.ast.body.FieldDeclaration
 *  com.github.javaparser.ast.body.VariableDeclarator
 *  com.github.javaparser.ast.expr.BooleanLiteralExpr
 *  com.github.javaparser.ast.expr.Expression
 *  com.github.javaparser.ast.expr.FieldAccessExpr
 *  com.github.javaparser.ast.expr.LiteralStringValueExpr
 *  com.github.javaparser.ast.type.Type
 *  com.github.javaparser.javadoc.Javadoc
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
 */
package org.apereo.cas.configuration.metadata;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.LiteralStringValueExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.javadoc.Javadoc;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.configuration.support.RelaxedPropertyNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;

public class ConfigurationMetadataPropertyCreator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationMetadataPropertyCreator.class);
    private static final Map<String, String> PRIMITIVES = Map.of(String.class.getSimpleName(), String.class.getName(), Integer.class.getSimpleName(), Integer.class.getName(), Boolean.class.getSimpleName(), Boolean.class.getName(), Long.class.getSimpleName(), Long.class.getName(), Double.class.getSimpleName(), Double.class.getName(), Float.class.getSimpleName(), Float.class.getName());
    private final boolean indexNameWithBrackets;
    private final Set<ConfigurationMetadataProperty> properties;
    private final Set<ConfigurationMetadataProperty> groups;
    private final String parentClass;

    public static StringBuilder collectJavadocsEnumFields(ConfigurationMetadataProperty prop, EnumDeclaration em) {
        StringBuilder builder = new StringBuilder(StringUtils.defaultString((String)prop.getDescription()));
        builder.append("\nAvailable values are as follows:\n");
        builder.append("<ul>");
        em.getEntries().stream().filter(entry -> entry.getJavadoc().isPresent()).forEach(entry -> {
            String text = ((Javadoc)entry.getJavadoc().get()).getDescription().toText();
            text = StringUtils.appendIfMissing((String)text, (CharSequence)".", (CharSequence[])new CharSequence[0]);
            String member = String.format("<li>{@code %s}: %s</li>", entry.getNameAsString(), text);
            builder.append(member);
        });
        builder.append("</ul>");
        return builder;
    }

    public ConfigurationMetadataProperty createConfigurationProperty(FieldDeclaration fieldDecl, String propName) {
        VariableDeclarator variable = (VariableDeclarator)fieldDecl.getVariables().get(0);
        String name = StreamSupport.stream(RelaxedPropertyNames.forCamelCase(variable.getNameAsString()).spliterator(), false).map(Object::toString).findFirst().orElseGet(() -> ((VariableDeclarator)variable).getNameAsString());
        String indexedGroup = propName.concat(this.indexNameWithBrackets ? "[]" : "");
        String indexedName = indexedGroup.concat(".").concat(name);
        ConfigurationMetadataProperty prop = new ConfigurationMetadataProperty();
        if (fieldDecl.getJavadoc().isPresent()) {
            String description = ((Javadoc)fieldDecl.getJavadoc().get()).getDescription().toText();
            prop.setDescription(description);
            prop.setShortDescription(StringUtils.substringBefore((String)description, (String)"."));
        } else {
            LOGGER.error("No Javadoc found for field [{}]", (Object)indexedName);
        }
        prop.setName(indexedName);
        prop.setId(indexedName);
        Type elementType = fieldDecl.getElementType();
        String elementTypeStr = elementType.asString();
        if (PRIMITIVES.containsKey(elementTypeStr)) {
            prop.setType(PRIMITIVES.get(elementTypeStr));
        } else if (elementTypeStr.startsWith("Map<") || elementTypeStr.startsWith("List<") || elementTypeStr.startsWith("Set<")) {
            prop.setType("java.util." + elementTypeStr);
            String typeName = elementTypeStr.substring(elementTypeStr.indexOf(60) + 1, elementTypeStr.indexOf(62));
            Node parent = (Node)fieldDecl.getParentNode().get();
            parent.findFirst(EnumDeclaration.class, em -> em.getNameAsString().contains(typeName)).ifPresent(em -> {
                StringBuilder builder = ConfigurationMetadataPropertyCreator.collectJavadocsEnumFields(prop, em);
                prop.setDescription(builder.toString());
            });
        } else {
            prop.setType(elementTypeStr);
            Node parent = (Node)fieldDecl.getParentNode().get();
            Optional enumDecl = parent.findFirst(EnumDeclaration.class, em -> em.getNameAsString().contains(elementTypeStr));
            if (enumDecl.isPresent()) {
                EnumDeclaration em2 = (EnumDeclaration)enumDecl.get();
                StringBuilder builder = ConfigurationMetadataPropertyCreator.collectJavadocsEnumFields(prop, em2);
                prop.setDescription(builder.toString());
                em2.getFullyQualifiedName().ifPresent(arg_0 -> ((ConfigurationMetadataProperty)prop).setType(arg_0));
            }
        }
        Optional initializer = variable.getInitializer();
        if (initializer.isPresent()) {
            Expression exp = (Expression)initializer.get();
            if (exp instanceof LiteralStringValueExpr) {
                prop.setDefaultValue((Object)((LiteralStringValueExpr)exp).getValue());
            } else if (exp instanceof BooleanLiteralExpr) {
                prop.setDefaultValue((Object)((BooleanLiteralExpr)exp).getValue());
            } else if (exp instanceof FieldAccessExpr) {
                prop.setDefaultValue((Object)((FieldAccessExpr)exp).getNameAsString());
            }
        }
        LOGGER.debug("Collecting property [{}]", (Object)prop.getName());
        this.properties.add(prop);
        ComparableConfigurationMetadataProperty grp = new ComparableConfigurationMetadataProperty();
        grp.setId(indexedGroup);
        grp.setName(indexedGroup);
        grp.setType(this.parentClass);
        LOGGER.debug("Collecting property [{}]", (Object)grp.getName());
        this.groups.add(grp);
        return prop;
    }

    @Generated
    public ConfigurationMetadataPropertyCreator(boolean indexNameWithBrackets, Set<ConfigurationMetadataProperty> properties, Set<ConfigurationMetadataProperty> groups, String parentClass) {
        this.indexNameWithBrackets = indexNameWithBrackets;
        this.properties = properties;
        this.groups = groups;
        this.parentClass = parentClass;
    }

    private static class ComparableConfigurationMetadataProperty
    extends ConfigurationMetadataProperty {
        private static final long serialVersionUID = -7924691650447203471L;

        private ComparableConfigurationMetadataProperty() {
        }

        public int hashCode() {
            return new HashCodeBuilder(17, 37).append((Object)this.getId()).toHashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ConfigurationMetadataProperty)) {
                return false;
            }
            ConfigurationMetadataProperty rhs = (ConfigurationMetadataProperty)obj;
            return new EqualsBuilder().append((Object)this.getId(), (Object)rhs.getId()).isEquals();
        }
    }
}

