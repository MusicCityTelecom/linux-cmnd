/*
 * Decompiled with CFR 0.152.
 */
package com.vdurmont.semver4j;

import com.vdurmont.semver4j.Range;
import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import com.vdurmont.semver4j.Tokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Requirement {
    private static final Pattern IVY_DYNAMIC_PATCH_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.\\+");
    private static final Pattern IVY_DYNAMIC_MINOR_PATTERN = Pattern.compile("(\\d+)\\.\\+");
    private static final Pattern IVY_LATEST_PATTERN = Pattern.compile("latest\\.\\w+");
    private static final Pattern IVY_MATH_BOUNDED_PATTERN = Pattern.compile("(\\[|\\])([\\d\\.]+),([\\d\\.]+)(\\[|\\])");
    private static final Pattern IVY_MATH_LOWER_UNBOUNDED_PATTERN = Pattern.compile("\\(,([\\d\\.]+)(\\[|\\])");
    private static final Pattern IVY_MATH_UPPER_UNBOUNDED_PATTERN = Pattern.compile("(\\[|\\])([\\d\\.]+),\\)");
    protected final Range range;
    protected final Requirement req1;
    protected final RequirementOperator op;
    protected final Requirement req2;

    protected Requirement(Range range, Requirement req1, RequirementOperator op, Requirement req2) {
        this.range = range;
        this.req1 = req1;
        this.op = op;
        this.req2 = req2;
    }

    public static Requirement build(Semver requirement) {
        return new Requirement(new Range(requirement, Range.RangeOperator.EQ), null, null, null);
    }

    public static Requirement buildStrict(String requirement) {
        return Requirement.build(new Semver(requirement, Semver.SemverType.STRICT));
    }

    public static Requirement buildLoose(String requirement) {
        return Requirement.build(new Semver(requirement, Semver.SemverType.LOOSE));
    }

    public static Requirement buildNPM(String requirement) {
        if (requirement.isEmpty()) {
            requirement = "*";
        }
        return Requirement.buildWithTokenizer(requirement, Semver.SemverType.NPM);
    }

    public static Requirement buildCocoapods(String requirement) {
        return Requirement.buildWithTokenizer(requirement, Semver.SemverType.COCOAPODS);
    }

    private static Requirement buildWithTokenizer(String requirement, Semver.SemverType type) {
        List<Tokenizer.Token> tokens = Tokenizer.tokenize(requirement, type);
        tokens = Requirement.removeFalsePositiveVersionRanges(tokens);
        tokens = Requirement.addParentheses(tokens);
        List<Tokenizer.Token> rpn = Requirement.toReversePolishNotation(tokens);
        return Requirement.evaluateReversePolishNotation(rpn.iterator(), type);
    }

    public static Requirement buildIvy(String requirement) {
        try {
            return Requirement.buildLoose(requirement);
        }
        catch (SemverException semverException) {
            Matcher matcher = IVY_DYNAMIC_PATCH_PATTERN.matcher(requirement);
            if (matcher.find()) {
                int major = Integer.valueOf(matcher.group(1));
                int minor = Integer.valueOf(matcher.group(2));
                Requirement lower = new Requirement(new Range(major + "." + minor + ".0", Range.RangeOperator.GTE), null, null, null);
                Requirement upper = new Requirement(new Range(major + "." + (minor + 1) + ".0", Range.RangeOperator.LT), null, null, null);
                return new Requirement(null, lower, RequirementOperator.AND, upper);
            }
            matcher = IVY_DYNAMIC_MINOR_PATTERN.matcher(requirement);
            if (matcher.find()) {
                int major = Integer.valueOf(matcher.group(1));
                Requirement lower = new Requirement(new Range(major + ".0.0", Range.RangeOperator.GTE), null, null, null);
                Requirement upper = new Requirement(new Range(major + 1 + ".0.0", Range.RangeOperator.LT), null, null, null);
                return new Requirement(null, lower, RequirementOperator.AND, upper);
            }
            matcher = IVY_LATEST_PATTERN.matcher(requirement);
            if (matcher.find()) {
                return new Requirement(new Range("0.0.0", Range.RangeOperator.GTE), null, null, null);
            }
            matcher = IVY_MATH_BOUNDED_PATTERN.matcher(requirement);
            if (matcher.find()) {
                Range.RangeOperator lowerOp = "[".equals(matcher.group(1)) ? Range.RangeOperator.GTE : Range.RangeOperator.GT;
                Semver lowerVersion = new Semver(matcher.group(2), Semver.SemverType.LOOSE);
                Semver upperVersion = new Semver(matcher.group(3), Semver.SemverType.LOOSE);
                Range.RangeOperator upperOp = "]".equals(matcher.group(4)) ? Range.RangeOperator.LTE : Range.RangeOperator.LT;
                Requirement lower = new Requirement(new Range(Requirement.extrapolateVersion(lowerVersion), lowerOp), null, null, null);
                Requirement upper = new Requirement(new Range(Requirement.extrapolateVersion(upperVersion), upperOp), null, null, null);
                return new Requirement(null, lower, RequirementOperator.AND, upper);
            }
            matcher = IVY_MATH_LOWER_UNBOUNDED_PATTERN.matcher(requirement);
            if (matcher.find()) {
                Semver version = new Semver(matcher.group(1), Semver.SemverType.LOOSE);
                Range.RangeOperator op = "]".equals(matcher.group(2)) ? Range.RangeOperator.LTE : Range.RangeOperator.LT;
                return new Requirement(new Range(Requirement.extrapolateVersion(version), op), null, null, null);
            }
            matcher = IVY_MATH_UPPER_UNBOUNDED_PATTERN.matcher(requirement);
            if (matcher.find()) {
                Range.RangeOperator op = "[".equals(matcher.group(1)) ? Range.RangeOperator.GTE : Range.RangeOperator.GT;
                Semver version = new Semver(matcher.group(2), Semver.SemverType.LOOSE);
                return new Requirement(new Range(Requirement.extrapolateVersion(version), op), null, null, null);
            }
            throw new SemverException("Invalid requirement");
        }
    }

    private static List<Tokenizer.Token> addParentheses(List<Tokenizer.Token> tokens) {
        ArrayList<Tokenizer.Token> result = new ArrayList<Tokenizer.Token>();
        result.add(new Tokenizer.Token(Tokenizer.TokenType.OPENING, "("));
        for (Tokenizer.Token token : tokens) {
            if (token.type == Tokenizer.TokenType.OR) {
                result.add(new Tokenizer.Token(Tokenizer.TokenType.CLOSING, ")"));
                result.add(token);
                result.add(new Tokenizer.Token(Tokenizer.TokenType.OPENING, "("));
                continue;
            }
            result.add(token);
        }
        result.add(new Tokenizer.Token(Tokenizer.TokenType.CLOSING, ")"));
        return result;
    }

    private static List<Tokenizer.Token> removeFalsePositiveVersionRanges(List<Tokenizer.Token> tokens) {
        ArrayList<Tokenizer.Token> result = new ArrayList<Tokenizer.Token>();
        for (int i = 0; i < tokens.size(); ++i) {
            Tokenizer.Token token = tokens.get(i);
            if (Requirement.thereIsFalsePositiveVersionRange(tokens, i)) {
                token = new Tokenizer.Token(Tokenizer.TokenType.VERSION, token.value + '-' + tokens.get((int)(i + 2)).value);
                i += 2;
            }
            result.add(token);
        }
        return result;
    }

    private static boolean thereIsFalsePositiveVersionRange(List<Tokenizer.Token> tokens, int i) {
        if (i + 2 >= tokens.size()) {
            return false;
        }
        Tokenizer.Token[] suspiciousTokens = new Tokenizer.Token[]{tokens.get(i), tokens.get(i + 1), tokens.get(i + 2)};
        if (!suspiciousTokens[0].type.equals((Object)Tokenizer.TokenType.VERSION)) {
            return false;
        }
        if (!suspiciousTokens[2].type.equals((Object)Tokenizer.TokenType.VERSION)) {
            return false;
        }
        if (!suspiciousTokens[1].type.equals((Object)Tokenizer.TokenType.HYPHEN)) {
            return false;
        }
        return Requirement.attemptToParse(suspiciousTokens[2].value) == null;
    }

    private static Semver attemptToParse(String value) {
        try {
            return new Semver(value, Semver.SemverType.NPM);
        }
        catch (SemverException semverException) {
            return null;
        }
    }

    private static List<Tokenizer.Token> toReversePolishNotation(List<Tokenizer.Token> tokens) {
        LinkedList<Tokenizer.Token> queue = new LinkedList<Tokenizer.Token>();
        Stack<Tokenizer.Token> stack = new Stack<Tokenizer.Token>();
        block4: for (int i = 0; i < tokens.size(); ++i) {
            Tokenizer.Token token = tokens.get(i);
            switch (token.type) {
                case VERSION: {
                    queue.push(token);
                    continue block4;
                }
                case CLOSING: {
                    while (((Tokenizer.Token)stack.peek()).type != Tokenizer.TokenType.OPENING) {
                        queue.push((Tokenizer.Token)stack.pop());
                    }
                    stack.pop();
                    if (stack.size() <= 0 || !((Tokenizer.Token)stack.peek()).type.isUnary()) continue block4;
                    queue.push((Tokenizer.Token)stack.pop());
                    continue block4;
                }
                default: {
                    if (token.type.isUnary()) {
                        queue.push(tokens.get(++i));
                        queue.push(token);
                        continue block4;
                    }
                    stack.push(token);
                }
            }
        }
        while (!stack.isEmpty()) {
            queue.push((Tokenizer.Token)stack.pop());
        }
        return queue;
    }

    private static Requirement evaluateReversePolishNotation(Iterator<Tokenizer.Token> iterator, Semver.SemverType type) {
        try {
            RequirementOperator requirementOp;
            Tokenizer.Token token = iterator.next();
            if (token.type == Tokenizer.TokenType.VERSION) {
                if ("*".equals(token.value) || type == Semver.SemverType.NPM && "latest".equals(token.value)) {
                    return new Requirement(new Range("0.0.0", Range.RangeOperator.GTE), null, null, null);
                }
                Semver version = new Semver(token.value, type);
                if (version.getMinor() != null && version.getPatch() != null) {
                    Range range = new Range(version, Range.RangeOperator.EQ);
                    return new Requirement(range, null, null, null);
                }
                return Requirement.tildeRequirement(version.getValue(), type);
            }
            if (token.type == Tokenizer.TokenType.HYPHEN) {
                Tokenizer.Token token3 = iterator.next();
                Tokenizer.Token token2 = iterator.next();
                return Requirement.hyphenRequirement(token2.value, token3.value, type);
            }
            if (token.type.isUnary()) {
                Range.RangeOperator rangeOp;
                Tokenizer.Token token2 = iterator.next();
                switch (token.type) {
                    case EQ: {
                        rangeOp = Range.RangeOperator.EQ;
                        break;
                    }
                    case LT: {
                        rangeOp = Range.RangeOperator.LT;
                        break;
                    }
                    case LTE: {
                        rangeOp = Range.RangeOperator.LTE;
                        break;
                    }
                    case GT: {
                        rangeOp = Range.RangeOperator.GT;
                        break;
                    }
                    case GTE: {
                        rangeOp = Range.RangeOperator.GTE;
                        break;
                    }
                    case TILDE: {
                        return Requirement.tildeRequirement(token2.value, type);
                    }
                    case CARET: {
                        return Requirement.caretRequirement(token2.value, type);
                    }
                    default: {
                        throw new SemverException("Invalid requirement");
                    }
                }
                Range range = new Range(token2.value, rangeOp);
                return new Requirement(range, null, null, null);
            }
            Requirement req2 = Requirement.evaluateReversePolishNotation(iterator, type);
            Requirement req1 = Requirement.evaluateReversePolishNotation(iterator, type);
            switch (token.type) {
                case OR: {
                    requirementOp = RequirementOperator.OR;
                    break;
                }
                case AND: {
                    requirementOp = RequirementOperator.AND;
                    break;
                }
                default: {
                    throw new SemverException("Invalid requirement");
                }
            }
            return new Requirement(null, req1, requirementOp, req2);
        }
        catch (NoSuchElementException e) {
            throw new SemverException("Invalid requirement");
        }
    }

    protected static Requirement tildeRequirement(String version, Semver.SemverType type) {
        String next;
        if (type != Semver.SemverType.NPM && type != Semver.SemverType.COCOAPODS) {
            throw new SemverException("The tilde requirements are only compatible with NPM and Cocoapods.");
        }
        Semver semver = new Semver(version, type);
        Requirement req1 = new Requirement(new Range(Requirement.extrapolateVersion(semver), Range.RangeOperator.GTE), null, null, null);
        switch (type) {
            case COCOAPODS: {
                if (semver.getPatch() != null) {
                    next = semver.getMajor() + "." + (semver.getMinor() + 1) + ".0";
                    break;
                }
                if (semver.getMinor() != null) {
                    next = semver.getMajor() + 1 + ".0.0";
                    break;
                }
                return req1;
            }
            case NPM: {
                if (semver.getMinor() != null) {
                    next = semver.getMajor() + "." + (semver.getMinor() + 1) + ".0";
                    break;
                }
                next = semver.getMajor() + 1 + ".0.0";
                break;
            }
            default: {
                throw new SemverException("The tilde requirements are only compatible with NPM and Cocoapods.");
            }
        }
        Requirement req2 = new Requirement(new Range(next, Range.RangeOperator.LT), null, null, null);
        return new Requirement(null, req1, RequirementOperator.AND, req2);
    }

    protected static Requirement caretRequirement(String version, Semver.SemverType type) {
        if (type != Semver.SemverType.NPM) {
            throw new SemverException("The caret requirements are only compatible with NPM.");
        }
        Semver semver = new Semver(version, type);
        Requirement req1 = new Requirement(new Range(Requirement.extrapolateVersion(semver), Range.RangeOperator.GTE), null, null, null);
        String next = semver.getMajor() == 0 ? (semver.getMinor() == null ? "1.0.0" : (semver.getMinor() == 0 ? (semver.getPatch() == null ? "0.1.0" : "0.0." + (semver.getPatch() + 1)) : "0." + (semver.getMinor() + 1) + ".0")) : semver.getMajor() + 1 + ".0.0";
        Requirement req2 = new Requirement(new Range(next, Range.RangeOperator.LT), null, null, null);
        return new Requirement(null, req1, RequirementOperator.AND, req2);
    }

    protected static Requirement hyphenRequirement(String lowerVersion, String upperVersion, Semver.SemverType type) {
        if (type != Semver.SemverType.NPM) {
            throw new SemverException("The hyphen requirements are only compatible with NPM.");
        }
        Semver lower = Requirement.extrapolateVersion(new Semver(lowerVersion, type));
        Semver upper = new Semver(upperVersion, type);
        Range.RangeOperator upperOperator = Range.RangeOperator.LTE;
        if (upper.getMinor() == null || upper.getPatch() == null) {
            upperOperator = Range.RangeOperator.LT;
            upper = upper.getMinor() == null ? Requirement.extrapolateVersion(upper).withIncMajor() : Requirement.extrapolateVersion(upper).withIncMinor();
        }
        Requirement req1 = new Requirement(new Range(lower, Range.RangeOperator.GTE), null, null, null);
        Requirement req2 = new Requirement(new Range(upper, upperOperator), null, null, null);
        return new Requirement(null, req1, RequirementOperator.AND, req2);
    }

    private static Semver extrapolateVersion(Semver semver) {
        StringBuilder sb = new StringBuilder().append(semver.getMajor()).append(".").append(semver.getMinor() == null ? 0 : semver.getMinor()).append(".").append(semver.getPatch() == null ? 0 : semver.getPatch());
        boolean first = true;
        for (int i = 0; i < semver.getSuffixTokens().length; ++i) {
            if (first) {
                sb.append("-");
                first = false;
            } else {
                sb.append(".");
            }
            sb.append(semver.getSuffixTokens()[i]);
        }
        if (semver.getBuild() != null) {
            sb.append("+").append(semver.getBuild());
        }
        return new Semver(sb.toString(), semver.getType());
    }

    public boolean isSatisfiedBy(String version) {
        if (this.range != null) {
            return this.isSatisfiedBy(new Semver(version, this.range.version.getType()));
        }
        return this.isSatisfiedBy(new Semver(version));
    }

    public boolean isSatisfiedBy(Semver version) {
        if (this.range != null) {
            return this.range.isSatisfiedBy(version);
        }
        switch (this.op) {
            case AND: {
                try {
                    List<Range> set = this.getAllRanges(this, new ArrayList<Range>());
                    for (Range range : set) {
                        if (range.isSatisfiedBy(version)) continue;
                        return false;
                    }
                    if (version.getSuffixTokens().length > 0) {
                        for (Range range : set) {
                            if (range.version == null || range.version.getSuffixTokens().length <= 0) continue;
                            Semver allowed = range.version;
                            if (!Objects.equals(version.getMajor(), allowed.getMajor()) || !Objects.equals(version.getMinor(), allowed.getMinor()) || !Objects.equals(version.getPatch(), allowed.getPatch())) continue;
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
                catch (Exception e) {
                    return this.req1.isSatisfiedBy(version) && this.req2.isSatisfiedBy(version);
                }
            }
            case OR: {
                return this.req1.isSatisfiedBy(version) || this.req2.isSatisfiedBy(version);
            }
        }
        throw new RuntimeException("Code error. Unknown RequirementOperator: " + (Object)((Object)this.op));
    }

    private List<Range> getAllRanges(Requirement requirement, List<Range> res) {
        if (requirement.range != null) {
            res.add(requirement.range);
        } else if (requirement.op == RequirementOperator.AND) {
            this.getAllRanges(requirement.req1, res);
            this.getAllRanges(requirement.req2, res);
        } else {
            throw new RuntimeException("OR in AND not allowed");
        }
        return res;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requirement)) {
            return false;
        }
        Requirement that = (Requirement)o;
        return Objects.equals(this.range, that.range) && Objects.equals(this.req1, that.req1) && this.op == that.op && Objects.equals(this.req2, that.req2);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.range, this.req1, this.op, this.req2});
    }

    public String toString() {
        if (this.range != null) {
            return this.range.toString();
        }
        return this.req1 + " " + (this.op == RequirementOperator.OR ? this.op.asString() + " " : "") + this.req2;
    }

    protected static enum RequirementOperator {
        AND(""),
        OR("||");

        private final String s;

        private RequirementOperator(String s) {
            this.s = s;
        }

        public String asString() {
            return this.s;
        }
    }
}

