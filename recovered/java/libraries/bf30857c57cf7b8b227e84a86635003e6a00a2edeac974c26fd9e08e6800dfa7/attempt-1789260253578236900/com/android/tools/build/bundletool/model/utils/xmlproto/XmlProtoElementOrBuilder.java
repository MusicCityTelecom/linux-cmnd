/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeOrBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNamespace;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNodeOrBuilder;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.MoreCollectors;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class XmlProtoElementOrBuilder<NodeProtoT extends Resources.XmlNodeOrBuilder, NodeWrapperT extends XmlProtoNodeOrBuilder<?, ElementWrapperT, ?>, ElementProtoT extends Resources.XmlElementOrBuilder, ElementWrapperT extends XmlProtoElementOrBuilder<NodeProtoT, ?, ElementProtoT, ElementWrapperT, ?, ?>, AttributeProtoT extends Resources.XmlAttributeOrBuilder, AttributeWrapperT extends XmlProtoAttributeOrBuilder<AttributeProtoT>> {
    XmlProtoElementOrBuilder() {
    }

    public abstract ElementProtoT getProto();

    protected abstract List<AttributeProtoT> getProtoAttributesList();

    protected abstract List<NodeProtoT> getProtoChildrenList();

    protected abstract NodeWrapperT newNode(NodeProtoT var1);

    protected abstract AttributeWrapperT newAttribute(AttributeProtoT var1);

    public final String getName() {
        return this.getProto().getName();
    }

    public final String getNamespaceUri() {
        return this.getProto().getNamespaceUri();
    }

    public final Stream<AttributeWrapperT> getAttributes() {
        return this.getProtoAttributesList().stream().map(this::newAttribute);
    }

    public final Stream<NodeWrapperT> getChildren() {
        return this.getProtoChildrenList().stream().map(this::newNode);
    }

    public final Stream<NodeWrapperT> getChildrenText() {
        return this.getChildren().filter(XmlProtoNodeOrBuilder::isText);
    }

    public final Optional<NodeWrapperT> getChildText() {
        return this.getChildrenText().collect(MoreCollectors.toOptional());
    }

    public final Stream<XmlProtoNamespace> getNamespaceDeclarations() {
        return this.getProto().getNamespaceDeclarationList().stream().map(XmlProtoNamespace::new);
    }

    public final Optional<AttributeWrapperT> getAttribute(String name) {
        return this.getAttribute("", name);
    }

    public final Optional<AttributeWrapperT> getAttribute(String namespaceUri, String name) {
        return this.getAttributes().filter(attr -> attr.getName().equals(name) && attr.getNamespaceUri().equals(namespaceUri)).findFirst();
    }

    @Deprecated
    public final Optional<AttributeWrapperT> getAttributeIgnoringNamespace(String name) {
        return this.getAttributes().filter(attr -> attr.getName().equals(name)).findFirst();
    }

    public final Optional<AttributeWrapperT> getAndroidAttribute(int resourceId) {
        return this.getAttributes().filter(attr -> attr.getResourceId() == resourceId).findFirst();
    }

    public final Stream<ElementWrapperT> getChildrenElements() {
        return this.getChildren().filter(XmlProtoNodeOrBuilder::isElement).map(XmlProtoNodeOrBuilder::getElement);
    }

    public final Stream<ElementWrapperT> getChildrenElements(String name) {
        return this.getChildrenElements("", name);
    }

    public final Stream<ElementWrapperT> getChildrenElements(String namespaceUri, String name) {
        return this.getChildrenElements(el -> el.getName().equals(name) && el.getNamespaceUri().equals(namespaceUri));
    }

    public final Stream<ElementWrapperT> getChildrenElements(Predicate<ElementWrapperT> predicate) {
        return this.getChildren().filter(node -> node.isElement()).map(node -> node.getElement()).filter(predicate);
    }

    public final Optional<ElementWrapperT> getOptionalChildElement(String namespaceUri, String name) {
        List matches = this.getChildrenElements(namespaceUri, name).collect(Collectors.toList());
        if (matches.size() > 1) {
            throw new XmlProtoException("At most one element <%s> with namespace '%s' was expected, but %d were found.", name, namespaceUri, matches.size());
        }
        return matches.isEmpty() ? Optional.empty() : Optional.of(Iterables.getOnlyElement(matches));
    }

    public final Optional<ElementWrapperT> getOptionalChildElement(String name) {
        return this.getOptionalChildElement("", name);
    }

    public final ElementWrapperT getChildElement(String namespaceUri, String name) {
        return (ElementWrapperT)((XmlProtoElementOrBuilder)this.getOptionalChildElement(namespaceUri, name).orElseThrow(() -> new XmlProtoException("One element <%s> with namespace '%s' was expected, but none were found.", name, namespaceUri)));
    }

    public final ElementWrapperT getChildElement(String name) {
        return (ElementWrapperT)((XmlProtoElementOrBuilder)this.getOptionalChildElement("", name).orElseThrow(() -> new XmlProtoException("One element <%s> was expected, but none were found.", name)));
    }

    public String toString() {
        return this.getProto().toString();
    }
}

