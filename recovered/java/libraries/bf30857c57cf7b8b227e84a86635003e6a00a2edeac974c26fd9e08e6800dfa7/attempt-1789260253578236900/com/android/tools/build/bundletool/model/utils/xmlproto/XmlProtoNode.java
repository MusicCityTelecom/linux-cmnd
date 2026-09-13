/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNodeBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNodeOrBuilder;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;

@Immutable
public final class XmlProtoNode
extends XmlProtoNodeOrBuilder<Resources.XmlElement, XmlProtoElement, Resources.XmlNode> {
    private final Resources.XmlNode node;

    public XmlProtoNode(Resources.XmlNode node) {
        this.node = Preconditions.checkNotNull(node);
    }

    public static XmlProtoNode createElementNode(XmlProtoElement element) {
        return new XmlProtoNode(Resources.XmlNode.newBuilder().setElement(element.getProto()).build());
    }

    public static XmlProtoNode createTextNode(String text) {
        return new XmlProtoNode(Resources.XmlNode.newBuilder().setText(text).build());
    }

    public XmlProtoNodeBuilder toBuilder() {
        return new XmlProtoNodeBuilder(this.node.toBuilder());
    }

    @Override
    public Resources.XmlNode getProto() {
        return this.node;
    }

    @Override
    protected Resources.XmlElement getProtoElement() {
        return this.node.getElement();
    }

    @Override
    protected XmlProtoElement newElement(Resources.XmlElement element) {
        return new XmlProtoElement(element);
    }

    public boolean equals(Object o3) {
        if (!(o3 instanceof XmlProtoNode)) {
            return false;
        }
        return this.node.equals(((XmlProtoNode)o3).getProto());
    }

    public int hashCode() {
        return this.node.hashCode();
    }
}

