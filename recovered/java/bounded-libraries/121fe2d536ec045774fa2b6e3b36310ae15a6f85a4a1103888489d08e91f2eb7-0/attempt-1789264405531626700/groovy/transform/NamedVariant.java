/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.groovy.lang.annotation.Incubating;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

@Incubating
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
@GroovyASTTransformationClass(value={"org.codehaus.groovy.transform.NamedVariantASTTransformation"})
public @interface NamedVariant {
    public String visibilityId() default "<DummyUndefinedMarkerString-DoNotUse>";

    public boolean autoDelegate() default false;

    public boolean coerce() default false;
}

