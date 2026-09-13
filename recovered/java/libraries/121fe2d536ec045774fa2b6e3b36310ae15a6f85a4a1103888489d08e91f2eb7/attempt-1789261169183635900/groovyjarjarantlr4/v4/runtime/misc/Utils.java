/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import groovyjarjarantlr4.v4.runtime.misc.IntegerList;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Predicate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Utils {
    public static String join(Iterable<?> iter, String separator) {
        return Utils.join(iter.iterator(), separator);
    }

    public static <T> String join(T[] array, String separator) {
        return Utils.join(Arrays.asList(array), separator);
    }

    public static <T> String join(Iterator<T> iter, String separator) {
        StringBuilder buf = new StringBuilder();
        while (iter.hasNext()) {
            buf.append(iter.next());
            if (!iter.hasNext()) continue;
            buf.append(separator);
        }
        return buf.toString();
    }

    public static boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    public static int numNonnull(Object[] data) {
        int n = 0;
        if (data == null) {
            return n;
        }
        for (Object o : data) {
            if (o == null) continue;
            ++n;
        }
        return n;
    }

    public static <T> void removeAllElements(Collection<T> data, T value) {
        if (data == null) {
            return;
        }
        while (data.contains(value)) {
            data.remove(value);
        }
    }

    public static String escapeWhitespace(String s, boolean escapeSpaces) {
        StringBuilder buf = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == ' ' && escapeSpaces) {
                buf.append('\u00b7');
                continue;
            }
            if (c == '\t') {
                buf.append("\\t");
                continue;
            }
            if (c == '\n') {
                buf.append("\\n");
                continue;
            }
            if (c == '\r') {
                buf.append("\\r");
                continue;
            }
            buf.append(c);
        }
        return buf.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeFile(@NotNull File file, @NotNull byte[] content) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(content);
        }
        finally {
            fos.close();
        }
    }

    public static void writeFile(@NotNull String fileName, @NotNull String content) throws IOException {
        Utils.writeFile(fileName, content, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeFile(@NotNull String fileName, @NotNull String content, @Nullable String encoding) throws IOException {
        File f = new File(fileName);
        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter osw = encoding != null ? new OutputStreamWriter((OutputStream)fos, encoding) : new OutputStreamWriter(fos);
        try {
            osw.write(content);
        }
        finally {
            osw.close();
        }
    }

    @NotNull
    public static char[] readFile(@NotNull String fileName) throws IOException {
        return Utils.readFile(fileName, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public static char[] readFile(@NotNull String fileName, @Nullable String encoding) throws IOException {
        File f = new File(fileName);
        int size = (int)f.length();
        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = encoding != null ? new InputStreamReader((InputStream)fis, encoding) : new InputStreamReader(fis);
        char[] data = null;
        try {
            data = new char[size];
            int n = isr.read(data);
            if (n < data.length) {
                data = Arrays.copyOf(data, n);
            }
        }
        finally {
            isr.close();
        }
        return data;
    }

    public static <T> void removeAll(@NotNull List<T> list, @NotNull Predicate<? super T> predicate) {
        int j = 0;
        for (int i = 0; i < list.size(); ++i) {
            T item = list.get(i);
            if (predicate.eval(item)) continue;
            if (j != i) {
                list.set(j, item);
            }
            ++j;
        }
        if (j < list.size()) {
            list.subList(j, list.size()).clear();
        }
    }

    public static <T> void removeAll(@NotNull Iterable<T> iterable, @NotNull Predicate<? super T> predicate) {
        if (iterable instanceof List) {
            Utils.removeAll((List)iterable, predicate);
            return;
        }
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (!predicate.eval(item)) continue;
            iterator.remove();
        }
    }

    public static Map<String, Integer> toMap(String[] keys) {
        HashMap<String, Integer> m = new HashMap<String, Integer>();
        for (int i = 0; i < keys.length; ++i) {
            m.put(keys[i], i);
        }
        return m;
    }

    public static char[] toCharArray(IntegerList data) {
        if (data == null) {
            return null;
        }
        char[] resultArray = new char[data.size()];
        int resultIdx = 0;
        boolean calculatedPreciseResultSize = false;
        for (int i = 0; i < data.size(); ++i) {
            int codePoint = data.get(i);
            if (!calculatedPreciseResultSize && Character.isSupplementaryCodePoint(codePoint)) {
                resultArray = Arrays.copyOf(resultArray, Utils.charArraySize(data));
                calculatedPreciseResultSize = true;
            }
            int charsWritten = Character.toChars(codePoint, resultArray, resultIdx);
            resultIdx += charsWritten;
        }
        return resultArray;
    }

    private static int charArraySize(IntegerList data) {
        int result = 0;
        for (int i = 0; i < data.size(); ++i) {
            result += Character.charCount(data.get(i));
        }
        return result;
    }

    @NotNull
    public static IntervalSet toSet(@NotNull BitSet bits) {
        IntervalSet s = new IntervalSet(new int[0]);
        int i = bits.nextSetBit(0);
        while (i >= 0) {
            s.add(i);
            i = bits.nextSetBit(i + 1);
        }
        return s;
    }
}

