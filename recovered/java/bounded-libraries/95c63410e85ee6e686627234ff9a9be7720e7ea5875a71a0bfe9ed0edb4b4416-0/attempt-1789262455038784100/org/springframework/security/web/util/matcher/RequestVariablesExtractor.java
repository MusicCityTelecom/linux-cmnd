/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.springframework.security.web.util.matcher;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Deprecated
public interface RequestVariablesExtractor {
    public Map<String, String> extractUriTemplateVariables(HttpServletRequest var1);
}

