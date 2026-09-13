/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyRuntimeException
 *  groovy.lang.Writable
 *  groovy.util.Node
 *  org.apache.groovy.io.StringBuilderWriter
 *  org.codehaus.groovy.runtime.InvokerHelper
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 */
package groovy.xml;

import groovy.lang.Closure;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.Writable;
import groovy.util.Node;
import groovy.xml.XmlNodePrinter;
import groovy.xml.slurpersupport.GPathResult;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.groovy.io.StringBuilderWriter;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class XmlUtil {
    public static String serialize(Element element) {
        return XmlUtil.serialize(element, false);
    }

    public static String serialize(Element element, boolean allowDocTypeDeclaration) {
        StringBuilderWriter sw = new StringBuilderWriter();
        XmlUtil.serialize((Source)new DOMSource(element), (Writer)sw, allowDocTypeDeclaration);
        return sw.toString();
    }

    public static void serialize(Element element, OutputStream os) {
        XmlUtil.serialize(element, os, false);
    }

    public static void serialize(Element element, OutputStream os, boolean allowDocTypeDeclaration) {
        DOMSource source = new DOMSource(element);
        XmlUtil.serialize((Source)source, os, allowDocTypeDeclaration);
    }

    public static void serialize(Element element, Writer w) {
        XmlUtil.serialize(element, w, false);
    }

    public static void serialize(Element element, Writer w, boolean allowDocTypeDeclaration) {
        DOMSource source = new DOMSource(element);
        XmlUtil.serialize((Source)source, w, allowDocTypeDeclaration);
    }

    public static String serialize(Node node) {
        return XmlUtil.serialize(XmlUtil.asString(node));
    }

    public static void serialize(Node node, OutputStream os) {
        XmlUtil.serialize(XmlUtil.asString(node), os);
    }

    public static void serialize(Node node, Writer w) {
        XmlUtil.serialize(XmlUtil.asString(node), w);
    }

    public static String serialize(GPathResult node) {
        return XmlUtil.serialize(XmlUtil.asString(node));
    }

    public static void serialize(GPathResult node, OutputStream os) {
        XmlUtil.serialize(XmlUtil.asString(node), os);
    }

    public static void serialize(GPathResult node, Writer w) {
        XmlUtil.serialize(XmlUtil.asString(node), w);
    }

    public static String serialize(Writable writable) {
        return XmlUtil.serialize(XmlUtil.asString(writable));
    }

    public static void serialize(Writable writable, OutputStream os) {
        XmlUtil.serialize(XmlUtil.asString(writable), os);
    }

    public static void serialize(Writable writable, Writer w) {
        XmlUtil.serialize(XmlUtil.asString(writable), w);
    }

    public static String serialize(String xmlString) {
        return XmlUtil.serialize(xmlString, false);
    }

    public static String serialize(String xmlString, boolean allowDocTypeDeclaration) {
        StringBuilderWriter sw = new StringBuilderWriter();
        XmlUtil.serialize((Source)XmlUtil.asStreamSource(xmlString), (Writer)sw, allowDocTypeDeclaration);
        return sw.toString();
    }

    public static void serialize(String xmlString, OutputStream os) {
        XmlUtil.serialize(xmlString, os, false);
    }

    public static void serialize(String xmlString, OutputStream os, boolean allowDocTypeDeclaration) {
        XmlUtil.serialize((Source)XmlUtil.asStreamSource(xmlString), os, allowDocTypeDeclaration);
    }

    public static void serialize(String xmlString, Writer w) {
        XmlUtil.serialize(xmlString, w, false);
    }

    public static void serialize(String xmlString, Writer w, boolean allowDocTypeDeclaration) {
        XmlUtil.serialize((Source)XmlUtil.asStreamSource(xmlString), w, allowDocTypeDeclaration);
    }

    public static SAXParser newSAXParser(String schemaLanguage, Source ... schemas) throws SAXException, ParserConfigurationException {
        return XmlUtil.newSAXParser(schemaLanguage, true, false, schemas);
    }

    public static SAXParser newSAXParser(String schemaLanguage, boolean namespaceAware, boolean validating, Source ... schemas) throws SAXException, ParserConfigurationException {
        return XmlUtil.newSAXParser(schemaLanguage, namespaceAware, validating, false, schemas);
    }

    public static SAXParser newSAXParser(String schemaLanguage, boolean namespaceAware, boolean validating, boolean allowDoctypeDecl, Source ... schemas) throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = XmlUtil.newFactoryInstance(namespaceAware, validating, allowDoctypeDecl);
        if (schemas.length != 0) {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
            factory.setSchema(schemaFactory.newSchema(schemas));
        }
        SAXParser saxParser = factory.newSAXParser();
        if (schemas.length == 0) {
            saxParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", schemaLanguage);
        }
        return saxParser;
    }

    private static SAXParserFactory newFactoryInstance(boolean namespaceAware, boolean validating, boolean allowDoctypeDecl) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(validating);
        factory.setNamespaceAware(namespaceAware);
        XmlUtil.setFeatureQuietly(factory, "http://javax.xml.XMLConstants/feature/secure-processing", true);
        XmlUtil.setFeatureQuietly(factory, "http://apache.org/xml/features/disallow-doctype-decl", !allowDoctypeDecl);
        return factory;
    }

    public static SAXParser newSAXParser(String schemaLanguage, File schema) throws SAXException, ParserConfigurationException {
        return XmlUtil.newSAXParser(schemaLanguage, true, false, schema);
    }

    public static SAXParser newSAXParser(String schemaLanguage, boolean namespaceAware, boolean validating, File schema) throws SAXException, ParserConfigurationException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        return XmlUtil.newSAXParser(namespaceAware, validating, schemaFactory.newSchema(schema));
    }

    public static SAXParser newSAXParser(String schemaLanguage, URL schema) throws SAXException, ParserConfigurationException {
        return XmlUtil.newSAXParser(schemaLanguage, true, false, schema);
    }

    public static SAXParser newSAXParser(String schemaLanguage, boolean namespaceAware, boolean validating, URL schema) throws SAXException, ParserConfigurationException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        return XmlUtil.newSAXParser(namespaceAware, validating, schemaFactory.newSchema(schema));
    }

    public static String escapeXml(String orig) {
        return StringGroovyMethods.collectReplacements((String)orig, (Closure)new Closure<String>(null){

            public String doCall(Character arg) {
                switch (arg.charValue()) {
                    case '&': {
                        return "&amp;";
                    }
                    case '<': {
                        return "&lt;";
                    }
                    case '>': {
                        return "&gt;";
                    }
                    case '\"': {
                        return "&quot;";
                    }
                    case '\'': {
                        return "&apos;";
                    }
                }
                return null;
            }
        });
    }

    public static String escapeControlCharacters(String orig) {
        return StringGroovyMethods.collectReplacements((String)orig, (Closure)new Closure<String>(null){

            public String doCall(Character arg) {
                if (arg.charValue() < '\u001f') {
                    return "&#" + arg.charValue() + ";";
                }
                return null;
            }
        });
    }

    private static SAXParser newSAXParser(boolean namespaceAware, boolean validating, Schema schema1) throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = XmlUtil.newFactoryInstance(namespaceAware, validating, false);
        factory.setSchema(schema1);
        return factory.newSAXParser();
    }

    private static String asString(Node node) {
        StringBuilderWriter sw = new StringBuilderWriter();
        PrintWriter pw = new PrintWriter((Writer)sw);
        XmlNodePrinter nodePrinter = new XmlNodePrinter(pw);
        nodePrinter.setPreserveWhitespace(true);
        nodePrinter.print(node);
        return sw.toString();
    }

    private static String asString(GPathResult node) {
        try {
            Object builder = Class.forName("groovy.xml.StreamingMarkupBuilder").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            InvokerHelper.setProperty(builder, (String)"encoding", (Object)"UTF-8");
            Writable w = (Writable)InvokerHelper.invokeMethod(builder, (String)"bindNode", (Object)node);
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + w.toString();
        }
        catch (Exception e) {
            return "Couldn't convert node to string because: " + e.getMessage();
        }
    }

    private static String asString(Writable writable) {
        if (writable instanceof GPathResult) {
            return XmlUtil.asString((GPathResult)writable);
        }
        StringBuilderWriter sw = new StringBuilderWriter();
        try {
            writable.writeTo((Writer)sw);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return sw.toString();
    }

    private static StreamSource asStreamSource(String xmlString) {
        return new StreamSource(new StringReader(xmlString));
    }

    private static void serialize(Source source, OutputStream os, boolean allowDocTypeDeclaration) {
        XmlUtil.serialize(source, new StreamResult(new OutputStreamWriter(os, StandardCharsets.UTF_8)), allowDocTypeDeclaration);
    }

    private static void serialize(Source source, Writer w, boolean allowDocTypeDeclaration) {
        XmlUtil.serialize(source, new StreamResult(w), allowDocTypeDeclaration);
    }

    private static void serialize(Source source, StreamResult target, boolean allowDocTypeDeclaration) {
        TransformerFactory factory = TransformerFactory.newInstance();
        XmlUtil.setFeatureQuietly(factory, "http://apache.org/xml/features/disallow-doctype-decl", !allowDocTypeDeclaration);
        XmlUtil.setIndent(factory, 2);
        try {
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("method", "xml");
            transformer.setOutputProperty("media-type", "text/xml");
            transformer.transform(source, target);
        }
        catch (TransformerException e) {
            throw new GroovyRuntimeException(e.getMessage());
        }
    }

    private static void setIndent(TransformerFactory factory, int indent) {
        try {
            factory.setAttribute("indent-number", indent);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
    }

    public static void setFeatureQuietly(TransformerFactory factory, String feature, boolean value) {
        try {
            factory.setFeature(feature, value);
        }
        catch (TransformerConfigurationException transformerConfigurationException) {
            // empty catch block
        }
    }

    public static void setFeatureQuietly(DocumentBuilderFactory factory, String feature, boolean value) {
        try {
            factory.setFeature(feature, value);
        }
        catch (ParserConfigurationException parserConfigurationException) {
            // empty catch block
        }
    }

    public static void setFeatureQuietly(SAXParserFactory factory, String feature, boolean value) {
        try {
            factory.setFeature(feature, value);
        }
        catch (ParserConfigurationException | SAXNotRecognizedException | SAXNotSupportedException exception) {
            // empty catch block
        }
    }
}

