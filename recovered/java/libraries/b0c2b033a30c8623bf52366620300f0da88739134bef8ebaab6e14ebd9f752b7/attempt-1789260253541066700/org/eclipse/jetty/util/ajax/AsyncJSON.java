/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.ajax;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.util.ArrayTernaryTrie;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.Trie;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.Utf8StringBuilder;
import org.eclipse.jetty.util.ajax.JSON;

public class AsyncJSON {
    private static final Object UNSET = new Object();
    private final FrameStack stack = new FrameStack();
    private final NumberBuilder numberBuilder = new NumberBuilder();
    private final Utf8StringBuilder stringBuilder = new Utf8StringBuilder(32);
    private final Factory factory;
    private List<ByteBuffer> chunks;

    public AsyncJSON(Factory factory) {
        this.factory = factory;
    }

    boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public boolean parse(byte[] bytes) {
        return this.parse(bytes, 0, bytes.length);
    }

    public boolean parse(byte[] bytes, int offset, int length) {
        return this.parse(ByteBuffer.wrap(bytes, offset, length));
    }

    public boolean parse(ByteBuffer buffer) {
        try {
            if (this.factory.isDetailedParseException()) {
                if (this.chunks == null) {
                    this.chunks = new ArrayList<ByteBuffer>();
                }
                ByteBuffer copy = buffer.isDirect() ? ByteBuffer.allocateDirect(buffer.remaining()) : ByteBuffer.allocate(buffer.remaining());
                copy.put(buffer).flip();
                this.chunks.add(copy);
                buffer.flip();
            }
            if (this.stack.isEmpty()) {
                this.stack.push(State.COMPLETE, AsyncJSON.UNSET);
            }
            block17: while (true) {
                Frame frame = this.stack.peek();
                State state = frame.state;
                switch (state) {
                    case COMPLETE: {
                        if (frame.value == UNSET) {
                            if (this.parseAny(buffer)) continue block17;
                            return false;
                        }
                        while (buffer.hasRemaining()) {
                            int position = buffer.position();
                            byte peek = buffer.get(position);
                            if (AsyncJSON.isWhitespace(peek)) {
                                buffer.position(position + 1);
                                continue;
                            }
                            throw this.newInvalidJSON(buffer, "invalid character after JSON data");
                        }
                        return true;
                    }
                    case NULL: {
                        if (this.parseNull(buffer)) continue block17;
                        return false;
                    }
                    case TRUE: {
                        if (this.parseTrue(buffer)) continue block17;
                        return false;
                    }
                    case FALSE: {
                        if (this.parseFalse(buffer)) continue block17;
                        return false;
                    }
                    case NUMBER: {
                        if (this.parseNumber(buffer)) continue block17;
                        return false;
                    }
                    case STRING: {
                        if (this.parseString(buffer)) continue block17;
                        return false;
                    }
                    case ESCAPE: {
                        if (this.parseEscape(buffer)) continue block17;
                        return false;
                    }
                    case UNICODE: {
                        if (this.parseUnicode(buffer)) continue block17;
                        return false;
                    }
                    case ARRAY: {
                        if (this.parseArray(buffer)) continue block17;
                        return false;
                    }
                    case OBJECT: {
                        if (this.parseObject(buffer)) continue block17;
                        return false;
                    }
                    case OBJECT_FIELD: {
                        if (this.parseObjectField(buffer)) continue block17;
                        return false;
                    }
                    case OBJECT_FIELD_NAME: {
                        if (this.parseObjectFieldName(buffer)) continue block17;
                        return false;
                    }
                    case OBJECT_FIELD_VALUE: {
                        if (this.parseObjectFieldValue(buffer)) continue block17;
                        return false;
                    }
                    default: {
                        throw new IllegalStateException("invalid state " + (Object)((Object)state));
                    }
                }
            }
        }
        catch (Throwable x) {
            this.reset();
            throw x;
        }
    }

