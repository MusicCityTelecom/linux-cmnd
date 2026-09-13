/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.internal.LexicalScope;
import org.apache.commons.jexl3.internal.Scope;
import org.apache.commons.jexl3.parser.ASTAmbiguous;
import org.apache.commons.jexl3.parser.ASTAssignment;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTJexlLambda;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.ASTSetAddNode;
import org.apache.commons.jexl3.parser.ASTSetAndNode;
import org.apache.commons.jexl3.parser.ASTSetDivNode;
import org.apache.commons.jexl3.parser.ASTSetMultNode;
import org.apache.commons.jexl3.parser.ASTSetOrNode;
import org.apache.commons.jexl3.parser.ASTSetSubNode;
import org.apache.commons.jexl3.parser.ASTSetXorNode;
import org.apache.commons.jexl3.parser.ASTVar;
import org.apache.commons.jexl3.parser.FeatureController;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.ParseException;
import org.apache.commons.jexl3.parser.StringParser;
import org.apache.commons.jexl3.parser.Token;

public abstract class JexlParser
extends StringParser {
    protected final FeatureController featureController = new FeatureController(JexlEngine.DEFAULT_FEATURES);
    protected JexlInfo info = null;
    protected String source = null;
    protected Scope frame = null;
    protected final Deque<Scope> frames = new ArrayDeque<Scope>();
    protected Map<String, Object> pragmas = null;
    protected Set<String> namespaces = null;
    protected int loopCount = 0;
    protected final Deque<Integer> loopCounts = new ArrayDeque<Integer>();
    protected LexicalUnit block = null;
    protected final Deque<LexicalUnit> blocks = new ArrayDeque<LexicalUnit>();
    protected static final String PRAGMA_JEXLNS = "jexl.namespace.";
    private static final Set<Class<? extends JexlNode>> ASSIGN_NODES = new HashSet<Class>(Arrays.asList(ASTAssignment.class, ASTSetAddNode.class, ASTSetMultNode.class, ASTSetDivNode.class, ASTSetAndNode.class, ASTSetOrNode.class, ASTSetXorNode.class, ASTSetSubNode.class));

    protected void cleanup(JexlFeatures features) {
        this.info = null;
        this.source = null;
        this.frame = null;
        this.frames.clear();
        this.pragmas = null;
        this.namespaces = null;
        this.loopCounts.clear();
        this.loopCount = 0;
        this.blocks.clear();
        this.block = null;
        this.setFeatures(features);
    }

    protected static String stringify(Iterable<String> lstr) {
        StringBuilder strb = new StringBuilder();
        boolean dot = false;
        for (String str : lstr) {
            if (!dot) {
                dot = true;
            } else {
                strb.append('.');
            }
            strb.append(str);
        }
        return strb.toString();
    }

    protected static String readSourceLine(String src, int lineno) {
        String msg = "";
        if (src != null && lineno >= 0) {
            try {
                BufferedReader reader = new BufferedReader(new StringReader(src));
                for (int l = 0; l < lineno; ++l) {
                    msg = reader.readLine();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return msg;
    }

    public void allowRegisters(boolean registers) {
        this.featureController.setFeatures(new JexlFeatures(this.featureController.getFeatures()).register(registers));
    }

    protected void setFeatures(JexlFeatures features) {
        this.featureController.setFeatures(features);
    }

    protected JexlFeatures getFeatures() {
        return this.featureController.getFeatures();
    }

    protected Scope getFrame() {
        return this.frame;
    }

    protected void pushFrame() {
        if (this.frame != null) {
            this.frames.push(this.frame);
        }
        this.frame = new Scope(this.frame, null);
        this.loopCounts.push(this.loopCount);
        this.loopCount = 0;
    }

    protected void popFrame() {
        this.frame = !this.frames.isEmpty() ? this.frames.pop() : null;
        if (!this.loopCounts.isEmpty()) {
            this.loopCount = this.loopCounts.pop();
        }
    }

    protected LexicalUnit getUnit() {
        return this.block;
    }

    protected void pushUnit(LexicalUnit unit) {
        if (this.block != null) {
            this.blocks.push(this.block);
        }
        this.block = unit;
    }

    protected void popUnit(LexicalUnit unit) {
        if (this.block == unit) {
            this.block = !this.blocks.isEmpty() ? this.blocks.pop() : null;
        }
    }

    private boolean isSymbolDeclared(JexlNode.Info info, int symbol) {
        for (JexlNode walk = info.getNode(); walk != null; walk = walk.jjtGetParent()) {
            if (!(walk instanceof LexicalUnit)) continue;
            LexicalScope scope = ((LexicalUnit)((Object)walk)).getLexicalScope();
            if (scope != null && scope.hasSymbol(symbol)) {
                return true;
            }
            if (walk instanceof ASTJexlLambda) break;
        }
        return false;
    }

    protected boolean isVariable(String name) {
        return this.frame != null && this.frame.getSymbol(name) != null;
    }

    protected String checkVariable(ASTIdentifier identifier, String name) {
        Integer symbol;
        if (this.frame != null && (symbol = this.frame.getSymbol(name)) != null) {
            boolean declared = true;
            if (this.frame.isCapturedSymbol(symbol)) {
                identifier.setCaptured(true);
            } else {
                declared = this.block.hasSymbol(symbol);
                if (!declared) {
                    for (LexicalUnit u : this.blocks) {
                        if (!u.hasSymbol(symbol)) continue;
                        declared = true;
                        break;
                    }
                }
                if (!declared && this.info instanceof JexlNode.Info) {
                    declared = this.isSymbolDeclared((JexlNode.Info)this.info, symbol);
                }
            }
            identifier.setSymbol(symbol, name);
            if (!declared) {
                identifier.setShaded(true);
                if (this.getFeatures().isLexicalShade()) {
                    throw new JexlException(identifier, name + ": variable is not defined");
                }
            }
        }
        return name;
    }

    protected boolean allowVariable(String image) {
        JexlFeatures features = this.getFeatures();
        if (!features.supportsLocalVar()) {
            return false;
        }
        return !features.isReservedName(image);
    }

    private boolean declareSymbol(int symbol) {
        for (LexicalUnit lu : this.blocks) {
            if (lu.hasSymbol(symbol)) {
                return false;
            }
            if (!(lu instanceof ASTJexlLambda)) continue;
            break;
        }
        return this.block == null || this.block.declareSymbol(symbol);
    }

    protected void declareVariable(ASTVar variable, Token token) {
        String name = token.image;
        if (!this.allowVariable(name)) {
            this.throwFeatureException(2, token);
        }
        if (this.frame == null) {
            this.frame = new Scope(null, null);
        }
        int symbol = this.frame.declareVariable(name);
        variable.setSymbol(symbol, name);
        if (this.frame.isCapturedSymbol(symbol)) {
            variable.setCaptured(true);
        }
        if (!this.declareSymbol(symbol)) {
            if (this.getFeatures().isLexical()) {
                throw new JexlException(variable, name + ": variable is already declared");
            }
            variable.setRedefined(true);
        }
    }

    protected void declarePragma(String key, Object value) {
        String nsname;
        Predicate<String> ns;
        if (!this.getFeatures().supportsPragma()) {
            this.throwFeatureException(11, this.getToken(0));
        }
        if (this.pragmas == null) {
            this.pragmas = new TreeMap<String, Object>();
        }
        if ((ns = this.getFeatures().namespaceTest()) != null && key.startsWith(PRAGMA_JEXLNS) && (nsname = key.substring(PRAGMA_JEXLNS.length())) != null && !nsname.isEmpty()) {
            if (this.namespaces == null) {
                this.namespaces = new HashSet<String>();
            }
            this.namespaces.add(nsname);
        }
        this.pragmas.put(key, value);
    }

    protected boolean isDeclaredNamespace(Token token, Token colon) {
        if (colon != null && ":".equals(colon.image) && colon.beginColumn - 1 == token.endColumn) {
            return true;
        }
        String name = token.image;
        if (!this.isVariable(name)) {
            Set<String> ns = this.namespaces;
            if (ns != null && ns.contains(name)) {
                return true;
            }
            if (this.getFeatures().namespaceTest().test(name)) {
                return true;
            }
        }
        return false;
    }

    protected void declareParameter(Token token) {
        int symbol;
        String identifier = token.image;
        if (!this.allowVariable(identifier)) {
            this.throwFeatureException(2, token);
        }
        if (this.frame == null) {
            this.frame = new Scope(null, null);
        }
        if (!this.block.declareSymbol(symbol = this.frame.declareParameter(identifier)) && this.getFeatures().isLexical()) {
            JexlInfo xinfo = this.info.at(token.beginLine, token.beginColumn);
            throw new JexlException(xinfo, identifier + ": variable is already declared", null);
        }
    }

    protected void Identifier(boolean top) throws ParseException {
    }

    protected abstract Token getToken(int var1);

    protected abstract Token getNextToken();

    protected void jjtreeOpenNodeScope(JexlNode node) {
    }

    protected void jjtreeCloseNodeScope(JexlNode node) {
        JexlNode lv;
        if (node instanceof ASTAmbiguous) {
            this.throwAmbiguousException(node);
        }
        if (node instanceof ASTJexlScript) {
            ASTJexlScript script;
            if (node instanceof ASTJexlLambda && !this.getFeatures().supportsLambda()) {
                this.throwFeatureException(8, node.jexlInfo());
            }
            if ((script = (ASTJexlScript)node).getScope() != this.frame) {
                script.setScope(this.frame);
            }
            this.popFrame();
        } else if (ASSIGN_NODES.contains(node.getClass()) && !(lv = node.jjtGetChild(0)).isLeftValue()) {
            this.throwParsingException(JexlException.Assignment.class, null);
        }
        this.featureController.controlNode(node);
    }

    protected void throwAmbiguousException(JexlNode node) {
        JexlInfo begin = node.jexlInfo();
        Token t = this.getToken(0);
        JexlInfo end = this.info.at(t.beginLine, t.endColumn);
        String msg = JexlParser.readSourceLine(this.source, end.getLine());
        throw new JexlException.Ambiguous(begin, end, msg);
    }

    protected void throwFeatureException(int feature, JexlInfo info) {
        String msg = info != null ? JexlParser.readSourceLine(this.source, info.getLine()) : null;
        throw new JexlException.Feature(info, feature, msg);
    }

    protected void throwFeatureException(int feature, Token token) {
        if (token == null && (token = this.getToken(0)) == null) {
            throw new JexlException.Parsing(null, JexlFeatures.stringify(feature));
        }
        JexlInfo xinfo = this.info.at(token.beginLine, token.beginColumn);
        this.throwFeatureException(feature, xinfo);
    }

    protected <T extends JexlException.Parsing> void throwParsingException(Class<T> xclazz, Token tok) {
        JexlInfo xinfo = null;
        String msg = "unrecoverable state";
        JexlException.Parsing xparse = null;
        if (tok == null) {
            tok = this.getToken(0);
        }
        if (tok != null) {
            xinfo = this.info.at(tok.beginLine, tok.beginColumn);
            msg = tok.image;
            if (xclazz != null) {
                try {
                    Constructor<T> ctor = xclazz.getConstructor(JexlInfo.class, String.class);
                    xparse = (JexlException.Parsing)ctor.newInstance(xinfo, msg);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        throw xparse != null ? xparse : new JexlException.Parsing(xinfo, msg);
    }

    protected static Token errorToken(Token ... tokens) {
        for (Token token : tokens) {
            if (token == null || token.image == null || token.image.isEmpty()) continue;
            return token;
        }
        return null;
    }

    public static interface LexicalUnit {
        public boolean declareSymbol(int var1);

        public boolean hasSymbol(int var1);

        public int getSymbolCount();

        public LexicalScope getLexicalScope();
    }
}

