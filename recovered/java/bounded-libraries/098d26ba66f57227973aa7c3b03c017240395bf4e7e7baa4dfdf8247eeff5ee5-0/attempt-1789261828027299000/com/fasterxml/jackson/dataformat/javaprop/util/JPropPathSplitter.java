/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.dataformat.javaprop.util;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropNode;
import com.fasterxml.jackson.dataformat.javaprop.util.Markers;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class JPropPathSplitter {
    protected final boolean _useSimpleIndex;

    protected JPropPathSplitter(boolean useSimpleIndex) {
        this._useSimpleIndex = useSimpleIndex;
    }

    public static JPropPathSplitter create(JavaPropsSchema schema) {
        String sep = schema.pathSeparator();
        Markers indexMarker = schema.indexMarker();
        if (indexMarker == null) {
            return JPropPathSplitter.pathOnlySplitter(schema);
        }
        if (sep.isEmpty()) {
            return new IndexOnlySplitter(schema.parseSimpleIndexes(), indexMarker);
        }
        return new FullSplitter(sep, schema.parseSimpleIndexes(), indexMarker, JPropPathSplitter.pathOnlySplitter(schema), schema.prefix());
    }

    private static JPropPathSplitter pathOnlySplitter(JavaPropsSchema schema) {
        String sep = schema.pathSeparator();
        if (sep.isEmpty()) {
            return NonSplitting.instance;
        }
        if (sep.length() == 1) {
            return new CharPathOnlySplitter(sep.charAt(0), schema.parseSimpleIndexes());
        }
        return new StringPathOnlySplitter(sep, schema.parseSimpleIndexes());
    }

    public abstract JPropNode splitAndAdd(JPropNode var1, String var2, String var3);

    protected JPropNode _addSegment(JPropNode parent, String segment) {
        int ix;
        if (this._useSimpleIndex && (ix = this._asInt(segment)) >= 0) {
            return parent.addByIndex(ix);
        }
        return parent.addByName(segment);
    }

    protected JPropNode _lastSegment(JPropNode parent, String path, int start, int end) {
        if (start < end) {
            if (start > 0) {
                path = path.substring(start);
            }
            parent = this._addSegment(parent, path);
        }
        return parent;
    }

    protected int _asInt(String segment) {
        int len = segment.length();
        if (len == 0 || len > 9) {
            return -1;
        }
        char c = segment.charAt(0);
        if (c > '9' || c < '0') {
            return -1;
        }
        for (int i = 0; i < len; ++i) {
            c = segment.charAt(i);
            if (c <= '9' && c >= '0') continue;
            return -1;
        }
        return Integer.parseInt(segment);
    }

    public static class FullSplitter
    extends JPropPathSplitter {
        protected final Pattern _indexMatch;
        protected final int _indexFirstChar;
        protected final JPropPathSplitter _simpleSplitter;
        protected final String _prefix;

        public FullSplitter(String pathSeparator, boolean useSimpleIndex, Markers indexMarker, JPropPathSplitter fallbackSplitter, String prefix) {
            super(useSimpleIndex);
            String startMarker = indexMarker.getStart();
            this._indexFirstChar = startMarker.charAt(0);
            this._simpleSplitter = fallbackSplitter;
            this._prefix = prefix == null || prefix.isEmpty() ? null : prefix + pathSeparator;
            this._indexMatch = Pattern.compile(String.format("(%s)|(%s(\\d{1,9})%s)", Pattern.quote(pathSeparator), Pattern.quote(startMarker), Pattern.quote(indexMarker.getEnd())));
        }

        @Override
        public JPropNode splitAndAdd(JPropNode parent, String key, String value) {
            if (this._prefix != null) {
                if (!key.startsWith(this._prefix)) {
                    return null;
                }
                key = key.substring(this._prefix.length());
            }
            if (key.indexOf(this._indexFirstChar) < 0) {
                return this._simpleSplitter.splitAndAdd(parent, key, value);
            }
            Matcher m = this._indexMatch.matcher(key);
            int start = 0;
            while (m.find()) {
                String segment;
                int ix = m.start(1);
                if (ix >= 0) {
                    if (ix > start) {
                        segment = key.substring(start, ix);
                        parent = this._addSegment(parent, segment);
                    }
                    start = m.end(1);
                    continue;
                }
                ix = m.start(2);
                if (ix > start) {
                    segment = key.substring(start, ix);
                    parent = this._addSegment(parent, segment);
                }
                start = m.end(2);
                ix = Integer.parseInt(m.group(3));
                parent = parent.addByIndex(ix);
            }
            return this._lastSegment(parent, key, start, key.length()).setValue(value);
        }
    }

    public static class IndexOnlySplitter
    extends JPropPathSplitter {
        protected final Pattern _indexMatch;

        public IndexOnlySplitter(boolean useSimpleIndex, Markers indexMarker) {
            super(useSimpleIndex);
            this._indexMatch = Pattern.compile(String.format("(.*)%s(\\d{1,9})%s$", Pattern.quote(indexMarker.getStart()), Pattern.quote(indexMarker.getEnd())));
        }

        @Override
        public JPropNode splitAndAdd(JPropNode parent, String key, String value) {
            Matcher m = this._indexMatch.matcher(key);
            if (!m.matches()) {
                return this._addSegment(parent, key).setValue(value);
            }
            return this._splitMore(parent, m.group(1), m.group(2)).setValue(value);
        }

        protected JPropNode _splitMore(JPropNode parent, String prefix, String indexStr) {
            int ix = Integer.parseInt(indexStr);
            Matcher m = this._indexMatch.matcher(prefix);
            parent = !m.matches() ? this._addSegment(parent, prefix) : this._splitMore(parent, m.group(1), m.group(2));
            return parent.addByIndex(ix);
        }
    }

    public static class StringPathOnlySplitter
    extends JPropPathSplitter {
        protected final String _pathSeparator;
        protected final int _pathSeparatorLength;

        public StringPathOnlySplitter(String pathSeparator, boolean useIndex) {
            super(useIndex);
            this._pathSeparator = pathSeparator;
            this._pathSeparatorLength = pathSeparator.length();
        }

        @Override
        public JPropNode splitAndAdd(JPropNode parent, String key, String value) {
            int ix;
            JPropNode curr = parent;
            int start = 0;
            int keyLen = key.length();
            while ((ix = key.indexOf(this._pathSeparator, start)) >= start) {
                if (ix > start) {
                    String segment = key.substring(start, ix);
                    curr = this._addSegment(curr, segment);
                }
                if ((start = ix + this._pathSeparatorLength) != key.length()) continue;
                break;
            }
            return this._lastSegment(curr, key, start, keyLen).setValue(value);
        }
    }

    public static class CharPathOnlySplitter
    extends JPropPathSplitter {
        protected final char _pathSeparatorChar;

        public CharPathOnlySplitter(char sepChar, boolean useIndex) {
            super(useIndex);
            this._pathSeparatorChar = sepChar;
        }

        @Override
        public JPropNode splitAndAdd(JPropNode parent, String key, String value) {
            int ix;
            JPropNode curr = parent;
            int start = 0;
            int keyLen = key.length();
            while ((ix = key.indexOf(this._pathSeparatorChar, start)) >= start) {
                if (ix > start) {
                    String segment = key.substring(start, ix);
                    curr = this._addSegment(curr, segment);
                }
                if ((start = ix + 1) != key.length()) continue;
                break;
            }
            return this._lastSegment(curr, key, start, keyLen).setValue(value);
        }
    }

    public static class NonSplitting
    extends JPropPathSplitter {
        public static final NonSplitting instance = new NonSplitting();

        private NonSplitting() {
            super(false);
        }

        @Override
        public JPropNode splitAndAdd(JPropNode parent, String key, String value) {
            return parent.addByName(key).setValue(value);
        }
    }
}

