/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.fieldvalues;

import java.util.Collections;
import java.util.Map;
import javax.lang.model.element.TypeElement;

@FunctionalInterface
public interface FieldValuesParser {
    public static final FieldValuesParser NONE = element -> Collections.emptyMap();

    public Map<String, Object> getFieldValues(TypeElement var1) throws Exception;
}

