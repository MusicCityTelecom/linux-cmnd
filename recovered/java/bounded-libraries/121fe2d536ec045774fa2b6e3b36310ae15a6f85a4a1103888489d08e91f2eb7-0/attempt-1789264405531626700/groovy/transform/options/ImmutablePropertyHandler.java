/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.options;

import groovy.lang.ReadOnlyPropertyException;
import groovy.transform.options.PropertyHandler;
import groovy.transform.stc.POJO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.apache.groovy.ast.tools.ConstructorNodeUtils;
import org.apache.groovy.ast.tools.ImmutablePropertyUtils;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.ImmutableASTTransformation;
import org.codehaus.groovy.transform.MapConstructorASTTransformation;
import org.codehaus.groovy.transform.NullCheckASTTransformation;

public class ImmutablePropertyHandler
extends PropertyHandler {
    private static final ClassNode CLONEABLE_TYPE = ClassHelper.make(Cloneable.class);
    private static final ClassNode COLLECTION_TYPE = ClassHelper.makeWithoutCaching(Collection.class, false);
    private static final ClassNode DGM_TYPE = ClassHelper.make(DefaultGroovyMethods.class);
    private static final ClassNode SELF_TYPE = ClassHelper.make(ImmutableASTTransformation.class);
    private static final ClassNode MAP_TYPE = ClassHelper.makeWithoutCaching(Map.class, false);
    private static final ClassNode SORTEDSET_CLASSNODE = ClassHelper.make(SortedSet.class);
    private static final ClassNode SORTEDMAP_CLASSNODE = ClassHelper.make(SortedMap.class);
    private static final ClassNode SET_CLASSNODE = ClassHelper.make(Set.class);
    private static final ClassNode MAP_CLASSNODE = ClassHelper.make(Map.class);
    private static final ClassNode READONLYEXCEPTION_TYPE = ClassHelper.make(ReadOnlyPropertyException.class);
    private static final ClassNode POJO_TYPE = ClassHelper.make(POJO.class);

    @Override
    public Statement createPropGetter(PropertyNode pNode) {
        FieldNode fNode = pNode.getField();
        BlockStatement body = new BlockStatement();
        ClassNode fieldType = fNode.getType();
        Statement statement = fieldType.isArray() || ImmutablePropertyUtils.implementsCloneable(fieldType) ? this.createGetterBodyArrayOrCloneable(fNode) : (ImmutablePropertyUtils.derivesFromDate(fieldType) ? this.createGetterBodyDate(fNode) : ImmutablePropertyHandler.createGetterBodyDefault(fNode));
        body.addStatement(statement);
        return body;
    }

    @Override
    public Statement createPropSetter(PropertyNode pNode) {
        return null;
    }

    @Override
    public boolean validateAttributes(AbstractASTTransformation xform, AnnotationNode anno) {
        return this.isValidAttribute(xform, anno, "useSuper");
    }

    @Override
    public boolean validateProperties(AbstractASTTransformation xform, BlockStatement body, ClassNode cNode, List<PropertyNode> props) {
        if (xform instanceof MapConstructorASTTransformation) {
            VariableExpression namedArgs = GeneralUtils.varX("args");
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.equalsNullX(namedArgs), GeneralUtils.assignS(namedArgs, new MapExpression())));
            boolean pojo = !cNode.getAnnotations(POJO_TYPE).isEmpty();
            body.addStatement(ConstructorNodeUtils.checkPropNamesS(namedArgs, pojo, props));
        }
        return super.validateProperties(xform, body, cNode, props);
    }

    @Override
    public Statement createPropInit(AbstractASTTransformation xform, AnnotationNode anno, ClassNode cNode, PropertyNode pNode, Parameter namedArgsMap) {
        FieldNode fNode = pNode.getField();
        if (fNode.isFinal() && fNode.isStatic()) {
            return null;
        }
        if (fNode.isFinal() && fNode.getInitialExpression() != null) {
            return this.checkFinalArgNotOverridden(cNode, fNode);
        }
        return this.createConstructorStatement(xform, cNode, pNode, namedArgsMap);
    }

    private static Statement createGetterBodyDefault(FieldNode fNode) {
        VariableExpression fieldExpr = GeneralUtils.varX(fNode);
        return GeneralUtils.stmt(fieldExpr);
    }

    private Statement createGetterBodyArrayOrCloneable(FieldNode fNode) {
        VariableExpression fieldExpr = GeneralUtils.varX(fNode);
        Expression expression = ImmutablePropertyUtils.cloneArrayOrCloneableExpr(fieldExpr, fNode.getType());
        return GeneralUtils.safeExpression(fieldExpr, expression);
    }

    private Statement createGetterBodyDate(FieldNode fNode) {
        VariableExpression fieldExpr = GeneralUtils.varX(fNode);
        Expression expression = ImmutablePropertyUtils.cloneDateExpr(fieldExpr);
        return GeneralUtils.safeExpression(fieldExpr, expression);
    }

    protected Expression cloneCollectionExpr(Expression fieldExpr, ClassNode type) {
        return GeneralUtils.castX(type, this.createIfInstanceOfAsImmutableS(fieldExpr, SORTEDSET_CLASSNODE, this.createIfInstanceOfAsImmutableS(fieldExpr, SORTEDMAP_CLASSNODE, this.createIfInstanceOfAsImmutableS(fieldExpr, SET_CLASSNODE, this.createIfInstanceOfAsImmutableS(fieldExpr, MAP_CLASSNODE, this.createIfInstanceOfAsImmutableS(fieldExpr, ClassHelper.LIST_TYPE, this.createAsImmutableX(fieldExpr, COLLECTION_TYPE)))))));
    }

    private Expression createIfInstanceOfAsImmutableS(Expression expr, ClassNode type, Expression elseStatement) {
        return GeneralUtils.ternaryX(GeneralUtils.isInstanceOfX(expr, type), this.createAsImmutableX(expr, type), elseStatement);
    }

    protected Expression createAsImmutableX(Expression expr, ClassNode type) {
        return GeneralUtils.callX(DGM_TYPE, "asImmutable", (Expression)GeneralUtils.castX(type, expr));
    }

    protected Statement createConstructorStatement(AbstractASTTransformation xform, ClassNode cNode, PropertyNode pNode, Parameter namedArgsMap) {
        Statement statement;
        List<String> knownImmutableClasses = ImmutablePropertyUtils.getKnownImmutableClasses(xform, cNode);
        List<String> knownImmutables = ImmutablePropertyUtils.getKnownImmutables(xform, cNode);
        FieldNode fNode = pNode.getField();
        ClassNode fType = fNode.getType();
        boolean shouldNullCheck = NullCheckASTTransformation.hasIncludeGenerated(cNode);
        if (ImmutablePropertyUtils.isKnownImmutableType(fType, knownImmutableClasses) || ImmutablePropertyHandler.isKnownImmutable(pNode.getName(), knownImmutables)) {
            statement = ImmutablePropertyHandler.createConstructorStatementDefault(fNode, namedArgsMap, shouldNullCheck);
        } else if (fType.isArray() || ImmutablePropertyUtils.implementsCloneable(fType)) {
            statement = ImmutablePropertyHandler.createConstructorStatementArrayOrCloneable(fNode, namedArgsMap, shouldNullCheck);
        } else if (ImmutablePropertyUtils.derivesFromDate(fType)) {
            statement = ImmutablePropertyHandler.createConstructorStatementDate(fNode, namedArgsMap, shouldNullCheck);
        } else if (GeneralUtils.isOrImplements(fType, COLLECTION_TYPE) || fType.isDerivedFrom(COLLECTION_TYPE) || GeneralUtils.isOrImplements(fType, MAP_TYPE) || fType.isDerivedFrom(MAP_TYPE)) {
            statement = this.createConstructorStatementCollection(fNode, namedArgsMap, shouldNullCheck);
        } else if (fType.isResolved()) {
            xform.addError(ImmutablePropertyUtils.createErrorMessage(cNode.getName(), fNode.getName(), fType.getName(), "compiling"), fNode);
            statement = EmptyStatement.INSTANCE;
        } else {
            statement = ImmutablePropertyHandler.createConstructorStatementGuarded(fNode, namedArgsMap, knownImmutables, knownImmutableClasses, shouldNullCheck);
        }
        return statement;
    }

    private static Statement createConstructorStatementDefault(FieldNode fNode, Parameter namedArgsMap, boolean shouldNullCheck) {
        Expression initExpr;
        ClassNode fType = fNode.getType();
        PropertyExpression fieldExpr = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), fNode.getName());
        Expression param = ImmutablePropertyHandler.getParam(fNode, namedArgsMap != null);
        Statement assignStmt = GeneralUtils.assignS(fieldExpr, GeneralUtils.castX(fType, param));
        if (shouldNullCheck) {
            assignStmt = GeneralUtils.ifElseS(GeneralUtils.equalsNullX(param), NullCheckASTTransformation.makeThrowStmt(fNode.getName()), assignStmt);
        }
        Statement assignInit = (initExpr = fNode.getInitialValueExpression()) == null || initExpr instanceof ConstantExpression && ((ConstantExpression)initExpr).isNullExpression() ? (ClassHelper.isPrimitiveType(fType) ? EmptyStatement.INSTANCE : (shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr))) : GeneralUtils.assignS(fieldExpr, initExpr);
        return ImmutablePropertyHandler.assignFieldWithDefault(namedArgsMap, fNode, assignStmt, assignInit);
    }

    private static Statement assignFieldWithDefault(Parameter map, FieldNode fNode, Statement assignStmt, Statement assignInit) {
        if (map == null) {
            return assignStmt;
        }
        ArgumentListExpression nameArg = GeneralUtils.args(GeneralUtils.constX(fNode.getName()));
        MethodCallExpression var = GeneralUtils.callX((Expression)GeneralUtils.varX(map), "get", (Expression)nameArg);
        var.setImplicitThis(false);
        MethodCallExpression containsKey = GeneralUtils.callX((Expression)GeneralUtils.varX(map), "containsKey", (Expression)nameArg);
        containsKey.setImplicitThis(false);
        fNode.getDeclaringClass().getField(fNode.getName()).setInitialValueExpression(null);
        return GeneralUtils.ifElseS(containsKey, assignStmt, assignInit);
    }

    private static Statement createConstructorStatementGuarded(FieldNode fNode, Parameter namedArgsMap, List<String> knownImmutables, List<String> knownImmutableClasses, boolean shouldNullCheck) {
        PropertyExpression fieldExpr = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), fNode.getName());
        Expression param = ImmutablePropertyHandler.getParam(fNode, namedArgsMap != null);
        Statement assignStmt = GeneralUtils.assignS(fieldExpr, ImmutablePropertyHandler.createCheckImmutable(fNode, param, knownImmutables, knownImmutableClasses));
        assignStmt = GeneralUtils.ifElseS(GeneralUtils.equalsNullX(param), shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr), assignStmt);
        Expression initExpr = fNode.getInitialValueExpression();
        Statement assignInit = initExpr == null || initExpr instanceof ConstantExpression && ((ConstantExpression)initExpr).isNullExpression() ? (shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr)) : GeneralUtils.assignS(fieldExpr, ImmutablePropertyHandler.createCheckImmutable(fNode, initExpr, knownImmutables, knownImmutableClasses));
        return ImmutablePropertyHandler.assignFieldWithDefault(namedArgsMap, fNode, assignStmt, assignInit);
    }

    private static Expression createCheckImmutable(FieldNode fNode, Expression value, List<String> knownImmutables, List<String> knownImmutableClasses) {
        ArgumentListExpression args = GeneralUtils.args(GeneralUtils.callThisX("getClass"), GeneralUtils.constX(fNode.getName()), value, GeneralUtils.list2args(knownImmutables), GeneralUtils.classList2args(knownImmutableClasses));
        return GeneralUtils.callX(SELF_TYPE, "checkImmutable", (Expression)args);
    }

    private Statement createConstructorStatementCollection(FieldNode fNode, Parameter namedArgsMap, boolean shouldNullCheck) {
        PropertyExpression fieldExpr = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), fNode.getName());
        ClassNode fieldType = fieldExpr.getType();
        Expression param = ImmutablePropertyHandler.getParam(fNode, namedArgsMap != null);
        Statement assignStmt = GeneralUtils.ifElseS(GeneralUtils.isInstanceOfX(param, CLONEABLE_TYPE), GeneralUtils.assignS(fieldExpr, this.cloneCollectionExpr(ImmutablePropertyUtils.cloneArrayOrCloneableExpr(param, fieldType), fieldType)), GeneralUtils.assignS(fieldExpr, this.cloneCollectionExpr(param, fieldType)));
        assignStmt = GeneralUtils.ifElseS(GeneralUtils.equalsNullX(param), shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr), assignStmt);
        Expression initExpr = fNode.getInitialValueExpression();
        Statement assignInit = initExpr == null || initExpr instanceof ConstantExpression && ((ConstantExpression)initExpr).isNullExpression() ? (shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr)) : GeneralUtils.assignS(fieldExpr, this.cloneCollectionExpr(initExpr, fieldType));
        return ImmutablePropertyHandler.assignFieldWithDefault(namedArgsMap, fNode, assignStmt, assignInit);
    }

    private static Statement createConstructorStatementArrayOrCloneable(FieldNode fNode, Parameter namedArgsMap, boolean shouldNullCheck) {
        PropertyExpression fieldExpr = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), fNode.getName());
        ClassNode fieldType = fNode.getType();
        Expression param = ImmutablePropertyHandler.getParam(fNode, namedArgsMap != null);
        Statement assignStmt = GeneralUtils.assignS(fieldExpr, ImmutablePropertyUtils.cloneArrayOrCloneableExpr(param, fieldType));
        assignStmt = GeneralUtils.ifElseS(GeneralUtils.equalsNullX(param), shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr), assignStmt);
        Expression initExpr = fNode.getInitialValueExpression();
        Statement assignInit = initExpr == null || initExpr instanceof ConstantExpression && ((ConstantExpression)initExpr).isNullExpression() ? (shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr)) : GeneralUtils.assignS(fieldExpr, ImmutablePropertyUtils.cloneArrayOrCloneableExpr(initExpr, fieldType));
        return ImmutablePropertyHandler.assignFieldWithDefault(namedArgsMap, fNode, assignStmt, assignInit);
    }

    private static Expression getParam(FieldNode fNode, boolean namedArgs) {
        return namedArgs ? GeneralUtils.findArg(fNode.getName()) : GeneralUtils.varX(fNode.getName(), fNode.getType());
    }

    private static Statement createConstructorStatementDate(FieldNode fNode, Parameter namedArgsMap, boolean shouldNullCheck) {
        PropertyExpression fieldExpr = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), fNode.getName());
        Expression param = ImmutablePropertyHandler.getParam(fNode, namedArgsMap != null);
        Statement assignStmt = GeneralUtils.assignS(fieldExpr, ImmutablePropertyUtils.cloneDateExpr(param));
        assignStmt = GeneralUtils.ifElseS(GeneralUtils.equalsNullX(param), shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr), assignStmt);
        Expression initExpr = fNode.getInitialValueExpression();
        Statement assignInit = initExpr == null || initExpr instanceof ConstantExpression && ((ConstantExpression)initExpr).isNullExpression() ? (shouldNullCheck ? NullCheckASTTransformation.makeThrowStmt(fNode.getName()) : GeneralUtils.assignNullS(fieldExpr)) : GeneralUtils.assignS(fieldExpr, ImmutablePropertyUtils.cloneDateExpr(initExpr));
        return ImmutablePropertyHandler.assignFieldWithDefault(namedArgsMap, fNode, assignStmt, assignInit);
    }

    private static boolean isKnownImmutable(String fieldName, List<String> knownImmutables) {
        return knownImmutables.contains(fieldName);
    }

    protected Statement checkFinalArgNotOverridden(ClassNode cNode, FieldNode fNode) {
        String name = fNode.getName();
        Expression value = GeneralUtils.findArg(name);
        return GeneralUtils.ifS((Expression)GeneralUtils.notX(GeneralUtils.equalsNullX(value)), GeneralUtils.throwS(GeneralUtils.ctorX(READONLYEXCEPTION_TYPE, GeneralUtils.args(GeneralUtils.constX(name), GeneralUtils.constX(cNode.getName())))));
    }
}

