/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.Reference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class CategoryASTTransformation
implements ASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        ClassNode targetClass;
        if (nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof ClassNode)) {
            sourceUnit.addError(new SyntaxException("@Category can only be added to a ClassNode but got: " + (nodes.length == 2 ? nodes[1] : "nothing"), nodes[0].getLineNumber(), nodes[0].getColumnNumber()));
        }
        ClassNode sourceClass = (ClassNode)nodes[1];
        Expression value = ((AnnotationNode)nodes[0]).getMember("value");
        if (value instanceof ClassExpression) {
            targetClass = value.getType();
        } else {
            targetClass = ClassHelper.OBJECT_TYPE;
            sourceUnit.addErrorAndContinue(new SyntaxException("@Category must define 'value' which is the class to apply this category to", nodes[0]));
        }
        if (CategoryASTTransformation.ensureNoInstanceFieldOrProperty(sourceClass, sourceUnit)) {
            this.transformReferencesToThis(targetClass, sourceClass, sourceUnit);
            new VariableScopeVisitor(sourceUnit, true).visitClass(sourceClass);
        }
    }

    private static boolean ensureNoInstanceFieldOrProperty(ClassNode sourceClass, SourceUnit sourceUnit) {
        boolean valid = true;
        for (FieldNode fieldNode : sourceClass.getFields()) {
            if (fieldNode.isStatic() || fieldNode.getLineNumber() <= 0) continue;
            CategoryASTTransformation.addUnsupportedInstanceMemberError(fieldNode.getName(), fieldNode, sourceUnit);
            valid = false;
        }
        for (PropertyNode propertyNode : sourceClass.getProperties()) {
            if (propertyNode.isStatic() || propertyNode.getLineNumber() <= 0) continue;
            CategoryASTTransformation.addUnsupportedInstanceMemberError(propertyNode.getName(), propertyNode, sourceUnit);
            valid = false;
        }
        return valid;
    }

    private static void addUnsupportedInstanceMemberError(String name, ASTNode node, SourceUnit unit) {
        unit.addErrorAndContinue(new SyntaxException("The @Category transformation does not support instance " + (node instanceof FieldNode ? "fields" : "properties") + " but found [" + name + "]", node));
    }

    private void transformReferencesToThis(final ClassNode targetClass, ClassNode sourceClass, final SourceUnit sourceUnit) {
        final Reference<Parameter> selfParameter = new Reference<Parameter>();
        final LinkedList varStack = new LinkedList();
        HashSet<String> names = new HashSet<String>();
        for (FieldNode fn : sourceClass.getFields()) {
            names.add(fn.getName());
        }
        for (PropertyNode pn : sourceClass.getProperties()) {
            names.add(pn.getName());
        }
        varStack.add(names);
        ClassCodeExpressionTransformer transformer = new ClassCodeExpressionTransformer(){
            private boolean inClosure;

            private void addVariablesToStack(Parameter[] parameter) {
                HashSet<String> names = new HashSet<String>((Collection)varStack.getLast());
                for (Parameter p : parameter) {
                    names.add(p.getName());
                }
                varStack.add(names);
            }

            private Expression createThisExpression() {
                VariableExpression ve = new VariableExpression("$this", targetClass);
                ve.setClosureSharedVariable(true);
                return ve;
            }

            @Override
            protected SourceUnit getSourceUnit() {
                return sourceUnit;
            }

            @Override
            public Expression transform(Expression expression) {
                if (expression instanceof VariableExpression) {
                    VariableExpression ve = (VariableExpression)expression;
                    if (ve.isThisExpression()) {
                        Expression thisExpression = this.createThisExpression();
                        thisExpression.setSourcePosition(ve);
                        return thisExpression;
                    }
                    if (!(this.inClosure || ve.isSuperExpression() || ((Set)varStack.getLast()).contains(ve.getName()))) {
                        PropertyExpression pe = new PropertyExpression(this.createThisExpression(), ve.getName());
                        pe.setSourcePosition(ve);
                        return pe;
                    }
                } else if (expression instanceof MethodCallExpression) {
                    MethodCallExpression mce = (MethodCallExpression)expression;
                    if (this.inClosure && mce.isImplicitThis() && ExpressionUtils.isThisExpression(mce.getObjectExpression())) {
                        mce.setArguments(this.transform(mce.getArguments()));
                        mce.setMethod(this.transform(mce.getMethod()));
                        return mce;
                    }
                } else if (expression instanceof ClosureExpression) {
                    ClosureExpression ce = (ClosureExpression)expression;
                    this.addVariablesToStack(ClosureUtils.hasImplicitParameter(ce) ? GeneralUtils.params(GeneralUtils.param(ClassHelper.OBJECT_TYPE, "it")) : ClosureUtils.getParametersSafe(ce));
                    ce.getVariableScope().putReferencedLocalVariable((Variable)selfParameter.get());
                    Collections.addAll((Collection)varStack.getLast(), "owner", "delegate", "thisObject");
                    boolean closure = this.inClosure;
                    this.inClosure = true;
                    ce.getCode().visit(this);
                    varStack.removeLast();
                    this.inClosure = closure;
                }
                return super.transform(expression);
            }

            @Override
            public void visitBlockStatement(BlockStatement statement) {
                HashSet names = new HashSet((Collection)varStack.getLast());
                varStack.add(names);
                super.visitBlockStatement(statement);
                varStack.remove(names);
            }

            @Override
            public void visitCatchStatement(CatchStatement statement) {
                ((Set)varStack.getLast()).add(statement.getVariable().getName());
                super.visitCatchStatement(statement);
                ((Set)varStack.getLast()).remove(statement.getVariable().getName());
            }

            @Override
            public void visitClosureExpression(ClosureExpression expression) {
            }

            @Override
            public void visitDeclarationExpression(DeclarationExpression expression) {
                if (expression.isMultipleAssignmentDeclaration()) {
                    for (Expression e : expression.getTupleExpression().getExpressions()) {
                        VariableExpression ve = (VariableExpression)e;
                        ((Set)varStack.getLast()).add(ve.getName());
                    }
                } else {
                    VariableExpression ve = expression.getVariableExpression();
                    ((Set)varStack.getLast()).add(ve.getName());
                }
                super.visitDeclarationExpression(expression);
            }

            @Override
            public void visitExpressionStatement(ExpressionStatement statement) {
                if (statement.getExpression() instanceof DeclarationExpression) {
                    statement.getExpression().visit(this);
                }
                super.visitExpressionStatement(statement);
            }

            @Override
            public void visitForLoop(ForStatement statement) {
                Expression exp = statement.getCollectionExpression();
                exp.visit(this);
                Parameter loopParam = statement.getVariable();
                if (loopParam != null) {
                    ((Set)varStack.getLast()).add(loopParam.getName());
                }
                super.visitForLoop(statement);
            }

            @Override
            public void visitMethod(MethodNode node) {
                this.addVariablesToStack(node.getParameters());
                super.visitMethod(node);
                varStack.removeLast();
            }
        };
        for (MethodNode method : sourceClass.getMethods()) {
            if (method.isStatic()) continue;
            Parameter p = new Parameter(targetClass, "$this");
            p.setClosureSharedVariable(true);
            selfParameter.set(p);
            Parameter[] oldParams = method.getParameters();
            Parameter[] newParams = new Parameter[oldParams.length + 1];
            newParams[0] = p;
            System.arraycopy(oldParams, 0, newParams, 1, oldParams.length);
            method.setModifiers(method.getModifiers() | 8);
            method.setParameters(newParams);
            transformer.visitMethod(method);
        }
    }
}

