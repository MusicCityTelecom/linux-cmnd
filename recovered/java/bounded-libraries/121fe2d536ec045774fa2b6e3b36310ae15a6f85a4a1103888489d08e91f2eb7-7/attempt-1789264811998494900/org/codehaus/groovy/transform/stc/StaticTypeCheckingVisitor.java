/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.stc;

import groovy.lang.DelegatesTo;
import groovy.lang.IntRange;
import groovy.lang.Tuple2;
import groovy.transform.NamedParam;
import groovy.transform.NamedParams;
import groovy.transform.TypeChecked;
import groovy.transform.TypeCheckingMode;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.ClosureSignatureConflictResolver;
import groovy.transform.stc.ClosureSignatureHint;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.groovy.util.BeanUtils;
import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BitwiseNegationExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ElvisOperatorExpression;
import org.codehaus.groovy.ast.expr.EmptyExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCall;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.MethodReferenceExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.codehaus.groovy.ast.expr.UnaryPlusExpression;
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
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.classgen.ReturnAdder;
import org.codehaus.groovy.classgen.asm.InvocationWriter;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.ResolveVisitor;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.TokenUtil;
import org.codehaus.groovy.transform.stc.DefaultTypeCheckingExtension;
import org.codehaus.groovy.transform.stc.DelegationMetadata;
import org.codehaus.groovy.transform.stc.EnumTypeCheckingExtension;
import org.codehaus.groovy.transform.stc.ExtensionMethodNode;
import org.codehaus.groovy.transform.stc.PropertyLookupVisitor;
import org.codehaus.groovy.transform.stc.Receiver;
import org.codehaus.groovy.transform.stc.SecondPassExpression;
import org.codehaus.groovy.transform.stc.SharedVariableCollector;
import org.codehaus.groovy.transform.stc.SignatureCodec;
import org.codehaus.groovy.transform.stc.SignatureCodecVersion1;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;
import org.codehaus.groovy.transform.stc.TraitTypeCheckingExtension;
import org.codehaus.groovy.transform.stc.TypeCheckingContext;
import org.codehaus.groovy.transform.stc.TypeCheckingExtension;
import org.codehaus.groovy.transform.stc.UnionTypeClassNode;
import org.codehaus.groovy.transform.trait.Traits;

