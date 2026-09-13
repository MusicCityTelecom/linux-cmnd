/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  antlr.ASTPair
 *  antlr.NoViableAltException
 *  antlr.RecognitionException
 *  antlr.SemanticException
 *  antlr.TreeParser
 *  antlr.collections.AST
 *  antlr.collections.impl.ASTArray
 *  antlr.collections.impl.BitSet
 *  org.jboss.logging.Logger
 */
package org.hibernate.hql.internal.antlr;

import antlr.ASTPair;
import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.SemanticException;
import antlr.TreeParser;
import antlr.collections.AST;
import antlr.collections.impl.ASTArray;
import antlr.collections.impl.BitSet;
import java.util.Stack;
import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.Logger;

public class HqlSqlBaseWalker
extends TreeParser
implements HqlSqlTokenTypes {
    private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, (String)HqlSqlBaseWalker.class.getName());
    private int level = 0;
    private boolean inSelect = false;
    private boolean inFunctionCall = false;
    private boolean inCase = false;
    private boolean inFrom = false;
    private boolean inCount = false;
    private boolean inCountDistinct = false;
    private boolean inSize = false;
    private int statementType;
    private String statementTypeName;
    private int currentClauseType;
    private int currentTopLevelClauseType;
    private int currentStatementType;
    private Stack<Integer> parentClauses = new Stack();
    public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"all\"", "\"any\"", "\"and\"", "\"as\"", "\"asc\"", "\"avg\"", "\"between\"", "\"class\"", "\"count\"", "\"delete\"", "\"desc\"", "DOT", "\"distinct\"", "\"elements\"", "\"escape\"", "\"exists\"", "\"false\"", "\"fetch\"", "\"from\"", "\"full\"", "\"group\"", "\"having\"", "\"in\"", "\"indices\"", "\"inner\"", "\"insert\"", "\"into\"", "\"is\"", "\"join\"", "\"left\"", "\"like\"", "\"max\"", "\"min\"", "\"new\"", "\"not\"", "\"null\"", "\"or\"", "\"order\"", "\"outer\"", "\"properties\"", "\"right\"", "\"select\"", "\"set\"", "\"some\"", "\"sum\"", "\"true\"", "\"update\"", "\"versioned\"", "\"where\"", "\"nulls\"", "FIRST", "LAST", "\"case\"", "\"end\"", "\"else\"", "\"then\"", "\"when\"", "\"on\"", "\"with\"", "\"both\"", "\"empty\"", "\"leading\"", "\"member\"", "\"object\"", "\"of\"", "\"trailing\"", "KEY", "VALUE", "ENTRY", "AGGREGATE", "ALIAS", "CONSTRUCTOR", "CASE2", "CAST", "COLL_PATH", "EXPR_LIST", "FILTER_ENTITY", "IN_LIST", "INDEX_OP", "IS_NOT_NULL", "IS_NULL", "METHOD_CALL", "NOT_BETWEEN", "NOT_IN", "NOT_LIKE", "ORDER_ELEMENT", "QUERY", "RANGE", "ROW_STAR", "SELECT_FROM", "COLL_SIZE", "UNARY_MINUS", "UNARY_PLUS", "VECTOR_EXPR", "WEIRD_IDENT", "CONSTANT", "NUM_DOUBLE", "NUM_FLOAT", "NUM_LONG", "NUM_BIG_INTEGER", "NUM_BIG_DECIMAL", "JAVA_CONSTANT", "COMMA", "EQ", "OPEN", "CLOSE", "IDENT", "\"by\"", "\"ascending\"", "\"descending\"", "NE", "SQL_NE", "LT", "GT", "LE", "GE", "CONCAT", "PLUS", "MINUS", "STAR", "DIV", "MOD", "OPEN_BRACKET", "CLOSE_BRACKET", "QUOTED_STRING", "COLON", "PARAM", "NUM_INT", "ID_START_LETTER", "ID_LETTER", "ESCqs", "WS", "HEX_DIGIT", "EXPONENT", "FLOAT_SUFFIX", "FROM_FRAGMENT", "IMPLIED_FROM", "JOIN_FRAGMENT", "ENTITY_JOIN", "SELECT_CLAUSE", "LEFT_OUTER", "RIGHT_OUTER", "ALIAS_REF", "PROPERTY_REF", "SQL_TOKEN", "SELECT_COLUMNS", "SELECT_EXPR", "THETA_JOINS", "FILTERS", "METHOD_NAME", "NAMED_PARAM", "BOGUS", "RESULT_VARIABLE_REF"};
    public static final BitSet _tokenSet_0 = new BitSet(HqlSqlBaseWalker.mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(HqlSqlBaseWalker.mk_tokenSet_1());

    public final boolean isSubQuery() {
        return this.level > 1;
    }

    public final boolean isInFrom() {
        return this.inFrom;
    }

    public final boolean isInFunctionCall() {
        return this.inFunctionCall;
    }

    public final boolean isInSelect() {
        return this.inSelect;
    }

    public final boolean isInCase() {
        return this.inCase;
    }

    public final boolean isInCount() {
        return this.inCount;
    }

    public final boolean isInCountDistinct() {
        return this.inCountDistinct;
    }

    public final boolean isInSize() {
        return this.inSize;
    }

    public final int getStatementType() {
        return this.statementType;
    }

    public final int getCurrentClauseType() {
        return this.currentClauseType;
    }

    public final int getCurrentTopLevelClauseType() {
        return this.currentTopLevelClauseType;
    }

    public final int getCurrentStatementType() {
        return this.currentStatementType;
    }

    public final boolean isComparativeExpressionClause() {
        return this.getCurrentClauseType() == 52 || this.getCurrentClauseType() == 62 || this.isInCase();
    }

    public final boolean isSelectStatement() {
        return this.statementType == 45;
    }

    private void beforeStatement(String statementName, int statementType) {
        this.inFunctionCall = false;
        ++this.level;
        if (this.level == 1) {
            this.statementTypeName = statementName;
            this.statementType = statementType;
        }
        this.currentStatementType = statementType;
        LOG.debugf("%s << begin [level=%s, statement=%s]", statementName, this.level, this.statementTypeName);
    }

    private void beforeStatementCompletion(String statementName) {
        LOG.debugf("%s : finishing up [level=%s, statement=%s]", statementName, this.level, this.statementTypeName);
    }

    private void afterStatementCompletion(String statementName) {
        LOG.debugf("%s >> end [level=%s, statement=%s]", statementName, this.level, this.statementTypeName);
        --this.level;
    }

    private void handleClauseStart(int clauseType) {
        this.parentClauses.push(this.currentClauseType);
        this.currentClauseType = clauseType;
        if (this.level == 1) {
            this.currentTopLevelClauseType = clauseType;
        }
    }

    private void handleClauseEnd() {
        this.currentClauseType = this.parentClauses.pop();
    }

    protected void evaluateAssignment(AST eq) throws SemanticException {
    }

    protected void prepareFromClauseInputTree(AST fromClauseInput) {
    }

    protected void pushFromClause(AST fromClause, AST inputFromNode) {
    }

    protected AST createFromElement(String path, AST alias, AST propertyFetch) throws SemanticException {
        return null;
    }

    protected void createFromJoinElement(AST path, AST alias, int joinType, AST fetch, AST propertyFetch, AST with) throws SemanticException {
    }

    protected AST createFromFilterElement(AST filterEntity, AST alias) throws SemanticException {
        return null;
    }

    protected void processQuery(AST select, AST query) throws SemanticException {
    }

    protected void postProcessUpdate(AST update) throws SemanticException {
    }

    protected void postProcessDelete(AST delete) throws SemanticException {
    }

    protected void postProcessInsert(AST insert) throws SemanticException {
    }

    protected void beforeSelectClause() throws SemanticException {
    }

    protected void processIndex(AST indexOp) throws SemanticException {
    }

    protected void processConstant(AST constant) throws SemanticException {
    }

    protected void processBoolean(AST constant) throws SemanticException {
    }

    protected void processNumericLiteral(AST literal) throws SemanticException {
    }

    protected void resolve(AST node) throws SemanticException {
    }

    protected void resolve(AST node, AST predicateNode) throws SemanticException {
    }

    protected void resolveSelectExpression(AST dotNode) throws SemanticException {
    }

    protected void processFunction(AST functionCall, boolean inSelect) throws SemanticException {
    }

    protected void processCastFunction(AST functionCall, boolean inSelect) throws SemanticException {
    }

    protected void processAggregation(AST node, boolean inSelect) throws SemanticException {
    }

    protected void processConstructor(AST constructor) throws SemanticException {
    }

    protected AST generateNamedParameter(AST delimiterNode, AST nameNode) throws SemanticException {
        return this.astFactory.make(new ASTArray(1).add(this.astFactory.create(154, nameNode.getText())));
    }

    protected AST generatePositionalParameter(AST delimiterNode, AST numberNode) throws SemanticException {
        return this.astFactory.make(new ASTArray(1).add(this.astFactory.create(130, numberNode.getText())));
    }

    protected void lookupAlias(AST ident) throws SemanticException {
    }

    protected void setAlias(AST selectExpr, AST ident) {
    }

    protected boolean isOrderExpressionResultVariableRef(AST ident) throws SemanticException {
        return false;
    }

    protected boolean isGroupExpressionResultVariableRef(AST ident) throws SemanticException {
        return false;
    }

    protected void handleResultVariableRef(AST resultVariableRef) throws SemanticException {
    }

    protected AST createCollectionSizeFunction(AST collectionPath, boolean inSelect) throws SemanticException {
        throw new UnsupportedOperationException("Walker should implement");
    }

    protected AST createCollectionPath(AST qualifier, AST reference) throws SemanticException {
        throw new UnsupportedOperationException("Walker should implement");
    }

    protected AST lookupProperty(AST dot, boolean root, boolean inSelect) throws SemanticException {
        return dot;
    }

    protected boolean isNonQualifiedPropertyRef(AST ident) {
        return false;
    }

    protected AST lookupNonQualifiedProperty(AST property) throws SemanticException {
        return property;
    }

    protected void setImpliedJoinType(int joinType) {
    }

    protected AST createIntoClause(String path, AST propertySpec) throws SemanticException {
        return null;
    }

    protected void prepareVersioned(AST updateNode, AST versionedNode) throws SemanticException {
    }

    protected void prepareLogicOperator(AST operator) throws SemanticException {
    }

    protected void prepareArithmeticOperator(AST operator) throws SemanticException {
    }

    protected void processMapComponentReference(AST node) throws SemanticException {
    }

    protected void validateMapPropertyExpression(AST node) throws SemanticException {
    }

    protected void finishFromClause(AST fromClause) throws SemanticException {
    }

    public HqlSqlBaseWalker() {
        this.tokenNames = _tokenNames;
    }

    public final void statement(AST _t) throws RecognitionException {
        AST statement_AST;
        block9: {
            AST statement_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            statement_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 90: {
                        this.selectStatement(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        statement_AST = currentAST.root;
                        break;
                    }
                    case 50: {
                        this.updateStatement(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        statement_AST = currentAST.root;
                        break;
                    }
                    case 13: {
                        this.deleteStatement(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        statement_AST = currentAST.root;
                        break;
                    }
                    case 29: {
                        this.insertStatement(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        statement_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block9;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = statement_AST;
        this._retTree = _t;
    }

    public final void selectStatement(AST _t) throws RecognitionException {
        AST selectStatement_AST;
        block2: {
            AST selectStatement_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            selectStatement_AST = null;
            try {
                this.query(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                selectStatement_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = selectStatement_AST;
        this._retTree = _t;
    }

    public final void updateStatement(AST _t) throws RecognitionException {
        AST updateStatement_AST;
        block12: {
            AST updateStatement_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            updateStatement_AST = null;
            AST u = null;
            AST u_AST = null;
            AST v = null;
            AST v_AST = null;
            AST f_AST = null;
            AST f = null;
            AST s_AST = null;
            AST s = null;
            AST w_AST = null;
            AST w = null;
            try {
                AST __t287 = _t;
                u = _t == ASTNULL ? null : _t;
                Object u_AST_in = null;
                u_AST = this.astFactory.create(u);
                ASTPair __currentAST287 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 50);
                _t = _t.getFirstChild();
                this.beforeStatement("update", 50);
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 51: {
                        v = _t;
                        Object v_AST_in = null;
                        v_AST = this.astFactory.create(v);
                        this.match(_t, 51);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 22: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                f = _t == ASTNULL ? null : _t;
                this.fromClause(_t);
                _t = this._retTree;
                f_AST = this.returnAST;
                s = _t == ASTNULL ? null : _t;
                this.setClause(_t);
                _t = this._retTree;
                s_AST = this.returnAST;
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 52: {
                        w = _t == ASTNULL ? null : _t;
                        this.whereClause(_t);
                        _t = this._retTree;
                        w_AST = this.returnAST;
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST287;
                _t = __t287;
                _t = _t.getNextSibling();
                updateStatement_AST = currentAST.root;
                updateStatement_AST = this.astFactory.make(new ASTArray(4).add(u_AST).add(f_AST).add(s_AST).add(w_AST));
                this.beforeStatementCompletion("update");
                this.prepareVersioned(updateStatement_AST, v_AST);
                this.postProcessUpdate(updateStatement_AST);
                this.afterStatementCompletion("update");
                currentAST.root = updateStatement_AST;
                currentAST.child = updateStatement_AST != null && updateStatement_AST.getFirstChild() != null ? updateStatement_AST.getFirstChild() : updateStatement_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block12;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = updateStatement_AST;
        this._retTree = _t;
    }

    public final void deleteStatement(AST _t) throws RecognitionException {
        AST deleteStatement_AST;
        block7: {
            AST deleteStatement_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            deleteStatement_AST = null;
            try {
                AST __t291 = _t;
                AST tmp1_AST = null;
                AST tmp1_AST_in = null;
                tmp1_AST = this.astFactory.create(_t);
                tmp1_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp1_AST);
                ASTPair __currentAST291 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 13);
                _t = _t.getFirstChild();
                this.beforeStatement("delete", 13);
                this.fromClause(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 52: {
                        this.whereClause(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST291;
                _t = __t291;
                _t = _t.getNextSibling();
                deleteStatement_AST = currentAST.root;
                this.beforeStatementCompletion("delete");
                this.postProcessDelete(deleteStatement_AST);
                this.afterStatementCompletion("delete");
                deleteStatement_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = deleteStatement_AST;
        this._retTree = _t;
    }

    public final void insertStatement(AST _t) throws RecognitionException {
        AST insertStatement_AST;
        block2: {
            AST insertStatement_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            insertStatement_AST = null;
            try {
                AST __t294 = _t;
                AST tmp2_AST = null;
                AST tmp2_AST_in = null;
                tmp2_AST = this.astFactory.create(_t);
                tmp2_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp2_AST);
                ASTPair __currentAST294 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 29);
                _t = _t.getFirstChild();
                this.beforeStatement("insert", 29);
                this.intoClause(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                this.query(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST294;
                _t = __t294;
                _t = _t.getNextSibling();
                insertStatement_AST = currentAST.root;
                this.beforeStatementCompletion("insert");
                this.postProcessInsert(insertStatement_AST);
                this.afterStatementCompletion("insert");
                insertStatement_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = insertStatement_AST;
        this._retTree = _t;
    }

    public final void query(AST _t) throws RecognitionException {
        AST query_AST;
        block22: {
            AST query_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            query_AST = null;
            AST f_AST = null;
            AST f = null;
            AST s_AST = null;
            AST s = null;
            AST w_AST = null;
            AST w = null;
            AST g_AST = null;
            AST g = null;
            AST o_AST = null;
            AST o = null;
            try {
                AST __t312 = _t;
                AST tmp3_AST = null;
                AST tmp3_AST_in = null;
                tmp3_AST = this.astFactory.create(_t);
                tmp3_AST_in = _t;
                ASTPair __currentAST312 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 90);
                _t = _t.getFirstChild();
                this.beforeStatement("select", 45);
                AST __t313 = _t;
                AST tmp4_AST = null;
                AST tmp4_AST_in = null;
                tmp4_AST = this.astFactory.create(_t);
                tmp4_AST_in = _t;
                ASTPair __currentAST313 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 93);
                _t = _t.getFirstChild();
                f = _t == ASTNULL ? null : _t;
                this.fromClause(_t);
                _t = this._retTree;
                f_AST = this.returnAST;
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 45: {
                        s = _t == ASTNULL ? null : _t;
                        this.selectClause(_t);
                        _t = this._retTree;
                        s_AST = this.returnAST;
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST313;
                _t = __t313;
                _t = _t.getNextSibling();
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 52: {
                        w = _t == ASTNULL ? null : _t;
                        this.whereClause(_t);
                        _t = this._retTree;
                        w_AST = this.returnAST;
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
                        g = _t == ASTNULL ? null : _t;
                        this.groupClause(_t);
                        _t = this._retTree;
                        g_AST = this.returnAST;
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
                        o = _t == ASTNULL ? null : _t;
                        this.orderClause(_t);
                        _t = this._retTree;
                        o_AST = this.returnAST;
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST312;
                _t = __t312;
                _t = _t.getNextSibling();
                query_AST = currentAST.root;
                query_AST = this.astFactory.make(new ASTArray(6).add(this.astFactory.create(45, "SELECT")).add(s_AST).add(f_AST).add(w_AST).add(g_AST).add(o_AST));
                this.beforeStatementCompletion("select");
                this.processQuery(s_AST, query_AST);
                this.afterStatementCompletion("select");
                currentAST.root = query_AST;
                currentAST.child = query_AST != null && query_AST.getFirstChild() != null ? query_AST.getFirstChild() : query_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block22;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = query_AST;
        this._retTree = _t;
    }

    public final void fromClause(AST _t) throws RecognitionException {
        AST fromClause_AST;
        block2: {
            AST fromClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            fromClause_AST = null;
            AST f = null;
            AST f_AST = null;
            this.prepareFromClauseInputTree(fromClause_AST_in);
            try {
                AST __t355 = _t;
                f = _t == ASTNULL ? null : _t;
                Object f_AST_in = null;
                f_AST = this.astFactory.create(f);
                this.astFactory.addASTChild(currentAST, f_AST);
                ASTPair __currentAST355 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 22);
                _t = _t.getFirstChild();
                fromClause_AST = currentAST.root;
                this.pushFromClause(fromClause_AST, f);
                this.handleClauseStart(22);
                this.fromElementList(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST355;
                _t = __t355;
                _t = _t.getNextSibling();
                this.finishFromClause(f_AST);
                this.handleClauseEnd();
                fromClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = fromClause_AST;
        this._retTree = _t;
    }

    public final void setClause(AST _t) throws RecognitionException {
        AST setClause_AST;
        block4: {
            AST setClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            setClause_AST = null;
            try {
                AST __t303 = _t;
                AST tmp5_AST = null;
                AST tmp5_AST_in = null;
                tmp5_AST = this.astFactory.create(_t);
                tmp5_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp5_AST);
                ASTPair __currentAST303 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 46);
                _t = _t.getFirstChild();
                this.handleClauseStart(46);
                while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    if (_t.getType() != 107) break;
                    this.assignment(_t);
                    _t = this._retTree;
                    this.astFactory.addASTChild(currentAST, this.returnAST);
                }
                currentAST = __currentAST303;
                _t = __t303;
                _t = _t.getNextSibling();
                this.handleClauseEnd();
                setClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block4;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = setClause_AST;
        this._retTree = _t;
    }

    public final void whereClause(AST _t) throws RecognitionException {
        AST whereClause_AST;
        block2: {
            AST whereClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            whereClause_AST = null;
            AST w = null;
            AST w_AST = null;
            AST b_AST = null;
            AST b = null;
            try {
                AST __t380 = _t;
                w = _t == ASTNULL ? null : _t;
                Object w_AST_in = null;
                w_AST = this.astFactory.create(w);
                this.astFactory.addASTChild(currentAST, w_AST);
                ASTPair __currentAST380 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 52);
                _t = _t.getFirstChild();
                this.handleClauseStart(52);
                b = _t == ASTNULL ? null : _t;
                this.logicalExpr(_t);
                _t = this._retTree;
                b_AST = this.returnAST;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST380;
                _t = __t380;
                _t = _t.getNextSibling();
                whereClause_AST = currentAST.root;
                whereClause_AST = this.astFactory.make(new ASTArray(2).add(w_AST).add(b_AST));
                this.handleClauseEnd();
                currentAST.root = whereClause_AST;
                currentAST.child = whereClause_AST != null && whereClause_AST.getFirstChild() != null ? whereClause_AST.getFirstChild() : whereClause_AST;
                currentAST.advanceChildToEnd();
                whereClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = whereClause_AST;
        this._retTree = _t;
    }

    public final void intoClause(AST _t) throws RecognitionException {
        AST intoClause_AST;
        block2: {
            AST intoClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            intoClause_AST = null;
            AST ps_AST = null;
            AST ps = null;
            String p = null;
            try {
                AST __t296 = _t;
                AST tmp6_AST = null;
                AST tmp6_AST_in = null;
                tmp6_AST = this.astFactory.create(_t);
                tmp6_AST_in = _t;
                ASTPair __currentAST296 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 30);
                _t = _t.getFirstChild();
                this.handleClauseStart(30);
                p = this.path(_t);
                _t = this._retTree;
                ps = _t == ASTNULL ? null : _t;
                this.insertablePropertySpec(_t);
                _t = this._retTree;
                ps_AST = this.returnAST;
                currentAST = __currentAST296;
                _t = __t296;
                _t = _t.getNextSibling();
                intoClause_AST = currentAST.root;
                intoClause_AST = this.createIntoClause(p, ps);
                this.handleClauseEnd();
                currentAST.root = intoClause_AST;
                currentAST.child = intoClause_AST != null && intoClause_AST.getFirstChild() != null ? intoClause_AST.getFirstChild() : intoClause_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = intoClause_AST;
        this._retTree = _t;
    }

    public final String path(AST _t) throws RecognitionException {
        String p;
        AST path_AST;
        block7: {
            AST path_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            path_AST = null;
            AST a_AST = null;
            AST a = null;
            AST y_AST = null;
            AST y = null;
            p = "???";
            String x = "?x?";
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 98: 
                    case 110: {
                        a = _t == ASTNULL ? null : _t;
                        this.identifier(_t);
                        _t = this._retTree;
                        a_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        p = a.getText();
                        path_AST = currentAST.root;
                        break;
                    }
                    case 15: {
                        AST __t375 = _t;
                        AST tmp7_AST = null;
                        AST tmp7_AST_in = null;
                        tmp7_AST = this.astFactory.create(_t);
                        tmp7_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp7_AST);
                        ASTPair __currentAST375 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 15);
                        _t = _t.getFirstChild();
                        x = this.path(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        y = _t == ASTNULL ? null : _t;
                        this.identifier(_t);
                        _t = this._retTree;
                        y_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST375;
                        _t = __t375;
                        _t = _t.getNextSibling();
                        StringBuilder buf = new StringBuilder();
                        buf.append(x).append(".").append(y.getText());
                        p = buf.toString();
                        path_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = path_AST;
        this._retTree = _t;
        return p;
    }

    public final void insertablePropertySpec(AST _t) throws RecognitionException {
        AST insertablePropertySpec_AST;
        block5: {
            AST insertablePropertySpec_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            insertablePropertySpec_AST = null;
            try {
                AST __t299 = _t;
                AST tmp8_AST = null;
                AST tmp8_AST_in = null;
                tmp8_AST = this.astFactory.create(_t);
                tmp8_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp8_AST);
                ASTPair __currentAST299 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 91);
                _t = _t.getFirstChild();
                int _cnt301 = 0;
                while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    if (_t.getType() != 110) {
                        if (_cnt301 >= 1) break;
                        throw new NoViableAltException(_t);
                    }
                    AST tmp9_AST = null;
                    AST tmp9_AST_in = null;
                    tmp9_AST = this.astFactory.create(_t);
                    tmp9_AST_in = _t;
                    this.astFactory.addASTChild(currentAST, tmp9_AST);
                    this.match(_t, 110);
                    _t = _t.getNextSibling();
                    ++_cnt301;
                }
                currentAST = __currentAST299;
                _t = __t299;
                _t = _t.getNextSibling();
                insertablePropertySpec_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block5;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = insertablePropertySpec_AST;
        this._retTree = _t;
    }

    public final void assignment(AST _t) throws RecognitionException {
        AST assignment_AST;
        block2: {
            AST assignment_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            assignment_AST = null;
            AST p_AST = null;
            AST p = null;
            try {
                AST __t307 = _t;
                AST tmp10_AST = null;
                AST tmp10_AST_in = null;
                tmp10_AST = this.astFactory.create(_t);
                tmp10_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp10_AST);
                ASTPair __currentAST307 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 107);
                _t = _t.getFirstChild();
                p = _t == ASTNULL ? null : _t;
                this.propertyRef(_t);
                _t = this._retTree;
                p_AST = this.returnAST;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                this.resolve(p_AST);
                this.newValue(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST307;
                _t = __t307;
                _t = _t.getNextSibling();
                assignment_AST = currentAST.root;
                this.evaluateAssignment(assignment_AST);
                assignment_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = assignment_AST;
        this._retTree = _t;
    }

    public final void propertyRef(AST _t) throws RecognitionException {
        AST propertyRef_AST;
        block10: {
            AST propertyRef_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            propertyRef_AST = null;
            AST mcr_AST = null;
            AST mcr = null;
            AST d = null;
            AST d_AST = null;
            AST lhs_AST = null;
            AST lhs = null;
            AST rhs_AST = null;
            AST rhs = null;
            AST p_AST = null;
            AST p = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 70: 
                    case 71: 
                    case 72: {
                        mcr = _t == ASTNULL ? null : _t;
                        this.mapComponentReference(_t);
                        _t = this._retTree;
                        mcr_AST = this.returnAST;
                        propertyRef_AST = currentAST.root;
                        this.resolve(mcr_AST);
                        currentAST.root = propertyRef_AST = mcr_AST;
                        currentAST.child = propertyRef_AST != null && propertyRef_AST.getFirstChild() != null ? propertyRef_AST.getFirstChild() : propertyRef_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 15: {
                        AST __t473 = _t;
                        d = _t == ASTNULL ? null : _t;
                        Object d_AST_in = null;
                        d_AST = this.astFactory.create(d);
                        ASTPair __currentAST473 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 15);
                        _t = _t.getFirstChild();
                        lhs = _t == ASTNULL ? null : _t;
                        this.propertyRefLhs(_t);
                        _t = this._retTree;
                        lhs_AST = this.returnAST;
                        rhs = _t == ASTNULL ? null : _t;
                        this.propertyName(_t);
                        _t = this._retTree;
                        rhs_AST = this.returnAST;
                        currentAST = __currentAST473;
                        _t = __t473;
                        _t = _t.getNextSibling();
                        propertyRef_AST = currentAST.root;
                        propertyRef_AST = this.astFactory.make(new ASTArray(3).add(d_AST).add(lhs_AST).add(rhs_AST));
                        currentAST.root = propertyRef_AST = this.lookupProperty(propertyRef_AST, false, true);
                        currentAST.child = propertyRef_AST != null && propertyRef_AST.getFirstChild() != null ? propertyRef_AST.getFirstChild() : propertyRef_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 98: 
                    case 110: {
                        p = _t == ASTNULL ? null : _t;
                        this.identifier(_t);
                        _t = this._retTree;
                        p_AST = this.returnAST;
                        propertyRef_AST = currentAST.root;
                        if (this.isNonQualifiedPropertyRef(p_AST)) {
                            propertyRef_AST = this.lookupNonQualifiedProperty(p_AST);
                        } else {
                            this.resolve(p_AST);
                            propertyRef_AST = p_AST;
                        }
                        currentAST.root = propertyRef_AST;
                        currentAST.child = propertyRef_AST != null && propertyRef_AST.getFirstChild() != null ? propertyRef_AST.getFirstChild() : propertyRef_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block10;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = propertyRef_AST;
        this._retTree = _t;
    }

    public final void newValue(AST _t) throws RecognitionException {
        AST newValue_AST;
        block7: {
            AST newValue_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            newValue_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
                        this.expr(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        newValue_AST = currentAST.root;
                        break;
                    }
                    case 90: {
                        this.query(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        newValue_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = newValue_AST;
        this._retTree = _t;
    }

    public final void expr(AST _t, AST predicateNode) throws RecognitionException {
        AST expr_AST;
        block14: {
            AST expr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            expr_AST = null;
            AST ae_AST = null;
            AST ae = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 15: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 82: 
                    case 98: 
                    case 110: {
                        ae = _t == ASTNULL ? null : _t;
                        this.addrExpr(_t, true);
                        _t = this._retTree;
                        ae_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.resolve(ae_AST, predicateNode);
                        expr_AST = currentAST.root;
                        break;
                    }
                    case 97: {
                        AST __t419 = _t;
                        AST tmp11_AST = null;
                        AST tmp11_AST_in = null;
                        tmp11_AST = this.astFactory.create(_t);
                        tmp11_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp11_AST);
                        ASTPair __currentAST419 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 97);
                        _t = _t.getFirstChild();
                        while (true) {
                            if (_t == null) {
                                _t = ASTNULL;
                            }
                            if (!_tokenSet_0.member(_t.getType())) break;
                            this.expr(_t, predicateNode);
                            _t = this._retTree;
                            this.astFactory.addASTChild(currentAST, this.returnAST);
                        }
                        currentAST = __currentAST419;
                        _t = __t419;
                        _t = _t.getNextSibling();
                        expr_AST = currentAST.root;
                        break;
                    }
                    case 20: 
                    case 39: 
                    case 49: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 128: 
                    case 131: {
                        this.constant(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        expr_AST = currentAST.root;
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
                        this.arithmeticExpr(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        expr_AST = currentAST.root;
                        break;
                    }
                    case 73: 
                    case 77: 
                    case 85: 
                    case 94: {
                        this.functionCall(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        expr_AST = currentAST.root;
                        break;
                    }
                    case 129: 
                    case 130: {
                        this.parameter(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        expr_AST = currentAST.root;
                        break;
                    }
                    case 12: {
                        this.count(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        expr_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block14;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = expr_AST;
        this._retTree = _t;
    }

    public final void selectClause(AST _t) throws RecognitionException {
        AST selectClause_AST;
        block7: {
            AST selectClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            selectClause_AST = null;
            AST d = null;
            AST d_AST = null;
            AST x_AST = null;
            AST x = null;
            try {
                AST __t335 = _t;
                AST tmp12_AST = null;
                AST tmp12_AST_in = null;
                tmp12_AST = this.astFactory.create(_t);
                tmp12_AST_in = _t;
                ASTPair __currentAST335 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 45);
                _t = _t.getFirstChild();
                this.handleClauseStart(45);
                this.beforeSelectClause();
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 16: {
                        d = _t;
                        Object d_AST_in = null;
                        d_AST = this.astFactory.create(d);
                        this.match(_t, 16);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 4: 
                    case 6: 
                    case 7: 
                    case 10: 
                    case 12: 
                    case 15: 
                    case 17: 
                    case 19: 
                    case 20: 
                    case 26: 
                    case 27: 
                    case 34: 
                    case 38: 
                    case 39: 
                    case 40: 
                    case 49: 
                    case 56: 
                    case 67: 
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
                    case 90: 
                    case 94: 
                    case 95: 
                    case 98: 
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
                    case 129: 
                    case 130: 
                    case 131: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                x = _t == ASTNULL ? null : _t;
                this.selectExprList(_t);
                _t = this._retTree;
                x_AST = this.returnAST;
                currentAST = __currentAST335;
                _t = __t335;
                _t = _t.getNextSibling();
                selectClause_AST = currentAST.root;
                selectClause_AST = this.astFactory.make(new ASTArray(3).add(this.astFactory.create(143, "{select clause}")).add(d_AST).add(x_AST));
                this.handleClauseEnd();
                currentAST.root = selectClause_AST;
                currentAST.child = selectClause_AST != null && selectClause_AST.getFirstChild() != null ? selectClause_AST.getFirstChild() : selectClause_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = selectClause_AST;
        this._retTree = _t;
    }

    public final void groupClause(AST _t) throws RecognitionException {
        AST groupClause_AST;
        block13: {
            AST groupClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            groupClause_AST = null;
            try {
                AST __t329 = _t;
                AST tmp13_AST = null;
                AST tmp13_AST_in = null;
                tmp13_AST = this.astFactory.create(_t);
                tmp13_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp13_AST);
                ASTPair __currentAST329 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 24);
                _t = _t.getFirstChild();
                this.handleClauseStart(24);
                int _cnt331 = 0;
                while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    if ((_t.getType() == 98 || _t.getType() == 110) && this.isGroupExpressionResultVariableRef(_t)) {
                        this.resultVariableRef(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                    } else if (_tokenSet_0.member(_t.getType())) {
                        this.expr(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                    } else {
                        if (_cnt331 >= 1) break;
                        throw new NoViableAltException(_t);
                    }
                    ++_cnt331;
                }
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 25: {
                        AST __t333 = _t;
                        AST tmp14_AST = null;
                        AST tmp14_AST_in = null;
                        tmp14_AST = this.astFactory.create(_t);
                        tmp14_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp14_AST);
                        ASTPair __currentAST333 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 25);
                        _t = _t.getFirstChild();
                        this.logicalExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST333;
                        _t = __t333;
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
                currentAST = __currentAST329;
                _t = __t329;
                _t = _t.getNextSibling();
                this.handleClauseEnd();
                groupClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block13;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = groupClause_AST;
        this._retTree = _t;
    }

    public final void orderClause(AST _t) throws RecognitionException {
        AST orderClause_AST;
        block2: {
            AST orderClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            orderClause_AST = null;
            try {
                AST __t319 = _t;
                AST tmp15_AST = null;
                AST tmp15_AST_in = null;
                tmp15_AST = this.astFactory.create(_t);
                tmp15_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp15_AST);
                ASTPair __currentAST319 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 41);
                _t = _t.getFirstChild();
                this.handleClauseStart(41);
                this.orderExprs(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST319;
                _t = __t319;
                _t = _t.getNextSibling();
                this.handleClauseEnd();
                orderClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = orderClause_AST;
        this._retTree = _t;
    }

    public final void orderExprs(AST _t) throws RecognitionException {
        AST orderExprs_AST;
        block18: {
            AST orderExprs_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            orderExprs_AST = null;
            try {
                this.orderExpr(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 8: {
                        AST tmp16_AST = null;
                        AST tmp16_AST_in = null;
                        tmp16_AST = this.astFactory.create(_t);
                        tmp16_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp16_AST);
                        this.match(_t, 8);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 14: {
                        AST tmp17_AST = null;
                        AST tmp17_AST_in = null;
                        tmp17_AST = this.astFactory.create(_t);
                        tmp17_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp17_AST);
                        this.match(_t, 14);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 3: 
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
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
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
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
                        this.nullOrdering(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                    }
                    case 3: 
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
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
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
                        this.orderExprs(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                orderExprs_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block18;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = orderExprs_AST;
        this._retTree = _t;
    }

    public final void orderExpr(AST _t) throws RecognitionException {
        AST orderExpr_AST;
        block5: {
            AST orderExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            orderExpr_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                if ((_t.getType() == 98 || _t.getType() == 110) && this.isOrderExpressionResultVariableRef(_t)) {
                    this.resultVariableRef(_t);
                    _t = this._retTree;
                    this.astFactory.addASTChild(currentAST, this.returnAST);
                    orderExpr_AST = currentAST.root;
                    break block5;
                }
                if (_tokenSet_0.member(_t.getType())) {
                    this.expr(_t, null);
                    _t = this._retTree;
                    this.astFactory.addASTChild(currentAST, this.returnAST);
                    orderExpr_AST = currentAST.root;
                    break block5;
                }
                throw new NoViableAltException(_t);
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block5;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = orderExpr_AST;
        this._retTree = _t;
    }

    public final void nullOrdering(AST _t) throws RecognitionException {
        AST nullOrdering_AST;
        block2: {
            AST nullOrdering_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            nullOrdering_AST = null;
            try {
                AST tmp18_AST = null;
                AST tmp18_AST_in = null;
                tmp18_AST = this.astFactory.create(_t);
                tmp18_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp18_AST);
                this.match(_t, 53);
                _t = _t.getNextSibling();
                this.nullPrecedence(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                nullOrdering_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = nullOrdering_AST;
        this._retTree = _t;
    }

    public final void nullPrecedence(AST _t) throws RecognitionException {
        AST nullPrecedence_AST;
        block7: {
            AST nullPrecedence_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            nullPrecedence_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 54: {
                        AST tmp19_AST = null;
                        AST tmp19_AST_in = null;
                        tmp19_AST = this.astFactory.create(_t);
                        tmp19_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp19_AST);
                        this.match(_t, 54);
                        _t = _t.getNextSibling();
                        nullPrecedence_AST = currentAST.root;
                        break;
                    }
                    case 55: {
                        AST tmp20_AST = null;
                        AST tmp20_AST_in = null;
                        tmp20_AST = this.astFactory.create(_t);
                        tmp20_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp20_AST);
                        this.match(_t, 55);
                        _t = _t.getNextSibling();
                        nullPrecedence_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = nullPrecedence_AST;
        this._retTree = _t;
    }

    public final void resultVariableRef(AST _t) throws RecognitionException {
        AST resultVariableRef_AST;
        block2: {
            AST resultVariableRef_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            resultVariableRef_AST = null;
            AST i_AST = null;
            AST i = null;
            try {
                i = _t == ASTNULL ? null : _t;
                this.identifier(_t);
                _t = this._retTree;
                i_AST = this.returnAST;
                resultVariableRef_AST = currentAST.root;
                resultVariableRef_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(156, i.getText())));
                this.handleResultVariableRef(resultVariableRef_AST);
                currentAST.root = resultVariableRef_AST;
                currentAST.child = resultVariableRef_AST != null && resultVariableRef_AST.getFirstChild() != null ? resultVariableRef_AST.getFirstChild() : resultVariableRef_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = resultVariableRef_AST;
        this._retTree = _t;
    }

    public final void identifier(AST _t) throws RecognitionException {
        AST identifier_AST;
        block7: {
            AST identifier_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            identifier_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 110: {
                        AST tmp21_AST = null;
                        AST tmp21_AST_in = null;
                        tmp21_AST = this.astFactory.create(_t);
                        tmp21_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp21_AST);
                        this.match(_t, 110);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 98: {
                        AST tmp22_AST = null;
                        AST tmp22_AST_in = null;
                        tmp22_AST = this.astFactory.create(_t);
                        tmp22_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp22_AST);
                        this.match(_t, 98);
                        _t = _t.getNextSibling();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                identifier_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = identifier_AST;
        this._retTree = _t;
    }

    public final void logicalExpr(AST _t) throws RecognitionException {
        AST logicalExpr_AST;
        block9: {
            AST logicalExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            logicalExpr_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 6: {
                        AST __t382 = _t;
                        AST tmp23_AST = null;
                        AST tmp23_AST_in = null;
                        tmp23_AST = this.astFactory.create(_t);
                        tmp23_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp23_AST);
                        ASTPair __currentAST382 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 6);
                        _t = _t.getFirstChild();
                        this.logicalExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.logicalExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST382;
                        _t = __t382;
                        _t = _t.getNextSibling();
                        logicalExpr_AST = currentAST.root;
                        break;
                    }
                    case 40: {
                        AST __t383 = _t;
                        AST tmp24_AST = null;
                        AST tmp24_AST_in = null;
                        tmp24_AST = this.astFactory.create(_t);
                        tmp24_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp24_AST);
                        ASTPair __currentAST383 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 40);
                        _t = _t.getFirstChild();
                        this.logicalExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.logicalExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST383;
                        _t = __t383;
                        _t = _t.getNextSibling();
                        logicalExpr_AST = currentAST.root;
                        break;
                    }
                    case 38: {
                        AST __t384 = _t;
                        AST tmp25_AST = null;
                        AST tmp25_AST_in = null;
                        tmp25_AST = this.astFactory.create(_t);
                        tmp25_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp25_AST);
                        ASTPair __currentAST384 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 38);
                        _t = _t.getFirstChild();
                        this.logicalExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST384;
                        _t = __t384;
                        _t = _t.getNextSibling();
                        logicalExpr_AST = currentAST.root;
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
                        this.comparisonExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        logicalExpr_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block9;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = logicalExpr_AST;
        this._retTree = _t;
    }

    public final void selectExprList(AST _t) throws RecognitionException {
        AST selectExprList_AST;
        block8: {
            AST selectExprList_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            selectExprList_AST = null;
            boolean oldInSelect = this.inSelect;
            this.inSelect = true;
            try {
                int _cnt339 = 0;
                block6: while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 4: 
                        case 6: 
                        case 10: 
                        case 12: 
                        case 15: 
                        case 17: 
                        case 19: 
                        case 20: 
                        case 26: 
                        case 27: 
                        case 34: 
                        case 38: 
                        case 39: 
                        case 40: 
                        case 49: 
                        case 56: 
                        case 67: 
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
                        case 90: 
                        case 94: 
                        case 95: 
                        case 98: 
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
                        case 129: 
                        case 130: 
                        case 131: {
                            this.selectExpr(_t);
                            _t = this._retTree;
                            this.astFactory.addASTChild(currentAST, this.returnAST);
                            break;
                        }
                        case 7: {
                            this.aliasedSelectExpr(_t);
                            _t = this._retTree;
                            this.astFactory.addASTChild(currentAST, this.returnAST);
                            break;
                        }
                        default: {
                            if (_cnt339 >= 1) break block6;
                            throw new NoViableAltException(_t);
                        }
                    }
                    ++_cnt339;
                }
                this.inSelect = oldInSelect;
                selectExprList_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block8;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = selectExprList_AST;
        this._retTree = _t;
    }

    public final void selectExpr(AST _t) throws RecognitionException {
        AST selectExpr_AST;
        block17: {
            AST selectExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            selectExpr_AST = null;
            AST p_AST = null;
            AST p = null;
            AST ar2_AST = null;
            AST ar2 = null;
            AST ar3_AST = null;
            AST ar3 = null;
            AST con_AST = null;
            AST con = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 15: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 98: 
                    case 110: {
                        p = _t == ASTNULL ? null : _t;
                        this.propertyRef(_t);
                        _t = this._retTree;
                        p_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.resolveSelectExpression(p_AST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 4: {
                        AST __t343 = _t;
                        AST tmp26_AST = null;
                        AST tmp26_AST_in = null;
                        tmp26_AST = this.astFactory.create(_t);
                        tmp26_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp26_AST);
                        ASTPair __currentAST343 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 4);
                        _t = _t.getFirstChild();
                        ar2 = _t == ASTNULL ? null : _t;
                        this.aliasRef(_t);
                        _t = this._retTree;
                        ar2_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST343;
                        _t = __t343;
                        _t = _t.getNextSibling();
                        selectExpr_AST = currentAST.root;
                        this.resolveSelectExpression(ar2_AST);
                        currentAST.root = selectExpr_AST = ar2_AST;
                        currentAST.child = selectExpr_AST != null && selectExpr_AST.getFirstChild() != null ? selectExpr_AST.getFirstChild() : selectExpr_AST;
                        currentAST.advanceChildToEnd();
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 67: {
                        AST __t344 = _t;
                        AST tmp27_AST = null;
                        AST tmp27_AST_in = null;
                        tmp27_AST = this.astFactory.create(_t);
                        tmp27_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp27_AST);
                        ASTPair __currentAST344 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 67);
                        _t = _t.getFirstChild();
                        ar3 = _t == ASTNULL ? null : _t;
                        this.aliasRef(_t);
                        _t = this._retTree;
                        ar3_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST344;
                        _t = __t344;
                        _t = _t.getNextSibling();
                        selectExpr_AST = currentAST.root;
                        this.resolveSelectExpression(ar3_AST);
                        currentAST.root = selectExpr_AST = ar3_AST;
                        currentAST.child = selectExpr_AST != null && selectExpr_AST.getFirstChild() != null ? selectExpr_AST.getFirstChild() : selectExpr_AST;
                        currentAST.advanceChildToEnd();
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 75: {
                        con = _t == ASTNULL ? null : _t;
                        this.constructor(_t);
                        _t = this._retTree;
                        con_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.processConstructor(con_AST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 73: 
                    case 77: 
                    case 85: 
                    case 94: {
                        this.functionCall(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 12: {
                        this.count(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 17: 
                    case 27: {
                        this.collectionFunction(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 20: 
                    case 39: 
                    case 49: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 128: 
                    case 131: {
                        this.constant(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
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
                        this.arithmeticExpr(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
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
                        this.logicalExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 129: 
                    case 130: {
                        this.parameter(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    case 90: {
                        this.query(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        selectExpr_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block17;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = selectExpr_AST;
        this._retTree = _t;
    }

    public final void aliasedSelectExpr(AST _t) throws RecognitionException {
        AST aliasedSelectExpr_AST;
        block2: {
            AST aliasedSelectExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            aliasedSelectExpr_AST = null;
            AST se_AST = null;
            AST se = null;
            AST i_AST = null;
            AST i = null;
            try {
                AST __t341 = _t;
                AST tmp28_AST = null;
                AST tmp28_AST_in = null;
                tmp28_AST = this.astFactory.create(_t);
                tmp28_AST_in = _t;
                ASTPair __currentAST341 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 7);
                _t = _t.getFirstChild();
                se = _t == ASTNULL ? null : _t;
                this.selectExpr(_t);
                _t = this._retTree;
                se_AST = this.returnAST;
                i = _t == ASTNULL ? null : _t;
                this.identifier(_t);
                _t = this._retTree;
                i_AST = this.returnAST;
                currentAST = __currentAST341;
                _t = __t341;
                _t = _t.getNextSibling();
                aliasedSelectExpr_AST = currentAST.root;
                this.setAlias(se_AST, i_AST);
                currentAST.root = aliasedSelectExpr_AST = se_AST;
                currentAST.child = aliasedSelectExpr_AST != null && aliasedSelectExpr_AST.getFirstChild() != null ? aliasedSelectExpr_AST.getFirstChild() : aliasedSelectExpr_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = aliasedSelectExpr_AST;
        this._retTree = _t;
    }

    public final void aliasRef(AST _t) throws RecognitionException {
        AST aliasRef_AST;
        block2: {
            AST aliasRef_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            aliasRef_AST = null;
            AST i_AST = null;
            AST i = null;
            try {
                i = _t == ASTNULL ? null : _t;
                this.identifier(_t);
                _t = this._retTree;
                i_AST = this.returnAST;
                aliasRef_AST = currentAST.root;
                aliasRef_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(146, i.getText())));
                this.lookupAlias(aliasRef_AST);
                currentAST.root = aliasRef_AST;
                currentAST.child = aliasRef_AST != null && aliasRef_AST.getFirstChild() != null ? aliasRef_AST.getFirstChild() : aliasRef_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = aliasRef_AST;
        this._retTree = _t;
    }

    public final void constructor(AST _t) throws RecognitionException {
        AST constructor_AST;
        block8: {
            AST constructor_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            constructor_AST = null;
            String className = null;
            try {
                AST __t350 = _t;
                AST tmp29_AST = null;
                AST tmp29_AST_in = null;
                tmp29_AST = this.astFactory.create(_t);
                tmp29_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp29_AST);
                ASTPair __currentAST350 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 75);
                _t = _t.getFirstChild();
                className = this.path(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                block6: while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    switch (_t.getType()) {
                        case 4: 
                        case 6: 
                        case 10: 
                        case 12: 
                        case 15: 
                        case 17: 
                        case 19: 
                        case 20: 
                        case 26: 
                        case 27: 
                        case 34: 
                        case 38: 
                        case 39: 
                        case 40: 
                        case 49: 
                        case 56: 
                        case 67: 
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
                        case 90: 
                        case 94: 
                        case 95: 
                        case 98: 
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
                        case 129: 
                        case 130: 
                        case 131: {
                            this.selectExpr(_t);
                            _t = this._retTree;
                            this.astFactory.addASTChild(currentAST, this.returnAST);
                            continue block6;
                        }
                        case 7: {
                            this.aliasedSelectExpr(_t);
                            _t = this._retTree;
                            this.astFactory.addASTChild(currentAST, this.returnAST);
                            continue block6;
                        }
                    }
                    break;
                }
                currentAST = __currentAST350;
                _t = __t350;
                _t = _t.getNextSibling();
                constructor_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block8;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = constructor_AST;
        this._retTree = _t;
    }

    public final void functionCall(AST _t) throws RecognitionException {
        AST functionCall_AST;
        block16: {
            AST functionCall_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            functionCall_AST = null;
            AST path_AST = null;
            AST path = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 94: {
                        AST __t451 = _t;
                        AST tmp30_AST = null;
                        AST tmp30_AST_in = null;
                        tmp30_AST = this.astFactory.create(_t);
                        tmp30_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp30_AST);
                        ASTPair __currentAST451 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 94);
                        _t = _t.getFirstChild();
                        this.inSize = true;
                        path = _t == ASTNULL ? null : _t;
                        this.collectionPath(_t);
                        _t = this._retTree;
                        path_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST451;
                        _t = __t451;
                        _t = _t.getNextSibling();
                        functionCall_AST = currentAST.root;
                        functionCall_AST = this.createCollectionSizeFunction(path_AST, this.inSelect);
                        this.inSize = false;
                        currentAST.root = functionCall_AST;
                        currentAST.child = functionCall_AST != null && functionCall_AST.getFirstChild() != null ? functionCall_AST.getFirstChild() : functionCall_AST;
                        currentAST.advanceChildToEnd();
                        functionCall_AST = currentAST.root;
                        break;
                    }
                    case 85: {
                        AST __t452 = _t;
                        AST tmp31_AST = null;
                        AST tmp31_AST_in = null;
                        tmp31_AST = this.astFactory.create(_t);
                        tmp31_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp31_AST);
                        ASTPair __currentAST452 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 85);
                        _t = _t.getFirstChild();
                        this.inFunctionCall = true;
                        this.pathAsIdent(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        switch (_t.getType()) {
                            case 79: {
                                AST __t454 = _t;
                                AST tmp32_AST = null;
                                AST tmp32_AST_in = null;
                                tmp32_AST = this.astFactory.create(_t);
                                tmp32_AST_in = _t;
                                this.astFactory.addASTChild(currentAST, tmp32_AST);
                                ASTPair __currentAST454 = currentAST.copy();
                                currentAST.root = currentAST.child;
                                currentAST.child = null;
                                this.match(_t, 79);
                                _t = _t.getFirstChild();
                                while (true) {
                                    if (_t == null) {
                                        _t = ASTNULL;
                                    }
                                    if (!_tokenSet_1.member(_t.getType())) break;
                                    this.exprOrSubquery(_t, null);
                                    _t = this._retTree;
                                    this.astFactory.addASTChild(currentAST, this.returnAST);
                                }
                                currentAST = __currentAST454;
                                _t = __t454;
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
                        currentAST = __currentAST452;
                        _t = __t452;
                        _t = _t.getNextSibling();
                        functionCall_AST = currentAST.root;
                        this.processFunction(functionCall_AST, this.inSelect);
                        this.inFunctionCall = false;
                        functionCall_AST = currentAST.root;
                        break;
                    }
                    case 77: {
                        AST __t457 = _t;
                        AST tmp33_AST = null;
                        AST tmp33_AST_in = null;
                        tmp33_AST = this.astFactory.create(_t);
                        tmp33_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp33_AST);
                        ASTPair __currentAST457 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 77);
                        _t = _t.getFirstChild();
                        this.inFunctionCall = true;
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.pathAsIdent(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST457;
                        _t = __t457;
                        _t = _t.getNextSibling();
                        functionCall_AST = currentAST.root;
                        this.processCastFunction(functionCall_AST, this.inSelect);
                        this.inFunctionCall = false;
                        functionCall_AST = currentAST.root;
                        break;
                    }
                    case 73: {
                        AST __t458 = _t;
                        AST tmp34_AST = null;
                        AST tmp34_AST_in = null;
                        tmp34_AST = this.astFactory.create(_t);
                        tmp34_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp34_AST);
                        ASTPair __currentAST458 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 73);
                        _t = _t.getFirstChild();
                        this.aggregateExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST458;
                        _t = __t458;
                        _t = _t.getNextSibling();
                        functionCall_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block16;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = functionCall_AST;
        this._retTree = _t;
    }

    public final void count(AST _t) throws RecognitionException {
        AST count_AST;
        block13: {
            AST count_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            count_AST = null;
            try {
                AST __t346 = _t;
                AST tmp35_AST = null;
                AST tmp35_AST_in = null;
                tmp35_AST = this.astFactory.create(_t);
                tmp35_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp35_AST);
                ASTPair __currentAST346 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 12);
                _t = _t.getFirstChild();
                this.inCount = true;
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 16: {
                        AST tmp36_AST = null;
                        AST tmp36_AST_in = null;
                        tmp36_AST = this.astFactory.create(_t);
                        tmp36_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp36_AST);
                        this.match(_t, 16);
                        _t = _t.getNextSibling();
                        this.inCountDistinct = true;
                        break;
                    }
                    case 4: {
                        AST tmp37_AST = null;
                        AST tmp37_AST_in = null;
                        tmp37_AST = this.astFactory.create(_t);
                        tmp37_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp37_AST);
                        this.match(_t, 4);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 12: 
                    case 15: 
                    case 17: 
                    case 20: 
                    case 27: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 90: 
                    case 92: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
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
                    case 12: 
                    case 15: 
                    case 17: 
                    case 20: 
                    case 27: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 90: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
                        this.aggregateExpr(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                    }
                    case 92: {
                        AST tmp38_AST = null;
                        AST tmp38_AST_in = null;
                        tmp38_AST = this.astFactory.create(_t);
                        tmp38_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp38_AST);
                        this.match(_t, 92);
                        _t = _t.getNextSibling();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST346;
                _t = __t346;
                _t = _t.getNextSibling();
                this.inCount = false;
                this.inCountDistinct = false;
                count_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block13;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = count_AST;
        this._retTree = _t;
    }

    public final void collectionFunction(AST _t) throws RecognitionException {
        AST collectionFunction_AST;
        block7: {
            AST collectionFunction_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            collectionFunction_AST = null;
            AST e = null;
            AST e_AST = null;
            AST p1_AST = null;
            AST p1 = null;
            AST i = null;
            AST i_AST = null;
            AST p2_AST = null;
            AST p2 = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 17: {
                        AST __t448 = _t;
                        e = _t == ASTNULL ? null : _t;
                        Object e_AST_in = null;
                        e_AST = this.astFactory.create(e);
                        this.astFactory.addASTChild(currentAST, e_AST);
                        ASTPair __currentAST448 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 17);
                        _t = _t.getFirstChild();
                        this.inFunctionCall = true;
                        p1 = _t == ASTNULL ? null : _t;
                        this.propertyRef(_t);
                        _t = this._retTree;
                        p1_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.resolve(p1_AST);
                        currentAST = __currentAST448;
                        _t = __t448;
                        _t = _t.getNextSibling();
                        this.processFunction(e_AST, this.inSelect);
                        this.inFunctionCall = false;
                        collectionFunction_AST = currentAST.root;
                        break;
                    }
                    case 27: {
                        AST __t449 = _t;
                        i = _t == ASTNULL ? null : _t;
                        Object i_AST_in = null;
                        i_AST = this.astFactory.create(i);
                        this.astFactory.addASTChild(currentAST, i_AST);
                        ASTPair __currentAST449 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 27);
                        _t = _t.getFirstChild();
                        this.inFunctionCall = true;
                        p2 = _t == ASTNULL ? null : _t;
                        this.propertyRef(_t);
                        _t = this._retTree;
                        p2_AST = this.returnAST;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.resolve(p2_AST);
                        currentAST = __currentAST449;
                        _t = __t449;
                        _t = _t.getNextSibling();
                        this.processFunction(i_AST, this.inSelect);
                        this.inFunctionCall = false;
                        collectionFunction_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = collectionFunction_AST;
        this._retTree = _t;
    }

    public final void constant(AST _t) throws RecognitionException {
        AST constant_AST;
        block10: {
            AST constant_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            constant_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 128: 
                    case 131: {
                        this.literal(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        constant_AST = currentAST.root;
                        break;
                    }
                    case 39: {
                        AST tmp39_AST = null;
                        AST tmp39_AST_in = null;
                        tmp39_AST = this.astFactory.create(_t);
                        tmp39_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp39_AST);
                        this.match(_t, 39);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                    case 49: {
                        AST tmp40_AST = null;
                        AST tmp40_AST_in = null;
                        tmp40_AST = this.astFactory.create(_t);
                        tmp40_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp40_AST);
                        this.match(_t, 49);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        this.processBoolean(constant_AST);
                        constant_AST = currentAST.root;
                        break;
                    }
                    case 20: {
                        AST tmp41_AST = null;
                        AST tmp41_AST_in = null;
                        tmp41_AST = this.astFactory.create(_t);
                        tmp41_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp41_AST);
                        this.match(_t, 20);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        this.processBoolean(constant_AST);
                        constant_AST = currentAST.root;
                        break;
                    }
                    case 105: {
                        AST tmp42_AST = null;
                        AST tmp42_AST_in = null;
                        tmp42_AST = this.astFactory.create(_t);
                        tmp42_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp42_AST);
                        this.match(_t, 105);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block10;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = constant_AST;
        this._retTree = _t;
    }

    public final void arithmeticExpr(AST _t, AST predicateNode) throws RecognitionException {
        AST arithmeticExpr_AST;
        block12: {
            AST arithmeticExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            arithmeticExpr_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 121: {
                        AST __t423 = _t;
                        AST tmp43_AST = null;
                        AST tmp43_AST_in = null;
                        tmp43_AST = this.astFactory.create(_t);
                        tmp43_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp43_AST);
                        ASTPair __currentAST423 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 121);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST423;
                        _t = __t423;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        this.prepareArithmeticOperator(arithmeticExpr_AST);
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                    case 122: {
                        AST __t424 = _t;
                        AST tmp44_AST = null;
                        AST tmp44_AST_in = null;
                        tmp44_AST = this.astFactory.create(_t);
                        tmp44_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp44_AST);
                        ASTPair __currentAST424 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 122);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST424;
                        _t = __t424;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        this.prepareArithmeticOperator(arithmeticExpr_AST);
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                    case 124: {
                        AST __t425 = _t;
                        AST tmp45_AST = null;
                        AST tmp45_AST_in = null;
                        tmp45_AST = this.astFactory.create(_t);
                        tmp45_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp45_AST);
                        ASTPair __currentAST425 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 124);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST425;
                        _t = __t425;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        this.prepareArithmeticOperator(arithmeticExpr_AST);
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                    case 125: {
                        AST __t426 = _t;
                        AST tmp46_AST = null;
                        AST tmp46_AST_in = null;
                        tmp46_AST = this.astFactory.create(_t);
                        tmp46_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp46_AST);
                        ASTPair __currentAST426 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 125);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST426;
                        _t = __t426;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        this.prepareArithmeticOperator(arithmeticExpr_AST);
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                    case 123: {
                        AST __t427 = _t;
                        AST tmp47_AST = null;
                        AST tmp47_AST_in = null;
                        tmp47_AST = this.astFactory.create(_t);
                        tmp47_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp47_AST);
                        ASTPair __currentAST427 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 123);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST427;
                        _t = __t427;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        this.prepareArithmeticOperator(arithmeticExpr_AST);
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                    case 95: {
                        AST __t428 = _t;
                        AST tmp48_AST = null;
                        AST tmp48_AST_in = null;
                        tmp48_AST = this.astFactory.create(_t);
                        tmp48_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp48_AST);
                        ASTPair __currentAST428 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 95);
                        _t = _t.getFirstChild();
                        this.expr(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST428;
                        _t = __t428;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        this.prepareArithmeticOperator(arithmeticExpr_AST);
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                    case 56: 
                    case 76: {
                        this.caseExpr(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block12;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = arithmeticExpr_AST;
        this._retTree = _t;
    }

    public final void parameter(AST _t) throws RecognitionException {
        AST parameter_AST;
        block12: {
            AST parameter_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            parameter_AST = null;
            AST c = null;
            AST c_AST = null;
            AST a_AST = null;
            AST a = null;
            AST p = null;
            AST p_AST = null;
            AST n = null;
            AST n_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 129: {
                        AST __t482 = _t;
                        c = _t == ASTNULL ? null : _t;
                        Object c_AST_in = null;
                        c_AST = this.astFactory.create(c);
                        ASTPair __currentAST482 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 129);
                        _t = _t.getFirstChild();
                        a = _t == ASTNULL ? null : _t;
                        this.identifier(_t);
                        _t = this._retTree;
                        a_AST = this.returnAST;
                        currentAST = __currentAST482;
                        _t = __t482;
                        _t = _t.getNextSibling();
                        parameter_AST = currentAST.root;
                        currentAST.root = parameter_AST = this.generateNamedParameter(c, a);
                        currentAST.child = parameter_AST != null && parameter_AST.getFirstChild() != null ? parameter_AST.getFirstChild() : parameter_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 130: {
                        AST __t483 = _t;
                        p = _t == ASTNULL ? null : _t;
                        Object p_AST_in = null;
                        p_AST = this.astFactory.create(p);
                        ASTPair __currentAST483 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 130);
                        _t = _t.getFirstChild();
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        switch (_t.getType()) {
                            case 131: {
                                n = _t;
                                Object n_AST_in = null;
                                n_AST = this.astFactory.create(n);
                                this.match(_t, 131);
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
                        currentAST = __currentAST483;
                        _t = __t483;
                        _t = _t.getNextSibling();
                        parameter_AST = currentAST.root;
                        currentAST.root = parameter_AST = this.generatePositionalParameter(p, n);
                        currentAST.child = parameter_AST != null && parameter_AST.getFirstChild() != null ? parameter_AST.getFirstChild() : parameter_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block12;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = parameter_AST;
        this._retTree = _t;
    }

    public final void aggregateExpr(AST _t) throws RecognitionException {
        AST aggregateExpr_AST;
        block8: {
            AST aggregateExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            aggregateExpr_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
                        this.expr(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        aggregateExpr_AST = currentAST.root;
                        break;
                    }
                    case 17: 
                    case 27: {
                        this.collectionFunction(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        aggregateExpr_AST = currentAST.root;
                        break;
                    }
                    case 90: {
                        this.selectStatement(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        aggregateExpr_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block8;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = aggregateExpr_AST;
        this._retTree = _t;
    }

    public final void fromElementList(AST _t) throws RecognitionException {
        AST fromElementList_AST;
        block5: {
            AST fromElementList_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            fromElementList_AST = null;
            boolean oldInFrom = this.inFrom;
            this.inFrom = true;
            try {
                int _cnt358 = 0;
                while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    if (_t.getType() != 32 && _t.getType() != 80 && _t.getType() != 91) {
                        if (_cnt358 >= 1) break;
                        throw new NoViableAltException(_t);
                    }
                    this.fromElement(_t);
                    _t = this._retTree;
                    this.astFactory.addASTChild(currentAST, this.returnAST);
                    ++_cnt358;
                }
                this.inFrom = oldInFrom;
                fromElementList_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block5;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = fromElementList_AST;
        this._retTree = _t;
    }

    public final void fromElement(AST _t) throws RecognitionException {
        AST fromElement_AST;
        block18: {
            AST fromElement_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            fromElement_AST = null;
            AST a = null;
            AST a_AST = null;
            AST pf = null;
            AST pf_AST = null;
            AST je_AST = null;
            AST je = null;
            AST fe = null;
            AST fe_AST = null;
            AST a3 = null;
            AST a3_AST = null;
            String p = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 91: {
                        AST __t360 = _t;
                        AST tmp49_AST = null;
                        AST tmp49_AST_in = null;
                        tmp49_AST = this.astFactory.create(_t);
                        tmp49_AST_in = _t;
                        ASTPair __currentAST360 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 91);
                        _t = _t.getFirstChild();
                        p = this.path(_t);
                        _t = this._retTree;
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        switch (_t.getType()) {
                            case 74: {
                                a = _t;
                                Object a_AST_in = null;
                                a_AST = this.astFactory.create(a);
                                this.match(_t, 74);
                                _t = _t.getNextSibling();
                                break;
                            }
                            case 3: 
                            case 21: {
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
                            case 21: {
                                pf = _t;
                                Object pf_AST_in = null;
                                pf_AST = this.astFactory.create(pf);
                                this.match(_t, 21);
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
                        currentAST = __currentAST360;
                        _t = __t360;
                        _t = _t.getNextSibling();
                        fromElement_AST = currentAST.root;
                        currentAST.root = fromElement_AST = this.createFromElement(p, a, pf);
                        currentAST.child = fromElement_AST != null && fromElement_AST.getFirstChild() != null ? fromElement_AST.getFirstChild() : fromElement_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 32: {
                        je = _t == ASTNULL ? null : _t;
                        this.joinElement(_t);
                        _t = this._retTree;
                        je_AST = this.returnAST;
                        fromElement_AST = currentAST.root;
                        currentAST.root = fromElement_AST = je_AST;
                        currentAST.child = fromElement_AST != null && fromElement_AST.getFirstChild() != null ? fromElement_AST.getFirstChild() : fromElement_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 80: {
                        fe = _t;
                        Object fe_AST_in = null;
                        fe_AST = this.astFactory.create(fe);
                        this.match(_t, 80);
                        a3 = _t = _t.getNextSibling();
                        Object a3_AST_in = null;
                        a3_AST = this.astFactory.create(a3);
                        this.match(_t, 74);
                        _t = _t.getNextSibling();
                        fromElement_AST = currentAST.root;
                        currentAST.root = fromElement_AST = this.createFromFilterElement(fe, a3);
                        currentAST.child = fromElement_AST != null && fromElement_AST.getFirstChild() != null ? fromElement_AST.getFirstChild() : fromElement_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block18;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = fromElement_AST;
        this._retTree = _t;
    }

    public final void joinElement(AST _t) throws RecognitionException {
        Object joinElement_AST;
        block27: {
            AST joinElement_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            joinElement_AST = null;
            AST f = null;
            AST f_AST = null;
            AST ref_AST = null;
            AST ref = null;
            AST a = null;
            AST a_AST = null;
            AST pf = null;
            AST pf_AST = null;
            AST with = null;
            AST with_AST = null;
            int j = 28;
            try {
                AST __t364 = _t;
                AST tmp50_AST = null;
                AST tmp50_AST_in = null;
                tmp50_AST = this.astFactory.create(_t);
                tmp50_AST_in = _t;
                ASTPair __currentAST364 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 32);
                _t = _t.getFirstChild();
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 23: 
                    case 28: 
                    case 33: 
                    case 44: {
                        j = this.joinType(_t);
                        _t = this._retTree;
                        this.setImpliedJoinType(j);
                        break;
                    }
                    case 15: 
                    case 21: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 98: 
                    case 110: {
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
                    case 21: {
                        f = _t;
                        Object f_AST_in = null;
                        f_AST = this.astFactory.create(f);
                        this.match(_t, 21);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 15: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 98: 
                    case 110: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                ref = _t == ASTNULL ? null : _t;
                this.propertyRef(_t);
                _t = this._retTree;
                ref_AST = this.returnAST;
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 74: {
                        a = _t;
                        Object a_AST_in = null;
                        a_AST = this.astFactory.create(a);
                        this.match(_t, 74);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 3: 
                    case 21: 
                    case 62: {
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
                    case 21: {
                        pf = _t;
                        Object pf_AST_in = null;
                        pf_AST = this.astFactory.create(pf);
                        this.match(_t, 21);
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 3: 
                    case 62: {
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
                    case 62: {
                        with = _t;
                        Object with_AST_in = null;
                        with_AST = this.astFactory.create(with);
                        this.match(_t, 62);
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
                currentAST = __currentAST364;
                _t = __t364;
                _t = _t.getNextSibling();
                this.createFromJoinElement(ref_AST, a, j, f, pf, with);
                this.setImpliedJoinType(28);
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block27;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = joinElement_AST;
        this._retTree = _t;
    }

    public final int joinType(AST _t) throws RecognitionException {
        int j;
        AST joinType_AST;
        block23: {
            AST joinType_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            joinType_AST = null;
            AST left = null;
            AST left_AST = null;
            AST right = null;
            AST right_AST = null;
            AST outer = null;
            AST outer_AST = null;
            j = 28;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 33: 
                    case 44: {
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        switch (_t.getType()) {
                            case 33: {
                                left = _t;
                                Object left_AST_in = null;
                                left_AST = this.astFactory.create(left);
                                this.astFactory.addASTChild(currentAST, left_AST);
                                this.match(_t, 33);
                                _t = _t.getNextSibling();
                                break;
                            }
                            case 44: {
                                right = _t;
                                Object right_AST_in = null;
                                right_AST = this.astFactory.create(right);
                                this.astFactory.addASTChild(currentAST, right_AST);
                                this.match(_t, 44);
                                _t = _t.getNextSibling();
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
                            case 42: {
                                outer = _t;
                                Object outer_AST_in = null;
                                outer_AST = this.astFactory.create(outer);
                                this.astFactory.addASTChild(currentAST, outer_AST);
                                this.match(_t, 42);
                                _t = _t.getNextSibling();
                                break;
                            }
                            case 15: 
                            case 21: 
                            case 70: 
                            case 71: 
                            case 72: 
                            case 98: 
                            case 110: {
                                break;
                            }
                            default: {
                                throw new NoViableAltException(_t);
                            }
                        }
                        if (left != null) {
                            j = 144;
                        } else if (right != null) {
                            j = 145;
                        } else if (outer != null) {
                            j = 145;
                        }
                        joinType_AST = currentAST.root;
                        break;
                    }
                    case 23: {
                        AST tmp51_AST = null;
                        AST tmp51_AST_in = null;
                        tmp51_AST = this.astFactory.create(_t);
                        tmp51_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp51_AST);
                        this.match(_t, 23);
                        _t = _t.getNextSibling();
                        j = 23;
                        joinType_AST = currentAST.root;
                        break;
                    }
                    case 28: {
                        AST tmp52_AST = null;
                        AST tmp52_AST_in = null;
                        tmp52_AST = this.astFactory.create(_t);
                        tmp52_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp52_AST);
                        this.match(_t, 28);
                        _t = _t.getNextSibling();
                        j = 28;
                        joinType_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block23;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = joinType_AST;
        this._retTree = _t;
        return j;
    }

    public final void pathAsIdent(AST _t) throws RecognitionException {
        AST pathAsIdent_AST;
        block2: {
            AST pathAsIdent_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            pathAsIdent_AST = null;
            String text = "?text?";
            try {
                text = this.path(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                pathAsIdent_AST = currentAST.root;
                currentAST.root = pathAsIdent_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(110, text)));
                currentAST.child = pathAsIdent_AST != null && pathAsIdent_AST.getFirstChild() != null ? pathAsIdent_AST.getFirstChild() : pathAsIdent_AST;
                currentAST.advanceChildToEnd();
                pathAsIdent_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = pathAsIdent_AST;
        this._retTree = _t;
    }

    public final void withClause(AST _t) throws RecognitionException {
        AST withClause_AST;
        block2: {
            AST withClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            withClause_AST = null;
            AST w = null;
            AST w_AST = null;
            AST b_AST = null;
            AST b = null;
            try {
                AST __t378 = _t;
                w = _t == ASTNULL ? null : _t;
                Object w_AST_in = null;
                w_AST = this.astFactory.create(w);
                this.astFactory.addASTChild(currentAST, w_AST);
                ASTPair __currentAST378 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 62);
                _t = _t.getFirstChild();
                this.handleClauseStart(62);
                b = _t == ASTNULL ? null : _t;
                this.logicalExpr(_t);
                _t = this._retTree;
                b_AST = this.returnAST;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST378;
                _t = __t378;
                _t = _t.getNextSibling();
                withClause_AST = currentAST.root;
                withClause_AST = this.astFactory.make(new ASTArray(2).add(w_AST).add(b_AST));
                this.handleClauseEnd();
                currentAST.root = withClause_AST;
                currentAST.child = withClause_AST != null && withClause_AST.getFirstChild() != null ? withClause_AST.getFirstChild() : withClause_AST;
                currentAST.advanceChildToEnd();
                withClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = withClause_AST;
        this._retTree = _t;
    }

    public final void comparisonExpr(AST _t) throws RecognitionException {
        AST comparisonExpr_AST;
        block35: {
            AST comparisonExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            comparisonExpr_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 107: {
                        AST __t387 = _t;
                        AST tmp53_AST = null;
                        AST tmp53_AST_in = null;
                        tmp53_AST = this.astFactory.create(_t);
                        tmp53_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp53_AST);
                        ASTPair __currentAST387 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 107);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST387;
                        _t = __t387;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 114: {
                        AST __t388 = _t;
                        AST tmp54_AST = null;
                        AST tmp54_AST_in = null;
                        tmp54_AST = this.astFactory.create(_t);
                        tmp54_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp54_AST);
                        ASTPair __currentAST388 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 114);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST388;
                        _t = __t388;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 116: {
                        AST __t389 = _t;
                        AST tmp55_AST = null;
                        AST tmp55_AST_in = null;
                        tmp55_AST = this.astFactory.create(_t);
                        tmp55_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp55_AST);
                        ASTPair __currentAST389 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 116);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST389;
                        _t = __t389;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 117: {
                        AST __t390 = _t;
                        AST tmp56_AST = null;
                        AST tmp56_AST_in = null;
                        tmp56_AST = this.astFactory.create(_t);
                        tmp56_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp56_AST);
                        ASTPair __currentAST390 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 117);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST390;
                        _t = __t390;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 118: {
                        AST __t391 = _t;
                        AST tmp57_AST = null;
                        AST tmp57_AST_in = null;
                        tmp57_AST = this.astFactory.create(_t);
                        tmp57_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp57_AST);
                        ASTPair __currentAST391 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 118);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST391;
                        _t = __t391;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 119: {
                        AST __t392 = _t;
                        AST tmp58_AST = null;
                        AST tmp58_AST_in = null;
                        tmp58_AST = this.astFactory.create(_t);
                        tmp58_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp58_AST);
                        ASTPair __currentAST392 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 119);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST392;
                        _t = __t392;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 34: {
                        AST __t393 = _t;
                        AST tmp59_AST = null;
                        AST tmp59_AST_in = null;
                        tmp59_AST = this.astFactory.create(_t);
                        tmp59_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp59_AST);
                        ASTPair __currentAST393 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 34);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.expr(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        switch (_t.getType()) {
                            case 18: {
                                AST __t395 = _t;
                                AST tmp60_AST = null;
                                AST tmp60_AST_in = null;
                                tmp60_AST = this.astFactory.create(_t);
                                tmp60_AST_in = _t;
                                this.astFactory.addASTChild(currentAST, tmp60_AST);
                                ASTPair __currentAST395 = currentAST.copy();
                                currentAST.root = currentAST.child;
                                currentAST.child = null;
                                this.match(_t, 18);
                                _t = _t.getFirstChild();
                                this.expr(_t, null);
                                _t = this._retTree;
                                this.astFactory.addASTChild(currentAST, this.returnAST);
                                currentAST = __currentAST395;
                                _t = __t395;
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
                        currentAST = __currentAST393;
                        _t = __t393;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 88: {
                        AST __t396 = _t;
                        AST tmp61_AST = null;
                        AST tmp61_AST_in = null;
                        tmp61_AST = this.astFactory.create(_t);
                        tmp61_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp61_AST);
                        ASTPair __currentAST396 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 88);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.expr(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        switch (_t.getType()) {
                            case 18: {
                                AST __t398 = _t;
                                AST tmp62_AST = null;
                                AST tmp62_AST_in = null;
                                tmp62_AST = this.astFactory.create(_t);
                                tmp62_AST_in = _t;
                                this.astFactory.addASTChild(currentAST, tmp62_AST);
                                ASTPair __currentAST398 = currentAST.copy();
                                currentAST.root = currentAST.child;
                                currentAST.child = null;
                                this.match(_t, 18);
                                _t = _t.getFirstChild();
                                this.expr(_t, null);
                                _t = this._retTree;
                                this.astFactory.addASTChild(currentAST, this.returnAST);
                                currentAST = __currentAST398;
                                _t = __t398;
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
                        currentAST = __currentAST396;
                        _t = __t396;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 10: {
                        AST __t399 = _t;
                        AST tmp63_AST = null;
                        AST tmp63_AST_in = null;
                        tmp63_AST = this.astFactory.create(_t);
                        tmp63_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp63_AST);
                        ASTPair __currentAST399 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 10);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST399;
                        _t = __t399;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 86: {
                        AST __t400 = _t;
                        AST tmp64_AST = null;
                        AST tmp64_AST_in = null;
                        tmp64_AST = this.astFactory.create(_t);
                        tmp64_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp64_AST);
                        ASTPair __currentAST400 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 86);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.exprOrSubquery(_t, null);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST400;
                        _t = __t400;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 26: {
                        AST __t401 = _t;
                        AST tmp65_AST = null;
                        AST tmp65_AST_in = null;
                        tmp65_AST = this.astFactory.create(_t);
                        tmp65_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp65_AST);
                        ASTPair __currentAST401 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 26);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.inRhs(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST401;
                        _t = __t401;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 87: {
                        AST __t402 = _t;
                        AST tmp66_AST = null;
                        AST tmp66_AST_in = null;
                        tmp66_AST = this.astFactory.create(_t);
                        tmp66_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp66_AST);
                        ASTPair __currentAST402 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 87);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        this.inRhs(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST402;
                        _t = __t402;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 84: {
                        AST __t403 = _t;
                        AST tmp67_AST = null;
                        AST tmp67_AST_in = null;
                        tmp67_AST = this.astFactory.create(_t);
                        tmp67_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp67_AST);
                        ASTPair __currentAST403 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 84);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST403;
                        _t = __t403;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 83: {
                        AST __t404 = _t;
                        AST tmp68_AST = null;
                        AST tmp68_AST_in = null;
                        tmp68_AST = this.astFactory.create(_t);
                        tmp68_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp68_AST);
                        ASTPair __currentAST404 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 83);
                        _t = _t.getFirstChild();
                        this.exprOrSubquery(_t, currentAST.root);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST404;
                        _t = __t404;
                        _t = _t.getNextSibling();
                        break;
                    }
                    case 19: {
                        AST __t405 = _t;
                        AST tmp69_AST = null;
                        AST tmp69_AST_in = null;
                        tmp69_AST = this.astFactory.create(_t);
                        tmp69_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp69_AST);
                        ASTPair __currentAST405 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 19);
                        _t = _t.getFirstChild();
                        if (_t == null) {
                            _t = ASTNULL;
                        }
                        switch (_t.getType()) {
                            case 12: 
                            case 15: 
                            case 20: 
                            case 39: 
                            case 49: 
                            case 56: 
                            case 70: 
                            case 71: 
                            case 72: 
                            case 73: 
                            case 76: 
                            case 77: 
                            case 82: 
                            case 85: 
                            case 94: 
                            case 95: 
                            case 97: 
                            case 98: 
                            case 100: 
                            case 101: 
                            case 102: 
                            case 103: 
                            case 104: 
                            case 105: 
                            case 110: 
                            case 121: 
                            case 122: 
                            case 123: 
                            case 124: 
                            case 125: 
                            case 128: 
                            case 129: 
                            case 130: 
                            case 131: {
                                this.expr(_t, null);
                                _t = this._retTree;
                                this.astFactory.addASTChild(currentAST, this.returnAST);
                                break;
                            }
                            case 17: 
                            case 27: 
                            case 90: {
                                this.collectionFunctionOrSubselect(_t);
                                _t = this._retTree;
                                this.astFactory.addASTChild(currentAST, this.returnAST);
                                break;
                            }
                            default: {
                                throw new NoViableAltException(_t);
                            }
                        }
                        currentAST = __currentAST405;
                        _t = __t405;
                        _t = _t.getNextSibling();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                comparisonExpr_AST = currentAST.root;
                this.prepareLogicOperator(comparisonExpr_AST);
                comparisonExpr_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block35;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = comparisonExpr_AST;
        this._retTree = _t;
    }

    public final void exprOrSubquery(AST _t, AST predicateNode) throws RecognitionException {
        AST exprOrSubquery_AST;
        block10: {
            AST exprOrSubquery_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            exprOrSubquery_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
                        this.expr(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        exprOrSubquery_AST = currentAST.root;
                        break;
                    }
                    case 90: {
                        this.query(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        exprOrSubquery_AST = currentAST.root;
                        break;
                    }
                    case 5: {
                        AST __t414 = _t;
                        AST tmp70_AST = null;
                        AST tmp70_AST_in = null;
                        tmp70_AST = this.astFactory.create(_t);
                        tmp70_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp70_AST);
                        ASTPair __currentAST414 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 5);
                        _t = _t.getFirstChild();
                        this.collectionFunctionOrSubselect(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST414;
                        _t = __t414;
                        _t = _t.getNextSibling();
                        exprOrSubquery_AST = currentAST.root;
                        break;
                    }
                    case 4: {
                        AST __t415 = _t;
                        AST tmp71_AST = null;
                        AST tmp71_AST_in = null;
                        tmp71_AST = this.astFactory.create(_t);
                        tmp71_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp71_AST);
                        ASTPair __currentAST415 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 4);
                        _t = _t.getFirstChild();
                        this.collectionFunctionOrSubselect(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST415;
                        _t = __t415;
                        _t = _t.getNextSibling();
                        exprOrSubquery_AST = currentAST.root;
                        break;
                    }
                    case 47: {
                        AST __t416 = _t;
                        AST tmp72_AST = null;
                        AST tmp72_AST_in = null;
                        tmp72_AST = this.astFactory.create(_t);
                        tmp72_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp72_AST);
                        ASTPair __currentAST416 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 47);
                        _t = _t.getFirstChild();
                        this.collectionFunctionOrSubselect(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST416;
                        _t = __t416;
                        _t = _t.getNextSibling();
                        exprOrSubquery_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block10;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = exprOrSubquery_AST;
        this._retTree = _t;
    }

    public final void inRhs(AST _t, AST predicateNode) throws RecognitionException {
        AST inRhs_AST;
        block9: {
            AST inRhs_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            inRhs_AST = null;
            try {
                AST __t408 = _t;
                AST tmp73_AST = null;
                AST tmp73_AST_in = null;
                tmp73_AST = this.astFactory.create(_t);
                tmp73_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp73_AST);
                ASTPair __currentAST408 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 81);
                _t = _t.getFirstChild();
                if (_t == null) {
                    _t = ASTNULL;
                }
                block1 : switch (_t.getType()) {
                    case 17: 
                    case 27: 
                    case 90: {
                        this.collectionFunctionOrSubselect(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                    }
                    case 3: 
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
                        while (true) {
                            if (_t == null) {
                                _t = ASTNULL;
                            }
                            if (!_tokenSet_0.member(_t.getType())) break block1;
                            this.expr(_t, predicateNode);
                            _t = this._retTree;
                            this.astFactory.addASTChild(currentAST, this.returnAST);
                        }
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST408;
                _t = __t408;
                _t = _t.getNextSibling();
                inRhs_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block9;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = inRhs_AST;
        this._retTree = _t;
    }

    public final void collectionFunctionOrSubselect(AST _t) throws RecognitionException {
        AST collectionFunctionOrSubselect_AST;
        block7: {
            AST collectionFunctionOrSubselect_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            collectionFunctionOrSubselect_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 17: 
                    case 27: {
                        this.collectionFunction(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        collectionFunctionOrSubselect_AST = currentAST.root;
                        break;
                    }
                    case 90: {
                        this.query(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        collectionFunctionOrSubselect_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = collectionFunctionOrSubselect_AST;
        this._retTree = _t;
    }

    public final void addrExpr(AST _t, boolean root) throws RecognitionException {
        AST addrExpr_AST;
        block11: {
            AST addrExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            addrExpr_AST = null;
            AST d = null;
            AST d_AST = null;
            AST lhs_AST = null;
            AST lhs = null;
            AST rhs_AST = null;
            AST rhs = null;
            AST i = null;
            AST i_AST = null;
            AST lhs2_AST = null;
            AST lhs2 = null;
            AST rhs2_AST = null;
            AST rhs2 = null;
            AST mcr_AST = null;
            AST mcr = null;
            AST p_AST = null;
            AST p = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 15: {
                        AST __t468 = _t;
                        d = _t == ASTNULL ? null : _t;
                        Object d_AST_in = null;
                        d_AST = this.astFactory.create(d);
                        ASTPair __currentAST468 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 15);
                        _t = _t.getFirstChild();
                        lhs = _t == ASTNULL ? null : _t;
                        this.addrExprLhs(_t);
                        _t = this._retTree;
                        lhs_AST = this.returnAST;
                        rhs = _t == ASTNULL ? null : _t;
                        this.propertyName(_t);
                        _t = this._retTree;
                        rhs_AST = this.returnAST;
                        currentAST = __currentAST468;
                        _t = __t468;
                        _t = _t.getNextSibling();
                        addrExpr_AST = currentAST.root;
                        addrExpr_AST = this.astFactory.make(new ASTArray(3).add(d_AST).add(lhs_AST).add(rhs_AST));
                        currentAST.root = addrExpr_AST = this.lookupProperty(addrExpr_AST, root, false);
                        currentAST.child = addrExpr_AST != null && addrExpr_AST.getFirstChild() != null ? addrExpr_AST.getFirstChild() : addrExpr_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 82: {
                        AST __t469 = _t;
                        i = _t == ASTNULL ? null : _t;
                        Object i_AST_in = null;
                        i_AST = this.astFactory.create(i);
                        ASTPair __currentAST469 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 82);
                        _t = _t.getFirstChild();
                        lhs2 = _t == ASTNULL ? null : _t;
                        this.addrExprLhs(_t);
                        _t = this._retTree;
                        lhs2_AST = this.returnAST;
                        rhs2 = _t == ASTNULL ? null : _t;
                        this.expr(_t, null);
                        _t = this._retTree;
                        rhs2_AST = this.returnAST;
                        currentAST = __currentAST469;
                        _t = __t469;
                        _t = _t.getNextSibling();
                        addrExpr_AST = currentAST.root;
                        addrExpr_AST = this.astFactory.make(new ASTArray(3).add(i_AST).add(lhs2_AST).add(rhs2_AST));
                        this.processIndex(addrExpr_AST);
                        currentAST.root = addrExpr_AST;
                        currentAST.child = addrExpr_AST != null && addrExpr_AST.getFirstChild() != null ? addrExpr_AST.getFirstChild() : addrExpr_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 70: 
                    case 71: 
                    case 72: {
                        mcr = _t == ASTNULL ? null : _t;
                        this.mapComponentReference(_t);
                        _t = this._retTree;
                        mcr_AST = this.returnAST;
                        addrExpr_AST = currentAST.root;
                        currentAST.root = addrExpr_AST = mcr_AST;
                        currentAST.child = addrExpr_AST != null && addrExpr_AST.getFirstChild() != null ? addrExpr_AST.getFirstChild() : addrExpr_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    case 98: 
                    case 110: {
                        p = _t == ASTNULL ? null : _t;
                        this.identifier(_t);
                        _t = this._retTree;
                        p_AST = this.returnAST;
                        addrExpr_AST = currentAST.root;
                        if (this.isNonQualifiedPropertyRef(p_AST)) {
                            addrExpr_AST = this.lookupNonQualifiedProperty(p_AST);
                        } else {
                            this.resolve(p_AST);
                            addrExpr_AST = p_AST;
                        }
                        currentAST.root = addrExpr_AST;
                        currentAST.child = addrExpr_AST != null && addrExpr_AST.getFirstChild() != null ? addrExpr_AST.getFirstChild() : addrExpr_AST;
                        currentAST.advanceChildToEnd();
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block11;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = addrExpr_AST;
        this._retTree = _t;
    }

    public final void caseExpr(AST _t, AST predicateNode) throws RecognitionException {
        AST caseExpr_AST;
        block7: {
            AST caseExpr_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            caseExpr_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 76: {
                        this.simpleCaseExpression(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        caseExpr_AST = currentAST.root;
                        break;
                    }
                    case 56: {
                        this.searchedCaseExpression(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        caseExpr_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = caseExpr_AST;
        this._retTree = _t;
    }

    public final void simpleCaseExpression(AST _t, AST predicateNode) throws RecognitionException {
        AST simpleCaseExpression_AST;
        block10: {
            AST simpleCaseExpression_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            simpleCaseExpression_AST = null;
            try {
                AST __t432 = _t;
                AST tmp74_AST = null;
                AST tmp74_AST_in = null;
                tmp74_AST = this.astFactory.create(_t);
                tmp74_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp74_AST);
                ASTPair __currentAST432 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 76);
                _t = _t.getFirstChild();
                this.inCase = true;
                this.expressionOrSubQuery(_t, currentAST.root);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                int _cnt434 = 0;
                while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    if (_t.getType() != 60) {
                        if (_cnt434 >= 1) break;
                        throw new NoViableAltException(_t);
                    }
                    this.simpleCaseWhenClause(_t, currentAST.root, predicateNode);
                    _t = this._retTree;
                    this.astFactory.addASTChild(currentAST, this.returnAST);
                    ++_cnt434;
                }
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 58: {
                        this.elseClause(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST432;
                _t = __t432;
                _t = _t.getNextSibling();
                this.inCase = false;
                simpleCaseExpression_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block10;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = simpleCaseExpression_AST;
        this._retTree = _t;
    }

    public final void searchedCaseExpression(AST _t, AST predicateNode) throws RecognitionException {
        AST searchedCaseExpression_AST;
        block10: {
            AST searchedCaseExpression_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            searchedCaseExpression_AST = null;
            try {
                AST __t441 = _t;
                AST tmp75_AST = null;
                AST tmp75_AST_in = null;
                tmp75_AST = this.astFactory.create(_t);
                tmp75_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp75_AST);
                ASTPair __currentAST441 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 56);
                _t = _t.getFirstChild();
                this.inCase = true;
                int _cnt443 = 0;
                while (true) {
                    if (_t == null) {
                        _t = ASTNULL;
                    }
                    if (_t.getType() != 60) {
                        if (_cnt443 >= 1) break;
                        throw new NoViableAltException(_t);
                    }
                    this.searchedCaseWhenClause(_t, predicateNode);
                    _t = this._retTree;
                    this.astFactory.addASTChild(currentAST, this.returnAST);
                    ++_cnt443;
                }
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 58: {
                        this.elseClause(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST441;
                _t = __t441;
                _t = _t.getNextSibling();
                this.inCase = false;
                searchedCaseExpression_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block10;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = searchedCaseExpression_AST;
        this._retTree = _t;
    }

    public final void expressionOrSubQuery(AST _t, AST predicateNode) throws RecognitionException {
        AST expressionOrSubQuery_AST;
        block7: {
            AST expressionOrSubQuery_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            expressionOrSubQuery_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 12: 
                    case 15: 
                    case 20: 
                    case 39: 
                    case 49: 
                    case 56: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 73: 
                    case 76: 
                    case 77: 
                    case 82: 
                    case 85: 
                    case 94: 
                    case 95: 
                    case 97: 
                    case 98: 
                    case 100: 
                    case 101: 
                    case 102: 
                    case 103: 
                    case 104: 
                    case 105: 
                    case 110: 
                    case 121: 
                    case 122: 
                    case 123: 
                    case 124: 
                    case 125: 
                    case 128: 
                    case 129: 
                    case 130: 
                    case 131: {
                        this.expr(_t, predicateNode);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        expressionOrSubQuery_AST = currentAST.root;
                        break;
                    }
                    case 90: {
                        this.query(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        expressionOrSubQuery_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = expressionOrSubQuery_AST;
        this._retTree = _t;
    }

    public final void simpleCaseWhenClause(AST _t, AST predicateNode, AST superPredicateNode) throws RecognitionException {
        AST simpleCaseWhenClause_AST;
        block2: {
            AST simpleCaseWhenClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            simpleCaseWhenClause_AST = null;
            try {
                AST __t437 = _t;
                AST tmp76_AST = null;
                AST tmp76_AST_in = null;
                tmp76_AST = this.astFactory.create(_t);
                tmp76_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp76_AST);
                ASTPair __currentAST437 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 60);
                _t = _t.getFirstChild();
                this.expressionOrSubQuery(_t, predicateNode);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                this.expressionOrSubQuery(_t, superPredicateNode);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST437;
                _t = __t437;
                _t = _t.getNextSibling();
                simpleCaseWhenClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = simpleCaseWhenClause_AST;
        this._retTree = _t;
    }

    public final void elseClause(AST _t, AST predicateNode) throws RecognitionException {
        AST elseClause_AST;
        block2: {
            AST elseClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            elseClause_AST = null;
            try {
                AST __t439 = _t;
                AST tmp77_AST = null;
                AST tmp77_AST_in = null;
                tmp77_AST = this.astFactory.create(_t);
                tmp77_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp77_AST);
                ASTPair __currentAST439 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 58);
                _t = _t.getFirstChild();
                this.expressionOrSubQuery(_t, predicateNode);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST439;
                _t = __t439;
                _t = _t.getNextSibling();
                elseClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = elseClause_AST;
        this._retTree = _t;
    }

    public final void searchedCaseWhenClause(AST _t, AST predicateNode) throws RecognitionException {
        AST searchedCaseWhenClause_AST;
        block2: {
            AST searchedCaseWhenClause_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            searchedCaseWhenClause_AST = null;
            try {
                AST __t446 = _t;
                AST tmp78_AST = null;
                AST tmp78_AST_in = null;
                tmp78_AST = this.astFactory.create(_t);
                tmp78_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp78_AST);
                ASTPair __currentAST446 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 60);
                _t = _t.getFirstChild();
                this.logicalExpr(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                this.expressionOrSubQuery(_t, predicateNode);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                currentAST = __currentAST446;
                _t = __t446;
                _t = _t.getNextSibling();
                searchedCaseWhenClause_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = searchedCaseWhenClause_AST;
        this._retTree = _t;
    }

    public final void collectionPath(AST _t) throws RecognitionException {
        AST collectionPath_AST;
        block7: {
            AST collectionPath_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            collectionPath_AST = null;
            AST ref_AST = null;
            AST ref = null;
            AST qualifier_AST = null;
            AST qualifier = null;
            try {
                AST __t460 = _t;
                AST tmp79_AST = null;
                AST tmp79_AST_in = null;
                tmp79_AST = this.astFactory.create(_t);
                tmp79_AST_in = _t;
                ASTPair __currentAST460 = currentAST.copy();
                currentAST.root = currentAST.child;
                currentAST.child = null;
                this.match(_t, 78);
                _t = _t.getFirstChild();
                ref = _t == ASTNULL ? null : _t;
                this.identifier(_t);
                _t = this._retTree;
                ref_AST = this.returnAST;
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 15: 
                    case 70: 
                    case 71: 
                    case 72: 
                    case 82: 
                    case 98: 
                    case 110: {
                        qualifier = _t == ASTNULL ? null : _t;
                        this.collectionPathQualifier(_t);
                        _t = this._retTree;
                        qualifier_AST = this.returnAST;
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
                currentAST = __currentAST460;
                _t = __t460;
                _t = _t.getNextSibling();
                collectionPath_AST = currentAST.root;
                this.resolve(qualifier_AST);
                currentAST.root = collectionPath_AST = this.createCollectionPath(qualifier_AST, ref_AST);
                currentAST.child = collectionPath_AST != null && collectionPath_AST.getFirstChild() != null ? collectionPath_AST.getFirstChild() : collectionPath_AST;
                currentAST.advanceChildToEnd();
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block7;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = collectionPath_AST;
        this._retTree = _t;
    }

    public final void collectionPathQualifier(AST _t) throws RecognitionException {
        AST collectionPathQualifier_AST;
        block2: {
            AST collectionPathQualifier_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            collectionPathQualifier_AST = null;
            try {
                this.addrExpr(_t, true);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                collectionPathQualifier_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = collectionPathQualifier_AST;
        this._retTree = _t;
    }

    public final void literal(AST _t) throws RecognitionException {
        AST literal_AST;
        block12: {
            AST literal_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            literal_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 131: {
                        AST tmp80_AST = null;
                        AST tmp80_AST_in = null;
                        tmp80_AST = this.astFactory.create(_t);
                        tmp80_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp80_AST);
                        this.match(_t, 131);
                        _t = _t.getNextSibling();
                        literal_AST = currentAST.root;
                        this.processNumericLiteral(literal_AST);
                        literal_AST = currentAST.root;
                        break;
                    }
                    case 102: {
                        AST tmp81_AST = null;
                        AST tmp81_AST_in = null;
                        tmp81_AST = this.astFactory.create(_t);
                        tmp81_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp81_AST);
                        this.match(_t, 102);
                        _t = _t.getNextSibling();
                        literal_AST = currentAST.root;
                        this.processNumericLiteral(literal_AST);
                        literal_AST = currentAST.root;
                        break;
                    }
                    case 101: {
                        AST tmp82_AST = null;
                        AST tmp82_AST_in = null;
                        tmp82_AST = this.astFactory.create(_t);
                        tmp82_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp82_AST);
                        this.match(_t, 101);
                        _t = _t.getNextSibling();
                        literal_AST = currentAST.root;
                        this.processNumericLiteral(literal_AST);
                        literal_AST = currentAST.root;
                        break;
                    }
                    case 100: {
                        AST tmp83_AST = null;
                        AST tmp83_AST_in = null;
                        tmp83_AST = this.astFactory.create(_t);
                        tmp83_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp83_AST);
                        this.match(_t, 100);
                        _t = _t.getNextSibling();
                        literal_AST = currentAST.root;
                        this.processNumericLiteral(literal_AST);
                        literal_AST = currentAST.root;
                        break;
                    }
                    case 103: {
                        AST tmp84_AST = null;
                        AST tmp84_AST_in = null;
                        tmp84_AST = this.astFactory.create(_t);
                        tmp84_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp84_AST);
                        this.match(_t, 103);
                        _t = _t.getNextSibling();
                        literal_AST = currentAST.root;
                        this.processNumericLiteral(literal_AST);
                        literal_AST = currentAST.root;
                        break;
                    }
                    case 104: {
                        AST tmp85_AST = null;
                        AST tmp85_AST_in = null;
                        tmp85_AST = this.astFactory.create(_t);
                        tmp85_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp85_AST);
                        this.match(_t, 104);
                        _t = _t.getNextSibling();
                        literal_AST = currentAST.root;
                        this.processNumericLiteral(literal_AST);
                        literal_AST = currentAST.root;
                        break;
                    }
                    case 128: {
                        AST tmp86_AST = null;
                        AST tmp86_AST_in = null;
                        tmp86_AST = this.astFactory.create(_t);
                        tmp86_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp86_AST);
                        this.match(_t, 128);
                        _t = _t.getNextSibling();
                        literal_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block12;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = literal_AST;
        this._retTree = _t;
    }

    public final void addrExprLhs(AST _t) throws RecognitionException {
        AST addrExprLhs_AST;
        block2: {
            AST addrExprLhs_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            addrExprLhs_AST = null;
            try {
                this.addrExpr(_t, false);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                addrExprLhs_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = addrExprLhs_AST;
        this._retTree = _t;
    }

    public final void propertyName(AST _t) throws RecognitionException {
        AST propertyName_AST;
        block9: {
            AST propertyName_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            propertyName_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 98: 
                    case 110: {
                        this.identifier(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        propertyName_AST = currentAST.root;
                        break;
                    }
                    case 11: {
                        AST tmp87_AST = null;
                        AST tmp87_AST_in = null;
                        tmp87_AST = this.astFactory.create(_t);
                        tmp87_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp87_AST);
                        this.match(_t, 11);
                        _t = _t.getNextSibling();
                        propertyName_AST = currentAST.root;
                        break;
                    }
                    case 17: {
                        AST tmp88_AST = null;
                        AST tmp88_AST_in = null;
                        tmp88_AST = this.astFactory.create(_t);
                        tmp88_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp88_AST);
                        this.match(_t, 17);
                        _t = _t.getNextSibling();
                        propertyName_AST = currentAST.root;
                        break;
                    }
                    case 27: {
                        AST tmp89_AST = null;
                        AST tmp89_AST_in = null;
                        tmp89_AST = this.astFactory.create(_t);
                        tmp89_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp89_AST);
                        this.match(_t, 27);
                        _t = _t.getNextSibling();
                        propertyName_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block9;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = propertyName_AST;
        this._retTree = _t;
    }

    public final void mapComponentReference(AST _t) throws RecognitionException {
        AST mapComponentReference_AST;
        block8: {
            AST mapComponentReference_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            mapComponentReference_AST = null;
            try {
                if (_t == null) {
                    _t = ASTNULL;
                }
                switch (_t.getType()) {
                    case 70: {
                        AST __t477 = _t;
                        AST tmp90_AST = null;
                        AST tmp90_AST_in = null;
                        tmp90_AST = this.astFactory.create(_t);
                        tmp90_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp90_AST);
                        ASTPair __currentAST477 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 70);
                        _t = _t.getFirstChild();
                        this.mapPropertyExpression(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST477;
                        _t = __t477;
                        _t = _t.getNextSibling();
                        mapComponentReference_AST = currentAST.root;
                        break;
                    }
                    case 71: {
                        AST __t478 = _t;
                        AST tmp91_AST = null;
                        AST tmp91_AST_in = null;
                        tmp91_AST = this.astFactory.create(_t);
                        tmp91_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp91_AST);
                        ASTPair __currentAST478 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 71);
                        _t = _t.getFirstChild();
                        this.mapPropertyExpression(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST478;
                        _t = __t478;
                        _t = _t.getNextSibling();
                        mapComponentReference_AST = currentAST.root;
                        break;
                    }
                    case 72: {
                        AST __t479 = _t;
                        AST tmp92_AST = null;
                        AST tmp92_AST_in = null;
                        tmp92_AST = this.astFactory.create(_t);
                        tmp92_AST_in = _t;
                        this.astFactory.addASTChild(currentAST, tmp92_AST);
                        ASTPair __currentAST479 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        this.match(_t, 72);
                        _t = _t.getFirstChild();
                        this.mapPropertyExpression(_t);
                        _t = this._retTree;
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        currentAST = __currentAST479;
                        _t = __t479;
                        _t = _t.getNextSibling();
                        mapComponentReference_AST = currentAST.root;
                        break;
                    }
                    default: {
                        throw new NoViableAltException(_t);
                    }
                }
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block8;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = mapComponentReference_AST;
        this._retTree = _t;
    }

    public final void propertyRefLhs(AST _t) throws RecognitionException {
        AST propertyRefLhs_AST;
        block2: {
            AST propertyRefLhs_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            propertyRefLhs_AST = null;
            try {
                this.propertyRef(_t);
                _t = this._retTree;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                propertyRefLhs_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = propertyRefLhs_AST;
        this._retTree = _t;
    }

    public final void mapPropertyExpression(AST _t) throws RecognitionException {
        AST mapPropertyExpression_AST;
        block2: {
            AST mapPropertyExpression_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            mapPropertyExpression_AST = null;
            AST e_AST = null;
            AST e = null;
            try {
                e = _t == ASTNULL ? null : _t;
                this.expr(_t, null);
                _t = this._retTree;
                e_AST = this.returnAST;
                this.astFactory.addASTChild(currentAST, this.returnAST);
                this.validateMapPropertyExpression(e_AST);
                mapPropertyExpression_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = mapPropertyExpression_AST;
        this._retTree = _t;
    }

    public final void numericInteger(AST _t) throws RecognitionException {
        AST numericInteger_AST;
        block2: {
            AST numericInteger_AST_in = _t == ASTNULL ? null : _t;
            this.returnAST = null;
            ASTPair currentAST = new ASTPair();
            numericInteger_AST = null;
            try {
                AST tmp93_AST = null;
                AST tmp93_AST_in = null;
                tmp93_AST = this.astFactory.create(_t);
                tmp93_AST_in = _t;
                this.astFactory.addASTChild(currentAST, tmp93_AST);
                this.match(_t, 131);
                _t = _t.getNextSibling();
                numericInteger_AST = currentAST.root;
            }
            catch (RecognitionException ex) {
                this.reportError(ex);
                if (_t == null) break block2;
                _t = _t.getNextSibling();
            }
        }
        this.returnAST = numericInteger_AST;
        this._retTree = _t;
    }

    private static final long[] mk_tokenSet_0() {
        long[] data = new long[]{72621093748248576L, 4467645557416145856L, 15L, 0L, 0L, 0L};
        return data;
    }

    private static final long[] mk_tokenSet_1() {
        long[] data = new long[]{72761831236603952L, 4467645557483254720L, 15L, 0L, 0L, 0L};
        return data;
    }
}

