/*
 * Decompiled with CFR 0.152.
 */
package groovy.grape;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface GrapeEngine {
    public static final String CALLEE_DEPTH = "calleeDepth";
    public static final int DEFAULT_CALLEE_DEPTH = 3;

    public Object grab(String var1);

    public Object grab(Map var1);

    public Object grab(Map var1, Map ... var2);

    public Map<String, Map<String, List<String>>> enumerateGrapes();

    public URI[] resolve(Map var1, Map ... var2);

    public URI[] resolve(Map var1, List var2, Map ... var3);

    public Map[] listDependencies(ClassLoader var1);

    public void addResolver(Map<String, Object> var1);
}

