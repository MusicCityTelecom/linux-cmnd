/*
 * Decompiled with CFR 0.152.
 */
package groovy.cli;

import groovy.transform.Undefined;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.FIELD})
public @interface Option {
    public String description() default "";

    public String shortName() default "";

    public String longName() default "";

    public String valueSeparator() default "";

    public boolean optionalArg() default false;

    public int numberOfArguments() default 1;

    public String numberOfArgumentsString() default "";

    public String defaultValue() default "";

    public Class convert() default Undefined.CLASS.class;
}

