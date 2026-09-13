/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.io.InputStreamSource
 *  org.springframework.core.io.Resource
 *  org.springframework.util.FileCopyUtils
 */
package org.springframework.boot.convert;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.boot.origin.Origin;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

class InputStreamSourceToByteArrayConverter
implements Converter<InputStreamSource, byte[]> {
    InputStreamSourceToByteArrayConverter() {
    }

    public byte[] convert(InputStreamSource source) {
        try {
            return FileCopyUtils.copyToByteArray((InputStream)source.getInputStream());
        }
        catch (IOException ex) {
            throw new IllegalStateException("Unable to read from " + this.getName(source), ex);
        }
    }

    private String getName(InputStreamSource source) {
        Origin origin = Origin.from(source);
        if (origin != null) {
            return origin.toString();
        }
        if (source instanceof Resource) {
            return ((Resource)source).getDescription();
        }
        return "input stream source";
    }
}

