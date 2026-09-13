/* synthetic */ module com.fasterxml.jackson.datatype.guava {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    /* static phase */ requires guava;
    /* static phase */ requires com.google.common;

    exports com.fasterxml.jackson.datatype.guava;
    exports com.fasterxml.jackson.datatype.guava.deser;
    exports com.fasterxml.jackson.datatype.guava.deser.multimap;
    exports com.fasterxml.jackson.datatype.guava.deser.multimap.list;
    exports com.fasterxml.jackson.datatype.guava.deser.multimap.set;
    exports com.fasterxml.jackson.datatype.guava.deser.util;
    exports com.fasterxml.jackson.datatype.guava.ser;

    provides Module with GuavaModule;

}

