/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementOrBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.util.List;

@Immutable
public final class XmlProtoElement
extends XmlProtoElementOrBuilder<Resources.XmlNode, XmlProtoNode, Resources.XmlElement, XmlProtoElement, Resources.XmlAttribute, XmlProtoAttribute> {
    private final Resources.XmlElement element;

    public static XmlProtoElement create(String namespaceUri, String name) {
        return new XmlProtoElement(Resources.XmlElement.newBuilder().setNamespaceUri(namespaceUri).setName(name).build());
    }

    public static XmlProtoElement create(String name) {
        return XmlProtoElement.create("", name);
    }

    public XmlProtoElement(Resources.XmlElement element) {
        this.element = Preconditions.checkNotNull(element);
    }

    public XmlProtoElementBuilder toBuilder() {
        return new XmlProtoElementBuilder(this.element.toBuilder());
    }

    @Override
    public Resources.XmlElement getProto() {
        return this.element;
    }

    @Override
    protected List<Resources.XmlAttribute> getProtoAttributesList() {
        return this.element.getAttributeList();
    }

    @Override
    protected List<Resources.XmlNode> getProtoChildrenList() {
        return this.element.getChildList();
    }

    @Override
    protected XmlProtoNode newNode(Resources.XmlNode node) {
        return new XmlProtoNode(node);
    }

    @Override
    protected XmlProtoAttribute newAttribute(Resources.XmlAttribute attribute) {
        return new XmlProtoAttribute(attribute);
    }

    public boolean equals(Object o3) {
        if (!(o3 instanceof XmlProtoElement)) {
            return false;
        }
        return this.element.equals(((XmlProtoElement)o3).getProto());
    }

    public int hashCode() {
        return this.element.hashCode();
    }
}

