/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNodeOrBuilder;
import com.google.common.base.Preconditions;

public final class XmlProtoNodeBuilder
extends XmlProtoNodeOrBuilder<Resources.XmlElement.Builder, XmlProtoElementBuilder, Resources.XmlNode.Builder> {
    private final Resources.XmlNode.Builder node;

    public XmlProtoNodeBuilder(Resources.XmlNode.Builder node) {
        this.node = Preconditions.checkNotNull(node);
    }

    public static XmlProtoNodeBuilder createElementNode(XmlProtoElementBuilder element) {
        return new XmlProtoNodeBuilder(Resources.XmlNode.newBuilder().setElement(element.getProto()));
    }

    public static XmlProtoNodeBuilder createTextNode(String text) {
        return new XmlProtoNodeBuilder(Resources.XmlNode.newBuilder().setText(text));
    }

    @Override
    public Resources.XmlNode.Builder getProto() {
        return this.node;
    }

    public XmlProtoNode build() {
        return new XmlProtoNode(this.node.build());
    }

    @Override
    protected Resources.XmlElement.Builder getProtoElement() {
        return this.node.getElementBuilder();
    }

    @Override
    protected XmlProtoElementBuilder newElement(Resources.XmlElement.Builder element) {
        return new XmlProtoElementBuilder(element);
    }

    public XmlProtoNodeBuilder setElement(XmlProtoElementBuilder newElement) {
        this.node.setElement(newElement.getProto());
        return this;
    }

    public XmlProtoNodeBuilder setText(String newText) {
        this.node.setText(newText);
        return this;
    }
}

