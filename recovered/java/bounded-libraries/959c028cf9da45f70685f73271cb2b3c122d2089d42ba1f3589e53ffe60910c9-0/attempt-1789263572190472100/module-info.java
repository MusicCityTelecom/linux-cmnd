module java.xml.bind {
    /* transitive */ requires java.activation;
    /* transitive */ requires java.xml;
    requires java.logging;
    requires java.desktop;

    exports javax.xml.bind;
    exports javax.xml.bind.annotation;
    exports javax.xml.bind.annotation.adapters;
    exports javax.xml.bind.attachment;
    exports javax.xml.bind.helpers;
    exports javax.xml.bind.util;

}

