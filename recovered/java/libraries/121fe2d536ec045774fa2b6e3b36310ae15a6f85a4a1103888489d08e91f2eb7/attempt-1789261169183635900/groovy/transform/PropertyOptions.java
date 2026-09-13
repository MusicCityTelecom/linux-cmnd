/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.transform.options.DefaultPropertyHandler;
import groovy.transform.options.PropertyHandler;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE})
public @interface PropertyOptions {
    public Class<? extends PropertyHandler> propertyHandler() default DefaultPropertyHandler.class;
}

