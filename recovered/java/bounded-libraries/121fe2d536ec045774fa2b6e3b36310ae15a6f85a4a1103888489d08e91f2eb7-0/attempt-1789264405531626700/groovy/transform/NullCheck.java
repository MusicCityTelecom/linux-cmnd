/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@GroovyASTTransformationClass(value={"org.codehaus.groovy.transform.NullCheckASTTransformation"})
public @interface NullCheck {
    public boolean includeGenerated() default false;
}

