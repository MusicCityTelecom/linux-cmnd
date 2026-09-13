/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.ajax;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JSON {
    static final Logger LOG = Log.getLogger(JSON.class);
    public static final JSON DEFAULT = new JSON();
    private Map<String, Convertor> _convertors = new ConcurrentHashMap<String, Convertor>();
    private int _stringBufferSize = 1024;

    public static void reset() {
        JSON.DEFAULT._convertors.clear();
        JSON.DEFAULT._stringBufferSize = 1024;
    }

    public int getStringBufferSize() {
        return this._stringBufferSize;
    }

    public void setStringBufferSize(int stringBufferSize) {
        this._stringBufferSize = stringBufferSize;
    }

    public static void registerConvertor(Class forClass, Convertor convertor) {
        DEFAULT.addConvertor(forClass, convertor);
    }

    public static JSON getDefault() {
        return DEFAULT;
    }

    @Deprecated
    public static void setDefault(JSON json) {
    }

    public static String toString(Object object) {
        StringBuilder buffer = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.append(buffer, object);
        return buffer.toString();
    }

    public static String toString(Map object) {
        StringBuilder buffer = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.appendMap(buffer, object);
        return buffer.toString();
    }

    public static String toString(Object[] array) {
        StringBuilder buffer = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.appendArray((Appendable)buffer, (Object)array);
        return buffer.toString();
    }

    public static Object parse(String s) {
        return DEFAULT.parse(new StringSource(s), false);
    }

    public static Object parse(String s, boolean stripOuterComment) {
        return DEFAULT.parse(new StringSource(s), stripOuterComment);
    }

    public static Object parse(Reader in) throws IOException {
        return DEFAULT.parse(new ReaderSource(in), false);
    }

    public static Object parse(Reader in, boolean stripOuterComment) throws IOException {
        return DEFAULT.parse(new ReaderSource(in), stripOuterComment);
    }

    @Deprecated
    public static Object parse(InputStream in) throws IOException {
        return DEFAULT.parse(new StringSource(IO.toString(in)), false);
    }

    @Deprecated
    public static Object parse(InputStream in, boolean stripOuterComment) throws IOException {
        return DEFAULT.parse(new StringSource(IO.toString(in)), stripOuterComment);
    }

    private void quotedEscape(Appendable buffer, String input) {
        try {
            buffer.append('\"');
            if (input != null && !input.isEmpty()) {
                this.escapeString(buffer, input);
            }
            buffer.append('\"');
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escapeString(Appendable buffer, String input) throws IOException {
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (c >= ' ' && c <= '~') {
                if (c == '\"' || c == '\\') {
                    buffer.append('\\').append(c);
                    continue;
                }
                buffer.append(c);
                continue;
            }
            if (c == '\b') {
                buffer.append("\\b");
                continue;
            }
            if (c == '\f') {
                buffer.append("\\f");
                continue;
            }
            if (c == '\n') {
                buffer.append("\\n");
                continue;
            }
            if (c == '\r') {
                buffer.append("\\r");
                continue;
            }
            if (c == '\t') {
                buffer.append("\\t");
                continue;
            }
            if (c < ' ' || c == '\u007f') {
                buffer.append(String.format("\\u%04x", (short)c));
                continue;
            }
            this.escapeUnicode(buffer, c);
        }
    }

    protected void escapeUnicode(Appendable buffer, char c) throws IOException {
        buffer.append(c);
    }

    public String toJSON(Object object) {
        StringBuilder buffer = new StringBuilder(this.getStringBufferSize());
        this.append(buffer, object);
        return buffer.toString();
    }

    public Object fromJSON(String json) {
        StringSource source = new StringSource(json);
        return this.parse(source);
    }

    @Deprecated
    public void append(StringBuffer buffer, Object object) {
        this.append((Appendable)buffer, object);
    }

    public void append(Appendable buffer, Object object) {
        try {
            if (object == null) {
                buffer.append("null");
            } else if (object instanceof Map) {
                this.appendMap(buffer, (Map)object);
            } else if (object instanceof String) {
                this.appendString(buffer, (String)object);
            } else if (object instanceof Number) {
                this.appendNumber(buffer, (Number)object);
            } else if (object instanceof Boolean) {
                this.appendBoolean(buffer, (Boolean)object);
            } else if (object.getClass().isArray()) {
                this.appendArray(buffer, object);
            } else if (object instanceof Character) {
                this.appendString(buffer, object.toString());
            } else if (object instanceof Convertible) {
                this.appendJSON(buffer, (Convertible)object);
            } else if (object instanceof Generator) {
                this.appendJSON(buffer, (Generator)object);
            } else {
                Convertor convertor = this.getConvertor(object.getClass());
                if (convertor != null) {
                    this.appendJSON(buffer, convertor, object);
                } else if (object instanceof Collection) {
                    this.appendArray(buffer, (Collection)object);
                } else {
                    this.appendString(buffer, object.toString());
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendNull(StringBuffer buffer) {
        this.appendNull((Appendable)buffer);
    }

    public void appendNull(Appendable buffer) {
        try {
            buffer.append("null");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendJSON(StringBuffer buffer, Convertor convertor, Object object) {
        this.appendJSON((Appendable)buffer, convertor, object);
    }

    public void appendJSON(Appendable buffer, final Convertor convertor, final Object object) {
        this.appendJSON(buffer, new Convertible(){

            @Override
            public void fromJSON(Map object2) {
            }

            @Override
            public void toJSON(Output out) {
                convertor.toJSON(object, out);
            }
        });
    }

    @Deprecated
    public void appendJSON(StringBuffer buffer, Convertible converter) {
        this.appendJSON((Appendable)buffer, converter);
    }

    public void appendJSON(Appendable buffer, Convertible converter) {
        ConvertableOutput out = new ConvertableOutput(buffer);
        converter.toJSON(out);
        out.complete();
    }

    @Deprecated
    public void appendJSON(StringBuffer buffer, Generator generator) {
        generator.addJSON(buffer);
    }

    public void appendJSON(Appendable buffer, Generator generator) {
        generator.addJSON(buffer);
    }

    @Deprecated
    public void appendMap(StringBuffer buffer, Map<?, ?> map) {
        this.appendMap((Appendable)buffer, map);
    }

    public void appendMap(Appendable buffer, Map<?, ?> map) {
        try {
            if (map == null) {
                this.appendNull(buffer);
                return;
            }
            buffer.append('{');
            Iterator<Map.Entry<?, ?>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<?, ?> entry = iter.next();
                this.quotedEscape(buffer, entry.getKey().toString());
                buffer.append(':');
                this.append(buffer, entry.getValue());
                if (!iter.hasNext()) continue;
                buffer.append(',');
            }
            buffer.append('}');
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendArray(StringBuffer buffer, Collection collection) {
        this.appendArray((Appendable)buffer, collection);
    }

    public void appendArray(Appendable buffer, Collection collection) {
        try {
            if (collection == null) {
                this.appendNull(buffer);
                return;
            }
            buffer.append('[');
            Iterator iter = collection.iterator();
            boolean first = true;
            while (iter.hasNext()) {
                if (!first) {
                    buffer.append(',');
                }
                first = false;
                this.append(buffer, iter.next());
            }
            buffer.append(']');
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendArray(StringBuffer buffer, Object array) {
        this.appendArray((Appendable)buffer, array);
    }

    public void appendArray(Appendable buffer, Object array) {
        try {
            if (array == null) {
                this.appendNull(buffer);
                return;
            }
            buffer.append('[');
            int length = Array.getLength(array);
            for (int i = 0; i < length; ++i) {
                if (i != 0) {
                    buffer.append(',');
                }
                this.append(buffer, Array.get(array, i));
            }
            buffer.append(']');
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendBoolean(StringBuffer buffer, Boolean b) {
        this.appendBoolean((Appendable)buffer, b);
    }

    public void appendBoolean(Appendable buffer, Boolean b) {
        try {
            if (b == null) {
                this.appendNull(buffer);
                return;
            }
            buffer.append(b != false ? "true" : "false");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendNumber(StringBuffer buffer, Number number) {
        this.appendNumber((Appendable)buffer, number);
    }

    public void appendNumber(Appendable buffer, Number number) {
        try {
            if (number == null) {
                this.appendNull(buffer);
                return;
            }
            buffer.append(String.valueOf(number));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendString(StringBuffer buffer, String string) {
        this.appendString((Appendable)buffer, string);
    }

    public void appendString(Appendable buffer, String string) {
        if (string == null) {
            this.appendNull(buffer);
            return;
        }
        this.quotedEscape(buffer, string);
    }

    protected String toString(char[] buffer, int offset, int length) {
        return new String(buffer, offset, length);
    }

    protected Map<String, Object> newMap() {
        return new HashMap<String, Object>();
    }

    protected Object[] newArray(int size) {
        return new Object[size];
    }

    protected JSON contextForArray() {
        return this;
    }

    protected JSON contextFor(String field) {
        return this;
    }

    protected Object convertTo(Class type, Map map) {
        if (type != null && Convertible.class.isAssignableFrom(type)) {
            try {
                Convertible conv = (Convertible)type.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                conv.fromJSON(map);
                return conv;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Convertor convertor = this.getConvertor(type);
        if (convertor != null) {
            return convertor.fromJSON(map);
        }
        return map;
    }

    public void addConvertor(Class forClass, Convertor convertor) {
        this._convertors.put(forClass.getName(), convertor);
    }

    protected Convertor getConvertor(Class forClass) {
        Class cls = forClass;
        Convertor convertor = this._convertors.get(cls.getName());
        if (convertor == null && this != DEFAULT) {
            convertor = DEFAULT.getConvertor(cls);
        }
        while (convertor == null && cls != Object.class) {
            Class<?>[] ifs = cls.getInterfaces();
            int i = 0;
            while (convertor == null && ifs != null && i < ifs.length) {
                convertor = this._convertors.get(ifs[i++].getName());
            }
            if (convertor != null) continue;
            cls = cls.getSuperclass();
            convertor = this._convertors.get(cls.getName());
        }
        return convertor;
    }

    public void addConvertorFor(String name, Convertor convertor) {
        this._convertors.put(name, convertor);
    }

    public Convertor getConvertorFor(String name) {
        Convertor convertor = this._convertors.get(name);
        if (convertor == null && this != DEFAULT) {
            convertor = DEFAULT.getConvertorFor(name);
        }
        return convertor;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object parse(Source source, boolean stripOuterComment) {
        int commentState = 0;
        if (!stripOuterComment) {
            return this.parse(source);
        }
        int stripState = 1;
        Object o = null;
        while (true) {
            block27: {
                char c;
                block26: {
                    if (!source.hasNext()) {
                        return o;
                    }
                    c = source.peek();
                    if (commentState != true) break block26;
                    switch (c) {
                        case '/': {
                            commentState = -1;
                            break;
                        }
                        case '*': {
                            commentState = 2;
                            if (stripState == 1) {
                                commentState = 0;
                                stripState = 2;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                    break block27;
                }
                if (commentState > 1) {
                    switch (c) {
                        case '*': {
                            commentState = 3;
                            break;
                        }
                        case '/': {
                            if (commentState == 3) {
                                commentState = 0;
                                if (stripState != 2) break;
                                return o;
                            }
                            commentState = 2;
                            break;
                        }
                        default: {
                            commentState = 2;
                            break;
                        }
                    }
                } else if (commentState < 0) {
                    switch (c) {
                        case '\n': 
                        case '\r': {
                            commentState = 0;
                            break;
                        }
                    }
                } else if (!Character.isWhitespace(c)) {
                    if (c == '/') {
                        commentState = 1;
                    } else if (c == '*') {
                        commentState = 3;
                    } else if (o == null) {
                        o = this.parse(source);
                        continue;
                    }
                }
            }
            source.next();
        }
    }

    public Object parse(Source source) {
        int commentState = 0;
        while (source.hasNext()) {
            char c = source.peek();
            if (commentState == 1) {
                switch (c) {
                    case '/': {
                        commentState = -1;
                        break;
                    }
                    case '*': {
                        commentState = 2;
                    }
                }
            } else if (commentState > 1) {
                switch (c) {
                    case '*': {
                        commentState = 3;
                        break;
                    }
                    case '/': {
                        if (commentState == 3) {
                            commentState = 0;
                            break;
                        }
                        commentState = 2;
                        break;
                    }
                    default: {
                        commentState = 2;
                        break;
                    }
                }
            } else if (commentState < 0) {
                switch (c) {
                    case '\n': 
                    case '\r': {
                        commentState = 0;
                        break;
                    }
                }
            } else {
                switch (c) {
                    case '{': {
                        return this.parseObject(source);
                    }
                    case '[': {
                        return this.parseArray(source);
                    }
                    case '\"': {
                        return this.parseString(source);
                    }
                    case '-': {
                        return this.parseNumber(source);
                    }
                    case 'n': {
                        JSON.complete("null", source);
                        return null;
                    }
                    case 't': {
                        JSON.complete("true", source);
                        return Boolean.TRUE;
                    }
                    case 'f': {
                        JSON.complete("false", source);
                        return Boolean.FALSE;
                    }
                    case 'u': {
                        JSON.complete("undefined", source);
                        return null;
                    }
                    case 'N': {
                        JSON.complete("NaN", source);
                        return null;
                    }
                    case '/': {
                        commentState = 1;
                        break;
                    }
                    default: {
                        if (Character.isDigit(c)) {
                            return this.parseNumber(source);
                        }
                        if (Character.isWhitespace(c)) break;
                        return this.handleUnknown(source, c);
                    }
                }
            }
            source.next();
        }
        return null;
    }

    protected Object handleUnknown(Source source, char c) {
        throw new IllegalStateException("unknown char '" + c + "'(" + c + ") in " + source);
    }

    protected Object parseObject(Source source) {
        String classname;
        String xclassname;
        if (source.next() != '{') {
            throw new IllegalStateException();
        }
        Map<String, Object> map = this.newMap();
        char next = this.seekTo("\"}", source);
        while (source.hasNext()) {
            if (next == '}') {
                source.next();
                break;
            }
            String name = this.parseString(source);
            this.seekTo(':', source);
            source.next();
            Object value = this.contextFor(name).parse(source);
            map.put(name, value);
            this.seekTo(",}", source);
            next = source.next();
            if (next == '}') break;
            next = this.seekTo("\"}", source);
        }
        if ((xclassname = (String)map.get("x-class")) != null) {
            Convertor c = this.getConvertorFor(xclassname);
            if (c != null) {
                return c.fromJSON(map);
            }
            LOG.warn("No Convertor for x-class '{}'", xclassname);
        }
        if ((classname = (String)map.get("class")) != null) {
            try {
                Class c = Loader.loadClass(classname);
                return this.convertTo(c, map);
            }
            catch (ClassNotFoundException e) {
                LOG.warn("No Class for '{}'", classname);
            }
        }
        return map;
    }

    protected Object parseArray(Source source) {
        if (source.next() != '[') {
            throw new IllegalStateException();
        }
        int size = 0;
        ArrayList<Object> list = null;
        Object item = null;
        boolean coma = true;
        block8: while (source.hasNext()) {
            char c = source.peek();
            switch (c) {
                case ']': {
                    source.next();
                    switch (size) {
                        case 0: {
                            return this.newArray(0);
                        }
                        case 1: {
                            Object[] array = this.newArray(1);
                            Array.set(array, 0, item);
                            return array;
                        }
                    }
                    return list.toArray(this.newArray(list.size()));
                }
                case ',': {
                    if (coma) {
                        throw new IllegalStateException();
                    }
                    coma = true;
                    source.next();
                    continue block8;
                }
            }
            if (Character.isWhitespace(c)) {
                source.next();
                continue;
            }
            coma = false;
            if (size++ == 0) {
                item = this.contextForArray().parse(source);
                continue;
            }
            if (list == null) {
                list = new ArrayList<Object>();
                list.add(item);
                item = this.contextForArray().parse(source);
                list.add(item);
                item = null;
                continue;
            }
            item = this.contextForArray().parse(source);
            list.add(item);
            item = null;
        }
        throw new IllegalStateException("unexpected end of array");
    }

    protected String parseString(Source source) {
        char uc;
        char c;
        if (source.next() != '\"') {
            throw new IllegalStateException();
        }
        boolean escape = false;
        StringBuilder b = null;
        char[] scratch = source.scratchBuffer();
        if (scratch != null) {
            int i = 0;
            block22: while (source.hasNext()) {
                if (i >= scratch.length) {
                    b = new StringBuilder(scratch.length * 2);
                    b.append(scratch, 0, i);
                    break;
                }
                c = source.next();
                if (escape) {
                    escape = false;
                    switch (c) {
                        case '\"': {
                            scratch[i++] = 34;
                            continue block22;
                        }
                        case '\\': {
                            scratch[i++] = 92;
                            continue block22;
                        }
                        case '/': {
                            scratch[i++] = 47;
                            continue block22;
                        }
                        case 'b': {
                            scratch[i++] = 8;
                            continue block22;
                        }
                        case 'f': {
                            scratch[i++] = 12;
                            continue block22;
                        }
                        case 'n': {
                            scratch[i++] = 10;
                            continue block22;
                        }
                        case 'r': {
                            scratch[i++] = 13;
                            continue block22;
                        }
                        case 't': {
                            scratch[i++] = 9;
                            continue block22;
                        }
                        case 'u': {
                            uc = (char)((TypeUtil.convertHexDigit((byte)source.next()) << 12) + (TypeUtil.convertHexDigit((byte)source.next()) << 8) + (TypeUtil.convertHexDigit((byte)source.next()) << 4) + TypeUtil.convertHexDigit((byte)source.next()));
                            scratch[i++] = uc;
                            continue block22;
                        }
                    }
                    scratch[i++] = c;
                    continue;
                }
                if (c == '\\') {
                    escape = true;
                    continue;
                }
                if (c == '\"') {
                    return this.toString(scratch, 0, i);
                }
                scratch[i++] = c;
            }
            if (b == null) {
                return this.toString(scratch, 0, i);
            }
        } else {
            b = new StringBuilder(this.getStringBufferSize());
        }
        StringBuilder builder = b;
        block23: while (source.hasNext()) {
            c = source.next();
            if (escape) {
                escape = false;
                switch (c) {
                    case '\"': {
                        builder.append('\"');
                        continue block23;
                    }
                    case '\\': {
                        builder.append('\\');
                        continue block23;
                    }
                    case '/': {
                        builder.append('/');
                        continue block23;
                    }
                    case 'b': {
                        builder.append('\b');
                        continue block23;
                    }
                    case 'f': {
                        builder.append('\f');
                        continue block23;
                    }
                    case 'n': {
                        builder.append('\n');
                        continue block23;
                    }
                    case 'r': {
                        builder.append('\r');
                        continue block23;
                    }
                    case 't': {
                        builder.append('\t');
                        continue block23;
                    }
                    case 'u': {
                        uc = (char)((TypeUtil.convertHexDigit((byte)source.next()) << 12) + (TypeUtil.convertHexDigit((byte)source.next()) << 8) + (TypeUtil.convertHexDigit((byte)source.next()) << 4) + TypeUtil.convertHexDigit((byte)source.next()));
                        builder.append(uc);
                        continue block23;
                    }
                }
                builder.append(c);
                continue;
            }
            if (c == '\\') {
                escape = true;
                continue;
            }
            if (c == '\"') break;
            builder.append(c);
        }
        return builder.toString();
    }

    public Number parseNumber(Source source) {
        char c;
        boolean minus = false;
        long number = 0L;
        StringBuilder buffer = null;
        block8: while (source.hasNext()) {
            c = source.peek();
            switch (c) {
                case '0': 
                case '1': 
                case '2': 
                case '3': 
                case '4': 
                case '5': 
                case '6': 
                case '7': 
                case '8': 
                case '9': {
                    number = number * 10L + (long)(c - 48);
                    source.next();
                    continue block8;
                }
                case '+': 
                case '-': {
                    if (number != 0L) {
                        throw new IllegalStateException("bad number");
                    }
                    minus = true;
                    source.next();
                    continue block8;
                }
                case '.': 
                case 'E': 
                case 'e': {
                    buffer = new StringBuilder(16);
                    if (minus) {
                        buffer.append('-');
                    }
                    buffer.append(number);
                    buffer.append(c);
                    source.next();
                    break;
                }
            }
            break;
        }
        if (buffer == null) {
            return minus ? -1L * number : number;
        }
        block9: while (source.hasNext()) {
            c = source.peek();
            switch (c) {
                case '+': 
                case '-': 
                case '.': 
                case '0': 
                case '1': 
                case '2': 
                case '3': 
                case '4': 
                case '5': 
                case '6': 
                case '7': 
                case '8': 
                case '9': 
                case 'E': 
                case 'e': {
                    buffer.append(c);
                    source.next();
                    continue block9;
                }
            }
            break;
        }
        return new Double(buffer.toString());
    }

    protected void seekTo(char seek, Source source) {
        while (source.hasNext()) {
            char c = source.peek();
            if (c == seek) {
                return;
            }
            if (!Character.isWhitespace(c)) {
                throw new IllegalStateException("Unexpected '" + c + " while seeking '" + seek + "'");
            }
            source.next();
        }
        throw new IllegalStateException("Expected '" + seek + "'");
    }

    protected char seekTo(String seek, Source source) {
        while (source.hasNext()) {
            char c = source.peek();
            if (seek.indexOf(c) >= 0) {
                return c;
            }
            if (!Character.isWhitespace(c)) {
                throw new IllegalStateException("Unexpected '" + c + "' while seeking one of '" + seek + "'");
            }
            source.next();
        }
        throw new IllegalStateException("Expected one of '" + seek + "'");
    }

    protected static void complete(String seek, Source source) {
        int i = 0;
        while (source.hasNext() && i < seek.length()) {
            char c = source.next();
            if (c == seek.charAt(i++)) continue;
            throw new IllegalStateException("Unexpected '" + c + " while seeking  \"" + seek + "\"");
        }
        if (i < seek.length()) {
            throw new IllegalStateException("Expected \"" + seek + "\"");
        }
    }

    public static class Literal
    implements Generator {
        private String _json;

        public Literal(String json) {
            if (LOG.isDebugEnabled()) {
                JSON.parse(json);
            }
            this._json = json;
        }

        public String toString() {
            return this._json;
        }

        @Override
        public void addJSON(Appendable buffer) {
            try {
                buffer.append(this._json);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static interface Generator {
        public void addJSON(Appendable var1);
    }

    public static interface Convertor {
        public void toJSON(Object var1, Output var2);

        public Object fromJSON(Map var1);
    }

    public static interface Convertible {
        public void toJSON(Output var1);

        public void fromJSON(Map var1);
    }

    public static interface Output {
        public void addClass(Class var1);

        public void add(Object var1);

        public void add(String var1, Object var2);

        public void add(String var1, double var2);

        public void add(String var1, long var2);

        public void add(String var1, boolean var2);
    }

    public static class ReaderSource
    implements Source {
        private Reader _reader;
        private int _next = -1;
        private char[] scratch;

        public ReaderSource(Reader r) {
            this._reader = r;
        }

        public void setReader(Reader reader) {
            this._reader = reader;
            this._next = -1;
        }

        @Override
        public boolean hasNext() {
            this.getNext();
            if (this._next < 0) {
                this.scratch = null;
                return false;
            }
            return true;
        }

        @Override
        public char next() {
            this.getNext();
            char c = (char)this._next;
            this._next = -1;
            return c;
        }

        @Override
        public char peek() {
            this.getNext();
            return (char)this._next;
        }

        private void getNext() {
            if (this._next < 0) {
                try {
                    this._next = this._reader.read();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[1024];
            }
            return this.scratch;
        }
    }

    public static class StringSource
    implements Source {
        private final String string;
        private int index;
        private char[] scratch;

        public StringSource(String s) {
            this.string = s;
        }

        @Override
        public boolean hasNext() {
            if (this.index < this.string.length()) {
                return true;
            }
            this.scratch = null;
            return false;
        }

        @Override
        public char next() {
            return this.string.charAt(this.index++);
        }

        @Override
        public char peek() {
            return this.string.charAt(this.index);
        }

        public String toString() {
            return this.string.substring(0, this.index) + "|||" + this.string.substring(this.index);
        }

        @Override
        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[this.string.length()];
            }
            return this.scratch;
        }
    }

    public static interface Source {
        public boolean hasNext();

        public char next();

        public char peek();

        public char[] scratchBuffer();
    }

    private final class ConvertableOutput
    implements Output {
        private final Appendable _buffer;
        char c = (char)123;

        private ConvertableOutput(Appendable buffer) {
            this._buffer = buffer;
        }

        public void complete() {
            try {
                if (this.c == '{') {
                    this._buffer.append("{}");
                } else if (this.c != '\u0000') {
                    this._buffer.append("}");
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void add(Object obj) {
            if (this.c == '\u0000') {
                throw new IllegalStateException();
            }
            JSON.this.append(this._buffer, obj);
            this.c = '\u0000';
        }

        @Override
        public void addClass(Class type) {
            try {
                if (this.c == '\u0000') {
                    throw new IllegalStateException();
                }
                this._buffer.append(this.c);
                this._buffer.append("\"class\":");
                JSON.this.append(this._buffer, (Object)type.getName());
                this.c = (char)44;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void add(String name, Object value) {
            try {
                if (this.c == '\u0000') {
                    throw new IllegalStateException();
                }
                this._buffer.append(this.c);
                JSON.this.quotedEscape(this._buffer, name);
                this._buffer.append(':');
                JSON.this.append(this._buffer, value);
                this.c = (char)44;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void add(String name, double value) {
            try {
                if (this.c == '\u0000') {
                    throw new IllegalStateException();
                }
                this._buffer.append(this.c);
                JSON.this.quotedEscape(this._buffer, name);
                this._buffer.append(':');
                JSON.this.appendNumber(this._buffer, (Number)value);
                this.c = (char)44;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void add(String name, long value) {
            try {
                if (this.c == '\u0000') {
                    throw new IllegalStateException();
                }
                this._buffer.append(this.c);
                JSON.this.quotedEscape(this._buffer, name);
                this._buffer.append(':');
                JSON.this.appendNumber(this._buffer, (Number)value);
                this.c = (char)44;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void add(String name, boolean value) {
            try {
                if (this.c == '\u0000') {
                    throw new IllegalStateException();
                }
                this._buffer.append(this.c);
                JSON.this.quotedEscape(this._buffer, name);
                this._buffer.append(':');
                JSON.this.appendBoolean(this._buffer, value ? Boolean.TRUE : Boolean.FALSE);
                this.c = (char)44;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

