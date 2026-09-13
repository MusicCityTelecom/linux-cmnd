/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.misc;

import groovyjarjarantlr4.v4.runtime.misc.Func1;
import groovyjarjarantlr4.v4.runtime.misc.Predicate;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final int INTEGER_POOL_MAX_VALUE = 1000;
    static Integer[] ints = new Integer[1001];

    public static String stripFileExtension(String name) {
        if (name == null) {
            return null;
        }
        int lastDot = name.lastIndexOf(46);
        if (lastDot < 0) {
            return name;
        }
        return name.substring(0, lastDot);
    }

    public static String join(Object[] a, String separator) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a.length; ++i) {
            Object o = a[i];
            buf.append(o.toString());
            if (i + 1 >= a.length) continue;
            buf.append(separator);
        }
        return buf.toString();
    }

    public static String sortLinesInString(String s) {
        Object[] lines = s.split("\n");
        Arrays.sort(lines);
        List<Object> linesL = Arrays.asList(lines);
        StringBuilder buf = new StringBuilder();
        for (String string : linesL) {
            buf.append(string);
            buf.append('\n');
        }
        return buf.toString();
    }

    public static <T extends GrammarAST> List<String> nodesToStrings(List<T> nodes) {
        if (nodes == null) {
            return null;
        }
        ArrayList<String> a = new ArrayList<String>();
        for (GrammarAST t : nodes) {
            a.add(t.getText());
        }
        return a;
    }

    public static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String decapitalize(String s) {
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    public static <From, To> List<To> select(List<From> list, Func1<From, To> selector) {
        if (list == null) {
            return null;
        }
        ArrayList<To> b = new ArrayList<To>();
        for (From f : list) {
            b.add(selector.eval(f));
        }
        return b;
    }

    public static <T> T find(List<?> ops, Class<T> cl) {
        for (Object o : ops) {
            if (!cl.isInstance(o)) continue;
            return cl.cast(o);
        }
        return null;
    }

    public static <T> int indexOf(List<? extends T> elems, Predicate<? super T> match) {
        for (int i = 0; i < elems.size(); ++i) {
            if (!match.eval(elems.get(i))) continue;
            return i;
        }
        return -1;
    }

    public static <T> int lastIndexOf(List<? extends T> elems, Predicate<? super T> match) {
        for (int i = elems.size() - 1; i >= 0; --i) {
            if (!match.eval(elems.get(i))) continue;
            return i;
        }
        return -1;
    }

    public static void setSize(List<?> list, int size) {
        if (size < list.size()) {
            list.subList(size, list.size()).clear();
        } else {
            while (size > list.size()) {
                list.add(null);
            }
        }
    }
}