    public <R> R complete() {
        try {
            if (this.stack.isEmpty()) {
                throw new IllegalStateException("no JSON parsed");
            }
            block6: while (true) {
                State state = this.stack.peek().state;
                switch (state) {
                    case NUMBER: {
                        Number value = this.numberBuilder.value();
                        this.stack.pop();
                        this.stack.peek().value(value);
                        continue block6;
                    }
                    case COMPLETE: {
                        if (this.stack.peek().value == UNSET) {
                            throw new IllegalStateException("invalid state " + (Object)((Object)state));
                        }
                        return (R)this.end();
                    }
                }
                break;
            }
            throw this.newInvalidJSON(BufferUtil.EMPTY_BUFFER, "incomplete JSON");
        }
        catch (Throwable x) {
            this.reset();
            throw x;
        }
    }

    protected Map<String, Object> newObject(Context context) {
        return new HashMap<String, Object>();
    }

    protected List<Object> newArray(Context context) {
        return new ArrayList<Object>();
    }

    private Object end() {
        Object result = this.stack.peek().value;
        this.reset();
        return result;
    }

    private void reset() {
        this.stack.clear();
        this.chunks = null;
    }

    private boolean parseAny(ByteBuffer buffer) {
        block9: while (buffer.hasRemaining()) {
            byte peek = buffer.get(buffer.position());
            switch (peek) {
                case 91: {
                    if (!this.parseArray(buffer)) continue block9;
                    return true;
                }
                case 123: {
                    if (!this.parseObject(buffer)) continue block9;
                    return true;
                }
                case 45: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    if (!this.parseNumber(buffer)) continue block9;
                    return true;
                }
                case 34: {
                    if (!this.parseString(buffer)) continue block9;
                    return true;
                }
                case 102: {
                    if (!this.parseFalse(buffer)) continue block9;
                    return true;
                }
                case 110: {
                    if (!this.parseNull(buffer)) continue block9;
                    return true;
                }
                case 116: {
                    if (!this.parseTrue(buffer)) continue block9;
                    return true;
                }
            }
            if (AsyncJSON.isWhitespace(peek)) {
                buffer.get();
                continue;
            }
            throw this.newInvalidJSON(buffer, "unrecognized JSON value");
        }
        return false;
    }

    private boolean parseNull(ByteBuffer buffer) {
        block5: while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            switch (currentByte) {
                case 110: {
                    if (this.stack.peek().state != State.NULL) {
                        this.stack.push(State.NULL, 0);
                        this.parseNullCharacter(buffer, 0);
                        continue block5;
                    }
                    throw this.newInvalidJSON(buffer, "invalid 'null' literal");
                }
                case 117: {
                    this.parseNullCharacter(buffer, 1);
                    continue block5;
                }
                case 108: {
                    int index = (Integer)this.stack.peek().value;
                    if (index != 2 && index != 3) {
                        throw this.newInvalidJSON(buffer, "invalid 'null' literal");
                    }
                    this.parseNullCharacter(buffer, index);
                    if (index != 3) continue block5;
                    this.stack.pop();
                    this.stack.peek().value(null);
                    return true;
                }
            }
            throw this.newInvalidJSON(buffer, "invalid 'null' literal");
        }
        return false;
    }

    private void parseNullCharacter(ByteBuffer buffer, int index) {
        Frame frame = this.stack.peek();
        int value = (Integer)frame.value;
        if (value != index) {
            throw this.newInvalidJSON(buffer, "invalid 'null' literal");
        }
        frame.value = ++value;
    }

    private boolean parseTrue(ByteBuffer buffer) {
        block6: while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            switch (currentByte) {
                case 116: {
                    if (this.stack.peek().state != State.TRUE) {
                        this.stack.push(State.TRUE, 0);
                        this.parseTrueCharacter(buffer, 0);
                        continue block6;
                    }
                    throw this.newInvalidJSON(buffer, "invalid 'true' literal");
                }
                case 114: {
                    this.parseTrueCharacter(buffer, 1);
                    continue block6;
                }
                case 117: {
                    this.parseTrueCharacter(buffer, 2);
                    continue block6;
                }
                case 101: {
                    this.parseTrueCharacter(buffer, 3);
                    this.stack.pop();
                    this.stack.peek().value(Boolean.TRUE);
                    return true;
                }
            }
            throw this.newInvalidJSON(buffer, "invalid 'true' literal");
        }
        return false;
    }

    private void parseTrueCharacter(ByteBuffer buffer, int index) {
        Frame frame = this.stack.peek();
        int value = (Integer)frame.value;
        if (value != index) {
            throw this.newInvalidJSON(buffer, "invalid 'true' literal");
        }
        frame.value = ++value;
    }

    private boolean parseFalse(ByteBuffer buffer) {
        block7: while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            switch (currentByte) {
                case 102: {
                    if (this.stack.peek().state != State.FALSE) {
                        this.stack.push(State.FALSE, 0);
                        this.parseFalseCharacter(buffer, 0);
                        continue block7;
                    }
                    throw this.newInvalidJSON(buffer, "invalid 'false' literal");
                }
                case 97: {
                    this.parseFalseCharacter(buffer, 1);
                    continue block7;
                }
                case 108: {
                    this.parseFalseCharacter(buffer, 2);
                    continue block7;
                }
                case 115: {
                    this.parseFalseCharacter(buffer, 3);
                    continue block7;
                }
                case 101: {
                    this.parseFalseCharacter(buffer, 4);
                    this.stack.pop();
                    this.stack.peek().value(Boolean.FALSE);
                    return true;
                }
            }
            throw this.newInvalidJSON(buffer, "invalid 'false' literal");
        }
        return false;
    }

    private void parseFalseCharacter(ByteBuffer buffer, int index) {
        Frame frame = this.stack.peek();
        int value = (Integer)frame.value;
        if (value != index) {
            throw this.newInvalidJSON(buffer, "invalid 'false' literal");
        }
        frame.value = ++value;
    }

    private boolean parseNumber(ByteBuffer buffer) {
        if (this.stack.peek().state != State.NUMBER) {
            this.stack.push(State.NUMBER, this.numberBuilder);
        }
        while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            switch (currentByte) {
                case 43: 
                case 45: {
                    if (this.numberBuilder.appendSign(currentByte)) break;
                    throw this.newInvalidJSON(buffer, "invalid number");
                }
                case 46: 
                case 69: 
                case 101: {
                    if (this.numberBuilder.appendAlpha(currentByte)) break;
                    throw this.newInvalidJSON(buffer, "invalid number");
                }
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: {
                    this.numberBuilder.appendDigit(currentByte);
                    break;
                }
                default: {
                    buffer.position(buffer.position() - 1);
                    Number value = this.numberBuilder.value();
                    this.stack.pop();
                    this.stack.peek().value(value);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean parseString(ByteBuffer buffer) {
        String result;
        Frame frame = this.stack.peek();
        if (buffer.hasRemaining() && frame.state != State.STRING && (result = this.factory.cached(buffer)) != null) {
            frame.value(result);
            return true;
        }
        while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            switch (currentByte) {
                case 34: {
                    if (this.stack.peek().state != State.STRING) {
                        this.stack.push(State.STRING, this.stringBuilder);
                        break;
                    }
                    String string = this.stringBuilder.toString();
                    this.stringBuilder.reset();
                    this.stack.pop();
                    this.stack.peek().value(string);
                    return true;
                }
                case 92: {
                    buffer.position(buffer.position() - 1);
                    if (this.parseEscape(buffer)) break;
                    return false;
                }
                default: {
                    this.stringBuilder.append(currentByte);
                }
            }
        }
        return false;
    }

    private boolean parseEscape(ByteBuffer buffer) {
        block10: while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            switch (currentByte) {
                case 92: {
                    if (this.stack.peek().state != State.ESCAPE) {
                        this.stack.push(State.ESCAPE, this.stringBuilder);
                        continue block10;
                    }
                    return this.parseEscapeCharacter((char)currentByte);
                }
                case 34: 
                case 47: {
                    return this.parseEscapeCharacter((char)currentByte);
                }
                case 98: {
                    return this.parseEscapeCharacter('\b');
                }
                case 102: {
                    return this.parseEscapeCharacter('\f');
                }
                case 110: {
                    return this.parseEscapeCharacter('\n');
                }
                case 114: {
                    return this.parseEscapeCharacter('\r');
                }
                case 116: {
                    return this.parseEscapeCharacter('\t');
                }
                case 117: {
                    this.stack.push(State.UNICODE, ByteBuffer.allocate(4));
                    return this.parseUnicode(buffer);
                }
            }
            throw this.newInvalidJSON(buffer, "invalid escape sequence");
        }
        return false;
    }

    private boolean parseEscapeCharacter(char escape) {
        this.stack.pop();
        this.stringBuilder.append(escape);
        return true;
    }

    private boolean parseUnicode(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            ByteBuffer hex = (ByteBuffer)this.stack.peek().value;
            hex.put(this.hexToByte(buffer, currentByte));
            if (hex.hasRemaining()) continue;
            int result = (hex.get(0) << 12) + (hex.get(1) << 8) + (hex.get(2) << 4) + hex.get(3);
            this.stack.pop();
            this.stack.pop();
            this.stringBuilder.append((char)result);
            return true;
        }
        return false;
    }

    private byte hexToByte(ByteBuffer buffer, byte currentByte) {
        try {
            return TypeUtil.convertHexDigit(currentByte);
        }
        catch (Throwable x) {
            throw this.newInvalidJSON(buffer, "invalid hex digit");
        }
    }

    private boolean parseArray(ByteBuffer buffer) {
        block5: while (buffer.hasRemaining()) {
            byte peek = buffer.get(buffer.position());
            switch (peek) {
                case 91: {
                    buffer.get();
                    this.stack.push(State.ARRAY, this.newArray(this.stack));
                    continue block5;
                }
                case 93: {
                    buffer.get();
                    Object array = this.stack.peek().value;
                    this.stack.pop();
                    this.stack.peek().value(array);
                    return true;
                }
                case 44: {
                    buffer.get();
                    continue block5;
                }
            }
            if (AsyncJSON.isWhitespace(peek)) {
                buffer.get();
                continue;
            }
            if (this.parseAny(buffer)) continue;
            return false;
        }
        return false;
    }

    private boolean parseObject(ByteBuffer buffer) {
        block5: while (buffer.hasRemaining()) {
            byte currentByte = buffer.get();
            switch (currentByte) {
                case 123: {
                    if (this.stack.peek().state != State.OBJECT) {
                        this.stack.push(State.OBJECT, this.newObject(this.stack));
                        continue block5;
                    }
                    throw this.newInvalidJSON(buffer, "invalid object");
                }
                case 125: {
                    Map object = (Map)this.stack.peek().value;
                    this.stack.pop();
                    this.stack.peek().value(this.convertObject(object));
                    return true;
                }
                case 44: {
                    continue block5;
                }
            }
            if (AsyncJSON.isWhitespace(currentByte)) continue;
            buffer.position(buffer.position() - 1);
            if (this.parseObjectField(buffer)) continue;
            return false;
        }
        return false;
    }

    private boolean parseObjectField(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            byte peek = buffer.get(buffer.position());
            switch (peek) {
                case 34: {
                    if (this.stack.peek().state == State.OBJECT) {
                        this.stack.push(State.OBJECT_FIELD, AsyncJSON.UNSET);
                        if (this.parseObjectFieldName(buffer)) break;
                        return false;
                    }
                    return this.parseObjectFieldValue(buffer);
                }
                default: {
                    if (AsyncJSON.isWhitespace(peek)) {
                        buffer.get();
                        break;
                    }
                    if (this.stack.peek().state == State.OBJECT_FIELD_VALUE) {
                        return this.parseObjectFieldValue(buffer);
                    }
                    throw this.newInvalidJSON(buffer, "invalid object field");
                }
            }
        }
        return false;
    }

    private boolean parseObjectFieldName(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            byte peek = buffer.get(buffer.position());
            switch (peek) {
                case 34: {
                    if (this.stack.peek().state == State.OBJECT_FIELD) {
                        this.stack.push(State.OBJECT_FIELD_NAME, AsyncJSON.UNSET);
                        if (this.parseString(buffer)) break;
                        return false;
                    }
                    throw this.newInvalidJSON(buffer, "invalid object field");
                }
                case 58: {
                    buffer.get();
                    String fieldName = (String)this.stack.peek().value;
                    this.stack.pop();
                    this.stack.push(fieldName, State.OBJECT_FIELD_VALUE, AsyncJSON.UNSET);
                    return true;
                }
                default: {
                    if (AsyncJSON.isWhitespace(peek)) {
                        buffer.get();
                        break;
                    }
                    throw this.newInvalidJSON(buffer, "invalid object field");
                }
            }
        }
        return false;
    }

    private boolean parseObjectFieldValue(ByteBuffer buffer) {
        if (this.stack.peek().value == UNSET && !this.parseAny(buffer)) {
            return false;
        }
        Frame frame = this.stack.peek();
        Object value = frame.value;
        String name = frame.name;
        this.stack.pop();
        this.stack.pop();
        Map map = (Map)this.stack.peek().value;
        map.put(name, value);
        return true;
    }

    private Object convertObject(Map<String, Object> object) {
        Object result = this.convertObject("x-class", object);
        if (result == null && (result = this.convertObject("class", object)) == null) {
            return object;
        }
        return result;
    }

    private Object convertObject(String fieldName, Map<String, Object> object) {
        String className = (String)object.get(fieldName);
        if (className == null) {
            return null;
        }
        JSON.Convertible convertible = this.toConvertible(className);
        if (convertible != null) {
            convertible.fromJSON(object);
            return convertible;
        }
        JSON.Convertor convertor = this.factory.getConvertor(className);
        if (convertor != null) {
            return convertor.fromJSON(object);
        }
        return null;
    }

    private JSON.Convertible toConvertible(String className) {
        try {
            Class klass = Loader.loadClass(className);
            if (JSON.Convertible.class.isAssignableFrom(klass)) {
                return (JSON.Convertible)klass.getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            return null;
        }
        catch (Throwable x) {
            throw new IllegalArgumentException(x);
        }
    }

    protected RuntimeException newInvalidJSON(ByteBuffer buffer, String message) {
        Utf8StringBuilder builder = new Utf8StringBuilder();
        builder.append(System.lineSeparator());
        int position = buffer.position();
        if (this.factory.isDetailedParseException()) {
            this.chunks.forEach(chunk -> builder.append(buffer));
        } else {
            buffer.position(0);
            builder.append(buffer);
            buffer.position(position);
        }
        builder.append(System.lineSeparator());
        String indent = "";
        if (position > 1) {
            char[] chars = new char[position - 1];
            Arrays.fill(chars, ' ');
            indent = new String(chars);
        }
        builder.append(indent);
        builder.append("^ ");
        builder.append(message);
        return new IllegalArgumentException(builder.toString());
    }

    private static boolean isWhitespace(byte ws) {
        switch (ws) {
            case 9: 
            case 10: 
            case 13: 
            case 32: {
                return true;
            }
        }
        return false;
    }

    private static class FrameStack
    implements Context {
        private final List<Frame> stack = new ArrayList<Frame>();
        private int cursor;

        private FrameStack() {
            this.grow(6);
        }

        private void grow(int grow) {
            for (int i = 0; i < grow; ++i) {
                this.stack.add(new Frame());
            }
        }

        private void clear() {
            while (!this.isEmpty()) {
                this.pop();
            }
        }

        private boolean isEmpty() {
            return this.cursor == 0;
        }

        @Override
        public int depth() {
            return this.cursor - 1;
        }

        private Frame peek() {
            if (this.isEmpty()) {
                throw new IllegalStateException("empty stack");
            }
            return this.stack.get(this.depth());
        }

        private void push(State state, Object value) {
            this.push(null, state, value);
        }

        private void push(String name, State state, Object value) {
            if (this.cursor == this.stack.size()) {
                this.grow(2);
            }
            ++this.cursor;
            Frame frame = this.stack.get(this.depth());
            frame.name = name;
            frame.state = state;
            frame.value = value;
        }

        private void pop() {
            if (this.isEmpty()) {
                throw new IllegalStateException("empty stack");
            }
            Frame frame = this.stack.get(this.depth());
            --this.cursor;
            frame.name = null;
            frame.value = null;
            frame.state = null;
        }
    }

    private static class NumberBuilder {
        private int integer = 1;
        private long value;
        private StringBuilder builder;

        private NumberBuilder() {
        }

        private boolean appendSign(byte b) {
            if (this.integer == 0) {
                if (this.builder.length() == 0) {
                    this.builder.append((char)b);
                    return true;
                }
                char c = this.builder.charAt(this.builder.length() - 1);
                if (c == 'E' || c == 'e') {
                    this.builder.append((char)b);
                    return true;
                }
                return false;
            }
            if (this.value == 0L) {
                if (b == 45) {
                    if (this.integer == 1) {
                        this.integer = -1;
                        return true;
                    }
                } else {
                    return true;
                }
            }
            return false;
        }

        private void appendDigit(byte b) {
            if (this.integer == 0) {
                this.builder.append((char)b);
            } else {
                this.value = this.value * 10L + (long)(b - 48);
            }
        }

        private boolean appendAlpha(byte b) {
            if (this.integer == 0) {
                char c = this.builder.charAt(this.builder.length() - 1);
                if ('0' <= c && c <= '9' && this.builder.indexOf("" + (char)b) < 0) {
                    this.builder.append((char)b);
                    return true;
                }
            } else {
                if (this.builder == null) {
                    this.builder = new StringBuilder(16);
                }
                if (this.integer == -1) {
                    this.builder.append('-');
                }
                this.integer = 0;
                this.builder.append(this.value);
                this.builder.append((char)b);
                return true;
            }
            return false;
        }

        private Number value() {
            try {
                if (this.integer == 0) {
                    Double d = Double.parseDouble(this.builder.toString());
                    return d;
                }
                Long l = (long)this.integer * this.value;
                return l;
            }
            finally {
                this.reset();
            }
        }

        private void reset() {
            this.integer = 1;
            this.value = 0L;
            if (this.builder != null) {
                this.builder.setLength(0);
            }
        }
    }

    private static class Frame {
        private String name;
        private State state;
        private Object value;

        private Frame() {
        }

        private void value(Object value) {
            switch (this.state) {
                case COMPLETE: 
                case STRING: 
                case OBJECT_FIELD_NAME: 
                case OBJECT_FIELD_VALUE: {
                    this.value = value;
                    break;
                }
                case ARRAY: {
                    List array = (List)this.value;
                    array.add(value);
                    break;
                }
                default: {
                    throw new IllegalStateException("invalid state " + (Object)((Object)this.state));
                }
            }
        }
    }

    private static enum State {
        COMPLETE,
        NULL,
        TRUE,
        FALSE,
        NUMBER,
        STRING,
        ESCAPE,
        UNICODE,
        ARRAY,
        OBJECT,
        OBJECT_FIELD,
        OBJECT_FIELD_NAME,
        OBJECT_FIELD_VALUE;

    }

    public static interface Context {
        public int depth();
    }

    public static class Factory {
        private Trie<CachedString> cache;
        private Map<String, JSON.Convertor> convertors;
        private boolean detailedParseException;

        public boolean isDetailedParseException() {
            return this.detailedParseException;
        }

        public void setDetailedParseException(boolean detailedParseException) {
            this.detailedParseException = detailedParseException;
        }

        public boolean cache(String value) {
            CachedString cached;
            if (this.cache == null) {
                this.cache = new ArrayTernaryTrie.Growing<CachedString>(false, 64, 64);
            }
            if ((cached = new CachedString(value)).isCacheable()) {
                this.cache.put(cached.encoded, cached);
                return true;
            }
            return false;
        }

        protected String cached(ByteBuffer buffer) {
            CachedString result;
            if (this.cache != null && (result = this.cache.getBest(buffer, 0, buffer.remaining())) != null) {
                buffer.position(buffer.position() + result.encoded.length());
                return result.value;
            }
            return null;
        }

        public AsyncJSON newAsyncJSON() {
            return new AsyncJSON(this);
        }

        public void putConvertor(String className, JSON.Convertor convertor) {
            if (this.convertors == null) {
                this.convertors = new ConcurrentHashMap<String, JSON.Convertor>();
            }
            this.convertors.put(className, convertor);
        }

        public JSON.Convertor removeConvertor(String className) {
            if (this.convertors != null) {
                return this.convertors.remove(className);
            }
            return null;
        }

        public JSON.Convertor getConvertor(String className) {
            return this.convertors == null ? null : this.convertors.get(className);
        }

        private static class CachedString {
            private final String encoded;
            private final String value;

            private CachedString(String value) {
                this.encoded = JSON.toString(value);
                this.value = value;
            }

            private boolean isCacheable() {
                int i = this.encoded.length();
                while (i-- > 0) {
                    char c = this.encoded.charAt(i);
                    if (c <= '\u007f') continue;
                    return false;
                }
                return true;
            }
        }
    }
}

