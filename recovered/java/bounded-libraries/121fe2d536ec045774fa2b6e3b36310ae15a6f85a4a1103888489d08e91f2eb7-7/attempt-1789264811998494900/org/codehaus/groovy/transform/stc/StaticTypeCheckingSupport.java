/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.stc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.stream.BaseStream;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.DefaultGroovyMethodsSupport;
import org.codehaus.groovy.runtime.metaclass.MetaClassRegistryImpl;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.tools.GroovyClass;
import org.codehaus.groovy.transform.stc.ExtensionMethodCache;
import org.codehaus.groovy.transform.stc.ExtensionMethodNode;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;
import org.codehaus.groovy.transform.stc.TypeCheckingContext;
import org.codehaus.groovy.transform.stc.UnionTypeClassNode;
import org.codehaus.groovy.transform.trait.Traits;

public abstract class StaticTypeCheckingSupport {
    protected static final ClassNode Matcher_TYPE = ClassHelper.makeWithoutCaching(Matcher.class);
    protected static final ClassNode ArrayList_TYPE = ClassHelper.makeWithoutCaching(ArrayList.class);
    protected static final ClassNode BaseStream_TYPE = ClassHelper.makeWithoutCaching(BaseStream.class);
    protected static final ClassNode Collection_TYPE = ClassHelper.COLLECTION_TYPE;
    protected static final ClassNode Deprecated_TYPE = ClassHelper.DEPRECATED_TYPE;
    protected static final ClassNode LinkedHashMap_TYPE = ClassHelper.makeWithoutCaching(LinkedHashMap.class);
    protected static final ClassNode LinkedHashSet_TYPE = ClassHelper.makeWithoutCaching(LinkedHashSet.class);
    protected static final Map<ClassNode, Integer> NUMBER_TYPES = Maps.of(ClassHelper.byte_TYPE, 0, ClassHelper.Byte_TYPE, 0, ClassHelper.short_TYPE, 1, ClassHelper.Short_TYPE, 1, ClassHelper.int_TYPE, 2, ClassHelper.Integer_TYPE, 2, ClassHelper.Long_TYPE, 3, ClassHelper.long_TYPE, 3, ClassHelper.float_TYPE, 4, ClassHelper.Float_TYPE, 4, ClassHelper.double_TYPE, 5, ClassHelper.Double_TYPE, 5);
    protected static final Map<String, Integer> NUMBER_OPS = Maps.of("plus", 200, "minus", 201, "multiply", 202, "div", 203, "or", 340, "and", 341, "xor", 342, "mod", 205, "intdiv", 204, "leftShift", 280, "rightShift", 281, "rightShiftUnsigned", 282);
    protected static final ClassNode GSTRING_STRING_CLASSNODE = WideningCategories.lowestUpperBound(ClassHelper.STRING_TYPE, ClassHelper.GSTRING_TYPE);
    protected static final ClassNode UNKNOWN_PARAMETER_TYPE = ClassHelper.make("<unknown parameter type>");
    protected static final Comparator<MethodNode> DGM_METHOD_NODE_COMPARATOR = (mn1, mn2) -> {
        if (mn1.getName().equals(mn2.getName())) {
            Parameter[] pa2;
            Parameter[] pa1 = mn1.getParameters();
            if (pa1.length == (pa2 = mn2.getParameters()).length) {
                boolean allEqual = true;
                int n = pa1.length;
                for (int i = 0; i < n && allEqual; ++i) {
                    allEqual = pa1[i].getType().equals(pa2[i].getType());
                }
                if (allEqual) {
                    if (mn1 instanceof ExtensionMethodNode && mn2 instanceof ExtensionMethodNode) {
                        return DGM_METHOD_NODE_COMPARATOR.compare(((ExtensionMethodNode)mn1).getExtensionMethodNode(), ((ExtensionMethodNode)mn2).getExtensionMethodNode());
                    }
                    return 0;
                }
            } else {
                return pa1.length - pa2.length;
            }
        }
        return 1;
    };
    protected static final ExtensionMethodCache EXTENSION_METHOD_CACHE = ExtensionMethodCache.INSTANCE;

    public static void clearExtensionMethodCache() {
        StaticTypeCheckingSupport.EXTENSION_METHOD_CACHE.cache.clearAll();
    }

    protected static boolean isArrayAccessExpression(Expression expression) {
        return expression instanceof BinaryExpression && StaticTypeCheckingSupport.isArrayOp(((BinaryExpression)expression).getOperation().getType());
    }

    public static boolean isWithCall(String name, Expression arguments) {
        List<Expression> args;
        return "with".equals(name) && arguments instanceof ArgumentListExpression && (args = ((ArgumentListExpression)arguments).getExpressions()).size() == 1 && args.get(0) instanceof ClosureExpression;
    }

    protected static Variable findTargetVariable(VariableExpression ve) {
        Variable accessedVariable = ve.getAccessedVariable();
        if (accessedVariable != null && accessedVariable != ve) {
            if (accessedVariable instanceof VariableExpression) {
                return StaticTypeCheckingSupport.findTargetVariable((VariableExpression)accessedVariable);
            }
            return accessedVariable;
        }
        return ve;
    }

    @Deprecated
    protected static Set<MethodNode> findDGMMethodsForClassNode(ClassNode clazz, String name) {
        return StaticTypeCheckingSupport.findDGMMethodsForClassNode(MetaClassRegistryImpl.class.getClassLoader(), clazz, name);
    }

    public static Set<MethodNode> findDGMMethodsForClassNode(ClassLoader loader, ClassNode clazz, String name) {
        TreeSet<MethodNode> accumulator = new TreeSet<MethodNode>(DGM_METHOD_NODE_COMPARATOR);
        StaticTypeCheckingSupport.findDGMMethodsForClassNode(loader, clazz, name, accumulator);
        return accumulator;
    }

    @Deprecated
    protected static void findDGMMethodsForClassNode(ClassNode clazz, String name, TreeSet<MethodNode> accumulator) {
        StaticTypeCheckingSupport.findDGMMethodsForClassNode(MetaClassRegistryImpl.class.getClassLoader(), clazz, name, accumulator);
    }

    protected static void findDGMMethodsForClassNode(ClassLoader loader, ClassNode clazz, String name, TreeSet<MethodNode> accumulator) {
        ClassNode componentClass;
        List<MethodNode> fromDGM = EXTENSION_METHOD_CACHE.get(loader).get(clazz.getName());
        if (fromDGM != null) {
            for (MethodNode node : fromDGM) {
                if (!node.getName().equals(name)) continue;
                accumulator.add(node);
            }
        }
        for (ClassNode node : clazz.getInterfaces()) {
            StaticTypeCheckingSupport.findDGMMethodsForClassNode(loader, node, name, accumulator);
        }
        if (clazz.isArray() && !ClassHelper.isObjectType(componentClass = clazz.getComponentType()) && !ClassHelper.isPrimitiveType(componentClass)) {
            if (componentClass.isInterface()) {
                StaticTypeCheckingSupport.findDGMMethodsForClassNode(loader, ClassHelper.OBJECT_TYPE.makeArray(), name, accumulator);
            } else {
                StaticTypeCheckingSupport.findDGMMethodsForClassNode(loader, componentClass.getSuperClass().makeArray(), name, accumulator);
            }
        }
        if (clazz.getSuperClass() != null) {
            StaticTypeCheckingSupport.findDGMMethodsForClassNode(loader, clazz.getSuperClass(), name, accumulator);
        } else if (!ClassHelper.isObjectType(clazz)) {
            StaticTypeCheckingSupport.findDGMMethodsForClassNode(loader, ClassHelper.OBJECT_TYPE, name, accumulator);
        }
    }

    public static int allParametersAndArgumentsMatch(Parameter[] parameters, ClassNode[] argumentTypes) {
        if (parameters == null) {
            parameters = Parameter.EMPTY_ARRAY;
        }
        int dist = 0;
        if (argumentTypes.length < parameters.length) {
            return -1;
        }
        int n = parameters.length;
        for (int i = 0; i < n; ++i) {
            ClassNode argType = argumentTypes[i];
            ClassNode paramType = parameters[i].getType();
            if (!StaticTypeCheckingSupport.isAssignableTo(argType, paramType)) {
                return -1;
            }
            if (paramType.equals(argType)) continue;
            dist += StaticTypeCheckingSupport.getDistance(argType, paramType);
        }
        return dist;
    }

    static int allParametersAndArgumentsMatchWithDefaultParams(Parameter[] parameters, ClassNode[] argumentTypes) {
        int dist = 0;
        ClassNode ptype = null;
        int j = 0;
        for (Parameter param : parameters) {
            ClassNode arg;
            ClassNode paramType = param.getType();
            ClassNode classNode = arg = j >= argumentTypes.length ? null : argumentTypes[j];
            if (arg == null || !StaticTypeCheckingSupport.isAssignableTo(arg, paramType)) {
                if (!(param.hasInitialExpression() || ptype != null && ptype.equals(paramType))) {
                    return -1;
                }
                ptype = null;
                continue;
            }
            ++j;
            if (!paramType.equals(arg)) {
                dist += StaticTypeCheckingSupport.getDistance(arg, paramType);
            }
            ptype = param.hasInitialExpression() ? arg : null;
        }
        return dist;
    }

    static int excessArgumentsMatchesVargsParameter(Parameter[] parameters, ClassNode[] argumentTypes) {
        int dist = 0;
        ClassNode vargsBase = parameters[parameters.length - 1].getType().getComponentType();
        for (int i = parameters.length; i < argumentTypes.length; ++i) {
            if (!StaticTypeCheckingSupport.isAssignableTo(argumentTypes[i], vargsBase)) {
                return -1;
            }
            dist += StaticTypeCheckingSupport.getClassDistance(vargsBase, argumentTypes[i]);
        }
        return dist;
    }

    static int lastArgMatchesVarg(Parameter[] parameters, ClassNode ... argumentTypes) {
        if (!StaticTypeCheckingSupport.isVargs(parameters)) {
            return -1;
        }
        int lastParamIndex = parameters.length - 1;
        if (lastParamIndex == argumentTypes.length) {
            return 0;
        }
        ClassNode arrayType = parameters[lastParamIndex].getType();
        ClassNode elementType = arrayType.getComponentType();
        ClassNode argumentType = argumentTypes[argumentTypes.length - 1];
        if (ClassHelper.isNumberType(elementType) && ClassHelper.isNumberType(argumentType) && !ClassHelper.getWrapper(elementType).equals(ClassHelper.getWrapper(argumentType))) {
            return -1;
        }
        return StaticTypeCheckingSupport.isAssignableTo(argumentType, elementType) ? Math.min(StaticTypeCheckingSupport.getDistance(argumentType, arrayType), StaticTypeCheckingSupport.getDistance(argumentType, elementType)) : -1;
    }

