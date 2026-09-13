/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.module.jaxb.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Deprecated
public class DomElementJsonDeserializer
extends StdDeserializer<Element> {
    private static final long serialVersionUID = 1L;
    private final DocumentBuilder builder;

    public DomElementJsonDeserializer() {
        super(Element.class);
        try {
            DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
            bf.setNamespaceAware(true);
            bf.setExpandEntityReferences(false);
            this.builder = bf.newDocumentBuilder();
            try {
                bf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            }
            catch (Exception exception) {}
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException();
        }
    }

    public DomElementJsonDeserializer(DocumentBuilder b) {
        super(Element.class);
        this.builder = b;
    }

    @Override
    public Element deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Document document = this.builder.newDocument();
        JsonNode n = (JsonNode)p.readValueAsTree();
        return this.fromNode(p, document, n);
    }

    protected Element fromNode(JsonParser p, Document document, JsonNode jsonNode) throws IOException {
        JsonNode childsNode;
        String name;
        String ns = jsonNode.get("namespace") != null ? jsonNode.get("namespace").asText() : null;
        String string = name = jsonNode.get("name") != null ? jsonNode.get("name").asText() : null;
        if (name == null) {
            throw JsonMappingException.from(p, "No name for DOM element was provided in the JSON object.");
        }
        Element element = document.createElementNS(ns, name);
        JsonNode attributesNode = jsonNode.get("attributes");
        if (attributesNode != null && attributesNode instanceof ArrayNode) {
            Iterator<JsonNode> atts = attributesNode.elements();
            while (atts.hasNext()) {
                String value;
                JsonNode node = atts.next();
                ns = node.get("namespace") != null ? node.get("namespace").asText() : null;
                name = node.get("name") != null ? node.get("name").asText() : null;
                String string2 = value = node.get("$") != null ? node.get("$").asText() : null;
                if (name == null) continue;
                element.setAttributeNS(ns, name, value);
            }
        }
        if ((childsNode = jsonNode.get("children")) != null && childsNode instanceof ArrayNode) {
            Iterator<JsonNode> els = childsNode.elements();
            while (els.hasNext()) {
                String value;
                JsonNode node = els.next();
                name = node.get("name") != null ? node.get("name").asText() : null;
                String string3 = value = node.get("$") != null ? node.get("$").asText() : null;
                if (value != null) {
                    element.appendChild(document.createTextNode(value));
                    continue;
                }
                if (name == null) continue;
                element.appendChild(this.fromNode(p, document, node));
            }
        }
        return element;
    }
}

