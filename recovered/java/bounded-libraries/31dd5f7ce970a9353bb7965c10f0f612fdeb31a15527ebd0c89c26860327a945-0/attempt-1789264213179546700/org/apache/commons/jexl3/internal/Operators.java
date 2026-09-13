/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.lang.reflect.Method;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlOperator;
import org.apache.commons.jexl3.internal.Interpreter;
import org.apache.commons.jexl3.internal.InterpreterBase;
import org.apache.commons.jexl3.internal.introspection.MethodExecutor;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlUberspect;
import org.apache.commons.jexl3.parser.JexlNode;

public class Operators {
    protected final InterpreterBase interpreter;
    protected final JexlArithmetic.Uberspect operators;

    protected Operators(InterpreterBase owner) {
        JexlArithmetic arithmetic = owner.arithmetic;
        JexlUberspect uberspect = owner.uberspect;
        this.interpreter = owner;
        this.operators = uberspect.getArithmetic(arithmetic);
    }

    private boolean returnsBoolean(JexlMethod vm) {
        if (vm != null) {
            Class<?> rc = vm.getReturnType();
            return Boolean.TYPE.equals(rc) || Boolean.class.equals(rc);
        }
        return false;
    }

    private boolean returnsInteger(JexlMethod vm) {
        if (vm != null) {
            Class<?> rc = vm.getReturnType();
            return Integer.TYPE.equals(rc) || Integer.class.equals(rc);
        }
        return false;
    }

    private boolean isArithmetic(JexlMethod vm) {
        if (vm instanceof MethodExecutor) {
            Method method = ((MethodExecutor)vm).getMethod();
            return JexlArithmetic.class.equals(method.getDeclaringClass());
        }
        return false;
    }

    protected Object tryOverload(JexlNode node, JexlOperator operator, Object ... args) {
        if (this.operators != null && this.operators.overloads(operator)) {
            JexlArithmetic arithmetic = this.interpreter.arithmetic;
            boolean cache = this.interpreter.cache;
            try {
                Object eval;
                JexlMethod me;
                Object cached;
                if (cache && (cached = node.jjtGetValue()) instanceof JexlMethod && !(me = (JexlMethod)cached).tryFailed(eval = me.tryInvoke(operator.getMethodName(), arithmetic, args))) {
                    return eval;
                }
                JexlMethod vm = this.operators.getOperator(operator, args);
                if (vm != null && !this.isArithmetic(vm)) {
                    Object result = vm.invoke(arithmetic, args);
                    if (cache) {
                        node.jjtSetValue(vm);
                    }
                    return result;
                }
            }
            catch (Exception xany) {
                return this.interpreter.operatorError(node, operator, xany);
            }
        }
        return JexlEngine.TRY_FAILED;
    }

    protected Object tryAssignOverload(JexlNode node, JexlOperator operator, Object ... args) {
        JexlArithmetic arithmetic = this.interpreter.arithmetic;
        if (args.length != operator.getArity()) {
            return JexlEngine.TRY_FAILED;
        }
        Object result = this.tryOverload(node, operator, args);
        if (result != JexlEngine.TRY_FAILED) {
            return result;
        }
        JexlOperator base = operator.getBaseOperator();
        if (base == null) {
            throw new IllegalArgumentException("must be called with a side-effect operator");
        }
        if (this.operators != null && this.operators.overloads(base)) {
            try {
                JexlMethod vm = this.operators.getOperator(base, args);
                if (vm != null && (result = vm.invoke(arithmetic, args)) != JexlEngine.TRY_FAILED) {
                    return result;
                }
            }
            catch (Exception xany) {
                this.interpreter.operatorError(node, base, xany);
            }
        }
        try {
            switch (operator) {
                case SELF_ADD: {
                    return arithmetic.add(args[0], args[1]);
                }
                case SELF_SUBTRACT: {
                    return arithmetic.subtract(args[0], args[1]);
                }
                case SELF_MULTIPLY: {
                    return arithmetic.multiply(args[0], args[1]);
                }
                case SELF_DIVIDE: {
                    return arithmetic.divide(args[0], args[1]);
                }
                case SELF_MOD: {
                    return arithmetic.mod(args[0], args[1]);
                }
                case SELF_AND: {
                    return arithmetic.and(args[0], args[1]);
                }
                case SELF_OR: {
                    return arithmetic.or(args[0], args[1]);
                }
                case SELF_XOR: {
                    return arithmetic.xor(args[0], args[1]);
                }
            }
            throw new UnsupportedOperationException(operator.getOperatorSymbol());
        }
        catch (Exception xany) {
            this.interpreter.operatorError(node, base, xany);
            return JexlEngine.TRY_FAILED;
        }
    }

