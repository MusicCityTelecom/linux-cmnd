/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.Dependents;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface RuleDependency {
    public Class<? extends Recognizer<?, ?>> recognizer();

    public int rule();

    public int version();

    public Dependents[] dependents() default {Dependents.SELF, Dependents.PARENTS};
}

