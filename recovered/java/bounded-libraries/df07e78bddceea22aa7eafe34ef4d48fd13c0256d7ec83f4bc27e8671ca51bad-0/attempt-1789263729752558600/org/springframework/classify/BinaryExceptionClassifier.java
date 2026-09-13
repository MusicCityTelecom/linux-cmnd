/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.classify;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.classify.BinaryExceptionClassifierBuilder;
import org.springframework.classify.SubclassClassifier;

public class BinaryExceptionClassifier
extends SubclassClassifier<Throwable, Boolean> {
    private boolean traverseCauses;

    public static BinaryExceptionClassifierBuilder builder() {
        return new BinaryExceptionClassifierBuilder();
    }

    public static BinaryExceptionClassifier defaultClassifier() {
        return new BinaryExceptionClassifier(Collections.singletonMap(Exception.class, true), false);
    }

    public BinaryExceptionClassifier(boolean defaultValue) {
        super(defaultValue);
    }

    public BinaryExceptionClassifier(Collection<Class<? extends Throwable>> exceptionClasses, boolean value) {
        this(!value);
        if (exceptionClasses != null) {
            HashMap<Class<? extends Throwable>, Boolean> map = new HashMap<Class<? extends Throwable>, Boolean>();
            for (Class<? extends Throwable> type : exceptionClasses) {
                map.put(type, (Boolean)this.getDefault() == false);
            }
            this.setTypeMap(map);
        }
    }

    public BinaryExceptionClassifier(Collection<Class<? extends Throwable>> exceptionClasses) {
        this(exceptionClasses, true);
    }

    public BinaryExceptionClassifier(Map<Class<? extends Throwable>, Boolean> typeMap) {
        this(typeMap, false);
    }

    public BinaryExceptionClassifier(Map<Class<? extends Throwable>, Boolean> typeMap, boolean defaultValue) {
        super(typeMap, defaultValue);
    }

    public BinaryExceptionClassifier(Map<Class<? extends Throwable>, Boolean> typeMap, boolean defaultValue, boolean traverseCauses) {
        super(typeMap, defaultValue);
        this.traverseCauses = traverseCauses;
    }

    public void setTraverseCauses(boolean traverseCauses) {
        this.traverseCauses = traverseCauses;
    }

    @Override
    public Boolean classify(Throwable classifiable) {
        Boolean classified = (Boolean)super.classify(classifiable);
        if (!this.traverseCauses) {
            return classified;
        }
        if (classified.equals(this.getDefault())) {
            Throwable cause = classifiable;
            do {
                if (this.getClassified().containsKey(cause.getClass())) {
                    return classified;
                }
                cause = cause.getCause();
                classified = (Boolean)super.classify(cause);
            } while (cause != null && classified.equals(this.getDefault()));
        }
        return classified;
    }
}

