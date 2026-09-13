/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 */
package com.sun.el.parser;

import com.sun.el.parser.AstAnd;
import com.sun.el.parser.AstBracketSuffix;
import com.sun.el.parser.AstChoice;
import com.sun.el.parser.AstCompositeExpression;
import com.sun.el.parser.AstDeferredExpression;
import com.sun.el.parser.AstDiv;
import com.sun.el.parser.AstDynamicExpression;
import com.sun.el.parser.AstEmpty;
import com.sun.el.parser.AstEqual;
import com.sun.el.parser.AstFalse;
import com.sun.el.parser.AstFloatingPoint;
import com.sun.el.parser.AstFunction;
import com.sun.el.parser.AstGreaterThan;
import com.sun.el.parser.AstGreaterThanEqual;
import com.sun.el.parser.AstIdentifier;
import com.sun.el.parser.AstInteger;
import com.sun.el.parser.AstLessThan;
import com.sun.el.parser.AstLessThanEqual;
import com.sun.el.parser.AstLiteralExpression;
import com.sun.el.parser.AstMethodSuffix;
import com.sun.el.parser.AstMinus;
import com.sun.el.parser.AstMod;
import com.sun.el.parser.AstMult;
import com.sun.el.parser.AstNegative;
import com.sun.el.parser.AstNot;
import com.sun.el.parser.AstNotEqual;
import com.sun.el.parser.AstNull;
import com.sun.el.parser.AstOr;
import com.sun.el.parser.AstPlus;
import com.sun.el.parser.AstPropertySuffix;
import com.sun.el.parser.AstString;
import com.sun.el.parser.AstTrue;
import com.sun.el.parser.AstValue;
import com.sun.el.parser.ELParserConstants;
import com.sun.el.parser.ELParserTokenManager;
import com.sun.el.parser.ELParserTreeConstants;
import com.sun.el.parser.JJTELParserState;
import com.sun.el.parser.Node;
import com.sun.el.parser.ParseException;
import com.sun.el.parser.SimpleCharStream;
import com.sun.el.parser.Token;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELException;

