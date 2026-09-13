/* synthetic */ module com.fasterxml.jackson.datatype.jdk8 {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.fasterxml.jackson.datatype.jdk8;

    provides Module with Jdk8Module;

}

