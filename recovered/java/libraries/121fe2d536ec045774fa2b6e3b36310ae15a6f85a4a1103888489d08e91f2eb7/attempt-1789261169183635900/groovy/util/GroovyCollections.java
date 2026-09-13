/*
 * Decompiled with CFR 0.152.
 */
package groovy.util;

import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromString;
import groovy.util.ClosureComparator;
import groovy.util.OrderBy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.NumberAwareComparator;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class GroovyCollections {
    public static List combinations(Object[] collections) {
        return GroovyCollections.combinations(Arrays.asList(collections));
    }

    public static <T> Set<List<T>> subsequences(List<T> items) {
        HashSet<List<Object>> ans = new HashSet<List<T>>();
        for (T h : items) {
            HashSet<List<Object>> next = new HashSet<List<Object>>();
            for (List list : ans) {
                ArrayList<T> sublist = new ArrayList<T>(list);
                sublist.add(h);
                next.add(sublist);
            }
            next.addAll(ans);
            ArrayList<T> hlist = new ArrayList<T>();
            hlist.add(h);
            next.add(hlist);
            ans = next;
        }
        return ans;
    }

    public static List combinations(Iterable collections) {
        ArrayList collectedCombos = new ArrayList();
        for (Object collection : collections) {
            Collection items = DefaultTypeTransformation.asCollection(collection);
            if (collectedCombos.isEmpty()) {
                for (Object item : items) {
                    ArrayList l = new ArrayList();
                    l.add(item);
                    collectedCombos.add(l);
                }
            } else {
                ArrayList savedCombos = new ArrayList(collectedCombos);
                ArrayList newCombos = new ArrayList();
                for (Object value : items) {
                    for (Object savedCombo : savedCombos) {
                        ArrayList oldList = new ArrayList((List)savedCombo);
                        oldList.add(value);
                        newCombos.add(oldList);
                    }
                }
                collectedCombos = newCombos;
            }
            if (!collectedCombos.isEmpty()) continue;
            break;
        }
        return collectedCombos;
    }

    public static <T> List<List<T>> inits(Iterable<T> collections) {
        List<T> copy = DefaultGroovyMethods.toList(collections);
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        for (int i = copy.size(); i >= 0; --i) {
            List<T> next = copy.subList(0, i);
            result.add(next);
        }
        return result;
    }

    public static <T> List<List<T>> tails(Iterable<T> collections) {
        List<T> copy = DefaultGroovyMethods.toList(collections);
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        for (int i = 0; i <= copy.size(); ++i) {
            List<T> next = copy.subList(i, copy.size());
            result.add(next);
        }
        return result;
    }

    public static List transpose(Object[] lists) {
        return GroovyCollections.transpose(Arrays.asList(lists));
    }

    public static List transpose(List lists) {
        List list;
        ArrayList result = new ArrayList();
        if (lists.isEmpty()) {
            return result;
        }
        int minSize = Integer.MAX_VALUE;
        for (Object listLike : lists) {
            list = (List)DefaultTypeTransformation.castToType(listLike, List.class);
            if (list.size() >= minSize) continue;
            minSize = list.size();
        }
        if (minSize == 0) {
            return result;
        }
        for (int i = 0; i < minSize; ++i) {
            result.add(new ArrayList());
        }
        for (Object listLike : lists) {
            list = (List)DefaultTypeTransformation.castToType(listLike, List.class);
            for (int i = 0; i < minSize; ++i) {
                List resultList = (List)result.get(i);
                resultList.add(list.get(i));
            }
        }
        return result;
    }

    public static <T> T min(T[] items) {
        return DefaultGroovyMethods.min(items);
    }

    public static <T> T min(Iterable<T> items) {
        return DefaultGroovyMethods.min(items);
    }

    public static <T> T max(T[] items) {
        return DefaultGroovyMethods.max(items);
    }

    public static <T> T max(Iterable<T> items) {
        return DefaultGroovyMethods.max(items);
    }

    public static Object sum(Object[] items) {
        return DefaultGroovyMethods.sum(items);
    }

    public static Object sum(Iterable<?> items) {
        return DefaultGroovyMethods.sum(items);
    }

    public static <T> List<T> union(Iterable<T> ... iterables) {
        return GroovyCollections.union(Arrays.asList(iterables));
    }

    public static <T> List<T> union(List<Iterable<T>> iterables) {
        return GroovyCollections.union(iterables, new NumberAwareComparator());
    }

    public static <T> List<T> union(Comparator<T> comparator, Iterable<T> ... iterables) {
        return GroovyCollections.union(Arrays.asList(iterables), comparator);
    }

    public static <T> List<T> union(List<Iterable<T>> iterables, Comparator<T> comparator) {
        LinkedHashSet<T> ansSet = new LinkedHashSet<T>();
        TreeSet<T> seen = new TreeSet<T>(comparator);
        for (Iterable<T> nextList : iterables) {
            for (T next : nextList) {
                if (!seen.add(next)) continue;
                ansSet.add(next);
            }
        }
        return new ArrayList(ansSet);
    }

    public static <T> List<T> union(@ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition, Iterable<T> ... iterables) {
        return GroovyCollections.union(Arrays.asList(iterables), condition);
    }

    public static <T> List<T> union(List<Iterable<T>> iterables, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        Serializable comparator = condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition, true) : new ClosureComparator(condition);
        return GroovyCollections.union(iterables, comparator);
    }
}

