/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.xml;

import com.google.common.collect.ImmutableBiMap;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

public final class XmlUtils {
    static final ImmutableBiMap<String, String> COMMON_NAMESPACE_PREFIXES = ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)ImmutableBiMap.builder().put("http://schemas.android.com/apk/res/android", "android")).put("http://schemas.android.com/apk/distribution", "dist")).put("http://schemas.android.com/tools", "tools")).build();

    public static String documentToString(Node document) {
        StringWriter sw = new StringWriter();
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            transformer.setOutputProperty("method", "xml");
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(document), new StreamResult(sw));
        }
        catch (TransformerException e2) {
            throw new IllegalStateException("An error occurred while converting the XML to a string", e2);
        }
        return sw.toString();
    }

    private XmlUtils() {
    }
}

