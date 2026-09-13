/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.glassfish.jersey.internal.util.SaxHelper;
import org.glassfish.jersey.jaxb.internal.JaxbFeatureUtil;
import org.glassfish.jersey.jaxb.internal.LocalizationMessages;
import org.xml.sax.EntityResolver;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SecureSaxParserFactory
extends SAXParserFactory {
    private static final Logger LOGGER = Logger.getLogger(SecureSaxParserFactory.class.getName());
    private static final EntityResolver EMPTY_ENTITY_RESOLVER = new EntityResolver(){

        @Override
        public InputSource resolveEntity(String publicId, String systemId) {
            return new InputSource(new ByteArrayInputStream(new byte[0]));
        }
    };
    private static final String EXTERNAL_GENERAL_ENTITIES_FEATURE = "http://xml.org/sax/features/external-general-entities";
    private static final String EXTERNAL_PARAMETER_ENTITIES_FEATURE = "http://xml.org/sax/features/external-parameter-entities";
    private static final String DISALLOW_DOCTYPE_DECLARATION_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    private final SAXParserFactory spf;
    private Map<String, Object> saxParserProperties;
    private final boolean isSecure;

    public SecureSaxParserFactory(SAXParserFactory spf) {
        this(spf, true);
    }

    SecureSaxParserFactory(SAXParserFactory spf, boolean isSecure) {
        this.spf = spf;
        this.isSecure = isSecure;
        if (SaxHelper.isXdkParserFactory(spf)) {
            LOGGER.log(Level.WARNING, LocalizationMessages.SAX_XDK_NO_SECURITY_FEATURES());
        } else {
            try {
                if (isSecure) {
                    spf.setFeature(EXTERNAL_GENERAL_ENTITIES_FEATURE, Boolean.FALSE);
                }
            }
            catch (Exception ex) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.SAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE(spf.getClass()), ex);
            }
            try {
                if (isSecure) {
                    spf.setFeature(EXTERNAL_PARAMETER_ENTITIES_FEATURE, Boolean.FALSE);
                }
            }
            catch (Exception ex) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.SAX_CANNOT_DISABLE_PARAMETER_ENTITY_PROCESSING_FEATURE(spf.getClass()), ex);
            }
            try {
                if (isSecure) {
                    spf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
                }
            }
            catch (Exception ex) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.SAX_CANNOT_ENABLE_SECURE_PROCESSING_FEATURE(spf.getClass()), ex);
            }
            try {
                if (isSecure) {
                    spf.setFeature(DISALLOW_DOCTYPE_DECLARATION_FEATURE, Boolean.TRUE);
                }
            }
            catch (Exception ex) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.SAX_CANNOT_ENABLE_DISALLOW_DOCTYPE_DECLARATION_FEATURE(spf.getClass()), ex);
            }
        }
    }

    @Override
    public void setNamespaceAware(boolean b) {
        this.spf.setNamespaceAware(b);
    }

    @Override
    public void setValidating(boolean b) {
        this.spf.setValidating(b);
    }

    @Override
    public boolean isNamespaceAware() {
        return this.spf.isNamespaceAware();
    }

    @Override
    public boolean isValidating() {
        return this.spf.isValidating();
    }

    @Override
    public Schema getSchema() {
        return this.spf.getSchema();
    }

    @Override
    public void setSchema(Schema schema) {
        this.spf.setSchema(schema);
    }

    @Override
    public void setXIncludeAware(boolean b) {
        this.spf.setXIncludeAware(b);
    }

    @Override
    public boolean isXIncludeAware() {
        return this.spf.isXIncludeAware();
    }

    @Override
    public SAXParser newSAXParser() throws ParserConfigurationException, SAXException {
        WrappingSAXParser wrappingParser = new WrappingSAXParser(this.spf.newSAXParser(), this.isSecure);
        if (this.saxParserProperties != null) {
            for (Map.Entry<String, Object> entry : this.saxParserProperties.entrySet()) {
                JaxbFeatureUtil.setProperty(SAXParser.class, entry, wrappingParser::setProperty);
            }
        }
        return wrappingParser;
    }

    @Override
    public void setFeature(String s, boolean b) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        this.spf.setFeature(s, b);
    }

    @Override
    public boolean getFeature(String s) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        return this.spf.getFeature(s);
    }

    void setSaxParserProperties(Map<String, Object> saxParserProperties) {
        this.saxParserProperties = saxParserProperties;
    }

    private static final class WrappingSAXParser
    extends SAXParser {
        private final SAXParser sp;
        private final boolean isSecure;

        private WrappingSAXParser(SAXParser sp, boolean isSecure) {
            this.sp = sp;
            this.isSecure = isSecure;
        }

        @Override
        public void reset() {
            this.sp.reset();
        }

        @Override
        public void parse(InputStream inputStream, HandlerBase handlerBase) throws SAXException, IOException {
            this.sp.parse(inputStream, handlerBase);
        }

        @Override
        public void parse(InputStream inputStream, HandlerBase handlerBase, String s) throws SAXException, IOException {
            this.sp.parse(inputStream, handlerBase, s);
        }

        @Override
        public void parse(InputStream inputStream, DefaultHandler defaultHandler) throws SAXException, IOException {
            this.sp.parse(inputStream, defaultHandler);
        }

        @Override
        public void parse(InputStream inputStream, DefaultHandler defaultHandler, String s) throws SAXException, IOException {
            this.sp.parse(inputStream, defaultHandler, s);
        }

        @Override
        public void parse(String s, HandlerBase handlerBase) throws SAXException, IOException {
            this.sp.parse(s, handlerBase);
        }

        @Override
        public void parse(String s, DefaultHandler defaultHandler) throws SAXException, IOException {
            this.sp.parse(s, defaultHandler);
        }

        @Override
        public void parse(File file, HandlerBase handlerBase) throws SAXException, IOException {
            this.sp.parse(file, handlerBase);
        }

        @Override
        public void parse(File file, DefaultHandler defaultHandler) throws SAXException, IOException {
            this.sp.parse(file, defaultHandler);
        }

        @Override
        public void parse(InputSource inputSource, HandlerBase handlerBase) throws SAXException, IOException {
            this.sp.parse(inputSource, handlerBase);
        }

        @Override
        public void parse(InputSource inputSource, DefaultHandler defaultHandler) throws SAXException, IOException {
            this.sp.parse(inputSource, defaultHandler);
        }

        @Override
        public Parser getParser() throws SAXException {
            return this.sp.getParser();
        }

        @Override
        public XMLReader getXMLReader() throws SAXException {
            XMLReader r = this.sp.getXMLReader();
            r.setEntityResolver(EMPTY_ENTITY_RESOLVER);
            try {
                if (this.isSecure) {
                    r.setFeature(SecureSaxParserFactory.EXTERNAL_GENERAL_ENTITIES_FEATURE, Boolean.FALSE);
                }
            }
            catch (Exception ex) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.SAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE_ON_READER(r.getClass()), ex);
            }
            return r;
        }

        @Override
        public boolean isNamespaceAware() {
            return this.sp.isNamespaceAware();
        }

        @Override
        public boolean isValidating() {
            return this.sp.isValidating();
        }

        @Override
        public void setProperty(String s, Object o) throws SAXNotRecognizedException, SAXNotSupportedException {
            this.sp.setProperty(s, o);
        }

        @Override
        public Object getProperty(String s) throws SAXNotRecognizedException, SAXNotSupportedException {
            return this.sp.getProperty(s);
        }

        @Override
        public Schema getSchema() {
            return this.sp.getSchema();
        }

        @Override
        public boolean isXIncludeAware() {
            return this.sp.isXIncludeAware();
        }
    }
}

