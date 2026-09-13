/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.tools;

import groovy.lang.MetaProperty;
import groovyjarjarasm.asm.MethodVisitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import org.codehaus.groovy.antlr.PrimitiveHelper;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ElvisOperatorExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.tools.BeanUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.BytecodeExpression;
import org.codehaus.groovy.control.io.ReaderSource;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.AbstractASTTransformation;

public class GeneralUtils {
    public static final Token ASSIGN = Token.newSymbol(100, -1, -1);
    public static final Token EQ = Token.newSymbol(123, -1, -1);
    public static final Token NE = Token.newSymbol(120, -1, -1);
    public static final Token NOT_IDENTICAL = Token.newSymbol(122, -1, -1);
    public static final Token LT = Token.newSymbol(124, -1, -1);
    public static final Token AND = Token.newSymbol(164, -1, -1);
    public static final Token OR = Token.newSymbol(162, -1, -1);
    public static final Token CMP = Token.newSymbol(128, -1, -1);
    public static final Token INSTANCEOF = Token.newSymbol(544, -1, -1);
    public static final Token PLUS = Token.newSymbol(200, -1, -1);
    private static final Token INDEX = Token.newSymbol("[", -1, -1);

    public static BinaryExpression andX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, AND, rhv);
    }

    public static ArgumentListExpression args(Expression ... expressions) {
        ArrayList<Expression> list = new ArrayList<Expression>(expressions.length);
        Collections.addAll(list, expressions);
        return GeneralUtils.args(list);
    }

    public static ArgumentListExpression args(List<Expression> expressions) {
        return new ArgumentListExpression(expressions);
    }

    public static ArgumentListExpression args(Parameter ... parameters) {
        return new ArgumentListExpression(parameters);
    }

    public static ArgumentListExpression args(String ... names) {
        return GeneralUtils.args((Expression[])Arrays.stream(names).map(GeneralUtils::varX).toArray(Expression[]::new));
    }

    public static CastExpression asX(ClassNode type, Expression expression) {
        return CastExpression.asExpression(type, expression);
    }

    public static Statement assignS(Expression target, Expression value) {
        return GeneralUtils.stmt(GeneralUtils.assignX(target, value));
    }

    public static Statement assignNullS(Expression target) {
        return GeneralUtils.assignS(target, ConstantExpression.EMPTY_EXPRESSION);
    }

    public static Expression assignX(Expression target, Expression value) {
        return GeneralUtils.binX(target, ASSIGN, value);
    }

    public static Expression attrX(Expression oe, Expression prop) {
        return new AttributeExpression(oe, prop);
    }

    public static BinaryExpression binX(Expression left, Token token, Expression right) {
        return new BinaryExpression(left, token, right);
    }

    public static BlockStatement block(VariableScope scope, Statement ... stmts) {
        BlockStatement block = new BlockStatement();
        block.setVariableScope(scope);
        for (Statement stmt : stmts) {
            block.addStatement(stmt);
        }
        return block;
    }

    public static BlockStatement block(VariableScope scope, List<Statement> stmts) {
        BlockStatement block = new BlockStatement();
        block.setVariableScope(scope);
        for (Statement stmt : stmts) {
            block.addStatement(stmt);
        }
        return block;
    }

    public static BlockStatement block(Statement ... stmts) {
        BlockStatement block = new BlockStatement();
        for (Statement stmt : stmts) {
            block.addStatement(stmt);
        }
        return block;
    }

    public static BooleanExpression boolX(Expression expr) {
        return new BooleanExpression(expr);
    }

    public static BytecodeExpression bytecodeX(final Consumer<MethodVisitor> writer) {
        return new BytecodeExpression(){

            @Override
            public void visit(MethodVisitor visitor) {
                writer.accept(visitor);
            }
        };
    }

    public static BytecodeExpression bytecodeX(ClassNode type, Consumer<MethodVisitor> writer) {
        BytecodeExpression expression = GeneralUtils.bytecodeX(writer);
        expression.setType(type);
        return expression;
    }

    public static MethodCallExpression callSuperX(String methodName) {
        return GeneralUtils.callSuperX(methodName, MethodCallExpression.NO_ARGUMENTS);
    }

    public static MethodCallExpression callSuperX(String methodName, Expression args) {
        return GeneralUtils.callX((Expression)GeneralUtils.varX("super"), methodName, args);
    }

    public static MethodCallExpression callThisX(String methodName) {
        return GeneralUtils.callThisX(methodName, MethodCallExpression.NO_ARGUMENTS);
    }

    public static MethodCallExpression callThisX(String methodName, Expression args) {
        return GeneralUtils.callX((Expression)GeneralUtils.varX("this"), methodName, args);
    }

    public static MethodCallExpression callX(Expression receiver, String methodName) {
        return GeneralUtils.callX(receiver, methodName, MethodCallExpression.NO_ARGUMENTS);
    }

    public static MethodCallExpression callX(Expression receiver, String methodName, Expression args) {
        return new MethodCallExpression(receiver, methodName, args);
    }

    public static MethodCallExpression callX(Expression receiver, Expression method, Expression args) {
        return new MethodCallExpression(receiver, method, args);
    }

    public static StaticMethodCallExpression callX(ClassNode receiver, String methodName) {
        return GeneralUtils.callX(receiver, methodName, MethodCallExpression.NO_ARGUMENTS);
    }

    public static StaticMethodCallExpression callX(ClassNode receiver, String methodName, Expression args) {
        return new StaticMethodCallExpression(receiver, methodName, args);
    }

    public static CaseStatement caseS(Expression expression, Statement code) {
        return new CaseStatement(expression, code);
    }

    public static CastExpression castX(ClassNode type, Expression expression) {
        return new CastExpression(type, expression);
    }

    public static CastExpression castX(ClassNode type, Expression expression, boolean ignoreAutoboxing) {
        return new CastExpression(type, expression, ignoreAutoboxing);
    }

    public static CatchStatement catchS(Parameter variable, Statement code) {
        return new CatchStatement(variable, code);
    }

    public static ClassExpression classX(ClassNode clazz) {
        return new ClassExpression(clazz);
    }

    public static ClassExpression classX(Class<?> clazz) {
        return GeneralUtils.classX(ClassHelper.make(clazz).getPlainNodeReference());
    }

    public static ClosureExpression closureX(Parameter[] params, Statement code) {
        return new ClosureExpression(params, code);
    }

    public static ClosureExpression closureX(Statement code) {
        return GeneralUtils.closureX(Parameter.EMPTY_ARRAY, code);
    }

    public static LambdaExpression lambdaX(Parameter[] params, Statement code) {
        return new LambdaExpression(params, code);
    }

    public static LambdaExpression lambdaX(Statement code) {
        return GeneralUtils.lambdaX(Parameter.EMPTY_ARRAY, code);
    }

    public static BinaryExpression cmpX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, CMP, rhv);
    }

    public static ConstantExpression constX(Object val) {
        return new ConstantExpression(val);
    }

    public static ConstantExpression constX(Object val, boolean keepPrimitive) {
        return new ConstantExpression(val, keepPrimitive);
    }

    public static ConstructorCallExpression ctorX(ClassNode type, Expression args) {
        return new ConstructorCallExpression(type, args);
    }

    public static ConstructorCallExpression ctorX(ClassNode type) {
        return GeneralUtils.ctorX(type, ArgumentListExpression.EMPTY_ARGUMENTS);
    }

    public static Statement ctorSuperS(Expression args) {
        return GeneralUtils.stmt(GeneralUtils.ctorSuperX(args));
    }

    public static ConstructorCallExpression ctorSuperX(Expression args) {
        return GeneralUtils.ctorX(ClassNode.SUPER, args);
    }

    public static Statement ctorThisS(Expression args) {
        return GeneralUtils.stmt(GeneralUtils.ctorThisX(args));
    }

    public static ConstructorCallExpression ctorThisX(Expression args) {
        return GeneralUtils.ctorX(ClassNode.THIS, args);
    }

    public static Statement ctorSuperS() {
        return GeneralUtils.stmt(GeneralUtils.ctorSuperX());
    }

    public static ConstructorCallExpression ctorSuperX() {
        return GeneralUtils.ctorX(ClassNode.SUPER);
    }

    public static Statement ctorThisS() {
        return GeneralUtils.stmt(GeneralUtils.ctorThisX());
    }

    public static ConstructorCallExpression ctorThisX() {
        return GeneralUtils.ctorX(ClassNode.THIS);
    }

    public static Statement declS(Expression target, Expression init) {
        return GeneralUtils.stmt(GeneralUtils.declX(target, init));
    }

    public static DeclarationExpression declX(Expression target, Expression init) {
        return new DeclarationExpression(target, ASSIGN, init);
    }

    public static ConstantExpression defaultValueX(ClassNode type) {
        return Optional.ofNullable((ConstantExpression)PrimitiveHelper.getDefaultValueForPrimitive(type)).orElse(GeneralUtils.nullX());
    }

    public static ElvisOperatorExpression elvisX(Expression base, Expression otherwise) {
        return new ElvisOperatorExpression(base, otherwise);
    }

    public static MapEntryExpression entryX(Expression key, Expression value) {
        return new MapEntryExpression(key, value);
    }

    public static BinaryExpression eqX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, EQ, rhv);
    }

    public static BooleanExpression equalsNullX(Expression argExpr) {
        return GeneralUtils.boolX(GeneralUtils.eqX(argExpr, GeneralUtils.nullX()));
    }

    public static FieldExpression fieldX(FieldNode fieldNode) {
        return new FieldExpression(fieldNode);
    }

    public static FieldExpression fieldX(ClassNode owner, String fieldName) {
        return new FieldExpression(owner.getField(fieldName));
    }

    public static Expression findArg(String argName) {
        return GeneralUtils.propX((Expression)GeneralUtils.varX("args"), argName);
    }

    public static ForStatement forS(Parameter variable, Expression collectionExpression, Statement loopS) {
        return new ForStatement(variable, collectionExpression, loopS);
    }

    public static List<MethodNode> getAllMethods(ClassNode type) {
        ArrayList<MethodNode> result = new ArrayList<MethodNode>();
        for (ClassNode node = type; node != null; node = node.getSuperClass()) {
            result.addAll(node.getMethods());
        }
        return result;
    }

    public static List<PropertyNode> getAllProperties(ClassNode type) {
        ArrayList<PropertyNode> result = new ArrayList<PropertyNode>();
        for (ClassNode node = type; node != null; node = node.getSuperClass()) {
            result.addAll(node.getProperties());
        }
        return result;
    }

    public static List<FieldNode> getInstanceNonPropertyFields(ClassNode cNode) {
        ArrayList<FieldNode> result = new ArrayList<FieldNode>();
        for (FieldNode fNode : cNode.getFields()) {
            if (fNode.isStatic() || cNode.getProperty(fNode.getName()) != null) continue;
            result.add(fNode);
        }
        return result;
    }

    public static List<String> getInstanceNonPropertyFieldNames(ClassNode cNode) {
        List<FieldNode> fList = GeneralUtils.getInstanceNonPropertyFields(cNode);
        ArrayList<String> result = new ArrayList<String>(fList.size());
        for (FieldNode fNode : fList) {
            result.add(fNode.getName());
        }
        return result;
    }

    public static List<PropertyNode> getInstanceProperties(ClassNode cNode) {
        ArrayList<PropertyNode> result = new ArrayList<PropertyNode>();
        for (PropertyNode pNode : cNode.getProperties()) {
            if (pNode.isStatic()) continue;
            result.add(pNode);
        }
        return result;
    }

    public static List<String> getInstancePropertyNames(ClassNode cNode) {
        List<PropertyNode> pList = BeanUtils.getAllProperties(cNode, false, false, true);
        ArrayList<String> result = new ArrayList<String>(pList.size());
        for (PropertyNode pNode : pList) {
            result.add(pNode.getName());
        }
        return result;
    }

    public static List<FieldNode> getInstancePropertyFields(ClassNode cNode) {
        ArrayList<FieldNode> result = new ArrayList<FieldNode>();
        for (PropertyNode pNode : cNode.getProperties()) {
            if (pNode.isStatic()) continue;
            result.add(pNode.getField());
        }
        return result;
    }

    private static void addAllInterfaces(Set<ClassNode> result, ClassNode source) {
        for (ClassNode in : source.getInterfaces()) {
            if (!result.add(in = GenericsUtils.parameterizeType(source, in))) continue;
            GeneralUtils.addAllInterfaces(result, in);
        }
        ClassNode sc = source.redirect().getUnresolvedSuperClass(false);
        if (sc != null && !ClassHelper.isObjectType(sc)) {
            GeneralUtils.addAllInterfaces(result, GenericsUtils.parameterizeType(source, sc));
        }
    }

    public static Set<ClassNode> getInterfacesAndSuperInterfaces(ClassNode cNode) {
        LinkedHashSet<ClassNode> result = new LinkedHashSet<ClassNode>();
        if (cNode.isInterface()) {
            result.add(cNode);
        }
        GeneralUtils.addAllInterfaces(result, cNode);
        return result;
    }

    public static List<FieldNode> getSuperNonPropertyFields(ClassNode cNode) {
        List<Object> result = ClassHelper.isObjectType(cNode) ? new ArrayList() : GeneralUtils.getSuperNonPropertyFields(cNode.getSuperClass());
        for (FieldNode fNode : cNode.getFields()) {
            if (fNode.isStatic() || cNode.getProperty(fNode.getName()) != null) continue;
            result.add(fNode);
        }
        return result;
    }

    public static List<FieldNode> getSuperPropertyFields(ClassNode cNode) {
        List<Object> result = ClassHelper.isObjectType(cNode) ? new ArrayList() : GeneralUtils.getSuperPropertyFields(cNode.getSuperClass());
        for (PropertyNode pNode : cNode.getProperties()) {
            if (pNode.isStatic()) continue;
            result.add(pNode.getField());
        }
        return result;
    }

    public static List<PropertyNode> getAllProperties(Set<String> names, ClassNode cNode, boolean includeProperties, boolean includeFields, boolean includePseudoGetters, boolean includePseudoSetters, boolean traverseSuperClasses, boolean skipReadonly) {
        return GeneralUtils.getAllProperties(names, cNode, cNode, includeProperties, includeFields, includePseudoGetters, includePseudoSetters, traverseSuperClasses, skipReadonly);
    }

    public static List<PropertyNode> getAllProperties(Set<String> names, ClassNode origType, ClassNode cNode, boolean includeProperties, boolean includeFields, boolean includePseudoGetters, boolean includePseudoSetters, boolean traverseSuperClasses, boolean skipReadonly) {
        return GeneralUtils.getAllProperties(names, origType, cNode, includeProperties, includeFields, includePseudoGetters, includePseudoSetters, traverseSuperClasses, skipReadonly, false, false, false);
    }

    public static List<PropertyNode> getAllProperties(Set<String> names, ClassNode origType, ClassNode cNode, boolean includeProperties, boolean includeFields, boolean includePseudoGetters, boolean includePseudoSetters, boolean traverseSuperClasses, boolean skipReadonly, boolean reverse, boolean allNames, boolean includeStatic) {
        ArrayList<PropertyNode> result = new ArrayList<PropertyNode>();
        if (!ClassHelper.isObjectType(cNode) && traverseSuperClasses && !reverse) {
            result.addAll(GeneralUtils.getAllProperties(names, origType, cNode.getSuperClass(), includeProperties, includeFields, includePseudoGetters, includePseudoSetters, true, skipReadonly));
        }
        if (includeProperties) {
            for (PropertyNode pNode : cNode.getProperties()) {
                if (pNode.isStatic() && !includeStatic || names.contains(pNode.getName())) continue;
                result.add(pNode);
                names.add(pNode.getName());
            }
            if (includePseudoGetters || includePseudoSetters) {
                BeanUtils.addPseudoProperties(origType, cNode, result, names, includeStatic, includePseudoGetters, includePseudoSetters);
            }
        }
        if (includeFields) {
            for (FieldNode fNode : cNode.getFields()) {
                if (fNode.isStatic() && !includeStatic || fNode.isSynthetic() || cNode.getProperty(fNode.getName()) != null || names.contains(fNode.getName()) || fNode.getName().contains("$") && !allNames || fNode.isPrivate() && !cNode.equals(origType) || fNode.isFinal() && fNode.getInitialExpression() != null && skipReadonly) continue;
                result.add(new PropertyNode(fNode, fNode.getModifiers(), null, null));
                names.add(fNode.getName());
            }
        }
        if (!ClassHelper.isObjectType(cNode) && traverseSuperClasses && reverse) {
            result.addAll(GeneralUtils.getAllProperties(names, origType, cNode.getSuperClass(), includeProperties, includeFields, includePseudoGetters, includePseudoSetters, true, skipReadonly));
        }
        return result;
    }

    public static Expression getterThisX(ClassNode annotatedNode, PropertyNode pNode) {
        ClassNode owner = pNode.getDeclaringClass();
        if (annotatedNode.equals(owner)) {
            return GeneralUtils.callThisX(pNode.getGetterNameOrDefault());
        }
        return GeneralUtils.propX((Expression)GeneralUtils.varX("this"), pNode.getName());
    }

    public static Expression getterX(ClassNode annotatedNode, Expression receiver, PropertyNode pNode) {
        ClassNode owner = pNode.getDeclaringClass();
        if (annotatedNode.equals(owner)) {
            return GeneralUtils.callX(receiver, pNode.getGetterNameOrDefault());
        }
        return GeneralUtils.propX(receiver, pNode.getName());
    }

    public static BinaryExpression hasClassX(Expression instance, ClassNode cNode) {
        return GeneralUtils.eqX(GeneralUtils.classX(cNode), GeneralUtils.callX(instance, "getClass"));
    }

    public static BinaryExpression hasEqualFieldX(FieldNode fNode, Expression other) {
        return GeneralUtils.eqX(GeneralUtils.varX(fNode), GeneralUtils.propX(other, fNode.getName()));
    }

    public static BinaryExpression hasEqualPropertyX(ClassNode annotatedNode, PropertyNode pNode, VariableExpression other) {
        return GeneralUtils.eqX(GeneralUtils.getterThisX(annotatedNode, pNode), GeneralUtils.getterX(other.getOriginType(), other, pNode));
    }

    @Deprecated
    public static BinaryExpression hasEqualPropertyX(PropertyNode pNode, Expression other) {
        String getterName = pNode.getGetterNameOrDefault();
        return GeneralUtils.eqX(GeneralUtils.callThisX(getterName), GeneralUtils.callX(other, getterName));
    }

    public static BooleanExpression hasSameFieldX(FieldNode fNode, Expression other) {
        return GeneralUtils.sameX(GeneralUtils.varX(fNode), GeneralUtils.propX(other, fNode.getName()));
    }

    public static BooleanExpression hasSamePropertyX(PropertyNode pNode, Expression other) {
        ClassNode cNode = pNode.getDeclaringClass();
        return GeneralUtils.sameX(GeneralUtils.getterThisX(cNode, pNode), GeneralUtils.getterX(cNode, other, pNode));
    }

    public static IfStatement ifElseS(Expression cond, Statement thenStmt, Statement elseStmt) {
        return new IfStatement(cond instanceof BooleanExpression ? (BooleanExpression)cond : GeneralUtils.boolX(cond), thenStmt, elseStmt);
    }

    public static IfStatement ifS(Expression cond, Expression trueExpr) {
        return GeneralUtils.ifElseS(cond, GeneralUtils.stmt(trueExpr), EmptyStatement.INSTANCE);
    }

    public static IfStatement ifS(Expression cond, Statement trueStmt) {
        return GeneralUtils.ifElseS(cond, trueStmt, EmptyStatement.INSTANCE);
    }

    public static Expression indexX(Expression target, Expression value) {
        return GeneralUtils.binX(target, INDEX, value);
    }

    public static BooleanExpression isInstanceOfX(Expression objectExpression, ClassNode cNode) {
        return GeneralUtils.boolX(GeneralUtils.binX(objectExpression, INSTANCEOF, GeneralUtils.classX(cNode)));
    }

    public static BooleanExpression isNullX(Expression expr) {
        return GeneralUtils.equalsNullX(expr);
    }

    public static BooleanExpression isOneX(Expression expr) {
        return GeneralUtils.boolX(GeneralUtils.binX(expr, EQ, GeneralUtils.constX(1)));
    }

    public static BooleanExpression isTrueX(Expression argExpr) {
        return GeneralUtils.boolX(GeneralUtils.binX(argExpr, EQ, GeneralUtils.constX(Boolean.TRUE)));
    }

    public static BooleanExpression isZeroX(Expression expr) {
        return GeneralUtils.boolX(GeneralUtils.binX(expr, EQ, GeneralUtils.constX(0)));
    }

    public static ListExpression listX(List<Expression> args) {
        return new ListExpression(args);
    }

    public static ListExpression list2args(List<?> args) {
        ListExpression result = new ListExpression();
        for (Object o : args) {
            result.addExpression(GeneralUtils.constX(o));
        }
        return result;
    }

    public static ListExpression classList2args(List<String> args) {
        ListExpression result = new ListExpression();
        for (String o : args) {
            result.addExpression(GeneralUtils.classX(ClassHelper.make(o.toString())));
        }
        return result;
    }

    public static VariableExpression localVarX(String name) {
        VariableExpression result = GeneralUtils.varX(name);
        result.setAccessedVariable(result);
        return result;
    }

    public static VariableExpression localVarX(String name, ClassNode type) {
        VariableExpression result = GeneralUtils.varX(name, type);
        result.setAccessedVariable(result);
        return result;
    }

    public static BinaryExpression ltX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, LT, rhv);
    }

    public static MapEntryExpression mapEntryX(Expression keyExpr, Expression valueExpr) {
        return new MapEntryExpression(keyExpr, valueExpr);
    }

    public static MapEntryExpression mapEntryX(String key, Expression valueExpr) {
        return new MapEntryExpression(GeneralUtils.constX(key), valueExpr);
    }

    public static MapExpression mapX(List<MapEntryExpression> expressions) {
        return new MapExpression(expressions);
    }

    public static BinaryExpression neX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, NE, rhv);
    }

    public static BinaryExpression notIdenticalX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, NOT_IDENTICAL, rhv);
    }

    public static BooleanExpression notNullX(Expression argExpr) {
        return GeneralUtils.boolX(GeneralUtils.binX(argExpr, NE, GeneralUtils.nullX()));
    }

    public static NotExpression notX(Expression expr) {
        return new NotExpression(expr instanceof BooleanExpression ? expr : GeneralUtils.boolX(expr));
    }

    public static ConstantExpression nullX() {
        return GeneralUtils.constX(null);
    }

    public static BinaryExpression orX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, OR, rhv);
    }

    public static Parameter param(ClassNode type, String name) {
        return GeneralUtils.param(type, name, null);
    }

    public static Parameter param(ClassNode type, String name, Expression initialExpression) {
        Parameter param = new Parameter(type, name);
        if (initialExpression != null) {
            param.setInitialExpression(initialExpression);
        }
        return param;
    }

    public static Parameter[] params(Parameter ... params) {
        return params != null ? params : Parameter.EMPTY_ARRAY;
    }

    public static BinaryExpression plusX(Expression lhv, Expression rhv) {
        return GeneralUtils.binX(lhv, PLUS, rhv);
    }

    public static PropertyExpression propX(Expression owner, String property) {
        return new PropertyExpression(owner, property);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ Expression propX(Expression owner, String property) {
        return GeneralUtils.propX(owner, property);
    }

    public static PropertyExpression propX(Expression owner, Expression property) {
        return new PropertyExpression(owner, property);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ Expression propX(Expression owner, Expression property) {
        return GeneralUtils.propX(owner, property);
    }

    public static PropertyExpression propX(Expression owner, Expression property, boolean safe) {
        return new PropertyExpression(owner, property, safe);
    }

    public static Statement returnS(Expression expr) {
        return new ReturnStatement(expr);
    }

    public static Statement safeExpression(Expression fieldExpr, Expression expression) {
        return new IfStatement(GeneralUtils.equalsNullX(fieldExpr), GeneralUtils.stmt(fieldExpr), GeneralUtils.stmt(expression));
    }

    public static BooleanExpression sameX(Expression self, Expression other) {
        return GeneralUtils.boolX(GeneralUtils.callX(self, "is", (Expression)GeneralUtils.args(other)));
    }

    public static Statement stmt(Expression expr) {
        return new ExpressionStatement(expr);
    }

    public static SwitchStatement switchS(Expression expr) {
        return new SwitchStatement(expr);
    }

    public static SwitchStatement switchS(Expression expr, Statement defaultStatement) {
        return new SwitchStatement(expr, defaultStatement);
    }

    public static SwitchStatement switchS(Expression expr, List<CaseStatement> caseStatements, Statement defaultStatement) {
        return new SwitchStatement(expr, caseStatements, defaultStatement);
    }

    public static TernaryExpression ternaryX(Expression cond, Expression trueExpr, Expression elseExpr) {
        return new TernaryExpression(cond instanceof BooleanExpression ? (BooleanExpression)cond : GeneralUtils.boolX(cond), trueExpr, elseExpr);
    }

    public static PropertyExpression thisPropX(boolean implicit, String property) {
        PropertyExpression pexp = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), property);
        pexp.setImplicitThis(implicit);
        return pexp;
    }

    public static ThrowStatement throwS(Expression expr) {
        return new ThrowStatement(expr);
    }

    public static TryCatchStatement tryCatchS(Statement tryStatement) {
        return GeneralUtils.tryCatchS(tryStatement, EmptyStatement.INSTANCE);
    }

    public static TryCatchStatement tryCatchS(Statement tryStatement, Statement finallyStatement) {
        return new TryCatchStatement(tryStatement, finallyStatement);
    }

    public static TryCatchStatement tryCatchS(Statement tryStatement, Statement finallyStatement, CatchStatement ... catchStatements) {
        TryCatchStatement result = new TryCatchStatement(tryStatement, finallyStatement);
        for (CatchStatement catchStatement : catchStatements) {
            result.addCatch(catchStatement);
        }
        return result;
    }

    public static VariableExpression varX(String name) {
        return new VariableExpression(name);
    }

    public static VariableExpression varX(Variable variable) {
        return new VariableExpression(variable);
    }

    public static VariableExpression varX(String name, ClassNode type) {
        return new VariableExpression(name, type);
    }

    public static Parameter[] cloneParams(Parameter[] parameters) {
        return (Parameter[])Arrays.stream(parameters).map(p -> GeneralUtils.param(p.getOriginType(), p.getName())).toArray(Parameter[]::new);
    }

    public static void copyAnnotatedNodeAnnotations(AnnotatedNode annotatedNode, List<AnnotationNode> copied, List<AnnotationNode> notCopied) {
        GeneralUtils.copyAnnotatedNodeAnnotations(annotatedNode, copied, notCopied, true);
    }

    public static void copyAnnotatedNodeAnnotations(AnnotatedNode annotatedNode, List<AnnotationNode> copied, List<AnnotationNode> notCopied, boolean includeGenerated) {
        List<AnnotationNode> annotationList = annotatedNode.getAnnotations();
        for (AnnotationNode annotation : annotationList) {
            PropertyExpression propertyExpression;
            boolean processAnnotation;
            AnnotationNode retentionPolicyAnnotation;
            Expression valueExpression;
            List<AnnotationNode> annotations = annotation.getClassNode().getAnnotations(AbstractASTTransformation.RETENTION_CLASSNODE);
            if (annotations.isEmpty()) continue;
            if (GeneralUtils.hasClosureMember(annotation)) {
                notCopied.add(annotation);
                continue;
            }
            if (!includeGenerated && annotation.getClassNode().getName().equals("groovy.transform.Generated") || !((valueExpression = (retentionPolicyAnnotation = annotations.get(0)).getMember("value")) instanceof PropertyExpression) || !(processAnnotation = (propertyExpression = (PropertyExpression)valueExpression).getProperty() instanceof ConstantExpression && ("RUNTIME".equals(((ConstantExpression)propertyExpression.getProperty()).getValue()) || "CLASS".equals(((ConstantExpression)propertyExpression.getProperty()).getValue())))) continue;
            AnnotationNode newAnnotation = new AnnotationNode(annotation.getClassNode());
            for (Map.Entry<String, Expression> member : annotation.getMembers().entrySet()) {
                newAnnotation.addMember(member.getKey(), member.getValue());
            }
            newAnnotation.setSourcePosition(annotatedNode);
            copied.add(newAnnotation);
        }
    }

    public static Statement createConstructorStatementDefault(FieldNode fNode) {
        String name = fNode.getName();
        ClassNode fType = fNode.getType();
        PropertyExpression fieldExpr = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), name);
        Expression initExpr = fNode.getInitialValueExpression();
        Statement assignInit = initExpr == null || initExpr instanceof ConstantExpression && ((ConstantExpression)initExpr).isNullExpression() ? (ClassHelper.isPrimitiveType(fType) ? EmptyStatement.INSTANCE : GeneralUtils.assignS(fieldExpr, ConstantExpression.EMPTY_EXPRESSION)) : GeneralUtils.assignS(fieldExpr, initExpr);
        fNode.setInitialValueExpression(null);
        Expression value = GeneralUtils.findArg(name);
        return GeneralUtils.ifElseS(GeneralUtils.equalsNullX(value), assignInit, GeneralUtils.assignS(fieldExpr, GeneralUtils.castX(fType, value)));
    }

    public static String getGetterName(PropertyNode pNode) {
        return pNode.getGetterNameOrDefault();
    }

    public static String getGetterName(String name, Class<?> type) {
        return MetaProperty.getGetterName(name, type);
    }

    public static String getGetterName(String name) {
        return MetaProperty.getGetterName(name, Object.class);
    }

    public static String getSetterName(String name) {
        return MetaProperty.getSetterName(name);
    }

    public static String convertASTToSource(ReaderSource readerSource, ASTNode expression) throws Exception {
        if (expression == null) {
            throw new IllegalArgumentException("Null: expression");
        }
        StringBuilder result = new StringBuilder();
        int y = expression.getLastLineNumber();
        for (int x = expression.getLineNumber(); x <= y; ++x) {
            String line = readerSource.getLine(x, null);
            if (line == null) {
                throw new Exception("Error calculating source code for expression. Trying to read line " + x + " from " + readerSource.getClass());
            }
            if (x == expression.getLastLineNumber()) {
                line = line.substring(0, expression.getLastColumnNumber() - 1);
            }
            if (x == expression.getLineNumber()) {
                line = line.substring(expression.getColumnNumber() - 1);
            }
            result.append(line).append('\n');
        }
        String source = result.toString().trim();
        return source;
    }

    public static boolean copyStatementsWithSuperAdjustment(ClosureExpression pre, BlockStatement body) {
        Statement preCode = pre.getCode();
        boolean changed = false;
        if (preCode instanceof BlockStatement) {
            BlockStatement block = (BlockStatement)preCode;
            List<Statement> statements = block.getStatements();
            int n = statements.size();
            for (int i = 0; i < n; ++i) {
                MethodCallExpression mce;
                String name;
                ExpressionStatement es;
                Expression preExp;
                Statement statement = statements.get(i);
                if (i == 0 && statement instanceof ExpressionStatement && (preExp = (es = (ExpressionStatement)statement).getExpression()) instanceof MethodCallExpression && "super".equals(name = (mce = (MethodCallExpression)preExp).getMethodAsString())) {
                    es.setExpression(new ConstructorCallExpression(ClassNode.SUPER, mce.getArguments()));
                    changed = true;
                }
                body.addStatement(statement);
            }
        }
        return changed;
    }

    private static boolean hasClosureMember(AnnotationNode annotation) {
        Map<String, Expression> members = annotation.getMembers();
        for (Map.Entry<String, Expression> member : members.entrySet()) {
            ClassExpression classExpression;
            Class typeClass;
            if (member.getValue() instanceof ClosureExpression) {
                return true;
            }
            if (!(member.getValue() instanceof ClassExpression) || (typeClass = (classExpression = (ClassExpression)member.getValue()).getType().isResolved() ? classExpression.getType().redirect().getTypeClass() : null) == null || !GeneratedClosure.class.isAssignableFrom(typeClass)) continue;
            return true;
        }
        return false;
    }

    public static boolean hasDeclaredMethod(ClassNode cNode, String name, int argsCount) {
        List<MethodNode> methods = cNode.getDeclaredMethods(name);
        for (MethodNode method : methods) {
            Parameter[] params = method.getParameters();
            if (params == null || params.length != argsCount) continue;
            return true;
        }
        return false;
    }

    public static MethodNode findDeclaredMethod(ClassNode cNode, String name, int argsCount) {
        List<MethodNode> methods = cNode.getDeclaredMethods(name);
        for (MethodNode method : methods) {
            Parameter[] params = method.getParameters();
            if (params == null || params.length != argsCount) continue;
            return method;
        }
        return null;
    }

    public static boolean inSamePackage(ClassNode first, ClassNode second) {
        PackageNode firstPackage = first.getPackage();
        PackageNode secondPackage = second.getPackage();
        return firstPackage == null && secondPackage == null || firstPackage != null && secondPackage != null && firstPackage.getName().equals(secondPackage.getName());
    }

    public static boolean inSamePackage(Class<?> first, Class<?> second) {
        Package firstPackage = first.getPackage();
        Package secondPackage = second.getPackage();
        return firstPackage == null && secondPackage == null || firstPackage != null && secondPackage != null && firstPackage.getName().equals(secondPackage.getName());
    }

    public static boolean isDefaultVisibility(int modifiers) {
        return (modifiers & 7) == 0;
    }

    public static boolean isOrImplements(ClassNode type, ClassNode interfaceType) {
        return type.equals(interfaceType) || type.implementsInterface(interfaceType);
    }

    @Deprecated
    public static String makeDescriptorWithoutReturnType(MethodNode mn) {
        StringBuilder sb = new StringBuilder();
        sb.append(mn.getName()).append(':');
        for (Parameter p : mn.getParameters()) {
            sb.append(p.getType()).append(',');
        }
        return sb.toString();
    }

    public static boolean maybeFallsThrough(Statement statement) {
        if (statement.isEmpty()) {
            return true;
        }
        if (statement instanceof ReturnStatement) {
            return false;
        }
        if (statement instanceof ThrowStatement) {
            return false;
        }
        if (statement instanceof BlockStatement) {
            int last;
            List<Statement> list = ((BlockStatement)statement).getStatements();
            if (!GeneralUtils.maybeFallsThrough(list.get(last = list.size() - 1))) {
                return false;
            }
            for (int i = 0; i < last; ++i) {
                if (GeneralUtils.maybeFallsThrough(list.get(i))) continue;
                return false;
            }
        } else {
            if (statement instanceof IfStatement) {
                return GeneralUtils.maybeFallsThrough(((IfStatement)statement).getElseBlock()) || GeneralUtils.maybeFallsThrough(((IfStatement)statement).getIfBlock());
            }
            if (!(statement instanceof SwitchStatement)) {
                if (statement instanceof TryCatchStatement) {
                    TryCatchStatement tryCatch = (TryCatchStatement)statement;
                    if (!GeneralUtils.maybeFallsThrough(tryCatch.getFinallyStatement())) {
                        return false;
                    }
                    for (CatchStatement cs : tryCatch.getCatchStatements()) {
                        if (!GeneralUtils.maybeFallsThrough(cs.getCode())) continue;
                        return true;
                    }
                    return GeneralUtils.maybeFallsThrough(tryCatch.getTryStatement());
                }
                if (statement instanceof SynchronizedStatement) {
                    return GeneralUtils.maybeFallsThrough(((SynchronizedStatement)statement).getCode());
                }
            }
        }
        return true;
    }
}

