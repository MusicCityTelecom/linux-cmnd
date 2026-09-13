/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyClassLoader;
import groovy.transform.CompilationUnitAware;
import groovy.transform.DefaultsMode;
import groovy.transform.TupleConstructor;
import groovy.transform.options.PropertyHandler;
import groovy.transform.stc.POJO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ConstructorNodeUtils;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.apache.groovy.ast.tools.VisibilityUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.ImmutableASTTransformation;
import org.codehaus.groovy.transform.MapConstructorASTTransformation;
import org.codehaus.groovy.transform.NamedVariantASTTransformation;
import org.codehaus.groovy.transform.TransformWithPriority;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class TupleConstructorASTTransformation
extends AbstractASTTransformation
implements CompilationUnitAware,
TransformWithPriority {
    private CompilationUnit compilationUnit;
    static final Class<?> MY_CLASS = TupleConstructor.class;
    static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final String NAMED_ARGS = "__namedArgs";
    private static final ClassNode LHMAP_TYPE = ClassHelper.makeWithoutCaching(LinkedHashMap.class, false);
    private static final ClassNode POJO_TYPE = ClassHelper.make(POJO.class);

    @Override
    public String getAnnotationName() {
        return MY_TYPE_NAME;
    }

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        this.compilationUnit = unit;
    }

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            ClassNode cNode = (ClassNode)parent;
            if (!this.checkNotInterface(cNode, MY_TYPE_NAME)) {
                return;
            }
            boolean includeFields = this.memberHasValue(anno, "includeFields", true);
            boolean includeProperties = !this.memberHasValue(anno, "includeProperties", false);
            boolean includeSuperFields = this.memberHasValue(anno, "includeSuperFields", true);
            boolean includeSuperProperties = this.memberHasValue(anno, "includeSuperProperties", true);
            boolean allProperties = this.memberHasValue(anno, "allProperties", true);
            List<String> excludes = TupleConstructorASTTransformation.getMemberStringList(anno, "excludes");
            List<String> includes = TupleConstructorASTTransformation.getMemberStringList(anno, "includes");
            boolean allNames = this.memberHasValue(anno, "allNames", true);
            if (!this.checkIncludeExcludeUndefinedAware(anno, excludes, includes, MY_TYPE_NAME)) {
                return;
            }
            if (!this.checkPropertyList(cNode, includes, "includes", anno, MY_TYPE_NAME, includeFields, includeSuperProperties, allProperties, includeSuperFields, false)) {
                return;
            }
            if (!this.checkPropertyList(cNode, excludes, "excludes", anno, MY_TYPE_NAME, includeFields, includeSuperProperties, allProperties, includeSuperFields, false)) {
                return;
            }
            GroovyClassLoader classLoader = this.compilationUnit != null ? this.compilationUnit.getTransformLoader() : source.getClassLoader();
            PropertyHandler handler = PropertyHandler.createPropertyHandler(this, classLoader, cNode);
            if (handler == null) {
                return;
            }
            if (!handler.validateAttributes(this, anno)) {
                return;
            }
            Expression pre = anno.getMember("pre");
            if (pre != null && !(pre instanceof ClosureExpression)) {
                this.addError("Expected closure value for annotation parameter 'pre'. Found " + pre, cNode);
                return;
            }
            Expression post = anno.getMember("post");
            if (post != null && !(post instanceof ClosureExpression)) {
                this.addError("Expected closure value for annotation parameter 'post'. Found " + post, cNode);
                return;
            }
            TupleConstructorASTTransformation.createConstructor(this, anno, cNode, includeFields, includeProperties, includeSuperFields, includeSuperProperties, excludes, includes, allNames, allProperties, this.sourceUnit, handler, (ClosureExpression)pre, (ClosureExpression)post);
            if (pre != null) {
                anno.setMember("pre", new ClosureExpression(Parameter.EMPTY_ARRAY, EmptyStatement.INSTANCE));
            }
            if (post != null) {
                anno.setMember("post", new ClosureExpression(Parameter.EMPTY_ARRAY, EmptyStatement.INSTANCE));
            }
        }
    }

    private static void createConstructor(AbstractASTTransformation xform, AnnotationNode anno, ClassNode cNode, boolean includeFields, boolean includeProperties, boolean includeSuperFields, boolean includeSuperProperties, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties, SourceUnit sourceUnit, PropertyHandler handler, ClosureExpression pre, ClosureExpression post) {
        FieldNode fNode;
        String name;
        boolean specialNamedArgCase;
        boolean callSuper = xform.memberHasValue(anno, "callSuper", true);
        boolean force = xform.memberHasValue(anno, "force", true);
        DefaultsMode defaultsMode = TupleConstructorASTTransformation.maybeDefaultsMode(anno, "defaultsMode");
        if (defaultsMode == null) {
            if (anno.getMember("defaults") == null) {
                defaultsMode = DefaultsMode.ON;
            } else {
                boolean defaults = !xform.memberHasValue(anno, "defaults", false);
                defaultsMode = defaults ? DefaultsMode.ON : DefaultsMode.OFF;
            }
        }
        boolean namedVariant = xform.memberHasValue(anno, "namedVariant", true);
        HashSet<String> names = new HashSet<String>();
        List<Object> superList = includeSuperProperties || includeSuperFields ? GeneralUtils.getAllProperties(names, cNode.getSuperClass(), includeSuperProperties, includeSuperFields, false, allProperties, true, true) : new ArrayList();
        List<PropertyNode> list = GeneralUtils.getAllProperties(names, cNode, includeProperties, includeFields, false, allProperties, false, true);
        boolean makeImmutable = ImmutableASTTransformation.makeImmutable(cNode);
        boolean bl = ImmutableASTTransformation.isSpecialNamedArgCase(list, defaultsMode == DefaultsMode.OFF) && superList.isEmpty() || ImmutableASTTransformation.isSpecialNamedArgCase(superList, defaultsMode == DefaultsMode.OFF) && list.isEmpty() ? true : (specialNamedArgCase = false);
        if (ClassNodeUtils.hasExplicitConstructor(null, cNode) && !force && !makeImmutable) {
            return;
        }
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        ArrayList<Expression> superParams = new ArrayList<Expression>();
        BlockStatement preBody = new BlockStatement();
        boolean superInPre = false;
        if (pre != null && (superInPre = GeneralUtils.copyStatementsWithSuperAdjustment(pre, preBody)) && callSuper) {
            xform.addError("Error during " + MY_TYPE_NAME + " processing, can't have a super call in 'pre' closure and also 'callSuper' enabled", cNode);
        }
        BlockStatement body = new BlockStatement();
        ArrayList<PropertyNode> tempList = new ArrayList<PropertyNode>(list);
        tempList.addAll(superList);
        if (!handler.validateProperties(xform, body, cNode, tempList)) {
            return;
        }
        for (PropertyNode propertyNode : superList) {
            Statement propInit;
            name = propertyNode.getName();
            fNode = propertyNode.getField();
            if (TupleConstructorASTTransformation.shouldSkipUndefinedAware(name, excludes, includes, allNames)) continue;
            params.add(TupleConstructorASTTransformation.createParam(fNode, name, defaultsMode, xform, makeImmutable));
            if (callSuper) {
                superParams.add(GeneralUtils.varX(name));
                continue;
            }
            if (superInPre || specialNamedArgCase || (propInit = handler.createPropInit(xform, anno, cNode, propertyNode, null)) == null) continue;
            body.addStatement(propInit);
        }
        if (callSuper) {
            body.addStatement(GeneralUtils.stmt(GeneralUtils.ctorX(ClassNode.SUPER, GeneralUtils.args(superParams))));
        }
        if (!preBody.isEmpty()) {
            body.addStatements(preBody.getStatements());
        }
        for (PropertyNode propertyNode : list) {
            name = propertyNode.getName();
            fNode = propertyNode.getField();
            if (TupleConstructorASTTransformation.shouldSkipUndefinedAware(name, excludes, includes, allNames)) continue;
            Parameter nextParam = TupleConstructorASTTransformation.createParam(fNode, name, defaultsMode, xform, makeImmutable);
            if (cNode.getNodeMetaData("_RECORD_HEADER") != null) {
                nextParam.addAnnotations(propertyNode.getAnnotations());
                nextParam.putNodeMetaData("_SKIPPABLE_ANNOTATIONS", true);
                fNode.putNodeMetaData("_SKIPPABLE_ANNOTATIONS", true);
            }
            params.add(nextParam);
        }
        if (includes != null) {
            Comparator<Parameter> includeComparator = Comparator.comparingInt(p -> includes.indexOf(p.getName()));
            params.sort(includeComparator);
        }
        for (PropertyNode propertyNode : list) {
            Statement propInit;
            name = propertyNode.getName();
            if (TupleConstructorASTTransformation.shouldSkipUndefinedAware(name, excludes, includes, allNames) || (propInit = handler.createPropInit(xform, anno, cNode, propertyNode, null)) == null) continue;
            body.addStatement(propInit);
        }
        if (post != null) {
            body.addStatement(post.getCode());
        }
        boolean hasMapCons = AnnotatedNodeUtils.hasAnnotation(cNode, MapConstructorASTTransformation.MY_TYPE);
        int n = VisibilityUtils.getVisibility(anno, cNode, ConstructorNode.class, 1);
        ConstructorNode consNode = ClassNodeUtils.addGeneratedConstructor(cNode, n, params.toArray(Parameter.EMPTY_ARRAY), ClassNode.EMPTY_ARRAY, body);
        if (cNode.getNodeMetaData("_RECORD_HEADER") != null) {
            consNode.addAnnotations(cNode.getAnnotations());
            consNode.putNodeMetaData("_SKIPPABLE_ANNOTATIONS", true);
        }
        if (namedVariant) {
            BlockStatement inner = new BlockStatement();
            Parameter mapParam = GeneralUtils.param(ClassHelper.MAP_TYPE.getPlainNodeReference(), NAMED_ARGS);
            ArrayList<Parameter> genParams = new ArrayList<Parameter>();
            genParams.add(mapParam);
            ArgumentListExpression args = new ArgumentListExpression();
            ArrayList<String> propNames = new ArrayList<String>();
            HashMap<Parameter, Expression> seen = new HashMap<Parameter, Expression>();
            for (Parameter p2 : params) {
                if (NamedVariantASTTransformation.processImplicitNamedParam(xform, consNode, mapParam, inner, args, propNames, p2, false, seen)) continue;
                return;
            }
            NamedVariantASTTransformation.createMapVariant(xform, consNode, anno, mapParam, genParams, cNode, inner, args, propNames);
        }
        if (sourceUnit != null && !body.isEmpty()) {
            VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit);
            scopeVisitor.visitClass(cNode);
        }
        if (body.isEmpty()) {
            body.addStatement(new ExpressionStatement(ConstantExpression.EMPTY_EXPRESSION));
        }
        if (!params.isEmpty() && defaultsMode != DefaultsMode.OFF && !hasMapCons && specialNamedArgCase) {
            ClassNode firstParamType = ((Parameter)params.get(0)).getType();
            if (params.size() > 1 || ClassHelper.isObjectType(firstParamType)) {
                String message = "The class " + cNode.getName() + " was incorrectly initialized via the map constructor with null.";
                TupleConstructorASTTransformation.addSpecialMapConstructors(n, cNode, message, false);
            }
        }
    }

    private static Parameter createParam(FieldNode fNode, String name, DefaultsMode defaultsMode, AbstractASTTransformation xform, boolean makeImmutable) {
        ClassNode fType = fNode.getType();
        ClassNode type = fType.getPlainNodeReference();
        type.setGenericsPlaceHolder(fType.isGenericsPlaceHolder());
        type.setGenericsTypes(fType.getGenericsTypes());
        Parameter param = new Parameter(type, name);
        if (defaultsMode == DefaultsMode.ON) {
            param.setInitialExpression(TupleConstructorASTTransformation.providedOrDefaultInitialValue(fNode));
        } else if (defaultsMode == DefaultsMode.AUTO && fNode.hasInitialExpression()) {
            param.setInitialExpression(fNode.getInitialExpression());
            fNode.setInitialValueExpression(null);
        } else if (!makeImmutable && fNode.hasInitialExpression()) {
            xform.addError("Error during " + MY_TYPE_NAME + " processing, default value processing disabled but default value found for '" + fNode.getName() + "'", fNode);
        }
        return param;
    }

    private static Expression providedOrDefaultInitialValue(FieldNode fNode) {
        ClassNode fType = fNode.getType();
        Expression init = fNode.getInitialExpression();
        fNode.setInitialValueExpression(null);
        if (init == null || ClassHelper.isPrimitiveType(fType) && ExpressionUtils.isNullConstant(init)) {
            init = GeneralUtils.defaultValueX(fType);
        }
        return init;
    }

    public static void addSpecialMapConstructors(int modifiers, ClassNode cNode, String message, boolean addNoArg) {
        Parameter[] parameters = GeneralUtils.params(new Parameter(LHMAP_TYPE, NAMED_ARGS));
        BlockStatement code = new BlockStatement();
        VariableExpression namedArgs = GeneralUtils.varX(NAMED_ARGS);
        namedArgs.setAccessedVariable(parameters[0]);
        code.addStatement(GeneralUtils.ifElseS(GeneralUtils.equalsNullX(namedArgs), TupleConstructorASTTransformation.illegalArgumentBlock(message), TupleConstructorASTTransformation.processArgsBlock(cNode, namedArgs)));
        ClassNodeUtils.addGeneratedConstructor(cNode, modifiers, parameters, ClassNode.EMPTY_ARRAY, code);
        if (addNoArg) {
            code = new BlockStatement();
            code.addStatement(GeneralUtils.stmt(GeneralUtils.ctorX(ClassNode.THIS, GeneralUtils.ctorX(LHMAP_TYPE))));
            ClassNodeUtils.addGeneratedConstructor(cNode, modifiers, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
        }
    }

    private static BlockStatement illegalArgumentBlock(String message) {
        return GeneralUtils.block(GeneralUtils.throwS(GeneralUtils.ctorX(ClassHelper.make(IllegalArgumentException.class), GeneralUtils.args(GeneralUtils.constX(message)))));
    }

    private static BlockStatement processArgsBlock(ClassNode cNode, VariableExpression namedArgs) {
        BlockStatement block = new BlockStatement();
        ArrayList<PropertyNode> props = new ArrayList<PropertyNode>();
        for (PropertyNode pNode : cNode.getProperties()) {
            if (pNode.isStatic()) continue;
            MethodCallExpression containsProperty = GeneralUtils.callX((Expression)namedArgs, "containsKey", (Expression)GeneralUtils.constX(pNode.getName()));
            containsProperty.setImplicitThis(false);
            block.addStatement(GeneralUtils.ifS((Expression)containsProperty, GeneralUtils.assignS(GeneralUtils.varX(pNode), GeneralUtils.propX((Expression)namedArgs, pNode.getName()))));
            props.add(pNode);
        }
        boolean pojo = !cNode.getAnnotations(POJO_TYPE).isEmpty();
        block.addStatement(ConstructorNodeUtils.checkPropNamesS(namedArgs, pojo, props));
        return block;
    }

    private static DefaultsMode maybeDefaultsMode(AnnotationNode node, String name) {
        if (node != null) {
            ClassExpression ce;
            PropertyExpression prop;
            Expression oe;
            Expression member = node.getMember(name);
            if (member instanceof ConstantExpression) {
                ConstantExpression ce2 = (ConstantExpression)member;
                if (ce2.getValue() instanceof DefaultsMode) {
                    return (DefaultsMode)((Object)ce2.getValue());
                }
            } else if (member instanceof PropertyExpression && (oe = (prop = (PropertyExpression)member).getObjectExpression()) instanceof ClassExpression && (ce = (ClassExpression)oe).getType().getName().equals("groovy.transform.DefaultsMode")) {
                return DefaultsMode.valueOf(prop.getPropertyAsString());
            }
        }
        return null;
    }

    @Override
    public int priority() {
        return 5;
    }
}

