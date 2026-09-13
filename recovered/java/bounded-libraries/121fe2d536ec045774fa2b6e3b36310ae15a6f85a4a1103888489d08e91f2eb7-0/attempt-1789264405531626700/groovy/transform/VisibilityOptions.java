/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.transform.options.Visibility;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface VisibilityOptions {
    public Visibility value() default Visibility.UNDEFINED;

    public String id() default "<DummyUndefinedMarkerString-DoNotUse>";

    public Visibility type() default Visibility.UNDEFINED;

    public Visibility method() default Visibility.UNDEFINED;

    public Visibility constructor() default Visibility.UNDEFINED;
}

