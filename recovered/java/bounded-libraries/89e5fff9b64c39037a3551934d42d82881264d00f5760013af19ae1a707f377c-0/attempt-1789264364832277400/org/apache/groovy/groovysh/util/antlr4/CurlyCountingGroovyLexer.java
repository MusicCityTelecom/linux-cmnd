/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  groovyjarjarantlr4.v4.runtime.CharStream
 *  groovyjarjarantlr4.v4.runtime.CharStreams
 *  groovyjarjarantlr4.v4.runtime.CommonTokenStream
 *  groovyjarjarantlr4.v4.runtime.Token
 *  groovyjarjarantlr4.v4.runtime.TokenSource
 *  org.apache.groovy.parser.antlr4.GroovyLangLexer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 */
package org.apache.groovy.groovysh.util.antlr4;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.CharStreams;
import groovyjarjarantlr4.v4.runtime.CommonTokenStream;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenSource;
import java.beans.Transient;
import java.io.Reader;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.groovy.parser.antlr4.GroovyLangLexer;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;

public class CurlyCountingGroovyLexer
extends GroovyLangLexer
implements GroovyObject {
    private int curlyLevel;
    private List<Token> tokens;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    protected CurlyCountingGroovyLexer(Reader reader) {
        super((CharStream)CharStreams.fromReader((Reader)reader));
        MetaClass metaClass;
        Object var2_2 = null;
        this.tokens = (List)ScriptBytecodeAdapter.castToType(var2_2, List.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public static CurlyCountingGroovyLexer createGroovyLexer(String src) {
        return new CurlyCountingGroovyLexer(new StringReader(src));
    }

    public int getCurlyLevel() {
        return this.curlyLevel;
    }

    public int countCurlyLevel() {
        CommonTokenStream tokenStream = new CommonTokenStream((TokenSource)this);
        try {
            List list;
            tokenStream.fill();
            this.tokens = list = tokenStream.getTokens();
        }
        catch (Exception ignore) {
        }
        return this.curlyLevel;
    }

    public List<Token> toList() {
        if (this.tokens == null) {
            this.countCurlyLevel();
        }
        return this.tokens;
    }

    protected void enterParenCallback(String text) {
        if (ScriptBytecodeAdapter.compareEqual((Object)Character.valueOf('{'), (Object)text)) {
            int n = this.curlyLevel;
            this.curlyLevel = n + 1;
        }
    }

    protected void exitParenCallback(String text) {
        if (ScriptBytecodeAdapter.compareEqual((Object)Character.valueOf('}'), (Object)text)) {
            int n = this.curlyLevel;
            this.curlyLevel = n - 1;
        }
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (((Object)((Object)this)).getClass() != CurlyCountingGroovyLexer.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
        }
        return classInfo.getMetaClass();
    }

    @Generated
    @Internal
    @Transient
    public MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = this.$getStaticMetaClass();
        return this.metaClass;
    }

    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }
}

