/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.type.MapLikeType
 *  com.fasterxml.jackson.databind.type.ReferenceType
 *  com.fasterxml.jackson.databind.type.TypeBindings
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  com.fasterxml.jackson.databind.type.TypeModifier
 *  com.google.common.base.Optional
 *  com.google.common.collect.Multimap
 */
package com.fasterxml.jackson.datatype.guava;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.google.common.base.Optional;
import com.google.common.collect.Multimap;
import java.io.Serializable;
import java.lang.reflect.Type;

public class GuavaTypeModifier
extends TypeModifier
implements Serializable {
    static final long serialVersionUID = 1L;

    public JavaType modifyType(JavaType type, Type jdkType, TypeBindings bindings, TypeFactory typeFactory) {
        if (type.isReferenceType() || type.isContainerType()) {
            return type;
        }
        Class raw = type.getRawClass();
        if (raw == Multimap.class) {
            return MapLikeType.upgradeFrom((JavaType)type, (JavaType)type.containedTypeOrUnknown(0), (JavaType)type.containedTypeOrUnknown(1));
        }
        if (raw == Optional.class) {
            return ReferenceType.upgradeFrom((JavaType)type, (JavaType)type.containedTypeOrUnknown(0));
        }
        return type;
    }
}

