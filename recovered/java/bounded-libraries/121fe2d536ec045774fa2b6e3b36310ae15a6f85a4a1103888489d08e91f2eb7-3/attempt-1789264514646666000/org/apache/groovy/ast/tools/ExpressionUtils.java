/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.ast.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.typehandling.NumberMath;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;

public final class ExpressionUtils {
    private static final int[] HANDLED_TYPES = new int[]{200, 201, 202, 203, 206, 340, 341, 342, 280, 281, 282};

    public static boolean isNullConstant(Expression expression) {
        return expression instanceof ConstantExpression && ((ConstantExpression)expression).isNullExpression();
    }

    public static boolean isThisExpression(Expression expression) {
        return expression instanceof VariableExpression && ((VariableExpression)expression).isThisExpression();
    }

    public static boolean isSuperExpression(Expression expression) {
        return expression instanceof VariableExpression && ((VariableExpression)expression).isSuperExpression();
    }

    public static boolean isThisOrSuper(Expression expression) {
        return ExpressionUtils.isThisExpression(expression) || ExpressionUtils.isSuperExpression(expression);
    }

    public static boolean isTypeOrArrayOfType(ClassNode targetType, ClassNode type, boolean recurse) {
        if (targetType == null) {
            return false;
        }
        if (type.equals(targetType)) {
            return true;
        }
        return targetType.isArray() && recurse ? ExpressionUtils.isTypeOrArrayOfType(targetType.getComponentType(), type, recurse) : type.equals(targetType.getComponentType());
    }

    public static boolean isNumberOrArrayOfNumber(ClassNode targetType, boolean recurse) {
        if (targetType == null) {
            return false;
        }
        if (targetType.isDerivedFrom(ClassHelper.Number_TYPE)) {
            return true;
        }
        return targetType.isArray() && recurse ? ExpressionUtils.isNumberOrArrayOfNumber(targetType.getComponentType(), recurse) : targetType.isArray() && targetType.getComponentType().isDerivedFrom(ClassHelper.Number_TYPE);
    }

    public static ConstantExpression transformBinaryConstantExpression(BinaryExpression be, ClassNode targetType) {
        int type;
        ClassNode wrapperType = ClassHelper.getWrapper(targetType);
        if (ExpressionUtils.isTypeOrArrayOfType(targetType, ClassHelper.STRING_TYPE, false)) {
            if (be.getOperation().getType() == 200) {
                Expression left = ExpressionUtils.transformInlineConstants(be.getLeftExpression(), targetType);
                Expression right = ExpressionUtils.transformInlineConstants(be.getRightExpression(), targetType);
                if (left instanceof ConstantExpression && right instanceof ConstantExpression) {
                    Object leftV = ((ConstantExpression)left).getValue();
                    if (leftV == null) {
                        leftV = "null";
                    }
                    if (leftV instanceof String) {
                        return ExpressionUtils.configure(be, new ConstantExpression((String)leftV + ((ConstantExpression)right).getValue()));
                    }
                }
            }
        } else if (ExpressionUtils.isNumberOrArrayOfNumber(wrapperType, false) && Arrays.binarySearch(HANDLED_TYPES, type = be.getOperation().getType()) >= 0) {
            Expression rightX;
            Expression leftX = be.getLeftExpression();
            if (!(leftX instanceof ConstantExpression)) {
                leftX = ExpressionUtils.transformInlineConstants(leftX, targetType);
            }
            if (!((rightX = be.getRightExpression()) instanceof ConstantExpression)) {
                boolean isShift = type >= 280 && type <= 282;
                rightX = ExpressionUtils.transformInlineConstants(rightX, isShift ? ClassHelper.int_TYPE : targetType);
            }
            if (leftX instanceof ConstantExpression && rightX instanceof ConstantExpression) {
                Number left = ExpressionUtils.safeNumber((ConstantExpression)leftX);
                Number right = ExpressionUtils.safeNumber((ConstantExpression)rightX);
                if (left == null || right == null) {
                    return null;
                }
                Number result = null;
                switch (type) {
                    case 200: {
                        result = NumberMath.add(left, right);
                        break;
                    }
                    case 201: {
                        result = NumberMath.subtract(left, right);
                        break;
                    }
                    case 202: {
                        result = NumberMath.multiply(left, right);
                        break;
                    }
                    case 203: {
                        result = NumberMath.divide(left, right);
                        break;
                    }
                    case 280: {
                        result = NumberMath.leftShift(left, right);
                        break;
                    }
                    case 281: {
                        result = NumberMath.rightShift(left, right);
                        break;
                    }
                    case 282: {
                        result = NumberMath.rightShiftUnsigned(left, right);
                        break;
                    }
                    case 341: {
                        result = NumberMath.and(left, right);
                        break;
                    }
                    case 340: {
                        result = NumberMath.or(left, right);
                        break;
                    }
                    case 342: {
                        result = NumberMath.xor(left, right);
                        break;
                    }
                    case 206: {
                        result = DefaultGroovyMethods.power(left, right);
                    }
                }
                if (result != null) {
                    if (ClassHelper.isWrapperInteger(wrapperType)) {
                        return ExpressionUtils.configure(be, new ConstantExpression(result.intValue(), true));
                    }
                    if (ClassHelper.isWrapperByte(wrapperType)) {
                        return ExpressionUtils.configure(be, new ConstantExpression(result.byteValue(), true));
                    }
                    if (ClassHelper.isWrapperLong(wrapperType)) {
                        return ExpressionUtils.configure(be, new ConstantExpression(result.longValue(), true));
                    }
                    if (ClassHelper.isWrapperShort(wrapperType)) {
                        return ExpressionUtils.configure(be, new ConstantExpression(result.shortValue(), true));
                    }
                    if (ClassHelper.isWrapperFloat(wrapperType)) {
                        return ExpressionUtils.configure(be, new ConstantExpression(Float.valueOf(result.floatValue()), true));
                    }
                    if (ClassHelper.isWrapperDouble(wrapperType)) {
                        return ExpressionUtils.configure(be, new ConstantExpression(result.doubleValue(), true));
                    }
                    if (ClassHelper.isWrapperCharacter(wrapperType)) {
                        return ExpressionUtils.configure(be, new ConstantExpression(Character.valueOf((char)result.intValue()), true));
                    }
                    return ExpressionUtils.configure(be, new ConstantExpression(result, true));
                }
            }
        }
        return null;
    }

