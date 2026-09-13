/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.AttributeDict;

public class Attribute {
    public String decl;
    public String type;
    public String name;
    public Token token;
    public String initValue;
    public AttributeDict dict;

    public Attribute() {
    }

    public Attribute(String name) {
        this(name, null);
    }

    public Attribute(String name, String decl) {
        this.name = name;
        this.decl = decl;
    }

    public String toString() {
        if (this.initValue != null) {
            return this.name + ":" + this.type + "=" + this.initValue;
        }
        if (this.type != null) {
            return this.name + ":" + this.type;
        }
        return this.name;
    }
}

