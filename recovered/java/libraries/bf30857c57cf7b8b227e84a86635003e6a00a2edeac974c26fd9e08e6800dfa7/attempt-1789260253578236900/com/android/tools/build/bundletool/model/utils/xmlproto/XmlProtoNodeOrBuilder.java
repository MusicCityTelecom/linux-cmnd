/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementOrBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoException;

abstract class XmlProtoNodeOrBuilder<ElementProtoT extends Resources.XmlElementOrBuilder, ElementWrapperT extends XmlProtoElementOrBuilder<NodeProtoT, ?, ElementProtoT, ElementWrapperT, ?, ?>, NodeProtoT extends Resources.XmlNodeOrBuilder> {
    XmlProtoNodeOrBuilder() {
    }

    protected abstract NodeProtoT getProto();

    protected abstract ElementProtoT getProtoElement();

    protected abstract ElementWrapperT newElement(ElementProtoT var1);

    public final boolean isElement() {
        return this.getProto().getNodeCase().equals(Resources.XmlNode.NodeCase.ELEMENT);
    }

    public final boolean isText() {
        return this.getProto().getNodeCase().equals(Resources.XmlNode.NodeCase.TEXT);
    }

    public final ElementWrapperT getElement() {
        if (!this.isElement()) {
            throw new XmlProtoException("Expected node of type 'element' but found: %s", this.getProto().getNodeCase());
        }
        return this.newElement(this.getProtoElement());
    }

    public final String getText() {
        if (!this.isText()) {
            throw new XmlProtoException("Expected node of type 'text' but found: %s", this.getProto().getNodeCase());
        }
        return this.getProto().getText();
    }

    public String toString() {
        return this.getProto().toString();
    }
}

