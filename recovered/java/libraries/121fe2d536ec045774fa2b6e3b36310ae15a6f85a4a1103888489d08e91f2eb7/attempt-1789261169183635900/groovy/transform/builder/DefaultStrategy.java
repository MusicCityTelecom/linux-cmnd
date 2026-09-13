/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.builder;

import groovy.transform.Undefined;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.BuilderASTTransformation;

public class DefaultStrategy
extends BuilderASTTransformation.AbstractBuilderStrategy {
    private static final Expression DEFAULT_INITIAL_VALUE = null;
    private static final int PUBLIC_STATIC = 9;

    @Override
    public void build(BuilderASTTransformation transform, AnnotatedNode annotatedNode, AnnotationNode anno) {
        if (this.unsupportedAttribute(transform, anno, "forClass")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "force")) {
            return;
        }
        if (annotatedNode instanceof ClassNode) {
            this.buildClass(transform, (ClassNode)annotatedNode, anno);
        } else if (annotatedNode instanceof MethodNode) {
            this.buildMethod(transform, (MethodNode)annotatedNode, anno);
        }
    }

    public void buildMethod(BuilderASTTransformation transform, MethodNode mNode, AnnotationNode anno) {
        if (transform.getMemberValue(anno, "includes") != null || transform.getMemberValue(anno, "excludes") != null) {
            transform.addError("Error during " + BuilderASTTransformation.MY_TYPE_NAME + " processing: includes/excludes only allowed on classes", anno);
        }
        ClassNode buildee = mNode.getDeclaringClass();
        ClassNode builder = DefaultStrategy.createBuilder(anno, buildee);
        DefaultStrategy.createBuilderFactoryMethod(anno, buildee, builder);
        for (Parameter parameter : mNode.getParameters()) {
            builder.addField(DefaultStrategy.createFieldCopy(buildee, parameter));
            ClassNodeUtils.addGeneratedMethod(builder, this.createBuilderMethodForProp(builder, new BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo(parameter.getName(), parameter.getType()), DefaultStrategy.getPrefix(anno)));
        }
        ClassNodeUtils.addGeneratedMethod(builder, DefaultStrategy.createBuildMethodForMethod(anno, buildee, mNode, mNode.getParameters()));
    }

    public void buildClass(BuilderASTTransformation transform, ClassNode buildee, AnnotationNode anno) {
        ArrayList<String> excludes = new ArrayList<String>();
        ArrayList<String> includes = new ArrayList<String>();
        includes.add("<DummyUndefinedMarkerString-DoNotUse>");
        if (!this.getIncludeExclude(transform, anno, buildee, excludes, includes)) {
            return;
        }
        if (includes.size() == 1 && Undefined.isUndefined((String)includes.get(0))) {
            includes = null;
        }
        ClassNode builder = DefaultStrategy.createBuilder(anno, buildee);
        DefaultStrategy.createBuilderFactoryMethod(anno, buildee, builder);
        boolean allNames = transform.memberHasValue(anno, "allNames", true);
        boolean allProperties = !transform.memberHasValue(anno, "allProperties", false);
        List<BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo> props = this.getPropertyInfos(transform, anno, buildee, excludes, includes, allNames, allProperties);
        for (BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo pi : props) {
            ClassNode correctedType = DefaultStrategy.getCorrectedType(buildee, pi.getType(), builder);
            String fieldName = pi.getName();
            builder.addField(DefaultStrategy.createFieldCopy(buildee, fieldName, correctedType));
            ClassNodeUtils.addGeneratedMethod(builder, this.createBuilderMethodForProp(builder, new BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo(fieldName, correctedType), DefaultStrategy.getPrefix(anno)));
        }
        ClassNodeUtils.addGeneratedMethod(builder, DefaultStrategy.createBuildMethod(anno, buildee, props));
    }

    private static ClassNode getCorrectedType(ClassNode buildee, ClassNode fieldType, ClassNode declaringClass) {
        Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(declaringClass);
        GenericsUtils.extractSuperClassGenerics(fieldType, buildee, genericsSpec);
        return GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, fieldType);
    }

    private static void createBuilderFactoryMethod(AnnotationNode anno, ClassNode buildee, ClassNode builder) {
        ClassNodeUtils.addGeneratedInnerClass(buildee, builder);
        ClassNodeUtils.addGeneratedMethod(buildee, DefaultStrategy.createBuilderMethod(anno, builder));
    }

    private static ClassNode createBuilder(AnnotationNode anno, ClassNode buildee) {
        return new InnerClassNode(buildee, DefaultStrategy.getFullName(anno, buildee), 9, ClassHelper.OBJECT_TYPE);
    }

    private static String getFullName(AnnotationNode anno, ClassNode buildee) {
        String builderClassName = AbstractASTTransformation.getMemberStringValue(anno, "builderClassName", buildee.getNameWithoutPackage() + "Builder");
        return buildee.getName() + "$" + builderClassName;
    }

    private static String getPrefix(AnnotationNode anno) {
        return AbstractASTTransformation.getMemberStringValue(anno, "prefix", "");
    }

    private static MethodNode createBuildMethodForMethod(AnnotationNode anno, ClassNode buildee, MethodNode mNode, Parameter[] params) {
        ClassNode returnType;
        String buildMethodName = AbstractASTTransformation.getMemberStringValue(anno, "buildMethodName", "build");
        BlockStatement body = new BlockStatement();
        if (mNode instanceof ConstructorNode) {
            returnType = GenericsUtils.newClass(buildee);
            body.addStatement(GeneralUtils.returnS(GeneralUtils.ctorX(GenericsUtils.newClass(mNode.getDeclaringClass()), GeneralUtils.args(params))));
        } else {
            body.addStatement(GeneralUtils.returnS(GeneralUtils.callX(GenericsUtils.newClass(mNode.getDeclaringClass()), mNode.getName(), (Expression)GeneralUtils.args(params))));
            returnType = GenericsUtils.newClass(mNode.getReturnType());
        }
        return new MethodNode(buildMethodName, 1, returnType, BuilderASTTransformation.NO_PARAMS, BuilderASTTransformation.NO_EXCEPTIONS, body);
    }

    private static MethodNode createBuilderMethod(AnnotationNode anno, ClassNode builder) {
        String builderMethodName = AbstractASTTransformation.getMemberStringValue(anno, "builderMethodName", "builder");
        BlockStatement body = new BlockStatement();
        body.addStatement(GeneralUtils.returnS(GeneralUtils.ctorX(builder)));
        return new MethodNode(builderMethodName, 9, builder, BuilderASTTransformation.NO_PARAMS, BuilderASTTransformation.NO_EXCEPTIONS, body);
    }

    private static MethodNode createBuildMethod(AnnotationNode anno, ClassNode buildee, List<BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo> props) {
        String buildMethodName = AbstractASTTransformation.getMemberStringValue(anno, "buildMethodName", "build");
        BlockStatement body = new BlockStatement();
        body.addStatement(GeneralUtils.returnS(DefaultStrategy.initializeInstance(buildee, props, body)));
        return new MethodNode(buildMethodName, 1, GenericsUtils.newClass(buildee), BuilderASTTransformation.NO_PARAMS, BuilderASTTransformation.NO_EXCEPTIONS, body);
    }

    private MethodNode createBuilderMethodForProp(ClassNode builder, BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo pinfo, String prefix) {
        ClassNode fieldType = pinfo.getType();
        String fieldName = pinfo.getName();
        String setterName = this.getSetterName(prefix, fieldName);
        return new MethodNode(setterName, 1, GenericsUtils.newClass(builder), GeneralUtils.params(GeneralUtils.param(fieldType, fieldName)), BuilderASTTransformation.NO_EXCEPTIONS, GeneralUtils.block(GeneralUtils.stmt(GeneralUtils.assignX(GeneralUtils.propX((Expression)GeneralUtils.varX("this"), GeneralUtils.constX(fieldName)), GeneralUtils.varX(fieldName, fieldType))), GeneralUtils.returnS(GeneralUtils.varX("this", builder))));
    }

    private static FieldNode createFieldCopy(ClassNode buildee, Parameter param) {
        Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(buildee);
        GenericsUtils.extractSuperClassGenerics(param.getType(), buildee, genericsSpec);
        ClassNode correctedParamType = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, param.getType());
        return new FieldNode(param.getName(), 2, correctedParamType, buildee, param.getInitialExpression());
    }

    private static FieldNode createFieldCopy(ClassNode buildee, String fieldName, ClassNode fieldType) {
        return new FieldNode(fieldName, 2, fieldType, buildee, DEFAULT_INITIAL_VALUE);
    }

    private static Expression initializeInstance(ClassNode buildee, List<BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo> props, BlockStatement body) {
        VariableExpression instance = GeneralUtils.localVarX("_the" + buildee.getNameWithoutPackage(), buildee);
        body.addStatement(GeneralUtils.declS(instance, GeneralUtils.ctorX(buildee)));
        for (BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo pi : props) {
            body.addStatement(GeneralUtils.stmt(GeneralUtils.assignX(GeneralUtils.propX((Expression)instance, pi.getName()), GeneralUtils.varX(pi.getName(), pi.getType()))));
        }
        return instance;
    }
}

