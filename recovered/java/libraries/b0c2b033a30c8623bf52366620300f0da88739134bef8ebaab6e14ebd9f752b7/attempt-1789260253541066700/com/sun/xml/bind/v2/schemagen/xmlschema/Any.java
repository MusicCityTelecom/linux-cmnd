/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.bind.v2.schemagen.xmlschema.Occurs;
import com.sun.xml.bind.v2.schemagen.xmlschema.Wildcard;
import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement(value="any")
public interface Any
extends Occurs,
Wildcard,
TypedXmlWriter {
}

