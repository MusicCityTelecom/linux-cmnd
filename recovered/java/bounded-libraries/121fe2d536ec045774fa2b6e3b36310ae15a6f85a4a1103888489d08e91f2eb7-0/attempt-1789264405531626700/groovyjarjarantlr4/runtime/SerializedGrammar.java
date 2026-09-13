/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SerializedGrammar {
    public static final String COOKIE = "$ANTLR";
    public static final int FORMAT_VERSION = 1;
    public String name;
    public char type;
    public List<? extends Rule> rules;

    public SerializedGrammar(String filename) throws IOException {
        System.out.println("loading " + filename);
        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bos = new BufferedInputStream(fis);
        DataInputStream in = new DataInputStream(bos);
        this.readFile(in);
        in.close();
    }

    protected void readFile(DataInputStream in) throws IOException {
        String grammarName;
        char grammarType;
        String cookie = this.readString(in);
        if (!cookie.equals(COOKIE)) {
            throw new IOException("not a serialized grammar file");
        }
        byte version = in.readByte();
        this.type = grammarType = (char)in.readByte();
        this.name = grammarName = this.readString(in);
        System.out.println(grammarType + " grammar " + grammarName);
        short numRules = in.readShort();
        System.out.println("num rules = " + numRules);
        this.rules = this.readRules(in, numRules);
    }

    protected List<? extends Rule> readRules(DataInputStream in, int numRules) throws IOException {
        ArrayList<Rule> rules = new ArrayList<Rule>();
        for (int i = 0; i < numRules; ++i) {
            Rule r = this.readRule(in);
            rules.add(r);
        }
        return rules;
    }

    protected Rule readRule(DataInputStream in) throws IOException {
        byte R = in.readByte();
        if (R != 82) {
            throw new IOException("missing R on start of rule");
        }
        String name = this.readString(in);
        System.out.println("rule: " + name);
        byte B = in.readByte();
        Block b = this.readBlock(in);
        byte period = in.readByte();
        if (period != 46) {
            throw new IOException("missing . on end of rule");
        }
        return new Rule(name, b);
    }

    protected Block readBlock(DataInputStream in) throws IOException {
        int nalts = in.readShort();
        List[] alts = new List[nalts];
        for (int i = 0; i < nalts; ++i) {
            List<Node> alt;
            alts[i] = alt = this.readAlt(in);
        }
        return new Block(alts);
    }

    protected List<Node> readAlt(DataInputStream in) throws IOException {
        ArrayList<Node> alt = new ArrayList<Node>();
        byte A = in.readByte();
        if (A != 65) {
            throw new IOException("missing A on start of alt");
        }
        byte cmd = in.readByte();
        while (cmd != 59) {
            switch (cmd) {
                case 116: {
                    short ttype = in.readShort();
                    alt.add(new TokenRef(ttype));
                    break;
                }
                case 114: {
                    short ruleIndex = in.readShort();
                    alt.add(new RuleRef(ruleIndex));
                    break;
                }
                case 46: {
                    break;
                }
                case 45: {
                    char from = in.readChar();
                    char to = in.readChar();
                    break;
                }
                case 126: {
                    short notThisTokenType = in.readShort();
                    break;
                }
                case 66: {
                    Block b = this.readBlock(in);
                    alt.add(b);
                }
            }
            cmd = in.readByte();
        }
        return alt;
    }

    protected String readString(DataInputStream in) throws IOException {
        byte c = in.readByte();
        StringBuilder buf = new StringBuilder();
        while (c != 59) {
            buf.append((char)c);
            c = in.readByte();
        }
        return buf.toString();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.type).append(" grammar ").append(this.name);
        buf.append(this.rules);
        return buf.toString();
    }

    protected class RuleRef
    extends Node {
        int ruleIndex;

        public RuleRef(int ruleIndex) {
            this.ruleIndex = ruleIndex;
        }

        public String toString() {
            return String.valueOf(this.ruleIndex);
        }
    }

    protected class TokenRef
    extends Node {
        int ttype;

        public TokenRef(int ttype) {
            this.ttype = ttype;
        }

        public String toString() {
            return String.valueOf(this.ttype);
        }
    }

    protected class Block
    extends Node {
        List[] alts;

        public Block(List[] alts) {
            this.alts = alts;
        }

        public String toString() {
            StringBuilder buf = new StringBuilder();
            buf.append("(");
            for (int i = 0; i < this.alts.length; ++i) {
                List alt = this.alts[i];
                if (i > 0) {
                    buf.append("|");
                }
                buf.append(alt.toString());
            }
            buf.append(")");
            return buf.toString();
        }
    }

    protected abstract class Node {
        protected Node() {
        }

        public abstract String toString();
    }

    protected class Rule {
        String name;
        Block block;

        public Rule(String name, Block block) {
            this.name = name;
            this.block = block;
        }

        public String toString() {
            return this.name + ":" + this.block;
        }
    }
}

