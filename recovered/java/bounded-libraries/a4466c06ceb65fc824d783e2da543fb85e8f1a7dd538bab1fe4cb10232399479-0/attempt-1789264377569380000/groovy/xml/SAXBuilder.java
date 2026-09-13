/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Tuple3
 *  groovy.namespace.QName
 *  groovy.util.BuilderSupport
 */
package groovy.xml;

import groovy.lang.Tuple3;
import groovy.namespace.QName;
import groovy.util.BuilderSupport;
import java.util.Iterator;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class SAXBuilder
extends BuilderSupport {
    private final ContentHandler handler;
    private Attributes emptyAttributes = new AttributesImpl();

    public SAXBuilder(ContentHandler handler) {
        this.handler = handler;
    }

    protected void setParent(Object parent, Object child) {
    }

    protected Object createNode(Object name) {
        this.doStartElement(name, this.emptyAttributes);
        return name;
    }

    protected Object createNode(Object name, Object value) {
        this.doStartElement(name, this.emptyAttributes);
        this.doText(value);
        return name;
    }

    private void doText(Object value) {
        try {
            char[] text = value.toString().toCharArray();
            this.handler.characters(text, 0, text.length);
        }
        catch (SAXException e) {
            this.handleException(e);
        }
    }

    protected Object createNode(Object name, Map attributeMap, Object text) {
        AttributesImpl attributes = new AttributesImpl();
        Iterator iterator = attributeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry o;
            Map.Entry entry = o = iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            Tuple3<String, String, String> nameInfo = this.getNameInfo(key);
            String uri = (String)nameInfo.getV1();
            String localName = (String)nameInfo.getV2();
            String qualifiedName = (String)nameInfo.getV3();
            String valueText = value != null ? value.toString() : "";
            attributes.addAttribute(uri, localName, qualifiedName, "CDATA", valueText);
        }
        this.doStartElement(name, attributes);
        if (text != null) {
            this.doText(text);
        }
        return name;
    }

    protected void doStartElement(Object name, Attributes attributes) {
        Tuple3<String, String, String> nameInfo = this.getNameInfo(name);
        String uri = (String)nameInfo.getV1();
        String localName = (String)nameInfo.getV2();
        String qualifiedName = (String)nameInfo.getV3();
        try {
            this.handler.startElement(uri, localName, qualifiedName, attributes);
        }
        catch (SAXException e) {
            this.handleException(e);
        }
    }

    protected void nodeCompleted(Object parent, Object name) {
        Tuple3<String, String, String> nameInfo = this.getNameInfo(name);
        String uri = (String)nameInfo.getV1();
        String localName = (String)nameInfo.getV2();
        String qualifiedName = (String)nameInfo.getV3();
        try {
            this.handler.endElement(uri, localName, qualifiedName);
        }
        catch (SAXException e) {
            this.handleException(e);
        }
    }

    protected void handleException(SAXException e) {
        throw new RuntimeException(e);
    }

    protected Object createNode(Object name, Map attributes) {
        return this.createNode(name, attributes, null);
    }

    private Tuple3<String, String, String> getNameInfo(Object name) {
        String qualifiedName;
        String localName;
        String uri;
        if (name instanceof QName) {
            QName qname = (QName)name;
            uri = qname.getNamespaceURI();
            localName = qname.getLocalPart();
            qualifiedName = qname.getQualifiedName();
        } else {
            uri = "";
            qualifiedName = localName = name.toString();
        }
        return new Tuple3((Object)uri, (Object)localName, (Object)qualifiedName);
    }
}

