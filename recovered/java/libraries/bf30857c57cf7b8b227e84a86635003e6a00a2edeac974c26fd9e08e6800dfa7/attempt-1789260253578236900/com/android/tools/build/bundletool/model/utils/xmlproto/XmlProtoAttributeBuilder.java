/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeOrBuilder;
import com.google.common.base.Preconditions;

public final class XmlProtoAttributeBuilder
extends XmlProtoAttributeOrBuilder<Resources.XmlAttribute.Builder> {
    private final Resources.XmlAttribute.Builder attribute;

    XmlProtoAttributeBuilder(Resources.XmlAttribute.Builder attribute) {
        this.attribute = Preconditions.checkNotNull(attribute);
    }

    public static XmlProtoAttributeBuilder create(String namespaceUri, String name) {
        return new XmlProtoAttributeBuilder(Resources.XmlAttribute.newBuilder().setName(name).setNamespaceUri(namespaceUri));
    }

    public static XmlProtoAttributeBuilder create(String name) {
        return XmlProtoAttributeBuilder.create("", name);
    }

    public static XmlProtoAttributeBuilder createAndroidAttribute(String name, int attributeResId) {
        return new XmlProtoAttributeBuilder(Resources.XmlAttribute.newBuilder().setName(name).setNamespaceUri("http://schemas.android.com/apk/res/android").setResourceId(attributeResId));
    }

    @Override
    public Resources.XmlAttribute.Builder getProto() {
        return this.attribute;
    }

    public XmlProtoAttribute build() {
        return new XmlProtoAttribute(this.attribute.build());
    }

    public XmlProtoAttributeBuilder setValueAsBoolean(boolean value) {
        this.attribute.clearValue();
        this.attribute.setCompiledItem(Resources.Item.newBuilder().setPrim(Resources.Primitive.newBuilder().setBooleanValue(value)));
        return this;
    }

    public XmlProtoAttributeBuilder setValueAsRefId(int refId) {
        this.attribute.clearValue();
        this.attribute.setCompiledItem(Resources.Item.newBuilder().setRef(Resources.Reference.newBuilder().setId(refId)));
        return this;
    }

    public XmlProtoAttributeBuilder setValueAsRefId(int refId, String name) {
        this.attribute.clearValue();
        this.attribute.setCompiledItem(Resources.Item.newBuilder().setRef(Resources.Reference.newBuilder().setId(refId).setName(name)));
        return this;
    }

    public XmlProtoAttributeBuilder setValueAsDecimalInteger(int value) {
        this.attribute.setValue(String.valueOf(value));
        this.attribute.setCompiledItem(Resources.Item.newBuilder().setPrim(Resources.Primitive.newBuilder().setIntDecimalValue(value)));
        return this;
    }

    public XmlProtoAttributeBuilder setValueAsHexInteger(int value) {
        this.attribute.setValue(String.format("0x%08x", value));
        this.attribute.setCompiledItem(Resources.Item.newBuilder().setPrim(Resources.Primitive.newBuilder().setIntHexadecimalValue(value)));
        return this;
    }

    public XmlProtoAttributeBuilder setValueAsString(String value) {
        this.attribute.setValue(value);
        this.attribute.setCompiledItem(Resources.Item.newBuilder().setStr(Resources.String.newBuilder().setValue(value)));
        return this;
    }

    public XmlProtoAttributeBuilder setResourceId(int resourceId) {
        this.attribute.setResourceId(resourceId);
        return this;
    }
}

