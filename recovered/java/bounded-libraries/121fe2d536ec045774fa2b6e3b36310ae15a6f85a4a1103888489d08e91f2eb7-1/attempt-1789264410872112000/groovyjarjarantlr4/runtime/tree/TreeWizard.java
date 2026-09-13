/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreePatternLexer;
import groovyjarjarantlr4.runtime.tree.TreePatternParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TreeWizard {
    protected TreeAdaptor adaptor;
    protected Map<String, Integer> tokenNameToTypeMap;

    public TreeWizard(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    public TreeWizard(TreeAdaptor adaptor, Map<String, Integer> tokenNameToTypeMap) {
        this.adaptor = adaptor;
        this.tokenNameToTypeMap = tokenNameToTypeMap;
    }

    public TreeWizard(TreeAdaptor adaptor, String[] tokenNames) {
        this.adaptor = adaptor;
        this.tokenNameToTypeMap = this.computeTokenTypes(tokenNames);
    }

    public TreeWizard(String[] tokenNames) {
        this((TreeAdaptor)new CommonTreeAdaptor(), tokenNames);
    }

    public Map<String, Integer> computeTokenTypes(String[] tokenNames) {
        HashMap<String, Integer> m = new HashMap<String, Integer>();
        if (tokenNames == null) {
            return m;
        }
        for (int ttype = 4; ttype < tokenNames.length; ++ttype) {
            String name = tokenNames[ttype];
            m.put(name, ttype);
        }
        return m;
    }

    public int getTokenType(String tokenName) {
        if (this.tokenNameToTypeMap == null) {
            return 0;
        }
        Integer ttypeI = this.tokenNameToTypeMap.get(tokenName);
        if (ttypeI != null) {
            return ttypeI;
        }
        return 0;
    }

    public Map<Integer, List<Object>> index(Object t) {
        HashMap<Integer, List<Object>> m = new HashMap<Integer, List<Object>>();
        this._index(t, m);
        return m;
    }

    protected void _index(Object t, Map<Integer, List<Object>> m) {
        if (t == null) {
            return;
        }
        int ttype = this.adaptor.getType(t);
        List<Object> elements = m.get(ttype);
        if (elements == null) {
            elements = new ArrayList<Object>();
            m.put(ttype, elements);
        }
        elements.add(t);
        int n = this.adaptor.getChildCount(t);
        for (int i = 0; i < n; ++i) {
            Object child = this.adaptor.getChild(t, i);
            this._index(child, m);
        }
    }

    public List<? extends Object> find(Object t, int ttype) {
        final ArrayList nodes = new ArrayList();
        this.visit(t, ttype, (ContextVisitor)new Visitor(){

            public void visit(Object t) {
                nodes.add(t);
            }
        });
        return nodes;
    }

    public List<? extends Object> find(Object t, String pattern) {
        final ArrayList subtrees = new ArrayList();
        TreePatternLexer tokenizer = new TreePatternLexer(pattern);
        TreePatternParser parser = new TreePatternParser(tokenizer, this, new TreePatternTreeAdaptor());
        final TreePattern tpattern = (TreePattern)parser.pattern();
        if (tpattern == null || tpattern.isNil() || tpattern.getClass() == WildcardTreePattern.class) {
            return null;
        }
        int rootTokenType = tpattern.getType();
        this.visit(t, rootTokenType, new ContextVisitor(){

            public void visit(Object t, Object parent, int childIndex, Map labels) {
                if (TreeWizard.this._parse(t, tpattern, null)) {
                    subtrees.add(t);
                }
            }
        });
        return subtrees;
    }

    public Object findFirst(Object t, int ttype) {
        return null;
    }

    public Object findFirst(Object t, String pattern) {
        return null;
    }

    public void visit(Object t, int ttype, ContextVisitor visitor) {
        this._visit(t, null, 0, ttype, visitor);
    }

    protected void _visit(Object t, Object parent, int childIndex, int ttype, ContextVisitor visitor) {
        if (t == null) {
            return;
        }
        if (this.adaptor.getType(t) == ttype) {
            visitor.visit(t, parent, childIndex, null);
        }
        int n = this.adaptor.getChildCount(t);
        for (int i = 0; i < n; ++i) {
            Object child = this.adaptor.getChild(t, i);
            this._visit(child, t, i, ttype, visitor);
        }
    }

    public void visit(Object t, String pattern, final ContextVisitor visitor) {
        TreePatternLexer tokenizer = new TreePatternLexer(pattern);
        TreePatternParser parser = new TreePatternParser(tokenizer, this, new TreePatternTreeAdaptor());
        final TreePattern tpattern = (TreePattern)parser.pattern();
        if (tpattern == null || tpattern.isNil() || tpattern.getClass() == WildcardTreePattern.class) {
            return;
        }
        final HashMap labels = new HashMap();
        int rootTokenType = tpattern.getType();
        this.visit(t, rootTokenType, new ContextVisitor(){

            @Override
            public void visit(Object t, Object parent, int childIndex, Map<String, Object> unusedlabels) {
                labels.clear();
                if (TreeWizard.this._parse(t, tpattern, labels)) {
                    visitor.visit(t, parent, childIndex, labels);
                }
            }
        });
    }

    public boolean parse(Object t, String pattern, Map<String, Object> labels) {
        TreePatternLexer tokenizer = new TreePatternLexer(pattern);
        TreePatternParser parser = new TreePatternParser(tokenizer, this, new TreePatternTreeAdaptor());
        TreePattern tpattern = (TreePattern)parser.pattern();
        boolean matched = this._parse(t, tpattern, labels);
        return matched;
    }

    public boolean parse(Object t, String pattern) {
        return this.parse(t, pattern, null);
    }

    protected boolean _parse(Object t1, TreePattern tpattern, Map<String, Object> labels) {
        int n2;
        int n1;
        if (t1 == null || tpattern == null) {
            return false;
        }
        if (tpattern.getClass() != WildcardTreePattern.class) {
            if (this.adaptor.getType(t1) != tpattern.getType()) {
                return false;
            }
            if (tpattern.hasTextArg && !this.adaptor.getText(t1).equals(tpattern.getText())) {
                return false;
            }
        }
        if (tpattern.label != null && labels != null) {
            labels.put(tpattern.label, t1);
        }
        if ((n1 = this.adaptor.getChildCount(t1)) != (n2 = tpattern.getChildCount())) {
            return false;
        }
        for (int i = 0; i < n1; ++i) {
            TreePattern child2;
            Object child1 = this.adaptor.getChild(t1, i);
            if (this._parse(child1, child2 = (TreePattern)tpattern.getChild(i), labels)) continue;
            return false;
        }
        return true;
    }

    public Object create(String pattern) {
        TreePatternLexer tokenizer = new TreePatternLexer(pattern);
        TreePatternParser parser = new TreePatternParser(tokenizer, this, this.adaptor);
        Object t = parser.pattern();
        return t;
    }

    public static boolean equals(Object t1, Object t2, TreeAdaptor adaptor) {
        return TreeWizard._equals(t1, t2, adaptor);
    }

    public boolean equals(Object t1, Object t2) {
        return TreeWizard._equals(t1, t2, this.adaptor);
    }

    protected static boolean _equals(Object t1, Object t2, TreeAdaptor adaptor) {
        int n2;
        if (t1 == null || t2 == null) {
            return false;
        }
        if (adaptor.getType(t1) != adaptor.getType(t2)) {
            return false;
        }
        if (!adaptor.getText(t1).equals(adaptor.getText(t2))) {
            return false;
        }
        int n1 = adaptor.getChildCount(t1);
        if (n1 != (n2 = adaptor.getChildCount(t2))) {
            return false;
        }
        for (int i = 0; i < n1; ++i) {
            Object child2;
            Object child1 = adaptor.getChild(t1, i);
            if (TreeWizard._equals(child1, child2 = adaptor.getChild(t2, i), adaptor)) continue;
            return false;
        }
        return true;
    }

    public static class TreePatternTreeAdaptor
    extends CommonTreeAdaptor {
        public Object create(Token payload) {
            return new TreePattern(payload);
        }
    }

    public static class WildcardTreePattern
    extends TreePattern {
        public WildcardTreePattern(Token payload) {
            super(payload);
        }
    }

    public static class TreePattern
    extends CommonTree {
        public String label;
        public boolean hasTextArg;

        public TreePattern(Token payload) {
            super(payload);
        }

        public String toString() {
            if (this.label != null) {
                return "%" + this.label + ":" + super.toString();
            }
            return super.toString();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class Visitor
    implements ContextVisitor {
        @Override
        public void visit(Object t, Object parent, int childIndex, Map<String, Object> labels) {
            this.visit(t);
        }

        public abstract void visit(Object var1);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static interface ContextVisitor {
        public void visit(Object var1, Object var2, int var3, Map<String, Object> var4);
    }
}

