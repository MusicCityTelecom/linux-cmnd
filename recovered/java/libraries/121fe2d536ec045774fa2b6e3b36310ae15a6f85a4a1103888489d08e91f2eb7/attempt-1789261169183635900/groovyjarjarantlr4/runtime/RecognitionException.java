/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.PositionTrackingStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;

public class RecognitionException
extends Exception {
    public transient IntStream input;
    public int index;
    public Token token;
    public Object node;
    public int c;
    public int line;
    public int charPositionInLine;
    public boolean approximateLineInfo;

    public RecognitionException() {
    }

    public RecognitionException(IntStream input) {
        this.input = input;
        this.index = input.index();
        if (input instanceof TokenStream) {
            this.token = ((TokenStream)input).LT(1);
            this.line = this.token.getLine();
            this.charPositionInLine = this.token.getCharPositionInLine();
        }
        if (input instanceof TreeNodeStream) {
            this.extractInformationFromTreeNodeStream(input);
        } else if (input instanceof CharStream) {
            this.c = input.LA(1);
            this.line = ((CharStream)input).getLine();
            this.charPositionInLine = ((CharStream)input).getCharPositionInLine();
        } else {
            this.c = input.LA(1);
        }
    }

    protected void extractInformationFromTreeNodeStream(IntStream input) {
        TreeAdaptor adaptor;
        Token payload;
        TreeNodeStream nodes = (TreeNodeStream)input;
        this.node = nodes.LT(1);
        Object positionNode = null;
        if (nodes instanceof PositionTrackingStream && (positionNode = (Object)((PositionTrackingStream)((Object)nodes)).getKnownPositionElement(false)) == null) {
            positionNode = ((PositionTrackingStream)((Object)nodes)).getKnownPositionElement(true);
            this.approximateLineInfo = positionNode != null;
        }
        if ((payload = (adaptor = nodes.getTreeAdaptor()).getToken(positionNode != null ? positionNode : this.node)) != null) {
            this.token = payload;
            if (payload.getLine() <= 0) {
                int i = -1;
                Object priorNode = nodes.LT(i);
                while (priorNode != null) {
                    Token priorPayload = adaptor.getToken(priorNode);
                    if (priorPayload != null && priorPayload.getLine() > 0) {
                        this.line = priorPayload.getLine();
                        this.charPositionInLine = priorPayload.getCharPositionInLine();
                        this.approximateLineInfo = true;
                        break;
                    }
                    --i;
                    try {
                        priorNode = nodes.LT(i);
                    }
                    catch (UnsupportedOperationException ex) {
                        priorNode = null;
                    }
                }
            } else {
                this.line = payload.getLine();
                this.charPositionInLine = payload.getCharPositionInLine();
            }
        } else if (this.node instanceof Tree) {
            this.line = ((Tree)this.node).getLine();
            this.charPositionInLine = ((Tree)this.node).getCharPositionInLine();
            if (this.node instanceof CommonTree) {
                this.token = ((CommonTree)this.node).token;
            }
        } else {
            int type = adaptor.getType(this.node);
            String text = adaptor.getText(this.node);
            this.token = new CommonToken(type, text);
        }
    }

    public int getUnexpectedType() {
        if (this.input instanceof TokenStream) {
            return this.token.getType();
        }
        if (this.input instanceof TreeNodeStream) {
            TreeNodeStream nodes = (TreeNodeStream)this.input;
            TreeAdaptor adaptor = nodes.getTreeAdaptor();
            return adaptor.getType(this.node);
        }
        return this.c;
    }
}

