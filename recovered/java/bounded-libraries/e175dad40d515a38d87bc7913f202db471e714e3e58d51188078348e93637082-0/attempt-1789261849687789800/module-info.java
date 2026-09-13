/* synthetic */ module com.fasterxml.jackson.jaxrs.json {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.module.jaxb;
    requires com.fasterxml.jackson.jaxrs.base;
    /* static phase */ requires javax.ws.rs.api;
    /* static phase */ requires java.ws.rs;
    /* static phase */ requires javax.ws.rs;
    /* static phase */ requires jakarta.ws.rs;
    /* static phase */ requires jakarta.ws.rs.api;

    exports com.fasterxml.jackson.jaxrs.json;
    exports com.fasterxml.jackson.jaxrs.json.annotation;

    opens com.fasterxml.jackson.jaxrs.json;

    provides MessageBodyReader with JacksonJsonProvider;
    provides MessageBodyWriter with JacksonJsonProvider;

}

