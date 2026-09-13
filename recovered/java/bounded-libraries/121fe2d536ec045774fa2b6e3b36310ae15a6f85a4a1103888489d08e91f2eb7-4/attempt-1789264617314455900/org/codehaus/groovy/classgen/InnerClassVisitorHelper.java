/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.util.ArrayList;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;

public abstract class InnerClassVisitorHelper
extends ClassCodeVisitorSupport {
    private static final ClassNode OBJECT_ARRAY = ClassHelper.OBJECT_TYPE.makeArray();

    protected static void addFieldInit(Parameter p, FieldNode fn, BlockStatement block) {
        block.addStatement(GeneralUtils.assignS(GeneralUtils.fieldX(fn), GeneralUtils.varX(p)));
    }

    protected static void setPropertyGetterDispatcher(BlockStatement block, Expression target, Parameter[] parameters) {
        block.addStatement(GeneralUtils.returnS(GeneralUtils.propX(target, InnerClassVisitorHelper.dynName(parameters[0]))));
    }

    protected static void setPropertySetterDispatcher(BlockStatement block, Expression target, Parameter[] parameters) {
        block.addStatement(GeneralUtils.stmt(GeneralUtils.assignX(GeneralUtils.propX(target, InnerClassVisitorHelper.dynName(parameters[0])), GeneralUtils.varX(parameters[1]))));
    }

    protected static void setMethodDispatcherCode(BlockStatement block, Expression target, Parameter[] parameters) {
        block.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(GeneralUtils.isInstanceOfX(GeneralUtils.varX(parameters[1]), OBJECT_ARRAY)), GeneralUtils.returnS(GeneralUtils.callX(target, InnerClassVisitorHelper.dynName(parameters[0]), (Expression)GeneralUtils.varX(parameters[1])))));
        block.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.eqX(GeneralUtils.propX((Expression)GeneralUtils.castX(OBJECT_ARRAY, GeneralUtils.varX(parameters[1])), "length"), GeneralUtils.constX(1, true)), GeneralUtils.returnS(GeneralUtils.callX(target, InnerClassVisitorHelper.dynName(parameters[0]), GeneralUtils.indexX(GeneralUtils.castX(OBJECT_ARRAY, GeneralUtils.varX(parameters[1])), GeneralUtils.constX(0, true))))));
        block.addStatement(GeneralUtils.returnS(GeneralUtils.callX(target, InnerClassVisitorHelper.dynName(parameters[0]), (Expression)new SpreadExpression(GeneralUtils.varX(parameters[1])))));
    }

    private static Expression dynName(Parameter p) {
        ArrayList<ConstantExpression> gStringStrings = new ArrayList<ConstantExpression>();
        gStringStrings.add(new ConstantExpression(""));
        gStringStrings.add(new ConstantExpression(""));
        ArrayList<Expression> gStringValues = new ArrayList<Expression>();
        gStringValues.add(GeneralUtils.varX(p));
        return new GStringExpression("$name", gStringStrings, gStringValues);
    }

    protected static boolean isStatic(InnerClassNode cn) {
        return cn.getDeclaredField("this$0") == null;
    }

    protected static ClassNode getClassNode(ClassNode cn, boolean isStatic) {
        return isStatic ? ClassHelper.CLASS_Type : cn;
    }

    protected static int getObjectDistance(ClassNode cn) {
        int count = 0;
        while (cn != null && !ClassHelper.isObjectType(cn)) {
            cn = cn.getSuperClass();
            ++count;
        }
        return count;
    }

    protected static boolean shouldHandleImplicitThisForInnerClass(ClassNode cn) {
        int explicitOrImplicitStatic = 16904;
        return (cn.getModifiers() & 0x4208) == 0 && cn instanceof InnerClassNode && !((InnerClassNode)cn).isAnonymous();
    }
}

