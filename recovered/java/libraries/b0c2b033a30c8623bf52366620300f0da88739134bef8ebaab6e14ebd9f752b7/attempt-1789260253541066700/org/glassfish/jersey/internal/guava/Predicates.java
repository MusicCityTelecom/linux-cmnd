/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import org.glassfish.jersey.internal.guava.Joiner;
import org.glassfish.jersey.internal.guava.Preconditions;

public final class Predicates {
    private static final Joiner COMMA_JOINER = Joiner.on();

    private Predicates() {
    }

    public static <T> Predicate<T> alwaysTrue() {
        return ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    private static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate<T>(predicate);
    }

    public static <T> Predicate<T> equalTo(T target) {
        return target == null ? Predicates.isNull() : new IsEqualToPredicate(target);
    }

    public static <T> Predicate<T> in(Collection<? extends T> target) {
        return new InPredicate(target);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate(predicate, function);
    }

    private static class CompositionPredicate<A, B>
    implements Predicate<A>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Predicate<B> p;
        final Function<A, ? extends B> f;

        private CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f) {
            this.p = Preconditions.checkNotNull(p);
            this.f = Preconditions.checkNotNull(f);
        }

        @Override
        public boolean test(A a) {
            return this.p.test(this.f.apply(a));
        }

        public boolean equals(Object obj) {
            if (obj instanceof CompositionPredicate) {
                CompositionPredicate that = (CompositionPredicate)obj;
                return this.f.equals(that.f) && this.p.equals(that.p);
            }
            return false;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.p.hashCode();
        }

        public String toString() {
            return this.p.toString() + "(" + this.f.toString() + ")";
        }
    }

    private static class InPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Collection<?> target;

        private InPredicate(Collection<?> target) {
            this.target = Preconditions.checkNotNull(target);
        }

        @Override
        public boolean test(T t) {
            try {
                return this.target.contains(t);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e) {
                return false;
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof InPredicate) {
                InPredicate that = (InPredicate)obj;
                return this.target.equals(that.target);
            }
            return false;
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public String toString() {
            return "Predicates.in(" + this.target + ")";
        }
    }

    private static class IsEqualToPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final T target;

        private IsEqualToPredicate(T target) {
            this.target = target;
        }

        @Override
        public boolean test(T t) {
            return this.target.equals(t);
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof IsEqualToPredicate) {
                IsEqualToPredicate that = (IsEqualToPredicate)obj;
                return this.target.equals(that.target);
            }
            return false;
        }

        public String toString() {
            return "Predicates.equalTo(" + this.target + ")";
        }
    }

    private static class NotPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Predicate<T> predicate;

        NotPredicate(Predicate<T> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public boolean test(T t) {
            return !this.predicate.test(t);
        }

        public int hashCode() {
            return ~this.predicate.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof NotPredicate) {
                NotPredicate that = (NotPredicate)obj;
                return this.predicate.equals(that.predicate);
            }
            return false;
        }

        public String toString() {
            return "Predicates.not(" + this.predicate.toString() + ")";
        }
    }

    static enum ObjectPredicate implements Predicate<Object>
    {
        ALWAYS_TRUE{

            @Override
            public boolean test(Object o) {
                return true;
            }

            public String toString() {
                return "Predicates.alwaysTrue()";
            }
        }
        ,
        IS_NULL{

            @Override
            public boolean test(Object o) {
                return o == null;
            }

            public String toString() {
                return "Predicates.isNull()";
            }
        };


        <T> Predicate<T> withNarrowedType() {
            return this;
        }
    }
}

