/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.xml;

import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNamespace;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.xml.XmlUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XmlProtoToXmlConverter {
    private static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
    private int nextPrefixIndex = 0;
    private final Map<String, Deque<String>> namespaceUriToPrefix = new HashMap<String, Deque<String>>();

    public static Document convert(XmlProtoNode protoXml) {
        return new XmlProtoToXmlConverter().convertInternal(protoXml);
    }

    private Document convertInternal(XmlProtoNode protoNode) {
        Document document;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        }
        catch (ParserConfigurationException e2) {
            throw new IllegalStateException(e2);
        }
        document.appendChild(this.createXmlNode(protoNode, document));
        return document;
    }

    private Node createXmlNode(XmlProtoNode protoNode, Document xmlFactory) {
        if (protoNode.isElement()) {
            Element xmlElement;
            XmlProtoElement protoElement = (XmlProtoElement)protoNode.getElement();
            String namespaceUri = protoElement.getNamespaceUri();
            if (namespaceUri.isEmpty()) {
                xmlElement = xmlFactory.createElement(protoElement.getName());
            } else {
                String prefix = this.getPrefixForNamespace(namespaceUri);
                xmlElement = xmlFactory.createElementNS(namespaceUri, prefix + ":" + protoElement.getName());
            }
            ImmutableList<XmlProtoNamespace> namespaces = protoElement.getNamespaceDeclarations().collect(ImmutableList.toImmutableList());
            for (XmlProtoNamespace namespace2 : namespaces) {
                String prefix = namespace2.getPrefix();
                Deque prefixes = this.namespaceUriToPrefix.computeIfAbsent(namespace2.getUri(), k2 -> new ArrayDeque());
                prefixes.addLast(prefix);
                xmlElement.setAttributeNS(XMLNS_NAMESPACE_URI, prefix.isEmpty() ? "xmlns" : "xmlns:" + prefix, namespace2.getUri());
            }
            for (XmlProtoAttribute protoAttribute : protoElement.getAttributes().collect(Collectors.toList())) {
                String attrNamespaceUri = protoAttribute.getNamespaceUri();
                if (attrNamespaceUri.isEmpty()) {
                    xmlElement.setAttribute(XmlProtoToXmlConverter.getAttributeTagName(protoAttribute), protoAttribute.getDebugString());
                    continue;
                }
                String prefix = this.getPrefixForNamespace(attrNamespaceUri);
                xmlElement.setAttributeNS(attrNamespaceUri, prefix + ":" + XmlProtoToXmlConverter.getAttributeTagName(protoAttribute), protoAttribute.getDebugString());
            }
            for (XmlProtoNode child : protoElement.getChildren().collect(ImmutableList.toImmutableList())) {
                xmlElement.appendChild(this.createXmlNode(child, xmlFactory));
            }
            namespaces.forEach(namespace -> this.namespaceUriToPrefix.get(namespace.getUri()).removeLast());
            return xmlElement;
        }
        return xmlFactory.createTextNode(protoNode.getText());
    }

    private static String getAttributeTagName(XmlProtoAttribute protoAttribute) {
        if (!protoAttribute.getName().isEmpty()) {
            return protoAttribute.getName();
        }
        if (protoAttribute.getResourceId() == 0) {
            return "_unknown_";
        }
        return String.format("_0x%08x_", protoAttribute.getResourceId());
    }

    private String getPrefixForNamespace(String attrNamespaceUri) {
        Deque prefixes = this.namespaceUriToPrefix.computeIfAbsent(attrNamespaceUri, uri -> XmlProtoToXmlConverter.createDequeWithElement(this.getCommonPrefix((String)uri).orElseGet(() -> this.createNewPrefix())));
        return (String)prefixes.peekLast();
    }

    private Optional<String> getCommonPrefix(String uri) {
        String prefix = (String)XmlUtils.COMMON_NAMESPACE_PREFIXES.get(uri);
        if (prefix == null || this.isNamespacePrefixInScope(prefix)) {
            return Optional.empty();
        }
        return Optional.of(prefix);
    }

    private boolean isNamespacePrefixInScope(String prefix) {
        return this.namespaceUriToPrefix.values().stream().flatMap(queue -> Streams.stream(queue.iterator())).anyMatch(Predicate.isEqual(prefix));
    }

    private String createNewPrefix() {
        return String.format("_unknown%d_", this.nextPrefixIndex++);
    }

    private static Deque<String> createDequeWithElement(String element) {
        ArrayDeque<String> queue = new ArrayDeque<String>();
        queue.add(element);
        return queue;
    }

    private XmlProtoToXmlConverter() {
    }
}

