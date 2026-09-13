/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache$Compiler
 *  com.samskivert.mustache.Template
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.core.io.Resource
 *  org.springframework.web.servlet.view.AbstractTemplateView
 */
package org.springframework.boot.web.servlet.view;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class MustacheView
extends AbstractTemplateView {
    private Mustache.Compiler compiler;
    private String charset;

    public void setCompiler(Mustache.Compiler compiler) {
        this.compiler = compiler;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean checkResource(Locale locale) throws Exception {
        Resource resource = this.getApplicationContext().getResource(this.getUrl());
        return resource != null && resource.exists();
    }

    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Template template = this.createTemplate(this.getApplicationContext().getResource(this.getUrl()));
        if (template != null) {
            template.execute(model, (Writer)response.getWriter());
        }
    }

    private Template createTemplate(Resource resource) throws IOException {
        try (Reader reader = this.getReader(resource);){
            Template template = this.compiler.compile(reader);
            return template;
        }
    }

    private Reader getReader(Resource resource) throws IOException {
        if (this.charset != null) {
            return new InputStreamReader(resource.getInputStream(), this.charset);
        }
        return new InputStreamReader(resource.getInputStream());
    }
}