    protected boolean startsWith(JexlNode node, String operator, Object left, Object right) {
        JexlArithmetic arithmetic = this.interpreter.arithmetic;
        JexlUberspect uberspect = this.interpreter.uberspect;
        try {
            Object result = this.tryOverload(node, JexlOperator.STARTSWITH, left, right);
            if (result instanceof Boolean) {
                return (Boolean)result;
            }
            Boolean matched = arithmetic.startsWith(left, right);
            if (matched != null) {
                return matched;
            }
            try {
                Object[] argv = new Object[]{right};
                JexlMethod vm = uberspect.getMethod(left, "startsWith", argv);
                if (this.returnsBoolean(vm)) {
                    return (Boolean)vm.invoke(left, argv);
                }
                if (arithmetic.narrowArguments(argv) && this.returnsBoolean(vm = uberspect.getMethod(left, "startsWith", argv))) {
                    return (Boolean)vm.invoke(left, argv);
                }
            }
            catch (Exception e) {
                throw new JexlException(node, operator + " error", (Throwable)e);
            }
            return arithmetic.equals(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, operator + " error", (Throwable)xrt);
        }
    }

    protected boolean endsWith(JexlNode node, String operator, Object left, Object right) {
        JexlArithmetic arithmetic = this.interpreter.arithmetic;
        JexlUberspect uberspect = this.interpreter.uberspect;
        try {
            Object result = this.tryOverload(node, JexlOperator.ENDSWITH, left, right);
            if (result instanceof Boolean) {
                return (Boolean)result;
            }
            Boolean matched = arithmetic.endsWith(left, right);
            if (matched != null) {
                return matched;
            }
            try {
                Object[] argv = new Object[]{right};
                JexlMethod vm = uberspect.getMethod(left, "endsWith", argv);
                if (this.returnsBoolean(vm)) {
                    return (Boolean)vm.invoke(left, argv);
                }
                if (arithmetic.narrowArguments(argv) && this.returnsBoolean(vm = uberspect.getMethod(left, "endsWith", argv))) {
                    return (Boolean)vm.invoke(left, argv);
                }
            }
            catch (Exception e) {
                throw new JexlException(node, operator + " error", (Throwable)e);
            }
            return arithmetic.equals(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, operator + " error", (Throwable)xrt);
        }
    }

    protected boolean contains(JexlNode node, String op, Object left, Object right) {
        JexlArithmetic arithmetic = this.interpreter.arithmetic;
        JexlUberspect uberspect = this.interpreter.uberspect;
        try {
            Object result = this.tryOverload(node, JexlOperator.CONTAINS, left, right);
            if (result instanceof Boolean) {
                return (Boolean)result;
            }
            Boolean matched = arithmetic.contains(left, right);
            if (matched != null) {
                return matched;
            }
            try {
                Object[] argv = new Object[]{right};
                JexlMethod vm = uberspect.getMethod(left, "contains", argv);
                if (this.returnsBoolean(vm)) {
                    return (Boolean)vm.invoke(left, argv);
                }
                if (arithmetic.narrowArguments(argv) && this.returnsBoolean(vm = uberspect.getMethod(left, "contains", argv))) {
                    return (Boolean)vm.invoke(left, argv);
                }
            }
            catch (Exception e) {
                throw new JexlException(node, op + " error", (Throwable)e);
            }
            return arithmetic.equals(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, op + " error", (Throwable)xrt);
        }
    }

    protected Object empty(JexlNode node, Object object) {
        if (object == null) {
            return true;
        }
        Object result = this.tryOverload(node, JexlOperator.EMPTY, object);
        if (result != JexlEngine.TRY_FAILED) {
            return result;
        }
        JexlArithmetic arithmetic = this.interpreter.arithmetic;
        result = arithmetic.isEmpty(object, null);
        if (result == null) {
            JexlUberspect uberspect = this.interpreter.uberspect;
            result = false;
            JexlMethod vm = uberspect.getMethod(object, "isEmpty", Interpreter.EMPTY_PARAMS);
            if (this.returnsBoolean(vm)) {
                try {
                    result = vm.invoke(object, Interpreter.EMPTY_PARAMS);
                }
                catch (Exception xany) {
                    this.interpreter.operatorError(node, JexlOperator.EMPTY, xany);
                }
            }
        }
        return !(result instanceof Boolean) || (Boolean)result != false;
    }

    protected Object size(JexlNode node, Object object) {
        JexlUberspect uberspect;
        JexlMethod vm;
        if (object == null) {
            return 0;
        }
        Object result = this.tryOverload(node, JexlOperator.SIZE, object);
        if (result != JexlEngine.TRY_FAILED) {
            return result;
        }
        JexlArithmetic arithmetic = this.interpreter.arithmetic;
        result = arithmetic.size(object, null);
        if (result == null && this.returnsInteger(vm = (uberspect = this.interpreter.uberspect).getMethod(object, "size", Interpreter.EMPTY_PARAMS))) {
            try {
                result = vm.invoke(object, Interpreter.EMPTY_PARAMS);
            }
            catch (Exception xany) {
                this.interpreter.operatorError(node, JexlOperator.SIZE, xany);
            }
        }
        return result instanceof Number ? ((Number)result).intValue() : 0;
    }
}

