/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.core.TSFBuilder
 */
package com.fasterxml.jackson.dataformat.javaprop;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;

public class JavaPropsFactoryBuilder
extends TSFBuilder<JavaPropsFactory, JavaPropsFactoryBuilder> {
    public JavaPropsFactoryBuilder() {
    }

    public JavaPropsFactoryBuilder(JavaPropsFactory base) {
        super((JsonFactory)base);
    }

    public JavaPropsFactory build() {
        return new JavaPropsFactory(this);
    }
}

