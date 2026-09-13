/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc;

import groovy.lang.Reference;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.asm.InvocationWriter;
import org.codehaus.groovy.classgen.asm.MopWriter;
import org.codehaus.groovy.classgen.asm.TypeChooser;
import org.codehaus.groovy.classgen.asm.WriterControllerFactory;
import org.codehaus.groovy.classgen.asm.sc.StaticCompilationMopWriter;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesTypeChooser;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class StaticCompilationVisitor
extends StaticTypeCheckingVisitor {
    public static final ClassNode TYPECHECKED_CLASSNODE = ClassHelper.make(TypeChecked.class);
    public static final ClassNode COMPILESTATIC_CLASSNODE = ClassHelper.make(CompileStatic.class);
    public static final ClassNode ARRAYLIST_CLASSNODE = ClassHelper.make(ArrayList.class);
    public static final MethodNode ARRAYLIST_ADD_METHOD = ARRAYLIST_CLASSNODE.getMethod("add", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE, "o")});
    public static final MethodNode ARRAYLIST_CONSTRUCTOR = new ConstructorNode(1, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE);
    private final TypeChooser typeChooser = new StaticTypesTypeChooser();
    private ClassNode classNode;

    public StaticCompilationVisitor(SourceUnit unit, ClassNode node) {
        super(unit, node);
    }

    @Override
    protected ClassNode[] getTypeCheckingAnnotations() {
        return new ClassNode[]{TYPECHECKED_CLASSNODE, COMPILESTATIC_CLASSNODE};
    }

    public static boolean isStaticallyCompiled(AnnotatedNode node) {
        if (node != null && node.getNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE) != null) {
            return Boolean.TRUE.equals(node.getNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE));
        }
        if (node instanceof MethodNode) {
            if (!Boolean.TRUE.equals(node.getNodeMetaData("DEFAULT_PARAMETER_GENERATED"))) {
                return StaticCompilationVisitor.isStaticallyCompiled(node.getDeclaringClass());
            }
        } else if (node instanceof ClassNode) {
            return StaticCompilationVisitor.isStaticallyCompiled(((ClassNode)node).getOuterClass());
        }
        return false;
    }

    private void addPrivateFieldAndMethodAccessors(ClassNode node) {
        StaticCompilationVisitor.addPrivateBridgeMethods(node);
        StaticCompilationVisitor.addPrivateFieldsAccessors(node);
        Iterator<InnerClassNode> it = node.getInnerClasses();
        while (it.hasNext()) {
            this.addPrivateFieldAndMethodAccessors(it.next());
        }
    }

    private void addDynamicOuterClassAccessorsCallback(ClassNode outer) {
        if (outer != null) {
            if (!StaticCompilationVisitor.isStaticallyCompiled(outer) && outer.getNodeMetaData((Object)StaticCompilationMetadataKeys.DYNAMIC_OUTER_NODE_CALLBACK) == null) {
                outer.putNodeMetaData((Object)StaticCompilationMetadataKeys.DYNAMIC_OUTER_NODE_CALLBACK, (source, context, classNode) -> {
                    if (classNode == outer) {
                        StaticCompilationVisitor.addPrivateBridgeMethods(classNode);
                        StaticCompilationVisitor.addPrivateFieldsAccessors(classNode);
                    }
                });
            }
            this.addDynamicOuterClassAccessorsCallback(outer.getOuterClass());
        }
    }

    @Override
    public void visitClass(ClassNode node) {
        boolean skip = this.shouldSkipClassNode(node);
        if (!skip && !this.anyMethodSkip(node)) {
            node.putNodeMetaData(MopWriter.Factory.class, StaticCompilationMopWriter.FACTORY);
        }
        ClassNode previousClassNode = this.classNode;
        this.classNode = node;
        this.classNode.getInnerClasses().forEachRemaining(innerClassNode -> {
            boolean innerStaticCompile = !skip && !this.isSkippedInnerClass((AnnotatedNode)innerClassNode);
            innerClassNode.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, innerStaticCompile);
            innerClassNode.putNodeMetaData(WriterControllerFactory.class, node.getNodeMetaData(WriterControllerFactory.class));
            if (innerStaticCompile && !this.anyMethodSkip((ClassNode)innerClassNode)) {
                innerClassNode.putNodeMetaData(MopWriter.Factory.class, StaticCompilationMopWriter.FACTORY);
            }
        });
        super.visitClass(node);
        this.addPrivateFieldAndMethodAccessors(node);
        if (StaticCompilationVisitor.isStaticallyCompiled(node)) {
            ClassNode outerClass = node.getOuterClass();
            this.addDynamicOuterClassAccessorsCallback(outerClass);
        }
        this.classNode = previousClassNode;
    }

    private boolean anyMethodSkip(ClassNode node) {
        for (MethodNode methodNode : node.getMethods()) {
            if (!this.isSkipMode(methodNode)) continue;
            return true;
        }
        return false;
    }

    private void visitConstructorOrMethod(MethodNode node) {
        ClassNode declaringClass;
        boolean isSC;
        boolean isSkipped = this.isSkipMode(node);
        boolean bl = isSC = !isSkipped && StaticCompilationVisitor.isStaticallyCompiled(node);
        if (isSkipped) {
            node.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.FALSE);
        }
        if (node instanceof ConstructorNode) {
            super.visitConstructor((ConstructorNode)node);
            declaringClass = node.getDeclaringClass();
            if (!(!isSC || StaticCompilationVisitor.isStaticallyCompiled(declaringClass) || declaringClass.getFields().isEmpty() && declaringClass.getProperties().isEmpty() && declaringClass.getObjectInitializerStatements().isEmpty())) {
                this.addStaticTypeError("Cannot statically compile constructor implicitly including non-static elements from fields, properties or initializers", node);
            }
        } else {
            super.visitMethod(node);
        }
        if (isSC) {
            declaringClass = node.getDeclaringClass();
            this.addDynamicOuterClassAccessorsCallback(declaringClass);
        }
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        this.visitConstructorOrMethod(node);
    }

    @Override
    public void visitMethod(MethodNode node) {
        this.visitConstructorOrMethod(node);
    }

    private static void addPrivateFieldsAccessors(ClassNode node) {
        Map privateFieldAccessors = (Map)node.getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_FIELDS_ACCESSORS);
        Map privateFieldMutators = (Map)node.getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_FIELDS_MUTATORS);
        if (privateFieldAccessors != null || privateFieldMutators != null) {
            return;
        }
        HashSet accessedFields = (HashSet)node.getNodeMetaData((Object)StaticTypesMarker.PV_FIELDS_ACCESS);
        Set mutatedFields = (Set)node.getNodeMetaData((Object)StaticTypesMarker.PV_FIELDS_MUTATION);
        if (accessedFields == null && mutatedFields == null) {
            return;
        }
        if (mutatedFields != null) {
            accessedFields = new HashSet(Optional.ofNullable(accessedFields).orElseGet(Collections::emptySet));
            accessedFields.addAll(mutatedFields);
        }
        int acc = -1;
        privateFieldAccessors = accessedFields != null ? new HashMap() : null;
        privateFieldMutators = mutatedFields != null ? new HashMap() : null;
        int modifiers = 4105;
        for (FieldNode fieldNode : node.getFields()) {
            Expression receiver;
            Parameter param;
            boolean generateMutator;
            boolean generateAccessor = accessedFields != null && accessedFields.contains(fieldNode);
            boolean bl = generateMutator = mutatedFields != null && mutatedFields.contains(fieldNode);
            if (generateAccessor) {
                param = new Parameter(node.getPlainNodeReference(), "$that");
                receiver = fieldNode.isStatic() ? GeneralUtils.classX(node) : GeneralUtils.varX(param);
                Statement body = GeneralUtils.returnS(GeneralUtils.attrX(receiver, GeneralUtils.constX(fieldNode.getName())));
                MethodNode accessor = node.addMethod("pfaccess$" + ++acc, 4105, fieldNode.getOriginType(), new Parameter[]{param}, ClassNode.EMPTY_ARRAY, body);
                accessor.setNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.TRUE);
                privateFieldAccessors.put(fieldNode.getName(), accessor);
            }
            if (!generateMutator) continue;
            if (!generateAccessor) {
                ++acc;
            }
            param = new Parameter(node.getPlainNodeReference(), "$that");
            receiver = fieldNode.isStatic() ? GeneralUtils.classX(node) : GeneralUtils.varX(param);
            Parameter value = new Parameter(fieldNode.getOriginType(), "$value");
            Statement body = GeneralUtils.assignS(GeneralUtils.attrX(receiver, GeneralUtils.constX(fieldNode.getName())), GeneralUtils.varX(value));
            MethodNode mutator = node.addMethod("pfaccess$0" + acc, 4105, fieldNode.getOriginType(), new Parameter[]{param, value}, ClassNode.EMPTY_ARRAY, body);
            mutator.setNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.TRUE);
            privateFieldMutators.put(fieldNode.getName(), mutator);
        }
        if (privateFieldAccessors != null) {
            node.setNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_FIELDS_ACCESSORS, privateFieldAccessors);
        }
        if (privateFieldMutators != null) {
            node.setNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_FIELDS_MUTATORS, privateFieldMutators);
        }
    }

    private static void addPrivateBridgeMethods(ClassNode node) {
        Set accessedMethods = (Set)node.getNodeMetaData((Object)StaticTypesMarker.PV_METHODS_ACCESS);
        if (accessedMethods == null) {
            return;
        }
        ArrayList<MethodNode> methods = new ArrayList<MethodNode>(node.getAllDeclaredMethods());
        methods.addAll(node.getDeclaredConstructors());
        HashMap<MethodNode, ConstructorNode> privateBridgeMethods = (HashMap<MethodNode, ConstructorNode>)node.getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_BRIDGE_METHODS);
        if (privateBridgeMethods != null) {
            return;
        }
        privateBridgeMethods = new HashMap<MethodNode, ConstructorNode>();
        int i = -1;
        int access = 4105;
        for (MethodNode method : methods) {
            MethodNode bridge;
            Statement body;
            ArgumentListExpression arguments;
            if (!accessedMethods.contains(method)) continue;
            List<String> methodSpecificGenerics = StaticCompilationVisitor.methodSpecificGenerics(method);
            ++i;
            ClassNode declaringClass = method.getDeclaringClass();
            Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(node);
            genericsSpec = GenericsUtils.addMethodGenerics(method, genericsSpec);
            GenericsUtils.extractSuperClassGenerics(node, declaringClass, genericsSpec);
            Parameter[] methodParameters = method.getParameters();
            Parameter[] newParams = new Parameter[methodParameters.length + 1];
            for (int j = 1; j < newParams.length; ++j) {
                Parameter orig = methodParameters[j - 1];
                newParams[j] = new Parameter(GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, orig.getOriginType(), methodSpecificGenerics), orig.getName());
            }
            if (method.getParameters() == null || method.getParameters().length == 0) {
                arguments = ArgumentListExpression.EMPTY_ARGUMENTS;
            } else {
                ArrayList<Expression> args = new ArrayList<Expression>();
                for (Parameter parameter : methodParameters) {
                    args.add(GeneralUtils.varX(parameter));
                }
                arguments = GeneralUtils.args(args);
            }
            if (method instanceof ConstructorNode) {
                ClassNode thatType = null;
                Iterator<InnerClassNode> innerClasses = node.getInnerClasses();
                if (innerClasses.hasNext()) {
                    thatType = innerClasses.next();
                } else {
                    thatType = new InnerClassNode(node.redirect(), node.getName() + "$1", 4104, ClassHelper.OBJECT_TYPE);
                    node.getModule().addClass(thatType);
                }
                newParams[0] = new Parameter(thatType.getPlainNodeReference(), "$that");
                body = GeneralUtils.ctorThisS(arguments);
                bridge = node.addConstructor(4096, newParams, ClassNode.EMPTY_ARRAY, body);
            } else {
                newParams[0] = new Parameter(node.getPlainNodeReference(), "$that");
                ClassExpression receiver = method.isStatic() ? GeneralUtils.classX(node) : GeneralUtils.varX(newParams[0]);
                MethodCallExpression call = GeneralUtils.callX((Expression)receiver, method.getName(), (Expression)arguments);
                call.setMethodTarget(method);
                body = new ExpressionStatement(call);
                bridge = node.addMethod("access$" + i, 4105, GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, method.getReturnType(), methodSpecificGenerics), newParams, method.getExceptions(), body);
            }
            GenericsType[] origGenericsTypes = method.getGenericsTypes();
            if (origGenericsTypes != null) {
                bridge.setGenericsTypes(GenericsUtils.applyGenericsContextToPlaceHolders(genericsSpec, origGenericsTypes));
            }
            bridge.setNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.TRUE);
            privateBridgeMethods.put(method, (ConstructorNode)bridge);
        }
        if (!privateBridgeMethods.isEmpty()) {
            node.setNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_BRIDGE_METHODS, privateBridgeMethods);
        }
    }

    private static List<String> methodSpecificGenerics(MethodNode method) {
        ArrayList<String> genericTypeNames = new ArrayList<String>();
        GenericsType[] genericsTypes = method.getGenericsTypes();
        if (genericsTypes != null) {
            for (GenericsType gt : genericsTypes) {
                genericTypeNames.add(gt.getName());
            }
        }
        return genericTypeNames;
    }

    private static void memorizeInitialExpressions(MethodNode node) {
        if (node.getParameters() != null) {
            for (Parameter parameter : node.getParameters()) {
                parameter.putNodeMetaData((Object)StaticTypesMarker.INITIAL_EXPRESSION, parameter.getInitialExpression());
            }
        }
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression call) {
        super.visitMethodCallExpression(call);
        MethodNode target = (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
        if (target != null) {
            call.setMethodTarget(target);
            StaticCompilationVisitor.memorizeInitialExpressions(target);
        }
        if (call.getMethodTarget() == null && call.getLineNumber() > 0) {
            this.addError("Target method for method call expression hasn't been set", call);
        }
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression call) {
        MethodNode target;
        super.visitConstructorCallExpression(call);
        if (call.isUsingAnonymousInnerClass() && call.getType().getNodeMetaData(StaticTypeCheckingVisitor.class) != null) {
            ClassNode anonType = call.getType();
            anonType.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, anonType.getEnclosingMethod().getNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE));
            anonType.putNodeMetaData(WriterControllerFactory.class, anonType.getOuterClass().getNodeMetaData(WriterControllerFactory.class));
        }
        if ((target = (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET)) == null && call.getLineNumber() > 0) {
            this.addError("Target constructor for constructor call expression hasn't been set", call);
        } else if (target == null) {
            ArgumentListExpression argumentListExpression = InvocationWriter.makeArgumentList(call.getArguments());
            List<Expression> expressions = argumentListExpression.getExpressions();
            ClassNode[] args = new ClassNode[expressions.size()];
            int n = args.length;
            for (int i = 0; i < n; ++i) {
                args[i] = this.typeChooser.resolveType(expressions.get(i), this.classNode);
            }
            target = this.findMethodOrFail(call, call.isSuperCall() ? this.classNode.getSuperClass() : this.classNode, "<init>", args);
            call.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, target);
        }
        if (target != null) {
            StaticCompilationVisitor.memorizeInitialExpressions(target);
        }
    }

    @Override
    public void visitForLoop(ForStatement statement) {
        super.visitForLoop(statement);
        Expression collectionExpression = statement.getCollectionExpression();
        if (!(collectionExpression instanceof ClosureListExpression)) {
            ClassNode forLoopVariableType = statement.getVariableType();
            ClassNode collectionType = this.getType(collectionExpression);
            ClassNode componentType = ClassHelper.isWrapperCharacter(ClassHelper.getWrapper(forLoopVariableType)) && ClassHelper.isStringType(collectionType) ? forLoopVariableType : StaticCompilationVisitor.inferLoopElementType(collectionType);
            statement.getVariable().setType(componentType);
        }
    }

    @Override
    protected MethodNode findMethodOrFail(Expression expr, ClassNode receiver, String name, ClassNode ... args) {
        MethodNode methodNode = super.findMethodOrFail(expr, receiver, name, args);
        if (expr instanceof BinaryExpression && methodNode != null) {
            expr.putNodeMetaData((Object)StaticCompilationMetadataKeys.BINARY_EXP_TARGET, new Object[]{methodNode, name});
        }
        return methodNode;
    }

    @Override
    protected boolean existsProperty(final PropertyExpression pexp, boolean checkForReadOnly, final ClassCodeVisitorSupport visitor) {
        Expression objectExpression = pexp.getObjectExpression();
        ClassNode objectExpressionType = this.getType(objectExpression);
        final Reference<ClassNode> rType = new Reference<ClassNode>(objectExpressionType);
        ClassCodeVisitorSupport receiverMemoizer = new ClassCodeVisitorSupport(){

            @Override
            protected SourceUnit getSourceUnit() {
                return null;
            }

            @Override
            public void visitField(FieldNode node) {
                ClassNode declaringClass;
                if (visitor != null) {
                    visitor.visitField(node);
                }
                if ((declaringClass = node.getDeclaringClass()) != null) {
                    if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(declaringClass, ClassHelper.LIST_TYPE)) {
                        boolean spread = declaringClass.getDeclaredField(node.getName()) != node;
                        pexp.setSpreadSafe(spread);
                    }
                    rType.set(declaringClass);
                }
            }

            @Override
            public void visitMethod(MethodNode node) {
                ClassNode declaringClass;
                if (visitor != null) {
                    visitor.visitMethod(node);
                }
                if ((declaringClass = node.getDeclaringClass()) != null) {
                    if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(declaringClass, ClassHelper.LIST_TYPE)) {
                        List<MethodNode> properties = declaringClass.getDeclaredMethods(node.getName());
                        boolean spread = true;
                        for (MethodNode mn : properties) {
                            if (node != mn) continue;
                            spread = false;
                            break;
                        }
                        pexp.setSpreadSafe(spread);
                    }
                    rType.set(declaringClass);
                }
            }

            @Override
            public void visitProperty(PropertyNode node) {
                ClassNode declaringClass;
                if (visitor != null) {
                    visitor.visitProperty(node);
                }
                if ((declaringClass = node.getDeclaringClass()) != null) {
                    if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(declaringClass, ClassHelper.LIST_TYPE)) {
                        List<PropertyNode> properties = declaringClass.getProperties();
                        boolean spread = true;
                        for (PropertyNode propertyNode : properties) {
                            if (propertyNode != node) continue;
                            spread = false;
                            break;
                        }
                        pexp.setSpreadSafe(spread);
                    }
                    rType.set(declaringClass);
                }
            }
        };
        boolean exists = super.existsProperty(pexp, checkForReadOnly, receiverMemoizer);
        if (exists) {
            objectExpressionType = rType.get();
            if (objectExpression.getNodeMetaData((Object)StaticCompilationMetadataKeys.PROPERTY_OWNER) == null) {
                objectExpression.putNodeMetaData((Object)StaticCompilationMetadataKeys.PROPERTY_OWNER, objectExpressionType);
            }
            if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(objectExpressionType, ClassHelper.LIST_TYPE)) {
                objectExpression.putNodeMetaData((Object)StaticCompilationMetadataKeys.COMPONENT_TYPE, this.inferComponentType(objectExpressionType, ClassHelper.int_TYPE));
            }
        }
        return exists;
    }

    @Override
    public void visitPropertyExpression(PropertyExpression expression) {
        super.visitPropertyExpression(expression);
        Object dynamic = expression.getNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION);
        if (dynamic != null) {
            expression.getObjectExpression().putNodeMetaData((Object)StaticCompilationMetadataKeys.RECEIVER_OF_DYNAMIC_PROPERTY, dynamic);
        }
    }

    static {
        ARRAYLIST_CONSTRUCTOR.setDeclaringClass(ARRAYLIST_CLASSNODE);
    }
}

