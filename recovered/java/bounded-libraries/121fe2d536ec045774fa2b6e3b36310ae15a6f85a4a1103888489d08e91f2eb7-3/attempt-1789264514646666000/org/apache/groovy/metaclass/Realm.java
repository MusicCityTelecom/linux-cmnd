/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.metaclass;

import java.lang.invoke.SwitchPoint;
import java.util.Objects;
import org.apache.groovy.internal.metaclass.MetaClassConstant;
import org.apache.groovy.internal.util.Function;
import org.apache.groovy.internal.util.ReevaluatingReference;
import org.apache.groovy.internal.util.Supplier;
import org.apache.groovy.lang.annotation.Incubating;
import org.apache.groovy.metaclass.MetaClass;

@Incubating
public final class Realm {
    private static final Realm ROOT = new Realm("ROOT", null);
    private final String name;
    private final Realm parent;
    private final ClassValue<MetaClassConstant<?>> cv = new ClassValue<MetaClassConstant<?>>(){

        @Override
        protected MetaClassConstant<?> computeValue(Class<?> type) {
            return new MetaClassConstant(type);
        }
    };

    private Realm(String name, Realm parent) {
        this.name = name;
        this.parent = parent;
    }

    public static Realm newRealm(String name, Realm parent) {
        Objects.requireNonNull(name, "missing realm name");
        if (parent == null) {
            return new Realm(name, ROOT);
        }
        return new Realm(name, parent);
    }

    public String toString() {
        return "Realm{name='" + this.name + '\'' + ", parent=" + this.parent + '}';
    }

    public <T> MetaClass<T> getMetaClass(Class<T> theClass) {
        Supplier<MetaClassConstant> valueSupplier = () -> this.cv.get(theClass);
        Function<MetaClassConstant, SwitchPoint> validationSupplier = MetaClassConstant::getSwitchPoint;
        ReevaluatingReference ref = new ReevaluatingReference(MetaClassConstant.class, valueSupplier, validationSupplier);
        return new MetaClass(ref);
    }
}

