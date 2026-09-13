/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyClassLoader;
import groovy.transform.CompilationUnitAware;
import groovy.transform.MapConstructor;
import groovy.transform.options.PropertyHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.VisibilityUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.ImmutableASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class MapConstructorASTTransformation
extends AbstractASTTransformation
implements CompilationUnitAware {
    private CompilationUnit compilationUnit;
    static final Class MY_CLASS = MapConstructor.class;
    static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode MAP_TYPE = ClassHelper.makeWithoutCaching(Map.class, false);
    private static final ClassNode LHMAP_TYPE = ClassHelper.makeWithoutCaching(LinkedHashMap.class, false);

    @Override
    public String getAnnotationName() {
        return MY_TYPE_NAME;
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
            boolean includeSuperProperties = this.memberHasValue(anno, "includeSuperProperties", true);
            boolean includeSuperFields = this.memberHasValue(anno, "includeSuperFields", true);
            boolean includeStatic = this.memberHasValue(anno, "includeStatic", true);
            boolean allProperties = this.memberHasValue(anno, "allProperties", true);
            boolean noArg = this.memberHasValue(anno, "noArg", true);
            boolean specialNamedArgHandling = !this.memberHasValue(anno, "specialNamedArgHandling", false);
            List<String> excludes = MapConstructorASTTransformation.getMemberStringList(anno, "excludes");
            List<String> includes = MapConstructorASTTransformation.getMemberStringList(anno, "includes");
            boolean allNames = this.memberHasValue(anno, "allNames", true);
            if (!this.checkIncludeExcludeUndefinedAware(anno, excludes, includes, MY_TYPE_NAME)) {
                return;
            }
            if (!this.checkPropertyList(cNode, includes, "includes", anno, MY_TYPE_NAME, includeFields, includeSuperProperties, allProperties)) {
                return;
            }
            if (!this.checkPropertyList(cNode, excludes, "excludes", anno, MY_TYPE_NAME, includeFields, includeSuperProperties, allProperties)) {
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
            MapConstructorASTTransformation.createConstructors(this, anno, handler, cNode, includeFields, includeProperties, includeSuperProperties, includeSuperFields, noArg, allNames, allProperties, specialNamedArgHandling, includeStatic, excludes, includes, (ClosureExpression)pre, (ClosureExpression)post, source);
            if (pre != null) {
                anno.setMember("pre", new ClosureExpression(Parameter.EMPTY_ARRAY, EmptyStatement.INSTANCE));
            }
            if (post != null) {
                anno.setMember("post", new ClosureExpression(Parameter.EMPTY_ARRAY, EmptyStatement.INSTANCE));
            }
        }
    }

    private static void createConstructors(AbstractASTTransformation xform, AnnotationNode anno, PropertyHandler handler, ClassNode cNode, boolean includeFields, boolean includeProperties, boolean includeSuperProperties, boolean includeSuperFields, boolean noArg, boolean allNames, boolean allProperties, boolean specialNamedArgHandling, boolean includeStatic, List<String> excludes, List<String> includes, ClosureExpression pre, ClosureExpression post, SourceUnit source) {
        cNode.getDeclaredConstructors().removeIf(next -> next.getFirstStatement() == null);
        HashSet<String> names = new HashSet<String>();
        List<Object> superList = includeSuperProperties || includeSuperFields ? GeneralUtils.getAllProperties(names, cNode, cNode.getSuperClass(), includeSuperProperties, includeSuperFields, false, allProperties, true, false, false, allNames, includeStatic) : new ArrayList();
        List<PropertyNode> list = GeneralUtils.getAllProperties(names, cNode, cNode, includeProperties, includeFields, false, allProperties, false, false, false, allNames, includeStatic);
        Parameter map = GeneralUtils.param(MAP_TYPE, "args");
        BlockStatement body = new BlockStatement();
        ClassCodeExpressionTransformer transformer = MapConstructorASTTransformation.makeMapTypedArgsTransformer();
        if (pre != null) {
            ClosureExpression transformed = (ClosureExpression)transformer.transform(pre);
            GeneralUtils.copyStatementsWithSuperAdjustment(transformed, body);
        }
        BlockStatement inner = new BlockStatement();
        superList.addAll(list);
        if (!handler.validateProperties(xform, body, cNode, superList)) {
            return;
        }
        boolean specialNamedArgCase = specialNamedArgHandling && ImmutableASTTransformation.isSpecialNamedArgCase(superList, true);
        MapConstructorASTTransformation.processProps(xform, anno, cNode, handler, allNames, excludes, includes, superList, map, inner);
        body.addStatement(inner);
        Parameter[] params = GeneralUtils.params(specialNamedArgCase ? new Parameter(LHMAP_TYPE, "args") : map);
        if (post != null) {
            ClosureExpression transformed = (ClosureExpression)transformer.transform(post);
            body.addStatement(transformed.getCode());
        }
        int modifiers = VisibilityUtils.getVisibility(anno, cNode, ConstructorNode.class, 1);
        MapConstructorASTTransformation.doAddConstructor(cNode, new ConstructorNode(modifiers, params, ClassNode.EMPTY_ARRAY, body));
        if (noArg && !superList.isEmpty() && !ClassNodeUtils.hasNoArgConstructor(cNode)) {
            MapConstructorASTTransformation.createNoArgConstructor(cNode, modifiers);
        }
    }

    private static void doAddConstructor(final ClassNode cNode, ConstructorNode constructorNode) {
        AnnotatedNodeUtils.markAsGenerated(cNode, constructorNode);
        cNode.addConstructor(constructorNode);
        Parameter argsParam = null;
        for (Parameter p : constructorNode.getParameters()) {
            if (!"args".equals(p.getName())) continue;
            argsParam = p;
            break;
        }
        if (argsParam != null) {
            final Parameter arg = argsParam;
            ClassCodeVisitorSupport variableExpressionFix = new ClassCodeVisitorSupport(){

                @Override
                protected SourceUnit getSourceUnit() {
                    return cNode.getModule().getContext();
                }

                @Override
                public void visitVariableExpression(VariableExpression expression) {
                    super.visitVariableExpression(expression);
                    if ("args".equals(expression.getName())) {
                        expression.setAccessedVariable(arg);
                    }
                }
            };
            variableExpressionFix.visitConstructor(constructorNode);
        }
    }

    private static void processProps(AbstractASTTransformation xform, AnnotationNode anno, ClassNode cNode, PropertyHandler handler, boolean allNames, List<String> excludes, List<String> includes, List<PropertyNode> superList, Parameter map, BlockStatement inner) {
        for (PropertyNode pNode : superList) {
            Statement propInit;
            String name = pNode.getName();
            if (MapConstructorASTTransformation.shouldSkipUndefinedAware(name, excludes, includes, allNames) || (propInit = handler.createPropInit(xform, anno, cNode, pNode, map)) == null) continue;
            inner.addStatement(propInit);
        }
    }

    private static void createNoArgConstructor(ClassNode cNode, int modifiers) {
        Statement body = GeneralUtils.stmt(GeneralUtils.ctorX(ClassNode.THIS, GeneralUtils.args(new MapExpression())));
        ConstructorNode consNode = new ConstructorNode(modifiers, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
        AnnotatedNodeUtils.markAsGenerated(cNode, consNode);
        cNode.addConstructor(consNode);
    }

    private static ClassCodeExpressionTransformer makeMapTypedArgsTransformer() {
        return new ClassCodeExpressionTransformer(){

            @Override
            public Expression transform(Expression exp) {
                VariableExpression ve;
                if (exp instanceof ClosureExpression) {
                    ClosureExpression ce = (ClosureExpression)exp;
                    ce.getCode().visit(this);
                } else if (exp instanceof VariableExpression && "args".equals((ve = (VariableExpression)exp).getName()) && ve.getAccessedVariable() instanceof DynamicVariable) {
                    VariableExpression newVe = GeneralUtils.varX(GeneralUtils.param(MAP_TYPE, "args"));
                    newVe.setSourcePosition(ve);
                    return newVe;
                }
                return exp.transformExpression(this);
            }

            @Override
            protected SourceUnit getSourceUnit() {
                return null;
            }
        };
    }

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        this.compilationUnit = unit;
    }
}

