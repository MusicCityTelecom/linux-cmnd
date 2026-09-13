/* synthetic */ module com.fasterxml.jackson.dataformat.javaprop {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.fasterxml.jackson.dataformat.javaprop;
    exports com.fasterxml.jackson.dataformat.javaprop.io;
    exports com.fasterxml.jackson.dataformat.javaprop.util;

    provides JsonFactory with JavaPropsFactory;
    provides ObjectCodec with JavaPropsMapper;

}

