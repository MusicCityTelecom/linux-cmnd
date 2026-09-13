/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4.util;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;
import org.apache.groovy.parser.antlr4.GroovyParser;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class PositionConfigureUtils {
    public static <T extends ASTNode> T configureAST(T astNode, GroovyParser.GroovyParserRuleContext ctx) {
        Token start = ctx.getStart();
        Token stop = ctx.getStop();
        astNode.setLineNumber(start.getLine());
        astNode.setColumnNumber(start.getCharPositionInLine() + 1);
        PositionConfigureUtils.configureEndPosition(astNode, stop);
        return astNode;
    }

    public static Tuple2<Integer, Integer> endPosition(Token token) {
        String stopText = token.getText();
        int stopTextLength = 0;
        int newLineCnt = 0;
        if (null != stopText) {
            stopTextLength = stopText.length();
            newLineCnt = (int)StringUtils.countChar(stopText, '\n');
        }
        if (0 == newLineCnt) {
            return Tuple.tuple(token.getLine(), token.getCharPositionInLine() + 1 + token.getText().length());
        }
        return Tuple.tuple(token.getLine() + newLineCnt, stopTextLength - stopText.lastIndexOf(10));
    }

    public static <T extends ASTNode> T configureAST(T astNode, TerminalNode terminalNode) {
        return PositionConfigureUtils.configureAST(astNode, terminalNode.getSymbol());
    }

    public static <T extends ASTNode> T configureAST(T astNode, Token token) {
        astNode.setLineNumber(token.getLine());
        astNode.setColumnNumber(token.getCharPositionInLine() + 1);
        astNode.setLastLineNumber(token.getLine());
        astNode.setLastColumnNumber(token.getCharPositionInLine() + 1 + token.getText().length());
        return astNode;
    }

    public static <T extends ASTNode> T configureAST(T astNode, ASTNode source) {
        astNode.setLineNumber(source.getLineNumber());
        astNode.setColumnNumber(source.getColumnNumber());
        astNode.setLastLineNumber(source.getLastLineNumber());
        astNode.setLastColumnNumber(source.getLastColumnNumber());
        return astNode;
    }

    public static <T extends ASTNode> T configureAST(T astNode, GroovyParser.GroovyParserRuleContext ctx, ASTNode initialStop) {
        Token start = ctx.getStart();
        astNode.setLineNumber(start.getLine());
        astNode.setColumnNumber(start.getCharPositionInLine() + 1);
        if (DefaultGroovyMethods.asBoolean(initialStop)) {
            astNode.setLastLineNumber(initialStop.getLastLineNumber());
            astNode.setLastColumnNumber(initialStop.getLastColumnNumber());
        } else {
            Token stop = ctx.getStop();
            PositionConfigureUtils.configureEndPosition(astNode, stop);
        }
        return astNode;
    }

    public static <T extends ASTNode> void configureEndPosition(T astNode, Token token) {
        Tuple2<Integer, Integer> endPosition = PositionConfigureUtils.endPosition(token);
        astNode.setLastLineNumber(endPosition.getV1());
        astNode.setLastColumnNumber(endPosition.getV2());
    }

    public static <T extends ASTNode> T configureAST(T astNode, ASTNode start, ASTNode stop) {
        astNode.setLineNumber(start.getLineNumber());
        astNode.setColumnNumber(start.getColumnNumber());
        if (DefaultGroovyMethods.asBoolean(stop)) {
            astNode.setLastLineNumber(stop.getLastLineNumber());
            astNode.setLastColumnNumber(stop.getLastColumnNumber());
        } else {
            astNode.setLastLineNumber(start.getLastLineNumber());
            astNode.setLastColumnNumber(start.getLastColumnNumber());
        }
        return astNode;
    }
}

