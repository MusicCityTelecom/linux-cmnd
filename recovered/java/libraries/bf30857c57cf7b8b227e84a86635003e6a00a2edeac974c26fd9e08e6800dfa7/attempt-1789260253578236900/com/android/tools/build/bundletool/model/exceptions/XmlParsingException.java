/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.tools.build.bundletool.model.exceptions.ValidationException;

public class XmlParsingException
extends ValidationException {
    public XmlParsingException(String message) {
        super(message);
    }

    public XmlParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParsingException(Throwable cause) {
        super(cause);
    }
}