public class StaticTypeCheckingVisitor
extends ClassCodeVisitorSupport {
    private static final boolean DEBUG_GENERATED_CODE = SystemUtil.getBooleanSafe("groovy.stc.debug");
    private static final AtomicLong UNIQUE_LONG = new AtomicLong();
    protected static final Object ERROR_COLLECTOR = ErrorCollector.class;
    protected static final List<MethodNode> EMPTY_METHODNODE_LIST = Collections.emptyList();
    protected static final ClassNode TYPECHECKED_CLASSNODE = ClassHelper.make(TypeChecked.class);
    protected static final ClassNode[] TYPECHECKING_ANNOTATIONS = new ClassNode[]{TYPECHECKED_CLASSNODE};
    protected static final ClassNode TYPECHECKING_INFO_NODE = ClassHelper.make(TypeChecked.TypeCheckingInfo.class);
    protected static final ClassNode DGM_CLASSNODE = ClassHelper.make(DefaultGroovyMethods.class);
    protected static final int CURRENT_SIGNATURE_PROTOCOL_VERSION = 1;
    protected static final Expression CURRENT_SIGNATURE_PROTOCOL = new ConstantExpression(1, true);
    protected static final MethodNode GET_DELEGATE = ClassHelper.CLOSURE_TYPE.getGetterMethod("getDelegate");
    protected static final MethodNode GET_OWNER = ClassHelper.CLOSURE_TYPE.getGetterMethod("getOwner");
    protected static final MethodNode GET_THISOBJECT = ClassHelper.CLOSURE_TYPE.getGetterMethod("getThisObject");
    protected static final ClassNode DELEGATES_TO = ClassHelper.make(DelegatesTo.class);
    protected static final ClassNode DELEGATES_TO_TARGET = ClassHelper.make(DelegatesTo.Target.class);
    protected static final ClassNode CLOSUREPARAMS_CLASSNODE = ClassHelper.make(ClosureParams.class);
    protected static final ClassNode NAMED_PARAMS_CLASSNODE = ClassHelper.make(NamedParams.class);
    protected static final ClassNode NAMED_PARAM_CLASSNODE = ClassHelper.make(NamedParam.class);
    @Deprecated
    protected static final ClassNode LINKEDHASHMAP_CLASSNODE = StaticTypeCheckingSupport.LinkedHashMap_TYPE;
    protected static final ClassNode ENUMERATION_TYPE = ClassHelper.make(Enumeration.class);
    protected static final ClassNode MAP_ENTRY_TYPE = ClassHelper.make(Map.Entry.class);
    protected static final ClassNode ITERABLE_TYPE = ClassHelper.ITERABLE_TYPE;
    private static List<ClassNode> TUPLE_TYPES = Arrays.stream(ClassHelper.TUPLE_CLASSES).map(ClassHelper::makeWithoutCaching).collect(Collectors.toList());
    public static final MethodNode CLOSURE_CALL_NO_ARG = ClassHelper.CLOSURE_TYPE.getDeclaredMethod("call", Parameter.EMPTY_ARRAY);
    public static final MethodNode CLOSURE_CALL_ONE_ARG = ClassHelper.CLOSURE_TYPE.getDeclaredMethod("call", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE, "arg")});
    public static final MethodNode CLOSURE_CALL_VARGS = ClassHelper.CLOSURE_TYPE.getDeclaredMethod("call", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE.makeArray(), "args")});
    public static final Statement GENERATED_EMPTY_STATEMENT = EmptyStatement.INSTANCE;
    protected final ReturnAdder.ReturnStatementListener returnListener = returnStatement -> {
        if (returnStatement.isReturningNullOrVoid()) {
            return;
        }
        ClassNode returnType = this.checkReturnType(returnStatement);
        if (this.typeCheckingContext.getEnclosingClosure() != null) {
            this.addClosureReturnType(returnType);
        } else if (this.typeCheckingContext.getEnclosingMethod() == null) {
            throw new GroovyBugError("Unexpected return statement at " + returnStatement.getLineNumber() + ":" + returnStatement.getColumnNumber() + " " + returnStatement.getText());
        }
    };
    protected final ReturnAdder returnAdder = new ReturnAdder(this.returnListener);
    protected FieldNode currentField;
    protected PropertyNode currentProperty;
    protected DefaultTypeCheckingExtension extension;
    protected TypeCheckingContext typeCheckingContext = new TypeCheckingContext(this);

    public StaticTypeCheckingVisitor(SourceUnit source, ClassNode classNode) {
        this.typeCheckingContext.pushEnclosingClassNode(classNode);
        this.typeCheckingContext.pushTemporaryTypeInfo();
        this.typeCheckingContext.pushErrorCollector(source.getErrorCollector());
        this.typeCheckingContext.source = source;
        this.extension = new DefaultTypeCheckingExtension(this);
        this.extension.addHandler(new EnumTypeCheckingExtension(this));
        this.extension.addHandler(new TraitTypeCheckingExtension(this));
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.typeCheckingContext.getSource();
    }

    public void initialize() {
        this.extension.setup();
    }

    public TypeCheckingContext getTypeCheckingContext() {
        return this.typeCheckingContext;
    }

    public void addTypeCheckingExtension(TypeCheckingExtension extension) {
        this.extension.addHandler(extension);
    }

    public void setCompilationUnit(CompilationUnit compilationUnit) {
        this.typeCheckingContext.setCompilationUnit(compilationUnit);
    }

    @Override
    public void visitClass(ClassNode node) {
        if (this.shouldSkipClassNode(node)) {
            return;
        }
        if (!this.extension.beforeVisitClass(node)) {
            Object type = node.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            if (type != null) {
                this.typeCheckingContext.pushErrorCollector();
            }
            this.typeCheckingContext.pushEnclosingClassNode(node);
            Set<MethodNode> oldSet = this.typeCheckingContext.alreadyVisitedMethods;
            this.typeCheckingContext.alreadyVisitedMethods = new LinkedHashSet<MethodNode>();
            super.visitClass(node);
            node.getInnerClasses().forEachRemaining(this::visitClass);
            this.typeCheckingContext.alreadyVisitedMethods = oldSet;
            this.typeCheckingContext.popEnclosingClassNode();
            if (type != null) {
                this.typeCheckingContext.popErrorCollector();
            }
            node.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, node);
            node.putNodeMetaData(StaticTypeCheckingVisitor.class, Boolean.TRUE);
            node.getMethods().forEach(n -> n.putNodeMetaData(StaticTypeCheckingVisitor.class, Boolean.TRUE));
            node.getDeclaredConstructors().forEach(n -> n.putNodeMetaData(StaticTypeCheckingVisitor.class, Boolean.TRUE));
        }
        this.extension.afterVisitClass(node);
    }

    protected ClassNode[] getTypeCheckingAnnotations() {
        return TYPECHECKING_ANNOTATIONS;
    }

    protected boolean shouldSkipClassNode(ClassNode node) {
        return Boolean.TRUE.equals(node.getNodeMetaData(StaticTypeCheckingVisitor.class)) || this.isSkipMode(node);
    }

    protected boolean shouldSkipMethodNode(MethodNode node) {
        return Boolean.TRUE.equals(node.getNodeMetaData(StaticTypeCheckingVisitor.class)) || this.isSkipMode(node);
    }

    public boolean isSkipMode(AnnotatedNode node) {
        if (node == null) {
            return false;
        }
        for (ClassNode tca : this.getTypeCheckingAnnotations()) {
            List<AnnotationNode> annotations = node.getAnnotations(tca);
            if (annotations == null) continue;
            for (AnnotationNode annotation : annotations) {
                Expression value = annotation.getMember("value");
                if (value == null) continue;
                if (value instanceof ConstantExpression) {
                    ConstantExpression ce = (ConstantExpression)value;
                    if (!TypeCheckingMode.SKIP.toString().equals(ce.getValue().toString())) continue;
                    return true;
                }
                if (!(value instanceof PropertyExpression)) continue;
                PropertyExpression pe = (PropertyExpression)value;
                if (!TypeCheckingMode.SKIP.toString().equals(pe.getPropertyAsString())) continue;
                return true;
            }
        }
        if (node instanceof MethodNode) {
            return this.isSkipMode(node.getDeclaringClass());
        }
        return this.isSkippedInnerClass(node);
    }

    protected boolean isSkippedInnerClass(AnnotatedNode node) {
        MethodNode enclosingMethod;
        ClassNode type;
        return node instanceof ClassNode && (type = (ClassNode)node).getOuterClass() != null && (enclosingMethod = type.getEnclosingMethod()) != null && this.isSkipMode(enclosingMethod);
    }

    @Override
    public void visitClassExpression(ClassExpression expression) {
        super.visitClassExpression(expression);
        ClassNode cn = (ClassNode)expression.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
        if (cn == null) {
            this.storeType(expression, this.getType(expression));
        }
    }

    private static ClassNode getOutermost(ClassNode cn) {
        while (cn.getOuterClass() != null) {
            cn = cn.getOuterClass();
        }
        return cn;
    }

    private static void addPrivateFieldOrMethodAccess(Expression source, ClassNode cn, StaticTypesMarker key, ASTNode accessedMember) {
        cn.getNodeMetaData((Object)key, x -> new LinkedHashSet()).add(accessedMember);
        source.putNodeMetaData((Object)key, accessedMember);
    }

    private void checkOrMarkPrivateAccess(Expression source, FieldNode fn, boolean lhsOfAssignment) {
        ClassNode enclosingClass;
        if (fn == null || !fn.isPrivate()) {
            return;
        }
        ClassNode declaringClass = fn.getDeclaringClass();
        if (declaringClass == (enclosingClass = this.typeCheckingContext.getEnclosingClassNode()) && this.typeCheckingContext.getEnclosingClosure() == null) {
            return;
        }
        if (declaringClass == enclosingClass || StaticTypeCheckingVisitor.getOutermost(declaringClass) == StaticTypeCheckingVisitor.getOutermost(enclosingClass)) {
            StaticTypesMarker accessKind = lhsOfAssignment ? StaticTypesMarker.PV_FIELDS_MUTATION : StaticTypesMarker.PV_FIELDS_ACCESS;
            StaticTypeCheckingVisitor.addPrivateFieldOrMethodAccess(source, declaringClass, accessKind, fn);
        }
    }

    private void checkOrMarkPrivateAccess(Expression source, MethodNode mn) {
        ClassNode enclosingClassNode;
        ClassNode declaringClass = mn.getDeclaringClass();
        if (declaringClass != (enclosingClassNode = this.typeCheckingContext.getEnclosingClassNode()) || this.typeCheckingContext.getEnclosingClosure() != null) {
            int mods = mn.getModifiers();
            boolean sameModule = declaringClass.getModule() == enclosingClassNode.getModule();
            String packageName = declaringClass.getPackageName();
            if (packageName == null) {
                packageName = "";
            }
            if (Modifier.isPrivate(mods) && sameModule) {
                StaticTypeCheckingVisitor.addPrivateFieldOrMethodAccess(source, declaringClass, StaticTypesMarker.PV_METHODS_ACCESS, mn);
            } else if (Modifier.isProtected(mods) && !packageName.equals(enclosingClassNode.getPackageName()) && !StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(enclosingClassNode, declaringClass)) {
                ClassNode cn = enclosingClassNode;
                while ((cn = cn.getOuterClass()) != null) {
                    if (!StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(cn, declaringClass)) continue;
                    StaticTypeCheckingVisitor.addPrivateFieldOrMethodAccess(source, cn, StaticTypesMarker.PV_METHODS_ACCESS, mn);
                    break;
                }
            }
        }
    }

    @Override
    public void visitVariableExpression(VariableExpression vexp) {
        super.visitVariableExpression(vexp);
        if (this.storeTypeForSuper(vexp)) {
            return;
        }
        if (this.storeTypeForThis(vexp)) {
            return;
        }
        String name = vexp.getName();
        Variable accessedVariable = vexp.getAccessedVariable();
        TypeCheckingContext.EnclosingClosure enclosingClosure = this.typeCheckingContext.getEnclosingClosure();
        if (accessedVariable instanceof DynamicVariable) {
            if (enclosingClosure != null) {
                switch (name) {
                    case "delegate": {
                        DelegationMetadata dm = this.getDelegationMetadata(enclosingClosure.getClosureExpression());
                        if (dm != null) {
                            this.storeType(vexp, dm.getType());
                            return;
                        }
                    }
                    case "owner": {
                        if (this.typeCheckingContext.getEnclosingClosureStack().size() > 1) {
                            this.storeType(vexp, ClassHelper.CLOSURE_TYPE);
                            return;
                        }
                    }
                    case "thisObject": {
                        this.storeType(vexp, this.typeCheckingContext.getEnclosingClassNode());
                        return;
                    }
                    case "parameterTypes": {
                        this.storeType(vexp, ClassHelper.CLASS_Type.makeArray());
                        return;
                    }
                    case "maximumNumberOfParameters": 
                    case "resolveStrategy": 
                    case "directive": {
                        this.storeType(vexp, ClassHelper.int_TYPE);
                        return;
                    }
                }
            }
            if (this.tryVariableExpressionAsProperty(vexp, name)) {
                return;
            }
            if (!this.extension.handleUnresolvedVariableExpression(vexp)) {
                this.addStaticTypeError("The variable [" + name + "] is undeclared.", vexp);
            }
        } else if (accessedVariable instanceof FieldNode) {
            if (enclosingClosure != null) {
                this.tryVariableExpressionAsProperty(vexp, name);
            } else {
                this.checkOrMarkPrivateAccess(vexp, (FieldNode)accessedVariable, this.typeCheckingContext.isTargetOfEnclosingAssignment(vexp));
                ClassNode inferredType = this.getInferredTypeFromTempInfo(vexp, null);
                if (inferredType != null && !ClassHelper.isObjectType(inferredType)) {
                    vexp.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, inferredType);
                } else {
                    this.storeType(vexp, this.getType(vexp));
                }
            }
        } else if (accessedVariable instanceof PropertyNode) {
            Expression leftExpression;
            SetterInfo setterInfo;
            BinaryExpression enclosingBinaryExpression;
            if (this.tryVariableExpressionAsProperty(vexp, name) && (enclosingBinaryExpression = this.typeCheckingContext.getEnclosingBinaryExpression()) != null && (setterInfo = StaticTypeCheckingVisitor.removeSetterInfo(leftExpression = enclosingBinaryExpression.getLeftExpression())) != null) {
                Expression rightExpression = enclosingBinaryExpression.getRightExpression();
                this.ensureValidSetter(vexp, leftExpression, rightExpression, setterInfo);
            }
        } else if (accessedVariable != null) {
            VariableExpression localVariable;
            if (accessedVariable instanceof Parameter) {
                Parameter prm = (Parameter)accessedVariable;
                localVariable = new ParameterVariableExpression(prm);
            } else {
                localVariable = (VariableExpression)accessedVariable;
            }
            ClassNode inferredType = (ClassNode)localVariable.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            inferredType = this.getInferredTypeFromTempInfo(localVariable, inferredType);
            if (inferredType != null && !ClassHelper.isObjectType(inferredType) && !inferredType.equals(accessedVariable.getType())) {
                vexp.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, inferredType);
            }
        }
    }

    private boolean storeTypeForSuper(VariableExpression vexp) {
        if (vexp == VariableExpression.SUPER_EXPRESSION) {
            return true;
        }
        if (!vexp.isSuperExpression()) {
            return false;
        }
        this.storeType(vexp, this.makeSuper());
        return true;
    }

    private boolean storeTypeForThis(VariableExpression vexp) {
        if (vexp == VariableExpression.THIS_EXPRESSION) {
            return true;
        }
        if (!vexp.isThisExpression()) {
            return false;
        }
        this.storeType(vexp, !ClassHelper.isObjectType(vexp.getType()) ? vexp.getType() : this.makeThis());
        return true;
    }

    private boolean tryVariableExpressionAsProperty(VariableExpression vexp, String dynName) {
        PropertyExpression pexp = GeneralUtils.thisPropX(true, dynName);
        if (this.existsProperty(pexp, !this.typeCheckingContext.isTargetOfEnclosingAssignment(vexp))) {
            vexp.copyNodeMetaData(pexp.getObjectExpression());
            for (Object key : new Object[]{StaticTypesMarker.IMPLICIT_RECEIVER, StaticTypesMarker.READONLY_PROPERTY, StaticTypesMarker.PV_FIELDS_ACCESS, StaticTypesMarker.PV_FIELDS_MUTATION, StaticTypesMarker.DECLARATION_INFERRED_TYPE, StaticTypesMarker.DIRECT_METHOD_CALL_TARGET}) {
                Object val = pexp.getNodeMetaData(key);
                if (val == null) continue;
                vexp.putNodeMetaData(key, val);
            }
            vexp.removeNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            ClassNode type = (ClassNode)pexp.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            this.storeType(vexp, Optional.ofNullable(type).orElseGet(pexp::getType));
            String receiver = (String)vexp.getNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER);
            Boolean dynamic = (Boolean)pexp.getNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION);
            if ((receiver != null && !receiver.endsWith("owner") || Boolean.TRUE.equals(dynamic)) && !(vexp.getAccessedVariable() instanceof DynamicVariable)) {
                vexp.setAccessedVariable(new DynamicVariable(dynName, false));
            }
            return true;
        }
        return false;
    }

    @Override
    public void visitPropertyExpression(PropertyExpression expression) {
        if (this.existsProperty(expression, !this.typeCheckingContext.isTargetOfEnclosingAssignment(expression))) {
            return;
        }
        if (!this.extension.handleUnresolvedProperty(expression)) {
            Expression objectExpression = expression.getObjectExpression();
            this.addStaticTypeError("No such property: " + expression.getPropertyAsString() + " for class: " + StaticTypeCheckingSupport.prettyPrintTypeName(this.findCurrentInstanceOfClass(objectExpression, this.getType(objectExpression))), expression);
        }
    }

    @Override
    public void visitAttributeExpression(AttributeExpression expression) {
        if (this.existsProperty(expression, true)) {
            return;
        }
        if (!this.extension.handleUnresolvedAttribute(expression)) {
            Expression objectExpression = expression.getObjectExpression();
            this.addStaticTypeError("No such attribute: " + expression.getPropertyAsString() + " for class: " + StaticTypeCheckingSupport.prettyPrintTypeName(this.findCurrentInstanceOfClass(objectExpression, this.getType(objectExpression))), expression);
        }
    }

    @Override
    public void visitRangeExpression(RangeExpression expression) {
        super.visitRangeExpression(expression);
        ClassNode fromType = ClassHelper.getWrapper(this.getType(expression.getFrom()));
        ClassNode toType = ClassHelper.getWrapper(this.getType(expression.getTo()));
        if (ClassHelper.isWrapperInteger(fromType) && ClassHelper.isWrapperInteger(toType)) {
            this.storeType(expression, ClassHelper.make(IntRange.class));
        } else {
            ClassNode rangeType = ClassHelper.RANGE_TYPE.getPlainNodeReference();
            rangeType.setGenericsTypes(new GenericsType[]{new GenericsType(WideningCategories.lowestUpperBound(fromType, toType))});
            this.storeType(expression, rangeType);
        }
    }

    @Override
    public void visitNotExpression(NotExpression expression) {
        this.typeCheckingContext.pushTemporaryTypeInfo();
        super.visitNotExpression(expression);
        this.typeCheckingContext.popTemporaryTypeInfo();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        BinaryExpression enclosingBinaryExpression = this.typeCheckingContext.getEnclosingBinaryExpression();
        this.typeCheckingContext.pushEnclosingBinaryExpression(expression);
        try {
            boolean isEmptyDeclaration;
            ClassNode resultType;
            ClassNode rType;
            int op = expression.getOperation().getType();
            Expression leftExpression = expression.getLeftExpression();
            Expression rightExpression = expression.getRightExpression();
            leftExpression.visit(this);
            SetterInfo setterInfo = StaticTypeCheckingVisitor.removeSetterInfo(leftExpression);
            ClassNode lType = null;
            if (setterInfo != null) {
                if (this.ensureValidSetter(expression, leftExpression, rightExpression, setterInfo)) {
                    return;
                }
                lType = this.getType(leftExpression);
            } else {
                if (op != 100 && op != 217) {
                    lType = this.getType(leftExpression);
                } else {
                    lType = this.getOriginalDeclarationType(leftExpression);
                    this.applyTargetType(lType, rightExpression);
                }
                rightExpression.visit(this);
            }
            ClassNode classNode = rType = StaticTypeCheckingVisitor.isNullConstant(rightExpression) && !ClassHelper.isPrimitiveType(lType) ? StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE : this.getInferredTypeFromTempInfo(rightExpression, this.getType(rightExpression));
            if (op == 573 || op == 129) {
                BinaryExpression reverseExpression = GeneralUtils.binX(rightExpression, expression.getOperation(), leftExpression);
                resultType = this.getResultType(rType, op, lType, reverseExpression);
                if (resultType == null) {
                    resultType = ClassHelper.boolean_TYPE;
                }
                this.storeTargetMethod(expression, (MethodNode)reverseExpression.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET));
            } else {
                resultType = this.getResultType(lType, op, rType, expression);
                if (op == 217) {
                    ElvisOperatorExpression fullExpression = new ElvisOperatorExpression(leftExpression, rightExpression);
                    fullExpression.setSourcePosition(expression);
                    ((ASTNode)fullExpression).visit(this);
                    resultType = this.getType(fullExpression);
                }
            }
            if (resultType == null) {
                resultType = lType;
            }
            if (StaticTypeCheckingSupport.isArrayOp(op)) {
                if (leftExpression instanceof VariableExpression && leftExpression.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE) == null) {
                    leftExpression.removeNodeMetaData((Object)StaticTypesMarker.INFERRED_RETURN_TYPE);
                    this.storeType(leftExpression, lType);
                }
                if (!lType.isArray() && enclosingBinaryExpression != null && enclosingBinaryExpression.getLeftExpression() == expression && StaticTypeCheckingSupport.isAssignment(enclosingBinaryExpression.getOperation().getType())) {
                    Expression enclosingExpressionRHS = enclosingBinaryExpression.getRightExpression();
                    if (!(enclosingExpressionRHS instanceof ClosureExpression)) {
                        enclosingExpressionRHS.visit(this);
                    }
                    ClassNode[] arguments = new ClassNode[]{rType, this.getType(enclosingExpressionRHS)};
                    List<MethodNode> nodes = this.findMethod(lType.redirect(), "putAt", arguments);
                    if (nodes.size() == 1) {
                        this.typeCheckMethodsWithGenericsOrFail(lType, arguments, nodes.get(0), enclosingExpressionRHS);
                    } else if (nodes.isEmpty()) {
                        this.addNoMatchingMethodError(lType, "putAt", arguments, enclosingBinaryExpression);
                    }
                }
            }
            boolean bl = isEmptyDeclaration = expression instanceof DeclarationExpression && (rightExpression instanceof EmptyExpression || rType == StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE);
            if (!isEmptyDeclaration && StaticTypeCheckingSupport.isAssignment(op)) {
                if (rightExpression instanceof ConstructorCallExpression) {
                    this.inferDiamondType((ConstructorCallExpression)rightExpression, lType);
                }
                if (lType.isUsingGenerics() && StaticTypeCheckingSupport.missesGenericsTypes(resultType) && !resultType.isGenericsPlaceHolder()) {
                    if (lType.equals(resultType)) {
                        if (!lType.isGenericsPlaceHolder()) {
                            resultType = lType;
                        }
                    } else {
                        HashMap<GenericsType.GenericsTypeName, GenericsType> gt = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
                        StaticTypeCheckingSupport.extractGenericsConnections(gt, resultType, resultType.redirect());
                        ClassNode sc = resultType;
                        while ((sc = ClassHelper.getNextSuperClass(sc, lType)) != null && !sc.equals(lType)) {
                        }
                        StaticTypeCheckingSupport.extractGenericsConnections(gt, lType, sc);
                        resultType = StaticTypeCheckingSupport.applyGenericsContext(gt, resultType.redirect());
                    }
                }
                ClassNode originType = this.getOriginalDeclarationType(leftExpression);
                this.typeCheckAssignment(expression, leftExpression, originType, rightExpression, resultType);
                if (!StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(StaticTypeCheckingVisitor.wrapTypeIfNecessary(resultType), StaticTypeCheckingVisitor.wrapTypeIfNecessary(originType))) {
                    resultType = originType;
                } else if (ClassHelper.isPrimitiveType(originType) && resultType.equals(ClassHelper.getWrapper(originType))) {
                    resultType = originType;
                } else {
                    int modifiers = resultType.getModifiers();
                    ClassNode enclosingType = this.typeCheckingContext.getEnclosingClassNode();
                    if (!(Modifier.isPublic(modifiers) || enclosingType.equals(resultType) || StaticTypeCheckingVisitor.getOutermost(enclosingType).equals(StaticTypeCheckingVisitor.getOutermost(resultType)) || !Modifier.isPrivate(modifiers) && Objects.equals(enclosingType.getPackageName(), resultType.getPackageName()))) {
                        resultType = originType;
                    } else if (GenericsUtils.hasUnresolvedGenerics(resultType)) {
                        Map<GenericsType.GenericsTypeName, GenericsType> enclosing = StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(this.typeCheckingContext);
                        resultType = StaticTypeCheckingSupport.fullyResolveType(resultType, Optional.ofNullable(enclosing).orElseGet(Collections::emptyMap));
                    }
                }
                if (leftExpression instanceof VariableExpression && this.typeCheckingContext.ifElseForWhileAssignmentTracker != null) {
                    Variable accessedVariable = ((VariableExpression)leftExpression).getAccessedVariable();
                    if (accessedVariable instanceof Parameter) {
                        accessedVariable = new ParameterVariableExpression((Parameter)accessedVariable);
                    }
                    if (accessedVariable instanceof VariableExpression) {
                        this.recordAssignment((VariableExpression)accessedVariable, resultType);
                    }
                }
                this.storeType(leftExpression, resultType);
                if (leftExpression instanceof VariableExpression) {
                    Variable targetVariable;
                    if (rightExpression instanceof ClosureExpression) {
                        leftExpression.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, ((ClosureExpression)rightExpression).getParameters());
                    } else if (rightExpression instanceof VariableExpression && ((VariableExpression)rightExpression).getAccessedVariable() instanceof Expression && ((Expression)((Object)((VariableExpression)rightExpression).getAccessedVariable())).getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS) != null && (targetVariable = StaticTypeCheckingSupport.findTargetVariable((VariableExpression)leftExpression)) instanceof ASTNode) {
                        ((ASTNode)((Object)targetVariable)).putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, ((Expression)((Object)((VariableExpression)rightExpression).getAccessedVariable())).getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS));
                    }
                }
            } else if (op == 544) {
                this.pushInstanceOfTypeInfo(leftExpression, rightExpression);
            }
            if (!isEmptyDeclaration) {
                this.storeType(expression, resultType);
            }
            this.validateResourceInARM(expression, resultType);
            if (leftExpression instanceof VariableExpression && ((VariableExpression)leftExpression).isClosureSharedVariable()) {
                this.typeCheckingContext.secondPassExpressions.add(new SecondPassExpression(expression));
            }
        }
        finally {
            this.typeCheckingContext.popEnclosingBinaryExpression();
        }
    }

    private void validateResourceInARM(BinaryExpression expression, ClassNode lType) {
        if (expression instanceof DeclarationExpression && TryCatchStatement.isResource(expression) && !GeneralUtils.isOrImplements(lType, ClassHelper.AUTOCLOSEABLE_TYPE)) {
            this.addError("Resource[" + lType.getName() + "] in ARM should be of type AutoCloseable", expression);
        }
    }

    private void applyTargetType(ClassNode target, Expression source) {
        if (ClassHelper.isFunctionalInterface(target)) {
            if (source instanceof ClosureExpression) {
                this.inferParameterAndReturnTypesOfClosureOnRHS(target, (ClosureExpression)source);
            } else if (source instanceof MethodReferenceExpression) {
                LambdaExpression lambdaExpression = this.constructLambdaExpressionForMethodReference(target);
                this.inferParameterAndReturnTypesOfClosureOnRHS(target, lambdaExpression);
                source.putNodeMetaData((Object)StaticTypesMarker.CONSTRUCTED_LAMBDA_EXPRESSION, lambdaExpression);
                source.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, Arrays.stream(lambdaExpression.getParameters()).map(Parameter::getType).toArray(ClassNode[]::new));
            }
        } else if (StaticTypeCheckingVisitor.isClosureWithType(target) && source instanceof ClosureExpression) {
            GenericsType returnType = target.getGenericsTypes()[0];
            this.storeInferredReturnType(source, StaticTypeCheckingSupport.getCombinedBoundType(returnType));
        }
    }

    private void inferParameterAndReturnTypesOfClosureOnRHS(ClassNode lhsType, ClosureExpression rhsExpression) {
        ClassNode[] samParameterTypes;
        int m;
        Tuple2<ClassNode[], ClassNode> typeInfo = GenericsUtils.parameterizeSAM(lhsType);
        Parameter[] closureParameters = ClosureUtils.getParametersSafe(rhsExpression);
        int n = closureParameters.length;
        if (n == (m = (samParameterTypes = typeInfo.getV1()).length) || 1 == m && ClosureUtils.hasImplicitParameter(rhsExpression)) {
            for (int i = 0; i < n; ++i) {
                Parameter parameter = closureParameters[i];
                if (parameter.isDynamicTyped()) {
                    parameter.setType(samParameterTypes[i]);
                    parameter.setOriginType(samParameterTypes[i]);
                    continue;
                }
                this.checkParamType(parameter, samParameterTypes[i], i == n - 1, rhsExpression instanceof LambdaExpression);
            }
        } else {
            this.addStaticTypeError("Wrong number of parameters for method target " + StaticTypeCheckingSupport.toMethodParametersString(ClassHelper.findSAM(lhsType).getName(), samParameterTypes), rhsExpression);
        }
        this.storeInferredReturnType(rhsExpression, typeInfo.getV2());
    }

    private void checkParamType(Parameter source, ClassNode target, boolean isLast, boolean lambda) {
        if (!StaticTypeCheckingSupport.typeCheckMethodArgumentWithGenerics(source.getOriginType(), target, isLast)) {
            this.addStaticTypeError("Expected type " + StaticTypeCheckingSupport.prettyPrintType(target) + " for " + (lambda ? "lambda" : "closure") + " parameter: " + source.getName(), source);
        }
    }

    private boolean ensureValidSetter(Expression expression, Expression leftExpression, Expression rightExpression, SetterInfo setterInfo) {
        VariableExpression receiver = GeneralUtils.varX("%", setterInfo.receiverType);
        Expression valueExpression = rightExpression;
        if (StaticTypeCheckingVisitor.isCompoundAssignment(expression)) {
            Token op = ((BinaryExpression)expression).getOperation();
            if (op.getType() == 217) {
                valueExpression = GeneralUtils.elvisX(leftExpression, rightExpression);
            } else {
                op = Token.newSymbol(TokenUtil.removeAssignment(op.getType()), op.getStartLine(), op.getStartColumn());
                valueExpression = GeneralUtils.binX(leftExpression, op, rightExpression);
            }
        }
        Function<Expression, MethodNode> setterCall = right -> {
            this.typeCheckingContext.pushEnclosingBinaryExpression(null);
            try {
                MethodCallExpression call = new MethodCallExpression((Expression)receiver, setterInfo.name, (Expression)right);
                call.setImplicitThis(false);
                this.visitMethodCallExpression(call);
                MethodNode methodNode = (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
                return methodNode;
            }
            finally {
                this.typeCheckingContext.popEnclosingBinaryExpression();
            }
        };
        Function<MethodNode, ClassNode> setterType = setter -> {
            ClassNode type = setter.getParameters()[0].getOriginType();
            if (!setter.isStatic() && !(setter instanceof ExtensionMethodNode) && GenericsUtils.hasUnresolvedGenerics(type)) {
                type = StaticTypeCheckingSupport.applyGenericsContext(StaticTypeCheckingVisitor.extractPlaceHolders(setterInfo.receiverType, setter.getDeclaringClass()), type);
            }
            return type;
        };
        MethodNode methodTarget = setterCall.apply(valueExpression);
        if (methodTarget == null && !StaticTypeCheckingVisitor.isCompoundAssignment(expression)) {
            ClassNode rType;
            MethodNode setter2;
            ClassNode lType;
            Iterator<MethodNode> iterator = setterInfo.setters.iterator();
            while (iterator.hasNext() && (!StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(lType = setterType.apply(setter2 = iterator.next()), rType = this.getDeclaredOrInferredType(valueExpression), valueExpression, false) || (methodTarget = setterCall.apply(GeneralUtils.castX(lType, valueExpression))) == null)) {
            }
        }
        if (methodTarget != null) {
            for (MethodNode setter2 : setterInfo.setters) {
                if (setter2 != methodTarget) continue;
                leftExpression.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, methodTarget);
                leftExpression.removeNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
                this.storeType(leftExpression, setterType.apply(methodTarget));
                break;
            }
            return false;
        }
        ClassNode firstSetterType = setterType.apply(setterInfo.setters.get(0));
        this.addAssignmentError(firstSetterType, this.getType(valueExpression), expression);
        return true;
    }

    private static boolean isClosureWithType(ClassNode type) {
        return type.equals(ClassHelper.CLOSURE_TYPE) && Optional.ofNullable(type.getGenericsTypes()).filter(gts -> gts != null && ((GenericsType[])gts).length == 1).isPresent();
    }

    private static boolean isCompoundAssignment(Expression exp) {
        if (exp instanceof BinaryExpression) {
            Token op = ((BinaryExpression)exp).getOperation();
            return StaticTypeCheckingSupport.isAssignment(op.getType()) && op.getType() != 100;
        }
        return false;
    }

    protected ClassNode getOriginalDeclarationType(Expression lhs) {
        if (lhs instanceof VariableExpression) {
            Variable var = StaticTypeCheckingSupport.findTargetVariable((VariableExpression)lhs);
            if (!(var instanceof DynamicVariable) && !(var instanceof PropertyNode)) {
                return var.getOriginType();
            }
        } else if (lhs instanceof FieldExpression) {
            return ((FieldExpression)lhs).getField().getOriginType();
        }
        return this.getType(lhs);
    }

    protected void inferDiamondType(ConstructorCallExpression cce, ClassNode lType) {
        ClassNode cceType = cce.getType();
        ClassNode inferredType = lType;
        if (cceType.getGenericsTypes() != null && cceType.getGenericsTypes().length == 0) {
            ArgumentListExpression argumentList = InvocationWriter.makeArgumentList(cce.getArguments());
            ConstructorNode constructor = (ConstructorNode)cce.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
            if (!argumentList.getExpressions().isEmpty() && constructor != null) {
                ClassNode type = GenericsUtils.parameterizeType(cceType, cceType);
                type = this.inferReturnTypeGenerics(type, constructor, argumentList);
                if (lType.getGenericsTypes() != null && (type.toString(false).indexOf(35) > 0 || StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(lType, type, cce) && !GenericsUtils.buildWildcardType(lType).isCompatibleWith(type))) {
                    ClassNode pType = GenericsUtils.parameterizeType(lType, type);
                    GenericsType[] lhs = pType.getGenericsTypes();
                    GenericsType[] rhs = type.getGenericsTypes();
                    if (lhs == null || rhs == null || lhs.length != rhs.length) {
                        throw new GroovyBugError("Parameterization failed: " + StaticTypeCheckingSupport.prettyPrintType(pType) + " ~ " + StaticTypeCheckingSupport.prettyPrintType(type));
                    }
                    if (IntStream.range(0, lhs.length).allMatch(i -> GenericsUtils.buildWildcardType(StaticTypeCheckingSupport.getCombinedBoundType(lhs[i])).isCompatibleWith(rhs[i].getType()))) {
                        type = pType;
                    }
                }
                inferredType = type;
            }
            if (inferredType.isGenericsPlaceHolder()) {
                inferredType = StaticTypeCheckingSupport.getCombinedBoundType(inferredType.getGenericsTypes()[0]);
            }
            this.adjustGenerics(inferredType, cceType);
            this.storeType(cce, cceType);
        }
    }

    private void adjustGenerics(ClassNode source, ClassNode target) {
        GenericsType[] genericsTypes = source.getGenericsTypes();
        if (genericsTypes == null) {
            genericsTypes = (GenericsType[])target.redirect().getGenericsTypes().clone();
            int n = genericsTypes.length;
            for (int i = 0; i < n; ++i) {
                GenericsType gt = genericsTypes[i];
                ClassNode cn = gt.getUpperBounds() != null ? gt.getUpperBounds()[0] : gt.getType().redirect();
                genericsTypes[i] = cn.getPlainNodeReference().asGenericsType();
            }
        } else {
            genericsTypes = (GenericsType[])genericsTypes.clone();
            int n = genericsTypes.length;
            for (int i = 0; i < n; ++i) {
                GenericsType gt = genericsTypes[i];
                genericsTypes[i] = new GenericsType(gt.getType(), gt.getUpperBounds(), gt.getLowerBound());
                genericsTypes[i].setWildcard(gt.isWildcard());
            }
        }
        target.setGenericsTypes(genericsTypes);
    }

    protected void pushInstanceOfTypeInfo(Expression objectOfInstanceOf, Expression typeExpression) {
        List potentialTypes = this.typeCheckingContext.temporaryIfBranchTypeInformation.peek().computeIfAbsent(this.extractTemporaryTypeInfoKey(objectOfInstanceOf), key -> new LinkedList());
        potentialTypes.add(typeExpression.getType());
    }

    private boolean typeCheckMultipleAssignmentAndContinue(Expression leftExpression, Expression rightExpression) {
        int i;
        block10: {
            block9: {
                if (rightExpression instanceof VariableExpression || rightExpression instanceof PropertyExpression) break block9;
                if (!(rightExpression instanceof MethodCall)) break block10;
            }
            ClassNode inferredType = Optional.ofNullable(this.getType(rightExpression)).orElseGet(rightExpression::getType);
            GenericsType[] genericsTypes = inferredType.getGenericsTypes();
            ListExpression listExpression = new ListExpression();
            listExpression.setSourcePosition(rightExpression);
            int n = TUPLE_TYPES.indexOf(inferredType);
            for (i = 0; i < n; ++i) {
                ClassNode type = genericsTypes != null ? genericsTypes[i].getType() : ClassHelper.OBJECT_TYPE;
                listExpression.addExpression(GeneralUtils.varX("v" + (i + 1), type));
            }
            if (!listExpression.getExpressions().isEmpty()) {
                rightExpression = listExpression;
            }
        }
        if (!(rightExpression instanceof ListExpression)) {
            this.addStaticTypeError("Multiple assignments without list or tuple on the right-hand side are unsupported in static type checking mode", rightExpression);
            return false;
        }
        TupleExpression tuple = (TupleExpression)leftExpression;
        ListExpression values = (ListExpression)rightExpression;
        List<Expression> tupleExpressions = tuple.getExpressions();
        List<Expression> valueExpressions = values.getExpressions();
        if (tupleExpressions.size() > valueExpressions.size()) {
            this.addStaticTypeError("Incorrect number of values. Expected:" + tupleExpressions.size() + " Was:" + valueExpressions.size(), values);
            return false;
        }
        int n = tupleExpressions.size();
        for (i = 0; i < n; ++i) {
            ClassNode targetType;
            ClassNode valueType = this.getType(valueExpressions.get(i));
            if (!StaticTypeCheckingSupport.isAssignableTo(valueType, targetType = this.getType(tupleExpressions.get(i)))) {
                this.addStaticTypeError("Cannot assign value of type " + StaticTypeCheckingSupport.prettyPrintType(valueType) + " to variable of type " + StaticTypeCheckingSupport.prettyPrintType(targetType), rightExpression);
                return false;
            }
            this.storeType(tupleExpressions.get(i), valueType);
        }
        return true;
    }

    private ClassNode adjustTypeForSpreading(ClassNode rightExpressionType, Expression leftExpression) {
        if (leftExpression instanceof PropertyExpression && ((PropertyExpression)leftExpression).isSpreadSafe()) {
            return this.extension.buildListType(rightExpressionType);
        }
        return rightExpressionType;
    }

    private boolean addedReadOnlyPropertyError(Expression expr) {
        if (expr.getNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY) == null) {
            return false;
        }
        String name = expr instanceof VariableExpression ? ((VariableExpression)expr).getName() : ((PropertyExpression)expr).getPropertyAsString();
        this.addStaticTypeError("Cannot set read-only property: " + name, expr);
        return true;
    }

    private void addPrecisionErrors(ClassNode leftRedirect, ClassNode lhsType, ClassNode rhsType, Expression rightExpression) {
        ClassNode rightComponentType;
        ClassNode leftComponentType;
        if (ClassHelper.isNumberType(leftRedirect)) {
            if (ClassHelper.isNumberType(rhsType) && StaticTypeCheckingSupport.checkPossibleLossOfPrecision(leftRedirect, rhsType, rightExpression)) {
                this.addStaticTypeError("Possible loss of precision from " + StaticTypeCheckingSupport.prettyPrintType(rhsType) + " to " + StaticTypeCheckingSupport.prettyPrintType(lhsType), rightExpression);
            }
            return;
        }
        if (!leftRedirect.isArray()) {
            return;
        }
        if (rightExpression instanceof ListExpression) {
            ClassNode leftComponentType2 = leftRedirect.getComponentType();
            for (Expression expression : ((ListExpression)rightExpression).getExpressions()) {
                ClassNode rightComponentType2 = this.getType(expression);
                if (StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(leftComponentType2, rightComponentType2) || StaticTypeCheckingVisitor.isNullConstant(expression) && !ClassHelper.isPrimitiveType(leftComponentType2)) continue;
                this.addStaticTypeError("Cannot assign value of type " + StaticTypeCheckingSupport.prettyPrintType(rightComponentType2) + " into array of type " + StaticTypeCheckingSupport.prettyPrintType(lhsType), rightExpression);
            }
        } else if (rhsType.redirect().isArray() && !StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(leftComponentType = leftRedirect.getComponentType(), rightComponentType = rhsType.redirect().getComponentType())) {
            this.addStaticTypeError("Cannot assign value of type " + StaticTypeCheckingSupport.prettyPrintType(rightComponentType) + " into array of type " + StaticTypeCheckingSupport.prettyPrintType(lhsType), rightExpression);
        }
    }

    private void addListAssignmentConstructorErrors(ClassNode leftRedirect, ClassNode leftExpressionType, ClassNode inferredRightExpressionType, Expression rightExpression, Expression assignmentExpression) {
        if (StaticTypeCheckingSupport.isWildcardLeftHandSide(leftRedirect) && !ClassHelper.isClassType(leftRedirect)) {
            return;
        }
        if (!(StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(ClassHelper.LIST_TYPE, leftRedirect) || leftRedirect.isAbstract() && !leftRedirect.isArray() || StaticTypeCheckingSupport.ArrayList_TYPE.isDerivedFrom(leftRedirect) || StaticTypeCheckingSupport.LinkedHashSet_TYPE.isDerivedFrom(leftRedirect))) {
            ClassNode[] types = this.getArgumentTypes(GeneralUtils.args(((ListExpression)rightExpression).getExpressions()));
            MethodNode methodNode = this.checkGroovyStyleConstructor(leftRedirect, types, assignmentExpression);
            if (methodNode != null) {
                rightExpression.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, methodNode);
            }
        } else if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(inferredRightExpressionType, ClassHelper.LIST_TYPE) && !StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(inferredRightExpressionType, leftRedirect) && !this.extension.handleIncompatibleAssignment(leftExpressionType, inferredRightExpressionType, assignmentExpression)) {
            this.addAssignmentError(leftExpressionType, inferredRightExpressionType, assignmentExpression);
        }
    }

    private void addMapAssignmentConstructorErrors(ClassNode leftRedirect, Expression leftExpression, Expression rightExpression) {
        if (leftExpression instanceof VariableExpression && ((VariableExpression)leftExpression).isDynamicTyped() || StaticTypeCheckingSupport.isWildcardLeftHandSide(leftRedirect) && !ClassHelper.isClassType(leftRedirect) || StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(leftRedirect, ClassHelper.MAP_TYPE)) {
            return;
        }
        ClassNode[] argTypes = this.getArgumentTypes(GeneralUtils.args(rightExpression));
        this.checkGroovyStyleConstructor(leftRedirect, argTypes, rightExpression);
        MapExpression mapExpression = (MapExpression)rightExpression;
        this.checkGroovyConstructorMap(leftExpression, leftRedirect, mapExpression);
    }

    private void checkTypeGenerics(ClassNode leftExpressionType, ClassNode wrappedRHS, Expression rightExpression) {
        if (!(!leftExpressionType.isUsingGenerics() || StaticTypeCheckingVisitor.isNullConstant(rightExpression) || rightExpression instanceof ClosureExpression || StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE.equals(wrappedRHS) || StaticTypeCheckingSupport.missesGenericsTypes(wrappedRHS) || GenericsUtils.buildWildcardType(leftExpressionType).isCompatibleWith(wrappedRHS))) {
            this.addStaticTypeError("Incompatible generic argument types. Cannot assign " + StaticTypeCheckingSupport.prettyPrintType(wrappedRHS) + " to: " + StaticTypeCheckingSupport.prettyPrintType(leftExpressionType), rightExpression);
        }
    }

    private boolean hasGStringStringError(ClassNode leftExpressionType, ClassNode wrappedRHS, Expression rightExpression) {
        if (StaticTypeCheckingSupport.isParameterizedWithString(leftExpressionType) && StaticTypeCheckingSupport.isParameterizedWithGStringOrGStringString(wrappedRHS)) {
            this.addStaticTypeError("You are trying to use a GString in place of a String in a type which explicitly declares accepting String. Make sure to call toString() on all GString values.", rightExpression);
            return true;
        }
        return false;
    }

    private static boolean isConstructorAbbreviation(ClassNode leftType, Expression rightExpression) {
        if (rightExpression instanceof ListExpression) {
            return !StaticTypeCheckingSupport.ArrayList_TYPE.isDerivedFrom(leftType) && !StaticTypeCheckingSupport.ArrayList_TYPE.implementsInterface(leftType) && !StaticTypeCheckingSupport.LinkedHashSet_TYPE.isDerivedFrom(leftType) && !StaticTypeCheckingSupport.LinkedHashSet_TYPE.implementsInterface(leftType);
        }
        if (rightExpression instanceof MapExpression) {
            return !StaticTypeCheckingSupport.LinkedHashMap_TYPE.isDerivedFrom(leftType) && !StaticTypeCheckingSupport.LinkedHashMap_TYPE.implementsInterface(leftType);
        }
        return false;
    }

    protected void typeCheckAssignment(BinaryExpression assignmentExpression, Expression leftExpression, ClassNode leftExpressionType, Expression rightExpression, ClassNode rightExpressionType) {
        if (leftExpression instanceof TupleExpression && !this.typeCheckMultipleAssignmentAndContinue(leftExpression, rightExpression)) {
            return;
        }
        if (this.addedReadOnlyPropertyError(leftExpression)) {
            return;
        }
        ClassNode rTypeWrapped = this.adjustTypeForSpreading(rightExpressionType, leftExpression);
        if (!StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(leftExpressionType, rTypeWrapped, rightExpression)) {
            if (!this.extension.handleIncompatibleAssignment(leftExpressionType, rightExpressionType, assignmentExpression)) {
                this.addAssignmentError(leftExpressionType, rightExpressionType, rightExpression);
            }
        } else {
            ClassNode lTypeRedirect = leftExpressionType.redirect();
            this.addPrecisionErrors(lTypeRedirect, leftExpressionType, rightExpressionType, rightExpression);
            if (rightExpression instanceof ListExpression) {
                this.addListAssignmentConstructorErrors(lTypeRedirect, leftExpressionType, rightExpressionType, rightExpression, assignmentExpression);
            } else if (rightExpression instanceof MapExpression) {
                this.addMapAssignmentConstructorErrors(lTypeRedirect, leftExpression, rightExpression);
            }
            if (!this.hasGStringStringError(leftExpressionType, rTypeWrapped, rightExpression) && !StaticTypeCheckingVisitor.isConstructorAbbreviation(leftExpressionType, rightExpression)) {
                this.checkTypeGenerics(leftExpressionType, rTypeWrapped, rightExpression);
            }
        }
    }

    protected void checkGroovyConstructorMap(Expression receiver, ClassNode receiverType, MapExpression mapExpression) {
        this.typeCheckingContext.pushEnclosingBinaryExpression(null);
        for (MapEntryExpression entryExpression : mapExpression.getMapEntryExpressions()) {
            Expression valueExpression;
            ClassNode valueType;
            ClassNode resultType;
            Expression keyExpression = entryExpression.getKeyExpression();
            if (!(keyExpression instanceof ConstantExpression)) {
                this.addStaticTypeError("Dynamic keys in map-style constructors are unsupported in static type checking", keyExpression);
                continue;
            }
            String pName = keyExpression.getText();
            AtomicReference<ClassNode> pType = new AtomicReference<ClassNode>();
            if (!this.existsProperty(GeneralUtils.propX((Expression)GeneralUtils.varX("_", receiverType), pName), false, new PropertyLookupVisitor(pType))) {
                this.addStaticTypeError("No such property: " + pName + " for class: " + StaticTypeCheckingSupport.prettyPrintTypeName(receiverType), receiver);
                continue;
            }
            ClassNode targetType = Optional.ofNullable(receiverType.getSetterMethod(GeneralUtils.getSetterName(pName), false)).map(setter -> setter.getParameters()[0].getType()).orElseGet(pType::get);
            if (StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(targetType, resultType = this.getResultType(targetType, 100, valueType = this.getType(valueExpression = entryExpression.getValueExpression()), StaticTypeCheckingVisitor.assignX(keyExpression, valueExpression, entryExpression)), valueExpression) || this.extension.handleIncompatibleAssignment(targetType, valueType, entryExpression)) continue;
            this.addAssignmentError(targetType, valueType, entryExpression);
        }
        this.typeCheckingContext.popEnclosingBinaryExpression();
    }

    @Deprecated
    protected static boolean hasRHSIncompleteGenericTypeInfo(ClassNode inferredRightExpressionType) {
        boolean replaceType = false;
        GenericsType[] genericsTypes = inferredRightExpressionType.getGenericsTypes();
        if (genericsTypes != null) {
            for (GenericsType genericsType : genericsTypes) {
                if (!genericsType.isPlaceholder()) continue;
                replaceType = true;
                break;
            }
        }
        return replaceType;
    }

    @Deprecated
    protected void checkGroovyStyleConstructor(ClassNode node, ClassNode[] arguments) {
        this.checkGroovyStyleConstructor(node, arguments, this.typeCheckingContext.getEnclosingClassNode());
    }

    protected MethodNode checkGroovyStyleConstructor(ClassNode node, ClassNode[] arguments, ASTNode source) {
        if (ClassHelper.isObjectType(node) || ClassHelper.isDynamicTyped(node)) {
            return null;
        }
        List<ConstructorNode> constructors = node.getDeclaredConstructors();
        if (constructors.isEmpty() && arguments.length == 0) {
            return null;
        }
        List<MethodNode> constructorList = this.findMethod(node, "<init>", arguments);
        if (constructorList.isEmpty()) {
            if (StaticTypeCheckingSupport.isBeingCompiled(node) && arguments.length == 1 && StaticTypeCheckingSupport.LinkedHashMap_TYPE.equals(arguments[0])) {
                ConstructorNode cn = new ConstructorNode(1, new Parameter[]{new Parameter(StaticTypeCheckingSupport.LinkedHashMap_TYPE, "args")}, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE);
                return cn;
            }
            this.addStaticTypeError("No matching constructor found: " + StaticTypeCheckingSupport.prettyPrintTypeName(node) + StaticTypeCheckingSupport.toMethodParametersString("", arguments), source);
            return null;
        }
        if (constructorList.size() > 1) {
            this.addStaticTypeError("Ambiguous constructor call " + StaticTypeCheckingSupport.prettyPrintTypeName(node) + StaticTypeCheckingSupport.toMethodParametersString("", arguments), source);
            return null;
        }
        return constructorList.get(0);
    }

    protected Object extractTemporaryTypeInfoKey(Expression expression) {
        return expression instanceof VariableExpression ? StaticTypeCheckingSupport.findTargetVariable((VariableExpression)expression) : expression.getText();
    }

    protected ClassNode findCurrentInstanceOfClass(Expression expr, ClassNode type) {
        List<ClassNode> nodes;
        if (!this.typeCheckingContext.temporaryIfBranchTypeInformation.isEmpty() && (nodes = this.getTemporaryTypesForExpression(expr)) != null && nodes.size() == 1) {
            return nodes.get(0);
        }
        return type;
    }

    protected boolean existsProperty(PropertyExpression pexp, boolean checkForReadOnly) {
        return this.existsProperty(pexp, checkForReadOnly, null);
    }

    protected boolean existsProperty(PropertyExpression pexp, boolean readMode, ClassCodeVisitorSupport visitor) {
        ClassNode receiverType;
        super.visitPropertyExpression(pexp);
        String propertyName = pexp.getPropertyAsString();
        if (propertyName == null) {
            return false;
        }
        Expression objectExpression = pexp.getObjectExpression();
        ClassNode objectExpressionType = this.getType(objectExpression);
        if (objectExpression instanceof ConstructorCallExpression) {
            ClassNode rawType = objectExpressionType.getPlainNodeReference();
            this.inferDiamondType((ConstructorCallExpression)objectExpression, rawType);
        }
        List<ClassNode> enclosingTypes = this.typeCheckingContext.getEnclosingClassNodes();
        boolean staticOnlyAccess = StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(objectExpressionType);
        if (staticOnlyAccess && "this".equals(propertyName)) {
            ClassNode outer = objectExpressionType.getGenericsTypes()[0].getType();
            ClassNode found = null;
            for (ClassNode enclosingType : enclosingTypes) {
                if (enclosingType.isStaticClass() || !outer.equals(enclosingType.getOuterClass())) continue;
                found = enclosingType;
                break;
            }
            if (found != null) {
                this.storeType(pexp, outer);
                return true;
            }
        }
        boolean foundGetterOrSetter = false;
        String capName = BeanUtils.capitalize(propertyName);
        HashSet<ClassNode[]> handledNodes = new HashSet<ClassNode[]>();
        ArrayList<Receiver<String>> receivers = new ArrayList<Receiver<String>>();
        this.addReceivers(receivers, this.makeOwnerList(objectExpression), pexp.isImplicitThis());
        for (Receiver receiver : receivers) {
            ClassNode[] classNodeArray;
            receiverType = receiver.getType();
            if (receiverType.isArray() && "length".equals(propertyName)) {
                pexp.putNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY, Boolean.TRUE);
                this.storeType(pexp, ClassHelper.int_TYPE);
                if (visitor != null) {
                    FieldNode length = new FieldNode("length", 17, ClassHelper.int_TYPE, receiverType, null);
                    length.setDeclaringClass(receiverType);
                    visitor.visitField(length);
                }
                return true;
            }
            LinkedList<ClassNode> queue = new LinkedList<ClassNode>();
            queue.add(receiverType);
            if (ClassHelper.isPrimitiveType(receiverType)) {
                queue.add(ClassHelper.getWrapper(receiverType));
            }
            while (!queue.isEmpty()) {
                boolean staticOnly;
                ClassNode[] current = (ClassNode[])queue.remove();
                if (!handledNodes.add(current)) continue;
                FieldNode field = current.getDeclaredField(propertyName);
                if (field == null) {
                    if (current.getSuperClass() != null) {
                        queue.addFirst(current.getSuperClass());
                    }
                    Collections.addAll(queue, current.getInterfaces());
                }
                boolean bl = staticOnly = receiver.getData() == null ? staticOnlyAccess : false;
                if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType((ClassNode)current)) {
                    staticOnly = false;
                }
                field = this.allowStaticAccessToMember(field, staticOnly);
                if (pexp instanceof AttributeExpression) {
                    if (field == null || !this.storeField(field, pexp, receiverType, visitor, (String)receiver.getData(), !readMode)) continue;
                    return true;
                }
                if (field != null && enclosingTypes.contains(current) && this.storeField(field, pexp, receiverType, visitor, (String)receiver.getData(), !readMode)) {
                    return true;
                }
                MethodNode getter = this.findGetter((ClassNode)current, "is" + capName, pexp.isImplicitThis());
                if ((getter = this.allowStaticAccessToMember(getter, staticOnly)) == null) {
                    getter = this.findGetter((ClassNode)current, GeneralUtils.getGetterName(propertyName), pexp.isImplicitThis());
                }
                getter = this.allowStaticAccessToMember(getter, staticOnly);
                List<MethodNode> setters = StaticTypeCheckingSupport.findSetters((ClassNode)current, GeneralUtils.getSetterName(propertyName), false);
                setters = this.allowStaticAccessToMember(setters, staticOnly);
                if (visitor != null && getter != null) {
                    visitor.visitMethod(getter);
                }
                Object property = current.getProperty(propertyName);
                if ((property = (PropertyNode)this.allowStaticAccessToMember(property, staticOnly)) == null || !enclosingTypes.contains(receiverType)) {
                    if (readMode) {
                        if (getter != null) {
                            ClassNode returnType = this.inferReturnTypeGenerics((ClassNode)current, getter, ArgumentListExpression.EMPTY_ARGUMENTS);
                            this.storeInferredTypeForPropertyExpression(pexp, returnType);
                            this.storeTargetMethod(pexp, getter);
                            String delegationData = (String)receiver.getData();
                            if (delegationData != null) {
                                pexp.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, delegationData);
                            }
                            return true;
                        }
                    } else {
                        if (!setters.isEmpty()) {
                            String delegationData;
                            if (visitor != null) {
                                if (field != null) {
                                    visitor.visitField(field);
                                } else {
                                    for (MethodNode setter : setters) {
                                        FieldNode virtual = new FieldNode(propertyName, 0, setter.getParameters()[0].getOriginType(), (ClassNode)current, null);
                                        virtual.setDeclaringClass(setter.getDeclaringClass());
                                        visitor.visitField(virtual);
                                    }
                                }
                            }
                            SetterInfo info = new SetterInfo((ClassNode)current, GeneralUtils.getSetterName(propertyName), setters);
                            BinaryExpression enclosingBinaryExpression = this.typeCheckingContext.getEnclosingBinaryExpression();
                            if (enclosingBinaryExpression != null) {
                                StaticTypeCheckingVisitor.putSetterInfo(enclosingBinaryExpression.getLeftExpression(), info);
                            }
                            if ((delegationData = (String)receiver.getData()) != null) {
                                pexp.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, delegationData);
                            }
                            pexp.removeNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY);
                            return true;
                        }
                        if (getter != null && field == null) {
                            pexp.putNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY, Boolean.TRUE);
                        }
                    }
                }
                if (property != null && this.storeProperty((PropertyNode)property, pexp, receiverType, visitor, (String)receiver.getData(), !readMode)) {
                    return true;
                }
                if (field != null && this.storeField(field, pexp, receiverType, visitor, (String)receiver.getData(), !readMode)) {
                    return true;
                }
                foundGetterOrSetter = foundGetterOrSetter || !setters.isEmpty() || getter != null;
            }
            if (ClassHelper.isPrimitiveType(receiverType)) {
                ClassNode[] classNodeArray2 = new ClassNode[2];
                classNodeArray2[0] = receiverType;
                classNodeArray = classNodeArray2;
                classNodeArray2[1] = ClassHelper.getWrapper(receiverType);
            } else {
                ClassNode[] classNodeArray3 = new ClassNode[1];
                classNodeArray = classNodeArray3;
                classNodeArray3[0] = receiverType;
            }
            for (ClassNode dgmReceiver : classNodeArray) {
                List<MethodNode> bestMethods;
                List<MethodNode> methods = StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(this.getSourceUnit().getClassLoader(), dgmReceiver, "get" + capName, ClassNode.EMPTY_ARRAY);
                for (MethodNode method2 : StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(this.getSourceUnit().getClassLoader(), dgmReceiver, "is" + capName, ClassNode.EMPTY_ARRAY)) {
                    if (!ClassHelper.isPrimitiveBoolean(method2.getReturnType())) continue;
                    methods.add(method2);
                }
                if (StaticTypeCheckingSupport.isUsingGenericsOrIsArrayUsingGenerics(dgmReceiver)) {
                    methods.removeIf(method -> !StaticTypeCheckingSupport.typeCheckMethodsWithGenerics(dgmReceiver, ClassNode.EMPTY_ARRAY, method));
                }
                if (methods.isEmpty() || (bestMethods = StaticTypeCheckingSupport.chooseBestMethod(dgmReceiver, methods, ClassNode.EMPTY_ARRAY)).size() != 1) continue;
                MethodNode getter = bestMethods.get(0);
                if (visitor != null) {
                    visitor.visitMethod(getter);
                }
                ClassNode returnType = this.inferReturnTypeGenerics(dgmReceiver, getter, ArgumentListExpression.EMPTY_ARGUMENTS);
                this.storeInferredTypeForPropertyExpression(pexp, returnType);
                if (readMode) {
                    this.storeTargetMethod(pexp, getter);
                }
                return true;
            }
            if (receiverType.isArray() || ClassHelper.isPrimitiveType(ClassHelper.getUnwrapper(receiverType)) || !pexp.isImplicitThis() || this.typeCheckingContext.getEnclosingClosure() == null) continue;
            MethodNode mopMethod = readMode ? receiverType.getMethod("get", new Parameter[]{new Parameter(ClassHelper.STRING_TYPE, "name")}) : receiverType.getMethod("set", new Parameter[]{new Parameter(ClassHelper.STRING_TYPE, "name"), new Parameter(ClassHelper.OBJECT_TYPE, "value")});
            if (mopMethod == null) {
                mopMethod = receiverType.getMethod("propertyMissing", new Parameter[]{new Parameter(ClassHelper.STRING_TYPE, "propertyName")});
            }
            if (mopMethod == null || mopMethod.isStatic() || mopMethod.isSynthetic()) continue;
            pexp.putNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION, Boolean.TRUE);
            pexp.removeNodeMetaData((Object)StaticTypesMarker.DECLARATION_INFERRED_TYPE);
            pexp.removeNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            visitor.visitMethod(mopMethod);
            return true;
        }
        for (Receiver receiver : receivers) {
            receiverType = receiver.getType();
            ClassNode propertyType = this.getTypeForMapPropertyExpression(receiverType, pexp);
            if (propertyType == null) {
                propertyType = this.getTypeForListPropertyExpression(receiverType, pexp);
            }
            if (propertyType == null) {
                propertyType = this.getTypeForSpreadExpression(receiverType, pexp);
            }
            if (propertyType == null) continue;
            if (visitor != null) {
                PropertyNode node = new PropertyNode(propertyName, 1, propertyType, receiver.getType(), null, null, null);
                node.setDeclaringClass(receiver.getType());
                visitor.visitProperty(node);
            }
            this.storeType(pexp, propertyType);
            String delegationData = (String)receiver.getData();
            if (delegationData != null) {
                pexp.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, delegationData);
            }
            return true;
        }
        return foundGetterOrSetter;
    }

    private static boolean hasAccessToField(ClassNode accessor, FieldNode field) {
        if (field.isPublic() || accessor.equals(field.getDeclaringClass())) {
            return true;
        }
        if (field.isProtected()) {
            return accessor.isDerivedFrom(field.getDeclaringClass());
        }
        return !field.isPrivate() && Objects.equals(accessor.getPackageName(), field.getDeclaringClass().getPackageName());
    }

    private MethodNode findGetter(ClassNode current, String name, boolean searchOuterClasses) {
        MethodNode getterMethod = current.getGetterMethod(name);
        if (getterMethod == null && searchOuterClasses && current.getOuterClass() != null) {
            return this.findGetter(current.getOuterClass(), name, true);
        }
        return getterMethod;
    }

    private ClassNode getTypeForMultiValueExpression(ClassNode compositeType, Expression prop) {
        GenericsType[] gts = compositeType.getGenericsTypes();
        ClassNode itemType = gts != null && gts.length == 1 ? StaticTypeCheckingSupport.getCombinedBoundType(gts[0]) : ClassHelper.OBJECT_TYPE;
        AtomicReference<ClassNode> propertyType = new AtomicReference<ClassNode>();
        if (this.existsProperty(GeneralUtils.propX((Expression)GeneralUtils.varX("{}", itemType), prop), true, new PropertyLookupVisitor(propertyType))) {
            return this.extension.buildListType(propertyType.get());
        }
        return null;
    }

    private ClassNode getTypeForSpreadExpression(ClassNode testClass, PropertyExpression pexp) {
        if (pexp.isSpreadSafe()) {
            MethodCallExpression mce = GeneralUtils.callX(GeneralUtils.varX("_", testClass), "iterator");
            mce.setImplicitThis(false);
            mce.visit(this);
            ClassNode iteratorType = this.getType(mce);
            if (GeneralUtils.isOrImplements(iteratorType, ClassHelper.Iterator_TYPE)) {
                return this.getTypeForMultiValueExpression(iteratorType, pexp.getProperty());
            }
        }
        return null;
    }

    private ClassNode getTypeForListPropertyExpression(ClassNode testClass, PropertyExpression pexp) {
        if (GeneralUtils.isOrImplements(testClass, ClassHelper.LIST_TYPE)) {
            ClassNode listType = testClass.equals(ClassHelper.LIST_TYPE) ? testClass : GenericsUtils.parameterizeType(testClass, ClassHelper.LIST_TYPE);
            return this.getTypeForMultiValueExpression(listType, pexp.getProperty());
        }
        return null;
    }

    private ClassNode getTypeForMapPropertyExpression(ClassNode testClass, PropertyExpression pexp) {
        if (GeneralUtils.isOrImplements(testClass, ClassHelper.MAP_TYPE)) {
            ClassNode mapType = testClass.equals(ClassHelper.MAP_TYPE) ? testClass : GenericsUtils.parameterizeType(testClass, ClassHelper.MAP_TYPE);
            GenericsType[] gts = mapType.getGenericsTypes();
            if (gts == null || gts.length != 2) {
                gts = new GenericsType[]{ClassHelper.OBJECT_TYPE.asGenericsType(), ClassHelper.OBJECT_TYPE.asGenericsType()};
            }
            if (!pexp.isSpreadSafe()) {
                return StaticTypeCheckingSupport.getCombinedBoundType(gts[1]);
            }
            switch (pexp.getPropertyAsString()) {
                case "key": {
                    pexp.putNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY, Boolean.TRUE);
                    return GenericsUtils.makeClassSafe0(ClassHelper.LIST_TYPE, gts[0]);
                }
                case "value": {
                    GenericsType v = gts[1];
                    if (!v.isWildcard() && !Modifier.isFinal(v.getType().getModifiers()) && this.typeCheckingContext.isTargetOfEnclosingAssignment(pexp)) {
                        v = GenericsUtils.buildWildcardType(v.getType());
                    }
                    return GenericsUtils.makeClassSafe0(ClassHelper.LIST_TYPE, v);
                }
            }
            this.addStaticTypeError("Spread operator on map only allows one of [key,value]", pexp);
        }
        return null;
    }

    private <T> T allowStaticAccessToMember(T member, boolean staticOnly) {
        if (member == null || !staticOnly) {
            return member;
        }
        if (member instanceof List) {
            List list = ((List)member).stream().map(m -> this.allowStaticAccessToMember(m, true)).filter(Objects::nonNull).collect(Collectors.toList());
            return (T)list;
        }
        boolean isStatic = member instanceof FieldNode ? ((FieldNode)member).isStatic() : (member instanceof MethodNode ? ((MethodNode)member).isStatic() : ((PropertyNode)member).isStatic());
        return (T)(isStatic ? member : null);
    }

    private boolean storeField(FieldNode field, PropertyExpression expressionToStoreOn, ClassNode receiver, ClassCodeVisitorSupport visitor, String delegationData, boolean lhsOfAssignment) {
        if (visitor != null) {
            visitor.visitField(field);
        }
        this.checkOrMarkPrivateAccess(expressionToStoreOn, field, lhsOfAssignment);
        boolean accessible = StaticTypeCheckingVisitor.hasAccessToField(StaticTypeCheckingVisitor.isSuperExpression(expressionToStoreOn.getObjectExpression()) ? this.typeCheckingContext.getEnclosingClassNode() : receiver, field);
        if (expressionToStoreOn instanceof AttributeExpression && !accessible) {
            this.addStaticTypeError("The field " + field.getDeclaringClass().getNameWithoutPackage() + "." + field.getName() + " is not accessible", expressionToStoreOn.getProperty());
        }
        this.storeWithResolve(field.getOriginType(), receiver, field.getDeclaringClass(), field.isStatic(), expressionToStoreOn);
        if (delegationData != null) {
            expressionToStoreOn.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, delegationData);
        }
        if (field.isFinal()) {
            MethodNode enclosing = this.typeCheckingContext.getEnclosingMethod();
            if (enclosing == null || !enclosing.getName().endsWith("init>")) {
                expressionToStoreOn.putNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY, Boolean.TRUE);
            }
        } else if (accessible) {
            expressionToStoreOn.removeNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY);
        }
        return true;
    }

    private boolean storeProperty(PropertyNode property, PropertyExpression expression, ClassNode receiver, ClassCodeVisitorSupport visitor, String delegationData, boolean lhsOfAssignment) {
        if (visitor != null) {
            visitor.visitProperty(property);
        }
        ClassNode propertyType = property.getOriginType();
        this.storeWithResolve(propertyType, receiver, property.getDeclaringClass(), property.isStatic(), expression);
        if (delegationData != null) {
            expression.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, delegationData);
        }
        if (Modifier.isFinal(property.getModifiers())) {
            expression.putNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY, Boolean.TRUE);
            if (!lhsOfAssignment) {
                MethodNode implicitGetter = new MethodNode(property.getGetterNameOrDefault(), 1, propertyType, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, null);
                implicitGetter.setDeclaringClass(property.getDeclaringClass());
                this.extension.onMethodSelection(expression, implicitGetter);
            }
        } else {
            expression.removeNodeMetaData((Object)StaticTypesMarker.READONLY_PROPERTY);
            if (lhsOfAssignment) {
                MethodNode implicitSetter = new MethodNode(property.getSetterNameOrDefault(), 1, ClassHelper.VOID_TYPE, new Parameter[]{new Parameter(propertyType, "value")}, ClassNode.EMPTY_ARRAY, null);
                implicitSetter.setDeclaringClass(property.getDeclaringClass());
                this.extension.onMethodSelection(expression, implicitSetter);
            }
        }
        return true;
    }

    private void storeWithResolve(ClassNode type, ClassNode receiver, ClassNode declaringClass, boolean isStatic, Expression expressionToStoreOn) {
        if (!isStatic && GenericsUtils.hasUnresolvedGenerics(type)) {
            type = this.resolveGenericsWithContext(StaticTypeCheckingVisitor.extractPlaceHolders(receiver, declaringClass), type);
        }
        if (expressionToStoreOn instanceof PropertyExpression) {
            this.storeInferredTypeForPropertyExpression((PropertyExpression)expressionToStoreOn, type);
        } else {
            this.storeType(expressionToStoreOn, type);
        }
    }

    private ClassNode resolveGenericsWithContext(Map<GenericsType.GenericsTypeName, GenericsType> resolvedPlaceholders, ClassNode currentType) {
        Map<GenericsType.GenericsTypeName, GenericsType> placeholdersFromContext = StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(this.typeCheckingContext);
        return StaticTypeCheckingSupport.resolveClassNodeGenerics(resolvedPlaceholders, placeholdersFromContext, currentType);
    }

    private void storeInferredTypeForPropertyExpression(PropertyExpression pexp, ClassNode type) {
        if (pexp.isSpreadSafe()) {
            this.storeType(pexp, this.extension.buildListType(type));
        } else {
            this.storeType(pexp, type);
        }
    }

    @Override
    public void visitProperty(PropertyNode node) {
        boolean osc = this.typeCheckingContext.isInStaticContext;
        try {
            this.typeCheckingContext.isInStaticContext = node.isInStaticContext();
            this.currentProperty = node;
            this.visitAnnotations(node);
            this.visitClassCodeContainer(node.getGetterBlock());
            this.visitClassCodeContainer(node.getSetterBlock());
        }
        finally {
            this.currentProperty = null;
            this.typeCheckingContext.isInStaticContext = osc;
        }
    }

    @Override
    public void visitField(FieldNode node) {
        boolean osc = this.typeCheckingContext.isInStaticContext;
        try {
            this.typeCheckingContext.isInStaticContext = node.isInStaticContext();
            this.currentField = node;
            this.visitAnnotations(node);
            this.visitInitialExpression(node.getInitialExpression(), new FieldExpression(node), node);
        }
        finally {
            this.currentField = null;
            this.typeCheckingContext.isInStaticContext = osc;
        }
    }

    private void visitInitialExpression(Expression value, Expression target, ASTNode position) {
        if (value != null) {
            ClassNode lType = target.getType();
            this.applyTargetType(lType, value);
            this.typeCheckingContext.pushEnclosingBinaryExpression(StaticTypeCheckingVisitor.assignX(target, value, position));
            value.visit(this);
            ClassNode rType = this.getType(value);
            if (value instanceof ConstructorCallExpression) {
                this.inferDiamondType((ConstructorCallExpression)value, lType);
            }
            BinaryExpression dummy = this.typeCheckingContext.popEnclosingBinaryExpression();
            this.typeCheckAssignment(dummy, target, lType, value, this.getResultType(lType, 100, rType, dummy));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitForLoop(ForStatement forLoop) {
        HashMap<VariableExpression, ClassNode> varOrigType = new HashMap<VariableExpression, ClassNode>();
        forLoop.getLoopBlock().visit(new VariableExpressionTypeMemoizer(varOrigType));
        Map<VariableExpression, List<ClassNode>> oldTracker = this.pushAssignmentTracking();
        Expression collectionExpression = forLoop.getCollectionExpression();
        if (collectionExpression instanceof ClosureListExpression) {
            super.visitForLoop(forLoop);
        } else {
            this.visitStatement(forLoop);
            collectionExpression.visit(this);
            ClassNode collectionType = this.getType(collectionExpression);
            ClassNode forLoopVariableType = forLoop.getVariableType();
            ClassNode componentType = ClassHelper.isWrapperCharacter(ClassHelper.getWrapper(forLoopVariableType)) && ClassHelper.isStringType(collectionType) ? forLoopVariableType : StaticTypeCheckingVisitor.inferLoopElementType(collectionType);
            if (ClassHelper.getUnwrapper(componentType) == forLoopVariableType) {
                componentType = forLoopVariableType;
            }
            if (!StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(forLoopVariableType, componentType)) {
                this.addStaticTypeError("Cannot loop with element of type " + StaticTypeCheckingSupport.prettyPrintType(forLoopVariableType) + " with collection of type " + StaticTypeCheckingSupport.prettyPrintType(collectionType), forLoop);
            }
            if (!ClassHelper.isDynamicTyped(forLoopVariableType)) {
                componentType = forLoopVariableType;
            }
            this.typeCheckingContext.controlStructureVariables.put(forLoop.getVariable(), componentType);
            try {
                forLoop.getLoopBlock().visit(this);
            }
            finally {
                this.typeCheckingContext.controlStructureVariables.remove(forLoop.getVariable());
            }
        }
        if (this.isSecondPassNeededForControlStructure(varOrigType, oldTracker)) {
            this.visitForLoop(forLoop);
        }
    }

    public static ClassNode inferLoopElementType(ClassNode collectionType) {
        ClassNode componentType = collectionType.getComponentType();
        if (componentType == null) {
            if (GeneralUtils.isOrImplements(collectionType, ITERABLE_TYPE)) {
                ClassNode col = GenericsUtils.parameterizeType(collectionType, ITERABLE_TYPE);
                componentType = StaticTypeCheckingSupport.getCombinedBoundType(col.getGenericsTypes()[0]);
            } else if (GeneralUtils.isOrImplements(collectionType, ClassHelper.MAP_TYPE)) {
                ClassNode col = GenericsUtils.parameterizeType(collectionType, ClassHelper.MAP_TYPE);
                componentType = GenericsUtils.makeClassSafe0(MAP_ENTRY_TYPE, col.getGenericsTypes());
            } else if (GeneralUtils.isOrImplements(collectionType, ClassHelper.STREAM_TYPE)) {
                ClassNode col = GenericsUtils.parameterizeType(collectionType, ClassHelper.STREAM_TYPE);
                componentType = StaticTypeCheckingSupport.getCombinedBoundType(col.getGenericsTypes()[0]);
            } else if (GeneralUtils.isOrImplements(collectionType, ENUMERATION_TYPE)) {
                ClassNode col = GenericsUtils.parameterizeType(collectionType, ENUMERATION_TYPE);
                componentType = StaticTypeCheckingSupport.getCombinedBoundType(col.getGenericsTypes()[0]);
            } else {
                componentType = ClassHelper.isStringType(collectionType) ? ClassHelper.STRING_TYPE : ClassHelper.OBJECT_TYPE;
            }
        }
        return componentType;
    }

    protected boolean isSecondPassNeededForControlStructure(Map<VariableExpression, ClassNode> varOrigType, Map<VariableExpression, List<ClassNode>> oldTracker) {
        for (Map.Entry<VariableExpression, ClassNode> entry : this.popAssignmentTracking(oldTracker).entrySet()) {
            Variable key = StaticTypeCheckingSupport.findTargetVariable(entry.getKey());
            if (!(key instanceof VariableExpression) || !varOrigType.containsKey(key)) continue;
            ClassNode origType = varOrigType.get(key);
            ClassNode newType = entry.getValue();
            if (newType.equals(origType)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void visitWhileLoop(WhileStatement loop) {
        Map<VariableExpression, List<ClassNode>> oldTracker = this.pushAssignmentTracking();
        super.visitWhileLoop(loop);
        this.popAssignmentTracking(oldTracker);
    }

    @Override
    public void visitBitwiseNegationExpression(BitwiseNegationExpression expression) {
        MethodNode mn;
        super.visitBitwiseNegationExpression(expression);
        ClassNode type = this.getType(expression);
        ClassNode typeRe = type.redirect();
        ClassNode resultType = WideningCategories.isBigIntCategory(typeRe) ? type : (ClassHelper.isStringType(typeRe) || ClassHelper.isGStringType(typeRe) ? ClassHelper.PATTERN_TYPE : (typeRe.equals(StaticTypeCheckingSupport.ArrayList_TYPE) ? StaticTypeCheckingSupport.ArrayList_TYPE : (typeRe.equals(ClassHelper.PATTERN_TYPE) ? ClassHelper.PATTERN_TYPE : ((mn = this.findMethodOrFail(expression, type, "bitwiseNegate", new ClassNode[0])) != null ? mn.getReturnType() : ClassHelper.OBJECT_TYPE))));
        this.storeType(expression, resultType);
    }

    @Override
    public void visitUnaryPlusExpression(UnaryPlusExpression expression) {
        super.visitUnaryPlusExpression(expression);
        this.negativeOrPositiveUnary(expression, "positive");
    }

    @Override
    public void visitUnaryMinusExpression(UnaryMinusExpression expression) {
        super.visitUnaryMinusExpression(expression);
        this.negativeOrPositiveUnary(expression, "negative");
    }

    @Override
    public void visitPostfixExpression(PostfixExpression expression) {
        Expression operand = expression.getExpression();
        int operator = expression.getOperation().getType();
        this.visitPrefixOrPostifExpression(expression, operand, operator);
    }

    @Override
    public void visitPrefixExpression(PrefixExpression expression) {
        Expression operand = expression.getExpression();
        int operator = expression.getOperation().getType();
        this.visitPrefixOrPostifExpression(expression, operand, operator);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void visitPrefixOrPostifExpression(Expression origin, Expression operand, int operator) {
        Optional<Token> token = TokenUtil.asAssignment(operator);
        token.ifPresent(value -> this.typeCheckingContext.pushEnclosingBinaryExpression(GeneralUtils.binX(operand, value, GeneralUtils.constX(1))));
        try {
            MethodNode node;
            String name;
            operand.visit(this);
            SetterInfo setterInfo = StaticTypeCheckingVisitor.removeSetterInfo(operand);
            if (setterInfo != null) {
                BinaryExpression rewrite = this.typeCheckingContext.getEnclosingBinaryExpression();
                rewrite.setSourcePosition(origin);
                if (this.ensureValidSetter(rewrite, operand, rewrite.getRightExpression(), setterInfo)) {
                    return;
                }
            }
            ClassNode operandType = this.getType(operand);
            boolean isPostfix = origin instanceof PostfixExpression;
            String string = operator == 250 ? "next" : (name = operator == 260 ? "previous" : null);
            if (name != null && ClassHelper.isNumberType(operandType)) {
                MethodNode node2;
                if (!ClassHelper.isPrimitiveType(operandType) && (node2 = this.findMethodOrFail(GeneralUtils.varX("_dummy_", operandType), operandType, name, new ClassNode[0])) != null) {
                    this.storeTargetMethod(origin, node2);
                    this.storeType(origin, isPostfix ? operandType : StaticTypeCheckingVisitor.getMathWideningClassNode(operandType));
                    return;
                }
                this.storeType(origin, operandType);
                return;
            }
            if (name != null && operandType.isDerivedFrom(ClassHelper.Number_TYPE) && (node = this.findMethodOrFail(operand, operandType, name, new ClassNode[0])) != null) {
                this.storeTargetMethod(origin, node);
                this.storeType(origin, StaticTypeCheckingVisitor.getMathWideningClassNode(operandType));
                return;
            }
            if (name == null) {
                this.addUnsupportedPreOrPostfixExpressionError(origin);
                return;
            }
            node = this.findMethodOrFail(operand, operandType, name, new ClassNode[0]);
            if (node != null) {
                this.storeTargetMethod(origin, node);
                this.storeType(origin, isPostfix ? operandType : this.inferReturnTypeGenerics(operandType, node, ArgumentListExpression.EMPTY_ARGUMENTS));
            }
        }
        finally {
            if (token.isPresent()) {
                this.typeCheckingContext.popEnclosingBinaryExpression();
            }
        }
    }

    private static ClassNode getMathWideningClassNode(ClassNode type) {
        if (ClassHelper.isPrimitiveByte(type) || ClassHelper.isPrimitiveShort(type) || ClassHelper.isPrimitiveInt(type)) {
            return ClassHelper.int_TYPE;
        }
        if (ClassHelper.isWrapperByte(type) || ClassHelper.isWrapperShort(type) || ClassHelper.isWrapperInteger(type)) {
            return ClassHelper.Integer_TYPE;
        }
        if (ClassHelper.isPrimitiveFloat(type)) {
            return ClassHelper.double_TYPE;
        }
        if (ClassHelper.isWrapperFloat(type)) {
            return ClassHelper.Double_TYPE;
        }
        return type;
    }

    private void negativeOrPositiveUnary(Expression expression, String name) {
        MethodNode mn;
        ClassNode type = this.getType(expression);
        ClassNode typeRe = type.redirect();
        ClassNode resultType = WideningCategories.isDoubleCategory(ClassHelper.getUnwrapper(typeRe)) ? type : (typeRe.equals(StaticTypeCheckingSupport.ArrayList_TYPE) ? StaticTypeCheckingSupport.ArrayList_TYPE : ((mn = this.findMethodOrFail(expression, type, name, new ClassNode[0])) != null ? mn.getReturnType() : type));
        this.storeType(expression, resultType);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        this.typeCheckingContext.pushTemporaryTypeInfo();
        super.visitExpressionStatement(statement);
        this.typeCheckingContext.popTemporaryTypeInfo();
    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {
        MethodNode method;
        if (this.typeCheckingContext.getEnclosingClosure() == null && (method = this.typeCheckingContext.getEnclosingMethod()) != null && !method.isVoidMethod() && !method.isDynamicReturnType()) {
            this.applyTargetType(method.getReturnType(), statement.getExpression());
        }
        super.visitReturnStatement(statement);
        this.returnListener.returnStatementAdded(statement);
    }

    protected ClassNode checkReturnType(ReturnStatement statement) {
        Expression expression = statement.getExpression();
        ClassNode type = this.getType(expression);
        TypeCheckingContext.EnclosingClosure enclosingClosure = this.typeCheckingContext.getEnclosingClosure();
        if (enclosingClosure != null) {
            if (enclosingClosure.getClosureExpression().getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE) != null) {
                return null;
            }
            ClassNode inferredReturnType = this.getInferredReturnType(enclosingClosure.getClosureExpression());
            if (expression instanceof ConstructorCallExpression) {
                this.inferDiamondType((ConstructorCallExpression)expression, inferredReturnType != null ? inferredReturnType : ClassHelper.dynamicType());
            }
            if (!(inferredReturnType == null || inferredReturnType.equals(type) || ClassHelper.isObjectType(inferredReturnType) || ClassHelper.isPrimitiveVoid(inferredReturnType) || ClassHelper.isPrimitiveBoolean(inferredReturnType) || GenericsUtils.hasUnresolvedGenerics(inferredReturnType))) {
                if (ClassHelper.isStringType(inferredReturnType) && StaticTypeCheckingSupport.isGStringOrGStringStringLUB(type)) {
                    type = ClassHelper.STRING_TYPE;
                } else if (GenericsUtils.buildWildcardType(StaticTypeCheckingVisitor.wrapTypeIfNecessary(inferredReturnType)).isCompatibleWith(StaticTypeCheckingVisitor.wrapTypeIfNecessary(type))) {
                    type = inferredReturnType;
                } else if (!ClassHelper.isPrimitiveVoid(type) && !this.extension.handleIncompatibleReturnType(statement, type)) {
                    this.addStaticTypeError("Cannot return value of type " + StaticTypeCheckingSupport.prettyPrintType(type) + " for " + (enclosingClosure.getClosureExpression() instanceof LambdaExpression ? "lambda" : "closure") + " expecting " + StaticTypeCheckingSupport.prettyPrintType(inferredReturnType), expression);
                }
            }
            return type;
        }
        MethodNode enclosingMethod = this.typeCheckingContext.getEnclosingMethod();
        if (enclosingMethod != null && !enclosingMethod.isVoidMethod() && !enclosingMethod.isDynamicReturnType()) {
            ClassNode returnType = enclosingMethod.getReturnType();
            if (!ClassHelper.isPrimitiveVoid(ClassHelper.getUnwrapper(type)) && !StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(returnType, type, null, false)) {
                if (!this.extension.handleIncompatibleReturnType(statement, type)) {
                    this.addStaticTypeError("Cannot return value of type " + StaticTypeCheckingSupport.prettyPrintType(type) + " for method returning " + StaticTypeCheckingSupport.prettyPrintType(returnType), expression);
                }
            } else if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(type, returnType)) {
                BinaryExpression dummy = StaticTypeCheckingVisitor.assignX(GeneralUtils.varX("{target}", returnType), expression, statement);
                ClassNode resultType = this.getResultType(returnType, 100, type, dummy);
                this.checkTypeGenerics(returnType, resultType, expression);
            }
        }
        return null;
    }

    protected void addClosureReturnType(ClassNode returnType) {
        if (returnType != null && !ClassHelper.isPrimitiveVoid(returnType)) {
            this.typeCheckingContext.getEnclosingClosure().addReturnType(returnType);
        }
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression call) {
        Set<MethodNode> methods;
        if (!this.extension.beforeMethodCall(call)) {
            MethodNode ctor;
            ClassNode receiver = call.isThisCall() ? this.makeThis() : (call.isSuperCall() ? this.makeSuper() : call.getType());
            Expression arguments = call.getArguments();
            ArgumentListExpression argumentList = InvocationWriter.makeArgumentList(arguments);
            this.checkForbiddenSpreadArgument(argumentList);
            this.visitMethodCallArguments(receiver, argumentList, false, null);
            ClassNode[] argumentTypes = this.getArgumentTypes(argumentList);
            if (this.looksLikeNamedArgConstructor(receiver, argumentTypes) && this.findMethod(receiver, "<init>", argumentTypes).isEmpty() && this.findMethod(receiver, "<init>", DefaultGroovyMethods.init(argumentTypes)).size() == 1) {
                ctor = this.typeCheckMapConstructor(call, receiver, arguments);
            } else {
                ctor = this.findMethodOrFail(call, receiver, "<init>", argumentTypes);
                if (ctor != null) {
                    Parameter[] parameters = ctor.getParameters();
                    if (this.looksLikeNamedArgConstructor(receiver, argumentTypes) && parameters.length == argumentTypes.length - 1) {
                        ctor = this.typeCheckMapConstructor(call, receiver, arguments);
                    } else {
                        if (DefaultGroovyMethods.asBoolean(receiver.getGenericsTypes())) {
                            Map<GenericsType.GenericsTypeName, GenericsType> context = StaticTypeCheckingVisitor.extractPlaceHoldersVisibleToDeclaration(receiver, ctor, argumentList);
                            parameters = (Parameter[])Arrays.stream(parameters).map(p -> new Parameter(StaticTypeCheckingSupport.applyGenericsContext(context, p.getType()), p.getName())).toArray(Parameter[]::new);
                        }
                        this.resolvePlaceholdersFromImplicitTypeHints(argumentTypes, argumentList, parameters);
                        this.typeCheckMethodsWithGenericsOrFail(receiver, argumentTypes, ctor, call);
                        this.visitMethodCallArguments(receiver, argumentList, true, ctor);
                    }
                }
            }
            if (ctor != null) {
                this.storeTargetMethod(call, ctor);
            }
        }
        if (call.isUsingAnonymousInnerClass() && !(methods = this.typeCheckingContext.methodsToBeVisited).isEmpty()) {
            this.typeCheckingContext.methodsToBeVisited = Collections.emptySet();
            ClassNode anonType = call.getType();
            this.visitClass(anonType);
            anonType.putNodeMetaData(StaticTypeCheckingVisitor.class, Boolean.TRUE);
            this.typeCheckingContext.methodsToBeVisited = methods;
        }
        this.extension.afterMethodCall(call);
    }

    private boolean looksLikeNamedArgConstructor(ClassNode receiver, ClassNode[] argumentTypes) {
        if (argumentTypes.length == 1 || argumentTypes.length == 2 && argumentTypes[0].equals(receiver.getOuterClass())) {
            return GeneralUtils.isOrImplements(argumentTypes[argumentTypes.length - 1], ClassHelper.MAP_TYPE);
        }
        return false;
    }

    protected MethodNode typeCheckMapConstructor(ConstructorCallExpression call, ClassNode receiver, Expression arguments) {
        Expression expression;
        TupleExpression texp;
        List<Expression> expressions;
        ConstructorNode node = null;
        if (arguments instanceof TupleExpression && ((expressions = (texp = (TupleExpression)arguments).getExpressions()).size() == 1 || expressions.size() == 2) && (expression = expressions.get(expressions.size() - 1)) instanceof MapExpression) {
            Parameter[] parameterArray;
            MapExpression argList = (MapExpression)expression;
            this.checkGroovyConstructorMap(call, receiver, argList);
            if (expressions.size() == 1) {
                Parameter[] parameterArray2 = new Parameter[1];
                parameterArray = parameterArray2;
                parameterArray2[0] = new Parameter(ClassHelper.MAP_TYPE, "map");
            } else {
                Parameter[] parameterArray3 = new Parameter[2];
                parameterArray3[0] = new Parameter(receiver.redirect().getOuterClass(), "$p$");
                parameterArray = parameterArray3;
                parameterArray3[1] = new Parameter(ClassHelper.MAP_TYPE, "map");
            }
            Parameter[] params = parameterArray;
            node = new ConstructorNode(1, params, ClassNode.EMPTY_ARRAY, GENERATED_EMPTY_STATEMENT);
            node.setDeclaringClass(receiver);
        }
        return node;
    }

    protected ClassNode[] getArgumentTypes(ArgumentListExpression argumentList) {
        return (ClassNode[])argumentList.getExpressions().stream().map(exp -> StaticTypeCheckingVisitor.isNullConstant(exp) ? StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE : this.getType((ASTNode)exp)).toArray(ClassNode[]::new);
    }

    private ClassNode getInferredTypeFromTempInfo(Expression expression, ClassNode expressionType) {
        List<ClassNode> tempTypes;
        if (expression instanceof VariableExpression && !this.typeCheckingContext.temporaryIfBranchTypeInformation.isEmpty() && (tempTypes = this.getTemporaryTypesForExpression(expression)) != null && !tempTypes.isEmpty()) {
            ArrayList<ClassNode> types = new ArrayList<ClassNode>(tempTypes.size() + 1);
            if (expressionType != null && !ClassHelper.isObjectType(expressionType) && tempTypes.stream().noneMatch(t -> StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(t, expressionType))) {
                types.add(expressionType);
            }
            types.addAll(tempTypes);
            if (types.isEmpty()) {
                return ClassHelper.OBJECT_TYPE;
            }
            if (types.size() == 1) {
                return (ClassNode)types.get(0);
            }
            return new UnionTypeClassNode(types.toArray(ClassNode.EMPTY_ARRAY));
        }
        return expressionType;
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        HashMap<VariableExpression, Map<StaticTypesMarker, Object>> variableMetadata;
        HashMap<VariableExpression, ClassNode> varTypes = new HashMap<VariableExpression, ClassNode>();
        expression.getCode().visit(new VariableExpressionTypeMemoizer(varTypes, true));
        Map<VariableExpression, List<ClassNode>> oldTracker = this.pushAssignmentTracking();
        SharedVariableCollector collector = new SharedVariableCollector(this.getSourceUnit());
        collector.visitClosureExpression(expression);
        Set<VariableExpression> closureSharedVariables = collector.getClosureSharedExpressions();
        if (!closureSharedVariables.isEmpty()) {
            for (VariableExpression ve : closureSharedVariables) {
                this.getType(ve);
            }
            variableMetadata = new HashMap<VariableExpression, Map<StaticTypesMarker, Object>>();
            this.saveVariableExpressionMetadata(closureSharedVariables, variableMetadata);
        } else {
            variableMetadata = null;
        }
        this.typeCheckingContext.pushEnclosingClosureExpression(expression);
        DelegationMetadata dmd = this.getDelegationMetadata(expression);
        this.typeCheckingContext.delegationMetadata = dmd != null ? this.newDelegationMetadata(dmd.getType(), dmd.getStrategy()) : this.newDelegationMetadata(this.typeCheckingContext.getEnclosingClassNode(), 0);
        super.visitClosureExpression(expression);
        this.typeCheckingContext.delegationMetadata = this.typeCheckingContext.delegationMetadata.getParent();
        this.returnAdder.visitMethod(new MethodNode("dummy", 0, ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, expression.getCode()));
        TypeCheckingContext.EnclosingClosure enclosingClosure = this.typeCheckingContext.popEnclosingClosure();
        if (!enclosingClosure.getReturnTypes().isEmpty()) {
            ClassNode returnType = WideningCategories.lowestUpperBound(enclosingClosure.getReturnTypes());
            this.storeInferredReturnType(expression, StaticTypeCheckingVisitor.wrapTypeIfNecessary(returnType));
            this.storeType(expression, StaticTypeCheckingVisitor.wrapClosureType(returnType));
        }
        if (this.isSecondPassNeededForControlStructure(varTypes, oldTracker)) {
            this.visitClosureExpression(expression);
        }
        this.restoreVariableExpressionMetadata(variableMetadata);
        for (Parameter parameter : ClosureUtils.getParametersSafe(expression)) {
            this.typeCheckingContext.controlStructureVariables.remove(parameter);
            this.visitInitialExpression(parameter.getInitialExpression(), GeneralUtils.varX(parameter), parameter);
        }
    }

    @Override
    public void visitMethodPointerExpression(MethodPointerExpression expression) {
        super.visitMethodPointerExpression(expression);
        Expression nameExpr = expression.getMethodName();
        if (nameExpr instanceof ConstantExpression && ClassHelper.isStringType(this.getType(nameExpr))) {
            String nameText = nameExpr.getText();
            if ("new".equals(nameText)) {
                ClassNode receiverType = this.getType(expression.getExpression());
                if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(receiverType)) {
                    this.storeType(expression, StaticTypeCheckingVisitor.wrapClosureType(receiverType));
                }
                return;
            }
            ArrayList<Receiver<String>> receivers = new ArrayList<Receiver<String>>();
            this.addReceivers(receivers, this.makeOwnerList(expression.getExpression()), false);
            ClassNode receiverType = null;
            List<MethodNode> candidates = EMPTY_METHODNODE_LIST;
            for (Receiver receiver : receivers) {
                receiverType = StaticTypeCheckingVisitor.wrapTypeIfNecessary(receiver.getType());
                candidates = this.findMethodsWithGenerated(receiverType, nameText);
                candidates.addAll(StaticTypeCheckingSupport.findDGMMethodsForClassNode(this.getSourceUnit().getClassLoader(), receiverType, nameText));
                if ((candidates = StaticTypeCheckingSupport.filterMethodsByVisibility(candidates, this.typeCheckingContext.getEnclosingClassNode())).isEmpty()) continue;
                break;
            }
            if (candidates.isEmpty()) {
                candidates = this.extension.handleMissingMethod(this.getType(expression.getExpression()), nameText, null, null, null);
            } else if (candidates.size() > 1) {
                candidates = this.extension.handleAmbiguousMethods(candidates, expression);
            }
            if (!candidates.isEmpty()) {
                Map<GenericsType.GenericsTypeName, GenericsType> gts = GenericsUtils.extractPlaceholders(receiverType);
                candidates.stream().map(candidate -> StaticTypeCheckingSupport.applyGenericsContext(gts, candidate.getReturnType())).reduce(WideningCategories::lowestUpperBound).ifPresent(returnType -> this.storeType(expression, StaticTypeCheckingVisitor.wrapClosureType(returnType)));
                expression.putNodeMetaData(MethodNode.class, candidates);
            } else if (!(expression instanceof MethodReferenceExpression)) {
                ClassNode type = StaticTypeCheckingVisitor.wrapTypeIfNecessary(this.getType(expression.getExpression()));
                if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(type)) {
                    type = type.getGenericsTypes()[0].getType();
                }
                this.addStaticTypeError("Cannot find matching method " + StaticTypeCheckingSupport.prettyPrintTypeName(type) + "#" + nameText + ". Please check if the declared type is correct and if the method exists.", nameExpr);
            }
        }
    }

    private static ClassNode wrapClosureType(ClassNode returnType) {
        return GenericsUtils.makeClassSafe0(ClassHelper.CLOSURE_TYPE, StaticTypeCheckingVisitor.wrapTypeIfNecessary(returnType).asGenericsType());
    }

    protected DelegationMetadata getDelegationMetadata(ClosureExpression expression) {
        return (DelegationMetadata)expression.getNodeMetaData((Object)StaticTypesMarker.DELEGATION_METADATA);
    }

    private DelegationMetadata newDelegationMetadata(ClassNode delegateType, int resolveStrategy) {
        return new DelegationMetadata(delegateType, resolveStrategy, this.typeCheckingContext.delegationMetadata);
    }

    protected void restoreVariableExpressionMetadata(Map<VariableExpression, Map<StaticTypesMarker, Object>> typesBeforeVisit) {
        if (typesBeforeVisit != null) {
            typesBeforeVisit.forEach((var, map) -> {
                for (StaticTypesMarker marker : StaticTypesMarker.values()) {
                    if (marker == StaticTypesMarker.INFERRED_TYPE) continue;
                    Object value = map.get((Object)marker);
                    if (value == null) {
                        var.removeNodeMetaData((Object)marker);
                        continue;
                    }
                    var.putNodeMetaData((Object)marker, value);
                }
                var.removeNodeMetaData((Object)StaticTypesMarker.DECLARATION_INFERRED_TYPE);
            });
        }
    }

    protected void saveVariableExpressionMetadata(Set<VariableExpression> closureSharedExpressions, Map<VariableExpression, Map<StaticTypesMarker, Object>> typesBeforeVisit) {
        for (VariableExpression ve : closureSharedExpressions) {
            Variable v;
            while ((v = ve.getAccessedVariable()) != ve && v instanceof VariableExpression) {
                ve = (VariableExpression)v;
            }
            EnumMap metadata = new EnumMap(StaticTypesMarker.class);
            for (StaticTypesMarker marker : StaticTypesMarker.values()) {
                Object value = ve.getNodeMetaData((Object)marker);
                if (value == null) continue;
                metadata.put(marker, value);
            }
            typesBeforeVisit.put(ve, metadata);
        }
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        if (this.shouldSkipMethodNode(node)) {
            return;
        }
        super.visitConstructor(node);
    }

    @Override
    public void visitMethod(MethodNode node) {
        if (this.shouldSkipMethodNode(node)) {
            return;
        }
        if (!this.extension.beforeVisitMethod(node)) {
            ErrorCollector collector = (ErrorCollector)node.getNodeMetaData(ERROR_COLLECTOR);
            if (collector != null) {
                this.typeCheckingContext.getErrorCollector().addCollectorContents(collector);
            } else {
                this.startMethodInference(node, this.typeCheckingContext.getErrorCollector());
            }
            node.removeNodeMetaData(ERROR_COLLECTOR);
        }
        this.extension.afterVisitMethod(node);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void startMethodInference(MethodNode node, ErrorCollector collector) {
        if ((this.typeCheckingContext.methodsToBeVisited.isEmpty() || this.typeCheckingContext.methodsToBeVisited.contains(node)) && this.typeCheckingContext.alreadyVisitedMethods.add(node)) {
            this.typeCheckingContext.pushErrorCollector(collector);
            boolean osc = this.typeCheckingContext.isInStaticContext;
            try {
                this.typeCheckingContext.isInStaticContext = StaticTypeCheckingVisitor.isNonStaticHelperMethod(node) ? false : node.isStatic();
                super.visitMethod(node);
            }
            finally {
                this.typeCheckingContext.isInStaticContext = osc;
            }
            this.typeCheckingContext.popErrorCollector();
            node.putNodeMetaData(ERROR_COLLECTOR, collector);
        }
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        this.typeCheckingContext.pushEnclosingMethod(node);
        ClassNode returnType = node.getReturnType();
        if (!isConstructor && (StaticTypeCheckingVisitor.isClosureWithType(returnType) || ClassHelper.isFunctionalInterface(returnType))) {
            new ReturnAdder(returnStmt -> this.applyTargetType(returnType, returnStmt.getExpression())).visitMethod(node);
        }
        this.readClosureParameterAnnotation(node);
        super.visitConstructorOrMethod(node, isConstructor);
        if (node.hasDefaultValue()) {
            this.visitDefaultParameterArguments(node.getParameters());
        }
        if (!isConstructor) {
            this.returnAdder.visitMethod(node);
        }
        this.typeCheckingContext.popEnclosingMethod();
    }

    private void readClosureParameterAnnotation(MethodNode node) {
        for (Parameter parameter : node.getParameters()) {
            for (AnnotationNode annotation : parameter.getAnnotations()) {
                Expression options;
                Expression value;
                List<ClassNode[]> signatures;
                if (!annotation.getClassNode().equals(CLOSUREPARAMS_CLASSNODE) || (signatures = this.getSignaturesFromHint(node, value = annotation.getMember("value"), options = annotation.getMember("options"), annotation)).size() != 1) continue;
                parameter.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, Arrays.stream(signatures.get(0)).map(t -> new Parameter((ClassNode)t, "")).toArray(Parameter[]::new));
            }
        }
    }

    private void visitDefaultParameterArguments(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            if (!parameter.hasInitialExpression()) continue;
            this.visitInitialExpression(parameter.getInitialExpression(), GeneralUtils.varX(parameter), parameter);
            parameter.getInitialExpression().visit(new CodeVisitorSupport(){

                @Override
                public void visitMethodCallExpression(MethodCallExpression mce) {
                    super.visitMethodCallExpression(mce);
                    mce.setMethodTarget(null);
                }
            });
        }
    }

    @Override
    protected void visitObjectInitializerStatements(ClassNode node) {
        ConstructorNode init = new ConstructorNode(0, null, null, new BlockStatement(node.getObjectInitializerStatements(), null));
        this.typeCheckingContext.pushEnclosingMethod(init);
        super.visitObjectInitializerStatements(node);
        this.typeCheckingContext.popEnclosingMethod();
    }

    protected void addTypeCheckingInfoAnnotation(MethodNode node) {
        if (node instanceof ConstructorNode) {
            return;
        }
        ClassNode rtype = this.getInferredReturnType(node);
        if (rtype != null && node.getAnnotations(TYPECHECKING_INFO_NODE).isEmpty()) {
            AnnotationNode anno = new AnnotationNode(TYPECHECKING_INFO_NODE);
            anno.setMember("version", CURRENT_SIGNATURE_PROTOCOL);
            SignatureCodec codec = SignatureCodecFactory.getCodec(1, this.getTransformLoader());
            String genericsSignature = codec.encode(rtype);
            if (genericsSignature != null) {
                ConstantExpression signature = new ConstantExpression(genericsSignature);
                signature.setType(ClassHelper.STRING_TYPE);
                anno.setMember("inferredType", signature);
                node.addAnnotation(anno);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
        String name = call.getMethod();
        if (name == null) {
            this.addStaticTypeError("cannot resolve dynamic method name at compile time.", call);
            return;
        }
        if (this.extension.beforeMethodCall(call)) {
            this.extension.afterMethodCall(call);
            return;
        }
        Expression callArguments = call.getArguments();
        ArgumentListExpression argumentList = InvocationWriter.makeArgumentList(callArguments);
        this.checkForbiddenSpreadArgument(argumentList);
        ClassNode receiver = call.getOwnerType();
        this.visitMethodCallArguments(receiver, argumentList, false, null);
        ClassNode[] args = this.getArgumentTypes(argumentList);
        try {
            LinkedList<Receiver<String>> receivers = new LinkedList<Receiver<String>>();
            this.addReceivers(receivers, this.makeOwnerList(new ClassExpression(receiver)), false);
            List<MethodNode> mn = null;
            Receiver chosenReceiver = null;
            for (Receiver receiver2 : receivers) {
                mn = this.findMethod(receiver2.getType(), name, args);
                if (mn.isEmpty()) continue;
                if (mn.size() == 1) {
                    this.resolvePlaceholdersFromImplicitTypeHints(args, argumentList, mn.get(0).getParameters());
                    this.typeCheckMethodsWithGenericsOrFail(receiver2.getType(), args, mn.get(0), call);
                }
                chosenReceiver = receiver2;
                break;
            }
            if (mn == null) {
                throw new GroovyBugError("Invalid state finding valid method: receivers should never be empty and findMethod should never return null");
            }
            if (mn.isEmpty()) {
                mn = this.extension.handleMissingMethod(receiver, name, argumentList, args, call);
            }
            boolean callArgsVisited = false;
            if (mn.isEmpty()) {
                this.addNoMatchingMethodError(receiver, name, args, call);
            } else {
                if ((mn = this.disambiguateMethods(mn, receiver, args, call)).size() == 1) {
                    MethodNode methodNode = mn.get(0);
                    ClassNode returnType = this.getType(methodNode);
                    if (returnType.isUsingGenerics() && !returnType.isEnum()) {
                        this.visitMethodCallArguments(receiver, argumentList, true, methodNode);
                        ClassNode irtg = this.inferReturnTypeGenerics(chosenReceiver.getType(), methodNode, callArguments);
                        returnType = irtg != null && StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(irtg, returnType) ? irtg : returnType;
                        callArgsVisited = true;
                    }
                    this.storeType(call, returnType);
                    this.storeTargetMethod(call, methodNode);
                } else {
                    this.addAmbiguousErrorMessage(mn, name, args, call);
                }
                if (!callArgsVisited) {
                    this.visitMethodCallArguments(receiver, argumentList, true, (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET));
                }
            }
        }
        finally {
            this.extension.afterMethodCall(call);
        }
    }

    @Deprecated
    protected void checkClosureParameters(Expression callArguments, ClassNode receiver) {
        if (callArguments instanceof ArgumentListExpression) {
            Parameter param;
            ArgumentListExpression argList = (ArgumentListExpression)callArguments;
            ClosureExpression closure = (ClosureExpression)argList.getExpression(0);
            Parameter[] parameters = closure.getParameters();
            if (parameters.length > 1) {
                this.addStaticTypeError("Unexpected number of parameters for a with call", argList);
            } else if (parameters.length == 1 && !(param = parameters[0]).isDynamicTyped() && !StaticTypeCheckingSupport.isAssignableTo(receiver, param.getType().redirect())) {
                this.addStaticTypeError("Expected parameter type: " + StaticTypeCheckingSupport.prettyPrintType(receiver) + " but was: " + StaticTypeCheckingSupport.prettyPrintType(param.getType()), param);
            }
            closure.putNodeMetaData((Object)StaticTypesMarker.DELEGATION_METADATA, this.newDelegationMetadata(receiver, 1));
        }
    }

    protected void silentlyVisitMethodNode(MethodNode directMethodCallCandidate) {
        ErrorCollector collector = new ErrorCollector(this.typeCheckingContext.getErrorCollector().getConfiguration());
        this.startMethodInference(directMethodCallCandidate, collector);
    }

    protected void visitMethodCallArguments(ClassNode receiver, ArgumentListExpression arguments, boolean visitClosures, MethodNode selectedMethod) {
        Parameter[] parameters;
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        if (selectedMethod instanceof ExtensionMethodNode) {
            parameters = ((ExtensionMethodNode)selectedMethod).getExtensionMethodNode().getParameters();
            expressions.add(GeneralUtils.varX("$self", receiver));
        } else {
            parameters = selectedMethod != null ? selectedMethod.getParameters() : Parameter.EMPTY_ARRAY;
        }
        expressions.addAll(arguments.getExpressions());
        int nExpressions = expressions.size();
        for (int i = 0; i < nExpressions; ++i) {
            Expression expression = (Expression)expressions.get(i);
            if (visitClosures && expression instanceof ClosureExpression || !visitClosures && !(expression instanceof ClosureExpression)) {
                if (i < parameters.length && visitClosures) {
                    Parameter target = parameters[i];
                    ClassNode targetType = target.getType();
                    ClosureExpression source = (ClosureExpression)expression;
                    this.checkClosureWithDelegatesTo(receiver, selectedMethod, GeneralUtils.args(expressions), parameters, source, target);
                    if (selectedMethod instanceof ExtensionMethodNode) {
                        if (i > 0) {
                            this.inferClosureParameterTypes(receiver, arguments, source, target, selectedMethod);
                        }
                    } else {
                        this.inferClosureParameterTypes(receiver, arguments, source, target, selectedMethod);
                    }
                    if (ClassHelper.isFunctionalInterface(targetType)) {
                        this.storeInferredReturnType(source, GenericsUtils.parameterizeSAM(targetType).getV2());
                    } else if (StaticTypeCheckingVisitor.isClosureWithType(targetType)) {
                        this.storeInferredReturnType(source, StaticTypeCheckingSupport.getCombinedBoundType(targetType.getGenericsTypes()[0]));
                    }
                }
                expression.visit(this);
                expression.removeNodeMetaData((Object)StaticTypesMarker.DELEGATION_METADATA);
            }
            if (i != 0 || parameters.length <= 0 || !(expression instanceof MapExpression)) continue;
            this.checkNamedParamsAnnotation(parameters[0], (MapExpression)expression);
        }
        if (visitClosures) {
            this.inferMethodReferenceType(receiver, arguments, selectedMethod);
        }
    }

    private void checkNamedParamsAnnotation(Parameter param, MapExpression args) {
        if (!GeneralUtils.isOrImplements(param.getType(), ClassHelper.MAP_TYPE)) {
            return;
        }
        List<MapEntryExpression> entryExpressions = args.getMapEntryExpressions();
        LinkedHashMap<Object, Expression> entries = new LinkedHashMap<Object, Expression>();
        for (MapEntryExpression entry : entryExpressions) {
            Object key = entry.getKeyExpression();
            if (key instanceof ConstantExpression) {
                key = ((ConstantExpression)key).getValue();
            }
            entries.put(key, entry.getValueExpression());
        }
        ArrayList<String> collectedNames = new ArrayList<String>();
        List<AnnotationNode> annotations = param.getAnnotations(NAMED_PARAMS_CLASSNODE);
        if (annotations != null && !annotations.isEmpty()) {
            AnnotationNode an = null;
            for (AnnotationNode next : annotations) {
                if (!next.getClassNode().getName().equals(NamedParams.class.getName())) continue;
                an = next;
            }
            if (an != null) {
                Expression expression = an.getMember("value");
                if (expression instanceof AnnotationConstantExpression) {
                    this.processNamedParam((AnnotationConstantExpression)expression, entries, (Expression)args, collectedNames);
                } else if (expression instanceof ListExpression) {
                    ListExpression le = (ListExpression)expression;
                    for (Expression next : le.getExpressions()) {
                        if (!(next instanceof AnnotationConstantExpression)) continue;
                        this.processNamedParam((AnnotationConstantExpression)next, entries, (Expression)args, collectedNames);
                    }
                }
            }
        }
        if ((annotations = param.getAnnotations(NAMED_PARAM_CLASSNODE)) != null && !annotations.isEmpty()) {
            for (AnnotationNode annotationNode : annotations) {
                if (!annotationNode.getClassNode().getName().equals(NamedParam.class.getName())) continue;
                this.processNamedParam(annotationNode, entries, (Expression)args, collectedNames);
            }
        }
        if (!collectedNames.isEmpty()) {
            for (Map.Entry entry : entries.entrySet()) {
                if (collectedNames.contains(entry.getKey())) continue;
                this.addStaticTypeError("unexpected named arg: " + entry.getKey(), args);
            }
        }
    }

    private void processNamedParam(AnnotationConstantExpression value, Map<Object, Expression> entries, Expression expression, List<String> collectedNames) {
        AnnotationNode namedParam = (AnnotationNode)value.getValue();
        if (!namedParam.getClassNode().getName().equals(NamedParam.class.getName())) {
            return;
        }
        this.processNamedParam(namedParam, entries, expression, collectedNames);
    }

    private void processNamedParam(AnnotationNode namedParam, Map<Object, Expression> entries, Expression expression, List<String> collectedNames) {
        ClassNode argumentType;
        ClassExpression typeX;
        String name = null;
        boolean required = false;
        ClassNode expectedType = null;
        ConstantExpression constX = (ConstantExpression)namedParam.getMember("value");
        if (constX != null) {
            name = (String)constX.getValue();
            collectedNames.add(name);
        }
        if ((constX = (ConstantExpression)namedParam.getMember("required")) != null) {
            required = (Boolean)constX.getValue();
        }
        if ((typeX = (ClassExpression)namedParam.getMember("type")) != null) {
            expectedType = typeX.getType();
        }
        if (!entries.containsKey(name)) {
            if (required) {
                this.addStaticTypeError("required named param '" + name + "' not found.", expression);
            }
        } else if (expectedType != null && !StaticTypeCheckingSupport.isAssignableTo(argumentType = this.getDeclaredOrInferredType(entries.get(name)), expectedType)) {
            this.addStaticTypeError("argument for named param '" + name + "' has type '" + StaticTypeCheckingSupport.prettyPrintType(argumentType) + "' but expected '" + StaticTypeCheckingSupport.prettyPrintType(expectedType) + "'.", expression);
        }
    }

    protected void inferClosureParameterTypes(ClassNode receiver, Expression arguments, ClosureExpression expression, Parameter target, MethodNode method) {
        List<AnnotationNode> annotations = target.getAnnotations(CLOSUREPARAMS_CLASSNODE);
        if (annotations != null && !annotations.isEmpty()) {
            for (AnnotationNode annotation : annotations) {
                Expression value = annotation.getMember("value");
                Expression options = annotation.getMember("options");
                Expression conflictResolver = annotation.getMember("conflictResolutionStrategy");
                this.doInferClosureParameterTypes(receiver, arguments, expression, method, value, conflictResolver, options);
            }
        } else if (ClassHelper.isSAMType(target.getOriginType())) {
            Parameter[] p;
            GenericsType[] typeParameters;
            Map<GenericsType.GenericsTypeName, GenericsType> context = StaticTypeCheckingVisitor.extractPlaceHoldersVisibleToDeclaration(receiver, method, arguments);
            GenericsType[] genericsTypeArray = typeParameters = method instanceof ConstructorNode ? method.getDeclaringClass().getGenericsTypes() : StaticTypeCheckingSupport.applyGenericsContext(context, method.getGenericsTypes());
            if (typeParameters != null) {
                int n;
                GenericsType[] typeArguments;
                MethodCallExpression mce;
                boolean typeParametersResolved = false;
                Expression emc = this.typeCheckingContext.getEnclosingMethodCall();
                if (emc instanceof MethodCallExpression && (mce = (MethodCallExpression)emc).getArguments() == arguments && (typeArguments = mce.getGenericsTypes()) != null && (n = typeParameters.length) == typeArguments.length) {
                    typeParametersResolved = true;
                    for (int i = 0; i < n; ++i) {
                        context.put(new GenericsType.GenericsTypeName(typeParameters[i].getName()), typeArguments[i]);
                    }
                }
                if (!typeParametersResolved) {
                    int i = -1;
                    p = method.getParameters();
                    for (Expression argument : (ArgumentListExpression)arguments) {
                        ++i;
                        if (StaticTypeCheckingVisitor.isNullConstant(argument)) continue;
                        ClassNode pType = p[Math.min(i, p.length - 1)].getType();
                        HashMap<GenericsType.GenericsTypeName, GenericsType> gc = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
                        StaticTypeCheckingSupport.extractGenericsConnections(gc, StaticTypeCheckingVisitor.wrapTypeIfNecessary(this.getType(argument)), pType);
                        if (argument == expression || argument instanceof ClosureExpression && ClassHelper.isSAMType(pType)) {
                            Parameter[] q = ClosureUtils.getParametersSafe((ClosureExpression)argument);
                            ClassNode[] r = StaticTypeCheckingVisitor.extractTypesFromParameters(q);
                            ClassNode[] s = GenericsUtils.parameterizeSAM(pType).getV1();
                            for (int j = 0; j < r.length && j < s.length; ++j) {
                                if (q[j].isDynamicTyped()) continue;
                                StaticTypeCheckingSupport.extractGenericsConnections(gc, r[j], s[j]);
                            }
                        }
                        gc.forEach((key, gt) -> {
                            for (GenericsType tp : typeParameters) {
                                if (!tp.getName().equals(key.getName())) continue;
                                context.putIfAbsent((GenericsType.GenericsTypeName)key, (GenericsType)gt);
                                break;
                            }
                        });
                    }
                    for (GenericsType tp : typeParameters) {
                        context.computeIfAbsent(new GenericsType.GenericsTypeName(tp.getName()), x -> StaticTypeCheckingSupport.fullyResolve(tp, context));
                    }
                }
            }
            ClassNode[] samParamTypes = GenericsUtils.parameterizeSAM(StaticTypeCheckingSupport.applyGenericsContext(context, target.getType())).getV1();
            ClassNode[] paramTypes = (ClassNode[])expression.getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS);
            if (paramTypes == null) {
                p = expression.getParameters();
                if (p == null) {
                    paramTypes = ClassNode.EMPTY_ARRAY;
                } else {
                    int n = p.length;
                    if (n == 0) {
                        paramTypes = samParamTypes;
                    } else {
                        paramTypes = Arrays.copyOf(samParamTypes, n);
                        for (int i = 0; i < Math.min(n, samParamTypes.length); ++i) {
                            this.checkParamType(p[i], paramTypes[i], i == n - 1, expression instanceof LambdaExpression);
                        }
                    }
                }
                expression.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, paramTypes);
            }
        }
    }

    private List<ClassNode[]> getSignaturesFromHint(MethodNode mn, Expression hintType, Expression options, ASTNode usage) {
        String hintTypeName = hintType.getText();
        try {
            Class<?> hintClass = this.getTransformLoader().loadClass(hintTypeName);
            List<ClassNode[]> closureSignatures = ((ClosureSignatureHint)hintClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0])).getClosureSignatures(mn instanceof ExtensionMethodNode ? ((ExtensionMethodNode)mn).getExtensionMethodNode() : mn, this.typeCheckingContext.getSource(), this.typeCheckingContext.getCompilationUnit(), StaticTypeCheckingVisitor.convertToStringArray(options), usage);
            return closureSignatures;
        }
        catch (ReflectiveOperationException e) {
            throw new GroovyBugError(e);
        }
    }

    private List<ClassNode[]> resolveWithResolver(List<ClassNode[]> candidates, ClassNode receiver, Expression arguments, ClosureExpression expression, MethodNode selectedMethod, Expression resolverClass, Expression options) {
        try {
            ClassLoader transformLoader = this.getTransformLoader();
            Class<?> resolver = transformLoader.loadClass(resolverClass.getText());
            ClosureSignatureConflictResolver resolverInstance = (ClosureSignatureConflictResolver)resolver.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            return resolverInstance.resolve(candidates, receiver, arguments, expression, selectedMethod instanceof ExtensionMethodNode ? ((ExtensionMethodNode)selectedMethod).getExtensionMethodNode() : selectedMethod, this.typeCheckingContext.getSource(), this.typeCheckingContext.getCompilationUnit(), StaticTypeCheckingVisitor.convertToStringArray(options));
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new GroovyBugError(e);
        }
    }

    private ClassLoader getTransformLoader() {
        return Optional.ofNullable(this.typeCheckingContext.getCompilationUnit()).map(CompilationUnit::getTransformLoader).orElseGet(() -> this.getSourceUnit().getClassLoader());
    }

    private void doInferClosureParameterTypes(ClassNode receiver, Expression arguments, ClosureExpression expression, MethodNode selectedMethod, Expression hintClass, Expression resolverClass, Expression options) {
        Parameter[] closureParams = expression.getParameters();
        if (closureParams == null) {
            return;
        }
        List<ClassNode[]> closureSignatures = this.getSignaturesFromHint(selectedMethod, hintClass, options, expression);
        List<ClassNode[]> candidates = new LinkedList<ClassNode[]>();
        for (ClassNode[] signature : closureSignatures) {
            this.resolveGenericsFromTypeHint(receiver, arguments, selectedMethod, signature);
            if (signature.length != closureParams.length && (signature.length != 1 || closureParams.length != 0) && (closureParams.length <= signature.length || !DefaultGroovyMethods.last(signature).isArray())) continue;
            candidates.add(signature);
        }
        if (candidates.size() > 1) {
            Iterator candIt = candidates.iterator();
            while (candIt.hasNext()) {
                ClassNode[] inferred = (ClassNode[])candIt.next();
                int n = closureParams.length;
                for (int i = 0; i < n; ++i) {
                    ClassNode inferredType;
                    Parameter closureParam = closureParams[i];
                    ClassNode declaredType = closureParam.getOriginType();
                    if (i < inferred.length - 1 || inferred.length == n) {
                        inferredType = inferred[i];
                    } else {
                        ClassNode lastInferred = inferred[inferred.length - 1];
                        if (lastInferred.isArray()) {
                            inferredType = lastInferred.getComponentType();
                        } else {
                            candIt.remove();
                            continue;
                        }
                    }
                    if (StaticTypeCheckingSupport.typeCheckMethodArgumentWithGenerics(declaredType, inferredType, i == n - 1)) continue;
                    candIt.remove();
                }
            }
            if (candidates.size() > 1 && resolverClass instanceof ClassExpression) {
                candidates = this.resolveWithResolver(candidates, receiver, arguments, expression, selectedMethod, resolverClass, options);
            }
            if (candidates.size() > 1) {
                this.addError("Ambiguous prototypes for closure. More than one target method matches. Please use explicit argument types.", expression);
            }
        }
        if (candidates.size() == 1) {
            ClassNode[] inferred = (ClassNode[])candidates.get(0);
            if (closureParams.length == 0 && inferred.length == 1) {
                expression.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, inferred);
            } else {
                int n = closureParams.length;
                for (int i = 0; i < n; ++i) {
                    Parameter closureParam = closureParams[i];
                    ClassNode inferredType = ClassHelper.OBJECT_TYPE;
                    if (i < inferred.length - 1 || inferred.length == n) {
                        inferredType = inferred[i];
                    } else {
                        ClassNode lastInferred = inferred[inferred.length - 1];
                        if (lastInferred.isArray()) {
                            inferredType = lastInferred.getComponentType();
                        } else {
                            this.addError("Incorrect number of parameters. Expected " + inferred.length + " but found " + n, expression);
                        }
                    }
                    this.checkParamType(closureParam, inferredType, i == n - 1, false);
                    this.typeCheckingContext.controlStructureVariables.put(closureParam, inferredType);
                }
            }
        }
    }

    private void resolveGenericsFromTypeHint(ClassNode receiver, Expression arguments, MethodNode selectedMethod, ClassNode[] signature) {
        ClassNode returnType = new ClassNode("ClForInference$" + UNIQUE_LONG.incrementAndGet(), 0, ClassHelper.OBJECT_TYPE).getPlainNodeReference();
        returnType.setGenericsTypes((GenericsType[])Arrays.stream(signature).map(ClassNode::asGenericsType).toArray(GenericsType[]::new));
        MethodNode dummyMN = selectedMethod instanceof ExtensionMethodNode ? ((ExtensionMethodNode)selectedMethod).getExtensionMethodNode() : selectedMethod;
        dummyMN = new MethodNode(dummyMN.getName(), dummyMN.getModifiers(), returnType, dummyMN.getParameters(), dummyMN.getExceptions(), null);
        dummyMN.setDeclaringClass(selectedMethod.getDeclaringClass());
        dummyMN.setGenericsTypes(selectedMethod.getGenericsTypes());
        if (selectedMethod instanceof ExtensionMethodNode) {
            dummyMN = new ExtensionMethodNode(dummyMN, dummyMN.getName(), dummyMN.getModifiers(), returnType, selectedMethod.getParameters(), selectedMethod.getExceptions(), null, ((ExtensionMethodNode)selectedMethod).isStaticExtension());
            dummyMN.setDeclaringClass(selectedMethod.getDeclaringClass());
            dummyMN.setGenericsTypes(selectedMethod.getGenericsTypes());
        }
        returnType = this.inferReturnTypeGenerics(receiver, dummyMN, arguments);
        for (GenericsType gt : returnType.getGenericsTypes()) {
            signature[i] = gt.isPlaceholder() ? (gt.getUpperBounds() != null ? gt.getUpperBounds()[0] : gt.getType().redirect()) : StaticTypeCheckingSupport.getCombinedBoundType(gt);
        }
    }

    private static String[] convertToStringArray(Expression options) {
        if (options == null) {
            return ResolveVisitor.EMPTY_STRING_ARRAY;
        }
        if (options instanceof ConstantExpression) {
            return new String[]{options.getText()};
        }
        if (options instanceof ListExpression) {
            return (String[])((ListExpression)options).getExpressions().stream().map(ASTNode::getText).toArray(String[]::new);
        }
        throw new IllegalArgumentException("Unexpected options for @ClosureParams:" + options);
    }

    private void checkClosureWithDelegatesTo(ClassNode receiver, MethodNode mn, ArgumentListExpression arguments, Parameter[] params, Expression expression, Parameter param) {
        List<AnnotationNode> annotations = param.getAnnotations(DELEGATES_TO);
        if (annotations != null && !annotations.isEmpty()) {
            for (AnnotationNode annotation : annotations) {
                Expression value = annotation.getMember("value");
                Expression strategy = annotation.getMember("strategy");
                Expression genericTypeIndex = annotation.getMember("genericTypeIndex");
                Expression type = annotation.getMember("type");
                Integer stInt = 0;
                if (strategy != null) {
                    stInt = (Integer)StaticTypeCheckingSupport.evaluateExpression(GeneralUtils.castX(ClassHelper.Integer_TYPE, strategy), this.getSourceUnit().getConfiguration());
                }
                if (value instanceof ClassExpression && !value.getType().equals(DELEGATES_TO_TARGET)) {
                    if (genericTypeIndex != null) {
                        this.addStaticTypeError("Cannot use @DelegatesTo(genericTypeIndex=" + genericTypeIndex.getText() + ") without @DelegatesTo.Target because generic argument types are not available at runtime", value);
                    }
                    expression.putNodeMetaData((Object)StaticTypesMarker.DELEGATION_METADATA, this.newDelegationMetadata(value.getType(), stInt));
                    continue;
                }
                if (type != null && !"".equals(type.getText()) && type instanceof ConstantExpression) {
                    String typeString = type.getText();
                    ClassNode[] resolved = GenericsUtils.parseClassNodesFromString(typeString, this.getSourceUnit(), this.typeCheckingContext.getCompilationUnit(), mn, type);
                    if (resolved == null) continue;
                    if (resolved.length == 1) {
                        this.resolveGenericsFromTypeHint(receiver, arguments, mn, resolved);
                        expression.putNodeMetaData((Object)StaticTypesMarker.DELEGATION_METADATA, this.newDelegationMetadata(resolved[0], stInt));
                        continue;
                    }
                    this.addStaticTypeError("Incorrect type hint found in method " + mn, type);
                    continue;
                }
                List<Expression> expressions = arguments.getExpressions();
                int expressionsSize = expressions.size();
                Expression parameter = annotation.getMember("target");
                String parameterName = parameter instanceof ConstantExpression ? parameter.getText() : "";
                int paramsLength = params.length;
                for (int j = 0; j < paramsLength; ++j) {
                    String id;
                    Parameter methodParam = params[j];
                    List<AnnotationNode> targets = methodParam.getAnnotations(DELEGATES_TO_TARGET);
                    if (targets == null || targets.size() != 1) continue;
                    AnnotationNode targetAnnotation = targets.get(0);
                    Expression idMember = targetAnnotation.getMember("value");
                    String string = id = idMember instanceof ConstantExpression ? idMember.getText() : "";
                    if (!id.equals(parameterName) || j >= expressionsSize) continue;
                    Expression actualArgument = expressions.get(j);
                    ClassNode actualType = this.getType(actualArgument);
                    if (genericTypeIndex instanceof ConstantExpression) {
                        int gti = Integer.parseInt(genericTypeIndex.getText());
                        ClassNode paramType = methodParam.getType();
                        GenericsType[] genericsTypes = paramType.getGenericsTypes();
                        if (genericsTypes == null) {
                            this.addStaticTypeError("Cannot use @DelegatesTo(genericTypeIndex=" + genericTypeIndex.getText() + ") with a type that doesn't use generics", methodParam);
                        } else if (gti < 0 || gti >= genericsTypes.length) {
                            this.addStaticTypeError("Index of generic type @DelegatesTo(genericTypeIndex=" + genericTypeIndex.getText() + ") " + (gti < 0 ? "lower" : "greater") + " than those of the selected type", methodParam);
                        } else {
                            ClassNode pType = GenericsUtils.parameterizeType(actualType, paramType);
                            GenericsType[] pTypeGenerics = pType.getGenericsTypes();
                            if (pTypeGenerics != null && pTypeGenerics.length > gti) {
                                actualType = pTypeGenerics[gti].getType();
                            } else {
                                this.addStaticTypeError("Unable to map actual type [" + StaticTypeCheckingSupport.prettyPrintType(actualType) + "] onto " + StaticTypeCheckingSupport.prettyPrintType(paramType), methodParam);
                            }
                        }
                    }
                    expression.putNodeMetaData((Object)StaticTypesMarker.DELEGATION_METADATA, this.newDelegationMetadata(actualType, stInt));
                    break;
                }
                if (expression.getNodeMetaData((Object)StaticTypesMarker.DELEGATION_METADATA) != null) continue;
                this.addError("Not enough arguments found for a @DelegatesTo method call. Please check that you either use an explicit class or @DelegatesTo.Target with a correct id", annotation);
            }
        }
    }

    protected void addReceivers(List<Receiver<String>> receivers, Collection<Receiver<String>> owners, boolean implicitThis) {
        if (!implicitThis || this.typeCheckingContext.delegationMetadata == null) {
            receivers.addAll(owners);
        } else {
            StaticTypeCheckingVisitor.addReceivers(receivers, owners, this.typeCheckingContext.delegationMetadata, "");
        }
    }

    private static void addReceivers(List<Receiver<String>> receivers, Collection<Receiver<String>> owners, DelegationMetadata dmd, String path) {
        int strategy = dmd.getStrategy();
        switch (strategy) {
            case 1: 
            case 3: {
                StaticTypeCheckingVisitor.addDelegateReceiver(receivers, dmd.getType(), path + "delegate");
                if (strategy != 1) break;
                if (dmd.getParent() == null) {
                    receivers.addAll(owners);
                    break;
                }
                StaticTypeCheckingVisitor.addReceivers(receivers, owners, dmd.getParent(), path + "owner.");
                break;
            }
            case 0: 
            case 2: {
                if (dmd.getParent() == null) {
                    receivers.addAll(owners);
                } else {
                    StaticTypeCheckingVisitor.addReceivers(receivers, owners, dmd.getParent(), path + "owner.");
                }
                if (strategy != 0) break;
                StaticTypeCheckingVisitor.addDelegateReceiver(receivers, dmd.getType(), path + "delegate");
            }
        }
    }

    private static void addDelegateReceiver(List<Receiver<String>> receivers, ClassNode delegate, String path) {
        if (receivers.stream().map(Receiver::getType).noneMatch(delegate::equals)) {
            receivers.add(new Receiver<String>(delegate, path));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitMethodCallExpression(MethodCallExpression call) {
        String name = call.getMethodAsString();
        if (name == null) {
            this.addStaticTypeError("Cannot resolve dynamic method name at compile time", call.getMethod());
            return;
        }
        if (this.extension.beforeMethodCall(call)) {
            this.extension.afterMethodCall(call);
            return;
        }
        this.typeCheckingContext.pushEnclosingMethodCall(call);
        Expression objectExpression = call.getObjectExpression();
        objectExpression.visit(this);
        call.getMethod().visit(this);
        ClassNode receiver = this.getType(objectExpression);
        if (objectExpression instanceof ConstructorCallExpression) {
            this.inferDiamondType((ConstructorCallExpression)objectExpression, receiver.getPlainNodeReference());
        }
        if (call.isSpreadSafe()) {
            ClassNode componentType = this.inferComponentType(receiver, null);
            if (componentType == null) {
                this.addStaticTypeError("Spread-dot operator can only be used on iterable types", objectExpression);
            } else {
                MethodCallExpression subcall = GeneralUtils.callX((Expression)GeneralUtils.varX("item", componentType), name, call.getArguments());
                subcall.setLineNumber(call.getLineNumber());
                subcall.setColumnNumber(call.getColumnNumber());
                subcall.setImplicitThis(call.isImplicitThis());
                this.visitMethodCallExpression(subcall);
                this.storeType(call, this.extension.buildListType(this.getType(subcall)));
                this.storeTargetMethod(call, (MethodNode)subcall.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET));
            }
            this.typeCheckingContext.popEnclosingMethodCall();
            return;
        }
        Expression callArguments = call.getArguments();
        ArgumentListExpression argumentList = InvocationWriter.makeArgumentList(callArguments);
        this.checkForbiddenSpreadArgument(argumentList);
        this.visitMethodCallArguments(receiver, argumentList, false, null);
        boolean isThisObjectExpression = StaticTypeCheckingVisitor.isThisExpression(objectExpression);
        boolean isCallOnClosure = false;
        FieldNode fieldNode = null;
        switch (name) {
            case "call": 
            case "doCall": {
                if (!isThisObjectExpression) {
                    isCallOnClosure = receiver.equals(ClassHelper.CLOSURE_TYPE);
                    break;
                }
            }
            default: {
                ClassNode enclosingType;
                if (!isThisObjectExpression || (fieldNode = (enclosingType = this.typeCheckingContext.getEnclosingClassNode()).getDeclaredField(name)) == null || !this.getType(fieldNode).equals(ClassHelper.CLOSURE_TYPE) || enclosingType.hasPossibleMethod(name, callArguments)) break;
                isCallOnClosure = true;
            }
        }
        try {
            ClassNode resultType;
            ClassNode[] args = this.getArgumentTypes(argumentList);
            boolean callArgsVisited = false;
            if (isCallOnClosure) {
                if (fieldNode != null) {
                    GenericsType[] genericsTypes = this.getType(fieldNode).getGenericsTypes();
                    if (genericsTypes != null) {
                        Parameter[] parameters = (Parameter[])fieldNode.getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS);
                        if (parameters != null) {
                            this.typeCheckClosureCall(callArguments, args, parameters);
                        }
                        ClassNode closureReturnType = genericsTypes[0].getType();
                        this.storeType(call, closureReturnType);
                    }
                } else if (objectExpression instanceof VariableExpression) {
                    Variable variable = StaticTypeCheckingSupport.findTargetVariable((VariableExpression)objectExpression);
                    if (variable instanceof ASTNode) {
                        ClassNode type;
                        Parameter[] parameters = (Parameter[])((ASTNode)((Object)variable)).getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS);
                        if (parameters != null) {
                            this.typeCheckClosureCall(callArguments, args, parameters);
                        }
                        if ((type = this.getType((ASTNode)((Object)variable))).equals(ClassHelper.CLOSURE_TYPE)) {
                            GenericsType[] genericsTypes = type.getGenericsTypes();
                            type = genericsTypes != null && genericsTypes.length == 1 && genericsTypes[0].getLowerBound() == null ? genericsTypes[0].getType() : ClassHelper.OBJECT_TYPE;
                        }
                        if (type != null) {
                            this.storeType(call, type);
                        }
                    }
                } else if (objectExpression instanceof ClosureExpression) {
                    ClassNode type;
                    Parameter[] parameters = ((ClosureExpression)objectExpression).getParameters();
                    if (parameters != null) {
                        this.typeCheckClosureCall(callArguments, args, parameters);
                    }
                    if ((type = this.getInferredReturnType(objectExpression)) != null) {
                        this.storeType(call, type);
                    }
                }
                int nArgs = 0;
                if (callArguments instanceof ArgumentListExpression) {
                    nArgs = ((ArgumentListExpression)callArguments).getExpressions().size();
                }
                this.storeTargetMethod(call, nArgs == 0 ? CLOSURE_CALL_NO_ARG : (nArgs == 1 ? CLOSURE_CALL_ONE_ARG : CLOSURE_CALL_VARGS));
            } else {
                ArrayList<Receiver<String>> receivers = new ArrayList<Receiver<String>>();
                this.addReceivers(receivers, this.makeOwnerList(objectExpression), call.isImplicitThis());
                List<MethodNode> mn = null;
                Receiver chosenReceiver = null;
                for (Receiver receiver2 : receivers) {
                    ClassNode receiverType = receiver2.getType();
                    mn = this.findMethod(receiverType, name, args);
                    if (!mn.isEmpty() && receiver2.getData() == null && (isThisObjectExpression || call.isImplicitThis()) && (this.typeCheckingContext.isInStaticContext || (receiverType.getModifiers() & 8) != 0)) {
                        LinkedList<MethodNode> accessibleMethods = new LinkedList<MethodNode>();
                        LinkedList<MethodNode> inaccessibleMethods = new LinkedList<MethodNode>();
                        for (MethodNode node : mn) {
                            if (node.isStatic() || !this.typeCheckingContext.isInStaticContext && StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(receiverType, node.getDeclaringClass())) {
                                accessibleMethods.add(node);
                                continue;
                            }
                            inaccessibleMethods.add(node);
                        }
                        mn = accessibleMethods;
                        if (accessibleMethods.isEmpty()) {
                            MethodNode node = (MethodNode)inaccessibleMethods.get(0);
                            this.addStaticTypeError("Non-static method " + StaticTypeCheckingSupport.prettyPrintTypeName(node.getDeclaringClass()) + "#" + node.getName() + " cannot be called from static context", call);
                        }
                    }
                    if (mn.isEmpty()) continue;
                    chosenReceiver = receiver2;
                    break;
                }
                if (mn.isEmpty() && isThisObjectExpression && call.isImplicitThis() && this.typeCheckingContext.getEnclosingClosure() != null && !(mn = ClassHelper.CLOSURE_TYPE.getDeclaredMethods(name)).isEmpty()) {
                    chosenReceiver = Receiver.make(ClassHelper.CLOSURE_TYPE);
                    objectExpression.removeNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
                }
                if (mn.isEmpty()) {
                    mn = this.extension.handleMissingMethod(receiver, name, argumentList, args, call);
                }
                if (mn.isEmpty()) {
                    this.addNoMatchingMethodError(receiver, name, args, call);
                } else {
                    if (this.areCategoryMethodCalls(mn, name, args)) {
                        this.addCategoryMethodCallError(call);
                    }
                    if ((mn = this.disambiguateMethods(mn, chosenReceiver != null ? chosenReceiver.getType() : null, args, call)).size() == 1) {
                        ClassNode irtg;
                        MethodNode targetMethodCandidate = mn.get(0);
                        ClassNode classNode = targetMethodCandidate.getDeclaringClass();
                        if (!targetMethodCandidate.isStatic() && !ClassHelper.isClassType(classNode) && objectExpression instanceof ClassExpression && call.getNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION) == null) {
                            this.addStaticTypeError("Non-static method " + StaticTypeCheckingSupport.prettyPrintTypeName(classNode) + "#" + targetMethodCandidate.getName() + " cannot be called from static context", call);
                        } else if (targetMethodCandidate.isAbstract() && StaticTypeCheckingVisitor.isSuperExpression(objectExpression)) {
                            String target = StaticTypeCheckingSupport.toMethodParametersString(targetMethodCandidate.getName(), StaticTypeCheckingVisitor.extractTypesFromParameters(targetMethodCandidate.getParameters()));
                            if (Traits.hasDefaultImplementation(targetMethodCandidate)) {
                                this.addStaticTypeError("Default method " + target + " requires qualified super", call);
                            } else {
                                this.addStaticTypeError("Abstract method " + target + " cannot be called directly", call);
                            }
                        }
                        if (chosenReceiver == null) {
                            chosenReceiver = Receiver.make(classNode);
                        }
                        boolean mergeType = call.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE) != null;
                        this.storeTargetMethod(call, targetMethodCandidate);
                        this.visitMethodCallArguments(chosenReceiver.getType(), argumentList, true, targetMethodCandidate);
                        callArgsVisited = true;
                        ClassNode returnType = this.getType(targetMethodCandidate);
                        if (StaticTypeCheckingSupport.isUsingGenericsOrIsArrayUsingGenerics(returnType) && (irtg = this.inferReturnTypeGenerics(chosenReceiver.getType(), targetMethodCandidate, callArguments, call.getGenericsTypes())) != null && StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(irtg, returnType)) {
                            returnType = irtg;
                        }
                        if (targetMethodCandidate == GET_DELEGATE && this.typeCheckingContext.getEnclosingClosure() != null) {
                            DelegationMetadata md = this.getDelegationMetadata(this.typeCheckingContext.getEnclosingClosure().getClosureExpression());
                            returnType = md != null ? md.getType() : this.typeCheckingContext.getEnclosingClassNode();
                        }
                        Parameter[] parameters = targetMethodCandidate.getParameters();
                        if (chosenReceiver.getType().getGenericsTypes() != null && !targetMethodCandidate.isStatic() && !(targetMethodCandidate instanceof ExtensionMethodNode)) {
                            Map<GenericsType.GenericsTypeName, GenericsType> context = StaticTypeCheckingVisitor.extractPlaceHoldersVisibleToDeclaration(chosenReceiver.getType(), targetMethodCandidate, argumentList);
                            parameters = (Parameter[])Arrays.stream(parameters).map(p -> new Parameter(StaticTypeCheckingSupport.applyGenericsContext(context, p.getType()), p.getName())).toArray(Parameter[]::new);
                        }
                        this.resolvePlaceholdersFromImplicitTypeHints(args, argumentList, parameters);
                        if (this.typeCheckMethodsWithGenericsOrFail(chosenReceiver.getType(), args, targetMethodCandidate, call)) {
                            String data = (String)chosenReceiver.getData();
                            if (data != null) {
                                call.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, data);
                            }
                            receiver = chosenReceiver.getType();
                            if (mergeType || call.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE) == null) {
                                this.storeType(call, StaticTypeCheckingVisitor.adjustWithTraits(targetMethodCandidate, receiver, args, returnType));
                            }
                            if (objectExpression instanceof VariableExpression && ((VariableExpression)objectExpression).isClosureSharedVariable()) {
                                this.typeCheckingContext.secondPassExpressions.add(new SecondPassExpression<ClassNode[]>(call, args));
                            }
                        } else {
                            call.removeNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
                        }
                    } else {
                        this.addAmbiguousErrorMessage(mn, name, args, call);
                    }
                }
            }
            if (args.length == 1 && ClassHelper.isNumberType(args[0]) && ClassHelper.isNumberType(receiver) && StaticTypeCheckingSupport.NUMBER_OPS.containsKey(name) && (resultType = StaticTypeCheckingVisitor.getMathResultType(StaticTypeCheckingSupport.NUMBER_OPS.get(name), receiver, args[0], name)) != null) {
                this.storeType(call, resultType);
            }
            MethodNode target = (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
            if (!callArgsVisited) {
                this.visitMethodCallArguments(receiver, argumentList, true, target);
            }
            if (target != null) {
                this.checkClosureMetadata(argumentList.getExpressions(), target.getParameters());
            }
        }
        finally {
            this.typeCheckingContext.popEnclosingMethodCall();
            this.extension.afterMethodCall(call);
        }
    }

    private void checkClosureMetadata(List<Expression> arguments, Parameter[] parameters) {
        int n = Math.min(arguments.size(), parameters.length);
        for (int i = 0; i < n; ++i) {
            int outgoingStrategy;
            int incomingStrategy;
            Expression argument = arguments.get(i);
            ClassNode aType = this.getType(argument);
            ClassNode pType = parameters[i].getType();
            if (!ClassHelper.CLOSURE_TYPE.equals(aType) || !ClassHelper.CLOSURE_TYPE.equals(pType) || !(argument instanceof VariableExpression) || !(((VariableExpression)argument).getAccessedVariable() instanceof Parameter) || (incomingStrategy = this.getResolveStrategy((Parameter)((VariableExpression)argument).getAccessedVariable())) == (outgoingStrategy = this.getResolveStrategy(parameters[i]))) continue;
            this.addStaticTypeError("Closure parameter with resolve strategy " + ClosureUtils.getResolveStrategyName(incomingStrategy) + " passed to method with resolve strategy " + ClosureUtils.getResolveStrategyName(outgoingStrategy), argument);
        }
    }

    private int getResolveStrategy(Parameter parameter) {
        Expression strategy;
        List<AnnotationNode> annotations = parameter.getAnnotations(DELEGATES_TO);
        if (annotations != null && !annotations.isEmpty() && (strategy = annotations.get(0).getMember("strategy")) != null) {
            return (Integer)StaticTypeCheckingSupport.evaluateExpression(GeneralUtils.castX(ClassHelper.Integer_TYPE, strategy), this.getSourceUnit().getConfiguration());
        }
        return 0;
    }

    private void inferMethodReferenceType(ClassNode receiver, ArgumentListExpression argumentList, MethodNode selectedMethod) {
        if (receiver == null) {
            return;
        }
        if (argumentList == null) {
            return;
        }
        if (selectedMethod == null) {
            return;
        }
        List<Expression> argumentExpressions = argumentList.getExpressions();
        if (argumentExpressions == null || argumentExpressions.stream().noneMatch(e -> e instanceof MethodReferenceExpression)) {
            return;
        }
        Parameter[] parameters = selectedMethod.getParameters();
        int nthParameter = parameters.length - 1;
        LinkedList<Integer> methodReferencePositions = new LinkedList<Integer>();
        LinkedList<Expression> newArgumentExpressions = new LinkedList<Expression>();
        int n = argumentExpressions.size();
        for (int i = 0; i < n; ++i) {
            Expression argumentExpression = argumentExpressions.get(i);
            if (!(argumentExpression instanceof MethodReferenceExpression)) {
                newArgumentExpressions.add(argumentExpression);
                continue;
            }
            Parameter param = parameters[Math.min(i, nthParameter)];
            ClassNode paramType = param.getType();
            if (i >= nthParameter && paramType.isArray()) {
                paramType = paramType.getComponentType();
            }
            if (!ClassHelper.isFunctionalInterface(paramType.redirect())) {
                this.addError("The argument is a method reference, but the parameter type is not a functional interface", argumentExpression);
                newArgumentExpressions.add(argumentExpression);
                continue;
            }
            methodReferencePositions.add(i);
            newArgumentExpressions.add(this.constructLambdaExpressionForMethodReference(paramType));
        }
        if (methodReferencePositions.isEmpty()) {
            return;
        }
        this.visitMethodCallArguments(receiver, GeneralUtils.args(newArgumentExpressions), true, selectedMethod);
        Iterator iterator = methodReferencePositions.iterator();
        while (iterator.hasNext()) {
            int index = (Integer)iterator.next();
            Expression lambdaExpression = (Expression)newArgumentExpressions.get(index);
            Expression methodReferenceExpression = argumentExpressions.get(index);
            methodReferenceExpression.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, lambdaExpression.getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS));
        }
    }

    private LambdaExpression constructLambdaExpressionForMethodReference(ClassNode functionalInterfaceType) {
        Parameter[] parameters = (Parameter[])ClassHelper.findSAM(functionalInterfaceType).getParameters().clone();
        int n = parameters.length;
        for (int i = 0; i < n; ++i) {
            parameters[i] = new Parameter(ClassHelper.dynamicType(), "p" + System.nanoTime());
        }
        return new LambdaExpression(parameters, EmptyStatement.INSTANCE);
    }

    private static ClassNode adjustWithTraits(MethodNode directMethodCallCandidate, ClassNode receiver, ClassNode[] args, ClassNode returnType) {
        ExtensionMethodNode emn;
        if (directMethodCallCandidate instanceof ExtensionMethodNode && "withTraits".equals((emn = (ExtensionMethodNode)directMethodCallCandidate).getName()) && "DefaultGroovyMethods".equals(emn.getExtensionMethodNode().getDeclaringClass().getNameWithoutPackage())) {
            LinkedList<ClassNode> nodes = new LinkedList<ClassNode>();
            Collections.addAll(nodes, receiver.getInterfaces());
            for (ClassNode arg : args) {
                if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(arg)) {
                    nodes.add(arg.getGenericsTypes()[0].getType());
                    continue;
                }
                nodes.add(arg);
            }
            return new WideningCategories.LowestUpperBoundClassNode(returnType.getName() + "Composed", ClassHelper.OBJECT_TYPE, nodes.toArray(ClassNode.EMPTY_ARRAY));
        }
        return returnType;
    }

    private static void addArrayMethods(List<MethodNode> methods, ClassNode receiver, String name, ClassNode[] args) {
        if (args.length != 1) {
            return;
        }
        if (!receiver.isArray()) {
            return;
        }
        if (!WideningCategories.isIntCategory(ClassHelper.getUnwrapper(args[0]))) {
            return;
        }
        if ("getAt".equals(name)) {
            MethodNode node = new MethodNode(name, 1, receiver.getComponentType(), new Parameter[]{new Parameter(args[0], "arg")}, null, null);
            node.setDeclaringClass(receiver.redirect());
            methods.add(node);
        } else if ("setAt".equals(name)) {
            MethodNode node = new MethodNode(name, 1, ClassHelper.VOID_TYPE, new Parameter[]{new Parameter(args[0], "arg")}, null, null);
            node.setDeclaringClass(receiver.redirect());
            methods.add(node);
        }
    }

    protected ClassNode getInferredReturnTypeFromWithClosureArgument(Expression callArguments) {
        if (!(callArguments instanceof ArgumentListExpression)) {
            return null;
        }
        ArgumentListExpression argList = (ArgumentListExpression)callArguments;
        ClosureExpression closure = (ClosureExpression)argList.getExpression(0);
        this.visitClosureExpression(closure);
        return this.getInferredReturnType(closure);
    }

    protected List<Receiver<String>> makeOwnerList(Expression objectExpression) {
        ClassNode receiver = this.getType(objectExpression);
        ArrayList<Receiver<String>> owners = new ArrayList<Receiver<String>>();
        if (this.typeCheckingContext.delegationMetadata != null && objectExpression instanceof VariableExpression && ((VariableExpression)objectExpression).getName().equals("owner") && this.typeCheckingContext.delegationMetadata.getParent() != null) {
            List<Receiver<String>> enclosingClass = Collections.singletonList(Receiver.make(this.typeCheckingContext.getEnclosingClassNode()));
            StaticTypeCheckingVisitor.addReceivers(owners, enclosingClass, this.typeCheckingContext.delegationMetadata.getParent(), "owner.");
        } else {
            List<ClassNode> potentialReceiverType;
            if (!this.typeCheckingContext.temporaryIfBranchTypeInformation.isEmpty() && (potentialReceiverType = this.getTemporaryTypesForExpression(objectExpression)) != null && !potentialReceiverType.isEmpty()) {
                for (ClassNode node : potentialReceiverType) {
                    owners.add(Receiver.make(node));
                }
            }
            if (this.typeCheckingContext.lastImplicitItType != null && objectExpression instanceof VariableExpression && ((VariableExpression)objectExpression).getName().equals("it")) {
                owners.add(Receiver.make(this.typeCheckingContext.lastImplicitItType));
            }
            if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(receiver)) {
                ClassNode staticType = receiver.getGenericsTypes()[0].getType();
                owners.add(Receiver.make(staticType));
                StaticTypeCheckingVisitor.addTraitType(staticType, owners);
                owners.add(Receiver.make(receiver));
            } else {
                StaticTypeCheckingVisitor.addBoundType(receiver, owners);
                StaticTypeCheckingVisitor.addSelfTypes(receiver, owners);
                StaticTypeCheckingVisitor.addTraitType(receiver, owners);
                if (receiver.redirect().isInterface()) {
                    owners.add(Receiver.make(ClassHelper.OBJECT_TYPE));
                } else if (StaticTypeCheckingVisitor.isSuperExpression(objectExpression)) {
                    for (ClassNode in : this.typeCheckingContext.getEnclosingClassNode().getInterfaces()) {
                        if (receiver.implementsInterface(in)) continue;
                        owners.add(Receiver.make(in));
                    }
                }
            }
        }
        return owners;
    }

    private static void addBoundType(ClassNode receiver, List<Receiver<String>> owners) {
        if (!receiver.isGenericsPlaceHolder() || receiver.getGenericsTypes() == null) {
            owners.add(Receiver.make(receiver));
            return;
        }
        GenericsType gt = receiver.getGenericsTypes()[0];
        if (gt.getLowerBound() == null && gt.getUpperBounds() != null) {
            for (ClassNode cn : gt.getUpperBounds()) {
                StaticTypeCheckingVisitor.addBoundType(cn, owners);
                StaticTypeCheckingVisitor.addSelfTypes(cn, owners);
            }
        } else {
            owners.add(Receiver.make(ClassHelper.OBJECT_TYPE));
        }
    }

    private static void addSelfTypes(ClassNode receiver, List<Receiver<String>> owners) {
        for (ClassNode selfType : Traits.collectSelfTypes(receiver, new LinkedHashSet<ClassNode>())) {
            owners.add(Receiver.make(selfType));
        }
    }

    private static void addTraitType(ClassNode receiver, List<Receiver<String>> owners) {
        if (Traits.isTrait(receiver.getOuterClass()) && receiver.getName().endsWith("$Helper")) {
            ClassNode traitType = receiver.getOuterClass();
            owners.add(Receiver.make(traitType));
            StaticTypeCheckingVisitor.addSelfTypes(traitType, owners);
        }
    }

    protected void checkForbiddenSpreadArgument(ArgumentListExpression argumentList) {
        for (Expression arg : argumentList.getExpressions()) {
            if (!(arg instanceof SpreadExpression)) continue;
            this.addStaticTypeError("The spread operator cannot be used as argument of method or closure calls with static type checking because the number of arguments cannot be determined at compile time", arg);
        }
    }

    protected List<ClassNode> getTemporaryTypesForExpression(Expression objectExpression) {
        List classNodes = null;
        int depth = this.typeCheckingContext.temporaryIfBranchTypeInformation.size();
        while (classNodes == null && depth > 0) {
            Map tempo = (Map)this.typeCheckingContext.temporaryIfBranchTypeInformation.get(--depth);
            Object key = objectExpression instanceof ParameterVariableExpression ? ((ParameterVariableExpression)objectExpression).parameter : this.extractTemporaryTypeInfoKey(objectExpression);
            classNodes = (List)tempo.get(key);
        }
        return classNodes;
    }

    protected void storeTargetMethod(Expression call, MethodNode target) {
        if (target == null) {
            call.removeNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
            return;
        }
        call.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, target);
        this.checkInterfaceStaticCall(call, target);
        this.checkOrMarkPrivateAccess(call, target);
        this.checkSuperCallFromClosure(call, target);
        this.extension.onMethodSelection(call, target);
    }

    private void checkInterfaceStaticCall(Expression call, MethodNode target) {
        ClassNode type;
        Expression objectExpression;
        if (target instanceof ExtensionMethodNode) {
            return;
        }
        ClassNode declaringClass = target.getDeclaringClass();
        if (declaringClass.isInterface() && target.isStatic() && (objectExpression = StaticTypeCheckingVisitor.getObjectExpression(call)) != null && (!StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(type = this.getType(objectExpression)) || !type.getGenericsTypes()[0].getType().equals(declaringClass))) {
            this.addStaticTypeError("static method of interface " + StaticTypeCheckingSupport.prettyPrintTypeName(declaringClass) + " can only be accessed with class qualifier", call);
        }
    }

    private void checkSuperCallFromClosure(Expression call, MethodNode target) {
        if (StaticTypeCheckingVisitor.isSuperExpression(StaticTypeCheckingVisitor.getObjectExpression(call)) && this.typeCheckingContext.getEnclosingClosure() != null) {
            ClassNode current = this.typeCheckingContext.getEnclosingClassNode();
            current.getNodeMetaData((Object)StaticTypesMarker.SUPER_MOP_METHOD_REQUIRED, x -> new LinkedList()).add(target);
            call.putNodeMetaData((Object)StaticTypesMarker.SUPER_MOP_METHOD_REQUIRED, current);
        }
    }

    private static Expression getObjectExpression(Expression expression) {
        if (expression instanceof MethodCallExpression) {
            return ((MethodCallExpression)expression).getObjectExpression();
        }
        if (expression instanceof PropertyExpression) {
            return ((PropertyExpression)expression).getObjectExpression();
        }
        return null;
    }

    protected void typeCheckClosureCall(Expression arguments, ClassNode[] argumentTypes, Parameter[] parameters) {
        if (StaticTypeCheckingSupport.allParametersAndArgumentsMatchWithDefaultParams(parameters, argumentTypes) < 0 && StaticTypeCheckingSupport.lastArgMatchesVarg(parameters, argumentTypes) < 0) {
            this.addStaticTypeError("Cannot call closure that accepts " + StaticTypeCheckingVisitor.formatArgumentList(StaticTypeCheckingVisitor.extractTypesFromParameters(parameters)) + " with " + StaticTypeCheckingVisitor.formatArgumentList(argumentTypes), arguments);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitIfElse(IfStatement ifElse) {
        Map<VariableExpression, List<ClassNode>> oldTracker = this.pushAssignmentTracking();
        try {
            this.typeCheckingContext.pushTemporaryTypeInfo();
            this.visitStatement(ifElse);
            ifElse.getBooleanExpression().visit(this);
            ifElse.getIfBlock().visit(this);
            this.typeCheckingContext.popTemporaryTypeInfo();
            this.restoreTypeBeforeConditional();
            ifElse.getElseBlock().visit(this);
            Map updates = (Map)ifElse.getElseBlock().getNodeMetaData("assignments");
            if (updates != null) {
                updates.forEach(this::recordAssignment);
            }
        }
        finally {
            ifElse.putNodeMetaData("assignments", this.popAssignmentTracking(oldTracker));
        }
        if (!this.typeCheckingContext.enclosingBlocks.isEmpty()) {
            BinaryExpression instanceOfExpression = this.findInstanceOfNotReturnExpression(ifElse);
            if (instanceOfExpression == null) {
                instanceOfExpression = this.findNotInstanceOfReturnExpression(ifElse);
            }
            if (instanceOfExpression != null) {
                this.visitInstanceofNot(instanceOfExpression);
            }
        }
    }

    protected void visitInstanceofNot(BinaryExpression be) {
        BlockStatement currentBlock = this.typeCheckingContext.enclosingBlocks.getFirst();
        assert (currentBlock != null);
        if (!this.typeCheckingContext.blockStatements2Types.containsKey(currentBlock)) {
            Map<VariableExpression, List<ClassNode>> oldTracker = this.pushAssignmentTracking();
            this.getTypeCheckingContext().pushTemporaryTypeInfo();
            this.typeCheckingContext.blockStatements2Types.put(currentBlock, oldTracker);
        }
        this.pushInstanceOfTypeInfo(be.getLeftExpression(), be.getRightExpression());
    }

    @Override
    public void visitBlockStatement(BlockStatement block) {
        if (block != null) {
            this.typeCheckingContext.enclosingBlocks.addFirst(block);
        }
        super.visitBlockStatement(block);
        if (block != null) {
            this.visitClosingBlock(block);
        }
    }

    public void visitClosingBlock(BlockStatement block) {
        BlockStatement peekBlock = this.typeCheckingContext.enclosingBlocks.removeFirst();
        boolean found = this.typeCheckingContext.blockStatements2Types.containsKey(peekBlock);
        if (found) {
            Map<VariableExpression, List<ClassNode>> oldTracker = this.typeCheckingContext.blockStatements2Types.remove(peekBlock);
            this.getTypeCheckingContext().popTemporaryTypeInfo();
            this.popAssignmentTracking(oldTracker);
        }
    }

    protected BinaryExpression findInstanceOfNotReturnExpression(IfStatement ifElse) {
        Statement elseBlock = ifElse.getElseBlock();
        if (!(elseBlock instanceof EmptyStatement)) {
            return null;
        }
        Expression conditionExpression = ifElse.getBooleanExpression().getExpression();
        if (!(conditionExpression instanceof NotExpression)) {
            return null;
        }
        NotExpression notExpression = (NotExpression)conditionExpression;
        Expression expression = notExpression.getExpression();
        if (!(expression instanceof BinaryExpression)) {
            return null;
        }
        BinaryExpression instanceOfExpression = (BinaryExpression)expression;
        int op = instanceOfExpression.getOperation().getType();
        if (op != 544) {
            return null;
        }
        if (StaticTypeCheckingVisitor.notReturningBlock(ifElse.getIfBlock())) {
            return null;
        }
        return instanceOfExpression;
    }

    protected BinaryExpression findNotInstanceOfReturnExpression(IfStatement ifElse) {
        Statement elseBlock = ifElse.getElseBlock();
        if (!(elseBlock instanceof EmptyStatement)) {
            return null;
        }
        Expression conditionExpression = ifElse.getBooleanExpression().getExpression();
        if (!(conditionExpression instanceof BinaryExpression)) {
            return null;
        }
        BinaryExpression instanceOfExpression = (BinaryExpression)conditionExpression;
        int op = instanceOfExpression.getOperation().getType();
        if (op != 130) {
            return null;
        }
        if (StaticTypeCheckingVisitor.notReturningBlock(ifElse.getIfBlock())) {
            return null;
        }
        return instanceOfExpression;
    }

    private static boolean notReturningBlock(Statement statement) {
        return statement.isEmpty() || !(statement instanceof BlockStatement) || !(DefaultGroovyMethods.last(((BlockStatement)statement).getStatements()) instanceof ReturnStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitSwitch(SwitchStatement statement) {
        this.typeCheckingContext.pushEnclosingSwitchStatement(statement);
        try {
            Map<VariableExpression, List<ClassNode>> oldTracker = this.pushAssignmentTracking();
            try {
                super.visitSwitch(statement);
            }
            finally {
                this.popAssignmentTracking(oldTracker);
            }
        }
        finally {
            this.typeCheckingContext.popEnclosingSwitchStatement();
        }
    }

    @Override
    protected void afterSwitchConditionExpressionVisited(SwitchStatement statement) {
        Expression conditionExpression = statement.getExpression();
        conditionExpression.putNodeMetaData((Object)StaticTypesMarker.TYPE, this.getType(conditionExpression));
    }

    @Override
    public void visitCaseStatement(CaseStatement statement) {
        Expression expression = statement.getExpression();
        if (expression instanceof ClosureExpression) {
            SwitchStatement switchStatement = this.typeCheckingContext.getEnclosingSwitchStatement();
            ClassNode inf = (ClassNode)switchStatement.getExpression().getNodeMetaData((Object)StaticTypesMarker.TYPE);
            expression.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, new ClassNode[]{inf});
            Parameter[] params = ((ClosureExpression)expression).getParameters();
            if (params != null && params.length == 1) {
                boolean lambda = expression instanceof LambdaExpression;
                this.checkParamType(params[0], StaticTypeCheckingVisitor.wrapTypeIfNecessary(inf), false, lambda);
            } else if (params == null || params.length > 1) {
                int paramCount = params != null ? params.length : 0;
                this.addError("Incorrect number of parameters. Expected 1 but found " + paramCount, expression);
            }
        }
        super.visitCaseStatement(statement);
        this.restoreTypeBeforeConditional();
    }

    private void recordAssignment(VariableExpression lhsExpr, ClassNode rhsType) {
        this.typeCheckingContext.ifElseForWhileAssignmentTracker.computeIfAbsent(lhsExpr, lhs -> {
            ClassNode lhsType = (ClassNode)lhs.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            ArrayList<ClassNode> types = new ArrayList<ClassNode>(2);
            types.add(lhsType);
            return types;
        }).add(rhsType);
    }

    private void restoreTypeBeforeConditional() {
        this.typeCheckingContext.ifElseForWhileAssignmentTracker.forEach((var, types) -> var.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, types.get(0)));
    }

    protected Map<VariableExpression, ClassNode> popAssignmentTracking(Map<VariableExpression, List<ClassNode>> oldTracker) {
        HashMap<VariableExpression, ClassNode> assignments = new HashMap<VariableExpression, ClassNode>();
        this.typeCheckingContext.ifElseForWhileAssignmentTracker.forEach((var, types) -> types.stream().filter(t -> t != null && t != StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE).reduce(WideningCategories::lowestUpperBound).ifPresent(type -> {
            assignments.put((VariableExpression)var, (ClassNode)type);
            this.storeType((Expression)var, (ClassNode)type);
        }));
        this.typeCheckingContext.ifElseForWhileAssignmentTracker = oldTracker;
        return assignments;
    }

    protected Map<VariableExpression, List<ClassNode>> pushAssignmentTracking() {
        Map<VariableExpression, List<ClassNode>> oldTracker = this.typeCheckingContext.ifElseForWhileAssignmentTracker;
        this.typeCheckingContext.ifElseForWhileAssignmentTracker = new HashMap<VariableExpression, List<ClassNode>>();
        return oldTracker;
    }

    @Override
    public void visitArrayExpression(ArrayExpression expression) {
        List<Expression> expressions;
        ClassNode elementType;
        super.visitArrayExpression(expression);
        if (expression.hasInitializer()) {
            elementType = expression.getElementType();
            expressions = expression.getExpressions();
        } else {
            elementType = ClassHelper.int_TYPE;
            expressions = expression.getSizeExpression();
        }
        for (Expression elementExpr : expressions) {
            if (StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(elementType, this.getType(elementExpr), elementExpr, false)) continue;
            this.addStaticTypeError("Cannot convert from " + StaticTypeCheckingSupport.prettyPrintType(this.getType(elementExpr)) + " to " + StaticTypeCheckingSupport.prettyPrintType(elementType), elementExpr);
        }
    }

    @Override
    public void visitCastExpression(CastExpression expression) {
        ClassNode target = expression.getType();
        Expression source = expression.getExpression();
        this.applyTargetType(target, source);
        source.visit(this);
        if (!expression.isCoerce() && !this.checkCast(target, source)) {
            this.addStaticTypeError("Inconvertible types: cannot cast " + StaticTypeCheckingSupport.prettyPrintType(this.getType(source)) + " to " + StaticTypeCheckingSupport.prettyPrintType(target), expression);
        }
    }

    protected boolean checkCast(ClassNode targetType, Expression source) {
        boolean sourceIsNull = StaticTypeCheckingVisitor.isNullConstant(source);
        ClassNode expressionType = this.getType(source);
        if (targetType.isArray() && expressionType.isArray()) {
            return this.checkCast(targetType.getComponentType(), GeneralUtils.varX("foo", expressionType.getComponentType()));
        }
        if (!(ClassHelper.isPrimitiveChar(targetType) && ClassHelper.isStringType(expressionType) && source instanceof ConstantExpression && source.getText().length() == 1 || ClassHelper.isWrapperCharacter(targetType) && (ClassHelper.isStringType(expressionType) || sourceIsNull) && (sourceIsNull || source instanceof ConstantExpression && source.getText().length() == 1) || WideningCategories.isNumberCategory(ClassHelper.getWrapper(targetType)) && (WideningCategories.isNumberCategory(ClassHelper.getWrapper(expressionType)) || ClassHelper.isPrimitiveChar(expressionType)) || sourceIsNull && !ClassHelper.isPrimitiveType(targetType) || ClassHelper.isPrimitiveChar(targetType) && ClassHelper.isPrimitiveType(expressionType) && ClassHelper.isNumberType(expressionType))) {
            if (sourceIsNull && ClassHelper.isPrimitiveType(targetType) && !ClassHelper.isPrimitiveBoolean(targetType)) {
                return false;
            }
            if ((expressionType.getModifiers() & 0x10) == 0 && targetType.isInterface()) {
                return true;
            }
            if ((targetType.getModifiers() & 0x10) == 0 && expressionType.isInterface()) {
                return true;
            }
            if (!StaticTypeCheckingSupport.isAssignableTo(targetType, expressionType) && !StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(expressionType, targetType)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void visitTernaryExpression(TernaryExpression expression) {
        ClassNode resultType;
        Map<VariableExpression, List<ClassNode>> oldTracker = this.pushAssignmentTracking();
        this.typeCheckingContext.pushTemporaryTypeInfo();
        if (!(expression instanceof ElvisOperatorExpression)) {
            expression.getBooleanExpression().visit(this);
        }
        Expression trueExpression = expression.getTrueExpression();
        ClassNode typeOfTrue = this.findCurrentInstanceOfClass(trueExpression, null);
        trueExpression.visit(this);
        if (typeOfTrue == null) {
            typeOfTrue = this.getType(trueExpression);
        }
        this.typeCheckingContext.popTemporaryTypeInfo();
        Expression falseExpression = expression.getFalseExpression();
        falseExpression.visit(this);
        ClassNode typeOfFalse = this.getType(falseExpression);
        if (StaticTypeCheckingVisitor.isNullConstant(trueExpression) && StaticTypeCheckingVisitor.isNullConstant(falseExpression)) {
            resultType = this.checkForTargetType(trueExpression, StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE);
        } else if (StaticTypeCheckingVisitor.isNullConstant(trueExpression) || StaticTypeCheckingVisitor.isEmptyCollection(trueExpression) && GeneralUtils.isOrImplements(typeOfTrue, typeOfFalse)) {
            resultType = StaticTypeCheckingVisitor.wrapTypeIfNecessary(this.checkForTargetType(falseExpression, typeOfFalse));
        } else if (StaticTypeCheckingVisitor.isNullConstant(falseExpression) || StaticTypeCheckingVisitor.isEmptyCollection(falseExpression) && GeneralUtils.isOrImplements(typeOfFalse, typeOfTrue)) {
            resultType = StaticTypeCheckingVisitor.wrapTypeIfNecessary(this.checkForTargetType(trueExpression, typeOfTrue));
        } else {
            typeOfFalse = this.checkForTargetType(falseExpression, typeOfFalse);
            typeOfTrue = this.checkForTargetType(trueExpression, typeOfTrue);
            resultType = WideningCategories.lowestUpperBound(typeOfTrue, typeOfFalse);
        }
        this.storeType(expression, resultType);
        this.popAssignmentTracking(oldTracker);
    }

    private ClassNode checkForTargetType(Expression expr, ClassNode type) {
        ClassNode sourceType = Optional.ofNullable(this.getInferredReturnType(expr)).orElse(type);
        ClassNode targetType = null;
        MethodNode enclosingMethod = this.typeCheckingContext.getEnclosingMethod();
        BinaryExpression enclosingExpression = this.typeCheckingContext.getEnclosingBinaryExpression();
        if (enclosingExpression != null && StaticTypeCheckingSupport.isAssignment(enclosingExpression.getOperation().getType()) && StaticTypeCheckingVisitor.isTypeSource(expr, enclosingExpression.getRightExpression())) {
            targetType = this.getDeclaredOrInferredType(enclosingExpression.getLeftExpression());
        } else if (enclosingMethod != null && !enclosingMethod.isAbstract() && !enclosingMethod.isVoidMethod() && StaticTypeCheckingVisitor.isTypeSource(expr, enclosingMethod)) {
            targetType = enclosingMethod.getReturnType();
        }
        if (expr instanceof ConstructorCallExpression) {
            if (targetType == null) {
                targetType = sourceType.getPlainNodeReference();
            }
            this.inferDiamondType((ConstructorCallExpression)expr, targetType);
            return sourceType;
        }
        if (targetType == null) {
            return sourceType;
        }
        if (!ClassHelper.isPrimitiveType(ClassHelper.getUnwrapper(targetType)) && !ClassHelper.isObjectType(targetType) && !sourceType.isGenericsPlaceHolder() && StaticTypeCheckingSupport.missesGenericsTypes(sourceType)) {
            return GenericsUtils.parameterizeType(targetType, sourceType.getPlainNodeReference());
        }
        return sourceType != StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE ? sourceType : targetType;
    }

    private static boolean isTypeSource(Expression expr, Expression right) {
        if (right instanceof TernaryExpression) {
            return StaticTypeCheckingVisitor.isTypeSource(expr, ((TernaryExpression)right).getTrueExpression()) || StaticTypeCheckingVisitor.isTypeSource(expr, ((TernaryExpression)right).getFalseExpression());
        }
        return expr == right;
    }

    private static boolean isTypeSource(final Expression expr, MethodNode mNode) {
        final boolean[] returned = new boolean[1];
        mNode.getCode().visit(new CodeVisitorSupport(){

            @Override
            public void visitReturnStatement(ReturnStatement returnStatement) {
                if (StaticTypeCheckingVisitor.isTypeSource(expr, returnStatement.getExpression())) {
                    returned[0] = true;
                }
            }

            @Override
            public void visitClosureExpression(ClosureExpression expression) {
            }
        });
        if (!returned[0]) {
            new ReturnAdder(returnStatement -> {
                if (StaticTypeCheckingVisitor.isTypeSource(expr, returnStatement.getExpression())) {
                    returned[0] = true;
                }
            }).visitMethod(mNode);
        }
        return returned[0];
    }

    private static boolean isEmptyCollection(Expression expr) {
        return StaticTypeCheckingVisitor.isEmptyList(expr) || StaticTypeCheckingVisitor.isEmptyMap(expr);
    }

    private static boolean isEmptyList(Expression expr) {
        return expr instanceof ListExpression && ((ListExpression)expr).getExpressions().isEmpty();
    }

    private static boolean isEmptyMap(Expression expr) {
        return expr instanceof MapExpression && ((MapExpression)expr).getMapEntryExpressions().isEmpty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitTryCatchFinally(TryCatchStatement statement) {
        List<CatchStatement> catchStatements = statement.getCatchStatements();
        for (CatchStatement catchStatement : catchStatements) {
            ClassNode exceptionType = catchStatement.getExceptionType();
            this.typeCheckingContext.controlStructureVariables.put(catchStatement.getVariable(), exceptionType);
        }
        try {
            super.visitTryCatchFinally(statement);
        }
        finally {
            for (CatchStatement catchStatement : catchStatements) {
                this.typeCheckingContext.controlStructureVariables.remove(catchStatement.getVariable());
            }
        }
    }

    protected void storeType(Expression exp, ClassNode cn) {
        ClassNode oldValue;
        if (cn == StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE) {
            cn = this.getOriginalDeclarationType(exp);
        }
        if (cn != null && ClassHelper.isPrimitiveType(cn)) {
            if (exp instanceof VariableExpression && ((VariableExpression)exp).isClosureSharedVariable()) {
                cn = ClassHelper.getWrapper(cn);
            } else if (exp instanceof MethodCallExpression && ((MethodCallExpression)exp).isSafe()) {
                cn = ClassHelper.getWrapper(cn);
            } else if (exp instanceof PropertyExpression && ((PropertyExpression)exp).isSafe()) {
                cn = ClassHelper.getWrapper(cn);
            }
        }
        if ((oldValue = (ClassNode)exp.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, cn)) != null) {
            ClassNode oldDIT = (ClassNode)exp.getNodeMetaData((Object)StaticTypesMarker.DECLARATION_INFERRED_TYPE);
            if (oldDIT != null) {
                exp.putNodeMetaData((Object)StaticTypesMarker.DECLARATION_INFERRED_TYPE, cn == null ? oldDIT : WideningCategories.lowestUpperBound(oldDIT, cn));
            } else {
                exp.putNodeMetaData((Object)StaticTypesMarker.DECLARATION_INFERRED_TYPE, cn == null ? null : WideningCategories.lowestUpperBound(oldValue, cn));
            }
        }
        if (exp instanceof VariableExpression) {
            List<ClassNode> temporaryTypesForExpression;
            VariableExpression var = (VariableExpression)exp;
            Variable accessedVariable = var.getAccessedVariable();
            if (accessedVariable instanceof VariableExpression) {
                if (accessedVariable != exp) {
                    this.storeType((VariableExpression)accessedVariable, cn);
                }
            } else if (accessedVariable instanceof Parameter || accessedVariable instanceof PropertyNode && ((PropertyNode)accessedVariable).getField().isSynthetic()) {
                ((AnnotatedNode)((Object)accessedVariable)).putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, cn);
            }
            if (cn != null && var.isClosureSharedVariable()) {
                List assignedTypes = this.typeCheckingContext.closureSharedVariablesAssignmentTypes.computeIfAbsent(var, k -> new LinkedList());
                assignedTypes.add(cn);
            }
            if (!this.typeCheckingContext.temporaryIfBranchTypeInformation.isEmpty() && (temporaryTypesForExpression = this.getTemporaryTypesForExpression(exp)) != null && !temporaryTypesForExpression.isEmpty()) {
                temporaryTypesForExpression.clear();
            }
        }
    }

    protected ClassNode getResultType(ClassNode left, int op, ClassNode right, BinaryExpression expr) {
        MethodNode method;
        ClassNode leftRedirect = left.redirect();
        ClassNode rightRedirect = right.redirect();
        Expression leftExpression = expr.getLeftExpression();
        Expression rightExpression = expr.getRightExpression();
        if (op == 100 || op == 217) {
            if (rightRedirect.isDerivedFrom(ClassHelper.CLOSURE_TYPE)) {
                MethodNode abstractMethod;
                ClosureExpression closureExpression = null;
                if (rightExpression instanceof ClosureExpression) {
                    closureExpression = (ClosureExpression)rightExpression;
                } else if (rightExpression instanceof MethodReferenceExpression) {
                    closureExpression = (ClosureExpression)rightExpression.getNodeMetaData((Object)StaticTypesMarker.CONSTRUCTED_LAMBDA_EXPRESSION);
                }
                if (closureExpression != null && (abstractMethod = ClassHelper.findSAM(left)) != null) {
                    return this.inferSAMTypeGenericsInAssignment(left, abstractMethod, right, closureExpression);
                }
            }
            if (leftExpression instanceof VariableExpression) {
                ClassNode initialType = this.getOriginalDeclarationType(leftExpression);
                if (ClassHelper.isPrimitiveType(rightRedirect) && initialType.isDerivedFrom(ClassHelper.Number_TYPE)) {
                    return ClassHelper.getWrapper(right);
                }
                if (ClassHelper.isPrimitiveType(initialType) && rightRedirect.isDerivedFrom(ClassHelper.Number_TYPE)) {
                    return ClassHelper.getUnwrapper(right);
                }
                if (StaticTypeCheckingSupport.isWildcardLeftHandSide(initialType) && !ClassHelper.isObjectType(initialType)) {
                    return initialType;
                }
            }
            if (!ClassHelper.isObjectType(leftRedirect)) {
                if (rightExpression instanceof ListExpression) {
                    if (ClassHelper.LIST_TYPE.equals(leftRedirect) || ITERABLE_TYPE.equals(leftRedirect) || StaticTypeCheckingSupport.Collection_TYPE.equals(leftRedirect) || StaticTypeCheckingSupport.ArrayList_TYPE.isDerivedFrom(leftRedirect)) {
                        return StaticTypeCheckingVisitor.getLiteralResultType(left, right, StaticTypeCheckingSupport.ArrayList_TYPE);
                    }
                    if (ClassHelper.SET_TYPE.equals(leftRedirect) || StaticTypeCheckingSupport.LinkedHashSet_TYPE.isDerivedFrom(leftRedirect)) {
                        return StaticTypeCheckingVisitor.getLiteralResultType(left, right, StaticTypeCheckingSupport.LinkedHashSet_TYPE);
                    }
                }
                if (rightExpression instanceof MapExpression && (ClassHelper.MAP_TYPE.equals(leftRedirect) || StaticTypeCheckingSupport.LinkedHashMap_TYPE.isDerivedFrom(leftRedirect))) {
                    return StaticTypeCheckingVisitor.getLiteralResultType(left, right, StaticTypeCheckingSupport.LinkedHashMap_TYPE);
                }
            }
            return right;
        }
        if (StaticTypeCheckingSupport.isBoolIntrinsicOp(op)) {
            return ClassHelper.boolean_TYPE;
        }
        if (op == 90) {
            return StaticTypeCheckingSupport.Matcher_TYPE;
        }
        if (StaticTypeCheckingSupport.isArrayOp(op)) {
            BinaryExpression newExpr = GeneralUtils.binX(leftExpression, expr.getOperation(), rightExpression);
            newExpr.setSourcePosition(expr);
            MethodNode method2 = this.findMethodOrFail(newExpr, left.getPlainNodeReference(), "getAt", right.getPlainNodeReference());
            if (method2 != null && StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(right, ClassHelper.RANGE_TYPE)) {
                return this.inferReturnTypeGenerics(left, method2, rightExpression);
            }
            return method2 != null ? this.inferComponentType(left, right) : null;
        }
        String operationName = StaticTypeCheckingSupport.getOperationName(op);
        if (operationName == null) {
            throw new GroovyBugError("Unknown result type for binary operator " + op);
        }
        ClassNode mathResultType = StaticTypeCheckingVisitor.getMathResultType(op, leftRedirect, rightRedirect, operationName);
        if (mathResultType != null) {
            return mathResultType;
        }
        if ("equals".equals(operationName) && (left == StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE || right == StaticTypeCheckingSupport.UNKNOWN_PARAMETER_TYPE)) {
            return ClassHelper.boolean_TYPE;
        }
        if (leftExpression instanceof ClassExpression) {
            left = ClassHelper.CLASS_Type.getPlainNodeReference();
        }
        if ((method = this.findMethodOrFail(expr, left, operationName, right)) != null) {
            this.storeTargetMethod(expr, method);
            this.typeCheckMethodsWithGenericsOrFail(left, new ClassNode[]{right}, method, expr);
            if (StaticTypeCheckingSupport.isAssignment(op)) {
                return left;
            }
            if (!"compareTo".equals(operationName)) {
                return this.inferReturnTypeGenerics(left, method, GeneralUtils.args(rightExpression));
            }
        }
        if (StaticTypeCheckingSupport.isCompareToBoolean(op)) {
            return ClassHelper.boolean_TYPE;
        }
        if (op == 128) {
            return ClassHelper.int_TYPE;
        }
        return null;
    }

    private static ClassNode getLiteralResultType(ClassNode targetType, ClassNode sourceType, ClassNode baseType) {
        ClassNode resultType;
        ClassNode classNode = resultType = sourceType.equals(baseType) ? sourceType : GenericsUtils.parameterizeType(sourceType, baseType.getPlainNodeReference());
        if (targetType.getGenericsTypes() != null && !GenericsUtils.buildWildcardType(targetType).isCompatibleWith(resultType)) {
            BiPredicate<GenericsType, GenericsType> isEqualOrSuper = (target, source) -> {
                if (target.isCompatibleWith(source.getType())) {
                    return true;
                }
                if (!target.isPlaceholder() && !target.isWildcard()) {
                    return GenericsUtils.buildWildcardType(StaticTypeCheckingSupport.getCombinedBoundType(target)).isCompatibleWith(source.getType());
                }
                return false;
            };
            GenericsType[] lgt = targetType.getGenericsTypes();
            GenericsType[] rgt = resultType.getGenericsTypes();
            if (IntStream.range(0, lgt.length).allMatch(i -> isEqualOrSuper.test(lgt[i], rgt[i]))) {
                resultType = GenericsUtils.parameterizeType(targetType, baseType.getPlainNodeReference());
            }
        }
        return resultType;
    }

    private static ClassNode getMathResultType(int op, ClassNode leftRedirect, ClassNode rightRedirect, String operationName) {
        if (ClassHelper.isNumberType(leftRedirect) && ClassHelper.isNumberType(rightRedirect)) {
            if (StaticTypeCheckingSupport.isOperationInGroup(op)) {
                if (WideningCategories.isIntCategory(leftRedirect) && WideningCategories.isIntCategory(rightRedirect)) {
                    return ClassHelper.int_TYPE;
                }
                if (WideningCategories.isLongCategory(leftRedirect) && WideningCategories.isLongCategory(rightRedirect)) {
                    return ClassHelper.long_TYPE;
                }
                if (WideningCategories.isFloat(leftRedirect) && WideningCategories.isFloat(rightRedirect)) {
                    return ClassHelper.float_TYPE;
                }
                if (WideningCategories.isDouble(leftRedirect) && WideningCategories.isDouble(rightRedirect)) {
                    return ClassHelper.double_TYPE;
                }
            } else {
                if (StaticTypeCheckingSupport.isPowerOperator(op)) {
                    return ClassHelper.Number_TYPE;
                }
                if (StaticTypeCheckingSupport.isBitOperator(op) || op == 204 || op == 214) {
                    if (WideningCategories.isIntCategory(ClassHelper.getUnwrapper(leftRedirect)) && WideningCategories.isIntCategory(ClassHelper.getUnwrapper(rightRedirect))) {
                        return ClassHelper.int_TYPE;
                    }
                    if (WideningCategories.isLongCategory(ClassHelper.getUnwrapper(leftRedirect)) && WideningCategories.isLongCategory(ClassHelper.getUnwrapper(rightRedirect))) {
                        return ClassHelper.long_TYPE;
                    }
                    if (WideningCategories.isBigIntCategory(ClassHelper.getUnwrapper(leftRedirect)) && WideningCategories.isBigIntCategory(ClassHelper.getUnwrapper(rightRedirect))) {
                        return ClassHelper.BigInteger_TYPE;
                    }
                } else if (StaticTypeCheckingSupport.isCompareToBoolean(op) || op == 123 || op == 120) {
                    return ClassHelper.boolean_TYPE;
                }
            }
        } else if (ClassHelper.isPrimitiveChar(leftRedirect) && ClassHelper.isPrimitiveChar(rightRedirect) && (StaticTypeCheckingSupport.isCompareToBoolean(op) || op == 123 || op == 120)) {
            return ClassHelper.boolean_TYPE;
        }
        if (StaticTypeCheckingSupport.isShiftOperation(operationName) && WideningCategories.isNumberCategory(leftRedirect) && (WideningCategories.isIntCategory(rightRedirect) || WideningCategories.isLongCategory(rightRedirect))) {
            return leftRedirect;
        }
        if (WideningCategories.isNumberCategory(ClassHelper.getWrapper(rightRedirect)) && WideningCategories.isNumberCategory(ClassHelper.getWrapper(leftRedirect)) && (203 == op || 213 == op)) {
            if (WideningCategories.isFloatingCategory(leftRedirect) || WideningCategories.isFloatingCategory(rightRedirect)) {
                if (!ClassHelper.isPrimitiveType(leftRedirect) || !ClassHelper.isPrimitiveType(rightRedirect)) {
                    return ClassHelper.Double_TYPE;
                }
                return ClassHelper.double_TYPE;
            }
            if (203 == op) {
                return ClassHelper.BigDecimal_TYPE;
            }
            return leftRedirect;
        }
        if (StaticTypeCheckingSupport.isOperationInGroup(op) && WideningCategories.isNumberCategory(ClassHelper.getWrapper(leftRedirect)) && WideningCategories.isNumberCategory(ClassHelper.getWrapper(rightRedirect))) {
            return StaticTypeCheckingVisitor.getGroupOperationResultType(leftRedirect, rightRedirect);
        }
        if (WideningCategories.isNumberCategory(ClassHelper.getWrapper(rightRedirect)) && WideningCategories.isNumberCategory(ClassHelper.getWrapper(leftRedirect)) && (205 == op || 215 == op)) {
            return leftRedirect;
        }
        return null;
    }

    private ClassNode inferSAMTypeGenericsInAssignment(ClassNode samType, MethodNode abstractMethod, ClassNode closureType, ClosureExpression closureExpression) {
        GenericsType[] samTypeGenerics = samType.getGenericsTypes();
        GenericsType[] closureGenerics = closureType.getGenericsTypes();
        if (samTypeGenerics == null || closureGenerics == null) {
            return samType;
        }
        HashMap<GenericsType.GenericsTypeName, GenericsType> connections = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
        StaticTypeCheckingSupport.extractGenericsConnections(connections, StaticTypeCheckingVisitor.wrapTypeIfNecessary(this.getInferredReturnType(closureExpression)), abstractMethod.getReturnType());
        if (closureExpression.isParameterSpecified()) {
            Parameter[] closureParams = closureExpression.getParameters();
            Parameter[] methodParams = abstractMethod.getParameters();
            int n = Math.min(closureParams.length, methodParams.length);
            for (int i = 0; i < n; ++i) {
                ClassNode closureParamType = closureParams[i].getType();
                ClassNode methodParamType = methodParams[i].getType();
                StaticTypeCheckingSupport.extractGenericsConnections(connections, closureParamType, methodParamType);
            }
        }
        return StaticTypeCheckingSupport.applyGenericsContext(connections, samType.redirect());
    }

    protected static ClassNode getGroupOperationResultType(ClassNode a, ClassNode b) {
        if (WideningCategories.isBigIntCategory(a) && WideningCategories.isBigIntCategory(b)) {
            return ClassHelper.BigInteger_TYPE;
        }
        if (WideningCategories.isBigDecCategory(a) && WideningCategories.isBigDecCategory(b)) {
            return ClassHelper.BigDecimal_TYPE;
        }
        if (ClassHelper.isBigDecimalType(a) || ClassHelper.isBigDecimalType(b)) {
            return ClassHelper.BigDecimal_TYPE;
        }
        if (ClassHelper.isBigIntegerType(a) || ClassHelper.isBigIntegerType(b)) {
            if (WideningCategories.isBigIntCategory(a) && WideningCategories.isBigIntCategory(b)) {
                return ClassHelper.BigInteger_TYPE;
            }
            return ClassHelper.BigDecimal_TYPE;
        }
        if (ClassHelper.isPrimitiveDouble(a) || ClassHelper.isPrimitiveDouble(b)) {
            return ClassHelper.double_TYPE;
        }
        if (ClassHelper.isWrapperDouble(a) || ClassHelper.isWrapperDouble(b)) {
            return ClassHelper.Double_TYPE;
        }
        if (ClassHelper.isPrimitiveFloat(a) || ClassHelper.isPrimitiveFloat(b)) {
            return ClassHelper.float_TYPE;
        }
        if (ClassHelper.isWrapperFloat(a) || ClassHelper.isWrapperFloat(b)) {
            return ClassHelper.Float_TYPE;
        }
        if (ClassHelper.isPrimitiveLong(a) || ClassHelper.isPrimitiveLong(b)) {
            return ClassHelper.long_TYPE;
        }
        if (ClassHelper.isWrapperLong(a) || ClassHelper.isWrapperLong(b)) {
            return ClassHelper.Long_TYPE;
        }
        if (ClassHelper.isPrimitiveInt(a) || ClassHelper.isPrimitiveInt(b)) {
            return ClassHelper.int_TYPE;
        }
        if (ClassHelper.isWrapperInteger(a) || ClassHelper.isWrapperInteger(b)) {
            return ClassHelper.Integer_TYPE;
        }
        if (ClassHelper.isPrimitiveShort(a) || ClassHelper.isPrimitiveShort(b)) {
            return ClassHelper.short_TYPE;
        }
        if (ClassHelper.isWrapperShort(a) || ClassHelper.isWrapperShort(b)) {
            return ClassHelper.Short_TYPE;
        }
        if (ClassHelper.isPrimitiveByte(a) || ClassHelper.isPrimitiveByte(b)) {
            return ClassHelper.byte_TYPE;
        }
        if (ClassHelper.isWrapperByte(a) || ClassHelper.isWrapperByte(b)) {
            return ClassHelper.Byte_TYPE;
        }
        if (ClassHelper.isPrimitiveChar(a) || ClassHelper.isPrimitiveChar(b)) {
            return ClassHelper.char_TYPE;
        }
        if (ClassHelper.isWrapperCharacter(a) || ClassHelper.isWrapperCharacter(b)) {
            return ClassHelper.Character_TYPE;
        }
        return ClassHelper.Number_TYPE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected ClassNode inferComponentType(ClassNode receiverType, ClassNode subscriptType) {
        ClassNode componentType = receiverType.getComponentType();
        if (componentType == null) {
            MethodCallExpression mce = subscriptType != null ? GeneralUtils.callX((Expression)GeneralUtils.varX("#", receiverType), "getAt", (Expression)GeneralUtils.varX("selector", subscriptType)) : GeneralUtils.callX(GeneralUtils.varX("#", receiverType), "iterator");
            mce.setImplicitThis(false);
            this.typeCheckingContext.pushErrorCollector();
            try {
                this.visitMethodCallExpression(mce);
            }
            finally {
                this.typeCheckingContext.popErrorCollector();
            }
            if (subscriptType != null) {
                componentType = this.getType(mce);
            } else {
                ClassNode iteratorType = this.getType(mce);
                if (GeneralUtils.isOrImplements(iteratorType, ClassHelper.Iterator_TYPE) && (iteratorType.getGenericsTypes() != null || !((MethodNode)mce.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET)).getDeclaringClass().equals(ClassHelper.OBJECT_TYPE))) {
                    componentType = Optional.ofNullable(iteratorType.getGenericsTypes()).map(gt -> StaticTypeCheckingSupport.getCombinedBoundType(gt[0])).orElse(ClassHelper.OBJECT_TYPE);
                }
            }
        }
        return componentType;
    }

    protected MethodNode findMethodOrFail(Expression expr, ClassNode receiver, String name, ClassNode ... args) {
        List<MethodNode> methods = this.findMethod(receiver, name, args);
        if (methods.isEmpty() && expr instanceof BinaryExpression) {
            BinaryExpression be = (BinaryExpression)expr;
            MethodCallExpression call = GeneralUtils.callX(be.getLeftExpression(), name, be.getRightExpression());
            methods = this.extension.handleMissingMethod(receiver, name, GeneralUtils.args(be.getLeftExpression()), args, call);
        }
        if (methods.isEmpty()) {
            this.addNoMatchingMethodError(receiver, name, args, expr);
        } else {
            if (this.areCategoryMethodCalls(methods, name, args)) {
                this.addCategoryMethodCallError(expr);
            }
            if ((methods = this.disambiguateMethods(methods, receiver, args, expr)).size() == 1) {
                return methods.get(0);
            }
            this.addAmbiguousErrorMessage(methods, name, args, expr);
        }
        return null;
    }

    private List<MethodNode> disambiguateMethods(List<MethodNode> methods, ClassNode receiver, ClassNode[] argTypes, Expression call) {
        if (methods.size() > 1 && receiver != null && argTypes != null) {
            LinkedList<MethodNode> filteredWithGenerics = new LinkedList<MethodNode>();
            for (MethodNode methodNode : methods) {
                if (!StaticTypeCheckingSupport.typeCheckMethodsWithGenerics(receiver, argTypes, methodNode) || (methodNode.getModifiers() & 0x40) != 0) continue;
                filteredWithGenerics.add(methodNode);
            }
            if (filteredWithGenerics.size() == 1) {
                return filteredWithGenerics;
            }
            methods = this.extension.handleAmbiguousMethods(methods, call);
        }
        if (methods.size() > 1 && call instanceof MethodCall) {
            LinkedList<MethodNode> methodNodeList = new LinkedList<MethodNode>();
            String methodName = ((MethodCall)((Object)call)).getMethodAsString();
            for (MethodNode methodNode : methods) {
                if (!methodNode.getName().equals(methodName)) continue;
                methodNodeList.add(methodNode);
            }
            methods = methodNodeList;
        }
        return methods;
    }

    protected static String prettyPrintMethodList(List<MethodNode> nodes) {
        StringBuilder sb = new StringBuilder("[");
        int n = nodes.size();
        for (int i = 0; i < n; ++i) {
            MethodNode node = nodes.get(i);
            sb.append(StaticTypeCheckingSupport.prettyPrintType(node.getReturnType()));
            sb.append(" ");
            sb.append(StaticTypeCheckingSupport.prettyPrintTypeName(node.getDeclaringClass()));
            sb.append("#");
            sb.append(StaticTypeCheckingSupport.toMethodParametersString(node.getName(), StaticTypeCheckingVisitor.extractTypesFromParameters(node.getParameters())));
            if (i >= n - 1) continue;
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    protected boolean areCategoryMethodCalls(List<MethodNode> foundMethods, String name, ClassNode[] args) {
        boolean category = false;
        if ("use".equals(name) && args != null && args.length == 2 && args[1].equals(ClassHelper.CLOSURE_TYPE)) {
            category = true;
            for (MethodNode method : foundMethods) {
                if (method instanceof ExtensionMethodNode && ((ExtensionMethodNode)method).getExtensionMethodNode().getDeclaringClass().equals(DGM_CLASSNODE)) continue;
                category = false;
                break;
            }
        }
        return category;
    }

    protected List<MethodNode> findMethodsWithGenerated(ClassNode receiver, String name) {
        if (receiver.isArray()) {
            if (name.equals("clone")) {
                MethodNode clone = new MethodNode("clone", 1, ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, null);
                clone.setDeclaringClass(ClassHelper.OBJECT_TYPE);
                clone.setNodeMetaData((Object)StaticTypesMarker.INFERRED_RETURN_TYPE, receiver);
                return Collections.singletonList(clone);
            }
            return ClassHelper.OBJECT_TYPE.getMethods(name);
        }
        List<MethodNode> methods = receiver.getMethods(name);
        if (receiver.isAbstract()) {
            StaticTypeCheckingVisitor.collectAllInterfaceMethodsByName(receiver, name, methods);
        } else {
            ArrayList<MethodNode> interfaceMethods = new ArrayList<MethodNode>();
            StaticTypeCheckingVisitor.collectAllInterfaceMethodsByName(receiver, name, interfaceMethods);
            interfaceMethods.stream().filter(mn -> mn.isDefault() || mn.isPublic() && !mn.isStatic() && !mn.isAbstract() && Traits.isTrait(mn.getDeclaringClass())).forEach(methods::add);
        }
        if (receiver.isInterface()) {
            methods.addAll(ClassHelper.OBJECT_TYPE.getMethods(name));
        }
        if (methods.isEmpty() || receiver.isResolved()) {
            return methods;
        }
        return StaticTypeCheckingVisitor.addGeneratedMethods(receiver, methods);
    }

    private static List<MethodNode> addGeneratedMethods(ClassNode receiver, List<MethodNode> methods) {
        LinkedList<MethodNode> result = new LinkedList<MethodNode>();
        for (MethodNode method : methods) {
            result.add(method);
            Parameter[] parameters = method.getParameters();
            int counter = 0;
            int size = parameters.length;
            for (int i = size - 1; i >= 0; --i) {
                Parameter parameter = parameters[i];
                if (parameter == null || !parameter.hasInitialExpression()) continue;
                ++counter;
            }
            for (int j = 1; j <= counter; ++j) {
                MethodNode stubbed;
                Parameter[] newParams = new Parameter[parameters.length - j];
                int index = 0;
                int k = 1;
                for (Parameter parameter : parameters) {
                    if (k > counter - j && parameter != null && parameter.hasInitialExpression()) {
                        ++k;
                        continue;
                    }
                    if (parameter != null && parameter.hasInitialExpression()) {
                        newParams[index++] = parameter;
                        ++k;
                        continue;
                    }
                    newParams[index++] = parameter;
                }
                if ("<init>".equals(method.getName())) {
                    stubbed = new ConstructorNode(method.getModifiers(), newParams, method.getExceptions(), GENERATED_EMPTY_STATEMENT);
                } else {
                    stubbed = new MethodNode(method.getName(), method.getModifiers(), method.getReturnType(), newParams, method.getExceptions(), GENERATED_EMPTY_STATEMENT);
                    stubbed.setGenericsTypes(method.getGenericsTypes());
                }
                stubbed.setDeclaringClass(method.getDeclaringClass());
                result.add(stubbed);
            }
        }
        return result;
    }

    protected List<MethodNode> findMethod(ClassNode receiver, String name, ClassNode ... args) {
        List<MethodNode> result;
        MethodNode constructor;
        List<MethodNode> chosen;
        List<MethodNode> methods;
        if (ClassHelper.isPrimitiveType(receiver)) {
            receiver = ClassHelper.getWrapper(receiver);
        }
        if ("<init>".equals(name) && !receiver.isInterface()) {
            methods = StaticTypeCheckingVisitor.addGeneratedMethods(receiver, new ArrayList<MethodNode>(receiver.getDeclaredConstructors()));
            if (methods.isEmpty()) {
                ConstructorNode node = new ConstructorNode(1, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, GENERATED_EMPTY_STATEMENT);
                node.setDeclaringClass(receiver);
                methods.add(node);
                if (receiver.isArray()) {
                    return methods;
                }
            }
        } else {
            PropertyNode property;
            String pname;
            MethodNode sam;
            methods = this.findMethodsWithGenerated(receiver, name);
            if ("call".equals(name) && receiver.isInterface() && (sam = ClassHelper.findSAM(receiver)) != null) {
                MethodNode callMethod = new MethodNode("call", sam.getModifiers(), sam.getReturnType(), sam.getParameters(), sam.getExceptions(), sam.getCode());
                callMethod.setDeclaringClass(sam.getDeclaringClass());
                callMethod.setSourcePosition(sam);
                methods.add(callMethod);
            }
            if (!receiver.isStaticClass() && receiver.getOuterClass() != null && this.typeCheckingContext.getEnclosingClassNodes().contains(receiver)) {
                ClassNode outer = receiver.getOuterClass();
                do {
                    methods.addAll(this.findMethodsWithGenerated(outer, name));
                } while (!outer.isStaticClass() && (outer = outer.getOuterClass()) != null);
            }
            if (methods.isEmpty()) {
                StaticTypeCheckingVisitor.addArrayMethods(methods, receiver, name, args);
            }
            if (methods.isEmpty() && (args == null || args.length == 0)) {
                pname = StaticTypeCheckingVisitor.extractPropertyNameFromMethodName("get", name);
                if (pname == null) {
                    pname = StaticTypeCheckingVisitor.extractPropertyNameFromMethodName("is", name);
                }
                property = null;
                if (pname != null) {
                    property = this.findProperty(receiver, pname);
                } else {
                    block1: for (ClassNode cn = receiver; cn != null; cn = cn.getSuperClass()) {
                        for (PropertyNode pn : cn.getProperties()) {
                            if (!name.equals(pn.getGetterName())) continue;
                            property = pn;
                            break block1;
                        }
                    }
                }
                if (property != null) {
                    int mods = 1 | (property.isStatic() ? 8 : 0);
                    MethodNode node = new MethodNode(name, mods, property.getType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, GENERATED_EMPTY_STATEMENT);
                    node.setDeclaringClass(property.getDeclaringClass());
                    return Collections.singletonList(node);
                }
            } else if (methods.isEmpty() && args != null && args.length == 1 && (pname = StaticTypeCheckingVisitor.extractPropertyNameFromMethodName("set", name)) != null && (property = this.findProperty(receiver, pname)) != null && !Modifier.isFinal(property.getModifiers())) {
                ClassNode type = property.getOriginType();
                if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(StaticTypeCheckingVisitor.wrapTypeIfNecessary(args[0]), StaticTypeCheckingVisitor.wrapTypeIfNecessary(type))) {
                    int mods = 1 | (property.isStatic() ? 8 : 0);
                    MethodNode node = new MethodNode(name, mods, ClassHelper.VOID_TYPE, new Parameter[]{new Parameter(type, name)}, ClassNode.EMPTY_ARRAY, GENERATED_EMPTY_STATEMENT);
                    node.setDeclaringClass(property.getDeclaringClass());
                    return Collections.singletonList(node);
                }
            }
        }
        if (!"<init>".equals(name) && !"<clinit>".equals(name)) {
            StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(this.getSourceUnit().getClassLoader(), receiver, name, args, methods);
        }
        if (!(chosen = StaticTypeCheckingSupport.chooseBestMethod(receiver, methods = StaticTypeCheckingSupport.filterMethodsByVisibility(methods, this.typeCheckingContext.getEnclosingClassNode()), args)).isEmpty()) {
            return chosen;
        }
        if (receiver instanceof InnerClassNode && ((InnerClassNode)receiver).isAnonymous() && methods.size() == 1 && args != null && "<init>".equals(name) && (constructor = methods.get(0)).getParameters().length == args.length) {
            return methods;
        }
        if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(receiver) && !(result = this.findMethod(receiver.getGenericsTypes()[0].getType(), name, args)).isEmpty()) {
            return result;
        }
        if (ClassHelper.isGStringType(receiver)) {
            return this.findMethod(ClassHelper.STRING_TYPE, name, args);
        }
        return EMPTY_METHODNODE_LIST;
    }

    private PropertyNode findProperty(ClassNode receiver, String name) {
        for (ClassNode cn = receiver; cn != null; cn = cn.getSuperClass()) {
            PropertyNode property = cn.getProperty(name);
            if (property != null) {
                return property;
            }
            if (cn.isStaticClass() || cn.getOuterClass() == null || !this.typeCheckingContext.getEnclosingClassNodes().contains(cn)) continue;
            ClassNode outer = cn.getOuterClass();
            do {
                if ((property = outer.getProperty(name)) == null) continue;
                return property;
            } while (!outer.isStaticClass() && (outer = outer.getOuterClass()) != null);
        }
        return null;
    }

    public static String extractPropertyNameFromMethodName(String prefix, String methodName) {
        String propertyName;
        String result;
        if (prefix == null || methodName == null) {
            return null;
        }
        if (methodName.startsWith(prefix) && prefix.length() < methodName.length() && (result = methodName.substring(prefix.length())).equals(BeanUtils.capitalize(propertyName = BeanUtils.decapitalize(result)))) {
            return propertyName;
        }
        return null;
    }

    private static void collectAllInterfaceMethodsByName(ClassNode type, String name, List<MethodNode> methods) {
        LinkedHashSet<ClassNode> done = new LinkedHashSet<ClassNode>();
        for (ClassNode next = type; next != null; next = next.getSuperClass()) {
            done.add(next);
            for (ClassNode face : next.getAllInterfaces()) {
                if (!done.add(face)) continue;
                methods.addAll(face.getDeclaredMethods(name));
            }
        }
    }

    protected ClassNode getType(ASTNode node) {
        ClassNode type = (ClassNode)node.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
        if (type != null) {
            return type;
        }
        if (node instanceof ClassExpression) {
            type = ((ClassExpression)node).getType();
            return GenericsUtils.makeClassSafe0(ClassHelper.CLASS_Type, new GenericsType(type));
        }
        if (node instanceof VariableExpression) {
            VariableExpression vexp = (VariableExpression)node;
            type = StaticTypeCheckingSupport.isTraitSelf(vexp);
            if (type != null) {
                return StaticTypeCheckingVisitor.makeSelf(type);
            }
            if (vexp.isThisExpression()) {
                return this.makeThis();
            }
            if (vexp.isSuperExpression()) {
                return this.makeSuper();
            }
            Variable variable = vexp.getAccessedVariable();
            if (variable instanceof FieldNode) {
                FieldNode fieldNode = (FieldNode)variable;
                ClassNode fieldType = fieldNode.getOriginType();
                if (!fieldNode.isStatic() && GenericsUtils.hasUnresolvedGenerics(fieldType)) {
                    ClassNode declType = fieldNode.getDeclaringClass();
                    ClassNode thisType = this.typeCheckingContext.getEnclosingClassNode();
                    fieldType = this.resolveGenericsWithContext(StaticTypeCheckingVisitor.extractPlaceHolders(thisType, declType), fieldType);
                }
                return fieldType;
            }
            if (variable != vexp && variable instanceof VariableExpression) {
                return this.getType((VariableExpression)variable);
            }
            if (variable instanceof Parameter) {
                Parameter parameter = (Parameter)variable;
                List<ClassNode> temporaryTypesForExpression = this.getTemporaryTypesForExpression(vexp);
                if (temporaryTypesForExpression == null || temporaryTypesForExpression.isEmpty()) {
                    type = this.typeCheckingContext.controlStructureVariables.get(parameter);
                }
                if (type == null && temporaryTypesForExpression == null) {
                    type = this.getTypeFromClosureArguments(parameter);
                }
                if (type != null) {
                    this.storeType(vexp, type);
                    return type;
                }
                return this.getType((Parameter)variable);
            }
            return vexp.getOriginType();
        }
        if (node instanceof Parameter || node instanceof FieldNode || node instanceof PropertyNode) {
            return ((Variable)((Object)node)).getOriginType();
        }
        if (node instanceof MethodNode) {
            if ((node == GET_DELEGATE || node == GET_OWNER || node == GET_THISOBJECT) && this.typeCheckingContext.getEnclosingClosure() != null) {
                return this.typeCheckingContext.getEnclosingClassNode();
            }
            type = ((MethodNode)node).getReturnType();
            return Optional.ofNullable(this.getInferredReturnType(node)).orElse(type);
        }
        if (node instanceof MethodCall) {
            if (node instanceof ConstructorCallExpression) {
                return ((ConstructorCallExpression)node).getType();
            }
            MethodNode target = (MethodNode)node.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
            if (target != null) {
                return this.getType(target);
            }
        }
        if (node instanceof ClosureExpression) {
            Parameter[] parameters;
            type = ClassHelper.CLOSURE_TYPE.getPlainNodeReference();
            ClassNode returnType = this.getInferredReturnType(node);
            if (returnType != null) {
                type.setGenericsTypes(new GenericsType[]{new GenericsType(StaticTypeCheckingVisitor.wrapTypeIfNecessary(returnType))});
            }
            int nParameters = (parameters = ((ClosureExpression)node).getParameters()) == null ? 0 : (parameters.length == 0 ? -1 : parameters.length);
            type.putNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS, nParameters);
            return type;
        }
        if (node instanceof ListExpression) {
            return this.inferListExpressionType((ListExpression)node);
        }
        if (node instanceof MapExpression) {
            return this.inferMapExpressionType((MapExpression)node);
        }
        if (node instanceof RangeExpression) {
            ClassNode toType;
            RangeExpression re = (RangeExpression)node;
            ClassNode fromType = this.getType(re.getFrom());
            type = fromType.equals(toType = this.getType(re.getTo())) ? StaticTypeCheckingVisitor.wrapTypeIfNecessary(fromType) : StaticTypeCheckingVisitor.wrapTypeIfNecessary(WideningCategories.lowestUpperBound(fromType, toType));
            return GenericsUtils.makeClassSafe0(ClassHelper.RANGE_TYPE, new GenericsType(type));
        }
        if (node instanceof SpreadExpression) {
            type = this.getType(((SpreadExpression)node).getExpression());
            return this.inferComponentType(type, null);
        }
        if (node instanceof UnaryPlusExpression) {
            return this.getType(((UnaryPlusExpression)node).getExpression());
        }
        if (node instanceof UnaryMinusExpression) {
            return this.getType(((UnaryMinusExpression)node).getExpression());
        }
        if (node instanceof BitwiseNegationExpression) {
            return this.getType(((BitwiseNegationExpression)node).getExpression());
        }
        return ((Expression)node).getType();
    }

    private ClassNode getTypeFromClosureArguments(Parameter parameter) {
        for (TypeCheckingContext.EnclosingClosure enclosingClosure : this.typeCheckingContext.getEnclosingClosureStack()) {
            Parameter[] parameters;
            ClosureExpression closureExpression = enclosingClosure.getClosureExpression();
            ClassNode[] closureParamTypes = (ClassNode[])closureExpression.getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS);
            if (closureParamTypes == null || (parameters = closureExpression.getParameters()) == null) continue;
            int n = parameters.length;
            String parameterName = parameter.getName();
            if (n == 0 && parameterName.equals("it")) {
                return closureParamTypes.length > 0 ? closureParamTypes[0] : null;
            }
            for (int i = 0; i < n; ++i) {
                if (!parameterName.equals(parameters[i].getName())) continue;
                return closureParamTypes.length > i ? closureParamTypes[i] : null;
            }
        }
        return null;
    }

    private static ClassNode makeSelf(ClassNode trait) {
        ClassNode selfType = trait;
        LinkedHashSet<ClassNode> selfTypes = Traits.collectSelfTypes(selfType, new LinkedHashSet<ClassNode>());
        if (!selfTypes.isEmpty()) {
            selfTypes.add(selfType);
            selfType = new UnionTypeClassNode(selfTypes.toArray(ClassNode.EMPTY_ARRAY));
        }
        return selfType;
    }

    private ClassNode makeSuper() {
        return StaticTypeCheckingVisitor.makeType(this.typeCheckingContext.getEnclosingClassNode().getUnresolvedSuperClass(), this.typeCheckingContext.isInStaticContext);
    }

    private ClassNode makeThis() {
        return StaticTypeCheckingVisitor.makeType(this.typeCheckingContext.getEnclosingClassNode(), this.typeCheckingContext.isInStaticContext);
    }

    private static ClassNode makeType(ClassNode cn, boolean usingClass) {
        if (usingClass) {
            ClassNode clazzType = ClassHelper.CLASS_Type.getPlainNodeReference();
            clazzType.setGenericsTypes(new GenericsType[]{new GenericsType(cn)});
            return clazzType;
        }
        return cn;
    }

    protected ClassNode storeInferredReturnType(ASTNode node, ClassNode type) {
        if (!(node instanceof ClosureExpression)) {
            throw new IllegalArgumentException("Storing inferred return type is only allowed on closures but found " + node.getClass());
        }
        return (ClassNode)node.putNodeMetaData((Object)StaticTypesMarker.INFERRED_RETURN_TYPE, type);
    }

    protected ClassNode getInferredReturnType(ASTNode exp) {
        return (ClassNode)exp.getNodeMetaData((Object)StaticTypesMarker.INFERRED_RETURN_TYPE);
    }

    protected ClassNode inferListExpressionType(ListExpression list) {
        List<Expression> expressions = list.getExpressions();
        int nExpressions = expressions.size();
        if (nExpressions == 0) {
            return list.getType();
        }
        ClassNode listType = list.getType();
        GenericsType[] genericsTypes = listType.getGenericsTypes();
        if (genericsTypes == null || genericsTypes.length == 0 || genericsTypes.length == 1 && ClassHelper.isObjectType(genericsTypes[0].getType())) {
            ArrayList<ClassNode> nodes = new ArrayList<ClassNode>(nExpressions);
            for (Expression expression : expressions) {
                if (StaticTypeCheckingVisitor.isNullConstant(expression)) continue;
                nodes.add(this.getType(expression));
            }
            if (!nodes.isEmpty()) {
                ClassNode itemType = WideningCategories.lowestUpperBound(nodes);
                listType = listType.getPlainNodeReference();
                listType.setGenericsTypes(new GenericsType[]{new GenericsType(StaticTypeCheckingVisitor.wrapTypeIfNecessary(itemType))});
            }
        }
        return listType;
    }

    protected static boolean isNullConstant(Expression expression) {
        return expression instanceof ConstantExpression && ((ConstantExpression)expression).isNullExpression();
    }

    protected static boolean isThisExpression(Expression expression) {
        return expression instanceof VariableExpression && ((VariableExpression)expression).isThisExpression();
    }

    protected static boolean isSuperExpression(Expression expression) {
        return expression instanceof VariableExpression && ((VariableExpression)expression).isSuperExpression();
    }

    protected ClassNode inferMapExpressionType(MapExpression map) {
        ClassNode mapType = StaticTypeCheckingSupport.LinkedHashMap_TYPE.getPlainNodeReference();
        List<MapEntryExpression> entryExpressions = map.getMapEntryExpressions();
        int nExpressions = entryExpressions.size();
        if (nExpressions == 0) {
            return mapType;
        }
        GenericsType[] genericsTypes = mapType.getGenericsTypes();
        if (genericsTypes == null || genericsTypes.length < 2 || genericsTypes.length == 2 && ClassHelper.isObjectType(genericsTypes[0].getType()) && ClassHelper.isObjectType(genericsTypes[1].getType())) {
            ArrayList<ClassNode> keyTypes = new ArrayList<ClassNode>(nExpressions);
            ArrayList<ClassNode> valueTypes = new ArrayList<ClassNode>(nExpressions);
            for (MapEntryExpression entryExpression : entryExpressions) {
                keyTypes.add(this.getType(entryExpression.getKeyExpression()));
                valueTypes.add(this.getType(entryExpression.getValueExpression()));
            }
            ClassNode keyType = WideningCategories.lowestUpperBound(keyTypes);
            ClassNode valueType = WideningCategories.lowestUpperBound(valueTypes);
            if (!ClassHelper.isObjectType(keyType) || !ClassHelper.isObjectType(valueType)) {
                mapType = mapType.getPlainNodeReference();
                mapType.setGenericsTypes(new GenericsType[]{new GenericsType(StaticTypeCheckingVisitor.wrapTypeIfNecessary(keyType)), new GenericsType(StaticTypeCheckingVisitor.wrapTypeIfNecessary(valueType))});
            }
        }
        return mapType;
    }

    protected ClassNode inferReturnTypeGenerics(ClassNode receiver, MethodNode method, Expression arguments) {
        return this.inferReturnTypeGenerics(receiver, method, arguments, null);
    }

    protected ClassNode inferReturnTypeGenerics(ClassNode receiver, MethodNode method, Expression arguments, GenericsType[] explicitTypeHints) {
        GenericsType[] methodGenericTypes;
        ClassNode returnType;
        ClassNode classNode = returnType = method instanceof ConstructorNode ? method.getDeclaringClass() : method.getReturnType();
        if (!GenericsUtils.hasUnresolvedGenerics(returnType)) {
            if (StaticTypeCheckingSupport.getGenericsWithoutArray(returnType) != null) {
                returnType = StaticTypeCheckingSupport.boundUnboundedWildcards(returnType);
            }
            return returnType;
        }
        if (method instanceof ExtensionMethodNode) {
            ArgumentListExpression args = StaticTypeCheckingVisitor.getExtensionArguments(receiver, method, arguments);
            MethodNode extension = ((ExtensionMethodNode)method).getExtensionMethodNode();
            return this.inferReturnTypeGenerics(receiver, extension, args, explicitTypeHints);
        }
        Map<GenericsType.GenericsTypeName, GenericsType> context = method.isStatic() || method instanceof ConstructorNode ? null : StaticTypeCheckingVisitor.extractPlaceHoldersVisibleToDeclaration(receiver, method, arguments);
        GenericsType[] genericsTypeArray = methodGenericTypes = method instanceof ConstructorNode ? method.getDeclaringClass().getGenericsTypes() : StaticTypeCheckingSupport.applyGenericsContext(context, method.getGenericsTypes());
        if (methodGenericTypes != null) {
            HashMap<GenericsType.GenericsTypeName, GenericsType> resolvedPlaceholders = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
            for (GenericsType gt : methodGenericTypes) {
                resolvedPlaceholders.put(new GenericsType.GenericsTypeName(gt.getName()), gt);
            }
            StaticTypeCheckingSupport.applyGenericsConnections(this.extractGenericsConnectionsFromArguments(methodGenericTypes, (Parameter[])Arrays.stream(method.getParameters()).map(param -> new Parameter(StaticTypeCheckingSupport.applyGenericsContext(context, param.getType()), param.getName())).toArray(Parameter[]::new), arguments, explicitTypeHints), resolvedPlaceholders);
            returnType = StaticTypeCheckingSupport.applyGenericsContext(resolvedPlaceholders, returnType);
        }
        if (context != null) {
            returnType = StaticTypeCheckingSupport.applyGenericsContext(context, returnType);
            if (receiver.getGenericsTypes() == null && receiver.redirect().getGenericsTypes() != null && !receiver.isGenericsPlaceHolder() && GenericsUtils.hasUnresolvedGenerics(returnType)) {
                returnType = returnType.getPlainNodeReference();
            }
        }
        returnType = StaticTypeCheckingSupport.applyGenericsContext(StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(this.typeCheckingContext), returnType);
        return returnType;
    }

    private Map<GenericsType.GenericsTypeName, GenericsType> extractGenericsConnectionsFromArguments(GenericsType[] methodGenericTypes, Parameter[] parameters, Expression arguments, GenericsType[] explicitTypeHints) {
        HashMap<GenericsType.GenericsTypeName, GenericsType> resolvedPlaceholders = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
        if (explicitTypeHints != null) {
            int n = methodGenericTypes.length;
            if (n == explicitTypeHints.length) {
                for (int i = 0; i < n; ++i) {
                    resolvedPlaceholders.put(new GenericsType.GenericsTypeName(methodGenericTypes[i].getName()), explicitTypeHints[i]);
                }
            }
        } else if (parameters.length > 0) {
            List<Expression> expressions = InvocationWriter.makeArgumentList(arguments).getExpressions();
            boolean isVargs = StaticTypeCheckingSupport.isVargs(parameters);
            int nArguments = expressions.size();
            int nParams = parameters.length;
            if (isVargs ? nArguments >= nParams - 1 : nArguments == nParams) {
                for (int i = 0; i < nArguments; ++i) {
                    MethodNode sam;
                    if (StaticTypeCheckingVisitor.isNullConstant(expressions.get(i))) continue;
                    ClassNode paramType = parameters[Math.min(i, nParams - 1)].getType();
                    ClassNode argumentType = this.getDeclaredOrInferredType(expressions.get(i));
                    if (!GenericsUtils.hasUnresolvedGenerics(paramType)) continue;
                    if (isVargs && (i >= nParams || i == nParams - 1 && (nArguments > nParams || !argumentType.isArray()))) {
                        paramType = paramType.getComponentType();
                    }
                    if (StaticTypeCheckingVisitor.isClosureWithType(argumentType) && (sam = ClassHelper.findSAM(paramType)) != null) {
                        argumentType = StaticTypeCheckingVisitor.convertClosureTypeToSAMType(expressions.get(i), argumentType, sam, paramType);
                    }
                    HashMap<GenericsType.GenericsTypeName, GenericsType> connections = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
                    StaticTypeCheckingSupport.extractGenericsConnections(connections, StaticTypeCheckingVisitor.wrapTypeIfNecessary(argumentType), paramType);
                    connections.forEach((gtn, gt) -> resolvedPlaceholders.merge((GenericsType.GenericsTypeName)gtn, (GenericsType)gt, (gt1, gt2) -> StaticTypeCheckingSupport.getCombinedGenericsType(gt1, gt2)));
                }
            }
            Map<GenericsType.GenericsTypeName, GenericsType> connections = Arrays.stream(methodGenericTypes).collect(Collectors.toMap(gt -> new GenericsType.GenericsTypeName(gt.getName()), gt -> gt, (v, x) -> v));
            StaticTypeCheckingVisitor.extractGenericsConnectionsForSuperClassAndInterfaces(connections, resolvedPlaceholders);
        }
        for (GenericsType gt2 : methodGenericTypes) {
            resolvedPlaceholders.computeIfAbsent(new GenericsType.GenericsTypeName(gt2.getName()), gtn -> {
                GenericsType xxx = new GenericsType(ClassHelper.makeWithoutCaching("#"), StaticTypeCheckingSupport.applyGenericsContext((Map<GenericsType.GenericsTypeName, GenericsType>)resolvedPlaceholders, gt2.getUpperBounds()), StaticTypeCheckingSupport.applyGenericsContext((Map<GenericsType.GenericsTypeName, GenericsType>)resolvedPlaceholders, gt2.getLowerBound()));
                xxx.getType().setRedirect(gt2.getType().redirect());
                xxx.putNodeMetaData(GenericsType.class, gt2);
                xxx.setName("#" + gt2.getName());
                xxx.setPlaceholder(true);
                return xxx;
            });
        }
        return resolvedPlaceholders;
    }

    private void resolvePlaceholdersFromImplicitTypeHints(ClassNode[] actuals, ArgumentListExpression argumentList, Parameter[] parameterArray) {
        int np = parameterArray.length;
        int n = actuals.length;
        for (int i = 0; np > 0 && i < n; ++i) {
            MethodNode aNode;
            Expression a = argumentList.getExpression(i);
            Parameter p = parameterArray[Math.min(i, np - 1)];
            ClassNode at = actuals[i];
            ClassNode pt = p.getOriginType();
            if (!StaticTypeCheckingSupport.isUsingGenericsOrIsArrayUsingGenerics(pt)) continue;
            if (i >= np - 1 && pt.isArray() && !at.isArray()) {
                pt = pt.getComponentType();
            }
            if (a instanceof ListExpression) {
                actuals[i] = StaticTypeCheckingVisitor.getLiteralResultType(pt, at, StaticTypeCheckingSupport.ArrayList_TYPE);
            } else if (a instanceof MapExpression) {
                actuals[i] = StaticTypeCheckingVisitor.getLiteralResultType(pt, at, StaticTypeCheckingSupport.LinkedHashMap_TYPE);
            } else if (a instanceof ConstructorCallExpression) {
                this.inferDiamondType((ConstructorCallExpression)a, pt);
            } else if (a instanceof TernaryExpression && at.getGenericsTypes() != null && at.getGenericsTypes().length == 0) {
                this.typeCheckingContext.pushEnclosingBinaryExpression(StaticTypeCheckingVisitor.assignX(GeneralUtils.varX(p), a, a));
                a.visit(this);
                this.typeCheckingContext.popEnclosingBinaryExpression();
                actuals[i] = this.getType(a);
            }
            if (!(a instanceof MethodCall) || a instanceof MethodCallExpression && ((MethodCallExpression)a).isUsingGenerics() || (aNode = (MethodNode)a.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET)) == null || aNode.getGenericsTypes() == null || !GenericsUtils.hasUnresolvedGenerics(at)) continue;
            while (!(at.equals(pt) || ClassHelper.isObjectType(at) || StaticTypeCheckingVisitor.isGenericsPlaceHolderOrArrayOf(at))) {
                at = StaticTypeCheckingSupport.applyGenericsContext(GenericsUtils.extractPlaceholders(at), ClassHelper.getNextSuperClass(at, pt));
            }
            HashMap<GenericsType.GenericsTypeName, GenericsType> linked = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
            Map<GenericsType.GenericsTypeName, GenericsType> source = GenericsUtils.extractPlaceholders(at);
            Map<GenericsType.GenericsTypeName, GenericsType> target = GenericsUtils.extractPlaceholders(pt);
            if (at.isGenericsPlaceHolder()) {
                target.put(new GenericsType.GenericsTypeName(at.getUnresolvedName()), pt.asGenericsType());
            }
            block2: for (GenericsType placeholder : aNode.getGenericsTypes()) {
                for (Map.Entry<GenericsType.GenericsTypeName, GenericsType> e : source.entrySet()) {
                    if (e.getValue().getNodeMetaData(GenericsType.class) != placeholder) continue;
                    Optional.ofNullable(target.get(e.getKey())).filter(gt -> StaticTypeCheckingSupport.isAssignableTo(gt.getType(), placeholder.getType())).ifPresent(gt -> linked.put(new GenericsType.GenericsTypeName(((GenericsType)e.getValue()).getName()), (GenericsType)gt));
                    continue block2;
                }
            }
            actuals[i] = StaticTypeCheckingSupport.applyGenericsContext(linked, at);
        }
    }

    private static void extractGenericsConnectionsForSuperClassAndInterfaces(Map<GenericsType.GenericsTypeName, GenericsType> resolvedPlaceholders, Map<GenericsType.GenericsTypeName, GenericsType> connections) {
        for (GenericsType value : new HashSet<GenericsType>(connections.values())) {
            if (value.isPlaceholder() || value.isWildcard()) continue;
            ClassNode valueType = value.getType();
            LinkedList<ClassNode> deepNodes = new LinkedList<ClassNode>();
            ClassNode unresolvedSuperClass = valueType.getUnresolvedSuperClass();
            if (unresolvedSuperClass != null && unresolvedSuperClass.isUsingGenerics()) {
                deepNodes.add(unresolvedSuperClass);
            }
            for (ClassNode classNode : valueType.getUnresolvedInterfaces()) {
                if (!classNode.isUsingGenerics()) continue;
                deepNodes.add(classNode);
            }
            if (deepNodes.isEmpty()) continue;
            for (GenericsType genericsType : resolvedPlaceholders.values()) {
                ClassNode[] classNodeArray;
                ClassNode lowerBound = genericsType.getLowerBound();
                if (lowerBound != null) {
                    for (ClassNode deepNode : deepNodes) {
                        if (!lowerBound.equals(deepNode)) continue;
                        StaticTypeCheckingSupport.extractGenericsConnections(connections, deepNode, lowerBound);
                    }
                }
                if ((classNodeArray = genericsType.getUpperBounds()) == null) continue;
                for (ClassNode upperBound : classNodeArray) {
                    for (ClassNode deepNode : deepNodes) {
                        if (!upperBound.equals(deepNode)) continue;
                        StaticTypeCheckingSupport.extractGenericsConnections(connections, deepNode, upperBound);
                    }
                }
            }
        }
    }

    private static ClassNode convertClosureTypeToSAMType(Expression expression, ClassNode closureType, MethodNode sam, ClassNode samType) {
        MethodPointerExpression mp;
        List candidates;
        Map<GenericsType.GenericsTypeName, GenericsType> samTypeConnections = GenericsUtils.extractPlaceholders(samType);
        samTypeConnections.replaceAll((xx, gt) -> Optional.ofNullable(gt.getLowerBound()).map(GenericsType::new).orElse((GenericsType)gt));
        ClassNode closureReturnType = closureType.getGenericsTypes()[0].getType();
        Parameter[] parameters = sam.getParameters();
        if (parameters.length > 0 && expression instanceof MethodPointerExpression && GenericsUtils.hasUnresolvedGenerics(closureReturnType) && (candidates = (List)(mp = (MethodPointerExpression)expression).getNodeMetaData(MethodNode.class)) != null && !candidates.isEmpty()) {
            ClassNode[] paramTypes = StaticTypeCheckingSupport.applyGenericsContext(samTypeConnections, StaticTypeCheckingVisitor.extractTypesFromParameters(parameters));
            ClassNode[] matchTypes = candidates.stream().map(candidate -> StaticTypeCheckingVisitor.collateMethodReferenceParameterTypes(mp, candidate)).filter(candidate -> StaticTypeCheckingVisitor.checkSignatureSuitability(candidate, paramTypes)).findFirst().orElse(null);
            if (matchTypes != null) {
                HashMap<GenericsType.GenericsTypeName, GenericsType> connections = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
                int n = parameters.length;
                for (int i = 0; i < n; ++i) {
                    StaticTypeCheckingSupport.extractGenericsConnections(connections, paramTypes[i], matchTypes[i]);
                }
                closureReturnType = StaticTypeCheckingSupport.applyGenericsContext(connections, closureReturnType);
                closureReturnType = StaticTypeCheckingSupport.applyGenericsContext(samTypeConnections, closureReturnType);
            }
        }
        StaticTypeCheckingSupport.extractGenericsConnections(samTypeConnections, closureReturnType, sam.getReturnType());
        if (parameters.length > 0 && expression instanceof ClosureExpression) {
            return closureType;
        }
        return StaticTypeCheckingSupport.applyGenericsContext(samTypeConnections, samType.redirect());
    }

    private static ClassNode[] collateMethodReferenceParameterTypes(MethodPointerExpression source, MethodNode target) {
        Parameter[] params;
        if (target instanceof ExtensionMethodNode && !((ExtensionMethodNode)target).isStaticExtension()) {
            params = ((ExtensionMethodNode)target).getExtensionMethodNode().getParameters();
        } else if (!target.isStatic() && source.getExpression() instanceof ClassExpression) {
            ClassNode thisType = ((ClassExpression)source.getExpression()).getType();
            int n = target.getParameters().length;
            params = new Parameter[n + 1];
            params[0] = new Parameter(thisType, "");
            System.arraycopy(target.getParameters(), 0, params, 1, n);
        } else {
            params = target.getParameters();
        }
        return StaticTypeCheckingVisitor.extractTypesFromParameters(params);
    }

    private static boolean checkSignatureSuitability(ClassNode[] receiverTypes, ClassNode[] providerTypes) {
        int n = receiverTypes.length;
        if (n != providerTypes.length) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (StaticTypeCheckingSupport.isAssignableTo(providerTypes[i], receiverTypes[i])) continue;
            return false;
        }
        return true;
    }

    private ClassNode getDeclaredOrInferredType(Expression expression) {
        ClassNode declaredOrInferred = expression instanceof Variable && !((Variable)((Object)expression)).isDynamicTyped() ? this.getOriginalDeclarationType(expression) : this.getType(expression);
        return this.getInferredTypeFromTempInfo(expression, declaredOrInferred);
    }

    private static ArgumentListExpression getExtensionArguments(ClassNode receiver, MethodNode method, Expression arguments) {
        VariableExpression self = GeneralUtils.varX("$self", receiver);
        self.putNodeMetaData(ExtensionMethodDeclaringClass.class, method.getDeclaringClass());
        ArgumentListExpression args = new ArgumentListExpression();
        args.addExpression(self);
        if (arguments instanceof TupleExpression) {
            for (Expression argument : (TupleExpression)arguments) {
                args.addExpression(argument);
            }
        } else {
            args.addExpression(arguments);
        }
        return args;
    }

    private static boolean isGenericsPlaceHolderOrArrayOf(ClassNode cn) {
        while (cn.isArray()) {
            cn = cn.getComponentType();
        }
        return cn.isGenericsPlaceHolder();
    }

    /*
     * Could not resolve type clashes
     */
    private static Map<GenericsType.GenericsTypeName, GenericsType> extractPlaceHolders(ClassNode receiver, ClassNode declaringClass) {
        HashMap<GenericsType.GenericsTypeName, GenericsType> result = null;
        ClassNode[] todo = receiver instanceof UnionTypeClassNode ? ((UnionTypeClassNode)receiver).getDelegates() : new ClassNode[]{!ClassHelper.isPrimitiveType(declaringClass) ? StaticTypeCheckingVisitor.wrapTypeIfNecessary(receiver) : receiver};
        ClassNode[] classNodeArray = todo;
        int n = classNodeArray.length;
        block0: for (int i = 0; i < n; ++i) {
            ClassNode type;
            ClassNode current = type = classNodeArray[i];
            while (current != null) {
                boolean currentIsDeclaring;
                HashMap<GenericsType.GenericsTypeName, GenericsType> placeHolders = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
                if (current.isGenericsPlaceHolder()) {
                    current = current.asGenericsType().getUpperBounds()[0];
                } else if (current.getGenericsTypes() != null ? current.getGenericsTypes().length == 0 : current.redirect().getGenericsTypes() != null) {
                    for (GenericsType gt : current.redirect().getGenericsTypes()) {
                        ClassNode cn = gt.getUpperBounds() != null ? gt.getUpperBounds()[0] : gt.getType().redirect();
                        placeHolders.put(new GenericsType.GenericsTypeName(gt.getName()), cn.getPlainNodeReference().asGenericsType());
                    }
                }
                boolean bl = currentIsDeclaring = current.equals(declaringClass) || StaticTypeCheckingVisitor.isGenericsPlaceHolderOrArrayOf(declaringClass);
                if (currentIsDeclaring) {
                    StaticTypeCheckingSupport.extractGenericsConnections(placeHolders, current, declaringClass);
                } else {
                    GenericsUtils.extractPlaceholders(current, placeHolders);
                }
                if (result != null) {
                    for (Map.Entry entry : placeHolders.entrySet()) {
                        GenericsType referenced;
                        GenericsType gt;
                        gt = (GenericsType)entry.getValue();
                        if (!gt.isPlaceholder() || (referenced = (GenericsType)result.get(new GenericsType.GenericsTypeName(gt.getName()))) == null) continue;
                        entry.setValue(referenced);
                    }
                }
                result = placeHolders;
                if (currentIsDeclaring) continue block0;
                if ((current = ClassHelper.getNextSuperClass(current, declaringClass)) == null && ClassHelper.isClassType(declaringClass)) {
                    current = declaringClass;
                    continue;
                }
                current = StaticTypeCheckingSupport.applyGenericsContext(placeHolders, current);
            }
        }
        if (result == null) {
            throw new GroovyBugError("Declaring class " + StaticTypeCheckingSupport.prettyPrintTypeName(declaringClass) + " was not matched with receiver " + StaticTypeCheckingSupport.prettyPrintTypeName(receiver) + ". This should not have happened!");
        }
        return result;
    }

    private static Map<GenericsType.GenericsTypeName, GenericsType> extractPlaceHoldersVisibleToDeclaration(ClassNode receiver, MethodNode method, Expression argument) {
        Map<GenericsType.GenericsTypeName, GenericsType> result;
        if (method.isStatic()) {
            result = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
        } else {
            ClassNode cn;
            List<Expression> arguments;
            ClassNode declaring = method.getDeclaringClass();
            if (argument instanceof ArgumentListExpression && !(arguments = ((ArgumentListExpression)argument).getExpressions()).isEmpty() && (cn = (ClassNode)arguments.get(0).getNodeMetaData(ExtensionMethodDeclaringClass.class)) != null) {
                declaring = cn;
            }
            if (!(result = StaticTypeCheckingVisitor.extractPlaceHolders(receiver, declaring)).isEmpty()) {
                Optional.ofNullable(method.getGenericsTypes()).ifPresent(methodGenerics -> Arrays.stream(methodGenerics).map(gt -> new GenericsType.GenericsTypeName(gt.getName())).forEach(result::remove));
            }
        }
        return result;
    }

    protected boolean typeCheckMethodsWithGenericsOrFail(ClassNode receiver, ClassNode[] arguments, MethodNode candidateMethod, Expression location) {
        if (!StaticTypeCheckingSupport.typeCheckMethodsWithGenerics(receiver, arguments, candidateMethod)) {
            ClassNode r = receiver;
            ClassNode[] at = arguments;
            MethodNode m = candidateMethod;
            if (candidateMethod instanceof ExtensionMethodNode) {
                m = ((ExtensionMethodNode)candidateMethod).getExtensionMethodNode();
                r = m.getDeclaringClass();
                at = new ClassNode[arguments.length + 1];
                at[0] = receiver;
                System.arraycopy(arguments, 0, at, 1, arguments.length);
            }
            Map<GenericsType.GenericsTypeName, GenericsType> spec = StaticTypeCheckingVisitor.extractPlaceHoldersVisibleToDeclaration(r, m, null);
            GenericsType[] gt = StaticTypeCheckingSupport.applyGenericsContext(spec, m.getGenericsTypes());
            GenericsUtils.extractPlaceholders(GenericsUtils.makeClassSafe0(ClassHelper.OBJECT_TYPE, gt), spec);
            Parameter[] parameters = m.getParameters();
            ClassNode[] paramTypes = new ClassNode[parameters.length];
            int n = parameters.length;
            for (int i = 0; i < n; ++i) {
                paramTypes[i] = StaticTypeCheckingSupport.fullyResolveType(parameters[i].getType(), spec);
                if (i >= at.length || !this.hasGStringStringError(paramTypes[i], at[i], location)) continue;
                return false;
            }
            this.addStaticTypeError("Cannot call " + (gt == null ? "" : GenericsUtils.toGenericTypesString(gt)) + StaticTypeCheckingSupport.prettyPrintTypeName(r) + "#" + StaticTypeCheckingSupport.toMethodParametersString(m.getName(), paramTypes) + " with arguments " + StaticTypeCheckingVisitor.formatArgumentList(at), location);
            return false;
        }
        return true;
    }

    protected static String formatArgumentList(ClassNode[] nodes) {
        if (nodes == null || nodes.length == 0) {
            return "[]";
        }
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (ClassNode node : nodes) {
            joiner.add(StaticTypeCheckingSupport.prettyPrintType(node));
        }
        return joiner.toString();
    }

    private static void putSetterInfo(Expression exp, SetterInfo info) {
        exp.putNodeMetaData(SetterInfo.class, info);
    }

    private static SetterInfo removeSetterInfo(Expression exp) {
        Object nodeMetaData = exp.getNodeMetaData(SetterInfo.class);
        if (nodeMetaData != null) {
            exp.removeNodeMetaData(SetterInfo.class);
            return (SetterInfo)nodeMetaData;
        }
        return null;
    }

    @Override
    public void addError(String msg, ASTNode expr) {
        Long err = (long)expr.getLineNumber() << 16 + expr.getColumnNumber();
        if (DEBUG_GENERATED_CODE && expr.getLineNumber() < 0 || !this.typeCheckingContext.reportedErrors.contains(err)) {
            this.typeCheckingContext.getErrorCollector().addErrorAndContinue(msg + '\n', expr, this.getSourceUnit());
            this.typeCheckingContext.reportedErrors.add(err);
        }
    }

    protected void addStaticTypeError(String msg, ASTNode expr) {
        if (expr.getColumnNumber() > 0 && expr.getLineNumber() > 0) {
            this.addError("[Static type checking] - " + msg, expr);
        } else if (DEBUG_GENERATED_CODE) {
            this.addError("[Static type checking] - Error in generated code [" + expr.getText() + "] - " + msg, expr);
        }
    }

    protected void addNoMatchingMethodError(ClassNode receiver, String name, ClassNode[] args, Expression call) {
        if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(receiver)) {
            receiver = receiver.getGenericsTypes()[0].getType();
        }
        this.addStaticTypeError("Cannot find matching method " + StaticTypeCheckingSupport.prettyPrintTypeName(receiver) + "#" + StaticTypeCheckingSupport.toMethodParametersString(name, args) + ". Please check if the declared type is correct and if the method exists.", call);
    }

    protected void addAmbiguousErrorMessage(List<MethodNode> foundMethods, String name, ClassNode[] args, Expression expr) {
        this.addStaticTypeError("Reference to method is ambiguous. Cannot choose between " + StaticTypeCheckingVisitor.prettyPrintMethodList(foundMethods), expr);
    }

    protected void addCategoryMethodCallError(Expression call) {
        this.addStaticTypeError("Due to their dynamic nature, usage of categories is not possible with static type checking active", call);
    }

    protected void addAssignmentError(ClassNode leftType, ClassNode rightType, Expression expression) {
        this.addStaticTypeError("Cannot assign value of type " + StaticTypeCheckingSupport.prettyPrintType(rightType) + " to variable of type " + StaticTypeCheckingSupport.prettyPrintType(leftType), expression);
    }

    protected void addUnsupportedPreOrPostfixExpressionError(Expression expression) {
        if (expression instanceof PostfixExpression) {
            this.addStaticTypeError("Unsupported postfix operation type [" + ((PostfixExpression)expression).getOperation() + "]", expression);
        } else if (expression instanceof PrefixExpression) {
            this.addStaticTypeError("Unsupported prefix operation type [" + ((PrefixExpression)expression).getOperation() + "]", expression);
        } else {
            throw new IllegalArgumentException("Method should be called with a PostfixExpression or a PrefixExpression");
        }
    }

    public void setMethodsToBeVisited(Set<MethodNode> methodsToBeVisited) {
        this.typeCheckingContext.methodsToBeVisited = methodsToBeVisited;
    }

    public void performSecondPass() {
        for (SecondPassExpression wrapper : this.typeCheckingContext.secondPassExpressions) {
            VariableExpression var;
            List<ClassNode> classNodes;
            Variable target;
            MethodCallExpression call;
            Expression objectExpression;
            Expression expression = wrapper.getExpression();
            if (expression instanceof BinaryExpression) {
                List<MethodNode> method;
                VariableExpression var2;
                List<ClassNode> classNodes2;
                Variable target2;
                Expression left = ((BinaryExpression)expression).getLeftExpression();
                if (!(left instanceof VariableExpression) || !((target2 = StaticTypeCheckingSupport.findTargetVariable((VariableExpression)left)) instanceof VariableExpression) || (classNodes2 = this.typeCheckingContext.closureSharedVariablesAssignmentTypes.get(var2 = (VariableExpression)target2)) == null || classNodes2.size() <= 1) continue;
                ClassNode lub = WideningCategories.lowestUpperBound(classNodes2);
                String message = StaticTypeCheckingSupport.getOperationName(((BinaryExpression)expression).getOperation().getType());
                if (message == null || !(method = this.findMethod(lub, message, this.getType(((BinaryExpression)expression).getRightExpression()))).isEmpty()) continue;
                this.addStaticTypeError("A closure shared variable [" + target2.getName() + "] has been assigned with various types and the method [" + StaticTypeCheckingSupport.toMethodParametersString(message, this.getType(((BinaryExpression)expression).getRightExpression())) + "] does not exist in the lowest upper bound of those types: [" + StaticTypeCheckingSupport.prettyPrintType(lub) + "]. In general, this is a bad practice (variable reuse) because the compiler cannot determine safely what is the type of the variable at the moment of the call in a multithreaded context.", expression);
                continue;
            }
            if (!(expression instanceof MethodCallExpression) || !((objectExpression = (call = (MethodCallExpression)expression).getObjectExpression()) instanceof VariableExpression) || !((target = StaticTypeCheckingSupport.findTargetVariable((VariableExpression)objectExpression)) instanceof VariableExpression) || (classNodes = this.typeCheckingContext.closureSharedVariablesAssignmentTypes.get(var = (VariableExpression)target)) == null || classNodes.size() <= 1) continue;
            ClassNode lub = WideningCategories.lowestUpperBound(classNodes);
            MethodNode methodNode = (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
            Parameter[] parameters = methodNode.getParameters();
            ClassNode[] params = StaticTypeCheckingVisitor.extractTypesFromParameters(parameters);
            ClassNode[] argTypes = (ClassNode[])wrapper.getData();
            List<MethodNode> method = this.findMethod(lub, methodNode.getName(), argTypes);
            if (method.size() == 1) continue;
            this.addStaticTypeError("A closure shared variable [" + target.getName() + "] has been assigned with various types and the method [" + StaticTypeCheckingSupport.toMethodParametersString(methodNode.getName(), params) + "] does not exist in the lowest upper bound of those types: [" + StaticTypeCheckingSupport.prettyPrintType(lub) + "]. In general, this is a bad practice (variable reuse) because the compiler cannot determine safely what is the type of the variable at the moment of the call in a multithreaded context.", call);
        }
        this.extension.finish();
    }

    protected static ClassNode[] extractTypesFromParameters(Parameter[] parameters) {
        return (ClassNode[])Arrays.stream(parameters).map(Parameter::getType).toArray(ClassNode[]::new);
    }

    protected static ClassNode wrapTypeIfNecessary(ClassNode type) {
        return type != null && ClassHelper.isPrimitiveType(type) ? ClassHelper.getWrapper(type) : type;
    }

    protected static boolean isClassInnerClassOrEqualTo(ClassNode toBeChecked, ClassNode start) {
        if (start == toBeChecked) {
            return true;
        }
        ClassNode outer = start.getOuterClass();
        if (outer != null) {
            return StaticTypeCheckingVisitor.isClassInnerClassOrEqualTo(toBeChecked, outer);
        }
        return false;
    }

    private static boolean isNonStaticHelperMethod(MethodNode method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length > 0 && parameters[0].getName().equals("$self")) {
            return !method.getName().contains("$init$") && Traits.isTrait(method.getDeclaringClass().getOuterClass());
        }
        return false;
    }

    private static BinaryExpression assignX(Expression lhs, Expression rhs, ASTNode pos) {
        BinaryExpression exp = (BinaryExpression)GeneralUtils.assignX(lhs, rhs);
        exp.setSourcePosition(pos);
        return exp;
    }

    private static class SetterInfo {
        final ClassNode receiverType;
        final String name;
        final List<MethodNode> setters;

        private SetterInfo(ClassNode receiverType, String name, List<MethodNode> setters) {
            this.receiverType = receiverType;
            this.setters = setters;
            this.name = name;
        }
    }

    private class ParameterVariableExpression
    extends VariableExpression {
        private final Parameter parameter;

        ParameterVariableExpression(Parameter parameter) {
            super(parameter);
            this.parameter = parameter;
            ClassNode inferredType = (ClassNode)this.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
            if (inferredType == null) {
                inferredType = StaticTypeCheckingVisitor.this.typeCheckingContext.controlStructureVariables.get(parameter);
                if (inferredType == null) {
                    inferredType = StaticTypeCheckingVisitor.this.getTypeFromClosureArguments(parameter);
                }
                this.setNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, inferredType != null ? inferredType : parameter.getType());
            }
        }

        @Override
        public Map<?, ?> getMetaDataMap() {
            return this.parameter.getMetaDataMap();
        }

        @Override
        public void setMetaDataMap(Map<?, ?> metaDataMap) {
            this.parameter.setMetaDataMap(metaDataMap);
        }
    }

    protected class VariableExpressionTypeMemoizer
    extends ClassCodeVisitorSupport {
        private final boolean onlySharedVariables;
        private final Map<VariableExpression, ClassNode> varOrigType;

        public VariableExpressionTypeMemoizer(Map<VariableExpression, ClassNode> varOrigType) {
            this(varOrigType, false);
        }

        public VariableExpressionTypeMemoizer(Map<VariableExpression, ClassNode> varOrigType, boolean onlySharedVariables) {
            this.varOrigType = varOrigType;
            this.onlySharedVariables = onlySharedVariables;
        }

        @Override
        protected SourceUnit getSourceUnit() {
            return StaticTypeCheckingVisitor.this.getSourceUnit();
        }

        @Override
        public void visitVariableExpression(VariableExpression expression) {
            Variable var = StaticTypeCheckingSupport.findTargetVariable(expression);
            if ((!this.onlySharedVariables || var.isClosureSharedVariable()) && var instanceof VariableExpression) {
                VariableExpression ve = (VariableExpression)var;
                ClassNode cn = (ClassNode)ve.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
                if (cn == null) {
                    cn = ve.getOriginType();
                }
                this.varOrigType.put(ve, cn);
            }
            super.visitVariableExpression(expression);
        }
    }

    public static class SignatureCodecFactory {
        public static SignatureCodec getCodec(int version, ClassLoader classLoader) {
            switch (version) {
                case 1: {
                    return new SignatureCodecVersion1(classLoader);
                }
            }
            return null;
        }
    }

    private static class ExtensionMethodDeclaringClass {
        private ExtensionMethodDeclaringClass() {
        }
    }
}

