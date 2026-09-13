/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.Toolkit
 *  org.terracotta.toolkit.builder.ToolkitStoreConfigBuilder
 *  org.terracotta.toolkit.collections.ToolkitSet
 *  org.terracotta.toolkit.collections.ToolkitSortedSet
 *  org.terracotta.toolkit.concurrent.locks.ToolkitLock
 *  org.terracotta.toolkit.internal.ToolkitInternal
 *  org.terracotta.toolkit.internal.concurrent.locks.ToolkitLockTypeInternal
 *  org.terracotta.toolkit.store.ToolkitConfigFields$Consistency
 *  org.terracotta.toolkit.store.ToolkitStore
 */
package org.terracotta.quartz.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.quartz.Calendar;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.terracotta.quartz.collections.SerializedToolkitStore;
import org.terracotta.quartz.collections.TimeTrigger;
import org.terracotta.quartz.collections.TimeTriggerSet;
import org.terracotta.quartz.wrappers.FiredTrigger;
import org.terracotta.quartz.wrappers.JobWrapper;
import org.terracotta.quartz.wrappers.TriggerWrapper;
import org.terracotta.toolkit.Toolkit;
import org.terracotta.toolkit.builder.ToolkitStoreConfigBuilder;
import org.terracotta.toolkit.collections.ToolkitSet;
import org.terracotta.toolkit.collections.ToolkitSortedSet;
import org.terracotta.toolkit.concurrent.locks.ToolkitLock;
import org.terracotta.toolkit.internal.ToolkitInternal;
import org.terracotta.toolkit.internal.concurrent.locks.ToolkitLockTypeInternal;
import org.terracotta.toolkit.store.ToolkitConfigFields;
import org.terracotta.toolkit.store.ToolkitStore;

public class ToolkitDSHolder {
    private static final String JOBS_MAP_PREFIX = "_tc_quartz_jobs";
    private static final String ALL_JOBS_GROUP_NAMES_SET_PREFIX = "_tc_quartz_grp_names";
    private static final String PAUSED_GROUPS_SET_PREFIX = "_tc_quartz_grp_paused_names";
    private static final String BLOCKED_JOBS_SET_PREFIX = "_tc_quartz_blocked_jobs";
    private static final String JOBS_GROUP_MAP_PREFIX = "_tc_quartz_grp_jobs_";
    private static final String TRIGGERS_MAP_PREFIX = "_tc_quartz_triggers";
    private static final String TRIGGERS_GROUP_MAP_PREFIX = "_tc_quartz_grp_triggers_";
    private static final String ALL_TRIGGERS_GROUP_NAMES_SET_PREFIX = "_tc_quartz_grp_names_triggers";
    private static final String PAUSED_TRIGGER_GROUPS_SET_PREFIX = "_tc_quartz_grp_paused_trogger_names";
    private static final String TIME_TRIGGER_SORTED_SET_PREFIX = "_tc_time_trigger_sorted_set";
    private static final String FIRED_TRIGGER_MAP_PREFIX = "_tc_quartz_fired_trigger";
    private static final String CALENDAR_WRAPPER_MAP_PREFIX = "_tc_quartz_calendar_wrapper";
    private static final String SINGLE_LOCK_NAME_PREFIX = "_tc_quartz_single_lock";
    private static final String DELIMETER = "|";
    private final String jobStoreName;
    protected final Toolkit toolkit;
    private final AtomicReference<SerializedToolkitStore<JobKey, JobWrapper>> jobsMapReference = new AtomicReference();
    private final AtomicReference<SerializedToolkitStore<TriggerKey, TriggerWrapper>> triggersMapReference = new AtomicReference();
    private final AtomicReference<ToolkitSet<String>> allGroupsReference = new AtomicReference();
    private final AtomicReference<ToolkitSet<String>> allTriggersGroupsReference = new AtomicReference();
    private final AtomicReference<ToolkitSet<String>> pausedGroupsReference = new AtomicReference();
    private final AtomicReference<ToolkitSet<JobKey>> blockedJobsReference = new AtomicReference();
    private final Map<String, ToolkitSet<String>> jobsGroupSet = new HashMap<String, ToolkitSet<String>>();
    private final Map<String, ToolkitSet<String>> triggersGroupSet = new HashMap<String, ToolkitSet<String>>();
    private final AtomicReference<ToolkitSet<String>> pausedTriggerGroupsReference = new AtomicReference();
    private final AtomicReference<ToolkitStore<String, FiredTrigger>> firedTriggersMapReference = new AtomicReference();
    private final AtomicReference<ToolkitStore<String, Calendar>> calendarWrapperMapReference = new AtomicReference();
    private final AtomicReference<TimeTriggerSet> timeTriggerSetReference = new AtomicReference();
    private final Map<String, ToolkitStore<?, ?>> toolkitMaps = new HashMap();

