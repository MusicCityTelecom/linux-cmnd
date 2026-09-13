/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeOrBuilder;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;

@Immutable
public final class XmlProtoAttribute
extends XmlProtoAttributeOrBuilder<Resources.XmlAttribute> {
    private final Resources.XmlAttribute attribute;

    XmlProtoAttribute(Resources.XmlAttribute attribute) {
        this.attribute = Preconditions.checkNotNull(attribute);
    }

    public static XmlProtoAttribute create(String namespaceUri, String name) {
        return new XmlProtoAttribute(Resources.XmlAttribute.newBuilder().setName(name).setNamespaceUri(namespaceUri).build());
    }

    public static XmlProtoAttribute create(String name) {
        return XmlProtoAttribute.create("", name);
    }

    public static XmlProtoAttribute createAndroidAttribute(String name, int attributeResId) {
        return new XmlProtoAttribute(Resources.XmlAttribute.newBuilder().setName(name).setNamespaceUri("http://schemas.android.com/apk/res/android").setResourceId(attributeResId).build());
    }

    @Override
    public Resources.XmlAttribute getProto() {
        return this.attribute;
    }

    public XmlProtoAttributeBuilder toBuilder() {
        return new XmlProtoAttributeBuilder(this.attribute.toBuilder());
    }

    public boolean equals(Object o3) {
        if (!(o3 instanceof XmlProtoAttribute)) {
            return false;
        }
        return this.attribute.equals(((XmlProtoAttribute)o3).getProto());
    }

    public int hashCode() {
        return this.attribute.hashCode();
    }
}

