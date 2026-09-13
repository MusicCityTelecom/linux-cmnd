/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.JsonStreamContext
 */
package com.fasterxml.jackson.dataformat.javaprop.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;

public class JPropWriteContext
extends JsonStreamContext {
    protected final JPropWriteContext _parent;
    protected JPropWriteContext _child = null;
    protected Object _currentValue;
    protected boolean _gotName;
    protected String _currentName;
    protected int _basePathLength;

    protected JPropWriteContext(int type, JPropWriteContext parent, int basePathLength) {
        this._type = type;
        this._parent = parent;
        this._basePathLength = basePathLength;
        this._index = -1;
    }

    private void reset(int type, int basePathLength) {
        this._type = type;
        this._basePathLength = basePathLength;
        this._currentValue = null;
        this._index = -1;
    }

    public static JPropWriteContext createRootContext() {
        return new JPropWriteContext(0, null, 0);
    }

    public static JPropWriteContext createRootContext(int basePathLength) {
        return new JPropWriteContext(0, null, basePathLength);
    }

    public JPropWriteContext createChildArrayContext(int basePathLength) {
        JPropWriteContext ctxt = this._child;
        if (ctxt == null) {
            this._child = ctxt = new JPropWriteContext(1, this, basePathLength);
            return ctxt;
        }
        ctxt.reset(1, basePathLength);
        return ctxt;
    }

    public JPropWriteContext createChildObjectContext(int basePathLength) {
        JPropWriteContext ctxt = this._child;
        if (ctxt == null) {
            this._child = ctxt = new JPropWriteContext(2, this, basePathLength);
            return ctxt;
        }
        ctxt.reset(2, basePathLength);
        return ctxt;
    }

    public boolean writeFieldName(String name) throws JsonProcessingException {
        if (this._gotName) {
            return false;
        }
        this._gotName = true;
        this._currentName = name;
        return true;
    }

    public boolean writeValue() {
        if (this._type == 2) {
            if (!this._gotName) {
                return false;
            }
            this._gotName = false;
        }
        ++this._index;
        return true;
    }

    public void truncatePath(StringBuilder sb) {
        int len = sb.length();
        if (len != this._basePathLength) {
            if (len < this._basePathLength) {
                throw new IllegalStateException(String.format("Internal error: base path length %d, buffered %d, trying to truncate", this._basePathLength, len));
            }
            sb.setLength(this._basePathLength);
        }
    }

    public final JPropWriteContext getParent() {
        return this._parent;
    }

    public String getCurrentName() {
        return this._currentName;
    }

    public Object getCurrentValue() {
        return this._currentValue;
    }

    public void setCurrentValue(Object v) {
        this._currentValue = v;
    }

    public StringBuilder appendDesc(StringBuilder sb) {
        if (this._parent != null) {
            sb = this._parent.appendDesc(sb);
            sb.append('/');
        }
        switch (this._type) {
            case 2: {
                if (this._currentName == null) break;
                sb.append(this._currentName);
                break;
            }
            case 1: {
                sb.append(this.getCurrentIndex());
                break;
            }
        }
        return sb;
    }

    public final String toString() {
        return this.appendDesc(new StringBuilder(64)).toString();
    }
}

