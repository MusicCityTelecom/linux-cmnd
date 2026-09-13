/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 *  groovy.util.CharsetToolkit
 *  org.codehaus.groovy.control.CompilationFailedException
 */
package groovy.text;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import groovy.text.Template;
import groovy.util.CharsetToolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.codehaus.groovy.control.CompilationFailedException;

public abstract class TemplateEngine {
    public abstract Template createTemplate(Reader var1) throws CompilationFailedException, ClassNotFoundException, IOException;

    public Template createTemplate(String templateText) throws CompilationFailedException, ClassNotFoundException, IOException {
        return this.createTemplate(new StringReader(templateText));
    }

    public Template createTemplate(File file) throws CompilationFailedException, ClassNotFoundException, IOException {
        CharsetToolkit toolkit = new CharsetToolkit(file);
        try (BufferedReader reader = toolkit.getReader();){
            Template template = this.createTemplate(reader);
            return template;
        }
    }

    public Template createTemplate(File file, Charset cs) throws CompilationFailedException, ClassNotFoundException, IOException {
        try (InputStreamReader reader = new InputStreamReader((InputStream)new FileInputStream(file), cs);){
            Template template = this.createTemplate(reader);
            return template;
        }
    }

    @SuppressFBWarnings(value={"DM_DEFAULT_ENCODING"}, justification="left for legacy reasons but users expected to heed warning")
    public Template createTemplate(URL url) throws CompilationFailedException, ClassNotFoundException, IOException {
        try (InputStreamReader reader = new InputStreamReader(url.openStream());){
            Template template = this.createTemplate(reader);
            return template;
        }
    }

    public Template createTemplate(URL url, Charset cs) throws CompilationFailedException, ClassNotFoundException, IOException {
        try (InputStreamReader reader = new InputStreamReader(url.openStream(), cs);){
            Template template = this.createTemplate(reader);
            return template;
        }
    }
}

