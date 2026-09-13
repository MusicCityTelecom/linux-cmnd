/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.common.LiteralExpression
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.integration.expression;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public final class ExpressionEvalMap
extends AbstractMap<String, Object> {
    public static final EvaluationCallback SIMPLE_CALLBACK = Expression::getValue;
    private final Map<String, ?> original;
    private final EvaluationCallback evaluationCallback;

    private ExpressionEvalMap(Map<String, ?> original, EvaluationCallback evaluationCallback) {
        this.original = original;
        this.evaluationCallback = evaluationCallback;
    }

    @Override
    @Nullable
    public Object get(Object key) {
        Object value = this.original.get(key);
        if (value != null) {
            Expression expression;
            if (value instanceof Expression) {
                expression = (Expression)value;
            } else if (value instanceof String) {
                expression = new LiteralExpression((String)value);
            } else {
                throw new IllegalArgumentException("Values must be 'java.lang.String' or 'org.springframework.expression.Expression'; the value type for key " + key + " is : " + value.getClass());
            }
            return this.evaluationCallback.evaluate(expression);
        }
        return null;
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.original.keySet().stream().map(key -> new AbstractMap.SimpleImmutableEntry<String, Object>((String)key, this.get(key))).collect(Collectors.toSet());
    }

    @Override
    public Collection<Object> values() {
        return this.original.values().stream().map(this::get).collect(Collectors.toList());
    }

    @Override
    public boolean containsKey(Object key) {
        return this.original.containsKey(key);
    }

    @Override
    public Set<String> keySet() {
        return this.original.keySet();
    }

    @Override
    public boolean isEmpty() {
        return this.original.isEmpty();
    }

    @Override
    public int size() {
        return this.original.size();
    }

    @Override
    public boolean equals(Object o) {
        return this.original.equals(o);
    }

    @Override
    public int hashCode() {
        return this.original.hashCode();
    }

    @Override
    public String toString() {
        return this.original.toString();
    }

    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public static ExpressionEvalMapBuilder from(Map<String, ?> expressions) {
        Assert.notNull(expressions, (String)"'expressions' must not be null.");
        return new ExpressionEvalMapBuilder(expressions);
    }

    @FunctionalInterface
    public static interface EvaluationCallback {
        @Nullable
        public Object evaluate(Expression var1);
    }

    public static final class ExpressionEvalMapBuilder {
        private final Map<String, ?> expressions;
        private EvaluationCallback evaluationCallback;
        @Nullable
        private EvaluationContext context;
        @Nullable
        private Object root;
        private boolean rootExplicitlySet;
        @Nullable
        private Class<?> returnType;
        private final ExpressionEvalMapComponentsBuilder evalMapComponentsBuilder = new ExpressionEvalMapComponentsBuilderImpl();
        private final ExpressionEvalMapFinalBuilder finalBuilder = new ExpressionEvalMapFinalBuilderImpl();

        private ExpressionEvalMapBuilder(Map<String, ?> expressions) {
            this.expressions = expressions;
        }

        public ExpressionEvalMapFinalBuilder usingCallback(EvaluationCallback callback) {
            this.evaluationCallback = callback;
            return this.finalBuilder;
        }

        public ExpressionEvalMapFinalBuilder usingSimpleCallback() {
            return this.usingCallback(SIMPLE_CALLBACK);
        }

        public ExpressionEvalMapComponentsBuilder usingEvaluationContext(EvaluationContext context) {
            this.context = context;
            return this.evalMapComponentsBuilder;
        }

        public ExpressionEvalMapComponentsBuilder withRoot(@Nullable Object root) {
            this.root = root;
            this.rootExplicitlySet = true;
            return this.evalMapComponentsBuilder;
        }

        public ExpressionEvalMapComponentsBuilder withReturnType(Class<?> returnType) {
            this.returnType = returnType;
            return this.evalMapComponentsBuilder;
        }

        private class ExpressionEvalMapComponentsBuilderImpl
        extends ExpressionEvalMapFinalBuilderImpl
        implements ExpressionEvalMapComponentsBuilder {
            ExpressionEvalMapComponentsBuilderImpl() {
            }

            @Override
            public ExpressionEvalMapComponentsBuilder usingEvaluationContext(EvaluationContext context) {
                return ExpressionEvalMapBuilder.this.usingEvaluationContext(context);
            }

            @Override
            public ExpressionEvalMapComponentsBuilder withRoot(@Nullable Object root) {
                return ExpressionEvalMapBuilder.this.withRoot(root);
            }

            @Override
            public ExpressionEvalMapComponentsBuilder withReturnType(Class<?> returnType) {
                return ExpressionEvalMapBuilder.this.withReturnType(returnType);
            }
        }

        private class ExpressionEvalMapFinalBuilderImpl
        implements ExpressionEvalMapFinalBuilder {
            ExpressionEvalMapFinalBuilderImpl() {
            }

            @Override
            public ExpressionEvalMap build() {
                if (ExpressionEvalMapBuilder.this.evaluationCallback != null) {
                    return new ExpressionEvalMap(ExpressionEvalMapBuilder.this.expressions, ExpressionEvalMapBuilder.this.evaluationCallback);
                }
                return new ExpressionEvalMap(ExpressionEvalMapBuilder.this.expressions, new ComponentsEvaluationCallback(ExpressionEvalMapBuilder.this.context, ExpressionEvalMapBuilder.this.root, ExpressionEvalMapBuilder.this.rootExplicitlySet, ExpressionEvalMapBuilder.this.returnType));
            }
        }
    }

    public static interface ExpressionEvalMapComponentsBuilder
    extends ExpressionEvalMapFinalBuilder {
        public ExpressionEvalMapComponentsBuilder usingEvaluationContext(EvaluationContext var1);

        public ExpressionEvalMapComponentsBuilder withRoot(@Nullable Object var1);

        public ExpressionEvalMapComponentsBuilder withReturnType(Class<?> var1);
    }

    @FunctionalInterface
    public static interface ExpressionEvalMapFinalBuilder {
        public ExpressionEvalMap build();
    }

    public static class ComponentsEvaluationCallback
    implements EvaluationCallback {
        @Nullable
        private final EvaluationContext context;
        @Nullable
        private final Object root;
        private final boolean rootExplicitlySet;
        @Nullable
        private final Class<?> returnType;

        public ComponentsEvaluationCallback(@Nullable EvaluationContext context, @Nullable Object root, boolean rootExplicitlySet, @Nullable Class<?> returnType) {
            this.context = context;
            this.root = root;
            this.rootExplicitlySet = rootExplicitlySet;
            this.returnType = returnType;
        }

        @Override
        public Object evaluate(Expression expression) {
            if (this.context != null) {
                if (this.rootExplicitlySet) {
                    return expression.getValue(this.context, this.root, this.returnType);
                }
                return expression.getValue(this.context, this.returnType);
            }
            return expression.getValue(this.root, this.returnType);
        }
    }
}

