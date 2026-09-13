/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class AttributeDict {
    public String name;
    public GrammarAST ast;
    public DictType type;
    public static final AttributeDict predefinedTokenDict = new AttributeDict(DictType.TOKEN);
    @NotNull
    public final LinkedHashMap<String, Attribute> attributes = new LinkedHashMap();

    public AttributeDict() {
    }

    public AttributeDict(DictType type) {
        this.type = type;
    }

    public Attribute add(Attribute a) {
        a.dict = this;
        return this.attributes.put(a.name, a);
    }

    public Attribute get(String name) {
        return this.attributes.get(name);
    }

    public String getName() {
        return this.name;
    }

    public int size() {
        return this.attributes.size();
    }

    @NotNull
    public Set<String> intersection(@Nullable AttributeDict other) {
        if (other == null || other.size() == 0 || this.size() == 0) {
            return Collections.emptySet();
        }
        HashSet<String> result = new HashSet<String>(this.attributes.keySet());
        result.retainAll(other.attributes.keySet());
        return result;
    }

    public String toString() {
        return this.getName() + ":" + this.attributes;
    }

    static {
        predefinedTokenDict.add(new Attribute("text"));
        predefinedTokenDict.add(new Attribute("type"));
        predefinedTokenDict.add(new Attribute("line"));
        predefinedTokenDict.add(new Attribute("index"));
        predefinedTokenDict.add(new Attribute("pos"));
        predefinedTokenDict.add(new Attribute("channel"));
        predefinedTokenDict.add(new Attribute("int"));
    }

    public static enum DictType {
        ARG,
        RET,
        LOCAL,
        TOKEN,
        PREDEFINED_RULE,
        PREDEFINED_LEXER_RULE;

    }
}

