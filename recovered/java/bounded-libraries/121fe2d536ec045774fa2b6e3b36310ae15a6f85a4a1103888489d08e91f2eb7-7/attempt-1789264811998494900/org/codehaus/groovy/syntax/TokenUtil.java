/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.syntax;

import java.util.Optional;
import org.codehaus.groovy.syntax.Token;

public class TokenUtil {
    private TokenUtil() {
    }

    public static Optional<Token> asAssignment(int op) {
        switch (op) {
            case 250: 
            case 251: 
            case 252: {
                return Optional.of(Token.newSymbol(210, -1, -1));
            }
            case 260: 
            case 261: 
            case 262: {
                return Optional.of(Token.newSymbol(211, -1, -1));
            }
        }
        return Optional.empty();
    }

    public static int removeAssignment(int op) {
        switch (op) {
            case 210: {
                return 200;
            }
            case 211: {
                return 201;
            }
            case 212: {
                return 202;
            }
            case 285: {
                return 280;
            }
            case 286: {
                return 281;
            }
            case 287: {
                return 282;
            }
            case 166: {
                return 162;
            }
            case 168: {
                return 164;
            }
            case 215: {
                return 205;
            }
            case 213: {
                return 203;
            }
            case 214: {
                return 204;
            }
            case 216: {
                return 206;
            }
            case 350: {
                return 340;
            }
            case 351: {
                return 341;
            }
            case 352: {
                return 342;
            }
        }
        return op;
    }
}

