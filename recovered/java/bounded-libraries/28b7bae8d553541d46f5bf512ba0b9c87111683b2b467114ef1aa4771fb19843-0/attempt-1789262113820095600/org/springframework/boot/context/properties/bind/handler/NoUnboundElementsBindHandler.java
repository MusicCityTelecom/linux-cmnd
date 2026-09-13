/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind.handler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import org.springframework.boot.context.properties.bind.AbstractBindHandler;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.UnboundConfigurationPropertiesException;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;

public class NoUnboundElementsBindHandler
extends AbstractBindHandler {
    private final Set<ConfigurationPropertyName> boundNames = new HashSet<ConfigurationPropertyName>();
    private final Set<ConfigurationPropertyName> attemptedNames = new HashSet<ConfigurationPropertyName>();
    private final Function<ConfigurationPropertySource, Boolean> filter;

    NoUnboundElementsBindHandler() {
        this(BindHandler.DEFAULT, configurationPropertySource -> true);
    }

    public NoUnboundElementsBindHandler(BindHandler parent) {
        this(parent, configurationPropertySource -> true);
    }

    public NoUnboundElementsBindHandler(BindHandler parent, Function<ConfigurationPropertySource, Boolean> filter) {
        super(parent);
        this.filter = filter;
    }

    @Override
    public <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
        this.attemptedNames.add(name);
        return super.onStart(name, target, context);
    }

    @Override
    public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        this.boundNames.add(name);
        return super.onSuccess(name, target, context, result);
    }

    @Override
    public Object onFailure(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Exception error) throws Exception {
        if (error instanceof UnboundConfigurationPropertiesException) {
            throw error;
        }
        return super.onFailure(name, target, context, error);
    }

    @Override
    public void onFinish(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) throws Exception {
        if (context.getDepth() == 0) {
            this.checkNoUnboundElements(name, context);
        }
    }

    private void checkNoUnboundElements(ConfigurationPropertyName name, BindContext context) {
        TreeSet<ConfigurationProperty> unbound = new TreeSet<ConfigurationProperty>();
        for (ConfigurationPropertySource source : context.getSources()) {
            if (!(source instanceof IterableConfigurationPropertySource) || !this.filter.apply(source).booleanValue()) continue;
            this.collectUnbound(name, unbound, (IterableConfigurationPropertySource)source);
        }
        if (!unbound.isEmpty()) {
            throw new UnboundConfigurationPropertiesException(unbound);
        }
    }

    private void collectUnbound(ConfigurationPropertyName name, Set<ConfigurationProperty> unbound, IterableConfigurationPropertySource source) {
        ConfigurationPropertySource filtered = source.filter(candidate -> this.isUnbound(name, (ConfigurationPropertyName)candidate));
        Iterator<ConfigurationPropertyName> iterator = filtered.iterator();
        while (iterator.hasNext()) {
            ConfigurationPropertyName unboundName = iterator.next();
            try {
                unbound.add(source.filter(candidate -> this.isUnbound(name, (ConfigurationPropertyName)candidate)).getConfigurationProperty(unboundName));
            }
            catch (Exception exception) {}
        }
    }

    private boolean isUnbound(ConfigurationPropertyName name, ConfigurationPropertyName candidate) {
        if (name.isAncestorOf(candidate)) {
            return !this.boundNames.contains(candidate) && !this.isOverriddenCollectionElement(candidate);
        }
        return false;
    }

    private boolean isOverriddenCollectionElement(ConfigurationPropertyName candidate) {
        String zeroethProperty;
        int lastIndex = candidate.getNumberOfElements() - 1;
        if (candidate.isLastElementIndexed()) {
            ConfigurationPropertyName propertyName = candidate.chop(lastIndex);
            return this.boundNames.contains(propertyName);
        }
        Indexed indexed = this.getIndexed(candidate);
        if (indexed != null && this.boundNames.contains(ConfigurationPropertyName.of(zeroethProperty = indexed.getName() + "[0]"))) {
            String nestedZeroethProperty = zeroethProperty + "." + indexed.getNestedPropertyName();
            return this.isCandidateValidPropertyName(nestedZeroethProperty);
        }
        return false;
    }

    private boolean isCandidateValidPropertyName(String nestedZeroethProperty) {
        return this.attemptedNames.contains(ConfigurationPropertyName.of(nestedZeroethProperty));
    }

    private Indexed getIndexed(ConfigurationPropertyName candidate) {
        for (int i = 0; i < candidate.getNumberOfElements(); ++i) {
            if (!candidate.isNumericIndex(i)) continue;
            return new Indexed(candidate.chop(i).toString(), candidate.getElement(i + 1, ConfigurationPropertyName.Form.UNIFORM));
        }
        return null;
    }

    private static final class Indexed {
        private final String name;
        private final String nestedPropertyName;

        private Indexed(String name, String nestedPropertyName) {
            this.name = name;
            this.nestedPropertyName = nestedPropertyName;
        }

        String getName() {
            return this.name;
        }

        String getNestedPropertyName() {
            return this.nestedPropertyName;
        }
    }
}

