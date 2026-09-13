/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.ImportNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.EmptyExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.NamedArgumentListExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.SourceUnit;

public class StaticImportVisitor
extends ClassCodeExpressionTransformer {
    private ClassNode currentClass;
    private MethodNode currentMethod;
    private SourceUnit sourceUnit;
    private boolean inSpecialConstructorCall;
    private boolean inClosure;
    private boolean inPropertyExpression;
    private Expression foundConstant;
    private Expression foundArgs;
    private boolean inAnnotation;
    private boolean inLeftExpression;

    public StaticImportVisitor(ClassNode classNode, SourceUnit sourceUnit) {
        this.currentClass = classNode;
        this.sourceUnit = sourceUnit;
    }

    @Deprecated
    public void visitClass(ClassNode classNode, SourceUnit sourceUnit) {
        this.currentClass = classNode;
        this.sourceUnit = sourceUnit;
        this.visitClass(classNode);
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        this.currentMethod = node;
        super.visitConstructorOrMethod(node, isConstructor);
        this.currentMethod = null;
    }

    @Override
    public void visitAnnotations(AnnotatedNode node) {
        boolean oldInAnnotation = this.inAnnotation;
        this.inAnnotation = true;
        super.visitAnnotations(node);
        this.inAnnotation = oldInAnnotation;
    }

    @Override
    public Expression transform(Expression exp) {
        if (exp == null) {
            return null;
        }
        Class<?> clazz = exp.getClass();
        if (clazz == VariableExpression.class) {
            return this.transformVariableExpression((VariableExpression)exp);
        }
        if (clazz == BinaryExpression.class) {
            return this.transformBinaryExpression((BinaryExpression)exp);
        }
        if (clazz == PropertyExpression.class) {
            return this.transformPropertyExpression((PropertyExpression)exp);
        }
        if (clazz == MethodCallExpression.class) {
            return this.transformMethodCallExpression((MethodCallExpression)exp);
        }
        if (exp instanceof ClosureExpression) {
            return this.transformClosureExpression((ClosureExpression)exp);
        }
        if (clazz == ConstructorCallExpression.class) {
            return this.transformConstructorCallExpression((ConstructorCallExpression)exp);
        }
        if (clazz == ArgumentListExpression.class) {
            Expression result = exp.transformExpression(this);
            if (this.foundArgs == null && this.inPropertyExpression) {
                this.foundArgs = result;
            }
            return result;
        }
        if (exp instanceof ConstantExpression) {
            ConstantExpression ce;
            Expression result = exp.transformExpression(this);
            if (this.foundConstant == null && this.inPropertyExpression) {
                this.foundConstant = result;
            }
            if (this.inAnnotation && exp instanceof AnnotationConstantExpression && (ce = (ConstantExpression)result).getValue() instanceof AnnotationNode) {
                AnnotationNode an = (AnnotationNode)ce.getValue();
                Map<String, Expression> attributes = an.getMembers();
                for (Map.Entry<String, Expression> entry : attributes.entrySet()) {
                    Expression attrExpr = this.transform(entry.getValue());
                    entry.setValue(attrExpr);
                }
            }
            return result;
        }
        return exp.transformExpression(this);
    }

    private Expression transformMapEntryExpression(MapEntryExpression me, ClassNode constructorCallType) {
        ImportNode importNode;
        Map<String, ImportNode> importNodes;
        Expression key = me.getKeyExpression();
        Expression value = me.getValueExpression();
        ModuleNode module = this.currentClass.getModule();
        if (module != null && key instanceof ConstantExpression && (importNodes = module.getStaticImports()).containsKey(key.getText()) && (importNode = importNodes.get(key.getText())).getType().equals(constructorCallType)) {
            String newKey = importNode.getFieldName();
            return new MapEntryExpression(new ConstantExpression(newKey), value.transformExpression(this));
        }
        return me;
    }

    protected Expression transformBinaryExpression(BinaryExpression be) {
        Expression left;
        int type = be.getOperation().getType();
        Expression right = this.transform(be.getRightExpression());
        be.setRightExpression(right);
        if (type == 100 && be.getLeftExpression() instanceof VariableExpression) {
            boolean oldInLeftExpression = this.inLeftExpression;
            this.inLeftExpression = true;
            left = this.transform(be.getLeftExpression());
            this.inLeftExpression = oldInLeftExpression;
            if (left instanceof StaticMethodCallExpression) {
                StaticMethodCallExpression smce = (StaticMethodCallExpression)left;
                StaticMethodCallExpression result = new StaticMethodCallExpression(smce.getOwnerType(), smce.getMethod(), right);
                result.copyNodeMetaData(smce);
                StaticImportVisitor.setSourcePosition(result, be);
                return result;
            }
        } else {
            left = this.transform(be.getLeftExpression());
        }
        be.setLeftExpression(left);
        return be;
    }

    protected Expression transformVariableExpression(VariableExpression ve) {
        Variable v = ve.getAccessedVariable();
        if (v instanceof DynamicVariable) {
            Expression result = this.findStaticFieldOrPropertyAccessorImportFromModule(v.getName());
            if (result != null) {
                StaticImportVisitor.setSourcePosition(result, ve);
                if (this.inAnnotation) {
                    result = ExpressionUtils.transformInlineConstants(result);
                }
                return result;
            }
        } else if (v instanceof FieldNode && this.inSpecialConstructorCall) {
            FieldNode fn = (FieldNode)v;
            ClassNode declaringClass = fn.getDeclaringClass();
            if (fn.isStatic() && this.currentClass.isDerivedFrom(declaringClass)) {
                PropertyExpression result = new PropertyExpression((Expression)new ClassExpression(declaringClass), v.getName());
                StaticImportVisitor.setSourcePosition(result, ve);
                return result;
            }
        }
        return ve;
    }

    protected Expression transformMethodCallExpression(MethodCallExpression mce) {
        boolean staticWrtCurrent;
        Expression object = this.transform(mce.getObjectExpression());
        Expression method = this.transform(mce.getMethod());
        Expression args = this.transform(mce.getArguments());
        boolean bl = staticWrtCurrent = this.inSpecialConstructorCall || this.currentMethod != null && this.currentMethod.isStatic();
        if (mce.isImplicitThis()) {
            Expression result;
            boolean thisOrSuperMethod;
            String name = mce.getMethodAsString();
            boolean bl2 = staticWrtCurrent ? ClassNodeUtils.hasPossibleStaticMethod(this.currentClass, name, args, true) : (thisOrSuperMethod = this.currentClass.tryFindPossibleMethod(name, args) != null);
            if (!thisOrSuperMethod && this.currentClass.getOuterClasses().stream().noneMatch(oc -> oc.tryFindPossibleMethod(name, args) != null) && (result = this.findStaticMethodImportFromModule(method, args)) != null) {
                StaticImportVisitor.setSourcePosition(result, mce);
                return result;
            }
        } else if (staticWrtCurrent && ExpressionUtils.isSuperExpression(object)) {
            MethodCallExpression result = new MethodCallExpression((Expression)new ClassExpression(this.currentClass.getSuperClass()), method, args);
            ((ASTNode)result).setSourcePosition(mce);
            return result;
        }
        if (method instanceof ConstantExpression && ((ConstantExpression)method).getValue() instanceof String && (mce.isImplicitThis() || ExpressionUtils.isThisOrSuper(object))) {
            String methodName = (String)((ConstantExpression)method).getValue();
            boolean foundInstanceMethod = !staticWrtCurrent && this.currentClass.hasPossibleMethod(methodName, args);
            Predicate<ClassNode> hasPossibleStaticMember = cn -> {
                if (ClassNodeUtils.hasPossibleStaticMethod(cn, methodName, args, true)) {
                    return true;
                }
                return args instanceof TupleExpression && ((TupleExpression)args).getExpressions().isEmpty() && ClassNodeUtils.hasPossibleStaticProperty(cn, methodName);
            };
            if (mce.isImplicitThis()) {
                if (ClassNodeUtils.isInnerClass(this.currentClass)) {
                    if (this.inSpecialConstructorCall && !foundInstanceMethod) {
                        if (this.currentClass.getOuterClass().hasPossibleMethod(methodName, args)) {
                            object = new PropertyExpression((Expression)new ClassExpression(this.currentClass.getOuterClass()), new ConstantExpression("this"));
                        } else if (hasPossibleStaticMember.test(this.currentClass.getOuterClass())) {
                            StaticMethodCallExpression result = new StaticMethodCallExpression(this.currentClass.getOuterClass(), methodName, args);
                            result.setSourcePosition(mce);
                            return result;
                        }
                    }
                } else if ((this.inSpecialConstructorCall || !this.inClosure && !foundInstanceMethod && !methodName.equals("call")) && hasPossibleStaticMember.test(this.currentClass)) {
                    StaticMethodCallExpression result = new StaticMethodCallExpression(this.currentClass, methodName, args);
                    result.setSourcePosition(mce);
                    return result;
                }
            }
        }
        MethodCallExpression result = new MethodCallExpression(object, method, args);
        result.setGenericsTypes(mce.getGenericsTypes());
        result.setMethodTarget(mce.getMethodTarget());
        result.setImplicitThis(mce.isImplicitThis());
        result.setSpreadSafe(mce.isSpreadSafe());
        result.setSafe(mce.isSafe());
        result.setSourcePosition(mce);
        return result;
    }

    protected Expression transformConstructorCallExpression(ConstructorCallExpression cce) {
        TupleExpression tuple;
        this.inSpecialConstructorCall = cce.isSpecialCall();
        Expression expression = cce.getArguments();
        if (expression instanceof TupleExpression && (tuple = (TupleExpression)expression).getExpressions().size() == 1 && (expression = tuple.getExpression(0)) instanceof NamedArgumentListExpression) {
            NamedArgumentListExpression namedArgs = (NamedArgumentListExpression)expression;
            List<MapEntryExpression> entryExpressions = namedArgs.getMapEntryExpressions();
            for (int i = 0; i < entryExpressions.size(); ++i) {
                entryExpressions.set(i, (MapEntryExpression)this.transformMapEntryExpression(entryExpressions.get(i), cce.getType()));
            }
        }
        Expression ret = cce.transformExpression(this);
        this.inSpecialConstructorCall = false;
        return ret;
    }

    protected Expression transformClosureExpression(ClosureExpression ce) {
        boolean oldInClosure = this.inClosure;
        this.inClosure = true;
        for (Parameter p : ClosureUtils.getParametersSafe(ce)) {
            if (!p.hasInitialExpression()) continue;
            p.setInitialExpression(this.transform(p.getInitialExpression()));
        }
        Statement code = ce.getCode();
        if (code != null) {
            code.visit(this);
        }
        this.inClosure = oldInClosure;
        return ce;
    }

    protected Expression transformPropertyExpression(PropertyExpression pe) {
        Expression result;
        if (this.currentMethod != null && this.currentMethod.isStatic() && ExpressionUtils.isSuperExpression(pe.getObjectExpression())) {
            PropertyExpression pexp = new PropertyExpression((Expression)new ClassExpression(this.currentClass.getUnresolvedSuperClass()), this.transform(pe.getProperty()));
            pexp.setSourcePosition(pe);
            return pexp;
        }
        boolean oldInPropertyExpression = this.inPropertyExpression;
        Expression oldFoundConstant = this.foundConstant;
        Expression oldFoundArgs = this.foundArgs;
        this.inPropertyExpression = true;
        this.foundConstant = null;
        this.foundArgs = null;
        Expression objectExpression = this.transform(pe.getObjectExpression());
        if (this.foundArgs != null && this.foundConstant != null && !this.foundConstant.getText().trim().isEmpty() && objectExpression instanceof MethodCallExpression && ((MethodCallExpression)objectExpression).isImplicitThis() && (result = this.findStaticMethodImportFromModule(this.foundConstant, this.foundArgs)) != null) {
            objectExpression = result;
            objectExpression.setSourcePosition(pe);
        }
        this.inPropertyExpression = oldInPropertyExpression;
        this.foundConstant = oldFoundConstant;
        this.foundArgs = oldFoundArgs;
        pe.setObjectExpression(objectExpression);
        return pe;
    }

    private Expression findStaticFieldOrPropertyAccessorImportFromModule(String name) {
        ImportNode importNode;
        Expression expression;
        ModuleNode module = this.currentClass.getModule();
        if (module == null) {
            return null;
        }
        Map<String, ImportNode> staticImports = module.getStaticImports();
        if (!this.inLeftExpression && (expression = this.findStaticProperty(staticImports, "is" + BeanUtils.capitalize(name))) != null) {
            return expression;
        }
        expression = this.findStaticProperty(staticImports, this.getAccessorName(name));
        if (expression != null) {
            return expression;
        }
        if (staticImports.containsKey(name) && (expression = this.findStaticPropertyOrField((importNode = staticImports.get(name)).getType(), importNode.getFieldName())) != null) {
            return expression;
        }
        for (ImportNode importNode2 : module.getStaticStarImports().values()) {
            expression = this.findStaticPropertyOrField(importNode2.getType(), name);
            if (expression == null) continue;
            return expression;
        }
        return null;
    }

    private Expression findStaticMethodImportFromModule(Expression method, Expression args) {
        boolean accessor;
        Expression expression;
        if (this.currentClass.getModule() == null) {
            return null;
        }
        if (!(method instanceof ConstantExpression)) {
            return null;
        }
        if (!(((ConstantExpression)method).getValue() instanceof String)) {
            return null;
        }
        String name = method.getText();
        Map<String, ImportNode> staticImports = this.currentClass.getModule().getStaticImports();
        if (staticImports.containsKey(name)) {
            ImportNode importNode = staticImports.get(name);
            expression = StaticImportVisitor.findStaticMethod(importNode.getType(), importNode.getFieldName(), args);
            if (expression != null) {
                return expression;
            }
            if (!this.inClosure && !this.inLeftExpression && (expression = this.findStaticPropertyOrField(importNode.getType(), importNode.getFieldName())) != null) {
                MethodCallExpression call = new MethodCallExpression(expression, "call", args);
                call.setImplicitThis(false);
                return call;
            }
        }
        if (accessor = ClassNodeUtils.isValidAccessorName(name)) {
            ImportNode importNode = staticImports.get(name);
            if (importNode != null) {
                String propName = ClassNodeUtils.getPropNameForAccessor(importNode.getFieldName());
                expression = this.findStaticPropertyAccessorGivenArgs(importNode.getType(), propName, args);
                if (expression != null) {
                    return StaticImportVisitor.newStaticMethodCallX(importNode.getType(), importNode.getFieldName(), args);
                }
            }
            if ((importNode = staticImports.get(ClassNodeUtils.getPropNameForAccessor(name))) != null) {
                ClassNode importType = importNode.getType();
                String importMember = importNode.getFieldName();
                expression = StaticImportVisitor.findStaticMethod(importType, StaticImportVisitor.prefix(name) + BeanUtils.capitalize(importMember), args);
                if (expression != null) {
                    return expression;
                }
                expression = this.findStaticPropertyAccessorGivenArgs(importType, importMember, args);
                if (expression != null) {
                    return StaticImportVisitor.newStaticMethodCallX(importType, StaticImportVisitor.prefix(name) + BeanUtils.capitalize(importMember), args);
                }
            }
        }
        Map<String, ImportNode> staticStarImports = this.currentClass.getModule().getStaticStarImports();
        if (this.currentClass.isEnum() && staticStarImports.containsKey(this.currentClass.getName())) {
            ImportNode importNode = staticStarImports.get(this.currentClass.getName());
            expression = StaticImportVisitor.findStaticMethod(importNode.getType(), name, args);
            return expression;
        }
        for (ImportNode importNode : staticStarImports.values()) {
            String propName;
            ClassNode importType = importNode.getType();
            expression = StaticImportVisitor.findStaticMethod(importType, name, args);
            if (expression != null) {
                return expression;
            }
            if (!this.inClosure && !this.inLeftExpression && (expression = this.findStaticPropertyOrField(importType, name)) != null) {
                MethodCallExpression call = new MethodCallExpression(expression, "call", args);
                call.setImplicitThis(false);
                return call;
            }
            if (!accessor || (expression = this.findStaticPropertyAccessorGivenArgs(importType, propName = ClassNodeUtils.getPropNameForAccessor(name), args)) == null) continue;
            return StaticImportVisitor.newStaticMethodCallX(importType, name, args);
        }
        return null;
    }

    private static String prefix(String name) {
        return name.startsWith("is") ? "is" : name.substring(0, 3);
    }

    private String getAccessorName(String name) {
        return this.inLeftExpression ? GeneralUtils.getSetterName(name) : GeneralUtils.getGetterName(name);
    }

    private Expression findStaticPropertyAccessorByFullName(ClassNode staticImportType, String accessorName) {
        MethodNode method;
        ArgumentListExpression argumentList = this.inLeftExpression ? new ArgumentListExpression(EmptyExpression.INSTANCE) : ArgumentListExpression.EMPTY_ARGUMENTS;
        Expression accessorExpr = StaticImportVisitor.findStaticMethod(staticImportType, accessorName, argumentList);
        if (accessorExpr != null && accessorName.startsWith("is") && ((method = staticImportType.getMethod(accessorName, Parameter.EMPTY_ARRAY)) == null || !ClassHelper.isPrimitiveBoolean(method.getReturnType()))) {
            accessorExpr = null;
        }
        return accessorExpr;
    }

    private Expression findStaticPropertyAccessorGivenArgs(ClassNode staticImportType, String propName, Expression args) {
        return this.findStaticPropertyAccessor(staticImportType, propName);
    }

    private Expression findStaticPropertyAccessor(ClassNode staticImportType, String propName) {
        String accessorName = this.getAccessorName(propName);
        Expression accessor = null;
        if (!this.inLeftExpression) {
            accessor = this.findStaticPropertyAccessorByFullName(staticImportType, "is" + accessorName.substring(3));
        }
        if (accessor == null) {
            accessor = this.findStaticPropertyAccessorByFullName(staticImportType, accessorName);
        }
        if (accessor == null && ClassNodeUtils.hasStaticProperty(staticImportType, propName)) {
            accessor = this.inLeftExpression ? StaticImportVisitor.newStaticMethodCallX(staticImportType, accessorName, ArgumentListExpression.EMPTY_ARGUMENTS) : StaticImportVisitor.newStaticPropertyX(staticImportType, propName);
        }
        return accessor;
    }

    private Expression findStaticPropertyOrField(ClassNode staticImportType, String variableName) {
        FieldNode field;
        Expression expression = this.findStaticPropertyAccessor(staticImportType, variableName);
        if (expression == null && (staticImportType.isPrimaryClassNode() || staticImportType.isResolved()) && (field = ClassNodeUtils.getField(staticImportType, variableName)) != null && field.isStatic()) {
            expression = StaticImportVisitor.newStaticPropertyX(staticImportType, variableName);
        }
        return expression;
    }

    private Expression findStaticProperty(Map<String, ImportNode> staticImports, String accessorName) {
        String propertyName;
        ClassNode importType;
        Expression expression = null;
        ImportNode importNode = staticImports.get(accessorName);
        if (importNode != null && (expression = this.findStaticPropertyAccessorByFullName(importType = importNode.getType(), importNode.getFieldName())) == null && ClassNodeUtils.hasStaticProperty(importType, propertyName = ClassNodeUtils.getPropNameForAccessor(importNode.getFieldName()))) {
            expression = this.inLeftExpression ? StaticImportVisitor.newStaticMethodCallX(importType, importNode.getFieldName(), ArgumentListExpression.EMPTY_ARGUMENTS) : StaticImportVisitor.newStaticPropertyX(importType, propertyName);
        }
        return expression;
    }

    private static Expression findStaticMethod(ClassNode staticImportType, String methodName, Expression args) {
        if ((staticImportType.isPrimaryClassNode() || staticImportType.isResolved()) && staticImportType.hasPossibleStaticMethod(methodName, args)) {
            return StaticImportVisitor.newStaticMethodCallX(staticImportType, methodName, args);
        }
        return null;
    }

    private static StaticMethodCallExpression newStaticMethodCallX(ClassNode type, String name, Expression args) {
        return new StaticMethodCallExpression(type.getPlainNodeReference(), name, args);
    }

    private static PropertyExpression newStaticPropertyX(ClassNode type, String name) {
        return new PropertyExpression((Expression)new ClassExpression(type.getPlainNodeReference()), name);
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }
}

