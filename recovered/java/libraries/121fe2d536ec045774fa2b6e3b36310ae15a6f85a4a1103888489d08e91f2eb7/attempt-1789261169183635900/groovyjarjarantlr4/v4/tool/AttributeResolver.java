/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;

public interface AttributeResolver {
    public boolean resolvesToListLabel(String var1, ActionAST var2);

    public boolean resolvesToLabel(String var1, ActionAST var2);

    public boolean resolvesToAttributeDict(String var1, ActionAST var2);

    public boolean resolvesToToken(String var1, ActionAST var2);

    public Attribute resolveToAttribute(String var1, ActionAST var2);

    public Attribute resolveToAttribute(String var1, String var2, ActionAST var3);
}

