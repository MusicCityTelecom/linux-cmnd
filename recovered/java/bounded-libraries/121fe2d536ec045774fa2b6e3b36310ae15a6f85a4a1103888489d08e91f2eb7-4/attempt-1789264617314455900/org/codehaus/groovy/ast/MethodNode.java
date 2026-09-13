/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AstToTextHelper;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GenericsUtils;

public class MethodNode
extends AnnotatedNode {
    private final String name;
    private int modifiers;
    private boolean syntheticPublic;
    private ClassNode returnType;
    private Parameter[] parameters;
    private boolean hasDefaultValue;
    private Statement code;
    private boolean dynamicReturnType;
    private VariableScope variableScope;
    private final ClassNode[] exceptions;
    private GenericsType[] genericsTypes;
    private String typeDescriptor;

    protected MethodNode() {
        this.name = null;
        this.exceptions = null;
    }

    public MethodNode(String name, int modifiers, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        this.name = name;
        this.modifiers = modifiers;
        this.exceptions = exceptions;
        this.code = code;
        this.setReturnType(returnType);
        this.setParameters(parameters);
    }

    public String getTypeDescriptor() {
        if (this.typeDescriptor == null) {
            this.typeDescriptor = MethodNodeUtils.methodDescriptor(this, false);
        }
        return this.typeDescriptor;
    }

    @Deprecated
    public String getTypeDescriptor(boolean pretty) {
        return MethodNodeUtils.methodDescriptor(this, pretty);
    }

    private void invalidateCachedData() {
        this.typeDescriptor = null;
    }

    public Statement getCode() {
        return this.code;
    }

    public void setCode(Statement code) {
        this.code = code;
    }

    public int getModifiers() {
        return this.modifiers;
    }

    public void setModifiers(int modifiers) {
        this.invalidateCachedData();
        this.modifiers = modifiers;
        this.getVariableScope().setInStaticContext(this.isStatic());
    }

    public String getName() {
        return this.name;
    }

    public Parameter[] getParameters() {
        return this.parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.invalidateCachedData();
        VariableScope scope = new VariableScope();
        this.hasDefaultValue = false;
        this.parameters = parameters;
        if (parameters != null && parameters.length > 0) {
            for (Parameter para : parameters) {
                if (para.hasInitialExpression()) {
                    this.hasDefaultValue = true;
                }
                para.setInStaticContext(this.isStatic());
                scope.putDeclaredVariable(para);
            }
        }
        this.setVariableScope(scope);
    }

    public boolean hasDefaultValue() {
        return this.hasDefaultValue;
    }

    public ClassNode getReturnType() {
        return this.returnType;
    }

    public void setReturnType(ClassNode returnType) {
        this.invalidateCachedData();
        this.dynamicReturnType |= ClassHelper.isDynamicTyped(returnType);
        this.returnType = returnType != null ? returnType : ClassHelper.OBJECT_TYPE;
    }

    public boolean isDynamicReturnType() {
        return this.dynamicReturnType;
    }

    public boolean isVoidMethod() {
        return ClassHelper.isPrimitiveVoid(this.getReturnType());
    }

    public VariableScope getVariableScope() {
        return this.variableScope;
    }

    public void setVariableScope(VariableScope variableScope) {
        this.variableScope = variableScope;
        variableScope.setInStaticContext(this.isStatic());
    }

    public boolean isAbstract() {
        return (this.modifiers & 0x400) != 0;
    }

    public boolean isDefault() {
        return (this.modifiers & 0x409) == 1 && Optional.ofNullable(this.getDeclaringClass()).filter(ClassNode::isInterface).isPresent();
    }

    public boolean isFinal() {
        return (this.modifiers & 0x10) != 0;
    }

    public boolean isStatic() {
        return (this.modifiers & 8) != 0;
    }

    public boolean isPublic() {
        return (this.modifiers & 1) != 0;
    }

    public boolean isPrivate() {
        return (this.modifiers & 2) != 0;
    }

    public boolean isProtected() {
        return (this.modifiers & 4) != 0;
    }

    public boolean isPackageScope() {
        return (this.modifiers & 7) == 0;
    }

    public ClassNode[] getExceptions() {
        return this.exceptions;
    }

    public Statement getFirstStatement() {
        if (this.code == null) {
            return null;
        }
        Statement first = this.code;
        while (first instanceof BlockStatement) {
            List<Statement> list = ((BlockStatement)first).getStatements();
            if (list.isEmpty()) {
                first = null;
                continue;
            }
            first = list.get(0);
        }
        return first;
    }

    public GenericsType[] getGenericsTypes() {
        return this.genericsTypes;
    }

    public void setGenericsTypes(GenericsType[] genericsTypes) {
        this.invalidateCachedData();
        this.genericsTypes = genericsTypes;
    }

    public boolean hasAnnotationDefault() {
        return Boolean.TRUE.equals(this.getNodeMetaData("org.codehaus.groovy.ast.MethodNode.hasDefaultValue"));
    }

    public void setAnnotationDefault(boolean hasDefaultValue) {
        if (hasDefaultValue) {
            this.putNodeMetaData("org.codehaus.groovy.ast.MethodNode.hasDefaultValue", Boolean.TRUE);
        } else {
            this.removeNodeMetaData("org.codehaus.groovy.ast.MethodNode.hasDefaultValue");
        }
    }

    public boolean isScriptBody() {
        return Boolean.TRUE.equals(this.getNodeMetaData("org.codehaus.groovy.ast.MethodNode.isScriptBody"));
    }

    public void setIsScriptBody() {
        this.setNodeMetaData("org.codehaus.groovy.ast.MethodNode.isScriptBody", Boolean.TRUE);
    }

    public boolean isStaticConstructor() {
        return "<clinit>".equals(this.name);
    }

    public boolean isConstructor() {
        return "<init>".equals(this.name);
    }

    public boolean isSyntheticPublic() {
        return this.syntheticPublic;
    }

    public void setSyntheticPublic(boolean syntheticPublic) {
        this.syntheticPublic = syntheticPublic;
    }

    @Override
    public String getText() {
        int mask = this instanceof ConstructorNode ? Modifier.constructorModifiers() : Modifier.methodModifiers();
        String name = this.getName();
        if (name.indexOf(32) != -1) {
            name = "\"" + name + "\"";
        }
        return AstToTextHelper.getModifiersText(this.getModifiers() & mask) + ' ' + GenericsUtils.toGenericTypesString(this.getGenericsTypes()) + AstToTextHelper.getClassText(this.getReturnType()) + ' ' + name + '(' + AstToTextHelper.getParametersText(this.getParameters()) + ')' + AstToTextHelper.getThrowsClauseText(this.getExceptions()) + " { ... }";
    }

    public String toString() {
        ClassNode declaringClass = this.getDeclaringClass();
        return super.toString() + "[" + MethodNodeUtils.methodDescriptor(this, true) + (declaringClass == null ? "" : " from " + ClassNodeUtils.formatTypeName(declaringClass)) + "]";
    }
}

