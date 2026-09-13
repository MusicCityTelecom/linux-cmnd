/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.xml;

import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNamespace;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.xml.XmlUtils;
import com.google.common.collect.ImmutableMap;
import java.util.Iterator;
import java.util.stream.Stream;
import javax.xml.namespace.NamespaceContext;

public final class XmlNamespaceContext
implements NamespaceContext {
    private final ImmutableMap<String, String> prefixToNamespaceUri;

    public XmlNamespaceContext(XmlProtoNode manifestProto) {
        this.prefixToNamespaceUri = XmlNamespaceContext.collectNamespaces((XmlProtoElement)manifestProto.getElement()).map(xmlProto -> XmlProtoNamespace.create(xmlProto.getPrefix(), xmlProto.getUri())).distinct().collect(ImmutableMap.toImmutableMap(XmlProtoNamespace::getPrefix, XmlProtoNamespace::getUri));
    }

    private static Stream<XmlProtoNamespace> collectNamespaces(XmlProtoElement protoElement) {
        return Stream.concat(protoElement.getNamespaceDeclarations(), protoElement.getChildrenElements().flatMap(XmlNamespaceContext::collectNamespaces));
    }

    @Override
    public String getNamespaceURI(String prefix) {
        String namespaceUri = this.prefixToNamespaceUri.get(prefix);
        if (namespaceUri == null) {
            namespaceUri = (String)((ImmutableMap)((Object)XmlUtils.COMMON_NAMESPACE_PREFIXES.inverse())).get(prefix);
        }
        if (namespaceUri == null) {
            throw ValidationException.builder().withMessage("Namespace prefix '%s' not found.", prefix).build();
        }
        return namespaceUri;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        throw new UnsupportedOperationException();
    }
}

