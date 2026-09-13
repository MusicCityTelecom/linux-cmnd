/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.dataformat.javaprop.util;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropNode;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropPathSplitter;
import java.util.Map;
import java.util.Properties;

public class JPropNodeBuilder {
    @Deprecated
    public static JPropNode build(JavaPropsSchema schema, Properties props) {
        return JPropNodeBuilder.build(props, schema);
    }

    public static JPropNode build(Map<?, ?> content, JavaPropsSchema schema) {
        JPropNode root = new JPropNode();
        JPropPathSplitter splitter = schema.pathSplitter();
        for (Map.Entry<?, ?> entry : content.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            splitter.splitAndAdd(root, key, value);
        }
        return root;
    }
}

