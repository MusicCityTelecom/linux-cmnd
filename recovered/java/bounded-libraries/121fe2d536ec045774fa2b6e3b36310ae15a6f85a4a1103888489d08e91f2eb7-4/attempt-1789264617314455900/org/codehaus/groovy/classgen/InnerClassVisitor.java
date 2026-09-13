/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.InnerClassVisitorHelper;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.trait.Traits;

public class InnerClassVisitor
extends InnerClassVisitorHelper {
    private ClassNode classNode;
    private FieldNode currentField;
    private MethodNode currentMethod;
    private final SourceUnit sourceUnit;
    private boolean inClosure;
    private boolean processingObjInitStatements;

    public InnerClassVisitor(CompilationUnit cu, SourceUnit su) {
        this.sourceUnit = su;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    @Override
    public void visitClass(ClassNode node) {
        this.classNode = node;
        InnerClassNode innerClass = null;
        if (!node.isEnum() && !node.isInterface() && node instanceof InnerClassNode && (innerClass = (InnerClassNode)node).getVariableScope() == null && (innerClass.getModifiers() & 8) == 0) {
            innerClass.addField("this$0", 4112, node.getOuterClass().getPlainNodeReference(), null);
        }
        super.visitClass(node);
        if (node.isEnum() || node.isInterface()) {
            return;
        }
        if (innerClass == null) {
            return;
        }
        if (node.getSuperClass().isInterface() || Traits.isAnnotatedWithTrait(node.getSuperClass())) {
            node.addInterface(node.getUnresolvedSuperClass());
            node.setUnresolvedSuperClass(ClassHelper.OBJECT_TYPE);
        }
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        boolean inClosureOld = this.inClosure;
        this.inClosure = true;
        super.visitClosureExpression(expression);
        this.inClosure = inClosureOld;
    }

    @Override
    protected void visitObjectInitializerStatements(ClassNode node) {
        this.processingObjInitStatements = true;
        super.visitObjectInitializerStatements(node);
        this.processingObjInitStatements = false;
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        this.currentMethod = node;
        this.visitAnnotations(node);
        this.visitClassCodeContainer(node.getCode());
        for (Parameter param : node.getParameters()) {
            if (param.hasInitialExpression()) {
                param.getInitialExpression().visit(this);
            }
            this.visitAnnotations(param);
        }
        this.currentMethod = null;
    }

    @Override
    public void visitField(FieldNode node) {
        this.currentField = node;
        super.visitField(node);
        this.currentField = null;
    }

    @Override
    public void visitProperty(PropertyNode node) {
        FieldNode field = node.getField();
        Expression init = field.getInitialExpression();
        field.setInitialValueExpression(null);
        super.visitProperty(node);
        field.setInitialValueExpression(init);
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression call) {
        super.visitConstructorCallExpression(call);
        if (!call.isUsingAnonymousInnerClass()) {
            this.passThisReference(call);
            return;
        }
        InnerClassNode innerClass = (InnerClassNode)call.getType();
        ClassNode outerClass = innerClass.getOuterClass();
        ClassNode superClass = innerClass.getSuperClass();
        if (!superClass.isInterface() && superClass.getOuterClass() != null && !superClass.isStaticClass() && (superClass.getModifiers() & 8) == 0) {
            this.insertThis0ToSuperCall(call, innerClass);
        }
        if (!innerClass.getDeclaredConstructors().isEmpty()) {
            return;
        }
        if ((innerClass.getModifiers() & 8) != 0) {
            return;
        }
        VariableScope scope = innerClass.getVariableScope();
        if (scope == null) {
            return;
        }
        boolean isStatic = !this.inClosure && this.isStatic(innerClass, scope, call);
        List<Expression> expressions = ((TupleExpression)call.getArguments()).getExpressions();
        BlockStatement block = new BlockStatement();
        int additionalParamCount = (isStatic ? 0 : 1) + scope.getReferencedLocalVariablesCount();
        ArrayList<Parameter> parameters = new ArrayList<Parameter>(expressions.size() + additionalParamCount);
        ArrayList<Expression> superCallArguments = new ArrayList<Expression>(expressions.size());
        int n = expressions.size();
        for (int i = 0; i < n; ++i) {
            Parameter param = new Parameter(ClassHelper.OBJECT_TYPE, "p" + additionalParamCount + i);
            parameters.add(param);
            superCallArguments.add(new VariableExpression(param));
        }
        ConstructorCallExpression cce = new ConstructorCallExpression(ClassNode.SUPER, new TupleExpression(superCallArguments));
        block.addStatement(new ExpressionStatement(cce));
        int pCount = 0;
        if (!isStatic) {
            ClassNode enclosingType = (this.inClosure ? ClassHelper.CLOSURE_TYPE : outerClass).getPlainNodeReference();
            expressions.add(pCount, new VariableExpression("this", enclosingType));
            Parameter thisParameter = new Parameter(enclosingType, "p" + pCount);
            parameters.add(pCount++, thisParameter);
            FieldNode thisField = innerClass.addField("this$0", 4112, enclosingType, null);
            InnerClassVisitor.addFieldInit(thisParameter, thisField, block);
        }
        Iterator<Variable> it = scope.getReferencedLocalVariablesIterator();
        while (it.hasNext()) {
            Variable var = it.next();
            VariableExpression ve = new VariableExpression(var);
            ve.setClosureSharedVariable(true);
            ve.setUseReferenceDirectly(true);
            expressions.add(pCount, ve);
            ClassNode referenceType = ClassHelper.REFERENCE_TYPE.getPlainNodeReference();
            Parameter p = new Parameter(referenceType, "p" + pCount);
            p.setOriginType(var.getOriginType());
            parameters.add(pCount++, p);
            VariableExpression initial = new VariableExpression(p);
            initial.setSynthetic(true);
            initial.setUseReferenceDirectly(true);
            FieldNode pField = innerClass.addFieldFirst(ve.getName(), 4097, referenceType, initial);
            pField.setHolder(true);
            pField.setOriginType(ClassHelper.getWrapper(var.getOriginType()));
        }
        innerClass.addConstructor(4096, parameters.toArray(Parameter.EMPTY_ARRAY), ClassNode.EMPTY_ARRAY, block);
    }

    private boolean isStatic(ClassNode innerClass, VariableScope scope, final ConstructorCallExpression call) {
        boolean isStatic = innerClass.isStaticClass();
        if (!isStatic) {
            if (this.currentMethod != null) {
                if (this.currentMethod instanceof ConstructorNode) {
                    final boolean[] precedesSuperOrThisCall = new boolean[1];
                    ConstructorNode ctor = (ConstructorNode)this.currentMethod;
                    CodeVisitorSupport visitor = new CodeVisitorSupport(){

                        @Override
                        public void visitConstructorCallExpression(ConstructorCallExpression cce) {
                            if (cce == call) {
                                precedesSuperOrThisCall[0] = true;
                            } else {
                                super.visitConstructorCallExpression(cce);
                            }
                        }
                    };
                    if (ctor.firstStatementIsSpecialConstructorCall()) {
                        this.currentMethod.getFirstStatement().visit(visitor);
                    }
                    Arrays.stream(ctor.getParameters()).filter(Parameter::hasInitialExpression).forEach(p -> p.getInitialExpression().visit(visitor));
                    isStatic = precedesSuperOrThisCall[0];
                } else {
                    isStatic = this.currentMethod.isStatic();
                }
            } else if (this.currentField != null) {
                isStatic = this.currentField.isStatic();
            }
        }
        isStatic = isStatic || innerClass.getOuterClass().getAnnotations().stream().anyMatch(a -> a.getClassNode().getName().equals("groovy.lang.Category"));
        return isStatic;
    }

    private void passThisReference(ConstructorCallExpression call) {
        ClassNode cn = call.getType().redirect();
        if (!InnerClassVisitor.shouldHandleImplicitThisForInnerClass(cn)) {
            return;
        }
        boolean isInStaticContext = this.currentMethod != null ? this.currentMethod.isStatic() : (this.currentField != null ? this.currentField.isStatic() : !this.processingObjInitStatements);
        ClassNode enclosing = this.classNode;
        while (!isInStaticContext && !enclosing.equals(cn.getOuterClass())) {
            boolean bl = isInStaticContext = (enclosing.getModifiers() & 8) != 0;
            if ((enclosing = enclosing.getOuterClass()) != null) continue;
        }
        if (isInStaticContext) {
            Expression args = call.getArguments();
            if (args instanceof TupleExpression && ((TupleExpression)args).getExpressions().isEmpty()) {
                this.addError("No enclosing instance passed in constructor call of a non-static inner class", call);
            }
        } else {
            this.insertThis0ToSuperCall(call, cn);
        }
    }

    private void insertThis0ToSuperCall(ConstructorCallExpression call, ClassNode cn) {
        ClassNode parent;
        int level = 0;
        for (parent = this.classNode; parent != null && parent != cn.getOuterClass(); parent = parent.getOuterClass()) {
            ++level;
        }
        if (parent == null) {
            return;
        }
        Expression args = call.getArguments();
        if (args instanceof TupleExpression) {
            Expression this0 = GeneralUtils.varX("this");
            for (int i = 0; i != level; ++i) {
                this0 = GeneralUtils.attrX(this0, GeneralUtils.constX("this$0"));
                if (i != 0 || !this.classNode.getDeclaredField("this$0").getType().equals(ClassHelper.CLOSURE_TYPE)) continue;
                this0 = GeneralUtils.callX(this0, "getThisObject");
            }
            ((TupleExpression)args).getExpressions().add(0, this0);
        }
    }
}

