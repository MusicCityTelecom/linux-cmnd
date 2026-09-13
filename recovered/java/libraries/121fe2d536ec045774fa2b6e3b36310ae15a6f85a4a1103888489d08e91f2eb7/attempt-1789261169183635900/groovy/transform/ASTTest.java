/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.lang.Closure;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.PACKAGE, ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@GroovyASTTransformationClass(value={"org.codehaus.groovy.transform.ASTTestTransformation"})
public @interface ASTTest {
    public CompilePhase phase() default CompilePhase.SEMANTIC_ANALYSIS;

    public Class<? extends Closure<?>> value();
}

