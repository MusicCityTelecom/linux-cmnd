/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovy.lang.groovydoc.Groovydoc;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.groovy.util.concurrent.LazyInitializable;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.NodeMetaDataHandler;
import org.codehaus.groovy.ast.expr.Expression;

class LazyFieldNode
extends FieldNode
implements LazyInitializable {
    private final Supplier<FieldNode> fieldNodeSupplier;
    private FieldNode delegate;
    private final String name;
    private volatile boolean initialized;

    public LazyFieldNode(Supplier<FieldNode> fieldNodeSupplier, String name) {
        this.fieldNodeSupplier = fieldNodeSupplier;
        this.name = name;
    }

    @Override
    public void doInit() {
        ClassNode owner;
        this.delegate = this.fieldNodeSupplier.get();
        ClassNode declaringClass = super.getDeclaringClass();
        if (null != declaringClass) {
            this.delegate.setDeclaringClass(declaringClass);
        }
        if (null != (owner = super.getOwner())) {
            this.delegate.setOwner(owner);
        }
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    @Override
    public Expression getInitialExpression() {
        this.lazyInit();
        return this.delegate.getInitialExpression();
    }

    @Override
    public int getModifiers() {
        this.lazyInit();
        return this.delegate.getModifiers();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ClassNode getType() {
        this.lazyInit();
        return this.delegate.getType();
    }

    @Override
    public void setType(ClassNode type) {
        this.lazyInit();
        this.delegate.setType(type);
    }

    @Override
    public ClassNode getOwner() {
        this.lazyInit();
        return this.delegate.getOwner();
    }

    @Override
    public boolean isHolder() {
        this.lazyInit();
        return this.delegate.isHolder();
    }

    @Override
    public void setHolder(boolean holder) {
        this.lazyInit();
        this.delegate.setHolder(holder);
    }

    @Override
    public boolean isDynamicTyped() {
        this.lazyInit();
        return this.delegate.isDynamicTyped();
    }

    @Override
    public void setModifiers(int modifiers) {
        this.lazyInit();
        this.delegate.setModifiers(modifiers);
    }

    @Override
    public boolean isStatic() {
        this.lazyInit();
        return this.delegate.isStatic();
    }

    @Override
    public boolean isEnum() {
        this.lazyInit();
        return this.delegate.isEnum();
    }

    @Override
    public boolean isFinal() {
        this.lazyInit();
        return this.delegate.isFinal();
    }

    @Override
    public boolean isVolatile() {
        this.lazyInit();
        return this.delegate.isVolatile();
    }

    @Override
    public boolean isPublic() {
        this.lazyInit();
        return this.delegate.isPublic();
    }

    @Override
    public boolean isProtected() {
        this.lazyInit();
        return this.delegate.isProtected();
    }

    @Override
    public boolean isPrivate() {
        this.lazyInit();
        return this.delegate.isPrivate();
    }

    @Override
    public void setOwner(ClassNode owner) {
        super.setOwner(owner);
    }

    @Override
    public boolean hasInitialExpression() {
        this.lazyInit();
        return this.delegate.hasInitialExpression();
    }

    @Override
    public boolean isInStaticContext() {
        this.lazyInit();
        return this.delegate.isInStaticContext();
    }

    @Override
    public Expression getInitialValueExpression() {
        this.lazyInit();
        return this.delegate.getInitialValueExpression();
    }

    @Override
    public void setInitialValueExpression(Expression initialValueExpression) {
        this.lazyInit();
        this.delegate.setInitialValueExpression(initialValueExpression);
    }

    @Override
    @Deprecated
    public boolean isClosureSharedVariable() {
        this.lazyInit();
        return this.delegate.isClosureSharedVariable();
    }

    @Override
    @Deprecated
    public void setClosureSharedVariable(boolean inClosure) {
        this.lazyInit();
        this.delegate.setClosureSharedVariable(inClosure);
    }

    @Override
    public ClassNode getOriginType() {
        this.lazyInit();
        return this.delegate.getOriginType();
    }

    @Override
    public void setOriginType(ClassNode cn) {
        this.lazyInit();
        this.delegate.setOriginType(cn);
    }

    @Override
    public void rename(String name) {
        this.lazyInit();
        this.delegate.rename(name);
    }

    @Override
    public List<AnnotationNode> getAnnotations() {
        this.lazyInit();
        return this.delegate.getAnnotations();
    }

    @Override
    public List<AnnotationNode> getAnnotations(ClassNode type) {
        this.lazyInit();
        return this.delegate.getAnnotations(type);
    }

    @Override
    public void addAnnotation(AnnotationNode annotation) {
        this.lazyInit();
        this.delegate.addAnnotation(annotation);
    }

    @Override
    public void addAnnotations(List<AnnotationNode> annotations) {
        this.lazyInit();
        this.delegate.addAnnotations(annotations);
    }

    @Override
    public ClassNode getDeclaringClass() {
        this.lazyInit();
        return this.delegate.getDeclaringClass();
    }

    @Override
    public void setDeclaringClass(ClassNode declaringClass) {
        super.setDeclaringClass(declaringClass);
    }

    @Override
    public Groovydoc getGroovydoc() {
        this.lazyInit();
        return this.delegate.getGroovydoc();
    }

    @Override
    public AnnotatedNode getInstance() {
        this.lazyInit();
        return this.delegate.getInstance();
    }

    @Override
    public boolean hasNoRealSourcePosition() {
        this.lazyInit();
        return this.delegate.hasNoRealSourcePosition();
    }

    @Override
    public void setHasNoRealSourcePosition(boolean hasNoRealSourcePosition) {
        this.lazyInit();
        this.delegate.setHasNoRealSourcePosition(hasNoRealSourcePosition);
    }

    @Override
    public boolean isSynthetic() {
        this.lazyInit();
        return this.delegate.isSynthetic();
    }

    @Override
    public void setSynthetic(boolean synthetic) {
        this.lazyInit();
        this.delegate.setSynthetic(synthetic);
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        this.lazyInit();
        this.delegate.visit(visitor);
    }

    @Override
    public String getText() {
        this.lazyInit();
        return this.delegate.getText();
    }

    @Override
    public int getLineNumber() {
        this.lazyInit();
        return this.delegate.getLineNumber();
    }

    @Override
    public void setLineNumber(int lineNumber) {
        this.lazyInit();
        this.delegate.setLineNumber(lineNumber);
    }

    @Override
    public int getColumnNumber() {
        this.lazyInit();
        return this.delegate.getColumnNumber();
    }

    @Override
    public void setColumnNumber(int columnNumber) {
        this.lazyInit();
        this.delegate.setColumnNumber(columnNumber);
    }

    @Override
    public int getLastLineNumber() {
        this.lazyInit();
        return this.delegate.getLastLineNumber();
    }

    @Override
    public void setLastLineNumber(int lastLineNumber) {
        this.lazyInit();
        this.delegate.setLastLineNumber(lastLineNumber);
    }

    @Override
    public int getLastColumnNumber() {
        this.lazyInit();
        return this.delegate.getLastColumnNumber();
    }

    @Override
    public void setLastColumnNumber(int lastColumnNumber) {
        this.lazyInit();
        this.delegate.setLastColumnNumber(lastColumnNumber);
    }

    @Override
    public void setSourcePosition(ASTNode node) {
        this.lazyInit();
        this.delegate.setSourcePosition(node);
    }

    @Override
    public void copyNodeMetaData(ASTNode other) {
        this.lazyInit();
        this.delegate.copyNodeMetaData(other);
    }

    @Override
    public Map<?, ?> getMetaDataMap() {
        this.lazyInit();
        return this.delegate.getMetaDataMap();
    }

    @Override
    public void setMetaDataMap(Map<?, ?> metaDataMap) {
        this.lazyInit();
        this.delegate.setMetaDataMap(metaDataMap);
    }

    public int hashCode() {
        this.lazyInit();
        return this.delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        this.lazyInit();
        return this.delegate.equals(obj);
    }

    public String toString() {
        this.lazyInit();
        return this.delegate.toString();
    }

    @Override
    public <T> T getNodeMetaData(Object key) {
        this.lazyInit();
        return this.delegate.getNodeMetaData(key);
    }

    @Override
    public <T> T getNodeMetaData(Object key, Function<?, ? extends T> valFn) {
        this.lazyInit();
        return this.delegate.getNodeMetaData(key, valFn);
    }

    @Override
    public void copyNodeMetaData(NodeMetaDataHandler other) {
        this.lazyInit();
        this.delegate.copyNodeMetaData(other);
    }

    @Override
    public void setNodeMetaData(Object key, Object value) {
        this.lazyInit();
        this.delegate.setNodeMetaData(key, value);
    }

    @Override
    public Object putNodeMetaData(Object key, Object value) {
        this.lazyInit();
        return this.delegate.putNodeMetaData(key, value);
    }

    @Override
    public void removeNodeMetaData(Object key) {
        this.lazyInit();
        this.delegate.removeNodeMetaData(key);
    }

    @Override
    public Map<?, ?> getNodeMetaData() {
        this.lazyInit();
        return this.delegate.getNodeMetaData();
    }
}

