/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.yaml.snakeyaml.Yaml
 *  org.yaml.snakeyaml.constructor.BaseConstructor
 *  org.yaml.snakeyaml.constructor.Constructor
 */
package org.springframework.boot.json;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.json.AbstractJsonParser;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlJsonParser
extends AbstractJsonParser {
    private final Yaml yaml = new Yaml((BaseConstructor)new TypeLimitedConstructor());

    @Override
    public Map<String, Object> parseMap(String json) {
        return this.parseMap(json, trimmed -> (Map)this.yaml.loadAs(trimmed, Map.class));
    }

    @Override
    public List<Object> parseList(String json) {
        return this.parseList(json, trimmed -> (List)this.yaml.loadAs(trimmed, List.class));
    }

    private static class TypeLimitedConstructor
    extends Constructor {
        private static final Set<String> SUPPORTED_TYPES;

        private TypeLimitedConstructor() {
        }

        protected Class<?> getClassForName(String name) throws ClassNotFoundException {
            Assert.state((boolean)SUPPORTED_TYPES.contains(name), () -> "Unsupported '" + name + "' type encountered in YAML document");
            return super.getClassForName(name);
        }

        static {
            LinkedHashSet<Class> supportedTypes = new LinkedHashSet<Class>();
            supportedTypes.add(List.class);
            supportedTypes.add(Map.class);
            SUPPORTED_TYPES = supportedTypes.stream().map(Class::getName).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        }
    }
}

