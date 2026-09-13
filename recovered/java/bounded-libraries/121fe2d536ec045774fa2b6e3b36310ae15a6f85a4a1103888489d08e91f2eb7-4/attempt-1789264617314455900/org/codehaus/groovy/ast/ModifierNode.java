/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.util.Map;
import java.util.Objects;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class ModifierNode
extends ASTNode {
    private Integer type;
    private Integer opcode;
    private String text;
    private AnnotationNode annotationNode;
    private boolean repeatable;
    public static final int ANNOTATION_TYPE = -999;
    public static final Map<Integer, Integer> MODIFIER_OPCODE_MAP = Maps.of(-999, 0, 8, 0, 12, 0, 37, 256, 52, 32, 56, 128, 59, 64, 44, 1, 43, 4, 42, 2, 48, 8, 14, 1024, 47, 0, 39, 0, 28, 16, 49, 2048, 23, 0);

    public ModifierNode(Integer type) {
        this.type = type;
        this.opcode = MODIFIER_OPCODE_MAP.get(type);
        boolean bl = this.repeatable = -999 == type;
        if (!DefaultGroovyMethods.asBoolean((Object)this.opcode)) {
            throw new IllegalArgumentException("Unsupported modifier type: " + type);
        }
    }

    public ModifierNode(Integer type, String text) {
        this(type);
        this.text = text;
    }

    public ModifierNode(AnnotationNode annotationNode, String text) {
        this(-999, text);
        this.annotationNode = annotationNode;
        if (!DefaultGroovyMethods.asBoolean(annotationNode)) {
            throw new IllegalArgumentException("annotationNode can not be null");
        }
    }

    public boolean isModifier() {
        return !this.isAnnotation() && !this.isDef();
    }

    public boolean isVisibilityModifier() {
        return Objects.equals(44, this.type) || Objects.equals(43, this.type) || Objects.equals(42, this.type);
    }

    public boolean isNonVisibilityModifier() {
        return this.isModifier() && !this.isVisibilityModifier();
    }

    public boolean isAnnotation() {
        return Objects.equals(-999, this.type);
    }

    public boolean isDef() {
        return Objects.equals(8, this.type) || Objects.equals(12, this.type);
    }

    public Integer getType() {
        return this.type;
    }

    public Integer getOpcode() {
        return this.opcode;
    }

    public boolean isRepeatable() {
        return this.repeatable;
    }

    @Override
    public String getText() {
        return this.text;
    }

    public AnnotationNode getAnnotationNode() {
        return this.annotationNode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ModifierNode that = (ModifierNode)o;
        return Objects.equals(this.type, that.type) && Objects.equals(this.text, that.text) && Objects.equals(this.annotationNode, that.annotationNode);
    }

    public int hashCode() {
        return Objects.hash(this.type, this.text, this.annotationNode);
    }

    public String toString() {
        return this.text;
    }
}

