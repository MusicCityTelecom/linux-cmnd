/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.lang.reflect.Modifier;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Types;

public class VariableScopeVisitor
extends ClassCodeVisitorSupport {
    private ClassNode currentClass;
    private VariableScope currentScope;
    private boolean inClosure;
    private boolean inConstructor;
    private boolean inSpecialConstructorCall;
    private final SourceUnit source;
    private final boolean recurseInnerClasses;
    private final Deque<StateStackElement> stateStack = new LinkedList<StateStackElement>();

    public VariableScopeVisitor(SourceUnit source, boolean recurseInnerClasses) {
        this.source = source;
        this.currentScope = new VariableScope();
        this.recurseInnerClasses = recurseInnerClasses;
    }

    public VariableScopeVisitor(SourceUnit source) {
        this(source, false);
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    private void pushState(boolean isStatic) {
        this.stateStack.push(new StateStackElement(this.currentClass, this.currentScope, this.inClosure, this.inConstructor));
        this.currentScope = new VariableScope(this.currentScope);
        this.currentScope.setInStaticContext(isStatic);
    }

    private void pushState() {
        this.pushState(this.currentScope.isInStaticContext());
    }

    private void popState() {
        StateStackElement state = this.stateStack.pop();
        this.currentClass = state.clazz;
        this.currentScope = state.scope;
        this.inClosure = state.inClosure;
        this.inConstructor = state.inConstructor;
    }

    private void declare(VariableExpression variable) {
        variable.setInStaticContext(this.currentScope.isInStaticContext());
        this.declare(variable, variable);
        variable.setAccessedVariable(variable);
    }

    private void declare(Variable variable, ASTNode context) {
        String scopeType = "scope";
        String variableType = "variable";
        if (context.getClass() == FieldNode.class) {
            scopeType = "class";
            variableType = "field";
        } else if (context.getClass() == PropertyNode.class) {
            scopeType = "class";
            variableType = "property";
        }
        StringBuilder msg = new StringBuilder();
        msg.append("The current ").append(scopeType);
        msg.append(" already contains a ").append(variableType);
        msg.append(" of the name ").append(variable.getName());
        if (this.currentScope.getDeclaredVariable(variable.getName()) != null) {
            this.addError(msg.toString(), context);
            return;
        }
        for (VariableScope scope = this.currentScope.getParent(); scope != null && (scope.getClassScope() == null || VariableScopeVisitor.isAnonymous(scope.getClassScope())); scope = scope.getParent()) {
            if (scope.getDeclaredVariable(variable.getName()) == null) continue;
            this.addError(msg.toString(), context);
            break;
        }
        this.currentScope.putDeclaredVariable(variable);
    }

    private Variable findClassMember(ClassNode node, String name) {
        boolean abstractType = node.isAbstract();
        for (ClassNode cn = node; cn != null && !ClassHelper.isObjectType(cn); cn = cn.getSuperClass()) {
            FieldNode fn;
            PropertyNode pn3;
            for (FieldNode fn2 : cn.getFields()) {
                if (!name.equals(fn2.getName())) continue;
                return fn2;
            }
            for (PropertyNode pn2 : cn.getProperties()) {
                if (!name.equals(pn2.getName())) continue;
                return pn2;
            }
            for (MethodNode mn : cn.getMethods()) {
                if (!abstractType && mn.isAbstract() || !name.equals(MethodNodeUtils.getPropertyName(mn))) continue;
                for (PropertyNode pn3 : GeneralUtils.getAllProperties(cn.getSuperClass())) {
                    if (!name.equals(pn3.getName())) continue;
                    return pn3;
                }
                fn = new FieldNode(name, mn.getModifiers() & 0xF, ClassHelper.dynamicType(), cn, null);
                fn.setHasNoRealSourcePosition(true);
                fn.setDeclaringClass(cn);
                fn.setSynthetic(true);
                pn3 = new PropertyNode(fn, fn.getModifiers(), null, null);
                pn3.putNodeMetaData("access.method", mn);
                pn3.setDeclaringClass(cn);
                return pn3;
            }
            for (ClassNode in : cn.getAllInterfaces()) {
                fn = in.getDeclaredField(name);
                if (fn != null) {
                    return fn;
                }
                pn3 = in.getProperty(name);
                if (pn3 == null) continue;
                return pn3;
            }
        }
        return null;
    }

    private Variable findVariableDeclaration(String name) {
        if ("super".equals(name) || "this".equals(name)) {
            return null;
        }
        Variable variable = null;
        VariableScope scope = this.currentScope;
        boolean crossingStaticContext = false;
        while (true) {
            crossingStaticContext = crossingStaticContext || scope.isInStaticContext();
            Variable var = scope.getDeclaredVariable(name);
            if (var != null) {
                variable = var;
                break;
            }
            var = scope.getReferencedLocalVariable(name);
            if (var != null) {
                variable = var;
                break;
            }
            var = scope.getReferencedClassVariable(name);
            if (var != null) {
                variable = var;
                break;
            }
            ClassNode node = scope.getClassScope();
            if (node != null) {
                boolean requireStatic;
                Variable member = this.findClassMember(node, name);
                boolean bl = requireStatic = crossingStaticContext || this.inSpecialConstructorCall;
                while (member == null && node.getOuterClass() != null && !VariableScopeVisitor.isAnonymous(node)) {
                    requireStatic = requireStatic || Modifier.isStatic(node.getModifiers());
                    node = node.getOuterClass();
                    member = this.findClassMember(node, name);
                }
                if (member != null && (requireStatic ? member.isInStaticContext() : !node.isScript())) {
                    variable = member;
                }
                if (!VariableScopeVisitor.isAnonymous(scope.getClassScope())) break;
            }
            scope = scope.getParent();
        }
        if (variable == null) {
            variable = new DynamicVariable(name, crossingStaticContext);
        }
        boolean isClassVariable = scope.isClassScope() && !scope.isReferencedLocalVariable(name) || scope.isReferencedClassVariable(name) && scope.getDeclaredVariable(name) == null;
        VariableScope end = scope;
        for (scope = this.currentScope; scope != end; scope = scope.getParent()) {
            if (isClassVariable) {
                scope.putReferencedClassVariable(variable);
                continue;
            }
            scope.putReferencedLocalVariable(variable);
        }
        return variable;
    }

    private static boolean isAnonymous(ClassNode node) {
        return node instanceof InnerClassNode && ((InnerClassNode)node).isAnonymous() && !node.isEnum();
    }

    private void markClosureSharedVariables() {
        Iterator<Variable> it = this.currentScope.getReferencedLocalVariablesIterator();
        while (it.hasNext()) {
            Variable variable = it.next();
            variable.setClosureSharedVariable(true);
        }
    }

    private void checkFinalFieldAccess(Expression expression) {
        BiConsumer<VariableExpression, ASTNode> checkForFinal = (expr, node) -> {
            Variable variable = expr.getAccessedVariable();
            if (variable != null && Modifier.isFinal(variable.getModifiers()) && variable instanceof Parameter) {
                this.addError("Cannot assign a value to final variable '" + variable.getName() + "'", (ASTNode)node);
            }
        };
        if (expression instanceof VariableExpression) {
            checkForFinal.accept((VariableExpression)expression, expression);
        } else if (expression instanceof TupleExpression) {
            TupleExpression tuple = (TupleExpression)expression;
            for (Expression tupleExpression : tuple.getExpressions()) {
                checkForFinal.accept((VariableExpression)tupleExpression, expression);
            }
        }
    }

    private void checkPropertyOnExplicitThis(PropertyExpression expression) {
        if (!this.currentScope.isInStaticContext()) {
            return;
        }
        Expression object = expression.getObjectExpression();
        if (!(object instanceof VariableExpression)) {
            return;
        }
        VariableExpression ve = (VariableExpression)object;
        if (!ve.getName().equals("this")) {
            return;
        }
        String name = expression.getPropertyAsString();
        if (name == null || name.equals("class")) {
            return;
        }
        Variable member = this.findClassMember(this.currentClass, name);
        if (member == null) {
            return;
        }
        this.checkVariableContextAccess(member, expression);
    }

    private void checkVariableContextAccess(Variable variable, Expression expression) {
        if (variable.isInStaticContext() || !this.currentScope.isInStaticContext()) {
            return;
        }
        this.addError(variable.getName() + " is declared in a dynamic context, but you tried to access it from a static context.", expression);
        this.currentScope.putDeclaredVariable(new DynamicVariable(variable.getName(), this.currentScope.isInStaticContext()));
    }

    public void prepareVisit(ClassNode node) {
        this.currentClass = node;
        this.currentScope.setClassScope(node);
    }

    @Override
    public void visitClass(ClassNode node) {
        if (VariableScopeVisitor.isAnonymous(node)) {
            return;
        }
        this.pushState();
        this.inClosure = false;
        this.currentClass = node;
        this.currentScope.setClassScope(node);
        super.visitClass(node);
        if (this.recurseInnerClasses) {
            Iterator<InnerClassNode> innerClasses = node.getInnerClasses();
            while (innerClasses.hasNext()) {
                this.visitClass(innerClasses.next());
            }
        }
        this.popState();
    }

    @Override
    public void visitField(FieldNode node) {
        this.pushState(node.isStatic());
        super.visitField(node);
        this.popState();
    }

    @Override
    public void visitProperty(PropertyNode node) {
        this.pushState(node.isStatic());
        super.visitProperty(node);
        this.popState();
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        this.pushState(node.isStatic());
        this.inConstructor = isConstructor;
        node.setVariableScope(this.currentScope);
        this.visitAnnotations(node);
        for (Parameter parameter : node.getParameters()) {
            this.visitAnnotations(parameter);
        }
        for (Parameter parameter : node.getParameters()) {
            if (parameter.hasInitialExpression()) {
                parameter.getInitialExpression().visit(this);
            }
            this.declare(parameter, node);
        }
        this.visitClassCodeContainer(node.getCode());
        this.popState();
    }

    @Override
    public void visitBlockStatement(BlockStatement statement) {
        this.pushState();
        statement.setVariableScope(this.currentScope);
        super.visitBlockStatement(statement);
        this.popState();
    }

    @Override
    public void visitCatchStatement(CatchStatement statement) {
        this.pushState();
        Parameter parameter = statement.getVariable();
        parameter.setInStaticContext(this.currentScope.isInStaticContext());
        this.declare(parameter, statement);
        super.visitCatchStatement(statement);
        this.popState();
    }

    @Override
    public void visitForLoop(ForStatement statement) {
        this.pushState();
        statement.setVariableScope(this.currentScope);
        Parameter parameter = statement.getVariable();
        parameter.setInStaticContext(this.currentScope.isInStaticContext());
        if (parameter != ForStatement.FOR_LOOP_DUMMY) {
            this.declare(parameter, statement);
        }
        super.visitForLoop(statement);
        this.popState();
    }

    @Override
    public void visitIfElse(IfStatement statement) {
        statement.getBooleanExpression().visit(this);
        this.pushState();
        statement.getIfBlock().visit(this);
        this.popState();
        this.pushState();
        statement.getElseBlock().visit(this);
        this.popState();
    }

    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        super.visitBinaryExpression(expression);
        if (Types.isAssignment(expression.getOperation().getType())) {
            this.checkFinalFieldAccess(expression.getLeftExpression());
        }
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        this.pushState();
        expression.setVariableScope(this.currentScope);
        boolean bl = this.inClosure = !VariableScopeVisitor.isAnonymous(this.currentScope.getParent().getClassScope());
        if (expression.isParameterSpecified()) {
            for (Parameter parameter : expression.getParameters()) {
                parameter.setInStaticContext(this.currentScope.isInStaticContext());
                if (parameter.hasInitialExpression()) {
                    parameter.getInitialExpression().visit(this);
                }
                this.declare(parameter, expression);
            }
        } else if (expression.getParameters() != null) {
            Parameter var = new Parameter(ClassHelper.dynamicType(), "it");
            var.setInStaticContext(this.currentScope.isInStaticContext());
            this.currentScope.putDeclaredVariable(var);
        }
        super.visitClosureExpression(expression);
        this.markClosureSharedVariables();
        this.popState();
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression expression) {
        boolean oldInSpecialCtorFlag = this.inSpecialConstructorCall;
        this.inSpecialConstructorCall |= expression.isSpecialCall();
        super.visitConstructorCallExpression(expression);
        this.inSpecialConstructorCall = oldInSpecialCtorFlag;
        if (!expression.isUsingAnonymousInnerClass()) {
            return;
        }
        this.pushState();
        InnerClassNode innerClass = (InnerClassNode)expression.getType();
        innerClass.setVariableScope(this.currentScope);
        this.currentScope.setClassScope(innerClass);
        this.currentScope.setInStaticContext(false);
        for (MethodNode method : innerClass.getMethods()) {
            Parameter[] parameters;
            this.visitAnnotations(method);
            for (Parameter p : parameters = method.getParameters()) {
                this.visitAnnotations(p);
            }
            if (parameters.length == 0) {
                parameters = null;
            }
            this.visitClosureExpression(new ClosureExpression(parameters, method.getCode()));
        }
        for (FieldNode field : innerClass.getFields()) {
            this.visitAnnotations(field);
            Expression initExpression = field.getInitialExpression();
            if (initExpression == null) continue;
            this.pushState(field.isStatic());
            if (initExpression.isSynthetic() && initExpression instanceof VariableExpression && ((VariableExpression)initExpression).getAccessedVariable() instanceof Parameter) {
                this.popState();
                continue;
            }
            initExpression.visit(this);
            this.popState();
        }
        for (Statement initStatement : innerClass.getObjectInitializerStatements()) {
            initStatement.visit(this);
        }
        this.markClosureSharedVariables();
        this.popState();
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        this.visitAnnotations(expression);
        expression.getRightExpression().visit(this);
        if (expression.isMultipleAssignmentDeclaration()) {
            TupleExpression list = expression.getTupleExpression();
            for (Expression listExpression : list.getExpressions()) {
                this.declare((VariableExpression)listExpression);
            }
        } else {
            this.declare(expression.getVariableExpression());
        }
    }

    @Override
    public void visitFieldExpression(FieldExpression expression) {
        String name = expression.getFieldName();
        Variable variable = this.findVariableDeclaration(name);
        this.checkVariableContextAccess(variable, expression);
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression expression) {
        if (expression.isImplicitThis() && expression.getMethod() instanceof ConstantExpression) {
            ConstantExpression methodNameConstant = (ConstantExpression)expression.getMethod();
            String methodName = methodNameConstant.getText();
            if (methodName == null) {
                throw new GroovyBugError("method name is null");
            }
            Variable variable = this.findVariableDeclaration(methodName);
            if (variable != null && !(variable instanceof DynamicVariable)) {
                this.checkVariableContextAccess(variable, expression);
            }
            if (variable instanceof VariableExpression || variable instanceof Parameter) {
                VariableExpression object = new VariableExpression(variable);
                object.setSourcePosition(methodNameConstant);
                expression.setObjectExpression(object);
                ConstantExpression method = new ConstantExpression("call");
                method.setSourcePosition(methodNameConstant);
                expression.setImplicitThis(false);
                expression.setMethod(method);
            }
        }
        super.visitMethodCallExpression(expression);
    }

    @Override
    public void visitPropertyExpression(PropertyExpression expression) {
        expression.getObjectExpression().visit(this);
        expression.getProperty().visit(this);
        this.checkPropertyOnExplicitThis(expression);
    }

    @Override
    public void visitVariableExpression(VariableExpression expression) {
        Variable variable = this.findVariableDeclaration(expression.getName());
        if (variable == null) {
            return;
        }
        expression.setAccessedVariable(variable);
        this.checkVariableContextAccess(variable, expression);
    }

    private static class StateStackElement {
        final ClassNode clazz;
        final VariableScope scope;
        final boolean inClosure;
        final boolean inConstructor;

        StateStackElement(ClassNode currentClass, VariableScope currentScope, boolean inClosure, boolean inConstructor) {
            this.clazz = currentClass;
            this.scope = currentScope;
            this.inClosure = inClosure;
            this.inConstructor = inConstructor;
        }
    }
}

