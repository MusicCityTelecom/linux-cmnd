/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils;

import java.io.Serializable;
import java.util.UUID;

public class Key<T>
implements Serializable,
Comparable<Key<T>> {
    private static final long serialVersionUID = -7141167957642391350L;
    public static final String DEFAULT_GROUP = "DEFAULT";
    private final String name;
    private final String group;

    public Key(String name, String group) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.name = name;
        this.group = group != null ? group : DEFAULT_GROUP;
    }

    public String getName() {
        return this.name;
    }

    public String getGroup() {
        return this.group;
    }

    public String toString() {
        return this.getGroup() + '.' + this.getName();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.group == null ? 0 : this.group.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Key other = (Key)obj;
        if (this.group == null ? other.group != null : !this.group.equals(other.group)) {
            return false;
        }
        return !(this.name == null ? other.name != null : !this.name.equals(other.name));
    }

    @Override
    public int compareTo(Key<T> o) {
        if (this.group.equals(DEFAULT_GROUP) && !o.group.equals(DEFAULT_GROUP)) {
            return -1;
        }
        if (!this.group.equals(DEFAULT_GROUP) && o.group.equals(DEFAULT_GROUP)) {
            return 1;
        }
        int r = this.group.compareTo(o.getGroup());
        if (r != 0) {
            return r;
        }
        return this.name.compareTo(o.getName());
    }

    public static String createUniqueName(String group) {
        if (group == null) {
            group = DEFAULT_GROUP;
        }
        String n1 = UUID.randomUUID().toString();
        String n2 = UUID.nameUUIDFromBytes(group.getBytes()).toString();
        return String.format("%s-%s", n2.substring(24), n1);
    }
}

