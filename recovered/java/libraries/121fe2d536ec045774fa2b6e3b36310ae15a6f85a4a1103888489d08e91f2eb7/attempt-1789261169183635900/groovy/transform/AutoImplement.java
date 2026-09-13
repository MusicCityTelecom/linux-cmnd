/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.transform.Undefined;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE})
@GroovyASTTransformationClass(value={"org.codehaus.groovy.transform.AutoImplementASTTransformation"})
public @interface AutoImplement {
    public Class<? extends RuntimeException> exception() default Undefined.EXCEPTION.class;

    public String message() default "<DummyUndefinedMarkerString-DoNotUse>";

    public Class code() default Undefined.CLASS.class;
}

