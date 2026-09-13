/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import java.util.ArrayList;
import java.util.Arrays;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.control.SourceUnit;

public class StaticVerifier
extends ClassCodeVisitorSupport {
    private boolean inClosure;
    private boolean inSpecialConstructorCall;
    private MethodNode methodNode;
    private SourceUnit sourceUnit;

    @Override
    protected SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    public void visitClass(ClassNode node, SourceUnit unit) {
        this.sourceUnit = unit;
        this.visitClass(node);
    }

    @Override
    public void visitClosureExpression(ClosureExpression ce) {
        boolean oldInClosure = this.inClosure;
        this.inClosure = true;
        super.visitClosureExpression(ce);
        this.inClosure = oldInClosure;
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression cce) {
        boolean oldIsSpecialConstructorCall = this.inSpecialConstructorCall;
        this.inSpecialConstructorCall |= cce.isSpecialCall();
        super.visitConstructorCallExpression(cce);
        this.inSpecialConstructorCall = oldIsSpecialConstructorCall;
    }

    @Override
    public void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        MethodNode oldMethodNode = this.methodNode;
        this.methodNode = node;
        super.visitConstructorOrMethod(node, isConstructor);
        if (isConstructor) {
            for (Parameter param : node.getParameters()) {
                if (!param.hasInitialExpression()) continue;
                boolean oldIsSpecialConstructorCall = this.inSpecialConstructorCall;
                this.inSpecialConstructorCall = true;
                param.getInitialExpression().visit(this);
                this.inSpecialConstructorCall = oldIsSpecialConstructorCall;
            }
        }
        this.methodNode = oldMethodNode;
    }

    @Override
    public void visitVariableExpression(VariableExpression ve) {
        if (ve.getAccessedVariable() instanceof DynamicVariable && (ve.isInStaticContext() || this.inSpecialConstructorCall) && !this.inClosure) {
            FieldNode fieldNode;
            if (this.methodNode != null && this.methodNode.isStatic() && (fieldNode = StaticVerifier.getDeclaredOrInheritedField(this.methodNode.getDeclaringClass(), ve.getName())) != null && fieldNode.isStatic()) {
                return;
            }
            this.addError("Apparent variable '" + ve.getName() + "' was found in a static scope but doesn't refer to a local variable, static field or class. Possible causes:\nYou attempted to reference a variable in the binding or an instance variable from a static context.\nYou misspelled a classname or statically imported field. Please check the spelling.\nYou attempted to use a method '" + ve.getName() + "' but left out brackets in a place not allowed by the grammar.", ve);
        }
    }

    private static FieldNode getDeclaredOrInheritedField(ClassNode cn, String fieldName) {
        for (ClassNode node = cn; node != null; node = node.getSuperClass()) {
            FieldNode fn = node.getDeclaredField(fieldName);
            if (fn != null) {
                return fn;
            }
            ArrayList<ClassNode> interfacesToCheck = new ArrayList<ClassNode>(Arrays.asList(node.getInterfaces()));
            while (!interfacesToCheck.isEmpty()) {
                ClassNode nextInterface = (ClassNode)interfacesToCheck.remove(0);
                fn = nextInterface.getDeclaredField(fieldName);
                if (fn != null) {
                    return fn;
                }
                interfacesToCheck.addAll(Arrays.asList(nextInterface.getInterfaces()));
            }
        }
        return null;
    }
}

