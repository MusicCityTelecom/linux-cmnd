/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonStreamContext
 *  com.fasterxml.jackson.core.JsonToken
 */
package com.fasterxml.jackson.dataformat.javaprop.io;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropNode;
import java.util.Iterator;
import java.util.Map;

public abstract class JPropReadContext
extends JsonStreamContext {
    protected final JPropReadContext _parent;
    protected String _currentName;
    protected String _currentText;
    protected Object _currentValue;
    protected JPropNode _nextNode;
    protected String _branchText;
    protected int _state;

    public JPropReadContext(int contextType, JPropReadContext p, JPropNode node) {
        this._type = contextType;
        this._index = -1;
        this._parent = p;
        this._branchText = node.getValue();
    }

    public static JPropReadContext create(JPropNode root) {
        if (root.isArray()) {
            return new ArrayContext(null, root);
        }
        return new ObjectContext(null, root);
    }

    public final JPropReadContext getParent() {
        return this._parent;
    }

    public final String getCurrentName() {
        return this._currentName;
    }

    public void overrideCurrentName(String name) {
        this._currentName = name;
    }

    public Object getCurrentValue() {
        return this._currentValue;
    }

    public void setCurrentValue(Object v) {
        this._currentValue = v;
    }

    public abstract JsonToken nextToken();

    public JPropReadContext nextContext() {
        JPropNode n = this._nextNode;
        if (n == null) {
            return this._parent;
        }
        this._nextNode = null;
        if (n.isArray()) {
            return new ArrayContext(this, n);
        }
        return new ObjectContext(this, n);
    }

    public String getCurrentText() {
        return this._currentText;
    }

    protected static final class ObjectContext
    extends JPropReadContext {
        static final int STATE_START = 0;
        static final int STATE_BRANCH_KEY = 1;
        static final int STATE_BRANCH_VALUE = 2;
        static final int STATE_CONTENT_KEY = 3;
        static final int STATE_CONTENT_VALUE = 4;
        static final int STATE_END = 5;
        protected Iterator<Map.Entry<String, JPropNode>> _contents;

        public ObjectContext(JPropReadContext p, JPropNode objectNode) {
            super(2, p, objectNode);
            this._contents = objectNode.objectContents();
            this._state = 0;
        }

        @Override
        public JsonToken nextToken() {
            switch (this._state) {
                case 0: {
                    this._state = this._branchText == null ? 3 : 1;
                    return JsonToken.START_OBJECT;
                }
                case 1: {
                    this._currentName = "";
                    this._state = 2;
                    return JsonToken.FIELD_NAME;
                }
                case 2: {
                    this._currentText = this._branchText;
                    this._state = 3;
                    return JsonToken.VALUE_STRING;
                }
                case 3: {
                    if (!this._contents.hasNext()) {
                        this._state = 5;
                        this._nextNode = null;
                        return JsonToken.END_OBJECT;
                    }
                    Map.Entry<String, JPropNode> entry = this._contents.next();
                    this._currentName = entry.getKey();
                    this._nextNode = entry.getValue();
                    this._state = 4;
                    return JsonToken.FIELD_NAME;
                }
                case 4: {
                    this._state = 3;
                    if (this._nextNode.isLeaf()) {
                        this._currentText = this._nextNode.getValue();
                        this._nextNode = null;
                        return JsonToken.VALUE_STRING;
                    }
                    return null;
                }
            }
            return null;
        }
    }

    protected static final class ArrayContext
    extends JPropReadContext {
        static final int STATE_START = 0;
        static final int STATE_BRANCH_VALUE = 1;
        static final int STATE_CONTENT_VALUE = 2;
        static final int STATE_END = 3;
        protected Iterator<JPropNode> _contents;

        public ArrayContext(JPropReadContext p, JPropNode arrayNode) {
            super(1, p, arrayNode);
            this._contents = arrayNode.arrayContents();
            this._state = 0;
        }

        @Override
        public JsonToken nextToken() {
            switch (this._state) {
                case 0: {
                    this._state = this._branchText == null ? 2 : 1;
                    return JsonToken.START_ARRAY;
                }
                case 1: {
                    this._state = 2;
                    this._currentText = this._branchText;
                    return JsonToken.VALUE_STRING;
                }
                case 2: {
                    if (!this._contents.hasNext()) {
                        this._state = 3;
                        return JsonToken.END_ARRAY;
                    }
                    JPropNode n = this._contents.next();
                    if (n.isLeaf()) {
                        this._currentText = n.getValue();
                        return JsonToken.VALUE_STRING;
                    }
                    this._nextNode = n;
                    return null;
                }
            }
            return null;
        }
    }
}

