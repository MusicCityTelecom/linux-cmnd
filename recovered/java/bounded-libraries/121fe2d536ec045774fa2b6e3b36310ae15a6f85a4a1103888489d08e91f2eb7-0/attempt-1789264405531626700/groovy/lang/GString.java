/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Buildable;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.MissingMethodException;
import groovy.lang.StringWriterIOException;
import groovy.lang.Writable;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.regex.Pattern;
import org.apache.groovy.io.StringBuilderWriter;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GStringUtil;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.StringGroovyMethods;

public abstract class GString
extends GroovyObjectSupport
implements Comparable,
CharSequence,
Writable,
Buildable,
Serializable {
    private static final long serialVersionUID = -2638020355892246323L;
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final GString EMPTY = new GString(EMPTY_OBJECT_ARRAY){
        private static final long serialVersionUID = -7676746462783374250L;
        private static final String EMPTY_STRING = "";

        @Override
        public String[] getStrings() {
            return new String[]{EMPTY_STRING};
        }

        @Override
        public String toString() {
            return EMPTY_STRING;
        }
    };
    private final Object[] values;

    public GString(Object values) {
        this.values = (Object[])values;
    }

    public GString(Object[] values) {
        this.values = values;
    }

    public abstract String[] getStrings();

    @Override
    public Object invokeMethod(String name, Object args) {
        try {
            return super.invokeMethod(name, args);
        }
        catch (MissingMethodException e) {
            return InvokerHelper.invokeMethod(this.toString(), name, args);
        }
    }

    public Object[] getValues() {
        return this.values;
    }

    public GString plus(GString that) {
        return GStringUtil.plusImpl(this.values, that.values, this.getStrings(), that.getStrings());
    }

    public GString plus(String that) {
        return this.plus(new GStringImpl(EMPTY_OBJECT_ARRAY, new String[]{that}));
    }

    public int getValueCount() {
        return this.values.length;
    }

    public Object getValue(int idx) {
        return this.values[idx];
    }

    @Override
    public String toString() {
        StringBuilderWriter buffer = new StringBuilderWriter(this.calcInitialCapacity());
        try {
            this.writeTo(buffer);
        }
        catch (IOException e) {
            throw new StringWriterIOException(e);
        }
        return ((Object)buffer).toString();
    }

    protected int calcInitialCapacity() {
        return GStringUtil.calcInitialCapacityImpl(this.values, this.getStrings());
    }

    @Override
    public Writer writeTo(Writer out) throws IOException {
        return GStringUtil.writeToImpl(out, this.values, this.getStrings());
    }

    @Override
    public void build(GroovyObject builder) {
        GStringUtil.buildImpl(builder, this.values, this.getStrings());
    }

    public int hashCode() {
        return 37 + this.toString().hashCode();
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof GString)) {
            return false;
        }
        return this.equals((GString)that);
    }

    public boolean equals(GString that) {
        return this.toString().equals(that.toString());
    }

    public int compareTo(Object that) {
        return this.toString().compareTo(that.toString());
    }

    @Override
    public char charAt(int index) {
        return this.toString().charAt(index);
    }

    @Override
    public int length() {
        return this.toString().length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.toString().subSequence(start, end);
    }

    public Pattern negate() {
        return StringGroovyMethods.bitwiseNegate(this.toString());
    }

    public byte[] getBytes() {
        return this.toString().getBytes();
    }

    public byte[] getBytes(String charset) throws UnsupportedEncodingException {
        return this.toString().getBytes(charset);
    }
}

