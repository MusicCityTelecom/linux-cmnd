/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoException;

public class UnexpectedAttributeTypeException
extends XmlProtoException {
    private final Resources.XmlAttributeOrBuilder attribute;
    private final String expectedType;

    UnexpectedAttributeTypeException(Resources.XmlAttributeOrBuilder attribute, String expectedType) {
        super(String.format("Attribute '%s' expected to have type '%s' but found:\n %s", attribute.getName(), expectedType, attribute), new Object[0]);
        this.attribute = attribute;
        this.expectedType = expectedType;
    }

    public Resources.XmlAttributeOrBuilder getAttribute() {
        return this.attribute;
    }

    public String getExpectedType() {
        return this.expectedType;
    }
}