    public ToolkitDSHolder(String jobStoreName, Toolkit toolkit) {
        this.jobStoreName = jobStoreName;
        this.toolkit = toolkit;
    }

    protected final String generateName(String prefix) {
        return prefix + DELIMETER + this.jobStoreName;
    }

    public SerializedToolkitStore<JobKey, JobWrapper> getOrCreateJobsMap() {
        String jobsMapName = this.generateName(JOBS_MAP_PREFIX);
        SerializedToolkitStore temp = new SerializedToolkitStore(this.createStore(jobsMapName));
        this.jobsMapReference.compareAndSet(null, temp);
        return this.jobsMapReference.get();
    }

    protected ToolkitStore<?, ?> toolkitMap(String nameOfMap) {
        ToolkitStore map = this.toolkitMaps.get(nameOfMap);
        if (map != null && !map.isDestroyed()) {
            return map;
        }
        map = this.createStore(nameOfMap);
        this.toolkitMaps.put(nameOfMap, map);
        return map;
    }

    private ToolkitStore createStore(String nameOfMap) {
        ToolkitStoreConfigBuilder builder = new ToolkitStoreConfigBuilder();
        return this.toolkit.getStore(nameOfMap, builder.consistency(ToolkitConfigFields.Consistency.STRONG).concurrency(1).build(), null);
    }

    public SerializedToolkitStore<TriggerKey, TriggerWrapper> getOrCreateTriggersMap() {
        String triggersMapName = this.generateName(TRIGGERS_MAP_PREFIX);
        SerializedToolkitStore temp = new SerializedToolkitStore(this.createStore(triggersMapName));
        this.triggersMapReference.compareAndSet(null, temp);
        return this.triggersMapReference.get();
    }

    public ToolkitStore<String, FiredTrigger> getOrCreateFiredTriggersMap() {
        String firedTriggerMapName = this.generateName(FIRED_TRIGGER_MAP_PREFIX);
        ToolkitStore temp = this.createStore(firedTriggerMapName);
        this.firedTriggersMapReference.compareAndSet(null, (ToolkitStore<String, FiredTrigger>)temp);
        return this.firedTriggersMapReference.get();
    }

    public ToolkitStore<String, Calendar> getOrCreateCalendarWrapperMap() {
        String calendarWrapperName = this.generateName(CALENDAR_WRAPPER_MAP_PREFIX);
        ToolkitStore temp = this.createStore(calendarWrapperName);
        this.calendarWrapperMapReference.compareAndSet(null, (ToolkitStore<String, Calendar>)temp);
        return this.calendarWrapperMapReference.get();
    }

    public Set<String> getOrCreateAllGroupsSet() {
        String allGrpSetNames = this.generateName(ALL_JOBS_GROUP_NAMES_SET_PREFIX);
        ToolkitSet temp = this.toolkit.getSet(allGrpSetNames, String.class);
        this.allGroupsReference.compareAndSet(null, (ToolkitSet<String>)temp);
        return (Set)this.allGroupsReference.get();
    }

