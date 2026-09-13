/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.introspection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class JexlSandbox {
    public static final String NULL = "?";
    private final Map<String, Permissions> sandbox;
    private final boolean inherit;
    private final boolean allow;
    private static final Names ALLOW_NAMES = new Names(){

        @Override
        public boolean add(String name) {
            return false;
        }

        @Override
        protected Names copy() {
            return this;
        }
    };
    private static final Names BLOCK_NAMES = new Names(){

        @Override
        public boolean add(String name) {
            return false;
        }

        @Override
        protected Names copy() {
            return this;
        }

        @Override
        public String get(String name) {
            return name == null ? JexlSandbox.NULL : null;
        }
    };
    private static final Permissions ALLOW_ALL = new Permissions(false, ALLOW_NAMES, ALLOW_NAMES, ALLOW_NAMES);
    private static final Permissions BLOCK_ALL = new Permissions(false, BLOCK_NAMES, BLOCK_NAMES, BLOCK_NAMES);

    public JexlSandbox() {
        this(true, false, null);
    }

    public JexlSandbox(boolean ab) {
        this(ab, false, null);
    }

    public JexlSandbox(boolean ab, boolean inh) {
        this(ab, inh, null);
    }

    @Deprecated
    protected JexlSandbox(Map<String, Permissions> map) {
        this(true, false, map);
    }

    @Deprecated
    protected JexlSandbox(boolean ab, Map<String, Permissions> map) {
        this(ab, false, map);
    }

    protected JexlSandbox(boolean ab, boolean inh, Map<String, Permissions> map) {
        this.allow = ab;
        this.inherit = inh;
        this.sandbox = map != null ? map : new HashMap();
    }

    public JexlSandbox copy() {
        ConcurrentHashMap<String, Permissions> map = new ConcurrentHashMap<String, Permissions>();
        for (Map.Entry<String, Permissions> entry : this.sandbox.entrySet()) {
            map.put(entry.getKey(), entry.getValue().copy());
        }
        return new JexlSandbox(this.allow, this.inherit, map);
    }

    static Class<?> forName(String cname) {
        try {
            return Class.forName(cname);
        }
        catch (Exception xany) {
            return null;
        }
    }

    public String read(Class<?> clazz, String name) {
        return this.get(clazz).read().get(name);
    }

    @Deprecated
    public String read(String clazz, String name) {
        return this.get(clazz).read().get(name);
    }

    public String write(Class<?> clazz, String name) {
        return this.get(clazz).write().get(name);
    }

    @Deprecated
    public String write(String clazz, String name) {
        return this.get(clazz).write().get(name);
    }

    public String execute(Class<?> clazz, String name) {
        String m = this.get(clazz).execute().get(name);
        return "".equals(name) && m != null ? clazz.getName() : m;
    }

    @Deprecated
    public String execute(String clazz, String name) {
        String m = this.get(clazz).execute().get(name);
        return "".equals(name) && m != null ? clazz : m;
    }

    public Permissions permissions(String clazz, boolean readFlag, boolean writeFlag, boolean executeFlag) {
        return this.permissions(clazz, this.inherit, readFlag, writeFlag, executeFlag);
    }

    public Permissions permissions(String clazz, boolean inhf, boolean readf, boolean writef, boolean execf) {
        Permissions box = new Permissions(inhf, readf, writef, execf);
        this.sandbox.put(clazz, box);
        return box;
    }

    public Permissions allow(String clazz) {
        return this.permissions(clazz, true, true, true);
    }

    @Deprecated
    public Permissions white(String clazz) {
        return this.allow(clazz);
    }

    public Permissions block(String clazz) {
        return this.permissions(clazz, false, false, false);
    }

    @Deprecated
    public Permissions black(String clazz) {
        return this.block(clazz);
    }

    public Permissions get(String clazz) {
        if (this.inherit) {
            return this.get(JexlSandbox.forName(clazz));
        }
        Permissions permissions = this.sandbox.get(clazz);
        if (permissions == null) {
            return this.allow ? ALLOW_ALL : BLOCK_ALL;
        }
        return permissions;
    }

    public Permissions get(Class<?> clazz) {
        Permissions permissions;
        Permissions permissions2 = permissions = clazz == null ? BLOCK_ALL : this.sandbox.get(clazz.getName());
        if (permissions == null) {
            if (this.inherit) {
                Class<?> inter;
                Class<?>[] classArray = clazz.getInterfaces();
                int n = classArray.length;
                for (int i = 0; !(i >= n || (permissions = this.sandbox.get((inter = classArray[i]).getName())) != null && permissions.isInheritable()); ++i) {
                }
                if (permissions == null) {
                    for (Class<?> zuper = clazz.getSuperclass(); !(zuper == null || (permissions = this.sandbox.get(zuper.getName())) != null && permissions.isInheritable()); zuper = zuper.getSuperclass()) {
                    }
                }
                if (permissions == null) {
                    permissions = this.allow ? ALLOW_ALL : BLOCK_ALL;
                }
                this.sandbox.put(clazz.getName(), permissions);
            } else {
                permissions = this.allow ? ALLOW_ALL : BLOCK_ALL;
            }
        }
        return permissions;
    }

    public static final class Permissions {
        private final boolean inheritable;
        private final Names read;
        private final Names write;
        private final Names execute;

        Permissions(boolean inherit, boolean readFlag, boolean writeFlag, boolean executeFlag) {
            this(inherit, readFlag ? new AllowSet() : new BlockSet(), writeFlag ? new AllowSet() : new BlockSet(), executeFlag ? new AllowSet() : new BlockSet());
        }

        Permissions(boolean inherit, Names nread, Names nwrite, Names nexecute) {
            this.read = nread != null ? nread : ALLOW_NAMES;
            this.write = nwrite != null ? nwrite : ALLOW_NAMES;
            this.execute = nexecute != null ? nexecute : ALLOW_NAMES;
            this.inheritable = inherit;
        }

        Permissions copy() {
            return new Permissions(this.inheritable, this.read.copy(), this.write.copy(), this.execute.copy());
        }

        public boolean isInheritable() {
            return this.inheritable;
        }

        public Permissions read(String ... pnames) {
            for (String pname : pnames) {
                this.read.add(pname);
            }
            return this;
        }

        public Permissions write(String ... pnames) {
            for (String pname : pnames) {
                this.write.add(pname);
            }
            return this;
        }

        public Permissions execute(String ... mnames) {
            for (String mname : mnames) {
                this.execute.add(mname);
            }
            return this;
        }

        public Names read() {
            return this.read;
        }

        public Names write() {
            return this.write;
        }

        public Names execute() {
            return this.execute;
        }
    }

    @Deprecated
    public static final class BlackSet
    extends BlockSet {
    }

    @Deprecated
    public static final class WhiteSet
    extends AllowSet {
    }

    static class BlockSet
    extends Names {
        private Set<String> names = null;

        BlockSet() {
        }

        @Override
        protected Names copy() {
            BlockSet copy = new BlockSet();
            copy.names = this.names == null ? null : new HashSet<String>(this.names);
            return copy;
        }

        @Override
        public boolean add(String name) {
            if (this.names == null) {
                this.names = new HashSet<String>();
            }
            return this.names.add(name);
        }

        @Override
        public String get(String name) {
            return this.names != null && !this.names.contains(name) ? name : (name != null ? null : JexlSandbox.NULL);
        }
    }

    static class AllowSet
    extends Names {
        private Map<String, String> names = null;

        AllowSet() {
        }

        @Override
        protected Names copy() {
            AllowSet copy = new AllowSet();
            copy.names = this.names == null ? null : new HashMap<String, String>(this.names);
            return copy;
        }

        @Override
        public boolean add(String name) {
            if (this.names == null) {
                this.names = new HashMap<String, String>();
            }
            return this.names.put(name, name) == null;
        }

        @Override
        public boolean alias(String name, String alias) {
            if (this.names == null) {
                this.names = new HashMap<String, String>();
            }
            return this.names.put(alias, name) == null;
        }

        @Override
        public String get(String name) {
            if (this.names == null) {
                return name;
            }
            String actual = this.names.get(name);
            if (name == null && actual == null && !this.names.containsKey(null)) {
                return JexlSandbox.NULL;
            }
            return actual;
        }
    }

    public static abstract class Names {
        public abstract boolean add(String var1);

        public boolean alias(String name, String alias) {
            return false;
        }

        public String get(String name) {
            return name;
        }

        protected Names copy() {
            return this;
        }
    }
}

