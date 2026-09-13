/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeOrBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementOrBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNodeBuilder;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class XmlProtoElementBuilder
extends XmlProtoElementOrBuilder<Resources.XmlNode.Builder, XmlProtoNodeBuilder, Resources.XmlElement.Builder, XmlProtoElementBuilder, Resources.XmlAttribute.Builder, XmlProtoAttributeBuilder> {
    private final Resources.XmlElement.Builder element;

    public static XmlProtoElementBuilder create(String namespaceUri, String name) {
        return new XmlProtoElementBuilder(Resources.XmlElement.newBuilder().setNamespaceUri(namespaceUri).setName(name));
    }

    public static XmlProtoElementBuilder create(String name) {
        return XmlProtoElementBuilder.create("", name);
    }

    public XmlProtoElementBuilder(Resources.XmlElement.Builder element) {
        this.element = Preconditions.checkNotNull(element);
    }

    public XmlProtoElement build() {
        return new XmlProtoElement(this.element.build());
    }

    @Override
    public Resources.XmlElement.Builder getProto() {
        return this.element;
    }

    @Override
    protected List<Resources.XmlAttribute.Builder> getProtoAttributesList() {
        return this.element.getAttributeBuilderList();
    }

    @Override
    protected List<Resources.XmlNode.Builder> getProtoChildrenList() {
        return this.element.getChildBuilderList();
    }

    @Override
    protected XmlProtoNodeBuilder newNode(Resources.XmlNode.Builder node) {
        return new XmlProtoNodeBuilder(node);
    }

    @Override
    protected XmlProtoAttributeBuilder newAttribute(Resources.XmlAttribute.Builder attribute) {
        return new XmlProtoAttributeBuilder(attribute);
    }

    public XmlProtoElementBuilder addAttribute(XmlProtoAttributeBuilder newAttribute) {
        this.element.addAttribute(newAttribute.getProto());
        return this;
    }

    public XmlProtoAttributeBuilder getOrCreateAttribute(String name) {
        return this.getOrCreateAttribute("", name);
    }

    public XmlProtoAttributeBuilder getOrCreateAttribute(String namespaceUri, String name) {
        return this.getOrCreateAttributeInternal(attr -> attr.getName().equals(name) && attr.getNamespaceUri().equals(namespaceUri), () -> Resources.XmlAttribute.newBuilder().setName(name).setNamespaceUri(namespaceUri));
    }

    public XmlProtoAttributeBuilder getOrCreateAndroidAttribute(String name, int resourceId) {
        return this.getOrCreateAttributeInternal(attr -> attr.getName().equals(name) && attr.getNamespaceUri().equals("http://schemas.android.com/apk/res/android") && attr.getResourceId() == resourceId, () -> Resources.XmlAttribute.newBuilder().setName(name).setNamespaceUri("http://schemas.android.com/apk/res/android").setResourceId(resourceId));
    }

    private XmlProtoAttributeBuilder getOrCreateAttributeInternal(Predicate<XmlProtoAttributeOrBuilder<?>> attributePredicate, Supplier<Resources.XmlAttribute.Builder> attributeFactory) {
        return this.getAttributes().filter(attributePredicate).findFirst().orElseGet(() -> {
            this.element.addAttribute((Resources.XmlAttribute.Builder)attributeFactory.get());
            return new XmlProtoAttributeBuilder(this.element.getAttributeBuilder(this.element.getAttributeCount() - 1));
        });
    }

    public XmlProtoElementBuilder removeAttribute(String namespaceUri, String name) {
        return this.removeAttributeInternal(attribute -> attribute.getName().equals(name) && attribute.getNamespaceUri().equals(namespaceUri));
    }

    public XmlProtoElementBuilder removeAndroidAttribute(int resourceId) {
        return this.removeAttributeInternal(attribute -> attribute.getResourceId() == resourceId && attribute.getNamespaceUri().equals("http://schemas.android.com/apk/res/android"));
    }

    private XmlProtoElementBuilder removeAttributeInternal(Predicate<Resources.XmlAttribute> attributePredicate) {
        for (int i2 = 0; i2 < this.element.getAttributeCount(); ++i2) {
            if (!attributePredicate.test(this.element.getAttribute(i2))) continue;
            this.element.removeAttribute(i2);
            break;
        }
        return this;
    }

    public XmlProtoElementBuilder getOrCreateChildElement(String name) {
        return this.getOrCreateChildElement("", name);
    }

    public XmlProtoElementBuilder getOrCreateChildElement(String namespaceUri, String name) {
        return this.getOptionalChildElement(namespaceUri, name).orElseGet(() -> {
            this.element.addChild(Resources.XmlNode.newBuilder().setElement(Resources.XmlElement.newBuilder().setName(name).setNamespaceUri(namespaceUri)));
            return new XmlProtoElementBuilder(this.element.getChildBuilder(this.element.getChildCount() - 1).getElementBuilder());
        });
    }

    public XmlProtoElementBuilder addChildElement(XmlProtoElementBuilder newElement) {
        this.element.addChild(Resources.XmlNode.newBuilder().setElement(newElement.getProto()));
        return this;
    }

    public XmlProtoElementBuilder addChildText(String text) {
        this.element.addChild(Resources.XmlNode.newBuilder().setText(text));
        return this;
    }

    public XmlProtoElementBuilder addNamespaceDeclaration(String prefix, String uri) {
        this.element.addNamespaceDeclaration(Resources.XmlNamespace.newBuilder().setPrefix(prefix).setUri(uri));
        return this;
    }

    public XmlProtoElementBuilder removeChildrenElementsIf(Predicate<XmlProtoNodeBuilder> filter) {
        ImmutableList keptChildren = this.getChildren().filter(filter.negate()).map(builder -> builder.build().getProto()).collect(ImmutableList.toImmutableList());
        if (this.getProtoChildrenList().size() != keptChildren.size()) {
            this.element.clearChild().addAllChild(keptChildren);
        }
        return this;
    }
}

