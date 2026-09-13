/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.module.jaxb.ser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Deprecated
public class DomElementJsonSerializer
extends StdSerializer<Element> {
    private static final long serialVersionUID = 1L;

    public DomElementJsonSerializer() {
        super(Element.class);
    }

    @Override
    public void serialize(Element value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        NodeList children;
        NamedNodeMap attributes;
        jgen.writeStartObject();
        jgen.writeStringField("name", value.getTagName());
        if (value.getNamespaceURI() != null) {
            jgen.writeStringField("namespace", value.getNamespaceURI());
        }
        if ((attributes = value.getAttributes()) != null && attributes.getLength() > 0) {
            jgen.writeArrayFieldStart("attributes");
            for (int i = 0; i < attributes.getLength(); ++i) {
                Attr attribute = (Attr)attributes.item(i);
                jgen.writeStartObject();
                jgen.writeStringField("$", attribute.getValue());
                jgen.writeStringField("name", attribute.getName());
                String ns = attribute.getNamespaceURI();
                if (ns != null) {
                    jgen.writeStringField("namespace", ns);
                }
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
        if ((children = value.getChildNodes()) != null && children.getLength() > 0) {
            jgen.writeArrayFieldStart("children");
            block5: for (int i = 0; i < children.getLength(); ++i) {
                Node child = children.item(i);
                switch (child.getNodeType()) {
                    case 3: 
                    case 4: {
                        jgen.writeStartObject();
                        jgen.writeStringField("$", child.getNodeValue());
                        jgen.writeEndObject();
                        continue block5;
                    }
                    case 1: {
                        this.serialize((Element)child, jgen, provider);
                    }
                }
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        if (visitor != null) {
            visitor.expectStringFormat(typeHint);
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return this.createSchemaNode("string", true);
    }
}

