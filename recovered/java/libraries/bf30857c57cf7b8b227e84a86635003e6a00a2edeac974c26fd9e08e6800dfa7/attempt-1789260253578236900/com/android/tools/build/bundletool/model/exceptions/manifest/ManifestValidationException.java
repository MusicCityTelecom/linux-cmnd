/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions.manifest;

import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;

public abstract class ManifestValidationException
extends ValidationException {
    @FormatMethod
    protected ManifestValidationException(@FormatString String message, Object ... args) {
        super(String.format(Preconditions.checkNotNull(message), args));
    }
}

