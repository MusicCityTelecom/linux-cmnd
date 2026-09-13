/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.logging;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.logging.LoggerGroup;

public final class LoggerGroups
implements Iterable<LoggerGroup> {
    private final Map<String, LoggerGroup> groups = new ConcurrentHashMap<String, LoggerGroup>();

    public LoggerGroups() {
    }

    public LoggerGroups(Map<String, List<String>> namesAndMembers) {
        this.putAll(namesAndMembers);
    }

    public void putAll(Map<String, List<String>> namesAndMembers) {
        namesAndMembers.forEach(this::put);
    }

    private void put(String name, List<String> members) {
        this.put(new LoggerGroup(name, members));
    }

    private void put(LoggerGroup loggerGroup) {
        this.groups.put(loggerGroup.getName(), loggerGroup);
    }

    public LoggerGroup get(String name) {
        return this.groups.get(name);
    }

    @Override
    public Iterator<LoggerGroup> iterator() {
        return this.groups.values().iterator();
    }
}

