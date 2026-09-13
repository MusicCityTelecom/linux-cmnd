/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.classgen.EnumVisitor;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.TupleConstructorASTTransformation;

public class EnumCompletionVisitor
extends ClassCodeVisitorSupport {
    private final SourceUnit sourceUnit;

    public EnumCompletionVisitor(CompilationUnit cu, SourceUnit su) {
        this.sourceUnit = su;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    @Override
    public void visitClass(ClassNode node) {
        if (node.isEnum()) {
            this.completeEnum(node);
        }
    }

    private void completeEnum(ClassNode enumClass) {
        if (EnumCompletionVisitor.nonSyntheticConstructors(enumClass).isEmpty()) {
            EnumCompletionVisitor.addImplicitConstructors(enumClass);
        }
        for (ConstructorNode ctor : EnumCompletionVisitor.nonSyntheticConstructors(enumClass)) {
            this.transformConstructor(ctor);
        }
    }

    private static void addImplicitConstructors(ClassNode enumClass) {
        List<ConstructorNode> superCtors;
        if (EnumVisitor.isAnonymousInnerClass(enumClass) && !(superCtors = EnumCompletionVisitor.nonSyntheticConstructors(enumClass.getSuperClass())).isEmpty()) {
            for (ConstructorNode ctor : superCtors) {
                ClassNodeUtils.addGeneratedConstructor(enumClass, 2, ctor.getParameters(), ClassNode.EMPTY_ARRAY, new BlockStatement());
            }
            return;
        }
        TupleConstructorASTTransformation.addSpecialMapConstructors(2, enumClass, "One of the enum constants for enum " + enumClass.getName() + " was initialized with null. Please use a non-null value or define your own constructor.", true);
    }

    private void transformConstructor(ConstructorNode ctor) {
        boolean chainedThisConstructorCall = false;
        ConstructorCallExpression cce = null;
        if (ctor.firstStatementIsSpecialConstructorCall()) {
            Statement code = ctor.getFirstStatement();
            cce = (ConstructorCallExpression)((ExpressionStatement)code).getExpression();
            if (cce.isSuperCall()) {
                return;
            }
            chainedThisConstructorCall = true;
        }
        Parameter[] oldP = ctor.getParameters();
        Parameter[] newP = new Parameter[oldP.length + 2];
        String stringParameterName = this.getUniqueVariableName("__str", ctor.getCode());
        newP[0] = new Parameter(ClassHelper.STRING_TYPE, stringParameterName);
        String intParameterName = this.getUniqueVariableName("__int", ctor.getCode());
        newP[1] = new Parameter(ClassHelper.int_TYPE, intParameterName);
        System.arraycopy(oldP, 0, newP, 2, oldP.length);
        ctor.setParameters(newP);
        VariableExpression stringVariable = new VariableExpression(newP[0]);
        VariableExpression intVariable = new VariableExpression(newP[1]);
        if (chainedThisConstructorCall) {
            TupleExpression args = (TupleExpression)cce.getArguments();
            List<Expression> argsExprs = args.getExpressions();
            argsExprs.add(0, stringVariable);
            argsExprs.add(1, intVariable);
        } else {
            ArrayList<Expression> args = new ArrayList<Expression>();
            args.add(stringVariable);
            args.add(intVariable);
            if (EnumVisitor.isAnonymousInnerClass(ctor.getDeclaringClass())) {
                for (Parameter parameter : oldP) {
                    args.add(new VariableExpression(parameter));
                }
                ClassNode enumClass = ctor.getDeclaringClass().getSuperClass();
                EnumCompletionVisitor.makeBridgeConstructor(enumClass, newP);
                args.add(new CastExpression(enumClass.getPlainNodeReference(), ConstantExpression.NULL));
            }
            cce = new ConstructorCallExpression(ClassNode.SUPER, new ArgumentListExpression(args));
            BlockStatement code = new BlockStatement();
            code.addStatement(new ExpressionStatement(cce));
            Statement oldCode = ctor.getCode();
            if (oldCode != null) {
                code.addStatement(oldCode);
            }
            ctor.setCode(code);
        }
    }

    private String getUniqueVariableName(final String name, Statement code) {
        if (code == null) {
            return name;
        }
        final Object[] found = new Object[1];
        CodeVisitorSupport cv = new CodeVisitorSupport(){

            @Override
            public void visitVariableExpression(VariableExpression expression) {
                if (expression.getName().equals(name)) {
                    found[0] = Boolean.TRUE;
                }
            }
        };
        code.visit(cv);
        if (found[0] != null) {
            return this.getUniqueVariableName("_" + name, code);
        }
        return name;
    }

    private static void makeBridgeConstructor(ClassNode e, Parameter[] p) {
        Parameter[] newP = new Parameter[p.length + 1];
        for (int i = 0; i < p.length; ++i) {
            newP[i] = new Parameter(p[i].getType(), "p" + i);
        }
        newP[p.length] = new Parameter(e.getPlainNodeReference(), "$anonymous");
        if (e.getDeclaredConstructor(newP) == null) {
            ArgumentListExpression args = new ArgumentListExpression();
            for (int i = 0; i < p.length; ++i) {
                args.addExpression(new VariableExpression(newP[i]));
            }
            ExpressionStatement thisCtorCall = new ExpressionStatement(new ConstructorCallExpression(ClassNode.THIS, args));
            ClassNodeUtils.addGeneratedConstructor(e, 4096, newP, ClassNode.EMPTY_ARRAY, thisCtorCall).setSynthetic(true);
        }
    }

    private static List<ConstructorNode> nonSyntheticConstructors(ClassNode cn) {
        return cn.getDeclaredConstructors().stream().filter(c -> !c.isSynthetic()).collect(Collectors.toList());
    }
}

