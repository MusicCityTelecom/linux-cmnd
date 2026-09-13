/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public interface BeanDefinitionRegisteringParser {
    public String parse(Element var1, ParserContext var2);
}

