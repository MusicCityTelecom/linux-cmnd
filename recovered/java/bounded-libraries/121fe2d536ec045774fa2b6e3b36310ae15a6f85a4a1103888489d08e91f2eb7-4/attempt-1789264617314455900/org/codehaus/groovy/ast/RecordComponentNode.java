/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;

public class RecordComponentNode
extends AnnotatedNode {
    private final String name;
    private final ClassNode type;

    public RecordComponentNode(ClassNode declaringClass, String name, ClassNode type) {
        this(declaringClass, name, type, Collections.emptyList());
    }

    public RecordComponentNode(ClassNode declaringClass, String name, ClassNode type, List<AnnotationNode> annotations) {
        this.name = name;
        this.type = type;
        this.setDeclaringClass(declaringClass);
        for (AnnotationNode annotationNode : annotations) {
            this.addAnnotation(annotationNode);
        }
    }

    public String getName() {
        return this.name;
    }

    public ClassNode getType() {
        return this.type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecordComponentNode)) {
            return false;
        }
        RecordComponentNode that = (RecordComponentNode)o;
        return this.name.equals(that.name) && this.getDeclaringClass().equals(that.getDeclaringClass());
    }

    public int hashCode() {
        return Objects.hash(this.getDeclaringClass(), this.name);
    }
}

