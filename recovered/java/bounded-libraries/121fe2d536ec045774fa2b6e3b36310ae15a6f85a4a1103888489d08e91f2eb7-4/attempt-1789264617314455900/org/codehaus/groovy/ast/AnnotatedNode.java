/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import groovy.lang.groovydoc.Groovydoc;
import groovy.lang.groovydoc.GroovydocHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;

public class AnnotatedNode
extends ASTNode
implements GroovydocHolder<AnnotatedNode> {
    private List<AnnotationNode> annotations = Collections.emptyList();
    private ClassNode declaringClass;
    private boolean synthetic;

    public List<AnnotationNode> getAnnotations() {
        return this.annotations;
    }

    public List<AnnotationNode> getAnnotations(ClassNode type) {
        ArrayList<AnnotationNode> ret = new ArrayList<AnnotationNode>(this.annotations.size());
        for (AnnotationNode node : this.getAnnotations()) {
            if (!type.equals(node.getClassNode())) continue;
            ret.add(node);
        }
        return ret;
    }

    public AnnotationNode addAnnotation(ClassNode type) {
        AnnotationNode node = new AnnotationNode(type);
        this.addAnnotation(node);
        return node;
    }

    public void addAnnotation(AnnotationNode annotation) {
        if (annotation != null) {
            if (this.annotations == Collections.EMPTY_LIST) {
                this.annotations = new ArrayList<AnnotationNode>(3);
            }
            this.annotations.add(annotation);
        }
    }

    public void addAnnotations(List<AnnotationNode> annotations) {
        for (AnnotationNode annotation : annotations) {
            this.addAnnotation(annotation);
        }
    }

    public ClassNode getDeclaringClass() {
        return this.declaringClass;
    }

    public void setDeclaringClass(ClassNode declaringClass) {
        this.declaringClass = declaringClass;
    }

    @Override
    public Groovydoc getGroovydoc() {
        Groovydoc groovydoc = (Groovydoc)this.getNodeMetaData("_DOC_COMMENT");
        return groovydoc != null ? groovydoc : Groovydoc.EMPTY_GROOVYDOC;
    }

    @Override
    public AnnotatedNode getInstance() {
        return this;
    }

    public boolean hasNoRealSourcePosition() {
        return Boolean.TRUE.equals(this.getNodeMetaData("org.codehaus.groovy.ast.AnnotatedNode.hasNoRealSourcePosition"));
    }

    public void setHasNoRealSourcePosition(boolean hasNoRealSourcePosition) {
        if (hasNoRealSourcePosition) {
            this.putNodeMetaData("org.codehaus.groovy.ast.AnnotatedNode.hasNoRealSourcePosition", Boolean.TRUE);
        } else {
            this.removeNodeMetaData("org.codehaus.groovy.ast.AnnotatedNode.hasNoRealSourcePosition");
        }
    }

    public boolean isSynthetic() {
        return this.synthetic;
    }

    public void setSynthetic(boolean synthetic) {
        this.synthetic = synthetic;
    }
}