public class ELParser
implements ELParserTreeConstants,
ELParserConstants {
    protected JJTELParserState jjtree = new JJTELParserState();
    public ELParserTokenManager token_source;
    SimpleCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos;
    private Token jj_lastpos;
    private int jj_la;
    private int jj_gen;
    private final int[] jj_la1 = new int[37];
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private final JJCalls[] jj_2_rtns = new JJCalls[3];
    private boolean jj_rescan = false;
    private int jj_gc = 0;
    private final LookaheadSuccess jj_ls = new LookaheadSuccess();
    private List jj_expentries = new ArrayList();
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int[] jj_lasttokens = new int[100];
    private int jj_endpos;

    public static Node parse(String ref) throws ELException {
        try {
            return new ELParser(new StringReader(ref)).CompositeExpression();
        }
        catch (ParseException pe) {
            throw new ELException(pe.getMessage());
        }
    }

    public final AstCompositeExpression CompositeExpression() throws ParseException {
        AstCompositeExpression jjtn000 = new AstCompositeExpression(0);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            block19: {
                block13: while (true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 1: 
                        case 2: 
                        case 3: {
                            break;
                        }
                        default: {
                            this.jj_la1[0] = this.jj_gen;
                            break block19;
                        }
                    }
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 3: {
                            this.DeferredExpression();
                            continue block13;
                        }
                        case 2: {
                            this.DynamicExpression();
                            continue block13;
                        }
                        case 1: {
                            this.LiteralExpression();
                            continue block13;
                        }
                    }
                    break;
                }
                this.jj_la1[1] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
            this.jj_consume_token(0);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            AstCompositeExpression astCompositeExpression = jjtn000;
            return astCompositeExpression;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void LiteralExpression() throws ParseException {
        AstLiteralExpression jjtn000 = new AstLiteralExpression(1);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t = null;
        try {
            t = this.jj_consume_token(1);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void DeferredExpression() throws ParseException {
        AstDeferredExpression jjtn000 = new AstDeferredExpression(2);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(3);
            this.Expression();
            this.jj_consume_token(17);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void DynamicExpression() throws ParseException {
        AstDynamicExpression jjtn000 = new AstDynamicExpression(3);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(2);
            this.Expression();
            this.jj_consume_token(17);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void Expression() throws ParseException {
        this.Choice();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void Choice() throws ParseException {
        this.Or();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 48: {
                    break;
                }
                default: {
                    this.jj_la1[2] = this.jj_gen;
                    return;
                }
            }
            this.jj_consume_token(48);
            this.Choice();
            this.jj_consume_token(23);
            AstChoice jjtn001 = new AstChoice(5);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            try {
                this.Choice();
                continue;
            }
            catch (Throwable jjte001) {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (!(jjte001 instanceof ParseException)) throw (Error)jjte001;
                throw (ParseException)jjte001;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 3);
                continue;
            }
            break;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void Or() throws ParseException {
        this.And();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 41: 
                case 42: {
                    break;
                }
                default: {
                    this.jj_la1[3] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 41: {
                    this.jj_consume_token(41);
                    break;
                }
                case 42: {
                    this.jj_consume_token(42);
                    break;
                }
                default: {
                    this.jj_la1[4] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            AstOr jjtn001 = new AstOr(6);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            try {
                this.And();
                continue;
            }
            catch (Throwable jjte001) {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (!(jjte001 instanceof ParseException)) throw (Error)jjte001;
                throw (ParseException)jjte001;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                continue;
            }
            break;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void And() throws ParseException {
        this.Equality();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 39: 
                case 40: {
                    break;
                }
                default: {
                    this.jj_la1[5] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 39: {
                    this.jj_consume_token(39);
                    break;
                }
                case 40: {
                    this.jj_consume_token(40);
                    break;
                }
                default: {
                    this.jj_la1[6] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            AstAnd jjtn001 = new AstAnd(7);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            try {
                this.Equality();
                continue;
            }
            catch (Throwable jjte001) {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (!(jjte001 instanceof ParseException)) throw (Error)jjte001;
                throw (ParseException)jjte001;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                continue;
            }
            break;
        }
    }

    public final void Equality() throws ParseException {
        this.Compare();
        block25: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 33: 
                case 34: 
                case 35: 
                case 36: {
                    break;
                }
                default: {
                    this.jj_la1[7] = this.jj_gen;
                    break block25;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 33: 
                case 34: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 33: {
                            this.jj_consume_token(33);
                            break;
                        }
                        case 34: {
                            this.jj_consume_token(34);
                            break;
                        }
                        default: {
                            this.jj_la1[8] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstEqual jjtn001 = new AstEqual(8);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Compare();
                        continue block25;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block25;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block25;
                    }
                }
                case 35: 
                case 36: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 35: {
                            this.jj_consume_token(35);
                            break;
                        }
                        case 36: {
                            this.jj_consume_token(36);
                            break;
                        }
                        default: {
                            this.jj_la1[9] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstNotEqual jjtn002 = new AstNotEqual(9);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Compare();
                        continue block25;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block25;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block25;
                    }
                }
                default: {
                    this.jj_la1[10] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    public final void Compare() throws ParseException {
        this.Math();
        block45: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: {
                    break;
                }
                default: {
                    this.jj_la1[11] = this.jj_gen;
                    break block45;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 27: 
                case 28: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 27: {
                            this.jj_consume_token(27);
                            break;
                        }
                        case 28: {
                            this.jj_consume_token(28);
                            break;
                        }
                        default: {
                            this.jj_la1[12] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstLessThan jjtn001 = new AstLessThan(10);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Math();
                        continue block45;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block45;
                    }
                }
                case 25: 
                case 26: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 25: {
                            this.jj_consume_token(25);
                            break;
                        }
                        case 26: {
                            this.jj_consume_token(26);
                            break;
                        }
                        default: {
                            this.jj_la1[13] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstGreaterThan jjtn002 = new AstGreaterThan(11);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Math();
                        continue block45;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block45;
                    }
                }
                case 31: 
                case 32: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 31: {
                            this.jj_consume_token(31);
                            break;
                        }
                        case 32: {
                            this.jj_consume_token(32);
                            break;
                        }
                        default: {
                            this.jj_la1[14] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstLessThanEqual jjtn003 = new AstLessThanEqual(12);
                    boolean jjtc003 = true;
                    this.jjtree.openNodeScope(jjtn003);
                    try {
                        this.Math();
                        continue block45;
                    }
                    catch (Throwable jjte003) {
                        if (jjtc003) {
                            this.jjtree.clearNodeScope(jjtn003);
                            jjtc003 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte003 instanceof RuntimeException) {
                            throw (RuntimeException)jjte003;
                        }
                        if (jjte003 instanceof ParseException) {
                            throw (ParseException)jjte003;
                        }
                        throw (Error)jjte003;
                    }
                    finally {
                        if (!jjtc003) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn003, 2);
                        continue block45;
                    }
                }
                case 29: 
                case 30: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 29: {
                            this.jj_consume_token(29);
                            break;
                        }
                        case 30: {
                            this.jj_consume_token(30);
                            break;
                        }
                        default: {
                            this.jj_la1[15] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstGreaterThanEqual jjtn004 = new AstGreaterThanEqual(13);
                    boolean jjtc004 = true;
                    this.jjtree.openNodeScope(jjtn004);
                    try {
                        this.Math();
                        continue block45;
                    }
                    catch (Throwable jjte004) {
                        if (jjtc004) {
                            this.jjtree.clearNodeScope(jjtn004);
                            jjtc004 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte004 instanceof RuntimeException) {
                            throw (RuntimeException)jjte004;
                        }
                        if (jjte004 instanceof ParseException) {
                            throw (ParseException)jjte004;
                        }
                        throw (Error)jjte004;
                    }
                    finally {
                        if (!jjtc004) continue block45;
                        this.jjtree.closeNodeScope((Node)jjtn004, 2);
                        continue block45;
                    }
                }
                default: {
                    this.jj_la1[16] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    public final void Math() throws ParseException {
        this.Multiplication();
        block17: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 46: 
                case 47: {
                    break;
                }
                default: {
                    this.jj_la1[17] = this.jj_gen;
                    break block17;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 46: {
                    this.jj_consume_token(46);
                    AstPlus jjtn001 = new AstPlus(14);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Multiplication();
                        continue block17;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block17;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block17;
                    }
                }
                case 47: {
                    this.jj_consume_token(47);
                    AstMinus jjtn002 = new AstMinus(15);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Multiplication();
                        continue block17;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block17;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block17;
                    }
                }
                default: {
                    this.jj_la1[18] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    public final void Multiplication() throws ParseException {
        this.Unary();
        block31: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 45: 
                case 49: 
                case 50: 
                case 51: 
                case 52: {
                    break;
                }
                default: {
                    this.jj_la1[19] = this.jj_gen;
                    break block31;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 45: {
                    this.jj_consume_token(45);
                    AstMult jjtn001 = new AstMult(16);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        this.Unary();
                        continue block31;
                    }
                    catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw (RuntimeException)jjte001;
                        }
                        if (jjte001 instanceof ParseException) {
                            throw (ParseException)jjte001;
                        }
                        throw (Error)jjte001;
                    }
                    finally {
                        if (!jjtc001) continue block31;
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        continue block31;
                    }
                }
                case 49: 
                case 50: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 49: {
                            this.jj_consume_token(49);
                            break;
                        }
                        case 50: {
                            this.jj_consume_token(50);
                            break;
                        }
                        default: {
                            this.jj_la1[20] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstDiv jjtn002 = new AstDiv(17);
                    boolean jjtc002 = true;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        this.Unary();
                        continue block31;
                    }
                    catch (Throwable jjte002) {
                        if (jjtc002) {
                            this.jjtree.clearNodeScope(jjtn002);
                            jjtc002 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw (RuntimeException)jjte002;
                        }
                        if (jjte002 instanceof ParseException) {
                            throw (ParseException)jjte002;
                        }
                        throw (Error)jjte002;
                    }
                    finally {
                        if (!jjtc002) continue block31;
                        this.jjtree.closeNodeScope((Node)jjtn002, 2);
                        continue block31;
                    }
                }
                case 51: 
                case 52: {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 51: {
                            this.jj_consume_token(51);
                            break;
                        }
                        case 52: {
                            this.jj_consume_token(52);
                            break;
                        }
                        default: {
                            this.jj_la1[21] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                    AstMod jjtn003 = new AstMod(18);
                    boolean jjtc003 = true;
                    this.jjtree.openNodeScope(jjtn003);
                    try {
                        this.Unary();
                        continue block31;
                    }
                    catch (Throwable jjte003) {
                        if (jjtc003) {
                            this.jjtree.clearNodeScope(jjtn003);
                            jjtc003 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte003 instanceof RuntimeException) {
                            throw (RuntimeException)jjte003;
                        }
                        if (jjte003 instanceof ParseException) {
                            throw (ParseException)jjte003;
                        }
                        throw (Error)jjte003;
                    }
                    finally {
                        if (!jjtc003) continue block31;
                        this.jjtree.closeNodeScope((Node)jjtn003, 2);
                        continue block31;
                    }
                }
                default: {
                    this.jj_la1[22] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            break;
        }
    }

    public final void Unary() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 47: {
                this.jj_consume_token(47);
                AstNegative jjtn001 = new AstNegative(19);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                try {
                    this.Unary();
                    break;
                }
                catch (Throwable jjte001) {
                    if (jjtc001) {
                        this.jjtree.clearNodeScope(jjtn001);
                        jjtc001 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte001 instanceof RuntimeException) {
                        throw (RuntimeException)jjte001;
                    }
                    if (jjte001 instanceof ParseException) {
                        throw (ParseException)jjte001;
                    }
                    throw (Error)jjte001;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, true);
                    }
                }
            }
            case 37: 
            case 38: {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 37: {
                        this.jj_consume_token(37);
                        break;
                    }
                    case 38: {
                        this.jj_consume_token(38);
                        break;
                    }
                    default: {
                        this.jj_la1[23] = this.jj_gen;
                        this.jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
                AstNot jjtn002 = new AstNot(20);
                boolean jjtc002 = true;
                this.jjtree.openNodeScope(jjtn002);
                try {
                    this.Unary();
                    break;
                }
                catch (Throwable jjte002) {
                    if (jjtc002) {
                        this.jjtree.clearNodeScope(jjtn002);
                        jjtc002 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte002 instanceof RuntimeException) {
                        throw (RuntimeException)jjte002;
                    }
                    if (jjte002 instanceof ParseException) {
                        throw (ParseException)jjte002;
                    }
                    throw (Error)jjte002;
                }
                finally {
                    if (jjtc002) {
                        this.jjtree.closeNodeScope((Node)jjtn002, true);
                    }
                }
            }
            case 43: {
                this.jj_consume_token(43);
                AstEmpty jjtn003 = new AstEmpty(21);
                boolean jjtc003 = true;
                this.jjtree.openNodeScope(jjtn003);
                try {
                    this.Unary();
                    break;
                }
                catch (Throwable jjte003) {
                    if (jjtc003) {
                        this.jjtree.clearNodeScope(jjtn003);
                        jjtc003 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte003 instanceof RuntimeException) {
                        throw (RuntimeException)jjte003;
                    }
                    if (jjte003 instanceof ParseException) {
                        throw (ParseException)jjte003;
                    }
                    throw (Error)jjte003;
                }
                finally {
                    if (jjtc003) {
                        this.jjtree.closeNodeScope((Node)jjtn003, true);
                    }
                }
            }
            case 9: 
            case 10: 
            case 12: 
            case 14: 
            case 15: 
            case 16: 
            case 19: 
            case 53: {
                this.Value();
                break;
            }
            default: {
                this.jj_la1[24] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    public final void Value() throws ParseException {
        AstValue jjtn001 = new AstValue(22);
        boolean jjtc001 = true;
        this.jjtree.openNodeScope(jjtn001);
        try {
            this.ValuePrefix();
            block7: while (true) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 18: 
                    case 21: {
                        break;
                    }
                    default: {
                        this.jj_la1[25] = this.jj_gen;
                        break block7;
                    }
                }
                this.ValueSuffix();
            }
            if (jjtc001) {
                this.jjtree.closeNodeScope((Node)jjtn001, this.jjtree.nodeArity() > 1);
            }
        }
        catch (Throwable jjte001) {
            try {
                if (jjtc001) {
                    this.jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte001 instanceof RuntimeException) {
                    throw (RuntimeException)jjte001;
                }
                if (jjte001 instanceof ParseException) {
                    throw (ParseException)jjte001;
                }
                throw (Error)jjte001;
            }
            catch (Throwable throwable) {
                if (jjtc001) {
                    this.jjtree.closeNodeScope((Node)jjtn001, this.jjtree.nodeArity() > 1);
                }
                throw throwable;
            }
        }
    }

    public final void ValuePrefix() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 9: 
            case 10: 
            case 12: 
            case 14: 
            case 15: 
            case 16: {
                this.Literal();
                break;
            }
            case 19: 
            case 53: {
                this.NonLiteral();
                break;
            }
            default: {
                this.jj_la1[26] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    public final void ValueSuffix() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 18: {
                this.DotSuffix();
                break;
            }
            case 21: {
                this.BracketSuffix();
                break;
            }
            default: {
                this.jj_la1[27] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    public final void DotSuffix() throws ParseException {
        this.jj_consume_token(18);
        if (this.jj_2_1(2)) {
            this.MethodSuffix();
        } else if (this.jj_2_2(2)) {
            this.PropertySuffix();
        } else {
            this.jj_consume_token(-1);
            throw new ParseException();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void PropertySuffix() throws ParseException {
        AstPropertySuffix jjtn000 = new AstPropertySuffix(23);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t = null;
        try {
            t = this.jj_consume_token(53);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void MethodSuffix() throws ParseException {
        AstMethodSuffix jjtn000 = new AstMethodSuffix(24);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t = null;
        try {
            t = this.jj_consume_token(53);
            jjtn000.setImage(t.image);
            this.jj_consume_token(19);
            block2 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: 
                case 10: 
                case 12: 
                case 14: 
                case 15: 
                case 16: 
                case 19: 
                case 37: 
                case 38: 
                case 43: 
                case 47: 
                case 53: {
                    this.Expression();
                    while (true) {
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 24: {
                                break;
                            }
                            default: {
                                this.jj_la1[28] = this.jj_gen;
                                break block2;
                            }
                        }
                        this.jj_consume_token(24);
                        this.Expression();
                    }
                }
                default: {
                    this.jj_la1[29] = this.jj_gen;
                }
            }
            this.jj_consume_token(20);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void BracketSuffix() throws ParseException {
        AstBracketSuffix jjtn000 = new AstBracketSuffix(25);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(21);
            this.Expression();
            this.jj_consume_token(22);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void NonLiteral() throws ParseException {
        block0 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 19: {
                this.jj_consume_token(19);
                this.Expression();
                this.jj_consume_token(20);
                break;
            }
            default: {
                this.jj_la1[30] = this.jj_gen;
                if (this.jj_2_3(4)) {
                    this.Function();
                    break;
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 53: {
                        this.Identifier();
                        break block0;
                    }
                }
                this.jj_la1[31] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Identifier() throws ParseException {
        AstIdentifier jjtn000 = new AstIdentifier(26);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t = null;
        try {
            t = this.jj_consume_token(53);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void Function() throws ParseException {
        AstFunction jjtn000 = new AstFunction(27);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t0 = null;
        Token t1 = null;
        try {
            t0 = this.jj_consume_token(53);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 23: {
                    this.jj_consume_token(23);
                    t1 = this.jj_consume_token(53);
                    break;
                }
                default: {
                    this.jj_la1[32] = this.jj_gen;
                }
            }
            if (t1 != null) {
                jjtn000.setPrefix(t0.image);
                jjtn000.setLocalName(t1.image);
            } else {
                jjtn000.setLocalName(t0.image);
            }
            this.jj_consume_token(19);
            block5 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 9: 
                case 10: 
                case 12: 
                case 14: 
                case 15: 
                case 16: 
                case 19: 
                case 37: 
                case 38: 
                case 43: 
                case 47: 
                case 53: {
                    this.Expression();
                    while (true) {
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 24: {
                                break;
                            }
                            default: {
                                this.jj_la1[33] = this.jj_gen;
                                break block5;
                            }
                        }
                        this.jj_consume_token(24);
                        this.Expression();
                    }
                }
                default: {
                    this.jj_la1[34] = this.jj_gen;
                }
            }
            this.jj_consume_token(20);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    public final void Literal() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 14: 
            case 15: {
                this.Boolean();
                break;
            }
            case 10: {
                this.FloatingPoint();
                break;
            }
            case 9: {
                this.Integer();
                break;
            }
            case 12: {
                this.String();
                break;
            }
            case 16: {
                this.Null();
                break;
            }
            default: {
                this.jj_la1[35] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Boolean() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 14: {
                AstTrue jjtn001 = new AstTrue(28);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                try {
                    this.jj_consume_token(14);
                    break;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, true);
                    }
                }
            }
            case 15: {
                AstFalse jjtn002 = new AstFalse(29);
                boolean jjtc002 = true;
                this.jjtree.openNodeScope(jjtn002);
                try {
                    this.jj_consume_token(15);
                    break;
                }
                finally {
                    if (jjtc002) {
                        this.jjtree.closeNodeScope((Node)jjtn002, true);
                    }
                }
            }
            default: {
                this.jj_la1[36] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void FloatingPoint() throws ParseException {
        AstFloatingPoint jjtn000 = new AstFloatingPoint(30);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t = null;
        try {
            t = this.jj_consume_token(10);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Integer() throws ParseException {
        AstInteger jjtn000 = new AstInteger(31);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t = null;
        try {
            t = this.jj_consume_token(9);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void String() throws ParseException {
        AstString jjtn000 = new AstString(32);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        Token t = null;
        try {
            t = this.jj_consume_token(12);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            jjtn000.setImage(t.image);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Null() throws ParseException {
        AstNull jjtn000 = new AstNull(33);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        try {
            this.jj_consume_token(16);
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_1(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_1();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(0, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_2(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_2();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(1, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean jj_2_3(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_3();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(2, xla);
        }
    }

    private boolean jj_3R_73() {
        return this.jj_scan_token(53);
    }

    private boolean jj_3R_48() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(51)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(52)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_17() {
        return this.jj_3R_19();
    }

    private boolean jj_3R_47() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(49)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(50)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_46() {
        return this.jj_scan_token(45);
    }

    private boolean jj_3R_39() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_46()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_47()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_48()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_65() {
        return this.jj_3R_73();
    }

    private boolean jj_3_3() {
        return this.jj_3R_14();
    }

    private boolean jj_3R_64() {
        if (this.jj_scan_token(19)) {
            return true;
        }
        return this.jj_3R_17();
    }

    private boolean jj_3R_56() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_64()) {
            this.jj_scanpos = xsp;
            if (this.jj_3_3()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_65()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_72() {
        return this.jj_scan_token(16);
    }

    private boolean jj_3R_32() {
        Token xsp;
        if (this.jj_3R_38()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_39());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_41() {
        return this.jj_scan_token(47);
    }

    private boolean jj_3R_33() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_40()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_41()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_40() {
        return this.jj_scan_token(46);
    }

    private boolean jj_3R_67() {
        return this.jj_scan_token(21);
    }

    private boolean jj_3R_71() {
        return this.jj_scan_token(12);
    }

    private boolean jj_3R_28() {
        Token xsp;
        if (this.jj_3R_32()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_33());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_12() {
        if (this.jj_scan_token(53)) {
            return true;
        }
        return this.jj_scan_token(19);
    }

    private boolean jj_3R_70() {
        return this.jj_scan_token(9);
    }

    private boolean jj_3R_37() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(29)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(30)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_36() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(31)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(32)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3_2() {
        return this.jj_3R_13();
    }

    private boolean jj_3_1() {
        return this.jj_3R_12();
    }

    private boolean jj_3R_35() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(25)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(26)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_29() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_34()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_35()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_36()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_37()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_34() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(27)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(28)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_13() {
        return this.jj_scan_token(53);
    }

    private boolean jj_3R_69() {
        return this.jj_scan_token(10);
    }

    private boolean jj_3R_75() {
        return this.jj_scan_token(15);
    }

    private boolean jj_3R_58() {
        return this.jj_3R_67();
    }

    private boolean jj_3R_26() {
        Token xsp;
        if (this.jj_3R_28()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_29());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_31() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(35)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(36)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_18() {
        return this.jj_scan_token(24);
    }

    private boolean jj_3R_74() {
        return this.jj_scan_token(14);
    }

    private boolean jj_3R_68() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_74()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_75()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_66() {
        return this.jj_scan_token(18);
    }

    private boolean jj_3R_27() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_30()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_31()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_30() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(33)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(34)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_63() {
        return this.jj_3R_72();
    }

    private boolean jj_3R_62() {
        return this.jj_3R_71();
    }

    private boolean jj_3R_25() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(39)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(40)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_61() {
        return this.jj_3R_70();
    }

    private boolean jj_3R_60() {
        return this.jj_3R_69();
    }

    private boolean jj_3R_57() {
        return this.jj_3R_66();
    }

    private boolean jj_3R_54() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_57()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_58()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_24() {
        Token xsp;
        if (this.jj_3R_26()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_27());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_59() {
        return this.jj_3R_68();
    }

    private boolean jj_3R_55() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_59()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_60()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_61()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_62()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_63()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_51() {
        return this.jj_3R_54();
    }

    private boolean jj_3R_16() {
        Token xsp;
        if (this.jj_3R_17()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_18());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_15() {
        if (this.jj_scan_token(23)) {
            return true;
        }
        return this.jj_scan_token(53);
    }

    private boolean jj_3R_53() {
        return this.jj_3R_56();
    }

    private boolean jj_3R_52() {
        return this.jj_3R_55();
    }

    private boolean jj_3R_50() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_52()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_53()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_22() {
        Token xsp;
        if (this.jj_3R_24()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_25());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_23() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(41)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(42)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_49() {
        Token xsp;
        if (this.jj_3R_50()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_51());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_20() {
        Token xsp;
        if (this.jj_3R_22()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_23());
        this.jj_scanpos = xsp;
        return false;
    }

    private boolean jj_3R_45() {
        return this.jj_3R_49();
    }

    private boolean jj_3R_44() {
        if (this.jj_scan_token(43)) {
            return true;
        }
        return this.jj_3R_38();
    }

    private boolean jj_3R_21() {
        return this.jj_scan_token(48);
    }

    private boolean jj_3R_14() {
        if (this.jj_scan_token(53)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_15()) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_scan_token(19)) {
            return true;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_16()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(20);
    }

    private boolean jj_3R_43() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(37)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(38)) {
                return true;
            }
        }
        return this.jj_3R_38();
    }

    private boolean jj_3R_38() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_42()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_43()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_44()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_45()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_42() {
        if (this.jj_scan_token(47)) {
            return true;
        }
        return this.jj_3R_38();
    }

    private boolean jj_3R_19() {
        Token xsp;
        if (this.jj_3R_20()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_21());
        this.jj_scanpos = xsp;
        return false;
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, -33554432, 0x18000000, 0x6000000, Integer.MIN_VALUE, 0x60000000, -33554432, 0, 0, 0, 0, 0, 0, 0, 644608, 0x240000, 644608, 0x240000, 0x1000000, 644608, 524288, 0, 0x800000, 0x1000000, 644608, 120320, 49152};
    }

    private static void jj_la1_init_1() {
        jj_la1_1 = new int[]{0, 0, 65536, 1536, 1536, 384, 384, 30, 6, 24, 30, 1, 0, 0, 1, 0, 1, 49152, 49152, 1974272, 393216, 0x180000, 1974272, 96, 2132064, 0, 0x200000, 0, 0, 2132064, 0, 0x200000, 0, 0, 2132064, 0, 0};
    }

    public ELParser(InputStream stream) {
        this(stream, null);
    }

    public ELParser(InputStream stream, String encoding) {
        int i;
        try {
            this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source = new ELParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 37; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(InputStream stream) {
        this.ReInit(stream, null);
    }

    public void ReInit(InputStream stream, String encoding) {
        int i;
        try {
            this.jj_input_stream.ReInit(stream, encoding, 1, 1);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 37; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public ELParser(Reader stream) {
        int i;
        this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
        this.token_source = new ELParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 37; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(Reader stream) {
        int i;
        this.jj_input_stream.ReInit(stream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 37; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public ELParser(ELParserTokenManager tm) {
        int i;
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 37; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(ELParserTokenManager tm) {
        int i;
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 37; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken = this.token;
        this.token = oldToken.next != null ? this.token.next : (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        if (this.token.kind == kind) {
            ++this.jj_gen;
            if (++this.jj_gc > 100) {
                this.jj_gc = 0;
                for (int i = 0; i < this.jj_2_rtns.length; ++i) {
                    JJCalls c = this.jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < this.jj_gen) {
                            c.first = null;
                        }
                        c = c.next;
                    }
                }
            }
            return this.token;
        }
        this.token = oldToken;
        this.jj_kind = kind;
        throw this.generateParseException();
    }

    private boolean jj_scan_token(int kind) {
        if (this.jj_scanpos == this.jj_lastpos) {
            --this.jj_la;
            if (this.jj_scanpos.next == null) {
                this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
                this.jj_lastpos = this.jj_scanpos.next;
            } else {
                this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
            }
        } else {
            this.jj_scanpos = this.jj_scanpos.next;
        }
        if (this.jj_rescan) {
            int i = 0;
            Token tok = this.token;
            while (tok != null && tok != this.jj_scanpos) {
                ++i;
                tok = tok.next;
            }
            if (tok != null) {
                this.jj_add_error_token(kind, i);
            }
        }
        if (this.jj_scanpos.kind != kind) {
            return true;
        }
        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            throw this.jj_ls;
        }
        return false;
    }

    public final Token getNextToken() {
        this.token = this.token.next != null ? this.token.next : (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        ++this.jj_gen;
        return this.token;
    }

    public final Token getToken(int index) {
        Token t = this.token;
        for (int i = 0; i < index; ++i) {
            t = t.next != null ? t.next : (t.next = this.token_source.getNextToken());
        }
        return t;
    }

    private int jj_ntk() {
        this.jj_nt = this.token.next;
        if (this.jj_nt == null) {
            this.token.next = this.token_source.getNextToken();
            this.jj_ntk = this.token.next.kind;
            return this.jj_ntk;
        }
        this.jj_ntk = this.jj_nt.kind;
        return this.jj_ntk;
    }

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) {
            return;
        }
        if (pos == this.jj_endpos + 1) {
            this.jj_lasttokens[this.jj_endpos++] = kind;
        } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];
            for (int i = 0; i < this.jj_endpos; ++i) {
                this.jj_expentry[i] = this.jj_lasttokens[i];
            }
            block1: for (int[] oldentry : this.jj_expentries) {
                if (oldentry.length != this.jj_expentry.length) continue;
                for (int i = 0; i < this.jj_expentry.length; ++i) {
                    if (oldentry[i] != this.jj_expentry[i]) continue block1;
                }
                this.jj_expentries.add(this.jj_expentry);
                break;
            }
            if (pos != 0) {
                this.jj_endpos = pos;
                this.jj_lasttokens[this.jj_endpos - 1] = kind;
            }
        }
    }

    public ParseException generateParseException() {
        int i;
        this.jj_expentries.clear();
        boolean[] la1tokens = new boolean[58];
        if (this.jj_kind >= 0) {
            la1tokens[this.jj_kind] = true;
            this.jj_kind = -1;
        }
        for (i = 0; i < 37; ++i) {
            if (this.jj_la1[i] != this.jj_gen) continue;
            for (int j = 0; j < 32; ++j) {
                if ((jj_la1_0[i] & 1 << j) != 0) {
                    la1tokens[j] = true;
                }
                if ((jj_la1_1[i] & 1 << j) == 0) continue;
                la1tokens[32 + j] = true;
            }
        }
        for (i = 0; i < 58; ++i) {
            if (!la1tokens[i]) continue;
            this.jj_expentry = new int[1];
            this.jj_expentry[0] = i;
            this.jj_expentries.add(this.jj_expentry);
        }
        this.jj_endpos = 0;
        this.jj_rescan_token();
        this.jj_add_error_token(0, 0);
        int[][] exptokseq = new int[this.jj_expentries.size()][];
        for (int i2 = 0; i2 < this.jj_expentries.size(); ++i2) {
            exptokseq[i2] = (int[])this.jj_expentries.get(i2);
        }
        return new ParseException(this.token, exptokseq, tokenImage);
    }

    public final void enable_tracing() {
    }

    public final void disable_tracing() {
    }

    private void jj_rescan_token() {
        this.jj_rescan = true;
        for (int i = 0; i < 3; ++i) {
            try {
                JJCalls p = this.jj_2_rtns[i];
                do {
                    if (p.gen <= this.jj_gen) continue;
                    this.jj_la = p.arg;
                    this.jj_lastpos = this.jj_scanpos = p.first;
                    switch (i) {
                        case 0: {
                            this.jj_3_1();
                            break;
                        }
                        case 1: {
                            this.jj_3_2();
                            break;
                        }
                        case 2: {
                            this.jj_3_3();
                        }
                    }
                } while ((p = p.next) != null);
                continue;
            }
            catch (LookaheadSuccess lookaheadSuccess) {
                // empty catch block
            }
        }
        this.jj_rescan = false;
    }

    private void jj_save(int index, int xla) {
        JJCalls p = this.jj_2_rtns[index];
        while (p.gen > this.jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        p.gen = this.jj_gen + xla - this.jj_la;
        p.first = this.token;
        p.arg = xla;
    }

    static {
        ELParser.jj_la1_init_0();
        ELParser.jj_la1_init_1();
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;

        JJCalls() {
        }
    }

    private static final class LookaheadSuccess
    extends Error {
        private LookaheadSuccess() {
        }
    }
}

