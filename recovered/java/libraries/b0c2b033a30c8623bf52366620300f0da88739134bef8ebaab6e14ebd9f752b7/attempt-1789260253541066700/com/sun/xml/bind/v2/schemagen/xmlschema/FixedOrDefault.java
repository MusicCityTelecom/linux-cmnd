/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;

public interface FixedOrDefault
extends TypedXmlWriter {
    @XmlAttribute(value="default")
    public FixedOrDefault _default(String var1);

    @XmlAttribute
    public FixedOrDefault fixed(String var1);
}