    public Set<JobKey> getOrCreateBlockedJobsSet() {
        String blockedJobsSetName = this.generateName(BLOCKED_JOBS_SET_PREFIX);
        ToolkitSet temp = this.toolkit.getSet(blockedJobsSetName, JobKey.class);
        this.blockedJobsReference.compareAndSet(null, (ToolkitSet<JobKey>)temp);
        return (Set)this.blockedJobsReference.get();
    }

    public Set<String> getOrCreatePausedGroupsSet() {
        String pausedGrpsSetName = this.generateName(PAUSED_GROUPS_SET_PREFIX);
        ToolkitSet temp = this.toolkit.getSet(pausedGrpsSetName, String.class);
        this.pausedGroupsReference.compareAndSet(null, (ToolkitSet<String>)temp);
        return (Set)this.pausedGroupsReference.get();
    }

    public Set<String> getOrCreatePausedTriggerGroupsSet() {
        String pausedGrpsSetName = this.generateName(PAUSED_TRIGGER_GROUPS_SET_PREFIX);
        ToolkitSet temp = this.toolkit.getSet(pausedGrpsSetName, String.class);
        this.pausedTriggerGroupsReference.compareAndSet(null, (ToolkitSet<String>)temp);
        return (Set)this.pausedTriggerGroupsReference.get();
    }

    public Set<String> getOrCreateJobsGroupMap(String name) {
        ToolkitSet set = this.jobsGroupSet.get(name);
        if (set != null && !set.isDestroyed()) {
            return set;
        }
        String nameForMap = this.generateName(JOBS_GROUP_MAP_PREFIX + name);
        set = this.toolkit.getSet(nameForMap, String.class);
        this.jobsGroupSet.put(name, (ToolkitSet<String>)set);
        return set;
    }

    public void removeJobsGroupMap(String name) {
        ToolkitSet<String> set = this.jobsGroupSet.remove(name);
        if (set != null) {
            set.destroy();
        }
    }

    public Set<String> getOrCreateTriggersGroupMap(String name) {
        ToolkitSet set = this.triggersGroupSet.get(name);
        if (set != null && !set.isDestroyed()) {
            return set;
        }
        String nameForMap = this.generateName(TRIGGERS_GROUP_MAP_PREFIX + name);
        set = this.toolkit.getSet(nameForMap, String.class);
        this.triggersGroupSet.put(name, (ToolkitSet<String>)set);
        return set;
    }

    public void removeTriggersGroupMap(String name) {
        ToolkitSet<String> set = this.triggersGroupSet.remove(name);
        if (set != null) {
            set.destroy();
        }
    }

    public Set<String> getOrCreateAllTriggersGroupsSet() {
        String allTriggersGrpsName = this.generateName(ALL_TRIGGERS_GROUP_NAMES_SET_PREFIX);
        ToolkitSet temp = this.toolkit.getSet(allTriggersGrpsName, String.class);
        this.allTriggersGroupsReference.compareAndSet(null, (ToolkitSet<String>)temp);
        return (Set)this.allTriggersGroupsReference.get();
    }

    public TimeTriggerSet getOrCreateTimeTriggerSet() {
        String triggerSetName = this.generateName(TIME_TRIGGER_SORTED_SET_PREFIX);
        TimeTriggerSet set = new TimeTriggerSet((ToolkitSortedSet<TimeTrigger>)this.toolkit.getSortedSet(triggerSetName, TimeTrigger.class));
        this.timeTriggerSetReference.compareAndSet(null, set);
        return this.timeTriggerSetReference.get();
    }

    public ToolkitLock getLock(ToolkitLockTypeInternal lockType) {
        String lockName = this.generateName(SINGLE_LOCK_NAME_PREFIX);
        return ((ToolkitInternal)this.toolkit).getLock(lockName, lockType);
    }
}

