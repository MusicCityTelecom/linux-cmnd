/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

public enum JexlOperator {
    ADD("+", "add", 2),
    SUBTRACT("-", "subtract", 2),
    MULTIPLY("*", "multiply", 2),
    DIVIDE("/", "divide", 2),
    MOD("%", "mod", 2),
    AND("&", "and", 2),
    OR("|", "or", 2),
    XOR("^", "xor", 2),
    EQ("==", "equals", 2),
    LT("<", "lessThan", 2),
    LTE("<=", "lessThanOrEqual", 2),
    GT(">", "greaterThan", 2),
    GTE(">=", "greaterThanOrEqual", 2),
    CONTAINS("=~", "contains", 2),
    STARTSWITH("=^", "startsWith", 2),
    ENDSWITH("=$", "endsWith", 2),
    NOT("!", "not", 1),
    COMPLEMENT("~", "complement", 1),
    NEGATE("-", "negate", 1),
    POSITIVIZE("+", "positivize", 1),
    EMPTY("empty", "empty", 1),
    SIZE("size", "size", 1),
    SELF_ADD("+=", "selfAdd", ADD),
    SELF_SUBTRACT("-=", "selfSubtract", SUBTRACT),
    SELF_MULTIPLY("*=", "selfMultiply", MULTIPLY),
    SELF_DIVIDE("/=", "selfDivide", DIVIDE),
    SELF_MOD("%=", "selfMod", MOD),
    SELF_AND("&=", "selfAnd", AND),
    SELF_OR("|=", "selfOr", OR),
    SELF_XOR("^=", "selfXor", XOR),
    ASSIGN("=", null, null),
    PROPERTY_GET(".", "propertyGet", 2),
    PROPERTY_SET(".=", "propertySet", 3),
    ARRAY_GET("[]", "arrayGet", 2),
    ARRAY_SET("[]=", "arraySet", 3),
    FOR_EACH("for(...)", "forEach", 1);

    private final String operator;
    private final String methodName;
    private final int arity;
    private final JexlOperator base;

    private JexlOperator(String o, String m, int argc) {
        this.operator = o;
        this.methodName = m;
        this.arity = argc;
        this.base = null;
    }

    private JexlOperator(String o, String m, JexlOperator b) {
        this.operator = o;
        this.methodName = m;
        this.arity = 2;
        this.base = b;
    }

    public final String getOperatorSymbol() {
        return this.operator;
    }

    public final String getMethodName() {
        return this.methodName;
    }

    public int getArity() {
        return this.arity;
    }

    public final JexlOperator getBaseOperator() {
        return this.base;
    }
}

