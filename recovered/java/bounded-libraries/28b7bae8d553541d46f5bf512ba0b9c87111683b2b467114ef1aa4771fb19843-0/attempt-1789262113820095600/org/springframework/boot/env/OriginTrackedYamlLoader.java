/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.YamlProcessor
 *  org.springframework.core.io.Resource
 *  org.yaml.snakeyaml.DumperOptions
 *  org.yaml.snakeyaml.LoaderOptions
 *  org.yaml.snakeyaml.Yaml
 *  org.yaml.snakeyaml.constructor.BaseConstructor
 *  org.yaml.snakeyaml.constructor.SafeConstructor
 *  org.yaml.snakeyaml.error.Mark
 *  org.yaml.snakeyaml.nodes.CollectionNode
 *  org.yaml.snakeyaml.nodes.MappingNode
 *  org.yaml.snakeyaml.nodes.Node
 *  org.yaml.snakeyaml.nodes.NodeTuple
 *  org.yaml.snakeyaml.nodes.ScalarNode
 *  org.yaml.snakeyaml.nodes.Tag
 *  org.yaml.snakeyaml.representer.Representer
 *  org.yaml.snakeyaml.resolver.Resolver
 */
package org.springframework.boot.env;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.origin.TextResourceOrigin;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

class OriginTrackedYamlLoader
extends YamlProcessor {
    private final Resource resource;

    OriginTrackedYamlLoader(Resource resource) {
        this.resource = resource;
        this.setResources(new Resource[]{resource});
    }

    protected Yaml createYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false);
        loaderOptions.setMaxAliasesForCollections(Integer.MAX_VALUE);
        loaderOptions.setAllowRecursiveKeys(true);
        return this.createYaml(loaderOptions);
    }

    private Yaml createYaml(LoaderOptions loaderOptions) {
        OriginTrackingConstructor constructor = new OriginTrackingConstructor(loaderOptions);
        Representer representer = new Representer();
        DumperOptions dumperOptions = new DumperOptions();
        LimitedResolver resolver = new LimitedResolver();
        return new Yaml((BaseConstructor)constructor, representer, dumperOptions, loaderOptions, (Resolver)resolver);
    }

    List<Map<String, Object>> load() {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        this.process((properties, map) -> result.add(this.getFlattenedMap(map)));
        return result;
    }

    private static class LimitedResolver
    extends Resolver {
        private LimitedResolver() {
        }

        public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
            if (tag == Tag.TIMESTAMP) {
                return;
            }
            super.addImplicitResolver(tag, regexp, first);
        }
    }

    private static class KeyScalarNode
    extends ScalarNode {
        KeyScalarNode(ScalarNode node) {
            super(node.getTag(), node.getValue(), node.getStartMark(), node.getEndMark(), node.getScalarStyle());
        }

        static NodeTuple get(NodeTuple nodeTuple) {
            Node keyNode = nodeTuple.getKeyNode();
            Node valueNode = nodeTuple.getValueNode();
            return new NodeTuple(KeyScalarNode.get(keyNode), valueNode);
        }

        private static Node get(Node node) {
            if (node instanceof ScalarNode) {
                return new KeyScalarNode((ScalarNode)node);
            }
            return node;
        }
    }

    private class OriginTrackingConstructor
    extends SafeConstructor {
        OriginTrackingConstructor(LoaderOptions loadingConfig) {
            super(loadingConfig);
        }

        public Object getData() throws NoSuchElementException {
            Object data = super.getData();
            if (data instanceof CharSequence && ((CharSequence)data).length() == 0) {
                return null;
            }
            return data;
        }

        protected Object constructObject(Node node) {
            if (node instanceof CollectionNode && ((CollectionNode)node).getValue().isEmpty()) {
                return this.constructTrackedObject(node, super.constructObject(node));
            }
            if (node instanceof ScalarNode && !(node instanceof KeyScalarNode)) {
                return this.constructTrackedObject(node, super.constructObject(node));
            }
            if (node instanceof MappingNode) {
                this.replaceMappingNodeKeys((MappingNode)node);
            }
            return super.constructObject(node);
        }

        private void replaceMappingNodeKeys(MappingNode node) {
            node.setValue(node.getValue().stream().map(KeyScalarNode::get).collect(Collectors.toList()));
        }

        private Object constructTrackedObject(Node node, Object value) {
            Origin origin = this.getOrigin(node);
            return OriginTrackedValue.of(this.getValue(value), origin);
        }

        private Object getValue(Object value) {
            return value != null ? value : "";
        }

        private Origin getOrigin(Node node) {
            Mark mark = node.getStartMark();
            TextResourceOrigin.Location location = new TextResourceOrigin.Location(mark.getLine(), mark.getColumn());
            return new TextResourceOrigin(OriginTrackedYamlLoader.this.resource, location);
        }
    }
}