    public static Expression transformInlineConstants(Expression exp) {
        if (exp instanceof PropertyExpression) {
            ClassNode clazz;
            FieldNode field;
            PropertyExpression pe = (PropertyExpression)exp;
            if (pe.getObjectExpression() instanceof ClassExpression && (field = ClassNodeUtils.getField(clazz = pe.getObjectExpression().getType(), pe.getPropertyAsString())) != null && field.isStatic() && field.isFinal() && !field.isEnum() && field.getInitialValueExpression() instanceof ConstantExpression) {
                ConstantExpression value = (ConstantExpression)field.getInitialValueExpression();
                if (!(value = new ConstantExpression(value.getValue())).getType().equals(field.getType()) && ClassHelper.isNumberType(field.getType())) {
                    value.setType(field.getType());
                }
                return ExpressionUtils.configure(exp, value);
            }
        } else if (exp instanceof BinaryExpression) {
            BinaryExpression be = (BinaryExpression)exp;
            Expression lhs = ExpressionUtils.transformInlineConstants(be.getLeftExpression());
            Expression rhs = ExpressionUtils.transformInlineConstants(be.getRightExpression());
            if (be.getOperation().getType() == 200 && lhs instanceof ConstantExpression && rhs instanceof ConstantExpression && ClassHelper.isStringType(lhs.getType()) && ClassHelper.isStringType(rhs.getType())) {
                return ExpressionUtils.configure(exp, new ConstantExpression(lhs.getText() + rhs.getText()));
            }
            be.setLeftExpression(lhs);
            be.setRightExpression(rhs);
        } else if (exp instanceof ListExpression) {
            List<Expression> list = ((ListExpression)exp).getExpressions();
            ListIterator<Expression> it = list.listIterator();
            while (it.hasNext()) {
                Expression e = ExpressionUtils.transformInlineConstants(it.next());
                it.set(e);
            }
        }
        return exp;
    }

