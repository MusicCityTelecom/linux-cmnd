/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.tools.javac;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import org.codehaus.groovy.control.CompilerConfiguration;

public class RawJavaFileObject
extends SimpleJavaFileObject {
    private static final Charset DEFAULT_CHARSET = Charset.forName(CompilerConfiguration.DEFAULT.getSourceEncoding());
    private final Path javaFilePath;
    private String src;

    public RawJavaFileObject(URI uri) {
        super(uri, JavaFileObject.Kind.SOURCE);
        this.javaFilePath = Paths.get(uri);
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return null != this.src ? this.src : (this.src = new String(Files.readAllBytes(this.javaFilePath), DEFAULT_CHARSET));
    }

    @Override
    public boolean delete() {
        return new File(this.uri).delete();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawJavaFileObject)) {
            return false;
        }
        RawJavaFileObject that = (RawJavaFileObject)o;
        return Objects.equals(this.uri, that.uri);
    }

    public int hashCode() {
        return Objects.hash(this.uri);
    }

    @Override
    public String toString() {
        return "RawJavaFileObject{uri=" + this.uri + '}';
    }
}

