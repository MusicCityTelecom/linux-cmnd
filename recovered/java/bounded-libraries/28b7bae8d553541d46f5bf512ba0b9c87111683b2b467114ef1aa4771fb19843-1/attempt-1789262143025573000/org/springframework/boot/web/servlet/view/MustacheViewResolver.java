/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache
 *  com.samskivert.mustache.Mustache$Compiler
 *  org.springframework.web.servlet.view.AbstractTemplateViewResolver
 *  org.springframework.web.servlet.view.AbstractUrlBasedView
 */
package org.springframework.boot.web.servlet.view;

import com.samskivert.mustache.Mustache;
import org.springframework.boot.web.servlet.view.MustacheView;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class MustacheViewResolver
extends AbstractTemplateViewResolver {
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

    protected Class<?> requiredViewClass() {
        return MustacheView.class;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        MustacheView view = (MustacheView)super.buildView(viewName);
        view.setCompiler(this.compiler);
        view.setCharset(this.charset);
        return view;
    }

    protected AbstractUrlBasedView instantiateView() {
        return this.getViewClass() == MustacheView.class ? new MustacheView() : super.instantiateView();
    }
}

