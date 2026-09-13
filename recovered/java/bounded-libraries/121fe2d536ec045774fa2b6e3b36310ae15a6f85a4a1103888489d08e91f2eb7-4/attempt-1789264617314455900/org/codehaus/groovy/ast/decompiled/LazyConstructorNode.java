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
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.NodeMetaDataHandler;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.stmt.Statement;

class LazyConstructorNode
extends ConstructorNode
implements LazyInitializable {
    private final Supplier<ConstructorNode> constructorNodeSupplier;
    private ConstructorNode delegate;
    private volatile boolean initialized;

    public LazyConstructorNode(Supplier<ConstructorNode> constructorNodeSupplier) {
        this.constructorNodeSupplier = constructorNodeSupplier;
    }

    @Override
    public void doInit() {
        this.delegate = this.constructorNodeSupplier.get();
        ClassNode declaringClass = super.getDeclaringClass();
        if (null != declaringClass) {
            this.delegate.setDeclaringClass(declaringClass);
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
    public boolean firstStatementIsSpecialConstructorCall() {
        this.lazyInit();
        return this.delegate.firstStatementIsSpecialConstructorCall();
    }

    @Override
    public String getTypeDescriptor() {
        this.lazyInit();
        return this.delegate.getTypeDescriptor();
    }

    @Override
    public Statement getCode() {
        this.lazyInit();
        return this.delegate.getCode();
    }

    @Override
    public void setCode(Statement code) {
        this.lazyInit();
        this.delegate.setCode(code);
    }

    @Override
    public int getModifiers() {
        this.lazyInit();
        return this.delegate.getModifiers();
    }

    @Override
    public void setModifiers(int modifiers) {
        this.lazyInit();
        this.delegate.setModifiers(modifiers);
    }

    @Override
    public String getName() {
        this.lazyInit();
        return this.delegate.getName();
    }

    @Override
    public Parameter[] getParameters() {
        this.lazyInit();
        return this.delegate.getParameters();
    }

    @Override
    public void setParameters(Parameter[] parameters) {
        this.lazyInit();
        this.delegate.setParameters(parameters);
    }

    @Override
    public boolean hasDefaultValue() {
        this.lazyInit();
        return this.delegate.hasDefaultValue();
    }

    @Override
    public ClassNode getReturnType() {
        this.lazyInit();
        return this.delegate.getReturnType();
    }

    @Override
    public void setReturnType(ClassNode returnType) {
        this.lazyInit();
        this.delegate.setReturnType(returnType);
    }

    @Override
    public boolean isDynamicReturnType() {
        this.lazyInit();
        return this.delegate.isDynamicReturnType();
    }

    @Override
    public boolean isVoidMethod() {
        this.lazyInit();
        return this.delegate.isVoidMethod();
    }

    @Override
    public VariableScope getVariableScope() {
        this.lazyInit();
        return this.delegate.getVariableScope();
    }

    @Override
    public void setVariableScope(VariableScope variableScope) {
        this.lazyInit();
        this.delegate.setVariableScope(variableScope);
    }

    @Override
    public boolean isAbstract() {
        this.lazyInit();
        return this.delegate.isAbstract();
    }

    @Override
    public boolean isDefault() {
        this.lazyInit();
        return this.delegate.isDefault();
    }

    @Override
    public boolean isFinal() {
        this.lazyInit();
        return this.delegate.isFinal();
    }

    @Override
    public boolean isStatic() {
        this.lazyInit();
        return this.delegate.isStatic();
    }

    @Override
    public boolean isPublic() {
        this.lazyInit();
        return this.delegate.isPublic();
    }

    @Override
    public boolean isPrivate() {
        this.lazyInit();
        return this.delegate.isPrivate();
    }

    @Override
    public boolean isProtected() {
        this.lazyInit();
        return this.delegate.isProtected();
    }

    @Override
    public boolean isPackageScope() {
        this.lazyInit();
        return this.delegate.isPackageScope();
    }

    @Override
    public ClassNode[] getExceptions() {
        this.lazyInit();
        return this.delegate.getExceptions();
    }

    @Override
    public Statement getFirstStatement() {
        this.lazyInit();
        return this.delegate.getFirstStatement();
    }

    @Override
    public GenericsType[] getGenericsTypes() {
        this.lazyInit();
        return this.delegate.getGenericsTypes();
    }

    @Override
    public void setGenericsTypes(GenericsType[] genericsTypes) {
        this.lazyInit();
        this.delegate.setGenericsTypes(genericsTypes);
    }

    @Override
    public boolean hasAnnotationDefault() {
        this.lazyInit();
        return this.delegate.hasAnnotationDefault();
    }

    @Override
    public void setAnnotationDefault(boolean hasDefaultValue) {
        this.lazyInit();
        this.delegate.setAnnotationDefault(hasDefaultValue);
    }

    @Override
    public boolean isScriptBody() {
        this.lazyInit();
        return this.delegate.isScriptBody();
    }

    @Override
    public void setIsScriptBody() {
        this.lazyInit();
        this.delegate.setIsScriptBody();
    }

    @Override
    public boolean isStaticConstructor() {
        this.lazyInit();
        return this.delegate.isStaticConstructor();
    }

    @Override
    public boolean isSyntheticPublic() {
        this.lazyInit();
        return this.delegate.isSyntheticPublic();
    }

    @Override
    public void setSyntheticPublic(boolean syntheticPublic) {
        this.lazyInit();
        this.delegate.setSyntheticPublic(syntheticPublic);
    }

    @Override
    public String getText() {
        this.lazyInit();
        return this.delegate.getText();
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

    public boolean equals(Object obj) {
        this.lazyInit();
        return this.delegate.equals(obj);
    }

    @Override
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

