/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.tools.build.bundletool.model.exceptions.ValidationException;

public class XmlProtoException
extends ValidationException {
    XmlProtoException(String message, Object ... args) {
        super(String.format(message, args));
    }
}

