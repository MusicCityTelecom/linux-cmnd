/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.env.Environment
 *  org.springframework.core.style.ToStringCreator
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class Profiles
implements Iterable<String> {
    public static final String INCLUDE_PROFILES_PROPERTY_NAME = "spring.profiles.include";
    static final ConfigurationPropertyName INCLUDE_PROFILES = ConfigurationPropertyName.of("spring.profiles.include");
    private static final Bindable<MultiValueMap<String, String>> STRING_STRINGS_MAP = Bindable.of(ResolvableType.forClassWithGenerics(MultiValueMap.class, (Class[])new Class[]{String.class, String.class}));
    private static final Bindable<Set<String>> STRING_SET = Bindable.setOf(String.class);
    private final MultiValueMap<String, String> groups;
    private final List<String> activeProfiles;
    private final List<String> defaultProfiles;

    Profiles(Environment environment, Binder binder, Collection<String> additionalProfiles) {
        this.groups = binder.bind("spring.profiles.group", STRING_STRINGS_MAP).orElseGet(LinkedMultiValueMap::new);
        this.activeProfiles = this.expandProfiles(this.getActivatedProfiles(environment, binder, additionalProfiles));
        this.defaultProfiles = this.expandProfiles(this.getDefaultProfiles(environment, binder));
    }

    private List<String> getActivatedProfiles(Environment environment, Binder binder, Collection<String> additionalProfiles) {
        return this.asUniqueItemList(this.getProfiles(environment, binder, Type.ACTIVE), additionalProfiles);
    }

    private List<String> getDefaultProfiles(Environment environment, Binder binder) {
        return this.asUniqueItemList(this.getProfiles(environment, binder, Type.DEFAULT));
    }

    private Collection<String> getProfiles(Environment environment, Binder binder, Type type) {
        String environmentPropertyValue = environment.getProperty(type.getName());
        Set environmentPropertyProfiles = !StringUtils.hasLength((String)environmentPropertyValue) ? Collections.emptySet() : StringUtils.commaDelimitedListToSet((String)StringUtils.trimAllWhitespace((String)environmentPropertyValue));
        LinkedHashSet<String> environmentProfiles = new LinkedHashSet<String>(Arrays.asList(type.get(environment)));
        BindResult<Set<String>> boundProfiles = binder.bind(type.getName(), STRING_SET);
        if (this.hasProgrammaticallySetProfiles(type, environmentPropertyValue, environmentPropertyProfiles, environmentProfiles)) {
            if (!type.isMergeWithEnvironmentProfiles() || !boundProfiles.isBound()) {
                return environmentProfiles;
            }
            return boundProfiles.map(bound -> this.merge((Set<String>)environmentProfiles, (Set<String>)bound)).get();
        }
        return boundProfiles.orElse(type.getDefaultValue());
    }

    private boolean hasProgrammaticallySetProfiles(Type type, String environmentPropertyValue, Set<String> environmentPropertyProfiles, Set<String> environmentProfiles) {
        if (!StringUtils.hasLength((String)environmentPropertyValue)) {
            return !type.getDefaultValue().equals(environmentProfiles);
        }
        if (type.getDefaultValue().equals(environmentProfiles)) {
            return false;
        }
        return !environmentPropertyProfiles.equals(environmentProfiles);
    }

    private Set<String> merge(Set<String> environmentProfiles, Set<String> bound) {
        LinkedHashSet<String> result = new LinkedHashSet<String>(environmentProfiles);
        result.addAll(bound);
        return result;
    }

    private List<String> expandProfiles(List<String> profiles) {
        ArrayDeque stack = new ArrayDeque();
        this.asReversedList(profiles).forEach(stack::push);
        LinkedHashSet<String> expandedProfiles = new LinkedHashSet<String>();
        while (!stack.isEmpty()) {
            String current = (String)stack.pop();
            if (!expandedProfiles.add(current)) continue;
            this.asReversedList((List)this.groups.get((Object)current)).forEach(stack::push);
        }
        return this.asUniqueItemList(expandedProfiles);
    }

    private List<String> asReversedList(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        ArrayList<String> reversed = new ArrayList<String>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    private List<String> asUniqueItemList(Collection<String> profiles) {
        return this.asUniqueItemList(profiles, null);
    }

    private List<String> asUniqueItemList(Collection<String> profiles, Collection<String> additional) {
        LinkedHashSet<String> uniqueItems = new LinkedHashSet<String>();
        if (!CollectionUtils.isEmpty(additional)) {
            uniqueItems.addAll(additional);
        }
        uniqueItems.addAll(profiles);
        return Collections.unmodifiableList(new ArrayList(uniqueItems));
    }

    @Override
    public Iterator<String> iterator() {
        return this.getAccepted().iterator();
    }

    public List<String> getActive() {
        return this.activeProfiles;
    }

    public List<String> getDefault() {
        return this.defaultProfiles;
    }

    public List<String> getAccepted() {
        return !this.activeProfiles.isEmpty() ? this.activeProfiles : this.defaultProfiles;
    }

    public boolean isAccepted(String profile) {
        return this.getAccepted().contains(profile);
    }

    public String toString() {
        ToStringCreator creator = new ToStringCreator((Object)this);
        creator.append("active", (Object)this.getActive().toString());
        creator.append("default", (Object)this.getDefault().toString());
        creator.append("accepted", (Object)this.getAccepted().toString());
        return creator.toString();
    }

    private static enum Type {
        ACTIVE("spring.profiles.active", Environment::getActiveProfiles, true, Collections.emptySet()),
        DEFAULT("spring.profiles.default", Environment::getDefaultProfiles, false, Collections.singleton("default"));

        private final Function<Environment, String[]> getter;
        private final boolean mergeWithEnvironmentProfiles;
        private final String name;
        private final Set<String> defaultValue;

        private Type(String name, Function<Environment, String[]> getter, boolean mergeWithEnvironmentProfiles, Set<String> defaultValue) {
            this.name = name;
            this.getter = getter;
            this.mergeWithEnvironmentProfiles = mergeWithEnvironmentProfiles;
            this.defaultValue = defaultValue;
        }

        String getName() {
            return this.name;
        }

        String[] get(Environment environment) {
            return this.getter.apply(environment);
        }

        Set<String> getDefaultValue() {
            return this.defaultValue;
        }

        boolean isMergeWithEnvironmentProfiles() {
            return this.mergeWithEnvironmentProfiles;
        }
    }
}

