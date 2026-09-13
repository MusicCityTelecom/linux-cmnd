/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc.transformers;

import groovy.lang.GroovyClassLoader;
import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.transform.sc.transformers.StaticCompilationTransformer;
import org.codehaus.groovy.transform.stc.ExtensionMethodNode;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;

class BooleanExpressionTransformer {
    private final StaticCompilationTransformer transformer;

    BooleanExpressionTransformer(StaticCompilationTransformer transformer) {
        this.transformer = transformer;
    }

    Expression transformBooleanExpression(BooleanExpression boolX) {
        Expression expr = boolX;
        boolean reverse = false;
        do {
            if (!(expr instanceof NotExpression)) continue;
            boolean bl = reverse = !reverse;
        } while ((expr = expr.getExpression()) instanceof BooleanExpression);
        if (!(expr instanceof BinaryExpression)) {
            expr = this.transformer.transform(expr);
            ClassNode type = this.transformer.getTypeChooser().resolveType(expr, this.transformer.getClassNode());
            BooleanExpression opt = new OptimizingBooleanExpression(expr, type);
            if (reverse) {
                opt = new NotExpression(opt);
            }
            opt.setSourcePosition(boolX);
            return opt;
        }
        return this.transformer.superTransform(boolX);
    }

    private static class OptimizingBooleanExpression
    extends BooleanExpression {
        private final ClassNode type;

        OptimizingBooleanExpression(Expression expression, ClassNode type) {
            super(expression);
            this.type = type.redirect();
        }

        @Override
        public Expression transformExpression(ExpressionTransformer transformer) {
            OptimizingBooleanExpression opt = new OptimizingBooleanExpression(transformer.transform(this.getExpression()), this.type);
            opt.setSourcePosition(this);
            opt.copyNodeMetaData(this);
            return opt;
        }

        @Override
        public void visit(GroovyCodeVisitor visitor) {
            if (visitor instanceof AsmClassGenerator) {
                WriterController controller = ((AsmClassGenerator)visitor).getController();
                MethodVisitor mv = controller.getMethodVisitor();
                OperandStack os = controller.getOperandStack();
                int mark = os.getStackLength();
                this.getExpression().visit(visitor);
                if (ClassHelper.isPrimitiveType(this.type)) {
                    if (ClassHelper.isPrimitiveBoolean(this.type)) {
                        os.doGroovyCast(ClassHelper.boolean_TYPE);
                    } else {
                        BytecodeHelper.convertPrimitiveToBoolean(mv, this.type);
                        os.replace(ClassHelper.boolean_TYPE);
                    }
                    return;
                }
                if (ClassHelper.isWrapperBoolean(this.type)) {
                    Label unbox = new Label();
                    Label exit = new Label();
                    mv.visitInsn(89);
                    mv.visitJumpInsn(199, unbox);
                    mv.visitInsn(87);
                    mv.visitInsn(3);
                    mv.visitJumpInsn(167, exit);
                    mv.visitLabel(unbox);
                    if (!os.getTopOperand().equals(this.type)) {
                        BytecodeHelper.doCast(mv, this.type);
                    }
                    mv.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
                    mv.visitLabel(exit);
                    os.replace(ClassHelper.boolean_TYPE);
                    return;
                }
                mv.visitInsn(89);
                Label end = new Label();
                Label asBoolean = new Label();
                mv.visitJumpInsn(199, asBoolean);
                mv.visitInsn(87);
                mv.visitInsn(3);
                mv.visitJumpInsn(167, end);
                mv.visitLabel(asBoolean);
                GroovyClassLoader loader = controller.getSourceUnit().getClassLoader();
                if (OptimizingBooleanExpression.replaceAsBooleanWithCompareToNull(this.type, loader)) {
                    mv.visitInsn(87);
                    mv.visitInsn(4);
                } else {
                    os.castToBool(mark, true);
                }
                mv.visitLabel(end);
                os.replace(ClassHelper.boolean_TYPE);
                return;
            }
            super.visit(visitor);
        }

        private static boolean replaceAsBooleanWithCompareToNull(ClassNode type, ClassLoader dgmProvider) {
            ClassNode selfType;
            MethodNode theAsBoolean;
            List<MethodNode> asBoolean;
            return (Modifier.isFinal(type.getModifiers()) || OptimizingBooleanExpression.isEffectivelyFinal(type)) && (asBoolean = StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(dgmProvider, type, "asBoolean", ClassNode.EMPTY_ARRAY)).size() == 1 && (theAsBoolean = asBoolean.get(0)) instanceof ExtensionMethodNode && ClassHelper.isObjectType(selfType = ((ExtensionMethodNode)theAsBoolean).getExtensionMethodNode().getParameters()[0].getType());
        }

        private static boolean isEffectivelyFinal(ClassNode type) {
            if (!Modifier.isPrivate(type.getModifiers())) {
                return false;
            }
            List<ClassNode> outers = type.getOuterClasses();
            ClassNode outer = outers.get(outers.size() - 1);
            return !OptimizingBooleanExpression.isExtended(type, outer.getInnerClasses());
        }

        private static boolean isExtended(ClassNode type, Iterator<? extends ClassNode> inners) {
            while (inners.hasNext()) {
                ClassNode next = inners.next();
                if (next != type && next.isDerivedFrom(type)) {
                    return true;
                }
                if (!OptimizingBooleanExpression.isExtended(type, next.getInnerClasses())) continue;
                return true;
            }
            return false;
        }
    }
}

