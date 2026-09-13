/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.util.matcher;

import java.beans.PropertyEditorSupport;
import org.springframework.security.web.util.matcher.ELRequestMatcher;

public class RequestMatcherEditor
extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(new ELRequestMatcher(text));
    }
}

