/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.springframework.util.FileCopyUtils
 *  org.springframework.web.servlet.View
 */
package org.apereo.cas.web.view;

import java.io.Writer;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.View;

public class DynamicHtmlView
implements View {
    private final String html;

    public String getContentType() {
        return "text/html";
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(this.getContentType());
        FileCopyUtils.copy((String)this.html, (Writer)response.getWriter());
    }

    @Generated
    public DynamicHtmlView(String html) {
        this.html = html;
    }

    @Generated
    public String getHtml() {
        return this.html;
    }
}

