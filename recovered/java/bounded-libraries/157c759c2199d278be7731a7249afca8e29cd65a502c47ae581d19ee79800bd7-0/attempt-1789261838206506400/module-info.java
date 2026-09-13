/* synthetic */ module com.fasterxml.jackson.dataformat.xml {
    requires java.xml;
    requires org.codehaus.stax2;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.fasterxml.jackson.dataformat.xml;
    exports com.fasterxml.jackson.dataformat.xml.annotation;
    exports com.fasterxml.jackson.dataformat.xml.deser;
    exports com.fasterxml.jackson.dataformat.xml.ser;
    exports com.fasterxml.jackson.dataformat.xml.util;

    provides JsonFactory with XmlFactory;
    provides ObjectCodec with XmlMapper;

}