    public static boolean isAssignableTo(ClassNode type, ClassNode toBeAssignedTo) {
        if (type == toBeAssignedTo || type == UNKNOWN_PARAMETER_TYPE) {
            return true;
        }
        if (ClassHelper.isPrimitiveType(type)) {
            type = ClassHelper.getWrapper(type);
        }
        if (ClassHelper.isPrimitiveType(toBeAssignedTo)) {
            toBeAssignedTo = ClassHelper.getWrapper(toBeAssignedTo);
        }
        if (NUMBER_TYPES.containsKey(type.redirect()) && NUMBER_TYPES.containsKey(toBeAssignedTo.redirect())) {
            return NUMBER_TYPES.get(type.redirect()) <= NUMBER_TYPES.get(toBeAssignedTo.redirect());
        }
        if (type.isArray() && toBeAssignedTo.isArray()) {
            return StaticTypeCheckingSupport.isAssignableTo(type.getComponentType(), toBeAssignedTo.getComponentType());
        }
        if (type.isDerivedFrom(ClassHelper.GSTRING_TYPE) && ClassHelper.isStringType(toBeAssignedTo)) {
            return true;
        }
        if (ClassHelper.isStringType(type) && toBeAssignedTo.isDerivedFrom(ClassHelper.GSTRING_TYPE)) {
            return true;
        }
        if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(type, toBeAssignedTo)) {
            if (toBeAssignedTo.getGenericsTypes() != null) {
                GenericsType gt = toBeAssignedTo.isGenericsPlaceHolder() ? toBeAssignedTo.getGenericsTypes()[0] : GenericsUtils.buildWildcardType(toBeAssignedTo);
                return gt.isCompatibleWith(type);
            }
            return true;
        }
        if (type.isGenericsPlaceHolder() && type.getUnresolvedName().charAt(0) == '#') {
            return type.getGenericsTypes()[0].isCompatibleWith(toBeAssignedTo);
        }
        return type.isDerivedFrom(ClassHelper.CLOSURE_TYPE) && ClassHelper.isSAMType(toBeAssignedTo);
    }

    @Deprecated
    static boolean isVargs(Parameter[] parameters) {
        return ParameterUtils.isVargs(parameters);
    }

    public static boolean isCompareToBoolean(int op) {
        return op == 124 || op == 125 || op == 126 || op == 127;
    }

    static boolean isArrayOp(int op) {
        return op == 30;
    }

    static boolean isBoolIntrinsicOp(int op) {
        switch (op) {
            case 94: 
            case 121: 
            case 122: 
            case 130: 
            case 162: 
            case 164: 
            case 544: {
                return true;
            }
        }
        return false;
    }

    static boolean isPowerOperator(int op) {
        return op == 206 || op == 216;
    }

    static String getOperationName(int op) {
        switch (op) {
            case 120: 
            case 123: {
                return "equals";
            }
            case 124: 
            case 125: 
            case 126: 
            case 127: 
            case 128: {
                return "compareTo";
            }
            case 341: 
            case 351: {
                return "and";
            }
            case 340: 
            case 350: {
                return "or";
            }
            case 342: 
            case 352: {
                return "xor";
            }
            case 200: 
            case 210: {
                return "plus";
            }
            case 201: 
            case 211: {
                return "minus";
            }
            case 202: 
            case 212: {
                return "multiply";
            }
            case 203: 
            case 213: {
                return "div";
            }
            case 204: 
            case 214: {
                return "intdiv";
            }
            case 205: 
            case 215: {
                return "mod";
            }
            case 206: 
            case 216: {
                return "power";
            }
            case 280: 
            case 285: {
                return "leftShift";
            }
            case 281: 
            case 286: {
                return "rightShift";
            }
            case 282: 
            case 287: {
                return "rightShiftUnsigned";
            }
            case 573: {
                return "isCase";
            }
            case 129: {
                return "isNotCase";
            }
        }
        return null;
    }

    static boolean isShiftOperation(String name) {
        return "leftShift".equals(name) || "rightShift".equals(name) || "rightShiftUnsigned".equals(name);
    }

    static boolean isOperationInGroup(int op) {
        switch (op) {
            case 200: 
            case 201: 
            case 202: 
            case 210: 
            case 211: 
            case 212: {
                return true;
            }
        }
        return false;
    }

    static boolean isBitOperator(int op) {
        switch (op) {
            case 340: 
            case 341: 
            case 342: 
            case 350: 
            case 351: 
            case 352: {
                return true;
            }
        }
        return false;
    }

    public static boolean isAssignment(int op) {
        return Types.isAssignment(op);
    }

    public static boolean checkCompatibleAssignmentTypes(ClassNode left, ClassNode right) {
        return StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(left, right, null);
    }

    public static boolean checkCompatibleAssignmentTypes(ClassNode left, ClassNode right, Expression rightExpression) {
        return StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(left, right, rightExpression, true);
    }

    public static boolean checkCompatibleAssignmentTypes(ClassNode left, ClassNode right, Expression rightExpression, boolean allowConstructorCoercion) {
        ClassNode rightRedirect;
        ClassNode leftRedirect;
        if (!ClassHelper.isPrimitiveType(left) && ExpressionUtils.isNullConstant(rightExpression)) {
            return true;
        }
        if (left.isArray()) {
            if (right.isArray()) {
                return StaticTypeCheckingSupport.checkCompatibleAssignmentTypes(left.getComponentType(), right.getComponentType(), rightExpression, false);
            }
            if (GeneralUtils.isOrImplements(right, Collection_TYPE) && !(rightExpression instanceof ListExpression)) {
                GenericsType elementType = GenericsUtils.parameterizeType(right, Collection_TYPE).getGenericsTypes()[0];
                return ClassHelper.OBJECT_TYPE.equals(left.getComponentType()) || elementType.getLowerBound() == null && StaticTypeCheckingSupport.isCovariant(StaticTypeCheckingSupport.extractType(elementType), left.getComponentType());
            }
            if (GeneralUtils.isOrImplements(right, BaseStream_TYPE)) {
                GenericsType elementType = GenericsUtils.parameterizeType(right, BaseStream_TYPE).getGenericsTypes()[0];
                return ClassHelper.isObjectType(left.getComponentType()) || elementType.getLowerBound() == null && StaticTypeCheckingSupport.isCovariant(StaticTypeCheckingSupport.extractType(elementType), ClassHelper.getWrapper(left.getComponentType()));
            }
        }
        if ((leftRedirect = left.redirect()) == (rightRedirect = right.redirect())) {
            return true;
        }
        if (leftRedirect == ClassHelper.VOID_TYPE) {
            return rightRedirect == ClassHelper.void_WRAPPER_TYPE;
        }
        if (leftRedirect == ClassHelper.void_WRAPPER_TYPE) {
            return rightRedirect == ClassHelper.VOID_TYPE;
        }
        if (WideningCategories.isLongCategory(ClassHelper.getUnwrapper(leftRedirect))) {
            if (ClassHelper.isNumberType(rightRedirect)) {
                return true;
            }
            if (leftRedirect == ClassHelper.char_TYPE && rightRedirect == ClassHelper.Character_TYPE) {
                return true;
            }
            if (leftRedirect == ClassHelper.Character_TYPE && rightRedirect == ClassHelper.char_TYPE) {
                return true;
            }
            if ((leftRedirect == ClassHelper.char_TYPE || leftRedirect == ClassHelper.Character_TYPE) && rightRedirect == ClassHelper.STRING_TYPE) {
                return rightExpression instanceof ConstantExpression && rightExpression.getText().length() == 1;
            }
        } else if (WideningCategories.isFloatingCategory(ClassHelper.getUnwrapper(leftRedirect))) {
            if (ClassHelper.isNumberType(rightRedirect) || ClassHelper.isBigDecimalType(rightRedirect)) {
                return true;
            }
        } else {
            if (left.isGenericsPlaceHolder()) {
                return right.getUnresolvedName().charAt(0) != '#' ? left.getGenericsTypes()[0].isCompatibleWith(right) : WideningCategories.implementsInterfaceOrSubclassOf(leftRedirect, rightRedirect);
            }
            if (ClassHelper.isBigDecimalType(leftRedirect) || ClassHelper.Number_TYPE.equals(leftRedirect)) {
                if (ClassHelper.isNumberType(rightRedirect) || rightRedirect.isDerivedFrom(ClassHelper.Number_TYPE)) {
                    return true;
                }
            } else if (ClassHelper.isBigIntegerType(leftRedirect)) {
                if (WideningCategories.isLongCategory(ClassHelper.getUnwrapper(rightRedirect)) || rightRedirect.isDerivedFrom(ClassHelper.BigInteger_TYPE)) {
                    return true;
                }
            } else if (leftRedirect.isDerivedFrom(ClassHelper.Enum_Type)) {
                if (rightRedirect == ClassHelper.STRING_TYPE || StaticTypeCheckingSupport.isGStringOrGStringStringLUB(rightRedirect)) {
                    return true;
                }
            } else if (StaticTypeCheckingSupport.isWildcardLeftHandSide(leftRedirect)) {
                return leftRedirect != ClassHelper.boolean_TYPE || !ExpressionUtils.isNullConstant(rightExpression);
            }
        }
        if (allowConstructorCoercion && StaticTypeCheckingSupport.isGroovyConstructorCompatible(rightExpression)) {
            return !rightRedirect.isArray() || leftRedirect.isArray();
        }
        if (!left.isInterface() ? right.isDerivedFrom(left) : GeneralUtils.isOrImplements(right, left)) {
            return true;
        }
        if (right.isDerivedFrom(ClassHelper.CLOSURE_TYPE) && ClassHelper.isSAMType(left)) {
            return true;
        }
        return right.isGenericsPlaceHolder() && right.asGenericsType().isCompatibleWith(left);
    }

    private static boolean isGroovyConstructorCompatible(Expression rightExpression) {
        return rightExpression instanceof ListExpression || rightExpression instanceof MapExpression || rightExpression instanceof ArrayExpression;
    }

    public static boolean isWildcardLeftHandSide(ClassNode node) {
        return ClassHelper.isObjectType(node) || ClassHelper.isStringType(node) || ClassHelper.isPrimitiveBoolean(node) || ClassHelper.isWrapperBoolean(node) || ClassHelper.isClassType(node);
    }

    public static boolean isBeingCompiled(ClassNode node) {
        return node.getCompileUnit() != null;
    }

    @Deprecated
    static boolean checkPossibleLooseOfPrecision(ClassNode left, ClassNode right, Expression rightExpr) {
        return StaticTypeCheckingSupport.checkPossibleLossOfPrecision(left, right, rightExpr);
    }

    static boolean checkPossibleLossOfPrecision(ClassNode left, ClassNode right, Expression rightExpr) {
        int rightIndex;
        if (left == right || left.equals(right)) {
            return false;
        }
        int leftIndex = NUMBER_TYPES.get(left);
        if (leftIndex >= (rightIndex = NUMBER_TYPES.get(right).intValue())) {
            return false;
        }
        if (rightExpr instanceof ConstantExpression) {
            Object value = ((ConstantExpression)rightExpr).getValue();
            if (!(value instanceof Number)) {
                return true;
            }
            Number number = (Number)value;
            switch (leftIndex) {
                case 0: {
                    byte val = number.byteValue();
                    if (number instanceof Short) {
                        return !Short.valueOf(val).equals(number);
                    }
                    if (number instanceof Integer) {
                        return !Integer.valueOf(val).equals(number);
                    }
                    if (number instanceof Long) {
                        return !Long.valueOf(val).equals(number);
                    }
                    if (number instanceof Float) {
                        return !Float.valueOf(val).equals(number);
                    }
                    return !Double.valueOf(val).equals(number);
                }
                case 1: {
                    short val = number.shortValue();
                    if (number instanceof Integer) {
                        return !Integer.valueOf(val).equals(number);
                    }
                    if (number instanceof Long) {
                        return !Long.valueOf(val).equals(number);
                    }
                    if (number instanceof Float) {
                        return !Float.valueOf(val).equals(number);
                    }
                    return !Double.valueOf(val).equals(number);
                }
                case 2: {
                    int val = number.intValue();
                    if (number instanceof Long) {
                        return !Long.valueOf(val).equals(number);
                    }
                    if (number instanceof Float) {
                        return !Float.valueOf(val).equals(number);
                    }
                    return !Double.valueOf(val).equals(number);
                }
                case 3: {
                    long val = number.longValue();
                    if (number instanceof Float) {
                        return !Float.valueOf(val).equals(number);
                    }
                    return !Double.valueOf(val).equals(number);
                }
                case 4: {
                    float val = number.floatValue();
                    return !Double.valueOf(val).equals(number);
                }
            }
            return false;
        }
        return true;
    }

    static String toMethodParametersString(String methodName, ClassNode ... parameters) {
        if (parameters == null || parameters.length == 0) {
            return methodName + "()";
        }
        StringJoiner joiner = new StringJoiner(", ", methodName + "(", ")");
        for (ClassNode parameter : parameters) {
            joiner.add(StaticTypeCheckingSupport.prettyPrintType(parameter));
        }
        return joiner.toString();
    }

    static String prettyPrintType(ClassNode type) {
        if (type.getUnresolvedName().charAt(0) == '#') {
            return type.redirect().toString(false);
        }
        return type.toString(false);
    }

    static String prettyPrintTypeName(ClassNode type) {
        if (type.isArray()) {
            return StaticTypeCheckingSupport.prettyPrintTypeName(type.getComponentType()) + "[]";
        }
        return type.isGenericsPlaceHolder() ? type.getUnresolvedName() : type.getText();
    }

    public static boolean implementsInterfaceOrIsSubclassOf(ClassNode type, ClassNode superOrInterface) {
        boolean result;
        boolean bl = result = type.equals(superOrInterface) || type.isDerivedFrom(superOrInterface) || type.implementsInterface(superOrInterface) || type == UNKNOWN_PARAMETER_TYPE;
        if (result) {
            return true;
        }
        if (superOrInterface instanceof WideningCategories.LowestUpperBoundClassNode) {
            WideningCategories.LowestUpperBoundClassNode cn = (WideningCategories.LowestUpperBoundClassNode)superOrInterface;
            result = StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(type, cn.getSuperClass());
            if (result) {
                ClassNode interfaceNode;
                ClassNode[] classNodeArray = cn.getInterfaces();
                int n = classNodeArray.length;
                for (int i = 0; i < n && (result = type.implementsInterface(interfaceNode = classNodeArray[i])); ++i) {
                }
            }
            if (result) {
                return true;
            }
        } else if (superOrInterface instanceof UnionTypeClassNode) {
            UnionTypeClassNode union = (UnionTypeClassNode)superOrInterface;
            for (ClassNode delegate : union.getDelegates()) {
                if (!StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(type, delegate)) continue;
                return true;
            }
        }
        if (type.isArray() && superOrInterface.isArray()) {
            return StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(type.getComponentType(), superOrInterface.getComponentType());
        }
        return ClassHelper.isGroovyObjectType(superOrInterface) && StaticTypeCheckingSupport.isBeingCompiled(type) && !type.isInterface();
    }

    static int getPrimitiveDistance(ClassNode primA, ClassNode primB) {
        return Math.abs(NUMBER_TYPES.get(primA) - NUMBER_TYPES.get(primB));
    }

    static int getDistance(ClassNode receiver, ClassNode compare) {
        ClassNode cn;
        if (receiver.isArray() && compare.isArray()) {
            return StaticTypeCheckingSupport.getDistance(receiver.getComponentType(), compare.getComponentType());
        }
        int dist = 0;
        ClassNode unwrapReceiver = ClassHelper.getUnwrapper(receiver);
        ClassNode unwrapCompare = ClassHelper.getUnwrapper(compare);
        if (ClassHelper.isPrimitiveType(unwrapReceiver) && ClassHelper.isPrimitiveType(unwrapCompare) && unwrapReceiver != unwrapCompare) {
            dist = StaticTypeCheckingSupport.getPrimitiveDistance(unwrapReceiver, unwrapCompare);
        }
        if (ClassHelper.isPrimitiveType(receiver) ^ ClassHelper.isPrimitiveType(compare)) {
            dist = dist + 1 << 1;
        }
        if (unwrapCompare.equals(unwrapReceiver) || receiver == UNKNOWN_PARAMETER_TYPE) {
            return dist;
        }
        if (receiver.isArray()) {
            dist += 256;
        }
        if (compare.isInterface()) {
            MethodNode sam;
            if (receiver.implementsInterface(compare)) {
                return dist + StaticTypeCheckingSupport.getMaximumInterfaceDistance(receiver, compare);
            }
            if (receiver.equals(ClassHelper.CLOSURE_TYPE) && (sam = ClassHelper.findSAM(compare)) != null) {
                Integer closureParamCount = (Integer)receiver.getNodeMetaData((Object)StaticTypesMarker.CLOSURE_ARGUMENTS);
                if (closureParamCount != null && closureParamCount == sam.getParameters().length) {
                    --dist;
                }
                return dist + 13;
            }
        }
        ClassNode classNode = cn = ClassHelper.isPrimitiveType(receiver) && !ClassHelper.isPrimitiveType(compare) ? ClassHelper.getWrapper(receiver) : receiver;
        while (cn != null && !cn.equals(compare)) {
            cn = cn.getSuperClass();
            ++dist;
            if (ClassHelper.isObjectType(cn)) {
                ++dist;
            }
            dist = dist + 1 << 1;
        }
        return dist;
    }

    private static int getMaximumInterfaceDistance(ClassNode c, ClassNode interfaceClass) {
        if (c == null) {
            return -1;
        }
        if (c.equals(interfaceClass)) {
            return 0;
        }
        ClassNode[] interfaces = c.getInterfaces();
        int max = -1;
        for (ClassNode anInterface : interfaces) {
            int sub = StaticTypeCheckingSupport.getMaximumInterfaceDistance(anInterface, interfaceClass);
            if (sub != -1) {
                ++sub;
            }
            max = Math.max(max, sub);
        }
        int superClassMax = StaticTypeCheckingSupport.getMaximumInterfaceDistance(c.getSuperClass(), interfaceClass);
        return Math.max(max, superClassMax);
    }

    @Deprecated
    public static List<MethodNode> findDGMMethodsByNameAndArguments(ClassNode receiver, String name, ClassNode[] args) {
        return StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(MetaClassRegistryImpl.class.getClassLoader(), receiver, name, args);
    }

    public static List<MethodNode> findDGMMethodsByNameAndArguments(ClassLoader loader, ClassNode receiver, String name, ClassNode[] args) {
        return StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(loader, receiver, name, args, new LinkedList<MethodNode>());
    }

    @Deprecated
    public static List<MethodNode> findDGMMethodsByNameAndArguments(ClassNode receiver, String name, ClassNode[] args, List<MethodNode> methods) {
        return StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(MetaClassRegistryImpl.class.getClassLoader(), receiver, name, args, methods);
    }

    public static List<MethodNode> findDGMMethodsByNameAndArguments(ClassLoader loader, ClassNode receiver, String name, ClassNode[] args, List<MethodNode> methods) {
        methods.addAll(StaticTypeCheckingSupport.findDGMMethodsForClassNode(loader, receiver, name));
        return methods.isEmpty() ? methods : StaticTypeCheckingSupport.chooseBestMethod(receiver, methods, args);
    }

    public static boolean isUsingUncheckedGenerics(ClassNode node) {
        return GenericsUtils.hasUnresolvedGenerics(node);
    }

    public static List<MethodNode> chooseBestMethod(ClassNode receiver, Collection<MethodNode> methods, ClassNode ... argumentTypes) {
        if (!DefaultGroovyMethods.asBoolean(methods)) {
            return Collections.emptyList();
        }
        int bestDist = Integer.MAX_VALUE;
        LinkedList<MethodNode> bestChoices = new LinkedList<MethodNode>();
        boolean noCulling = methods.size() <= 1 || "<init>".equals(methods.iterator().next().getName());
        Collection<MethodNode> candidates = noCulling ? methods : StaticTypeCheckingSupport.removeCovariantsAndInterfaceEquivalents(methods);
        Iterator iterator = candidates.iterator();
        while (iterator.hasNext()) {
            Parameter[] params;
            int dist;
            Map<GenericsType, GenericsType> spec;
            ClassNode actualReceiver;
            MethodNode candidate;
            MethodNode safeNode = candidate = (MethodNode)iterator.next();
            ClassNode[] safeArgs = argumentTypes;
            boolean isExtensionMethod = candidate instanceof ExtensionMethodNode;
            if (isExtensionMethod) {
                int nArgs = argumentTypes.length;
                safeArgs = new ClassNode[nArgs + 1];
                System.arraycopy(argumentTypes, 0, safeArgs, 1, nArgs);
                safeArgs[0] = receiver;
                safeNode = ((ExtensionMethodNode)candidate).getExtensionMethodNode();
            }
            ClassNode declaringClass = candidate.getDeclaringClass();
            ClassNode classNode = actualReceiver = receiver != null ? receiver : declaringClass;
            if (candidate.isStatic()) {
                spec = Collections.emptyMap();
            } else {
                spec = GenericsUtils.makeDeclaringAndActualGenericsTypeMapOfExactType(declaringClass, actualReceiver);
                GenericsType[] methodGenerics = candidate.getGenericsTypes();
                if (methodGenerics != null) {
                    int n = methodGenerics.length;
                    for (int i = 0; i < n && !spec.isEmpty(); ++i) {
                        Iterator<GenericsType> it = spec.keySet().iterator();
                        while (it.hasNext()) {
                            if (!it.next().getName().equals(methodGenerics[i].getName())) continue;
                            it.remove();
                        }
                    }
                }
            }
            if ((dist = StaticTypeCheckingSupport.measureParametersAndArgumentsDistance(params = StaticTypeCheckingSupport.makeRawTypes(safeNode.getParameters(), spec), safeArgs)) < 0) continue;
            dist += StaticTypeCheckingSupport.getClassDistance(declaringClass, actualReceiver);
            if ((dist += StaticTypeCheckingSupport.getExtensionDistance(isExtensionMethod)) < bestDist) {
                bestDist = dist;
                bestChoices.clear();
                bestChoices.add(candidate);
                continue;
            }
            if (dist != bestDist) continue;
            bestChoices.add(candidate);
        }
        if (bestChoices.size() > 1) {
            LinkedList<MethodNode> onlyExtensionMethods = new LinkedList<MethodNode>();
            for (MethodNode choice : bestChoices) {
                if (!(choice instanceof ExtensionMethodNode)) continue;
                onlyExtensionMethods.add(choice);
            }
            if (onlyExtensionMethods.size() == 1) {
                return onlyExtensionMethods;
            }
        }
        return bestChoices;
    }

    private static int measureParametersAndArgumentsDistance(Parameter[] parameters, ClassNode[] argumentTypes) {
        int dist = -1;
        if (parameters.length == argumentTypes.length) {
            int allMatch = StaticTypeCheckingSupport.allParametersAndArgumentsMatch(parameters, argumentTypes);
            int endMatch = -1;
            if (StaticTypeCheckingSupport.isVargs(parameters) && StaticTypeCheckingSupport.firstParametersAndArgumentsMatch(parameters, argumentTypes) >= 0 && (endMatch = StaticTypeCheckingSupport.lastArgMatchesVarg(parameters, argumentTypes)) >= 0) {
                endMatch += StaticTypeCheckingSupport.getVarargsDistance(parameters);
            }
            dist = allMatch >= 0 ? Math.max(allMatch, endMatch) : endMatch;
        } else if (StaticTypeCheckingSupport.isVargs(parameters) && (dist = StaticTypeCheckingSupport.firstParametersAndArgumentsMatch(parameters, argumentTypes)) >= 0) {
            dist += StaticTypeCheckingSupport.getVarargsDistance(parameters);
            if (parameters.length < argumentTypes.length) {
                int excessArgumentsDistance = StaticTypeCheckingSupport.excessArgumentsMatchesVargsParameter(parameters, argumentTypes);
                dist = excessArgumentsDistance >= 0 ? (dist += excessArgumentsDistance) : -1;
            }
        }
        return dist;
    }

    private static int firstParametersAndArgumentsMatch(Parameter[] parameters, ClassNode[] safeArgumentTypes) {
        int dist = 0;
        if (parameters.length > 0) {
            Parameter[] firstParams = new Parameter[parameters.length - 1];
            System.arraycopy(parameters, 0, firstParams, 0, firstParams.length);
            dist = StaticTypeCheckingSupport.allParametersAndArgumentsMatch(firstParams, safeArgumentTypes);
        }
        return dist;
    }

    private static int getVarargsDistance(Parameter[] parameters) {
        return 256 - parameters.length;
    }

    private static int getClassDistance(ClassNode declaringClassForDistance, ClassNode actualReceiverForDistance) {
        if (actualReceiverForDistance.equals(declaringClassForDistance)) {
            return 0;
        }
        return StaticTypeCheckingSupport.getDistance(actualReceiverForDistance, declaringClassForDistance);
    }

    private static int getExtensionDistance(boolean isExtensionMethodNode) {
        return isExtensionMethodNode ? 0 : 1;
    }

    private static Parameter[] makeRawTypes(Parameter[] parameters, Map<GenericsType, GenericsType> genericsPlaceholderAndTypeMap) {
        return (Parameter[])Arrays.stream(parameters).map(param -> {
            String name = param.getType().getUnresolvedName();
            Optional<GenericsType> value = genericsPlaceholderAndTypeMap.entrySet().stream().filter(e -> ((GenericsType)e.getKey()).getName().equals(name)).findFirst().map(Map.Entry::getValue);
            ClassNode type = value.map(gt -> !gt.isPlaceholder() ? gt.getType() : StaticTypeCheckingSupport.makeRawType(gt.getType())).orElseGet(() -> StaticTypeCheckingSupport.makeRawType(param.getType()));
            return new Parameter(type, param.getName());
        }).toArray(Parameter[]::new);
    }

    private static ClassNode makeRawType(ClassNode receiver) {
        if (receiver.isArray()) {
            return StaticTypeCheckingSupport.makeRawType(receiver.getComponentType()).makeArray();
        }
        ClassNode raw = receiver.getPlainNodeReference();
        raw.setUsingGenerics(false);
        raw.setGenericsTypes(null);
        return raw;
    }

    private static List<MethodNode> removeCovariantsAndInterfaceEquivalents(Collection<MethodNode> collection) {
        ArrayList<MethodNode> toBeRemoved = new ArrayList<MethodNode>();
        ArrayList<MethodNode> list = new ArrayList<MethodNode>(new LinkedHashSet<MethodNode>(collection));
        int n = list.size();
        for (int i = 0; i < n - 1; ++i) {
            MethodNode one = (MethodNode)list.get(i);
            if (toBeRemoved.contains(one)) continue;
            for (int j = i + 1; j < n; ++j) {
                ClassNode twoDC;
                MethodNode two = (MethodNode)list.get(j);
                if (toBeRemoved.contains(two) || one.getParameters().length != two.getParameters().length) continue;
                ClassNode oneDC = one.getDeclaringClass();
                if (oneDC == (twoDC = two.getDeclaringClass())) {
                    if (ParameterUtils.parametersEqual(one.getParameters(), two.getParameters())) {
                        ClassNode twoRT;
                        ClassNode oneRT = one.getReturnType();
                        if (StaticTypeCheckingSupport.isCovariant(oneRT, twoRT = two.getReturnType())) {
                            toBeRemoved.add(two);
                            continue;
                        }
                        if (!StaticTypeCheckingSupport.isCovariant(twoRT, oneRT)) continue;
                        toBeRemoved.add(one);
                        continue;
                    }
                    if (one.isSynthetic() && !two.isSynthetic()) {
                        toBeRemoved.add(one);
                        continue;
                    }
                    if (!two.isSynthetic() || one.isSynthetic()) continue;
                    toBeRemoved.add(two);
                    continue;
                }
                if (oneDC.equals(twoDC) || !ParameterUtils.parametersEqual(one.getParameters(), two.getParameters())) continue;
                if (twoDC.isInterface() ? oneDC.implementsInterface(twoDC) : oneDC.isDerivedFrom(twoDC)) {
                    toBeRemoved.add(two);
                    continue;
                }
                if (!(oneDC.isInterface() ? twoDC.isInterface() : twoDC.isDerivedFrom(oneDC))) continue;
                toBeRemoved.add(one);
            }
        }
        if (toBeRemoved.isEmpty()) {
            return list;
        }
        LinkedList<MethodNode> result = new LinkedList<MethodNode>(list);
        result.removeAll(toBeRemoved);
        return result;
    }

    private static boolean isCovariant(ClassNode one, ClassNode two) {
        if (one.isArray() && two.isArray()) {
            return StaticTypeCheckingSupport.isCovariant(one.getComponentType(), two.getComponentType());
        }
        return one.isDerivedFrom(two) || one.implementsInterface(two);
    }

    public static Parameter[] parameterizeArguments(ClassNode receiver, MethodNode m) {
        Map<GenericsType.GenericsTypeName, GenericsType> genericFromReceiver = GenericsUtils.extractPlaceholders(receiver);
        Map<GenericsType.GenericsTypeName, GenericsType> contextPlaceholders = StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(m);
        Parameter[] methodParameters = m.getParameters();
        Parameter[] params = new Parameter[methodParameters.length];
        for (Parameter methodParameter : methodParameters) {
            ClassNode paramType = methodParameter.getType();
            params[i] = StaticTypeCheckingSupport.buildParameter(genericFromReceiver, contextPlaceholders, methodParameter, paramType);
        }
        return params;
    }

    private static Parameter buildParameter(Map<GenericsType.GenericsTypeName, GenericsType> genericFromReceiver, Map<GenericsType.GenericsTypeName, GenericsType> placeholdersFromContext, Parameter methodParameter, ClassNode paramType) {
        if (genericFromReceiver.isEmpty() && (placeholdersFromContext == null || placeholdersFromContext.isEmpty())) {
            return methodParameter;
        }
        if (paramType.isArray()) {
            ClassNode componentType = paramType.getComponentType();
            Parameter subMethodParameter = new Parameter(componentType, methodParameter.getName());
            Parameter component = StaticTypeCheckingSupport.buildParameter(genericFromReceiver, placeholdersFromContext, subMethodParameter, componentType);
            return new Parameter(component.getType().makeArray(), component.getName());
        }
        ClassNode resolved = StaticTypeCheckingSupport.resolveClassNodeGenerics(genericFromReceiver, placeholdersFromContext, paramType);
        return new Parameter(resolved, methodParameter.getName());
    }

    public static boolean isUsingGenericsOrIsArrayUsingGenerics(ClassNode cn) {
        if (cn.isArray()) {
            return StaticTypeCheckingSupport.isUsingGenericsOrIsArrayUsingGenerics(cn.getComponentType());
        }
        return cn.isUsingGenerics() && cn.getGenericsTypes() != null;
    }

    protected static GenericsType fullyResolve(GenericsType gt, Map<GenericsType.GenericsTypeName, GenericsType> placeholders) {
        ClassNode[] upperBounds;
        GenericsType fromMap = placeholders.get(new GenericsType.GenericsTypeName(gt.getName()));
        if (gt.isPlaceholder() && fromMap != null) {
            gt = fromMap;
        }
        ClassNode type = StaticTypeCheckingSupport.fullyResolveType(gt.getType(), placeholders);
        ClassNode lowerBound = gt.getLowerBound();
        if (lowerBound != null) {
            lowerBound = StaticTypeCheckingSupport.fullyResolveType(lowerBound, placeholders);
        }
        if ((upperBounds = gt.getUpperBounds()) != null) {
            ClassNode[] copy = new ClassNode[upperBounds.length];
            for (ClassNode upperBound : upperBounds) {
                copy[i] = StaticTypeCheckingSupport.fullyResolveType(upperBound, placeholders);
            }
            upperBounds = copy;
        }
        GenericsType genericsType = new GenericsType(type, upperBounds, lowerBound);
        genericsType.setWildcard(gt.isWildcard());
        return genericsType;
    }

    protected static ClassNode fullyResolveType(ClassNode type, Map<GenericsType.GenericsTypeName, GenericsType> placeholders) {
        if (type.isArray()) {
            return StaticTypeCheckingSupport.fullyResolveType(type.getComponentType(), placeholders).makeArray();
        }
        if (!type.isUsingGenerics()) {
            return type;
        }
        if (type.isGenericsPlaceHolder()) {
            GenericsType gt = placeholders.get(new GenericsType.GenericsTypeName(type.getUnresolvedName()));
            if (gt != null) {
                return gt.getType();
            }
            ClassNode cn = type.redirect();
            return cn != type ? cn : ClassHelper.OBJECT_TYPE;
        }
        Object[] gts = type.getGenericsTypes();
        if (DefaultGroovyMethods.asBoolean(gts)) {
            gts = (GenericsType[])gts.clone();
            int n = gts.length;
            for (int i = 0; i < n; ++i) {
                Object gt = gts[i];
                if (((GenericsType)gt).isPlaceholder()) {
                    String name = ((GenericsType)gt).getName();
                    if ((gt = placeholders.get(new GenericsType.GenericsTypeName(name))) == null) {
                        gt = StaticTypeCheckingSupport.extractType((GenericsType)gts[i]).asGenericsType();
                    }
                    if (((GenericsType)gt).isPlaceholder() && ((GenericsType)gt).getName().equals(name)) continue;
                    gts[i] = gt;
                    continue;
                }
                gts[i] = StaticTypeCheckingSupport.fullyResolve((GenericsType)gt, placeholders);
            }
        }
        ClassNode cn = type.getPlainNodeReference();
        cn.setGenericsTypes((GenericsType[])gts);
        return cn;
    }

    protected static boolean typeCheckMethodArgumentWithGenerics(ClassNode parameterType, ClassNode argumentType, boolean lastArg) {
        if (UNKNOWN_PARAMETER_TYPE == argumentType) {
            return !ClassHelper.isPrimitiveType(parameterType);
        }
        if (!(StaticTypeCheckingSupport.isAssignableTo(argumentType, parameterType) || lastArg && parameterType.isArray() && StaticTypeCheckingSupport.isAssignableTo(argumentType, parameterType.getComponentType()))) {
            return false;
        }
        if (parameterType.isUsingGenerics() && argumentType.isUsingGenerics()) {
            GenericsType gt = GenericsUtils.buildWildcardType(parameterType);
            if (!gt.isCompatibleWith(argumentType)) {
                boolean samCoercion;
                boolean bl = samCoercion = ClassHelper.isSAMType(parameterType) && argumentType.equals(ClassHelper.CLOSURE_TYPE);
                if (!samCoercion) {
                    return false;
                }
            }
        } else {
            if (parameterType.isArray() && argumentType.isArray()) {
                return StaticTypeCheckingSupport.typeCheckMethodArgumentWithGenerics(parameterType.getComponentType(), argumentType.getComponentType(), lastArg);
            }
            if (lastArg && parameterType.isArray()) {
                return StaticTypeCheckingSupport.typeCheckMethodArgumentWithGenerics(parameterType.getComponentType(), argumentType, lastArg);
            }
        }
        return true;
    }

    protected static boolean typeCheckMethodsWithGenerics(ClassNode receiver, ClassNode[] argumentTypes, MethodNode candidateMethod) {
        if (candidateMethod instanceof ExtensionMethodNode) {
            ClassNode[] realTypes = new ClassNode[argumentTypes.length + 1];
            realTypes[0] = receiver;
            System.arraycopy(argumentTypes, 0, realTypes, 1, argumentTypes.length);
            MethodNode realMethod = ((ExtensionMethodNode)candidateMethod).getExtensionMethodNode();
            return StaticTypeCheckingSupport.typeCheckMethodsWithGenerics(realMethod.getDeclaringClass(), realTypes, realMethod, true);
        }
        if (receiver.isUsingGenerics() && ClassHelper.isClassType(receiver) && !ClassHelper.isClassType(candidateMethod.getDeclaringClass())) {
            return StaticTypeCheckingSupport.typeCheckMethodsWithGenerics(receiver.getGenericsTypes()[0].getType(), argumentTypes, candidateMethod);
        }
        return StaticTypeCheckingSupport.typeCheckMethodsWithGenerics(receiver, argumentTypes, candidateMethod, false);
    }

    private static boolean typeCheckMethodsWithGenerics(ClassNode receiver, ClassNode[] argumentTypes, MethodNode candidateMethod, boolean isExtensionMethod) {
        boolean skipBecauseOfInnerClassNotReceiver;
        Parameter[] parameters = candidateMethod.getParameters();
        if (parameters.length == 0 || parameters.length > argumentTypes.length) {
            return true;
        }
        boolean failure = false;
        Set<GenericsType.GenericsTypeName> fixedPlaceHolders = Collections.emptySet();
        Map<Object, Object> candidateGenerics = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
        boolean bl = skipBecauseOfInnerClassNotReceiver = !StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(receiver, candidateMethod.getDeclaringClass());
        if (!skipBecauseOfInnerClassNotReceiver) {
            if (candidateMethod instanceof ConstructorNode) {
                candidateGenerics = GenericsUtils.extractPlaceholders(receiver);
                fixedPlaceHolders = new HashSet<Object>(candidateGenerics.keySet());
            } else {
                failure = StaticTypeCheckingSupport.inferenceCheck(fixedPlaceHolders, candidateGenerics, candidateMethod.getDeclaringClass(), receiver, false);
                GenericsType[] gts = candidateMethod.getGenericsTypes();
                if (candidateMethod.isStatic()) {
                    candidateGenerics.clear();
                } else if (gts != null) {
                    for (GenericsType gt : gts) {
                        candidateGenerics.remove(new GenericsType.GenericsTypeName(gt.getName()));
                    }
                    gts = StaticTypeCheckingSupport.applyGenericsContext(candidateGenerics, gts);
                }
                GenericsUtils.extractPlaceholders(GenericsUtils.makeClassSafe0(ClassHelper.OBJECT_TYPE, gts), candidateGenerics);
                fixedPlaceHolders = StaticTypeCheckingSupport.extractResolvedPlaceHolders(candidateGenerics);
            }
        }
        int lastParamIndex = parameters.length - 1;
        int n = argumentTypes.length;
        for (int i = 0; i < n; ++i) {
            ClassNode parameterType = parameters[Math.min(i, lastParamIndex)].getOriginType();
            ClassNode argumentType = StaticTypeCheckingVisitor.wrapTypeIfNecessary(argumentTypes[i]);
            failure |= StaticTypeCheckingSupport.inferenceCheck(fixedPlaceHolders, candidateGenerics, parameterType, argumentType, i >= lastParamIndex);
            if (i != 0 || !isExtensionMethod) continue;
            fixedPlaceHolders = StaticTypeCheckingSupport.extractResolvedPlaceHolders(candidateGenerics);
        }
        return !failure;
    }

    private static Set<GenericsType.GenericsTypeName> extractResolvedPlaceHolders(Map<GenericsType.GenericsTypeName, GenericsType> resolvedMethodGenerics) {
        if (resolvedMethodGenerics.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<GenericsType.GenericsTypeName> result = new HashSet<GenericsType.GenericsTypeName>();
        for (Map.Entry<GenericsType.GenericsTypeName, GenericsType> entry : resolvedMethodGenerics.entrySet()) {
            GenericsType value = entry.getValue();
            if (value.isPlaceholder()) continue;
            result.add(entry.getKey());
        }
        return result;
    }

    private static boolean inferenceCheck(Set<GenericsType.GenericsTypeName> fixedPlaceHolders, Map<GenericsType.GenericsTypeName, GenericsType> resolvedMethodGenerics, ClassNode type, ClassNode wrappedArgument, boolean lastArg) {
        if (lastArg && type.isArray() && type.getComponentType().isGenericsPlaceHolder() && !wrappedArgument.isArray() && wrappedArgument.isGenericsPlaceHolder()) {
            type = type.getComponentType();
        }
        LinkedHashMap<GenericsType.GenericsTypeName, GenericsType> connections = new LinkedHashMap<GenericsType.GenericsTypeName, GenericsType>();
        StaticTypeCheckingSupport.extractGenericsConnections(connections, wrappedArgument, type);
        for (Map.Entry entry : connections.entrySet()) {
            GenericsType candidate = (GenericsType)entry.getValue();
            GenericsType resolved = resolvedMethodGenerics.get(entry.getKey());
            if (resolved == null || candidate.isPlaceholder() && !StaticTypeCheckingSupport.hasNonTrivialBounds(candidate) || StaticTypeCheckingSupport.compatibleConnection(resolved, candidate)) continue;
            if (!(resolved.isPlaceholder() || resolved.isWildcard() || fixedPlaceHolders.contains(entry.getKey()))) {
                if (StaticTypeCheckingSupport.compatibleConnection(candidate, resolved)) {
                    resolvedMethodGenerics.put((GenericsType.GenericsTypeName)entry.getKey(), candidate);
                    continue;
                }
                if (!candidate.isPlaceholder() && !candidate.isWildcard()) {
                    ClassNode lub = WideningCategories.lowestUpperBound(candidate.getType(), resolved.getType());
                    resolvedMethodGenerics.put((GenericsType.GenericsTypeName)entry.getKey(), lub.asGenericsType());
                    continue;
                }
            }
            return true;
        }
        connections.keySet().removeAll(fixedPlaceHolders);
        StaticTypeCheckingSupport.applyGenericsConnections(connections, resolvedMethodGenerics);
        StaticTypeCheckingSupport.addMissingEntries(connections, resolvedMethodGenerics);
        ClassNode resolvedType = StaticTypeCheckingSupport.applyGenericsContext(resolvedMethodGenerics, type);
        return !StaticTypeCheckingSupport.typeCheckMethodArgumentWithGenerics(resolvedType, wrappedArgument, lastArg);
    }

    private static boolean compatibleConnection(GenericsType resolved, GenericsType connection) {
        GenericsType gt;
        ClassNode resolvedType;
        if (resolved.isPlaceholder() && resolved.getUpperBounds() != null && resolved.getUpperBounds().length == 1 && !resolved.getUpperBounds()[0].isGenericsPlaceHolder() && resolved.getUpperBounds()[0].getName().equals("java.lang.Object")) {
            return true;
        }
        if (StaticTypeCheckingSupport.hasNonTrivialBounds(resolved)) {
            resolvedType = StaticTypeCheckingSupport.getCombinedBoundType(resolved);
            resolvedType = resolvedType.redirect().getPlainNodeReference();
        } else if (!resolved.isPlaceholder()) {
            resolvedType = resolved.getType().getPlainNodeReference();
        } else {
            return true;
        }
        if (connection.isWildcard()) {
            gt = connection;
        } else {
            ClassNode lowerBound = connection.getType().getPlainNodeReference();
            if (StaticTypeCheckingSupport.hasNonTrivialBounds(connection)) {
                lowerBound.setGenericsTypes(new GenericsType[]{connection});
            }
            gt = new GenericsType(ClassHelper.makeWithoutCaching("?"), null, lowerBound);
            gt.setWildcard(true);
        }
        return gt.isCompatibleWith(resolvedType);
    }

    private static void addMissingEntries(Map<GenericsType.GenericsTypeName, GenericsType> connections, Map<GenericsType.GenericsTypeName, GenericsType> resolved) {
        for (Map.Entry<GenericsType.GenericsTypeName, GenericsType> entry : connections.entrySet()) {
            GenericsType gt;
            ClassNode cn;
            if (resolved.containsKey(entry.getKey()) || (cn = (gt = entry.getValue()).getType()).redirect() == UNKNOWN_PARAMETER_TYPE) continue;
            resolved.put(entry.getKey(), gt);
        }
    }

    public static ClassNode resolveClassNodeGenerics(Map<GenericsType.GenericsTypeName, GenericsType> resolvedPlaceholders, Map<GenericsType.GenericsTypeName, GenericsType> placeholdersFromContext, ClassNode currentType) {
        ClassNode type = currentType;
        type = StaticTypeCheckingSupport.applyGenericsContext(resolvedPlaceholders, type);
        type = StaticTypeCheckingSupport.applyGenericsContext(placeholdersFromContext, type);
        return type;
    }

    static void applyGenericsConnections(Map<GenericsType.GenericsTypeName, GenericsType> connections, Map<GenericsType.GenericsTypeName, GenericsType> resolvedPlaceholders) {
        if (connections == null || connections.isEmpty()) {
            return;
        }
        for (Map.Entry<GenericsType.GenericsTypeName, GenericsType> entry : resolvedPlaceholders.entrySet()) {
            ClassNode replacementType;
            ClassNode suitabilityType;
            GenericsType.GenericsTypeName name;
            GenericsType newValue;
            GenericsType oldValue = entry.getValue();
            if (!oldValue.isPlaceholder() || (newValue = connections.get(name = new GenericsType.GenericsTypeName(oldValue.getName()))) == oldValue) continue;
            if (newValue == null && (newValue = connections.get(entry.getKey())) != null) {
                newValue = StaticTypeCheckingSupport.getCombinedGenericsType(oldValue, newValue);
            }
            if (newValue == null) {
                newValue = StaticTypeCheckingSupport.applyGenericsContext(connections, oldValue);
                entry.setValue(newValue);
                continue;
            }
            if (newValue.isPlaceholder() && newValue == resolvedPlaceholders.get(name) || !oldValue.isCompatibleWith(suitabilityType = !(replacementType = StaticTypeCheckingSupport.extractType(newValue)).isGenericsPlaceHolder() ? replacementType : Optional.ofNullable(replacementType.getGenericsTypes()).map(gts -> StaticTypeCheckingSupport.extractType(gts[0])).orElse(replacementType.redirect()))) continue;
            if (newValue.isWildcard() && newValue.getLowerBound() == null && newValue.getUpperBounds() == null) {
                entry.setValue(replacementType.asGenericsType());
                continue;
            }
            entry.setValue(newValue);
        }
    }

    private static ClassNode extractType(GenericsType gt) {
        ClassNode cn;
        if (!gt.isPlaceholder()) {
            cn = StaticTypeCheckingSupport.getCombinedBoundType(gt);
        } else {
            cn = gt.getType().redirect();
            if (gt.getType().getGenericsTypes() != null) {
                gt = gt.getType().getGenericsTypes()[0];
            }
            if (gt.getLowerBound() != null) {
                cn = gt.getLowerBound();
            } else if (DefaultGroovyMethods.asBoolean(gt.getUpperBounds())) {
                cn = gt.getUpperBounds()[0];
            }
        }
        return cn;
    }

    private static boolean equalIncludingGenerics(GenericsType one, GenericsType two) {
        ClassNode[] upper2;
        ClassNode lower2;
        if (one == two) {
            return true;
        }
        if (one.isWildcard() != two.isWildcard()) {
            return false;
        }
        if (one.isPlaceholder() != two.isPlaceholder()) {
            return false;
        }
        if (!StaticTypeCheckingSupport.equalIncludingGenerics(one.getType(), two.getType())) {
            return false;
        }
        ClassNode lower1 = one.getLowerBound();
        if (lower1 == null ^ (lower2 = two.getLowerBound()) == null) {
            return false;
        }
        if (lower1 != lower2 && !StaticTypeCheckingSupport.equalIncludingGenerics(lower1, lower2)) {
            return false;
        }
        ClassNode[] upper1 = one.getUpperBounds();
        if (upper1 == null ^ (upper2 = two.getUpperBounds()) == null) {
            return false;
        }
        if (upper1 != upper2) {
            if (upper1.length != upper2.length) {
                return false;
            }
            int n = upper1.length;
            for (int i = 0; i < n; ++i) {
                if (StaticTypeCheckingSupport.equalIncludingGenerics(upper1[i], upper2[i])) continue;
                return false;
            }
        }
        return true;
    }

    private static boolean equalIncludingGenerics(ClassNode one, ClassNode two) {
        GenericsType[] gt2;
        if (one == two) {
            return true;
        }
        if (one.isGenericsPlaceHolder() != two.isGenericsPlaceHolder()) {
            return false;
        }
        if (!one.equals(two)) {
            return false;
        }
        GenericsType[] gt1 = one.getGenericsTypes();
        if (gt1 == null ^ (gt2 = two.getGenericsTypes()) == null) {
            return false;
        }
        if (gt1 != gt2) {
            if (gt1.length != gt2.length) {
                return false;
            }
            int n = gt1.length;
            for (int i = 0; i < n; ++i) {
                if (StaticTypeCheckingSupport.equalIncludingGenerics(gt1[i], gt2[i])) continue;
                return false;
            }
        }
        return true;
    }

    static void extractGenericsConnections(Map<GenericsType.GenericsTypeName, GenericsType> connections, ClassNode type, ClassNode target) {
        if (target == null || target == type || !StaticTypeCheckingSupport.isUsingGenericsOrIsArrayUsingGenerics(target)) {
            return;
        }
        if (type == null || type == UNKNOWN_PARAMETER_TYPE) {
            return;
        }
        if (target.isGenericsPlaceHolder()) {
            StaticTypeCheckingSupport.storeGenericsConnection(connections, target.getUnresolvedName(), new GenericsType(type));
        } else if (type.isGenericsPlaceHolder()) {
            StaticTypeCheckingSupport.extractGenericsConnections(connections, StaticTypeCheckingSupport.extractType(new GenericsType(type)), target);
        } else if (type.isArray() && target.isArray()) {
            StaticTypeCheckingSupport.extractGenericsConnections(connections, type.getComponentType(), target.getComponentType());
        } else if (type.equals(ClassHelper.CLOSURE_TYPE) && ClassHelper.isSAMType(target)) {
            ClassNode returnType = StaticTypeCheckingVisitor.wrapTypeIfNecessary(GenericsUtils.parameterizeSAM(target).getV2());
            StaticTypeCheckingSupport.extractGenericsConnections(connections, type.getGenericsTypes(), new GenericsType[]{returnType.asGenericsType()});
        } else if (type.equals(target)) {
            StaticTypeCheckingSupport.extractGenericsConnections(connections, type.getGenericsTypes(), target.getGenericsTypes());
        } else if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(type, target)) {
            ClassNode superClass = ClassHelper.getNextSuperClass(type, target);
            if (superClass != null) {
                Map<String, ClassNode> spec;
                if (GenericsUtils.hasUnresolvedGenerics(superClass) && !(spec = GenericsUtils.createGenericsSpec(type)).isEmpty()) {
                    superClass = GenericsUtils.correctToGenericsSpecRecurse(spec, superClass);
                }
                StaticTypeCheckingSupport.extractGenericsConnections(connections, superClass, target);
            } else {
                throw new GroovyBugError("The type " + type + " seems not to normally extend " + target + ". Sorry, I cannot handle this.");
            }
        }
    }

    private static void extractGenericsConnections(Map<GenericsType.GenericsTypeName, GenericsType> connections, GenericsType[] usage, GenericsType[] declaration) {
        if (usage == null || declaration == null || declaration.length == 0) {
            return;
        }
        if (usage.length != declaration.length) {
            return;
        }
        int n = usage.length;
        for (int i = 0; i < n; ++i) {
            GenericsType ui = usage[i];
            GenericsType di = declaration[i];
            if (di.isPlaceholder()) {
                StaticTypeCheckingSupport.storeGenericsConnection(connections, di.getName(), ui);
                continue;
            }
            if (di.isWildcard()) {
                ClassNode boundType;
                ClassNode lowerBound = di.getLowerBound();
                ClassNode[] upperBounds = di.getUpperBounds();
                if (ui.isWildcard()) {
                    StaticTypeCheckingSupport.extractGenericsConnections(connections, ui.getLowerBound(), lowerBound);
                    StaticTypeCheckingSupport.extractGenericsConnections(connections, ui.getUpperBounds(), upperBounds);
                    continue;
                }
                if (StaticTypeCheckingSupport.isUnboundedWildcard(di)) continue;
                ClassNode classNode = boundType = lowerBound != null ? lowerBound : upperBounds[0];
                if (boundType.isGenericsPlaceHolder()) {
                    String placeholderName = boundType.getUnresolvedName();
                    ui = new GenericsType(ui.getType());
                    ui.setWildcard(true);
                    StaticTypeCheckingSupport.storeGenericsConnection(connections, placeholderName, ui);
                    continue;
                }
                StaticTypeCheckingSupport.extractGenericsConnections(connections, ui.getType(), boundType);
                continue;
            }
            StaticTypeCheckingSupport.extractGenericsConnections(connections, ui.getType(), di.getType());
        }
    }

    private static void extractGenericsConnections(Map<GenericsType.GenericsTypeName, GenericsType> connections, ClassNode[] usage, ClassNode[] declaration) {
        if (usage == null || declaration == null || declaration.length == 0) {
            return;
        }
        int n = usage.length;
        for (int i = 0; i < n; ++i) {
            ClassNode ui = usage[i];
            ClassNode di = declaration[i];
            if (di.isGenericsPlaceHolder()) {
                StaticTypeCheckingSupport.storeGenericsConnection(connections, di.getUnresolvedName(), new GenericsType(ui));
                continue;
            }
            if (!di.isUsingGenerics()) continue;
            StaticTypeCheckingSupport.extractGenericsConnections(connections, ui.getGenericsTypes(), di.getGenericsTypes());
        }
    }

    private static void storeGenericsConnection(Map<GenericsType.GenericsTypeName, GenericsType> connections, String placeholderName, GenericsType gt) {
        connections.put(new GenericsType.GenericsTypeName(placeholderName), gt);
    }

    static GenericsType[] getGenericsWithoutArray(ClassNode type) {
        if (type.isArray()) {
            return StaticTypeCheckingSupport.getGenericsWithoutArray(type.getComponentType());
        }
        return type.getGenericsTypes();
    }

    static GenericsType[] applyGenericsContext(Map<GenericsType.GenericsTypeName, GenericsType> spec, GenericsType[] gts) {
        if (gts == null || spec == null || spec.isEmpty()) {
            return gts;
        }
        int n = gts.length;
        if (n == 0) {
            return gts;
        }
        GenericsType[] newGTs = new GenericsType[n];
        for (int i = 0; i < n; ++i) {
            newGTs[i] = StaticTypeCheckingSupport.applyGenericsContext(spec, gts[i]);
        }
        return newGTs;
    }

    private static GenericsType applyGenericsContext(Map<GenericsType.GenericsTypeName, GenericsType> spec, GenericsType gt) {
        ClassNode newType;
        if (gt.isPlaceholder()) {
            GenericsType.GenericsTypeName name = new GenericsType.GenericsTypeName(gt.getName());
            GenericsType specType = spec.get(name);
            if (specType != null) {
                return specType;
            }
            if (StaticTypeCheckingSupport.hasNonTrivialBounds(gt)) {
                GenericsType newGT = new GenericsType(gt.getType(), StaticTypeCheckingSupport.applyGenericsContext(spec, gt.getUpperBounds()), StaticTypeCheckingSupport.applyGenericsContext(spec, gt.getLowerBound()));
                newGT.setPlaceholder(true);
                return newGT;
            }
            return gt;
        }
        if (gt.isWildcard()) {
            GenericsType newGT = new GenericsType(gt.getType(), StaticTypeCheckingSupport.applyGenericsContext(spec, gt.getUpperBounds()), StaticTypeCheckingSupport.applyGenericsContext(spec, gt.getLowerBound()));
            newGT.setWildcard(true);
            return newGT;
        }
        ClassNode type = gt.getType();
        if (type.isArray()) {
            newType = StaticTypeCheckingSupport.applyGenericsContext(spec, type.getComponentType()).makeArray();
        } else {
            if (type.getGenericsTypes() == null) {
                return gt;
            }
            newType = type.getPlainNodeReference();
            newType.setGenericsPlaceHolder(type.isGenericsPlaceHolder());
            newType.setGenericsTypes(StaticTypeCheckingSupport.applyGenericsContext(spec, type.getGenericsTypes()));
        }
        return new GenericsType(newType);
    }

    private static boolean hasNonTrivialBounds(GenericsType gt) {
        if (gt.isWildcard()) {
            return true;
        }
        if (gt.getLowerBound() != null) {
            return true;
        }
        ClassNode[] upperBounds = gt.getUpperBounds();
        if (upperBounds != null) {
            return upperBounds.length != 1 || upperBounds[0].isGenericsPlaceHolder() || !ClassHelper.isObjectType(upperBounds[0]);
        }
        return false;
    }

    static ClassNode[] applyGenericsContext(Map<GenericsType.GenericsTypeName, GenericsType> spec, ClassNode[] types) {
        if (types == null) {
            return null;
        }
        int nTypes = types.length;
        ClassNode[] newTypes = new ClassNode[nTypes];
        for (int i = 0; i < nTypes; ++i) {
            newTypes[i] = StaticTypeCheckingSupport.applyGenericsContext(spec, types[i]);
        }
        return newTypes;
    }

    static ClassNode applyGenericsContext(Map<GenericsType.GenericsTypeName, GenericsType> spec, ClassNode type) {
        if (type == null || !StaticTypeCheckingSupport.isUsingGenericsOrIsArrayUsingGenerics(type)) {
            return type;
        }
        if (type.isArray()) {
            return StaticTypeCheckingSupport.applyGenericsContext(spec, type.getComponentType()).makeArray();
        }
        GenericsType[] gt = type.getGenericsTypes();
        if (DefaultGroovyMethods.asBoolean(spec)) {
            gt = StaticTypeCheckingSupport.applyGenericsContext(spec, gt);
        }
        if (!type.isGenericsPlaceHolder()) {
            ClassNode cn = type.getPlainNodeReference();
            cn.setGenericsTypes(gt);
            return cn;
        }
        if (!gt[0].isPlaceholder()) {
            return StaticTypeCheckingSupport.getCombinedBoundType(gt[0]);
        }
        if (type.getGenericsTypes()[0] != gt[0]) {
            ClassNode cn = ClassHelper.make(gt[0].getName());
            cn.setRedirect(gt[0].getType());
            cn.setGenericsPlaceHolder(true);
            cn.setGenericsTypes(gt);
            return cn;
        }
        return type;
    }

    static ClassNode getCombinedBoundType(GenericsType genericsType) {
        if (StaticTypeCheckingSupport.hasNonTrivialBounds(genericsType)) {
            if (genericsType.getLowerBound() != null) {
                return ClassHelper.OBJECT_TYPE;
            }
            if (genericsType.getUpperBounds() != null) {
                return genericsType.getUpperBounds()[0];
            }
        }
        return genericsType.getType();
    }

    static GenericsType getCombinedGenericsType(GenericsType gt1, GenericsType gt2) {
        if (StaticTypeCheckingSupport.isUnboundedWildcard(gt1)) {
            gt1 = gt1.getType().asGenericsType();
        }
        if (StaticTypeCheckingSupport.isUnboundedWildcard(gt2)) {
            gt2 = gt2.getType().asGenericsType();
        }
        ClassNode cn1 = GenericsUtils.makeClassSafe0(ClassHelper.CLASS_Type, gt1);
        ClassNode cn2 = GenericsUtils.makeClassSafe0(ClassHelper.CLASS_Type, gt2);
        ClassNode lub = WideningCategories.lowestUpperBound(cn1, cn2);
        return lub.getGenericsTypes()[0];
    }

    static ClassNode boundUnboundedWildcards(ClassNode type) {
        if (type.isArray()) {
            return StaticTypeCheckingSupport.boundUnboundedWildcards(type.getComponentType()).makeArray();
        }
        ClassNode redirect = type.redirect();
        if (redirect == null || redirect == type || !StaticTypeCheckingSupport.isUsingGenericsOrIsArrayUsingGenerics(redirect)) {
            return type;
        }
        ClassNode newType = type.getPlainNodeReference();
        newType.setGenericsPlaceHolder(type.isGenericsPlaceHolder());
        newType.setGenericsTypes(StaticTypeCheckingSupport.boundUnboundedWildcards(type.getGenericsTypes(), redirect.getGenericsTypes()));
        return newType;
    }

    private static GenericsType[] boundUnboundedWildcards(GenericsType[] actual, GenericsType[] declared) {
        int n = actual.length;
        GenericsType[] newTypes = new GenericsType[n];
        for (int i = 0; i < n; ++i) {
            newTypes[i] = StaticTypeCheckingSupport.boundUnboundedWildcard(actual[i], declared[i]);
        }
        return newTypes;
    }

    private static GenericsType boundUnboundedWildcard(GenericsType actual, GenericsType declared) {
        if (!StaticTypeCheckingSupport.isUnboundedWildcard(actual)) {
            return actual;
        }
        ClassNode lowerBound = declared.getLowerBound();
        ClassNode[] upperBounds = declared.getUpperBounds();
        if (lowerBound != null) {
            assert (upperBounds == null);
        } else if (upperBounds == null) {
            upperBounds = new ClassNode[]{ClassHelper.OBJECT_TYPE};
        } else if (declared.isPlaceholder()) {
            upperBounds = (ClassNode[])upperBounds.clone();
            int n = upperBounds.length;
            for (int i = 0; i < n; ++i) {
                if (!GenericsUtils.extractPlaceholders(upperBounds[i]).containsKey(new GenericsType.GenericsTypeName(declared.getName()))) continue;
                upperBounds[i] = upperBounds[i].getPlainNodeReference();
            }
        }
        GenericsType newType = new GenericsType(ClassHelper.makeWithoutCaching("?"), upperBounds, lowerBound);
        newType.setWildcard(true);
        return newType;
    }

    public static boolean isUnboundedWildcard(GenericsType gt) {
        if (gt.isWildcard() && gt.getLowerBound() == null) {
            ClassNode[] upperBounds = gt.getUpperBounds();
            return upperBounds == null || upperBounds.length == 0 || upperBounds.length == 1 && ClassHelper.isObjectType(upperBounds[0]) && !upperBounds[0].isGenericsPlaceHolder();
        }
        return false;
    }

    static Map<GenericsType.GenericsTypeName, GenericsType> extractGenericsParameterMapOfThis(TypeCheckingContext context) {
        ClassNode cn = context.getEnclosingClassNode();
        MethodNode mn = context.getEnclosingMethod();
        if (cn != null && cn.getEnclosingMethod() == mn) {
            return StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(cn);
        }
        return StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(mn);
    }

    private static Map<GenericsType.GenericsTypeName, GenericsType> extractGenericsParameterMapOfThis(MethodNode mn) {
        GenericsType[] gts;
        if (mn == null) {
            return null;
        }
        Map<GenericsType.GenericsTypeName, GenericsType> map = null;
        if (!mn.isStatic()) {
            map = StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(mn.getDeclaringClass());
        }
        if ((gts = mn.getGenericsTypes()) != null) {
            if (map == null) {
                map = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
            }
            for (GenericsType gt : gts) {
                assert (gt.isPlaceholder());
                map.put(new GenericsType.GenericsTypeName(gt.getName()), gt);
            }
        }
        return map;
    }

    private static Map<GenericsType.GenericsTypeName, GenericsType> extractGenericsParameterMapOfThis(ClassNode cn) {
        GenericsType[] gts;
        if (cn == null) {
            return null;
        }
        Map<GenericsType.GenericsTypeName, GenericsType> map = null;
        if ((cn.getModifiers() & 8) == 0) {
            if (cn.getEnclosingMethod() != null) {
                map = StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(cn.getEnclosingMethod());
            } else if (cn.getOuterClass() != null) {
                map = StaticTypeCheckingSupport.extractGenericsParameterMapOfThis(cn.getOuterClass());
            }
        }
        if (!(cn instanceof InnerClassNode && ((InnerClassNode)cn).isAnonymous() || (gts = cn.getGenericsTypes()) == null)) {
            if (map == null) {
                map = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
            }
            for (GenericsType gt : gts) {
                assert (gt.isPlaceholder());
                map.put(new GenericsType.GenericsTypeName(gt.getName()), gt);
            }
        }
        return map;
    }

    public static List<MethodNode> filterMethodsByVisibility(List<MethodNode> methodNodeList, ClassNode enclosingClassNode) {
        if (!DefaultGroovyMethods.asBoolean(methodNodeList)) {
            return StaticTypeCheckingVisitor.EMPTY_METHODNODE_LIST;
        }
        LinkedList<MethodNode> result = new LinkedList<MethodNode>();
        boolean isEnclosingInnerClass = enclosingClassNode instanceof InnerClassNode;
        List<ClassNode> outerClasses = enclosingClassNode.getOuterClasses();
        block0: for (MethodNode methodNode : methodNodeList) {
            if (methodNode instanceof ExtensionMethodNode) {
                result.add(methodNode);
                continue;
            }
            ClassNode declaringClass = methodNode.getDeclaringClass();
            if (isEnclosingInnerClass) {
                for (ClassNode outerClass : outerClasses) {
                    if (!outerClass.isDerivedFrom(declaringClass)) continue;
                    if (outerClass.equals(declaringClass)) {
                        result.add(methodNode);
                        continue block0;
                    }
                    if (!methodNode.isPublic() && !methodNode.isProtected()) continue;
                    result.add(methodNode);
                    continue block0;
                }
            }
            if (declaringClass instanceof InnerClassNode && declaringClass.getOuterClasses().contains(enclosingClassNode)) {
                result.add(methodNode);
                continue;
            }
            if (methodNode.isPrivate() && !enclosingClassNode.equals(declaringClass) || methodNode.isProtected() && !enclosingClassNode.isDerivedFrom(declaringClass) && !ClassNodeUtils.samePackageName(enclosingClassNode, declaringClass) || methodNode.isPackageScope() && !ClassNodeUtils.samePackageName(enclosingClassNode, declaringClass)) continue;
            result.add(methodNode);
        }
        return result;
    }

    public static boolean isGStringOrGStringStringLUB(ClassNode node) {
        return ClassHelper.isGStringType(node) || GSTRING_STRING_CLASSNODE.equals(node);
    }

    public static boolean isParameterizedWithGStringOrGStringString(ClassNode node) {
        GenericsType[] genericsTypes;
        if (node.isArray()) {
            return StaticTypeCheckingSupport.isParameterizedWithGStringOrGStringString(node.getComponentType());
        }
        if (node.isUsingGenerics() && (genericsTypes = node.getGenericsTypes()) != null) {
            for (GenericsType genericsType : genericsTypes) {
                if (!StaticTypeCheckingSupport.isGStringOrGStringStringLUB(genericsType.getType())) continue;
                return true;
            }
        }
        return node.getSuperClass() != null && StaticTypeCheckingSupport.isParameterizedWithGStringOrGStringString(node.getUnresolvedSuperClass());
    }

    public static boolean isParameterizedWithString(ClassNode node) {
        GenericsType[] genericsTypes;
        if (node.isArray()) {
            return StaticTypeCheckingSupport.isParameterizedWithString(node.getComponentType());
        }
        if (node.isUsingGenerics() && (genericsTypes = node.getGenericsTypes()) != null) {
            for (GenericsType genericsType : genericsTypes) {
                if (!ClassHelper.STRING_TYPE.equals(genericsType.getType())) continue;
                return true;
            }
        }
        return node.getSuperClass() != null && StaticTypeCheckingSupport.isParameterizedWithString(node.getUnresolvedSuperClass());
    }

    public static boolean missesGenericsTypes(ClassNode cn) {
        while (cn.isArray()) {
            cn = cn.getComponentType();
        }
        GenericsType[] cnGenerics = cn.getGenericsTypes();
        GenericsType[] rnGenerics = cn.redirect().getGenericsTypes();
        return cnGenerics == null || cnGenerics.length == 0 ? rnGenerics != null : GenericsUtils.hasUnresolvedGenerics(cn);
    }

    public static Object evaluateExpression(Expression expr, CompilerConfiguration config) {
        Expression ce;
        Expression expression = ce = expr instanceof CastExpression ? ((CastExpression)expr).getExpression() : expr;
        if (ce instanceof ConstantExpression) {
            if (expr.getType().equals(ce.getType())) {
                return ((ConstantExpression)ce).getValue();
            }
        } else if (ce instanceof ListExpression && expr.getType().isArray() && expr.getType().getComponentType().equals(ClassHelper.STRING_TYPE)) {
            return ((ListExpression)ce).getExpressions().stream().map(e -> StaticTypeCheckingSupport.evaluateExpression(e, config)).toArray(String[]::new);
        }
        String className = "Expression$" + UUID.randomUUID().toString().replace('-', '$');
        ClassNode simpleClass = new ClassNode(className, 1, ClassHelper.OBJECT_TYPE);
        ClassNodeUtils.addGeneratedMethod(simpleClass, "eval", 9, ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, new ReturnStatement(expr));
        CompilerConfiguration cc = new CompilerConfiguration(config);
        cc.setPreviewFeatures(false);
        cc.setTargetBytecode(CompilerConfiguration.DEFAULT.getTargetBytecode());
        CompilationUnit cu = new CompilationUnit(cc);
        try {
            cu.addClassNode(simpleClass);
            cu.compile(7);
            List<GroovyClass> classes = cu.getClasses();
            Class aClass = cu.getClassLoader().defineClass(className, classes.get(0).getBytes());
            try {
                Object object = aClass.getMethod("eval", new Class[0]).invoke(null, new Object[0]);
                return object;
            }
            catch (ReflectiveOperationException e2) {
                throw new GroovyBugError(e2);
            }
        }
        finally {
            DefaultGroovyMethodsSupport.closeQuietly(cu.getClassLoader());
        }
    }

    @Deprecated
    public static Set<ClassNode> collectAllInterfaces(ClassNode node) {
        return GeneralUtils.getInterfacesAndSuperInterfaces(node);
    }

    @Deprecated
    public static ClassNode getCorrectedClassNode(ClassNode cn, ClassNode sc, boolean completed) {
        if (completed && GenericsUtils.hasUnresolvedGenerics(cn)) {
            return sc.getPlainNodeReference();
        }
        return GenericsUtils.correctToGenericsSpecRecurse(GenericsUtils.createGenericsSpec(cn), sc);
    }

    public static boolean isClassClassNodeWrappingConcreteType(ClassNode classNode) {
        GenericsType[] genericsTypes = classNode.getGenericsTypes();
        return ClassHelper.isClassType(classNode) && classNode.isUsingGenerics() && genericsTypes != null && !genericsTypes[0].isPlaceholder() && !genericsTypes[0].isWildcard();
    }

    public static List<MethodNode> findSetters(ClassNode cn, String setterName, boolean voidOnly) {
        ArrayList<MethodNode> result = new ArrayList<MethodNode>();
        if (!cn.isInterface()) {
            for (MethodNode method : cn.getMethods(setterName)) {
                if (!StaticTypeCheckingSupport.isSetter(method, voidOnly)) continue;
                result.add(method);
            }
        }
        for (ClassNode in : cn.getAllInterfaces()) {
            for (MethodNode method : in.getDeclaredMethods(setterName)) {
                if (!StaticTypeCheckingSupport.isSetter(method, voidOnly)) continue;
                result.add(method);
            }
        }
        return result;
    }

    private static boolean isSetter(MethodNode mn, boolean voidOnly) {
        return (!voidOnly || mn.isVoidMethod()) && mn.getParameters().length == 1;
    }

    public static ClassNode isTraitSelf(VariableExpression vexp) {
        if ("$self".equals(vexp.getName())) {
            ClassNode type;
            Variable accessedVariable = vexp.getAccessedVariable();
            ClassNode classNode = type = accessedVariable != null ? accessedVariable.getType() : null;
            if (accessedVariable instanceof Parameter && Traits.isTrait(type)) {
                return type;
            }
        }
        return null;
    }

    public static class DoubleArrayStaticTypesHelper {
        public static Double getAt(double[] array, int index) {
            return array != null ? Double.valueOf(array[index]) : null;
        }

        public static void putAt(double[] array, int index, double value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class FloatArrayStaticTypesHelper {
        public static Float getAt(float[] array, int index) {
            return array != null ? Float.valueOf(array[index]) : null;
        }

        public static void putAt(float[] array, int index, float value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class LongArrayStaticTypesHelper {
        public static Long getAt(long[] array, int index) {
            return array != null ? Long.valueOf(array[index]) : null;
        }

        public static void putAt(long[] array, int index, long value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class IntArrayStaticTypesHelper {
        public static Integer getAt(int[] array, int index) {
            return array != null ? Integer.valueOf(array[index]) : null;
        }

        public static void putAt(int[] array, int index, int value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class ShortArrayStaticTypesHelper {
        public static Short getAt(short[] array, int index) {
            return array != null ? Short.valueOf(array[index]) : null;
        }

        public static void putAt(short[] array, int index, short value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class ByteArrayStaticTypesHelper {
        public static Byte getAt(byte[] array, int index) {
            return array != null ? Byte.valueOf(array[index]) : null;
        }

        public static void putAt(byte[] array, int index, byte value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class CharArrayStaticTypesHelper {
        public static Character getAt(char[] array, int index) {
            return array != null ? Character.valueOf(array[index]) : null;
        }

        public static void putAt(char[] array, int index, char value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class BooleanArrayStaticTypesHelper {
        public static Boolean getAt(boolean[] array, int index) {
            return array != null ? Boolean.valueOf(array[index]) : null;
        }

        public static void putAt(boolean[] array, int index, boolean value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }

    public static class ObjectArrayStaticTypesHelper {
        public static <T> T getAt(T[] array, int index) {
            return array != null ? (T)array[index] : null;
        }

        public static <T, U extends T> void putAt(T[] array, int index, U value) {
            if (array != null) {
                array[index] = value;
            }
        }
    }
}

