/*
 * Decompiled with CFR 0.152.
 */
package com.sun.research.ws.wadl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="HTTPMethods")
@XmlEnum
public enum HTTPMethods {
    GET,
    POST,
    PUT,
    HEAD,
    DELETE;


    public String value() {
        return this.name();
    }

    public static HTTPMethods fromValue(String v) {
        return HTTPMethods.valueOf(v);
    }
}

