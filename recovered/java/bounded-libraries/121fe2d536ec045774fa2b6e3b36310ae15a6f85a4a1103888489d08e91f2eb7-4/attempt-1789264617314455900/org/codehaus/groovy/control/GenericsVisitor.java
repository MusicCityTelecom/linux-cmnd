/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.trait.Traits;

public class GenericsVisitor
extends ClassCodeVisitorSupport {
    private final SourceUnit source;

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    public GenericsVisitor(SourceUnit source) {
        this.source = source;
    }

    @Override
    public void visitClass(ClassNode node) {
        ClassNode sn = node.getUnresolvedSuperClass(false);
        if (this.checkWildcard(sn)) {
            return;
        }
        boolean isAIC = node instanceof InnerClassNode && ((InnerClassNode)node).isAnonymous();
        this.checkGenericsUsage(sn, node.getSuperClass(), isAIC ? Boolean.TRUE : null);
        for (ClassNode face : node.getInterfaces()) {
            this.checkGenericsUsage(face);
        }
        this.visitObjectInitializerStatements(node);
        node.visitContents(this);
    }

    @Override
    public void visitField(FieldNode node) {
        this.checkGenericsUsage(node.getType());
        super.visitField(node);
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        for (Parameter p : node.getParameters()) {
            this.checkGenericsUsage(p.getType());
        }
        if (!isConstructor) {
            this.checkGenericsUsage(node.getReturnType());
        }
        super.visitConstructorOrMethod(node, isConstructor);
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression expression) {
        ClassNode type = expression.getType();
        boolean isAIC = type instanceof InnerClassNode && ((InnerClassNode)type).isAnonymous();
        this.checkGenericsUsage(type, type.redirect(), isAIC);
        super.visitConstructorCallExpression(expression);
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        if (expression.isMultipleAssignmentDeclaration()) {
            for (Expression e : expression.getTupleExpression().getExpressions()) {
                this.checkGenericsUsage(((VariableExpression)e).getOriginType());
            }
        } else {
            this.checkGenericsUsage(expression.getVariableExpression().getOriginType());
        }
        super.visitDeclarationExpression(expression);
    }

    @Override
    public void visitArrayExpression(ArrayExpression expression) {
        this.checkGenericsUsage(expression.getType());
        super.visitArrayExpression(expression);
    }

    @Override
    public void visitCastExpression(CastExpression expression) {
        this.checkGenericsUsage(expression.getType());
        super.visitCastExpression(expression);
    }

    private boolean checkWildcard(ClassNode sn) {
        boolean wildcard = false;
        if (sn.getGenericsTypes() != null) {
            for (GenericsType gt : sn.getGenericsTypes()) {
                if (!gt.isWildcard()) continue;
                this.addError("A supertype may not specify a wildcard type", sn);
                wildcard = true;
            }
        }
        return wildcard;
    }

    private void checkGenericsUsage(ClassNode cn) {
        while (cn.isArray()) {
            cn = cn.getComponentType();
        }
        this.checkGenericsUsage(cn, cn.redirect(), null);
    }

    private void checkGenericsUsage(ClassNode cn, ClassNode rn, Boolean isAIC) {
        if (cn.isGenericsPlaceHolder()) {
            return;
        }
        GenericsType[] cnTypes = cn.getGenericsTypes();
        GenericsType[] rnTypes = rn.getGenericsTypes();
        if (cnTypes == null) {
            return;
        }
        if (rnTypes == null) {
            String message = "The class " + cn.toString(false) + " (supplied with " + GenericsVisitor.plural("type parameter", cnTypes.length) + ") refers to the class " + rn.toString(false) + " which takes no parameters";
            if (cnTypes.length == 0) {
                message = message + " (invalid Diamond <> usage?)";
            }
            this.addError(message, cn);
            return;
        }
        if (cnTypes.length != rnTypes.length) {
            String message;
            if (Boolean.FALSE.equals(isAIC) && cnTypes.length == 0) {
                return;
            }
            if (Boolean.TRUE.equals(isAIC) && cnTypes.length == 0) {
                message = "Cannot use diamond <> with anonymous inner classes";
            } else {
                message = "The class " + cn.toString(false) + " (supplied with " + GenericsVisitor.plural("type parameter", cnTypes.length) + ") refers to the class " + rn.toString(false) + " which takes " + GenericsVisitor.plural("parameter", rnTypes.length);
                if (cnTypes.length == 0) {
                    message = message + " (invalid Diamond <> usage?)";
                }
            }
            this.addError(message, cn);
            return;
        }
        for (int i = 0; i < cnTypes.length; ++i) {
            boolean valid;
            ClassNode cnType = cnTypes[i].getType();
            ClassNode rnType = rnTypes[i].getType();
            this.checkGenericsUsage(cnType);
            if (StaticTypeCheckingSupport.isUnboundedWildcard(cnTypes[i])) continue;
            ClassNode[] bounds = rnTypes[i].getUpperBounds();
            boolean bl = valid = cnType.isDerivedFrom(rnType) || (rnType.isInterface() || Traits.isTrait(rnType)) && cnType.implementsInterface(rnType);
            if (valid && bounds != null && bounds.length > 1) {
                for (int j = 1; j < bounds.length; ++j) {
                    ClassNode bound = bounds[j];
                    if (cnType.implementsInterface(bound)) continue;
                    valid = false;
                    break;
                }
            }
            if (valid) continue;
            this.addError("The type " + cnTypes[i].getName() + " is not a valid substitute for the bounded parameter <" + rnTypes[i] + ">", cnTypes[i]);
        }
    }

    private static String plural(String string, int count) {
        return "" + count + " " + (count == 1 ? string : string + "s");
    }
}