    public static Expression transformInlineConstants(Expression exp, ClassNode attrType) {
        if (exp instanceof PropertyExpression) {
            PropertyExpression pe = (PropertyExpression)exp;
            ClassNode type = pe.getObjectExpression().getType();
            if (pe.getObjectExpression() instanceof ClassExpression && !type.isEnum()) {
                if (type.isPrimaryClassNode()) {
                    Expression e;
                    FieldNode fn = type.getField(pe.getPropertyAsString());
                    if (fn != null && fn.isStatic() && fn.isFinal() && (e = ExpressionUtils.transformInlineConstants(fn.getInitialValueExpression(), attrType)) != null) {
                        return e;
                    }
                } else if (type.isResolved()) {
                    try {
                        Field field = type.redirect().getTypeClass().getField(pe.getPropertyAsString());
                        if (field != null && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                            ConstantExpression ce = new ConstantExpression(field.get(null), true);
                            ExpressionUtils.configure(exp, ce);
                            return ce;
                        }
                    }
                    catch (Exception | LinkageError field) {}
                }
            }
        } else if (exp instanceof VariableExpression) {
            Expression e;
            FieldNode fn;
            VariableExpression ve = (VariableExpression)exp;
            if (ve.getAccessedVariable() instanceof FieldNode && (fn = (FieldNode)ve.getAccessedVariable()).isStatic() && fn.isFinal() && (e = ExpressionUtils.transformInlineConstants(fn.getInitialValueExpression(), attrType)) != null) {
                return e;
            }
        } else if (exp instanceof ConstantExpression) {
            Object value = ((ConstantExpression)exp).getValue();
            ClassNode targetType = ClassHelper.getWrapper(attrType);
            if (value instanceof Integer) {
                Integer integer = (Integer)value;
                if (ClassHelper.isWrapperByte(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(integer.byteValue(), true));
                }
                if (ClassHelper.isWrapperLong(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(integer.longValue(), true));
                }
                if (ClassHelper.isWrapperShort(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(integer.shortValue(), true));
                }
                if (ClassHelper.isWrapperFloat(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(Float.valueOf(integer.floatValue()), true));
                }
                if (ClassHelper.isWrapperDouble(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(integer.doubleValue(), true));
                }
                if (ClassHelper.isWrapperCharacter(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(Character.valueOf((char)integer.intValue()), true));
                }
            } else if (value instanceof BigDecimal) {
                BigDecimal decimal = (BigDecimal)value;
                if (ClassHelper.isWrapperFloat(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(Float.valueOf(decimal.floatValue()), true));
                }
                if (ClassHelper.isWrapperDouble(targetType)) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(decimal.doubleValue(), true));
                }
            } else if (value instanceof String) {
                String string = (String)value;
                if (ClassHelper.isWrapperCharacter(targetType) && string.length() == 1) {
                    return ExpressionUtils.configure(exp, new ConstantExpression(Character.valueOf(string.charAt(0)), true));
                }
            }
        } else if (exp instanceof CastExpression) {
            Expression e = ExpressionUtils.transformInlineConstants(((CastExpression)exp).getExpression(), exp.getType());
            if (ClassHelper.getWrapper(e.getType()).isDerivedFrom(ClassHelper.getWrapper(attrType))) {
                return e;
            }
        } else if (exp instanceof BinaryExpression) {
            ConstantExpression ce = ExpressionUtils.transformBinaryConstantExpression((BinaryExpression)exp, attrType);
            if (ce != null) {
                return ce;
            }
        } else if (exp instanceof ListExpression) {
            return ExpressionUtils.transformListOfConstants((ListExpression)exp, attrType);
        }
        return exp;
    }

    public static Expression transformListOfConstants(ListExpression origList, ClassNode attrType) {
        ListExpression newList = new ListExpression();
        attrType = StaticTypeCheckingVisitor.inferLoopElementType(attrType);
        boolean changed = false;
        for (Expression e : origList.getExpressions()) {
            try {
                Expression transformed = ExpressionUtils.transformInlineConstants(e, attrType);
                newList.addExpression(transformed);
                if (transformed == e) continue;
                changed = true;
            }
            catch (Exception ignored) {
                newList.addExpression(e);
            }
        }
        if (changed) {
            newList.setSourcePosition(origList);
            return newList;
        }
        return origList;
    }

    private static ConstantExpression configure(Expression origX, ConstantExpression newX) {
        newX.setSourcePosition(origX);
        return newX;
    }

    private static Number safeNumber(ConstantExpression constX) {
        Object value = constX.getValue();
        return value instanceof Number ? (Number)((Number)value) : (Number)null;
    }

    private ExpressionUtils() {
        assert (false);
    }

    static {
        Arrays.sort(HANDLED_TYPES);
    }
}

