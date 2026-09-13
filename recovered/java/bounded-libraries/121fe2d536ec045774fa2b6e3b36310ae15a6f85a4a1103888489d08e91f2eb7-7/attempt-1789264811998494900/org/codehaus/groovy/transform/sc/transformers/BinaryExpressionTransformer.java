/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc.transformers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.sc.StaticPropertyAccessHelper;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.sc.ListOfExpressionsExpression;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.sc.TemporaryVariableExpression;
import org.codehaus.groovy.transform.sc.transformers.CompareIdentityExpression;
import org.codehaus.groovy.transform.sc.transformers.CompareToNullExpression;
import org.codehaus.groovy.transform.sc.transformers.StaticCompilationTransformer;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class BinaryExpressionTransformer {
    private static final MethodNode COMPARE_TO_METHOD = ClassHelper.COMPARABLE_TYPE.getMethods("compareTo").get(0);
    private static final ConstantExpression CONSTANT_MINUS_ONE = GeneralUtils.constX(-1, true);
    private static final ConstantExpression CONSTANT_ZERO = GeneralUtils.constX(0, true);
    private static final ConstantExpression CONSTANT_ONE = GeneralUtils.constX(1, true);
    private int tmpVarCounter;
    private final StaticCompilationTransformer staticCompilationTransformer;

    public BinaryExpressionTransformer(StaticCompilationTransformer staticCompilationTransformer) {
        this.staticCompilationTransformer = staticCompilationTransformer;
    }

    public Expression transformBinaryExpression(BinaryExpression bin) {
        Expression leftExpression = bin.getLeftExpression();
        Expression rightExpression = bin.getRightExpression();
        if (bin instanceof DeclarationExpression && leftExpression instanceof VariableExpression && rightExpression instanceof ConstantExpression && !((ConstantExpression)rightExpression).isNullExpression()) {
            Character c;
            ClassNode declarationType = ((VariableExpression)leftExpression).getOriginType();
            if (declarationType.equals(ClassHelper.char_TYPE) && (c = BinaryExpressionTransformer.tryCharConstant(rightExpression)) != null) {
                return this.transformCharacterInitialization(bin, c);
            }
            if (!declarationType.equals(rightExpression.getType()) && WideningCategories.isDoubleCategory(ClassHelper.getUnwrapper(declarationType)) && ClassHelper.getWrapper(rightExpression.getType()).isDerivedFrom(ClassHelper.Number_TYPE)) {
                return this.transformNumericalInitialization(bin, (Number)((ConstantExpression)rightExpression).getValue(), declarationType);
            }
        }
        boolean equal = false;
        switch (bin.getOperation().getType()) {
            case 100: {
                this.optimizeArrayCollectionAssignment(bin);
                Expression expr = this.transformAssignmentToSetterCall(bin);
                if (expr != null) {
                    return expr;
                }
                if (!(leftExpression instanceof TupleExpression) || !(rightExpression instanceof ListExpression)) break;
                return this.transformMultipleAssignment(bin);
            }
            case 573: {
                equal = true;
            }
            case 129: {
                return this.transformInOperation(bin, equal);
            }
            case 121: 
            case 123: {
                equal = true;
            }
            case 120: 
            case 122: {
                Expression expr = this.transformEqualityComparison(bin, equal);
                if (expr == null) break;
                return expr;
            }
            case 128: {
                Expression expr = this.transformRelationComparison(bin);
                if (expr == null) break;
                return expr;
            }
        }
        Object[] array = (Object[])bin.getNodeMetaData((Object)StaticCompilationMetadataKeys.BINARY_EXP_TARGET);
        if (array != null) {
            return this.transformToTargetMethodCall(bin, (MethodNode)array[0], (String)array[1]);
        }
        return this.staticCompilationTransformer.superTransform(bin);
    }

    private Expression transformCharacterInitialization(BinaryExpression bin, Character rhs) {
        ConstantExpression ce = GeneralUtils.constX(rhs, true);
        ce.setSourcePosition(bin.getRightExpression());
        bin.setRightExpression(ce);
        return bin;
    }

    private Expression transformNumericalInitialization(BinaryExpression bin, Number rhs, ClassNode lhsType) {
        ConstantExpression ce = GeneralUtils.constX(BinaryExpressionTransformer.convertConstant(rhs, ClassHelper.getWrapper(lhsType)), true);
        ce.setSourcePosition(bin.getRightExpression());
        ce.setType(lhsType);
        bin.setRightExpression(ce);
        return bin;
    }

    private void optimizeArrayCollectionAssignment(BinaryExpression bin) {
        Expression rightExpression = bin.getRightExpression();
        ClassNode leftType = this.findType(bin.getLeftExpression());
        ClassNode rightType = this.findType(rightExpression);
        if (leftType.isArray() && !(rightExpression instanceof ListExpression) && GeneralUtils.isOrImplements(rightType, ClassHelper.COLLECTION_TYPE)) {
            ArrayExpression emptyArray = new ArrayExpression(leftType.getComponentType(), null, Collections.singletonList(CONSTANT_ZERO));
            rightExpression = GeneralUtils.callX(rightExpression, "toArray", (Expression)GeneralUtils.args(emptyArray));
            rightExpression.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, leftType);
            ((MethodCallExpression)rightExpression).setMethodTarget(rightType.getMethod("toArray", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE.makeArray(), "a")}));
            ((MethodCallExpression)rightExpression).setImplicitThis(false);
            ((MethodCallExpression)rightExpression).setSafe(true);
            bin.setRightExpression(rightExpression);
        }
    }

    private Expression transformAssignmentToSetterCall(BinaryExpression bin) {
        MethodNode directMCT = (MethodNode)bin.getLeftExpression().getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
        if (directMCT != null) {
            Expression left = this.staticCompilationTransformer.transform(bin.getLeftExpression());
            Expression right = this.staticCompilationTransformer.transform(bin.getRightExpression());
            if (left instanceof PropertyExpression) {
                PropertyExpression pe = (PropertyExpression)left;
                return BinaryExpressionTransformer.transformAssignmentToSetterCall(pe.getObjectExpression(), directMCT, right, false, pe.isSafe(), pe.getProperty(), bin);
            }
            if (left instanceof VariableExpression) {
                return BinaryExpressionTransformer.transformAssignmentToSetterCall(GeneralUtils.varX("this"), directMCT, right, true, false, left, bin);
            }
        }
        return null;
    }

    private static Expression transformAssignmentToSetterCall(Expression receiver, MethodNode setterMethod, Expression valueExpression, boolean implicitThis, boolean safeNavigation, Expression nameExpression, Expression sourceExpression) {
        PropertyExpression pos = new PropertyExpression(null, nameExpression);
        pos.setSourcePosition(sourceExpression);
        return StaticPropertyAccessHelper.transformToSetterCall(receiver, setterMethod, valueExpression, implicitThis, safeNavigation, false, true, pos);
    }

    private Expression transformInOperation(BinaryExpression bin, boolean in) {
        Expression leftExpression = bin.getLeftExpression();
        Expression rightExpression = bin.getRightExpression();
        MethodCallExpression call = GeneralUtils.callX(rightExpression, in ? "isCase" : "isNotCase", leftExpression);
        call.setImplicitThis(false);
        call.setSourcePosition(bin);
        call.copyNodeMetaData(bin);
        call.setMethodTarget((MethodNode)bin.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET));
        if (rightExpression instanceof ListExpression || rightExpression instanceof MapExpression || rightExpression instanceof RangeExpression || rightExpression instanceof ClassExpression || rightExpression instanceof ConstantExpression && !ExpressionUtils.isNullConstant(rightExpression)) {
            return this.staticCompilationTransformer.transform(call);
        }
        rightExpression = this.transformRepeatedReference(rightExpression);
        call.setObjectExpression(rightExpression);
        TernaryExpression safe = GeneralUtils.ternaryX(new CompareToNullExpression(rightExpression, true), new CompareToNullExpression(leftExpression, in), call);
        safe.putNodeMetaData("classgen.callback", BinaryExpressionTransformer.classgenCallback(call.getObjectExpression()));
        return this.staticCompilationTransformer.transform(safe);
    }

    private Expression transformRepeatedReference(Expression exp) {
        if (exp instanceof ConstantExpression || exp instanceof VariableExpression && ((VariableExpression)exp).getAccessedVariable() instanceof Parameter) {
            return exp;
        }
        return new TemporaryVariableExpression(exp);
    }

    private Expression transformEqualityComparison(BinaryExpression bin, boolean eq) {
        Expression leftExpression = bin.getLeftExpression();
        Expression rightExpression = bin.getRightExpression();
        if (ExpressionUtils.isNullConstant(rightExpression)) {
            CompareToNullExpression ctn = new CompareToNullExpression(this.staticCompilationTransformer.transform(leftExpression), eq);
            ctn.setSourcePosition(bin);
            return ctn;
        }
        if (ExpressionUtils.isNullConstant(leftExpression)) {
            CompareToNullExpression ctn = new CompareToNullExpression(this.staticCompilationTransformer.transform(rightExpression), eq);
            ctn.setSourcePosition(bin);
            return ctn;
        }
        if (bin.getOperation().getText().length() == 3 && !ClassHelper.isPrimitiveType(this.findType(leftExpression)) && !ClassHelper.isPrimitiveType(this.findType(rightExpression))) {
            CompareIdentityExpression cid = new CompareIdentityExpression(this.staticCompilationTransformer.transform(leftExpression), eq, this.staticCompilationTransformer.transform(rightExpression));
            cid.setSourcePosition(bin);
            return cid;
        }
        return null;
    }

    private Expression transformRelationComparison(BinaryExpression bin) {
        ClassNode rightType;
        Expression leftExpression = bin.getLeftExpression();
        Expression rightExpression = bin.getRightExpression();
        ClassNode leftType = this.findType(leftExpression);
        if (leftType.equals(rightType = this.findType(rightExpression)) && ClassHelper.isPrimitiveType(leftType) || ClassHelper.isPrimitiveType(rightType)) {
            ClassNode wrapperType = ClassHelper.getWrapper(leftType);
            ArgumentListExpression leftAndRight = GeneralUtils.args(this.staticCompilationTransformer.transform(leftExpression), this.staticCompilationTransformer.transform(rightExpression));
            MethodCallExpression call = GeneralUtils.callX((Expression)GeneralUtils.classX(wrapperType), "compare", (Expression)leftAndRight);
            call.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, ClassHelper.int_TYPE);
            call.setMethodTarget(wrapperType.getMethods("compare").get(0));
            call.setImplicitThis(false);
            call.setSourcePosition(bin);
            return call;
        }
        if (leftType.implementsInterface(ClassHelper.COMPARABLE_TYPE) && rightType.implementsInterface(ClassHelper.COMPARABLE_TYPE)) {
            Expression left = this.transformRepeatedReference(this.staticCompilationTransformer.transform(leftExpression));
            Expression right = this.transformRepeatedReference(this.staticCompilationTransformer.transform(rightExpression));
            MethodCallExpression call = GeneralUtils.callX(left, "compareTo", (Expression)GeneralUtils.args(right));
            call.setMethodTarget(COMPARE_TO_METHOD);
            call.setImplicitThis(false);
            call.setSourcePosition(bin);
            TernaryExpression expr = GeneralUtils.ternaryX(new CompareToNullExpression(right, true), CONSTANT_ONE, call);
            expr.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, ClassHelper.int_TYPE);
            expr = GeneralUtils.ternaryX(new CompareToNullExpression(left, true), CONSTANT_MINUS_ONE, expr);
            expr.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, ClassHelper.int_TYPE);
            expr = GeneralUtils.ternaryX(new CompareIdentityExpression(left, right), CONSTANT_ZERO, expr);
            expr.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, ClassHelper.int_TYPE);
            expr.putNodeMetaData("classgen.callback", BinaryExpressionTransformer.classgenCallback(right).andThen(BinaryExpressionTransformer.classgenCallback(left)));
            return expr;
        }
        return null;
    }

    private Expression transformMultipleAssignment(BinaryExpression bin) {
        ListOfExpressionsExpression list = new ListOfExpressionsExpression();
        List<Expression> leftExpressions = ((TupleExpression)bin.getLeftExpression()).getExpressions();
        List<Expression> rightExpressions = ((ListExpression)bin.getRightExpression()).getExpressions();
        Iterator<Expression> leftIt = leftExpressions.iterator();
        Iterator<Expression> rightIt = rightExpressions.iterator();
        if (bin instanceof DeclarationExpression) {
            while (leftIt.hasNext()) {
                Expression left = leftIt.next();
                if (!rightIt.hasNext()) continue;
                Expression right = rightIt.next();
                DeclarationExpression bexp = new DeclarationExpression(left, bin.getOperation(), right);
                bexp.setSourcePosition(right);
                list.addExpression(bexp);
            }
        } else {
            int size = rightExpressions.size();
            ArrayList<DeclarationExpression> tmpAssignments = new ArrayList<DeclarationExpression>(size);
            ArrayList<BinaryExpression> finalAssignments = new ArrayList<BinaryExpression>(size);
            int n = Math.min(size, leftExpressions.size());
            for (int i = 0; i < n; ++i) {
                Expression left = leftIt.next();
                Expression right = rightIt.next();
                VariableExpression tmpVar = GeneralUtils.varX("$tmpVar$" + this.tmpVarCounter++);
                BinaryExpression bexp = new DeclarationExpression(tmpVar, bin.getOperation(), right);
                bexp.setSourcePosition(right);
                tmpAssignments.add((DeclarationExpression)bexp);
                bexp = GeneralUtils.binX(left, bin.getOperation(), GeneralUtils.varX(tmpVar));
                bexp.setSourcePosition(left);
                finalAssignments.add(bexp);
            }
            for (Expression expression : tmpAssignments) {
                list.addExpression(expression);
            }
            for (Expression expression : finalAssignments) {
                list.addExpression(expression);
            }
        }
        return this.staticCompilationTransformer.transform(list);
    }

    private Expression transformToTargetMethodCall(BinaryExpression bin, MethodNode node, String name) {
        MethodCallExpression call;
        Expression right;
        Token operation = bin.getOperation();
        int operationType = operation.getType();
        Expression left = this.staticCompilationTransformer.transform(bin.getLeftExpression());
        Expression expr = this.tryOptimizeCharComparison(left, right = this.staticCompilationTransformer.transform(bin.getRightExpression()), bin);
        if (expr != null) {
            expr.removeNodeMetaData((Object)StaticCompilationMetadataKeys.BINARY_EXP_TARGET);
            expr.removeNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
            return expr;
        }
        MethodNode adapter = StaticCompilationTransformer.BYTECODE_BINARY_ADAPTERS.get(operationType);
        if (adapter != null) {
            ClassExpression sba = GeneralUtils.classX(StaticCompilationTransformer.BYTECODE_ADAPTER_CLASS);
            call = GeneralUtils.callX((Expression)sba, adapter.getName(), (Expression)GeneralUtils.args(left, right));
            call.setMethodTarget(adapter);
        } else {
            call = GeneralUtils.callX(left, name, (Expression)GeneralUtils.args(right));
            call.setMethodTarget(node);
        }
        call.setImplicitThis(false);
        if (Types.isAssignment(operationType)) {
            BinaryExpression be;
            expr = GeneralUtils.binX(left, Token.newSymbol(100, operation.getStartLine(), operation.getStartColumn()), call);
            if (left instanceof BinaryExpression && (be = (BinaryExpression)left).getOperation().getType() == 30) {
                be.setLeftExpression(this.transformRepeatedReference(be.getLeftExpression()));
                be.setRightExpression(this.transformRepeatedReference(be.getRightExpression()));
                expr.putNodeMetaData("classgen.callback", BinaryExpressionTransformer.classgenCallback(be.getRightExpression()).andThen(BinaryExpressionTransformer.classgenCallback(be.getLeftExpression())));
            }
        } else {
            expr = call;
        }
        expr.setSourcePosition(bin);
        return expr;
    }

    private BinaryExpression tryOptimizeCharComparison(Expression left, Expression right, BinaryExpression bin) {
        int op = bin.getOperation().getType();
        if (StaticTypeCheckingSupport.isCompareToBoolean(op) || op == 123 || op == 120) {
            Character cLeft = BinaryExpressionTransformer.tryCharConstant(left);
            Character cRight = BinaryExpressionTransformer.tryCharConstant(right);
            if (cLeft != null || cRight != null) {
                Expression oRight;
                Expression oLeft;
                Expression expression = oLeft = cLeft == null ? left : GeneralUtils.constX(cLeft, true);
                if (oLeft instanceof PropertyExpression && !BinaryExpressionTransformer.hasCharType(oLeft)) {
                    return null;
                }
                oLeft.setSourcePosition(left);
                Expression expression2 = oRight = cRight == null ? right : GeneralUtils.constX(cRight, true);
                if (oRight instanceof PropertyExpression && !BinaryExpressionTransformer.hasCharType(oRight)) {
                    return null;
                }
                oRight.setSourcePosition(right);
                bin.setLeftExpression(oLeft);
                bin.setRightExpression(oRight);
                return bin;
            }
        }
        return null;
    }

    private ClassNode findType(Expression e) {
        return this.staticCompilationTransformer.getTypeChooser().resolveType(e, this.staticCompilationTransformer.getClassNode());
    }

    private static boolean hasCharType(Expression e) {
        ClassNode inferredType = (ClassNode)e.getNodeMetaData((Object)StaticTypesMarker.INFERRED_RETURN_TYPE);
        return inferredType != null && ClassHelper.getWrapper(inferredType).equals(ClassHelper.Character_TYPE);
    }

    private static Character tryCharConstant(Expression e) {
        String value;
        if (e instanceof ConstantExpression && ClassHelper.STRING_TYPE.equals(e.getType()) && (value = (String)((ConstantExpression)e).getValue()) != null && value.length() == 1) {
            return Character.valueOf(value.charAt(0));
        }
        return null;
    }

    private static Object convertConstant(Number source, ClassNode target) {
        if (ClassHelper.isWrapperInteger(target)) {
            return source.intValue();
        }
        if (ClassHelper.isWrapperLong(target)) {
            return source.longValue();
        }
        if (ClassHelper.isWrapperByte(target)) {
            return source.byteValue();
        }
        if (ClassHelper.isWrapperShort(target)) {
            return source.shortValue();
        }
        if (ClassHelper.isWrapperFloat(target)) {
            return Float.valueOf(source.floatValue());
        }
        if (ClassHelper.isWrapperDouble(target)) {
            return source.doubleValue();
        }
        if (ClassHelper.isWrapperCharacter(target)) {
            return Character.valueOf((char)source.intValue());
        }
        if (ClassHelper.isBigDecimalType(target)) {
            return DefaultGroovyMethods.asType(source, BigDecimal.class);
        }
        if (ClassHelper.isBigIntegerType(target)) {
            return DefaultGroovyMethods.asType(source, BigInteger.class);
        }
        throw new IllegalArgumentException("Unsupported conversion: " + target.getText());
    }

    private static Consumer<WriterController> classgenCallback(Expression source) {
        return source instanceof TemporaryVariableExpression ? ((TemporaryVariableExpression)source)::remove : wc -> {};
    }
}

