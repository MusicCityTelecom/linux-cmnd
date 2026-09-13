/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache$TemplateLoader
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.ResourceLoader
 */
package org.springframework.boot.autoconfigure.mustache;

import com.samskivert.mustache.Mustache;
import java.io.InputStreamReader;
import java.io.Reader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class MustacheResourceTemplateLoader
implements Mustache.TemplateLoader,
ResourceLoaderAware {
    private String prefix = "";
    private String suffix = "";
    private String charSet = "UTF-8";
    private ResourceLoader resourceLoader = new DefaultResourceLoader(null);

    public MustacheResourceTemplateLoader() {
    }

    public MustacheResourceTemplateLoader(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void setCharset(String charSet) {
        this.charSet = charSet;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Reader getTemplate(String name) throws Exception {
        return new InputStreamReader(this.resourceLoader.getResource(this.prefix + name + this.suffix).getInputStream(), this.charSet);
    }
}

