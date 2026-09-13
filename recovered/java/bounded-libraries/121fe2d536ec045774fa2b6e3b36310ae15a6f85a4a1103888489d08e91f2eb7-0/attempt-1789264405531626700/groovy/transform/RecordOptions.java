/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.transform.RecordTypeMode;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE})
public @interface RecordOptions {
    public RecordTypeMode mode() default RecordTypeMode.AUTO;

    public boolean getAt() default true;

    public boolean toList() default true;

    public boolean toMap() default true;

    public boolean size() default true;

    public boolean copyWith() default false;

    public boolean components() default false;
}

