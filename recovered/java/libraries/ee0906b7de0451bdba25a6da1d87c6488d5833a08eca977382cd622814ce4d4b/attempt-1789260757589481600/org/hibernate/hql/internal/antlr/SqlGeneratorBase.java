/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  antlr.MismatchedTokenException
 *  antlr.NoViableAltException
 *  antlr.RecognitionException
 *  antlr.TreeParser
 *  antlr.collections.AST
 *  antlr.collections.impl.BitSet
 */
package org.hibernate.hql.internal.antlr;

import antlr.MismatchedTokenException;
import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.TreeParser;
import antlr.collections.AST;
import antlr.collections.impl.BitSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.hql.internal.antlr.SqlTokenTypes;

public class SqlGeneratorBase
extends TreeParser
implements SqlTokenTypes {
    private StringBuilder buf = new StringBuilder();
    private boolean captureExpression = false;
    protected List<StringBuilder> exprs = new ArrayList<StringBuilder>(Arrays.asList(new StringBuilder()));
    public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"all\"", "\"any\"", "\"and\"", "\"as\"", "\"asc\"", "\"avg\"", "\"between\"", "\"class\"", "\"count\"", "\"delete\"", "\"desc\"", "DOT", "\"distinct\"", "\"elements\"", "\"escape\"", "\"exists\"", "\"false\"", "\"fetch\"", "\"from\"", "\"full\"", "\"group\"", "\"having\"", "\"in\"", "\"indices\"", "\"inner\"", "\"insert\"", "\"into\"", "\"is\"", "\"join\"", "\"left\"", "\"like\"", "\"max\"", "\"min\"", "\"new\"", "\"not\"", "\"null\"", "\"or\"", "\"order\"", "\"outer\"", "\"properties\"", "\"right\"", "\"select\"", "\"set\"", "\"some\"", "\"sum\"", "\"true\"", "\"update\"", "\"versioned\"", "\"where\"", "\"nulls\"", "FIRST", "LAST", "\"case\"", "\"end\"", "\"else\"", "\"then\"", "\"when\"", "\"on\"", "\"with\"", "\"both\"", "\"empty\"", "\"leading\"", "\"member\"", "\"object\"", "\"of\"", "\"trailing\"", "KEY", "VALUE", "ENTRY", "AGGREGATE", "ALIAS", "CONSTRUCTOR", "CASE2", "CAST", "COLL_PATH", "EXPR_LIST", "FILTER_ENTITY", "IN_LIST", "INDEX_OP", "IS_NOT_NULL", "IS_NULL", "METHOD_CALL", "NOT_BETWEEN", "NOT_IN", "NOT_LIKE", "ORDER_ELEMENT", "QUERY", "RANGE", "ROW_STAR", "SELECT_FROM", "COLL_SIZE", "UNARY_MINUS", "UNARY_PLUS", "VECTOR_EXPR", "WEIRD_IDENT", "CONSTANT", "NUM_DOUBLE", "NUM_FLOAT", "NUM_LONG", "NUM_BIG_INTEGER", "NUM_BIG_DECIMAL", "JAVA_CONSTANT", "COMMA", "EQ", "OPEN", "CLOSE", "IDENT", "\"by\"", "\"ascending\"", "\"descending\"", "NE", "SQL_NE", "LT", "GT", "LE", "GE", "CONCAT", "PLUS", "MINUS", "STAR", "DIV", "MOD", "OPEN_BRACKET", "CLOSE_BRACKET", "QUOTED_STRING", "COLON", "PARAM", "NUM_INT", "ID_START_LETTER", "ID_LETTER", "ESCqs", "WS", "HEX_DIGIT", "EXPONENT", "FLOAT_SUFFIX", "FROM_FRAGMENT", "IMPLIED_FROM", "JOIN_FRAGMENT", "ENTITY_JOIN", "SELECT_CLAUSE", "LEFT_OUTER", "RIGHT_OUTER", "ALIAS_REF", "PROPERTY_REF", "SQL_TOKEN", "SELECT_COLUMNS", "SELECT_EXPR", "THETA_JOINS", "FILTERS", "METHOD_NAME", "NAMED_PARAM", "BOGUS", "RESULT_VARIABLE_REF"};
    public static final BitSet _tokenSet_0 = new BitSet(SqlGeneratorBase.mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(SqlGeneratorBase.mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(SqlGeneratorBase.mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(SqlGeneratorBase.mk_tokenSet_3());
    public static final BitSet _tokenSet_4 = new BitSet(SqlGeneratorBase.mk_tokenSet_4());
    public static final BitSet _tokenSet_5 = new BitSet(SqlGeneratorBase.mk_tokenSet_5());

    protected void out(String s) {
        this.getStringBuilder().append(s);
    }

    protected int getLastChar() {
        int len = this.buf.length();
        if (len == 0) {
            return -1;
        }
        return this.buf.charAt(len - 1);
    }

    protected void optionalSpace() {
    }

    protected void out(AST n) {
        this.out(n.getText());
    }

    protected void separator(AST n, String sep) {
        if (n.getNextSibling() != null) {
            this.out(sep);
        }
    }

    protected boolean hasText(AST a) {
        String t = a.getText();
        return t != null && t.length() > 0;
    }

    protected void fromFragmentSeparator(AST a) {
    }

    protected void nestedFromFragment(AST d, AST parent) {
    }

    protected StringBuilder getStringBuilder() {
        return this.captureExpression ? this.exprs.get(this.exprs.size() - 1) : this.buf;
    }

    protected void nyi(AST n) {
        throw new UnsupportedOperationException("Unsupported node: " + n);
    }

    protected void beginFunctionTemplate(AST m, AST i) {
        this.out(i);
        this.out("(");
    }

    protected void endFunctionTemplate(AST m) {
        this.out(")");
    }

    protected void betweenFunctionArguments() {
        this.out(", ");
    }

    protected void captureExpressionStart() {
        if (this.captureExpression) {
            this.exprs.add(new StringBuilder());
        } else {
            this.captureExpression = true;
        }
    }

    protected void captureExpressionFinish() {
        if (this.exprs.size() == 1) {
            this.captureExpression = false;
        }
    }

    protected String resetCapture() {
        StringBuilder sb = this.exprs.remove(this.exprs.size() - 1);
        String expression = sb.toString();
        if (this.exprs.isEmpty()) {
            sb.setLength(0);
            this.exprs.add(sb);
        }
        return expression;
    }

    protected String renderOrderByElement(String expression, String order, String nulls) {
        throw new UnsupportedOperationException("Concrete SQL generator should override this method.");
    }

    protected void renderCollectionSize(AST collectionSizeNode) {
        throw new UnsupportedOperationException("Concrete SQL generator should override this method.");
    }

    public SqlGeneratorBase() {
        this.tokenNames = _tokenNames;
    }

    public final void statement(AST _t) throws RecognitionException {
        AST statement_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 45: {
                    this.selectStatement(_t);
                    _t = this._retTree;
                    break;
                }
                case 50: {
                    this.updateStatement(_t);
                    _t = this._retTree;
                    break;
                }
                case 13: {
                    this.deleteStatement(_t);
                    _t = this._retTree;
                    break;
                }
                case 29: {
                    this.insertStatement(_t);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void selectStatement(AST _t) throws RecognitionException {
        AST selectStatement_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t488 = _t;
            AST tmp1_AST_in = _t;
            this.match(_t, 45);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out("select ");
            }
            this.selectClause(_t);
            _t = this._retTree;
            this.from(_t);
            _t = this._retTree;
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 52: {
                    AST __t490 = _t;
                    AST tmp2_AST_in = _t;
                    this.match(_t, 52);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(" where ");
                    }
                    this.whereExpr(_t);
                    _t = this._retTree;
                    _t = __t490;
                    _t = _t.getNextSibling();
                    break;
                }
                case 3: 
                case 24: 
                case 41: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 24: {
                    AST __t492 = _t;
                    AST tmp3_AST_in = _t;
                    this.match(_t, 24);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(" group by ");
                    }
                    this.groupExprs(_t);
                    _t = this._retTree;
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 25: {
                            AST __t494 = _t;
                            AST tmp4_AST_in = _t;
                            this.match(_t, 25);
                            _t = _t.getFirstChild();
                            if (this.inputState.guessing == 0) {
                                this.out(" having ");
                            }
                            this.booleanExpr(_t, false);
                            _t = this._retTree;
                            _t = __t494;
                            _t = _t.getNextSibling();
                            break;
                        }
                        case 3: {
                            break;
                        }
                        default: {
                            throw new NoViableAltException(_t);
                        }
                    }
                    _t = __t492;
                    _t = _t.getNextSibling();
                    break;
                }
                case 3: 
                case 41: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 41: {
                    AST __t496 = _t;
                    AST tmp5_AST_in = _t;
                    this.match(_t, 41);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(" order by ");
                    }
                    this.orderExprs(_t);
                    _t = this._retTree;
                    _t = __t496;
                    _t = _t.getNextSibling();
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            _t = __t488;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void updateStatement(AST _t) throws RecognitionException {
        AST updateStatement_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t498 = _t;
            AST tmp6_AST_in = _t;
            this.match(_t, 50);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out("update ");
            }
            AST __t499 = _t;
            AST tmp7_AST_in = _t;
            this.match(_t, 22);
            _t = _t.getFirstChild();
            this.fromTable(_t);
            _t = this._retTree;
            _t = __t499;
            _t = _t.getNextSibling();
            this.setClause(_t);
            _t = this._retTree;
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 52: {
                    this.whereClause(_t);
                    _t = this._retTree;
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            _t = __t498;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void deleteStatement(AST _t) throws RecognitionException {
        AST deleteStatement_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t502 = _t;
            AST tmp8_AST_in = _t;
            this.match(_t, 13);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out("delete");
            }
            this.from(_t);
            _t = this._retTree;
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 52: {
                    this.whereClause(_t);
                    _t = this._retTree;
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            _t = __t502;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void insertStatement(AST _t) throws RecognitionException {
        AST insertStatement_AST_in = _t == ASTNULL ? null : _t;
        AST i = null;
        try {
            AST __t505 = _t;
            AST tmp9_AST_in = _t;
            this.match(_t, 29);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out("insert ");
            }
            i = _t;
            this.match(_t, 30);
            _t = _t.getNextSibling();
            if (this.inputState.guessing == 0) {
                this.out(i);
                this.out(" ");
            }
            this.selectStatement(_t);
            _t = this._retTree;
            _t = __t505;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void selectClause(AST _t) throws RecognitionException {
        AST selectClause_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t536 = _t;
            AST tmp10_AST_in = _t;
            this.match(_t, 143);
            _t = _t.getFirstChild();
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 4: 
                case 16: {
                    this.distinctOrAll(_t);
                    _t = this._retTree;
                    break;
                }
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 40: 
                case 45: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 75: 
                case 76: 
                case 77: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 150: 
                case 154: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            int _cnt539 = 0;
            while (true) {
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (!_tokenSet_0.member(_t.getType())) {
                    if (_cnt539 >= 1) break;
                    throw new NoViableAltException(_t);
                }
                this.selectColumn(_t);
                _t = this._retTree;
                ++_cnt539;
            }
            _t = __t536;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void from(AST _t) throws RecognitionException {
        AST from_AST_in = _t == ASTNULL ? null : _t;
        AST f = null;
        try {
            AST __t555 = _t;
            f = _t == ASTNULL ? null : _t;
            this.match(_t, 22);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out(" from ");
            }
            while (true) {
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (_t.getType() != 139 && _t.getType() != 141 && _t.getType() != 142) break;
                this.fromTable(_t);
                _t = this._retTree;
            }
            _t = __t555;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void whereExpr(AST _t) throws RecognitionException {
        AST whereExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            block1 : switch (_t.getType()) {
                case 152: {
                    this.filters(_t);
                    _t = this._retTree;
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 151: {
                            if (this.inputState.guessing == 0) {
                                this.out(" and ");
                            }
                            this.thetaJoins(_t);
                            _t = this._retTree;
                            break;
                        }
                        case 3: 
                        case 6: 
                        case 10: 
                        case 19: 
                        case 26: 
                        case 34: 
                        case 38: 
                        case 40: 
                        case 83: 
                        case 84: 
                        case 86: 
                        case 87: 
                        case 88: 
                        case 107: 
                        case 114: 
                        case 116: 
                        case 117: 
                        case 118: 
                        case 119: 
                        case 148: {
                            break;
                        }
                        default: {
                            throw new NoViableAltException(_t);
                        }
                    }
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 6: 
                        case 10: 
                        case 19: 
                        case 26: 
                        case 34: 
                        case 38: 
                        case 40: 
                        case 83: 
                        case 84: 
                        case 86: 
                        case 87: 
                        case 88: 
                        case 107: 
                        case 114: 
                        case 116: 
                        case 117: 
                        case 118: 
                        case 119: 
                        case 148: {
                            if (this.inputState.guessing == 0) {
                                this.out(" and ");
                            }
                            this.booleanExpr(_t, true);
                            _t = this._retTree;
                            break block1;
                        }
                        case 3: {
                            break block1;
                        }
                    }
                    throw new NoViableAltException(_t);
                }
                case 151: {
                    this.thetaJoins(_t);
                    _t = this._retTree;
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 6: 
                        case 10: 
                        case 19: 
                        case 26: 
                        case 34: 
                        case 38: 
                        case 40: 
                        case 83: 
                        case 84: 
                        case 86: 
                        case 87: 
                        case 88: 
                        case 107: 
                        case 114: 
                        case 116: 
                        case 117: 
                        case 118: 
                        case 119: 
                        case 148: {
                            if (this.inputState.guessing == 0) {
                                this.out(" and ");
                            }
                            this.booleanExpr(_t, true);
                            _t = this._retTree;
                            break block1;
                        }
                        case 3: {
                            break block1;
                        }
                    }
                    throw new NoViableAltException(_t);
                }
                case 6: 
                case 10: 
                case 19: 
                case 26: 
                case 34: 
                case 38: 
                case 40: 
                case 83: 
                case 84: 
                case 86: 
                case 87: 
                case 88: 
                case 107: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 148: {
                    this.booleanExpr(_t, false);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void groupExprs(AST _t) throws RecognitionException {
        AST groupExprs_AST_in = _t == ASTNULL ? null : _t;
        try {
            this.expr(_t);
            _t = this._retTree;
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 4: 
                case 5: 
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 45: 
                case 47: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 97: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    if (this.inputState.guessing == 0) {
                        this.out(" , ");
                    }
                    this.groupExprs(_t);
                    _t = this._retTree;
                    break;
                }
                case 3: 
                case 25: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void booleanExpr(AST _t, boolean parens) throws RecognitionException {
        AST booleanExpr_AST_in = _t == ASTNULL ? null : _t;
        AST st = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 6: 
                case 38: 
                case 40: {
                    this.booleanOp(_t, parens);
                    _t = this._retTree;
                    break;
                }
                case 10: 
                case 19: 
                case 26: 
                case 34: 
                case 83: 
                case 84: 
                case 86: 
                case 87: 
                case 88: 
                case 107: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: {
                    this.comparisonExpr(_t, parens);
                    _t = this._retTree;
                    break;
                }
                case 148: {
                    st = _t;
                    this.match(_t, 148);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(st);
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void orderExprs(AST _t) throws RecognitionException {
        AST orderExprs_AST_in = _t == ASTNULL ? null : _t;
        AST e = null;
        AST dir = null;
        String ordExp = null;
        String ordDir = null;
        String ordNul = null;
        try {
            if (this.inputState.guessing == 0) {
                this.captureExpressionStart();
            }
            e = _t == ASTNULL ? null : _t;
            this.expr(_t);
            _t = this._retTree;
            if (this.inputState.guessing == 0) {
                this.captureExpressionFinish();
                ordExp = this.resetCapture();
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 8: 
                case 14: {
                    dir = _t == ASTNULL ? null : _t;
                    this.orderDirection(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing != 0) break;
                    ordDir = dir.getText();
                    break;
                }
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 45: 
                case 47: 
                case 49: 
                case 53: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 97: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 53: {
                    ordNul = this.nullOrdering(_t);
                    _t = this._retTree;
                    break;
                }
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 45: 
                case 47: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 97: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            if (this.inputState.guessing == 0) {
                this.out(e.getType() == 148 && ordDir == null && ordNul == null ? ordExp : this.renderOrderByElement(ordExp, ordDir, ordNul));
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 4: 
                case 5: 
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 45: 
                case 47: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 97: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    if (this.inputState.guessing == 0) {
                        this.out(", ");
                    }
                    this.orderExprs(_t);
                    _t = this._retTree;
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void fromTable(AST _t) throws RecognitionException {
        AST fromTable_AST_in = _t == ASTNULL ? null : _t;
        AST a = null;
        AST b = null;
        AST c = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 139: {
                    AST __t559 = _t;
                    a = _t == ASTNULL ? null : _t;
                    this.match(_t, 139);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(a);
                    }
                    while (true) {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() != 139 && _t.getType() != 141 && _t.getType() != 142) break;
                        this.tableJoin(_t, a);
                        _t = this._retTree;
                    }
                    if (this.inputState.guessing == 0) {
                        this.fromFragmentSeparator(a);
                    }
                    _t = __t559;
                    _t = _t.getNextSibling();
                    break;
                }
                case 141: {
                    AST __t562 = _t;
                    b = _t == ASTNULL ? null : _t;
                    this.match(_t, 141);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(b);
                    }
                    while (true) {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() != 139 && _t.getType() != 141 && _t.getType() != 142) break;
                        this.tableJoin(_t, b);
                        _t = this._retTree;
                    }
                    if (this.inputState.guessing == 0) {
                        this.fromFragmentSeparator(b);
                    }
                    _t = __t562;
                    _t = _t.getNextSibling();
                    break;
                }
                case 142: {
                    AST __t565 = _t;
                    c = _t == ASTNULL ? null : _t;
                    this.match(_t, 142);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(c);
                    }
                    while (true) {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() != 139 && _t.getType() != 141 && _t.getType() != 142) break;
                        this.tableJoin(_t, c);
                        _t = this._retTree;
                    }
                    if (this.inputState.guessing == 0) {
                        this.fromFragmentSeparator(c);
                    }
                    _t = __t565;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void setClause(AST _t) throws RecognitionException {
        AST setClause_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t507 = _t;
            AST tmp11_AST_in = _t;
            this.match(_t, 46);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out(" set ");
            }
            this.comparisonExpr(_t, false);
            _t = this._retTree;
            while (true) {
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (!_tokenSet_1.member(_t.getType())) break;
                if (this.inputState.guessing == 0) {
                    this.out(", ");
                }
                this.comparisonExpr(_t, false);
                _t = this._retTree;
            }
            _t = __t507;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void whereClause(AST _t) throws RecognitionException {
        AST whereClause_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t511 = _t;
            AST tmp12_AST_in = _t;
            this.match(_t, 52);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out(" where ");
            }
            this.whereClauseExpr(_t);
            _t = this._retTree;
            _t = __t511;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void comparisonExpr(AST _t, boolean parens) throws RecognitionException {
        AST comparisonExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 107: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: {
                    this.binaryComparisonExpression(_t);
                    _t = this._retTree;
                    break;
                }
                case 10: 
                case 19: 
                case 26: 
                case 34: 
                case 83: 
                case 84: 
                case 86: 
                case 87: 
                case 88: {
                    if (this.inputState.guessing == 0 && parens) {
                        this.out("(");
                    }
                    this.exoticComparisonExpression(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0 && parens) {
                        this.out(")");
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void whereClauseExpr(AST _t) throws RecognitionException {
        block10: {
            AST whereClauseExpr_AST_in = _t == ASTNULL ? null : _t;
            try {
                boolean synPredMatched514 = false;
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (_t.getType() == 148) {
                    AST __t514 = _t;
                    synPredMatched514 = true;
                    ++this.inputState.guessing;
                    try {
                        AST tmp13_AST_in = _t;
                        this.match(_t, 148);
                        _t = _t.getNextSibling();
                    }
                    catch (RecognitionException pe) {
                        synPredMatched514 = false;
                    }
                    _t = __t514;
                    --this.inputState.guessing;
                }
                if (synPredMatched514) {
                    this.conditionList(_t);
                    _t = this._retTree;
                    break block10;
                }
                if (_tokenSet_2.member(_t.getType())) {
                    this.booleanExpr(_t, false);
                    _t = this._retTree;
                    break block10;
                }
                throw new NoViableAltException(_t);
            }
            catch (RecognitionException ex) {
                if (this.inputState.guessing == 0) {
                    this.reportError(ex);
                    if (_t != null) {
                        _t = _t.getNextSibling();
                    }
                }
                throw ex;
            }
        }
        this._retTree = _t;
    }

    public final void conditionList(AST _t) throws RecognitionException {
        AST conditionList_AST_in = _t == ASTNULL ? null : _t;
        try {
            this.sqlToken(_t);
            _t = this._retTree;
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 148: {
                    if (this.inputState.guessing == 0) {
                        this.out(" and ");
                    }
                    this.conditionList(_t);
                    _t = this._retTree;
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void expr(AST _t) throws RecognitionException {
        AST expr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    this.simpleExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 97: {
                    this.tupleExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 45: {
                    this.parenSelect(_t);
                    _t = this._retTree;
                    break;
                }
                case 5: {
                    AST __t613 = _t;
                    AST tmp14_AST_in = _t;
                    this.match(_t, 5);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out("any ");
                    }
                    this.quantified(_t);
                    _t = this._retTree;
                    _t = __t613;
                    _t = _t.getNextSibling();
                    break;
                }
                case 4: {
                    AST __t614 = _t;
                    AST tmp15_AST_in = _t;
                    this.match(_t, 4);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out("all ");
                    }
                    this.quantified(_t);
                    _t = this._retTree;
                    _t = __t614;
                    _t = _t.getNextSibling();
                    break;
                }
                case 47: {
                    AST __t615 = _t;
                    AST tmp16_AST_in = _t;
                    this.match(_t, 47);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out("some ");
                    }
                    this.quantified(_t);
                    _t = this._retTree;
                    _t = __t615;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void orderDirection(AST _t) throws RecognitionException {
        AST orderDirection_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 8: {
                    AST tmp17_AST_in = _t;
                    this.match(_t, 8);
                    _t = _t.getNextSibling();
                    break;
                }
                case 14: {
                    AST tmp18_AST_in = _t;
                    this.match(_t, 14);
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final String nullOrdering(AST _t) throws RecognitionException {
        String nullOrdExp = null;
        AST nullOrdering_AST_in = _t == ASTNULL ? null : _t;
        AST fl = null;
        try {
            AST tmp19_AST_in = _t;
            this.match(_t, 53);
            _t = _t.getNextSibling();
            fl = _t == ASTNULL ? null : _t;
            this.nullPrecedence(_t);
            _t = this._retTree;
            if (this.inputState.guessing == 0) {
                nullOrdExp = fl.getText();
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
        return nullOrdExp;
    }

    public final void nullPrecedence(AST _t) throws RecognitionException {
        AST nullPrecedence_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 54: {
                    AST tmp20_AST_in = _t;
                    this.match(_t, 54);
                    _t = _t.getNextSibling();
                    break;
                }
                case 55: {
                    AST tmp21_AST_in = _t;
                    this.match(_t, 55);
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void filters(AST _t) throws RecognitionException {
        AST filters_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t530 = _t;
            AST tmp22_AST_in = _t;
            this.match(_t, 152);
            _t = _t.getFirstChild();
            this.conditionList(_t);
            _t = this._retTree;
            _t = __t530;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void thetaJoins(AST _t) throws RecognitionException {
        AST thetaJoins_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t532 = _t;
            AST tmp23_AST_in = _t;
            this.match(_t, 151);
            _t = _t.getFirstChild();
            this.conditionList(_t);
            _t = this._retTree;
            _t = __t532;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void sqlToken(AST _t) throws RecognitionException {
        AST sqlToken_AST_in = _t == ASTNULL ? null : _t;
        AST t = null;
        try {
            t = _t;
            this.match(_t, 148);
            _t = _t.getNextSibling();
            if (this.inputState.guessing == 0) {
                this.out(t);
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void distinctOrAll(AST _t) throws RecognitionException {
        AST distinctOrAll_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 16: {
                    AST tmp24_AST_in = _t;
                    this.match(_t, 16);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out("distinct ");
                    }
                    break;
                }
                case 4: {
                    AST tmp25_AST_in = _t;
                    this.match(_t, 4);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out("all ");
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void selectColumn(AST _t) throws RecognitionException {
        AST selectColumn_AST_in = _t == ASTNULL ? null : _t;
        AST p = null;
        AST sc = null;
        try {
            p = _t == ASTNULL ? null : _t;
            this.selectExpr(_t);
            _t = this._retTree;
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 149: {
                    sc = _t;
                    this.match(_t, 149);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing != 0) break;
                    this.out(sc);
                    break;
                }
                case 3: 
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 40: 
                case 45: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 75: 
                case 76: 
                case 77: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 150: 
                case 154: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            if (this.inputState.guessing == 0) {
                this.separator(sc != null ? sc : p, ", ");
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void selectExpr(AST _t) throws RecognitionException {
        AST selectExpr_AST_in = _t == ASTNULL ? null : _t;
        AST e = null;
        AST mcr = null;
        AST c = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 15: 
                case 146: 
                case 148: 
                case 150: {
                    e = _t == ASTNULL ? null : _t;
                    this.selectAtom(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(e);
                    }
                    break;
                }
                case 70: 
                case 71: 
                case 72: {
                    mcr = _t == ASTNULL ? null : _t;
                    this.mapComponentReference(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(mcr);
                    }
                    break;
                }
                case 12: {
                    this.count(_t);
                    _t = this._retTree;
                    break;
                }
                case 75: {
                    AST __t543 = _t;
                    AST tmp26_AST_in = _t;
                    this.match(_t, 75);
                    _t = _t.getFirstChild();
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 15: {
                            AST tmp27_AST_in = _t;
                            this.match(_t, 15);
                            _t = _t.getNextSibling();
                            break;
                        }
                        case 110: {
                            AST tmp28_AST_in = _t;
                            this.match(_t, 110);
                            _t = _t.getNextSibling();
                            break;
                        }
                        default: {
                            throw new NoViableAltException(_t);
                        }
                    }
                    int _cnt546 = 0;
                    while (true) {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (!_tokenSet_0.member(_t.getType())) {
                            if (_cnt546 >= 1) break;
                            throw new NoViableAltException(_t);
                        }
                        this.selectColumn(_t);
                        _t = this._retTree;
                        ++_cnt546;
                    }
                    _t = __t543;
                    _t = _t.getNextSibling();
                    break;
                }
                case 77: 
                case 85: 
                case 94: {
                    this.methodCall(_t);
                    _t = this._retTree;
                    break;
                }
                case 73: {
                    this.aggregate(_t);
                    _t = this._retTree;
                    break;
                }
                case 20: 
                case 49: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 110: 
                case 128: 
                case 131: {
                    c = _t == ASTNULL ? null : _t;
                    this.constant(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(c);
                    }
                    break;
                }
                case 56: 
                case 76: 
                case 95: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: {
                    this.arithmeticExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 6: 
                case 10: 
                case 19: 
                case 26: 
                case 34: 
                case 38: 
                case 40: 
                case 83: 
                case 84: 
                case 86: 
                case 87: 
                case 88: 
                case 107: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: {
                    this.selectBooleanExpr(_t, false);
                    _t = this._retTree;
                    break;
                }
                case 130: 
                case 154: {
                    this.parameter(_t);
                    _t = this._retTree;
                    break;
                }
                case 45: {
                    if (this.inputState.guessing == 0) {
                        this.out("(");
                    }
                    this.selectStatement(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(")");
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void selectAtom(AST _t) throws RecognitionException {
        AST selectAtom_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 15: {
                    AST tmp29_AST_in = _t;
                    this.match(_t, 15);
                    _t = _t.getNextSibling();
                    break;
                }
                case 148: {
                    AST tmp30_AST_in = _t;
                    this.match(_t, 148);
                    _t = _t.getNextSibling();
                    break;
                }
                case 146: {
                    AST tmp31_AST_in = _t;
                    this.match(_t, 146);
                    _t = _t.getNextSibling();
                    break;
                }
                case 150: {
                    AST tmp32_AST_in = _t;
                    this.match(_t, 150);
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void mapComponentReference(AST _t) throws RecognitionException {
        AST mapComponentReference_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 70: {
                    AST tmp33_AST_in = _t;
                    this.match(_t, 70);
                    _t = _t.getNextSibling();
                    break;
                }
                case 71: {
                    AST tmp34_AST_in = _t;
                    this.match(_t, 71);
                    _t = _t.getNextSibling();
                    break;
                }
                case 72: {
                    AST tmp35_AST_in = _t;
                    this.match(_t, 72);
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void count(AST _t) throws RecognitionException {
        AST count_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t548 = _t;
            AST tmp36_AST_in = _t;
            this.match(_t, 12);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out("count(");
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 4: 
                case 16: {
                    this.distinctOrAll(_t);
                    _t = this._retTree;
                    break;
                }
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 92: 
                case 94: 
                case 95: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            this.countExpr(_t);
            _t = this._retTree;
            if (this.inputState.guessing == 0) {
                this.out(")");
            }
            _t = __t548;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void methodCall(AST _t) throws RecognitionException {
        AST methodCall_AST_in = _t == ASTNULL ? null : _t;
        AST m = null;
        AST i = null;
        AST c = null;
        AST cs = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 85: {
                    AST __t656 = _t;
                    m = _t == ASTNULL ? null : _t;
                    this.match(_t, 85);
                    i = _t = _t.getFirstChild();
                    this.match(_t, 153);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.beginFunctionTemplate(m, i);
                    }
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 79: {
                            AST __t658 = _t;
                            AST tmp37_AST_in = _t;
                            this.match(_t, 79);
                            _t = _t.getFirstChild();
                            if (_t == null) {
                                _t = ASTNULL;
                            }
                            switch (_t.getType()) {
                                case 4: 
                                case 5: 
                                case 6: 
                                case 10: 
                                case 12: 
                                case 15: 
                                case 19: 
                                case 20: 
                                case 26: 
                                case 34: 
                                case 38: 
                                case 39: 
                                case 40: 
                                case 45: 
                                case 47: 
                                case 49: 
                                case 56: 
                                case 70: 
                                case 71: 
                                case 72: 
                                case 73: 
                                case 76: 
                                case 77: 
                                case 82: 
                                case 83: 
                                case 84: 
                                case 85: 
                                case 86: 
                                case 87: 
                                case 88: 
                                case 94: 
                                case 95: 
                                case 97: 
                                case 99: 
                                case 100: 
                                case 101: 
                                case 102: 
                                case 103: 
                                case 104: 
                                case 105: 
                                case 107: 
                                case 110: 
                                case 114: 
                                case 116: 
                                case 117: 
                                case 118: 
                                case 119: 
                                case 121: 
                                case 122: 
                                case 123: 
                                case 124: 
                                case 125: 
                                case 128: 
                                case 130: 
                                case 131: 
                                case 146: 
                                case 148: 
                                case 154: 
                                case 156: {
                                    this.arguments(_t);
                                    _t = this._retTree;
                                    break;
                                }
                                case 3: {
                                    break;
                                }
                                default: {
                                    throw new NoViableAltException(_t);
                                }
                            }
                            _t = __t658;
                            _t = _t.getNextSibling();
                            break;
                        }
                        case 3: {
                            break;
                        }
                        default: {
                            throw new NoViableAltException(_t);
                        }
                    }
                    if (this.inputState.guessing == 0) {
                        this.endFunctionTemplate(m);
                    }
                    _t = __t656;
                    _t = _t.getNextSibling();
                    break;
                }
                case 77: {
                    AST __t660 = _t;
                    c = _t == ASTNULL ? null : _t;
                    this.match(_t, 77);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.beginFunctionTemplate(c, c);
                    }
                    this.castExpression(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.betweenFunctionArguments();
                    }
                    this.castTargetType(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.endFunctionTemplate(c);
                    }
                    _t = __t660;
                    _t = _t.getNextSibling();
                    break;
                }
                case 94: {
                    cs = _t;
                    this.match(_t, 94);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.renderCollectionSize(cs);
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void aggregate(AST _t) throws RecognitionException {
        AST aggregate_AST_in = _t == ASTNULL ? null : _t;
        AST a = null;
        try {
            AST __t654 = _t;
            a = _t == ASTNULL ? null : _t;
            this.match(_t, 73);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.beginFunctionTemplate(a, a);
            }
            this.expr(_t);
            _t = this._retTree;
            if (this.inputState.guessing == 0) {
                this.endFunctionTemplate(a);
            }
            _t = __t654;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void constant(AST _t) throws RecognitionException {
        AST constant_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 100: {
                    AST tmp38_AST_in = _t;
                    this.match(_t, 100);
                    _t = _t.getNextSibling();
                    break;
                }
                case 101: {
                    AST tmp39_AST_in = _t;
                    this.match(_t, 101);
                    _t = _t.getNextSibling();
                    break;
                }
                case 131: {
                    AST tmp40_AST_in = _t;
                    this.match(_t, 131);
                    _t = _t.getNextSibling();
                    break;
                }
                case 102: {
                    AST tmp41_AST_in = _t;
                    this.match(_t, 102);
                    _t = _t.getNextSibling();
                    break;
                }
                case 103: {
                    AST tmp42_AST_in = _t;
                    this.match(_t, 103);
                    _t = _t.getNextSibling();
                    break;
                }
                case 104: {
                    AST tmp43_AST_in = _t;
                    this.match(_t, 104);
                    _t = _t.getNextSibling();
                    break;
                }
                case 128: {
                    AST tmp44_AST_in = _t;
                    this.match(_t, 128);
                    _t = _t.getNextSibling();
                    break;
                }
                case 99: {
                    AST tmp45_AST_in = _t;
                    this.match(_t, 99);
                    _t = _t.getNextSibling();
                    break;
                }
                case 105: {
                    AST tmp46_AST_in = _t;
                    this.match(_t, 105);
                    _t = _t.getNextSibling();
                    break;
                }
                case 49: {
                    AST tmp47_AST_in = _t;
                    this.match(_t, 49);
                    _t = _t.getNextSibling();
                    break;
                }
                case 20: {
                    AST tmp48_AST_in = _t;
                    this.match(_t, 20);
                    _t = _t.getNextSibling();
                    break;
                }
                case 110: {
                    AST tmp49_AST_in = _t;
                    this.match(_t, 110);
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void arithmeticExpr(AST _t) throws RecognitionException {
        AST arithmeticExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 121: 
                case 122: {
                    this.additiveExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 123: 
                case 124: 
                case 125: {
                    this.multiplicativeExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 95: {
                    AST __t626 = _t;
                    AST tmp50_AST_in = _t;
                    this.match(_t, 95);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out("-");
                    }
                    this.nestedExprAfterMinusDiv(_t);
                    _t = this._retTree;
                    _t = __t626;
                    _t = _t.getNextSibling();
                    break;
                }
                case 56: 
                case 76: {
                    this.caseExpr(_t);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void selectBooleanExpr(AST _t, boolean parens) throws RecognitionException {
        AST selectBooleanExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 6: 
                case 38: 
                case 40: {
                    this.booleanOp(_t, parens);
                    _t = this._retTree;
                    break;
                }
                case 10: 
                case 19: 
                case 26: 
                case 34: 
                case 83: 
                case 84: 
                case 86: 
                case 87: 
                case 88: 
                case 107: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: {
                    this.comparisonExpr(_t, parens);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void parameter(AST _t) throws RecognitionException {
        AST parameter_AST_in = _t == ASTNULL ? null : _t;
        AST n = null;
        AST p = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 154: {
                    n = _t;
                    this.match(_t, 154);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(n);
                    }
                    break;
                }
                case 130: {
                    p = _t;
                    this.match(_t, 130);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(p);
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void countExpr(AST _t) throws RecognitionException {
        AST countExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 92: {
                    AST tmp51_AST_in = _t;
                    this.match(_t, 92);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out("*");
                    }
                    break;
                }
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    this.simpleExpr(_t);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void simpleExpr(AST _t) throws RecognitionException {
        AST simpleExpr_AST_in = _t == ASTNULL ? null : _t;
        AST c = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 20: 
                case 49: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 110: 
                case 128: 
                case 131: {
                    c = _t == ASTNULL ? null : _t;
                    this.constant(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(c);
                    }
                    break;
                }
                case 39: {
                    AST tmp52_AST_in = _t;
                    this.match(_t, 39);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out("null");
                    }
                    break;
                }
                case 15: 
                case 70: 
                case 71: 
                case 72: 
                case 82: 
                case 146: 
                case 156: {
                    this.addrExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 148: {
                    this.sqlToken(_t);
                    _t = this._retTree;
                    break;
                }
                case 73: {
                    this.aggregate(_t);
                    _t = this._retTree;
                    break;
                }
                case 77: 
                case 85: 
                case 94: {
                    this.methodCall(_t);
                    _t = this._retTree;
                    break;
                }
                case 12: {
                    this.count(_t);
                    _t = this._retTree;
                    break;
                }
                case 130: 
                case 154: {
                    this.parameter(_t);
                    _t = this._retTree;
                    break;
                }
                case 56: 
                case 76: 
                case 95: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: {
                    this.arithmeticExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 6: 
                case 10: 
                case 19: 
                case 26: 
                case 34: 
                case 38: 
                case 40: 
                case 83: 
                case 84: 
                case 86: 
                case 87: 
                case 88: 
                case 107: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: {
                    this.selectBooleanExpr(_t, false);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void tableJoin(AST _t, AST parent) throws RecognitionException {
        AST tableJoin_AST_in = _t == ASTNULL ? null : _t;
        AST d = null;
        AST e = null;
        AST f = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 141: {
                    AST __t569 = _t;
                    d = _t == ASTNULL ? null : _t;
                    this.match(_t, 141);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(" ");
                        this.out(d);
                    }
                    while (true) {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() != 139 && _t.getType() != 141 && _t.getType() != 142) break;
                        this.tableJoin(_t, d);
                        _t = this._retTree;
                    }
                    _t = __t569;
                    _t = _t.getNextSibling();
                    break;
                }
                case 139: {
                    AST __t572 = _t;
                    e = _t == ASTNULL ? null : _t;
                    this.match(_t, 139);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.nestedFromFragment(e, parent);
                    }
                    while (true) {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() != 139 && _t.getType() != 141 && _t.getType() != 142) break;
                        this.tableJoin(_t, e);
                        _t = this._retTree;
                    }
                    _t = __t572;
                    _t = _t.getNextSibling();
                    break;
                }
                case 142: {
                    AST __t575 = _t;
                    f = _t == ASTNULL ? null : _t;
                    this.match(_t, 142);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(" ");
                        this.out(f);
                    }
                    while (true) {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() != 139 && _t.getType() != 141 && _t.getType() != 142) break;
                        this.tableJoin(_t, f);
                        _t = this._retTree;
                    }
                    _t = __t575;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void booleanOp(AST _t, boolean parens) throws RecognitionException {
        AST booleanOp_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 6: {
                    AST __t579 = _t;
                    AST tmp53_AST_in = _t;
                    this.match(_t, 6);
                    _t = _t.getFirstChild();
                    this.booleanExpr(_t, true);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" and ");
                    }
                    this.booleanExpr(_t, true);
                    _t = this._retTree;
                    _t = __t579;
                    _t = _t.getNextSibling();
                    break;
                }
                case 40: {
                    AST __t580 = _t;
                    AST tmp54_AST_in = _t;
                    this.match(_t, 40);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0 && parens) {
                        this.out("(");
                    }
                    this.booleanExpr(_t, false);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" or ");
                    }
                    this.booleanExpr(_t, false);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0 && parens) {
                        this.out(")");
                    }
                    _t = __t580;
                    _t = _t.getNextSibling();
                    break;
                }
                case 38: {
                    AST __t581 = _t;
                    AST tmp55_AST_in = _t;
                    this.match(_t, 38);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(" not (");
                    }
                    this.booleanExpr(_t, false);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(")");
                    }
                    _t = __t581;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void binaryComparisonExpression(AST _t) throws RecognitionException {
        AST binaryComparisonExpression_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 107: {
                    AST __t586 = _t;
                    AST tmp56_AST_in = _t;
                    this.match(_t, 107);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("=");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t586;
                    _t = _t.getNextSibling();
                    break;
                }
                case 114: {
                    AST __t587 = _t;
                    AST tmp57_AST_in = _t;
                    this.match(_t, 114);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("<>");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t587;
                    _t = _t.getNextSibling();
                    break;
                }
                case 117: {
                    AST __t588 = _t;
                    AST tmp58_AST_in = _t;
                    this.match(_t, 117);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(">");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t588;
                    _t = _t.getNextSibling();
                    break;
                }
                case 119: {
                    AST __t589 = _t;
                    AST tmp59_AST_in = _t;
                    this.match(_t, 119);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(">=");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t589;
                    _t = _t.getNextSibling();
                    break;
                }
                case 116: {
                    AST __t590 = _t;
                    AST tmp60_AST_in = _t;
                    this.match(_t, 116);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("<");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t590;
                    _t = _t.getNextSibling();
                    break;
                }
                case 118: {
                    AST __t591 = _t;
                    AST tmp61_AST_in = _t;
                    this.match(_t, 118);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("<=");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t591;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void exoticComparisonExpression(AST _t) throws RecognitionException {
        AST exoticComparisonExpression_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 34: {
                    AST __t593 = _t;
                    AST tmp62_AST_in = _t;
                    this.match(_t, 34);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" like ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    this.likeEscape(_t);
                    _t = this._retTree;
                    _t = __t593;
                    _t = _t.getNextSibling();
                    break;
                }
                case 88: {
                    AST __t594 = _t;
                    AST tmp63_AST_in = _t;
                    this.match(_t, 88);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" not like ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    this.likeEscape(_t);
                    _t = this._retTree;
                    _t = __t594;
                    _t = _t.getNextSibling();
                    break;
                }
                case 10: {
                    AST __t595 = _t;
                    AST tmp64_AST_in = _t;
                    this.match(_t, 10);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" between ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" and ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t595;
                    _t = _t.getNextSibling();
                    break;
                }
                case 86: {
                    AST __t596 = _t;
                    AST tmp65_AST_in = _t;
                    this.match(_t, 86);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" not between ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" and ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t596;
                    _t = _t.getNextSibling();
                    break;
                }
                case 26: {
                    AST __t597 = _t;
                    AST tmp66_AST_in = _t;
                    this.match(_t, 26);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" in");
                    }
                    this.inList(_t);
                    _t = this._retTree;
                    _t = __t597;
                    _t = _t.getNextSibling();
                    break;
                }
                case 87: {
                    AST __t598 = _t;
                    AST tmp67_AST_in = _t;
                    this.match(_t, 87);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" not in ");
                    }
                    this.inList(_t);
                    _t = this._retTree;
                    _t = __t598;
                    _t = _t.getNextSibling();
                    break;
                }
                case 19: {
                    AST __t599 = _t;
                    AST tmp68_AST_in = _t;
                    this.match(_t, 19);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.optionalSpace();
                        this.out("exists ");
                    }
                    this.quantified(_t);
                    _t = this._retTree;
                    _t = __t599;
                    _t = _t.getNextSibling();
                    break;
                }
                case 84: {
                    AST __t600 = _t;
                    AST tmp69_AST_in = _t;
                    this.match(_t, 84);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t600;
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(" is null");
                    }
                    break;
                }
                case 83: {
                    AST __t601 = _t;
                    AST tmp70_AST_in = _t;
                    this.match(_t, 83);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t601;
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(" is not null");
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void likeEscape(AST _t) throws RecognitionException {
        AST likeEscape_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 18: {
                    AST __t604 = _t;
                    AST tmp71_AST_in = _t;
                    this.match(_t, 18);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out(" escape ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t604;
                    _t = _t.getNextSibling();
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void inList(AST _t) throws RecognitionException {
        AST inList_AST_in = _t == ASTNULL ? null : _t;
        try {
            AST __t606 = _t;
            AST tmp72_AST_in = _t;
            this.match(_t, 81);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out(" ");
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 45: {
                    this.parenSelect(_t);
                    _t = this._retTree;
                    break;
                }
                case 3: 
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 97: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    this.simpleExprList(_t);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            _t = __t606;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void quantified(AST _t) throws RecognitionException {
        AST quantified_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (this.inputState.guessing == 0) {
                this.out("(");
            }
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 148: {
                    this.sqlToken(_t);
                    _t = this._retTree;
                    break;
                }
                case 45: {
                    this.selectStatement(_t);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
            if (this.inputState.guessing == 0) {
                this.out(")");
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void parenSelect(AST _t) throws RecognitionException {
        AST parenSelect_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (this.inputState.guessing == 0) {
                this.out("(");
            }
            this.selectStatement(_t);
            _t = this._retTree;
            if (this.inputState.guessing == 0) {
                this.out(")");
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void simpleExprList(AST _t) throws RecognitionException {
        AST simpleExprList_AST_in = _t == ASTNULL ? null : _t;
        AST e = null;
        try {
            if (this.inputState.guessing == 0) {
                this.out("(");
            }
            while (true) {
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (!_tokenSet_3.member(_t.getType())) break;
                e = _t == ASTNULL ? null : _t;
                this.simpleOrTupleExpr(_t);
                _t = this._retTree;
                if (this.inputState.guessing != 0) continue;
                this.separator(e, " , ");
            }
            if (this.inputState.guessing == 0) {
                this.out(")");
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void simpleOrTupleExpr(AST _t) throws RecognitionException {
        AST simpleOrTupleExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 39: 
                case 40: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 76: 
                case 77: 
                case 82: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 154: 
                case 156: {
                    this.simpleExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 97: {
                    this.tupleExpr(_t);
                    _t = this._retTree;
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void tupleExpr(AST _t) throws RecognitionException {
        AST tupleExpr_AST_in = _t == ASTNULL ? null : _t;
        AST e = null;
        try {
            AST __t617 = _t;
            AST tmp73_AST_in = _t;
            this.match(_t, 97);
            _t = _t.getFirstChild();
            if (this.inputState.guessing == 0) {
                this.out("(");
            }
            while (true) {
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (!_tokenSet_4.member(_t.getType())) break;
                e = _t == ASTNULL ? null : _t;
                this.expr(_t);
                _t = this._retTree;
                if (this.inputState.guessing != 0) continue;
                this.separator(e, " , ");
            }
            if (this.inputState.guessing == 0) {
                this.out(")");
            }
            _t = __t617;
            _t = _t.getNextSibling();
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void addrExpr(AST _t) throws RecognitionException {
        AST addrExpr_AST_in = _t == ASTNULL ? null : _t;
        AST r = null;
        AST i = null;
        AST j = null;
        AST v = null;
        AST mcr = null;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 15: {
                    AST __t668 = _t;
                    r = _t == ASTNULL ? null : _t;
                    this.match(_t, 15);
                    AST tmp74_AST_in = _t = _t.getFirstChild();
                    if (_t == null) {
                        throw new MismatchedTokenException();
                    }
                    AST tmp75_AST_in = _t = _t.getNextSibling();
                    if (_t == null) {
                        throw new MismatchedTokenException();
                    }
                    _t = _t.getNextSibling();
                    _t = __t668;
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(r);
                    }
                    break;
                }
                case 146: {
                    i = _t;
                    this.match(_t, 146);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(i);
                    }
                    break;
                }
                case 82: {
                    j = _t;
                    this.match(_t, 82);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(j);
                    }
                    break;
                }
                case 156: {
                    v = _t;
                    this.match(_t, 156);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out(v);
                    }
                    break;
                }
                case 70: 
                case 71: 
                case 72: {
                    mcr = _t == ASTNULL ? null : _t;
                    this.mapComponentReference(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(mcr);
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void additiveExpr(AST _t) throws RecognitionException {
        AST additiveExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 121: {
                    AST __t628 = _t;
                    AST tmp76_AST_in = _t;
                    this.match(_t, 121);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("+");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    _t = __t628;
                    _t = _t.getNextSibling();
                    break;
                }
                case 122: {
                    AST __t629 = _t;
                    AST tmp77_AST_in = _t;
                    this.match(_t, 122);
                    _t = _t.getFirstChild();
                    this.expr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("-");
                    }
                    this.nestedExprAfterMinusDiv(_t);
                    _t = this._retTree;
                    _t = __t629;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void multiplicativeExpr(AST _t) throws RecognitionException {
        AST multiplicativeExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 123: {
                    AST __t631 = _t;
                    AST tmp78_AST_in = _t;
                    this.match(_t, 123);
                    _t = _t.getFirstChild();
                    this.nestedExpr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("*");
                    }
                    this.nestedExpr(_t);
                    _t = this._retTree;
                    _t = __t631;
                    _t = _t.getNextSibling();
                    break;
                }
                case 124: {
                    AST __t632 = _t;
                    AST tmp79_AST_in = _t;
                    this.match(_t, 124);
                    _t = _t.getFirstChild();
                    this.nestedExpr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out("/");
                    }
                    this.nestedExprAfterMinusDiv(_t);
                    _t = this._retTree;
                    _t = __t632;
                    _t = _t.getNextSibling();
                    break;
                }
                case 125: {
                    AST __t633 = _t;
                    AST tmp80_AST_in = _t;
                    this.match(_t, 125);
                    _t = _t.getFirstChild();
                    this.nestedExpr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(" % ");
                    }
                    this.nestedExprAfterMinusDiv(_t);
                    _t = this._retTree;
                    _t = __t633;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void nestedExprAfterMinusDiv(AST _t) throws RecognitionException {
        block12: {
            AST nestedExprAfterMinusDiv_AST_in = _t == ASTNULL ? null : _t;
            try {
                boolean synPredMatched639 = false;
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (_tokenSet_5.member(_t.getType())) {
                    AST __t639 = _t;
                    synPredMatched639 = true;
                    ++this.inputState.guessing;
                    try {
                        this.arithmeticExpr(_t);
                        _t = this._retTree;
                    }
                    catch (RecognitionException pe) {
                        synPredMatched639 = false;
                    }
                    _t = __t639;
                    --this.inputState.guessing;
                }
                if (synPredMatched639) {
                    if (this.inputState.guessing == 0) {
                        this.out("(");
                    }
                    this.arithmeticExpr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(")");
                    }
                    break block12;
                }
                if (_tokenSet_4.member(_t.getType())) {
                    this.expr(_t);
                    _t = this._retTree;
                    break block12;
                }
                throw new NoViableAltException(_t);
            }
            catch (RecognitionException ex) {
                if (this.inputState.guessing == 0) {
                    this.reportError(ex);
                    if (_t != null) {
                        _t = _t.getNextSibling();
                    }
                }
                throw ex;
            }
        }
        this._retTree = _t;
    }

    public final void caseExpr(AST _t) throws RecognitionException {
        AST caseExpr_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 56: {
                    AST __t641 = _t;
                    AST tmp81_AST_in = _t;
                    this.match(_t, 56);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out("case");
                    }
                    int _cnt644 = 0;
                    while (true) {
                        AST __t643;
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() == 60) {
                            __t643 = _t;
                            AST tmp82_AST_in = _t;
                            this.match(_t, 60);
                            _t = _t.getFirstChild();
                            if (this.inputState.guessing == 0) {
                                this.out(" when ");
                            }
                            this.booleanExpr(_t, false);
                            _t = this._retTree;
                            if (this.inputState.guessing == 0) {
                                this.out(" then ");
                            }
                        } else {
                            if (_cnt644 >= 1) break;
                            throw new NoViableAltException(_t);
                        }
                        this.expr(_t);
                        _t = this._retTree;
                        _t = __t643;
                        _t = _t.getNextSibling();
                        ++_cnt644;
                    }
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 58: {
                            AST __t646 = _t;
                            AST tmp83_AST_in = _t;
                            this.match(_t, 58);
                            _t = _t.getFirstChild();
                            if (this.inputState.guessing == 0) {
                                this.out(" else ");
                            }
                            this.expr(_t);
                            _t = this._retTree;
                            _t = __t646;
                            _t = _t.getNextSibling();
                            break;
                        }
                        case 3: {
                            break;
                        }
                        default: {
                            throw new NoViableAltException(_t);
                        }
                    }
                    if (this.inputState.guessing == 0) {
                        this.out(" end");
                    }
                    _t = __t641;
                    _t = _t.getNextSibling();
                    break;
                }
                case 76: {
                    AST __t647 = _t;
                    AST tmp84_AST_in = _t;
                    this.match(_t, 76);
                    _t = _t.getFirstChild();
                    if (this.inputState.guessing == 0) {
                        this.out("case ");
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    int _cnt650 = 0;
                    while (true) {
                        AST __t649;
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        if (_t.getType() == 60) {
                            __t649 = _t;
                            AST tmp85_AST_in = _t;
                            this.match(_t, 60);
                            _t = _t.getFirstChild();
                            if (this.inputState.guessing == 0) {
                                this.out(" when ");
                            }
                            this.expr(_t);
                            _t = this._retTree;
                            if (this.inputState.guessing == 0) {
                                this.out(" then ");
                            }
                        } else {
                            if (_cnt650 >= 1) break;
                            throw new NoViableAltException(_t);
                        }
                        this.expr(_t);
                        _t = this._retTree;
                        _t = __t649;
                        _t = _t.getNextSibling();
                        ++_cnt650;
                    }
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 58: {
                            AST __t652 = _t;
                            AST tmp86_AST_in = _t;
                            this.match(_t, 58);
                            _t = _t.getFirstChild();
                            if (this.inputState.guessing == 0) {
                                this.out(" else ");
                            }
                            this.expr(_t);
                            _t = this._retTree;
                            _t = __t652;
                            _t = _t.getNextSibling();
                            break;
                        }
                        case 3: {
                            break;
                        }
                        default: {
                            throw new NoViableAltException(_t);
                        }
                    }
                    if (this.inputState.guessing == 0) {
                        this.out(" end");
                    }
                    _t = __t647;
                    _t = _t.getNextSibling();
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void nestedExpr(AST _t) throws RecognitionException {
        block12: {
            AST nestedExpr_AST_in = _t == ASTNULL ? null : _t;
            try {
                boolean synPredMatched636 = false;
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (_t.getType() == 121 || _t.getType() == 122) {
                    AST __t636 = _t;
                    synPredMatched636 = true;
                    ++this.inputState.guessing;
                    try {
                        this.additiveExpr(_t);
                        _t = this._retTree;
                    }
                    catch (RecognitionException pe) {
                        synPredMatched636 = false;
                    }
                    _t = __t636;
                    --this.inputState.guessing;
                }
                if (synPredMatched636) {
                    if (this.inputState.guessing == 0) {
                        this.out("(");
                    }
                    this.additiveExpr(_t);
                    _t = this._retTree;
                    if (this.inputState.guessing == 0) {
                        this.out(")");
                    }
                    break block12;
                }
                if (_tokenSet_4.member(_t.getType())) {
                    this.expr(_t);
                    _t = this._retTree;
                    break block12;
                }
                throw new NoViableAltException(_t);
            }
            catch (RecognitionException ex) {
                if (this.inputState.guessing == 0) {
                    this.reportError(ex);
                    if (_t != null) {
                        _t = _t.getNextSibling();
                    }
                }
                throw ex;
            }
        }
        this._retTree = _t;
    }

    public final void arguments(AST _t) throws RecognitionException {
        AST arguments_AST_in = _t == ASTNULL ? null : _t;
        try {
            this.expr(_t);
            _t = this._retTree;
            while (true) {
                if (_t == null) {
                    _t = ASTNULL;
                }
                if (_tokenSet_4.member(_t.getType())) {
                    if (this.inputState.guessing == 0) {
                        this.betweenFunctionArguments();
                    }
                    this.expr(_t);
                    _t = this._retTree;
                    continue;
                }
                break;
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void castExpression(AST _t) throws RecognitionException {
        AST castExpression_AST_in = _t == ASTNULL ? null : _t;
        try {
            if (_t == null) {
                _t = ASTNULL;
            }
            switch (_t.getType()) {
                case 6: 
                case 10: 
                case 12: 
                case 15: 
                case 19: 
                case 20: 
                case 26: 
                case 34: 
                case 38: 
                case 40: 
                case 45: 
                case 49: 
                case 56: 
                case 70: 
                case 71: 
                case 72: 
                case 73: 
                case 75: 
                case 76: 
                case 77: 
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 94: 
                case 95: 
                case 99: 
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 107: 
                case 110: 
                case 114: 
                case 116: 
                case 117: 
                case 118: 
                case 119: 
                case 121: 
                case 122: 
                case 123: 
                case 124: 
                case 125: 
                case 128: 
                case 130: 
                case 131: 
                case 146: 
                case 148: 
                case 150: 
                case 154: {
                    this.selectExpr(_t);
                    _t = this._retTree;
                    break;
                }
                case 39: {
                    AST tmp87_AST_in = _t;
                    this.match(_t, 39);
                    _t = _t.getNextSibling();
                    if (this.inputState.guessing == 0) {
                        this.out("null");
                    }
                    break;
                }
                default: {
                    throw new NoViableAltException(_t);
                }
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    public final void castTargetType(AST _t) throws RecognitionException {
        AST castTargetType_AST_in = _t == ASTNULL ? null : _t;
        AST i = null;
        try {
            i = _t;
            this.match(_t, 110);
            _t = _t.getNextSibling();
            if (this.inputState.guessing == 0) {
                this.out(i);
            }
        }
        catch (RecognitionException ex) {
            if (this.inputState.guessing == 0) {
                this.reportError(ex);
                if (_t != null) {
                    _t = _t.getNextSibling();
                }
            }
            throw ex;
        }
        this._retTree = _t;
    }

    private static final long[] mk_tokenSet_0() {
        long[] data = new long[]{72657120001561664L, 4536334256447175616L, 72613901L, 0L, 0L, 0L};
        return data;
    }

    private static final long[] mk_tokenSet_1() {
        long[] data = new long[]{0x404080400L, 68688690441355264L, 0L, 0L};
        return data;
    }

    private static final long[] mk_tokenSet_2() {
        long[] data = new long[]{1391637038144L, 68688690441355264L, 0x100000L, 0L, 0L, 0L};
        return data;
    }

    private static final long[] mk_tokenSet_3() {
        long[] data = new long[]{72622485385286720L, 4536334265037370304L, 336855053L, 0L, 0L, 0L};
        return data;
    }

    private static final long[] mk_tokenSet_4() {
        long[] data = new long[]{72798407245730928L, 4536334265037370304L, 336855053L, 0L, 0L, 0L};
        return data;
    }

    private static final long[] mk_tokenSet_5() {
        long[] data = new long[]{0x100000000000000L, 4467570832499019776L, 0L, 0L};
        return data;
    }
}

