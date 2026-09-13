/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  org.yaml.snakeyaml.error.MarkedYAMLException
 *  org.yaml.snakeyaml.error.YAMLException
 */
package com.fasterxml.jackson.dataformat.yaml.snakeyaml.error;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.error.Mark;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.error.YAMLException;

@Deprecated
public class MarkedYAMLException
extends YAMLException {
    private static final long serialVersionUID = 1L;
    protected final org.yaml.snakeyaml.error.MarkedYAMLException _source;

    protected MarkedYAMLException(JsonParser p, org.yaml.snakeyaml.error.MarkedYAMLException src) {
        super(p, (org.yaml.snakeyaml.error.YAMLException)src);
        this._source = src;
    }

    public static MarkedYAMLException from(JsonParser p, org.yaml.snakeyaml.error.MarkedYAMLException src) {
        return new MarkedYAMLException(p, src);
    }

    public String getContext() {
        return this._source.getContext();
    }

    public Mark getContextMark() {
        return Mark.from(this._source.getContextMark());
    }

    public String getProblem() {
        return this._source.getProblem();
    }

    public Mark getProblemMark() {
        return Mark.from(this._source.getProblemMark());
    }
}

