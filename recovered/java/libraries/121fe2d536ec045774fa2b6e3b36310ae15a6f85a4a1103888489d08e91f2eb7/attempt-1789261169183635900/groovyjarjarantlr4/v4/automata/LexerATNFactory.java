/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 */
package groovyjarjarantlr4.v4.automata;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.automata.ATNFactory;
import groovyjarjarantlr4.v4.automata.ATNOptimizer;
import groovyjarjarantlr4.v4.automata.ParserATNFactory;
import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.misc.EscapeSequenceParsing;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.CodePointTransitions;
import groovyjarjarantlr4.v4.runtime.atn.LexerAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerChannelAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerCustomAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerModeAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerMoreAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerPopModeAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerPushModeAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerSkipAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerTypeAction;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.TokensStartState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.RangeAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class LexerATNFactory
extends ParserATNFactory {
    @Nullable
    public STGroup codegenTemplates;
    public static final Map<String, Integer> COMMON_CONSTANTS = new HashMap<String, Integer>();
    private final List<String> ruleCommands = new ArrayList<String>();
    protected Map<Integer, LexerAction> indexToActionMap = new HashMap<Integer, LexerAction>();
    protected Map<LexerAction, Integer> actionToIndexMap = new HashMap<LexerAction, Integer>();

    public LexerATNFactory(LexerGrammar g) {
        super(g);
        String language = g.getOptionString("language");
        CodeGenerator gen = new CodeGenerator(g.tool, null, language);
        Target target = gen.getTarget();
        this.codegenTemplates = target != null ? target.getTemplates() : null;
    }

    public static Set<String> getCommonConstants() {
        return COMMON_CONSTANTS.keySet();
    }

    @Override
    public ATN createATN() {
        Set modes = ((LexerGrammar)this.g).modes.keySet();
        for (String string : modes) {
            TokensStartState startState = this.newState(TokensStartState.class, null);
            this.atn.defineMode(string, startState);
        }
        this.atn.ruleToTokenType = new int[this.g.rules.size()];
        for (Rule rule : this.g.rules.values()) {
            this.atn.ruleToTokenType[rule.index] = this.g.getTokenType(rule.name);
        }
        this._createATN(this.g.rules.values());
        this.atn.lexerActions = new LexerAction[this.indexToActionMap.size()];
        for (Map.Entry entry : this.indexToActionMap.entrySet()) {
            this.atn.lexerActions[((Integer)entry.getKey()).intValue()] = (LexerAction)entry.getValue();
        }
        for (String string : modes) {
            List rules = (List)((LexerGrammar)this.g).modes.get(string);
            TokensStartState startState = this.atn.modeNameToStartState.get(string);
            for (Rule r : rules) {
                if (r.isFragment()) continue;
                RuleStartState s = this.atn.ruleToStartState[r.index];
                this.epsilon(startState, s);
            }
        }
        ATNOptimizer.optimize(this.g, this.atn);
        return this.atn;
    }

    @Override
    public ATNFactory.Handle rule(GrammarAST ruleAST, String name, ATNFactory.Handle blk) {
        this.ruleCommands.clear();
        return super.rule(ruleAST, name, blk);
    }

    @Override
    public ATNFactory.Handle action(ActionAST action) {
        int ruleIndex = this.currentRule.index;
        int actionIndex = this.g.lexerActions.get(action);
        LexerCustomAction lexerAction = new LexerCustomAction(ruleIndex, actionIndex);
        return this.action(action, lexerAction);
    }

    protected int getLexerActionIndex(LexerAction lexerAction) {
        Integer lexerActionIndex = this.actionToIndexMap.get(lexerAction);
        if (lexerActionIndex == null) {
            lexerActionIndex = this.actionToIndexMap.size();
            this.actionToIndexMap.put(lexerAction, lexerActionIndex);
            this.indexToActionMap.put(lexerActionIndex, lexerAction);
        }
        return lexerActionIndex;
    }

    @Override
    public ATNFactory.Handle action(String action) {
        if (action.trim().isEmpty()) {
            ATNState left = this.newState(null);
            ATNState right = this.newState(null);
            this.epsilon(left, right);
            return new ATNFactory.Handle(left, right);
        }
        ActionAST ast = new ActionAST(new CommonToken(4, action));
        this.currentRule.defineActionInAlt(this.currentOuterAlt, ast);
        return this.action(ast);
    }

    protected ATNFactory.Handle action(GrammarAST node, LexerAction lexerAction) {
        ATNState left = this.newState(node);
        ATNState right = this.newState(node);
        boolean isCtxDependent = false;
        int lexerActionIndex = this.getLexerActionIndex(lexerAction);
        ActionTransition a = new ActionTransition(right, this.currentRule.index, lexerActionIndex, isCtxDependent);
        left.addTransition(a);
        node.atnState = left;
        ATNFactory.Handle h = new ATNFactory.Handle(left, right);
        return h;
    }

    @Override
    public ATNFactory.Handle lexerAltCommands(ATNFactory.Handle alt, ATNFactory.Handle cmds) {
        ATNFactory.Handle h = new ATNFactory.Handle(alt.left, cmds.right);
        this.epsilon(alt.right, cmds.left);
        return h;
    }

    @Override
    public ATNFactory.Handle lexerCallCommand(GrammarAST ID, GrammarAST arg) {
        LexerAction lexerAction = this.createLexerAction(ID, arg);
        if (lexerAction != null) {
            return this.action(ID, lexerAction);
        }
        if (this.codegenTemplates == null) {
            return this.epsilon(ID);
        }
        ST cmdST = this.codegenTemplates.getInstanceOf("Lexer" + CharSupport.capitalize(ID.getText()) + "Command");
        if (cmdST == null) {
            this.g.tool.errMgr.grammarError(ErrorType.INVALID_LEXER_COMMAND, this.g.fileName, ID.token, ID.getText());
            return this.epsilon(ID);
        }
        if (cmdST.impl.formalArguments == null || !cmdST.impl.formalArguments.containsKey("arg")) {
            this.g.tool.errMgr.grammarError(ErrorType.UNWANTED_LEXER_COMMAND_ARGUMENT, this.g.fileName, ID.token, ID.getText());
            return this.epsilon(ID);
        }
        cmdST.add("arg", (Object)arg.getText());
        cmdST.add("grammar", (Object)arg.g);
        return this.action(cmdST.render());
    }

    @Override
    public ATNFactory.Handle lexerCommand(GrammarAST ID) {
        LexerAction lexerAction = this.createLexerAction(ID, null);
        if (lexerAction != null) {
            return this.action(ID, lexerAction);
        }
        if (this.codegenTemplates == null) {
            return this.epsilon(ID);
        }
        ST cmdST = this.codegenTemplates.getInstanceOf("Lexer" + CharSupport.capitalize(ID.getText()) + "Command");
        if (cmdST == null) {
            this.g.tool.errMgr.grammarError(ErrorType.INVALID_LEXER_COMMAND, this.g.fileName, ID.token, ID.getText());
            return this.epsilon(ID);
        }
        if (cmdST.impl.formalArguments != null && cmdST.impl.formalArguments.containsKey("arg")) {
            this.g.tool.errMgr.grammarError(ErrorType.MISSING_LEXER_COMMAND_ARGUMENT, this.g.fileName, ID.token, ID.getText());
            return this.epsilon(ID);
        }
        return this.action(cmdST.render());
    }

    @Override
    public ATNFactory.Handle range(GrammarAST a, GrammarAST b) {
        ATNState left = this.newState(a);
        ATNState right = this.newState(b);
        int t1 = CharSupport.getCharValueFromGrammarCharLiteral(a.getText());
        int t2 = CharSupport.getCharValueFromGrammarCharLiteral(b.getText());
        this.checkRange(a, b, t1, t2);
        left.addTransition(CodePointTransitions.createWithCodePointRange(right, t1, t2));
        a.atnState = left;
        b.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    @Override
    public ATNFactory.Handle set(GrammarAST associatedAST, List<GrammarAST> alts, boolean invert) {
        ATNState left = this.newState(associatedAST);
        ATNState right = this.newState(associatedAST);
        IntervalSet set = new IntervalSet(new int[0]);
        for (GrammarAST t : alts) {
            if (t.getType() == 52) {
                int a = CharSupport.getCharValueFromGrammarCharLiteral(t.getChild(0).getText());
                int b = CharSupport.getCharValueFromGrammarCharLiteral(t.getChild(1).getText());
                if (!this.checkRange((GrammarAST)t.getChild(0), (GrammarAST)t.getChild(1), a, b)) continue;
                this.checkSetCollision(associatedAST, set, a, b);
                set.add(a, b);
                continue;
            }
            if (t.getType() == 32) {
                set.addAll(this.getSetFromCharSetLiteral(t));
                continue;
            }
            if (t.getType() == 62) {
                int c = CharSupport.getCharValueFromGrammarCharLiteral(t.getText());
                if (c != -1) {
                    this.checkSetCollision(associatedAST, set, c);
                    set.add(c);
                    continue;
                }
                this.g.tool.errMgr.grammarError(ErrorType.INVALID_LITERAL_IN_LEXER_SET, this.g.fileName, t.getToken(), t.getText());
                continue;
            }
            if (t.getType() != 66) continue;
            this.g.tool.errMgr.grammarError(ErrorType.UNSUPPORTED_REFERENCE_IN_LEXER_SET, this.g.fileName, t.getToken(), t.getText());
        }
        if (invert) {
            left.addTransition(new NotSetTransition(right, set));
        } else {
            Transition transition;
            if (set.getIntervals().size() == 1) {
                Interval interval = set.getIntervals().get(0);
                transition = CodePointTransitions.createWithCodePointRange(right, interval.a, interval.b);
            } else {
                transition = new SetTransition(right, set);
            }
            left.addTransition(transition);
        }
        associatedAST.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    protected boolean checkRange(GrammarAST leftNode, GrammarAST rightNode, int leftValue, int rightValue) {
        boolean result = true;
        if (leftValue == -1) {
            result = false;
            this.g.tool.errMgr.grammarError(ErrorType.INVALID_LITERAL_IN_LEXER_SET, this.g.fileName, leftNode.getToken(), leftNode.getText());
        }
        if (rightValue == -1) {
            result = false;
            this.g.tool.errMgr.grammarError(ErrorType.INVALID_LITERAL_IN_LEXER_SET, this.g.fileName, rightNode.getToken(), rightNode.getText());
        }
        if (!result) {
            return result;
        }
        if (rightValue < leftValue) {
            this.g.tool.errMgr.grammarError(ErrorType.EMPTY_STRINGS_AND_SETS_NOT_ALLOWED, this.g.fileName, leftNode.parent.getToken(), leftNode.getText() + ".." + rightNode.getText());
        }
        return result;
    }

    @Override
    public ATNFactory.Handle stringLiteral(TerminalAST stringLiteralAST) {
        int codePoint;
        String chars = stringLiteralAST.getText();
        ATNState left = this.newState(stringLiteralAST);
        String s = CharSupport.getStringFromGrammarStringLiteral(chars);
        if (s == null) {
            return new ATNFactory.Handle(left, left);
        }
        int n = s.length();
        ATNState prev = left;
        ATNState right = null;
        for (int i = 0; i < n; i += Character.charCount(codePoint)) {
            right = this.newState(stringLiteralAST);
            codePoint = s.codePointAt(i);
            prev.addTransition(CodePointTransitions.createWithCodePoint(right, codePoint));
            prev = right;
        }
        stringLiteralAST.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    @Override
    public ATNFactory.Handle charSetLiteral(GrammarAST charSetAST) {
        ATNState left = this.newState(charSetAST);
        ATNState right = this.newState(charSetAST);
        IntervalSet set = this.getSetFromCharSetLiteral(charSetAST);
        left.addTransition(new SetTransition(right, set));
        charSetAST.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    public IntervalSet getSetFromCharSetLiteral(GrammarAST charSetAST) {
        int offset;
        String chars = charSetAST.getText();
        chars = chars.substring(1, chars.length() - 1);
        IntervalSet set = new IntervalSet(new int[0]);
        if (chars.length() == 0) {
            this.g.tool.errMgr.grammarError(ErrorType.EMPTY_STRINGS_AND_SETS_NOT_ALLOWED, this.g.fileName, charSetAST.getToken(), "[]");
            return set;
        }
        CharSetParseState state = CharSetParseState.NONE;
        int n = chars.length();
        for (int i = 0; i < n; i += offset) {
            if (state.mode == CharSetParseState.Mode.ERROR) {
                return new IntervalSet(new int[0]);
            }
            int c = chars.codePointAt(i);
            offset = Character.charCount(c);
            if (c == 92) {
                EscapeSequenceParsing.Result escapeParseResult = EscapeSequenceParsing.parseEscape(chars, i);
                switch (escapeParseResult.type) {
                    case INVALID: {
                        String invalid = chars.substring(escapeParseResult.startOffset, escapeParseResult.startOffset + escapeParseResult.parseLength);
                        this.g.tool.errMgr.grammarError(ErrorType.INVALID_ESCAPE_SEQUENCE, this.g.fileName, charSetAST.getToken(), invalid);
                        state = CharSetParseState.ERROR;
                        break;
                    }
                    case CODE_POINT: {
                        state = this.applyPrevStateAndMoveToCodePoint(charSetAST, set, state, escapeParseResult.codePoint);
                        break;
                    }
                    case PROPERTY: {
                        state = this.applyPrevStateAndMoveToProperty(charSetAST, set, state, escapeParseResult.propertyIntervalSet);
                    }
                }
                offset = escapeParseResult.parseLength;
                continue;
            }
            if (c == 45 && !state.inRange && i != 0 && i != n - 1) {
                if (state.mode == CharSetParseState.Mode.PREV_PROPERTY) {
                    this.g.tool.errMgr.grammarError(ErrorType.UNICODE_PROPERTY_NOT_ALLOWED_IN_RANGE, this.g.fileName, charSetAST.getToken(), charSetAST.getText());
                    state = CharSetParseState.ERROR;
                    continue;
                }
                state = new CharSetParseState(state.mode, true, state.prevCodePoint, state.prevProperty);
                continue;
            }
            state = this.applyPrevStateAndMoveToCodePoint(charSetAST, set, state, c);
        }
        if (state.mode == CharSetParseState.Mode.ERROR) {
            return new IntervalSet(new int[0]);
        }
        this.applyPrevState(charSetAST, set, state);
        return set;
    }

    private CharSetParseState applyPrevStateAndMoveToCodePoint(GrammarAST charSetAST, IntervalSet set, CharSetParseState state, int codePoint) {
        if (state.inRange) {
            if (state.prevCodePoint > codePoint) {
                this.g.tool.errMgr.grammarError(ErrorType.EMPTY_STRINGS_AND_SETS_NOT_ALLOWED, this.g.fileName, charSetAST.getToken(), CharSupport.getRangeEscapedString(state.prevCodePoint, codePoint));
            }
            this.checkSetCollision(charSetAST, set, state.prevCodePoint, codePoint);
            set.add(state.prevCodePoint, codePoint);
            state = CharSetParseState.NONE;
        } else {
            this.applyPrevState(charSetAST, set, state);
            state = new CharSetParseState(CharSetParseState.Mode.PREV_CODE_POINT, false, codePoint, IntervalSet.EMPTY_SET);
        }
        return state;
    }

    private CharSetParseState applyPrevStateAndMoveToProperty(GrammarAST charSetAST, IntervalSet set, CharSetParseState state, IntervalSet property) {
        if (state.inRange) {
            this.g.tool.errMgr.grammarError(ErrorType.UNICODE_PROPERTY_NOT_ALLOWED_IN_RANGE, this.g.fileName, charSetAST.getToken(), charSetAST.getText());
            return CharSetParseState.ERROR;
        }
        this.applyPrevState(charSetAST, set, state);
        state = new CharSetParseState(CharSetParseState.Mode.PREV_PROPERTY, false, -1, property);
        return state;
    }

    private void applyPrevState(GrammarAST charSetAST, IntervalSet set, CharSetParseState state) {
        switch (state.mode) {
            case NONE: 
            case ERROR: {
                break;
            }
            case PREV_CODE_POINT: {
                this.checkSetCollision(charSetAST, set, state.prevCodePoint);
                set.add(state.prevCodePoint);
                break;
            }
            case PREV_PROPERTY: {
                set.addAll(state.prevProperty);
            }
        }
    }

    protected void checkSetCollision(GrammarAST ast, IntervalSet set, int el) {
        this.checkSetCollision(ast, set, el, el);
    }

    protected void checkSetCollision(GrammarAST ast, IntervalSet set, int a, int b) {
        for (int i = a; i <= b; ++i) {
            String setText;
            if (!set.contains(i)) continue;
            if (ast.getChildren() == null) {
                setText = ast.getText();
            } else {
                StringBuilder sb = new StringBuilder();
                for (Object object : ast.getChildren()) {
                    if (object instanceof RangeAST) {
                        sb.append(((RangeAST)object).getChild(0).getText());
                        sb.append("..");
                        sb.append(((RangeAST)object).getChild(1).getText());
                    } else {
                        sb.append(((GrammarAST)object).getText());
                    }
                    sb.append(" | ");
                }
                sb.replace(sb.length() - 3, sb.length(), "");
                setText = sb.toString();
            }
            this.g.tool.errMgr.grammarError(ErrorType.CHARACTERS_COLLISION_IN_SET, this.g.fileName, ast.getToken(), CharSupport.getRangeEscapedString(a, b), setText);
            break;
        }
    }

    @Override
    public ATNFactory.Handle tokenRef(TerminalAST node) {
        if (node.getText().equals("EOF")) {
            ATNState left = this.newState(node);
            ATNState right = this.newState(node);
            left.addTransition(new AtomTransition(right, -1));
            return new ATNFactory.Handle(left, right);
        }
        return this._ruleRef(node);
    }

    @Nullable
    private LexerAction createLexerAction(@NotNull GrammarAST ID, @Nullable GrammarAST arg) {
        String command = ID.getText();
        this.checkCommands(command, ID.getToken());
        if ("skip".equals(command) && arg == null) {
            return LexerSkipAction.INSTANCE;
        }
        if ("more".equals(command) && arg == null) {
            return LexerMoreAction.INSTANCE;
        }
        if ("popMode".equals(command) && arg == null) {
            return LexerPopModeAction.INSTANCE;
        }
        if ("mode".equals(command) && arg != null) {
            String modeName = arg.getText();
            Integer mode = this.getModeConstantValue(modeName, arg.getToken());
            if (mode == null) {
                return null;
            }
            return new LexerModeAction(mode);
        }
        if ("pushMode".equals(command) && arg != null) {
            String modeName = arg.getText();
            Integer mode = this.getModeConstantValue(modeName, arg.getToken());
            if (mode == null) {
                return null;
            }
            return new LexerPushModeAction(mode);
        }
        if ("type".equals(command) && arg != null) {
            String typeName = arg.getText();
            Integer type = this.getTokenConstantValue(typeName, arg.getToken());
            if (type == null) {
                return null;
            }
            return new LexerTypeAction(type);
        }
        if ("channel".equals(command) && arg != null) {
            String channelName = arg.getText();
            Integer channel = this.getChannelConstantValue(channelName, arg.getToken());
            if (channel == null) {
                return null;
            }
            return new LexerChannelAction(channel);
        }
        return null;
    }

    private void checkCommands(String command, Token commandToken) {
        if (!command.equals("pushMode") && !command.equals("popMode")) {
            if (this.ruleCommands.contains(command)) {
                this.g.tool.errMgr.grammarError(ErrorType.DUPLICATED_COMMAND, this.g.fileName, commandToken, command);
            }
            if (!this.ruleCommands.equals("mode")) {
                String firstCommand = null;
                if (command.equals("skip")) {
                    if (this.ruleCommands.contains("more")) {
                        firstCommand = "more";
                    } else if (this.ruleCommands.contains("type")) {
                        firstCommand = "type";
                    } else if (this.ruleCommands.contains("channel")) {
                        firstCommand = "channel";
                    }
                } else if (command.equals("more")) {
                    if (this.ruleCommands.contains("skip")) {
                        firstCommand = "skip";
                    } else if (this.ruleCommands.contains("type")) {
                        firstCommand = "type";
                    } else if (this.ruleCommands.contains("channel")) {
                        firstCommand = "channel";
                    }
                } else if (command.equals("type") || command.equals("channel")) {
                    if (this.ruleCommands.contains("more")) {
                        firstCommand = "more";
                    } else if (this.ruleCommands.contains("skip")) {
                        firstCommand = "skip";
                    }
                }
                if (firstCommand != null) {
                    this.g.tool.errMgr.grammarError(ErrorType.INCOMPATIBLE_COMMANDS, this.g.fileName, commandToken, firstCommand, command);
                }
            }
        }
        this.ruleCommands.add(command);
    }

    @Nullable
    private Integer getModeConstantValue(@Nullable String modeName, @Nullable Token token) {
        if (modeName == null) {
            return null;
        }
        if (modeName.equals("DEFAULT_MODE")) {
            return 0;
        }
        if (COMMON_CONSTANTS.containsKey(modeName)) {
            this.g.tool.errMgr.grammarError(ErrorType.MODE_CONFLICTS_WITH_COMMON_CONSTANTS, this.g.fileName, token, token.getText());
            return null;
        }
        ArrayList modeNames = new ArrayList(((LexerGrammar)this.g).modes.keySet());
        int mode = modeNames.indexOf(modeName);
        if (mode >= 0) {
            return mode;
        }
        try {
            return Integer.parseInt(modeName);
        }
        catch (NumberFormatException ex) {
            this.g.tool.errMgr.grammarError(ErrorType.CONSTANT_VALUE_IS_NOT_A_RECOGNIZED_MODE_NAME, this.g.fileName, token, token.getText());
            return null;
        }
    }

    @Nullable
    private Integer getTokenConstantValue(@Nullable String tokenName, @Nullable Token token) {
        if (tokenName == null) {
            return null;
        }
        if (tokenName.equals("EOF")) {
            return -1;
        }
        if (COMMON_CONSTANTS.containsKey(tokenName)) {
            this.g.tool.errMgr.grammarError(ErrorType.TOKEN_CONFLICTS_WITH_COMMON_CONSTANTS, this.g.fileName, token, token.getText());
            return null;
        }
        int tokenType = this.g.getTokenType(tokenName);
        if (tokenType != 0) {
            return tokenType;
        }
        try {
            return Integer.parseInt(tokenName);
        }
        catch (NumberFormatException ex) {
            this.g.tool.errMgr.grammarError(ErrorType.CONSTANT_VALUE_IS_NOT_A_RECOGNIZED_TOKEN_NAME, this.g.fileName, token, token.getText());
            return null;
        }
    }

    @Nullable
    private Integer getChannelConstantValue(@Nullable String channelName, @Nullable Token token) {
        if (channelName == null) {
            return null;
        }
        if (channelName.equals("HIDDEN")) {
            return 1;
        }
        if (channelName.equals("DEFAULT_TOKEN_CHANNEL")) {
            return 0;
        }
        if (COMMON_CONSTANTS.containsKey(channelName)) {
            this.g.tool.errMgr.grammarError(ErrorType.CHANNEL_CONFLICTS_WITH_COMMON_CONSTANTS, this.g.fileName, token, token.getText());
            return null;
        }
        int channelValue = this.g.getChannelValue(channelName);
        if (channelValue >= 2) {
            return channelValue;
        }
        try {
            return Integer.parseInt(channelName);
        }
        catch (NumberFormatException ex) {
            this.g.tool.errMgr.grammarError(ErrorType.CONSTANT_VALUE_IS_NOT_A_RECOGNIZED_CHANNEL_NAME, this.g.fileName, token, token.getText());
            return null;
        }
    }

    static {
        COMMON_CONSTANTS.put("HIDDEN", 1);
        COMMON_CONSTANTS.put("DEFAULT_TOKEN_CHANNEL", 0);
        COMMON_CONSTANTS.put("DEFAULT_MODE", 0);
        COMMON_CONSTANTS.put("SKIP", -3);
        COMMON_CONSTANTS.put("MORE", -2);
        COMMON_CONSTANTS.put("EOF", -1);
        COMMON_CONSTANTS.put("MAX_CHAR_VALUE", 0x10FFFF);
        COMMON_CONSTANTS.put("MIN_CHAR_VALUE", 0);
    }

    private static class CharSetParseState {
        public static final CharSetParseState NONE = new CharSetParseState(Mode.NONE, false, -1, IntervalSet.EMPTY_SET);
        public static final CharSetParseState ERROR = new CharSetParseState(Mode.ERROR, false, -1, IntervalSet.EMPTY_SET);
        public final Mode mode;
        public final boolean inRange;
        public final int prevCodePoint;
        public final IntervalSet prevProperty;

        public CharSetParseState(Mode mode, boolean inRange, int prevCodePoint, IntervalSet prevProperty) {
            this.mode = mode;
            this.inRange = inRange;
            this.prevCodePoint = prevCodePoint;
            this.prevProperty = prevProperty;
        }

        public String toString() {
            return String.format("%s mode=%s inRange=%s prevCodePoint=%d prevProperty=%s", new Object[]{super.toString(), this.mode, this.inRange, this.prevCodePoint, this.prevProperty});
        }

        public boolean equals(Object other) {
            if (!(other instanceof CharSetParseState)) {
                return false;
            }
            CharSetParseState that = (CharSetParseState)other;
            if (this == that) {
                return true;
            }
            return Utils.equals((Object)this.mode, (Object)that.mode) && Utils.equals(this.inRange, that.inRange) && Utils.equals(this.prevCodePoint, that.prevCodePoint) && Utils.equals(this.prevProperty, that.prevProperty);
        }

        public int hashCode() {
            int hash = MurmurHash.initialize();
            hash = MurmurHash.update(hash, (Object)this.mode);
            hash = MurmurHash.update(hash, this.inRange);
            hash = MurmurHash.update(hash, this.prevCodePoint);
            hash = MurmurHash.update(hash, this.prevProperty);
            return MurmurHash.finish(hash, 4);
        }

        static enum Mode {
            NONE,
            ERROR,
            PREV_CODE_POINT,
            PREV_PROPERTY;

        }
    }
}

