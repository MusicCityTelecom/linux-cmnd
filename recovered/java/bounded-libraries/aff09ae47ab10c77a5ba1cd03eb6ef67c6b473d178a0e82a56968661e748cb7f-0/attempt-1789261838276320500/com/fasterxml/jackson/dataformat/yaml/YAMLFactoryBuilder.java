/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.core.TSFBuilder
 *  org.yaml.snakeyaml.DumperOptions$Version
 */
package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.util.StringQuotingChecker;
import org.yaml.snakeyaml.DumperOptions;

public class YAMLFactoryBuilder
extends TSFBuilder<YAMLFactory, YAMLFactoryBuilder> {
    protected int _formatGeneratorFeatures;
    protected StringQuotingChecker _quotingChecker;
    protected DumperOptions.Version _version;

    protected YAMLFactoryBuilder() {
        this._formatGeneratorFeatures = YAMLFactory.DEFAULT_YAML_GENERATOR_FEATURE_FLAGS;
    }

    public YAMLFactoryBuilder(YAMLFactory base) {
        super((JsonFactory)base);
        this._formatGeneratorFeatures = base._yamlGeneratorFeatures;
        this._version = base._version;
        this._quotingChecker = base._quotingChecker;
    }

    public YAMLFactoryBuilder enable(YAMLGenerator.Feature f) {
        this._formatGeneratorFeatures |= f.getMask();
        return this;
    }

    public YAMLFactoryBuilder enable(YAMLGenerator.Feature first, YAMLGenerator.Feature ... other) {
        this._formatGeneratorFeatures |= first.getMask();
        for (YAMLGenerator.Feature f : other) {
            this._formatGeneratorFeatures |= f.getMask();
        }
        return this;
    }

    public YAMLFactoryBuilder disable(YAMLGenerator.Feature f) {
        this._formatGeneratorFeatures &= ~f.getMask();
        return this;
    }

    public YAMLFactoryBuilder disable(YAMLGenerator.Feature first, YAMLGenerator.Feature ... other) {
        this._formatGeneratorFeatures &= ~first.getMask();
        for (YAMLGenerator.Feature f : other) {
            this._formatGeneratorFeatures &= ~f.getMask();
        }
        return this;
    }

    public YAMLFactoryBuilder configure(YAMLGenerator.Feature f, boolean state) {
        return state ? this.enable(f) : this.disable(f);
    }

    public YAMLFactoryBuilder stringQuotingChecker(StringQuotingChecker sqc) {
        this._quotingChecker = sqc;
        return this;
    }

    public YAMLFactoryBuilder yamlVersionToWrite(DumperOptions.Version v) {
        this._version = v;
        return this;
    }

    public int formatGeneratorFeaturesMask() {
        return this._formatGeneratorFeatures;
    }

    public DumperOptions.Version yamlVersionToWrite() {
        return this._version;
    }

    public StringQuotingChecker stringQuotingChecker() {
        if (this._quotingChecker != null) {
            return this._quotingChecker;
        }
        return StringQuotingChecker.Default.instance();
    }

    public YAMLFactory build() {
        return new YAMLFactory(this);
    }
}

