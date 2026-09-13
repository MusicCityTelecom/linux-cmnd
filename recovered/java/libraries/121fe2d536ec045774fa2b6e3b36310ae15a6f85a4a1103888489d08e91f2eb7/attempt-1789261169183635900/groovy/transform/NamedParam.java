/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.transform.NamedParams;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.PARAMETER})
@Repeatable(value=NamedParams.class)
public @interface NamedParam {
    public String value() default "<DummyUndefinedMarkerString-DoNotUse>";

    public Class type() default Object.class;

    public boolean required() default false;
}

