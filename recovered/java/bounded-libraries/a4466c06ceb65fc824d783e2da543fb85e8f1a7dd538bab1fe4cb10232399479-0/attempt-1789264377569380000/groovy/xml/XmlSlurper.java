/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.namespace.QName
 */
package groovy.xml;

import groovy.namespace.QName;
import groovy.xml.FactorySupport;
import groovy.xml.XmlUtil;
import groovy.xml.slurpersupport.GPathResult;
import groovy.xml.slurpersupport.NamespaceAwareHashMap;
import groovy.xml.slurpersupport.Node;
import groovy.xml.slurpersupport.NodeChild;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlSlurper
extends DefaultHandler {
    private final XMLReader reader;
    private Node currentNode = null;
    private final Stack<Node> stack = new Stack();
    private final StringBuilder charBuffer = new StringBuilder();
    private final Map<String, String> namespaceTagHints = new HashMap<String, String>();
    private boolean keepIgnorableWhitespace = false;
    private boolean namespaceAware = false;

    public XmlSlurper() throws ParserConfigurationException, SAXException {
        this(false, true);
    }

    public XmlSlurper(boolean validating, boolean namespaceAware) throws ParserConfigurationException, SAXException {
        this(validating, namespaceAware, false);
    }

    public XmlSlurper(boolean validating, boolean namespaceAware, boolean allowDocTypeDeclaration) throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = FactorySupport.createSaxParserFactory();
        factory.setNamespaceAware(namespaceAware);
        this.namespaceAware = namespaceAware;
        factory.setValidating(validating);
        XmlUtil.setFeatureQuietly(factory, "http://javax.xml.XMLConstants/feature/secure-processing", true);
        XmlUtil.setFeatureQuietly(factory, "http://apache.org/xml/features/disallow-doctype-decl", !allowDocTypeDeclaration);
        this.reader = factory.newSAXParser().getXMLReader();
    }

    public XmlSlurper(XMLReader reader) {
        this.reader = reader;
    }

    public XmlSlurper(SAXParser parser) throws SAXException {
        this(parser.getXMLReader());
    }

    @Deprecated
    public void setKeepWhitespace(boolean keepWhitespace) {
        this.setKeepIgnorableWhitespace(keepWhitespace);
    }

    public void setKeepIgnorableWhitespace(boolean keepIgnorableWhitespace) {
        this.keepIgnorableWhitespace = keepIgnorableWhitespace;
    }

    public boolean isKeepIgnorableWhitespace() {
        return this.keepIgnorableWhitespace;
    }

    public GPathResult getDocument() {
        try {
            if (this.namespaceAware) {
                this.namespaceTagHints.put("xml", "http://www.w3.org/XML/1998/namespace");
            }
            NodeChild nodeChild = new NodeChild(this.currentNode, null, this.namespaceTagHints);
            return nodeChild;
        }
        finally {
            this.currentNode = null;
        }
    }

    public GPathResult parse(InputSource input) throws IOException, SAXException {
        this.reader.setContentHandler(this);
        this.reader.parse(input);
        return this.getDocument();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GPathResult parse(File file) throws IOException, SAXException {
        FileInputStream fis = new FileInputStream(file);
        InputSource input = new InputSource(fis);
        input.setSystemId("file://" + file.getAbsolutePath());
        try {
            GPathResult gPathResult = this.parse(input);
            return gPathResult;
        }
        finally {
            fis.close();
        }
    }

    public GPathResult parse(InputStream input) throws IOException, SAXException {
        return this.parse(new InputSource(input));
    }

    public GPathResult parse(Reader in) throws IOException, SAXException {
        return this.parse(new InputSource(in));
    }

    public GPathResult parse(String uri) throws IOException, SAXException {
        return this.parse(new InputSource(uri));
    }

    public GPathResult parse(Path path) throws IOException, SAXException {
        return this.parse(Files.newInputStream(path, new OpenOption[0]));
    }

    public GPathResult parseText(String text) throws IOException, SAXException {
        return this.parse(new StringReader(text));
    }

    public DTDHandler getDTDHandler() {
        return this.reader.getDTDHandler();
    }

    public EntityResolver getEntityResolver() {
        return this.reader.getEntityResolver();
    }

    public ErrorHandler getErrorHandler() {
        return this.reader.getErrorHandler();
    }

    public boolean getFeature(String uri) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.reader.getFeature(uri);
    }

    public Object getProperty(String uri) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.reader.getProperty(uri);
    }

    public void setDTDHandler(DTDHandler dtdHandler) {
        this.reader.setDTDHandler(dtdHandler);
    }

    public void setEntityResolver(EntityResolver entityResolver) {
        this.reader.setEntityResolver(entityResolver);
    }

    public void setEntityBaseUrl(URL base) {
        this.reader.setEntityResolver((publicId, systemId) -> new InputSource(new URL(base, systemId).openStream()));
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.reader.setErrorHandler(errorHandler);
    }

    public void setFeature(String uri, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.reader.setFeature(uri, value);
    }

    public void setProperty(String uri, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.reader.setProperty(uri, value);
    }

    @Override
    public void startDocument() throws SAXException {
        this.currentNode = null;
        this.charBuffer.setLength(0);
    }

    @Override
    public void startPrefixMapping(String tag, String uri) throws SAXException {
        if (this.namespaceAware) {
            this.namespaceTagHints.put(tag, uri);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        this.addCdata();
        NamespaceAwareHashMap attributes = new NamespaceAwareHashMap();
        HashMap<String, String> attributeNamespaces = new HashMap<String, String>();
        for (int i = atts.getLength() - 1; i != -1; --i) {
            if (atts.getURI(i).length() == 0) {
                attributes.put(atts.getQName(i), atts.getValue(i));
                continue;
            }
            String key = new QName(atts.getURI(i), atts.getLocalName(i)).toString();
            attributes.put(key, atts.getValue(i));
            attributeNamespaces.put(key, atts.getURI(i));
        }
        Node newElement = namespaceURI.length() == 0 ? new Node(this.currentNode, qName, attributes, attributeNamespaces, namespaceURI) : new Node(this.currentNode, localName, attributes, attributeNamespaces, namespaceURI);
        if (this.currentNode != null) {
            this.currentNode.addChild(newElement);
        }
        this.stack.push(this.currentNode);
        this.currentNode = newElement;
    }

    @Override
    public void ignorableWhitespace(char[] buffer, int start, int len) throws SAXException {
        if (this.keepIgnorableWhitespace) {
            this.characters(buffer, start, len);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.charBuffer.append(ch, start, length);
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        this.addCdata();
        Node oldCurrentNode = this.stack.pop();
        if (oldCurrentNode != null) {
            this.currentNode = oldCurrentNode;
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }

    private void addCdata() {
        if (this.charBuffer.length() != 0) {
            String cdata = this.charBuffer.toString();
            this.charBuffer.setLength(0);
            if (this.keepIgnorableWhitespace || cdata.trim().length() != 0) {
                this.currentNode.addChild(cdata);
            }
        }
    }
}

