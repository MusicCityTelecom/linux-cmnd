/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

public final class JexlFeatures {
    private long flags;
    private Set<String> reservedNames;
    private Predicate<String> nameSpaces;
    public static final Predicate<String> TEST_STR_FALSE = s -> false;
    private static final String[] F_NAMES = new String[]{"register", "reserved variable", "local variable", "assign/modify", "global assign/modify", "array reference", "create instance", "loop", "function", "method call", "set/map/array literal", "pragma", "annotation", "script", "lexical", "lexicalShade"};
    private static final int REGISTER = 0;
    public static final int RESERVED = 1;
    public static final int LOCAL_VAR = 2;
    public static final int SIDE_EFFECT = 3;
    public static final int SIDE_EFFECT_GLOBAL = 4;
    public static final int ARRAY_REF_EXPR = 5;
    public static final int NEW_INSTANCE = 6;
    public static final int LOOP = 7;
    public static final int LAMBDA = 8;
    public static final int METHOD_CALL = 9;
    public static final int STRUCTURED_LITERAL = 10;
    public static final int PRAGMA = 11;
    public static final int ANNOTATION = 12;
    public static final int SCRIPT = 13;
    public static final int LEXICAL = 14;
    public static final int LEXICAL_SHADE = 15;

    public JexlFeatures() {
        this.flags = 16380L;
        this.reservedNames = Collections.emptySet();
        this.nameSpaces = TEST_STR_FALSE;
    }

    public JexlFeatures(JexlFeatures features) {
        this.flags = features.flags;
        this.reservedNames = features.reservedNames;
        this.nameSpaces = features.nameSpaces;
    }

    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int)(this.flags ^ this.flags >>> 32);
        hash = 53 * hash + (this.reservedNames != null ? this.reservedNames.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        JexlFeatures other = (JexlFeatures)obj;
        if (this.flags != other.flags) {
            return false;
        }
        return Objects.equals(this.reservedNames, other.reservedNames);
    }

    public static String stringify(int feature) {
        return feature >= 0 && feature < F_NAMES.length ? F_NAMES[feature] : "unsupported feature";
    }

    public JexlFeatures reservedNames(Collection<String> names) {
        this.reservedNames = names == null || names.isEmpty() ? Collections.emptySet() : Collections.unmodifiableSet(new TreeSet<String>(names));
        this.setFeature(1, !this.reservedNames.isEmpty());
        return this;
    }

    public Set<String> getReservedNames() {
        return this.reservedNames;
    }

    public boolean isReservedName(String name) {
        return name != null && this.reservedNames.contains(name);
    }

    public JexlFeatures namespaceTest(Predicate<String> names) {
        this.nameSpaces = names == null ? TEST_STR_FALSE : names;
        return this;
    }

    public Predicate<String> namespaceTest() {
        return this.nameSpaces;
    }

    private void setFeature(int feature, boolean flag) {
        this.flags = flag ? (this.flags |= (long)(1 << feature)) : (this.flags &= 1L << feature ^ 0xFFFFFFFFFFFFFFFFL);
    }

    private boolean getFeature(int feature) {
        return (this.flags & 1L << feature) != 0L;
    }

    public JexlFeatures register(boolean flag) {
        this.setFeature(0, flag);
        return this;
    }

    public boolean supportsRegister() {
        return this.getFeature(0);
    }

    public JexlFeatures localVar(boolean flag) {
        this.setFeature(2, flag);
        return this;
    }

    public boolean supportsLocalVar() {
        return this.getFeature(2);
    }

    public JexlFeatures sideEffectGlobal(boolean flag) {
        this.setFeature(4, flag);
        return this;
    }

    public boolean supportsSideEffectGlobal() {
        return this.getFeature(4);
    }

    public JexlFeatures sideEffect(boolean flag) {
        this.setFeature(3, flag);
        return this;
    }

    public boolean supportsSideEffect() {
        return this.getFeature(3);
    }

    public JexlFeatures arrayReferenceExpr(boolean flag) {
        this.setFeature(5, flag);
        return this;
    }

    public boolean supportsArrayReferenceExpr() {
        return this.getFeature(5);
    }

    public JexlFeatures methodCall(boolean flag) {
        this.setFeature(9, flag);
        return this;
    }

    public boolean supportsMethodCall() {
        return this.getFeature(9);
    }

    public JexlFeatures structuredLiteral(boolean flag) {
        this.setFeature(10, flag);
        return this;
    }

    public boolean supportsStructuredLiteral() {
        return this.getFeature(10);
    }

    public JexlFeatures newInstance(boolean flag) {
        this.setFeature(6, flag);
        return this;
    }

    public boolean supportsNewInstance() {
        return this.getFeature(6);
    }

    public JexlFeatures loops(boolean flag) {
        this.setFeature(7, flag);
        return this;
    }

    public boolean supportsLoops() {
        return this.getFeature(7);
    }

    public JexlFeatures lambda(boolean flag) {
        this.setFeature(8, flag);
        return this;
    }

    public boolean supportsLambda() {
        return this.getFeature(8);
    }

    public JexlFeatures pragma(boolean flag) {
        this.setFeature(11, flag);
        return this;
    }

    public boolean supportsPragma() {
        return this.getFeature(11);
    }

    public JexlFeatures annotation(boolean flag) {
        this.setFeature(12, flag);
        return this;
    }

    public boolean supportsAnnotation() {
        return this.getFeature(12);
    }

    public JexlFeatures script(boolean flag) {
        this.setFeature(13, flag);
        return this;
    }

    public boolean supportsScript() {
        return this.getFeature(13);
    }

    public boolean supportsExpression() {
        return !this.getFeature(13);
    }

    public JexlFeatures lexical(boolean flag) {
        this.setFeature(14, flag);
        return this;
    }

    public boolean isLexical() {
        return this.getFeature(14);
    }

    public JexlFeatures lexicalShade(boolean flag) {
        this.setFeature(15, flag);
        if (flag) {
            this.setFeature(14, true);
        }
        return this;
    }

    public boolean isLexicalShade() {
        return this.getFeature(15);
    }
}

