/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.transform.AnnotationCollectorMode;
import groovy.transform.Undefined;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface AnnotationCollector {
    public String processor() default "org.codehaus.groovy.transform.AnnotationCollectorTransform";

    public AnnotationCollectorMode mode() default AnnotationCollectorMode.DUPLICATE;

    public Class[] value() default {};

    public Class serializeClass() default Undefined.CLASS.class;
}

