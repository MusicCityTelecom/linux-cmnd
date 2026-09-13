/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.apache.commons.jexl3;

import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.jexl3.introspection.JexlSandbox;
import org.apache.commons.jexl3.introspection.JexlUberspect;
import org.apache.commons.logging.Log;

public class JexlBuilder {
    protected static final int CACHE_THRESHOLD = 64;
    private JexlUberspect uberspect = null;
    private JexlUberspect.ResolverStrategy strategy = null;
    private JexlSandbox sandbox = null;
    private Log logger = null;
    private Boolean debug = null;
    private Boolean cancellable = null;
    private final JexlOptions options = new JexlOptions();
    private int collectMode = 1;
    private JexlArithmetic arithmetic = null;
    private int cache = -1;
    private int stackOverflow = Integer.MAX_VALUE;
    private int cacheThreshold = 64;
    private Charset charset = Charset.defaultCharset();
    private ClassLoader loader = null;
    private JexlFeatures features = null;

    public JexlBuilder uberspect(JexlUberspect u) {
        this.uberspect = u;
        return this;
    }

    public JexlUberspect uberspect() {
        return this.uberspect;
    }

    public JexlBuilder strategy(JexlUberspect.ResolverStrategy rs) {
        this.strategy = rs;
        return this;
    }

    public JexlUberspect.ResolverStrategy strategy() {
        return this.strategy;
    }

    public JexlOptions options() {
        return this.options;
    }

    public JexlBuilder arithmetic(JexlArithmetic a) {
        this.arithmetic = a;
        this.options.setStrictArithmetic(a.isStrict());
        this.options.setMathContext(a.getMathContext());
        this.options.setMathScale(a.getMathScale());
        return this;
    }

    public JexlArithmetic arithmetic() {
        return this.arithmetic;
    }

    public JexlBuilder sandbox(JexlSandbox box) {
        this.sandbox = box;
        return this;
    }

    public JexlSandbox sandbox() {
        return this.sandbox;
    }

    public JexlBuilder features(JexlFeatures f) {
        this.features = f;
        if (this.features != null) {
            if (this.features.isLexical()) {
                this.options.setLexical(true);
            }
            if (this.features.isLexicalShade()) {
                this.options.setLexicalShade(true);
            }
        }
        return this;
    }

    public JexlFeatures features() {
        return this.features;
    }

    public JexlBuilder logger(Log log) {
        this.logger = log;
        return this;
    }

    public Log logger() {
        return this.logger;
    }

    public JexlBuilder loader(ClassLoader l) {
        this.loader = l;
        return this;
    }

    public ClassLoader loader() {
        return this.loader;
    }

    @Deprecated
    public JexlBuilder loader(Charset arg) {
        return this.charset(arg);
    }

    public JexlBuilder charset(Charset arg) {
        this.charset = arg;
        return this;
    }

    public Charset charset() {
        return this.charset;
    }

    public JexlBuilder antish(boolean flag) {
        this.options.setAntish(flag);
        return this;
    }

    public boolean antish() {
        return this.options.isAntish();
    }

    public JexlBuilder lexical(boolean flag) {
        this.options.setLexical(flag);
        return this;
    }

    public boolean lexical() {
        return this.options.isLexical();
    }

    public JexlBuilder lexicalShade(boolean flag) {
        this.options.setLexicalShade(flag);
        return this;
    }

    public boolean lexicalShade() {
        return this.options.isLexicalShade();
    }

    public JexlBuilder silent(boolean flag) {
        this.options.setSilent(flag);
        return this;
    }

    public Boolean silent() {
        return this.options.isSilent();
    }

    public JexlBuilder strict(boolean flag) {
        this.options.setStrict(flag);
        return this;
    }

    public Boolean strict() {
        return this.options.isStrict();
    }

    public JexlBuilder safe(boolean flag) {
        this.options.setSafe(flag);
        return this;
    }

    public Boolean safe() {
        return this.options.isSafe();
    }

    public JexlBuilder debug(boolean flag) {
        this.debug = flag;
        return this;
    }

    public Boolean debug() {
        return this.debug;
    }

    public JexlBuilder cancellable(boolean flag) {
        this.cancellable = flag;
        this.options.setCancellable(flag);
        return this;
    }

    public Boolean cancellable() {
        return this.cancellable;
    }

    public JexlBuilder collectAll(boolean flag) {
        return this.collectMode(flag ? 1 : 0);
    }

    public JexlBuilder collectMode(int mode) {
        this.collectMode = mode;
        return this;
    }

    public boolean collectAll() {
        return this.collectMode != 0;
    }

    public int collectMode() {
        return this.collectMode;
    }

    public JexlBuilder namespaces(Map<String, Object> ns) {
        this.options.setNamespaces(ns);
        return this;
    }

    public Map<String, Object> namespaces() {
        return this.options.getNamespaces();
    }

    public JexlBuilder cache(int size) {
        this.cache = size;
        return this;
    }

    public int cache() {
        return this.cache;
    }

    public JexlBuilder cacheThreshold(int length) {
        this.cacheThreshold = length > 0 ? length : 64;
        return this;
    }

    public int cacheThreshold() {
        return this.cacheThreshold;
    }

    public JexlBuilder stackOverflow(int size) {
        this.stackOverflow = size;
        return this;
    }

    public int stackOverflow() {
        return this.stackOverflow;
    }

    public JexlEngine create() {
        return new Engine(this);
    }
}

