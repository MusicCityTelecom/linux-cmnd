/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache
 *  com.samskivert.mustache.Mustache$Compiler
 *  org.springframework.web.reactive.result.view.AbstractUrlBasedView
 *  org.springframework.web.reactive.result.view.UrlBasedViewResolver
 */
package org.springframework.boot.web.reactive.result.view;

import com.samskivert.mustache.Mustache;
import org.springframework.boot.web.reactive.result.view.MustacheView;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

public class MustacheViewResolver
extends UrlBasedViewResolver {
    private final Mustache.Compiler compiler;
    private String charset;

    public MustacheViewResolver() {
        this.compiler = Mustache.compiler();
        this.setViewClass(this.requiredViewClass());
    }

    public MustacheViewResolver(Mustache.Compiler compiler) {
        this.compiler = compiler;
        this.setViewClass(this.requiredViewClass());
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    protected Class<?> requiredViewClass() {
        return MustacheView.class;
    }

    protected AbstractUrlBasedView createView(String viewName) {
        MustacheView view = (MustacheView)super.createView(viewName);
        view.setCompiler(this.compiler);
        view.setCharset(this.charset);
        return view;
    }
}

