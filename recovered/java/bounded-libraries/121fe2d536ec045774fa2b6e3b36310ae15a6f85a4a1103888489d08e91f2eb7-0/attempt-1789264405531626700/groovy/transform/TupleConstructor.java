/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.transform.DefaultsMode;
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
@GroovyASTTransformationClass(value={"org.codehaus.groovy.transform.TupleConstructorASTTransformation"})
public @interface TupleConstructor {
    public String[] excludes() default {};

    public String[] includes() default {"<DummyUndefinedMarkerString-DoNotUse>"};

    public boolean includeProperties() default true;

    public boolean includeFields() default false;

    public boolean includeSuperProperties() default false;

    public boolean includeSuperFields() default false;

    public boolean callSuper() default false;

    public boolean force() default false;

    public boolean defaults() default true;

    public DefaultsMode defaultsMode() default DefaultsMode.ON;

    public boolean useSetters() default false;

    public boolean allNames() default false;

    public boolean allProperties() default false;

    public boolean namedVariant() default false;

    public String visibilityId() default "<DummyUndefinedMarkerString-DoNotUse>";

    public Class pre() default Undefined.CLASS.class;

    public Class post() default Undefined.CLASS.class;
}

